package com.gotoapps.walkin.model;

import com.google.gson.annotations.SerializedName;

public class Company {

    @SerializedName("id")
    private Integer id;

    @SerializedName("name")
    private String name;

    @SerializedName("logo")
    private String logo;

    @SerializedName("banner")
    private String banner;

    @SerializedName("type")
    private String companyType;

    @SerializedName("city")
    private String city;

    @SerializedName("state")
    private String state;

    @SerializedName("country")
    private String country;

    @SerializedName("address")
    private String address;

    @SerializedName("pincode")
    private String pincode;

    @SerializedName("email")
    private String email;

    @SerializedName("phone")
    private String phone;

    @SerializedName("about_company")
    private String about_company;

    @SerializedName("website")
    private String website;

    public Company() {
    }

    public Company(Integer id, String name, String logo, String banner, String companyType, String city, String state, String country, String address, String pincode, String email, String phone, String about_company, String website) {
        this.id = id;
        this.name = name;
        this.logo = logo;
        this.banner = banner;
        this.companyType = companyType;
        this.city = city;
        this.state = state;
        this.country = country;
        this.address = address;
        this.pincode = pincode;
        this.email = email;
        this.phone = phone;
        this.about_company = about_company;
        this.website = website;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public String getCompanyType() {
        return companyType;
    }

    public void setCompanyType(String companyType) {
        this.companyType = companyType;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAbout_company() {
        return about_company;
    }

    public void setAbout_company(String about_company) {
        this.about_company = about_company;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    @Override
    public String toString() {
        return "Company{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", logo='" + logo + '\'' +
                ", banner='" + banner + '\'' +
                ", companyType='" + companyType + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", country='" + country + '\'' +
                ", address='" + address + '\'' +
                ", pincode=" + pincode +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", about_company='" + about_company + '\'' +
                ", website='" + website + '\'' +
                '}';
    }
}
