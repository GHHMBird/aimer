package com.buyint.mergerbot.interfaces

import com.buyint.mergerbot.dto.GetUserBusinessActivityListResponse

/**
 * Created by huheming on 2018/7/20
 */
interface IGetUserBusinessActivityList {
    fun getUserBusinessActivityListSuccess(response: GetUserBusinessActivityListResponse)
    fun getUserBusinessActivityListFail(throwable: Throwable)
}