package com.buyint.mergerbot.interfaces

import com.buyint.mergerbot.dto.MatchRecordListModel

/**
 * Created by huheming on 2018/8/21
 */
interface ISearchPeople {
    fun searchPeopleSuccess(response: ArrayList<MatchRecordListModel>)
    fun searchPeopleFail(throwable: Throwable)
}