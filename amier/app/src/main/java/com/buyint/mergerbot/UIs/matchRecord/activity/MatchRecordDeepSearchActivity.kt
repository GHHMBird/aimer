package com.buyint.mergerbot.UIs.matchRecord.activity

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.SpannableStringBuilder
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import com.buyint.mergerbot.R
import com.buyint.mergerbot.UIs.match.baseAdapter.QuickMatchCodeNameAdapter
import com.buyint.mergerbot.UIs.match.baseAdapter.QuickMatchStringAdapter
import com.buyint.mergerbot.base.BaseActivity
import com.buyint.mergerbot.dto.CodeNameBean
import com.buyint.mergerbot.dto.MatchRecordDeepMatchRequest
import com.buyint.mergerbot.dto.MatchRecordResultBean
import com.buyint.mergerbot.dto.UserAndCompanyNameAutoCompleteResponse
import com.buyint.mergerbot.helper.HttpHelper
import com.buyint.mergerbot.interfaces.IUserAndCompanyNameAutoComplete
import com.buyint.mergerbot.presenter.MatchRecordSplashPresenter
import com.buyint.mergerbot.view.SelectableBackgroundView
import kotlinx.android.synthetic.main.activity_match_record_deep_search.*
import kotlinx.android.synthetic.main.toolbar_white.*

/**
 * Created by huheming on 2018/10/16
 */
class MatchRecordDeepSearchActivity : BaseActivity(), IUserAndCompanyNameAutoComplete {

    private var userName = CodeNameBean()
    private var isShow = false
    private lateinit var nameAdapter: QuickMatchCodeNameAdapter
    private lateinit var companyAdapter: QuickMatchStringAdapter
    private var presenter: MatchRecordSplashPresenter? = null
    private var userClick = false

    override fun onCreate(savedInstanceState: Bundle?) {
        setTitleWhiteAndTextBlank()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match_record_deep_search)

        presenter = MatchRecordSplashPresenter(null, this)

        toolbar_white_title.text = getString(R.string.precise_search)
        toolbar_white_back.setOnClickListener { onBackPressed() }
        toolbar_white_top.visibility = View.GONE
        toolbar_white_right_add_rl.visibility = View.GONE
        toolbar_white_right_search_rl.visibility = View.GONE

        companyAdapter = QuickMatchStringAdapter(this, ArrayList(), QuickMatchStringAdapter.QuickMatchStringAdapterListener {
            activity_match_record_deep_search_company.text = SpannableStringBuilder(it)
            activity_match_record_deep_search_company.setSelection(it!!.length)
            activity_match_record_deep_search_company.dismissDropDown()
        })
        activity_match_record_deep_search_company.setAdapter(companyAdapter)
        activity_match_record_deep_search_company.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!TextUtils.isEmpty(s.toString().trim())) {
                    if (TextUtils.isEmpty(userName.name)) {
                        userName.name = ""
                    }
                    presenter!!.userAndCompanyNameAutoComplete(1, userName.name, s.toString().trim())
                }
            }
        })

        nameAdapter = QuickMatchCodeNameAdapter(this, ArrayList(), QuickMatchCodeNameAdapter.QuickMatchCodeNameAdapterListener {
            userName = it
            userClick = true
            activity_match_record_deep_search_name.text = SpannableStringBuilder(it.name)
            activity_match_record_deep_search_name.setSelection(it.name!!.length)
            activity_match_record_deep_search_name.dismissDropDown()
        })
        activity_match_record_deep_search_name.setAdapter(nameAdapter)
        activity_match_record_deep_search_name.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!TextUtils.isEmpty(s.toString().trim())) {
                    var companyName = ""
                    if (!TextUtils.isEmpty(activity_match_record_deep_search_company.text.toString().trim())) {
                        companyName = activity_match_record_deep_search_company.text.toString().trim()
                    }
                    presenter!!.userAndCompanyNameAutoComplete(0, s.toString().trim(), companyName)
                }
                if (!userClick) {
                    userName.name = s.toString().trim()
                    userName.code = ""
                }
            }
        })

        activity_match_record_deep_search_sbv.setListener(object : SelectableBackgroundView.SelectableBackgroundViewListener {
            override fun finished() {
                if (!TextUtils.isEmpty(activity_match_record_deep_search_name.text.toString().trim())) {
                    search()
                }
            }
        })

        activity_match_record_deep_search_iv_visible.setOnClickListener {
            if (isShow) {
                activity_match_record_deep_search_ll.visibility = View.GONE
                activity_match_record_deep_search_iv_visible.setImageResource(R.mipmap.icon_contact_down)
            } else {
                activity_match_record_deep_search_ll.visibility = View.VISIBLE
                activity_match_record_deep_search_iv_visible.setImageResource(R.mipmap.icon_contact_up)
            }
            isShow = !isShow
        }
    }

    private fun search() {
        showLoadingFragment(R.id.activity_match_record_deep_search_fl)
        val request = MatchRecordDeepMatchRequest()
        request.name = activity_match_record_deep_search_name.text.toString().trim()
        request.companyName = activity_match_record_deep_search_company.text.toString().trim()
        request.position = activity_match_record_deep_search_position.text.toString().trim()
        request.enjoyProject = activity_match_record_deep_search_thing.text.toString().trim()
        request.onceInCompany = activity_match_record_deep_search_old_company.text.toString().trim()
        HttpHelper.matchRecordDeepMatch(request).subscribe({

            removeLoadingFragment()

            val type = intent.getStringExtra(getString(R.string.TYPE))
            if (type == getString(R.string.TYPE_0)) {
                val intent = Intent()
                val bean = MatchRecordResultBean()
                bean.list = it
                intent.putExtra(getString(R.string.DATA), bean)
                intent.putExtra(getString(R.string.NAME), activity_match_record_deep_search_name.text.toString().trim())
                setResult(1010, intent)
                onBackPressed()
            } else if (type == getString(R.string.TYPE_1)) {
                val intent = Intent(this@MatchRecordDeepSearchActivity, MatchRecordResultActivity::class.java)
                val bean = MatchRecordResultBean()
                bean.list = it
                intent.putExtra(getString(R.string.DATA), bean)
                intent.putExtra(getString(R.string.NAME), activity_match_record_deep_search_name.text.toString().trim())
                startActivity(intent)
                onBackPressed()
            }

        }, {
            removeLoadingFragment()
            showThrowable(it)
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        if (presenter != null) {
            presenter!!.destory()
            presenter = null
        }
    }

    override fun userAndCompanyNameAutoCompleteUserNameSuccess(response: UserAndCompanyNameAutoCompleteResponse) {
        if (response.data != null && response.data!!.userNames != null) {
            nameAdapter.setList(response.data!!.userNames)
        }
    }

    override fun userAndCompanyNameAutoCompleteUserCompanyNameSuccess(response: UserAndCompanyNameAutoCompleteResponse) {
        if (response.data != null && response.data!!.companyNames != null) {
            companyAdapter.setList(response.data!!.companyNames)
        }
    }

    override fun userAndCompanyNameAutoCompleteUserFail(throwable: Throwable) {
        showThrowable(throwable)
    }
}