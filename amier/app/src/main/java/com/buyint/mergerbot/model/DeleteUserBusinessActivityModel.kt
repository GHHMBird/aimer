package com.buyint.mergerbot.model

import com.buyint.mergerbot.dto.BooleanResponse
import com.buyint.mergerbot.helper.HttpHelper
import com.buyint.mergerbot.interfaces.IDeleteUserBusinessActivityModel
import io.reactivex.Observable

/**
 * Created by huheming on 2018/7/20
 */
class DeleteUserBusinessActivityModel : IDeleteUserBusinessActivityModel {
    override fun deleteUserBusinessActivity(id: String): Observable<BooleanResponse> {
        return HttpHelper.deleteUserBusinessActivity(id)
    }
}