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
import com.buyint.mergerbot.UIs.setting.WriteActivity
import com.buyint.mergerbot.Utility.ViewHelper
import com.buyint.mergerbot.base.BaseActivity
import com.buyint.mergerbot.dto.AddEducationExperienceRequest
import com.buyint.mergerbot.dto.BooleanResponse
import com.buyint.mergerbot.dto.StringResponse
import com.buyint.mergerbot.interfaces.IAddEducationExperience
import com.buyint.mergerbot.interfaces.IDeleteEducationExperience
import com.buyint.mergerbot.interfaces.IPutEducationExperience
import com.buyint.mergerbot.presenter.EducationExperienceWritePresenter
import com.buyint.mergerbot.view.FluidLayout
import com.buyint.mergerbot.dto.FluidLayoutBean
import com.buyint.mergerbot.view.pickerview.TimePickerDialog
import com.buyint.mergerbot.view.pickerview.data.Type
import kotlinx.android.synthetic.main.activity_education_experience.*
import kotlinx.android.synthetic.main.toolbar.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by huheming on 2018/7/19
 */
class EducationExperienceWriteActivity : BaseActivity(), View.OnClickListener, IAddEducationExperience, IPutEducationExperience, IDeleteEducationExperience {

    private val sdf = SimpleDateFormat("yyyy年MM月")
    private var professionList = arrayListOf<String>()
    private var isTongZhao = true
    private var academicAchievement: String? = null
    private var startTime: Long? = null
    private var endTime: Long? = null
    private var model: AddEducationExperienceRequest? = null

    private var presenter: EducationExperienceWritePresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        presenter = EducationExperienceWritePresenter(this, this, this)

        setMyTitleColor()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_education_experience)

        toorbar_title.text = getString(R.string.educational_experience)
        toolbar_back.setOnClickListener { onBackPressed() }
        val drawable = resources.getDrawable(R.mipmap.duigou)
        drawable.setBounds(0, 0, dp2px(16f), dp2px(16f))
        toolbar_right.setCompoundDrawables(null, null, drawable, null)
        toolbar_right_rl.setOnClickListener {
            showLoadingFragment(R.id.activity_education_experience_fl)
            if (model != null) {//更新信息
                putData()
            } else {//新增信息
                addData()
            }
        }

        model = intent.getSerializableExtra(getString(R.string.DATA)) as AddEducationExperienceRequest?
        if (model != null) {
            activity_education_experience_delete.visibility = View.VISIBLE
            if (!TextUtils.isEmpty(model!!.schoolName)) {
                activity_education_experience_school_name_tv.text = model!!.schoolName
            }
            activity_education_experience_start_time_tv.text = sdf.format(model!!.startTime)
            activity_education_experience_end_time_tv.text = sdf.format(model!!.endTime)
            startTime = model!!.startTime
            endTime = model!!.endTime
            if (model!!.professional != null) {
                professionList = model!!.professional!!
                initFluidLayout(initList())
            }
            if (!TextUtils.isEmpty(model!!.educationBackground)) {
                activity_education_experience_education_tv.text = model!!.educationBackground
            }
            if (model!!.unifiedEntrance!!) {
                isTongZhao = true
                activity_education_experience_tongzhao_iv.setImageResource(R.drawable.ring_blue)
                activity_education_experience_feitongzhao_iv.setImageResource(R.drawable.ring_gray)
            } else {
                isTongZhao = false
                activity_education_experience_tongzhao_iv.setImageResource(R.drawable.ring_gray)
                activity_education_experience_feitongzhao_iv.setImageResource(R.drawable.ring_blue)
            }
            if (!TextUtils.isEmpty(model!!.description)) {
                academicAchievement = model!!.description
                activity_education_experience_academic_achievements_tv.text = getString(R.string.mydetail_write_text1) + academicAchievement!!.length + getString(R.string.mydetail_write_text2)
            }
        } else {
            activity_education_experience_delete.visibility = View.GONE
        }

        activity_education_experience_school_name.setOnClickListener(this)
        activity_education_experience_start_time.setOnClickListener(this)
        activity_education_experience_end_time.setOnClickListener(this)
        activity_education_experience_profession.setOnClickListener(this)
        activity_education_experience_education.setOnClickListener(this)
        activity_education_experience_tongzhao.setOnClickListener(this)
        activity_education_experience_feitongzhao.setOnClickListener(this)
        activity_education_experience_academic_achievements.setOnClickListener(this)
        activity_education_experience_delete.setOnClickListener(this)

    }

    //更新信息
    private fun putData() {
        val request = AddEducationExperienceRequest()
        request.schoolName = activity_education_experience_school_name_tv.text.toString().trim()
        request.startTime = startTime
        request.endTime = endTime
        request.professional = professionList
        request.educationBackground = activity_education_experience_education_tv.text.toString().trim()
        request.unifiedEntrance = isTongZhao
        request.description = academicAchievement
        request.id = model!!.id
        presenter!!.putEducationExperience(request)
    }

    //新增信息
    private fun addData() {
        val request = AddEducationExperienceRequest()
        request.schoolName = activity_education_experience_school_name_tv.text.toString().trim()
        request.startTime = startTime
        request.endTime = endTime
        request.professional = professionList
        request.educationBackground = activity_education_experience_education_tv.text.toString().trim()
        request.unifiedEntrance = isTongZhao
        request.description = academicAchievement
        presenter!!.addEducationExperience(request)
    }

    override fun putEducationExperienceSuccess(response: BooleanResponse) {
        removeLoadingFragment()
        if (response.data) {
            finish()
        }
    }

    override fun putEducationExperienceFail(throwable: Throwable) {
        showThrowable(throwable)
        removeLoadingFragment()
    }

    override fun addEducationExperienceSuccess(response: StringResponse) {
        removeLoadingFragment()
        if (!TextUtils.isEmpty(response.data)) {
            finish()
        }
    }

    override fun addEducationExperienceFail(throwable: Throwable) {
        showThrowable(throwable)
        removeLoadingFragment()
    }


    override fun onClick(v: View?) {
        when (v) {
            activity_education_experience_school_name -> {
                intent.setClass(this@EducationExperienceWriteActivity, QuickMatchActivity::class.java)
                intent.putExtra(getString(R.string.NAME), getString(R.string.write_school_name))
                startActivityForResult(intent, 320)
            }
            activity_education_experience_start_time -> {
                val build = TimePickerDialog.Builder()
                        .setType(Type.YEAR_MONTH)
                        .setCallBack { _, millseconds ->
                            val string = sdf.format(Date(millseconds))
                            startTime = millseconds
                            activity_education_experience_start_time_tv.text = string
                        }
                        .build()
                build.show(supportFragmentManager, "a")
            }
            activity_education_experience_end_time -> {
                val build = TimePickerDialog.Builder()
                        .setType(Type.YEAR_MONTH)
                        .setCallBack { _, millseconds ->
                            val string = sdf.format(Date(millseconds))
                            endTime = millseconds
                            activity_education_experience_end_time_tv.text = string
                        }
                        .build()
                build.show(supportFragmentManager, "b")
            }
            activity_education_experience_profession -> {
                intent.setClass(this@EducationExperienceWriteActivity, QuickMatchActivity::class.java)
                intent.putExtra(getString(R.string.NAME), getString(R.string.write_school_name))
                startActivityForResult(intent, 321)
            }
            activity_education_experience_education -> {
                intent.setClass(this@EducationExperienceWriteActivity, EducationSelectActivity::class.java)
                startActivityForResult(intent, 322)
            }
            activity_education_experience_tongzhao -> {
                isTongZhao = true
                activity_education_experience_tongzhao_iv.setImageResource(R.drawable.ring_blue)
                activity_education_experience_feitongzhao_iv.setImageResource(R.drawable.ring_gray)
            }
            activity_education_experience_feitongzhao -> {
                isTongZhao = false
                activity_education_experience_tongzhao_iv.setImageResource(R.drawable.ring_gray)
                activity_education_experience_feitongzhao_iv.setImageResource(R.drawable.ring_blue)
            }
            activity_education_experience_academic_achievements -> {
                intent.setClass(this@EducationExperienceWriteActivity, WriteActivity::class.java)
                if (!TextUtils.isEmpty(academicAchievement)) {
                    intent.putExtra(getString(R.string.DATA), academicAchievement)
                }
                intent.putExtra(getString(R.string.MAX), 300)
                intent.putExtra(getString(R.string.TITLE), getString(R.string.academic_achievements))
                intent.putExtra(getString(R.string.NAME), getString(R.string.write_your_academic_achievements))
                startActivityForResult(intent, 323)
            }
            activity_education_experience_delete -> {
                ViewHelper.showOneLineCard(this, getString(R.string.confirm_to_delete_the_experience), getString(R.string.alright), getString(R.string.cancel), { _, _ ->
                    deleteData()
                }, { _, _ -> })
            }
        }
    }

    private fun deleteData() {
        presenter!!.deleteEducationExperience(model!!.id!!)
    }

    override fun deleteEducationExperienceSuccess(response: BooleanResponse) {
        removeLoadingFragment()
        if (response.data) {
            finish()
        }
    }

    override fun deleteEducationExperienceFail(throwable: Throwable) {
        showThrowable(throwable)
        removeLoadingFragment()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == 1001) {
            when (requestCode) {
                320 -> {
                    activity_education_experience_school_name_tv.text = data!!.getStringExtra(getString(R.string.DATA))
                }
                321 -> {
                    val data = data!!.getStringExtra(getString(R.string.DATA))
                    professionList.add(data)
                    initFluidLayout(initList())
                }
                322 -> {
                    activity_education_experience_education_tv.text = data!!.getStringExtra(getString(R.string.DATA))
                }
                323 -> {
                    academicAchievement = data!!.getStringExtra(getString(R.string.DATA))
                    activity_education_experience_academic_achievements_tv.text = getString(R.string.mydetail_write_text1) + academicAchievement!!.length + getString(R.string.mydetail_write_text2)
                }
            }
        }
    }

    private fun initList(): ArrayList<FluidLayoutBean> {
        val list = ArrayList<FluidLayoutBean>()
        for (i in professionList) {
            val b = FluidLayoutBean()
            b.index = i
            b.text = i
            list.add(b)
        }
        return list
    }

    private fun initFluidLayout(list: ArrayList<FluidLayoutBean>) {
        activity_education_experience_profession_fl.removeAllViews()
        activity_education_experience_profession_fl.setGravity(Gravity.TOP)
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
                for (j in professionList.indices) {
                    if (professionList[j] == bean.text) {
                        index = j
                    }
                }
                professionList.removeAt(index)
                initFluidLayout(initList())
            }

            val params = FluidLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            )
            params.setMargins(0, 20, 30, 20)
            activity_education_experience_profession_fl.addView(tv, params)
        }
    }

}