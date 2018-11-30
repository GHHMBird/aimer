package com.buyint.mergerbot.interfaces

import com.buyint.mergerbot.dto.GetLawyerCompanyBean
import io.reactivex.Observable

/**
 * Created by huheming on 2018/8/23
 */
interface IGetLawyerCompanyModel {
    fun getLawyerCompany(name: String): Observable<ArrayList<GetLawyerCompanyBean>>
}