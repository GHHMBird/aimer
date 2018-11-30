package com.buyint.mergerbot.interfaces

import com.buyint.mergerbot.dto.GetLawyerBean

/**
 * Created by huheming on 2018/8/23
 */
interface IGetLawyer {
    fun getLawyerSuccess(list: ArrayList<GetLawyerBean>)
    fun getLawyerFail(throwable: Throwable)
}