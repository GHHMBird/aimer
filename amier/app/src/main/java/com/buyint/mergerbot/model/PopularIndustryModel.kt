package com.buyint.mergerbot.model

import com.buyint.mergerbot.dto.CodeNameResponse
import com.buyint.mergerbot.helper.HttpHelper
import com.buyint.mergerbot.interfaces.IPopularIndustryModel
import io.reactivex.Observable

/**
 * Created by huheming on 2018/7/23
 */
class PopularIndustryModel : IPopularIndustryModel {
    override fun popularIndustry(): Observable<CodeNameResponse> {
        return HttpHelper.popularIndustry()
    }

}