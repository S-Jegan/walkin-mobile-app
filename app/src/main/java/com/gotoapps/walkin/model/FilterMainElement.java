package com.gotoapps.walkin.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class FilterMainElement implements Parcelable {
    String filterElementName;
    List<FilterSubElement> filterSubElements;

    public FilterMainElement(String filterElementName, List<FilterSubElement> filterSubElements) {
        this.filterElementName = filterElementName;
        this.filterSubElements = filterSubElements;
    }

    public FilterMainElement() {
    }

    public String getFilterElementName() {
        return filterElementName;
    }

    public void setFilterElementName(String filterElementName) {
        this.filterElementName = filterElementName;
    }

    public List<FilterSubElement> getFilterSubElements() {
        return filterSubElements;
    }

    public void setFilterSubElements(List<FilterSubElement> filterSubElements) {
        this.filterSubElements = filterSubElements;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.filterElementName);
        dest.writeTypedList(this.filterSubElements);
    }

    protected FilterMainElement(Parcel in) {
        this.filterElementName = in.readString();
        this.filterSubElements = in.createTypedArrayList(FilterSubElement.CREATOR);
    }

    public static final Parcelable.Creator<FilterMainElement> CREATOR = new Parcelable.Creator<FilterMainElement>() {
        @Override
        public FilterMainElement createFromParcel(Parcel source) {
            return new FilterMainElement(source);
        }

        @Override
        public FilterMainElement[] newArray(int size) {
            return new FilterMainElement[size];
        }
    };

    @Override
    public String toString() {
        return "FilterMainElement{" +
                "filterElementName='" + filterElementName + '\'' +
                ", filterSubElements=" + filterSubElements +
                '}';
    }
}
