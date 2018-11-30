package com.buyint.mergerbot.interfaces

import com.buyint.mergerbot.dto.GetProductRequest
import com.buyint.mergerbot.dto.MatchCompanyResponse
import io.reactivex.Observable

/**
 * Created by huheming on 2018/7/24
 */
interface IGetProductModel {
    fun getProduct(request: GetProductRequest): Observable<MatchCompanyResponse>
}