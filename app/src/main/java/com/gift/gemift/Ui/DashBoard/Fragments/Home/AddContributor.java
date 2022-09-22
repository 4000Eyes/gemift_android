package com.gift.gemift.Ui.DashBoard.Fragments.Home;

import static com.gift.gemift.Utils.Constant.FRIEND_CIRCLE_ID;
import static com.gift.gemift.Utils.Constant.GEMIFT_CONTRIBUTOR;
import static com.gift.gemift.Utils.Constant.NEWCONTACT_CONTRIBUTOR;
import static com.gift.gemift.Utils.Constant.PH_CONTRIBUTOR;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.cazaea.sweetalert.SweetAlertDialog;
import com.gift.gemift.Model.AddFriendModel;
import com.gift.gemift.Model.ContributorsModel;
import com.gift.gemift.Network.ApiClient;
import com.gift.gemift.Network.ApiInterface;
import com.gift.gemift.R;
import com.gift.gemift.Storage.AppPreference.AppPreferences;
import com.gift.gemift.Ui.Adaptor.ContributorAdaptor;
import com.gift.gemift.Ui.DashBoard.DashBoard;
import com.gift.gemift.Ui.DashBoard.Fragments.Friends.AddFriend;
import com.gift.gemift.Ui.DashBoard.Fragments.Friends.PhoneContacts_Activity;
import com.gift.gemift.Ui.DashBoard.Fragments.Friends.SearchFriend;
import com.gift.gemift.Utils.AppUtils;
import com.gift.gemift.Utils.FilterHelper;
import com.gift.gemift.databinding.AddContributorBinding;

import org.json.JSONObject;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class AddContributor extends AppCompatActivity {
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    ArrayList<ContributorsModel> fullcontriarraylist = new ArrayList<>();
    ActivityResultLauncher<Intent> activityResultLauncher;
    ArrayList<ContributorsModel> contriarraylist = new ArrayList<>();
    private final String gender = "";
    private AddContributorBinding binding;
    private String friendCircleId = "";
    private AppPreferences appPreferences;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = AddContributorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appbar.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        final Drawable upArrow = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_baseline_arrow, null);
        FilterHelper.setColorFilter(upArrow, getResources().getColor(R.color.white), FilterHelper.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        appPreferences = new AppPreferences(this);
        binding.appbar.txtTitle.setText("Contributor");

        Intent dataIntent = getIntent();
        friendCircleId = dataIntent.getStringExtra(FRIEND_CIRCLE_ID);


        LoadUi();

    }

    private void LoadUi() {

        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                Bundle bundle = result.getData().getExtras();
                contriarraylist = (ArrayList<ContributorsModel>) bundle.get("data");

            } else if (result.getResultCode() == 101 && result.getData() != null) {
                Bundle bundle1 = result.getData().getExtras();

            }
            LoadRecycle();
        });

        binding.contriPhonecontacts.setOnClickListener(view -> {
            contriarraylist.clear();
            appPreferences.setIdentifier(PH_CONTRIBUTOR);
            Intent intent = new Intent(this, PhoneContacts_Activity.class);
            activityResultLauncher.launch(intent);

        });

        binding.contriNewcontact.setOnClickListener(view -> {
            contriarraylist.clear();
            appPreferences.setIdentifier(NEWCONTACT_CONTRIBUTOR);
            Intent intent = new Intent(this, AddFriend.class);
            activityResultLauncher.launch(intent);

        });


        binding.contriGemcontact.setOnClickListener(view -> {
            contriarraylist.clear();
            appPreferences.setIdentifier(GEMIFT_CONTRIBUTOR);
            Intent intent = new Intent(this, SearchFriend.class);
            activityResultLauncher.launch(intent);

        });

        binding.submitContributors.setOnClickListener(view -> {

            for (int i = 0; i < fullcontriarraylist.size(); i++) {
                String identity = fullcontriarraylist.get(i).getContact_identity();
                String referred_id = fullcontriarraylist.get(i).getUser_id();
                if (identity.equals("gemift_contacts")) {
                    addGemiftContributor(friendCircleId, referred_id);
                } else if (identity.equals("new_contact")) {
                    addNewContactContributor(friendCircleId, fullcontriarraylist.get(i));
                } else {
                    addPhoneContactContributor(friendCircleId, fullcontriarraylist.get(i));
                }
            }

        });
    }

    private void LoadRecycle() {

        binding.linearAllcontri.setVisibility(View.VISIBLE);

        fullcontriarraylist.addAll(contriarraylist);
        if (fullcontriarraylist.size() > 0) {
            binding.submitContributors.setVisibility(View.VISIBLE);
        }
        ContributorAdaptor contributorAdaptor = new ContributorAdaptor(fullcontriarraylist, this);
//            binding.recyclerview.setLayoutManager(new GridLayoutManager(this, 4));
        binding.recyclerview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        binding.recyclerview.setAdapter(contributorAdaptor);
    }


    private void addGemiftContributor(String friend_circle_id, String referred_userid) {

        AddFriendModel addFriendModel = new AddFriendModel();
        addFriendModel.setRequest_id(1);
        addFriendModel.setFriend_circle_id(friend_circle_id);
        addFriendModel.setReferrer_user_id(appPreferences.getUserId());
        addFriendModel.setReferred_user_id(referred_userid);

        SweetAlertDialog pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);
        pDialog.show();
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Observable<Response<AddFriendModel>> responseObservable = apiInterface.addFriend(addFriendModel);
        compositeDisposable.add(responseObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(result -> result)
                .subscribe(response -> {
                    pDialog.dismiss();
                    if (response.isSuccessful() && (response.code() == 200 || response.code() == 201)) {
                        finish();

                        if (response.body().getStatus() != null) {
                            try {
                                String asdf = response.body().toString();
//                                startActivity(new Intent(this, DashBoard.class));
                            } catch (Exception e) {
                                Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                            AppUtils.showMessage(this, response.body().getStatus());
                        }

                    } else if (response.code() == 400) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.errorBody().string());
                            String error = jsonObject.getString("Error");
                            Toast.makeText(this, error, Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    } else {
                        pDialog.dismiss();
                    }

                }, throwable -> AppUtils.showMessage(this, throwable.getMessage())));

    }

    private void addNewContactContributor(String friend_circle_id, ContributorsModel fullcontriarraylist) {

        AddFriendModel addFriendModel = new AddFriendModel();
        addFriendModel.setRequest_id(2);
        addFriendModel.setFriend_circle_id(friend_circle_id);
        addFriendModel.setReferrer_user_id(appPreferences.getUserId());
        addFriendModel.setEmail_address(fullcontriarraylist.getEmail_address());
        addFriendModel.setPhone_number(fullcontriarraylist.getPhone_number());
        addFriendModel.setFirst_name(fullcontriarraylist.getFirst_name());
        addFriendModel.setLast_name(fullcontriarraylist.getLast_name());
        addFriendModel.setGender(fullcontriarraylist.getGender());
        addFriendModel.setLocation(fullcontriarraylist.getLocation());
        addFriendModel.setCountry_code(fullcontriarraylist.getCountry_code());


        SweetAlertDialog pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);
        pDialog.show();
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Observable<Response<AddFriendModel>> responseObservable = apiInterface.addFriend(addFriendModel);
        compositeDisposable.add(responseObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(result -> result)
                .subscribe(response -> {
                    pDialog.dismiss();
                    if (response.isSuccessful() && (response.code() == 200 || response.code() == 201)) {
                        finish();

                        if (response.body().getStatus() != null) {
                            try {
                                String asdf = response.body().toString();

                            } catch (Exception e) {
                                Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                            AppUtils.showMessage(this, response.body().getStatus());
                        }

                    } else if (response.code() == 400) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.errorBody().string());
                            String error = jsonObject.getString("Error");
                            Toast.makeText(this, error, Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    } else {
                        pDialog.dismiss();
                    }

                }, throwable -> AppUtils.showMessage(this, throwable.getMessage())));

    }

    private void addPhoneContactContributor(String friend_circle_id, ContributorsModel fullcontriarraylist) {

        AddFriendModel addFriendModel = new AddFriendModel();
        addFriendModel.setRequest_id(2);
        addFriendModel.setFriend_circle_id(friend_circle_id);
        addFriendModel.setReferrer_user_id(appPreferences.getUserId());
        addFriendModel.setEmail_address(" ");
        addFriendModel.setPhone_number(fullcontriarraylist.getPhone_number());
        addFriendModel.setFirst_name(fullcontriarraylist.getFirst_name());
        addFriendModel.setLast_name(fullcontriarraylist.getLast_name());
        addFriendModel.setCountry_code(fullcontriarraylist.getCountry_code());
        addFriendModel.setGender("M");
        addFriendModel.setLocation("India");
        addFriendModel.setCountry_code(fullcontriarraylist.getCountry_code());


        SweetAlertDialog pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);
        pDialog.show();
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Observable<Response<AddFriendModel>> responseObservable = apiInterface.addFriend(addFriendModel);
        compositeDisposable.add(responseObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(result -> result)
                .subscribe(response -> {
                    pDialog.dismiss();
                    if (response.isSuccessful() && (response.code() == 200 || response.code() == 201)) {
                        finish();

                        if (response.body().getStatus() != null) {
                            try {
                                String asdf = response.body().toString();

                            } catch (Exception e) {
                                Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                            AppUtils.showMessage(this, response.body().getStatus());
                        }

                    } else if (response.code() == 400) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.errorBody().string());
                            String error = jsonObject.getString("Error");
                            Toast.makeText(this, error, Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    } else {
                        pDialog.dismiss();
                    }

                }, throwable -> AppUtils.showMessage(this, throwable.getMessage())));

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
