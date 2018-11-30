package com.buyint.mergerbot.interfaces

import com.buyint.mergerbot.dto.MatchRecordListResponse
import io.reactivex.Observable

/**
 * Created by huheming on 2018/7/27
 */
interface IMatchRecordDetailPersonModel {
    fun matchRecordDetailPerson(personId: String): Observable<MatchRecordListResponse>
}