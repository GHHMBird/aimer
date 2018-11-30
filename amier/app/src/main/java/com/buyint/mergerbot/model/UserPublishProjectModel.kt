package com.buyint.mergerbot.model

import com.buyint.mergerbot.dto.MatchCompanyMorePageResponse
import com.buyint.mergerbot.dto.PageRequest
import com.buyint.mergerbot.helper.HttpHelper
import com.buyint.mergerbot.interfaces.IUserPublishProjectModel
import io.reactivex.Observable

/*
 * Created by huheming on 2018/6/7.
 */
class UserPublishProjectModel : IUserPublishProjectModel {

    override fun getUserPublishProject(request: PageRequest): Observable<MatchCompanyMorePageResponse> {
        return HttpHelper.getMyProjectList(request)
    }
}