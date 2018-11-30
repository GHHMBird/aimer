package com.buyint.mergerbot.interfaces

import com.buyint.mergerbot.dto.BooleanResponse
import com.buyint.mergerbot.dto.CodeNameBean
import io.reactivex.Observable

/**
 * Created by huheming on 2018/7/23
 */
interface IUpdateFocusAreaModel {
    fun updateFocusArea(list: ArrayList<CodeNameBean>): Observable<BooleanResponse>
}