package com.buyint.mergerbot.dto;

import java.io.Serializable;

/**
 * created by licheng  on date 2018/8/9
 */
public class ContactPersonRelation implements Serializable {

    private String description;
    private long endDate;
    private String projectName;
    private long startDate;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }



    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Long getEndDate() {
        return endDate;
    }

    public void setEndDate(Long endDate) {
        this.endDate = endDate;
    }

    public Long getStartDate() {
        return startDate;
    }

    public void setStartDate(Long startDate) {
        this.startDate = startDate;
    }
}
