package com.buyint.mergerbot.UIs.user.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.buyint.mergerbot.R
import com.buyint.mergerbot.UIs.user.adapter.BusinessActivityAdapter
import com.buyint.mergerbot.base.BaseActivity
import com.buyint.mergerbot.dto.GetUserBusinessActivityListResponse
import com.buyint.mergerbot.dto.GetUserProjectPerformanceListRequest
import com.buyint.mergerbot.interfaces.IGetUserBusinessActivityList
import com.buyint.mergerbot.presenter.BusinessActivityPresenter
import kotlinx.android.synthetic.main.activity_business_activity.*
import kotlinx.android.synthetic.main.toolbar.*

/**
 * Created by huheming on 2018/7/17
 */
class BusinessActivityActivity : BaseActivity(), IGetUserBusinessActivityList {

    private var presenter: BusinessActivityPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        presenter = BusinessActivityPresenter(this)

        setMyTitleColor()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_business_activity)

        toorbar_title.text = getString(R.string.business_activity)
        toolbar_back.setOnClickListener { onBackPressed() }

        activity_business_activity_recycler.layoutManager = LinearLayoutManager(this)
        activity_business_activity_recycler.adapter = BusinessActivityAdapter(this, ArrayList(), true)
        activity_business_activity_recycler.isNestedScrollingEnabled = false

        activity_business_activity_add_activity.setOnClickListener {
            val intent = Intent(this@BusinessActivityActivity, BusinessActivityWriteActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        showLoadingFragment(R.id.activity_business_activity_fl)
        getData()
    }

    private fun getData() {
        val request = GetUserProjectPerformanceListRequest()
        request.pageNum = 1
        request.pageSize = 100
        request.total = 100
        presenter!!.getUserBusinessActivityList(request)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (presenter != null) {
            presenter!!.destory()
            presenter = null
        }
    }

    override fun getUserBusinessActivityListSuccess(response: GetUserBusinessActivityListResponse) {
        removeLoadingFragment()
        if (response.data != null && response.data!!.size > 0) {
            activity_business_activity_recycler.adapter = BusinessActivityAdapter(this, response.data, true)
        }
    }

    override fun getUserBusinessActivityListFail(throwable: Throwable) {
        removeLoadingFragment()
        showThrowable(throwable)
    }
}