package com.buyint.mergerbot.interfaces

import com.buyint.mergerbot.dto.GetUserBusinessActivityResponse

/**
 * Created by huheming on 2018/7/20
 */
interface IGetUserBusinessActivity {
    fun getUserBusinessActivitySuccess(response: GetUserBusinessActivityResponse)
    fun getUserBusinessActivityFail(throwable: Throwable)
}