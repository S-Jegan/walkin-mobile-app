package com.gotoapps.walkin.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by C2CSLS on 10/14/2018.
 */

public class SharedPreferenceManager {

    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    public static final String PREF_NAME = "WalkInPref";

    public static final String VIEWED_JOBS = "VIEWED_JOBS";
    public static final String NOTIFIED_JOBS = "NOTIFIED_JOBS";
    public static final String FAVOURITE_JOBS = "FAVOURITE_JOBS";
    public static final String PREF_JOB_CATEGORY = "PREF_JOB_CATEGORY";
    public static final String PREF_JOB_QUALIFICATION = "PREF_JOB_QUALIFICATION";
    public static final String PREF_JOB_LOCATION = "PREF_JOB_LOCATION";
    public static final String FIRST_TIME_USER = "FIRST_TIME_USER";
    public static final String NOTIFICATION_DISABLED = "NOTIFICATION_DISABLED";
    public static final String VIBRATION_DISABLED = "VIBRATION_DISABLED";



    // Constructor
    public SharedPreferenceManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void saveSelectedStringsSets(String key, Set<String> stringSet){
        editor.putStringSet(key,stringSet);
        editor.apply();     // This line is IMPORTANT !!!
    }
    public Set<String> getSelectedStringsSets(String key){
        return pref.getStringSet(key,null);
    }

    public void saveStrings(String key, String value){
        editor.putString(key,value);
        editor.apply();     // This line is IMPORTANT !!!
    }

    public void saveBoolean(String key, boolean value){
        editor.putBoolean(key,value);
        editor.apply();     // This line is IMPORTANT !!!
    }

    public boolean getBoolean(String key){
        return pref.getBoolean(key,true);     // This line is IMPORTANT !!!
    }

    public boolean getFCMRegistration(String key){
        return pref.getBoolean(key,false);     // This line is IMPORTANT !!!
    }

    public void deleteSelectedStringsSets(String key){
        String jobs=pref.getString(key,null);
        if(jobs != null) {
            editor.remove(key);
            editor.apply();  // This line is IMPORTANT !!!
        }
    }

    public void deleteStringsSet(String key){
        Set<String> jobs=pref.getStringSet(key,null);
        if(jobs != null) {
            editor.remove(key);
            editor.apply();  // This line is IMPORTANT !!!
        }
    }

    public void saveStringSet(String key , Integer id) {
        Set<String> favouriteJobs=getSavedJobs(key);
        if(favouriteJobs!=null) {
            favouriteJobs.add(id.toString());
        }else{
            favouriteJobs=new HashSet<>();
            favouriteJobs.add(id.toString());
        }
        editor.putStringSet(key,favouriteJobs);
        editor.apply();     // This line is IMPORTANT !!!
    }

    public Set<String> getSavedJobs(String key) {
        return pref.getStringSet(key,null);
    }

    public void deleteFavouriteJob(String key, Integer id) {
        Set<String> favouriteJobs=getSavedJobs(key);
        if (id!=null && favouriteJobs !=null) {
            favouriteJobs.remove(id.toString());
            editor.putStringSet(key, favouriteJobs);
            editor.apply();
        }
    }

}
