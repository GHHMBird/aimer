package com.buyint.mergerbot.UIs.user.activity

import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.Gravity
import android.view.ViewGroup
import android.widget.TextView
import com.buyint.mergerbot.R
import com.buyint.mergerbot.UIs.setting.WriteActivity
import com.buyint.mergerbot.UIs.user.adapter.ExtraInfomationAdapter
import com.buyint.mergerbot.base.BaseActivity
import com.buyint.mergerbot.dto.CodeNameBean
import com.buyint.mergerbot.dto.GetAttachMessageResponse
import com.buyint.mergerbot.interfaces.IGetAttachMessage
import com.buyint.mergerbot.presenter.ExtraInformationPresenter
import com.buyint.mergerbot.view.FluidLayout
import com.buyint.mergerbot.dto.FluidLayoutBean
import kotlinx.android.synthetic.main.activity_extra_information.*
import kotlinx.android.synthetic.main.toolbar.*

/**
 * Created by huheming on 2018/7/18
 */
class ExtraInformationActivity : BaseActivity(), IGetAttachMessage {

    private var languageList: ArrayList<String> = arrayListOf()
    private var otherLanguageList: ArrayList<String> = arrayListOf()

    private var focusList: ArrayList<CodeNameBean> = arrayListOf()

    private var presenter: ExtraInformationPresenter? = null
    private var adapter: ExtraInfomationAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        presenter = ExtraInformationPresenter(this)

        setMyTitleColor()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_extra_information)

        toolbar_back.setOnClickListener { onBackPressed() }
        toorbar_title.text = getString(R.string.extra_information)

        activity_extra_information_language.setOnClickListener {
            val intent = Intent(this@ExtraInformationActivity, LanguageSkillActivity::class.java)
            intent.putExtra(getString(R.string.data1), languageList)
            intent.putExtra(getString(R.string.data2), otherLanguageList)
            startActivityForResult(intent, 10011)
        }

        activity_extra_information_focus_area.setOnClickListener {
            val intent = Intent(this@ExtraInformationActivity, FocusAreaActivity::class.java)
            intent.putExtra(getString(R.string.DATA), focusList)
            startActivityForResult(intent, 10013)
        }

        activity_extra_information_add_education.setOnClickListener {
            val intent = Intent(this@ExtraInformationActivity, EducationExperienceWriteActivity::class.java)
            startActivityForResult(intent, 10020)
        }

        activity_extra_information_extra_information.setOnClickListener {
            intent.setClass(this@ExtraInformationActivity, WriteActivity::class.java)
            if (!TextUtils.isEmpty(activity_extra_information_extra_information_tv.text.toString().trim())) {
                intent.putExtra(getString(R.string.DATA), activity_extra_information_extra_information_tv.text.toString().trim())
            }
            intent.putExtra(getString(R.string.result), getString(R.string.TYPE_1))
            intent.putExtra(getString(R.string.MAX), 300)
            intent.putExtra(getString(R.string.TITLE), getString(R.string.extra_information))
            intent.putExtra(getString(R.string.NAME), getString(R.string.please_write_your_patents_or_publication))
            startActivityForResult(intent, 10085)
        }

        activity_extra_information_recycler.layoutManager = LinearLayoutManager(this)
        activity_extra_information_recycler.isNestedScrollingEnabled = false
        adapter = ExtraInfomationAdapter(this, arrayListOf(), true)
        activity_extra_information_recycler.adapter = adapter
    }

    override fun onStart() {
        super.onStart()
        showLoadingFragment(R.id.activity_extra_information_fl)
        presenter!!.getAttachMessage()
    }

    override fun getAttachMessageSuccess(response: GetAttachMessageResponse) {
        removeLoadingFragment()
        if (response.data != null) {
            if (response.data!!.languageCompetence != null) {
                languageList = response.data!!.languageCompetence!!
            }
            if (response.data!!.otherLanguageCompetence != null) {
                otherLanguageList = response.data!!.otherLanguageCompetence!!
            }
            initLanguageFluidLayout(initLanguageList())
            if (response.data!!.industryClassification != null) {
                focusList = response.data!!.industryClassification!!
            }
            initFocusFluidLayout(initFocusList())

            if (!TextUtils.isEmpty(response.data!!.attachMessage)) {
                activity_extra_information_extra_information_tv.text = response.data!!.attachMessage
            }

            if (response.data!!.educationExperience != null) {
                adapter!!.setLists(response.data!!.educationExperience!!)
            }
        }
    }

    override fun getAttachMessageFail(throwable: Throwable) {
        removeLoadingFragment()
        showThrowable(throwable)
        initLanguageFluidLayout(initLanguageList())
        initFocusFluidLayout(initFocusList())
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 10011 && resultCode == 10012) {
            languageList = data!!.getStringArrayListExtra(getString(R.string.data1))
            otherLanguageList = data.getStringArrayListExtra(getString(R.string.data2))
            initLanguageFluidLayout(initLanguageList())
        }
        if (requestCode == 10013 && resultCode == 10014) {
            focusList = data!!.getSerializableExtra(getString(R.string.DATA)) as ArrayList<CodeNameBean>
            initFocusFluidLayout(initFocusList())
        }
        if (requestCode == 10085 && resultCode == 1001) {
            activity_extra_information_extra_information_tv.text = data!!.getStringExtra(getString(R.string.DATA))
        }
    }

    private fun initLanguageList(): ArrayList<FluidLayoutBean> {
        val list = ArrayList<FluidLayoutBean>()
        for (i in this.languageList) {
            val b = FluidLayoutBean()
            b.index = i
            b.text = i
            list.add(b)
        }
        for (i in this.otherLanguageList) {
            val b = FluidLayoutBean()
            b.index = i
            b.text = i
            list.add(b)
        }
        return list
    }

    private fun initLanguageFluidLayout(list: ArrayList<FluidLayoutBean>) {
        activity_extra_information_language_fl.removeAllViews()
        activity_extra_information_language_fl.setGravity(Gravity.TOP)
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
            activity_extra_information_language_fl.addView(tv, params)
        }
    }

    private fun initFocusList(): ArrayList<FluidLayoutBean> {
        val list = ArrayList<FluidLayoutBean>()
        for (i in this.focusList) {
            val b = FluidLayoutBean()
            b.index = i.code
            b.text = i.name
            list.add(b)
        }
        return list
    }

    private fun initFocusFluidLayout(list: ArrayList<FluidLayoutBean>) {
        activity_extra_information_focus_area_fl.removeAllViews()
        activity_extra_information_focus_area_fl.setGravity(Gravity.TOP)
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
            activity_extra_information_focus_area_fl.addView(tv, params)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (presenter != null) {
            presenter!!.destory()
            presenter = null
        }
    }
}