package com.gift.gemift.Utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.widget.EditText;

public class OTPReceiver extends BroadcastReceiver {

    private static EditText editText;

    public void setEditText_OTP(EditText editText) {
        OTPReceiver.editText = editText;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        SmsMessage[] smsMessages = Telephony.Sms.Intents.getMessagesFromIntent(intent);
        for (SmsMessage message : smsMessages) {
            String msgBody = message.getMessageBody();
            String otp = msgBody.split(":")[1];
            editText.setText(otp);
        }
    }
}
