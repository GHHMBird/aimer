package com.buyint.mergerbot.UIs.contact

import android.os.Bundle
import com.buyint.mergerbot.R
import com.buyint.mergerbot.UIs.contact.mvp.ContactMateContract
import com.buyint.mergerbot.UIs.contact.mvp.ContactMatePresent
import com.buyint.mergerbot.Utility.DateUtils
import com.buyint.mergerbot.base.AppApplication
import com.buyint.mergerbot.base.BaseActivity
import com.buyint.mergerbot.dto.ContactAddRequest
import com.buyint.mergerbot.dto.ContactPersonInfoBean
import com.buyint.mergerbot.dto.ContactworkMateRelation
import com.buyint.mergerbot.view.pickerview.TimePickerDialog
import com.buyint.mergerbot.view.pickerview.data.Type
import kotlinx.android.synthetic.main.activity_contact_mate.*
import kotlinx.android.synthetic.main.layout_contact_title.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * created by licheng  on date 2018/8/8
 */
class ContactMateActivity : BaseActivity(), ContactMateContract.View {

    private lateinit var mateRelation: ContactworkMateRelation
    private lateinit var personBean: ContactPersonInfoBean
    private var Isjob: Boolean? = true
    private var IsCooperate: Boolean? = true
    private lateinit var mPresent: ContactMateContract.Present
    private var startTime: Long? = null
    private var endtTime: Long? = null
    private var states = 1//1表示原生添加，2表示跳过来修改
    private var mPersonInfo: ContactAddRequest? = null
    override
    fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTitleWhiteAndTextBlank()
        setContentView(R.layout.activity_contact_mate)

        if (null != intent.getSerializableExtra("mPersonInfo")) {
            mPersonInfo = intent.getSerializableExtra("mPersonInfo") as ContactAddRequest
        }

        if (null != mPersonInfo) {
            if (null != mPersonInfo?.workMateRelation) {
                if (null != mPersonInfo?.workMateRelation?.companyName) {
                    edit_company_name.setText(mPersonInfo?.workMateRelation?.companyName)
                }
                if (null != mPersonInfo?.workMateRelation?.departmentName) {
                    edit_company_job.setText(mPersonInfo?.workMateRelation?.departmentName)
                }
                if (null != mPersonInfo?.workMateRelation?.startDate) {
                    tv_time_start.text = DateUtils.getdata(mPersonInfo?.workMateRelation?.startDate)
                    startTime = mPersonInfo?.workMateRelation?.startDate
                }
                if (null != mPersonInfo?.workMateRelation?.endDate) {
                    tv_time_end.text = DateUtils.getdata(mPersonInfo?.workMateRelation?.endDate)
                    endtTime = mPersonInfo?.workMateRelation?.endDate
                }

                if (null != mPersonInfo?.workMateRelation?.sameDepartment && !mPersonInfo?.workMateRelation?.sameDepartment!!) {
                    Isjob = false
                    iv_job_yes.setBackgroundResource(R.drawable.ring_gray)
                    iv_job_no.setBackgroundResource(R.drawable.ring_blue)
                }

                if (null != mPersonInfo?.workMateRelation?.sameDepartment && !mPersonInfo?.workMateRelation?.partnership!!) {
                    iv_cooperate_no.setBackgroundResource(R.drawable.ring_blue)
                    iv_cooperate_yes.setBackgroundResource(R.drawable.ring_gray)
                    IsCooperate = false
                }
            }
        }

        tv_contact_title.text = getString(R.string.contact_mate_title)
        AppApplication.getAppApplication().addActivity(this@ContactMateActivity)
        mateRelation = ContactworkMateRelation()
        personBean = intent.getSerializableExtra("personBean") as ContactPersonInfoBean
        ContactMatePresent(this)
        states = intent.getIntExtra("states", -1)
        iv_job_yes.setOnClickListener {
            Isjob = true

            iv_job_yes.setBackgroundResource(R.drawable.ring_blue)
            iv_job_no.setBackgroundResource(R.drawable.ring_gray)
        }

        iv_job_no.setOnClickListener {
            Isjob = false

            iv_job_yes.setBackgroundResource(R.drawable.ring_gray)
            iv_job_no.setBackgroundResource(R.drawable.ring_blue)
        }

        iv_cooperate_yes.setOnClickListener {
            IsCooperate = true
            iv_cooperate_no.setBackgroundResource(R.drawable.ring_gray)
            iv_cooperate_yes.setBackgroundResource(R.drawable.ring_blue)
        }

        iv_cooperate_no.setOnClickListener {
            iv_cooperate_no.setBackgroundResource(R.drawable.ring_blue)
            iv_cooperate_yes.setBackgroundResource(R.drawable.ring_gray)
            IsCooperate = false
        }

        //时间  开始

        tv_time_start.setOnClickListener {

            val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")

            val build = TimePickerDialog.Builder()
                    .setType(Type.YEAR_MONTH_DAY)
                    .setCallBack { _, millseconds ->
                        val string = simpleDateFormat.format(Date(millseconds))
                        startTime = millseconds
                        tv_time_start.text = string
                    }
                    .build()
            build.show(supportFragmentManager, "a")

        }


        //时间 结束

        tv_time_end.setOnClickListener {
            val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")

            val build = TimePickerDialog.Builder()
                    .setType(Type.YEAR_MONTH_DAY)
                    .setCallBack { _, millseconds ->
                        val string = simpleDateFormat.format(Date(millseconds))
                        endtTime = millseconds
                        tv_time_end.text = string
                    }
                    .build()
            build.show(supportFragmentManager, "a")
        }




        btn_contact_next.setOnClickListener {

            showLoadingFragment(R.id.activity_contact_mate_fl)

            val company_name = edit_company_name.text.toString().trim()
            val company_job = edit_company_job.text.toString().trim()
            var time_start = tv_time_start.text.toString().trim()
            var time_end = tv_time_end.text.toString().trim()
            val b_Isjob = Isjob
            val b_IsCooperate = IsCooperate

            mateRelation.companyName = company_name
            mateRelation.departmentName = company_job
            mateRelation.startDate = startTime
            mateRelation.endDate = endtTime
            mateRelation.sameDepartment = b_Isjob
            mateRelation.partnership = b_IsCooperate

            val confirmationRequest = ContactAddRequest()

            confirmationRequest.personInfo = personBean
            confirmationRequest.workMateRelation = mateRelation

            if (states == 2) {
                mPresent.putName(confirmationRequest)
            } else {
                mPresent.addName(confirmationRequest)
            }
        }

        iv_contact_back.setOnClickListener { onBackPressed() }

    }

    override fun addtNameSuccess(succ: Boolean) {
        AppApplication.getAppApplication().cleanActivity()
        removeLoadingFragment()
    }

    override fun addtNameFailed() {
        removeLoadingFragment()
    }

    override fun setPresent(present: ContactMateContract.Present) {
        mPresent = present
    }


    override fun putNameSuccess(succ: Boolean) {
        AppApplication.getAppApplication().cleanActivity()
        removeLoadingFragment()
    }

    override fun aputNameFailed() {
        removeLoadingFragment()
    }

}
