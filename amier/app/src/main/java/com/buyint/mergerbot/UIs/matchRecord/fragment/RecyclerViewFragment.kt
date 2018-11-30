package com.buyint.mergerbot.UIs.matchRecord.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.buyint.mergerbot.R
import com.buyint.mergerbot.UIs.main.adapter.ResultAdapter2
import com.buyint.mergerbot.UIs.match.RecyclerAdapter.MatchRecordDetailClientAdapter
import com.buyint.mergerbot.UIs.matchRecord.adapter.MatchRecordAdapter
import com.buyint.mergerbot.base.BaseFragment
import com.buyint.mergerbot.dto.MatchCompanyMorePageResponse
import com.buyint.mergerbot.dto.MatchRecordDetailClientResponse
import com.buyint.mergerbot.dto.MatchRecordListResponse
import com.buyint.mergerbot.interfaces.IMatchRecordDetailClient
import com.buyint.mergerbot.interfaces.IMatchRecordDetailPerson
import com.buyint.mergerbot.interfaces.IMatchRecordDetailProject
import com.buyint.mergerbot.presenter.MatchRecordDetailFragmentPresenter

/**
 * Created by huheming on 2018/8/1
 */
@SuppressLint("ValidFragment")
class RecyclerViewFragment() : BaseFragment(), IMatchRecordDetailPerson, IMatchRecordDetailProject, IMatchRecordDetailClient {
    override fun matchRecordDetailClientSuccess(it: MatchRecordDetailClientResponse) {
        if (it.data != null) {
            if (listener != null) {
                listener!!.getNum(it.data!!.size)
            }
            recycler!!.adapter = MatchRecordDetailClientAdapter(activity!!, it.data!!)
        } else {
            if (listener != null) {
                listener!!.getNum(0)
            }
        }
    }

    override fun matchRecordDetailClientFail(it: Throwable) {
        showThrowable(it)
        if (listener != null) {
            listener!!.getNum(0)
        }
    }

    override fun matchRecordDetailProjectSuccess(response: MatchCompanyMorePageResponse) {
        if (response.data != null) {
            if (listener != null) {
                listener!!.getNum(response.data!!.size)
            }
            for (bean in response.data) {
                bean.type = getString(R.string.matchedRecordProjectDetail)
            }
            recycler!!.adapter = ResultAdapter2(activity, response.data, id, 0)
        } else {
            if (listener != null) {
                listener!!.getNum(0)
            }
        }
    }

    override fun matchRecordDetailProjectFail(throwable: Throwable) {
        showThrowable(throwable)
        if (listener != null) {
            listener!!.getNum(0)
        }
    }

    override fun matchRecordDetailPersonSuccess(response: MatchRecordListResponse) {
        if (response.data != null) {
            if (listener != null) {
                listener!!.getNum(response.data!!.size)
            }
            recycler!!.adapter = MatchRecordAdapter(activity!!, false, response.data)
        } else {
            if (listener != null) {
                listener!!.getNum(0)
            }
        }
    }

    override fun matchRecordDetailPersonFail(throwable: Throwable) {
        showThrowable(throwable)
        if (listener != null) {
            listener!!.getNum(0)
        }
    }

    private var type: Int? = null
    private var id: String? = null
    private var presenter: MatchRecordDetailFragmentPresenter? = null
    private var recycler: RecyclerView? = null
    private var listener: RecyclerViewInterface? = null

    constructor(type: Int, id: String?, listener: RecyclerViewInterface?) : this() {
        this.type = type
        this.id = id
        this.listener = listener
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val inflate = LayoutInflater.from(activity).inflate(R.layout.fragemnt_recyclerview, null, false)
        recycler = inflate.findViewById(R.id.fragment_recycler_recycler) as RecyclerView
        recycler!!.layoutManager = LinearLayoutManager(activity)
        presenter = MatchRecordDetailFragmentPresenter(this, this, this)
        when (type) {
            0 -> {
                presenter!!.matchRecordDetailProject(id!!)
            }
            1 -> {
                presenter!!.matchRecordDetailPerson(id!!)
            }
            2 -> {
                presenter!!.matchRecordDetailClient(id!!)
            }
        }
        return inflate
    }

    override fun onDestroy() {
        super.onDestroy()
        if (presenter != null) {
            presenter!!.destory()
            presenter = null
        }
    }

    interface RecyclerViewInterface {
        fun getNum(num: Int)
    }
}