package com.buyint.mergerbot.UIs.user.activity

import android.graphics.Bitmap
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.BitmapImageViewTarget
import com.buyint.mergerbot.R
import com.buyint.mergerbot.UIs.user.adapter.BusinessActivityAdapter
import com.buyint.mergerbot.UIs.user.adapter.ExtraInfomationAdapter
import com.buyint.mergerbot.UIs.user.adapter.ProjectPerformanceAdapter
import com.buyint.mergerbot.base.BaseActivity
import com.buyint.mergerbot.database.getLoginResponse
import com.buyint.mergerbot.dto.*
import com.buyint.mergerbot.enums.ShowNameType
import com.buyint.mergerbot.interfaces.IGetAttachMessage
import com.buyint.mergerbot.interfaces.IGetUserBusinessActivityList
import com.buyint.mergerbot.interfaces.IGetUserProjectPerformanceList
import com.buyint.mergerbot.presenter.BusinessActivityPresenter
import com.buyint.mergerbot.presenter.ExtraInformationPresenter
import com.buyint.mergerbot.presenter.ProjectPerformancePresenter
import com.buyint.mergerbot.view.FluidLayout
import com.buyint.mergerbot.dto.FluidLayoutBean
import kotlinx.android.synthetic.main.activity_business_file.*
import kotlinx.android.synthetic.main.toolbar.*

/**
 * Created by huheming on 2018/7/10
 */
class BusinessFileActivity : BaseActivity(), IGetUserBusinessActivityList, IGetUserProjectPerformanceList, IGetAttachMessage {

    private var businessActivityPresenter: BusinessActivityPresenter? = null
    private var projectPerformancePresenter: ProjectPerformancePresenter? = null
    private var extraInformationPresenter: ExtraInformationPresenter? = null
    private var businessActivityHttp = false
    private var projectPerformanceHttp = false
    private var extraInformationHttp = false

    override fun onCreate(savedInstanceState: Bundle?) {

        businessActivityPresenter = BusinessActivityPresenter(this)
        projectPerformancePresenter = ProjectPerformancePresenter(this)
        extraInformationPresenter = ExtraInformationPresenter(this)

        setMyTitleColor()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_business_file)

        toorbar_title.text = getString(R.string.my_business_file)
        toolbar_back.setOnClickListener { onBackPressed() }

        initHttpData()
        initData()
    }

    private fun initHttpData() {
        showLoadingFragment(R.id.activity_business_file_fl)
        val request = GetUserProjectPerformanceListRequest()
        request.pageNum = 1
        request.pageSize = 100
        request.total = 100
        projectPerformancePresenter!!.getUserProjectPerformanceList(request)
        businessActivityPresenter!!.getUserBusinessActivityList(request)
        extraInformationPresenter!!.getAttachMessage()
    }

    private fun initData() {
        val response = getLoginResponse(this)
        Glide.with(this).load(response!!.model.avatars).asBitmap().centerCrop().error(R.mipmap.default_user).placeholder(R.mipmap.default_user).into(object : BitmapImageViewTarget(activity_business_file_user_icon) {
            override fun setResource(resource: Bitmap?) {
                val rbd = RoundedBitmapDrawableFactory.create(resources, resource)
                rbd.isCircular = true
                activity_business_file_user_icon.setImageDrawable(rbd)
            }
        })

        if (response.model.userPrivacySetting == null) {
            if (TextUtils.isEmpty(response.model.englishName)) {
                activity_business_file_name.text = getString(R.string.unknown_user)
            } else {
                activity_business_file_name.text = response.model.englishName
            }
        } else {
            when (response.model.userPrivacySetting.showNameType) {
                ShowNameType.REALNAME.name -> {
                    if (TextUtils.isEmpty(response.model.userName)) {
                        activity_business_file_name.text = getString(R.string.unknown_user)
                    } else {
                        activity_business_file_name.text = response.model.userName
                    }
                }
                ShowNameType.NICKNAME.name -> {
                    if (TextUtils.isEmpty(response.model.englishName)) {
                        activity_business_file_name.text = getString(R.string.unknown_user)
                    } else {
                        activity_business_file_name.text = response.model.englishName
                    }
                }
                ShowNameType.NAMDE.name -> {
                    if (TextUtils.isEmpty(response.model.userName)) {
                        activity_business_file_name.text = getString(R.string.unknown_user)
                    } else {
                        activity_business_file_name.text = getString(R.string.sir_or_miss_2_start) + response.model.userName.substring(0, 1) + getString(R.string.sir_or_miss_2_end)
                    }
                }
            }
        }

        if (response.model.userWorkMessage == null) {
            activity_business_file_company.text = getString(R.string.undisclosed_company) + "|" + getString(R.string.undisclosed_position)
        } else {
            var companyName = response.model.userWorkMessage.companyName
            if (TextUtils.isEmpty(companyName)) {
                companyName = getString(R.string.undisclosed_company)
            }
            var position = response.model.userWorkMessage.position
            if (TextUtils.isEmpty(position)) {
                position = getString(R.string.undisclosed_position)
            }
            activity_business_file_company.text = companyName + "|" + position
        }

        activity_business_file_commercial_reputation_code.text = "100"

        activity_business_file_exceed.text = "95%"

        if (TextUtils.isEmpty(response.model.description)) {
            activity_business_file_introduction.text = getString(R.string.guess)
        } else {
            activity_business_file_introduction.text = response.model.description
        }
    }

    override fun getAttachMessageSuccess(response: GetAttachMessageResponse) {
        extraInformationHttp = true
        if (businessActivityHttp && projectPerformanceHttp && extraInformationHttp) {
            removeLoadingFragment()
        }
        if (response.data != null) {
            if (response.data!!.languageCompetence != null) {
                initLanguageFluidLayout(initLanguageList(response.data!!.languageCompetence!!))
            } else {
                activity_business_file_language_skill_ll.visibility = View.GONE
            }
            if (response.data!!.industryClassification != null) {
                initFocusFluidLayout(initFocusList(response.data!!.industryClassification!!))
            } else {
                activity_business_file_focus_area_ll.visibility = View.GONE
            }

            if (!TextUtils.isEmpty(response.data!!.attachMessage)) {
                activity_business_file_extra_information_tv.text = response.data!!.attachMessage
            }

            if (response.data!!.educationExperience != null) {
                activity_business_file_education_experience_recycler.layoutManager = LinearLayoutManager(this)
                activity_business_file_education_experience_recycler.isNestedScrollingEnabled = false
                val adapter = ExtraInfomationAdapter(this, response.data!!.educationExperience!!,false)
                activity_business_file_education_experience_recycler.adapter = adapter
            } else {
                activity_business_file_education_experience_tv.visibility = View.GONE
                activity_business_file_education_experience_recycler.visibility = View.GONE
            }
        }
    }

    override fun getAttachMessageFail(throwable: Throwable) {
        extraInformationHttp = true
        if (businessActivityHttp && projectPerformanceHttp && extraInformationHttp) {
            removeLoadingFragment()
        }
        showThrowable(throwable)
    }

    override fun getUserBusinessActivityListSuccess(response: GetUserBusinessActivityListResponse) {
        businessActivityHttp = true
        if (businessActivityHttp && projectPerformanceHttp && extraInformationHttp) {
            removeLoadingFragment()
        }
        if (response.data == null || response.data!!.size == 0) {
            activity_business_file_business_activity_ll.visibility = View.GONE
        } else {
            activity_business_file_business_activity_recyclerview.layoutManager = LinearLayoutManager(this)
            activity_business_file_business_activity_recyclerview.isNestedScrollingEnabled = false
            activity_business_file_business_activity_recyclerview.adapter = BusinessActivityAdapter(this, response.data, false)
        }
    }

    override fun getUserBusinessActivityListFail(throwable: Throwable) {
        businessActivityHttp = true
        if (businessActivityHttp && projectPerformanceHttp && extraInformationHttp) {
            removeLoadingFragment()
        }
        showThrowable(throwable)
    }

    override fun getUserProjectPerformanceListSuccess(response: GetUserProjectPerformanceListResponse) {
        projectPerformanceHttp = true
        if (businessActivityHttp && projectPerformanceHttp && extraInformationHttp) {
            removeLoadingFragment()
        }
        if (response.data == null || response.data!!.size == 0) {
            activity_business_file_project_performance_ll.visibility = View.GONE
        } else {
            activity_business_file_project_performance_recyclerview.layoutManager = LinearLayoutManager(this)
            activity_business_file_project_performance_recyclerview.isNestedScrollingEnabled = false
            activity_business_file_project_performance_recyclerview.adapter = ProjectPerformanceAdapter(this, response.data!!, false)
        }
    }

    override fun getUserProjectPerformanceListFail(throwable: Throwable) {
        projectPerformanceHttp = true
        if (businessActivityHttp && projectPerformanceHttp && extraInformationHttp) {
            removeLoadingFragment()
        }
        showThrowable(throwable)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (businessActivityPresenter != null) {
            businessActivityPresenter!!.destory()
            businessActivityPresenter = null
        }
        if (projectPerformancePresenter != null) {
            projectPerformancePresenter!!.destory()
            projectPerformancePresenter = null
        }
        if (extraInformationPresenter != null) {
            extraInformationPresenter!!.destory()
            extraInformationPresenter = null
        }
    }


    private fun initLanguageList(lists: ArrayList<String>): ArrayList<FluidLayoutBean> {
        val list = ArrayList<FluidLayoutBean>()
        for (i in lists) {
            val b = FluidLayoutBean()
            b.index = i
            b.text = i
            list.add(b)
        }
        return list
    }

    private fun initLanguageFluidLayout(list: ArrayList<FluidLayoutBean>) {
        activity_business_file_language_skill_fl.removeAllViews()
        activity_business_file_language_skill_fl.setGravity(Gravity.TOP)
        for (i in list.indices) {

            val bean = list[i]

            val tv = TextView(this)
            tv.setPadding(25, 0, 25, 0)
            tv.text = bean.text
            tv.gravity = Gravity.CENTER
            tv.textSize = 14f

            tv.setTextColor(ContextCompat.getColor(this, R.color.color_999999))

            val params = FluidLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            )
            params.setMargins(0, 20, 30, 20)
            activity_business_file_language_skill_fl.addView(tv, params)
        }
    }

    private fun initFocusList(lists: ArrayList<CodeNameBean>): ArrayList<FluidLayoutBean> {
        val list = ArrayList<FluidLayoutBean>()
        for (i in lists) {
            val b = FluidLayoutBean()
            b.index = i.code
            b.text = i.name
            list.add(b)
        }
        return list
    }

    private fun initFocusFluidLayout(list: ArrayList<FluidLayoutBean>) {
        activity_business_file_focus_area_fl.removeAllViews()
        activity_business_file_focus_area_fl.setGravity(Gravity.TOP)
        for (i in list.indices) {

            val bean = list[i]

            val tv = TextView(this)
            tv.setPadding(25, 0, 25, 0)
            tv.text = bean.text
            tv.gravity = Gravity.CENTER
            tv.textSize = 14f

            tv.setTextColor(ContextCompat.getColor(this, R.color.color_999999))

            val params = FluidLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            )
            params.setMargins(0, 20, 30, 20)
            activity_business_file_focus_area_fl.addView(tv, params)
        }
    }

}