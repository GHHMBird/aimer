package com.buyint.mergerbot.presenter

import com.buyint.mergerbot.dto.CodeNameBean
import com.buyint.mergerbot.interfaces.IPopularIndustry
import com.buyint.mergerbot.interfaces.IPopularIndustryModel
import com.buyint.mergerbot.interfaces.IUpdateFocusArea
import com.buyint.mergerbot.interfaces.IUpdateFocusAreaModel
import com.buyint.mergerbot.model.PopularIndustryModel
import com.buyint.mergerbot.model.UpdateFocusAreaModel
import io.reactivex.disposables.Disposable

/**
 * Created by huheming on 2018/7/23
 */
class FocusAreaPresenter(var iPopularIndustry: IPopularIndustry?, var iUpdateFocusArea: IUpdateFocusArea?) {

    private var subscribe: Disposable? = null
    private var subscribe2: Disposable? = null
    private var iPopularIndustryModel: IPopularIndustryModel? = null
    private var iUpdateFocusAreaModel: IUpdateFocusAreaModel? = null

    fun popularIndustry() {
        if (iPopularIndustryModel == null) {
            iPopularIndustryModel = PopularIndustryModel()
        }
        subscribe = iPopularIndustryModel!!.popularIndustry().subscribe({
            if (iPopularIndustry != null) {
                iPopularIndustry!!.popularIndustrySuccess(it)
            }
        }, {
            if (iPopularIndustry != null) {
                iPopularIndustry!!.popularIndustryFail(it)
            }
        })
    }

    fun updateFocusArea(list: ArrayList<CodeNameBean>) {
        if (iUpdateFocusAreaModel == null) {
            iUpdateFocusAreaModel = UpdateFocusAreaModel()
        }
        subscribe2 = iUpdateFocusAreaModel!!.updateFocusArea(list).subscribe({
            if (iUpdateFocusArea != null) {
                iUpdateFocusArea!!.updateFocusAreaSuccess(it)
            }
        }, {
            if (iUpdateFocusArea != null) {
                iUpdateFocusArea!!.updateFocusAreaFail(it)
            }
        })
    }

    fun ondestory() {
        if (subscribe != null) {
            subscribe!!.dispose()
            subscribe = null
        }
        if (iPopularIndustry != null) {
            iPopularIndustry = null
        }
        if (subscribe2 != null) {
            subscribe2!!.dispose()
            subscribe2 = null
        }
        if (iUpdateFocusArea != null) {
            iUpdateFocusArea = null
        }
    }
}