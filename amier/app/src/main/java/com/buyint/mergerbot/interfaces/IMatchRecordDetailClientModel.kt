package com.buyint.mergerbot.interfaces

import com.buyint.mergerbot.dto.MatchRecordDetailClientResponse
import io.reactivex.Observable

/**
 * Created by huheming on 2018/8/9
 */
interface IMatchRecordDetailClientModel {
    fun matchRecordDetailClient(personId: String): Observable<MatchRecordDetailClientResponse>
}