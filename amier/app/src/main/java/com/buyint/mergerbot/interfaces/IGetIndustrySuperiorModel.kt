package com.buyint.mergerbot.interfaces

import com.buyint.mergerbot.dto.IndustrySuperiorResponse
import io.reactivex.Observable

/**
 * Created by huheming on 2018/7/24
 */
interface IGetIndustrySuperiorModel {
    fun getIndustrySuperior(): Observable<IndustrySuperiorResponse>
}