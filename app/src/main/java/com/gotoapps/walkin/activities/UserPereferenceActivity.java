package com.gotoapps.walkin.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.gotoapps.walkin.MainActivity;
import com.gotoapps.walkin.R;
import com.gotoapps.walkin.adapters.UserPreferenceAdapter;
import com.gotoapps.walkin.db.DBKeys;
import com.gotoapps.walkin.model.Category;
import com.gotoapps.walkin.model.Industry;
import com.gotoapps.walkin.restclient.ApiClient;
import com.gotoapps.walkin.restclient.ApiInterface;
import com.gotoapps.walkin.utils.Constants;
import com.snappydb.DB;
import com.snappydb.DBFactory;
import com.snappydb.SnappydbException;

import net.khirr.android.privacypolicy.PrivacyPolicyDialog;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserPereferenceActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ShimmerFrameLayout mShimmerViewContainer;
    private List<Category> categoryList;
    private UserPreferenceAdapter userPreferenceAdapter;
    private Button selectCategory;
    PrivacyPolicyDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_pereference);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_black_24dp);
        getSupportActionBar().setTitle("Choose Your Preference");
        recyclerView =  findViewById(R.id.recycler_view);
        mShimmerViewContainer = findViewById(R.id.shimmer_view_container);
        categoryList=new ArrayList<>();
        fetchJSONValues();
        /**
         * Privacy Policy Dialog
         */
        dialog = new PrivacyPolicyDialog(this,
                Constants.TERMS_OF_SERVICE_URL,
                Constants.PRIVACY_POLICY_URL);
        dialog.setAcceptButtonColor(ContextCompat.getColor(this, R.color.colorAccent));
        dialog.addPoliceLine(Constants.DISCLAIMER_NOTE);
        dialog.show();

        dialog.setOnClickListener(new PrivacyPolicyDialog.OnClickListener() {
            @Override
            public void onAccept(boolean isFirstTime) {
                dialog.getDialog().dismiss();
            }
            @Override
            public void onCancel() {
                finish();
            }
        });
        selectCategory=findViewById(R.id.category);
        selectCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(userPreferenceAdapter.getSelectedElements().size()<1){
                    Toast.makeText(getApplicationContext(),"Please Select at least one category",Toast.LENGTH_SHORT).show();
                }else{
                    try {
                        DB snappydb = DBFactory.open(getApplicationContext());
                        List<Category> selectedValues=userPreferenceAdapter.getSelectedElements();
                        Integer[] categories=new Integer[selectedValues.size()];
                        int counter=0;
                        for(Category id:selectedValues){
                            categories[counter]=id.getId();
                            counter++;
                        }
                        snappydb.put(DBKeys.INTERESTED_JOBS,categories);
                        snappydb.putBoolean(DBKeys.FIRST_TIME_USER,false);
                        snappydb.putBoolean(DBKeys.TC_ACCEPTED,true);
                        snappydb.close();
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        finish();
                    }catch(SnappydbException ex){
                        Snackbar.make(view,"Something went worng!",Snackbar.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void fetchJSONValues(){
        mShimmerViewContainer.startShimmerAnimation();
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<Category>> industries = apiService.getCategoriesList();
        industries.enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                if(response.isSuccessful() && response.body().size()>0){
                    categoryList=response.body();
                    userPreferenceAdapter = new UserPreferenceAdapter(getApplicationContext(),categoryList);
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                    recyclerView.setLayoutManager(mLayoutManager);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(userPreferenceAdapter);
                    stopShimmer();
                }
            }
            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
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
