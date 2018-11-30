package com.buyint.mergerbot.model

import com.buyint.mergerbot.dto.BooleanResponse
import com.buyint.mergerbot.helper.HttpHelper
import com.buyint.mergerbot.interfaces.INoticeProjectModel
import io.reactivex.Observable

/**
 * Created by huheming on 2018/7/24
 */
class NoticeProjectModel : INoticeProjectModel {
    override fun noticeProject(id: String): Observable<BooleanResponse> {
        return HttpHelper.noticeProject(id)
    }
}