package com.buyint.mergerbot.interfaces

import com.buyint.mergerbot.dto.MatchRecordListResponse
import io.reactivex.Observable

/**
 * Created by huheming on 2018/7/27
 */
interface IMatchRecordListModel {
    fun matchRecordList(type:String):Observable<MatchRecordListResponse>
}