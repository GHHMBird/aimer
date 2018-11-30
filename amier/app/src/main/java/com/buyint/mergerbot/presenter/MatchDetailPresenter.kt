package com.buyint.mergerbot.presenter

import com.buyint.mergerbot.interfaces.*
import com.buyint.mergerbot.model.DeleteProjectModel
import com.buyint.mergerbot.model.GetMatchCompanyDetailModel
import com.buyint.mergerbot.model.NotNoticeProjectModel
import com.buyint.mergerbot.model.NoticeProjectModel
import io.reactivex.disposables.Disposable

/**
 * Created by huheming on 2018/7/24
 */
class MatchDetailPresenter(var iGetMatchCompanyDetail: IGetMatchCompanyDetail?, var iNoticeProject: INoticeProject?, var iNotNoticeProject: INotNoticeProject?, var iDeleteProject: IDeleteProject?) {

    private var Disposable: Disposable? = null
    private var Disposable2: Disposable? = null
    private var Disposable3: Disposable? = null
    private var Disposable4: Disposable? = null
    private var iGetMatchCompanyDetailModel: IGetMatchCompanyDetailModel? = null
    private var iNoticeProjectModel: INoticeProjectModel? = null
    private var iNotNoticeProjectModel: INotNoticeProjectModel? = null
    private var iDeleteProjectModel: IDeleteProjectModel? = null

    fun getMatchCompanyDetail(click: String, typeId: String, type: String) {
        if (iGetMatchCompanyDetailModel == null) {
            iGetMatchCompanyDetailModel = GetMatchCompanyDetailModel()
        }
        Disposable = iGetMatchCompanyDetailModel!!.getMatchCompanyDetail(click, typeId, type).subscribe({
            if (iGetMatchCompanyDetail != null) {
                iGetMatchCompanyDetail!!.getMatchCompanyDetailSuccess(it)
            }
        }, {
            if (iGetMatchCompanyDetail != null) {
                iGetMatchCompanyDetail!!.getMatchCompanyDetailFail(it)
            }
        })
    }

    fun noticeProject(id: String) {
        if (iNoticeProjectModel == null) {
            iNoticeProjectModel = NoticeProjectModel()
        }
        Disposable2 = iNoticeProjectModel!!.noticeProject(id).subscribe({
            if (iNoticeProject != null) {
                iNoticeProject!!.noticeProjectSuccess(null, it)
            }
        }, {
            if (iNoticeProject != null) {
                iNoticeProject!!.noticeProjectFail(it)
            }
        })
    }

    fun notNoticeProject(id: String) {
        if (iNotNoticeProjectModel == null) {
            iNotNoticeProjectModel = NotNoticeProjectModel()
        }
        Disposable3 = iNotNoticeProjectModel!!.notNoticeProject(id).subscribe({
            if (iNotNoticeProject != null) {
                iNotNoticeProject!!.notNoticeProjectSuccess(null, it)
            }
        }, {
            if (iNotNoticeProject != null) {
                iNotNoticeProject!!.notNoticeProjectFail(it)
            }
        })
    }

    fun deleteProject(id: String) {
        if (iDeleteProjectModel == null) {
            iDeleteProjectModel = DeleteProjectModel()
        }
        Disposable4 = iDeleteProjectModel!!.deleteProject(id).subscribe({
            if (iDeleteProject != null) {
                iDeleteProject!!.deleteProjectSuccess(it)
            }
        }, {
            if (iDeleteProject != null) {
                iDeleteProject!!.deleteProjectFail(it)
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
        if (Disposable4 != null) {
            Disposable4!!.dispose()
            Disposable4 = null
        }
        if (iGetMatchCompanyDetail != null) {
            iGetMatchCompanyDetail = null
        }
        if (iNoticeProject != null) {
            iNoticeProject = null
        }
        if (iNotNoticeProject != null) {
            iNotNoticeProject = null
        }
        if (iDeleteProject != null) {
            iDeleteProject = null
        }
    }
}