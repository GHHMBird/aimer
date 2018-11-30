package com.buyint.mergerbot.UIs.contact.data;

import com.buyint.mergerbot.dto.ContactAddRequest;
import com.buyint.mergerbot.helper.HttpHelper;

import io.reactivex.Observable;

/**
 * created by licheng  on date 2018/8/7
 */
public class ContactMateRepository {

    public Observable<Boolean> getContactAdd(ContactAddRequest contactAddRequest) {
        return HttpHelper.getContactadd(contactAddRequest);
    }

    public Observable<Boolean> putContactPut(ContactAddRequest contactAddRequest) {
        return HttpHelper.putContactput(contactAddRequest);
    }
}
