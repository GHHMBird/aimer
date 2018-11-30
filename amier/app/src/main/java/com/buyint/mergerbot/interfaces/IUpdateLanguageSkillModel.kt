package com.buyint.mergerbot.interfaces

import com.buyint.mergerbot.dto.BooleanResponse
import com.buyint.mergerbot.dto.UpdateLanguageSkillRequest
import io.reactivex.Observable

/**
 * Created by huheming on 2018/7/23
 */
interface IUpdateLanguageSkillModel {
    fun updateLanguageSkill(request: UpdateLanguageSkillRequest): Observable<BooleanResponse>
}