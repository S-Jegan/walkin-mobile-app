package com.gotoapps.walkin.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gotoapps.walkin.R;
import com.gotoapps.walkin.activities.InternetConnectionNotFoundActivity;
import com.gotoapps.walkin.activities.InterviewListActivity;
import com.gotoapps.walkin.model.Location;
import com.gotoapps.walkin.utils.Constants;
import com.gotoapps.walkin.utils.NetworkConnectionStatusNotificationUtil;
import com.gotoapps.walkin.utils.NumberFormatter;

import java.util.List;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.LocationViewHolder>{
    private List<Location> locationList;
    private Context mContext;

    public class LocationViewHolder extends RecyclerView.ViewHolder {
        public TextView location, state, count;

        public LocationViewHolder(View view) {
            super(view);
            location = view.findViewById(R.id.location);
            state =  view.findViewById(R.id.state);
            count =  view.findViewById(R.id.count);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(NetworkConnectionStatusNotificationUtil.checkConnectionStatus()) {
                        Intent intent = new Intent(mContext, InterviewListActivity.class);
                        intent.putExtra("LOCATION_NAME", locationList.get(getAdapterPosition()).getLocationName());
                        intent.putExtra("REST_URL_HINT", Constants.LOCATION_SPECIFIC_JOBS);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(intent);
                    }else{
                        Intent intent = new Intent(mContext, InternetConnectionNotFoundActivity.class);
                        mContext.startActivity(intent);
                    }
                }
            });
        }
    }

    public LocationAdapter(Context context,List<Location> locationList) {
        this.locationList = locationList;
        this.mContext = context;
    }

    @Override
    public LocationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.location_list_row, parent, false);
        return new LocationViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(LocationViewHolder holder, int position) {
        Location location = locationList.get(position);
        holder.location.setText(location.getLocationName());
        holder.state.setText(location.getLocationState());
        if(location.getInterviewCount()==1) {
            holder.count.setText(NumberFormatter.format(location.getInterviewCount()) + " Job Found");
        }else{
            holder.count.setText(NumberFormatter.format(location.getInterviewCount())+ " Jobs Found");
        }
    }

    @Override
    public int getItemCount() {
        return locationList.size();
    }

}