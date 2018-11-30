package com.buyint.mergerbot.dto;

import com.buyint.mergerbot.http.RequestData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AddSecretaryRequest extends RequestData implements Serializable {

    @Expose
    @SerializedName("email")
    public String email;

    @Expose
    @SerializedName("company")
    public String company;

    @Expose
    @SerializedName("name")
    public String name;

    @Expose
    @SerializedName("password")
    public String password;

    @Expose
    @SerializedName("phone")
    public String phone;

    @Expose
    @SerializedName("title")
    public String title;


}