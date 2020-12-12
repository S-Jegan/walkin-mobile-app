package com.gotoapps.walkin.model;

import com.google.gson.annotations.SerializedName;

public class Category {

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("img_url")
    private String categoryImage;

    @SerializedName("jobs_count")
    private int jobsCount;

    public Category(int id, String name, String categoryImage, int jobsCount) {
        this.id = id;
        this.name = name;
        this.categoryImage = categoryImage;
        this.jobsCount = jobsCount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategoryImage() {
        return categoryImage;
    }

    public void setCategoryImage(String categoryImage) {
        this.categoryImage = categoryImage;
    }

    public int getJobsCount() {
        return jobsCount;
    }

    public void setJobsCount(int jobsCount) {
        this.jobsCount = jobsCount;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", categoryImage='" + categoryImage + '\'' +
                ", jobsCount=" + jobsCount +
                '}';
    }
}
