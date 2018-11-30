package com.buyint.mergerbot.model

import com.buyint.mergerbot.dto.MatchCompanyMorePageResponse
import com.buyint.mergerbot.helper.HttpHelper
import com.buyint.mergerbot.interfaces.IMatchRecordDetailProjectModel
import io.reactivex.Observable

/**
 * Created by huheming on 2018/7/27
 */
class MatchRecordDetailProjectModel : IMatchRecordDetailProjectModel {
    override fun matchRecordDetailProject(personId: String): Observable<MatchCompanyMorePageResponse> {
        return HttpHelper.matchRecordDetailProject(personId)
    }
}