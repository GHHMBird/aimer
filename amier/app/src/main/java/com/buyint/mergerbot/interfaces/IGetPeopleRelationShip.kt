package com.buyint.mergerbot.interfaces

import com.buyint.mergerbot.dto.GetPeopleRelationShipModel

/**
 * Created by huheming on 2018/8/15
 */
interface IGetPeopleRelationShip {
    fun getPeopleRelationShipSuccess(response: GetPeopleRelationShipModel)
    fun getPeopleRelationShipFail(throwable: Throwable)
}