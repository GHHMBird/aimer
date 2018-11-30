package com.buyint.mergerbot.presenter

import com.buyint.mergerbot.interfaces.*
import com.buyint.mergerbot.model.MatchRecordDetailClientModel
import com.buyint.mergerbot.model.MatchRecordDetailPersonModel
import com.buyint.mergerbot.model.MatchRecordDetailProjectModel
import io.reactivex.disposables.Disposable

/**
 * Created by huheming on 2018/7/27
 */
class MatchRecordDetailFragmentPresenter(var iMatchRecordPersonDetail: IMatchRecordDetailPerson?, var iMatchRecordDetailProject: IMatchRecordDetailProject?, var iMatchRecordDetailClient: IMatchRecordDetailClient?) {

    private var Disposable: Disposable? = null
    private var Disposable2: Disposable? = null
    private var Disposable3: Disposable? = null
    private var iMatchRecordDetailPersonModel: IMatchRecordDetailPersonModel? = null
    private var iMatchRecordDetailProjectModel: IMatchRecordDetailProjectModel? = null
    private var iMatchRecordDetailClientModel: IMatchRecordDetailClientModel? = null

    fun matchRecordDetailPerson(personId: String) {
        if (iMatchRecordDetailPersonModel == null) {
            iMatchRecordDetailPersonModel = MatchRecordDetailPersonModel()
        }
        Disposable = iMatchRecordDetailPersonModel!!.matchRecordDetailPerson(personId).subscribe({
            if (iMatchRecordPersonDetail != null) {
                iMatchRecordPersonDetail!!.matchRecordDetailPersonSuccess(it)
            }
        }, {
            if (iMatchRecordPersonDetail != null) {
                iMatchRecordPersonDetail!!.matchRecordDetailPersonFail(it)
            }
        })
    }

    fun matchRecordDetailProject(personId: String) {
        if (iMatchRecordDetailProjectModel == null) {
            iMatchRecordDetailProjectModel = MatchRecordDetailProjectModel()
        }
        Disposable2 = iMatchRecordDetailProjectModel!!.matchRecordDetailProject(personId).subscribe({
            if (iMatchRecordDetailProject != null) {
                iMatchRecordDetailProject!!.matchRecordDetailProjectSuccess(it)
            }
        }, {
            if (iMatchRecordDetailProject != null) {
                iMatchRecordDetailProject!!.matchRecordDetailProjectFail(it)
            }
        })
    }

    fun matchRecordDetailClient(personId: String) {
        if (iMatchRecordDetailClientModel == null) {
            iMatchRecordDetailClientModel = MatchRecordDetailClientModel()
        }
        Disposable3 = iMatchRecordDetailClientModel!!.matchRecordDetailClient(personId).subscribe({
            if (iMatchRecordDetailClient != null) {
                iMatchRecordDetailClient!!.matchRecordDetailClientSuccess(it)
            }
        }, {
            if (iMatchRecordDetailClient != null) {
                iMatchRecordDetailClient!!.matchRecordDetailClientFail(it)
            }
        })
    }

    fun destory() {
        if (Disposable != null) {
            Disposable!!.dispose()
            Disposable = null
        }
        if (Disposable2 != null) {
            Disposable2!!.dispose()
            Disposable2 = null
        }
        if (Disposable3 != null) {
            Disposable3!!.dispose()
            Disposable3 = null
        }
        if (iMatchRecordDetailClient != null) {
            iMatchRecordDetailClient = null
        }
        if (iMatchRecordPersonDetail != null) {
            iMatchRecordPersonDetail = null
        }
        if (iMatchRecordDetailProject != null) {
            iMatchRecordDetailProject = null
        }
    }
}