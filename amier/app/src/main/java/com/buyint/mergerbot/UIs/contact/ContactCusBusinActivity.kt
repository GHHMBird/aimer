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
import com.buyint.mergerbot.dto.ContactPersonRelation
import com.buyint.mergerbot.enums.RelationType
import com.buyint.mergerbot.view.pickerview.TimePickerDialog
import com.buyint.mergerbot.view.pickerview.data.Type
import kotlinx.android.synthetic.main.activity_contact_cusbusin.*
import kotlinx.android.synthetic.main.layout_contact_title.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * created by licheng  on date 2018/8/8
 */
class ContactCusBusinActivity : BaseActivity(), ContactMateContract.View {

    private var startTime: Long? = null
    private var endtTime: Long? = null
    private lateinit var personBean: ContactPersonInfoBean
    private lateinit var personRelation: ContactPersonRelation
    private lateinit var mPresent: ContactMateContract.Present
    private lateinit var type: String
    private var states = 1//1表示原生添加，2表示跳过来修改
    private var mPersonInfo: ContactAddRequest? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        setTitleWhiteAndTextBlank()
        super.onCreate(savedInstanceState)
        personBean = intent.getSerializableExtra("personBean") as ContactPersonInfoBean
        type = intent.getStringExtra("type")
        setContentView(R.layout.activity_contact_cusbusin)
        tv_contact_title.text = getString(R.string.contact_mate_title)
        states = intent.getIntExtra("states", -1)
        AppApplication.getAppApplication().addActivity(this@ContactCusBusinActivity)
        if (type == RelationType.BUSINESS_FRIEND.name) {
            tv_cus_title.text = getString(R.string.contact_bus_title)
            edit_company_name.hint = getString(R.string.contact_bus_jobname)
            edit_project_des.hint = getString(R.string.contact_bus_jobdes)
        } else if (type == RelationType.CUSTOMER.name) {
            tv_cus_title.text = getString(R.string.contact_cus_title)
            edit_company_name.hint = getString(R.string.contact_cus_jobname)
            edit_project_des.hint = getString(R.string.contact_cus_jobdes)
        }

        if (null != intent.getSerializableExtra("mPersonInfo")) {

            mPersonInfo = intent.getSerializableExtra("mPersonInfo") as ContactAddRequest
        }

        if (null != mPersonInfo) {

            if (null != mPersonInfo?.personRelation) {

                if (null != mPersonInfo?.personRelation?.projectName) {

                    edit_company_name.setText(mPersonInfo?.personRelation?.projectName)
                }
                if (null != mPersonInfo?.personRelation?.description) {

                    edit_project_des.setText(mPersonInfo?.personRelation?.description)
                }

                if (null != mPersonInfo?.personRelation?.startDate) {

                    tv_time_start.text = DateUtils.getdata(mPersonInfo?.personRelation?.startDate)
                    startTime = mPersonInfo?.personRelation?.startDate
                }

                if (null != mPersonInfo?.personRelation?.startDate) {
                    tv_time_end.text = DateUtils.getdata(mPersonInfo?.personRelation?.endDate)
                    endtTime = mPersonInfo?.personRelation?.endDate
                }
            }
        }


        ContactMatePresent(this)
        personRelation = ContactPersonRelation()


        //开始时间
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

        //开始时间
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

            val company_name = edit_company_name.text.toString().trim()
            var time_start = tv_time_start.text.toString().trim()
            var time_end = tv_time_end.text.toString().trim()
            val project_des = edit_project_des.text.toString().trim()

            personRelation.projectName = company_name
            personRelation.description = project_des
            personRelation.startDate = startTime
            personRelation.endDate = endtTime

            val confirmationRequest = ContactAddRequest()

            confirmationRequest.personInfo = personBean
            confirmationRequest.personRelation = personRelation

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
    }

    override fun addtNameFailed() {
    }

    override fun setPresent(present: ContactMateContract.Present) {
        mPresent = present
    }


    override fun putNameSuccess(succ: Boolean) {
        AppApplication.getAppApplication().cleanActivity()
    }

    override fun aputNameFailed() {
    }

}
