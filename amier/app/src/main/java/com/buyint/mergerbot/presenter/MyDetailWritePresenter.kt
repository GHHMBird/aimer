package com.buyint.mergerbot.presenter

import com.buyint.mergerbot.dto.UpdateUserAllInfoRequest
import com.buyint.mergerbot.interfaces.IUpdateAllUserInfo
import com.buyint.mergerbot.interfaces.IUpdateAllUserInfoModel
import com.buyint.mergerbot.model.UpdateAllUserInfoModel
import io.reactivex.disposables.Disposable

/**
 * Created by huheming on 2018/7/12
 */
class MyDetailWritePresenter(var iUpdateAllUserInfo: IUpdateAllUserInfo?) {

    private var iUpdateAllUserInfoModel: IUpdateAllUserInfoModel? = null
    private var subscribe: Disposable? = null

    fun updateAllUserInfo(request: UpdateUserAllInfoRequest) {
        if (iUpdateAllUserInfoModel == null) {
            iUpdateAllUserInfoModel = UpdateAllUserInfoModel()
        }
        subscribe = iUpdateAllUserInfoModel!!.updateAllUserInfo(request).subscribe({
            if (iUpdateAllUserInfo != null) {
                iUpdateAllUserInfo!!.updateAllUserInfoSuccess(it, request)
            }
        }, {
            if (iUpdateAllUserInfo != null) {
                iUpdateAllUserInfo!!.updateAllUserInfoFail(it)
            }
        })
    }

    fun destory() {
        if (subscribe != null) {
            subscribe!!.dispose()
            subscribe = null
        }
        if (iUpdateAllUserInfo != null) {
            iUpdateAllUserInfo = null
        }
    }
}