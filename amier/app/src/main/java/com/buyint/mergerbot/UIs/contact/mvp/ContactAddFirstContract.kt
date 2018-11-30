package com.buyint.mergerbot.UIs.contact.mvp

import com.buyint.mergerbot.base.BaseView
import com.buyint.mergerbot.dto.ContactAddRequest

/**
 * created by licheng  on date 2018/8/7
 */
interface ContactAddFirstContract {

    interface Present {
        fun getInfo(personId: String)
    }

    interface View : BaseView<Present> {
        fun getInfoSuccess(contactAddRequest: ContactAddRequest)
        fun getInfoFailed()
    }
}
