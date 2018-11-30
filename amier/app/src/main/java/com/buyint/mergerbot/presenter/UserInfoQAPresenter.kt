package com.buyint.mergerbot.presenter

import com.buyint.mergerbot.dto.UserNikeAndIntentionRequest
import com.buyint.mergerbot.interfaces.ISetUserInfo
import com.buyint.mergerbot.interfaces.ISetUserInfoModel
import com.buyint.mergerbot.interfaces.IUpdateFile
import com.buyint.mergerbot.interfaces.IUpdateFileModel
import com.buyint.mergerbot.model.SetUserInfoModel
import com.buyint.mergerbot.model.UpdateFileModel
import io.reactivex.disposables.Disposable
import java.io.File

/**
 * Created by huheming on 2018/7/24
 */
class UserInfoQAPresenter(var iSetUserInfo: ISetUserInfo?, var iUpdateFile: IUpdateFile?) {

    private var Disposable: Disposable? = null
    private var Disposable2: Disposable? = null
    private var iSetUserInfoModel: ISetUserInfoModel? = null
    private var iUpdateFileModel: IUpdateFileModel? = null

    fun setUserInfo(request: UserNikeAndIntentionRequest) {
        if (iSetUserInfoModel == null) {
            iSetUserInfoModel = SetUserInfoModel()
        }
        Disposable = iSetUserInfoModel!!.setUserInfo(request).subscribe({
            if (iSetUserInfo != null) {
                iSetUserInfo!!.setUserInfoSuccess(it)
            }
        }, {
            if (iSetUserInfo != null) {
                iSetUserInfo!!.setUserInfoFail(it)
            }
        })
    }

    fun updateFile(file: File) {
        if (iUpdateFileModel == null) {
            iUpdateFileModel = UpdateFileModel()
        }
        Disposable2 = iUpdateFileModel!!.updateFile(file).subscribe({
            if (iUpdateFile != null) {
                iUpdateFile!!.updateFileSuccess(it)
            }
        }, {
            if (iUpdateFile != null) {
                iUpdateFile!!.updateFileFail(it)
            }
        })
    }

    fun destory() {
        if (Disposable != null) {
            Disposable!!.dispose()
            Disposable = null
        }
        if (Disposable2 != null) {
            Disposable2!!.dispose()
            Disposable2 = null
        }
        if (iSetUserInfo != null) {
            iSetUserInfo = null
        }
        if (iUpdateFile != null) {
            iUpdateFile = null
        }
    }
}