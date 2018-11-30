package com.buyint.mergerbot.UIs.user.activity

import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.text.TextUtils
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.buyint.mergerbot.R
import com.buyint.mergerbot.UIs.match.activity.QuickMatchActivity
import com.buyint.mergerbot.UIs.setting.LocationActivity
import com.buyint.mergerbot.UIs.setting.WriteActivity
import com.buyint.mergerbot.Utility.ViewHelper
import com.buyint.mergerbot.base.BaseActivity
import com.buyint.mergerbot.dto.BooleanResponse
import com.buyint.mergerbot.dto.GetUserProjectPerformanceResponse
import com.buyint.mergerbot.dto.PutUserProjectPerformanceRequest
import com.buyint.mergerbot.dto.StringResponse
import com.buyint.mergerbot.interfaces.IDeleteUserProjectPerformance
import com.buyint.mergerbot.interfaces.IGetUserProjectPerformance
import com.buyint.mergerbot.interfaces.IPostUserProjectPerformance
import com.buyint.mergerbot.interfaces.IPutUserProjectPerformance
import com.buyint.mergerbot.presenter.ProjectPerformanceWritePresenter
import com.buyint.mergerbot.view.FluidLayout
import com.buyint.mergerbot.dto.FluidLayoutBean
import com.buyint.mergerbot.view.pickerview.TimePickerDialog
import com.buyint.mergerbot.view.pickerview.data.Type
import kotlinx.android.synthetic.main.activity_performance_achievement_write.*
import kotlinx.android.synthetic.main.toolbar.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by huheming on 2018/7/13
 */
class ProjectPerformanceWriteActivity : BaseActivity(), View.OnClickListener, IPutUserProjectPerformance, IGetUserProjectPerformance, IPostUserProjectPerformance, IDeleteUserProjectPerformance {

    private val sdf = SimpleDateFormat("yyyy年MM月")
    private var projectDesc = ""
    private var projectPosition = ""
    private var projectPerformance = ""
    private var relatedPeopleList = ArrayList<String>()
    private var partyList = ArrayList<String>()
    private var transactionTypeList = ArrayList<String>()
    private var presenter: ProjectPerformanceWritePresenter? = null
    private var startTime: Long? = null
    private var endTime: Long? = null
    private var id: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        presenter = ProjectPerformanceWritePresenter(this, this, this, this)

        setMyTitleColor()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_performance_achievement_write)

        toorbar_title.text = getString(R.string.project_performance)
        toolbar_back.setOnClickListener { onBackPressed() }
        val drawable = resources.getDrawable(R.mipmap.duigou)
        drawable.setBounds(0, 0, dp2px(16f), dp2px(16f))
        toolbar_right.setCompoundDrawables(null, null, drawable, null)
        toolbar_right_rl.setOnClickListener {
            showLoadingFragment(R.id.activity_performance_achievement_write_fl)
            if (TextUtils.isEmpty(id)) {
                updateData()
            } else {
                saveData()
            }
        }

        id = intent.getStringExtra(getString(R.string.DATA))
        if (TextUtils.isEmpty(id)) {
            activity_performance_achievement_write_delete.visibility = View.GONE
        } else {
            activity_performance_achievement_write_delete.visibility = View.VISIBLE
            presenter!!.getUserProjectPerformance(id!!)
        }

        activity_performance_achievement_write_project_name.setOnClickListener(this)
        activity_performance_achievement_write_company_name.setOnClickListener(this)
        activity_performance_achievement_write_position_name.setOnClickListener(this)
        activity_performance_achievement_write_start_time.setOnClickListener(this)
        activity_performance_achievement_write_end_time.setOnClickListener(this)
        activity_performance_achievement_write_related_people.setOnClickListener(this)
        activity_performance_achievement_write_party.setOnClickListener(this)
        activity_performance_achievement_write_transaction_type.setOnClickListener(this)
        activity_performance_achievement_write_company_address.setOnClickListener(this)
        activity_performance_achievement_write_project_desc.setOnClickListener(this)
        activity_performance_achievement_write_project_position.setOnClickListener(this)
        activity_performance_achievement_write_project_performance.setOnClickListener(this)
        activity_performance_achievement_write_delete.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        val intent = Intent()
        when (v) {
            activity_performance_achievement_write_project_name -> {
                intent.setClass(this@ProjectPerformanceWriteActivity, QuickMatchActivity::class.java)
                intent.putExtra(getString(R.string.NAME), getString(R.string.write_project_name))
                startActivityForResult(intent, 120)
            }
            activity_performance_achievement_write_company_name -> {
                intent.setClass(this@ProjectPerformanceWriteActivity, QuickMatchActivity::class.java)
                intent.putExtra(getString(R.string.NAME), getString(R.string.please_write_company_name))
                startActivityForResult(intent, 121)
            }
            activity_performance_achievement_write_position_name -> {
                intent.setClass(this@ProjectPerformanceWriteActivity, QuickMatchActivity::class.java)
                intent.putExtra(getString(R.string.NAME), getString(R.string.write_company_position))
                startActivityForResult(intent, 122)
            }
            activity_performance_achievement_write_start_time -> {
                val build = TimePickerDialog.Builder()
                        .setType(Type.YEAR_MONTH)
                        .setCallBack { _, millseconds ->
                            val string = sdf.format(Date(millseconds))
                            startTime = millseconds
                            activity_performance_achievement_write_start_time_tv.text = string
                        }
                        .build()
                build.show(supportFragmentManager, "a")
            }
            activity_performance_achievement_write_end_time -> {
                val build = TimePickerDialog.Builder()
                        .setType(Type.YEAR_MONTH)
                        .setCallBack { _, millseconds ->
                            val string = sdf.format(Date(millseconds))
                            endTime = millseconds
                            activity_performance_achievement_write_end_time_tv.text = string
                        }
                        .build()
                build.show(supportFragmentManager, "b")
            }
            activity_performance_achievement_write_related_people -> {
                intent.setClass(this@ProjectPerformanceWriteActivity, QuickMatchActivity::class.java)
                intent.putExtra(getString(R.string.NAME), getString(R.string.please_write_related_people))
                startActivityForResult(intent, 123)
            }
            activity_performance_achievement_write_party -> {
                intent.setClass(this@ProjectPerformanceWriteActivity, QuickMatchActivity::class.java)
                intent.putExtra(getString(R.string.NAME), getString(R.string.write_public_or_both_parties))
                startActivityForResult(intent, 124)
            }
            activity_performance_achievement_write_transaction_type -> {
                intent.setClass(this@ProjectPerformanceWriteActivity, QuickMatchActivity::class.java)
                intent.putExtra(getString(R.string.NAME), getString(R.string.write_transaction_type))
                startActivityForResult(intent, 125)
            }
            activity_performance_achievement_write_company_address -> {
                intent.setClass(this@ProjectPerformanceWriteActivity, LocationActivity::class.java)
                startActivityForResult(intent, 126)
            }
            activity_performance_achievement_write_project_desc -> {
                intent.setClass(this@ProjectPerformanceWriteActivity, WriteActivity::class.java)
                if (!TextUtils.isEmpty(projectDesc)) {
                    intent.putExtra(getString(R.string.DATA), projectDesc)
                }
                intent.putExtra(getString(R.string.MAX), 300)
                intent.putExtra(getString(R.string.TITLE), getString(R.string.project_desc))
                intent.putExtra(getString(R.string.NAME), getString(R.string.please_write_project_desc))
                startActivityForResult(intent, 127)
            }
            activity_performance_achievement_write_project_position -> {
                intent.setClass(this@ProjectPerformanceWriteActivity, WriteActivity::class.java)
                if (!TextUtils.isEmpty(projectPosition)) {
                    intent.putExtra(getString(R.string.DATA), projectPosition)
                }
                intent.putExtra(getString(R.string.MAX), 300)
                intent.putExtra(getString(R.string.TITLE), getString(R.string.project_position))
                intent.putExtra(getString(R.string.NAME), getString(R.string.please_write_project_position))
                startActivityForResult(intent, 128)
            }
            activity_performance_achievement_write_project_performance -> {
                val intent1 = Intent(this@ProjectPerformanceWriteActivity, WriteActivity::class.java)
                if (!TextUtils.isEmpty(projectPerformance)) {
                    intent1.putExtra(getString(R.string.DATA), projectPerformance)
                }
                intent1.putExtra(getString(R.string.MAX), 300)
                intent1.putExtra(getString(R.string.TITLE), getString(R.string.project_performance))
                intent1.putExtra(getString(R.string.NAME), getString(R.string.please_write_project_performance))
                startActivityForResult(intent1, 129)
            }
            activity_performance_achievement_write_delete -> {
                ViewHelper.showOneLineCard(this, getString(R.string.confirm_to_delete_the_experience), getString(R.string.alright), getString(R.string.cancel), { _, _ ->
                    showLoadingFragment(R.id.activity_performance_achievement_write_fl)
                    presenter!!.deleteUserProjectPerformance(id!!)
                }, { _, _ -> })
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == 1001) {
            when (requestCode) {
                120 -> {
                    activity_performance_achievement_write_project_name_tv.text = data!!.getStringExtra(getString(R.string.DATA))
                }
                121 -> {
                    activity_performance_achievement_write_company_name_tv.text = data!!.getStringExtra(getString(R.string.DATA))
                }
                122 -> {
                    activity_performance_achievement_write_position_name_tv.text = data!!.getStringExtra(getString(R.string.DATA))
                }
                123 -> {
                    val name = data!!.getStringExtra(getString(R.string.DATA))
                    relatedPeopleList.add(name)
                    initRelatedPeopleFluidLayout(initRelatedPeopleList())
                }
                124 -> {
                    val name = data!!.getStringExtra(getString(R.string.DATA))
                    partyList.add(name)
                    initPartyFluidLayout(initPartyList())
                }
                125 -> {
                    val name = data!!.getStringExtra(getString(R.string.DATA))
                    transactionTypeList.add(name)
                    initTranscationTypeFluidLayout(initTranscationTypeList())
                }
                126 -> {
                    activity_performance_achievement_write_company_address_tv.text = data!!.getStringExtra(getString(R.string.DATA))
                }
                127 -> {
                    projectDesc = data!!.getStringExtra(getString(R.string.DATA))
                    activity_performance_achievement_write_project_desc_tv.text = getString(R.string.mydetail_write_text1) + projectDesc.length + getString(R.string.mydetail_write_text2)
                }
                128 -> {
                    projectPosition = data!!.getStringExtra(getString(R.string.DATA))
                    activity_performance_achievement_write_project_position_tv.text = getString(R.string.mydetail_write_text1) + projectPosition.length + getString(R.string.mydetail_write_text2)
                }
                129 -> {
                    projectPerformance = data!!.getStringExtra(getString(R.string.DATA))
                    activity_performance_achievement_write_project_performance_tv.text = getString(R.string.mydetail_write_text1) + projectPerformance.length + getString(R.string.mydetail_write_text2)
                }
            }
        }
    }


    private fun initRelatedPeopleList(): ArrayList<FluidLayoutBean> {
        val list = ArrayList<FluidLayoutBean>()
        for (i in relatedPeopleList) {
            val b = FluidLayoutBean()
            b.index = i
            b.text = i
            list.add(b)
        }
        return list
    }

    private fun initRelatedPeopleFluidLayout(list: ArrayList<FluidLayoutBean>) {
        activity_performance_achievement_write_related_people_fl.removeAllViews()
        activity_performance_achievement_write_related_people_fl.setGravity(Gravity.TOP)
        for (i in list.indices) {

            val bean = list[i]

            val tv = TextView(this)
            tv.setPadding(25, 0, 25, 0)
            tv.text = bean.text
            tv.gravity = Gravity.CENTER
            tv.textSize = 14f
            tv.isClickable = true

            tv.setBackgroundResource(R.mipmap.bf_match_delete)
            tv.setTextColor(ContextCompat.getColor(this, R.color.color_2b3563))

            tv.setOnClickListener {
                var index = -1
                for (j in relatedPeopleList.indices) {
                    if (relatedPeopleList[j] == bean.text) {
                        index = j
                    }
                }
                relatedPeopleList.removeAt(index)
                initRelatedPeopleFluidLayout(initRelatedPeopleList())
            }

            val params = FluidLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            )
            params.setMargins(0, 20, 30, 20)
            activity_performance_achievement_write_related_people_fl.addView(tv, params)
        }
    }

    private fun initPartyList(): ArrayList<FluidLayoutBean> {
        val list = ArrayList<FluidLayoutBean>()
        for (i in partyList) {
            val b = FluidLayoutBean()
            b.index = i
            b.text = i
            list.add(b)
        }
        return list
    }

    private fun initPartyFluidLayout(list: ArrayList<FluidLayoutBean>) {
        activity_performance_achievement_write_party_fl.removeAllViews()
        activity_performance_achievement_write_party_fl.setGravity(Gravity.TOP)
        for (i in list.indices) {

            val bean = list[i]

            val tv = TextView(this)
            tv.setPadding(25, 0, 25, 0)
            tv.text = bean.text
            tv.gravity = Gravity.CENTER
            tv.textSize = 14f
            tv.isClickable = true

            tv.setBackgroundResource(R.mipmap.bf_match_delete)
            tv.setTextColor(ContextCompat.getColor(this, R.color.color_2b3563))

            tv.setOnClickListener {
                var index = -1
                for (j in partyList.indices) {
                    if (partyList[j] == bean.text) {
                        index = j
                    }
                }
                partyList.removeAt(index)
                initPartyFluidLayout(initPartyList())
            }

            val params = FluidLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            )
            params.setMargins(0, 20, 30, 20)
            activity_performance_achievement_write_party_fl.addView(tv, params)
        }
    }

    private fun initTranscationTypeList(): ArrayList<FluidLayoutBean> {
        val list = ArrayList<FluidLayoutBean>()
        for (i in transactionTypeList) {
            val b = FluidLayoutBean()
            b.index = i
            b.text = i
            list.add(b)
        }
        return list
    }

    private fun initTranscationTypeFluidLayout(list: ArrayList<FluidLayoutBean>) {
        activity_performance_achievement_write_transaction_type_fl.removeAllViews()
        activity_performance_achievement_write_transaction_type_fl.setGravity(Gravity.TOP)
        for (i in list.indices) {

            val bean = list[i]

            val tv = TextView(this)
            tv.setPadding(25, 0, 25, 0)
            tv.text = bean.text
            tv.gravity = Gravity.CENTER
            tv.textSize = 14f
            tv.isClickable = true

            tv.setBackgroundResource(R.mipmap.bf_match_delete)
            tv.setTextColor(ContextCompat.getColor(this, R.color.color_2b3563))

            tv.setOnClickListener {
                var index = -1
                for (j in transactionTypeList.indices) {
                    if (transactionTypeList[j] == bean.text) {
                        index = j
                    }
                }
                transactionTypeList.removeAt(index)
                initTranscationTypeFluidLayout(initTranscationTypeList())
            }

            val params = FluidLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            )
            params.setMargins(0, 20, 30, 20)
            activity_performance_achievement_write_transaction_type_fl.addView(tv, params)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (presenter != null) {
            presenter!!.destory()
            presenter = null
        }
    }

    //更新老的数据
    fun saveData() {
        val request = PutUserProjectPerformanceRequest()
        request.projectName = activity_performance_achievement_write_project_name_tv.text.toString().trim()
        request.companyName = activity_performance_achievement_write_company_name_tv.text.toString().trim()
        request.positionName = activity_performance_achievement_write_position_name_tv.text.toString().trim()
        request.startTime = startTime
        request.endTime = endTime
        request.relatedPersonnel = relatedPeopleList
        request.parties = partyList
        request.transactionType = transactionTypeList
        request.companyAddress = activity_performance_achievement_write_company_address_tv.text.toString().trim()
        request.projectDescription = projectDesc
        request.projectProfession = projectPosition
        request.projectAchievement = projectPerformance
        request.id = id
        presenter!!.putUserProjectPerformance(request)
    }

    //上传新的数据
    private fun updateData() {
        val request = PutUserProjectPerformanceRequest()
        request.projectName = activity_performance_achievement_write_project_name_tv.text.toString().trim()
        request.companyName = activity_performance_achievement_write_company_name_tv.text.toString().trim()
        request.positionName = activity_performance_achievement_write_position_name_tv.text.toString().trim()
        request.startTime = startTime
        request.endTime = endTime
        request.relatedPersonnel = relatedPeopleList
        request.parties = partyList
        request.transactionType = transactionTypeList
        request.companyAddress = activity_performance_achievement_write_company_address_tv.text.toString().trim()
        request.projectDescription = projectDesc
        request.projectProfession = projectPosition
        request.projectAchievement = projectPerformance
        presenter!!.postUserProjectPerformance(request)
    }

    override fun putUserProjectPerformanceSuccess(response: BooleanResponse) {
        removeLoadingFragment()
        if (response.data) {
            showToast(getString(R.string.file_save_success))
            finish()
        }
    }

    override fun putUserProjectPerformanceFail(throwable: Throwable) {
        removeLoadingFragment()
        showThrowable(throwable)
    }

    override fun getUserProjectPerformanceSuccess(response: GetUserProjectPerformanceResponse) {
        if (response.data != null) {
            if (!TextUtils.isEmpty(response.data!!.projectName)) {
                activity_performance_achievement_write_project_name_tv.text = response.data!!.projectName
            }
            if (!TextUtils.isEmpty(response.data!!.companyName)) {
                activity_performance_achievement_write_company_name_tv.text = response.data!!.companyName
            }
            if (!TextUtils.isEmpty(response.data!!.positionName)) {
                activity_performance_achievement_write_position_name_tv.text = response.data!!.positionName
            }
            startTime = response.data!!.startTime
            endTime = response.data!!.endTime
            activity_performance_achievement_write_start_time_tv.text = sdf.format(startTime)
            activity_performance_achievement_write_end_time_tv.text = sdf.format(endTime)

            if (response.data!!.relatedPersonnel != null) {
                relatedPeopleList = response.data!!.relatedPersonnel!!
                initRelatedPeopleFluidLayout(initRelatedPeopleList())
            }
            if (response.data!!.parties != null) {
                partyList = response.data!!.parties!!
                initPartyFluidLayout(initPartyList())
            }
            if (response.data!!.transactionType != null) {
                transactionTypeList = response.data!!.transactionType!!
                initTranscationTypeFluidLayout(initTranscationTypeList())
            }

            if (!TextUtils.isEmpty(response.data!!.companyAddress)) {
                activity_performance_achievement_write_company_address_tv.text = response.data!!.companyAddress
            }

            if (!TextUtils.isEmpty(response.data!!.projectDescription)) {
                projectDesc = response.data!!.projectDescription!!
                activity_performance_achievement_write_project_desc_tv.text = getString(R.string.mydetail_write_text1) + projectDesc.length + getString(R.string.mydetail_write_text2)
            }
            if (!TextUtils.isEmpty(response.data!!.projectProfession)) {
                projectPosition = response.data!!.projectProfession!!
                activity_performance_achievement_write_project_position_tv.text = getString(R.string.mydetail_write_text1) + projectPosition.length + getString(R.string.mydetail_write_text2)
            }
            if (!TextUtils.isEmpty(response.data!!.projectAchievement)) {
                projectPerformance = response.data!!.projectAchievement!!
                activity_performance_achievement_write_project_performance_tv.text = getString(R.string.mydetail_write_text1) + projectPerformance.length + getString(R.string.mydetail_write_text2)
            }
        }
    }

    override fun getUserProjectPerformanceFail(throwable: Throwable) {
        removeLoadingFragment()
        showThrowable(throwable)
    }

    override fun postUserProjectPerformanceSuccess(response: StringResponse) {
        removeLoadingFragment()
        if (!TextUtils.isEmpty(response.data)) {
            showToast(getString(R.string.file_save_success))
            finish()
        }
    }

    override fun postUserProjectPerformanceFail(throwable: Throwable) {
        removeLoadingFragment()
        showThrowable(throwable)
    }

    override fun deleteUserProjectPerformanceSuccess(response: BooleanResponse) {
        removeLoadingFragment()
        if (response.data) {
            finish()
        }
    }

    override fun deleteUserProjectPerformanceFail(throwable: Throwable) {
        removeLoadingFragment()
        showThrowable(throwable)
    }
}