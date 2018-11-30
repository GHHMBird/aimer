package com.buyint.mergerbot.presenter

import com.buyint.mergerbot.interfaces.IGetQRString
import com.buyint.mergerbot.interfaces.IGetQRStringModel
import com.buyint.mergerbot.model.GetQRStringModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Created by huheming on 2018/8/21
 */
class MyQRcodePresenter(var iGetQRString: IGetQRString?) {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private var disposable: Disposable? = null
    private var iGetQRStringModel: IGetQRStringModel? = null

    fun getQRString() {
        if (iGetQRStringModel == null) {
            iGetQRStringModel = GetQRStringModel()
        }
        disposable = iGetQRStringModel!!.getQRString().subscribe({
            if (iGetQRString != null) {
                iGetQRString!!.getQRStringSuccess(it)
            }
        }, {
            if (iGetQRString != null) {
                iGetQRString!!.getQRStringFail(it)
            }
        })
        compositeDisposable.add(disposable!!)
    }

    fun destory() {
        compositeDisposable.dispose()
        if (iGetQRString != null) {
            iGetQRString = null
        }
    }
}