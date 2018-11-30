package com.buyint.mergerbot.dto;

import java.io.Serializable;

/**
 * created by licheng  on date 2018/8/9
 */
public class ContactAddRequest implements Serializable {

    private ContactPersonInfoBean personInfo;

    private ContactPersonRelation personRelation;

    private ContactworkMateRelation workMateRelation;

    public ContactPersonInfoBean getPersonInfo() {
        return personInfo;
    }

    public void setPersonInfo(ContactPersonInfoBean personInfo) {
        this.personInfo = personInfo;
    }

    public ContactPersonRelation getPersonRelation() {
        return personRelation;
    }

    public void setPersonRelation(ContactPersonRelation personRelation) {
        this.personRelation = personRelation;
    }

    public ContactworkMateRelation getWorkMateRelation() {
        return workMateRelation;
    }

    public void setWorkMateRelation(ContactworkMateRelation workMateRelation) {
        this.workMateRelation = workMateRelation;
    }
}
