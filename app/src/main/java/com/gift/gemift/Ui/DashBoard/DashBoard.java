package com.gift.gemift.Ui.DashBoard;

import static com.gift.gemift.Utils.Constant.CIRCLE_ID;
import static com.gift.gemift.Utils.Constant.FRIEND_CIRCLE_IMAGE;
import static com.gift.gemift.Utils.Constant.FRIEND_CIRCLE_NAME;
import static com.gift.gemift.Utils.Constant.FRIEND_CIRCLE_TITLE;
import static com.gift.gemift.Utils.Constant.SECRET_FIRST_NAME;
import static com.gift.gemift.Utils.Constant.SECRET_LAST_NAME;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.gift.gemift.Model.ImageUploadResponse;
import com.gift.gemift.Model.NotificationCount;
import com.gift.gemift.Model.StatusItem;
import com.gift.gemift.Model.UpdateImagePathResponse;
import com.gift.gemift.Model.Wallet.WalletViewNotificationInput;
import com.gift.gemift.Network.ApiClient;
import com.gift.gemift.Network.ApiInterface;
import com.gift.gemift.R;
import com.gift.gemift.Storage.AppPreference.AppPreferences;
import com.gift.gemift.Storage.DB.ViewModel.UserDetailViewModel;
import com.gift.gemift.Ui.Adaptor.NotificationAdaptor;
import com.gift.gemift.Ui.DashBoard.Fragments.Friends.FriendsFragment;
import com.gift.gemift.Ui.DashBoard.Fragments.Home.AddInterest;
import com.gift.gemift.Ui.DashBoard.Fragments.Home.AddOccasionList;
import com.gift.gemift.Ui.DashBoard.Fragments.Home.HomeFragment;
import com.gift.gemift.Ui.DashBoard.Fragments.Home.ViewMyInterests;
import com.gift.gemift.Ui.DashBoard.Fragments.Notifications.NotificationFragment;
import com.gift.gemift.Ui.DashBoard.Fragments.Profile.ProfileFragment;
import com.gift.gemift.Ui.DashBoard.Fragments.Wallet.MoneyFragment;
import com.gift.gemift.Utils.AppUtils;
import com.gift.gemift.databinding.DashboardBinding;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.navigation.NavigationView;

import java.io.File;
import java.util.TimeZone;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Response;

public class DashBoard extends AppCompatActivity implements NotificationAdaptor.onClickPerformed {

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    public ActionBarDrawerToggle actionBarDrawerToggle;
    private DashboardBinding binding;
    private ProgressDialog pDialog;
    private AppPreferences appPreferences;
    private UserDetailViewModel userDetailViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        appPreferences = new AppPreferences(this);
        binding.contentMain.navigation.setItemIconTintList(null);
        userDetailViewModel = new ViewModelProvider(this).get(UserDetailViewModel.class);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, binding.drawerLayout, R.string.nav_open, R.string.nav_close);
        binding.drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Home");


        getSupportFragmentManager().beginTransaction().replace(R.id.FrameLayout, new HomeFragment()).commit();
        binding.contentMain.navigation.setOnItemSelectedListener(item -> {
            Fragment fragment = null;
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    fragment = new HomeFragment();
                    getNotification();

                    break;
                case R.id.navigation_friends:
                    fragment = new FriendsFragment();
                    getNotification();

                    break;
                case R.id.navigation_notification:
                    fragment = new NotificationFragment(this);
                    getNotification();

                    break;
                case R.id.navigation_profile:
                    fragment = new ProfileFragment();
                    getNotification();

                    break;
                case R.id.navigation_money:
                    fragment = new MoneyFragment();
                    getNotification();

                    break;
                default:
                    fragment = new HomeFragment();
                    getNotification();

                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.FrameLayout, fragment).commit();
            return true;
        });


        binding.navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_profile:
                        binding.drawerLayout.closeDrawer(GravityCompat.START);

                        break;

                    case R.id.nav_my_interest:

                        binding.drawerLayout.closeDrawer(GravityCompat.START);
                        startActivity(new Intent(DashBoard.this, ViewMyInterests.class));
                        break;


                }
                return false;
            }
        });

        binding.contentMain.navigation.getOrCreateBadge(R.id.navigation_notification).clearNumber();


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            File file = ImagePicker.Companion.getFile(data);
            Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
            String base64 = AppUtils.encodeToBase64(bitmap, Bitmap.CompressFormat.JPEG, 60);
            imageUpload(base64);
        }
    }

    private void imageUpload(String base64) {
        pDialog = new ProgressDialog(DashBoard.this);
        pDialog.setMessage("Please Wait...");
        pDialog.show();
        ApiInterface apiInterface = ApiClient.getApiClientImageUpload().create(ApiInterface.class);
        ImageUploadResponse imageUploadResponse = new ImageUploadResponse(appPreferences.getUserId() + "_" + AppUtils.getCurrentDate(), "data:image/jpg;base64," + base64);
        Observable<Response<ImageUploadResponse>> otpResponse = apiInterface.uploadImage(imageUploadResponse);
        compositeDisposable.add(otpResponse
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(result -> result)
                .subscribe(response -> {
                    if (response.isSuccessful() && (response.code() == 200 || response.code() == 201)) {
                        updateImagePath(response.body().getImagePath());
                        appPreferences.setProfileImage(response.body().getImagePath());
                        userDetailViewModel.updateProfileImage(appPreferences.getUserId(), response.body().getImagePath());
                    } else if (response.code() == 400) {
                        pDialog.dismiss();

                    }

                }, throwable -> {
                    AppUtils.showMessage(this, throwable.getMessage());
                    pDialog.dismiss();
                }));
    }

    private void updateImagePath(String imagePath) {
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        UpdateImagePathResponse imageUploadResponse = new UpdateImagePathResponse(3, appPreferences.getUserId(), imagePath, "user");
        Observable<Response<ResponseBody>> otpResponse = apiInterface.updateImagePath(imageUploadResponse);
        compositeDisposable.add(otpResponse
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(result -> result)
                .subscribe(response -> {
                    pDialog.dismiss();
                    if (response.isSuccessful() && (response.code() == 200 || response.code() == 201)) {
                        AppUtils.showMessage(this, "Success");
                    } else if (response.code() == 400) {
                    }

                }, throwable -> {
                    AppUtils.showMessage(this, throwable.getMessage());
                    pDialog.dismiss();
                }));
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent startMain = new Intent(Intent.ACTION_MAIN);
            startMain.addCategory(Intent.CATEGORY_HOME);
            startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(startMain);
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();

        getNotification();
    }

    private void getNotification() {

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Observable<Response<NotificationCount>> otpResponse = apiInterface.getNotificationCount("11", appPreferences.getUserId());
        compositeDisposable.add(otpResponse
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(result -> result)
                .subscribe(response -> {
                    if(response.body().getMessageCount()!=0){
                        binding.contentMain.navigation.getOrCreateBadge(R.id.navigation_notification).clearNumber();

                        binding.contentMain.navigation.getBadge(R.id.navigation_notification).setNumber(response.body().getMessageCount());
                    }else {
                        binding.contentMain.navigation.getOrCreateBadge(R.id.navigation_notification).clearNumber();

                    }
                }, throwable -> {

                }));
    }

    @Override
    public void onClick(StatusItem statusItem) {


        if(statusItem.getTypeId().equals("FRIEND_CIRCLE_NO_OCCASION")){

            Intent intent = new Intent(this, AddOccasionList.class);
            intent.putExtra("isList",true);
            intent.putExtra(CIRCLE_ID, statusItem.getFriendCircleId());
            intent.putExtra(FRIEND_CIRCLE_TITLE, statusItem.getFriend_circle_name());
            intent.putExtra(SECRET_FIRST_NAME, statusItem.getSecretFirstName());
            intent.putExtra(SECRET_LAST_NAME, statusItem.getSecretLastName());
            startActivity(intent);


        }
        else if(statusItem.getTypeId().equals("FRIEND_CIRCLE_WITH_NO_INTERESTS")){


            Intent intent = new Intent(DashBoard.this, AddInterest.class);
            intent.putExtra("isList",true);
            intent.putExtra(CIRCLE_ID, statusItem.getFriendCircleId());
            intent.putExtra(FRIEND_CIRCLE_NAME, statusItem.getFriend_circle_name());
            intent.putExtra(SECRET_FIRST_NAME, statusItem.getSecretFirstName());
            intent.putExtra(SECRET_LAST_NAME, statusItem.getSecretLastName());
            startActivity(intent);

        }
        else if(statusItem.getTypeId().equals("DAYS_SINCE_LAST_OCCASION")){

            Intent intent = new Intent(DashBoard.this, AddOccasionList.class);
            intent.putExtra("isList",true);
            intent.putExtra(CIRCLE_ID, statusItem.getFriendCircleId());
            intent.putExtra(FRIEND_CIRCLE_TITLE, statusItem.getFriend_circle_name());
            intent.putExtra(SECRET_FIRST_NAME, statusItem.getSecretFirstName());
            intent.putExtra(SECRET_LAST_NAME, statusItem.getSecretLastName());
            startActivity(intent);


        }
        else if(statusItem.getTypeId().equals("INTEREST_REMINDERS")){

            Intent intent = new Intent(DashBoard.this, AddInterest.class);
            intent.putExtra("isList",true);
            intent.putExtra(CIRCLE_ID, statusItem.getFriendCircleId());
            intent.putExtra(FRIEND_CIRCLE_NAME, statusItem.getFriend_circle_name());
            intent.putExtra(SECRET_FIRST_NAME, statusItem.getSecretFirstName());
            intent.putExtra(SECRET_LAST_NAME, statusItem.getSecretLastName());
            startActivity(intent);

        }

        callNotificationView(statusItem.getMessageId());
    }

    @Override
    public void onClickWallet(StatusItem statusItem) {


        WalletViewNotificationInput walletViewNotificationInput = new WalletViewNotificationInput();

        walletViewNotificationInput.setApiCallTime(AppUtils.getCurrentDateCopy());
        walletViewNotificationInput.setTimeZone(TimeZone.getDefault().getID());
        walletViewNotificationInput.setRequestType("update_wallet_message");
        walletViewNotificationInput.setTransactionId(statusItem.getTransaction_id());
        walletViewNotificationInput.setUserId(appPreferences.getUserId());
        walletViewNotificationInput.setTypeId(""+statusItem.getTypeId());
        callWalletViewNotification(walletViewNotificationInput);
    }

    private void callNotificationView(String messageId) {
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Observable<Response<ResponseBody>> otpResponse = apiInterface.updateNotification("9", appPreferences.getUserId(), messageId);
        compositeDisposable.add(otpResponse
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(result -> result)
                .subscribe(response -> {
                    getNotification();


                }, throwable -> {

                }));
    }


    private void callWalletViewNotification(WalletViewNotificationInput walletViewNotificationInput) {
        ApiInterface apiInterface = ApiClient.getApiClientShareFund().create(ApiInterface.class);
        Observable<Response<ResponseBody>> otpResponse = apiInterface.updateWalletNotification(walletViewNotificationInput);
        compositeDisposable.add(otpResponse
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(result -> result)
                .subscribe(response -> {
                    getNotification();


                }, throwable -> {

                }));
    }
}
