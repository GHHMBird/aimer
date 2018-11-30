package com.buyint.mergerbot.UIs.trainAimer.activity

import android.os.Bundle
import android.view.View
import com.buyint.mergerbot.R
import com.buyint.mergerbot.base.BaseActivity
import com.buyint.mergerbot.view.SelectableBackgroundView
import kotlinx.android.synthetic.main.activity_train_aimer_help.*
import kotlinx.android.synthetic.main.toolbar_white.*

/**
 * Created by huheming on 2018/9/14
 */
class TrainAimerHelpActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setTitleWhiteAndTextBlank()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_train_aimer_help)

        toolbar_white_top.visibility = View.GONE
        toolbar_white_right_add_rl.visibility = View.GONE
        toolbar_white_right_search_rl.visibility = View.GONE
        toolbar_white_back.setOnClickListener { onBackPressed() }

        activity_train_aimer_help_sb.setListener(object : SelectableBackgroundView.SelectableBackgroundViewListener {
            override fun finished() {
                onBackPressed()
            }
        })
    }
}