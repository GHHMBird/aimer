package com.buyint.mergerbot.interfaces

import com.buyint.mergerbot.dto.MatchRecordListModel
import io.reactivex.Observable

/**
 * Created by huheming on 2018/8/21
 */
interface ISearchPeopleModel {
    fun searchPeople(text: String): Observable<ArrayList<MatchRecordListModel>>
}