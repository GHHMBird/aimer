package com.buyint.mergerbot.presenter

import com.buyint.mergerbot.dto.AccountVerificationGetMessageRequest
import com.buyint.mergerbot.interfaces.IAccountVerificationGetMessage
import com.buyint.mergerbot.interfaces.IAccountVerificationGetMessageModel
import com.buyint.mergerbot.interfaces.IUserIconUpdate
import com.buyint.mergerbot.interfaces.IUserIconUpdateModel
import com.buyint.mergerbot.model.AccountVerificationGetMessageModel
import com.buyint.mergerbot.model.UserIconUpdateModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import java.io.File

/**
 * Created by huheming on 2018/8/3
 */
class BusinessIdentityVerificationPresenter(var iUserIconUpdate: IUserIconUpdate?, var iAccountVerificationGetMessage: IAccountVerificationGetMessage?) {

    private var dispose: Disposable? = null
    private var dispose2: Disposable? = null
    private val compositeDisposable = CompositeDisposable()
    private var iUserIconUpdateModel: IUserIconUpdateModel? = null
    private var iAccountVerificationGetMessageModel: IAccountVerificationGetMessageModel? = null

    fun userIconUpdate(file: File) {
        if (iUserIconUpdateModel == null) {
            iUserIconUpdateModel = UserIconUpdateModel()
        }
        dispose = iUserIconUpdateModel!!.userIconUpdate(file).subscribe({
            if (iUserIconUpdate != null) {
                iUserIconUpdate!!.userIconUpdateSuccess(it)
            }
        }, {
            if (iUserIconUpdate != null) {
                iUserIconUpdate!!.userIconUpdateFail(it)
            }
        })
        compositeDisposable.add(dispose!!)
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
        if (iUserIconUpdate != null) {
            iUserIconUpdate = null
        }
        if (iAccountVerificationGetMessage != null) {
            iAccountVerificationGetMessage = null
        }
    }
}