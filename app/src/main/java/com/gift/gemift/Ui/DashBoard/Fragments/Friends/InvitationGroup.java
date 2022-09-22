package com.gift.gemift.Ui.DashBoard.Fragments.Friends;

import static com.gift.gemift.Utils.Constant.FRIEND_CIRCLE_ID;
import static com.gift.gemift.Utils.Constant.FRIEND_CIRCLE_NAME;
import static com.gift.gemift.Utils.Constant.MOBILE_NUMBER;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.gift.gemift.Model.AddFriendModel;
import com.gift.gemift.Network.ApiClient;
import com.gift.gemift.Network.ApiInterface;
import com.gift.gemift.Storage.AppPreference.AppPreferences;
import com.gift.gemift.Ui.DashBoard.DashBoard;
import com.gift.gemift.Utils.AppUtils;
import com.gift.gemift.databinding.InvitationGroupBinding;

import org.json.JSONObject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Response;

public class InvitationGroup extends AppCompatActivity {

    private InvitationGroupBinding binding;
    String circle_id, circle_name, phone_number;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = InvitationGroupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        AppPreferences appPreferences = new AppPreferences(this);

        Intent dataIntent = getIntent();
        circle_id = dataIntent.getStringExtra(FRIEND_CIRCLE_ID);
        circle_name = dataIntent.getStringExtra(FRIEND_CIRCLE_NAME);
        phone_number = dataIntent.getStringExtra(MOBILE_NUMBER);

        binding.txtCirclename.setText(circle_name);

        if(phone_number.isEmpty()){
            binding.txtSkip.setVisibility(View.GONE);
        }
        binding.txtSkip.setOnClickListener(view -> startActivity(new Intent(InvitationGroup.this, DashBoard.class)));

        binding.btnJoin.setOnClickListener(view -> {
            InsertJoinGroup(circle_id, appPreferences.getPhoneNumber());
        });

        binding.btnDecline.setOnClickListener(view -> {
            InsertDeclineGroup(circle_id, appPreferences.getPhoneNumber());
        });

    }

    private void InsertJoinGroup(String circle_id, String phone_number) {
        ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please Wait...");
        pDialog.show();
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        AddFriendModel joinGroup = new AddFriendModel();
        joinGroup.setRequest_id(7);
        joinGroup.setFriend_circle_id(circle_id);
        joinGroup.setPhone_number(phone_number);
        joinGroup.setSignal("1");


        Observable<Response<ResponseBody>> addCategoryUser = apiInterface.insert_joingroup(joinGroup);
        compositeDisposable.add(addCategoryUser
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(result -> result)
                .subscribe(response -> {
                    pDialog.dismiss();
                    if (response.isSuccessful() && (response.code() == 200 || response.code() == 201)) {
                        AppUtils.showMessage(this, "Joined group");
                        startActivity(new Intent(this, DashBoard.class));
                    } else if (response.code() == 400) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.errorBody().string());
                            String error = jsonObject.getString("Error");
                            Toast.makeText(InvitationGroup.this, error, Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            Toast.makeText(InvitationGroup.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }

                }, throwable -> {
                    pDialog.dismiss();
                    AppUtils.showMessage(InvitationGroup.this, throwable.getMessage());
                }));
    }

    private void InsertDeclineGroup(String circle_id, String phone_number) {
        ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please Wait...");
        pDialog.show();
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        AddFriendModel joinGroup = new AddFriendModel();
        joinGroup.setRequest_id(7);
        joinGroup.setFriend_circle_id(circle_id);
        joinGroup.setPhone_number(phone_number);
        joinGroup.setSignal("-1");


        Observable<Response<ResponseBody>> addCategoryUser = apiInterface.insert_joingroup(joinGroup);
        compositeDisposable.add(addCategoryUser
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(result -> result)
                .subscribe(response -> {
                    pDialog.dismiss();
                    if (response.isSuccessful() && (response.code() == 200 || response.code() == 201)) {
                        AppUtils.showMessage(this, "Declined group");
                        startActivity(new Intent(this, DashBoard.class));
                    } else if (response.code() == 400) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.errorBody().string());
                            String error = jsonObject.getString("Error");
                            Toast.makeText(InvitationGroup.this, error, Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            Toast.makeText(InvitationGroup.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }

                }, throwable -> {
                    pDialog.dismiss();
                    AppUtils.showMessage(InvitationGroup.this, throwable.getMessage());
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


