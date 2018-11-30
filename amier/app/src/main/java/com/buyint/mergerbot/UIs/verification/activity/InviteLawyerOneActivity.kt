package com.buyint.mergerbot.UIs.verification.activity

import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import com.buyint.mergerbot.R
import com.buyint.mergerbot.UIs.match.activity.QuickMatchActivity
import com.buyint.mergerbot.base.BaseActivity
import com.buyint.mergerbot.dto.GetLawyerBean
import com.buyint.mergerbot.dto.GetLawyerCompanyBean
import com.buyint.mergerbot.dto.InviteLawyerBean
import com.buyint.mergerbot.view.SelectableBackgroundView
import kotlinx.android.synthetic.main.activity_invite_lawyer_one.*
import kotlinx.android.synthetic.main.toolbar_white.*

/**
 * created by licheng  on date 2018/8/20
 */
class InviteLawyerOneActivity : BaseActivity(), View.OnClickListener {

    private var companyBean = GetLawyerCompanyBean()
    private var nameBean = GetLawyerBean()
    private var canClick = true

    override fun onClick(v: View?) {
        val intent = Intent(this@InviteLawyerOneActivity, QuickMatchActivity::class.java)
        when (v) {
            activity_invite_lawyer_one_name -> {
                intent.putExtra(getString(R.string.data1), nameBean)
                if (null == companyBean.law_id) {
                    companyBean.law_id = ""
                }
                intent.putExtra(getString(R.string.data2), companyBean.law_id)
                intent.putExtra(getString(R.string.TYPE), 18)
                startActivityForResult(intent, 10)
            }
            activity_invite_lawyer_one_position -> {
                if (canClick) {
                    intent.putExtra(getString(R.string.NAME), getString(R.string.write_company_position))
                    startActivityForResult(intent, 11)
                }
            }
            activity_invite_lawyer_one_company -> {
                if (canClick) {
                    intent.putExtra(getString(R.string.DATA), companyBean)
                    intent.putExtra(getString(R.string.TYPE), 17)
                    startActivityForResult(intent, 12)
                }
            }
            activity_invite_lawyer_one_website -> {
                if (canClick) {
                    intent.putExtra(getString(R.string.NAME), getString(R.string.please_write_company_website))
                    startActivityForResult(intent, 13)
                }
            }
            activity_invite_lawyer_one_email -> {
                if (canClick) {
                    intent.putExtra(getString(R.string.NAME), getString(R.string.please_write_company_email))
                    startActivityForResult(intent, 14)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTitleWhiteAndTextBlank()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_invite_lawyer_one)
        toolbar_white_title.text = getString(R.string.invite_info_title)
        toolbar_white_top.visibility = View.GONE
        toolbar_white_right_add_rl.visibility = View.GONE
        toolbar_white_right_search_rl.visibility = View.GONE
        toolbar_white_back.setOnClickListener { onBackPressed() }

        val domain = intent.getStringExtra(getString(R.string.DATA))
        val bean = intent.getSerializableExtra("amiAccessBean")

        activity_invite_lawyer_one_name.setOnClickListener(this)
        activity_invite_lawyer_one_position.setOnClickListener(this)
        activity_invite_lawyer_one_company.setOnClickListener(this)
        activity_invite_lawyer_one_website.setOnClickListener(this)
        activity_invite_lawyer_one_email.setOnClickListener(this)

        activity_invite_lawyer_one_bottom_tv.paint.flags = Paint.UNDERLINE_TEXT_FLAG
        activity_invite_lawyer_one_bottom_tv.setOnClickListener {
            //上传地理位置获取律师列表
            val intent = Intent(this@InviteLawyerOneActivity, BusinessIdentityVerificationInviteLawyerActivity::class.java)
            intent.putExtra(getString(R.string.DATA), domain)
            intent.putExtra("amiAccessBean", bean)
            startActivityForResult(intent, 1001)
        }

        activity_invite_lawyer_one_button.setListener(object : SelectableBackgroundView.SelectableBackgroundViewListener {
            override fun finished() {
                if (TextUtils.isEmpty(activity_invite_lawyer_one_name.text.toString().trim()) || TextUtils.isEmpty(activity_invite_lawyer_one_position.text.toString().trim()) || TextUtils.isEmpty(activity_invite_lawyer_one_company.text.toString().trim()) || TextUtils.isEmpty(activity_invite_lawyer_one_website.text.toString().trim()) || TextUtils.isEmpty(activity_invite_lawyer_one_email.text.toString().trim())) {
                    return
                }
                val inviteLawyerBean = InviteLawyerBean()
                if (canClick) {
                    inviteLawyerBean.email = activity_invite_lawyer_one_email.text.toString().trim()
                    inviteLawyerBean.instituteName = activity_invite_lawyer_one_company.text.toString().trim()
                    inviteLawyerBean.instituteSite = activity_invite_lawyer_one_website.text.toString().trim()
                    inviteLawyerBean.lawyerId = ""
                    inviteLawyerBean.lawyerName = activity_invite_lawyer_one_name.text.toString().trim()
                    inviteLawyerBean.position = activity_invite_lawyer_one_position.text.toString().trim()
                } else {
                    inviteLawyerBean.email = nameBean.email
                    inviteLawyerBean.instituteName = nameBean.company
                    inviteLawyerBean.instituteSite = nameBean.source
                    inviteLawyerBean.lawyerId = nameBean.id
                    inviteLawyerBean.lawyerName = nameBean.name
                    inviteLawyerBean.position = nameBean.position
                }
                val intent = Intent(this@InviteLawyerOneActivity, InviteActivity::class.java)
                intent.putExtra(getString(R.string.TYPE), 1)
                intent.putExtra("amiAccessBean", bean)
                intent.putExtra(getString(R.string.DATA), inviteLawyerBean)
                startActivity(intent)
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 10 && resultCode == 1001) {
            nameBean = data!!.getSerializableExtra(getString(R.string.DATA)) as GetLawyerBean
            activity_invite_lawyer_one_name.text = nameBean.name
            canClick = TextUtils.isEmpty(nameBean.id)
            if (!canClick) {
                activity_invite_lawyer_one_position.text = nameBean.position
                activity_invite_lawyer_one_company.text = nameBean.company
                activity_invite_lawyer_one_website.text = nameBean.source
                activity_invite_lawyer_one_email.text = nameBean.email
            }
        } else if (requestCode == 11 && resultCode == 1001) {
            val text = data!!.getStringExtra(getString(R.string.DATA))
            activity_invite_lawyer_one_position.text = text
        } else if (requestCode == 12 && resultCode == 1001) {
            companyBean = data!!.getSerializableExtra(getString(R.string.DATA)) as GetLawyerCompanyBean
            activity_invite_lawyer_one_company.text = companyBean.name
        } else if (requestCode == 13 && resultCode == 1001) {
            val text = data!!.getStringExtra(getString(R.string.DATA))
            activity_invite_lawyer_one_website.text = text
        } else if (requestCode == 14 && resultCode == 1001) {
            val text = data!!.getStringExtra(getString(R.string.DATA))
            activity_invite_lawyer_one_email.text = text
        }
    }
}
