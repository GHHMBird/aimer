package com.buyint.mergerbot.dto;

import java.io.Serializable;

/**
 * created by licheng  on date 2018/8/16
 */
public class LayerListBean implements Serializable {

    private  String company;
    private  String email ;
    private  String id;
    private  String name ;
    private  String position ;
    private  String source ;

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
