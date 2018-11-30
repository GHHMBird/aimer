package com.buyint.mergerbot.presenter

import com.buyint.mergerbot.dto.PutUserProjectPerformanceRequest
import com.buyint.mergerbot.interfaces.*
import com.buyint.mergerbot.model.DeleteUserProjectPerformanceModel
import com.buyint.mergerbot.model.GetUserProjectPerformanceModel
import com.buyint.mergerbot.model.PostUserProjectPerformanceModel
import com.buyint.mergerbot.model.PutUserProjectPerformanceModel
import io.reactivex.disposables.Disposable

/**
 * Created by huheming on 2018/7/20
 */
class ProjectPerformanceWritePresenter(var iPutUserProjectPerformance: IPutUserProjectPerformance?, var iGetUserProjectPerformance: IGetUserProjectPerformance?, var iPostUserProjectPerformance: IPostUserProjectPerformance?, var iDeleteUserProjectPerformance: IDeleteUserProjectPerformance?) {

    private var iPutUserProjectPerformanceModel: IPutUserProjectPerformanceModel? = null
    private var iGetUserProjectPerformanceModel: IGetUserProjectPerformanceModel? = null
    private var iPostUserProjectPerformanceModel: IPostUserProjectPerformanceModel? = null
    private var iDeleteUserProjectPerformanceModel: IDeleteUserProjectPerformanceModel? = null
    private var Disposable: Disposable? = null
    private var Disposable2: Disposable? = null
    private var Disposable3: Disposable? = null
    private var Disposable4: Disposable? = null

    fun putUserProjectPerformance(request: PutUserProjectPerformanceRequest) {
        if (iPutUserProjectPerformanceModel == null) {
            iPutUserProjectPerformanceModel = PutUserProjectPerformanceModel()
        }
        Disposable = iPutUserProjectPerformanceModel!!.putUserProjectPerformance(request).subscribe({
            if (iPutUserProjectPerformance != null) {
                iPutUserProjectPerformance!!.putUserProjectPerformanceSuccess(it)
            }
        }, {
            if (iPutUserProjectPerformance != null) {
                iPutUserProjectPerformance!!.putUserProjectPerformanceFail(it)
            }
        })
    }

    fun getUserProjectPerformance(id: String) {
        if (iGetUserProjectPerformanceModel == null) {
            iGetUserProjectPerformanceModel = GetUserProjectPerformanceModel()
        }
        Disposable2 = iGetUserProjectPerformanceModel!!.getUserProjectPerformance(id).subscribe({
            if (iGetUserProjectPerformance != null) {
                iGetUserProjectPerformance!!.getUserProjectPerformanceSuccess(it)
            }
        }, {
            if (iGetUserProjectPerformance != null) {
                iGetUserProjectPerformance!!.getUserProjectPerformanceFail(it)
            }
        })
    }

    fun postUserProjectPerformance(request: PutUserProjectPerformanceRequest) {
        if (iPostUserProjectPerformanceModel == null) {
            iPostUserProjectPerformanceModel = PostUserProjectPerformanceModel()
        }
        Disposable3 = iPostUserProjectPerformanceModel!!.postUserProjectPerformance(request).subscribe({
            if (iPostUserProjectPerformance != null) {
                iPostUserProjectPerformance!!.postUserProjectPerformanceSuccess(it)
            }
        }, {
            if (iPostUserProjectPerformance != null) {
                iPostUserProjectPerformance!!.postUserProjectPerformanceFail(it)
            }
        })
    }

    fun deleteUserProjectPerformance(id: String) {
        if (iDeleteUserProjectPerformanceModel == null) {
            iDeleteUserProjectPerformanceModel = DeleteUserProjectPerformanceModel()
        }
        Disposable4 = iDeleteUserProjectPerformanceModel!!.deleteUserProjectPerformance(id).subscribe({
            if (iDeleteUserProjectPerformance != null) {
                iDeleteUserProjectPerformance!!.deleteUserProjectPerformanceSuccess(it)
            }
        }, {
            if (iDeleteUserProjectPerformance != null) {
                iDeleteUserProjectPerformance!!.deleteUserProjectPerformanceFail(it)
            }
        })
    }

    fun destory() {
        if (Disposable != null) {
            Disposable!!.dispose()
            Disposable = null
        }
        if (iPutUserProjectPerformance != null) {
            iPutUserProjectPerformance = null
        }
        if (Disposable2 != null) {
            Disposable2!!.dispose()
            Disposable2 = null
        }
        if (iGetUserProjectPerformance != null) {
            iGetUserProjectPerformance = null
        }
        if (Disposable3 != null) {
            Disposable3!!.dispose()
            Disposable3 = null
        }
        if (iPostUserProjectPerformance != null) {
            iPostUserProjectPerformance = null
        }
        if (Disposable4 != null) {
            Disposable4!!.dispose()
            Disposable4 = null
        }
        if (iDeleteUserProjectPerformance != null) {
            iDeleteUserProjectPerformance = null
        }
    }

}