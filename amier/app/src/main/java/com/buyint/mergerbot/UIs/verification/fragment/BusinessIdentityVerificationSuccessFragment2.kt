package com.buyint.mergerbot.UIs.verification.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.buyint.mergerbot.R
import com.buyint.mergerbot.base.BaseFragment

/**
 * Created by huheming on 2018/8/2
 */
class BusinessIdentityVerificationSuccessFragment2 : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val inflate = LayoutInflater.from(activity).inflate(R.layout.fragment_business_identity_verification_success_two, null, false)
        return inflate
    }

}