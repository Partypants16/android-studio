package com.fit2081.incomingcallsreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String[] requiredPermissions = new String[]{
                android.Manifest.permission.CALL_PHONE,
                android.Manifest.permission.READ_CALL_LOG,
                android.Manifest.permission.READ_PHONE_STATE
        };
        ActivityCompat.requestPermissions(this, requiredPermissions, 0);
    }
    public class MyCallsReceiver extends BroadcastReceiver {
        Context self;
        static TelephonyManager telephonyManager;
        public void onReceive(Context context, Intent intent) {
            self = context;
            if (telephonyManager == null) {
                telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            }
            MyPhoneStateListener PhoneListener = new MyPhoneStateListener();
            telephonyManager.listen(PhoneListener, PhoneStateListener.LISTEN_CALL_STATE);
        }
        private class MyPhoneStateListener extends PhoneStateListener {
            @Override
            public void onCallStateChanged(int state, String incomingNumber) {
                if (state == 1) {
                    showToast("Number:=" + incomingNumber);
                }
            }
        }
        private void showToast(String message){
            Toast.makeText(self, message, Toast.LENGTH_LONG).show();
        }
    }

}