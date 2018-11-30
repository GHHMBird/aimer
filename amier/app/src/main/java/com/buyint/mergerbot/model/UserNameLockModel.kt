package com.buyint.mergerbot.model

import com.buyint.mergerbot.dto.MatchRecordLockRequest
import com.buyint.mergerbot.dto.StringResponse
import com.buyint.mergerbot.helper.HttpHelper
import com.buyint.mergerbot.interfaces.IUserNameLockModel
import io.reactivex.Observable

/**
 * Created by huheming on 2018/7/27
 */
class UserNameLockModel : IUserNameLockModel {
    override fun userNameLock(req: MatchRecordLockRequest): Observable<StringResponse> {
        return HttpHelper.matchRecordLock(req)
    }
}