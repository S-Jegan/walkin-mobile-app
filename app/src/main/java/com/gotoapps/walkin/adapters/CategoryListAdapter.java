package com.gotoapps.walkin.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.gotoapps.walkin.R;
import com.gotoapps.walkin.activities.InternetConnectionNotFoundActivity;
import com.gotoapps.walkin.activities.InterviewListActivity;
import com.gotoapps.walkin.model.Category;
import com.gotoapps.walkin.model.Location;
import com.gotoapps.walkin.utils.Constants;
import com.gotoapps.walkin.utils.NetworkConnectionStatusNotificationUtil;
import com.gotoapps.walkin.utils.NumberFormatter;
import com.gotoapps.walkin.utils.VolleyImageLoader;

import java.util.List;

public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.CategoryViewHolder>{

    private List<Category> categoryList;
    private Context mContext;
    private ImageLoader mImageLoader;

    public class CategoryViewHolder extends RecyclerView.ViewHolder {
        public TextView categoryName;
        NetworkImageView categoryLogo;

        public CategoryViewHolder(View view) {
            super(view);
            this.categoryName = view.findViewById(R.id.category_title);
            this.categoryLogo =  view.findViewById(R.id.category_thumbnail);
        }
    }

    public CategoryListAdapter(Context context,List<Category> categoryList) {
        this.categoryList = categoryList;
        this.mContext = context;
    }

    @Override
    public CategoryListAdapter.CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView =LayoutInflater.from(parent.getContext()).inflate(R.layout.card_category, parent, false);
        return new CategoryListAdapter.CategoryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CategoryListAdapter.CategoryViewHolder holder, int position) {
        final Category category = categoryList.get(position);
        holder.categoryName.setText(category.getName());
        mImageLoader = VolleyImageLoader.getInstance().getImageLoader();
        holder.categoryLogo.setImageUrl(category.getCategoryImage().replace("localhost", "192.168.43.113"), mImageLoader);
        holder.categoryLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(NetworkConnectionStatusNotificationUtil.checkConnectionStatus()) {
                    Intent intent = new Intent(mContext, InterviewListActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    if(category.getId()==14){
                        intent.putExtra("REST_URL_HINT", Constants.FRESHER_JOBS);
                    }else {
                        intent.putExtra("REST_URL_HINT", Constants.CATEGORY_SPECIFIC_JOBS);
                        intent.putExtra("CATEGORY_ID", category.getId());
                    }
                    mContext.startActivity(intent);
                }else{
                    Intent intent = new Intent(mContext, InternetConnectionNotFoundActivity.class);
                    mContext.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

}
