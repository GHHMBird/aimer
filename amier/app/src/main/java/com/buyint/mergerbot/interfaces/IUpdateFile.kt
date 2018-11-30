package com.buyint.mergerbot.interfaces

import com.buyint.mergerbot.dto.StringResponse

/**
 * Created by huheming on 2018/7/24
 */
interface IUpdateFile {
    fun updateFileSuccess(response: StringResponse)
    fun updateFileFail(throwable: Throwable)
}