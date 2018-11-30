package com.buyint.mergerbot.interfaces

import com.buyint.mergerbot.dto.MatchCompanyMorePageResponse
import io.reactivex.Observable

/**
 * Created by huheming on 2018/7/27
 */
interface IMatchRecordDetailProjectModel {
    fun matchRecordDetailProject(personId: String): Observable<MatchCompanyMorePageResponse>
}