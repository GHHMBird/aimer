package com.buyint.mergerbot.interfaces

import com.buyint.mergerbot.dto.GetPeopleRelationShipModel
import io.reactivex.Observable

/**
 * Created by huheming on 2018/8/15
 */
interface IGetPeopleRelationShipModel {
    fun getPeopleRelationShip(source: String, target: String): Observable<GetPeopleRelationShipModel>
}