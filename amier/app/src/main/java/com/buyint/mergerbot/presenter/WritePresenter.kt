package com.buyint.mergerbot.presenter

import com.buyint.mergerbot.interfaces.ISetAttachMessage
import com.buyint.mergerbot.interfaces.ISetAttachMessageModel
import com.buyint.mergerbot.model.SetAttachMessageModel
import io.reactivex.disposables.Disposable

/**
 * Created by huheming on 2018/7/23
 */
class WritePresenter(var iSetAttachMessage: ISetAttachMessage?) {

    private var Disposable: Disposable? = null
    private var iSetAttachMessageModel: ISetAttachMessageModel? = null

    fun setAttachMessage(string: String) {
        if (iSetAttachMessageModel == null) {
            iSetAttachMessageModel = SetAttachMessageModel()
        }
        Disposable = iSetAttachMessageModel!!.setAttachMessage(string).subscribe({
            if (iSetAttachMessage != null) {
                iSetAttachMessage!!.setAttachMessageSuccess(it)
            }
        }, {
            if (iSetAttachMessage != null) {
                iSetAttachMessage!!.setAttachMessageFail(it)
            }
        })
    }

    fun destory() {
        if (Disposable != null) {
            Disposable!!.dispose()
            Disposable = null
        }
        if (iSetAttachMessage != null) {
            iSetAttachMessage = null
        }
    }
}