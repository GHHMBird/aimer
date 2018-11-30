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
import com.buyint.mergerbot.dto.GetUserBusinessActivityResponse
import com.buyint.mergerbot.dto.PutUserBusinessActivityRequest
import com.buyint.mergerbot.dto.StringResponse
import com.buyint.mergerbot.interfaces.IDeleteUserBusinessActivity
import com.buyint.mergerbot.interfaces.IGetUserBusinessActivity
import com.buyint.mergerbot.interfaces.IPostUserBusinessActivity
import com.buyint.mergerbot.interfaces.IPutUserBusinessActivity
import com.buyint.mergerbot.presenter.BusinessActivityWritePresenter
import com.buyint.mergerbot.view.FluidLayout
import com.buyint.mergerbot.dto.FluidLayoutBean
import com.buyint.mergerbot.view.pickerview.TimePickerDialog
import com.buyint.mergerbot.view.pickerview.data.Type
import kotlinx.android.synthetic.main.activity_business_activity_write.*
import kotlinx.android.synthetic.main.toolbar.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by huheming on 2018/7/18
 */
class BusinessActivityWriteActivity : BaseActivity(), View.OnClickListener, IDeleteUserBusinessActivity, IGetUserBusinessActivity, IPostUserBusinessActivity, IPutUserBusinessActivity {

    override fun getUserBusinessActivitySuccess(response: GetUserBusinessActivityResponse) {
        if (response.data != null) {
            if (!TextUtils.isEmpty(response.data!!.activityName)) {
                activity_business_activity_write_activity_name_tv.text = response.data!!.activityName
            }
            activity_business_activity_write_start_time_tv.text = sdf.format(response.data!!.startTime)
            activity_business_activity_write_end_time_tv.text = sdf.format(response.data!!.endTime)
            startTime = response.data!!.startTime
            endTime = response.data!!.endTime
            if (response.data!!.parties != null && response.data!!.parties!!.size > 0) {
                for (name in response.data!!.parties!!) {
                    peopleList.add(name)
                }
                initPeopleFluidLayout(initPeopleList())
            }
            if (response.data!!.activityType != null && response.data!!.activityType!!.size > 0) {
                for (name in response.data!!.activityType!!) {
                    typeList.add(name)
                }
                initMainFluidLayout(initMainList())
            }
            if (!TextUtils.isEmpty(response.data!!.activityAddress)) {
                activity_business_activity_write_activity_address_tv.text = response.data!!.activityAddress
            }
            if (!TextUtils.isEmpty(response.data!!.activityDescription)) {
                activityDesc = response.data!!.activityDescription!!
                activity_business_activity_write_activity_desc_tv.text = getString(R.string.mydetail_write_text1) + activityDesc.length + getString(R.string.mydetail_write_text2)
            }
        }
    }

    override fun getUserBusinessActivityFail(throwable: Throwable) {
        removeLoadingFragment()
        showThrowable(throwable)
    }

    private var id: String? = null
    private val sdf = SimpleDateFormat("yyyy-MM")
    private var activityDesc = ""
    private var peopleList = ArrayList<String>()
    private var typeList = ArrayList<String>()
    private var presenter: BusinessActivityWritePresenter? = null
    private var startTime: Long? = null
    private var endTime: Long? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        presenter = BusinessActivityWritePresenter(this, this, this, this)

        setMyTitleColor()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_business_activity_write)

        toorbar_title.text = getString(R.string.business_activity)
        toolbar_back.setOnClickListener { onBackPressed() }
        val drawable = resources.getDrawable(R.mipmap.duigou)
        drawable.setBounds(0, 0, dp2px(16f), dp2px(16f))
        toolbar_right.setCompoundDrawables(null, null, drawable, null)
        toolbar_right_rl.setOnClickListener {
            showLoadingFragment(R.id.activity_business_activity_write_fl)
            if (TextUtils.isEmpty(id)) {
                saveData()
            } else {
                updateData()
            }
        }

        activity_business_activity_write_activity_name.setOnClickListener(this)
        activity_business_activity_write_start_time.setOnClickListener(this)
        activity_business_activity_write_end_time.setOnClickListener(this)
        activity_business_activity_write_related_people.setOnClickListener(this)
        activity_business_activity_write_main_category.setOnClickListener(this)
        activity_business_activity_write_activity_address.setOnClickListener(this)
        activity_business_activity_write_activity_desc.setOnClickListener(this)
        activity_business_activity_write_delete.setOnClickListener(this)

        id = intent.getStringExtra(getString(R.string.DATA))
        if (!TextUtils.isEmpty(id)) {
            activity_business_activity_write_delete.visibility = View.VISIBLE
            presenter!!.getUserBusinessActivity(id!!)
        } else {
            activity_business_activity_write_delete.visibility = View.GONE
        }
    }

    override fun onClick(v: View?) {
        var intent = Intent()
        when (v) {
            activity_business_activity_write_activity_name -> {
                intent.setClass(this@BusinessActivityWriteActivity, QuickMatchActivity::class.java)
                intent.putExtra(getString(R.string.NAME), getString(R.string.write_activity_name))
                startActivityForResult(intent, 220)
            }
            activity_business_activity_write_start_time -> {
                val build = TimePickerDialog.Builder()
                        .setType(Type.YEAR_MONTH)
                        .setCallBack { _, millseconds ->
                            val string = sdf.format(Date(millseconds))
                            startTime = millseconds
                            activity_business_activity_write_start_time_tv.text = string
                        }
                        .build()
                build.show(supportFragmentManager, "a")
            }
            activity_business_activity_write_end_time -> {
                val build = TimePickerDialog.Builder()
                        .setType(Type.YEAR_MONTH)
                        .setCallBack { _, millseconds ->
                            val string = sdf.format(Date(millseconds))
                            endTime = millseconds
                            activity_business_activity_write_end_time_tv.text = string
                        }
                        .build()
                build.show(supportFragmentManager, "b")
            }
            activity_business_activity_write_related_people -> {
                intent.setClass(this@BusinessActivityWriteActivity, QuickMatchActivity::class.java)
                intent.putExtra(getString(R.string.NAME), getString(R.string.please_write_related_people))
                startActivityForResult(intent, 221)
            }
            activity_business_activity_write_main_category -> {
                intent.setClass(this@BusinessActivityWriteActivity, QuickMatchActivity::class.java)
                intent.putExtra(getString(R.string.NAME), getString(R.string.write_main_category))
                startActivityForResult(intent, 222)
            }
            activity_business_activity_write_activity_address -> {
                intent.setClass(this@BusinessActivityWriteActivity, LocationActivity::class.java)
                startActivityForResult(intent, 223)
            }
            activity_business_activity_write_activity_desc -> {
                intent.setClass(this@BusinessActivityWriteActivity, WriteActivity::class.java)
                if (!TextUtils.isEmpty(activityDesc)) {
                    intent.putExtra(getString(R.string.DATA), activityDesc)
                }
                intent.putExtra(getString(R.string.MAX), 300)
                intent.putExtra(getString(R.string.TITLE), getString(R.string.activity_desc))
                intent.putExtra(getString(R.string.NAME), getString(R.string.please_write_activity_desc))
                startActivityForResult(intent, 224)
            }
            activity_business_activity_write_delete -> {
                ViewHelper.showOneLineCard(this, getString(R.string.confirm_to_delete_the_activity), getString(R.string.alright), getString(R.string.cancel), { _, _ ->
                   showLoadingFragment(R.id.activity_business_activity_write_fl)
                    presenter!!.deleteUserBusinessActivity(id!!)
                }, { _, _ -> })
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == 1001) {
            when (requestCode) {
                220 -> {
                    activity_business_activity_write_activity_name_tv.text = data!!.getStringExtra(getString(R.string.DATA))
                }
                221 -> {
                    val name = data!!.getStringExtra(getString(R.string.DATA))
                    peopleList.add(name)
                    initPeopleFluidLayout(initPeopleList())
                }
                222 -> {
                    val name = data!!.getStringExtra(getString(R.string.DATA))
                    typeList.add(name)
                    initMainFluidLayout(initMainList())
                }
                223 -> {
                    activity_business_activity_write_activity_address_tv.text = data!!.getStringExtra(getString(R.string.DATA))
                }
                224 -> {
                    activityDesc = data!!.getStringExtra(getString(R.string.DATA))
                    activity_business_activity_write_activity_desc_tv.text = getString(R.string.mydetail_write_text1) + activityDesc.length + getString(R.string.mydetail_write_text2)
                }
            }
        }
    }


    private fun initPeopleList(): ArrayList<FluidLayoutBean> {
        val list = ArrayList<FluidLayoutBean>()
        for (i in peopleList) {
            val b = FluidLayoutBean()
            b.index = i
            b.text = i
            list.add(b)
        }
        return list
    }

    private fun initPeopleFluidLayout(list: ArrayList<FluidLayoutBean>) {
        activity_business_activity_write_related_people_fl.removeAllViews()
        activity_business_activity_write_related_people_fl.setGravity(Gravity.TOP)
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
                for (j in peopleList.indices) {
                    if (peopleList[j] == bean.text) {
                        index = j
                    }
                }
                peopleList.removeAt(index)
                initPeopleFluidLayout(initPeopleList())
            }

            val params = FluidLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            )
            params.setMargins(0, 20, 30, 20)
            activity_business_activity_write_related_people_fl.addView(tv, params)
        }
    }


    private fun initMainList(): ArrayList<FluidLayoutBean> {
        val list = ArrayList<FluidLayoutBean>()
        for (i in typeList) {
            val b = FluidLayoutBean()
            b.index = i
            b.text = i
            list.add(b)
        }
        return list
    }

    private fun initMainFluidLayout(list: ArrayList<FluidLayoutBean>) {
        activity_business_activity_write_main_category_fl.removeAllViews()
        activity_business_activity_write_main_category_fl.setGravity(Gravity.TOP)
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
                for (j in typeList.indices) {
                    if (typeList[j] == bean.text) {
                        index = j
                    }
                }
                typeList.removeAt(index)
                initMainFluidLayout(initMainList())
            }

            val params = FluidLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            )
            params.setMargins(0, 20, 30, 20)
            activity_business_activity_write_main_category_fl.addView(tv, params)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (presenter != null) {
            presenter!!.destory()
            presenter = null
        }
    }

    //更新数据
    private fun updateData() {
        val request = PutUserBusinessActivityRequest()
        request.activityName = activity_business_activity_write_activity_name_tv.text.toString().trim()
        request.activityAddress = activity_business_activity_write_activity_address_tv.text.toString().trim()
        request.activityDescription = activityDesc
        request.activityType = typeList
        request.endTime = endTime
        request.startTime = startTime
        request.parties = typeList
        request.id = id!!
        presenter!!.putUserBusinessActivity(request)
    }

    //新增数据
    private fun saveData() {
        val request = PutUserBusinessActivityRequest()
        request.activityName = activity_business_activity_write_activity_name_tv.text.toString().trim()
        request.activityAddress = activity_business_activity_write_activity_address_tv.text.toString().trim()
        request.activityDescription = activityDesc
        request.activityType = typeList
        request.endTime = endTime
        request.startTime = startTime
        request.parties = typeList
        presenter!!.postUserBusinessActivity(request)
    }

    override fun deleteUserBusinessActivitySuccess(response: BooleanResponse) {
        removeLoadingFragment()
        if (response.data) {
            finish()
        }
    }

    override fun deleteUserBusinessActivityFail(throwable: Throwable) {
        removeLoadingFragment()
        showThrowable(throwable)
    }

    override fun postUserBusinessActivitySuccess(response: StringResponse) {
        removeLoadingFragment()
        if (!TextUtils.isEmpty(response.data)) {
            showToast(getString(R.string.file_save_success))
            finish()
        }
    }

    override fun postUserBusinessActivityFail(throwable: Throwable) {
        showThrowable(throwable)
        removeLoadingFragment()
    }

    override fun putUserBusinessActivitySuccess(response: BooleanResponse) {
        removeLoadingFragment()
        if (response.data) {
            finish()
        }
    }

    override fun putUserBusinessActivityFail(throwable: Throwable) {
        showThrowable(throwable)
        removeLoadingFragment()
    }
}