package com.buyint.mergerbot.presenter

import com.buyint.mergerbot.dto.VerifyCodeChangePasswordRequest
import com.buyint.mergerbot.interfaces.ILoginGetSms
import com.buyint.mergerbot.interfaces.IVerifycodeChangePassword
import com.buyint.mergerbot.interfaces.IVerifycodeChangePasswordModel
import com.buyint.mergerbot.model.VerifycodeChangePasswordModel
import io.reactivex.disposables.Disposable

/**
 * Created by huheming on 2018/7/4
 */
class ForgetPasswordPresenter(iLoginGetSms: ILoginGetSms,var iVerifycodeChangePassword: IVerifycodeChangePassword?) : AccountBindingPresenter(iLoginGetSms) {

    private var verifycodeChangePasswordModel: IVerifycodeChangePasswordModel? = null
    private var subscribe2: Disposable? = null

    fun verifycodeChangePassword(request: VerifyCodeChangePasswordRequest) {
        if (verifycodeChangePasswordModel == null) {
            verifycodeChangePasswordModel = VerifycodeChangePasswordModel()
        }
        subscribe2 = verifycodeChangePasswordModel!!.verifycodeChangePassword(request).subscribe({ booleanResponse ->
            if (iVerifycodeChangePassword != null) {
                iVerifycodeChangePassword!!.verifycodeChangePasswordSuccess(booleanResponse)
            }
        }, { throwable ->
            if (iVerifycodeChangePassword != null) {
                iVerifycodeChangePassword!!.verifycodeChangePasswordFail(throwable)
            }
        })
    }

    override fun destory() {
        super.destory()
        if (subscribe2 != null) {
            subscribe2!!.dispose()
            subscribe2 = null
        }
        if (iVerifycodeChangePassword != null) {
            iVerifycodeChangePassword = null
        }
    }
}