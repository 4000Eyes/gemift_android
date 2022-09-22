package com.gift.gemift.Utils;


import static com.gift.gemift.Utils.Constant.NOT_CONNECT;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.gift.gemift.R;


public class InternetCheck extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View layout = LayoutInflater.from(context).inflate(R.layout.no_internet, null);
        builder.setView(layout);
        AlertDialog alertDialog = builder.create();
        if (NetworkUtil.getConnectivityStatusString(context).equals(NOT_CONNECT)) {
            alertDialog.show();
            alertDialog.setCancelable(false);
            Button btn_retry = layout.findViewById(R.id.btn_retry);
            alertDialog.getWindow().setGravity(Gravity.CENTER);
            btn_retry.setOnClickListener(view -> {
                alertDialog.dismiss();
                onReceive(context, intent);
            });
        } else {
            if (alertDialog.isShowing()) {
                alertDialog.dismiss();
            }
        }
    }
}
