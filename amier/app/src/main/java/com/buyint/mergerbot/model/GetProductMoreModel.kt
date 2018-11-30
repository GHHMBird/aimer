package com.buyint.mergerbot.model

import com.buyint.mergerbot.dto.MatchCompanyMorePageRequest
import com.buyint.mergerbot.dto.MatchCompanyMorePageResponse
import com.buyint.mergerbot.helper.HttpHelper
import com.buyint.mergerbot.interfaces.IGetProductMoreModel
import io.reactivex.Observable

/**
 * Created by huheming on 2018/7/24
 */
class GetProductMoreModel : IGetProductMoreModel {
    override fun getProductMore(request: MatchCompanyMorePageRequest): Observable<MatchCompanyMorePageResponse> {
        return HttpHelper.getProductMore(request)
    }
}