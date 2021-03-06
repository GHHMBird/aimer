package com.buyint.mergerbot.UIs.main.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.buyint.mergerbot.R
import com.buyint.mergerbot.base.BaseActivity
import com.buyint.mergerbot.dto.UserBackRequest
import com.buyint.mergerbot.helper.HttpHelper
import com.buyint.mergerbot.view.SelectableBackgroundView
import kotlinx.android.synthetic.main.activity_user_back.*
import kotlinx.android.synthetic.main.toolbar_white.*

/**
 * Created by huheming on 2018/9/18
 */

class UserBackActivity : BaseActivity(), View.OnClickListener {
    override fun onClick(v: View?) {
        when (v) {
            activity_user_back_ll1 -> {
                activity_user_back_iv1.setImageResource(R.drawable.ring_blue)
                activity_user_back_iv2.setImageResource(R.drawable.ring_gray)
                activity_user_back_iv3.setImageResource(R.drawable.ring_gray)
                activity_user_back_iv4.setImageResource(R.drawable.ring_gray)
                activity_user_back_iv5.setImageResource(R.drawable.ring_gray)
                activity_user_back_iv6.setImageResource(R.drawable.ring_gray)
                click = 1
            }
            activity_user_back_ll2 -> {
                activity_user_back_iv1.setImageResource(R.drawable.ring_gray)
                activity_user_back_iv2.setImageResource(R.drawable.ring_blue)
                activity_user_back_iv3.setImageResource(R.drawable.ring_gray)
                activity_user_back_iv4.setImageResource(R.drawable.ring_gray)
                activity_user_back_iv5.setImageResource(R.drawable.ring_gray)
                activity_user_back_iv6.setImageResource(R.drawable.ring_gray)
                click = 2
            }
            activity_user_back_ll3 -> {
                activity_user_back_iv1.setImageResource(R.drawable.ring_gray)
                activity_user_back_iv2.setImageResource(R.drawable.ring_gray)
                activity_user_back_iv3.setImageResource(R.drawable.ring_blue)
                activity_user_back_iv4.setImageResource(R.drawable.ring_gray)
                activity_user_back_iv5.setImageResource(R.drawable.ring_gray)
                activity_user_back_iv6.setImageResource(R.drawable.ring_gray)
                click = 3
            }
            activity_user_back_ll4 -> {
                activity_user_back_iv1.setImageResource(R.drawable.ring_gray)
                activity_user_back_iv2.setImageResource(R.drawable.ring_gray)
                activity_user_back_iv3.setImageResource(R.drawable.ring_gray)
                activity_user_back_iv4.setImageResource(R.drawable.ring_blue)
                activity_user_back_iv5.setImageResource(R.drawable.ring_gray)
                activity_user_back_iv6.setImageResource(R.drawable.ring_gray)
                click = 4
            }
            activity_user_back_ll5 -> {
                activity_user_back_iv1.setImageResource(R.drawable.ring_gray)
                activity_user_back_iv2.setImageResource(R.drawable.ring_gray)
                activity_user_back_iv3.setImageResource(R.drawable.ring_gray)
                activity_user_back_iv4.setImageResource(R.drawable.ring_gray)
                activity_user_back_iv5.setImageResource(R.drawable.ring_blue)
                activity_user_back_iv6.setImageResource(R.drawable.ring_gray)
                click = 5
            }
            activity_user_back_ll6 -> {
                activity_user_back_iv1.setImageResource(R.drawable.ring_gray)
                activity_user_back_iv2.setImageResource(R.drawable.ring_gray)
                activity_user_back_iv3.setImageResource(R.drawable.ring_gray)
                activity_user_back_iv4.setImageResource(R.drawable.ring_gray)
                activity_user_back_iv5.setImageResource(R.drawable.ring_gray)
                activity_user_back_iv6.setImageResource(R.drawable.ring_blue)
                click = 6
            }
        }
    }

    private var click = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        setTitleWhiteAndTextBlank()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_back)

        toolbar_white_top.visibility = View.GONE
        toolbar_white_back.setOnClickListener { onBackPressed() }
        toolbar_white_title.text = getString(R.string.feedback_and_suggestions)
        toolbar_white_right_add_rl.visibility = View.GONE
        toolbar_white_right_search_rl.visibility = View.GONE

        activity_user_back_ll1.setOnClickListener(this)
        activity_user_back_ll2.setOnClickListener(this)
        activity_user_back_ll3.setOnClickListener(this)
        activity_user_back_ll4.setOnClickListener(this)
        activity_user_back_ll5.setOnClickListener(this)
        activity_user_back_ll6.setOnClickListener(this)

        activity_user_back_history.setListener(object : SelectableBackgroundView.SelectableBackgroundViewListener {
            override fun finished() {
                startActivity(Intent(this@UserBackActivity, QAHistoryActivity::class.java))
            }
        })

        activity_user_back_submit.setListener(object : SelectableBackgroundView.SelectableBackgroundViewListener {
            override fun finished() {
                if (click == -1) {
                    return
                }
                showLoadingFragment(R.id.activity_user_back_fl)
                var text = ""
                when (click) {
                    1 -> {
                        text = getString(R.string.project_is_wrong)
                    }
                    2 -> {
                        text = getString(R.string.project_is_small)
                    }
                    3 -> {
                        text = getString(R.string.project_is_large)
                    }
                    4 -> {
                        text = getString(R.string.project_i_do_not_care)
                    }
                    5 -> {
                        text = getString(R.string.project_detail_is_small)
                    }
                    6 -> {
                        text = activity_user_back_et.text.toString().trim()
                    }
                }
                val bean = UserBackRequest()
                bean.content = text
                HttpHelper.userBack(bean).subscribe({
                    removeLoadingFragment()
                    //展示结束页面
//                        startActivity(Intent(this@QASpeakBackActivity, QASpeakBackFinishActivity::class.java))
                    showToast(getString(R.string.userback_update_success))
                    finish()
                }, {
                    removeLoadingFragment()
                    showThrowable(it)
                })
            }
        })
    }

}
