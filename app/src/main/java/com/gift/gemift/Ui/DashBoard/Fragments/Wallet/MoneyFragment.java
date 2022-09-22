package com.gift.gemift.Ui.DashBoard.Fragments.Wallet;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.collection.ArraySet;
import androidx.fragment.app.Fragment;

import com.gift.gemift.Model.NotificationResponse;
import com.gift.gemift.Model.Optin;
import com.gift.gemift.Model.StatusItem;
import com.gift.gemift.Model.Wallet.MessageItem;
import com.gift.gemift.Model.Wallet.WalletResponse;
import com.gift.gemift.Model.WalletDetails;
import com.gift.gemift.Network.ApiClient;
import com.gift.gemift.Network.ApiInterface;
import com.gift.gemift.Storage.AppPreference.AppPreferences;
import com.gift.gemift.Ui.Adaptor.MyWalletAdapter;
import com.gift.gemift.Ui.Adaptor.NotificationAdaptor;
import com.gift.gemift.Utils.AppUtils;
import com.gift.gemift.Utils.Utils;
import com.gift.gemift.databinding.FragmentWalletBinding;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Response;

public class MoneyFragment extends Fragment implements MyWalletAdapter.onClickPerformed {

    private FragmentWalletBinding binding;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    MessageItem item;
    public MoneyFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentWalletBinding.inflate(inflater, container, false);

        binding.actionClose.setOnClickListener(view -> binding.llPay.setVisibility(View.GONE));

        binding.pay.setOnClickListener(view -> {
            if(!binding.edtPay.getText().toString().isEmpty()){
                Optin optin = new Optin();
                optin.setUser_id(item.getUserId());
                optin.setTransaction_id(item.getTransactionId());
                optin.setRequest_type("pay_amount");
                optin.setPaid_amount(binding.edtPay.getText().toString());
                callPay(optin);
            }

        });

        binding.llPay.setOnClickListener(view ->{

        } );
        return binding.getRoot();
    }




    @Override
    public void onResume() {
        super.onResume();

        getTransition();
    }

    private void getTransition() {
        Utils.showProgressBar(getActivity());
        List<MessageItem> messageItems = new ArrayList<>();
        AppPreferences appPreferences = new AppPreferences(getContext());
        ApiInterface apiInterface = ApiClient.getApiClientShareFund().create(ApiInterface.class);
        Observable<Response<WalletResponse>> notify = apiInterface.getTransaction("get_team_buy_status_by_user", appPreferences.getUserId());
        compositeDisposable.add(notify
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(result -> result)
                .subscribe(response -> {
                    Utils.hideProgressBar(getActivity());
                    if (response.isSuccessful() && (response.code() == 200 || response.code() == 201)) {
                        if (response.body() != null) {
                            if(response.body().getMessage().size()!=0){
                                messageItems.addAll(response.body().getMessage());
                                binding.rvList.setAdapter(new MyWalletAdapter(messageItems,getContext(),this));
                            }else {
                                binding.noData.setVisibility(View.VISIBLE);
                            }
                        }
                    } else if (response.code() == 400) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.errorBody().string());
                            String error = jsonObject.getString("Error");
                            Toast.makeText(getContext(), error, Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    } else {
                        try {
                            String error = response.errorBody().toString();
                            Toast.makeText(getContext(), error, Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }

                }, throwable -> {
                    AppUtils.showMessage(getContext(), throwable.getMessage());
                }));
    }

    @Override
    public void onClick(MessageItem statusItem) {
        startActivity(new Intent(getActivity(),TransactionDetails.class).putExtra("transaction_id",statusItem.getTransactionId()).putExtra("userType", statusItem.getUserType()).putExtra("optStatus", statusItem.getOptInFlag()));
    }

    @Override
    public void onSwitchAction(MessageItem item, boolean check) {

        Optin optin = new Optin();
        optin.setUser_id(item.getUserId());
        optin.setTransaction_id(item.getTransactionId());
        if (check){
            optin.setRequest_type("opt_in");
            optin.setOpt_in_flag("Y");
        }else {
            optin.setRequest_type("opt_out");
            optin.setOpt_in_flag("N");
        }
        callOpt(optin);
    }

    @Override
    public void onClickPay(MessageItem item) {
        binding.llPay.setVisibility(View.VISIBLE);
        this.item = item;
    }

    private void callOpt(Optin optin) {

        ApiInterface apiInterface = ApiClient.getApiClientShareFund().create(ApiInterface.class);
        Observable<Response<ResponseBody>> notify = apiInterface.opt(optin);
        compositeDisposable.add(notify
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(result -> result)
                .subscribe(responseBodyResponse -> {
                    onResume();
                }));
    }

    private void callPay(Optin optin) {
        ApiInterface apiInterface = ApiClient.getApiClientShareFund().create(ApiInterface.class);
        Observable<Response<ResponseBody>> notify = apiInterface.pay_amount(optin);
        compositeDisposable.add(notify
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(result -> result)
                .subscribe(this::response,this::thrown));
    }

    private void thrown(Throwable throwable) {
        Toast.makeText(getActivity(), ""+throwable.getMessage(), Toast.LENGTH_SHORT).show();
    }

    private void response(Response<ResponseBody> response) {
        if (response.isSuccessful() && (response.code() == 200 || response.code() == 201)) {
            binding.edtPay.setText("");
            binding.llPay.setVisibility(View.GONE);
            getTransition();
        } else if (response.code() == 400) {
            try {
                JSONObject jsonObject = new JSONObject(response.errorBody().string());
                String error = jsonObject.getString("Error");
                Toast.makeText(getContext(), error, Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            }
        } else {
            try {
                String error = response.errorBody().toString();
                Toast.makeText(getContext(), error, Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }


}