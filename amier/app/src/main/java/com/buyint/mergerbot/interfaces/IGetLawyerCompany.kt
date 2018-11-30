package com.buyint.mergerbot.interfaces

import com.buyint.mergerbot.dto.GetLawyerCompanyBean

/**
 * Created by huheming on 2018/8/23
 */
interface IGetLawyerCompany {
    fun getLawyerCompanySuccess(list: ArrayList<GetLawyerCompanyBean>)
    fun getLawyerCompanyFail(throwable: Throwable)
}