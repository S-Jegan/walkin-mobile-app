package com.gotoapps.walkin.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.gotoapps.walkin.MainActivity;
import com.gotoapps.walkin.R;
import com.gotoapps.walkin.adapters.AboutUsAdapter;
import com.gotoapps.walkin.model.About;

import java.util.ArrayList;
import java.util.List;

public class AboutWalkInActivity extends AppCompatActivity {
    private List<About> aboutUsList = new ArrayList<>();
    private RecyclerView recyclerView;
    private AboutUsAdapter aboutUsAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("About WalkIn");
        setContentView(R.layout.activity_about_walk_in);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        aboutUsAdapter = new AboutUsAdapter(getApplicationContext(),aboutUsList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(aboutUsAdapter);
        prepareAboutUsData();
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

    private void prepareAboutUsData() {
        About appVersion=new About("App Version 1.0",R.drawable.ic_loyalty_black_24dp,R.drawable.ic_navigate_next_black_24dp,"test");
        aboutUsList.add(appVersion);

        About faq=new About("FAQ's",R.drawable.ic_live_help_black_24dp,R.drawable.ic_navigate_next_black_24dp,"http://gotoapps.in/static-contents/faq.html");
        aboutUsList.add(faq);

        About privacyPolicy=new About("Privacy Policy",R.drawable.ic_enhanced_encryption_black_24dp,R.drawable.ic_navigate_next_black_24dp,"http://gotoapps.in/static-contents/privacy-policy.html");
        aboutUsList.add(privacyPolicy);

        About feedback=new About("Feedback",R.drawable.ic_feedback_black_24dp,R.drawable.ic_navigate_next_black_24dp,"");
        aboutUsList.add(feedback);

        About fb=new About("Like us on Facebook",R.drawable.about_icon_facebook,R.drawable.ic_navigate_next_black_24dp,"");
        aboutUsList.add(fb);

        //About youTube=new About("Watch us on YouTube",R.drawable.about_icon_youtube,R.drawable.ic_navigate_next_black_24dp,"");
        //aboutUsList.add(youTube);

        About playStore=new About("Rate us on PlayStore",R.drawable.about_icon_google_play,R.drawable.ic_navigate_next_black_24dp,"");
        aboutUsList.add(playStore);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
