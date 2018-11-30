package com.buyint.mergerbot.presenter

import com.buyint.mergerbot.dto.PasswordChangePasswordRequest
import com.buyint.mergerbot.interfaces.ILoginGetSms
import com.buyint.mergerbot.interfaces.IPasswordChangePassword
import com.buyint.mergerbot.model.LoginGetSmsModel
import com.buyint.mergerbot.model.PasswordChangePasswordModel
import io.reactivex.disposables.Disposable

/**
 * Created by huheming on 2018/7/2
 */
class SetPasswordPresenter(var iPasswordChangePassword: IPasswordChangePassword?, var iLoginGetSms: ILoginGetSms?) {

    private var passwordChangePasswordModel: PasswordChangePasswordModel? = null
    private var iLoginGetSmsModel: LoginGetSmsModel? = null
    private var subscribe: Disposable? = null
    private var subscribe2: Disposable? = null

    fun passwordChangePassword(request: PasswordChangePasswordRequest) {
        if (passwordChangePasswordModel == null) {
            passwordChangePasswordModel = PasswordChangePasswordModel()
        }
        subscribe = passwordChangePasswordModel!!.passwordChangePassword(request).subscribe({
            if (iPasswordChangePassword != null) {
                iPasswordChangePassword!!.passwordChangePasswordSuccess(it)
            }
        }, {
            if (iPasswordChangePassword != null) {
                iPasswordChangePassword!!.passwordChangePasswordFail(it)
            }
        })
    }

    fun loginGetSms(type: String, phone: String) {
        if (iLoginGetSmsModel == null) {
            iLoginGetSmsModel = LoginGetSmsModel()
        }
        subscribe2 = iLoginGetSmsModel!!.loginGetSms(type, phone).subscribe({
            if (iLoginGetSms != null) {
                iLoginGetSms!!.loginGetMsmSuccess(it)
            }
        }, {
            if (iLoginGetSms != null) {
                iLoginGetSms!!.loginGetMsmFail(it)
            }
        })
    }

    fun destory() {
        if (subscribe != null) {
            subscribe!!.dispose()
            subscribe = null
        }
        if (subscribe2 != null) {
            subscribe2!!.dispose()
            subscribe2 = null
        }
        if (iPasswordChangePassword != null) {
            iPasswordChangePassword = null
        }
        if (iLoginGetSms != null) {
            iLoginGetSms = null
        }
    }
}