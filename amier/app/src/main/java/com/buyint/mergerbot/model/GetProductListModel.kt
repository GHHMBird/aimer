package com.buyint.mergerbot.model

import com.buyint.mergerbot.dto.GetAutoProductResponse
import com.buyint.mergerbot.helper.HttpHelper
import com.buyint.mergerbot.interfaces.IGetProductListModel
import io.reactivex.Observable

/**
 * Created by huheming on 2018/7/24
 */
class GetProductListModel : IGetProductListModel {
    override fun getProductList(product: String): Observable<GetAutoProductResponse> {
        return HttpHelper.getProductList(product)
    }
}