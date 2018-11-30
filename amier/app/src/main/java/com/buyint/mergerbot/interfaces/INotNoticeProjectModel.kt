package com.buyint.mergerbot.interfaces

import com.buyint.mergerbot.dto.BooleanResponse
import io.reactivex.Observable

/**
 * Created by huheming on 2018/7/24
 */
interface INotNoticeProjectModel {
    fun notNoticeProject(id: String): Observable<BooleanResponse>
}