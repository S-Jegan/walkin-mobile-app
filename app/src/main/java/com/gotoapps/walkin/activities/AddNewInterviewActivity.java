package com.gotoapps.walkin.activities;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.gotoapps.walkin.R;
import com.gotoapps.walkin.mailservice.Config;
import com.gotoapps.walkin.mailservice.SendMail;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddNewInterviewActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText designation, qualification,experience, date, jobDesc;
    private EditText company,location,email,phone,url;
    private Button submitButton;
    private String postedJob;
    final Calendar myCalendar = Calendar.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_interview);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Post New Job");

        designation=  findViewById(R.id.editTextDesignation);
        qualification=  findViewById(R.id.editTextQualification);
        experience=  findViewById(R.id.editTextExperience);
        date=  findViewById(R.id.editTextInterviewDate);
        jobDesc=  findViewById(R.id.editTextJobDesc);
        company=  findViewById(R.id.editTextInterviewCompanyName);
        location=  findViewById(R.id.editTextInterviewLocation);
        email=  findViewById(R.id.editTextEmail);
        phone=  findViewById(R.id.editTextInterviewPhone);
        url=  findViewById(R.id.editTextJobURL);
        submitButton=  findViewById(R.id.buttonSubmit);
        submitButton.setOnClickListener(this);

        final DatePickerDialog.OnDateSetListener datePicker = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(AddNewInterviewActivity.this, datePicker, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
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
    public void onClick(View v) {
        boolean isInputValidated=true;
        if(TextUtils.isEmpty(designation.getText().toString())) {
            designation.setError("Please Add Designation");
            isInputValidated=false;
        }if(TextUtils.isEmpty(qualification.getText().toString())) {
            qualification.setError("Please Add Qualification");
            isInputValidated=false;
        }if(TextUtils.isEmpty(date.getText().toString())) {
            date.setError("Please Add Interview Date");
            isInputValidated=false;
        }if(TextUtils.isEmpty(company.getText().toString())) {
            company.setError("Please Add Company Name");
            isInputValidated=false;
        }if(TextUtils.isEmpty(location.getText().toString())) {
            location.setError("Please Add Location");
            isInputValidated=false;
        }if(TextUtils.isEmpty(email.getText().toString())) {
            email.setError("Please Add Email");
            isInputValidated=false;
        }if(TextUtils.isEmpty(phone.getText().toString())) {
            phone.setError("Please Add Phone Numer");
            isInputValidated=false;
        }
        if(isInputValidated) {
            postedJob = "Designation : " + designation.getText().toString() + "\n" +
                    "Qualification : " + qualification.getText().toString() + "\n" +
                    "Experience : " + experience.getText().toString() + "\n" +
                    "Interview Date : " + date.getText().toString() + "\n" +
                    "Job Description : " + jobDesc.getText().toString() + "\n" +
                    "Company Name : " + company.getText().toString() + "\n" +
                    "Interview Location : " + location.getText().toString() + "\n" +
                    "Email : " + email.getText().toString() + "\n" +
                    "Phone : " + phone.getText().toString() + "\n" +
                    "Job Site URL : " + url.getText().toString() + "\n" +
                    "Thanks !!! ";
            sendEmail(postedJob);
        }
    }

    private void sendEmail(@NonNull String postedJobText) {
        if(postedJobText.length()>0) {
            SendMail sm = new SendMail(this, Config.EMAIL, Config.NEW_JOB_SUBJECT, postedJobText, "Submitting Job Details");
            sm.execute();
        }else{
            Toast.makeText(getApplicationContext(),"Please Enter Missing Information",Toast.LENGTH_SHORT).show();
        }
    }

    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        date.setText(sdf.format(myCalendar.getTime()));
    }
}
