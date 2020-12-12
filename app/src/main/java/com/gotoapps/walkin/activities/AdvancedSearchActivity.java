package com.gotoapps.walkin.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;

import com.gotoapps.walkin.R;
import com.gotoapps.walkin.utils.SharedPreferenceManager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


public class AdvancedSearchActivity extends AppCompatActivity {

    CheckBox offCampus,bankJobs,itJobs,itesJobs,coreJobs,otherJobs,govtJobs,allCategoryJobs;
    CheckBox beMe,bcomMcom,btechMtech,bbaMba,bscMsc,bedMed,bcaMca,barchMarch,otherDegrees,allDegrees;
    CheckBox andraPradesh,nagaland,arunachalPradesh,odisha,assam,punjab,bihar,rajasthan,chhattisgarh,sikkim,goa,tamilNadu,
             gujarat,teleangana,haryana,tripura,himachalPradesh,uttarkhand,jammuKashmir,uttarPradesh,jharkhand,westBengal,
             karnataka,andaman,kerala,chandigarh,madyaPradesh,dadraNagarHaveli,maharastra,damanDiu,manipur,delhi,meghalaya,
            lakshadweep,mizoram,puduchery,otherLocations,allLocations;

    Set<String> jobCategoryList =  new HashSet<String>();
    Set<String> jobQualificationList =  new HashSet<String>();
    Set<String> jobLocationList =  new HashSet<String>();

    ArrayList<CheckBox> jobCategoryCheckBoxesList= new ArrayList<>();
    ArrayList<CheckBox> jobQualificationCheckBoxesList  = new ArrayList<>();
    ArrayList<CheckBox> jobLocationCheckBoxesList  = new ArrayList<>();

    // Session Manager Class
    SharedPreferenceManager preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adv_search);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Advanced Search");

        //Job category
        offCampus= (CheckBox) findViewById(R.id.checkBox);
        bankJobs= (CheckBox) findViewById(R.id.checkBox12);
        itJobs= (CheckBox) findViewById(R.id.checkBox1);
        itesJobs= (CheckBox) findViewById(R.id.checkBox11);
        coreJobs= (CheckBox) findViewById(R.id.checkBox2);
        otherJobs= (CheckBox) findViewById(R.id.checkBox21);
        govtJobs= (CheckBox) findViewById(R.id.checkBox3);
        allCategoryJobs= (CheckBox) findViewById(R.id.checkBox31);

        jobCategoryCheckBoxesList.add(offCampus);
        jobCategoryCheckBoxesList.add(bankJobs);
        jobCategoryCheckBoxesList.add(itJobs);
        jobCategoryCheckBoxesList.add(itesJobs);
        jobCategoryCheckBoxesList.add(coreJobs);
        jobCategoryCheckBoxesList.add(otherJobs);
        jobCategoryCheckBoxesList.add(govtJobs);
        jobCategoryCheckBoxesList.add(allCategoryJobs);

        //Job Qualification
        beMe= (CheckBox) findViewById(R.id.checkBox51);
        bcomMcom= (CheckBox) findViewById(R.id.checkBox55);
        btechMtech= (CheckBox) findViewById(R.id.checkBox52);
        bbaMba= (CheckBox) findViewById(R.id.checkBox56);
        bscMsc= (CheckBox) findViewById(R.id.checkBox53);
        bedMed= (CheckBox) findViewById(R.id.checkBox57);
        bcaMca= (CheckBox) findViewById(R.id.checkBox54);
        barchMarch= (CheckBox) findViewById(R.id.checkBox58);
        otherDegrees= (CheckBox) findViewById(R.id.checkBox59);
        allDegrees= (CheckBox) findViewById(R.id.checkBox60);

        jobQualificationCheckBoxesList.add(beMe);
        jobQualificationCheckBoxesList.add(bcomMcom);
        jobQualificationCheckBoxesList.add(btechMtech);
        jobQualificationCheckBoxesList.add(bbaMba);
        jobQualificationCheckBoxesList.add(bscMsc);
        jobQualificationCheckBoxesList.add(bedMed);
        jobQualificationCheckBoxesList.add(bcaMca);
        jobQualificationCheckBoxesList.add(barchMarch);
        jobQualificationCheckBoxesList.add(otherDegrees);
        jobQualificationCheckBoxesList.add(allDegrees);

        //Job Locations
        andraPradesh =(CheckBox) findViewById(R.id.checkBox511);
        nagaland =(CheckBox) findViewById(R.id.checkBox531);
        arunachalPradesh =(CheckBox) findViewById(R.id.checkBox512);
        odisha =(CheckBox) findViewById(R.id.checkBox532);
        assam =(CheckBox) findViewById(R.id.checkBox513);
        punjab =(CheckBox) findViewById(R.id.checkBox533);
        bihar =(CheckBox) findViewById(R.id.checkBox514);
        rajasthan =(CheckBox) findViewById(R.id.checkBox534);
        chhattisgarh =(CheckBox) findViewById(R.id.checkBox515);
        sikkim =(CheckBox) findViewById(R.id.checkBox535);
        goa =(CheckBox) findViewById(R.id.checkBox516);
        tamilNadu =(CheckBox) findViewById(R.id.checkBox536);
        gujarat =(CheckBox) findViewById(R.id.checkBox517);
        teleangana =(CheckBox) findViewById(R.id.checkBox537);
        haryana =(CheckBox) findViewById(R.id.checkBox518);
        tripura =(CheckBox) findViewById(R.id.checkBox538);
        himachalPradesh =(CheckBox) findViewById(R.id.checkBox519);
        uttarkhand =(CheckBox) findViewById(R.id.checkBox539);
        jammuKashmir =(CheckBox) findViewById(R.id.checkBox520);
        uttarPradesh =(CheckBox) findViewById(R.id.checkBox540);
        jharkhand =(CheckBox) findViewById(R.id.checkBox521);
        westBengal =(CheckBox) findViewById(R.id.checkBox541);
        karnataka =(CheckBox) findViewById(R.id.checkBox522);
        andaman =(CheckBox) findViewById(R.id.checkBox542);
        kerala =(CheckBox) findViewById(R.id.checkBox523);
        chandigarh =(CheckBox) findViewById(R.id.checkBox543);
        madyaPradesh =(CheckBox) findViewById(R.id.checkBox524);
        dadraNagarHaveli =(CheckBox) findViewById(R.id.checkBox544);
        maharastra =(CheckBox) findViewById(R.id.checkBox525);
        damanDiu =(CheckBox) findViewById(R.id.checkBox545);
        manipur =(CheckBox) findViewById(R.id.checkBox526);
        delhi =(CheckBox) findViewById(R.id.checkBox546);
        meghalaya =(CheckBox) findViewById(R.id.checkBox527);
        lakshadweep =(CheckBox) findViewById(R.id.checkBox547);
        mizoram =(CheckBox) findViewById(R.id.checkBox528);
        puduchery =(CheckBox) findViewById(R.id.checkBox548);
        otherLocations =(CheckBox) findViewById(R.id.checkBox529);
        allLocations =(CheckBox) findViewById(R.id.checkBox549);

        jobLocationCheckBoxesList.add(andraPradesh);
        jobLocationCheckBoxesList.add(nagaland);
        jobLocationCheckBoxesList.add(arunachalPradesh);
        jobLocationCheckBoxesList.add(odisha);
        jobLocationCheckBoxesList.add(assam);
        jobLocationCheckBoxesList.add(punjab);
        jobLocationCheckBoxesList.add(bihar);
        jobLocationCheckBoxesList.add(rajasthan);
        jobLocationCheckBoxesList.add(chhattisgarh);
        jobLocationCheckBoxesList.add(sikkim);
        jobLocationCheckBoxesList.add(goa);
        jobLocationCheckBoxesList.add(tamilNadu);
        jobLocationCheckBoxesList.add(gujarat);
        jobLocationCheckBoxesList.add(teleangana);
        jobLocationCheckBoxesList.add(haryana);
        jobLocationCheckBoxesList.add(tripura);
        jobLocationCheckBoxesList.add(himachalPradesh);
        jobLocationCheckBoxesList.add(uttarkhand);
        jobLocationCheckBoxesList.add(jammuKashmir);
        jobLocationCheckBoxesList.add(uttarPradesh);
        jobLocationCheckBoxesList.add(jharkhand);
        jobLocationCheckBoxesList.add(westBengal);
        jobLocationCheckBoxesList.add(karnataka);
        jobLocationCheckBoxesList.add(andaman);
        jobLocationCheckBoxesList.add(kerala);
        jobLocationCheckBoxesList.add(chandigarh);
        jobLocationCheckBoxesList.add(madyaPradesh);
        jobLocationCheckBoxesList.add(dadraNagarHaveli);
        jobLocationCheckBoxesList.add(maharastra);
        jobLocationCheckBoxesList.add(damanDiu);
        jobLocationCheckBoxesList.add(manipur);
        jobLocationCheckBoxesList.add(delhi);
        jobLocationCheckBoxesList.add(meghalaya);
        jobLocationCheckBoxesList.add(lakshadweep);
        jobLocationCheckBoxesList.add(mizoram);
        jobLocationCheckBoxesList.add(puduchery);
        jobLocationCheckBoxesList.add(otherLocations);
        jobLocationCheckBoxesList.add(allLocations);

        // Session class instance
        preferences = new SharedPreferenceManager(getApplicationContext());
        jobCategoryList = preferences.getSelectedStringsSets(SharedPreferenceManager.PREF_JOB_CATEGORY);
        jobQualificationList = preferences.getSelectedStringsSets(SharedPreferenceManager.PREF_JOB_QUALIFICATION);
        jobLocationList = preferences.getSelectedStringsSets(SharedPreferenceManager.PREF_JOB_LOCATION);

        if(jobCategoryList!=null && jobCategoryList.size() >0){
            for (CheckBox categoryCheckBox : jobCategoryCheckBoxesList){
                if(jobCategoryList.contains(categoryCheckBox.getText().toString())){
                    categoryCheckBox.setChecked(true);
                }
            }
        }

        if(jobQualificationList !=null && jobQualificationList.size() >0){
            for (CheckBox qualificationCheckBox : jobQualificationCheckBoxesList){
                if(jobQualificationList.contains(qualificationCheckBox.getText().toString())){
                    qualificationCheckBox.setChecked(true);
                }
            }
        }

        if(jobLocationList !=null && jobLocationList.size() >0){
            for (CheckBox locationCheckBox : jobLocationCheckBoxesList){
                if(jobLocationList.contains(locationCheckBox.getText().toString())){
                    locationCheckBox.setChecked(true);
                }
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_preference, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.menu_done:
                //check the values for the job category
                if(jobCategoryList==null){
                    jobCategoryList =  new HashSet<String>();
                }
                for (CheckBox categoryCheckBox : jobCategoryCheckBoxesList){
                    if(categoryCheckBox.isChecked()) {
                        jobCategoryList.add(categoryCheckBox.getText().toString());
                    }
                    if(!categoryCheckBox.isChecked()) {
                        jobCategoryList.remove(categoryCheckBox.getText().toString());
                    }
                }
                if(jobQualificationList==null){
                    jobQualificationList =  new HashSet<String>();
                }
                //check the values for the job Qualification
                for (CheckBox qualificationCheckBox : jobQualificationCheckBoxesList){
                    if(qualificationCheckBox.isChecked()) {
                        jobQualificationList.add(qualificationCheckBox.getText().toString());
                    }
                    if(!qualificationCheckBox.isChecked()) {
                        jobQualificationList.remove(qualificationCheckBox.getText().toString());
                    }
                }
                if(jobLocationList==null){
                    jobLocationList =  new HashSet<String>();
                }
                //check the values for the job Location
                for (CheckBox locationCheckBox : jobLocationCheckBoxesList){
                    if(locationCheckBox.isChecked()) {
                        jobLocationList.add(locationCheckBox.getText().toString());
                    }
                    if(!locationCheckBox.isChecked()) {
                        jobLocationList.remove(locationCheckBox.getText().toString());
                    }
                }
                preferences = new SharedPreferenceManager(getApplicationContext());
                preferences.saveSelectedStringsSets(SharedPreferenceManager.PREF_JOB_CATEGORY,jobCategoryList);
                preferences.saveSelectedStringsSets(SharedPreferenceManager.PREF_JOB_QUALIFICATION,jobQualificationList);
                preferences.saveSelectedStringsSets(SharedPreferenceManager.PREF_JOB_LOCATION,jobLocationList);
                return true;
            case R.id.menu_clear:
                for (CheckBox categoryCheckBox : jobCategoryCheckBoxesList){
                    categoryCheckBox.setChecked(false);
                }
                for (CheckBox qualificationCheckBox : jobQualificationCheckBoxesList) {
                    qualificationCheckBox.setChecked(false);
                }
                for (CheckBox locationCheckBox : jobLocationCheckBoxesList) {
                    locationCheckBox.setChecked(false);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onCheckboxClicked(View view) {
        boolean checked = ((CheckBox) view).isChecked();
        switch(view.getId()) {
            case R.id.checkBox31:
                if(checked){
                    for (CheckBox categoryCheckBox : jobCategoryCheckBoxesList){
                            categoryCheckBox.setChecked(true);
                    }
                }else{
                    if(jobCategoryList!=null && jobCategoryList.size() >0){
                        if(jobCategoryList.size() == jobCategoryCheckBoxesList.size()) {
                            for (CheckBox categoryCheckBox : jobCategoryCheckBoxesList) {
                                    categoryCheckBox.setChecked(false);
                            }
                        }else{
                            for (CheckBox categoryCheckBox : jobCategoryCheckBoxesList) {
                                if (jobCategoryList.contains(categoryCheckBox.getText().toString())) {
                                    categoryCheckBox.setChecked(true);
                                } else {
                                    categoryCheckBox.setChecked(false);
                                }
                            }
                        }

                    }else{
                        for (CheckBox categoryCheckBox : jobCategoryCheckBoxesList){
                                categoryCheckBox.setChecked(false);
                        }
                    }
                }
                break;
            case R.id.checkBox60:
                if(checked) {
                    for (CheckBox qualificationCheckBox : jobQualificationCheckBoxesList) {
                        qualificationCheckBox.setChecked(true);
                    }
                }else{
                    if(jobQualificationList !=null && jobQualificationList.size() >0){
                        if(jobQualificationList.size() == jobQualificationCheckBoxesList.size()) {
                            for (CheckBox categoryCheckBox : jobQualificationCheckBoxesList) {
                                categoryCheckBox.setChecked(false);
                            }
                        }else{
                            for (CheckBox categoryCheckBox : jobQualificationCheckBoxesList) {
                                if (jobQualificationList.contains(categoryCheckBox.getText().toString())) {
                                    categoryCheckBox.setChecked(true);
                                } else {
                                    categoryCheckBox.setChecked(false);
                                }
                            }
                        }

                    }else{
                        for (CheckBox qualificationCheckBox : jobQualificationCheckBoxesList){
                                qualificationCheckBox.setChecked(false);
                        }
                    }
                }
                break;
            case R.id.checkBox549:
                if(checked) {
                    for (CheckBox locationCheckBox : jobLocationCheckBoxesList) {
                        locationCheckBox.setChecked(true);
                    }
                }else{
                    if(jobLocationList !=null && jobLocationList.size() >0){
                        if(jobLocationList.size() == jobLocationCheckBoxesList.size()) {
                            for (CheckBox categoryCheckBox : jobLocationCheckBoxesList) {
                                categoryCheckBox.setChecked(false);
                            }
                        }else{
                            for (CheckBox categoryCheckBox : jobLocationCheckBoxesList) {
                                if (jobLocationList.contains(categoryCheckBox.getText().toString())) {
                                    categoryCheckBox.setChecked(true);
                                } else {
                                    categoryCheckBox.setChecked(false);
                                }
                            }
                        }
                    }else{
                        for (CheckBox locationCheckBox : jobLocationCheckBoxesList){
                                locationCheckBox.setChecked(false);
                        }
                    }
                }
                break;
        }
    }
}
