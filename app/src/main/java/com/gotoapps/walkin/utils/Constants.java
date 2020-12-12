package com.gotoapps.walkin.utils;

/**
 * Created by C2CSLS on 6/27/2018.
 */

public class Constants {
    public static String APP_PNAME = "com.gotoapps.walkin";// Package Name

    public static String VIEW_JOB= "WalkIn - Your Job Is In Your App";
    public static String SENT_FROM= "Sent From: WalkIn App";
    public static String DOWNLOAD="Download App : https://tinyurl.com/yx2d27z3";
    public static String VIEW_MORE="View more about this Job : http://walkinappindia.in/walkin/api/v1/interview/details/";

    // global topic to receive app wide push notifications
    public static final String TOPIC_GLOBAL = "global";

    // broadcast receiver intent filters
    public static final String REGISTRATION_COMPLETE = "registrationComplete";
    public static final String PUSH_NOTIFICATION = "pushNotification";
    public static final String FCM_SUBSCRIPTION_COMPLETE = "FCM_subscriptionComplete";

    // id to handle the notification in the notification tray
    public static final int NOTIFICATION_ID = 100;
    public static final String CHANNEL_ID="fcm_default_channel";
    /**
     * REST URL HINTS
     */
    public static String VISA_SPONSORED_JOBS="VISA_SPONSORED_JOBS";
    public static String NEWLY_ADDED_JOBS="NEWLY_ADDED_JOBS";
    public static String RECOMMENDED_JOBS="RECOMMENDED_JOBS";
    public static String TODAY_JOBS="TODAY_JOBS";
    public static String TOMORROW_JOBS="TOMORROW_JOBS";
    public static String VERIFIED_JOBS="VERIFIED_JOBS";
    public static String FRESHER_JOBS="FRESHER_JOBS";
    public static String INDUSTRY_SPECIFIC_JOBS="INDUSTRY_SPECIFIC_JOBS";
    public static String LOCATION_SPECIFIC_JOBS="LOCATION_SPECIFIC_JOBS";
    public static String FAVOURITE_JOBS="FAVOURITE_JOBS";
    public static String NOTIFIED_JOBS="NOTIFIED_JOBS";
    public static String VIEWED_JOBS="VIEWED_JOBS";
    public static String CATEGORY_SPECIFIC_JOBS="CATEGORY_SPECIFIC_JOBS";
    public static String JOB_SEARCH="JOB_SEARCH";
    public static String ALL_JOBS="ALL_JOBS";

    /**
     * OTHER CONSTANTS
     */
    public static final String INTERVIEW_ID = "INTERVIEW_ID";

    public static final String TERMS_OF_SERVICE_URL="http://walkinappindia.com/terms-conditions.html";
    public static final String PRIVACY_POLICY_URL="http://walkinappindia.com/privacy-policy.html";
    public static final String FAQ_URL="http://walkinappindia.com/faq.html";

    public static  final String DISCLAIMER_NOTE="All the job opportunities getting advertised through WalkIn are validated by WalkIn App team. " +
            "However, it is the responsibility of the applicants to verify the standard and authenticity of the company and the role for which they are applying. " +
            "WalkIn App will not hold any responsibility in case of any damage or loss caused because of the application being advertised by them.";

}
