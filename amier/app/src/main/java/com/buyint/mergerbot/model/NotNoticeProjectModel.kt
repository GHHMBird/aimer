package com.buyint.mergerbot.model

import com.buyint.mergerbot.dto.BooleanResponse
import com.buyint.mergerbot.helper.HttpHelper
import com.buyint.mergerbot.interfaces.INotNoticeProjectModel
import io.reactivex.Observable

/**
 * Created by huheming on 2018/7/24
 */
class NotNoticeProjectModel : INotNoticeProjectModel {
    override fun notNoticeProject(id: String): Observable<BooleanResponse> {
        return HttpHelper.notNoticeProject(id)
    }
}