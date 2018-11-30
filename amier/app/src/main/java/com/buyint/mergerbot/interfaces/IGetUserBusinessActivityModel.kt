package com.buyint.mergerbot.interfaces

import com.buyint.mergerbot.dto.GetUserBusinessActivityResponse
import io.reactivex.Observable

/**
 * Created by huheming on 2018/7/20
 */
interface IGetUserBusinessActivityModel {
    fun getUserBusinessActivity(id: String): Observable<GetUserBusinessActivityResponse>
}