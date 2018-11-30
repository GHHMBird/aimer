package com.buyint.mergerbot.dto

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Created by huheming on 2018/9/5
 */
class ContactBean : Serializable {

    @Expose
    @SerializedName("name")
    var name: String? = null

    @Expose
    @SerializedName("phone")
    var phone: String? = null

    var prefix: String? = null
    var firstName: String? = null
    var middleName: String? = null
    var lastname: String? = null
    var suffix: String? = null
    var phoneticFirstName: String? = null
    var phoneticMiddleName: String? = null
    var phoneticLastName: String? = null

    var mobile: String? = null
    var homeNum: String? = null
    var jobNum: String? = null
    var workFax: String? = null
    var homeFax: String? = null
    var pager: String? = null
    var quickNum: String? = null
    var jobTel: String? = null
    var carNum: String? = null
    var isdn: String? = null
    var tel: String? = null
    var wirelessDev: String? = null
    var telegram: String? = null
    var tty_tdd: String? = null
    var jobMobile: String? = null
    var jobPager: String? = null
    var assistantNum: String? = null
    var mms: String? = null
    var mobileEmail: String? = null

    var birthday: String? = null
    var anniversary: String? = null
    var workMsg: String? = null
    var msn: String? = null
    var qq: String? = null
    var remark: String? = null
    var nickName: String? = null
    var company: String? = null
    var jobTitle: String? = null
    var department: String? = null
    var custom: String? = null
    var home: String? = null
    var homePage: String? = null
    var workPage: String? = null
    var street: String? = null
    var city: String? = null
    var box: String? = null
    var area: String? = null
    var state: String? = null
    var zip: String? = null
    var country: String? = null
    var homeStreet: String? = null
    var homeCity: String? = null
    var homeBox: String? = null
    var homeArea: String? = null
    var homeState: String? = null
    var homeZip: String? = null
    var homeCountry: String? = null
    var otherStreet: String? = null
    var otherCity: String? = null
    var otherBox: String? = null
    var otherArea: String? = null
    var otherState: String? = null
    var otherZip: String? = null
    var otherCountry: String? = null
}