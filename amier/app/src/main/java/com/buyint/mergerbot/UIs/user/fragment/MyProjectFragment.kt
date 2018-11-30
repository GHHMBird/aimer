package com.buyint.mergerbot.UIs.user.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Paint
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import com.buyint.mergerbot.R
import com.buyint.mergerbot.UIs.main.activity.MainActivity
import com.buyint.mergerbot.UIs.main.adapter.ResultAdapter2
import com.buyint.mergerbot.UIs.match.activity.DeepMatchActivity
import com.buyint.mergerbot.UIs.user.activity.MyProjectActivity
import com.buyint.mergerbot.base.LazyFirstBaseFragment
import com.buyint.mergerbot.dto.MatchCompanyMorePageResponse
import com.buyint.mergerbot.enums.ProjectFilterType
import com.buyint.mergerbot.interfaces.IGetMyMatchList
import com.buyint.mergerbot.interfaces.IUserCareProject
import com.buyint.mergerbot.interfaces.IUserPublishProject
import com.buyint.mergerbot.presenter.MyProjectActivityPresenter
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import java.util.*

@SuppressLint("ValidFragment")
/**
 * Created by huheming on 2018/9/13
 */
class MyProjectFragment(var type: Int, var listener: MyProjectFragmentListener) : LazyFirstBaseFragment(), IUserCareProject, IUserPublishProject, IGetMyMatchList {

    override fun initView(): View {
        val view = LayoutInflater.from(activity).inflate(R.layout.fragment_my_project_fragment, null, false)

        presenter = MyProjectActivityPresenter(this, this, this)

        val tvAll = view.findViewById<TextView>(R.id.fragment_my_project_fragment_select_all)
        val tvTouRongZi = view.findViewById<TextView>(R.id.fragment_my_project_fragment_select_tourongzi)
        val tvIntelligence = view.findViewById<TextView>(R.id.fragment_my_project_fragment_select_intelligence)
        val tvMerger = view.findViewById<TextView>(R.id.fragment_my_project_fragment_select_merger)

        val tvQuick = view.findViewById<TextView>(R.id.fragment_my_project_fragment_quick_match)
        val tvDeep = view.findViewById<TextView>(R.id.fragment_my_project_fragment_deep_match)

        rlNoProject = view.findViewById(R.id.fragment_my_project_fragment_rl_no_project)

        tvQuick.paint.flags = Paint.UNDERLINE_TEXT_FLAG
        tvDeep.paint.flags = Paint.UNDERLINE_TEXT_FLAG
        tvQuick.setOnClickListener { _ ->
            listener.quickMatch()
        }
        tvDeep.setOnClickListener { _ ->
            val intent = Intent(activity, MainActivity::class.java)
            intent.putExtra(getString(R.string.ISSPEAK), false)
            startActivity(intent)
            startActivity(Intent(activity, DeepMatchActivity::class.java))
            activity?.onBackPressed()
        }

        tvAll.setOnClickListener { _ ->
            tvAll.setBackgroundResource(R.drawable.rentage_white)
            tvTouRongZi.setBackgroundResource(R.drawable.tx_bg_gray)
            tvIntelligence.setBackgroundResource(R.drawable.tx_bg_gray)
            tvMerger.setBackgroundResource(R.drawable.tx_bg_gray)
            tvAll.setTextColor((activity as MyProjectActivity).clickColor)
            tvTouRongZi.setTextColor((activity as MyProjectActivity).unClickColor)
            tvIntelligence.setTextColor((activity as MyProjectActivity).unClickColor)
            tvMerger.setTextColor((activity as MyProjectActivity).unClickColor)
            tvAll.elevation = 5f
            tvTouRongZi.elevation = 0f
            tvIntelligence.elevation = 0f
            tvMerger.elevation = 0f

            showLoadingFragment(R.id.fragment_my_project_fragment_inner_fl)
            projectFilterType = ProjectFilterType.ALL.name
            refreshData()
        }
        tvTouRongZi.setOnClickListener { _ ->
            tvAll.setBackgroundResource(R.drawable.tx_bg_gray)
            tvTouRongZi.setBackgroundResource(R.drawable.rentage_white)
            tvIntelligence.setBackgroundResource(R.drawable.tx_bg_gray)
            tvMerger.setBackgroundResource(R.drawable.tx_bg_gray)
            tvAll.setTextColor((activity as MyProjectActivity).unClickColor)
            tvTouRongZi.setTextColor((activity as MyProjectActivity).clickColor)
            tvIntelligence.setTextColor((activity as MyProjectActivity).unClickColor)
            tvMerger.setTextColor((activity as MyProjectActivity).unClickColor)
            tvAll.elevation = 0f
            tvTouRongZi.elevation = 5f
            tvIntelligence.elevation = 0f
            tvMerger.elevation = 0f

            showLoadingFragment(R.id.fragment_my_project_fragment_inner_fl)
            projectFilterType = ProjectFilterType.INVESTMENT_AND_FINANCING.name
            refreshData()
        }
        tvIntelligence.setOnClickListener { _ ->
            tvAll.setBackgroundResource(R.drawable.tx_bg_gray)
            tvTouRongZi.setBackgroundResource(R.drawable.tx_bg_gray)
            tvIntelligence.setBackgroundResource(R.drawable.rentage_white)
            tvMerger.setBackgroundResource(R.drawable.tx_bg_gray)
            tvAll.setTextColor((activity as MyProjectActivity).unClickColor)
            tvTouRongZi.setTextColor((activity as MyProjectActivity).unClickColor)
            tvIntelligence.setTextColor((activity as MyProjectActivity).clickColor)
            tvMerger.setTextColor((activity as MyProjectActivity).unClickColor)
            tvAll.elevation = 0f
            tvTouRongZi.elevation = 0f
            tvIntelligence.elevation = 5f
            tvMerger.elevation = 0f

            showLoadingFragment(R.id.fragment_my_project_fragment_inner_fl)
            projectFilterType = ProjectFilterType.INTELLIGENCE.name
            refreshData()
        }
        tvMerger.setOnClickListener { _ ->
            tvAll.setBackgroundResource(R.drawable.tx_bg_gray)
            tvTouRongZi.setBackgroundResource(R.drawable.tx_bg_gray)
            tvIntelligence.setBackgroundResource(R.drawable.tx_bg_gray)
            tvMerger.setBackgroundResource(R.drawable.rentage_white)
            tvAll.setTextColor((activity as MyProjectActivity).unClickColor)
            tvTouRongZi.setTextColor((activity as MyProjectActivity).unClickColor)
            tvIntelligence.setTextColor((activity as MyProjectActivity).unClickColor)
            tvMerger.setTextColor((activity as MyProjectActivity).clickColor)
            tvAll.elevation = 0f
            tvTouRongZi.elevation = 0f
            tvIntelligence.elevation = 0f
            tvMerger.elevation = 5f

            showLoadingFragment(R.id.fragment_my_project_fragment_inner_fl)
            projectFilterType = ProjectFilterType.BUYING_AND_SELLING.name
            refreshData()
        }

        swipe = view.findViewById(R.id.fragment_my_project_fragment_swipe)
        recyclerView = view.findViewById(R.id.fragment_my_project_fragment_recyclerview)
        val linearLayoutManager = LinearLayoutManager(activity)
        recyclerView!!.layoutManager = linearLayoutManager
        when (type) {
            0 -> {
                adapter = ResultAdapter2(activity, ArrayList(), getString(R.string.userRecommend), 1)
                recyclerView!!.adapter = adapter
            }
            1 -> {
                adapter = ResultAdapter2(activity, ArrayList(), getString(R.string.userRecommend), 2)
                recyclerView!!.adapter = adapter
            }
            2 -> {
                adapter = ResultAdapter2(activity, ArrayList(), getString(R.string.userRecommend), 2)
                recyclerView!!.adapter = adapter
            }
        }

        swipe!!.setOnRefreshListener {
            refreshData()
        }

        swipe!!.setOnLoadMoreListener {
            isLoading = true
            pageNum += 1
            adapter!!.notifyDataSetChanged()
            when (type) {
                0 -> presenter!!.getUserPublishProject(pageNum, projectFilterType)
                1 -> presenter!!.getMyMatchList(pageNum, projectFilterType)
                2 -> presenter!!.getUserCareProject(pageNum, projectFilterType)
            }
        }

        return view
    }

    override fun initData() {
        showLoadingFragment(R.id.fragment_my_project_fragment_fl)
        when (type) {
            0 -> {
                presenter!!.getUserPublishProject(pageNum, projectFilterType)
            }
            1 -> {
                presenter!!.getMyMatchList(pageNum, projectFilterType)
            }
            2 -> {
                presenter!!.getUserCareProject(pageNum, projectFilterType)
            }
        }
    }

    //0我发布的项目 1我关注的项目 2我匹配的项目

    private var presenter: MyProjectActivityPresenter? = null
    private var projectFilterType = ProjectFilterType.ALL.name
    private var rlNoProject: RelativeLayout? = null
    private var recyclerView: RecyclerView? = null
    private var swipe: SmartRefreshLayout? = null
    private var hasMoreItems = true
    private var isLoading: Boolean = false
    private var pageNum = 1
    private var adapter: ResultAdapter2? = null

    private fun refreshData() {
        pageNum = 1
        adapter!!.notifyDataSetChanged()
        isLoading = false
        hasMoreItems = true

        when (type) {
            0 -> presenter!!.getUserPublishProject(pageNum, projectFilterType)
            1 -> presenter!!.getMyMatchList(pageNum, projectFilterType)
            2 -> presenter!!.getUserCareProject(pageNum, projectFilterType)
        }
    }

    override fun getUserCareProjectSuccess(response: MatchCompanyMorePageResponse?) {
        removeLoadingFragment()
        if (response?.data == null || response.data.size == 0) {
            swipe!!.visibility = View.GONE
            rlNoProject!!.visibility = View.VISIBLE
        } else {
            swipe!!.visibility = View.VISIBLE
            rlNoProject!!.visibility = View.GONE
            adapter!!.list = response.data
            if (response.data.size < 10) {
                hasMoreItems = false
                adapter!!.notifyDataSetChanged()
            }
        }
        isLoading = false
        swipe!!.finishRefresh()
        swipe!!.finishLoadMore()
    }

    override fun getUserCareProjectFail(throwable: Throwable) {
        removeLoadingFragment()
        swipe!!.finishRefresh()
        swipe!!.finishLoadMore()
        showThrowable(throwable)
    }

    override fun getUserCareProjectMoreSuccess(response: MatchCompanyMorePageResponse) {
        removeLoadingFragment()
        if (response.data != null && response.data.size > 0) {
            adapter!!.addList(response.data)
        } else {
            hasMoreItems = false
            adapter!!.notifyDataSetChanged()
        }
        isLoading = false
        swipe!!.finishRefresh()
        swipe!!.finishLoadMore()
    }

    override fun getUserCareProjectMoreFail(throwable: Throwable) {
        removeLoadingFragment()
        swipe!!.finishRefresh()
        swipe!!.finishLoadMore()
        if (isLoading) {
            pageNum--
            isLoading = false
            adapter!!.notifyDataSetChanged()
        }
        showThrowable(throwable)
    }

    override fun getUserPublishProjectSuccess(response: MatchCompanyMorePageResponse) {
        removeLoadingFragment()
        if (response.data == null || response.data.size == 0) {
            swipe!!.visibility = View.GONE
            rlNoProject!!.visibility = View.VISIBLE
        } else {
            swipe!!.visibility = View.VISIBLE
            rlNoProject!!.visibility = View.GONE
            adapter!!.list = response.data
            if (response.data.size < 10) {
                hasMoreItems = false
                adapter!!.notifyDataSetChanged()
            }
        }
        isLoading = false
        swipe!!.finishRefresh()
        swipe!!.finishLoadMore()
    }

    override fun getUserPublishProjectFail(throwable: Throwable) {
        removeLoadingFragment()
        swipe!!.finishRefresh()
        swipe!!.finishLoadMore()
        showThrowable(throwable)
    }

    override fun getUserPublishProjectMoreSuccess(response: MatchCompanyMorePageResponse) {
        removeLoadingFragment()
        if (response.data != null && response.data.size > 0) {
            adapter!!.addList(response.data)
        } else {
            hasMoreItems = false
            adapter!!.notifyDataSetChanged()
        }
        isLoading = false
        swipe!!.finishRefresh()
        swipe!!.finishLoadMore()
    }

    override fun getUserPublishProjectMoreFail(throwable: Throwable) {
        removeLoadingFragment()
        swipe!!.finishRefresh()
        swipe!!.finishLoadMore()
        if (isLoading) {
            pageNum--
            isLoading = false
            adapter!!.notifyDataSetChanged()
        }
        showThrowable(throwable)
    }

    override fun getMyMatchListSuccess(response: MatchCompanyMorePageResponse) {
        removeLoadingFragment()
        if (response.data == null || response.data.size == 0) {
            swipe!!.visibility = View.GONE
            rlNoProject!!.visibility = View.VISIBLE
        } else {

            for (i in response.data.indices) {
                response.data[i].type = getString(R.string.matchedProjectDetail)
            }

            swipe!!.visibility = View.VISIBLE
            rlNoProject!!.visibility = View.GONE
            adapter!!.list = response.data
            if (response.data.size < 10) {
                hasMoreItems = false
                adapter!!.notifyDataSetChanged()
            }
        }
        isLoading = false
        swipe!!.finishRefresh()
        swipe!!.finishLoadMore()
    }

    override fun getMyMatchListFail(throwable: Throwable) {
        removeLoadingFragment()
        swipe!!.finishRefresh()
        swipe!!.finishLoadMore()
        showThrowable(throwable)
    }

    override fun getMyMatchListMoreSuccess(response: MatchCompanyMorePageResponse) {
        removeLoadingFragment()
        if (response.data != null && response.data.size > 0) {

            for (i in response.data.indices) {
                response.data[i].type = getString(R.string.matchedProjectDetail)
            }

            adapter!!.addList(response.data)
        } else {
            hasMoreItems = false
            adapter!!.notifyDataSetChanged()
        }
        isLoading = false
        swipe!!.finishRefresh()
        swipe!!.finishLoadMore()
    }

    override fun getMyMatchListMoreFail(throwable: Throwable) {
        removeLoadingFragment()
        swipe!!.finishRefresh()
        swipe!!.finishLoadMore()
        if (isLoading) {
            pageNum--
            isLoading = false
            adapter!!.notifyDataSetChanged()
        }
        showThrowable(throwable)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (presenter != null) {
            presenter!!.destory()
            presenter = null
        }
    }

    interface MyProjectFragmentListener {
        fun quickMatch()
    }
}