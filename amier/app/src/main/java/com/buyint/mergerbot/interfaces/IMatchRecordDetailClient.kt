package com.buyint.mergerbot.interfaces

import com.buyint.mergerbot.dto.MatchRecordDetailClientResponse

/**
 * Created by huheming on 2018/8/9
 */
interface IMatchRecordDetailClient {
    fun matchRecordDetailClientSuccess(it: MatchRecordDetailClientResponse)
    fun matchRecordDetailClientFail(it: Throwable)
}