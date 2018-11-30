package com.buyint.mergerbot.interfaces

import com.buyint.mergerbot.dto.GetNameDetailResponse
import io.reactivex.Observable

/**
 * Created by huheming on 2018/8/14
 */
interface IGetNameDetailModel {
    fun getNameDetail(name: String): Observable<GetNameDetailResponse>
}