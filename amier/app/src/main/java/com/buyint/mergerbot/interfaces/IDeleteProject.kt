package com.buyint.mergerbot.interfaces

import com.buyint.mergerbot.dto.BooleanResponse

/**
 * Created by huheming on 2018/7/24
 */
interface IDeleteProject {
    fun deleteProjectSuccess(response: BooleanResponse)
    fun deleteProjectFail(throwable: Throwable)
}