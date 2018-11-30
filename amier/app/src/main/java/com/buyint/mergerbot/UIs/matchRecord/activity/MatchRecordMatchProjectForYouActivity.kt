package com.buyint.mergerbot.UIs.matchRecord.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.buyint.mergerbot.R
import com.buyint.mergerbot.UIs.main.adapter.ResultAdapter2
import com.buyint.mergerbot.base.BaseActivity
import com.buyint.mergerbot.helper.HttpHelper
import kotlinx.android.synthetic.main.activity_match_record_match_project_for_you.*
import kotlinx.android.synthetic.main.toolbar_white.*

/**
 * Created by huheming on 2018/9/10
 */
class MatchRecordMatchProjectForYouActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setTitleWhiteAndTextBlank()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match_record_match_project_for_you)

        toolbar_white_title.text = getString(R.string.project_with_you)
        toolbar_white_back.setOnClickListener { onBackPressed() }
        toolbar_white_top.visibility = View.GONE
        toolbar_white_right_add_rl.visibility = View.GONE
        toolbar_white_right_search_rl.visibility = View.GONE

        val personId = intent.getStringExtra(getString(R.string.DATA))

        showLoadingFragment(R.id.activity_match_record_match_project_for_you_fl)
        HttpHelper.matchRecordDetailProject(personId).subscribe({
            if (it?.data != null && it.data.size > 0) {
                for (bean in it.data) {
                    bean.type = getString(R.string.matchedRecordProjectDetail)
                }
                activity_match_record_match_project_for_you_recycler.layoutManager = LinearLayoutManager(this)
                activity_match_record_match_project_for_you_recycler.adapter = ResultAdapter2(this, it.data, personId, 0)
            }
            removeLoadingFragment()
        }, {
            removeLoadingFragment()
            showThrowable(it)
        })
    }

}