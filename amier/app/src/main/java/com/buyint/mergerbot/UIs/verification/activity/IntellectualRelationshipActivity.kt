package com.buyint.mergerbot.UIs.verification.activity

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import com.buyint.mergerbot.R
import com.buyint.mergerbot.UIs.match.activity.QuickMatchActivity
import com.buyint.mergerbot.base.BaseActivity
import com.buyint.mergerbot.database.getLoginResponse
import com.buyint.mergerbot.dto.GetNameDetailModel
import com.buyint.mergerbot.view.SelectableBackgroundView
import kotlinx.android.synthetic.main.activity_intellectual_relationship.*
import kotlinx.android.synthetic.main.toolbar_white.*

/**
 * Created by huheming on 2018/8/13
 */
class IntellectualRelationshipActivity : BaseActivity() {

    private var target: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        setTitleWhiteAndTextBlank()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intellectual_relationship)

        toolbar_white_title.text = getString(R.string.match_market_relationship)
        toolbar_white_right_add_rl.visibility = View.GONE
        toolbar_white_right_search_rl.visibility = View.GONE
        toolbar_white_top.visibility = View.GONE
        toolbar_white_back.setOnClickListener { onBackPressed() }

        val loginResponse = getLoginResponse(this)

        if (loginResponse!!.model.authentication) {
            activity_intellectual_relationship_verification_ll.visibility = View.GONE
        }

        activity_intellectual_relationship_start_name.text = loginResponse.model.userName

        activity_intellectual_relationship_start_name.setOnClickListener {
            if (!loginResponse.model.authentication) {
                startActivity(Intent(this@IntellectualRelationshipActivity, VerificationPromptActivity::class.java))
            } else {
                //起点
            }
        }

        activity_intellectual_relationship_end_name.setOnClickListener {
            //终点
            val intent = Intent(this@IntellectualRelationshipActivity, QuickMatchActivity::class.java)
            intent.putExtra(getString(R.string.TYPE), 16)
            intent.putExtra(getString(R.string.TITLE), getString(R.string.please_write_the_name))
            intent.putExtra(getString(R.string.NAME), activity_intellectual_relationship_end_name.text.toString().trim())
            startActivityForResult(intent, 16)
        }

        activity_intellectual_relationship_btn.setListener(object : SelectableBackgroundView.SelectableBackgroundViewListener {
            override fun finished() {
                //开始匹配
                if (!TextUtils.isEmpty(target)) {
                    val response = getLoginResponse(this@IntellectualRelationshipActivity)
                    val intent = Intent(this@IntellectualRelationshipActivity, IntellectualRelationshipChartActivity::class.java)
                    intent.putExtra(getString(R.string.data1), response!!.model.matchRecordPersonId)
                    intent.putExtra(getString(R.string.data2), target!!)
                    intent.putExtra(getString(R.string.TITLE), activity_intellectual_relationship_start_name.text.toString().trim() + "--->" + activity_intellectual_relationship_end_name.text.toString().trim())
                    startActivity(intent)
                }
            }
        })

        activity_intellectual_relationship_verification_sbv.setListener(object : SelectableBackgroundView.SelectableBackgroundViewListener {
            override fun finished() {
                startActivity(Intent(this@IntellectualRelationshipActivity, VerificationPromptActivity::class.java))
            }
        })

        activity_intellectual_relationship_history.setOnClickListener {
            startActivity(Intent(this@IntellectualRelationshipActivity, HistoryRelationActivity::class.java))
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 16 && resultCode == 1001) {
            val model = data!!.getSerializableExtra(getString(R.string.DATA)) as GetNameDetailModel
            var name: String? = model.name
            if (TextUtils.isEmpty(name)) {
                name = ""
            }
            var company: String? = model.company
            if (TextUtils.isEmpty(company)) {
                company = ""
            }
            var role: String? = model.role
            if (TextUtils.isEmpty(role)) {
                val title = model.title
                role = if (title != null && title.size > 0) {
                    title[0]
                } else {
                    ""
                }
            }
            target = model.p_id
            activity_intellectual_relationship_end_name.text = "$name $company $role"
        }
    }
}