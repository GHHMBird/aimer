package com.buyint.mergerbot.presenter

import com.buyint.mergerbot.dto.PostUserInfoType3Request
import com.buyint.mergerbot.dto.RegisterRequest
import com.buyint.mergerbot.helper.HttpHelper
import com.buyint.mergerbot.interfaces.*
import com.buyint.mergerbot.model.*
import io.reactivex.disposables.Disposable
import java.io.File

/**
 * Created by huheming on 2018/6/28
 */
class RegisterLoginPresenter(var iPostUserInfoType3: IPostUserInfoType3?, var iUserIconUpdate: IUserIconUpdate?, var iRegisterGetSms: IRegisterGetSms?, var iRegisterSetPassword: IRegisterSetPassword?, var iRegisterVerification: IRegisterVerification?) {

    private var iRegisterGetSmsModel: IRegisterGetSmsModel? = null
    private var iRegisterVerificationModel: IRegisterVerificationModel? = null
    private var iRegisterSetPasswordModel: IRegisterSetPasswordModel? = null
    private var iUserIconUpdateModel: IUserIconUpdateModel? = null
    private var iPostUserInfoType3Model: IPostUserInfoType3Model? = null
    private var subscribe: Disposable? = null
    private var subscribe2: Disposable? = null
    private var subscribe3: Disposable? = null
    private var subscribe4: Disposable? = null
    private var subscribe5: Disposable? = null

    fun postUserInfoType3(request: PostUserInfoType3Request) {
        if (iPostUserInfoType3Model == null) {
            iPostUserInfoType3Model = PostUserInfoType3Model()
        }
        subscribe5 = iPostUserInfoType3Model!!.postUserInfoType3(request).subscribe({
            if (iPostUserInfoType3 != null) {
                iPostUserInfoType3!!.postUserInfoType3Success(it)
            }
        }, {
            if (iPostUserInfoType3 != null) {
                iPostUserInfoType3!!.postUserInfoType3Fail(it)
            }
        })
    }

    fun registerGetSms(type: String, phone: String) {
        if (iRegisterGetSmsModel == null) {
            iRegisterGetSmsModel = RegisterGetSmsModel()
        }
        subscribe = iRegisterGetSmsModel!!.registerGetSms(type, phone).subscribe({
            if (iRegisterGetSms != null) {
                iRegisterGetSms!!.registerGetSmsSuccess(it)
            }
        }, {
            if (iRegisterGetSms != null) {
                iRegisterGetSms!!.registerGetSmsFail(it)
            }
        })
    }

    fun registerSetPassword(password: String) {
        if (iRegisterSetPasswordModel == null) {
            iRegisterSetPasswordModel = RegisterSetPasswordModel()
        }
        subscribe3 = HttpHelper.setPassword(password).subscribe({
            if (iRegisterSetPassword != null) {
                iRegisterSetPassword!!.registerSetPasswordSuccess(it)
            }
        }, {
            if (iRegisterSetPassword != null) {
                iRegisterSetPassword!!.registerSetPasswordFail(it)
            }
        })
    }

    fun registerVerification(phoneNumber: String, verificationCode: String) {
        if (iRegisterVerificationModel == null) {
            iRegisterVerificationModel = RegisterVerificationModel()
        }
        val request = RegisterRequest()
        request.phoneNumber = phoneNumber
        request.verificationCode = verificationCode
        subscribe2 = iRegisterVerificationModel!!.registerVerification(request).subscribe({
            if (iRegisterVerification != null) {
                iRegisterVerification!!.registerVerificationSuccess(it)
            }
        }, {
            if (iRegisterVerification != null) {
                iRegisterVerification!!.registerVerificationFail(it)
            }
        })
    }

    fun userIconUpdate(file: File) {
        if (iUserIconUpdateModel == null) {
            iUserIconUpdateModel = UserIconUpdateModel()
        }
        subscribe4 = iUserIconUpdateModel!!.userIconUpdate(file).subscribe({
            if (iUserIconUpdate != null) {
                iUserIconUpdate!!.userIconUpdateSuccess(it)
            }
        }, {
            if (iUserIconUpdate != null) {
                iUserIconUpdate!!.userIconUpdateFail(it)
            }
        })
    }

    fun destory() {
        if (subscribe5 != null) {
            subscribe5!!.dispose()
            subscribe5 = null
        }
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
        if (iRegisterGetSms != null) {
            iRegisterGetSms = null
        }
        if (iRegisterVerification != null) {
            iRegisterVerification = null
        }
        if (iRegisterSetPassword != null) {
            iRegisterSetPassword = null
        }
        if (iUserIconUpdate != null) {
            iUserIconUpdate = null
        }
        if (iPostUserInfoType3 != null) {
            iPostUserInfoType3 = null
        }
    }
}