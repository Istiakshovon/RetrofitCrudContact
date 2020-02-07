package com.istiak.retrofitcrudcontact.model;

import com.google.gson.annotations.SerializedName;

public class Contacts {
    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("relativeName")
    private String relativeName;
    @SerializedName("contact")
    private String relativeContact;
    @SerializedName("address")
    private String relativeAddress;

    @SerializedName("password")
    private String password;

    @SerializedName("value")
    private String value;
    @SerializedName("message")
    private String massage;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getRelativeName() {
        return relativeName;
    }
    public String getContact() {
        return relativeContact;
    }
    public String getAddress() {
        return relativeAddress;
    }
    public String getPassword() {
        return password;
    }


    public String getValue() {
        return value;
    }

    public String getMassage() {
        return massage;
    }
}
