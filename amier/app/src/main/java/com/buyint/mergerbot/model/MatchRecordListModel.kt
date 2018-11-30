package com.buyint.mergerbot.model

import com.buyint.mergerbot.dto.MatchRecordListResponse
import com.buyint.mergerbot.helper.HttpHelper
import com.buyint.mergerbot.interfaces.IMatchRecordListModel
import io.reactivex.Observable

/**
 * Created by huheming on 2018/7/27
 */
class MatchRecordListModel : IMatchRecordListModel {
    override fun matchRecordList(type: String): Observable<MatchRecordListResponse> {
        return HttpHelper.matchRecordList(type)
    }
}