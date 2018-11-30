package com.buyint.mergerbot.UIs.verification.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.buyint.mergerbot.R
import com.buyint.mergerbot.base.BaseActivity
import kotlinx.android.synthetic.main.activity_access_invite.*
import kotlinx.android.synthetic.main.toolbar_white.*

/**
 * created by licheng  on date 2018/8/17
 */
class InviteSendSuccessActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTitleWhiteAndTextBlank()
        setContentView(R.layout.activity_invite_sendsuccess)

        toolbar_white_title.text = getString(R.string.access_send_invite)
        toolbar_white_top.visibility = View.GONE
        toolbar_white_right_add_rl.visibility = View.GONE
        toolbar_white_right_search_rl.visibility = View.GONE
        toolbar_white_back.setOnClickListener { onBackPressed() }
        toolbar_white_title.visibility = View.GONE

        btn_send.setOnClickListener {

            val intent = Intent(this@InviteSendSuccessActivity, BusinessIdentityVerificationInviteLawyerActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)

        }
    }


}
