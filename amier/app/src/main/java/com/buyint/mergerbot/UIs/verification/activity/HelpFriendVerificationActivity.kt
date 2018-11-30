package com.buyint.mergerbot.UIs.verification.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.buyint.mergerbot.R
import com.buyint.mergerbot.UIs.verification.adapter.HelpFriendVerificationAdapter
import com.buyint.mergerbot.base.BaseActivity
import kotlinx.android.synthetic.main.activity_help_friend_verification.*
import kotlinx.android.synthetic.main.toolbar_white.*

/**
 * Created by huheming on 2018/8/7
 */
class HelpFriendVerificationActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setTitleWhiteAndTextBlank()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_help_friend_verification)

        toolbar_white_top.visibility = View.GONE
        toolbar_white_right_add_rl.visibility = View.GONE
        toolbar_white_right_search_rl.visibility = View.GONE
        toolbar_white_title.text = getString(R.string.help_friend_verification)
        toolbar_white_back.setOnClickListener { onBackPressed() }

        activity_help_friend_verification_recycler.layoutManager = LinearLayoutManager(this)
        activity_help_friend_verification_recycler.adapter = HelpFriendVerificationAdapter(this)
    }

}