package com.gotoapps.walkin.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gotoapps.walkin.R;
import com.gotoapps.walkin.activities.FeedbackActivity;
import com.gotoapps.walkin.activities.InternetConnectionNotFoundActivity;
import com.gotoapps.walkin.activities.WalkInWebViewActivity;
import com.gotoapps.walkin.model.About;
import com.gotoapps.walkin.utils.Constants;
import com.gotoapps.walkin.utils.NetworkConnectionStatusNotificationUtil;

import java.util.List;

public class AboutUsAdapter extends RecyclerView.Adapter<AboutUsAdapter.AboutUsViewHolder>{
    private List<About> aboutUsList;
    private Context mContext;
    private final static String APP_PNAME = "com.gotoapps.walkin";// Package Name

    public class AboutUsViewHolder extends RecyclerView.ViewHolder {
        public TextView aboutUsTitle;
        public ImageView aboutUsLogoImage, getAboutUsLogoArrow;
        public String contentURL;
        public AboutUsViewHolder(View view) {
            super(view);
            aboutUsTitle = (TextView) view.findViewById(R.id.textView);
            aboutUsLogoImage=(ImageView)view.findViewById(R.id.imageView);
            getAboutUsLogoArrow=(ImageView)view.findViewById(R.id.imageView1);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(NetworkConnectionStatusNotificationUtil.checkConnectionStatus()) {
                        if(aboutUsTitle.getText().toString().contains("FAQ")) {
                            Uri uriUrl = Uri.parse(Constants.FAQ_URL);
                            Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
                            launchBrowser.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            mContext.startActivity(launchBrowser);
                        }
                        if(aboutUsTitle.getText().toString().contains("Privacy")) {
                            Uri uriUrl = Uri.parse(Constants.PRIVACY_POLICY_URL);
                            Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
                            launchBrowser.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            mContext.startActivity(launchBrowser);
                        }
                        if(aboutUsTitle.getText().toString().contains("Feedback")) {
                            Intent intent = new Intent(mContext, FeedbackActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            mContext.startActivity(intent);
                        }
                        if(aboutUsTitle.getText().toString().contains("Facebook")){
                            try {
                                mContext.getPackageManager().getPackageInfo("com.facebook.katana", 0);
                                Intent intent =new Intent(Intent.ACTION_VIEW, Uri.parse("fb://page/273151413400988"));
                                mContext.startActivity(intent);
                            } catch (Exception e) {
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/walkinmobileapp/?modal=admin_todo_tour"));
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                mContext.startActivity(intent);
                            }
                        }
                        if(aboutUsTitle.getText().toString().contains("YouTube")){
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=bzSTpdcs-EI"));
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.setPackage("com.google.android.youtube");
                            mContext.startActivity(intent);
                        }
                        if(aboutUsTitle.getText().toString().contains("PlayStore")){
                            Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + APP_PNAME));
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            mContext.startActivity(intent);
                        }
                    }else{
                        Intent intent = new Intent(mContext, InternetConnectionNotFoundActivity.class);
                        mContext.startActivity(intent);
                    }
                }
            });
        }
    }
    public AboutUsAdapter(Context context,List<About> aboutUsList) {
        this.aboutUsList = aboutUsList;
        this.mContext = context;
    }

    @Override
    public AboutUsAdapter.AboutUsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.about_us_list_row, parent, false);
        return new AboutUsAdapter.AboutUsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AboutUsAdapter.AboutUsViewHolder holder, int position) {
        About about = aboutUsList.get(position);
        holder.aboutUsTitle.setText(about.getTitle());
        holder.aboutUsLogoImage.setImageResource(about.getLogoIamgeName());
        holder.contentURL=about.getContentURL().trim();
        if(holder.aboutUsTitle.getText().toString().contains("Facebook")) {
            holder.getAboutUsLogoArrow.setImageResource(0);
            DrawableCompat.setTint(holder.aboutUsLogoImage.getDrawable(), ContextCompat.getColor(mContext, R.color.about_facebook_color));
        }
        if(holder.aboutUsTitle.getText().toString().contains("YouTube")) {
            holder.getAboutUsLogoArrow.setImageResource(0);
            DrawableCompat.setTint(holder.aboutUsLogoImage.getDrawable(), ContextCompat.getColor(mContext, R.color.about_youtube_color));
        }
        if(holder.aboutUsTitle.getText().toString().contains("PlayStore")) {
            holder.getAboutUsLogoArrow.setImageResource(0);
            DrawableCompat.setTint(holder.aboutUsLogoImage.getDrawable(), ContextCompat.getColor(mContext, R.color.about_play_store_color));
        }
        if(holder.aboutUsTitle.getText().toString().contains("App Version")) {
            holder.getAboutUsLogoArrow.setImageResource(0);
        }else{
            holder.getAboutUsLogoArrow.setImageResource(about.getArrowImageName());
        }
    }

    @Override
    public int getItemCount() {
        return aboutUsList.size();
    }

}