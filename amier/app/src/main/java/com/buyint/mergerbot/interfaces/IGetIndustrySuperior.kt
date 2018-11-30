package com.buyint.mergerbot.interfaces

import com.buyint.mergerbot.dto.IndustrySuperiorResponse

/**
 * Created by huheming on 2018/7/24
 */
interface IGetIndustrySuperior {
    fun getIndustrySuperiorSuccess(response: IndustrySuperiorResponse)
    fun getIndustrySuperiorFail(throwable: Throwable)
}