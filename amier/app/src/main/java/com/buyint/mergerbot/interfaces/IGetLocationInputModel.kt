package com.buyint.mergerbot.interfaces

import com.buyint.mergerbot.dto.CodeNameResponse
import io.reactivex.Observable

/**
 * Created by huheming on 2018/7/24
 */
interface IGetLocationInputModel {
    fun getLocationInput(place: String): Observable<CodeNameResponse>
}