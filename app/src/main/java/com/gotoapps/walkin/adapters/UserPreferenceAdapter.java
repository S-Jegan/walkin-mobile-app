package com.gotoapps.walkin.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gotoapps.walkin.R;
import com.gotoapps.walkin.model.Category;
import com.gotoapps.walkin.model.Industry;

import java.util.ArrayList;
import java.util.List;

public class UserPreferenceAdapter extends RecyclerView.Adapter<UserPreferenceAdapter.UserPreferenceViewHolder>{

    private List<Category> categoryList;
    private Context mContext;
    private List<Category> industryListSelected=new ArrayList<>();


    public UserPreferenceAdapter(Context mContext,List<Category> categoryList) {
        this.categoryList = categoryList;
        this.mContext = mContext;
    }

    public class UserPreferenceViewHolder extends RecyclerView.ViewHolder {
        public TextView keyText;
        public CheckBox isSelected;
        public RelativeLayout relativeLayout;
        public UserPreferenceViewHolder(View view) {
            super(view);
            keyText =  view.findViewById(R.id.filterItemName);
            isSelected =  view.findViewById(R.id.filterItemCheckBox);
            relativeLayout=view.findViewById(R.id.relative);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    isSelected =  view.findViewById(R.id.filterItemCheckBox);
                    boolean checked = isSelected.isChecked();
                    Category myVal=categoryList.get(getAdapterPosition());
                    if(checked){
                        industryListSelected.remove(myVal);
                        isSelected.setChecked(false);
                    }else {
                        isSelected.setChecked(true);
                        industryListSelected.add(myVal);
                    }
                }
            });
           isSelected.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    boolean checked = ((CheckBox) view).isChecked();
                    Category myVal=categoryList.get(getAdapterPosition());
                    if(checked){
                        industryListSelected.add(myVal);
                    }else{
                        industryListSelected.remove(myVal);
                    }
                }
            });
        }
    }

    @Override
    public UserPreferenceAdapter.UserPreferenceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.filter_checkbox_row, parent, false);
        return new UserPreferenceAdapter.UserPreferenceViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(UserPreferenceAdapter.UserPreferenceViewHolder holder, int position) {
        Category category=categoryList.get(position);
        holder.keyText.setText(category.getName());
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public List<Category> getSelectedElements() {
        return industryListSelected;
    }
}
