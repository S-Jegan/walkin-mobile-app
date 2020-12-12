package com.gotoapps.walkin.restclient;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Set;

/**
 * Created by C2CSLS on 9/17/2018.
 */

public class PostData {

    @SerializedName("page")
    @Expose
    private Integer page;

    @SerializedName("per_page")
    @Expose
    private Integer per_page;

    @SerializedName("ids")
    @Expose
    private Set<String> interviewIds;

    public Set<String> getInterviewIds() {
        return interviewIds;
    }

    public void setInterviewIds(Set<String> interviewIds) {
        this.interviewIds = interviewIds;
    }

    public PostData(Integer per_page) {
        this.per_page = per_page;
    }

    public PostData(Integer page, Integer per_page) {
        this.page = page;
        this.per_page = per_page;
    }

    public PostData(Integer page, Integer per_page,Set<String> interviewIds) {
        this.page = page;
        this.per_page = per_page;
        this.interviewIds = interviewIds;
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

}
