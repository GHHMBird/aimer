package com.buyint.mergerbot.interfaces

import com.buyint.mergerbot.dto.GetAutoProductResponse

/**
 * Created by huheming on 2018/7/24
 */
interface IGetProductList {
    fun getProductListSuccess(response: GetAutoProductResponse)
    fun getProductListFail(throwable: Throwable)
}