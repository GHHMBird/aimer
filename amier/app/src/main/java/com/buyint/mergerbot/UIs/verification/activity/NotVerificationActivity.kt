package com.buyint.mergerbot.UIs.verification.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.buyint.mergerbot.R
import com.buyint.mergerbot.base.BaseActivity
import com.buyint.mergerbot.view.SelectableBackgroundView
import kotlinx.android.synthetic.main.activity_not_verification.*
import kotlinx.android.synthetic.main.toolbar_white.*

/**
 * Created by huheming on 2018/8/2
 */
class NotVerificationActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setTitleWhiteAndTextBlank()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_not_verification)

        toolbar_white_top.visibility = View.GONE
        toolbar_white_back.setOnClickListener { onBackPressed() }
        toolbar_white_right_add_rl.visibility = View.GONE
        toolbar_white_right_search_rl.visibility = View.GONE

        val type = intent.getStringExtra(getString(R.string.DATA))

        when (type) {
            getString(R.string.TYPE_0) -> {//深度匹配
                activity_not_verification_text.text = getString(R.string.verification_and_open_deep_match)
                activity_not_verification_iv.setImageResource(R.mipmap.deep_match_pic)
                activity_not_verification_hint1.text = getString(R.string.real_high_on_the_hand)
                activity_not_verification_hint2.text = getString(R.string.do_thing_with_people)
            }
            getString(R.string.TYPE_1) -> {//匹配录
                activity_not_verification_text.text = getString(R.string.verification_and_open_match_record)
                activity_not_verification_iv.setImageResource(R.mipmap.activity_match_record_splash)
                activity_not_verification_hint1.text = getString(R.string.match_record_text1)
                activity_not_verification_hint2.text = getString(R.string.match_record_text2)
            }
            getString(R.string.TYPE_2) -> {//商业关系链
                activity_not_verification_text.text = getString(R.string.verification_and_open_match_record)
                activity_not_verification_iv.setImageResource(R.mipmap.shangyeguanxilian)
                activity_not_verification_hint1.text = getString(R.string.match_record_text1)
                activity_not_verification_hint2.text = getString(R.string.match_record_text2)
            }
            getString(R.string.TYPE_3) -> {//靠谱度
                activity_not_verification_text.text = getString(R.string.verification_and_up_your_reliable)
                activity_not_verification_iv.setImageResource(R.mipmap.reliable)
                activity_not_verification_hint1.visibility = View.INVISIBLE
                activity_not_verification_hint2.text = getString(R.string.reliable_text_hint)
            }
            getString(R.string.TYPE_4) -> {//智约高手
                activity_not_verification_text.text = getString(R.string.verification_and_open_webview_search)
                activity_not_verification_iv.setImageResource(R.mipmap.zhiyuegaoshou)
                activity_not_verification_hint1.text = getString(R.string.real_high_on_the_hand)
                activity_not_verification_hint2.text = getString(R.string.do_thing_with_people)
            }
        }

        activity_not_verification_button.setListener(object : SelectableBackgroundView.SelectableBackgroundViewListener {
            override fun finished() {
                startActivity(Intent(this@NotVerificationActivity, BusinessIdentityVerificationActivity::class.java))
                finish()
            }
        })

        activity_not_verification_button.setOnClickListener {
            activity_not_verification_button.selectBackground()
        }
    }

}