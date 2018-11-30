package com.buyint.mergerbot.UIs.verification.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.buyint.mergerbot.R
import com.buyint.mergerbot.base.BaseFragment

/**
 * Created by huheming on 2018/8/2
 */
@SuppressLint("ValidFragment")
class BusinessIdentityVerificationSuccessFragment1() : BaseFragment() {

    private var listener: BusinessIdentityVerificationSuccessFragment1Listener? = null

    constructor(listener: BusinessIdentityVerificationSuccessFragment1Listener) : this() {
        this.listener = listener
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val inflate = LayoutInflater.from(activity).inflate(R.layout.fragment_business_identity_verification_success_one, null, false)
        inflate.findViewById<View>(R.id.fragment_business_identity_verification_success_one_next_page).setOnClickListener {
            listener?.goToPage2()
        }
        inflate.findViewById<View>(R.id.fragment_business_identity_verification_success_one_invite).setOnClickListener {
            listener?.invite()
        }

        return inflate
    }

    interface BusinessIdentityVerificationSuccessFragment1Listener {
        fun goToPage2()
        fun invite()
    }
}