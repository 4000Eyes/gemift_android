package com.gift.gemift.Ui.DashBoard.Fragments.Friends;

import static com.gift.gemift.Utils.Constant.MOBILE_NUMBER;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import com.gift.gemift.Model.AddFriendModel;
import com.gift.gemift.Model.FriendDataSearchModel;
import com.gift.gemift.Network.ApiClient;
import com.gift.gemift.Network.ApiInterface;
import com.gift.gemift.R;
import com.gift.gemift.Storage.AppPreference.AppPreferences;
import com.gift.gemift.Ui.DashBoard.DashBoard;
import com.gift.gemift.Utils.AppUtils;
import com.gift.gemift.Utils.FilterHelper;
import com.gift.gemift.databinding.AddfriendCircleBinding;
import com.google.gson.Gson;

import org.json.JSONObject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Response;

public class AddFriendCircle extends AppCompatActivity {

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private AddfriendCircleBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = AddfriendCircleBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appbar.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        final Drawable upArrow = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_baseline_arrow, null);
        FilterHelper.setColorFilter(upArrow, getResources().getColor(R.color.white), FilterHelper.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);



        LoadUi();
    }

    @SuppressLint("SetTextI18n")
    private void LoadUi() {
        Intent dataIntent = getIntent();
        AppPreferences appPreferences = new AppPreferences(this);
        String friendDetail = dataIntent.getStringExtra("friendDetail");
        binding.appbar.txtTitle.setText("Add Friend");
        FriendDataSearchModel friendDataSearchModel = new Gson().fromJson(friendDetail,FriendDataSearchModel.class);
        binding.txtFriendName.setText(friendDataSearchModel.getFirst_name()+" "+friendDataSearchModel.getLast_name());
        binding.txtPhoneNumber.setText(friendDataSearchModel.getPhone_number());
//        binding.txtEmail.setText(friendDataSearchModel.get());
        binding.btnProceed.setOnClickListener(v -> {
            if (binding.edtCircleName.getText().toString().isEmpty()) {
                AppUtils.showMessage(this, "Enter the Circle Name");
            } else {
                addCircle(friendDataSearchModel.getUser_id(),appPreferences.getUserId());
            }
        });
    }

    private void addCircle(String friend_id, String userId) {
        ProgressDialog pDialog = new ProgressDialog(AddFriendCircle.this);
        pDialog.setMessage("Please Wait...");
        pDialog.show();
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        AddFriendModel addFriendModel = new AddFriendModel();
        addFriendModel.setRequest_id(3);
        addFriendModel.setReferrer_user_id(friend_id);
        addFriendModel.setReferred_user_id(userId);
        Observable<Response<ResponseBody>> responseObservable = apiInterface.addFriendCircle(addFriendModel);
        compositeDisposable.add(responseObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(result -> result)
                .subscribe(response -> {
                    pDialog.dismiss();
                    if (response.isSuccessful() && (response.code() == 200 || response.code() == 201)) {
                        startActivity(new Intent(this, DashBoard.class));
                    } else {
                        try {
                            JSONObject jsonObject = new JSONObject(response.errorBody().string());
                            String error = jsonObject.getString("Error");
                            Toast.makeText(this, error, Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
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
