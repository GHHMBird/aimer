package com.buyint.mergerbot.presenter

import com.buyint.mergerbot.interfaces.ILoginGetSms
import com.buyint.mergerbot.interfaces.ILoginGetSmsModel
import com.buyint.mergerbot.model.LoginGetSmsModel
import io.reactivex.disposables.Disposable


/**
 * Created by huheming on 2018/7/4
 */
open class AccountBindingPresenter(var iLoginGetSms: ILoginGetSms?) {

    private var iLoginGetSmsModel: ILoginGetSmsModel? = null
    private var subscribe: Disposable? = null

    fun accountBindingGetSms(type: String, account: String) {
        if (iLoginGetSmsModel == null) {
            iLoginGetSmsModel = LoginGetSmsModel()
        }
        subscribe = iLoginGetSmsModel!!.loginGetSms(type, account).subscribe({
            if (iLoginGetSms != null) {
                iLoginGetSms!!.loginGetMsmSuccess(it)
            }
        }, {
            if (iLoginGetSms != null) {
                iLoginGetSms!!.loginGetMsmFail(it)
            }
        })
    }

    open fun destory() {
        if (subscribe != null) {
            subscribe!!.dispose()
            subscribe = null
        }
        if (iLoginGetSms != null) {
            iLoginGetSms = null
        }
    }

}