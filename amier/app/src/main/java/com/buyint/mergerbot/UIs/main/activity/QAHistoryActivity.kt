package com.buyint.mergerbot.UIs.main.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.buyint.mergerbot.R
import com.buyint.mergerbot.UIs.main.adapter.QAHistoryAdapter
import com.buyint.mergerbot.base.BaseActivity
import com.buyint.mergerbot.helper.HttpHelper
import kotlinx.android.synthetic.main.activity_qa_history.*
import kotlinx.android.synthetic.main.toolbar_white.*

/**
 * Created by huheming on 2018/9/7
 */
class QAHistoryActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setTitleWhiteAndTextBlank()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qa_history)

        toolbar_white_title.text = getString(R.string.history_project)
        toolbar_white_top.visibility = View.GONE
        toolbar_white_right_add_rl.visibility = View.GONE
        toolbar_white_back.setOnClickListener { onBackPressed() }

        HttpHelper.getHistoryProjectList().subscribe({
            if (it != null && it.size > 0) {
                activity_qa_history_recycler.layoutManager = LinearLayoutManager(this)
                activity_qa_history_recycler.adapter = QAHistoryAdapter(this, it)
            }
        }, {
            showThrowable(it)
        })

    }
}