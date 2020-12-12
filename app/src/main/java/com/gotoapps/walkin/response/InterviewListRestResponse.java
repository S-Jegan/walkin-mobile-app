package com.gotoapps.walkin.response;

import com.google.gson.annotations.SerializedName;
import com.gotoapps.walkin.model.InterviewJSON;

import java.util.ArrayList;

public class InterviewListRestResponse {

    @SerializedName("total")
    private int totalPages;

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

    @SerializedName("filter")
    private FilterRestResponse filterRestResponses;

    @SerializedName("interviews")
    private ArrayList<InterviewJSON> interviewList;

    public InterviewListRestResponse(int totalPages, int perPage, int currentPage, int lastPage, String nextPageURL, String prevPageURL, int fromRecord, int toRecord, FilterRestResponse filterRestResponse, ArrayList<InterviewJSON> interviewList) {
        this.totalPages = totalPages;
        this.perPage = perPage;
        this.currentPage = currentPage;
        this.lastPage = lastPage;
        this.nextPageURL = nextPageURL;
        this.prevPageURL = prevPageURL;
        this.fromRecord = fromRecord;
        this.toRecord = toRecord;
        this.filterRestResponses = filterRestResponses;
        this.interviewList = interviewList;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
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

    public FilterRestResponse getFilterRestResponses() {
        return filterRestResponses;
    }

    public void setFilterRestResponses(FilterRestResponse filterRestResponses) {
        this.filterRestResponses = filterRestResponses;
    }

    public ArrayList<InterviewJSON> getInterviewList() {
        return interviewList;
    }

    public void setInterviewList(ArrayList<InterviewJSON> interviewList) {
        this.interviewList = interviewList;
    }

    @Override
    public String toString() {
        return "InterviewListRestResponse{" +
                "totalPages=" + totalPages +
                ", perPage=" + perPage +
                ", currentPage=" + currentPage +
                ", lastPage=" + lastPage +
                ", nextPageURL='" + nextPageURL + '\'' +
                ", prevPageURL='" + prevPageURL + '\'' +
                ", fromRecord=" + fromRecord +
                ", toRecord=" + toRecord +
                ", filterRestResponses=" + filterRestResponses +
                ", interviewList=" + interviewList +
                '}';
    }
}
