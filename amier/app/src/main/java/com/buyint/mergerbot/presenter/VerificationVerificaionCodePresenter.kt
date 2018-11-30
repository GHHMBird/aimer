package com.buyint.mergerbot.presenter

import com.buyint.mergerbot.dto.AccountVerificationGetMessageRequest
import com.buyint.mergerbot.dto.AccountVerificationVerifyMessageRequest
import com.buyint.mergerbot.interfaces.IAccountVerificationGetMessage
import com.buyint.mergerbot.interfaces.IAccountVerificationGetMessageModel
import com.buyint.mergerbot.interfaces.IAccountVerificationVerifyMessage
import com.buyint.mergerbot.interfaces.IAccountVerificationVerifyMessageModel
import com.buyint.mergerbot.model.AccountVerificationGetMessageModel
import com.buyint.mergerbot.model.AccountVerificationVerifyMessageModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Created by huheming on 2018/8/3
 */
class VerificationVerificaionCodePresenter(var iAccountVerificationVerifyMessage: IAccountVerificationVerifyMessage?, var iAccountVerificationGetMessage: IAccountVerificationGetMessage?) {

    private var disposable: Disposable? = null
    private var iAccountVerificationVerifyMessageModel: IAccountVerificationVerifyMessageModel? = null
    private var dispose2: Disposable? = null
    private val compositeDisposable = CompositeDisposable()
    private var iAccountVerificationGetMessageModel: IAccountVerificationGetMessageModel? = null

    fun accountVerificationVerifyMessage(request: AccountVerificationVerifyMessageRequest) {
        if (iAccountVerificationVerifyMessageModel == null) {
            iAccountVerificationVerifyMessageModel = AccountVerificationVerifyMessageModel()
        }
        disposable = iAccountVerificationVerifyMessageModel!!.accountVerificationVerifyMessage(request).subscribe({
            if (iAccountVerificationVerifyMessage != null) {
                iAccountVerificationVerifyMessage!!.accountVerificationVerifyMessageSuccess(it)
            }
        }, {
            if (iAccountVerificationVerifyMessage != null) {
                iAccountVerificationVerifyMessage!!.accountVerificationVerifyMessageFail(it)
            }
        })
        compositeDisposable.add(disposable!!)
    }


    fun accountVerificationGetMessage(request: AccountVerificationGetMessageRequest) {
        if (iAccountVerificationGetMessageModel == null) {
            iAccountVerificationGetMessageModel = AccountVerificationGetMessageModel()
        }
        dispose2 = iAccountVerificationGetMessageModel!!.accountVerificationGetMessage(request).subscribe({
            if (iAccountVerificationGetMessage != null) {
                iAccountVerificationGetMessage!!.accountVerificationGetMessageSuccess(it)
            }
        }, {
            if (iAccountVerificationGetMessage != null) {
                iAccountVerificationGetMessage!!.accountVerificationGetMessageFail(it)
            }
        })
        compositeDisposable.add(dispose2!!)
    }

    fun destory() {
        compositeDisposable.dispose()
        if (iAccountVerificationVerifyMessage != null) {
            iAccountVerificationVerifyMessage = null
        }
        if (iAccountVerificationGetMessage != null) {
            iAccountVerificationGetMessage = null
        }
    }
}