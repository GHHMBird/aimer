package com.buyint.mergerbot.presenter

import com.buyint.mergerbot.interfaces.IMatchRecordDetailClient
import com.buyint.mergerbot.interfaces.IMatchRecordDetailClientModel
import com.buyint.mergerbot.interfaces.IMatchRecordList
import com.buyint.mergerbot.interfaces.IMatchRecordListModel
import com.buyint.mergerbot.model.MatchRecordDetailClientModel
import com.buyint.mergerbot.model.MatchRecordListModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Created by huheming on 2018/7/27
 */
class MatchRecordPresenter(var iMatchRecordList: IMatchRecordList?, var iMatchRecordDetailClient: IMatchRecordDetailClient?) {

    private val compositeDisposable = CompositeDisposable()
    private var Disposable: Disposable? = null
    private var Disposabl2: Disposable? = null
    private var iMatchRecordListModel: IMatchRecordListModel? = null
    private var iMatchRecordDetailClientModel: IMatchRecordDetailClientModel? = null

    fun matchRecordList(type: String) {
        if (iMatchRecordListModel == null) {
            iMatchRecordListModel = MatchRecordListModel()
        }
        Disposable = iMatchRecordListModel!!.matchRecordList(type).subscribe({
            if (iMatchRecordList != null) {
                iMatchRecordList!!.matchRecordListSuccess(it)
            }
        }, {
            if (iMatchRecordList != null) {
                iMatchRecordList!!.matchRecordListFail(it)
            }
        })
        compositeDisposable.add(Disposable!!)
    }

    fun matchRecordDetailClient(personId: String) {
        if (iMatchRecordDetailClientModel == null) {
            iMatchRecordDetailClientModel = MatchRecordDetailClientModel()
        }
        Disposabl2 = iMatchRecordDetailClientModel!!.matchRecordDetailClient(personId).subscribe({
            if (iMatchRecordDetailClient != null) {
                iMatchRecordDetailClient!!.matchRecordDetailClientSuccess(it)
            }
        }, {
            if (iMatchRecordDetailClient != null) {
                iMatchRecordDetailClient!!.matchRecordDetailClientFail(it)
            }
        })
        compositeDisposable.add(Disposabl2!!)
    }

    fun destory() {
        compositeDisposable.dispose()
        if (iMatchRecordDetailClient != null) {
            iMatchRecordDetailClient = null
        }
        if (iMatchRecordList != null) {
            iMatchRecordList = null
        }
    }
}