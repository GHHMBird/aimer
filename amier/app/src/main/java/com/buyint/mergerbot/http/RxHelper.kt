package com.buyint.mergerbot.http

import com.buyint.mergerbot.dto.ContactAddRequest
import com.buyint.mergerbot.dto.ResultBean
import com.buyint.mergerbot.rx.RequestErrorThrowable
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * created by licheng  on date 2018/8/9
 */
object RxHelper {


    /**
     * 统一线程处理
     *
     * @param <T>
     * @return
    </T> */
    fun <T> rxSchedulerHelper(): ObservableTransformer<T, T> {    //compose简化线程
        return ObservableTransformer { observable ->
            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
        }
    }








}
