package com.gotoapps.walkin.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gotoapps.walkin.R;
import com.gotoapps.walkin.adapters.FilterItemAdapter;
import com.gotoapps.walkin.model.FilterMainElement;
import com.gotoapps.walkin.response.FilterCategoryRestResponse;
import com.gotoapps.walkin.response.FilterRestResponse;

import java.util.ArrayList;
import java.util.List;

public class LocationFragment extends Fragment{
    View rootView;
    FilterItemAdapter filterItemAdapter;
    public LocationFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_filter, container, false);
        Bundle bundle = this.getArguments();
        FilterRestResponse filterRestResponse=null;
        if (bundle != null) {
            filterRestResponse=bundle.getParcelable("Filter");
        }
        RecyclerView my_recycler_view = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        filterItemAdapter = new FilterItemAdapter(getActivity(),filterRestResponse.getLocation());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        my_recycler_view.setLayoutManager(mLayoutManager);
        my_recycler_view.setItemAnimator(new DefaultItemAnimator());
        my_recycler_view.setAdapter(filterItemAdapter);
        return rootView;
    }

    public List<FilterCategoryRestResponse> getFilteredCategory() {
        return filterItemAdapter.getSelectedEelements();
    }
    public List<FilterCategoryRestResponse> getAllElements() {
        return filterItemAdapter.getAllElements();
    }
}