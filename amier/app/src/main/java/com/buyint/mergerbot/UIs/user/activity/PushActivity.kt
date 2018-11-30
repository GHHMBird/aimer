package com.buyint.mergerbot.UIs.user.activity

import android.os.Bundle
import com.buyint.mergerbot.R
import com.buyint.mergerbot.base.BaseActivity
import kotlinx.android.synthetic.main.toolbar.*

/**
 * Created by huheming on 2018/6/26
 */
class PushActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        setMyTitleColor()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_push)

        toorbar_title.text = getString(R.string.push_manager)
        toolbar_back.setOnClickListener { onBackPressed() }

    }
}