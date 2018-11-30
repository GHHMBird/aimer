package com.buyint.mergerbot.interfaces

import com.buyint.mergerbot.dto.MatchCompanyMorePageRequest
import com.buyint.mergerbot.dto.MatchCompanyMorePageResponse
import io.reactivex.Observable

/**
 * Created by huheming on 2018/7/24
 */
interface IMatchCompanyMorePageModel {
    fun matchCompanyMorePage(request: MatchCompanyMorePageRequest): Observable<MatchCompanyMorePageResponse>
}