package com.buyint.mergerbot.model

import com.buyint.mergerbot.dto.GetProductRequest
import com.buyint.mergerbot.dto.MatchCompanyResponse
import com.buyint.mergerbot.helper.HttpHelper
import com.buyint.mergerbot.interfaces.IGetProductModel
import io.reactivex.Observable

/**
 * Created by huheming on 2018/7/24
 */
class GetProductModel : IGetProductModel {
    override fun getProduct(request: GetProductRequest): Observable<MatchCompanyResponse> {
        return HttpHelper.getProduct(request)
    }
}