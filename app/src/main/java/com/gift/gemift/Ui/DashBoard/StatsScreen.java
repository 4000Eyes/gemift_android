package com.gift.gemift.Ui.DashBoard;

import static com.gift.gemift.Utils.Constant.MOBILE_NUMBER;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.gift.gemift.Ui.LoginActivity.SignUpDetails;
import com.gift.gemift.databinding.StatsScreenBinding;

public class StatsScreen extends AppCompatActivity {

    private StatsScreenBinding binding;
    private String MobileNumber;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = StatsScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        LoadUi();
    }

    private void LoadUi() {
        Intent dataIntent = getIntent();
        MobileNumber = dataIntent.getStringExtra(MOBILE_NUMBER);
        binding.btnSignup.setOnClickListener(v -> {
            Intent intent = new Intent(StatsScreen.this, SignUpDetails.class);
            intent.putExtra(MOBILE_NUMBER, MobileNumber);
            startActivity(intent);
        });
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
