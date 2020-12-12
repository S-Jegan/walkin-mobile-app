package com.gotoapps.walkin.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.view.MenuItem;
import android.widget.CompoundButton;

import com.google.firebase.messaging.FirebaseMessaging;
import com.gotoapps.walkin.R;
import com.gotoapps.walkin.utils.Constants;
import com.gotoapps.walkin.utils.SharedPreferenceManager;

public class SettingsActivity extends AppCompatActivity {

    SwitchCompat notificationSwitch, vibrationSwitch;
    SharedPreferenceManager preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Settings");
        notificationSwitch =  findViewById(R.id.notificationSwitch);
        vibrationSwitch =  findViewById(R.id.vibrationSwitch);

        preferences = new SharedPreferenceManager(getApplicationContext());
        boolean notificationSwitchVal=preferences.getBoolean(SharedPreferenceManager.NOTIFICATION_DISABLED);
        notificationSwitch.setChecked(notificationSwitchVal);
        notificationSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                preferences.saveBoolean(SharedPreferenceManager.NOTIFICATION_DISABLED,isChecked);
                if(isChecked){
                    subscribeToFCM();
                }else{
                    unSubscribeToFCM();
                }
            }
        });

        boolean vibrationSwitchVal=preferences.getBoolean(SharedPreferenceManager.VIBRATION_DISABLED);
        vibrationSwitch.setChecked(vibrationSwitchVal);
        vibrationSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    preferences.saveBoolean(SharedPreferenceManager.VIBRATION_DISABLED,isChecked);
                }else{
                    preferences.saveBoolean(SharedPreferenceManager.VIBRATION_DISABLED,isChecked);
                }
            }
        });
    }

    private void subscribeToFCM() {
        preferences = new SharedPreferenceManager(getApplicationContext());
        if(!preferences.getFCMRegistration(Constants.FCM_SUBSCRIPTION_COMPLETE)) {
            FirebaseMessaging.getInstance().subscribeToTopic(Constants.TOPIC_GLOBAL);
            preferences.saveBoolean(Constants.FCM_SUBSCRIPTION_COMPLETE, true);
        }
    }

    private void unSubscribeToFCM() {
        preferences = new SharedPreferenceManager(getApplicationContext());
        if(preferences.getFCMRegistration(Constants.FCM_SUBSCRIPTION_COMPLETE)) {
            FirebaseMessaging.getInstance().unsubscribeFromTopic(Constants.TOPIC_GLOBAL);
            preferences.saveBoolean(Constants.FCM_SUBSCRIPTION_COMPLETE, false);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
