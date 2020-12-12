package com.gotoapps.walkin.restclient;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Set;

public class PostSearch {

    @SerializedName("page")
    @Expose
    private Integer page;

    @SerializedName("per_page")
    @Expose
    private Integer per_page;

    @SerializedName("keywords")
    @Expose
    Set<String> keyWordsList;

    @SerializedName("location")
    @Expose
    Set<String> locationList;

    @SerializedName("experience")
    @Expose
    String expLimit;

    @SerializedName("salary")
    @Expose
    String salLimit;

    public PostSearch() {
    }

    public PostSearch(Integer page, Integer per_page, Set<String> keyWordsList, Set<String> locationList, String expLimit, String salLimit) {
        this.page = page;
        this.per_page = per_page;
        this.keyWordsList = keyWordsList;
        this.locationList = locationList;
        this.expLimit = expLimit;
        this.salLimit = salLimit;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPer_page() {
        return per_page;
    }

    public void setPer_page(Integer per_page) {
        this.per_page = per_page;
    }

    public Set<String> getKeyWordsList() {
        return keyWordsList;
    }

    public void setKeyWordsList(Set<String> keyWordsList) {
        this.keyWordsList = keyWordsList;
    }

    public Set<String> getLocationList() {
        return locationList;
    }

    public void setLocationList(Set<String> locationList) {
        this.locationList = locationList;
    }

    public String getExpLimit() {
        return expLimit;
    }

    public void setExpLimit(String expLimit) {
        this.expLimit = expLimit;
    }

    public String getSalLimit() {
        return salLimit;
    }

    public void setSalLimit(String salLimit) {
        this.salLimit = salLimit;
    }

    @Override
    public String toString() {
        return "PostSearch{" +
                "page=" + page +
                ", per_page=" + per_page +
                ", keyWordsList=" + keyWordsList +
                ", locationList=" + locationList +
                ", expLimit='" + expLimit + '\'' +
                ", salLimit='" + salLimit + '\'' +
                '}';
    }
}
