package com.buyint.mergerbot.interfaces

import com.buyint.mergerbot.dto.MatchCompanyDetailResponse

/**
 * Created by huheming on 2018/7/24
 */
interface IGetMatchCompanyDetail {
    fun getMatchCompanyDetailSuccess(response: MatchCompanyDetailResponse)
    fun getMatchCompanyDetailFail(throwable: Throwable)
}