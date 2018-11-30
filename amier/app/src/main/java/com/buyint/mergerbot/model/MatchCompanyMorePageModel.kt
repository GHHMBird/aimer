package com.buyint.mergerbot.model

import com.buyint.mergerbot.dto.MatchCompanyMorePageRequest
import com.buyint.mergerbot.dto.MatchCompanyMorePageResponse
import com.buyint.mergerbot.helper.HttpHelper
import com.buyint.mergerbot.interfaces.IMatchCompanyMorePageModel
import io.reactivex.Observable

/**
 * Created by huheming on 2018/7/24
 */
class MatchCompanyMorePageModel : IMatchCompanyMorePageModel {
    override fun matchCompanyMorePage(request: MatchCompanyMorePageRequest): Observable<MatchCompanyMorePageResponse> {
        return HttpHelper.matchCompanyMorePage(request)
    }
}