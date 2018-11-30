package com.buyint.mergerbot.UIs.verification.mvp

import com.buyint.mergerbot.dto.LayerlistRequest
import com.buyint.mergerbot.helper.HttpHelper

/**
 * created by licheng  on date 2018/8/16
 */
class LayerlistPresent(var mView: LayerlistContract.View) : LayerlistContract.Present {

    init {
        mView.setPresent(this)
    }

    override fun getLayerlist(body: LayerlistRequest) {
        HttpHelper.getAuthenticationLawyerList(body).subscribe({ mView.getLayerlistSuccess(it) }, { mView.getLayerlistFailed() })
    }
}
