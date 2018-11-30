package com.buyint.mergerbot.UIs.user.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.buyint.mergerbot.R
import com.buyint.mergerbot.UIs.user.adapter.ProjectPerformanceAdapter
import com.buyint.mergerbot.base.BaseActivity
import com.buyint.mergerbot.dto.GetUserProjectPerformanceListRequest
import com.buyint.mergerbot.dto.GetUserProjectPerformanceListResponse
import com.buyint.mergerbot.interfaces.IGetUserProjectPerformanceList
import com.buyint.mergerbot.presenter.ProjectPerformancePresenter
import kotlinx.android.synthetic.main.activity_performance_achievement.*
import kotlinx.android.synthetic.main.toolbar.*

/**
 * Created by huheming on 2018/7/13
 */
class ProjectPerformanceActivity : BaseActivity(), IGetUserProjectPerformanceList {

    private var presenter: ProjectPerformancePresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        presenter = ProjectPerformancePresenter(this)

        setMyTitleColor()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_performance_achievement)

        toorbar_title.text = getString(R.string.project_performance)
        toolbar_back.setOnClickListener { onBackPressed() }

        activity_performance_achievement_recycler.layoutManager = LinearLayoutManager(this)
        activity_performance_achievement_recycler.adapter = ProjectPerformanceAdapter(this, ArrayList(), true)

        activity_performance_achievement_add_experience.setOnClickListener {
            val intent = Intent(this@ProjectPerformanceActivity, ProjectPerformanceWriteActivity::class.java)
            startActivityForResult(intent, 1001)
        }

    }

    override fun onStart() {
        super.onStart()
        showLoadingFragment(R.id.activity_performance_achievement_fl)
        getData()
    }

    private fun getData() {
        val request = GetUserProjectPerformanceListRequest()
        request.pageNum = 1
        request.pageSize = 100
        request.total = 100
        presenter!!.getUserProjectPerformanceList(request)
    }

    override fun getUserProjectPerformanceListSuccess(response: GetUserProjectPerformanceListResponse) {
        removeLoadingFragment()
        if (response.data != null && response.data!!.size > 0) {
            activity_performance_achievement_recycler.adapter = ProjectPerformanceAdapter(this, response.data!!, true)
        }
    }

    override fun getUserProjectPerformanceListFail(throwable: Throwable) {
        showThrowable(throwable)
        removeLoadingFragment()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (presenter != null) {
            presenter!!.destory()
            presenter = null
        }
    }
}