package com.gotoapps.walkin.activities;

import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.gotoapps.walkin.AppController;
import com.gotoapps.walkin.R;
import com.gotoapps.walkin.adapters.InterviewListDataAdapter;
import com.gotoapps.walkin.adapters.LocationAdapter;
import com.gotoapps.walkin.model.InterviewJSON;
import com.gotoapps.walkin.model.Location;
import com.gotoapps.walkin.restclient.ApiClient;
import com.gotoapps.walkin.restclient.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LocationBasedInterviews extends AppCompatActivity {

    private List<Location> locationList = new ArrayList<>();
    private RecyclerView recyclerView;
    private LocationAdapter locationAdapter;
    private TextView emptyText;
    private ShimmerFrameLayout mShimmerViewContainer;
    SearchView mySearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_based_interviews);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Interview Locations");
        recyclerView = findViewById(R.id.recycler_view);
        mShimmerViewContainer =findViewById(R.id.shimmer_view_container);
        emptyText =findViewById(R.id.tv_no_data);
        fetchJSONValues();
    }
    private void fetchJSONValues(){
        mShimmerViewContainer.startShimmerAnimation();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<Location>> locationBasedInterviews = apiService.getLocationBasedInterviews();
        locationBasedInterviews.enqueue(new Callback<List<Location>>() {
            @Override
            public void onResponse(Call<List<Location>> call, Response<List<Location>> response) {
                locationList=response.body();
                if(!locationList.isEmpty()){
                    emptyText.setVisibility(View.INVISIBLE);
                    locationAdapter = new LocationAdapter(getApplicationContext(),locationList);
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                    recyclerView.setLayoutManager(mLayoutManager);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.addItemDecoration(new DividerItemDecoration(AppController.getAppContext(), LinearLayoutManager.VERTICAL));
                    recyclerView.setAdapter(locationAdapter);
                    stopShimmer();
                }
                else{
                    getSupportActionBar().setSubtitle("No Interviews Found");
                    emptyText.setVisibility(View.VISIBLE);
                    stopShimmer();
                }
            }
            @Override
            public void onFailure(Call<List<Location>> call, Throwable t) {
                Log.d("LocationActivity",t.getMessage());
                emptyText.setVisibility(View.VISIBLE);
                emptyText.setText("Internal Server Error");
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        MenuItem searchMenuItem = menu.findItem(R.id.menu_search);
        mySearch = (SearchView) MenuItemCompat.getActionView(searchMenuItem);
        mySearch.setQueryHint("Job Title or Keyword");
        mySearch.setIconified(true);
        mySearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                if (TextUtils.isEmpty(newText)){
                    clearSearchFilter();
                }else{
                    filterInterviewData(newText);
                }
                return true;
            }
        });
        return true;
    }

    private void filterInterviewData(String query) {
        List<Location> filteredOutput = new ArrayList<>();
        String locationSearchVal;
        if (mySearch != null) {
            for (Location location : locationList) {
                locationSearchVal = location.searchString();
                if (locationSearchVal.toLowerCase().contains(query.toLowerCase())) {
                    filteredOutput.add(location);
                }
            }
        } else {
            filteredOutput = locationList;
        }
        locationAdapter = new LocationAdapter(AppController.getAppContext(), filteredOutput);
        recyclerView.setAdapter(locationAdapter);
    }

    private void clearSearchFilter() {
        locationAdapter = new LocationAdapter(AppController.getAppContext(), locationList);
        recyclerView.setAdapter(locationAdapter);
    }
}
