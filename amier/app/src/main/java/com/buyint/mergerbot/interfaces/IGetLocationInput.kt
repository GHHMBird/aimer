package com.buyint.mergerbot.interfaces

import com.buyint.mergerbot.dto.CodeNameResponse

/**
 * Created by huheming on 2018/7/24
 */
interface IGetLocationInput {
    fun getLocationInputSuccess(response: CodeNameResponse)
    fun getLocationInputFail(throwable: Throwable)
}