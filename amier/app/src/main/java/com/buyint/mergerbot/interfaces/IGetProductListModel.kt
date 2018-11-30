package com.buyint.mergerbot.interfaces

import com.buyint.mergerbot.dto.GetAutoProductResponse
import io.reactivex.Observable

/**
 * Created by huheming on 2018/7/24
 */
interface IGetProductListModel {
    fun getProductList(product: String): Observable<GetAutoProductResponse>
}