package com.buyint.mergerbot.model

import com.buyint.mergerbot.dto.GetPeopleRelationShipModel
import com.buyint.mergerbot.helper.HttpHelper
import com.buyint.mergerbot.interfaces.IGetPeopleRelationShipModel
import io.reactivex.Observable

/**
 * Created by huheming on 2018/8/15
 */
class GetPeopleRelationShipModel : IGetPeopleRelationShipModel {
    override fun getPeopleRelationShip(source: String, target: String): Observable<GetPeopleRelationShipModel> {
        return HttpHelper.getPeopleRelationShip(source, target)
    }
}