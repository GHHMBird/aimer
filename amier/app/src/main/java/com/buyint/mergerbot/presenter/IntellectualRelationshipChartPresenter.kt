package com.buyint.mergerbot.presenter

import com.buyint.mergerbot.interfaces.IGetPeopleRelationShip
import com.buyint.mergerbot.interfaces.IGetPeopleRelationShipModel
import com.buyint.mergerbot.model.GetPeopleRelationShipModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Created by huheming on 2018/8/15
 */
class IntellectualRelationshipChartPresenter(var iGetPeopleRelationShip: IGetPeopleRelationShip?) {

    private val compositeDisposable = CompositeDisposable()
    private var disposables: Disposable? = null
    private var iGetPeopleRelationShipModel: IGetPeopleRelationShipModel? = null

    fun getPeopleRelationShip(source: String, target: String) {
        if (iGetPeopleRelationShipModel == null) {
            iGetPeopleRelationShipModel = GetPeopleRelationShipModel()
        }
        disposables = iGetPeopleRelationShipModel!!.getPeopleRelationShip(source, target).subscribe({
            if (iGetPeopleRelationShip != null) {
                iGetPeopleRelationShip!!.getPeopleRelationShipSuccess(it)
            }
        }, {
            iGetPeopleRelationShip?.getPeopleRelationShipFail(it)
        })
        compositeDisposable.add(disposables!!)
    }

    fun destory() {
        compositeDisposable.clear()
        if (iGetPeopleRelationShip != null) {
            iGetPeopleRelationShip = null
        }
    }
}