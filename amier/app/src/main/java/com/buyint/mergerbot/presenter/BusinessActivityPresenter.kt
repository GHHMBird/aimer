package com.buyint.mergerbot.presenter

import com.buyint.mergerbot.dto.GetUserProjectPerformanceListRequest
import com.buyint.mergerbot.interfaces.IGetUserBusinessActivityList
import com.buyint.mergerbot.interfaces.IGetUserBusinessActivityListModel
import com.buyint.mergerbot.model.GetUserBusinessActivityListModel
import io.reactivex.disposables.Disposable

/**
 * Created by huheming on 2018/7/20
 */
class BusinessActivityPresenter(var iGetUserBusinessActivityList: IGetUserBusinessActivityList?) {

    private var subscribe: Disposable? = null
    private var iGetUserBusinessActivityListModel: IGetUserBusinessActivityListModel? = null

    fun getUserBusinessActivityList(request:GetUserProjectPerformanceListRequest){
        if(iGetUserBusinessActivityListModel==null){
            iGetUserBusinessActivityListModel = GetUserBusinessActivityListModel()
        }
        subscribe = iGetUserBusinessActivityListModel!!.getUserBusinessActivityList(request).subscribe({
            if(iGetUserBusinessActivityList!=null){
                iGetUserBusinessActivityList!!.getUserBusinessActivityListSuccess(it)
            }
        },{
            if(iGetUserBusinessActivityList!=null){
                iGetUserBusinessActivityList!!.getUserBusinessActivityListFail(it)
            }
        })
    }

    fun destory() {
        if (subscribe != null) {
            subscribe!!.dispose()
            subscribe = null
        }
        if (iGetUserBusinessActivityList != null) {
            iGetUserBusinessActivityList = null
        }
    }
}