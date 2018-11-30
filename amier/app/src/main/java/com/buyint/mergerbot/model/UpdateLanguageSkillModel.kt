package com.buyint.mergerbot.model

import com.buyint.mergerbot.dto.BooleanResponse
import com.buyint.mergerbot.dto.UpdateLanguageSkillRequest
import com.buyint.mergerbot.helper.HttpHelper
import com.buyint.mergerbot.interfaces.IUpdateLanguageSkillModel
import io.reactivex.Observable

/**
 * Created by huheming on 2018/7/23
 */
class UpdateLanguageSkillModel : IUpdateLanguageSkillModel {
    override fun updateLanguageSkill(request: UpdateLanguageSkillRequest): Observable<BooleanResponse> {
        return HttpHelper.updateLanguageSkill(request)
    }
}