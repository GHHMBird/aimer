package com.buyint.mergerbot.Utility;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Email;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.StructuredName;
import android.text.TextUtils;
import android.util.Log;

import com.buyint.mergerbot.dto.ContactBean;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * @author larson
 */
public class ContactUtil {

    /**
     * 获取联系人信息，并把数据转换成json数据
     */
    public static ArrayList<ContactBean> getContactInfo(Context context) {
        ArrayList<ContactBean> contactData = new ArrayList();
        String mimetype;
        ContactBean bean = new ContactBean();
        int oldrid = -1;
        int contactId;
        // 1.查询通讯录所有联系人信息，通过id排序，我们看下android联系人的表就知道，所有的联系人的数据是由RAW_CONTACT_ID来索引开的  
        // 所以，先获取所有的人的RAW_CONTACT_ID  
        Uri uri = ContactsContract.Data.CONTENT_URI; // 联系人Uri；
        Cursor cursor = context.getContentResolver().query(uri, null, null, null, ContactsContract.Data.RAW_CONTACT_ID);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                contactId = cursor.getInt(cursor.getColumnIndex(ContactsContract.Data.RAW_CONTACT_ID));
                if (oldrid != contactId) {
                    bean = new ContactBean();
                    contactData.add(bean);
                    oldrid = contactId;
                }
                mimetype = cursor.getString(cursor.getColumnIndex(ContactsContract.Data.MIMETYPE)); // 取得mimetype类型,扩展的数据都在这个类型里面
                // 1.1,拿到联系人的各种名字
                if (StructuredName.CONTENT_ITEM_TYPE.equals(mimetype)) {
                    cursor.getString(cursor.getColumnIndex(StructuredName.DISPLAY_NAME));
                    bean.setPrefix(cursor.getString(cursor.getColumnIndex(StructuredName.PREFIX)));
                    bean.setFirstName(cursor.getString(cursor.getColumnIndex(StructuredName.FAMILY_NAME)));
                    bean.setMiddleName(cursor.getString(cursor.getColumnIndex(StructuredName.MIDDLE_NAME)));
                    bean.setLastname(cursor.getString(cursor.getColumnIndex(StructuredName.GIVEN_NAME)));
                    bean.setSuffix(cursor.getString(cursor.getColumnIndex(StructuredName.SUFFIX)));
                    bean.setPhoneticFirstName(cursor.getString(cursor.getColumnIndex(StructuredName.PHONETIC_FAMILY_NAME)));
                    bean.setPhoneticMiddleName(cursor.getString(cursor.getColumnIndex(StructuredName.PHONETIC_MIDDLE_NAME)));
                    bean.setPhoneticLastName(cursor.getString(cursor.getColumnIndex(StructuredName.PHONETIC_GIVEN_NAME)));
                    //整合为一个名字
                    String name = "";
                    if (!TextUtils.isEmpty(bean.getFirstName())) {
                        name += bean.getFirstName();
                    }
                    if (!TextUtils.isEmpty(bean.getLastname())) {
                        name += bean.getLastname();
                    }
                    bean.setName(name);
                }
                // 1.2 获取各种电话信息
                if (Phone.CONTENT_ITEM_TYPE.equals(mimetype)) {
                    int phoneType = cursor.getInt(cursor.getColumnIndex(Phone.TYPE)); // 手机
                    if (phoneType == Phone.TYPE_MOBILE)
                        bean.setMobile(cursor.getString(cursor.getColumnIndex(Phone.NUMBER)));
                    // 住宅电话
                    if (phoneType == Phone.TYPE_HOME)
                        bean.setHomeNum(cursor.getString(cursor.getColumnIndex(Phone.NUMBER)));
                    // 单位电话
                    if (phoneType == Phone.TYPE_WORK)
                        bean.setJobNum(cursor.getString(cursor.getColumnIndex(Phone.NUMBER)));
                    // 单位传真
                    if (phoneType == Phone.TYPE_FAX_WORK)
                        bean.setWorkFax(cursor.getString(cursor.getColumnIndex(Phone.NUMBER)));
                    // 住宅传真
                    if (phoneType == Phone.TYPE_FAX_HOME)
                        bean.setHomeFax(cursor.getString(cursor.getColumnIndex(Phone.NUMBER)));
                    // 寻呼机
                    if (phoneType == Phone.TYPE_PAGER)
                        bean.setPager(cursor.getString(cursor.getColumnIndex(Phone.NUMBER)));
                    // 回拨号码
                    if (phoneType == Phone.TYPE_CALLBACK)
                        bean.setQuickNum(cursor.getString(cursor.getColumnIndex(Phone.NUMBER)));
                    // 公司总机
                    if (phoneType == Phone.TYPE_COMPANY_MAIN)
                        bean.setJobTel(cursor.getString(cursor.getColumnIndex(Phone.NUMBER)));
                    // 车载电话
                    if (phoneType == Phone.TYPE_CAR)
                        bean.setCarNum(cursor.getString(cursor.getColumnIndex(Phone.NUMBER)));
                    // ISDN
                    if (phoneType == Phone.TYPE_ISDN)
                        bean.setIsdn(cursor.getString(cursor.getColumnIndex(Phone.NUMBER)));
                    // 总机
                    if (phoneType == Phone.TYPE_MAIN)
                        bean.setTel(cursor.getString(cursor.getColumnIndex(Phone.NUMBER)));
                    // 无线装置
                    if (phoneType == Phone.TYPE_RADIO)
                        bean.setWirelessDev(cursor.getString(cursor.getColumnIndex(Phone.NUMBER)));
                    // 电报
                    if (phoneType == Phone.TYPE_TELEX)
                        bean.setTelegram(cursor.getString(cursor.getColumnIndex(Phone.NUMBER)));
                    // TTY_TDD
                    if (phoneType == Phone.TYPE_TTY_TDD)
                        bean.setTty_tdd(cursor.getString(cursor.getColumnIndex(Phone.NUMBER)));
                    // 单位手机
                    if (phoneType == Phone.TYPE_WORK_MOBILE)
                        bean.setJobMobile(cursor.getString(cursor.getColumnIndex(Phone.NUMBER)));
                    // 单位寻呼机
                    if (phoneType == Phone.TYPE_WORK_PAGER)
                        bean.setJobPager(cursor.getString(cursor.getColumnIndex(Phone.NUMBER)));
                    // 助理
                    if (phoneType == Phone.TYPE_ASSISTANT)
                        bean.setAssistantNum(cursor.getString(cursor.getColumnIndex(Phone.NUMBER)));
                    // 彩信
                    if (phoneType == Phone.TYPE_MMS)
                        bean.setMms(cursor.getString(cursor.getColumnIndex(Phone.NUMBER)));
                    bean.setMobileEmail(cursor.getString(cursor.getColumnIndex(Email.DATA)));
                    if (!TextUtils.isEmpty(bean.getMobile())) {
                        bean.setPhone(bean.getMobile());
                    }
                }
                // 查找event地址
                if (ContactsContract.CommonDataKinds.Event.CONTENT_ITEM_TYPE.equals(mimetype)) { // 取出时间类型
                    int eventType = cursor.getInt(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Event.TYPE)); // 生日
                    if (eventType == ContactsContract.CommonDataKinds.Event.TYPE_BIRTHDAY)
                        bean.setBirthday(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Event.START_DATE)));
                    // 周年纪念日
                    if (eventType == ContactsContract.CommonDataKinds.Event.TYPE_ANNIVERSARY)
                        bean.setAnniversary(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Event.START_DATE)));
                    // 获取即时通讯消息
                    if (ContactsContract.CommonDataKinds.Im.CONTENT_ITEM_TYPE.equals(mimetype)) { // 取出即时消息类型
                        int protocal = cursor.getInt(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Im.PROTOCOL));
                        if (ContactsContract.CommonDataKinds.Im.TYPE_CUSTOM == protocal)
                            bean.setWorkMsg(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Im.DATA)));
                        if (ContactsContract.CommonDataKinds.Im.PROTOCOL_MSN == protocal)
                            bean.setMsn(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Im.DATA)));
                        if (ContactsContract.CommonDataKinds.Im.PROTOCOL_QQ == protocal)
                            bean.setQq(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Im.DATA)));
                    }
                    // 获取备注信息
                    if (ContactsContract.CommonDataKinds.Note.CONTENT_ITEM_TYPE.equals(mimetype))
                        bean.setRemark(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Note.NOTE)));
                    // 获取昵称信息
                    if (ContactsContract.CommonDataKinds.Nickname.CONTENT_ITEM_TYPE.equals(mimetype))
                        bean.setNickName(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Nickname.NAME)));
                    // 获取组织信息
                    if (ContactsContract.CommonDataKinds.Organization.CONTENT_ITEM_TYPE.equals(mimetype)) { // 取出组织类型
                        int orgType = cursor.getInt(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Organization.TYPE)); // 单位
                        if (orgType == ContactsContract.CommonDataKinds.Organization.TYPE_CUSTOM) {
                            bean.setCompany(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Organization.COMPANY)));
                            bean.setJobTitle(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Organization.TITLE)));
                            bean.setDepartment(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Organization.DEPARTMENT)));
                        }
                    }
                    // 获取网站信息
                    if (ContactsContract.CommonDataKinds.Website.CONTENT_ITEM_TYPE.equals(mimetype)) { // 取出组织类型
                        int webType = cursor.getInt(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Website.TYPE)); // 主页
                        if (webType == ContactsContract.CommonDataKinds.Website.TYPE_CUSTOM)
                            bean.setCustom(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Website.URL)));
                        // 主页
                        if (webType == ContactsContract.CommonDataKinds.Website.TYPE_HOME)
                            bean.setHome(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Website.URL)));
                        // 个人主页
                        if (webType == ContactsContract.CommonDataKinds.Website.TYPE_HOMEPAGE)
                            bean.setHomePage(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Website.URL)));
                        // 工作主页
                        if (webType == ContactsContract.CommonDataKinds.Website.TYPE_WORK)
                            bean.setWorkPage(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Website.URL)));
                    }
                    // 查找通讯地址
                    if (ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_ITEM_TYPE.equals(mimetype)) { // 取出邮件类型
                        int postalType = cursor.getInt(cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.TYPE)); // 单位通讯地址
                        if (postalType == ContactsContract.CommonDataKinds.StructuredPostal.TYPE_WORK) {
                            bean.setStreet(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.STREET)));
                            bean.setCity(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.CITY)));
                            bean.setBox(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.POBOX)));
                            bean.setArea(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.NEIGHBORHOOD)));
                            bean.setState(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.REGION)));
                            bean.setZip(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.POSTCODE)));
                            bean.setCountry(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.COUNTRY)));
                        }
                        // 住宅通讯地址
                        if (postalType == ContactsContract.CommonDataKinds.StructuredPostal.TYPE_HOME) {
                            bean.setHomeStreet(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.STREET)));
                            bean.setHomeCity(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.CITY)));
                            bean.setHomeBox(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.POBOX)));
                            bean.setHomeArea(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.NEIGHBORHOOD)));
                            bean.setHomeState(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.REGION)));
                            bean.setHomeZip(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.POSTCODE)));
                            bean.setHomeCountry(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.COUNTRY)));
                        }
                        // 其他通讯地址
                        if (postalType == ContactsContract.CommonDataKinds.StructuredPostal.TYPE_OTHER) {
                            bean.setOtherStreet(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.STREET)));
                            bean.setOtherCity(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.CITY)));
                            bean.setOtherBox(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.POBOX)));
                            bean.setOtherArea(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.NEIGHBORHOOD)));
                            bean.setOtherState(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.REGION)));
                            bean.setOtherZip(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.POSTCODE)));
                            bean.setOtherCountry(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.COUNTRY)));
                        }
                    }
                }
            }
            cursor.close();
        }
        return contactData;
    }

    /**
     * 获取联系人信息，并把数据转换成json数据
     */
    public static String getContactInfo2Json(Context context) throws JSONException {
        JSONObject contactData = new JSONObject();
        JSONObject jsonObject = null;
        String mimetype = "";
        int oldrid = -1;
        int contactId = -1;
        // 1.查询通讯录所有联系人信息，通过id排序，我们看下android联系人的表就知道，所有的联系人的数据是由RAW_CONTACT_ID来索引开的
        // 所以，先获取所有的人的RAW_CONTACT_ID
        Uri uri = ContactsContract.Data.CONTENT_URI; // 联系人Uri；
        Cursor cursor = context.getContentResolver().query(uri,
                null, null, null, ContactsContract.Data.RAW_CONTACT_ID);
        int numm = 0;
        while (cursor.moveToNext()) {
            contactId = cursor.getInt(cursor
                    .getColumnIndex(ContactsContract.Data.RAW_CONTACT_ID));
            if (oldrid != contactId) {
                jsonObject = new JSONObject();
                contactData.put("contact" + numm, jsonObject);
                numm++;
                oldrid = contactId;
            }
            mimetype = cursor.getString(cursor.getColumnIndex(ContactsContract.Data.MIMETYPE)); // 取得mimetype类型,扩展的数据都在这个类型里面
            // 1.1,拿到联系人的各种名字
            if (StructuredName.CONTENT_ITEM_TYPE.equals(mimetype)) {
                cursor.getString(cursor
                        .getColumnIndex(StructuredName.DISPLAY_NAME));
                String prefix = cursor.getString(cursor
                        .getColumnIndex(StructuredName.PREFIX));
                jsonObject.put("prefix", prefix);
                String firstName = cursor.getString(cursor
                        .getColumnIndex(StructuredName.FAMILY_NAME));
                jsonObject.put("firstName", firstName);
                String middleName = cursor.getString(cursor
                        .getColumnIndex(StructuredName.MIDDLE_NAME));
                jsonObject.put("middleName", middleName);
                String lastname = cursor.getString(cursor
                        .getColumnIndex(StructuredName.GIVEN_NAME));
                jsonObject.put("lastname", lastname);
                String suffix = cursor.getString(cursor
                        .getColumnIndex(StructuredName.SUFFIX));
                jsonObject.put("suffix", suffix);
                String phoneticFirstName = cursor.getString(cursor
                        .getColumnIndex(StructuredName.PHONETIC_FAMILY_NAME));
                jsonObject.put("phoneticFirstName", phoneticFirstName);

                String phoneticMiddleName = cursor.getString(cursor
                        .getColumnIndex(StructuredName.PHONETIC_MIDDLE_NAME));
                jsonObject.put("phoneticMiddleName", phoneticMiddleName);
                String phoneticLastName = cursor.getString(cursor
                        .getColumnIndex(StructuredName.PHONETIC_GIVEN_NAME));
                jsonObject.put("phoneticLastName", phoneticLastName);
            }
            // 1.2 获取各种电话信息
            if (Phone.CONTENT_ITEM_TYPE.equals(mimetype)) {
                int phoneType = cursor
                        .getInt(cursor.getColumnIndex(Phone.TYPE)); // 手机
                if (phoneType == Phone.TYPE_MOBILE) {
                    String mobile = cursor.getString(cursor
                            .getColumnIndex(Phone.NUMBER));
                    jsonObject.put("mobile", mobile);
                }
                // 住宅电话
                if (phoneType == Phone.TYPE_HOME) {
                    String homeNum = cursor.getString(cursor
                            .getColumnIndex(Phone.NUMBER));
                    jsonObject.put("homeNum", homeNum);
                }
                // 单位电话
                if (phoneType == Phone.TYPE_WORK) {
                    String jobNum = cursor.getString(cursor
                            .getColumnIndex(Phone.NUMBER));
                    jsonObject.put("jobNum", jobNum);
                }
                // 单位传真
                if (phoneType == Phone.TYPE_FAX_WORK) {
                    String workFax = cursor.getString(cursor
                            .getColumnIndex(Phone.NUMBER));
                    jsonObject.put("workFax", workFax);
                }
                // 住宅传真
                if (phoneType == Phone.TYPE_FAX_HOME) {
                    String homeFax = cursor.getString(cursor
                            .getColumnIndex(Phone.NUMBER));

                    jsonObject.put("homeFax", homeFax);
                } // 寻呼机
                if (phoneType == Phone.TYPE_PAGER) {
                    String pager = cursor.getString(cursor
                            .getColumnIndex(Phone.NUMBER));
                    jsonObject.put("pager", pager);
                }
                // 回拨号码
                if (phoneType == Phone.TYPE_CALLBACK) {
                    String quickNum = cursor.getString(cursor
                            .getColumnIndex(Phone.NUMBER));
                    jsonObject.put("quickNum", quickNum);
                }
                // 公司总机
                if (phoneType == Phone.TYPE_COMPANY_MAIN) {
                    String jobTel = cursor.getString(cursor
                            .getColumnIndex(Phone.NUMBER));
                    jsonObject.put("jobTel", jobTel);
                }
                // 车载电话
                if (phoneType == Phone.TYPE_CAR) {
                    String carNum = cursor.getString(cursor
                            .getColumnIndex(Phone.NUMBER));
                    jsonObject.put("carNum", carNum);
                } // ISDN
                if (phoneType == Phone.TYPE_ISDN) {
                    String isdn = cursor.getString(cursor
                            .getColumnIndex(Phone.NUMBER));
                    jsonObject.put("isdn", isdn);
                } // 总机
                if (phoneType == Phone.TYPE_MAIN) {
                    String tel = cursor.getString(cursor
                            .getColumnIndex(Phone.NUMBER));
                    jsonObject.put("tel", tel);
                }
                // 无线装置
                if (phoneType == Phone.TYPE_RADIO) {
                    String wirelessDev = cursor.getString(cursor
                            .getColumnIndex(Phone.NUMBER));

                    jsonObject.put("wirelessDev", wirelessDev);
                } // 电报
                if (phoneType == Phone.TYPE_TELEX) {
                    String telegram = cursor.getString(cursor
                            .getColumnIndex(Phone.NUMBER));
                    jsonObject.put("telegram", telegram);
                }
                // TTY_TDD
                if (phoneType == Phone.TYPE_TTY_TDD) {
                    String tty_tdd = cursor.getString(cursor
                            .getColumnIndex(Phone.NUMBER));
                    jsonObject.put("tty_tdd", tty_tdd);
                }
                // 单位手机
                if (phoneType == Phone.TYPE_WORK_MOBILE) {
                    String jobMobile = cursor.getString(cursor
                            .getColumnIndex(Phone.NUMBER));
                    jsonObject.put("jobMobile", jobMobile);
                }
                // 单位寻呼机
                if (phoneType == Phone.TYPE_WORK_PAGER) {
                    String jobPager = cursor.getString(cursor
                            .getColumnIndex(Phone.NUMBER));
                    jsonObject.put("jobPager", jobPager);
                } // 助理
                if (phoneType == Phone.TYPE_ASSISTANT) {
                    String assistantNum = cursor.getString(cursor
                            .getColumnIndex(Phone.NUMBER));
                    jsonObject.put("assistantNum", assistantNum);
                } // 彩信
                if (phoneType == Phone.TYPE_MMS) {
                    String mms = cursor.getString(cursor
                            .getColumnIndex(Phone.NUMBER));
                    jsonObject.put("mms", mms);
                }

                String mobileEmail = cursor.getString(cursor
                        .getColumnIndex(Email.DATA));
                jsonObject.put("mobileEmail", mobileEmail);
            }
        }
        // 查找event地址
        if (ContactsContract.CommonDataKinds.Event.CONTENT_ITEM_TYPE.equals(mimetype)) { // 取出时间类型
            int eventType = cursor.getInt(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Event.TYPE)); // 生日
            if (eventType == ContactsContract.CommonDataKinds.Event.TYPE_BIRTHDAY) {
                String birthday = cursor.getString(cursor
                        .getColumnIndex(ContactsContract.CommonDataKinds.Event.START_DATE));
                jsonObject.put("birthday", birthday);
            }
            // 周年纪念日
            if (eventType == ContactsContract.CommonDataKinds.Event.TYPE_ANNIVERSARY) {
                String anniversary = cursor.getString(cursor
                        .getColumnIndex(ContactsContract.CommonDataKinds.Event.START_DATE));
                jsonObject.put("anniversary", anniversary);
            }
        }
        // 获取即时通讯消息
        if (ContactsContract.CommonDataKinds.Im.CONTENT_ITEM_TYPE.equals(mimetype)) { // 取出即时消息类型
            int protocal = cursor.getInt(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Im.PROTOCOL));
            if (ContactsContract.CommonDataKinds.Im.TYPE_CUSTOM == protocal) {
                String workMsg = cursor.getString(cursor
                        .getColumnIndex(ContactsContract.CommonDataKinds.Im.DATA));
                jsonObject.put("workMsg", workMsg);
            } else if (ContactsContract.CommonDataKinds.Im.PROTOCOL_MSN == protocal) {
                String workMsg = cursor.getString(cursor
                        .getColumnIndex(ContactsContract.CommonDataKinds.Im.DATA));
                jsonObject.put("workMsg", workMsg);
            }
            if (ContactsContract.CommonDataKinds.Im.PROTOCOL_QQ == protocal) {
                String instantsMsg = cursor.getString(cursor
                        .getColumnIndex(ContactsContract.CommonDataKinds.Im.DATA));

                jsonObject.put("instantsMsg", instantsMsg);
            }
        }
        // 获取备注信息
        if (ContactsContract.CommonDataKinds.Note.CONTENT_ITEM_TYPE.equals(mimetype)) {
            String remark = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Note.NOTE));
            jsonObject.put("remark", remark);
        }
        // 获取昵称信息
        if (ContactsContract.CommonDataKinds.Nickname.CONTENT_ITEM_TYPE.equals(mimetype)) {
            String nickName = cursor.getString(cursor
                    .getColumnIndex(ContactsContract.CommonDataKinds.Nickname.NAME));
            jsonObject.put("nickName", nickName);
        }
        // 获取组织信息
        if (ContactsContract.CommonDataKinds.Organization.CONTENT_ITEM_TYPE.equals(mimetype)) { // 取出组织类型
            int orgType = cursor.getInt(cursor
                    .getColumnIndex(ContactsContract.CommonDataKinds.Organization.TYPE)); // 单位
            if (orgType == ContactsContract.CommonDataKinds.Organization.TYPE_CUSTOM) { // if (orgType ==
                // Organization.TYPE_WORK)
                // {
                String company = cursor.getString(cursor
                        .getColumnIndex(ContactsContract.CommonDataKinds.Organization.COMPANY));
                jsonObject.put("company", company);
                String jobTitle = cursor.getString(cursor
                        .getColumnIndex(ContactsContract.CommonDataKinds.Organization.TITLE));
                jsonObject.put("jobTitle", jobTitle);
                String department = cursor.getString(cursor
                        .getColumnIndex(ContactsContract.CommonDataKinds.Organization.DEPARTMENT));
                jsonObject.put("department", department);
            }
        }
        // 获取网站信息
        if (ContactsContract.CommonDataKinds.Website.CONTENT_ITEM_TYPE.equals(mimetype)) { // 取出组织类型
            int webType = cursor.getInt(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Website.TYPE)); // 主页
            if (webType == ContactsContract.CommonDataKinds.Website.TYPE_CUSTOM) {

                String home = cursor.getString(cursor
                        .getColumnIndex(ContactsContract.CommonDataKinds.Website.URL));
                jsonObject.put("home", home);
            } // 主页
            else if (webType == ContactsContract.CommonDataKinds.Website.TYPE_HOME) {
                String home = cursor.getString(cursor
                        .getColumnIndex(ContactsContract.CommonDataKinds.Website.URL));
                jsonObject.put("home", home);
            }
            // 个人主页
            if (webType == ContactsContract.CommonDataKinds.Website.TYPE_HOMEPAGE) {
                String homePage = cursor.getString(cursor
                        .getColumnIndex(ContactsContract.CommonDataKinds.Website.URL));
                jsonObject.put("homePage", homePage);
            }
            // 工作主页
            if (webType == ContactsContract.CommonDataKinds.Website.TYPE_WORK) {
                String workPage = cursor.getString(cursor
                        .getColumnIndex(ContactsContract.CommonDataKinds.Website.URL));
                jsonObject.put("workPage", workPage);
            }
        }
        // 查找通讯地址
        if (ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_ITEM_TYPE.equals(mimetype)) { // 取出邮件类型
            int postalType = cursor.getInt(cursor
                    .getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.TYPE)); // 单位通讯地址
            if (postalType == ContactsContract.CommonDataKinds.StructuredPostal.TYPE_WORK) {
                String street = cursor.getString(cursor
                        .getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.STREET));
                jsonObject.put("street", street);
                String ciry = cursor.getString(cursor
                        .getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.CITY));
                jsonObject.put("ciry", ciry);
                String box = cursor.getString(cursor
                        .getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.POBOX));
                jsonObject.put("box", box);
                String area = cursor.getString(cursor
                        .getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.NEIGHBORHOOD));
                jsonObject.put("area", area);

                String state = cursor.getString(cursor
                        .getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.REGION));
                jsonObject.put("state", state);
                String zip = cursor.getString(cursor
                        .getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.POSTCODE));
                jsonObject.put("zip", zip);
                String country = cursor.getString(cursor
                        .getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.COUNTRY));
                jsonObject.put("country", country);
            }
            // 住宅通讯地址
            if (postalType == ContactsContract.CommonDataKinds.StructuredPostal.TYPE_HOME) {
                String homeStreet = cursor.getString(cursor
                        .getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.STREET));
                jsonObject.put("homeStreet", homeStreet);
                String homeCity = cursor.getString(cursor
                        .getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.CITY));
                jsonObject.put("homeCity", homeCity);
                String homeBox = cursor.getString(cursor
                        .getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.POBOX));
                jsonObject.put("homeBox", homeBox);
                String homeArea = cursor.getString(cursor
                        .getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.NEIGHBORHOOD));
                jsonObject.put("homeArea", homeArea);
                String homeState = cursor.getString(cursor
                        .getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.REGION));
                jsonObject.put("homeState", homeState);
                String homeZip = cursor.getString(cursor
                        .getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.POSTCODE));
                jsonObject.put("homeZip", homeZip);
                String homeCountry = cursor.getString(cursor
                        .getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.COUNTRY));
                jsonObject.put("homeCountry", homeCountry);
            }
            // 其他通讯地址
            if (postalType == ContactsContract.CommonDataKinds.StructuredPostal.TYPE_OTHER) {
                String otherStreet = cursor.getString(cursor
                        .getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.STREET));
                jsonObject.put("otherStreet", otherStreet);

                String otherCity = cursor.getString(cursor
                        .getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.CITY));
                jsonObject.put("otherCity", otherCity);
                String otherBox = cursor.getString(cursor
                        .getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.POBOX));
                jsonObject.put("otherBox", otherBox);
                String otherArea = cursor.getString(cursor
                        .getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.NEIGHBORHOOD));
                jsonObject.put("otherArea", otherArea);
                String otherState = cursor.getString(cursor
                        .getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.REGION));
                jsonObject.put("otherState", otherState);
                String otherZip = cursor.getString(cursor
                        .getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.POSTCODE));
                jsonObject.put("otherZip", otherZip);
                String otherCountry = cursor.getString(cursor
                        .getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.COUNTRY));
                jsonObject.put("otherCountry", otherCountry);
            }
        }
        cursor.close();
        Log.i("contactData", contactData.toString());
        return contactData.toString();
    }


}