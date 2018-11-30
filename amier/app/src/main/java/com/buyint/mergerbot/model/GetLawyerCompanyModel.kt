package com.buyint.mergerbot.model

import com.buyint.mergerbot.dto.GetLawyerCompanyBean
import com.buyint.mergerbot.helper.HttpHelper
import com.buyint.mergerbot.interfaces.IGetLawyerCompanyModel
import io.reactivex.Observable

/**
 * Created by huheming on 2018/8/23
 */
class GetLawyerCompanyModel : IGetLawyerCompanyModel {
    override fun getLawyerCompany(name: String): Observable<ArrayList<GetLawyerCompanyBean>> {
        return HttpHelper.getLawyerCompany(name)
    }
}