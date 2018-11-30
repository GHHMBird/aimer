package com.buyint.mergerbot.model

import com.buyint.mergerbot.dto.BooleanResponse
import com.buyint.mergerbot.dto.CodeNameBean
import com.buyint.mergerbot.helper.HttpHelper
import com.buyint.mergerbot.interfaces.IUpdateFocusAreaModel
import io.reactivex.Observable

/**
 * Created by huheming on 2018/7/23
 */
class UpdateFocusAreaModel : IUpdateFocusAreaModel {
    override fun updateFocusArea(list: ArrayList<CodeNameBean>): Observable<BooleanResponse> {
        return HttpHelper.updateFocusArea(list)
    }

}