package com.gotoapps.walkin.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.support.design.widget.Snackbar;
import android.widget.Toast;

import com.gotoapps.walkin.AppController;
import com.gotoapps.walkin.db.DBKeys;
import com.gotoapps.walkin.model.Industry;
import com.gotoapps.walkin.model.Properties;
import com.gotoapps.walkin.restclient.ApiClient;
import com.gotoapps.walkin.restclient.ApiInterface;
import com.snappydb.DB;
import com.snappydb.DBFactory;
import com.snappydb.SnappydbException;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by C2CSLS on 7/14/2018.
 */

public class AppMaintenanceUtil {

    public static boolean checkAppSataus(){
        boolean maintanaceMode=false;
        Integer applicationVersionCode=0;
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<Properties>> propertiesCallBack = apiService.getAppProperties();
        try
        {
            List<Properties> appProperties = propertiesCallBack.execute().body();
            for(Properties property:appProperties){
                if((property.getkey().equalsIgnoreCase("maintenance_mode"))
                        && property.getvalue().equalsIgnoreCase("1")){
                    maintanaceMode=true;
                }
                if(property.getkey().equalsIgnoreCase("app_version_code")){
                    applicationVersionCode=Integer.parseInt(property.getvalue().trim());
                }
                try {
                    DB snappydb = DBFactory.open(AppController.getAppContext());
                    snappydb.putInt(DBKeys.APP_VERSION_CODE,applicationVersionCode);
                    snappydb.close();
                }catch (SnappydbException ex){
                    Toast.makeText(AppController.getAppContext(),"Something went wrong",Toast.LENGTH_SHORT).show();
                }
            }
        }
        catch (Exception ex)
        {
            maintanaceMode=false;
        }
        return maintanaceMode;
    }

}
