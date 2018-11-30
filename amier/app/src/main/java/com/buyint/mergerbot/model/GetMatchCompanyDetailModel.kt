package com.buyint.mergerbot.model

import com.buyint.mergerbot.dto.MatchCompanyDetailResponse
import com.buyint.mergerbot.helper.HttpHelper
import com.buyint.mergerbot.interfaces.IGetMatchCompanyDetailModel
import io.reactivex.Observable

/**
 * Created by huheming on 2018/7/24
 */
class GetMatchCompanyDetailModel : IGetMatchCompanyDetailModel {
    override fun getMatchCompanyDetail(clickId: String, typeId: String, type: String): Observable<MatchCompanyDetailResponse> {
        return HttpHelper.getMatchCompanyDetail(clickId, typeId, type)
    }
}