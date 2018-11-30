package com.buyint.mergerbot.UIs.matchRecord.activity

import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.text.Editable
import android.text.SpannableStringBuilder
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import com.buyint.mergerbot.R
import com.buyint.mergerbot.UIs.match.baseAdapter.QuickMatchCodeNameAdapter
import com.buyint.mergerbot.UIs.match.baseAdapter.QuickMatchStringAdapter
import com.buyint.mergerbot.Utility.ViewHelper
import com.buyint.mergerbot.base.BaseActivity
import com.buyint.mergerbot.database.getLoginResponse
import com.buyint.mergerbot.database.saveLoginResponse
import com.buyint.mergerbot.dto.*
import com.buyint.mergerbot.interfaces.IUserAndCompanyNameAutoComplete
import com.buyint.mergerbot.interfaces.IUserNameLock
import com.buyint.mergerbot.presenter.MatchRecordSplashPresenter
import kotlinx.android.synthetic.main.activity_match_record_splash.*
import kotlinx.android.synthetic.main.toolbar.*

/**
 * Created by huheming on 2018/7/25
 */
class MatchRecordSplashActivity : BaseActivity(), IUserNameLock, IUserAndCompanyNameAutoComplete {

    private var loginResponse: LoginResponse? = null
    private var userName = CodeNameBean()
    private var presenter: MatchRecordSplashPresenter? = null
    private lateinit var nameAdapter: QuickMatchCodeNameAdapter
    private lateinit var companyAdapter: QuickMatchStringAdapter
    private var userClick = false

    override fun onCreate(savedInstanceState: Bundle?) {

        presenter = MatchRecordSplashPresenter(this, this)

        setMyTitleColor()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match_record_splash)

        attachKeyboardListeners()

        toolbar_back.setOnClickListener { onBackPressed() }

        activity_match_record_splash_button.setOnClickListener {
            if (!TextUtils.isEmpty(activity_match_record_splash_name.text.toString().trim()) && !TextUtils.isEmpty(activity_match_record_splash_company.text.toString().trim())) {
                ViewHelper.showOneLineCard(this@MatchRecordSplashActivity, getString(R.string.sure_to_open_match_record), getString(R.string.alright), getString(R.string.cancel), { _, _ ->
                    updateNameAndCompany()
                }, { _, _ -> })
            }
        }

        nameAdapter = QuickMatchCodeNameAdapter(this, ArrayList(), QuickMatchCodeNameAdapter.QuickMatchCodeNameAdapterListener {
            userName = it
            userClick = true
            activity_match_record_splash_name.text = SpannableStringBuilder(it.name)
            activity_match_record_splash_name.setSelection(it.name!!.length)
            activity_match_record_splash_name.dismissDropDown()
        })
        activity_match_record_splash_name.setAdapter(nameAdapter)
        activity_match_record_splash_name.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!TextUtils.isEmpty(s.toString().trim())) {
                    var companyName = ""
                    if (!TextUtils.isEmpty(activity_match_record_splash_company.text.toString().trim())) {
                        companyName = activity_match_record_splash_company.text.toString().trim()
                    }
                    presenter!!.userAndCompanyNameAutoComplete(0, s.toString().trim(), companyName)
                }
                if (!userClick) {
                    userName.name = s.toString().trim()
                    userName.code = ""
                }
            }
        })

        companyAdapter = QuickMatchStringAdapter(this, ArrayList(), QuickMatchStringAdapter.QuickMatchStringAdapterListener {
            activity_match_record_splash_company.text = SpannableStringBuilder(it)
            activity_match_record_splash_company.setSelection(it!!.length)
            activity_match_record_splash_company.dismissDropDown()
        })
        activity_match_record_splash_company.setAdapter(companyAdapter)
        activity_match_record_splash_company.addTextChangedListener(object : TextWatcher {
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
    }

    private fun updateNameAndCompany() {
        showLoadingFragment(R.id.activity_match_record_splash_fl)
        val req = MatchRecordLockRequest()
        req.personId = userName.code
        req.userName = userName.name
        req.companyName = activity_match_record_splash_company.text.toString().trim()
        presenter!!.userNameLock(req)
    }

    override fun userNameLockSuccess(response: StringResponse) {
        removeLoadingFragment()
        if (!TextUtils.isEmpty(response.data)) {
            loginResponse = getLoginResponse(this)
            loginResponse!!.model.userName = userName.name
            loginResponse!!.model.matchRecordPersonId = response.data
            loginResponse!!.model.userWorkMessage.companyName = activity_match_record_splash_company.text.toString().trim()
            saveLoginResponse(this, loginResponse!!)
            startActivity(Intent(this@MatchRecordSplashActivity, MatchRecordActivity::class.java))
            finish()
        }
    }

    override fun userNameLockFail(throwable: Throwable) {
        removeLoadingFragment()
        showThrowable(throwable)
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

    override fun onShowKeyboard(keyboardHeight: Int) {
        activity_match_record_splash_tv_ll.visibility = View.GONE
        activity_match_record_title.visibility = View.GONE
        activity_match_record_splash_name.setTextColor(ContextCompat.getColor(this, R.color.white))
        activity_match_record_splash_name.setHintTextColor(ContextCompat.getColor(this, R.color.white))
        activity_match_record_splash_company.setTextColor(ContextCompat.getColor(this, R.color.white))
        activity_match_record_splash_company.setHintTextColor(ContextCompat.getColor(this, R.color.white))
    }

    override fun onHideKeyboard() {
        activity_match_record_splash_tv_ll.visibility = View.VISIBLE
        activity_match_record_title.visibility = View.VISIBLE
        activity_match_record_splash_name.setTextColor(ContextCompat.getColor(this, R.color.colorBlack))
        activity_match_record_splash_name.setHintTextColor(ContextCompat.getColor(this, R.color.colorBlack))
        activity_match_record_splash_company.setTextColor(ContextCompat.getColor(this, R.color.colorBlack))
        activity_match_record_splash_company.setHintTextColor(ContextCompat.getColor(this, R.color.colorBlack))
    }

}