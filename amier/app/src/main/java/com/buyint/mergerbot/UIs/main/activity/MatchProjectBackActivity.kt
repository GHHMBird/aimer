package com.buyint.mergerbot.UIs.main.activity

import android.os.Bundle
import android.view.View
import com.buyint.mergerbot.R
import com.buyint.mergerbot.base.BaseActivity
import com.buyint.mergerbot.dto.QAUserBackAndProjectBackBean
import com.buyint.mergerbot.helper.HttpHelper
import com.buyint.mergerbot.view.SelectableBackgroundView
import kotlinx.android.synthetic.main.activity_match_project_back.*
import kotlinx.android.synthetic.main.toolbar_white.*

/**
 * Created by huheming on 2018/9/5
 */
class MatchProjectBackActivity : BaseActivity(), View.OnClickListener {

    private var click = -1

    override fun onClick(v: View?) {
        when (v) {
            activity_match_project_back_ll1 -> {
                activity_match_project_back_iv1.setImageResource(R.drawable.ring_blue)
                activity_match_project_back_iv2.setImageResource(R.drawable.ring_gray)
                activity_match_project_back_iv3.setImageResource(R.drawable.ring_gray)
                activity_match_project_back_iv4.setImageResource(R.drawable.ring_gray)
                activity_match_project_back_iv5.setImageResource(R.drawable.ring_gray)
                activity_match_project_back_iv6.setImageResource(R.drawable.ring_gray)
                activity_match_project_back_iv7.setImageResource(R.drawable.ring_gray)
                click = 1
            }
            activity_match_project_back_ll2 -> {
                activity_match_project_back_iv1.setImageResource(R.drawable.ring_gray)
                activity_match_project_back_iv2.setImageResource(R.drawable.ring_blue)
                activity_match_project_back_iv3.setImageResource(R.drawable.ring_gray)
                activity_match_project_back_iv4.setImageResource(R.drawable.ring_gray)
                activity_match_project_back_iv5.setImageResource(R.drawable.ring_gray)
                activity_match_project_back_iv6.setImageResource(R.drawable.ring_gray)
                activity_match_project_back_iv7.setImageResource(R.drawable.ring_gray)
                click = 2
            }
            activity_match_project_back_ll3 -> {
                activity_match_project_back_iv1.setImageResource(R.drawable.ring_gray)
                activity_match_project_back_iv2.setImageResource(R.drawable.ring_gray)
                activity_match_project_back_iv3.setImageResource(R.drawable.ring_blue)
                activity_match_project_back_iv4.setImageResource(R.drawable.ring_gray)
                activity_match_project_back_iv5.setImageResource(R.drawable.ring_gray)
                activity_match_project_back_iv6.setImageResource(R.drawable.ring_gray)
                activity_match_project_back_iv7.setImageResource(R.drawable.ring_gray)
                click = 3
            }
            activity_match_project_back_ll4 -> {
                activity_match_project_back_iv1.setImageResource(R.drawable.ring_gray)
                activity_match_project_back_iv2.setImageResource(R.drawable.ring_gray)
                activity_match_project_back_iv3.setImageResource(R.drawable.ring_gray)
                activity_match_project_back_iv4.setImageResource(R.drawable.ring_blue)
                activity_match_project_back_iv5.setImageResource(R.drawable.ring_gray)
                activity_match_project_back_iv6.setImageResource(R.drawable.ring_gray)
                activity_match_project_back_iv7.setImageResource(R.drawable.ring_gray)
                click = 4
            }
            activity_match_project_back_ll5 -> {
                activity_match_project_back_iv1.setImageResource(R.drawable.ring_gray)
                activity_match_project_back_iv2.setImageResource(R.drawable.ring_gray)
                activity_match_project_back_iv3.setImageResource(R.drawable.ring_gray)
                activity_match_project_back_iv4.setImageResource(R.drawable.ring_gray)
                activity_match_project_back_iv5.setImageResource(R.drawable.ring_blue)
                activity_match_project_back_iv6.setImageResource(R.drawable.ring_gray)
                activity_match_project_back_iv7.setImageResource(R.drawable.ring_gray)
                click = 5
            }
            activity_match_project_back_ll6 -> {
                activity_match_project_back_iv1.setImageResource(R.drawable.ring_gray)
                activity_match_project_back_iv2.setImageResource(R.drawable.ring_gray)
                activity_match_project_back_iv3.setImageResource(R.drawable.ring_gray)
                activity_match_project_back_iv4.setImageResource(R.drawable.ring_gray)
                activity_match_project_back_iv5.setImageResource(R.drawable.ring_gray)
                activity_match_project_back_iv6.setImageResource(R.drawable.ring_blue)
                activity_match_project_back_iv7.setImageResource(R.drawable.ring_gray)
                click = 6
            }
            activity_match_project_back_ll7 -> {
                activity_match_project_back_iv1.setImageResource(R.drawable.ring_gray)
                activity_match_project_back_iv2.setImageResource(R.drawable.ring_gray)
                activity_match_project_back_iv3.setImageResource(R.drawable.ring_gray)
                activity_match_project_back_iv4.setImageResource(R.drawable.ring_gray)
                activity_match_project_back_iv5.setImageResource(R.drawable.ring_gray)
                activity_match_project_back_iv6.setImageResource(R.drawable.ring_gray)
                activity_match_project_back_iv7.setImageResource(R.drawable.ring_blue)
                click = 7
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTitleWhiteAndTextBlank()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match_project_back)

        toolbar_white_top.visibility = View.GONE
        toolbar_white_back.setOnClickListener { onBackPressed() }
        toolbar_white_title.text = getString(R.string.about_this_project)
        toolbar_white_right_add_rl.visibility = View.GONE
        toolbar_white_right_search_rl.visibility = View.GONE

        activity_match_project_back_ll1.setOnClickListener(this)
        activity_match_project_back_ll2.setOnClickListener(this)
        activity_match_project_back_ll3.setOnClickListener(this)
        activity_match_project_back_ll4.setOnClickListener(this)
        activity_match_project_back_ll5.setOnClickListener(this)
        activity_match_project_back_ll6.setOnClickListener(this)
        activity_match_project_back_ll7.setOnClickListener(this)

        activity_match_project_back_submit.setListener(object : SelectableBackgroundView.SelectableBackgroundViewListener {
            override fun finished() {
                if (click == -1) {
                    return
                }
                showLoadingFragment(R.id.activity_match_project_back_fl)
                var text = ""
                when (click) {
                    1 -> {
                        text = getString(R.string.project_is_not_my_heart)
                    }
                    2 -> {
                        text = getString(R.string.industry_is_wrong)
                    }
                    3 -> {
                        text = getString(R.string.price_is_wrong)
                    }
                    4 -> {
                        text = getString(R.string.place_is_wrong)
                    }
                    5 -> {
                        text = getString(R.string.product_is_wrong)
                    }
                    6 -> {
                        text = getString(R.string.project_detail_is_less)
                    }
                    7 -> {
                        text = activity_match_project_back_et.text.toString().trim()
                    }
                }
                val bean = QAUserBackAndProjectBackBean()
                bean.qid = intent.getStringExtra(getString(R.string.DATA))
                bean.text = text
                bean.type = "project"
                HttpHelper.qaUserBack(bean).subscribe({
                    removeLoadingFragment()
                    finish()
                }, {
                    removeLoadingFragment()
                    showThrowable(it)
                })
            }
        })
    }

}