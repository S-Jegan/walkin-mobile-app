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
import com.gotoapps.walkin.activities.CategoriesActivity;
import com.gotoapps.walkin.activities.InternetConnectionNotFoundActivity;
import com.gotoapps.walkin.activities.InterviewListActivity;
import com.gotoapps.walkin.model.Category;
import com.gotoapps.walkin.model.CategoryListTitle;
import com.gotoapps.walkin.model.InterviewCategoryList;
import com.gotoapps.walkin.utils.Constants;
import com.gotoapps.walkin.utils.NetworkConnectionStatusNotificationUtil;

import java.util.ArrayList;
import java.util.List;

public class CategoryListWithTitleAdapter extends RecyclerView.Adapter<CategoryListWithTitleAdapter.ItemRowHolder>{
    private  ArrayList<CategoryListTitle> dataList;
    private Context mContext;
    Intent intent;
    private boolean layoutSmall;

    public CategoryListWithTitleAdapter(Context context, ArrayList<CategoryListTitle> dataList, boolean layoutSmall) {
        this.dataList = dataList;
        this.mContext = context;
        this.layoutSmall=layoutSmall;
    }

    @Override
    public CategoryListWithTitleAdapter.ItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.dashboard_category_list, null);
        CategoryListWithTitleAdapter.ItemRowHolder mh = new CategoryListWithTitleAdapter.ItemRowHolder(v);
        return mh;
    }

    @Override
    public void onBindViewHolder(CategoryListWithTitleAdapter.ItemRowHolder itemRowHolder, final int i) {
        final String sectionName = dataList.get(i).getCategoryName();
        List<Category> singleSectionItems = dataList.get(i).getCategoryList();
        itemRowHolder.itemTitle.setText(sectionName);
        CategoryListAdapter categoryListAdapter = new CategoryListAdapter(mContext, singleSectionItems);
        itemRowHolder.categoryList.setHasFixedSize(true);
        itemRowHolder.categoryList.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        itemRowHolder.categoryList.setAdapter(categoryListAdapter);
        itemRowHolder.categoryList.setNestedScrollingEnabled(false);
        itemRowHolder.btnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(NetworkConnectionStatusNotificationUtil.checkConnectionStatus()) {
                    intent = new Intent(mContext, CategoriesActivity.class);
                    mContext.startActivity(intent);
                }else {
                    intent = new Intent(mContext, InternetConnectionNotFoundActivity.class);
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
        protected RecyclerView categoryList;
        protected TextView btnMore;
        public ItemRowHolder(View view) {
            super(view);
            this.itemTitle =  view.findViewById(R.id.itemTitle);
            this.categoryList =  view.findViewById(R.id.recycler_view_list);
            this.btnMore=  view.findViewById(R.id.btnMore);
        }
    }

}
