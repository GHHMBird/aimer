package com.buyint.mergerbot.model

import com.buyint.mergerbot.dto.GetFriendListResponse
import com.buyint.mergerbot.helper.HttpHelper
import com.buyint.mergerbot.interfaces.IGetFriendListModel
import io.reactivex.Observable

/**
 * Created by huheming on 2018/9/4
 */
class GetFriendListModel : IGetFriendListModel {
    override fun getFriendList(text: String): Observable<GetFriendListResponse> {
        return HttpHelper.getFriendList(text)
    }
}