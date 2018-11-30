package com.buyint.mergerbot.UIs.user.activity

import android.content.Intent
import android.os.Bundle
import com.buyint.mergerbot.R
import com.buyint.mergerbot.Utility.MatchesUtils
import com.buyint.mergerbot.base.BaseActivity
import com.buyint.mergerbot.dto.SmsResponse
import com.buyint.mergerbot.interfaces.ILoginGetSms
import com.buyint.mergerbot.presenter.AccountBindingPresenter
import kotlinx.android.synthetic.main.activity_verification_msg0.*
import kotlinx.android.synthetic.main.toolbar.*

/**
 * Created by huheming on 2018/7/4
 */
class AccountBindingActivity : BaseActivity(), ILoginGetSms {

    override fun loginGetMsmSuccess(response: SmsResponse) {
        removeLoadingFragment()
        if (response.data.phoneNumberExit) {
            val intent = Intent(this@AccountBindingActivity, AccountBinding2Activity::class.java)
            intent.putExtra(getString(R.string.DATA), activity_verification_msg0_et.text.toString().trim())
            startActivityForResult(intent, 1002)
        } else {
            showToast(getString(R.string.please_register))
        }
    }

    override fun loginGetMsmFail(throwable: Throwable) {
        showThrowable(throwable)
        removeLoadingFragment()
    }

    private var presenter: AccountBindingPresenter? = null
    private var type: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        setMyTitleColor()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verification_msg0)

        presenter = AccountBindingPresenter(this)

        toorbar_title.text = getString(R.string.account_connection)
        toolbar_back.setOnClickListener { onBackPressed() }
        val drawable = resources.getDrawable(R.mipmap.duigou)
        drawable.setBounds(0, 0, dp2px(16f), dp2px(16f))
        toolbar_right.setCompoundDrawables(null, null, drawable, null)
        toolbar_right_rl.setOnClickListener {
            if (type == getString(R.string.TYPE_0)) {
                //绑定电话
                if (MatchesUtils.validatePhoneNumber(activity_verification_msg0_et.text.toString().trim())) {
                    showLoadingFragment(R.id.activity_verification_msg0_fl)
                    presenter!!.accountBindingGetSms(getString(R.string.TYPE_0), activity_verification_msg0_et.text.toString().trim())
                }
            } else if (type == getString(R.string.TYPE_1)) {
                //绑定邮箱
                if (MatchesUtils.validateEmail(activity_verification_msg0_et.text.toString().trim())) {
                    showLoadingFragment(R.id.activity_verification_msg0_fl)
                    presenter!!.accountBindingGetSms(getString(R.string.TYPE_1), activity_verification_msg0_et.text.toString().trim())
                }
            }
        }
        type = intent.getStringExtra(getString(R.string.TYPE))
    }

    override fun onDestroy() {
        if (presenter != null) {
            presenter!!.destory()
            presenter = null
        }
        super.onDestroy()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 1002 && resultCode == 1003) {
            setResult(999)
            finish()
        }
    }
}