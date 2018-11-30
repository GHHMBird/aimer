package com.buyint.mergerbot.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SecretaryBean implements Serializable {



    @Expose
    @SerializedName("authId")
    public String authId;


    @Expose
    @SerializedName("company")
    public String company;


    @Expose
    @SerializedName("name")
    public String name;


    @Expose
    @SerializedName("title")
    public String title;


    @Expose
    @SerializedName("uuid")
    public String uuid;


}