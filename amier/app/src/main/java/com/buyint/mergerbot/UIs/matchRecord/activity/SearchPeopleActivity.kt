package com.buyint.mergerbot.UIs.matchRecord.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.buyint.mergerbot.R
import com.buyint.mergerbot.UIs.matchRecord.adapter.MatchRecordAdapter
import com.buyint.mergerbot.base.BaseActivity
import com.buyint.mergerbot.dto.MatchRecordListModel
import com.buyint.mergerbot.interfaces.ISearchPeople
import com.buyint.mergerbot.presenter.SearchPeoplePresenter
import kotlinx.android.synthetic.main.activity_search_people.*
import kotlinx.android.synthetic.main.toolbar_white.*

/**
 * Created by huheming on 2018/7/30
 *
 * 搜索好友页面
 */
class SearchPeopleActivity : BaseActivity(), ISearchPeople {

    private lateinit var type: String
    private var presenter: SearchPeoplePresenter? = null
    private lateinit var adapter: MatchRecordAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        presenter = SearchPeoplePresenter(this)
        setTitleWhiteAndTextBlank()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_people)

        toolbar_white_top.visibility = View.GONE
        toolbar_white_right_search_rl.visibility = View.GONE
        toolbar_white_right_add_rl.visibility = View.GONE

        toolbar_white_back.setOnClickListener { onBackPressed() }

        type = intent.getStringExtra(getString(R.string.TYPE))
        if (type == getString(R.string.TYPE_0)) {//添加好友
            toolbar_white_title.text = getString(R.string.add_friend)
            activity_search_people_et.hint = getString(R.string.please_write_friend_account)
        } else {//搜索好友
            toolbar_white_title.text = getString(R.string.search_friend)
            activity_search_people_et.hint = getString(R.string.please_write_friend_account_and_other)
        }

        activity_search_people_recycler.layoutManager = LinearLayoutManager(this)
        adapter = MatchRecordAdapter(this, false, ArrayList())
        activity_search_people_recycler.adapter = adapter

        activity_search_people_et.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (activity_search_people_et.text.toString().isNotEmpty()) {
                    presenter!!.searchPeople(activity_search_people_et.text.toString())
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        if (presenter != null) {
            presenter!!.destory()
            presenter = null
        }
    }

    override fun searchPeopleSuccess(response: ArrayList<MatchRecordListModel>) {
        if (response.size > 0) {
            adapter.setData(response)
        }
    }

    override fun searchPeopleFail(throwable: Throwable) {
        showThrowable(throwable)
    }
}