package com.buyint.mergerbot.UIs.contact.mvp;

import com.buyint.mergerbot.helper.HttpHelper;

/**
 * created by licheng  on date 2018/8/7
 */
public class ContactAddFirstPresent implements ContactAddFirstContract.Present {

    private ContactAddFirstContract.View Mview;

    public ContactAddFirstPresent(ContactAddFirstContract.View view) {
        this.Mview = view;
        view.setPresent(this);
    }

    @Override
    public void getInfo(String personId) {
        HttpHelper.getAddressListByPersonId(personId).
                subscribe(contactAddRequest -> Mview.getInfoSuccess(contactAddRequest), throwable -> Mview.getInfoFailed());
    }
}
