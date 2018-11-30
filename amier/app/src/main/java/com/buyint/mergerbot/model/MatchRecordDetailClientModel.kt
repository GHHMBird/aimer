package com.buyint.mergerbot.model

import com.buyint.mergerbot.dto.MatchRecordDetailClientResponse
import com.buyint.mergerbot.helper.HttpHelper
import com.buyint.mergerbot.interfaces.IMatchRecordDetailClientModel
import io.reactivex.Observable

/**
 * Created by huheming on 2018/8/9
 */
class MatchRecordDetailClientModel : IMatchRecordDetailClientModel {
    override fun matchRecordDetailClient(personId: String): Observable<MatchRecordDetailClientResponse> {
        return HttpHelper.matchRecordDetailClient(personId)
    }
}