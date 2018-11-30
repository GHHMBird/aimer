package com.buyint.mergerbot.presenter

import com.buyint.mergerbot.interfaces.IGetFriendList
import com.buyint.mergerbot.interfaces.IGetFriendListModel
import com.buyint.mergerbot.interfaces.ISendQRString
import com.buyint.mergerbot.interfaces.ISendQRStringModel
import com.buyint.mergerbot.model.GetFriendListModel
import com.buyint.mergerbot.model.SendQRStringModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Created by huheming on 2018/9/3
 */
class ScanQRcodePresenter(var iSendQRString: ISendQRString?, var iGetFriendList: IGetFriendList?) {

    private val compositeDisposable = CompositeDisposable()
    private var Disposable: Disposable? = null
    private var Disposabl2: Disposable? = null
    private var iSendQRStringModel: ISendQRStringModel? = null
    private var iGetFriendListModel: IGetFriendListModel? = null

    fun getFriendList(text: String) {
        if (iGetFriendListModel == null) {
            iGetFriendListModel = GetFriendListModel()
        }
        Disposabl2 = iGetFriendListModel!!.getFriendList(text).subscribe({
            if (iGetFriendList != null) {
                iGetFriendList!!.getFriendListSuccess(it)
            }
        }, {
            if (iGetFriendList != null) {
                iGetFriendList!!.getFriendListFail(it)
            }
        })
        compositeDisposable.add(Disposabl2!!)
    }

    fun sendQRString(text: String) {
        if (iSendQRStringModel == null) {
            iSendQRStringModel = SendQRStringModel()
        }
        Disposable = iSendQRStringModel!!.sendQRString(text).subscribe({
            if (iSendQRString != null) {
                iSendQRString!!.sendQRStringSuccess(it)
            }
        }, {
            if (iSendQRString != null) {
                iSendQRString!!.sendQRStringFail(it)
            }
        })
        compositeDisposable.add(Disposable!!)
    }

    fun destory() {
        compositeDisposable.dispose()
        if (iSendQRString != null) {
            iSendQRString = null
        }
        if (iGetFriendList != null) {
            iGetFriendList = null
        }
    }
}