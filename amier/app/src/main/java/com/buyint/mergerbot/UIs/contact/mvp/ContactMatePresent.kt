package com.buyint.mergerbot.UIs.contact.mvp

import com.buyint.mergerbot.UIs.contact.data.ContactMateRepository
import com.buyint.mergerbot.dto.ContactAddRequest

/**
 * created by licheng  on date 2018/8/9
 */
class ContactMatePresent(private val mView: ContactMateContract.View) : ContactMateContract.Present {

    private val contactMateRepository: ContactMateRepository

    init {
        mView.setPresent(this)
        contactMateRepository = ContactMateRepository()
    }

    override fun addName(contactAddRequest: ContactAddRequest) {
        contactMateRepository.getContactAdd(contactAddRequest).subscribe({ aBoolean -> mView.addtNameSuccess(aBoolean!!) }, { mView.addtNameFailed() })
    }

    override fun putName(contactAddRequest: ContactAddRequest) {
        contactMateRepository.putContactPut(contactAddRequest).subscribe({ aBoolean -> mView.putNameSuccess(aBoolean) }, { mView.aputNameFailed() })
    }
}
