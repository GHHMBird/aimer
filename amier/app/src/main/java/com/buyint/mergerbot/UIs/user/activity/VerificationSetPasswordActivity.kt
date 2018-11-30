package com.buyint.mergerbot.UIs.user.activity

import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.v4.content.ContextCompat
import android.text.TextUtils
import com.buyint.mergerbot.R
import com.buyint.mergerbot.base.BaseActivity
import com.buyint.mergerbot.database.getLoginResponse
import com.buyint.mergerbot.dto.BooleanResponse
import com.buyint.mergerbot.dto.SmsResponse
import com.buyint.mergerbot.dto.VerifyCodeChangePasswordRequest
import com.buyint.mergerbot.interfaces.ILoginGetSms
import com.buyint.mergerbot.interfaces.IVerifycodeChangePassword
import com.buyint.mergerbot.presenter.VerificationSetPasswordPresenter
import kotlinx.android.synthetic.main.activity_verification_set_password.*
import kotlinx.android.synthetic.main.toolbar.*

/**
 * Created by huheming on 2018/7/2
 */
class VerificationSetPasswordActivity : BaseActivity(), ILoginGetSms, IVerifycodeChangePassword {
    override fun verifycodeChangePasswordSuccess(booleanResponse: BooleanResponse) {
        removeLoadingFragment()
        showToast(getString(R.string.password_reset_success))
        setResult(10086)
        finish()
    }

    override fun verifycodeChangePasswordFail(throwable: Throwable) {
        removeLoadingFragment()
        showThrowable(throwable)
    }

    override fun loginGetMsmFail(throwable: Throwable) {
        removeLoadingFragment()
        showThrowable(throwable)
    }

    override fun loginGetMsmSuccess(response: SmsResponse) {
        removeLoadingFragment()
        canClick = false
        handler.sendEmptyMessage(60)
    }

    private var canClick = false
    private val presenter = VerificationSetPasswordPresenter(this, this)
    private var phone: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        setMyTitleColor()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verification_set_password)

        activity_verification_set_password_new_password.typeface = activity_verification_set_password_verifycode.typeface
        activity_verification_set_password_new_password2.typeface = activity_verification_set_password_verifycode.typeface

        toolbar_back.setOnClickListener { onBackPressed() }
        toorbar_title.text = getString(R.string.set_password)
        val drawable = resources.getDrawable(R.mipmap.duigou)
        drawable.setBounds(0, 0, dp2px(16f), dp2px(16f))
        toolbar_right.setCompoundDrawables(null, null, drawable, null)
        toolbar_right_rl.setOnClickListener {
            if (TextUtils.isEmpty(activity_verification_set_password_verifycode.text.toString().trim()) || TextUtils.isEmpty(activity_verification_set_password_new_password.text.toString().trim()) || TextUtils.isEmpty(activity_verification_set_password_new_password2.text.toString().trim())) {
            } else if (activity_verification_set_password_new_password.text.toString().trim() != activity_verification_set_password_new_password2.text.toString().trim()) {
            } else {
                showLoadingFragment(R.id.activity_verification_set_password_fl)
                update()
            }
        }

        activity_verification_set_password_get_sms.setOnClickListener {
            if (canClick) {
                showLoadingFragment(R.id.activity_verification_set_password_fl)
                //判断是电话还是邮箱
                val poa: String = if (phone!!.contains("@")) {
                    //邮箱
                    getString(R.string.TYPE_1)
                } else {
                    //电话
                    getString(R.string.TYPE_0)
                }
                presenter.loginGetSms(poa, phone!!)
            }
        }

        val response = getLoginResponse(this)
        phone = response!!.model.loginCount
        activity_verification_set_password_account.text = response.model.phone

        handler.sendEmptyMessage(60)
    }

    fun update() {
        val request = VerifyCodeChangePasswordRequest()
        request.account = phone
        request.password = activity_verification_set_password_new_password.text.toString().trim()
        request.verificationCode = activity_verification_set_password_verifycode.text.toString().trim()
        presenter.verifycodeChangePassword(request)
    }

    internal var handler: Handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            if (msg.what != 0) {
                activity_verification_set_password_get_sms.setTextColor(ContextCompat.getColor(this@VerificationSetPasswordActivity, R.color.color_b3b3b3))
                activity_verification_set_password_get_sms.text = getString(R.string.after_can_resend_before) + msg.what.toString() + getString(R.string.after_can_resend_after)
                this.sendEmptyMessageDelayed(msg.what - 1, 1000)
            } else {
                canClick = true
                activity_verification_set_password_get_sms.setTextColor(ContextCompat.getColor(this@VerificationSetPasswordActivity, R.color.color_959dce))
                activity_verification_set_password_get_sms.text = resources.getString(R.string.resend)
            }
        }
    }

    override fun onDestroy() {
        presenter.destory()
        super.onDestroy()
    }
}