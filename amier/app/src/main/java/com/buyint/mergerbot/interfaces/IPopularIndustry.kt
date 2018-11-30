package com.buyint.mergerbot.interfaces

import com.buyint.mergerbot.dto.CodeNameResponse

/**
 * Created by huheming on 2018/7/23
 */
interface IPopularIndustry {
    fun popularIndustrySuccess(response: CodeNameResponse)
    fun popularIndustryFail(throwable: Throwable)
}