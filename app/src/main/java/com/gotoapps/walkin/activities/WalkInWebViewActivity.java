package com.gotoapps.walkin.activities;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.gotoapps.walkin.R;
import com.gotoapps.walkin.model.Location;
import com.gotoapps.walkin.model.Resume;
import com.gotoapps.walkin.restclient.ApiClient;
import com.gotoapps.walkin.restclient.ApiInterface;

import java.io.File;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WalkInWebViewActivity extends AppCompatActivity{

    private WebView walkInWebView;
    private String actionBarTitle,url,pdfFlag,downloadURL,fileName,resumeId;
    ProgressDialog prDialog;
    private static final int WRITE_REQUEST_CODE = 300;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walk_in_web_view);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            actionBarTitle=extras.get("ACTION_BAR_TITLE").toString();
            url = extras.get("URL").toString();
            pdfFlag=extras.get("PDF_FLAG").toString();
            downloadURL=extras.get("DOWNLOAD_URL").toString();
            fileName=extras.get("RESUME_TITLE").toString();
            resumeId=extras.get("RESUME_ID").toString();
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(actionBarTitle);
        final ProgressDialog pd = ProgressDialog.show(WalkInWebViewActivity.this, "Loading...",
                "Please wait", true);
        walkInWebView=  findViewById(R.id.webView);
        walkInWebView.getSettings().setJavaScriptEnabled(true);
        walkInWebView.getSettings().setPluginState(WebSettings.PluginState.ON);
        walkInWebView.getSettings().setSupportZoom(true);
        walkInWebView.getSettings().setBuiltInZoomControls(false);
        registerReceiver(onComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));

        if(pdfFlag.equalsIgnoreCase("Y")){
            url = "https://docs.google.com/gview?embedded=true&url=" + url;
        }

        walkInWebView.setWebViewClient(new WebViewClient(){
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Toast.makeText(getApplicationContext(), "Something went wrong!", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon)
            {
                pd.show();
            }
            @Override
            public void onPageFinished(WebView view, String url) {
                if (view.getTitle().equals("")){
                    view.reload();
                }else{
                    pd.dismiss();
                }
            }
        });
        walkInWebView.loadUrl(url);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.resume_download:
                DownloadManager.Request request = new DownloadManager.Request(Uri.parse(downloadURL));
                request.setDescription(fileName+downloadURL.substring(downloadURL.lastIndexOf('.') , downloadURL.length()));
                request.setTitle(fileName+downloadURL.substring(downloadURL.lastIndexOf('.'), downloadURL.length()));
                fileName=fileName+"_"+downloadURL.substring(downloadURL.lastIndexOf('/') + 1, downloadURL.length());
                request.setDestinationInExternalFilesDir(WalkInWebViewActivity.this, Environment.DIRECTORY_DOWNLOADS,fileName);
                request.allowScanningByMediaScanner();
                DownloadManager downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                downloadManager.enqueue(request);
                increaseDownloadCount(resumeId);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    BroadcastReceiver onComplete=new BroadcastReceiver() {
        public void onReceive(Context ctxt, Intent intent) {
            Snackbar snackbar = Snackbar.make(walkInWebView,actionBarTitle+" Downloaded Successfully.",Snackbar.LENGTH_INDEFINITE)
                    .setAction("Open Folder", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startActivity(new Intent(DownloadManager.ACTION_VIEW_DOWNLOADS));
                        }
                    });
            snackbar.show();
        }
    };

    private void increaseDownloadCount(String resumeId) {
        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<List<Resume>> addDownloadCount = apiService.addResumeDownloadCount(Integer.parseInt(resumeId));
        addDownloadCount.enqueue(new Callback<List<Resume>>() {
            @Override
            public void onResponse(Call<List<Resume>> call, Response<List<Resume>> response) {

            }
            @Override
            public void onFailure(Call<List<Resume>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Something went wrong",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_download_resume, menu);
        return true;
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        //Intent intent = new Intent(WalkInWebViewActivity.this, AboutWalkInActivity.class);
        //startActivity(intent);
    }

}
