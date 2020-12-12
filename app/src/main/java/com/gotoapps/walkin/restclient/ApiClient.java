package com.gotoapps.walkin.restclient;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by C2CSLS on 9/17/2018.
 */

public class ApiClient {
    public static final String BASE_URL_LOCAL = "http://192.168.43.113/walkin/api/v1/";
    public static final String BASE_URL_SUPPORT = "http://gotoapps.in/admin/walkin/api/v1/";
    /*public static final String BASE_URL_LIVE = "http://walkinappindia.com/admin/walkin/api/v1/";*/
    public static final String BASE_URL_LIVE = "http://walkinappindia.in/walkin/api/v1/";
    private static Retrofit retrofit = null;
    public static Retrofit getClient() {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL_LIVE)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
