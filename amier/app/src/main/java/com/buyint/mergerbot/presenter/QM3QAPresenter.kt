package com.buyint.mergerbot.presenter

import com.buyint.mergerbot.dto.MatchCompanyBean
import com.buyint.mergerbot.interfaces.INotNoticeProject
import com.buyint.mergerbot.interfaces.INotNoticeProjectModel
import com.buyint.mergerbot.interfaces.INoticeProject
import com.buyint.mergerbot.interfaces.INoticeProjectModel
import com.buyint.mergerbot.model.NotNoticeProjectModel
import com.buyint.mergerbot.model.NoticeProjectModel
import io.reactivex.disposables.Disposable

/**
 * Created by huheming on 2018/7/24
 */
class QM3QAPresenter(var iNoticeProject: INoticeProject?, var iNotNoticeProject: INotNoticeProject?) {

    private var Disposable: Disposable? = null
    private var Disposable2: Disposable? = null
    private var iNoticeProjectModel: INoticeProjectModel? = null
    private var iNotNoticeProjectModel: INotNoticeProjectModel? = null

    fun noticeProject(bean: MatchCompanyBean, id: String) {
        if (iNoticeProjectModel == null) {
            iNoticeProjectModel = NoticeProjectModel()
        }
        Disposable = iNoticeProjectModel!!.noticeProject(id).subscribe({
            if (iNoticeProject != null) {
                iNoticeProject!!.noticeProjectSuccess(bean,it)
            }
        }, {
            if (iNoticeProject != null) {
                iNoticeProject!!.noticeProjectFail(it)
            }
        })
    }

    fun notNoticeProject(bean: MatchCompanyBean, id: String) {
        if (iNotNoticeProjectModel == null) {
            iNotNoticeProjectModel = NotNoticeProjectModel()
        }
        Disposable2 = iNotNoticeProjectModel!!.notNoticeProject(id).subscribe({
            if (iNotNoticeProject != null) {
                iNotNoticeProject!!.notNoticeProjectSuccess(bean,it)
            }
        }, {
            if (iNotNoticeProject != null) {
                iNotNoticeProject!!.notNoticeProjectFail(it)
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
        if (iNoticeProject != null) {
            iNoticeProject = null
        }
        if (iNotNoticeProject != null) {
            iNotNoticeProject = null
        }
    }
}