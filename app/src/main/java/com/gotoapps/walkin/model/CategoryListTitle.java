package com.gotoapps.walkin.model;

import java.util.ArrayList;
import java.util.List;

public class CategoryListTitle {

    private String categoryName;

    private List<Category> categoryList;

    public CategoryListTitle() {
    }

    public CategoryListTitle(String categoryName, List<Category> categoryList) {
        this.categoryName = categoryName;
        this.categoryList = categoryList;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public List<Category> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<Category> categoryList) {
        this.categoryList = categoryList;
    }

    @Override
    public String toString() {
        return "CategoryListTitle{" +
                "categoryName='" + categoryName + '\'' +
                ", categoryList=" + categoryList +
                '}';
    }
}
