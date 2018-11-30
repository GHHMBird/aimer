package com.buyint.mergerbot.model

import com.buyint.mergerbot.dto.StringResponse
import com.buyint.mergerbot.helper.HttpHelper
import com.buyint.mergerbot.interfaces.IUpdateFileModel
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import io.reactivex.Observable
import java.io.File

/**
 * Created by huheming on 2018/7/24
 */
class UpdateFileModel : IUpdateFileModel {
    override fun updateFile(file: File): Observable<StringResponse> {
        val create = RequestBody.create(MediaType.parse("multipart/form-data"), file)
        val createFormData = MultipartBody.Part.createFormData("file", file.name, create)
        return HttpHelper.updateFile(createFormData)
    }
}