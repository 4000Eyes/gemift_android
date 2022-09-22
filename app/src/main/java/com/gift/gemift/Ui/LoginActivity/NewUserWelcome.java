package com.gift.gemift.Ui.LoginActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.gift.gemift.Storage.AppPreference.AppPreferences;
import com.gift.gemift.Storage.DB.ViewModel.UserDetailViewModel;
import com.gift.gemift.Ui.DashBoard.DashBoard;
import com.gift.gemift.Ui.DashBoard.Fragments.Friends.CreateFriendCircle;
import com.gift.gemift.Ui.DashBoard.Fragments.Home.AddInterestOwn;
import com.gift.gemift.Ui.DashBoard.Fragments.Home.FriendCircleList;
import com.gift.gemift.databinding.NewUserStaticViewBinding;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class NewUserWelcome extends AppCompatActivity {

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private NewUserStaticViewBinding binding;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = NewUserStaticViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        AppPreferences appPreferences = new AppPreferences(this);
        UserDetailViewModel userDetailViewModel = new ViewModelProvider(this).get(UserDetailViewModel.class);

        compositeDisposable.add(userDetailViewModel.
                getUserDetail()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(userDetailEntities -> {
                    if (userDetailEntities != null) {
                        if (userDetailEntities.length > 0) {
                            binding.txtCustomerName.setText("Hello " + userDetailEntities[0].getFirst_name() + " " + userDetailEntities[0].getLast_name());
                        }
                    }
                }));
        binding.appbar.txtTitle.setText("Welcome");

        binding.btnProceed.setOnClickListener(v ->
        startActivity(new Intent(this, AddInterestOwn.class)));
        binding.btnProceed1.setOnClickListener(v -> startActivity(new Intent(this, CreateFriendCircle.class).putExtra("firstCome", true)));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
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
}
