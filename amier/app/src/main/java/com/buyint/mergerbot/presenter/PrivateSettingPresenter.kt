package com.buyint.mergerbot.presenter

import com.buyint.mergerbot.dto.UpdatePrivacySettingRequest
import com.buyint.mergerbot.interfaces.IUpdatePrivacySetting
import com.buyint.mergerbot.interfaces.IUpdatePrivacySettingModel
import com.buyint.mergerbot.model.UpdatePrivacySettingModel
import io.reactivex.disposables.Disposable

/**
 * Created by huheming on 2018/7/12
 */
class PrivateSettingPresenter(var iUpdatePrivacySetting: IUpdatePrivacySetting?) {

    private var subscribe: Disposable? = null
    private var iUpdatePrivacySettingModel: IUpdatePrivacySettingModel? = null

    fun updatePrivacySettingArchives(request: UpdatePrivacySettingRequest) {
        if (iUpdatePrivacySettingModel == null) {
            iUpdatePrivacySettingModel = UpdatePrivacySettingModel()
        }
        subscribe = iUpdatePrivacySettingModel!!.updatePrivacySetting(request).subscribe({
            if (iUpdatePrivacySetting != null) {
                iUpdatePrivacySetting!!.updatePrivacySettingSuccess(it)
            }
        }, {
            if (iUpdatePrivacySetting != null) {
                iUpdatePrivacySetting!!.updatePrivacySettingFail(it)
            }
        })
    }

    fun destory() {
        if (subscribe != null) {
            subscribe!!.dispose()
            subscribe = null
        }
        if (iUpdatePrivacySetting != null) {
            iUpdatePrivacySetting = null
        }
    }
}