package com.buyint.mergerbot.UIs.verification.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.buyint.mergerbot.R
import com.buyint.mergerbot.UIs.verification.fragment.HistoryRelationAdapter
import com.buyint.mergerbot.base.BaseActivity
import com.buyint.mergerbot.database.getBean
import com.buyint.mergerbot.database.saveBean
import com.buyint.mergerbot.dto.SourceTargetGetPeopleRelationship
import com.buyint.mergerbot.view.SelectableBackgroundView
import kotlinx.android.synthetic.main.activity_history_relation.*
import kotlinx.android.synthetic.main.toolbar_white.*

/***
 * Created by huheming on 2018/8/17
 */
class HistoryRelationActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setTitleWhiteAndTextBlank()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history_relation)

        toolbar_white_title.text = getString(R.string.see_history)
        toolbar_white_right_add_rl.visibility = View.GONE
        toolbar_white_right_search_rl.visibility = View.GONE
        toolbar_white_top.visibility = View.GONE
        toolbar_white_back.setOnClickListener { onBackPressed() }

        val bean = getBean(this)
        if (bean?.list != null && bean.list!!.size > 0) {
            activity_history_relation_recycler.layoutManager = LinearLayoutManager(this)
            activity_history_relation_recycler.adapter = HistoryRelationAdapter(this, bean.list!!)
        }

        activity_history_relation_clear.setListener(object:SelectableBackgroundView.SelectableBackgroundViewListener{
            override fun finished() {
                saveBean(this@HistoryRelationActivity, SourceTargetGetPeopleRelationship())
                activity_history_relation_recycler.visibility=View.GONE
            }
        })
    }

}