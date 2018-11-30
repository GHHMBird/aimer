package com.buyint.mergerbot.model

import com.buyint.mergerbot.dto.MatchCompanyMorePageResponse
import com.buyint.mergerbot.dto.PageRequest
import com.buyint.mergerbot.helper.HttpHelper
import com.buyint.mergerbot.interfaces.IGetMyMatchListModel
import io.reactivex.Observable

/**
 * Created by huheming on 2018/8/8
 */
class GetMyMatchListModel : IGetMyMatchListModel {
    override fun getMyMatchList(request: PageRequest): Observable<MatchCompanyMorePageResponse> {
        return HttpHelper.getMyMatchList(request)
    }
}