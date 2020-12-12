package com.gotoapps.walkin.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.gotoapps.walkin.R;
import com.gotoapps.walkin.activities.InternetConnectionNotFoundActivity;
import com.gotoapps.walkin.activities.WalkinsActivity;
import com.gotoapps.walkin.model.InterviewJSON;
import com.gotoapps.walkin.utils.Constants;
import com.gotoapps.walkin.utils.NetworkConnectionStatusNotificationUtil;
import com.gotoapps.walkin.utils.NumberFormatter;
import com.gotoapps.walkin.utils.SharedPreferenceManager;
import com.gotoapps.walkin.utils.TimeAgo;
import com.gotoapps.walkin.utils.VolleyImageLoader;
import com.like.LikeButton;
import com.like.OnLikeListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;

/**
 * Created by C2CSLS on 5/19/2018.
 */

public class InterviewListDataAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private List<InterviewJSON> dataList;
    private Context mContext;
    private LayoutInflater inflater;
    private ImageLoader mImageLoader;
    private SharedPreferenceManager sharedPreferenceManager;

    public InterviewListDataAdapter(Context context, List<InterviewJSON> dataList, RecyclerView recyclerView) {
        this.dataList = dataList;
        this.mContext = context;
        inflater= LayoutInflater.from(context);
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View myView = inflater.inflate(R.layout.card_interview_main,parent,false);
        InterviewListViewHolder mInterviewListViewHolder = new InterviewListViewHolder(myView);
        return mInterviewListViewHolder;

    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            InterviewListViewHolder mInterviewListViewHolder = (InterviewListViewHolder) holder;
            final InterviewJSON model = dataList.get(position);
            mInterviewListViewHolder.companyName.setText(model.getCompany().getName());
            mInterviewListViewHolder.designation.setText(model.getDesignation());
            mInterviewListViewHolder.location.setText(model.getLocation());
            mInterviewListViewHolder.qualification.setText(model.getQualification());
            try {
                String interviewDate=model.getInterviewStartDate().substring(0, 10);
                String interviewStarDate = model.getInterviewStartDate();
                String interviewEndDate = model.getInterviewEndDate();
                Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse(interviewStarDate);
                Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse(interviewEndDate);

                String startDay= (String) DateFormat.format("dd",   startDate); // 20
                String startMonth  = (String) DateFormat.format("MMM",  startDate); // Jun

                String endDay= (String) DateFormat.format("dd",   endDate); // 20
                String endMonth  = (String) DateFormat.format("MMM",  endDate); // Jun

                if(startDay.equals(endDay) && startMonth.equals(endMonth)){
                    interviewDate=startDay+"-"+startMonth;
                }
                else {
                    interviewDate=startDay+"-"+startMonth +" to "+ endDay+"-"+endMonth;
                }
                mInterviewListViewHolder.interviewDate.setText(interviewDate);

            }catch (ParseException e){
                Log.d("InterviewListAdapter","Error in Parsing InterviewDate");
            }

            //mInterviewListViewHolder.interviewDate.setText(model.getInterviewStartDate().substring(0, 10));
            //mInterviewListViewHolder.passedOut.setText("Batch: " + model.getPassedout());
            String exp=model.getExpStart() +" - "+model.getExpEnd()+" Years";
            if(model.getExpStart().equals(model.getExpEnd())){
                exp=model.getExpStart() +" Years";
            }
            mInterviewListViewHolder.experience.setText(exp);
            if(model.getNoOfViews()!=null) {
                mInterviewListViewHolder.noOfViews.setText(NumberFormatter.format(model.getNoOfViews()) + " views");
            }else{
                mInterviewListViewHolder.noOfViews.setText("");
            }
            if(model.getVerified()==1) {
                mInterviewListViewHolder.verfiedIcon.setImageResource(R.drawable.ic_verified_fkt);
            }else{
                mInterviewListViewHolder.verfiedIcon.setImageResource(android.R.color.transparent);
            }
            mImageLoader = VolleyImageLoader.getInstance().getImageLoader();
            mInterviewListViewHolder.companyLogo.setImageUrl(model.getCompany().getLogo().replace("localhost", "192.168.43.113"), mImageLoader);
        try {
            String patternString="yyyy-MM-dd hh:mm:ss";
            java.text.DateFormat indianFormat = new SimpleDateFormat(patternString);
            Date timestamp = indianFormat.parse(model.getCreatedAt());
            String output = TimeAgo.getTimeAgo(timestamp.getTime());
            mInterviewListViewHolder.timeAgo.setText(output);
        } catch(Exception e) {
           Log.d("Recycler Adapter","Error in Parsing Days ago logic");
        }
        //Added for Like Button
        sharedPreferenceManager = new SharedPreferenceManager(mContext);
        Set<String> favJobsList=sharedPreferenceManager.getSavedJobs(SharedPreferenceManager.FAVOURITE_JOBS);
        if(favJobsList!=null && favJobsList.contains(model.getId().toString())){
            mInterviewListViewHolder.favouriteJob.setLiked(true);
            mInterviewListViewHolder.favouriteJob.setLikeDrawableRes(R.drawable.ic_favorite_black_24dp);
        }else{
            mInterviewListViewHolder.favouriteJob.setLiked(false);
            mInterviewListViewHolder.favouriteJob.setLikeDrawableRes(R.drawable.ic_favorite_border_black_24dp);
        }
    }
    @Override
    public int getItemCount() {
        return  (dataList != null? dataList.size():0);
        // return dataList.size();
    }
    public void addInterviews(List<InterviewJSON> interviewJSONs){
        dataList.addAll(interviewJSONs);
        notifyDataSetChanged();
    }
    public void deleteInterviews(){
        dataList.clear();
        notifyDataSetChanged();
    }
    public void deleteInterview(int id){
        dataList.remove(id);
        notifyDataSetChanged();
    }

    class InterviewListViewHolder extends RecyclerView.ViewHolder{
        TextView companyName;
        TextView designation;
        TextView location;
        TextView qualification;
        TextView interviewDate;
        //TextView passedOut;
        TextView experience;
        TextView timeAgo;
        TextView noOfViews;
        TextView verifiedIndicator;
        LinearLayout myLinearLayout;
        LikeButton favouriteJob;
        ImageView designationIcon, locationIcon, qualificationIcon, passedOutIcon,experienceIcon, lastDateIcon,verfiedIcon,bookMark;
        NetworkImageView companyLogo;
        public InterviewListViewHolder(View itemView) {
            super(itemView);
            this.companyName =  itemView.findViewById(R.id.textView);
            this.designation =  itemView.findViewById(R.id.textView4);
            this.location =  itemView.findViewById(R.id.textView5);
            this.qualification =  itemView.findViewById(R.id.textView6);
            this.interviewDate =  itemView.findViewById(R.id.textView8);
            this.experience =  itemView.findViewById(R.id.textView62);
            this.timeAgo =  itemView.findViewById(R.id.textView9);
            this.noOfViews =  itemView.findViewById(R.id.textView10);
            this.designationIcon = itemView.findViewById(R.id.imageView);
            this.designationIcon.setImageResource(R.drawable.ic_designation);
            this.locationIcon = itemView.findViewById(R.id.imageView2);
            this.locationIcon.setImageResource(R.drawable.ic_location);
            this.qualificationIcon = itemView.findViewById(R.id.imageView3);
            this.qualificationIcon.setImageResource(R.drawable.ic_qualification);
            this.experienceIcon = itemView.findViewById(R.id.imageView32);
            this.experienceIcon.setImageResource(R.drawable.ic_experience);
            this.lastDateIcon = itemView.findViewById(R.id.imageView4);
            this.lastDateIcon.setImageResource(R.drawable.ic_calendar);
            this.verfiedIcon = itemView.findViewById(R.id.imageView11);
            this.verfiedIcon.setImageResource(R.drawable.ic_verified_fkt);
            this.companyLogo =  itemView.findViewById(R.id.imageView5);
            this.favouriteJob = itemView.findViewById(R.id.likeButton1);
            sharedPreferenceManager = new SharedPreferenceManager(mContext);

            itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    if(NetworkConnectionStatusNotificationUtil.checkConnectionStatus()) {
                        sharedPreferenceManager.saveStringSet(SharedPreferenceManager.VIEWED_JOBS,dataList.get(getAdapterPosition()).getId());
                        Intent intent = new Intent(mContext, WalkinsActivity.class);
                        intent.putExtra(Constants.INTERVIEW_ID,dataList.get(getAdapterPosition()).getId());
                        //intent.putExtra("selectedObject",dataList.get(getAdapterPosition()));
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(intent);
                    }else{
                        Intent intent = new Intent(mContext, InternetConnectionNotFoundActivity.class);
                        mContext.startActivity(intent);
                    }
                }
            });

           favouriteJob.setOnLikeListener(new OnLikeListener() {
               @Override
               public void liked(LikeButton likeButton) {
                   sharedPreferenceManager.saveStringSet(SharedPreferenceManager.FAVOURITE_JOBS,dataList.get(getAdapterPosition()).getId());
                   favouriteJob.setLiked(true);
                   favouriteJob.setLikeDrawableRes(R.drawable.ic_favorite_black_24dp);
                   Snackbar snackbar = Snackbar.make(likeButton,"Added as a Favourite Job.",Snackbar.LENGTH_SHORT);
                   snackbar.show();
               }
               @Override
               public void unLiked(LikeButton likeButton) {
                   sharedPreferenceManager.deleteFavouriteJob(SharedPreferenceManager.FAVOURITE_JOBS,dataList.get(getAdapterPosition()).getId());
                   favouriteJob.setLiked(false);
                   favouriteJob.setLikeDrawableRes(R.drawable.ic_favorite_border_black_24dp);
                   Snackbar snackbar = Snackbar.make(likeButton,"Removed from Favourite Job.",Snackbar.LENGTH_SHORT);
                   snackbar.show();
               }
           });
        }
    }

}
