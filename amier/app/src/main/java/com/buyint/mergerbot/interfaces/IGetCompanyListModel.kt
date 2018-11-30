package com.buyint.mergerbot.interfaces

import com.buyint.mergerbot.dto.ProjectIdNameBean
import io.reactivex.Observable

/**
 * Created by huheming on 2018/8/28
 */
interface IGetCompanyListModel {
    fun getCompanyList(text: String): Observable<ArrayList<ProjectIdNameBean>>
}