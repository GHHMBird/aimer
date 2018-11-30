package com.buyint.mergerbot.interfaces

import com.buyint.mergerbot.dto.MatchCompanyMorePageResponse

/**
 * Created by huheming on 2018/7/27
 */
interface IMatchRecordDetailProject {
    fun matchRecordDetailProjectSuccess(response: MatchCompanyMorePageResponse)
    fun matchRecordDetailProjectFail(throwable: Throwable)
}