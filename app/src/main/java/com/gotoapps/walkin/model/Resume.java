package com.gotoapps.walkin.model;

import com.google.gson.annotations.SerializedName;

public class Resume {

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String resumeName;

    @SerializedName("format")
    private String formatName;

    @SerializedName("designation")
    private String resumeDesignation;

    @SerializedName("category")
    private Category category;

    @SerializedName("pdf_url")
    private String resumeURL;

    @SerializedName("downloads")
    private int downloads;

    @SerializedName("word_url")
    private String downloadURL;


    public Resume(int id, String resumeName, String formatName, String resumeDesignation, Category resumeCategory, String resumeURL, int downloads, String downloadURL) {
        this.id = id;
        this.resumeName = resumeName;
        this.formatName = formatName;
        this.resumeDesignation = resumeDesignation;
        this.category = resumeCategory;
        this.resumeURL = resumeURL;
        this.downloads = downloads;
        this.downloadURL = downloadURL;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getResumeName() {
        return resumeName;
    }

    public void setResumeName(String resumeName) {
        this.resumeName = resumeName;
    }

    public String getFormatName() {
        return formatName;
    }

    public void setFormatName(String formatName) {
        this.formatName = formatName;
    }

    public String getResumeDesignation() {
        return resumeDesignation;
    }

    public void setResumeDesignation(String resumeDesignation) {
        this.resumeDesignation = resumeDesignation;
    }

    public Category getResumeCategory() {
        return category;
    }

    public void setResumeCategory(Category resumeCategory) {
        this.category = resumeCategory;
    }

    public String getResumeURL() {
        return resumeURL;
    }

    public void setResumeURL(String resumeURL) {
        this.resumeURL = resumeURL;
    }

    public int getDownloads() {
        return downloads;
    }

    public void setDownloads(int downloads) {
        this.downloads = downloads;
    }

    public String getDownloadURL() {
        return downloadURL;
    }

    public void setDownloadURL(String downloadURL) {
        this.downloadURL = downloadURL;
    }

    @Override
    public String toString() {
        return "Resume{" +
                "id=" + id +
                ", resumeName='" + resumeName + '\'' +
                ", formatName='" + formatName + '\'' +
                ", resumeDesignation='" + resumeDesignation + '\'' +
                ", resumeCategory='" + category + '\'' +
                ", resumeURL='" + resumeURL + '\'' +
                ", downloads=" + downloads +
                ", downloadURL='" + downloadURL + '\'' +
                '}';
    }


}
