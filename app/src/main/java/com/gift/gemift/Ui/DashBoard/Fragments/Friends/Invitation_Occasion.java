package com.gift.gemift.Ui.DashBoard.Fragments.Friends;

import static com.gift.gemift.Utils.Constant.FRIEND_CIRCLE_ID;
import static com.gift.gemift.Utils.Constant.FRIEND_CIRCLE_NAME;
import static com.gift.gemift.Utils.Constant.MOBILE_NUMBER;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.gift.gemift.Model.MyOccasionResponse;
import com.gift.gemift.Model.Root;
import com.gift.gemift.Model.UnapprovedOccasion;
import com.gift.gemift.Network.ApiClient;
import com.gift.gemift.Network.ApiInterface;
import com.gift.gemift.Storage.AppPreference.AppPreferences;
import com.gift.gemift.Ui.Adaptor.OccasionInvitesAdaptor;
import com.gift.gemift.Ui.DashBoard.DashBoard;
import com.gift.gemift.Utils.AppUtils;
import com.gift.gemift.databinding.InvitationOccasionBinding;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class Invitation_Occasion extends AppCompatActivity {


    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    InvitationOccasionBinding binding;
    ArrayList<UnapprovedOccasion> unapprovedOccasions = new ArrayList<>();
    AppPreferences appPreferences;
    ApiInterface apiInterface;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = InvitationOccasionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        appPreferences = new AppPreferences(this);
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        Type type = new TypeToken<List<UnapprovedOccasion>>() {
        }.getType();

        unapprovedOccasions.clear();
        unapprovedOccasions = new Gson().fromJson(getIntent().getStringExtra("List"), type);
        LoadOccasionInvites(unapprovedOccasions);

        binding.txtSkip.setOnClickListener(view -> {
            getInvites(appPreferences.getUserId(), appPreferences.getPhoneNumber(), appPreferences.getCountryCode());
        });

    }

    @SuppressLint("NotifyDataSetChanged")
    private void LoadOccasionInvites(ArrayList<UnapprovedOccasion> occasionArrayList) {
        binding.rvOccasionInvites.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        OccasionInvitesAdaptor occasionInvitesAdaptor = new OccasionInvitesAdaptor(occasionArrayList, this, compositeDisposable, apiInterface, appPreferences);
        binding.rvOccasionInvites.setAdapter(occasionInvitesAdaptor);
        occasionInvitesAdaptor.notifyDataSetChanged();
    }

    private void getInvites(String logged_user_id, String phone_number, String countryCode) {
        ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please Wait...");
        pDialog.show();
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Observable<Response<MyOccasionResponse>> notify = apiInterface.getUpComingOccasions(2, logged_user_id, phone_number,countryCode);
        compositeDisposable.add(notify
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(result -> result)
                .subscribe(response -> {
                    pDialog.dismiss();
                    if (response.isSuccessful() && (response.code() == 200 || response.code() == 201)) {
                        if (response.body() != null) {
                            if (response.body().getMyOccasionData().get(0).getContributor_invites().size() > 0) {
                                String circlename =response.body().getMyOccasionData().get(0).getContributor_invites().get(0).getFriend_circle_name();
                                String circle_id = response.body().getMyOccasionData().get(0).getContributor_invites().get(0).getFriend_circle_id();
                                Intent i = new Intent(this, InvitationGroup.class);
                                i.putExtra(FRIEND_CIRCLE_ID, circle_id);
                                i.putExtra(FRIEND_CIRCLE_NAME, circlename);
                                i.putExtra(MOBILE_NUMBER, phone_number);
                                startActivity(i);

                            } else {
                                startActivity(new Intent(this, DashBoard.class));
                            }
                        }
                    } else if (response.code() == 400) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.errorBody().string());
                            String error = jsonObject.getString("Error");
                            Toast.makeText(Invitation_Occasion.this, error, Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            Toast.makeText(Invitation_Occasion.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }

                }, throwable -> {
                    pDialog.dismiss();
                    AppUtils.showMessage(Invitation_Occasion.this, throwable.getMessage());
                }));
    }

    @Override
    protected void onDestroy() {
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