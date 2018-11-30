package com.buyint.mergerbot.UIs.loginAndRegister.activity

import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.v4.content.ContextCompat
import android.text.TextUtils
import com.buyint.mergerbot.R
import com.buyint.mergerbot.Utility.MatchesUtils
import com.buyint.mergerbot.base.BaseActivity
import com.buyint.mergerbot.dto.BooleanResponse
import com.buyint.mergerbot.dto.SmsResponse
import com.buyint.mergerbot.dto.VerifyCodeChangePasswordRequest
import com.buyint.mergerbot.interfaces.ILoginGetSms
import com.buyint.mergerbot.interfaces.IVerifycodeChangePassword
import com.buyint.mergerbot.presenter.ForgetPasswordPresenter
import com.buyint.mergerbot.view.SelectableBackgroundView
import kotlinx.android.synthetic.main.activity_forget_password.*
import kotlinx.android.synthetic.main.toolbar.*

/**
 * Created by huheming on 2018/7/4
 */
class ForgetPasswordActivity : BaseActivity(), ILoginGetSms, IVerifycodeChangePassword {

    private var canClick = true
    private var phoneOrPassword: String? = null
    private var poa: String? = null
    private var presenter: ForgetPasswordPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        setMyTitleColor()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forget_password)

        presenter = ForgetPasswordPresenter(this, this)

        activity_forget_password_password.typeface = activity_forget_password_account.typeface
        activity_forget_password_new_password.typeface = activity_forget_password_account.typeface

        toolbar_back.setOnClickListener { onBackPressed() }
        toorbar_title.text = getString(R.string.find_password)

        activity_forget_password_button.setListener(object : SelectableBackgroundView.SelectableBackgroundViewListener {
            override fun finished() {
                if (!TextUtils.isEmpty(activity_forget_password_account.text.toString().trim()) && !TextUtils.isEmpty(activity_forget_password_verify_code.text.toString().trim()) && !TextUtils.isEmpty(activity_forget_password_password.text.toString().trim()) && !TextUtils.isEmpty(activity_forget_password_new_password.text.toString().trim())) {
                    if(activity_forget_password_password.text.toString().trim() != activity_forget_password_new_password.text.toString().trim()){
                        showToast(getString(R.string.twice_password_difference))
                        return
                    }
                    showLoadingFragment(R.id.activity_forget_password_fl)
                    val request = VerifyCodeChangePasswordRequest()
                    request.verificationCode = activity_forget_password_verify_code.text.toString().trim()
                    request.account = activity_forget_password_account.text.toString().trim()
                    request.password = activity_forget_password_password.text.toString().trim()
                    presenter!!.verifycodeChangePassword(request)
                }
            }
        })

        activity_forget_password_get_verify_code.setOnClickListener {
            if (canClick) {
                if (MatchesUtils.validatePhoneNumber(activity_forget_password_account.text.toString()) || MatchesUtils.validateEmail(activity_forget_password_account.text.toString())) {
                    phoneOrPassword = activity_forget_password_account.text.toString().trim()
                    poa = if (phoneOrPassword!!.contains("@")) {
                        getString(R.string.TYPE_1)
                    } else {
                        getString(R.string.TYPE_0)
                    }
                    showLoadingFragment(R.id.activity_forget_password_fl)
                    presenter!!.accountBindingGetSms(poa!!, phoneOrPassword!!)
                }
            }
        }
    }

    internal var handler: Handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            if (msg.what != 0) {
                activity_forget_password_get_verify_code.setTextColor(ContextCompat.getColor(this@ForgetPasswordActivity, R.color.color_b3b3b3))
                activity_forget_password_get_verify_code.background = ContextCompat.getDrawable(this@ForgetPasswordActivity, R.drawable.tx_bg_gray2)
                activity_forget_password_get_verify_code.text = getString(R.string.after_can_resend_before) + msg.what.toString() + getString(R.string.after_can_resend_after)
                this.sendEmptyMessageDelayed(msg.what - 1, 1000)
            } else {
                canClick = true
                activity_forget_password_get_verify_code.setTextColor(ContextCompat.getColor(this@ForgetPasswordActivity, R.color.color_959dce))
                activity_forget_password_get_verify_code.background = ContextCompat.getDrawable(this@ForgetPasswordActivity, R.drawable.tx_bg_blue)
                activity_forget_password_get_verify_code.text = resources.getString(R.string.resend)
            }
        }
    }

    override fun loginGetMsmSuccess(response: SmsResponse) {
        removeLoadingFragment()
        canClick = false
        handler.sendEmptyMessage(60)
    }

    override fun loginGetMsmFail(throwable: Throwable) {
        showThrowable(throwable)
        removeLoadingFragment()
    }

    override fun verifycodeChangePasswordSuccess(booleanResponse: BooleanResponse) {
        showToast(getString(R.string.password_reset_success))
        finish()
        removeLoadingFragment()
    }

    override fun verifycodeChangePasswordFail(throwable: Throwable) {
        showThrowable(throwable)
        removeLoadingFragment()
    }
}