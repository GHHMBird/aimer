package com.buyint.mergerbot.presenter

import com.buyint.mergerbot.interfaces.IUserIconUpdate
import com.buyint.mergerbot.interfaces.IUserIconUpdateModel
import com.buyint.mergerbot.model.UserIconUpdateModel
import io.reactivex.disposables.Disposable
import java.io.File

/**
 * Created by huheming on 2018/7/24
 */
class MyDetailPresenter(var iUserIconUpdate: IUserIconUpdate?) {

    private var Disposable: Disposable? = null
    private var iUserIconUpdateModel: IUserIconUpdateModel? = null

    fun userIconUpdate(file: File) {
        if (iUserIconUpdateModel == null) {
            iUserIconUpdateModel = UserIconUpdateModel()
        }
        Disposable = iUserIconUpdateModel!!.userIconUpdate(file).subscribe({
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
        if (Disposable != null) {
            Disposable!!.dispose()
            Disposable = null
        }
        if (iUserIconUpdate != null) {
            iUserIconUpdate = null
        }
    }
}