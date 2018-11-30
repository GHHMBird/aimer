package com.buyint.mergerbot.UIs.verification.activity

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import com.buyint.mergerbot.R
import com.buyint.mergerbot.UIs.loginAndRegister.adapter.RegistrationActivityViewPager
import com.buyint.mergerbot.UIs.verification.fragment.BusinessIdentityVerificationSuccessFragment1
import com.buyint.mergerbot.UIs.verification.fragment.BusinessIdentityVerificationSuccessFragment2
import com.buyint.mergerbot.base.BaseActivity
import kotlinx.android.synthetic.main.activity_business_identity_verification_success.*
import kotlinx.android.synthetic.main.toolbar_white.*

/**
 * Created by huheming on 2018/8/2
 */
class BusinessIdentityVerificationSuccessActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setTitleWhiteAndTextBlank()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_business_identity_verification_success)

        toolbar_white_top.visibility = View.GONE
        toolbar_white_right_add_rl.visibility = View.GONE
        toolbar_white_right_search_rl.visibility = View.GONE
        toolbar_white_title.text = getString(R.string.certification_rights)
        toolbar_white_back.setOnClickListener { onBackPressed() }

        val fragments = ArrayList<Fragment>()
        fragments.add(BusinessIdentityVerificationSuccessFragment1(object : BusinessIdentityVerificationSuccessFragment1.BusinessIdentityVerificationSuccessFragment1Listener {
            override fun invite() {
//                startActivity(Intent(this@BusinessIdentityVerificationSuccessActivity, HelpFriendVerificationActivity::class.java))
            }

            override fun goToPage2() {
                activity_business_identity_verification_success_viewpager.currentItem = 1
            }
        }))
        fragments.add(BusinessIdentityVerificationSuccessFragment2())

        val adapter = RegistrationActivityViewPager(supportFragmentManager, fragments)
        activity_business_identity_verification_success_viewpager.adapter = adapter

    }

}