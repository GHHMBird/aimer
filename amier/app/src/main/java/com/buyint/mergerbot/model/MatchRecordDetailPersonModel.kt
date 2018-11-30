package com.buyint.mergerbot.model

import com.buyint.mergerbot.dto.MatchRecordListResponse
import com.buyint.mergerbot.helper.HttpHelper
import com.buyint.mergerbot.interfaces.IMatchRecordDetailPersonModel
import io.reactivex.Observable

/**
 * Created by huheming on 2018/7/27
 */
class MatchRecordDetailPersonModel : IMatchRecordDetailPersonModel {
    override fun matchRecordDetailPerson(personId: String): Observable<MatchRecordListResponse> {
        return HttpHelper.matchRecordDetailPerson(personId)
    }
}