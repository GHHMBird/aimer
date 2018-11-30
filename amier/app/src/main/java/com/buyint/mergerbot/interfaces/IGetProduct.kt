package com.buyint.mergerbot.interfaces

import com.buyint.mergerbot.dto.MatchCompanyResponse

/**
 * Created by huheming on 2018/7/24
 */
interface IGetProduct {
    fun getProductSuccess(response: MatchCompanyResponse)
    fun getProductFail(throwable: Throwable)
}