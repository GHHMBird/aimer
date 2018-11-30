package com.buyint.mergerbot.presenter

import com.buyint.mergerbot.interfaces.IGetAttachMessage
import com.buyint.mergerbot.interfaces.IGetAttachMessageModel
import com.buyint.mergerbot.model.GetAttachMessageModel
import io.reactivex.disposables.Disposable

/**
 * Created by huheming on 2018/7/23
 */
class ExtraInformationPresenter(var iGetAttachMessage: IGetAttachMessage?) {

    private var Disposable: Disposable? = null
    private var iGetAttachMessageModel: IGetAttachMessageModel? = null

    fun getAttachMessage() {
        if (iGetAttachMessageModel == null) {
            iGetAttachMessageModel = GetAttachMessageModel()
        }
        Disposable = iGetAttachMessageModel!!.getAttachMessage().subscribe({
            if (iGetAttachMessage != null) {
                iGetAttachMessage!!.getAttachMessageSuccess(it)
            }
        }, {
            if (iGetAttachMessage != null) {
                iGetAttachMessage!!.getAttachMessageFail(it)
            }
        })
    }

    fun destory() {
        if (Disposable != null) {
            Disposable!!.dispose()
            Disposable = null
        }
        if (iGetAttachMessage != null) {
            iGetAttachMessage = null
        }
    }
}