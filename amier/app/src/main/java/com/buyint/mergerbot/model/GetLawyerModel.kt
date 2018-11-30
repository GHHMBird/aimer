package com.buyint.mergerbot.model

import com.buyint.mergerbot.dto.GetLawyerBean
import com.buyint.mergerbot.helper.HttpHelper
import com.buyint.mergerbot.interfaces.IGetLawyerModel
import io.reactivex.Observable

/**
 * Created by huheming on 2018/8/23
 */
class GetLawyerModel : IGetLawyerModel {
    override fun getLawyer(name: String, law: String): Observable<ArrayList<GetLawyerBean>> {
        return HttpHelper.getLawyer(name, law)
    }
}