package com.buyint.mergerbot.dto;

import java.io.Serializable;

/**
 * created by licheng  on date 2018/8/9
 */
public class ContactworkMateRelation implements Serializable {

    private String companyName;
    private String departmentName;
    private long endDate;
    private boolean partnership;
    private boolean sameDepartment;
    private long startDate;


    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }


    public Boolean getPartnership() {
        return partnership;
    }

    public void setPartnership(Boolean partnership) {
        this.partnership = partnership;
    }

    public Boolean getSameDepartment() {
        return sameDepartment;
    }

    public void setSameDepartment(Boolean sameDepartment) {
        this.sameDepartment = sameDepartment;
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
