package com.buyint.mergerbot.model

import com.buyint.mergerbot.dto.ProjectIdNameBean
import com.buyint.mergerbot.helper.HttpHelper
import com.buyint.mergerbot.interfaces.IGetCompanyListModel
import io.reactivex.Observable

/**
 * Created by huheming on 2018/8/28
 */
class GetCompanyListModel : IGetCompanyListModel {
    override fun getCompanyList(text: String): Observable<ArrayList<ProjectIdNameBean>> {
        return HttpHelper.getCompanyList(text)
    }
}