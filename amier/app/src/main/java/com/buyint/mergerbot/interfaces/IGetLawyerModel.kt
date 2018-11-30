package com.buyint.mergerbot.interfaces

import com.buyint.mergerbot.dto.GetLawyerBean
import io.reactivex.Observable

/**
 * Created by huheming on 2018/8/23
 */
interface IGetLawyerModel {
    fun getLawyer(name: String, law: String): Observable<ArrayList<GetLawyerBean>>
}