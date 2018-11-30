package com.buyint.mergerbot.interfaces

import com.buyint.mergerbot.dto.BooleanResponse

/**
 * Created by huheming on 2018/7/20
 */
interface IPutUserBusinessActivity {
    fun putUserBusinessActivitySuccess(response: BooleanResponse)
    fun putUserBusinessActivityFail(throwable: Throwable)
}