package com.buyint.mergerbot.interfaces

import com.buyint.mergerbot.dto.MatchCompanyDetailResponse
import io.reactivex.Observable

/**
 * Created by huheming on 2018/7/24
 */
interface IGetMatchCompanyDetailModel {
    fun getMatchCompanyDetail(clickId: String, thisProductId: String, type: String): Observable<MatchCompanyDetailResponse>
}