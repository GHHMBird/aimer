package com.buyint.mergerbot.interfaces

import com.buyint.mergerbot.dto.BooleanResponse
import com.buyint.mergerbot.dto.MatchCompanyBean

/**
 * Created by huheming on 2018/7/24
 */
interface INotNoticeProject {
    fun notNoticeProjectSuccess(bean: MatchCompanyBean?, response: BooleanResponse)
    fun notNoticeProjectFail(throwable: Throwable)
}