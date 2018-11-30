package com.buyint.mergerbot.interfaces

import com.buyint.mergerbot.dto.IndustryListResponse
import io.reactivex.Observable

/**
 * Created by huheming on 2018/7/24
 */
interface IGetIndustryListModel {
    fun getIndustryList(industry: String): Observable<IndustryListResponse>
}