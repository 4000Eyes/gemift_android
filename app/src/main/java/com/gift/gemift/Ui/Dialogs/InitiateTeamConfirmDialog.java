package com.gift.gemift.Ui.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;

import androidx.annotation.NonNull;

import com.gift.gemift.Model.Wallet.TransactionDetailResponse;
import com.gift.gemift.Ui.Adaptor.TransactionAdapter;
import com.gift.gemift.databinding.InitiateTeamDialogBinding;

import retrofit2.Response;

public class InitiateTeamConfirmDialog extends Dialog {
    Response<TransactionDetailResponse> response;
    Context context;
    InitiateTeamDialogBinding binding;
    public InitiateTeamConfirmDialog(@NonNull Context context, Response<TransactionDetailResponse> response) {
        super(context);
        this.context = context;
        this.response = response;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = InitiateTeamDialogBinding.inflate(getLayoutInflater());
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(binding.getRoot());

        binding.rvList.setAdapter(new TransactionAdapter(response.body().getTransaction().get(1).getDetail(),context));

        binding.confirmButton.setOnClickListener(view -> {});
        binding.cancel.setOnClickListener(view -> {
            dismiss();
        });

    }
}
