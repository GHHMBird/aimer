package com.buyint.mergerbot.model

import com.buyint.mergerbot.dto.CodeNameResponse
import com.buyint.mergerbot.helper.HttpHelper
import com.buyint.mergerbot.interfaces.IGetLocationInputModel
import io.reactivex.Observable

/**
 * Created by huheming on 2018/7/24
 */
class GetLocationInputModel : IGetLocationInputModel {
    override fun getLocationInput(place: String): Observable<CodeNameResponse> {
        return HttpHelper.getLocationInput(place)
    }
}