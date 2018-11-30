package com.buyint.mergerbot.model

import com.buyint.mergerbot.dto.BooleanResponse
import com.buyint.mergerbot.helper.HttpHelper
import com.buyint.mergerbot.interfaces.IDeleteEducationExperienceModel
import io.reactivex.Observable

/**
 * Created by huheming on 2018/7/23
 */
class DeleteEducationExperienceModel : IDeleteEducationExperienceModel {
    override fun deleteEducationExperience(id: String): Observable<BooleanResponse> {
        return HttpHelper.deleteEducationExperience(id)
    }
}