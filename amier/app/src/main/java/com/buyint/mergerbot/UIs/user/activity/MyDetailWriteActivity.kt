package com.buyint.mergerbot.UIs.user.activity

import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.text.Editable
import android.text.SpannableStringBuilder
import android.text.TextUtils
import android.text.TextWatcher
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.afollestad.materialdialogs.MaterialDialog
import com.buyint.mergerbot.R
import com.buyint.mergerbot.UIs.match.activity.QuickMatchActivity
import com.buyint.mergerbot.UIs.match.baseAdapter.QuickMatchProjectIdNameAdapter
import com.buyint.mergerbot.UIs.setting.LocationActivity
import com.buyint.mergerbot.UIs.setting.WriteActivity
import com.buyint.mergerbot.base.BaseActivity
import com.buyint.mergerbot.database.getLoginResponse
import com.buyint.mergerbot.database.saveLoginResponse
import com.buyint.mergerbot.dto.*
import com.buyint.mergerbot.enums.*
import com.buyint.mergerbot.helper.HttpHelper
import com.buyint.mergerbot.interfaces.IUpdateAllUserInfo
import com.buyint.mergerbot.presenter.MyDetailWritePresenter
import com.buyint.mergerbot.view.FluidLayout
import com.buyint.mergerbot.dto.FluidLayoutBean
import kotlinx.android.synthetic.main.activity_my_detail_write.*
import kotlinx.android.synthetic.main.toolbar.*

/**
 * Created by huheming on 2018/6/28
 */
class MyDetailWriteActivity : BaseActivity(), View.OnClickListener, IUpdateAllUserInfo {

    private var desc = ""
    private var industryList = ArrayList<CodeNameBean>()

    override fun onClick(v: View?) {
        val intent = Intent(this, QuickMatchActivity::class.java)
        when (v) {
            activity_my_detail_write_industry -> {
                intent.putExtra(getString(R.string.TYPE), 11)
                startActivityForResult(intent, 24)
            }
            activity_my_detail_write_email -> {
                intent.putExtra(getString(R.string.NAME), getString(R.string.please_write_your_email))
                startActivityForResult(intent, 25)
            }
            activity_my_detail_write_web -> {
                intent.putExtra(getString(R.string.NAME), getString(R.string.please_write_your_web))
                startActivityForResult(intent, 26)
            }
            activity_my_detail_write_show1_ll -> {
                activity_my_detail_write_show1_iv.setImageDrawable(ContextCompat.getDrawable(this@MyDetailWriteActivity, R.drawable.ring_blue))
                activity_my_detail_write_show2_iv.setImageDrawable(ContextCompat.getDrawable(this@MyDetailWriteActivity, R.drawable.ring_gray))
                activity_my_detail_write_show3_iv.setImageDrawable(ContextCompat.getDrawable(this@MyDetailWriteActivity, R.drawable.ring_gray))
                showNameType = ShowNameType.REALNAME.name
            }
            activity_my_detail_write_show2_ll -> {
                activity_my_detail_write_show2_iv.setImageDrawable(ContextCompat.getDrawable(this@MyDetailWriteActivity, R.drawable.ring_blue))
                activity_my_detail_write_show1_iv.setImageDrawable(ContextCompat.getDrawable(this@MyDetailWriteActivity, R.drawable.ring_gray))
                activity_my_detail_write_show3_iv.setImageDrawable(ContextCompat.getDrawable(this@MyDetailWriteActivity, R.drawable.ring_gray))
                showNameType = ShowNameType.NICKNAME.name
            }
            activity_my_detail_write_show3_ll -> {
                activity_my_detail_write_show3_iv.setImageDrawable(ContextCompat.getDrawable(this@MyDetailWriteActivity, R.drawable.ring_blue))
                activity_my_detail_write_show2_iv.setImageDrawable(ContextCompat.getDrawable(this@MyDetailWriteActivity, R.drawable.ring_gray))
                activity_my_detail_write_show1_iv.setImageDrawable(ContextCompat.getDrawable(this@MyDetailWriteActivity, R.drawable.ring_gray))
                showNameType = ShowNameType.NAMDE.name
            }
            activity_my_detail_write_work_place -> {
                val intent2 = Intent(this@MyDetailWriteActivity, LocationActivity::class.java)
                startActivityForResult(intent2, 28)
            }
            activity_my_detail_write_introduction -> {
                val intent1 = Intent(this@MyDetailWriteActivity, WriteActivity::class.java)
                if (!TextUtils.isEmpty(desc)) {
                    intent1.putExtra(getString(R.string.DATA), desc)
                }
                intent1.putExtra(getString(R.string.MAX), 300)
                intent1.putExtra(getString(R.string.TITLE), getString(R.string.please_write_desc))
                intent1.putExtra(getString(R.string.NAME), getString(R.string.please_write_your_introduction))
                startActivityForResult(intent1, 29)
            }
        }
    }

    private var presenter: MyDetailWritePresenter? = null
    private var showNameType = ShowNameType.REALNAME.name

    override fun onCreate(savedInstanceState: Bundle?) {

        setMyTitleColor()
        presenter = MyDetailWritePresenter(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_detail_write)

        toolbar_back.setOnClickListener { onBackPressed() }
        toorbar_title.text = getString(R.string.personal_overview)
        val drawable = resources.getDrawable(R.mipmap.duigou)
        drawable.setBounds(0, 0, dp2px(16f), dp2px(16f))
        toolbar_right.setCompoundDrawables(null, null, drawable, null)
        toolbar_right_rl.setOnClickListener { saveData() }

        activity_my_detail_write_industry.setOnClickListener(this)
        activity_my_detail_write_email.setOnClickListener(this)
        activity_my_detail_write_web.setOnClickListener(this)
        activity_my_detail_write_show1_ll.setOnClickListener(this)
        activity_my_detail_write_show2_ll.setOnClickListener(this)
        activity_my_detail_write_show3_ll.setOnClickListener(this)
        activity_my_detail_write_work_place.setOnClickListener(this)
        activity_my_detail_write_introduction.setOnClickListener(this)

        initEditText()
        initData()
    }

    private fun initEditText() {
        val companyAdapter = QuickMatchProjectIdNameAdapter(this, ArrayList(), QuickMatchProjectIdNameAdapter.QuickMatchProjectIdNameAdapterListener {
            activity_my_detail_write_company_tv.text = SpannableStringBuilder(it.projectName)
            activity_my_detail_write_company_tv.setSelection(it.projectName!!.length)
            activity_my_detail_write_company_tv.dismissDropDown()
        })
        activity_my_detail_write_company_tv.setAdapter(companyAdapter)
        activity_my_detail_write_company_tv.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!TextUtils.isEmpty(s)) {
                    HttpHelper.getCompanyList(s.toString()).subscribe({
                        if (it != null) {
                            companyAdapter.setList(it)
                        }
                    }, {
                        showThrowable(it)
                    })
                }
            }
        })
    }

    private fun saveData() {
        val request = UpdateUserAllInfoRequest()
        request.showNameType = showNameType
        request.description = desc
        request.userName = activity_my_detail_write_name_tv.text.toString().trim()
        request.englishName = activity_my_detail_write_english_name_tv.text.toString().trim()
        request.userWorkMessage = UserNikeAndIntentionDto()
        request.userWorkMessage!!.companyName = activity_my_detail_write_company_tv.text.toString().trim()
        request.userWorkMessage!!.companyWebSite = activity_my_detail_write_web_tv.text.toString().trim()
        request.userWorkMessage!!.businessDivision = activity_my_detail_write_division_of_labor_tv.text.toString().trim()
        request.userWorkMessage!!.position = activity_my_detail_write_position_tv.text.toString().trim()
        request.userWorkMessage!!.workEmail = activity_my_detail_write_email_tv.text.toString().trim()
        request.userWorkMessage!!.workAddress = activity_my_detail_write_work_place_tv.text.toString().trim()
        if (industryList.size > 0) {
            request.registerIndustryClassification = industryList
        }

        presenter!!.updateAllUserInfo(request)
    }

    private fun initData() {
        val response = getLoginResponse(this)

        if (response!!.model.englishName != null) {
            activity_my_detail_write_english_name_tv.text = SpannableStringBuilder(response.model.englishName)
        }
        if (response.model.userPrivacySetting == null) {
            activity_my_detail_write_show1_iv.setImageDrawable(ContextCompat.getDrawable(this@MyDetailWriteActivity, R.drawable.ring_blue))
            activity_my_detail_write_show2_iv.setImageDrawable(ContextCompat.getDrawable(this@MyDetailWriteActivity, R.drawable.ring_gray))
            activity_my_detail_write_show3_iv.setImageDrawable(ContextCompat.getDrawable(this@MyDetailWriteActivity, R.drawable.ring_gray))
            showNameType = ShowNameType.REALNAME.name
        } else {
            when (response.model.userPrivacySetting.showNameType) {
                ShowNameType.REALNAME.name -> {
                    activity_my_detail_write_show1_iv.setImageDrawable(ContextCompat.getDrawable(this@MyDetailWriteActivity, R.drawable.ring_blue))
                    activity_my_detail_write_show2_iv.setImageDrawable(ContextCompat.getDrawable(this@MyDetailWriteActivity, R.drawable.ring_gray))
                    activity_my_detail_write_show3_iv.setImageDrawable(ContextCompat.getDrawable(this@MyDetailWriteActivity, R.drawable.ring_gray))
                    showNameType = ShowNameType.REALNAME.name
                }
                ShowNameType.NICKNAME.name -> {
                    activity_my_detail_write_show2_iv.setImageDrawable(ContextCompat.getDrawable(this@MyDetailWriteActivity, R.drawable.ring_blue))
                    activity_my_detail_write_show1_iv.setImageDrawable(ContextCompat.getDrawable(this@MyDetailWriteActivity, R.drawable.ring_gray))
                    activity_my_detail_write_show3_iv.setImageDrawable(ContextCompat.getDrawable(this@MyDetailWriteActivity, R.drawable.ring_gray))
                    showNameType = ShowNameType.NICKNAME.name
                }
                ShowNameType.NAMDE.name -> {
                    activity_my_detail_write_show3_iv.setImageDrawable(ContextCompat.getDrawable(this@MyDetailWriteActivity, R.drawable.ring_blue))
                    activity_my_detail_write_show2_iv.setImageDrawable(ContextCompat.getDrawable(this@MyDetailWriteActivity, R.drawable.ring_gray))
                    activity_my_detail_write_show1_iv.setImageDrawable(ContextCompat.getDrawable(this@MyDetailWriteActivity, R.drawable.ring_gray))
                    showNameType = ShowNameType.NAMDE.name
                }
            }
        }
        if (!TextUtils.isEmpty(response.model.userName)) {
            activity_my_detail_write_name_tv.text = SpannableStringBuilder(response.model.userName)
        }
        if (response.model.userWorkMessage != null) {
            if (!TextUtils.isEmpty(response.model.userWorkMessage.companyName)) {
                activity_my_detail_write_company_tv.text = SpannableStringBuilder(response.model.userWorkMessage.companyName)
            }
            if (!TextUtils.isEmpty(response.model.userWorkMessage.position)) {
                activity_my_detail_write_position_tv.text = SpannableStringBuilder(response.model.userWorkMessage.position)
            }
            if (!TextUtils.isEmpty(response.model.userWorkMessage.workEmail)) {
                activity_my_detail_write_email_tv.text = response.model.userWorkMessage.workEmail
            }
            if (!TextUtils.isEmpty(response.model.userWorkMessage.companyWebSite)) {
                activity_my_detail_write_web_tv.text = response.model.userWorkMessage.companyWebSite
            }
            if (!TextUtils.isEmpty(response.model.userWorkMessage.businessDivision)) {
                activity_my_detail_write_division_of_labor_tv.text = SpannableStringBuilder(response.model.userWorkMessage.businessDivision)
            }
            if (!TextUtils.isEmpty(response.model.userWorkMessage.workAddress)) {
                activity_my_detail_write_work_place_tv.text = response.model.userWorkMessage.workAddress
            }

            if (response.model.registerIndustryClassification != null && response.model.registerIndustryClassification.size > 0) {
                industryList = response.model.registerIndustryClassification
                initFluidLayout(initList())
            }

            if (!TextUtils.isEmpty(response.model.description)) {
                desc = response.model.description
                activity_my_detail_write_introduction_tv.text = getString(R.string.mydetail_write_text1) + desc.length + getString(R.string.mydetail_write_text2)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 20 && resultCode == 1001) {
            val stringExtra = data!!.getStringExtra(getString(R.string.DATA))
            activity_my_detail_write_name_tv.text = SpannableStringBuilder(stringExtra)
        } else if (requestCode == 24 && resultCode == 1001) {
            val hangyeBean = data!!.getSerializableExtra(getString(R.string.IndustryBean)) as CodeTitleBean
            val codeNameBean = CodeNameBean()
            codeNameBean.code = hangyeBean.code
            codeNameBean.name = hangyeBean.name[0]
            industryList.add(codeNameBean)
            initFluidLayout(initList())
        } else if (requestCode == 25 && resultCode == 1001) {
            val stringExtra = data!!.getStringExtra(getString(R.string.DATA))
            activity_my_detail_write_email_tv.text = stringExtra
        } else if (requestCode == 26 && resultCode == 1001) {
            val stringExtra = data!!.getStringExtra(getString(R.string.DATA))
            activity_my_detail_write_web_tv.text = stringExtra
        } else if (requestCode == 27 && resultCode == 1001) {
            val stringExtra = data!!.getStringExtra(getString(R.string.DATA))
            activity_my_detail_write_english_name_tv.text = SpannableStringBuilder(stringExtra)
        } else if (requestCode == 28 && resultCode == 1001) {
            val stringExtra = data!!.getStringExtra(getString(R.string.DATA))
            activity_my_detail_write_work_place_tv.text = stringExtra
        } else if (requestCode == 29 && resultCode == 1001) {
            val stringExtra = data!!.getStringExtra(getString(R.string.DATA))
            activity_my_detail_write_introduction_tv.text = getString(R.string.mydetail_write_text1) + stringExtra.length + getString(R.string.mydetail_write_text2)
            desc = stringExtra
        }
    }

    override fun onBackPressed() {
        val builder = MaterialDialog.Builder(this)
        builder.positiveText(R.string.alright)
        builder.negativeText(R.string.cancel)
        builder.positiveColorRes(R.color.colorBlack)
        builder.negativeColorRes(R.color.colorBlack)
        builder.content(R.string.sure_to_quit_write)
        builder.onNegative { dialog, _ ->
            dialog.dismiss()
        }
        builder.onPositive { dialog, _ ->
            dialog.dismiss()
            super.onBackPressed()
        }
        builder.build().show()
    }

    override fun updateAllUserInfoSuccess(response: BooleanResponse, request: UpdateUserAllInfoRequest) {
        if (response.data) {

            val response = getLoginResponse(this)
            response!!.model.registerIndustryClassification = request.registerIndustryClassification
            response.model.userName = request.userName
            response.model.description = request.description
            response.model.englishName = request.englishName
            response.model.userWorkMessage = request.userWorkMessage
            response.model.userMessageLevel = "3"
            if (response.model.userPrivacySetting == null) {
                response.model.userPrivacySetting = UpdatePrivacySettingModel()
                response.model.userPrivacySetting.showNameType = request.showNameType
                response.model.userPrivacySetting.commercialRecord = ArrayList()
                response.model.userPrivacySetting.shielding = ArrayList()
                response.model.userPrivacySetting.contactTime = ContactTime.ANYTIME.name
                response.model.userPrivacySetting.contactWay = ContactWay.AIMER.name
                response.model.userPrivacySetting.viewCommercialPermission = ViewCommercialPermission.HIDDEN.name
                response.model.userPrivacySetting.commercialRecord.add(ShowPart.RESUME.name)
                response.model.userPrivacySetting.commercialRecord.add(ShowPart.LINGUISTIC_COMPETENCE.name)
                response.model.userPrivacySetting.commercialRecord.add(ShowPart.INDUSTRY.name)
                response.model.userPrivacySetting.commercialRecord.add(ShowPart.ACHIEVEMENTS.name)
                response.model.userPrivacySetting.commercialRecord.add(ShowPart.TRANSACTION.name)
                response.model.userPrivacySetting.commercialRecord.add(ShowPart.COMMERCIAL_ACTIVITY.name)
                response.model.userPrivacySetting.commercialRecord.add(ShowPart.EDUCATION_EXPERIENCE.name)
                response.model.userPrivacySetting.commercialRecord.add(ShowPart.OVERHEAD_INFORMATION.name)
            } else {
                response.model.userPrivacySetting.showNameType = request.showNameType
            }
            saveLoginResponse(this, response)

            setResult(10086)
            finish()
        } else {
            showToast(getString(R.string.please_check_internet))
            removeLoadingFragment()
        }
    }

    override fun updateAllUserInfoFail(throwable: Throwable) {
        removeLoadingFragment()
        showThrowable(throwable)
    }


    private fun initList(): java.util.ArrayList<FluidLayoutBean> {
        val list = java.util.ArrayList<FluidLayoutBean>()
        for (i in industryList) {
            val b = FluidLayoutBean()
            b.index = i.code
            b.text = i.name
            list.add(b)
        }
        return list
    }

    private fun initFluidLayout(list: java.util.ArrayList<FluidLayoutBean>) {
        activity_my_detail_write_industry_fl.removeAllViews()
        activity_my_detail_write_industry_fl.setGravity(Gravity.TOP)
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
                for (j in industryList.indices) {
                    if (industryList[j].code == bean.index) {
                        index = j
                    }
                }
                industryList.removeAt(index)
                initFluidLayout(initList())
            }

            val params = FluidLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            )
            params.setMargins(0, 20, 30, 20)
            activity_my_detail_write_industry_fl.addView(tv, params)
        }
    }
}