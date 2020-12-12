package com.gotoapps.walkin.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class InterviewJSON {

    @SerializedName("id")
    private Integer id;

    @SerializedName("category_id")
    private Integer categoryId;

    @SerializedName("designation")
    private String designation;

    @SerializedName("vacancies")
    private String vacancies;

    @SerializedName("qualification")
    private String qualification;

    @SerializedName("skills")
    private String skills;

    @SerializedName("exp_start")
    private Integer expStart;

    @SerializedName("exp_end")
    private Integer expEnd;

    @SerializedName("salary_start")
    private Double salStart;

    @SerializedName("salary_end")
    private Double salEnd;

    @SerializedName("salary_type")
    private String salType;

    @SerializedName("audience")
    private String audience;

    @SerializedName("work_mode")
    private String workMode;

    @SerializedName("job_description")
    private String jobDesc;

    @SerializedName("location")
    private String location;

    @SerializedName("state")
    private String state;

    @SerializedName("walkin_address")
    private String walkinLocation;

    @SerializedName("contact_details")
    private String contactDetails;

    @SerializedName("other_info")
    private String otherInfo;

    @SerializedName("interview_start_date")
    private String interviewStartDate;

    @SerializedName("interview_end_date")
    private String interviewEndDate;

    @SerializedName("no_of_views")
    private Integer noOfViews;

    @SerializedName("no_of_shares")
    private Integer noOfShares;

    @SerializedName("source")
    private String source;

    @SerializedName("created_at")
    private String createdAt;

    @SerializedName("is_verified")
    private Integer isVerified;

    @SerializedName("is_visa_sponsored")
    private Integer isVisaSponsored;

    @SerializedName("company")
    private Company company;

    @SerializedName("industry")
    private Industry industry;

    public InterviewJSON() {
    }

    public InterviewJSON(Integer id,Integer categoryId ,String designation, String vacancies, String qualification, String skills, Integer expStart, Integer expEnd, Double salStart, Double salEnd, String salType,String audience, String workMode, String jobDesc, String location, String state, String walkinLocation, String contactDetails, String otherInfo, String interviewStartDate, String interviewEndDate, Integer noOfViews, Integer noOfShares, String source, String createdAt, Integer isVerified, Integer isVisaSponsored, Company company, Industry industry) {
        this.id = id;
        this.categoryId = categoryId;
        this.designation = designation;
        this.vacancies = vacancies;
        this.qualification = qualification;
        this.skills = skills;
        this.expStart = expStart;
        this.expEnd = expEnd;
        this.salStart = salStart;
        this.salEnd = salEnd;
        this.salType=salType;
        this.audience = audience;
        this.workMode = workMode;
        this.jobDesc = jobDesc;
        this.location = location;
        this.state = state;
        this.walkinLocation = walkinLocation;
        this.contactDetails = contactDetails;
        this.otherInfo = otherInfo;
        this.interviewStartDate = interviewStartDate;
        this.interviewEndDate = interviewEndDate;
        this.noOfViews = noOfViews;
        this.noOfShares = noOfShares;
        this.source = source;
        this.createdAt = createdAt;
        this.isVerified = isVerified;
        this.isVisaSponsored = isVisaSponsored;
        this.company = company;
        this.industry = industry;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getVacancies() {
        return vacancies;
    }

    public void setVacancies(String vacancies) {
        this.vacancies = vacancies;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public Integer getExpStart() {
        return expStart;
    }

    public void setExpStart(Integer expStart) {
        this.expStart = expStart;
    }

    public Integer getExpEnd() {
        return expEnd;
    }

    public void setExpEnd(Integer expEnd) {
        this.expEnd = expEnd;
    }

    public Double getSalStart() {
        return salStart;
    }

    public void setSalStart(Double salStart) {
        this.salStart = salStart;
    }

    public Double getSalEnd() {
        return salEnd;
    }

    public void setSalEnd(Double salEnd) {
        this.salEnd = salEnd;
    }

    public String getAudience() {
        return audience;
    }

    public void setAudience(String audience) {
        this.audience = audience;
    }

    public String getWorkMode() {
        return workMode;
    }

    public void setWorkMode(String workMode) {
        this.workMode = workMode;
    }

    public String getJobDesc() {
        return jobDesc;
    }

    public void setJobDesc(String jobDesc) {
        this.jobDesc = jobDesc;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getWalkinLocation() {
        return walkinLocation;
    }

    public void setWalkinLocation(String walkinLocation) {
        this.walkinLocation = walkinLocation;
    }

    public String getContactDetails() {
        return contactDetails;
    }

    public void setContactDetails(String contactDetails) {
        this.contactDetails = contactDetails;
    }

    public String getOtherInfo() {
        return otherInfo;
    }

    public void setOtherInfo(String otherInfo) {
        this.otherInfo = otherInfo;
    }

    public String getInterviewStartDate() {
        return interviewStartDate;
    }

    public void setInterviewStartDate(String interviewStartDate) {
        this.interviewStartDate = interviewStartDate;
    }

    public String getInterviewEndDate() {
        return interviewEndDate;
    }

    public void setInterviewEndDate(String interviewEndDate) {
        this.interviewEndDate = interviewEndDate;
    }

    public Integer getNoOfViews() {
        return noOfViews;
    }

    public void setNoOfViews(Integer noOfViews) {
        this.noOfViews = noOfViews;
    }

    public Integer getNoOfShares() {
        return noOfShares;
    }

    public void setNoOfShares(Integer noOfShares) {
        this.noOfShares = noOfShares;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getVerified() {
        return isVerified;
    }

    public void setVerified(Integer verified) {
        isVerified = verified;
    }

    public Integer getVisaSponsored() {
        return isVisaSponsored;
    }

    public void setVisaSponsored(Integer visaSponsored) {
        isVisaSponsored = visaSponsored;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Industry getIndustry() {
        return industry;
    }

    public void setIndustry(Industry industry) {
        this.industry = industry;
    }

    public String getSalType() {
        return salType;
    }

    public void setSalType(String salType) {
        this.salType = salType;
    }

    @Override
    public String toString() {
        return "InterviewJSON{" +
                "id=" + id +
                ", designation='" + designation + '\'' +
                ", vacancies='" + vacancies + '\'' +
                ", qualification='" + qualification + '\'' +
                ", skills='" + skills + '\'' +
                ", expStart=" + expStart +
                ", expEnd=" + expEnd +
                ", salStart=" + salStart +
                ", salEnd=" + salEnd +
                ", audience='" + audience + '\'' +
                ", workMode='" + workMode + '\'' +
                ", jobDesc='" + jobDesc + '\'' +
                ", location='" + location + '\'' +
                ", state='" + state + '\'' +
                ", walkinLocation='" + walkinLocation + '\'' +
                ", contactDetails='" + contactDetails + '\'' +
                ", otherInfo='" + otherInfo + '\'' +
                ", interviewStartDate='" + interviewStartDate + '\'' +
                ", interviewEndDate='" + interviewEndDate + '\'' +
                ", noOfViews=" + noOfViews +
                ", noOfShares=" + noOfShares +
                ", source='" + source + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", isVerified=" + isVerified +
                ", isVisaSponsored=" + isVisaSponsored +
                ", company=" + company +
                ", industry=" + industry +
                '}';
    }

    public String searchString() {
        return "InterviewJSON{" +
                ", designation='" + designation + '\'' +
                ", qualification='" + qualification + '\'' +
                ", skills='" + skills + '\'' +
                ", audience='" + audience + '\'' +
                ", workMode='" + workMode + '\'' +
                ", jobDesc='" + jobDesc + '\'' +
                ", location='" + location + '\'' +
                ", state='" + state + '\'' +
                ", walkinLocation='" + walkinLocation + '\'' +
                ", contactDetails='" + contactDetails + '\'' +
                ", otherInfo='" + otherInfo + '\'' +
                ", company='" + company.getName() + '\'' +
                ", industry=" + industry +
                '}';
    }
}
