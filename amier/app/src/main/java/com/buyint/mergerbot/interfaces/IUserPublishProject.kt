package com.buyint.mergerbot.interfaces

import com.buyint.mergerbot.dto.MatchCompanyMorePageResponse

/*
 * Created by huheming on 2018/6/7.
 */
interface IUserPublishProject {

    fun getUserPublishProjectSuccess(response: MatchCompanyMorePageResponse)

    fun getUserPublishProjectFail(throwable: Throwable)

    fun getUserPublishProjectMoreSuccess(response: MatchCompanyMorePageResponse)

    fun getUserPublishProjectMoreFail(throwable: Throwable)
}