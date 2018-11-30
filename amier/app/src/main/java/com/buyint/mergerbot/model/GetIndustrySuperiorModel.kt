package com.buyint.mergerbot.model

import com.buyint.mergerbot.dto.IndustrySuperiorResponse
import com.buyint.mergerbot.helper.HttpHelper
import com.buyint.mergerbot.interfaces.IGetIndustrySuperiorModel
import io.reactivex.Observable

/**
 * Created by huheming on 2018/7/24
 */
class GetIndustrySuperiorModel : IGetIndustrySuperiorModel {
    override fun getIndustrySuperior(): Observable<IndustrySuperiorResponse> {
        return HttpHelper.getIndustrySuperior()
    }
}