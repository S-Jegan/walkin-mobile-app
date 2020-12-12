package com.gotoapps.walkin.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.gotoapps.walkin.R;
import com.gotoapps.walkin.adapters.ResumeListAdapter;
import com.gotoapps.walkin.model.Location;
import com.gotoapps.walkin.model.Resume;
import com.gotoapps.walkin.response.ResumeResponse;
import com.gotoapps.walkin.restclient.ApiClient;
import com.gotoapps.walkin.restclient.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResumeCornerActivity extends AppCompatActivity {

    private List<Resume> resumeList = new ArrayList<>();
    private RecyclerView recyclerView;
    private ResumeListAdapter resumeListAdapter;
    private TextView emptyText;
    private ShimmerFrameLayout mShimmerViewContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resume_corner);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Resume Corner");

        recyclerView =  findViewById(R.id.recycler_view);
        mShimmerViewContainer = findViewById(R.id.shimmer_view_container);
        emptyText =findViewById(R.id.tv_no_data);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        getResumeJSONData();
    }

    private void getResumeJSONData() {
        mShimmerViewContainer.startShimmerAnimation();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ResumeResponse> resumesList = apiService.getResumes();
        resumesList.enqueue(new Callback<ResumeResponse>() {
            @Override
            public void onResponse(Call<ResumeResponse> call, Response<ResumeResponse> response) {
                ResumeResponse resumeResponse=response.body();
                if(resumeResponse.getTotal()>0){
                    resumeList=resumeResponse.getResumeList();
                    resumeListAdapter = new ResumeListAdapter(getApplicationContext(),resumeList);
                    recyclerView.setAdapter(resumeListAdapter);
                    stopShimmer();
                }
                else{
                    emptyText.setVisibility(View.VISIBLE);
                    emptyText.setText("No Resumes Found");
                    stopShimmer();
                }
            }
            @Override
            public void onFailure(Call<ResumeResponse> call, Throwable t) {
                emptyText.setVisibility(View.VISIBLE);
                emptyText.setText("Something went wrong, Please try again later!");
                stopShimmer();
            }
        });
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

    private void stopShimmer(){
        mShimmerViewContainer.stopShimmerAnimation();
        mShimmerViewContainer.setVisibility(View.GONE);
    }
}
