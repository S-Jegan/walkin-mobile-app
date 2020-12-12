package com.gotoapps.walkin.response;

import com.google.gson.annotations.SerializedName;

public class InterviewRestResponse {

    @SerializedName("id")
    private Integer id;

    @SerializedName("company_name")
    private String companyName;

    @SerializedName("logo_url")
    private String logoURL;

    @SerializedName("fcm_img_url")
    private String fcmImgURL;

    @SerializedName("indicator")
    private String indicator;

    @SerializedName("designation")
    private String designation;

    @SerializedName("last_date")
    private String last_date;

    @SerializedName("date_posted")
    private String date_posted;

    @SerializedName("interview_date")
    private String interview_date;

    @SerializedName("qualification")
    private String qualification;

    @SerializedName("location")
    private String location;

    @SerializedName("experience")
    private String experience;

    @SerializedName("passedout")
    private String passedout;

    @SerializedName("job_desc")
    private String job_desc;

    @SerializedName("about_company")
    private String about_company;

    @SerializedName("walkin_location")
    private String walkin_location;

    @SerializedName("contact_details")
    private String contact_details;

    @SerializedName("other_info")
    private String other_info;

    @SerializedName("no_of_views")
    private Integer no_of_views;

    @SerializedName("interview_category")
    private String interview_category;

    @SerializedName("skills")
    private String skills;

    @SerializedName("job_verified")
    private Boolean job_verified;

    @SerializedName("source")
    private String source;

    @SerializedName("created_timestamp")
    private String created_timestamp;

    public InterviewRestResponse(Integer id, String companyName, String logoURL, String fcmImgURL, String indicator, String designation, String last_date, String date_posted, String interview_date, String qualification, String location, String experience, String passedout, String job_desc, String about_company, String walkin_location, String contact_details, String other_info, Integer no_of_views, String interview_category, String skills, Boolean job_verified, String source, String created_timestamp) {
        this.id = id;
        this.companyName = companyName;
        this.logoURL = logoURL;
        this.fcmImgURL = fcmImgURL;
        this.indicator = indicator;
        this.designation = designation;
        this.last_date = last_date;
        this.date_posted = date_posted;
        this.interview_date = interview_date;
        this.qualification = qualification;
        this.location = location;
        this.experience = experience;
        this.passedout = passedout;
        this.job_desc = job_desc;
        this.about_company = about_company;
        this.walkin_location = walkin_location;
        this.contact_details = contact_details;
        this.other_info = other_info;
        this.no_of_views = no_of_views;
        this.interview_category = interview_category;
        this.skills = skills;
        this.job_verified = job_verified;
        this.source = source;
        this.created_timestamp=created_timestamp;
    }

    public InterviewRestResponse(Integer id, String companyName, String logoURL, String designation, String location) {
        this.id = id;
        this.companyName = companyName;
        this.logoURL = logoURL;
        this.designation = designation;
        this.location = location;
    }

    public InterviewRestResponse() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getLogoURL() {
        return logoURL;
    }

    public void setLogoURL(String logoURL) {
        this.logoURL = logoURL;
    }

    public String getFcmImgURL() {
        return fcmImgURL;
    }

    public void setFcmImgURL(String fcmImgURL) {
        this.fcmImgURL = fcmImgURL;
    }

    public String getIndicator() {
        return indicator;
    }

    public void setIndicator(String indicator) {
        this.indicator = indicator;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getLast_date() {
        return last_date;
    }

    public void setLast_date(String last_date) {
        this.last_date = last_date;
    }

    public String getDate_posted() {
        return date_posted;
    }

    public void setDate_posted(String date_posted) {
        this.date_posted = date_posted;
    }

    public String getInterview_date() {
        return interview_date;
    }

    public void setInterview_date(String interview_date) {
        this.interview_date = interview_date;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getPassedout() {
        return passedout;
    }

    public void setPassedout(String passedout) {
        this.passedout = passedout;
    }

    public String getJob_desc() {
        return job_desc;
    }

    public void setJob_desc(String job_desc) {
        this.job_desc = job_desc;
    }

    public String getAbout_company() {
        return about_company;
    }

    public void setAbout_company(String about_company) {
        this.about_company = about_company;
    }

    public String getWalkin_location() {
        return walkin_location;
    }

    public void setWalkin_location(String walkin_location) {
        this.walkin_location = walkin_location;
    }

    public String getContact_details() {
        return contact_details;
    }

    public void setContact_details(String contact_details) {
        this.contact_details = contact_details;
    }

    public String getOther_info() {
        return other_info;
    }

    public void setOther_info(String other_info) {
        this.other_info = other_info;
    }

    public Integer getNo_of_views() {
        return no_of_views;
    }

    public void setNo_of_views(Integer no_of_views) {
        this.no_of_views = no_of_views;
    }

    public String getInterview_category() {
        return interview_category;
    }

    public void setInterview_category(String interview_category) {
        this.interview_category = interview_category;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public Boolean getJob_verified() {
        return job_verified;
    }

    public void setJob_verified(Boolean job_verified) {
        this.job_verified = job_verified;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getCreated_timestamp() {
        return created_timestamp;
    }

    public void setCreated_timestamp(String created_timestamp) {
        this.created_timestamp = created_timestamp;
    }

    @Override
    public String toString() {
        return "InterviewJSON{" +
                "id=" + id +
                ", companyName='" + companyName + '\'' +
                ", logoURL='" + logoURL + '\'' +
                ", fcmImgURL='" + fcmImgURL + '\'' +
                ", indicator='" + indicator + '\'' +
                ", designation='" + designation + '\'' +
                ", last_date='" + last_date + '\'' +
                ", date_posted='" + date_posted + '\'' +
                ", interview_date='" + interview_date + '\'' +
                ", qualification='" + qualification + '\'' +
                ", location='" + location + '\'' +
                ", experience='" + experience + '\'' +
                ", passedout='" + passedout + '\'' +
                ", job_desc='" + job_desc + '\'' +
                ", about_company='" + about_company + '\'' +
                ", walkin_location='" + walkin_location + '\'' +
                ", contact_details='" + contact_details + '\'' +
                ", other_info='" + other_info + '\'' +
                ", no_of_views=" + no_of_views +
                ", interview_category='" + interview_category + '\'' +
                ", skills='" + skills + '\'' +
                ", job_verified=" + job_verified +
                ", source='" + source + '\'' +
                '}';
    }

    public String searchString() {
        return "InterviewJSON{" +
                ", companyName='" + companyName + '\'' +
                ", designation='" + designation + '\'' +
                ", qualification='" + qualification + '\'' +
                ", location='" + location + '\'' +
                ", experience='" + experience + '\'' +
                ", passedout='" + passedout + '\'' +
                ", skills='" + skills + '\'' +
                '}';
    }

}
