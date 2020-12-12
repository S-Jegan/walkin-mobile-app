package com.gotoapps.walkin.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.gotoapps.walkin.R;
import com.gotoapps.walkin.activities.CategoriesActivity;
import com.gotoapps.walkin.activities.FeedbackActivity;
import com.gotoapps.walkin.activities.InternetConnectionNotFoundActivity;
import com.gotoapps.walkin.activities.InterviewListActivity;
import com.gotoapps.walkin.activities.SearchViewActivity;
import com.gotoapps.walkin.adapters.RelatedJobsListWithTitleAdapter;
import com.gotoapps.walkin.model.InterviewCategoryList;
import com.gotoapps.walkin.model.InterviewJSON;
import com.gotoapps.walkin.model.Interviews;
import com.gotoapps.walkin.restclient.ApiClient;
import com.gotoapps.walkin.restclient.ApiInterface;
import com.gotoapps.walkin.restclient.PostData;
import com.gotoapps.walkin.utils.Constants;
import com.gotoapps.walkin.utils.NetworkConnectionStatusNotificationUtil;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;

public class HomeFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "Home Fragment";
    View rootView;
    TextView moreJobsTxt,freshersJobTxt,engineeringJobTxt,softwareJobTxt,educationJobTxt,bankJobsTxt,itesJobTxt,mgmtJobTxt;
    ImageView moreJobsImg,freshersJobImg,engineeringJobImg,softwareJobImg,educationJobImg,bankJobsImg,itesJobImg,mgmtJobImg;
    Intent intent;
    ArrayList<InterviewCategoryList> relatedInterviewsList;
    ImageView mainBannerImage;
    EditText searchText;
    private final static String APP_PNAME = "com.gotoapps.walkin";// Package Name
    ArrayList<InterviewJSON> interviewListVal = new ArrayList<>();
    public RecyclerView mRecyclerView;
    RelatedJobsListWithTitleAdapter adapter;

    public HomeFragment() {
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_home, container, false);

        moreJobsImg=rootView.findViewById(R.id.more_jobs_img);
        freshersJobImg=rootView.findViewById(R.id.freshers_jobs_img);
        engineeringJobImg=rootView.findViewById(R.id.engg_job_img);
        softwareJobImg=rootView.findViewById(R.id.software_job_img);
        educationJobImg=rootView.findViewById(R.id.education_job_img);
        bankJobsImg=rootView.findViewById(R.id.bank_job_img);
        itesJobImg=rootView.findViewById(R.id.ites_job_img);
        mgmtJobImg=rootView.findViewById(R.id.mgmt_jobs_img);

        moreJobsTxt=rootView.findViewById(R.id.more_jobs);
        freshersJobTxt=rootView.findViewById(R.id.freshers_jobs);
        engineeringJobTxt=rootView.findViewById(R.id.engg_job);
        softwareJobTxt=rootView.findViewById(R.id.software_job);
        educationJobTxt=rootView.findViewById(R.id.education_job);
        bankJobsTxt=rootView.findViewById(R.id.bank_job);
        itesJobTxt=rootView.findViewById(R.id.ites_job);
        mgmtJobTxt=rootView.findViewById(R.id.mgmt_jobs);

        mainBannerImage=rootView.findViewById(R.id.imageView7);
        searchText=rootView.findViewById(R.id.searchView);
        mRecyclerView=rootView.findViewById(R.id.my_recycler_view);
        searchText.requestFocus();
        if(searchText.requestFocus()) {
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        }
        //changeBannerIamge();

        searchText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;
                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getRawX() >= (searchText.getRight() - searchText.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        intent = new Intent(getActivity(), SearchViewActivity.class);
                        startActivity(intent);
                        return true;
                    }
                }
                return false;
            }
        });

        freshersJobImg.setOnClickListener(this);
        engineeringJobImg.setOnClickListener(this);
        softwareJobImg.setOnClickListener(this);
        educationJobImg.setOnClickListener(this);
        bankJobsImg.setOnClickListener(this);
        itesJobImg.setOnClickListener(this);
        mgmtJobImg.setOnClickListener(this);
        moreJobsImg.setOnClickListener(this);
        searchText.setOnClickListener(this);
        relatedInterviewsList = new ArrayList<>();

        moreJobsTxt.setOnClickListener(this);
        freshersJobTxt.setOnClickListener(this);
        engineeringJobTxt.setOnClickListener(this);
        softwareJobTxt.setOnClickListener(this);
        educationJobTxt.setOnClickListener(this);
        bankJobsTxt.setOnClickListener(this);
        itesJobTxt.setOnClickListener(this);
        mgmtJobTxt.setOnClickListener(this);

        //createDummyData();
        fetchJSONValues("Trending");
        //fetchJSONValues("Today");
        //fetchJSONValues("Tomorrow");
        RecyclerView my_recycler_view =  rootView.findViewById(R.id.my_recycler_view);
        my_recycler_view.setHasFixedSize(true);
        RelatedJobsListWithTitleAdapter adapter = new RelatedJobsListWithTitleAdapter(getActivity(), relatedInterviewsList);
        my_recycler_view.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        my_recycler_view.setAdapter(adapter);
        my_recycler_view.setItemAnimator(new DefaultItemAnimator());
        my_recycler_view.setNestedScrollingEnabled(false);
        return rootView;

    }

    private void changeBannerIamge() {
        Glide.with(this).load("https://scontent-sea1-1.cdninstagram.com/vp/5d059792aba4e1bd5266b4571bf9fd32/5C41E685/t51.2885-15/fr/e15/s1080x1080/40850731_829281447463012_2520096830676396455_n.jpg")
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(mainBannerImage);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.new_home_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.feedbck:
                intent=new Intent(getActivity(), FeedbackActivity.class);
                startActivity(intent);
                return true;
            case R.id.rate:
                try {
                    Intent appStoreIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + APP_PNAME));
                    appStoreIntent.setPackage("com.android.vending");

                    startActivity(appStoreIntent);
                } catch (android.content.ActivityNotFoundException exception) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + APP_PNAME)));
                }
                return true;
            case R.id.share:
                try {
                    Intent i = new Intent(Intent.ACTION_SEND);
                    i.setType("text/plain");
                    i.putExtra(Intent.EXTRA_SUBJECT, "WalkIn App");
                    String sAux = "WalkIn App \nYour Job is in Your App\n";
                    sAux = sAux + "https://play.google.com/store/apps/details?id=com.gotoapps.walkin";
                    i.putExtra(Intent.EXTRA_TEXT, sAux);
                    startActivity(Intent.createChooser(i, "Choose one"));
                } catch(Exception e) {
                    Toast.makeText(getActivity(),"Something went wrong, Please try later",Toast.LENGTH_SHORT).show();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.freshers_jobs:
            case R.id.freshers_jobs_img:
                //callInterviewListActivity(14);
                if(NetworkConnectionStatusNotificationUtil.checkConnectionStatus()) {
                    intent = new Intent(getActivity(), InterviewListActivity.class);
                    intent.putExtra("REST_URL_HINT", Constants.FRESHER_JOBS);
                    startActivity(intent);
                }else{
                    callInternetDownActivity();
                }
                break;
            case R.id.engg_job:
            case R.id.engg_job_img:
                callInterviewListActivity(1);
                break;
            case R.id.software_job:
            case R.id.software_job_img:
                callInterviewListActivity(6);
                break;
            case R.id.education_job:
            case R.id.education_job_img:
                callInterviewListActivity(7);
                break;
            case R.id.bank_job:
            case R.id.bank_job_img:
                callInterviewListActivity(2);
                break;
            case R.id.ites_job:
            case R.id.ites_job_img:
                callInterviewListActivity(5);
                break;
            case R.id.mgmt_jobs:
            case R.id.mgmt_jobs_img:
                callInterviewListActivity(12);
                break;

            case R.id.more_jobs:
            case R.id.more_jobs_img:
                if(NetworkConnectionStatusNotificationUtil.checkConnectionStatus()) {
                    intent = new Intent(getActivity(), CategoriesActivity.class);
                    startActivity(intent);
                }else {
                    callInternetDownActivity();
                }
                break;

            case R.id.searchView:
                if(NetworkConnectionStatusNotificationUtil.checkConnectionStatus()) {
                    intent = new Intent(getActivity(), SearchViewActivity.class);
                    startActivity(intent);
                }else{
                    callInternetDownActivity();
                }
                break;
        }

    }

    private void callInterviewListActivity(int categoryId) {
        if(NetworkConnectionStatusNotificationUtil.checkConnectionStatus()) {
            intent = new Intent(getActivity(), InterviewListActivity.class);
            intent.putExtra("REST_URL_HINT", Constants.CATEGORY_SPECIFIC_JOBS);
            intent.putExtra("CATEGORY_ID", categoryId);
            startActivity(intent);
        }else{
            callInternetDownActivity();
        }
    }

    private void callInternetDownActivity() {
        startActivity(new Intent(getActivity(), InternetConnectionNotFoundActivity.class));
    }

    private void fetchJSONValues(String title){
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        final InterviewCategoryList dm = new InterviewCategoryList();
        Call<Interviews> interviewList;
        dm.setCategoryName(title+ " Walkins");
        if(title.equalsIgnoreCase("Today")){
            interviewList = apiService.getTodayInterviews(new PostData(1,10));
        }else if(title.equalsIgnoreCase("Tomorrow")){
            interviewList = apiService.getTomorrowInterviews(new PostData(1,10));
        }else{
            interviewList = apiService.getInterviews(new PostData(1,10));
        }
        interviewList.enqueue(new Callback<Interviews>() {
            @Override
            public void onResponse(Call<Interviews> call, retrofit2.Response<Interviews> response) {
                Interviews interviews = response.body();
                mRecyclerView.setVisibility(View.VISIBLE);
                interviewListVal = interviews.getInterviewJSONList();
                if(interviewListVal.size() >0) {
                    dm.setInterviewJSONList(interviewListVal);
                    relatedInterviewsList.add(dm);
                    adapter = new RelatedJobsListWithTitleAdapter(getActivity(), relatedInterviewsList);
                    mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                    mRecyclerView.setAdapter(adapter);
                    mRecyclerView.setNestedScrollingEnabled(false);
                }else{
                    mRecyclerView.setVisibility(View.INVISIBLE);
                }
            }
            @Override
            public void onFailure(Call<Interviews> call, Throwable t) {
                //Log.d("HomeFragment",t.getMessage());
             }
        });

    }
}
