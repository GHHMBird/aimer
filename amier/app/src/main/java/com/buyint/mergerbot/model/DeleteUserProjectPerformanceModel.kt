package com.buyint.mergerbot.model

import com.buyint.mergerbot.dto.BooleanResponse
import com.buyint.mergerbot.helper.HttpHelper
import com.buyint.mergerbot.interfaces.IDeleteUserProjectPerformanceModel
import io.reactivex.Observable

/**
 * Created by huheming on 2018/7/20
 */
class DeleteUserProjectPerformanceModel : IDeleteUserProjectPerformanceModel {
    override fun deleteUserProjectPerformance(id: String): Observable<BooleanResponse> {
        return HttpHelper.deleteUserProjectPerformance(id)
    }
}