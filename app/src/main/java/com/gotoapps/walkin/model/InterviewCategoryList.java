package com.gotoapps.walkin.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class InterviewCategoryList{

    @SerializedName("category_name")
    private String categoryName;

    @SerializedName("data")
    private ArrayList<InterviewJSON> interviewJSONList;

    public InterviewCategoryList() {
    }

    public InterviewCategoryList(String categoryName, ArrayList<InterviewJSON> interviewJSONList) {
        this.categoryName = categoryName;
        this.interviewJSONList = interviewJSONList;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public ArrayList<InterviewJSON> getInterviewJSONList() {
        return interviewJSONList;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public void setInterviewJSONList(ArrayList<InterviewJSON> interviewJSONList) {
        this.interviewJSONList = interviewJSONList;
    }
}
