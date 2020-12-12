package com.gotoapps.walkin.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.gotoapps.walkin.MainActivity;
import com.gotoapps.walkin.R;
import com.gotoapps.walkin.db.DBKeys;
import com.gotoapps.walkin.model.Industry;
import com.gotoapps.walkin.model.InterviewJSON;
import com.gotoapps.walkin.utils.AppMaintenanceUtil;
import com.gotoapps.walkin.utils.NetworkConnectionStatusNotificationUtil;
import com.gotoapps.walkin.utils.SharedPreferenceManager;
import com.snappydb.DB;
import com.snappydb.DBFactory;
import com.snappydb.SnappydbException;

import java.util.ArrayList;
import java.util.List;

public class SplashScreenActivity extends AppCompatActivity {
    ProgressBar progress;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Thread logoTimer = new Thread() {
            public void run() {
                try{
                    Thread.sleep(300);
                } catch (Exception e) {
                    Log.d("Exception", "Exception" + e);
                } finally {
                   if( NetworkConnectionStatusNotificationUtil.checkConnectionStatus()
                            && AppMaintenanceUtil.checkAppSataus() )
                   {
                        intent = new Intent(SplashScreenActivity.this, AppUnderMaintenanceActivity.class);
                        startActivity(intent);
                    } else if(NetworkConnectionStatusNotificationUtil.checkConnectionStatus()) {
                       boolean ftu=true;
                       try {
                           DB snappydb = DBFactory.open(getApplicationContext());
                           ftu=snappydb.getBoolean(DBKeys.FIRST_TIME_USER);
                           snappydb.close();
                       }catch(SnappydbException ex){
                           ex.printStackTrace();
                        }
                       if(ftu) {
                           intent = new Intent(SplashScreenActivity.this, UserPereferenceActivity.class);
                           startActivity(intent);
                       }else{
                           intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                           startActivity(intent);
                       }
                    }
                    else{
                        startActivity(new Intent(SplashScreenActivity.this, InternetConnectionNotFoundActivity.class));
                    }
                }
                finish();
            }
        };
        logoTimer.start();
    }

}
