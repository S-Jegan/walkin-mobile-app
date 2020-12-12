package com.gotoapps.walkin.activities;

import android.content.DialogInterface;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.gotoapps.walkin.AppController;
import com.gotoapps.walkin.R;
import com.gotoapps.walkin.adapters.InterviewListDataAdapter;
import com.gotoapps.walkin.db.DBKeys;
import com.gotoapps.walkin.model.InterviewJSON;
import com.gotoapps.walkin.model.Interviews;
import com.gotoapps.walkin.restclient.ApiClient;
import com.gotoapps.walkin.restclient.ApiInterface;
import com.gotoapps.walkin.restclient.PostData;
import com.gotoapps.walkin.restclient.PostSearch;
import com.gotoapps.walkin.utils.Constants;
import com.gotoapps.walkin.utils.SharedPreferenceManager;
import com.snappydb.DB;
import com.snappydb.DBFactory;
import com.snappydb.SnappydbException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;

public class InterviewListActivity extends AppCompatActivity {
    private SharedPreferenceManager sharedPreferenceManager;
    ArrayList<InterviewJSON> interviewListVal = new ArrayList<>();
    RecyclerView mRecyclerView;
    InterviewListDataAdapter adapter;
    TextView emptyText;
    LinearLayoutManager manager;
    Boolean isLoading = true;
    private int pastVisibleItems,visibleItemsCount,totalItemsCount,prevTotal =0;
    private int viewThreshold = 10;
    private ShimmerFrameLayout mShimmerViewContainer;
    int currentPage,total;
    SearchView mySearch;
    private ImageView nodataFoundImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interview_list);
        emptyText =findViewById(R.id.tv_no_data);
        nodataFoundImage=(ImageView)findViewById(R.id.no_data_img);
        manager = new LinearLayoutManager(AppController.getAppContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView = findViewById(R.id.recycler);
        mShimmerViewContainer = findViewById(R.id.shimmer_view_container);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setHasFixedSize(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Interviews List");
        fetchJSONValues();
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                visibleItemsCount = manager.getChildCount();
                totalItemsCount = manager.getItemCount();
                pastVisibleItems=manager.findFirstCompletelyVisibleItemPosition();
                if(dy > 0){
                    if(isLoading){
                        if(totalItemsCount > prevTotal) {
                            isLoading = false;
                            prevTotal = totalItemsCount;
                        }
                    }
                    if(!isLoading && (totalItemsCount - visibleItemsCount <= (pastVisibleItems+viewThreshold))){
                        performPagination();
                        isLoading = true;
                    }
                }
            }
        });
    }

    private void fetchJSONValues(){
        mShimmerViewContainer.startShimmerAnimation();
        Call<Interviews> interviewList=getRESTService(0);
        interviewList.enqueue(new Callback<Interviews>() {
            @Override
            public void onResponse(Call<Interviews> call, retrofit2.Response<Interviews> response) {
                Interviews interviews = response.body();
                interviewListVal = interviews.getInterviewJSONList();
                currentPage = interviews.getCurrentPage();
                total=interviews.getTotalPages();
                if(total==1){
                    getSupportActionBar().setSubtitle(total +" Interview Found");
                }else if(total>1){
                    getSupportActionBar().setSubtitle(total + " Interviews Found");
                }
                if(!interviewListVal.isEmpty()){
                    emptyText.setVisibility(View.INVISIBLE);
                    adapter = new InterviewListDataAdapter(getApplicationContext(),interviewListVal,mRecyclerView);
                    mRecyclerView.setAdapter(adapter);
                    stopShimmer();
                }
                else{
                    displayNoRowsFound();
                }
            }
            @Override
            public void onFailure(Call<Interviews> call, Throwable t) {
                Log.d("InterviewListActivity",t.getMessage());
                t.printStackTrace();
                emptyText.setVisibility(View.VISIBLE);
                emptyText.setText("Something went wrong, Please try again later!");
                nodataFoundImage.setVisibility(View.VISIBLE);
                stopShimmer();
            }
        });
    }

    private void performPagination(){
        mShimmerViewContainer.startShimmerAnimation();
        Call<Interviews> interviewList=getRESTService(currentPage);
        interviewList.enqueue(new Callback<Interviews>() {
            @Override
            public void onResponse(Call<Interviews> call, retrofit2.Response<Interviews> response) {
                if(response.body().getInterviewJSONList().size() >0){
                    Interviews interviews = response.body();
                    currentPage = interviews.getCurrentPage();
                    List<InterviewJSON> pagInterviewJSONs = interviews.getInterviewJSONList();
                    adapter.addInterviews(pagInterviewJSONs);
                }
                stopShimmer();
            }
            @Override
            public void onFailure(Call<Interviews> call, Throwable t) {
                Log.d("InterviewListActivity",t.getMessage());
                stopShimmer();
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        //adapter.notifyDataSetChanged();

    }

    private void stopShimmer(){
        mShimmerViewContainer.stopShimmerAnimation();
        mShimmerViewContainer.setVisibility(View.GONE);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.menu_search:
                return true;
            case R.id.menu_delete:
                Bundle extras = getIntent().getExtras();
                if (!mySearch.isIconified()) {
                    mySearch.onActionViewCollapsed();
                }
                final String restURL=extras.getString("REST_URL_HINT");
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder.setTitle("Do you want to delete the interviews?");
                //alertDialogBuilder.setMessage("Do you want to delete the interviews?");
                alertDialogBuilder.setCancelable(true);
                alertDialogBuilder.setPositiveButton("No",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                //dialog.dismiss();
                            }
                        });
                alertDialogBuilder.setNegativeButton("Yes",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(adapter!=null) {
                            sharedPreferenceManager.deleteStringsSet(restURL);
                            adapter.deleteInterviews();
                            emptyText.setText("Interviews got cleared");
                            emptyText.setVisibility(View.VISIBLE);
                            nodataFoundImage.setVisibility(View.VISIBLE);
                            getSupportActionBar().setSubtitle("");
                        }else{
                            Toast.makeText(getApplicationContext(), "Nothing to delete!", Toast.LENGTH_LONG).show();
                        }
                    }
                });
                final AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_options, menu);
        MenuItem searchMenuItem = menu.findItem(R.id.menu_search);
        MenuItem deleteMenuItem = menu.findItem(R.id.menu_delete);
        mySearch = (SearchView) MenuItemCompat.getActionView(searchMenuItem);
        mySearch.setQueryHint("Job Title or Keyword");
        mySearch.setIconified(true);
        Bundle extras = getIntent().getExtras();
        String restURL=extras.getString("REST_URL_HINT");
        if(restURL.equalsIgnoreCase(Constants.NOTIFIED_JOBS) ||
                restURL.equalsIgnoreCase(Constants.FAVOURITE_JOBS) ||
                restURL.equalsIgnoreCase(Constants.VIEWED_JOBS)){
            deleteMenuItem.setVisible(true);
        }
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
        ArrayList<InterviewJSON> filteredOutput = new ArrayList<>();
        String interViewObjVal;
        if (mySearch != null) {
            for (InterviewJSON interview : interviewListVal) {
                interViewObjVal = interview.searchString();
                if (interViewObjVal.toLowerCase().contains(query.toLowerCase())) {
                    filteredOutput.add(interview);
                }
            }
        } else {
            filteredOutput = interviewListVal;
        }
        adapter = new InterviewListDataAdapter(AppController.getAppContext(), filteredOutput,mRecyclerView);
        mRecyclerView.setAdapter(adapter);
    }

    private void clearSearchFilter() {
        adapter = new InterviewListDataAdapter(AppController.getAppContext(), interviewListVal,mRecyclerView);
        mRecyclerView.setAdapter(adapter);
    }

    private Call<Interviews> getRESTService(Integer currentPage) {
        Bundle extras = getIntent().getExtras();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        String restURL;
        Call<Interviews> interviewList=null;
        sharedPreferenceManager=new SharedPreferenceManager(getApplicationContext());
        if (extras != null && extras.getString("REST_URL_HINT")!=null) {
            restURL=extras.getString("REST_URL_HINT");
            if(restURL.equalsIgnoreCase(Constants.VISA_SPONSORED_JOBS)){
                interviewList = apiService.fetchVisaSponsoredInterviews(new PostData(currentPage+1,10));
            }else if(restURL.equalsIgnoreCase(Constants.VERIFIED_JOBS)){
                interviewList = apiService.fetchVerifiedInterviews(new PostData(currentPage+1,10));
            }else if(restURL.equalsIgnoreCase(Constants.FRESHER_JOBS)){
                interviewList = apiService.fetchFresherInterviews(new PostData(currentPage+1,10));
            }else if(restURL.equalsIgnoreCase(Constants.INDUSTRY_SPECIFIC_JOBS)){
                Integer industryId=extras.getInt("INDUSTRY_ID");
                interviewList = apiService.fetchIndustrySpecificInterviews(industryId,new PostData(currentPage+1,10));
            }else if(restURL.equalsIgnoreCase(Constants.CATEGORY_SPECIFIC_JOBS)){
                Integer categoryId=extras.getInt("CATEGORY_ID");
                interviewList = apiService.fetchCategorySpecificInterviews(categoryId,new PostData(currentPage+1,10));
            }
            else if(restURL.equalsIgnoreCase(Constants.NEWLY_ADDED_JOBS)){
                interviewList = apiService.fetchNewlyAddedInterviews(new PostData(currentPage+1,10));
            }else if(restURL.equalsIgnoreCase(Constants.LOCATION_SPECIFIC_JOBS)){
                String locationName=extras.getString("LOCATION_NAME");
                interviewList = apiService.fetchLocationSpecificInterviews(locationName,new PostData(currentPage+1,10));
            }else if(restURL.equalsIgnoreCase(Constants.TODAY_JOBS)){
                interviewList = apiService.getTodayInterviews(new PostData(currentPage+1,10));
            }else if(restURL.equalsIgnoreCase(Constants.TOMORROW_JOBS)){
                interviewList = apiService.getTomorrowInterviews(new PostData(currentPage+1,10));
            }else if(restURL.equalsIgnoreCase(Constants.RECOMMENDED_JOBS)){
                Integer[] categories=null;
                try {
                    DB snappydb = DBFactory.open(this);
                    categories=snappydb.getObjectArray(DBKeys.INTERESTED_JOBS,Integer.class);
                    snappydb.close();
                }catch(SnappydbException ex){
                    Log.d("SnappydbException",ex.getMessage());
                }
                if(categories!=null && categories.length>0) {
                    Set<String> categoryids=new HashSet<>();
                    for(Integer catId:categories){
                        categoryids.add(catId.toString());
                    }
                    interviewList = apiService.fetchInterviewsByMultipleCategoryIds(new PostData(currentPage+1,10,categoryids));
                }
            }
            else if(restURL.equalsIgnoreCase(Constants.FAVOURITE_JOBS)){
                getSupportActionBar().setTitle("Favourite Jobs");
                Set<String> favJobIdSet = sharedPreferenceManager.getSavedJobs(SharedPreferenceManager.FAVOURITE_JOBS);
                if(favJobIdSet==null){
                    favJobIdSet=new HashSet<>();
                }
                interviewList = apiService.fetchInterviewsByMultipleIds(new PostData(currentPage+1,10,favJobIdSet));
            }else if(restURL.equalsIgnoreCase(Constants.VIEWED_JOBS)){
                getSupportActionBar().setTitle("Viewed Jobs");
                Set<String> viewedJobIdSet = sharedPreferenceManager.getSavedJobs(SharedPreferenceManager.VIEWED_JOBS);
                if(viewedJobIdSet==null){
                    viewedJobIdSet=new HashSet<>();
                }
                interviewList = apiService.fetchInterviewsByMultipleIds(new PostData(currentPage+1,10,viewedJobIdSet));
            }
            else if(restURL.equalsIgnoreCase(Constants.NOTIFIED_JOBS)){
                getSupportActionBar().setTitle("Notifications");
                Set<String> notifiedJobSet = sharedPreferenceManager.getSavedJobs(SharedPreferenceManager.NOTIFIED_JOBS);
                if(notifiedJobSet==null){
                    notifiedJobSet=new HashSet<>();
                }
                interviewList = apiService.fetchInterviewsByMultipleIds(new PostData(currentPage+1,10,notifiedJobSet));
            }else if(restURL.equalsIgnoreCase(Constants.JOB_SEARCH)){
                PostSearch postSearchData=new PostSearch();
                try {
                    DB snappydb = DBFactory.open(getApplicationContext());
                    postSearchData=snappydb.getObject(DBKeys.LAST_SEARCH,PostSearch.class);
                    snappydb.close();
                }catch(SnappydbException ex){
                    Log.d("SnappydbException",ex.getMessage());
                }
                if(postSearchData!=null)
                {
                    postSearchData.setPage(currentPage+1);
                    postSearchData.setPer_page(10);
                    interviewList = apiService.fetchInterviewsByKewords(postSearchData);
                }else{
                    interviewList = apiService.fetchAllInterviews(new PostData(currentPage+1,10));
                }
            }else if(restURL.equalsIgnoreCase(Constants.ALL_JOBS)){
                interviewList = apiService.fetchAllInterviews(new PostData(currentPage+1,10));
            }
        }else {
            interviewList = apiService.fetchAllInterviews(new PostData(currentPage+1,10));
        }
        return interviewList;
    }

    private void displayNoRowsFound() {
        //getSupportActionBar().setSubtitle("No Interviews Found");
        emptyText.setVisibility(View.VISIBLE);
        nodataFoundImage.setVisibility(View.VISIBLE);
        stopShimmer();
    }

}
