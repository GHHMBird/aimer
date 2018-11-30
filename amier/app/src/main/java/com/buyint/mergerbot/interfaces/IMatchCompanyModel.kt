package com.buyint.mergerbot.interfaces

import com.buyint.mergerbot.dto.MatchCompanyRequest
import com.buyint.mergerbot.dto.MatchCompanyResponse
import io.reactivex.Observable

/**
 * Created by huheming on 2018/7/24
 */
interface IMatchCompanyModel {
    fun matchCompany(request: MatchCompanyRequest): Observable<MatchCompanyResponse>
}