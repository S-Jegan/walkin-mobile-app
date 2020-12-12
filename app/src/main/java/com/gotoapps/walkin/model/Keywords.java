package com.gotoapps.walkin.model;

import com.google.gson.annotations.SerializedName;

public class Keywords {

    @SerializedName("skills")
    private String skills;

    @SerializedName("designation")
    private String designation;

    public Keywords() {
    }

    public Keywords(String skills, String designation) {
        this.skills = skills;
        this.designation = designation;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    @Override
    public String toString() {
        return "Keywords{" +
                "skills='" + skills + '\'' +
                ", designation='" + designation + '\'' +
                '}';
    }
}
