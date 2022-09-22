package com.gift.gemift.Ui.DashBoard.Fragments.Wallet;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.gift.gemift.Model.Optin;
import com.gift.gemift.Model.Wallet.DetailItem;
import com.gift.gemift.Model.Wallet.Header;
import com.gift.gemift.Model.Wallet.TransactionDetailResponse;
import com.gift.gemift.Network.ApiClient;
import com.gift.gemift.Network.ApiInterface;
import com.gift.gemift.Storage.AppPreference.AppPreferences;
import com.gift.gemift.Ui.Adaptor.TransactionAdapter;
import com.gift.gemift.Utils.AppUtils;
import com.gift.gemift.databinding.OccationTransactionDetailsBinding;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Response;

public class TransactionDetails extends AppCompatActivity {

    private OccationTransactionDetailsBinding binding;
    private String transactionId;
    private String userType;
    private String optStatus;

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private Header header;

    AppPreferences appPreferences;

    List<DetailItem> messageItems = new ArrayList<>();




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = OccationTransactionDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        appPreferences = new AppPreferences(this);

        LoadUi();
    }

    private void LoadUi() {
        Intent dataIntent = getIntent();
        userType = dataIntent.getStringExtra("userType");
        transactionId = dataIntent.getStringExtra("transaction_id");
        optStatus = dataIntent.getStringExtra("optStatus");


        if(optStatus.equals("Y")){
            binding.switchOption.setChecked(true);
        }else {
            binding.switchOption.setChecked(false);
        }

        binding.back.setOnClickListener(view -> finish());
        binding.btnComplete.setOnClickListener(view -> {
            Optin optin = new Optin();
            optin.setTransaction_id(transactionId);
            optin.setRequest_type("complete_transaction");
            callOpt(optin);
        });
        binding.btnCancel.setOnClickListener(view -> {
            Optin optin = new Optin();
            optin.setTransaction_id(transactionId);
            optin.setRequest_type("cancel_transaction");
            callOpt(optin);
        });



        binding.switchOption.setChecked(true);
        binding.switchOption.setOnCheckedChangeListener((buttonView, isChecked) -> {

            Optin optin = new Optin();
            optin.setUser_id(appPreferences.getUserId());
            optin.setTransaction_id(header.getTransactionId());
            if (isChecked){
                optin.setRequest_type("opt_in");
                optin.setOpt_in_flag("Y");
            }else {
                optin.setRequest_type("opt_out");
                optin.setOpt_in_flag("N");
            }
            callOpt(optin);
        });


        binding.txtPay.setOnClickListener(view -> binding.llPay.setVisibility(View.VISIBLE));

        binding.pay.setOnClickListener(view -> {
            if(!binding.edtPay.getText().toString().isEmpty()){
                Optin optin = new Optin();
                optin.setUser_id(appPreferences.getUserId());
                optin.setTransaction_id(header.getTransactionId());
                optin.setRequest_type("pay_amount");
                optin.setPaid_amount(binding.edtPay.getText().toString());
                callPay(optin);
            }
        });

        binding.llPay.setOnClickListener(view -> {});

    }

    private void getTransition() {


        AppPreferences appPreferences = new AppPreferences(this);
        ApiInterface apiInterface = ApiClient.getApiClientShareFund().create(ApiInterface.class);
        Observable<Response<TransactionDetailResponse>> notify = apiInterface.getTransactionDetails("get_team_buy_status", transactionId);
        compositeDisposable.add(notify
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(result -> result)
                .subscribe(response -> {
                    if (response.isSuccessful() && (response.code() == 200 || response.code() == 201)) {
                        if (response.body() != null) {
                            if(response.body().getTransaction().size()!=0){
                                if(messageItems.size()==0){
                                    messageItems.addAll(response.body().getTransaction().get(1).getDetail());
                                    setHeader(response.body().getTransaction().get(0).getHeader());
                                    setRv(messageItems, true);
                                }else {
                                    messageItems.addAll(response.body().getTransaction().get(1).getDetail());
                                    setRv(messageItems, false);
                                }
                            }else {
                            }
                        }
                    } else if (response.code() == 400) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.errorBody().string());
                            String error = jsonObject.getString("Error");
                            Toast.makeText(TransactionDetails.this, error, Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            Toast.makeText(TransactionDetails.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    } else {
                        try {
                            String error = response.errorBody().toString();
                            Toast.makeText(TransactionDetails.this, error, Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            Toast.makeText(TransactionDetails.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }

                }, throwable -> {
                    AppUtils.showMessage(TransactionDetails.this, throwable.getMessage());
                }));
    }

    private void setRv(List<DetailItem> messageItems, boolean b) {
        binding.rvList.setAdapter(new TransactionAdapter(messageItems,this));
    }

    private void setHeader(Header header) {
        this.header = header;
        binding.title.setText(header.getSecretFirstName()+" "+header.getSecretLastName());
        binding.titleCircle.setText(header.getFriendCircleName());
        binding.titleName.setText(header.getOccasionName()+"");
        binding.productAmount.setText(header.getProductPrice()+"");
        binding.userCount.setText("Total Member : "+header.getMiscCost());

        if(appPreferences.getUserId().equals(header.getUserId())){
            binding.btnCancel.setVisibility(View.VISIBLE);
            binding.btnComplete.setVisibility(View.VISIBLE);
        }else if(appPreferences.getUserId().equals(header.getFriendCircleCreatorId())){
            if(!header.getFriendCircleId().equals(header.getUserId())){
                binding.btnCancel.setVisibility(View.VISIBLE);
                binding.btnComplete.setVisibility(View.GONE);
                binding.llOpt.setVisibility(View.VISIBLE);
            }
        }else {
            binding.llOpt.setVisibility(View.VISIBLE);
        }



        binding.perRate.setText(header.getMiscCost()+"/per");

        if(header.getExpirationDate()==null || header.getExpirationDate().isEmpty()){
            binding.endsDate.setText("Ends :"+AppUtils.addDayWithNoOfDays(AppUtils.getCurrentDateCopy(),5));
        }else {
            binding.endsDate.setText("Ends : "+header.getExpirationDate().replace(header.getExpirationDate().substring(10),""));

        }

        if(header.getInitiatedDate()==null || header.getInitiatedDate().isEmpty()){
            binding.startDate.setText("Starts :"+AppUtils.getCurrentDateCopy());
        }else {
            binding.startDate.setText("Starts : "+header.getInitiatedDate().replace(header.getInitiatedDate().substring(10),""));
        }


        String start = header.getInitiatedDate();

        String date = header.getExpirationDate();

        Date started = AppUtils.convertDate(start.replace(start.substring(10),""));
        Date expired = AppUtils.convertDate(date.replace(date.substring(10),""));
        if (!userType.equals("Admin")){
            if (started.after(expired))
            {
                Log.i("DateValidation", started+":"+expired);
                binding.llOpt.setVisibility(View.INVISIBLE);
                binding.txtPay.setVisibility(View.VISIBLE);
            }else {
                binding.llOpt.setVisibility(View.VISIBLE);
                binding.txtPay.setVisibility(View.GONE);
            }
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
        }
        return true;
    }

    private void callOpt(Optin optin) {

        ApiInterface apiInterface = ApiClient.getApiClientShareFund().create(ApiInterface.class);
        Observable<Response<ResponseBody>> notify = apiInterface.opt(optin);
        compositeDisposable.add(notify
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(result -> result)
                .subscribe());
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
        Toast.makeText(this, ""+throwable.getMessage(), Toast.LENGTH_SHORT).show();
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
                Toast.makeText(this, error, Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        } else {
            try {
                String error = response.errorBody().toString();
                Toast.makeText(this, error, Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getTransition();
    }
}
