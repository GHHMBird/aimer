package com.buyint.mergerbot.interfaces

import com.buyint.mergerbot.dto.GetFriendListResponse
import io.reactivex.Observable

/**
 * Created by huheming on 2018/9/4
 */
interface IGetFriendListModel {
    fun getFriendList(text: String): Observable<GetFriendListResponse>
}