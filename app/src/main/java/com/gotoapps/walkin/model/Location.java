package com.gotoapps.walkin.model;

import com.google.gson.annotations.SerializedName;

public class Location {

    @SerializedName("location")
    private String locationName;

    @SerializedName("state")
    private String locationState;

    @SerializedName("total")
    private int interviewCount;

    public Location(String locationName, String locationState, int interviewCount) {
        this.locationName = locationName;
        this.locationState = locationState;
        this.interviewCount = interviewCount;
    }

    public String getLocationName() {
        return locationName;
    }

    public String getLocationState() {
        return locationState;
    }

    public int getInterviewCount() {
        return interviewCount;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public void setLocationState(String locationState) {
        this.locationState = locationState;
    }

    public void setInterviewCount(int interviewCount) {
        this.interviewCount = interviewCount;
    }

    public String searchString() {
        return "Location{" +
                "locationName='" + locationName + '\'' +
                ", locationState='" + locationState + '\'' +
                '}';
    }
}
