package com.buyint.mergerbot.UIs.matchRecord.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import com.buyint.mergerbot.R
import com.buyint.mergerbot.UIs.contact.ContactAddFirstActivity
import com.buyint.mergerbot.UIs.contact.ContactAddSecondActivity
import com.buyint.mergerbot.UIs.verification.activity.InviteActivity
import com.buyint.mergerbot.base.AppApplication.context
import com.buyint.mergerbot.base.BaseActivity
import com.buyint.mergerbot.dto.ContactAddRequest
import com.buyint.mergerbot.dto.MatchRecordListModel
import com.buyint.mergerbot.enums.RelationType
import com.buyint.mergerbot.helper.HttpHelper
import kotlinx.android.synthetic.main.activity_match_record_more.*
import kotlinx.android.synthetic.main.toolbar_white.*
import java.text.SimpleDateFormat

/**
 * Created by huheming on 2018/8/30
 */
class MatchRecordMoreActivity : BaseActivity() {

    private var response: ContactAddRequest? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        setTitleWhiteAndTextBlank()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match_record_more)

        val model = intent.getSerializableExtra(getString(R.string.DATA)) as MatchRecordListModel

        toolbar_white_title.text = getString(R.string.match_record)
        toolbar_white_back.setOnClickListener { onBackPressed() }
        toolbar_white_top.visibility = View.GONE
        toolbar_white_right_add_rl.visibility = View.GONE
        toolbar_white_right_search_rl.visibility = View.GONE

        activity_match_record_more_relation.setOnClickListener {
            val intent = Intent(this@MatchRecordMoreActivity, ContactAddSecondActivity::class.java)
            intent.putExtra("personBean", response!!.personInfo)
            intent.putExtra("states", 2)
            intent.putExtra("mPersonInfo", response)
            startActivity(intent)
            finish()
        }

        activity_match_record_more_setting.setOnClickListener {
            startActivity(Intent(this@MatchRecordMoreActivity, MatchRecordMoreRelationActivity::class.java))
        }

        activity_match_record_more_ps.setOnClickListener {
            val intent = Intent(context, ContactAddFirstActivity::class.java)
            intent.putExtra("model", model)
            context.startActivity(intent)
            finish()
        }

        activity_match_record_more_message.setOnClickListener {
            if (!TextUtils.isEmpty(model.phoneNumber)) {
                sendMessage(model.phoneNumber!!)
            } else {
                showToast(getString(R.string.please_add_his_phone))
            }
        }

        activity_match_record_more_email.setOnClickListener {
            if (!TextUtils.isEmpty(model.phoneNumber)) {
                val intent = Intent(this@MatchRecordMoreActivity, InviteActivity::class.java)
                intent.putExtra(getString(R.string.TYPE), 2)
                intent.putExtra(getString(R.string.EMAIL), response!!.personInfo.email)
                startActivity(intent)
            } else {
                showToast(getString(R.string.please_add_his_phone))
            }
        }

        showLoadingFragment(R.id.activity_match_record_more_fl)
        HttpHelper.getAddressListByPersonId(model.personId).subscribe({
            removeLoadingFragment()
            if (it != null) {
                this.response = it
                setData()
            }
        }, {
            removeLoadingFragment()
            showThrowable(it)
        })
    }

    private fun setData() {
        when (response!!.personInfo.relationType) {
            RelationType.WORK_MATE.name -> {//同事
                activity_match_record_more_relation_tv2.text = getString(R.string.work_mate)
                activity_match_record_more_tv1.text = getString(R.string.affiliated_company)
                activity_match_record_more_tv2.text = getString(R.string.department)
                activity_match_record_more_tv3.text = getString(R.string.start_and_end_time)
                if (response?.workMateRelation != null) {
                    if (!TextUtils.isEmpty(response!!.workMateRelation.companyName)) {
                        activity_match_record_more_tv11.text = response!!.workMateRelation.companyName
                    } else {
                        activity_match_record_more_tv_ll1.visibility = View.GONE
                    }
                    if (!TextUtils.isEmpty(response!!.workMateRelation.departmentName)) {
                        activity_match_record_more_tv21.text = response!!.workMateRelation.departmentName
                    } else {
                        activity_match_record_more_tv_ll2.visibility = View.GONE
                    }
                    if (response!!.workMateRelation.startDate == 0L && response!!.workMateRelation.endDate == 0L) {
                        activity_match_record_more_tv_ll3.visibility = View.GONE
                    } else {
                        val sdf = SimpleDateFormat("yyyy/MM/dd")
                        activity_match_record_more_tv31.text = sdf.format(response!!.workMateRelation.startDate) + " to " + sdf.format(response!!.workMateRelation.endDate)
                    }
                    val sb = StringBuilder()
                    if (null == response!!.workMateRelation.sameDepartment || !response!!.workMateRelation.sameDepartment) {
                        sb.append(getString(R.string.different_department)).append("    ")
                    } else {
                        sb.append(getString(R.string.same_department)).append("    ")
                    }
                    if (null == response!!.workMateRelation.sameDepartment || !response!!.workMateRelation.sameDepartment) {
                        sb.append(getString(R.string.dispartnership))
                    } else {
                        sb.append(getString(R.string.paernership))
                    }
                    activity_match_record_more_tv41.text = sb.toString()
                } else {
                    activity_match_record_more_ll.visibility = View.GONE
                }
            }
            RelationType.BUSINESS_FRIEND.name -> {//商友
                activity_match_record_more_relation_tv2.text = getString(R.string.business_friend)
                if (response?.personRelation == null) {
                    activity_match_record_more_ll.visibility = View.GONE
                } else {
                    activity_match_record_more_tv1.text = getString(R.string.cooperation_projects)
                    activity_match_record_more_tv2.text = getString(R.string.start_and_end_time)
                    activity_match_record_more_tv3.text = getString(R.string.participant)
                    activity_match_record_more_tv4.text = getString(R.string.project_desc)
                    if (!TextUtils.isEmpty(response!!.personRelation.projectName)) {
                        activity_match_record_more_tv11.text = response!!.personRelation.projectName
                    } else {
                        activity_match_record_more_tv_ll1.visibility = View.GONE
                    }
                    if (response!!.personRelation.startDate == 0L && response!!.personRelation.endDate == 0L) {
                        activity_match_record_more_tv_ll2.visibility = View.GONE
                    } else {
                        val sdf = SimpleDateFormat("yyyy/MM/dd")
                        activity_match_record_more_tv21.text = sdf.format(response!!.personRelation.startDate) + " to " + sdf.format(response!!.personRelation.endDate)
                    }
                    activity_match_record_more_tv_ll3.visibility = View.GONE
                    if (!TextUtils.isEmpty(response!!.personRelation.description)) {
                        activity_match_record_more_tv41.text = response!!.personRelation.description
                    } else {
                        activity_match_record_more_tv_ll4.visibility = View.GONE
                    }
                }
            }
            RelationType.CUSTOMER.name -> {//客户
                activity_match_record_more_relation_tv2.text = getString(R.string.client)
                if (response?.personRelation == null) {
                    activity_match_record_more_ll.visibility = View.GONE
                } else {
                    activity_match_record_more_tv1.text = getString(R.string.contact_cus_jobname)
                    activity_match_record_more_tv2.text = getString(R.string.start_and_end_time)
                    activity_match_record_more_tv3.text = getString(R.string.participant)
                    activity_match_record_more_tv4.text = getString(R.string.contact_cus_jobdes)
                    if (!TextUtils.isEmpty(response!!.personRelation.projectName)) {
                        activity_match_record_more_tv11.text = response!!.personRelation.projectName
                    } else {
                        activity_match_record_more_tv_ll1.visibility = View.GONE
                    }
                    if (response!!.personRelation.startDate == 0L && response!!.personRelation.endDate == 0L) {
                        activity_match_record_more_tv_ll2.visibility = View.GONE
                    } else {
                        val sdf = SimpleDateFormat("yyyy/MM/dd")
                        activity_match_record_more_tv21.text = sdf.format(response!!.personRelation.startDate) + " to " + sdf.format(response!!.personRelation.endDate)
                    }
                    activity_match_record_more_tv_ll3.visibility = View.GONE
                    if (!TextUtils.isEmpty(response!!.personRelation.description)) {
                        activity_match_record_more_tv41.text = response!!.personRelation.description
                    } else {
                        activity_match_record_more_tv_ll4.visibility = View.GONE
                    }
                }
            }
            RelationType.OTHER.name -> {
                activity_match_record_more_relation_tv2.text = getString(R.string.other)
                activity_match_record_more_tv_ll1.visibility = View.GONE
                activity_match_record_more_tv_ll2.visibility = View.GONE
                activity_match_record_more_tv_ll3.visibility = View.GONE
                if (!TextUtils.isEmpty(response!!.personInfo.relationName)) {
                    activity_match_record_more_tv41.text = response!!.personInfo.relationName
                } else {
                    activity_match_record_more_tv_ll4.visibility = View.GONE
                }
            }
            RelationType.WORK_MATE_AND_BUSINESS_FRIEND.name -> {
                activity_match_record_more_relation_tv1.visibility = View.VISIBLE
                activity_match_record_more_relation_tv1.text = getString(R.string.work_mate)
                activity_match_record_more_relation_tv2.text = getString(R.string.business_friend)
                activity_match_record_more_ll.visibility = View.GONE

            }
        }
    }

    private fun sendMessage(phoneNumber: String) {
        val intent = Intent(Intent.ACTION_SENDTO)
        val uri = Uri.parse("smsto:$phoneNumber")
        intent.data = uri
        startActivity(intent)
    }

}