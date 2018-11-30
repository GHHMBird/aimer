package com.buyint.mergerbot.presenter

import com.buyint.mergerbot.dto.AccountMergerRequest
import com.buyint.mergerbot.interfaces.IAccountMerger
import com.buyint.mergerbot.interfaces.IAccountMergerModel
import com.buyint.mergerbot.model.AccountMergerModel
import io.reactivex.disposables.Disposable

/**
 * Created by huheming on 2018/7/4
 */
class AccountManagementPresenter(var iAccountMerger: IAccountMerger?) {

    private var iAccountMergerModel: IAccountMergerModel? = null
    private var subscribe: Disposable? = null

    fun accountMerger(request: AccountMergerRequest) {
        if (iAccountMergerModel == null) {
            iAccountMergerModel = AccountMergerModel()
        }
        subscribe = iAccountMergerModel!!.accountMerger(request).subscribe({
            if (iAccountMerger != null) {
                iAccountMerger!!.accountMergerSuccess(it)
            }
        }, {
            if (iAccountMerger != null) {
                iAccountMerger!!.accountMergerFail(it)
            }
        })
    }

    fun destory() {
        if (subscribe != null) {
            subscribe!!.dispose()
            subscribe = null
        }
        if (iAccountMerger != null) {
            iAccountMerger = null
        }
    }
}