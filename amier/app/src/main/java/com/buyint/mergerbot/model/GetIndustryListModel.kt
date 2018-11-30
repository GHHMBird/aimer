package com.buyint.mergerbot.model

import com.buyint.mergerbot.dto.IndustryListResponse
import com.buyint.mergerbot.helper.HttpHelper
import com.buyint.mergerbot.interfaces.IGetIndustryListModel
import io.reactivex.Observable

/**
 * Created by huheming on 2018/7/24
 */
class GetIndustryListModel : IGetIndustryListModel {
    override fun getIndustryList(industry: String): Observable<IndustryListResponse> {
        return HttpHelper.getIndustryList(industry)
    }
}