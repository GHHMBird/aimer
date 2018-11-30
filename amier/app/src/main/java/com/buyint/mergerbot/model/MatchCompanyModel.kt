package com.buyint.mergerbot.model

import com.buyint.mergerbot.dto.MatchCompanyRequest
import com.buyint.mergerbot.dto.MatchCompanyResponse
import com.buyint.mergerbot.helper.HttpHelper
import com.buyint.mergerbot.interfaces.IMatchCompanyModel
import io.reactivex.Observable

/**
 * Created by huheming on 2018/7/24
 */
class MatchCompanyModel : IMatchCompanyModel {
    override fun matchCompany(request: MatchCompanyRequest): Observable<MatchCompanyResponse> {
        return HttpHelper.matchCompany(request)
    }
}