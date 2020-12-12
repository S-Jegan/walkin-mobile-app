package com.gotoapps.walkin.model;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class FilterSubElement implements Parcelable {
    String filterItemName;
    int filterItemCount;

    public FilterSubElement(String filterItemName, int filterItemCount) {
        this.filterItemName = filterItemName;
        this.filterItemCount = filterItemCount;
    }

    public FilterSubElement() {
    }

    public String getFilterItemName() {
        return filterItemName;
    }

    public void setFilterItemName(String filterItemName) {
        this.filterItemName = filterItemName;
    }

    public int getFilterItemCount() {
        return filterItemCount;
    }

    public void setFilterItemCount(int filterItemCount) {
        this.filterItemCount = filterItemCount;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.filterItemName);
        dest.writeInt(this.filterItemCount);
    }

    protected FilterSubElement(Parcel in) {
        this.filterItemName = in.readString();
        this.filterItemCount = in.readInt();
    }

    public static final Parcelable.Creator<FilterSubElement> CREATOR = new Parcelable.Creator<FilterSubElement>() {
        @Override
        public FilterSubElement createFromParcel(Parcel source) {
            return new FilterSubElement(source);
        }

        @Override
        public FilterSubElement[] newArray(int size) {
            return new FilterSubElement[size];
        }
    };

    @Override
    public String toString() {
        return "FilterSubElement{" +
                "filterItemName='" + filterItemName + '\'' +
                ", filterItemCount=" + filterItemCount +
                '}';
    }
}
