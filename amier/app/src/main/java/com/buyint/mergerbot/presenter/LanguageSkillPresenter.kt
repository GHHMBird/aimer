package com.buyint.mergerbot.presenter

import com.buyint.mergerbot.dto.UpdateLanguageSkillRequest
import com.buyint.mergerbot.interfaces.IUpdateLanguageSkill
import com.buyint.mergerbot.interfaces.IUpdateLanguageSkillModel
import com.buyint.mergerbot.model.UpdateLanguageSkillModel
import io.reactivex.disposables.Disposable

/**
 * Created by huheming on 2018/7/23
 */
class LanguageSkillPresenter(var iUpdateLanguageSkill: IUpdateLanguageSkill?) {

    private var subscribe: Disposable? = null
    private var iUpdateLanguageSkillModel: IUpdateLanguageSkillModel? = null

    fun updateLanguageSkill(request: UpdateLanguageSkillRequest) {
        if (iUpdateLanguageSkillModel == null) {
            iUpdateLanguageSkillModel = UpdateLanguageSkillModel()
        }
        subscribe = iUpdateLanguageSkillModel!!.updateLanguageSkill(request).subscribe({
            if (iUpdateLanguageSkill != null) {
                iUpdateLanguageSkill!!.updateLanguageSkillSuccess(it)
            }
        }, {
            if (iUpdateLanguageSkill != null) {
                iUpdateLanguageSkill!!.updateLanguageSkillFail(it)
            }
        })
    }

    fun destory() {
        if (subscribe != null) {
            subscribe!!.dispose()
            subscribe = null
        }
        if (iUpdateLanguageSkill != null) {
            iUpdateLanguageSkill = null
        }
    }
}