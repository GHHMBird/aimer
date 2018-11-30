package com.buyint.mergerbot.interfaces

import com.buyint.mergerbot.dto.MatchRecordLockRequest
import com.buyint.mergerbot.dto.StringResponse
import io.reactivex.Observable

/**
 * Created by huheming on 2018/7/27
 */
interface IUserNameLockModel {
    fun userNameLock(req: MatchRecordLockRequest): Observable<StringResponse>
}