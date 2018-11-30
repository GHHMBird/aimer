package com.buyint.mergerbot.UIs.verification.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.buyint.mergerbot.R
import com.buyint.mergerbot.base.BaseActivity
import com.buyint.mergerbot.view.SelectableBackgroundView
import kotlinx.android.synthetic.main.activity_verification_prompt.*
import kotlinx.android.synthetic.main.toolbar_white.*

/**
 * Created by huheming on 2018/8/2
 */
class VerificationPromptActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setTitleWhiteAndTextBlank()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verification_prompt)

        toolbar_white_top.visibility = View.GONE
        toolbar_white_right_search_rl.visibility = View.GONE
        toolbar_white_right_add_rl.visibility = View.GONE

        activity_verification_prompt_button.setListener(object : SelectableBackgroundView.SelectableBackgroundViewListener {
            override fun finished() {
                startActivity(Intent(this@VerificationPromptActivity, BusinessIdentityVerificationActivity::class.java))
                finish()
            }
        })

        activity_verification_prompt_button.setOnClickListener {
            activity_verification_prompt_button.selectBackground()
        }

        toolbar_white_back.setOnClickListener {
            onBackPressed()
        }
    }

}