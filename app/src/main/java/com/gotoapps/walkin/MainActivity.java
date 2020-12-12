package com.gotoapps.walkin;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.firebase.messaging.FirebaseMessaging;
import com.gotoapps.walkin.activities.AboutWalkInActivity;
import com.gotoapps.walkin.activities.AddNewInterviewActivity;
import com.gotoapps.walkin.activities.InternetConnectionNotFoundActivity;
import com.gotoapps.walkin.activities.InterviewListActivity;
import com.gotoapps.walkin.activities.LocationBasedInterviews;
import com.gotoapps.walkin.activities.ResumeCornerActivity;
import com.gotoapps.walkin.activities.SettingsActivity;
import com.gotoapps.walkin.db.DBKeys;
import com.gotoapps.walkin.fragment.HomeFragment;
import com.gotoapps.walkin.utils.AppMaintenanceUtil;
import com.gotoapps.walkin.utils.CircleTransform;
import com.gotoapps.walkin.utils.Constants;
import com.gotoapps.walkin.utils.NetworkConnectionStatusNotificationUtil;
import com.gotoapps.walkin.utils.SharedPreferenceManager;
import com.snappydb.DB;
import com.snappydb.DBFactory;
import com.snappydb.SnappydbException;

import static com.gotoapps.walkin.utils.Constants.APP_PNAME;

public class MainActivity extends AppCompatActivity {

    private NavigationView navigationView;
    private DrawerLayout drawer;
    private View navHeader;
    private ImageView imgNavHeaderBg, imgProfile;
    private TextView txtName, txtWebsite;
    private Toolbar toolbar;
    public static int navItemIndex = 0;
    Intent intent;

    private static final String TAG_HOME = "home";
    public static String CURRENT_TAG = TAG_HOME;

    private boolean shouldLoadHomeFragOnBackPress = true;
    private Handler mHandler;

    //Added for FCM
    private static final String TAG = MainActivity.class.getSimpleName();
    private BroadcastReceiver mRegistrationBroadcastReceiver;

    // Session Manager Class
    SharedPreferenceManager preferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mHandler = new Handler();
        drawer =  findViewById(R.id.drawer_layout);
        navigationView =  findViewById(R.id.nav_view);

        // Navigation view header
        navHeader = navigationView.getHeaderView(0);
        txtName =  navHeader.findViewById(R.id.name);
        txtWebsite =  navHeader.findViewById(R.id.website);
       // imgNavHeaderBg = (ImageView) navHeader.findViewById(R.id.img_header_bg);
        imgProfile =  navHeader.findViewById(R.id.img_profile);

        loadNavHeader();
        setUpNavigationView();
        if (savedInstanceState == null) {
            navItemIndex = 0;
            CURRENT_TAG = TAG_HOME;
            loadHomeFragment();
        }


        //ADDED FOR FCM
        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(Constants.REGISTRATION_COMPLETE)) {
                    FirebaseMessaging.getInstance().subscribeToTopic(Constants.TOPIC_GLOBAL);
                } else if (intent.getAction().equals(Constants.PUSH_NOTIFICATION)) {
                    String message = intent.getStringExtra("message");
                }
            }
        };
        preferences = new SharedPreferenceManager(getApplicationContext());
        if(preferences.getBoolean(SharedPreferenceManager.FIRST_TIME_USER)) {
            subscribeToFCM();
        }
        checkAppUpdate();
    }

    private void checkAppUpdate() {
        int versionCode = BuildConfig.VERSION_CODE;
        Integer applicationVersionCode=0;
        try {
            DB snappydb = DBFactory.open(getApplicationContext());
            applicationVersionCode=snappydb.getInt(DBKeys.APP_VERSION_CODE);
            snappydb.close();
        }catch (SnappydbException ex){
            applicationVersionCode=0;
        }
        if(applicationVersionCode > versionCode){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("New update available!");
            builder.setMessage("Update the App to latest version for an Awesome Experience.");
            builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    try {
                        Intent appStoreIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + APP_PNAME));
                        appStoreIntent.setPackage("com.android.vending");
                        startActivity(appStoreIntent);
                    } catch (android.content.ActivityNotFoundException exception) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + APP_PNAME)));
                    }
                }
            });
            builder.setNegativeButton("Cancel", null);
            AlertDialog dialog = builder.create();
            dialog.setCancelable(true);
            dialog.show();
        }
    }

    private void subscribeToFCM() {
        preferences = new SharedPreferenceManager(getApplicationContext());
        if(!preferences.getFCMRegistration(Constants.FCM_SUBSCRIPTION_COMPLETE)) {
            FirebaseMessaging.getInstance().subscribeToTopic(Constants.TOPIC_GLOBAL);
            preferences.saveBoolean(Constants.FCM_SUBSCRIPTION_COMPLETE, true);
            preferences.saveBoolean(SharedPreferenceManager.FIRST_TIME_USER,false);
        }
    }

    /***
     * Load navigation menu header information
     * like background image, profile image
     * name, website, notifications action view (dot)
     */
    private void loadNavHeader() {

        int versionCode = BuildConfig.VERSION_CODE;
        String versionName = BuildConfig.VERSION_NAME;

        txtName.setText("WalkIn");
        txtWebsite.setText("Your Job is in Your App");
        // Loading profile image
       Glide.with(this).load(R.drawable.ic_logo)
                .crossFade()
                .thumbnail(0.5f)
                .bitmapTransform(new CircleTransform(this))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgProfile);
    }

    /***
     * Returns respected fragment that user
     * selected from navigation menu
     */
    private void loadHomeFragment() {
        // selecting appropriate nav menu item
        selectNavMenu();
        // if user select the current navigation menu again, don't do anything
        // just close the navigation drawer
        if (getSupportFragmentManager().findFragmentByTag(CURRENT_TAG) != null) {
            drawer.closeDrawers();
            return;
        }

        // Sometimes, when fragment has huge data, screen seems hanging
        // when switching between navigation menus
        // So using runnable, the fragment is loaded with cross fade effect
        // This effect can be seen in GMail app
        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                // update the main content by replacing fragments
                Fragment fragment = getHomeFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame, fragment, CURRENT_TAG);
                fragmentTransaction.commitAllowingStateLoss();
            }
        };

        // If mPendingRunnable is not null, then add to the message queue
        if (mPendingRunnable != null) {
            mHandler.post(mPendingRunnable);
        }
        //Closing drawer on item click
        drawer.closeDrawers();
        // refresh toolbar menu
        invalidateOptionsMenu();
    }

    private Fragment getHomeFragment() {
        switch (navItemIndex) {
            case 0:
                // home
                HomeFragment homeFragment = new HomeFragment();
                return homeFragment;
            default:
                return new HomeFragment();
        }
    }

    private void selectNavMenu() {
        navigationView.getMenu().getItem(navItemIndex).setChecked(true);
    }

    private void setUpNavigationView() {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                   /* case R.id.visa_sponsored:
                        if(NetworkConnectionStatusNotificationUtil.checkConnectionStatus()) {
                            intent = new Intent(MainActivity.this, InterviewListActivity.class);
                            intent.putExtra("REST_URL_HINT", Constants.VISA_SPONSORED_JOBS);
                            closeDrawer(intent);
                        }else{
                            startActivity(new Intent(MainActivity.this, InternetConnectionNotFoundActivity.class));
                        }
                        return true;*/

                     case R.id.nav_new:
                        if(NetworkConnectionStatusNotificationUtil.checkConnectionStatus()) {
                            intent = new Intent(MainActivity.this, InterviewListActivity.class);
                            intent.putExtra("REST_URL_HINT", Constants.NEWLY_ADDED_JOBS);
                            closeDrawer(intent);
                        }else{
                            startActivity(new Intent(MainActivity.this, InternetConnectionNotFoundActivity.class));
                        }
                        return true;
                    case R.id.nav_locations:
                        if(NetworkConnectionStatusNotificationUtil.checkConnectionStatus()) {
                            intent = new Intent(MainActivity.this, LocationBasedInterviews.class);
                            closeDrawer(intent);
                        }else{
                            startActivity(new Intent(MainActivity.this, InternetConnectionNotFoundActivity.class));
                        }
                        return true;

                    case R.id.nav_recommended:
                        if(NetworkConnectionStatusNotificationUtil.checkConnectionStatus()) {
                            intent = new Intent(MainActivity.this, InterviewListActivity.class);
                            intent.putExtra("REST_URL_HINT", Constants.RECOMMENDED_JOBS);
                            closeDrawer(intent);
                        }else{
                            startActivity(new Intent(MainActivity.this, InternetConnectionNotFoundActivity.class));
                        }
                        return true;

                    case R.id.nav_today:
                        if(NetworkConnectionStatusNotificationUtil.checkConnectionStatus()) {
                            intent = new Intent(MainActivity.this, InterviewListActivity.class);
                            intent.putExtra("REST_URL_HINT", Constants.TODAY_JOBS);
                            closeDrawer(intent);
                        }else{
                            startActivity(new Intent(MainActivity.this, InternetConnectionNotFoundActivity.class));
                        }
                        return true;

                    case R.id.nav_tomorrow:
                        if(NetworkConnectionStatusNotificationUtil.checkConnectionStatus()) {
                            intent = new Intent(MainActivity.this, InterviewListActivity.class);
                            intent.putExtra("REST_URL_HINT", Constants.TOMORROW_JOBS);
                            closeDrawer(intent);
                        }else{
                            startActivity(new Intent(MainActivity.this, InternetConnectionNotFoundActivity.class));
                        }
                        return true;

                    case R.id.nav_resume_corner:
                        if(NetworkConnectionStatusNotificationUtil.checkConnectionStatus()) {
                            intent = new Intent(MainActivity.this, ResumeCornerActivity.class);
                            closeDrawer(intent);
                        }else{
                            startActivity(new Intent(MainActivity.this, InternetConnectionNotFoundActivity.class));
                        }
                        return true;
                    case R.id.nav_viewedJobs:
                        if(NetworkConnectionStatusNotificationUtil.checkConnectionStatus()) {
                            intent = new Intent(MainActivity.this, InterviewListActivity.class);
                            intent.putExtra("REST_URL_HINT", Constants.VIEWED_JOBS);
                            closeDrawer(intent);
                        }else{
                            startActivity(new Intent(MainActivity.this, InternetConnectionNotFoundActivity.class));
                        }
                        return true;

                    case R.id.nav_favJobs:
                        if(NetworkConnectionStatusNotificationUtil.checkConnectionStatus()) {
                            intent = new Intent(MainActivity.this, InterviewListActivity.class);
                            intent.putExtra("REST_URL_HINT", Constants.FAVOURITE_JOBS);
                            closeDrawer(intent);
                        }else{
                            startActivity(new Intent(MainActivity.this, InternetConnectionNotFoundActivity.class));
                        }
                        return true;

                    case R.id.nav_notifications:
                        if(NetworkConnectionStatusNotificationUtil.checkConnectionStatus()) {
                            intent = new Intent(MainActivity.this, InterviewListActivity.class);
                            intent.putExtra("REST_URL_HINT", Constants.NOTIFIED_JOBS);
                            closeDrawer(intent);
                        }else{
                            startActivity(new Intent(MainActivity.this, InternetConnectionNotFoundActivity.class));
                        }
                        return true;

                    case R.id.nav_aboutus:
                        if(NetworkConnectionStatusNotificationUtil.checkConnectionStatus()) {
                            intent = new Intent(MainActivity.this, AboutWalkInActivity.class);
                            closeDrawer(intent);
                        }else{
                            startActivity(new Intent(MainActivity.this, InternetConnectionNotFoundActivity.class));
                        }
                        return true;

                    case R.id.nav_settings:
                        if(NetworkConnectionStatusNotificationUtil.checkConnectionStatus()) {
                            intent = new Intent(MainActivity.this, SettingsActivity.class);
                            closeDrawer(intent);
                        }else{
                            startActivity(new Intent(MainActivity.this, InternetConnectionNotFoundActivity.class));
                        }
                        return true;

                    case R.id.nav_postnewjob:
                        if(NetworkConnectionStatusNotificationUtil.checkConnectionStatus()) {
                            intent = new Intent(MainActivity.this, AddNewInterviewActivity.class);
                            closeDrawer(intent);
                        }else{
                            startActivity(new Intent(MainActivity.this, InternetConnectionNotFoundActivity.class));
                        }
                        return true;
                    default:
                        navItemIndex = 0;
                }
                //Checking if the item is in checked state or not, if not make it in checked state
                if (menuItem.isChecked()) {
                    menuItem.setChecked(false);
                } else {
                    menuItem.setChecked(true);
                }
                menuItem.setChecked(true);
                loadHomeFragment();
                return true;
            }
        });

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.openDrawer, R.string.closeDrawer) {
            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }
            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView);
            }
        };
        //Setting the actionbarToggle to drawer layout
        //drawer.setDrawerListener(actionBarDrawerToggle);
        drawer.addDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessary or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();
    }

    private void closeDrawer(final Intent intent) {
        Handler mHandler = new Handler();
        drawer.closeDrawers();
        mHandler.postDelayed(
                new Runnable() {
                    @Override
                    public void run () {
                        startActivity(intent);
                    }
                }
                , 250 // 250...for delay
        );
    }


    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawers();
            return;
        }
        // This code loads home fragment when back key is pressed
        // when user is in other fragment than home
        if (shouldLoadHomeFragOnBackPress) {
            // checking if user is on other navigation menu
            // rather than home
            if (navItemIndex != 0) {
                navItemIndex = 0;
                CURRENT_TAG = TAG_HOME;
                loadHomeFragment();
                return;

            }
        }
        finish();
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.new_home_menu, menu);
        return true;
    }


    @Override
    protected void onResume() {
        super.onResume();

        // register GCM registration complete receiver
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Constants.REGISTRATION_COMPLETE));

        // register new push message receiver
        // by doing this, the activity will be notified each time a new message arrives
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Constants.PUSH_NOTIFICATION));

        for (int i = 0; i < navigationView.getMenu().size(); i++) {
            navigationView.getMenu().getItem(i).setChecked(false);
        }
        HomeFragment homeFragment = (HomeFragment) getSupportFragmentManager().findFragmentByTag(TAG_HOME);
        if(homeFragment != null && homeFragment.isAdded()){
            homeFragment.mRecyclerView.getAdapter().notifyDataSetChanged();
        }
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();

    }

}