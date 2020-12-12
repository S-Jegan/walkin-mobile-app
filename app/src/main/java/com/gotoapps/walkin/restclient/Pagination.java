package com.gotoapps.walkin.restclient;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Pagination {

    @SerializedName("total")
    @Expose
    private Integer total;

    @SerializedName("per_page")
    @Expose
    private Integer per_page;

    @SerializedName("current_page")
    @Expose
    private Integer current_page;

    @SerializedName("last_page")
    @Expose
    private Integer last_page;

    @SerializedName("next_page_url")
    @Expose
    private String next_page_url;

    @SerializedName("prev_page_url")
    @Expose
    private String prev_page_url;

    @SerializedName("from")
    @Expose
    private Integer from;

    @SerializedName("to")
    @Expose
    private Integer to;

    public Pagination(Integer total, Integer per_page, Integer current_page, Integer last_page, String next_page_url, String prev_page_url, Integer from, Integer to) {
        this.total = total;
        this.per_page = per_page;
        this.current_page = current_page;
        this.last_page = last_page;
        this.next_page_url = next_page_url;
        this.prev_page_url = prev_page_url;
        this.from = from;
        this.to = to;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getPer_page() {
        return per_page;
    }

    public void setPer_page(Integer per_page) {
        this.per_page = per_page;
    }

    public Integer getCurrent_page() {
        return current_page;
    }

    public void setCurrent_page(Integer current_page) {
        this.current_page = current_page;
    }

    public Integer getLast_page() {
        return last_page;
    }

    public void setLast_page(Integer last_page) {
        this.last_page = last_page;
    }

    public String getNext_page_url() {
        return next_page_url;
    }

    public void setNext_page_url(String next_page_url) {
        this.next_page_url = next_page_url;
    }

    public String getPrev_page_url() {
        return prev_page_url;
    }

    public void setPrev_page_url(String prev_page_url) {
        this.prev_page_url = prev_page_url;
    }

    public Integer getFrom() {
        return from;
    }

    public void setFrom(Integer from) {
        this.from = from;
    }

    public Integer getTo() {
        return to;
    }

    public void setTo(Integer to) {
        this.to = to;
    }

    @Override
    public String toString() {
        return "Pagination{" +
                "total=" + total +
                ", per_page=" + per_page +
                ", current_page=" + current_page +
                ", last_page=" + last_page +
                ", next_page_url='" + next_page_url + '\'' +
                ", prev_page_url='" + prev_page_url + '\'' +
                ", from=" + from +
                ", to=" + to +
                '}';
    }
}
