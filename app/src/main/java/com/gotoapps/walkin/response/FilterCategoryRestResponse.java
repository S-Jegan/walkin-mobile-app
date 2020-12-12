package com.gotoapps.walkin.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class FilterCategoryRestResponse implements Parcelable {

    @SerializedName("name")
    private String name;

    @SerializedName("count")
    private long count;

    @SerializedName("filtered")
    private boolean filtered;

    public FilterCategoryRestResponse()
    {

    }
    public FilterCategoryRestResponse(String name, long count, boolean filtered) {
        this.name = name;
        this.count = count;
        this.filtered = filtered;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public boolean isFiltered() {
        return filtered;
    }

    public void setFiltered(boolean filtered) {
        this.filtered = filtered;
    }

    @Override
    public String toString() {
        return "FilterCategoryRestResponse{" +
                "name='" + name + '\'' +
                ", count=" + count +
                ", filtered=" + filtered +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeLong(this.count);
        dest.writeByte(this.filtered ? (byte) 1 : (byte) 0);
    }

    protected FilterCategoryRestResponse(Parcel in) {
        this.name = in.readString();
        this.count = in.readLong();
        this.filtered = in.readByte() != 0;
    }

    public static final Creator<FilterCategoryRestResponse> CREATOR = new Creator<FilterCategoryRestResponse>() {
        @Override
        public FilterCategoryRestResponse createFromParcel(Parcel source) {
            return new FilterCategoryRestResponse(source);
        }

        @Override
        public FilterCategoryRestResponse[] newArray(int size) {
            return new FilterCategoryRestResponse[size];
        }
    };
}
