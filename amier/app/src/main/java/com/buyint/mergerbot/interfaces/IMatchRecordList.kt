package com.buyint.mergerbot.interfaces

import com.buyint.mergerbot.dto.MatchRecordListResponse

/**
 * Created by huheming on 2018/7/27
 */
interface IMatchRecordList {
    fun matchRecordListSuccess(response: MatchRecordListResponse)
    fun matchRecordListFail(throwable: Throwable)
}