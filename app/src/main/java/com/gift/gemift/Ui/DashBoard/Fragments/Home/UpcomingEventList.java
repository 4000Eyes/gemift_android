package com.gift.gemift.Ui.DashBoard.Fragments.Home;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import com.cazaea.sweetalert.SweetAlertDialog;
import com.gift.gemift.Model.MyOccasionDataItems;
import com.gift.gemift.Model.MyOccasionResponse;
import com.gift.gemift.Model.OccasionsModel;
import com.gift.gemift.Model.Root;
import com.gift.gemift.Network.ApiClient;
import com.gift.gemift.Network.ApiInterface;
import com.gift.gemift.R;
import com.gift.gemift.Storage.AppPreference.AppPreferences;
import com.gift.gemift.Ui.Adaptor.UpcomingEventsAbove28Adaptor;
import com.gift.gemift.Ui.Adaptor.UpcomingEventsAdaptor;
import com.gift.gemift.Ui.Adaptor.UpcomingEventsBelow14Adaptor;
import com.gift.gemift.Ui.Adaptor.UpcomingEventsBelow28Adaptor;
import com.gift.gemift.Utils.AppUtils;
import com.gift.gemift.Utils.FilterHelper;
import com.gift.gemift.databinding.FriendCircleListBinding;
import com.gift.gemift.databinding.UpcomingEventsBinding;
import com.gift.gemift.databinding.UpcommingEventListBinding;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class UpcomingEventList extends AppCompatActivity {
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    public UpcommingEventListBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = UpcommingEventListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appbar.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        final Drawable upArrow = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_baseline_arrow, null);
        FilterHelper.setColorFilter(upArrow, getResources().getColor(R.color.white), FilterHelper.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);

//
        LoadUi();
    }

    @SuppressLint("SetTextI18n")
    private void LoadUi() {
        binding.appbar.txtTitle.setText("Upcoming Event");
        AppPreferences appPreferences = new AppPreferences(this);
        getMyOccasion(appPreferences.getUserId(), appPreferences.getPhoneNumber(), appPreferences.getCountryCode());
    }

    private void getMyOccasion(String logged_user_id, String phone_number, String countryCode) {
        SweetAlertDialog pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);
        pDialog.show();
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        Observable<Response<MyOccasionResponse>>notify = apiInterface.getUpComingOccasions(2, logged_user_id, phone_number, countryCode);

//        Observable<Response<NotificationModel>> notify = apiInterface.getInvites(1, logged_user_id, phone_number);
        compositeDisposable.add(notify
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(result -> result)
                .subscribe(response -> {
                    pDialog.dismiss();

                    if (response.isSuccessful() && (response.code() == 200 || response.code() == 201)) {
                        if (response.body() != null) {
                            ArrayList<MyOccasionDataItems> occasionsModelArrayList = new ArrayList<>();
                            occasionsModelArrayList.addAll(response.body().getMyOccasionData().get(0).getOccasions());

                            if(occasionsModelArrayList.size()!=0){
                                LoadOccasionRecycle(occasionsModelArrayList);
                                binding.noData.setVisibility(View.GONE);
                            }else {
                                binding.noData.setVisibility(View.GONE);
                            }
                        }

                    } else if (response.code() == 400) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.errorBody().string());
                            String error = jsonObject.getString("Error");
                            Toast.makeText(this, error, Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }

                }, throwable -> {
                    pDialog.dismiss();
                    AppUtils.showMessage(this, throwable.getMessage());
                }));
    }

    private void LoadOccasionRecycle(ArrayList<MyOccasionDataItems> occasionsModelArrayList) {
        IgnoreCaseComparator icc = new IgnoreCaseComparator();
        Collections.sort(occasionsModelArrayList, icc);
        ArrayList<MyOccasionDataItems> below14 = new ArrayList<>();
        ArrayList<MyOccasionDataItems> below28 = new ArrayList<>();
        ArrayList<MyOccasionDataItems> above28 = new ArrayList<>();



        for(MyOccasionDataItems myOccasionDataItems : occasionsModelArrayList){
            if(myOccasionDataItems.getDaysLeft()<=14){
                below14.add(myOccasionDataItems);
            }else if (myOccasionDataItems.getDaysLeft() <=28 && myOccasionDataItems.getDaysLeft() >= 14){
                below28.add(myOccasionDataItems);
            }else if(myOccasionDataItems.getDaysLeft() >28){
                above28.add(myOccasionDataItems);
            }
        }

        if(below14.size() != 0)
        {
            binding.llBelow14days.setVisibility(View.VISIBLE);
            UpcomingEventsBelow14Adaptor upcomingEventsBelow14Adaptor = new UpcomingEventsBelow14Adaptor(below14,this);
            binding.rvBelow14days.setAdapter(upcomingEventsBelow14Adaptor);
            upcomingEventsBelow14Adaptor.notifyDataSetChanged();
        }

        if(below28.size() != 0)
        {
            binding.llBelow28days.setVisibility(View.VISIBLE);
            UpcomingEventsBelow28Adaptor upcomingEventsBelow28Adaptor = new UpcomingEventsBelow28Adaptor(below28,this);
            binding.rvBelow28days.setAdapter(upcomingEventsBelow28Adaptor);
            upcomingEventsBelow28Adaptor.notifyDataSetChanged();
        }

        if(above28.size() != 0)
        {
            binding.llAbove28days.setVisibility(View.VISIBLE);
            UpcomingEventsAbove28Adaptor upcomingEventsAbove28Adaptor = new UpcomingEventsAbove28Adaptor(above28,this);
            binding.rvAbove28days.setAdapter(upcomingEventsAbove28Adaptor);
            upcomingEventsAbove28Adaptor.notifyDataSetChanged();
        }
//        UpcomingEventsAdaptor upcomingEventsAdaptor = new UpcomingEventsAdaptor(occasionsModelArrayList, this);
//        binding.recyleFriendLits.setAdapter(upcomingEventsAdaptor);
//        upcomingEventsAdaptor.notifyDataSetChanged();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return (super.onOptionsItemSelected(menuItem));
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();

        }
        return true;
    }
}

class IgnoreCaseComparator implements Comparator<MyOccasionDataItems> {


    @Override
    public int compare(MyOccasionDataItems ob1, MyOccasionDataItems ob2) {

        return Integer.compare(ob1.getDaysLeft(), ob2.getDaysLeft());
    }
}
