package com.gotoapps.walkin.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.gotoapps.walkin.R;
import com.gotoapps.walkin.activities.InternetConnectionNotFoundActivity;
import com.gotoapps.walkin.activities.WalkinsActivity;
import com.gotoapps.walkin.model.InterviewJSON;
import com.gotoapps.walkin.utils.Constants;
import com.gotoapps.walkin.utils.NetworkConnectionStatusNotificationUtil;
import com.gotoapps.walkin.utils.SharedPreferenceManager;
import com.gotoapps.walkin.utils.VolleyImageLoader;
import com.like.LikeButton;
import com.like.OnLikeListener;

import java.util.ArrayList;
import java.util.Set;

public class RelatedJobsListDataAdapter extends RecyclerView.Adapter<RelatedJobsListDataAdapter.InterviewViewHolder>{

    private ArrayList<InterviewJSON> itemsList;
    private Context mContext;
    private ImageLoader mImageLoader;
    private SharedPreferenceManager sharedPreferenceManager;

    public RelatedJobsListDataAdapter(Context context, ArrayList<InterviewJSON> itemsList) {
        this.itemsList = itemsList;
        this.mContext = context;
    }

    @Override
    public InterviewViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_interview_small, null);
        InterviewViewHolder mh = new InterviewViewHolder(v);
        return mh;
    }

    @Override
    public void onBindViewHolder(InterviewViewHolder holder, int i) {
        InterviewJSON singleItem = itemsList.get(i);
        holder.companyName.setText(singleItem.getCompany().getName());
        holder.designation.setText(singleItem.getDesignation());
        holder.location.setText(singleItem.getLocation());
        mImageLoader = VolleyImageLoader.getInstance().getImageLoader();
        holder.companyLogo.setImageUrl(singleItem.getCompany().getLogo().replace("localhost", "192.168.43.113"), mImageLoader);

        //Added for Like Button
        sharedPreferenceManager = new SharedPreferenceManager(mContext);
        Set<String> favJobsList=sharedPreferenceManager.getSavedJobs(SharedPreferenceManager.FAVOURITE_JOBS);
        if(favJobsList!=null && favJobsList.contains(singleItem.getId().toString())){
            holder.favouriteJob.setLiked(true);
            holder.favouriteJob.setLikeDrawableRes(R.drawable.ic_favorite_black_24dp);
        }else{
            holder.favouriteJob.setLiked(false);
            holder.favouriteJob.setLikeDrawableRes(R.drawable.ic_favorite_border_black_24dp);
        }
    }

    @Override
    public int getItemCount() {
        return (null != itemsList ? itemsList.size() : 0);
    }

    public class InterviewViewHolder extends RecyclerView.ViewHolder {
        protected TextView companyName;
        protected TextView designation;
        protected TextView location;
        NetworkImageView companyLogo;
        LikeButton favouriteJob;

        public InterviewViewHolder(View view) {
            super(view);
            this.companyName =  view.findViewById(R.id.companyName);
            this.designation =  view.findViewById(R.id.designation);
            this.location =  view.findViewById(R.id.location);
            this.companyLogo =  view.findViewById(R.id.itemImage);
            this.favouriteJob = itemView.findViewById(R.id.likeButton1);
            sharedPreferenceManager = new SharedPreferenceManager(mContext);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(NetworkConnectionStatusNotificationUtil.checkConnectionStatus()) {
                        sharedPreferenceManager.saveStringSet(SharedPreferenceManager.VIEWED_JOBS,itemsList.get(getAdapterPosition()).getId());
                        Intent intent = new Intent(mContext, WalkinsActivity.class);
                        intent.putExtra(Constants.INTERVIEW_ID,itemsList.get(getAdapterPosition()).getId());
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(intent);
                        //((WalkinsActivity)mContext).finish();

                    }else{
                        Intent intent = new Intent(mContext, InternetConnectionNotFoundActivity.class);
                        mContext.startActivity(intent);
                    }
                }
            });

            favouriteJob.setOnLikeListener(new OnLikeListener() {
                @Override
                public void liked(LikeButton likeButton) {
                    sharedPreferenceManager.saveStringSet(SharedPreferenceManager.FAVOURITE_JOBS,itemsList.get(getAdapterPosition()).getId());
                    favouriteJob.setLiked(true);
                    favouriteJob.setLikeDrawableRes(R.drawable.ic_favorite_black_24dp);
                    Snackbar snackbar = Snackbar.make(favouriteJob,"Added as a Favourite Job.",Snackbar.LENGTH_SHORT);
                    snackbar.show();
                }
                @Override
                public void unLiked(LikeButton likeButton) {
                    sharedPreferenceManager.deleteFavouriteJob(SharedPreferenceManager.FAVOURITE_JOBS,itemsList.get(getAdapterPosition()).getId());
                    favouriteJob.setLiked(false);
                    favouriteJob.setLikeDrawableRes(R.drawable.ic_favorite_border_black_24dp);
                    Snackbar snackbar = Snackbar.make(favouriteJob,"Removed from Favourite Job.",Snackbar.LENGTH_SHORT);
                    snackbar.show();
                }
            });
        }
    }
}
