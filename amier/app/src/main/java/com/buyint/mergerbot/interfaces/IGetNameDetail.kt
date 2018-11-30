package com.buyint.mergerbot.interfaces

import com.buyint.mergerbot.dto.GetNameDetailResponse

/**
 * Created by huheming on 2018/8/14
 */
interface IGetNameDetail {
    fun getNameDetailSuccess(response: GetNameDetailResponse)
    fun getNameDetailFail(throwable: Throwable)
}