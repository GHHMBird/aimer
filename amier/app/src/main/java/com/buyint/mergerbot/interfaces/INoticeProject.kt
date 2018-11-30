package com.buyint.mergerbot.interfaces

import com.buyint.mergerbot.dto.BooleanResponse
import com.buyint.mergerbot.dto.MatchCompanyBean

/**
 * Created by huheming on 2018/7/24
 */
interface INoticeProject {
    fun noticeProjectSuccess(bean: MatchCompanyBean?, response: BooleanResponse)
    fun noticeProjectFail(throwable: Throwable)
}