package com.gotoapps.walkin.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;

import com.gotoapps.walkin.AppController;
import com.gotoapps.walkin.MainActivity;
import com.gotoapps.walkin.R;
import com.gotoapps.walkin.adapters.LocationAdapter;
import com.gotoapps.walkin.db.DBKeys;
import com.gotoapps.walkin.model.Industry;
import com.gotoapps.walkin.model.Keywords;
import com.gotoapps.walkin.model.Location;
import com.gotoapps.walkin.restclient.ApiClient;
import com.gotoapps.walkin.restclient.ApiInterface;
import com.gotoapps.walkin.restclient.PostSearch;
import com.gotoapps.walkin.utils.Constants;
import com.snappydb.DB;
import com.snappydb.DBFactory;
import com.snappydb.SnappydbException;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchViewActivity extends AppCompatActivity {
    EditText searchText;
    private List<Location> locationList = new ArrayList<>();
    private List<Keywords> keywordsList = new ArrayList<>();
    private MultiAutoCompleteTextView jobLocationText;
    private MultiAutoCompleteTextView jobKeywordsText;
    private ArrayAdapter<String> location;
    private CardView lastSearch;
    private TextView lastSearchText;
    private TextView lastSearchTextLocation;
    private CardView recentSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_search);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Search your Job");
        fetchLocationList();
        fetchKeyWordsList();
        jobLocationText =  findViewById(R.id.editSearchText2);
        jobKeywordsText = findViewById(R.id.editSearchText1);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        recentSearch=findViewById(R.id.recentSearches);

        String[] experience=getResources().getStringArray(R.array.experience);
        final AutoCompleteTextView experienceAutoComplete =  findViewById(R.id.editSearchText3);
        final ArrayAdapter<String> experienceList = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, experience);
        experienceAutoComplete.setAdapter(experienceList);
        experienceAutoComplete.setThreshold(1);

        String[] salary=getResources().getStringArray(R.array.salary);
        final AutoCompleteTextView salaryAutoComplete =  findViewById(R.id.editSearchText4);
        ArrayAdapter<String> salaryList = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, salary);
        salaryAutoComplete.setAdapter(salaryList);
        salaryAutoComplete.setThreshold(1);

        lastSearch=findViewById(R.id.recentSearches);
        lastSearchText=findViewById(R.id.textView2);
        lastSearchTextLocation=findViewById(R.id.textView3);
        PostSearch lastSearchedData=null;
        try {
            DB snappydb = DBFactory.open(getApplicationContext());
            lastSearchedData=snappydb.getObject(DBKeys.LAST_SEARCH,PostSearch.class);
            snappydb.close();
        }catch(SnappydbException ex){
        }
        if(lastSearchedData!=null){
            lastSearch.setVisibility(View.VISIBLE);
            lastSearchText.setText(lastSearchedData.getKeyWordsList().toString().replace('[',' ').replace(']',' '));
            if(lastSearchedData.getLocationList()!=null) {
                lastSearchTextLocation.setText(lastSearchedData.getLocationList().toString().replace('[', ' ').replace(']', ' '));
            }else{
                lastSearchTextLocation.setVisibility(View.GONE);
            }
        }


        Button search= findViewById(R.id.searchButton);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final PostSearch postSearchData=new PostSearch();
                if(TextUtils.isEmpty(jobKeywordsText.getText().toString())) {
                    jobKeywordsText.setError("Please enter valid values!");
                }else {
                    List<String> jobKeywordList=Arrays.asList(StringUtils.split(jobKeywordsText.getText().toString().trim(), ","));
                    Set<String> jobKeysSet=new HashSet<>();
                    List<String> jobLocList=Arrays.asList(StringUtils.split(jobLocationText.getText().toString().trim(), ","));
                    Set<String> jobLocSet=new HashSet<>();
                    for (String val:jobKeywordList){
                        jobKeysSet.add(val);
                    }
                    for(String val:jobLocList){
                        jobLocSet.add(val);
                    }
                    postSearchData.setKeyWordsList(jobKeysSet);
                    postSearchData.setLocationList(jobLocSet);
                    if(!experienceAutoComplete.getText().toString().isEmpty() &&
                            experienceAutoComplete.getText().toString().indexOf(' ') > 0){
                        String expVal=experienceAutoComplete.getText().toString();
                        String expLimit=expVal.substring(0,expVal.indexOf(' '));
                        postSearchData.setExpLimit(expLimit);
                    }else{
                        postSearchData.setExpLimit(null);
                    }
                    if(!salaryAutoComplete.getText().toString().isEmpty() &&
                            salaryAutoComplete.getText().toString().indexOf(' ') > 0){
                        String salVal=salaryAutoComplete.getText().toString();
                        salVal=salVal.replace("+","");
                        String salLimit=salVal.substring(0,salVal.indexOf(' '));
                        Integer salSelected=Integer.valueOf(salLimit)*100000;
                        postSearchData.setSalLimit(salSelected.toString());
                    }else{
                        postSearchData.setSalLimit(null);
                    }
                    try {
                        DB snappydb = DBFactory.open(getApplicationContext());
                        snappydb.put(DBKeys.LAST_SEARCH,postSearchData);
                        snappydb.close();
                    }catch(SnappydbException ex){
                        Snackbar.make(view,"Something went worng!",Snackbar.LENGTH_SHORT).show();
                    }
                    Intent intent = new Intent(SearchViewActivity.this, InterviewListActivity.class);
                    intent.putExtra("REST_URL_HINT", Constants.JOB_SEARCH);
                    startActivity(intent);
                }
            }
        });

        recentSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SearchViewActivity.this, InterviewListActivity.class);
                intent.putExtra("REST_URL_HINT", Constants.JOB_SEARCH);
                startActivity(intent);
            }
        });
    }

    private void fetchKeyWordsList() {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<Keywords>> searchKeywords = apiService.getSearchKeywords();
        searchKeywords.enqueue(new Callback<List<Keywords>>() {
            @Override
            public void onResponse(Call<List<Keywords>> call, Response<List<Keywords>> response) {
                Set<String> keywords=new HashSet<>();
                keywordsList=response.body();
                for(Keywords keyword:keywordsList){
                    keywords.add(keyword.getDesignation());
                    keywords.addAll(Arrays.asList(StringUtils.split(keyword.getSkills(),",")));
                }
                String[] keywordNames = new String[keywords.size()];
                int counter=0;
                for(String name:keywords){
                    keywordNames[counter]=name;
                    counter++;
                }
                ArrayAdapter<String> keywordsAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, keywordNames);
                jobKeywordsText.setAdapter(keywordsAdapter);
                jobKeywordsText.setThreshold(1);
                jobKeywordsText.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
            }

            @Override
            public void onFailure(Call<List<Keywords>> call, Throwable t) {
                t.printStackTrace();
                Log.d("SearchViewKewords",t.getMessage());
            }
        });
    }

    private void fetchLocationList() {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<Location>> locationBasedInterviews = apiService.getLocationBasedInterviews();
        locationBasedInterviews.enqueue(new Callback<List<Location>>() {
            @Override
            public void onResponse(Call<List<Location>> call, Response<List<Location>> response) {
                locationList=response.body();
                String[] LocationNames = new String[locationList.size()];
                int counter=0;
                for(Location location:locationList){
                    LocationNames[counter]=location.getLocationName();
                    counter++;
                }
                ArrayAdapter<String> location = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, LocationNames);
                jobLocationText.setAdapter(location);
                jobLocationText.setThreshold(1);
                jobLocationText.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
            }
            @Override
            public void onFailure(Call<List<Location>> call, Throwable t) {
                t.printStackTrace();
                Log.d("SearchViewLocationList",t.getMessage());
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

    @Override
    public void onRestart()
    {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }

}
