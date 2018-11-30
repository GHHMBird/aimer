package com.buyint.mergerbot.interfaces

import com.buyint.mergerbot.dto.MatchCompanyMorePageResponse
import com.buyint.mergerbot.dto.PageRequest
import io.reactivex.Observable

/**
 * Created by huheming on 2018/8/8
 */
interface IGetMyMatchListModel {
    fun getMyMatchList(request: PageRequest): Observable<MatchCompanyMorePageResponse>
}