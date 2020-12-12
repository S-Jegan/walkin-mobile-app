package com.gotoapps.walkin.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.gotoapps.walkin.R;
import com.gotoapps.walkin.model.FilterMainElement;
import com.gotoapps.walkin.model.FilterSubElement;
import com.gotoapps.walkin.response.FilterCategoryRestResponse;
import com.gotoapps.walkin.response.FilterRestResponse;

import java.util.ArrayList;
import java.util.List;

public class FilterItemAdapter extends  RecyclerView.Adapter<FilterItemAdapter.FilterItemViewHolder>{

    private List<FilterCategoryRestResponse> filterCategoryRestResponses;
    private List<FilterCategoryRestResponse> filterCategoryRestResponsesChosen = new ArrayList<>();

    private Context mContext;
    public FilterItemAdapter(Context context,List<FilterCategoryRestResponse> filterCategoryRestResponses) {
        this.filterCategoryRestResponses = filterCategoryRestResponses;
        this.mContext = context;
    }
    public class FilterItemViewHolder extends RecyclerView.ViewHolder {
        public TextView keyText;
        public CheckBox isSelected;
        public FilterItemViewHolder(View view) {
            super(view);
            keyText = (TextView) view.findViewById(R.id.filterItemName);
            isSelected = (CheckBox) view.findViewById(R.id.filterItemCheckBox);
            isSelected.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   boolean checked = ((CheckBox) view).isChecked();
                   if(checked){
                       filterCategoryRestResponses.get(getAdapterPosition()).setFiltered(true);
                   }else{
                       filterCategoryRestResponses.get(getAdapterPosition()).setFiltered(false);
                   }
               }
           });
        }
    }

    @Override
    public FilterItemAdapter.FilterItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.filter_checkbox_row, parent, false);
        return new FilterItemAdapter.FilterItemViewHolder(itemView);    }

    @Override
    public void onBindViewHolder(FilterItemAdapter.FilterItemViewHolder holder, int position) {
        FilterCategoryRestResponse filterCategoryRestResponse = filterCategoryRestResponses.get(position);
        holder.keyText.setText(filterCategoryRestResponse.getName()+" ("+filterCategoryRestResponse.getCount()+")");
        holder.isSelected.setChecked(filterCategoryRestResponse.isFiltered());
    }

    @Override
    public int getItemCount() {
        return filterCategoryRestResponses.size();
    }

    public List<FilterCategoryRestResponse> getSelectedEelements() {
        return filterCategoryRestResponsesChosen;
    }

    public List<FilterCategoryRestResponse> getAllElements() {
        return filterCategoryRestResponses;
    }
}
