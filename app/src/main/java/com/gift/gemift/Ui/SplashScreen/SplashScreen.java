package com.gift.gemift.Ui.SplashScreen;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.gift.gemift.R;
import com.gift.gemift.Storage.AppPreference.AppPreferences;
import com.gift.gemift.Ui.DashBoard.DashBoard;
import com.gift.gemift.Ui.LoginActivity.NewUserWelcome;
import com.gift.gemift.databinding.SplashScreenBinding;

public class SplashScreen extends AppCompatActivity {
    private SplashScreenBinding binding;
    private Animation anim;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = SplashScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    @Override
    protected void onStart() {
        super.onStart();
        anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in); // Create the animation.
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                AppPreferences appPreferences = new AppPreferences(SplashScreen.this);
                if (appPreferences.getUserId() != null && !appPreferences.getUserId().isEmpty()) {

                    if(appPreferences.getFriendCircleSize()!=0){
                        startActivity(new Intent(SplashScreen.this, DashBoard.class));
                    }else {
                        startActivity(new Intent(SplashScreen.this, NewUserWelcome.class));

                    }
                } else {
                    startActivity(new Intent(SplashScreen.this, WelcomeActivity.class));
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        binding.imgLogo.startAnimation(anim);
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
