package com.buyint.mergerbot.UIs.matchRecord.activity

import android.os.Bundle
import android.view.View
import com.buyint.mergerbot.R
import com.buyint.mergerbot.base.BaseActivity
import kotlinx.android.synthetic.main.activity_match_record_more_relation.*
import kotlinx.android.synthetic.main.toolbar_white.*

/**
 * Created by huheming on 2018/8/30
 */
class MatchRecordMoreRelationActivity : BaseActivity() {

    private var oneClick = false
    private var twoClick = false
    private var threeClick = false
    private var fourClick = false
    private var fiveClick = false

    override fun onCreate(savedInstanceState: Bundle?) {

        setTitleWhiteAndTextBlank()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match_record_more_relation)

        toolbar_white_title.text = getString(R.string.relationship_setting)
        toolbar_white_back.setOnClickListener { onBackPressed() }
        toolbar_white_top.visibility = View.GONE
        toolbar_white_right_add_rl.visibility = View.GONE
        toolbar_white_right_search_rl.visibility = View.GONE

        activity_match_record_more_relation_ll1.setOnClickListener {
            if (oneClick) {
                activity_match_record_more_relation_iv1.setImageResource(R.drawable.ring_gray)
            } else {
                activity_match_record_more_relation_iv1.setImageResource(R.mipmap.blue_success_2)
            }
            oneClick = !oneClick
        }
        activity_match_record_more_relation_ll2.setOnClickListener {
            if (twoClick) {
                activity_match_record_more_relation_iv2.setImageResource(R.drawable.ring_gray)
            } else {
                activity_match_record_more_relation_iv2.setImageResource(R.mipmap.blue_success_2)
            }
            twoClick = !twoClick
        }
        activity_match_record_more_relation_ll3.setOnClickListener {
            if (threeClick) {
                activity_match_record_more_relation_iv3.setImageResource(R.drawable.ring_gray)
            } else {
                activity_match_record_more_relation_iv3.setImageResource(R.mipmap.blue_success_2)
            }
            threeClick = !threeClick
        }
        activity_match_record_more_relation_ll4.setOnClickListener {
            if (fourClick) {
                activity_match_record_more_relation_iv4.setImageResource(R.drawable.ring_gray)
            } else {
                activity_match_record_more_relation_iv4.setImageResource(R.mipmap.blue_success_2)
            }
            fourClick = !fourClick
        }
        activity_match_record_more_relation_ll5.setOnClickListener {
            if (fiveClick) {
                activity_match_record_more_relation_iv5.setImageResource(R.drawable.ring_gray)
            } else {
                activity_match_record_more_relation_iv5.setImageResource(R.mipmap.blue_success_2)
            }
            fiveClick = !fiveClick
        }
    }

}