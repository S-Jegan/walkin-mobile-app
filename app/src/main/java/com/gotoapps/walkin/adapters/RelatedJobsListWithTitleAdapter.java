package com.gotoapps.walkin.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gotoapps.walkin.R;
import com.gotoapps.walkin.activities.InterviewListActivity;
import com.gotoapps.walkin.model.InterviewCategoryList;
import com.gotoapps.walkin.utils.Constants;

import java.util.ArrayList;

public class RelatedJobsListWithTitleAdapter extends RecyclerView.Adapter<RelatedJobsListWithTitleAdapter.ItemRowHolder>{
    private ArrayList<InterviewCategoryList> dataList;
    private Context mContext;
    Intent intent;

    public RelatedJobsListWithTitleAdapter(Context context, ArrayList<InterviewCategoryList> dataList) {
        this.dataList = dataList;
        this.mContext = context;
    }

    @Override
    public ItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.dashboard_recommendations_list, null);
        ItemRowHolder mh = new ItemRowHolder(v);
        return mh;
    }

    @Override
    public void onBindViewHolder(ItemRowHolder itemRowHolder, final int i) {
        final String sectionName = dataList.get(i).getCategoryName();
        ArrayList singleSectionItems = dataList.get(i).getInterviewJSONList();
        itemRowHolder.itemTitle.setText(sectionName);
        RelatedJobsListDataAdapter itemListDataAdapter = new RelatedJobsListDataAdapter(mContext, singleSectionItems);
        itemRowHolder.interviewCategoryList.setHasFixedSize(true);
        itemRowHolder.interviewCategoryList.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        itemRowHolder.interviewCategoryList.setAdapter(itemListDataAdapter);
        itemRowHolder.interviewCategoryList.setNestedScrollingEnabled(false);
        itemRowHolder.btnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(sectionName.trim().equalsIgnoreCase("Related Jobs")){
                   intent = new Intent(mContext, InterviewListActivity.class);
                   intent.putExtra("REST_URL_HINT", Constants.CATEGORY_SPECIFIC_JOBS);
                   intent.putExtra("CATEGORY_ID", dataList.get(i).getInterviewJSONList().get(0).getCategoryId());
                   intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                   mContext.startActivity(intent);
                   //((Activity)mContext).finish();
               }else{
                   intent = new Intent(mContext, InterviewListActivity.class);
                   intent.putExtra("REST_URL_HINT", Constants.ALL_JOBS);
                   intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                   mContext.startActivity(intent);
               }
            }
        });
    }

    @Override
    public int getItemCount() {
        return (null != dataList ? dataList.size() : 0);
    }

    public class ItemRowHolder extends RecyclerView.ViewHolder {
        protected TextView itemTitle;
        protected RecyclerView interviewCategoryList;
        protected TextView btnMore;
        public ItemRowHolder(View view) {
            super(view);
            this.itemTitle =  view.findViewById(R.id.itemTitle);
            this.interviewCategoryList =  view.findViewById(R.id.recycler_view_list);
            this.btnMore=  view.findViewById(R.id.btnMore);
        }
    }

}
