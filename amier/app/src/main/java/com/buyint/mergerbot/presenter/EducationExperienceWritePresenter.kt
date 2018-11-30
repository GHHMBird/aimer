package com.buyint.mergerbot.presenter

import com.buyint.mergerbot.dto.AddEducationExperienceRequest
import com.buyint.mergerbot.interfaces.*
import com.buyint.mergerbot.model.AddEducationExperienceModel
import com.buyint.mergerbot.model.DeleteEducationExperienceModel
import com.buyint.mergerbot.model.PutEducationExperienceModel
import io.reactivex.disposables.Disposable


/**
 * Created by huheming on 2018/7/23
 */
class EducationExperienceWritePresenter(var iAddEducationExperience: IAddEducationExperience?, var iPutEducationExperience: IPutEducationExperience?, var iDeleteEducationExperience: IDeleteEducationExperience?) {

    private var Disposable: Disposable? = null
    private var Disposable2: Disposable? = null
    private var Disposable3: Disposable? = null
    private var iAddEducationExperienceModel: IAddEducationExperienceModel? = null
    private var iPutEducationExperienceModel: IPutEducationExperienceModel? = null
    private var iDeleteEducationExperienceModel: IDeleteEducationExperienceModel? = null

    fun addEducationExperience(request: AddEducationExperienceRequest) {
        if (iAddEducationExperienceModel == null) {
            iAddEducationExperienceModel = AddEducationExperienceModel()
        }
        Disposable = iAddEducationExperienceModel!!.addEducationExperience(request).subscribe({
            if (iAddEducationExperience != null) {
                iAddEducationExperience!!.addEducationExperienceSuccess(it)
            }
        }, {
            if (iAddEducationExperience != null) {
                iAddEducationExperience!!.addEducationExperienceFail(it)
            }
        })
    }

    fun putEducationExperience(request: AddEducationExperienceRequest) {
        if (iPutEducationExperienceModel == null) {
            iPutEducationExperienceModel = PutEducationExperienceModel()
        }
        Disposable2 = iPutEducationExperienceModel!!.putEducationExperience(request).subscribe({
            if (iPutEducationExperience != null) {
                iPutEducationExperience!!.putEducationExperienceSuccess(it)
            }
        }, {
            if (iPutEducationExperience != null) {
                iPutEducationExperience!!.putEducationExperienceFail(it)
            }
        })
    }

    fun deleteEducationExperience(id: String) {
        if (iDeleteEducationExperienceModel == null) {
            iDeleteEducationExperienceModel = DeleteEducationExperienceModel()
        }
        Disposable3 = iDeleteEducationExperienceModel!!.deleteEducationExperience(id).subscribe({
            if (iDeleteEducationExperience != null) {
                iDeleteEducationExperience!!.deleteEducationExperienceSuccess(it)
            }
        }, {
            if (iDeleteEducationExperience != null) {
                iDeleteEducationExperience!!.deleteEducationExperienceFail(it)
            }
        })
    }

    fun destory() {
        if (Disposable != null) {
            Disposable!!.dispose()
            Disposable = null
        }
        if (iAddEducationExperience != null) {
            iAddEducationExperience = null
        }
        if (Disposable2 != null) {
            Disposable2!!.dispose()
            Disposable2 = null
        }
        if (iPutEducationExperience != null) {
            iPutEducationExperience = null
        }
        if (Disposable3 != null) {
            Disposable3!!.dispose()
            Disposable3 = null
        }
        if (iDeleteEducationExperience != null) {
            iDeleteEducationExperience = null
        }
    }
}