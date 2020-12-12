package com.gotoapps.walkin.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FilterRestResponse implements Serializable,Parcelable {

    @SerializedName("salary")
    private List<FilterCategoryRestResponse> salary;

    @SerializedName("location")
    private List<FilterCategoryRestResponse> location;

    @SerializedName("freshness")
    private List<FilterCategoryRestResponse> freshness;

    @SerializedName("role")
    private List<FilterCategoryRestResponse> role;

    @SerializedName("workmode")
    private List<FilterCategoryRestResponse> workmode;

    @SerializedName("experience")
    private List<FilterCategoryRestResponse> experience;

    @SerializedName("education")
    private List<FilterCategoryRestResponse> education;

    @SerializedName("category")
    private List<FilterCategoryRestResponse> category;

    public FilterRestResponse(){

    }

    public FilterRestResponse(List<FilterCategoryRestResponse> salary, List<FilterCategoryRestResponse> location, List<FilterCategoryRestResponse> freshness, List<FilterCategoryRestResponse> role, List<FilterCategoryRestResponse> workmode, List<FilterCategoryRestResponse> experience, List<FilterCategoryRestResponse> education, List<FilterCategoryRestResponse> category) {
        this.salary = salary;
        this.location = location;
        this.freshness = freshness;
        this.role = role;
        this.workmode = workmode;
        this.experience = experience;
        this.education = education;
        this.category = category;
    }

    public List<FilterCategoryRestResponse> getSalary() {
        return salary;
    }

    public void setSalary(List<FilterCategoryRestResponse> salary) {
        this.salary = salary;
    }

    public List<FilterCategoryRestResponse> getLocation() {
        return location;
    }

    public void setLocation(List<FilterCategoryRestResponse> location) {
        this.location = location;
    }

    public List<FilterCategoryRestResponse> getFreshness() {
        return freshness;
    }

    public void setFreshness(List<FilterCategoryRestResponse> freshness) {
        this.freshness = freshness;
    }

    public List<FilterCategoryRestResponse> getRole() {
        return role;
    }

    public void setRole(List<FilterCategoryRestResponse> role) {
        this.role = role;
    }

    public List<FilterCategoryRestResponse> getWorkmode() {
        return workmode;
    }

    public void setWorkmode(List<FilterCategoryRestResponse> workmode) {
        this.workmode = workmode;
    }

    public List<FilterCategoryRestResponse> getExperience() {
        return experience;
    }

    public void setExperience(List<FilterCategoryRestResponse> experience) {
        this.experience = experience;
    }

    public List<FilterCategoryRestResponse> getEducation() {
        return education;
    }

    public void setEducation(List<FilterCategoryRestResponse> education) {
        this.education = education;
    }

    public List<FilterCategoryRestResponse> getCategory() {
        return category;
    }

    public void setCategory(List<FilterCategoryRestResponse> category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "FilterRestResponse{" +
                "salary=" + salary +
                ", location=" + location +
                ", freshness=" + freshness +
                ", role=" + role +
                ", workmode=" + workmode +
                ", experience=" + experience +
                ", education=" + education +
                ", category=" + category +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.salary);
        dest.writeTypedList(this.location);
        dest.writeTypedList(this.freshness);
        dest.writeTypedList(this.role);
        dest.writeTypedList(this.workmode);
        dest.writeTypedList(this.experience);
        dest.writeTypedList(this.education);
        dest.writeTypedList(this.category);
    }

    protected FilterRestResponse(Parcel in) {
        this.salary = in.createTypedArrayList(FilterCategoryRestResponse.CREATOR);
        this.location = in.createTypedArrayList(FilterCategoryRestResponse.CREATOR);
        this.freshness = in.createTypedArrayList(FilterCategoryRestResponse.CREATOR);
        this.role = in.createTypedArrayList(FilterCategoryRestResponse.CREATOR);
        this.workmode = in.createTypedArrayList(FilterCategoryRestResponse.CREATOR);
        this.experience = in.createTypedArrayList(FilterCategoryRestResponse.CREATOR);
        this.education = in.createTypedArrayList(FilterCategoryRestResponse.CREATOR);
        this.category = in.createTypedArrayList(FilterCategoryRestResponse.CREATOR);
    }

    public static final Creator<FilterRestResponse> CREATOR = new Creator<FilterRestResponse>() {
        @Override
        public FilterRestResponse createFromParcel(Parcel source) {
            return new FilterRestResponse(source);
        }

        @Override
        public FilterRestResponse[] newArray(int size) {
            return new FilterRestResponse[size];
        }
    };
}
