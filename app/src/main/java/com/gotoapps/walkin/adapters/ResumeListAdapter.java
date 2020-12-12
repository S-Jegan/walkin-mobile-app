package com.gotoapps.walkin.adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.gotoapps.walkin.R;
import com.gotoapps.walkin.activities.WalkInWebViewActivity;
import com.gotoapps.walkin.model.Resume;
import com.gotoapps.walkin.restclient.ApiClient;
import com.gotoapps.walkin.restclient.ApiInterface;
import com.gotoapps.walkin.utils.NumberFormatter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResumeListAdapter extends RecyclerView.Adapter<ResumeListAdapter.ResumeViewHolder>{
    private List<Resume> resumeList;
    private Context mContext;
    private ProgressDialog pDialog;

    public ResumeListAdapter(Context context,List<Resume> resumeList) {
        this.resumeList = resumeList;
        this.mContext = context;
    }

    public class ResumeViewHolder extends RecyclerView.ViewHolder {
        public TextView resumeName, resumeDesignation, resumeCategory,downloads;

        public ResumeViewHolder(View view) {
            super(view);
            resumeName =  view.findViewById(R.id.resumeName);
            resumeDesignation =  view.findViewById(R.id.resumeDesignation);
            resumeCategory =  view.findViewById(R.id.resumeCategory);
            downloads =  view.findViewById(R.id.downloads);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Resume selectedResume = resumeList.get(getAdapterPosition());
                    Intent intent = new Intent(mContext, WalkInWebViewActivity.class);
                    intent.putExtra("ACTION_BAR_TITLE", selectedResume.getResumeName());
                    intent.putExtra("DOWNLOAD_URL", selectedResume.getDownloadURL());
                    intent.putExtra("URL", selectedResume.getResumeURL());
                    intent.putExtra("PDF_FLAG", "Y");
                    intent.putExtra("RESUME_ID", selectedResume.getId());
                    intent.putExtra("RESUME_TITLE",selectedResume.getResumeDesignation().replace(" ","_"));
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(intent);
                }
            });

        }
    }

    @Override
    public ResumeListAdapter.ResumeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.resume_list_row, parent, false);
        return new ResumeListAdapter.ResumeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ResumeListAdapter.ResumeViewHolder holder, int position) {
        final Resume resume = resumeList.get(position);
        holder.resumeName.setText(resume.getResumeName());
        holder.resumeDesignation.setText(resume.getResumeDesignation());
        holder.resumeCategory.setText(resume.getResumeCategory().getName());
        holder.downloads.setText(NumberFormatter.format(resume.getDownloads())+" Downloads");
    }

    @Override
    public int getItemCount() {
        return resumeList.size();
    }
}