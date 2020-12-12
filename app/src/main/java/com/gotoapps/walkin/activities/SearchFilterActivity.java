package com.gotoapps.walkin.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.gotoapps.walkin.R;
import com.gotoapps.walkin.db.DBKeys;
import com.gotoapps.walkin.fragment.CategoryFragment;
import com.gotoapps.walkin.fragment.EducationFragment;
import com.gotoapps.walkin.fragment.ExperienceFragment;
import com.gotoapps.walkin.fragment.FreshnessFragment;
import com.gotoapps.walkin.fragment.LocationFragment;
import com.gotoapps.walkin.fragment.RoleFragment;
import com.gotoapps.walkin.fragment.SalaryFragment;
import com.gotoapps.walkin.fragment.WorkModeFragment;
import com.gotoapps.walkin.model.FilterMainElement;
import com.gotoapps.walkin.response.FilterRestResponse;
import com.snappydb.DB;
import com.snappydb.DBFactory;
import com.snappydb.SnappydbException;

import java.util.ArrayList;
import java.util.List;

public class SearchFilterActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Button applyFilter;

    private LocationFragment locationFragment;
    private SalaryFragment salaryFragment;
    private FreshnessFragment freshnessFragment;
    private RoleFragment roleFragment;
    private WorkModeFragment workModeFragment;
    private ExperienceFragment experienceFragment;
    private EducationFragment educationFragment;
    private CategoryFragment categoryFragment;

    private List<FilterMainElement> filterMainElements;
    private FilterRestResponse filterRestResponses;
    private FilterRestResponse filterRestResponsesSelectedValues=new FilterRestResponse();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_filter);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Advanced Filter");

        locationFragment=new LocationFragment();
        salaryFragment=new SalaryFragment();
        freshnessFragment=new FreshnessFragment();
        roleFragment=new RoleFragment();
        workModeFragment=new WorkModeFragment();
        experienceFragment=new ExperienceFragment();
        educationFragment=new EducationFragment();
        categoryFragment=new CategoryFragment();

        filterMainElements = new ArrayList<>();
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();

        applyFilter=(Button)findViewById(R.id.filter);
        applyFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(locationFragment!=null){
                    filterRestResponsesSelectedValues.setLocation(locationFragment.getAllElements());
                }
                if(salaryFragment!=null){
                    filterRestResponsesSelectedValues.setSalary(salaryFragment.getAllElements());
                }
                if(freshnessFragment!=null){
                    filterRestResponsesSelectedValues.setFreshness(freshnessFragment.getAllElements());
                }
                if(roleFragment!=null){
                    filterRestResponsesSelectedValues.setRole(roleFragment.getAllElements());
                }
                if(workModeFragment!=null){
                    filterRestResponsesSelectedValues.setWorkmode(workModeFragment.getAllElements());
                }
                if(experienceFragment!=null){
                    filterRestResponsesSelectedValues.setWorkmode(experienceFragment.getAllElements());
                }
                if(educationFragment!=null){
                    filterRestResponsesSelectedValues.setWorkmode(educationFragment.getAllElements());
                }
                if(categoryFragment!=null){
                    filterRestResponsesSelectedValues.setWorkmode(categoryFragment.getAllElements());
                }
                System.out.println("========================="+filterRestResponses);
             }

        });
    }

    private void setupTabIcons() {
        int[] tabIcons = {
                R.drawable.ic_filter_salary,
                R.drawable.ic_filter_location,
                R.drawable.ic_filter_freshness,
                R.drawable.ic_filter_role,
                R.drawable.ic_filter_education,
                R.drawable.ic_filter_work_mode,
                R.drawable.ic_filter_exp,
                R.drawable.ic_filter_experience};
        for (int i=0; i < tabLayout.getTabCount(); i++)
        {
            if(tabLayout.getTabAt(i).getText().equals("Salary")){
                tabLayout.getTabAt(i).setIcon(tabIcons[0]);
            }else  if(tabLayout.getTabAt(i).getText().equals("Location")) {
                tabLayout.getTabAt(i).setIcon(tabIcons[1]);
            }else  if(tabLayout.getTabAt(i).getText().equals("Freshness")) {
                tabLayout.getTabAt(i).setIcon(tabIcons[2]);
            }else  if(tabLayout.getTabAt(i).getText().equals("Role")) {
                tabLayout.getTabAt(i).setIcon(tabIcons[3]);
            }else  if(tabLayout.getTabAt(i).getText().equals("Work Mode")) {
                tabLayout.getTabAt(i).setIcon(tabIcons[5]);
            }else  if(tabLayout.getTabAt(i).getText().equals("Experience")) {
                tabLayout.getTabAt(i).setIcon(tabIcons[6]);
            }else  if(tabLayout.getTabAt(i).getText().equals("Education")) {
                tabLayout.getTabAt(i).setIcon(tabIcons[4]);
            }else  if(tabLayout.getTabAt(i).getText().equals("Category")) {
                tabLayout.getTabAt(i).setIcon(tabIcons[7]);
            }
        }
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        try {
            DB snappydb = DBFactory.open(getApplicationContext());
            filterRestResponses= snappydb.get(DBKeys.JOB_FILTER,FilterRestResponse.class);
            snappydb.close();
        }catch(SnappydbException ex){
            ex.printStackTrace();
        }

        Bundle filterBundle = new Bundle();
        filterBundle.putParcelable("Filter",filterRestResponses);

        if(filterRestResponses.getSalary().size()>0){
            adapter.addFragment(salaryFragment, "Salary",filterBundle);
        }
        if(filterRestResponses.getLocation().size()>0){
            adapter.addFragment(locationFragment, "Location",filterBundle);
        }
        if(filterRestResponses.getFreshness().size()>0){
            adapter.addFragment(freshnessFragment, "Freshness",filterBundle);
        }
        if(filterRestResponses.getRole().size()>0){
            adapter.addFragment(roleFragment, "Role",filterBundle);
        }
        if(filterRestResponses.getWorkmode().size()>0){
            adapter.addFragment(workModeFragment, "Work Mode",filterBundle);
        }
        if(filterRestResponses.getExperience().size()>0){
            adapter.addFragment(experienceFragment, "Experience",filterBundle);
        }
        if(filterRestResponses.getEducation().size()>0){
            adapter.addFragment(educationFragment, "Education",filterBundle);
        }
        if(filterRestResponses.getCategory().size()>0){
            adapter.addFragment(categoryFragment, "Category",filterBundle);
        }
        viewPager.setOffscreenPageLimit(8);
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title, Bundle args) {
            fragment.setArguments(args);
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
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
}
