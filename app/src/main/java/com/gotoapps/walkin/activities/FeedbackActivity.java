package com.gotoapps.walkin.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.gotoapps.walkin.R;
import com.gotoapps.walkin.mailservice.Config;
import com.gotoapps.walkin.mailservice.SendMail;

public class FeedbackActivity extends AppCompatActivity implements View.OnClickListener{

    EditText feedBackText;
    Button feedbackSendButton;
    //Progressdialog to show while sending email
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Send Feedback");
        feedBackText=findViewById(R.id.feedbackMessage);
        feedbackSendButton=findViewById(R.id.buttonSend);
        feedbackSendButton.setOnClickListener(this);
    }

    private void sendEmail() {
        String message = feedBackText.getText().toString().trim();
        if(message.length()>0) {
            SendMail sm = new SendMail(this, Config.EMAIL, Config.FEEDBACK_SUBJECT, message, "Submitting Feedback");
            sm.execute();
        }else{
            Toast.makeText(getApplicationContext(),"Please Enter Feedback",Toast.LENGTH_SHORT).show();
            feedBackText.setFocusable(true);
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
    @Override
    public void onClick(View v) {
        sendEmail();
    }
}
