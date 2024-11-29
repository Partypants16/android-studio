package edu.monash.optionsmenu;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Telephony;
import android.telephony.SmsMessage;

public class SMSReceiver extends BroadcastReceiver {

    // used as a channel to broadcast the message
    //Any application aware of this channel can listen to the broadcasts
    public static final String SMS_FILTER = "SMS_FILTER_WEEK5";

    //Within the broadcast, we would like to send information
    // and this will be the key to identifying that information, in this case, the SMS message
    public static final String SMS_MSG_KEY = "SMS_MSG_KEY";


    @Override
    public void onReceive(Context context, Intent intent) {
        SmsMessage[] messages = Telephony.Sms.Intents.getMessagesFromIntent(intent);

        for (int i = 0; i < messages.length; i++) {
            SmsMessage currentMessage = messages[i];
            String phoneNumber = currentMessage.getDisplayOriginatingAddress();// phone number of sender
            String message = currentMessage.getDisplayMessageBody();

            //Toast.makeText(context, "Number: " + phoneNumber + ", Message:" + message, Toast.LENGTH_LONG).show();

            Intent intent1 = new Intent();
            intent1.setAction(SMS_FILTER); // channel

            // set data for broadcast message
            intent1.putExtra(SMS_MSG_KEY, message);
            context.sendBroadcast(intent1); // startActivity
        }
    }
}