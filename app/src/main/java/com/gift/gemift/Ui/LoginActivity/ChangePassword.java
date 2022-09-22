package com.gift.gemift.Ui.LoginActivity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.gift.gemift.databinding.ChangePasswordBinding;

public class ChangePassword extends AppCompatActivity {

    private ChangePasswordBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ChangePasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }
}
