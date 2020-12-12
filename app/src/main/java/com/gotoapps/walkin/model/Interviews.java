package com.gotoapps.walkin.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by C2CSLS on 9/17/2018.
 */

public class Interviews {
    @SerializedName("total")
    private int totalRecords;

    @SerializedName("per_page")
    private int perPage;

    @SerializedName("current_page")
    private int currentPage;

    @SerializedName("last_page")
    private int lastPage;

    @SerializedName("next_page_url")
    private String nextPageURL;

    @SerializedName("prev_page_url")
    private String prevPageURL;

    @SerializedName("from")
    private int fromRecord;

    @SerializedName("to")
    private int toRecord;

    @SerializedName("data")
    private ArrayList<InterviewJSON> interviewJSONList;

    public Interviews() {
    }

    public Interviews(int totalRecords, int perPage, int currentPage, int lastPage, String nextPageURL, String prevPageURL, int fromRecord, int toRecord, ArrayList<InterviewJSON> interviewJSONList) {
        this.totalRecords = totalRecords;
        this.perPage = perPage;
        this.currentPage = currentPage;
        this.lastPage = lastPage;
        this.nextPageURL = nextPageURL;
        this.prevPageURL = prevPageURL;
        this.fromRecord = fromRecord;
        this.toRecord = toRecord;
        this.interviewJSONList = interviewJSONList;
    }

    public int getTotalPages() {
        return totalRecords;
    }

    public void setTotalPages(int totalPages) {
        this.totalRecords = totalPages;
    }

    public int getPerPage() {
        return perPage;
    }

    public void setPerPage(int perPage) {
        this.perPage = perPage;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getLastPage() {
        return lastPage;
    }

    public void setLastPage(int lastPage) {
        this.lastPage = lastPage;
    }

    public String getNextPageURL() {
        return nextPageURL;
    }

    public void setNextPageURL(String nextPageURL) {
        this.nextPageURL = nextPageURL;
    }

    public String getPrevPageURL() {
        return prevPageURL;
    }

    public void setPrevPageURL(String prevPageURL) {
        this.prevPageURL = prevPageURL;
    }

    public int getFromRecord() {
        return fromRecord;
    }

    public void setFromRecord(int fromRecord) {
        this.fromRecord = fromRecord;
    }

    public int getToRecord() {
        return toRecord;
    }

    public void setToRecord(int toRecord) {
        this.toRecord = toRecord;
    }

    public ArrayList<InterviewJSON> getInterviewJSONList() {
        return interviewJSONList;
    }

    public void setInterviewJSONList(ArrayList<InterviewJSON> interviewJSONList) {
        this.interviewJSONList = interviewJSONList;
    }

    @Override
    public String toString() {
        return "Interviews{" +
                "totalPages=" + totalRecords +
                ", perPage=" + perPage +
                ", currentPage=" + currentPage +
                ", lastPage=" + lastPage +
                ", nextPageURL='" + nextPageURL + '\'' +
                ", prevPageURL='" + prevPageURL + '\'' +
                ", fromRecord=" + fromRecord +
                ", toRecord=" + toRecord +
                ", interviewJSONList=" + interviewJSONList +
                '}';
    }
}
