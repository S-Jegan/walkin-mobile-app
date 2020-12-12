package com.gotoapps.walkin.model;

public class About {

    private String title;
    private int logoIamgeName;
    private int arrowImageName;
    private String contentURL;

    public About(String title, int logoIamgeName, int arrowImageName, String contentURL) {
        this.title = title;
        this.logoIamgeName = logoIamgeName;
        this.arrowImageName = arrowImageName;
        this.contentURL = contentURL;
    }

    public String getContentURL() {
        return contentURL;
    }

    public void setContentURL(String contentURL) {
        this.contentURL = contentURL;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getLogoIamgeName() {
        return logoIamgeName;
    }

    public void setLogoIamgeName(int logoIamgeName) {
        this.logoIamgeName = logoIamgeName;
    }

    public int getArrowImageName() {
        return arrowImageName;
    }

    public void setArrowImageName(int arrowImageName) {
        this.arrowImageName = arrowImageName;
    }
}
