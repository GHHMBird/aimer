package com.buyint.mergerbot.UIs.verification.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.buyint.mergerbot.R
import com.buyint.mergerbot.base.BaseActivity
import com.buyint.mergerbot.view.SelectableBackgroundView
import kotlinx.android.synthetic.main.activity_business_identity_verification_fail.*
import kotlinx.android.synthetic.main.toolbar_white.*

/**
 * Created by huheming on 2018/8/2
 */
class BusinessIdentityVerificationFailActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setTitleWhiteAndTextBlank()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_business_identity_verification_fail)

        toolbar_white_top.visibility = View.GONE
        toolbar_white_right_add_rl.visibility = View.GONE
        toolbar_white_right_search_rl.visibility = View.GONE
        toolbar_white_back.setOnClickListener { onBackPressed() }

        activity_business_identity_verification_fail_restart.setListener(object : SelectableBackgroundView.SelectableBackgroundViewListener {
            override fun finished() {
                startActivity(Intent(this@BusinessIdentityVerificationFailActivity, BusinessIdentityVerificationActivity::class.java))
                finish()
            }
        })
        activity_business_identity_verification_fail_lawyer.setListener(object : SelectableBackgroundView.SelectableBackgroundViewListener {
            override fun finished() {
                startActivity(Intent(this@BusinessIdentityVerificationFailActivity, BusinessIdentityVerificationInviteLawyerActivity::class.java))
                finish()
            }
        })

        activity_business_identity_verification_fail_restart.setOnClickListener {
            activity_business_identity_verification_fail_restart.selectBackground()
        }

        activity_business_identity_verification_fail_lawyer.setOnClickListener {
            activity_business_identity_verification_fail_lawyer.selectBackground()
        }
    }
}