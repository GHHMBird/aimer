package com.buyint.mergerbot.UIs.contact.mvp

import com.buyint.mergerbot.base.BaseView
import com.buyint.mergerbot.dto.ContactAddRequest

/**
 * created by licheng  on date 2018/8/7
 */
interface ContactMateContract {

    interface Present {
        fun addName(contactAddRequest: ContactAddRequest)
        fun putName(contactAddRequest: ContactAddRequest)
    }

    interface View : BaseView<Present> {
        fun addtNameSuccess(succ: Boolean)
        fun addtNameFailed()

        fun putNameSuccess(succ: Boolean)
        fun aputNameFailed()
    }
}
