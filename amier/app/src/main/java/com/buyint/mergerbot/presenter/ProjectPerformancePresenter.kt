package com.buyint.mergerbot.presenter

import com.buyint.mergerbot.dto.GetUserProjectPerformanceListRequest
import com.buyint.mergerbot.interfaces.IGetUserProjectPerformanceList
import com.buyint.mergerbot.interfaces.IGetUserProjectPerformanceListModel
import com.buyint.mergerbot.model.GetUserProjectPerformanceListModel
import io.reactivex.disposables.Disposable

/**
 * Created by huheming on 2018/7/20
 */
class ProjectPerformancePresenter(var iGetProjectPerformance: IGetUserProjectPerformanceList?) {

    private var subscribe: Disposable? = null
    private var iGetProjectPerformanceModel: IGetUserProjectPerformanceListModel? = null

    fun getUserProjectPerformanceList(request: GetUserProjectPerformanceListRequest) {
        if (iGetProjectPerformanceModel == null) {
            iGetProjectPerformanceModel = GetUserProjectPerformanceListModel()
        }
        subscribe = iGetProjectPerformanceModel!!.getUserProjectPerformanceList(request).subscribe({
            if (iGetProjectPerformance != null) {
                iGetProjectPerformance!!.getUserProjectPerformanceListSuccess(it)
            }
        }, {
            if (iGetProjectPerformance != null) {
                iGetProjectPerformance!!.getUserProjectPerformanceListFail(it)
            }
        })
    }

    fun destory() {
        if (subscribe != null) {
            subscribe!!.dispose()
            subscribe = null
        }
        if (iGetProjectPerformance != null) {
            iGetProjectPerformance = null
        }
    }

}