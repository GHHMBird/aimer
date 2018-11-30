package com.buyint.mergerbot.interfaces

import com.buyint.mergerbot.dto.ProjectIdNameBean

/**
 * Created by huheming on 2018/8/28
 */
interface IGetCompanyList {
    fun getCompanyListSuccess(list: ArrayList<ProjectIdNameBean>)
    fun getCompanyListFail(throwable: Throwable)
}