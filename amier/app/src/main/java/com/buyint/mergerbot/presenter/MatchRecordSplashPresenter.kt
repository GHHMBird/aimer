package com.buyint.mergerbot.presenter

import com.buyint.mergerbot.dto.MatchRecordLockRequest
import com.buyint.mergerbot.interfaces.IUserAndCompanyNameAutoComplete
import com.buyint.mergerbot.interfaces.IUserAndCompanyNameAutoCompleteModel
import com.buyint.mergerbot.interfaces.IUserNameLock
import com.buyint.mergerbot.interfaces.IUserNameLockModel
import com.buyint.mergerbot.model.UserAndCompanyNameAutoCompleteModel
import com.buyint.mergerbot.model.UserNameLockModel
import io.reactivex.disposables.Disposable

/**
 * Created by huheming on 2018/7/27
 */
class MatchRecordSplashPresenter(var iUserNameLock: IUserNameLock?, var iUserAndCompanyNameAutoComplete: IUserAndCompanyNameAutoComplete?) {

    private var Disposable: Disposable? = null
    private var Disposable4: Disposable? = null
    private var iUserNameLockModel: IUserNameLockModel? = null
    private var iUserAndCompanyNameAutoCompleteModel: IUserAndCompanyNameAutoCompleteModel? = null

    fun userNameLock(req: MatchRecordLockRequest) {
        if (iUserNameLockModel == null) {
            iUserNameLockModel = UserNameLockModel()
        }
        Disposable = iUserNameLockModel!!.userNameLock(req).subscribe({
            if (iUserNameLock != null) {
                iUserNameLock!!.userNameLockSuccess(it)
            }
        }, {
            if (iUserNameLock != null) {
                iUserNameLock!!.userNameLockFail(it)
            }
        })
    }

    /*
     * 0name 1company
     */
    fun userAndCompanyNameAutoComplete(type: Int, userName: String, companyName: String) {
        if (iUserAndCompanyNameAutoCompleteModel == null) {
            iUserAndCompanyNameAutoCompleteModel = UserAndCompanyNameAutoCompleteModel()
        }
        Disposable4 = iUserAndCompanyNameAutoCompleteModel!!.userAndCompanyNameAutoComplete(userName, companyName).subscribe({
            if (iUserAndCompanyNameAutoComplete != null) {
                if (type == 0) {
                    iUserAndCompanyNameAutoComplete!!.userAndCompanyNameAutoCompleteUserNameSuccess(it)
                } else if (type == 1) {
                    iUserAndCompanyNameAutoComplete!!.userAndCompanyNameAutoCompleteUserCompanyNameSuccess(it)
                }
            }
        }, {
            if (iUserAndCompanyNameAutoComplete != null) {
                iUserAndCompanyNameAutoComplete!!.userAndCompanyNameAutoCompleteUserFail(it)
            }
        })
    }

    fun destory() {
        if (Disposable4 != null) {
            Disposable4!!.dispose()
            Disposable4 = null
        }
        if (Disposable != null) {
            Disposable!!.dispose()
            Disposable = null
        }
        if (iUserNameLock != null) {
            iUserNameLock = null
        }
        if (iUserAndCompanyNameAutoComplete != null) {
            iUserAndCompanyNameAutoComplete = null
        }
    }
}