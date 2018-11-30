package com.buyint.mergerbot.interfaces

import com.buyint.mergerbot.dto.StringResponse
import io.reactivex.Observable
import java.io.File

/**
 * Created by huheming on 2018/7/24
 */
interface IUpdateFileModel {
    fun updateFile(file: File): Observable<StringResponse>
}