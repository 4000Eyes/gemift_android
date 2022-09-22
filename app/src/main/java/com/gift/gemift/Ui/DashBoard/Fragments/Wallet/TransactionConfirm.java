package com.gift.gemift.Ui.DashBoard.Fragments.Wallet;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.gift.gemift.Model.TransactionConfirmInput;
import com.gift.gemift.Model.Wallet.DetailItem;
import com.gift.gemift.Model.Wallet.Header;
import com.gift.gemift.Model.Wallet.TransactionItem;
import com.gift.gemift.Network.ApiClient;
import com.gift.gemift.Network.ApiInterface;
import com.gift.gemift.Storage.AppPreference.AppPreferences;
import com.gift.gemift.Ui.Adaptor.TransactionAdapter;
import com.gift.gemift.Utils.AppUtils;
import com.gift.gemift.databinding.OccationTransactionDetailsBinding;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Response;

public class TransactionConfirm extends AppCompatActivity {

    private OccationTransactionDetailsBinding binding;



    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private Header header;

    AppPreferences appPreferences;

    Serializable data;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = OccationTransactionDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        appPreferences = new AppPreferences(this);

        LoadUi();
    }

    private void LoadUi() {

        binding.btnComplete.setText("Confirm");


        ArrayList<TransactionItem> myList = (ArrayList<TransactionItem>) getIntent().getSerializableExtra("data");



        binding.back.setOnClickListener(view -> finish());

        setHeader(myList.get(0).getHeader());
        setRv(myList.get(1).getDetail());

        binding.btnCancel.setOnClickListener(view -> {
            finish();
        });





    }


    private void setRv(List<DetailItem> messageItems) {
        binding.rvList.setAdapter(new TransactionAdapter(messageItems,this));
    }

    private void setHeader(Header header) {
        this.header = header;
        binding.title.setText(header.getSecretFirstName()+" "+header.getSecretLastName());
        binding.titleCircle.setText(header.getFriendCircleName());
        binding.titleName.setText(header.getOccasionName()+"");
        binding.productAmount.setText(header.getProductPrice()+"");
        binding.userCount.setText("Total Member : "+header.getMiscCost());

        binding.btnCancel.setVisibility(View.VISIBLE);
        binding.btnComplete.setVisibility(View.VISIBLE);

        binding.llOpt.setVisibility(View.GONE);



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

        binding.btnComplete.setOnClickListener(view -> {

            TransactionConfirmInput transactionConfirm = new TransactionConfirmInput();
            transactionConfirm.setTransaction_id(header.getTransactionId());
            transactionConfirm.setApi_call_time(AppUtils.getCurrentDateCopy());
            transactionConfirm.setTime_zone(TimeZone.getDefault().getID());
            transactionConfirm.setRequest_type("in_discussion");
            ApiInterface apiInterface = ApiClient.getApiClientShareFund().create(ApiInterface.class);
            Observable<Response<ResponseBody>> notify = apiInterface.confirm(transactionConfirm);
            compositeDisposable.add(notify
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .map(result -> result)
                    .subscribe(this::response,this::error));


        });

    }

    private void error(Throwable throwable) {
        Toast.makeText(this, ""+throwable.getMessage(), Toast.LENGTH_SHORT).show();
    }

    private void response(Response<ResponseBody> responseBodyResponse) {
        if(responseBodyResponse.isSuccessful()){
            Toast.makeText(this, "successfully initiated...", Toast.LENGTH_SHORT).show();
            finish();
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
        }
        return true;
    }


    @Override
    protected void onResume() {
        super.onResume();
    }
}
