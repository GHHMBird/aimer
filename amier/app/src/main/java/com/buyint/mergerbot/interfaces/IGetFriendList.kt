package com.buyint.mergerbot.interfaces

import com.buyint.mergerbot.dto.GetFriendListResponse

/**
 * Created by huheming on 2018/9/4
 */
interface IGetFriendList {
    fun getFriendListSuccess(response: GetFriendListResponse)
    fun getFriendListFail(throwable: Throwable)
}