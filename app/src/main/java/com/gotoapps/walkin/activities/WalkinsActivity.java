package com.gotoapps.walkin.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.gotoapps.walkin.R;
import com.gotoapps.walkin.adapters.RelatedJobsListWithTitleAdapter;
import com.gotoapps.walkin.model.InterviewCategoryList;
import com.gotoapps.walkin.model.InterviewJSON;
import com.gotoapps.walkin.model.Interviews;
import com.gotoapps.walkin.restclient.ApiClient;
import com.gotoapps.walkin.restclient.ApiInterface;
import com.gotoapps.walkin.restclient.PostData;
import com.gotoapps.walkin.utils.Constants;
import com.gotoapps.walkin.utils.NumberFormatter;
import com.gotoapps.walkin.utils.SharedPreferenceManager;
import com.gotoapps.walkin.utils.TimeAgo;
import com.gotoapps.walkin.utils.VolleyImageLoader;
import com.like.LikeButton;
import com.like.OnLikeListener;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WalkinsActivity extends AppCompatActivity {

    private FloatingActionButton fab;
    InterviewJSON mSelectedObject;
    TextView companyName, designation, salaryTxt,location, qualification, experience, innterviewDate, jobDesc, walkinAddress, contactDetails, otherInfo, aboutCompany,indicator, noOfViews, timeAgo, skills;
    NetworkImageView companyLogo;
    LikeButton favouriteJob;
    CardView cardOthers;
    private ImageLoader mImageLoader;
    ImageView verfiedImage;
    private AdView mAdView,mAdView1;
    int jobId =0;
    private SharedPreferenceManager sharedPreferenceManager;
    ArrayList<InterviewCategoryList> allSampleData;
    ArrayList<InterviewJSON> interviewListVal = new ArrayList<>();
    RecyclerView mRecyclerView;
    LinearLayout mainLinearLayout;
    private ShimmerFrameLayout mShimmerViewContainer;
    String otherInfoVal,jobDescVal,walkInLocationVal,contactDetailsVal,aboutCompanyVal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walkins);
        Bundle extras = getIntent().getExtras();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mShimmerViewContainer = findViewById(R.id.shimmer_view_container);
        Intent appLinkIntent = getIntent();
        String appLinkAction = appLinkIntent.getAction();
        Uri appLinkData = appLinkIntent.getData();
        if (extras != null && appLinkData==null) {
            jobId = extras.getInt(Constants.INTERVIEW_ID);
            mSelectedObject = getInterviewDetails(jobId);
        }else{
            // ATTENTION: This was auto-generated to handle app links.
            List<String> params=appLinkData.getPathSegments();
            if (Intent.ACTION_VIEW.equals(appLinkAction) && appLinkData != null) {
                jobId = Integer.parseInt(appLinkData.getLastPathSegment());
                mSelectedObject = getInterviewDetails(jobId);
            }
        }
        companyName = findViewById(R.id.textView);
        designation = findViewById(R.id.textView4);
        location = findViewById(R.id.textView5);
        qualification = findViewById(R.id.textView6);
        experience = findViewById(R.id.textView62);
        innterviewDate = findViewById(R.id.textView8);
        jobDesc = findViewById(R.id.textView3);
        walkinAddress = findViewById(R.id.textView12);
        contactDetails = findViewById(R.id.textView18);
        otherInfo = findViewById(R.id.textView16);
        aboutCompany = findViewById(R.id.textView14);
        companyLogo = findViewById(R.id.imageView5);
        verfiedImage = findViewById(R.id.imageView11);
        noOfViews = findViewById(R.id.textView99);
        timeAgo = findViewById(R.id.textView9);
        skills = findViewById(R.id.textViewSkills);
        salaryTxt = findViewById(R.id.salaryTxt);
        favouriteJob = findViewById(R.id.likeButton1);
        mainLinearLayout = findViewById(R.id.mainLayout);
        fab = findViewById(R.id.fab);
        fab.hide();
        mainLinearLayout.setVisibility(View.INVISIBLE);
    }

    private void fillFieldValues() {
        getSupportActionBar().setTitle(mSelectedObject.getDesignation());
        getSupportActionBar().setSubtitle(mSelectedObject.getCompany().getName());
        companyName.setText(mSelectedObject.getCompany().getName());
        designation.setText(mSelectedObject.getDesignation());
        location.setText(mSelectedObject.getLocation());
        qualification.setText(mSelectedObject.getQualification());
        String exp=mSelectedObject.getExpStart() +" - "+mSelectedObject.getExpEnd()+" Years";
        if(mSelectedObject.getExpStart().equals(mSelectedObject.getExpEnd())){
            exp=mSelectedObject.getExpStart() +" Years";
        }
        experience.setText(exp);
        if(mSelectedObject.getNoOfViews()!=null) {
            noOfViews.setText(NumberFormatter.format(mSelectedObject.getNoOfViews()) + " views");
        }else{
            noOfViews.setText("");
        }
        if(mSelectedObject.getVerified()==1) {
            verfiedImage.setImageResource(R.drawable.ic_verified_fkt);
        }else{
            verfiedImage.setImageResource(android.R.color.transparent);
        }
        try {
            String patternString="yyyy-MM-dd hh:mm:ss";
            DateFormat indianFormat = new SimpleDateFormat(patternString);
            Date timestamp = indianFormat.parse(mSelectedObject.getCreatedAt());
            String output = TimeAgo.getTimeAgo(timestamp.getTime());
            timeAgo.setText(output);
        } catch(Exception e) {
            Log.d("WalkinActivity","Error in Parsing Days ago logic");
        }

        try {
            String interviewDate=mSelectedObject.getInterviewStartDate().substring(0, 10);
            String interviewStarDate = mSelectedObject.getInterviewStartDate();
            String interviewEndDate = mSelectedObject.getInterviewEndDate();
            Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse(interviewStarDate);
            Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse(interviewEndDate);

            String startDay= (String) android.text.format.DateFormat.format("dd",   startDate); // 20
            String startMonth  = (String) android.text.format.DateFormat.format("MMM",  startDate); // Jun

            String endDay= (String) android.text.format.DateFormat.format("dd",   endDate); // 20
            String endMonth  = (String) android.text.format.DateFormat.format("MMM",  endDate); // Jun

            if(startDay.equals(endDay) && startMonth.equals(endMonth)){
                interviewDate=startDay+"-"+startMonth;
            }
            else {
                interviewDate=startDay+"-"+startMonth +" to "+ endDay+"-"+endMonth;
            }
            innterviewDate.setText(interviewDate);

        }catch (ParseException e){
            Log.d("WalkinActivity","Error in Parsing InterviewDate");
        }
        String salStart = formatSalary(mSelectedObject.getSalStart(),mSelectedObject.getSalType());
        String salEnd = formatSalary(mSelectedObject.getSalEnd(), mSelectedObject.getSalType());
        String salyTpe = getSalTypeDisplayVaalue(mSelectedObject.getSalType());
        if(mSelectedObject.getSalStart() >0 && mSelectedObject.getSalEnd() > 0){
            if(salStart.equals(salEnd)){
                salaryTxt.setText(salStart + salyTpe);
            }else {
                salaryTxt.setText(salStart + " - " + salEnd + salyTpe);
            }
        }else if(mSelectedObject.getSalStart() >0){
            salaryTxt.setText(salStart + salyTpe);
        }else if(mSelectedObject.getSalEnd() > 0){
            salaryTxt.setText(salEnd +salyTpe);
        }
        else{
            salaryTxt.setText("Not Disclosed");
        }
        if(mSelectedObject.getSkills().isEmpty()){
            skills.setText("No specific skills");
        }else {
            skills.setText(mSelectedObject.getSkills());
        }

        sharedPreferenceManager = new SharedPreferenceManager(this);
        sharedPreferenceManager.saveStringSet(SharedPreferenceManager.VIEWED_JOBS,mSelectedObject.getId());
        Set<String> favJobsList=sharedPreferenceManager.getSavedJobs(SharedPreferenceManager.FAVOURITE_JOBS);
        if(favJobsList!=null && favJobsList.contains(mSelectedObject.getId().toString())){
            favouriteJob.setLiked(true);
            favouriteJob.setLikeDrawableRes(R.drawable.ic_favorite_black_24dp);
        }
        if (android.os.Build.VERSION.SDK_INT >= 24){
            jobDescVal=Html.fromHtml(mSelectedObject.getJobDesc(),Html.FROM_HTML_OPTION_USE_CSS_COLORS).toString().trim();
            jobDesc.setText(jobDescVal);
            walkInLocationVal=Html.fromHtml(mSelectedObject.getWalkinLocation(),Html.FROM_HTML_OPTION_USE_CSS_COLORS).toString().trim();
            walkinAddress.setText(walkInLocationVal);
            if(mSelectedObject.getContactDetails().trim().isEmpty()) {
                contactDetails.setText(Html.fromHtml("Information Not Available", Html.FROM_HTML_OPTION_USE_CSS_COLORS));
            }else {
                contactDetailsVal=Html.fromHtml(mSelectedObject.getContactDetails(), Html.FROM_HTML_OPTION_USE_CSS_COLORS).toString().trim();
                contactDetails.setText(contactDetailsVal);
            }
            if(mSelectedObject.getOtherInfo().trim().isEmpty()) {
                otherInfo.setText(Html.fromHtml("Information Not Available", Html.FROM_HTML_OPTION_USE_CSS_COLORS));
            }else {
                otherInfoVal=Html.fromHtml(mSelectedObject.getOtherInfo(), Html.FROM_HTML_OPTION_USE_CSS_COLORS).toString().trim();
                otherInfo.setText(otherInfoVal);
            }
            aboutCompanyVal=Html.fromHtml(mSelectedObject.getCompany().getAbout_company(),Html.FROM_HTML_OPTION_USE_CSS_COLORS).toString().trim();
            aboutCompany.setText(aboutCompanyVal);
        }
        else{
            jobDescVal=Html.fromHtml(mSelectedObject.getJobDesc()).toString().trim();
            jobDesc.setText(jobDescVal);
            walkInLocationVal=Html.fromHtml(mSelectedObject.getWalkinLocation()).toString().trim();
            walkinAddress.setText(walkInLocationVal);
            if(mSelectedObject.getContactDetails().trim().isEmpty()) {
                contactDetails.setText(Html.fromHtml("Information Not Available"));
            }else {
                contactDetailsVal=Html.fromHtml(mSelectedObject.getContactDetails()).toString().trim();
                contactDetails.setText(contactDetailsVal);
            }
            if(mSelectedObject.getOtherInfo().trim().isEmpty()) {
                otherInfo.setText(Html.fromHtml("Information Not Available"));
            }else {
                otherInfoVal=Html.fromHtml(mSelectedObject.getOtherInfo()).toString().trim();
                otherInfo.setText(otherInfoVal);
            }
            aboutCompanyVal=Html.fromHtml(mSelectedObject.getCompany().getAbout_company()).toString().trim();
            aboutCompany.setText(aboutCompanyVal);
        }
        mImageLoader = VolleyImageLoader.getInstance().getImageLoader();
        companyLogo.setImageUrl(mSelectedObject.getCompany().getLogo().replace("localhost", "192.168.43.113").trim(),mImageLoader);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addShareCount(mSelectedObject.getId());
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/html");
                String lines="--------------------------------------------------";
                String shareBody = Constants.VIEW_JOB +"\n"+
                        lines+"\n"+
                        mSelectedObject.getCompany().getName()+"\n"+
                        mSelectedObject.getDesignation()+"\n"+
                        Constants.VIEW_MORE+mSelectedObject.getId()+"\n"+
                        lines+"\n"+
                        Constants.SENT_FROM +"\n"+Constants.DOWNLOAD;
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, mSelectedObject.getDesignation());
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Share via"));
            }
        });

        favouriteJob.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                sharedPreferenceManager.saveStringSet(SharedPreferenceManager.FAVOURITE_JOBS,mSelectedObject.getId());
                favouriteJob.setLiked(true);
                favouriteJob.setLikeDrawableRes(R.drawable.ic_favorite_black_24dp);
                Snackbar snackbar = Snackbar.make(likeButton,"Added as a Favourite Job.",Snackbar.LENGTH_SHORT);
                snackbar.show();
            }
            @Override
            public void unLiked(LikeButton likeButton) {
                sharedPreferenceManager.deleteFavouriteJob(SharedPreferenceManager.FAVOURITE_JOBS,mSelectedObject.getId());
                favouriteJob.setLiked(false);
                favouriteJob.setLikeDrawableRes(R.drawable.ic_favorite_border_black_24dp);
                Snackbar snackbar = Snackbar.make(likeButton,"Removed from Favourite Job.",Snackbar.LENGTH_SHORT);
                snackbar.show();
            }
        });


        /******************************************************************************************/
        /* ADMOB HAS BEEN ENABLED FOR THIS APPLICATION IN INTERVIEW DETAILS ACTIVITY SCREEN       */
        /******************************************************************************************/
        mAdView1 = findViewById(R.id.adView1);
        AdRequest adRequest1 = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).addTestDevice("2CC69C5A9CD8D0D90A54CA3A4A259D4D").build();
        //AdRequest adRequest1 = new AdRequest.Builder().build();
        mAdView1.loadAd(adRequest1);
        mAdView1.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                mAdView1.setVisibility(View.VISIBLE);
            }
            @Override
            public void onAdFailedToLoad(int error) {
                mAdView1.setVisibility(View.GONE);
            }
        });


        mAdView = findViewById(R.id.adView);
        //AdRequest adRequest = new AdRequest.Builder().build();
        AdRequest adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).addTestDevice("2CC69C5A9CD8D0D90A54CA3A4A259D4D").build();
        mAdView.loadAd(adRequest);
        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                mAdView.setVisibility(View.VISIBLE);
            }
            @Override
            public void onAdFailedToLoad(int error) {
                mAdView.setVisibility(View.GONE);
            }
        });

        allSampleData = new ArrayList<InterviewCategoryList>();
        fetchJSONValues();
        mRecyclerView = findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        stopShimmer();
        mainLinearLayout.setVisibility(View.VISIBLE);
        fab.show();
    }

    private String getSalTypeDisplayVaalue(String salType) {
        if(salType.trim().equalsIgnoreCase("Monthly")){
            return " (PM)";
        }else{
            return " (PA)";
        }
    }

    private void addShareCount(Integer id) {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<InterviewJSON>> interviewJson=apiService.addShareCount(jobId);
        interviewJson.enqueue(new Callback<List<InterviewJSON>>() {
            @Override
            public void onResponse(Call<List<InterviewJSON>> call, Response<List<InterviewJSON>> response) {
                Log.i("Share Count","Increased");
            }
            @Override
            public void onFailure(Call<List<InterviewJSON>> call, Throwable t) {
                Log.d("WalkinsActivity",t.getMessage());
            }
        });
    }

    private String formatSalary(Double sal, String salType) {
        Locale locale = new Locale("en","IN");
        DecimalFormat decimalFormat = (DecimalFormat) DecimalFormat.getCurrencyInstance(locale);
        DecimalFormatSymbols dfs = DecimalFormatSymbols.getInstance(locale);
        dfs.setCurrencySymbol("\u20B9");
        decimalFormat.setDecimalFormatSymbols(dfs);
        String val;
        if(salType.trim().equalsIgnoreCase("Monthly")){
            val=decimalFormat.format(sal/1000);
            val=val.substring(0,val.indexOf('.'))+" K";;
        }else{
            val=decimalFormat.format(sal/100000);
            val=val.substring(0,val.indexOf('.')+2)+" L";
        }
        //String val=decimalFormat.format(sal/100000);
        return val;
    }

    private InterviewJSON getInterviewDetails(int jobId) {
            mShimmerViewContainer.startShimmerAnimation();
            ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
            Call<List<InterviewJSON>> interviewJson=apiService.getInterviewByID(jobId);
            interviewJson.enqueue(new Callback<List<InterviewJSON>>() {
                @Override
                public void onResponse(Call<List<InterviewJSON>> call, Response<List<InterviewJSON>> response) {
                    mSelectedObject=response.body().get(0);
                    fillFieldValues();
                }
                @Override
                public void onFailure(Call<List<InterviewJSON>> call, Throwable t) {
                    Log.d("WalkinsActivity",t.getMessage());
                    stopShimmer();
                }
            });
        return mSelectedObject;
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onPause() {
        if (mAdView != null && mAdView1 != null ) {
            mAdView.pause();
            mAdView1.pause();
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mAdView != null && mAdView1 != null ) {
            mAdView.resume();
            mAdView1.resume();
        }
    }

    @Override
    public void onDestroy() {
        if (mAdView != null ) {
            mAdView.destroy();
        }
        if(mAdView1 != null){
            mAdView1.destroy();
        }
        super.onDestroy();
    }

    private void fetchJSONValues(){
        mShimmerViewContainer.startShimmerAnimation();
        final InterviewCategoryList dm = new InterviewCategoryList();
        dm.setCategoryName("Related Jobs");
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<Interviews> interviewList = apiService.fetchCategorySpecificInterviews(mSelectedObject.getCategoryId(),new PostData(1,10));
        interviewList.enqueue(new Callback<Interviews>() {
            @Override
            public void onResponse(Call<Interviews> call, retrofit2.Response<Interviews> response) {
                Interviews interviews = response.body();
                interviewListVal = interviews.getInterviewJSONList();
                stopShimmer();
                for(InterviewJSON interviewJSON:interviewListVal){
                    if(interviewJSON.getId()==mSelectedObject.getId()){
                        interviewListVal.remove(interviewJSON);
                        break;
                    }
                }
                dm.setInterviewJSONList(interviewListVal);
                allSampleData.add(dm);
                RelatedJobsListWithTitleAdapter adapter = new RelatedJobsListWithTitleAdapter(getApplicationContext(), allSampleData);
                mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
                mRecyclerView.setAdapter(adapter);
                mRecyclerView.setNestedScrollingEnabled(false);
                if(interviewListVal.size()==0){
                    mRecyclerView.setVisibility(View.INVISIBLE);
                }
            }
            @Override
            public void onFailure(Call<Interviews> call, Throwable t) {
                Log.d("WalkinsActivity",t.getMessage());
                stopShimmer();
            }
        });

    }
    private void stopShimmer(){
        mShimmerViewContainer.stopShimmerAnimation();
        mShimmerViewContainer.setVisibility(View.GONE);
    }

}
