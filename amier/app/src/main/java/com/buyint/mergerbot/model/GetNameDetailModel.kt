package com.buyint.mergerbot.model

import com.buyint.mergerbot.dto.GetNameDetailResponse
import com.buyint.mergerbot.helper.HttpHelper
import com.buyint.mergerbot.interfaces.IGetNameDetailModel
import io.reactivex.Observable

/**
 * Created by huheming on 2018/8/14
 */
class GetNameDetailModel : IGetNameDetailModel {
    override fun getNameDetail(name: String): Observable<GetNameDetailResponse> {
        return HttpHelper.getNameDetail(name)
    }
}