package com.buyint.mergerbot.interfaces

import com.buyint.mergerbot.dto.MatchRecordListResponse

/**
 * Created by huheming on 2018/7/27
 */
interface IMatchRecordDetailPerson {
    fun matchRecordDetailPersonSuccess(response: MatchRecordListResponse)
    fun matchRecordDetailPersonFail(throwable: Throwable)
}