package com.buyint.mergerbot.presenter

import com.buyint.mergerbot.dto.PutUserBusinessActivityRequest
import com.buyint.mergerbot.interfaces.*
import com.buyint.mergerbot.model.DeleteUserBusinessActivityModel
import com.buyint.mergerbot.model.GetUserBusinessActivityModel
import com.buyint.mergerbot.model.PostUserBusinessActivityModel
import com.buyint.mergerbot.model.PutUserBusinessActivityModel
import io.reactivex.disposables.Disposable

/**
 * Created by huheming on 2018/7/20
 */
class BusinessActivityWritePresenter(var iDeleteUserBusinessActivity: IDeleteUserBusinessActivity?, var iGetUserBusinessActivity: IGetUserBusinessActivity?, var iPostUserBusinessActivity: IPostUserBusinessActivity?, var iPutUserBusinessActivity: IPutUserBusinessActivity?) {

    private var subscribe: Disposable? = null
    private var subscribe2: Disposable? = null
    private var subscribe3: Disposable? = null
    private var subscribe4: Disposable? = null
    private var iDeleteBusinessActivityListModel: IDeleteUserBusinessActivityModel? = null
    private var iGetUserBusinessActivityModel: IGetUserBusinessActivityModel? = null
    private var iPostBusinessActivityModel: IPostUserBusinessActivityModel? = null
    private var iPutBusinessActivityModel: IPutUserBusinessActivityModel? = null

    fun deleteUserBusinessActivity(id: String) {
        if (iDeleteBusinessActivityListModel == null) {
            iDeleteBusinessActivityListModel = DeleteUserBusinessActivityModel()
        }
        subscribe = iDeleteBusinessActivityListModel!!.deleteUserBusinessActivity(id).subscribe({
            if (iDeleteUserBusinessActivity != null) {
                iDeleteUserBusinessActivity!!.deleteUserBusinessActivitySuccess(it)
            }
        }, {
            if (iDeleteUserBusinessActivity != null) {
                iDeleteUserBusinessActivity!!.deleteUserBusinessActivityFail(it)
            }
        })
    }

    fun getUserBusinessActivity(id: String) {
        if (iGetUserBusinessActivityModel == null) {
            iGetUserBusinessActivityModel = GetUserBusinessActivityModel()
        }
        subscribe2 = iGetUserBusinessActivityModel!!.getUserBusinessActivity(id).subscribe({
            if (iGetUserBusinessActivity != null) {
                iGetUserBusinessActivity!!.getUserBusinessActivitySuccess(it)
            }
        }, {
            if (iGetUserBusinessActivity != null) {
                iGetUserBusinessActivity!!.getUserBusinessActivityFail(it)
            }
        })
    }

    fun postUserBusinessActivity(request: PutUserBusinessActivityRequest) {
        if (iPostBusinessActivityModel == null) {
            iPostBusinessActivityModel = PostUserBusinessActivityModel()
        }
        subscribe3 = iPostBusinessActivityModel!!.postUserBusinessActivity(request).subscribe({
            if (iPostUserBusinessActivity != null) {
                iPostUserBusinessActivity!!.postUserBusinessActivitySuccess(it)
            }
        }, {
            if (iPostUserBusinessActivity != null) {
                iPostUserBusinessActivity!!.postUserBusinessActivityFail(it)
            }
        })
    }

    fun putUserBusinessActivity(request: PutUserBusinessActivityRequest) {
        if (iPutBusinessActivityModel == null) {
            iPutBusinessActivityModel = PutUserBusinessActivityModel()
        }
        subscribe4 = iPutBusinessActivityModel!!.putUserBusinessActivity(request).subscribe({
            if(iPutUserBusinessActivity!=null){
                iPutUserBusinessActivity!!.putUserBusinessActivitySuccess(it)
            }
        },{
            if(iPutUserBusinessActivity!=null){
                iPutUserBusinessActivity!!.putUserBusinessActivityFail(it)
            }
        })
    }

    fun destory() {
        if (subscribe != null) {
            subscribe!!.dispose()
            subscribe = null
        }
        if (subscribe2 != null) {
            subscribe2!!.dispose()
            subscribe2 = null
        }
        if (subscribe3 != null) {
            subscribe3!!.dispose()
            subscribe3 = null
        }
        if (subscribe4 != null) {
            subscribe4!!.dispose()
            subscribe4 = null
        }
        if (iDeleteUserBusinessActivity != null) {
            iDeleteUserBusinessActivity = null
        }
        if (iGetUserBusinessActivity != null) {
            iGetUserBusinessActivity = null
        }
        if (iPostUserBusinessActivity != null) {
            iPostUserBusinessActivity = null
        }
        if (iPutUserBusinessActivity != null) {
            iPutUserBusinessActivity = null
        }
    }
}