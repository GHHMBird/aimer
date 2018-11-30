package com.buyint.mergerbot.UIs.verification.mvp

import com.buyint.mergerbot.base.BaseView
import com.buyint.mergerbot.dto.LayerListBean
import com.buyint.mergerbot.dto.LayerlistRequest

/**
 * created by licheng  on date 2018/8/16
 */
class LayerlistContract {

    interface Present {
        fun getLayerlist(body: LayerlistRequest)
    }

    interface View : BaseView<Present> {
        fun getLayerlistSuccess(list: List<LayerListBean>)
        fun getLayerlistFailed()
    }
}
