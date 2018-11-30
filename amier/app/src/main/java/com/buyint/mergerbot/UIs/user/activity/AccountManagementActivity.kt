package com.buyint.mergerbot.UIs.user.activity

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import com.buyint.mergerbot.R
import com.buyint.mergerbot.Utility.LinkedInUtils
import com.buyint.mergerbot.base.BaseActivity
import com.buyint.mergerbot.database.getLoginResponse
import com.buyint.mergerbot.database.saveLoginResponse
import com.buyint.mergerbot.dto.AccountMergerRequest
import com.buyint.mergerbot.dto.LinkedinRequest
import com.buyint.mergerbot.dto.LoginResponse
import com.buyint.mergerbot.interfaces.IAccountMerger
import com.buyint.mergerbot.presenter.AccountManagementPresenter
import com.linkedin.platform.LISessionManager
import kotlinx.android.synthetic.main.activity_account_manager.*
import kotlinx.android.synthetic.main.toolbar.*

/**
 * Created by huheming on 2018/7/2
 */
class AccountManagementActivity : BaseActivity(), IAccountMerger {
    override fun accountMergerSuccess(response: LoginResponse) {
        saveLoginResponse(this, response)
        initData()
        removeLoadingFragment()
    }

    override fun accountMergerFail(throwable: Throwable) {
        showThrowable(throwable)
        removeLoadingFragment()
    }

    private var phoneClick = true
    private var emailClick = true
    private var linkedInClick = true
    private var presenter: AccountManagementPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        setMyTitleColor()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_manager)

        presenter = AccountManagementPresenter(this)

        toolbar_back.setOnClickListener { onBackPressed() }
        toorbar_title.text = getString(R.string.account_management)

        activity_account_manager_set_password.setOnClickListener {
            val intent = Intent(this@AccountManagementActivity, SetPasswordActivity::class.java)
            intent.putExtra(getString(R.string.TYPE), getString(R.string.TYPE_1))
            startActivity(intent)
        }

        activity_account_manager_linkedin_ll.setOnClickListener {
            if (linkedInClick) {
                showLoadingFragment(R.id.activity_account_manager_fl)
                startLinkedIn()
            }
        }

        activity_account_manager_email_ll.setOnClickListener {
            if (emailClick) {
                val intent = Intent(this@AccountManagementActivity, AccountBindingActivity::class.java)
                intent.putExtra(getString(R.string.TYPE), getString(R.string.TYPE_1))
                startActivityForResult(intent, 1001)
            }
        }

        activity_account_manager_phone_ll.setOnClickListener {
            if (phoneClick) {
                val intent = Intent(this@AccountManagementActivity, AccountBindingActivity::class.java)
                intent.putExtra(getString(R.string.TYPE), getString(R.string.TYPE_0))
                startActivityForResult(intent, 1000)
            }
        }

        initData()
    }

    private fun initData() {
        val response = getLoginResponse(this)
        if (!TextUtils.isEmpty(response!!.model.phone)) {
            phoneClick = false
            activity_account_manager_phone.text = response.model.phone
            activity_account_manager_phone_arrow.visibility = View.GONE
        } else {
            phoneClick = true
            activity_account_manager_phone.text = null
            activity_account_manager_phone_arrow.visibility = View.VISIBLE
        }
        if (response.model.email != null && response.model.email.size > 0) {
            emailClick = false
            activity_account_manager_email.text = response.model.email[0]
            activity_account_manager_email_arrow.visibility = View.GONE
        } else {
            emailClick = true
            activity_account_manager_email.text = null
            activity_account_manager_email_arrow.visibility = View.VISIBLE
        }
        if (!TextUtils.isEmpty(response.model.linkedInId)) {
            linkedInClick = false
            activity_account_manager_linkedin.text = response.model.linkedInInfo.formattedName
            activity_account_manager_linkedin_arrow.visibility = View.GONE
        } else {
            linkedInClick = true
            activity_account_manager_linkedin.text = null
            activity_account_manager_linkedin_arrow.visibility = View.VISIBLE
        }
    }

    private fun startLinkedIn() {
        LinkedInUtils.linkedinLogin(this, object : LinkedInUtils.LinkedinUtilsListener {
            override fun success(req: LinkedinRequest?) {
                val request = AccountMergerRequest()
                request.accessToken = req!!.accessToken
                request.mergedAccount = req!!.linkedInId
                request.mergeType = getString(R.string.LINKEDIN)
                presenter!!.accountMerger(request)
            }

            override fun fail() {
                removeLoadingFragment()
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 1000 && resultCode == 999) {
            initData()
        } else if (requestCode == 1001 && resultCode == 999) {
            initData()
        }
        LISessionManager.getInstance(this).onActivityResult(this, requestCode, resultCode, data)
    }

    override fun onDestroy() {
        if (presenter != null) {
            presenter!!.destory()
            presenter = null
        }
        super.onDestroy()
    }
}