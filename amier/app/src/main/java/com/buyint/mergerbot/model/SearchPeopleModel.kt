package com.buyint.mergerbot.model

import com.buyint.mergerbot.dto.MatchRecordListModel
import com.buyint.mergerbot.helper.HttpHelper
import com.buyint.mergerbot.interfaces.ISearchPeopleModel
import io.reactivex.Observable

/**
 * Created by huheming on 2018/8/21
 */
class SearchPeopleModel : ISearchPeopleModel {
    override fun searchPeople(text: String): Observable<ArrayList<MatchRecordListModel>> {
        return HttpHelper.searchPeople(text)
    }
}