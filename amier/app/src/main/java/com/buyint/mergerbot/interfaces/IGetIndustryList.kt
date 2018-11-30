package com.buyint.mergerbot.interfaces

import com.buyint.mergerbot.dto.IndustryListResponse

/**
 * Created by huheming on 2018/7/24
 */
interface IGetIndustryList {
    fun getIndustryListSuccess(response: IndustryListResponse)
    fun getIndustryListFail(throwable: Throwable)
}