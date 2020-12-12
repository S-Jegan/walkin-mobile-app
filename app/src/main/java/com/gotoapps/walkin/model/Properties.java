package com.gotoapps.walkin.model;

import com.google.gson.annotations.SerializedName;

/**
 * Property Model Class to hold the app properties - Retrieved from REST Service
 * Author : Jegan Pandiyan
 */
public class Properties {

    @SerializedName("key")
    private String key;

    @SerializedName("value")
    private String value;

    public Properties(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getkey() {
        return key;
    }

    public void setkey(String key) {
        this.key = key;
    }

    public String getvalue() {
        return value;
    }

    public void setvalue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Properties{" +
                "key='" + key + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
