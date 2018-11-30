package com.buyint.mergerbot.presenter

import com.buyint.mergerbot.interfaces.ISearchPeople
import com.buyint.mergerbot.interfaces.ISearchPeopleModel
import com.buyint.mergerbot.model.SearchPeopleModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Created by huheming on 2018/8/21
 */
class SearchPeoplePresenter(var iSearchPeople: ISearchPeople?) {

    private var compositeDisposable = CompositeDisposable()
    private var iSearchPeopleModel: ISearchPeopleModel? = null
    private var disposable: Disposable? = null

    fun searchPeople(text: String) {
        if (iSearchPeopleModel == null) {
            iSearchPeopleModel = SearchPeopleModel()
        }
        disposable = iSearchPeopleModel!!.searchPeople(text).subscribe({
            if (iSearchPeople != null) {
                iSearchPeople!!.searchPeopleSuccess(it)
            }
        }, {
            if (iSearchPeople != null) {
                iSearchPeople!!.searchPeopleFail(it)
            }
        })
        compositeDisposable.add(disposable!!)
    }

    fun destory() {
        compositeDisposable.dispose()
        if (iSearchPeople != null) {
            iSearchPeople = null
        }
    }
}