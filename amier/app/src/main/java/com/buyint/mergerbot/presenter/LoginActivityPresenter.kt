package com.buyint.mergerbot.presenter

import com.buyint.mergerbot.dto.LinkedinRequest
import com.buyint.mergerbot.dto.PasswordLoginRequest
import com.buyint.mergerbot.dto.RegisterRequest
import com.buyint.mergerbot.interfaces.*
import com.buyint.mergerbot.model.LinkedInLoginModel
import com.buyint.mergerbot.model.LoginGetSmsModel
import com.buyint.mergerbot.model.PasswordLoginModel
import com.buyint.mergerbot.model.SmsLoginModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Created by huheming on 2018/6/20
 */
class LoginActivityPresenter(var iLoginGetSms: ILoginGetSms?, var iSmsLogin: ISmsLogin?, var iLinkedInLogin: ILinkedInLogin?, var iPasswordLogin: IPasswordLogin?) {

    private var iLinkedInLoginModel: ILinkedInLoginModel? = null
    private var iLoginGetSmsModel: ILoginGetSmsModel? = null
    private var iSmsLoginModel: ISmsLoginModel? = null
    private var iPasswordLoginModel: IPasswordLoginModel? = null
    private var subscribe: Disposable? = null
    private var subscribe2: Disposable? = null
    private var subscribe3: Disposable? = null
    private var subscribe4: Disposable? = null
    private val compositeDisposable = CompositeDisposable()

    fun passwordLogin(request: PasswordLoginRequest) {
        if (iPasswordLoginModel == null) {
            iPasswordLoginModel = PasswordLoginModel()
        }
        subscribe4 = iPasswordLoginModel!!.passwordLogin(request).subscribe({
            if (iPasswordLogin != null) {
                iPasswordLogin!!.passwordLoginSuccess(it)
            }
        }, {
            if (iPasswordLogin != null) {
                iPasswordLogin!!.passwordLoginFail(it)
            }
        })
        compositeDisposable.add(subscribe4!!)
    }

    fun loginGetSms(type: String, phone: String) {
        if (iLoginGetSmsModel == null) {
            iLoginGetSmsModel = LoginGetSmsModel()
        }
        subscribe = iLoginGetSmsModel!!.loginGetSms(type, phone).subscribe({
            if (iLoginGetSms != null) {
                iLoginGetSms!!.loginGetMsmSuccess(it)
            }
        }, {
            if (iLoginGetSms != null) {
                iLoginGetSms!!.loginGetMsmFail(it)
            }
        })
    }

    fun linkedInLogin(request: LinkedinRequest) {
        if (iLinkedInLoginModel == null) {
            iLinkedInLoginModel = LinkedInLoginModel()
        }
        subscribe3 = iLinkedInLoginModel!!.LinkedInLogin(request).subscribe({
            if (iLinkedInLogin != null) {
                iLinkedInLogin!!.linkedInLoginSuccess(it)
            }
        }, {
            if (iLinkedInLogin != null) {
                iLinkedInLogin!!.linkedInLoginFail(it)
            }
        })
    }

    fun smsLogin(request: RegisterRequest) {
        if (iSmsLoginModel == null) {
            iSmsLoginModel = SmsLoginModel()
        }
        subscribe2 = iSmsLoginModel!!.smsLogin(request).subscribe({
            if (iSmsLogin != null) {
                iSmsLogin!!.smsLoginSuccess(it)
            }
        }, {
            if (iSmsLogin != null) {
                iSmsLogin!!.smsLoginFail(it)
            }
        })
    }

    fun destory() {
//        compositeDisposable.dispose()
        if (subscribe != null) {
            subscribe!!.dispose()
            subscribe = null
        }
        if (subscribe2 != null) {
            subscribe2!!.dispose()
            subscribe2 = null
        }
        if (subscribe3 != null) {
            subscribe3!!.dispose()
            subscribe3 = null
        }
        if (subscribe4 != null) {
            subscribe4!!.dispose()
            subscribe4 = null
        }
        if (iLoginGetSms != null) {
            iLoginGetSms = null
        }
        if (iSmsLogin != null) {
            iSmsLogin = null
        }
        if (iLinkedInLogin != null) {
            iLinkedInLogin = null
        }
        if (iPasswordLogin != null) {
            iPasswordLogin = null
        }
    }

}