package com.buyint.mergerbot.UIs.verification.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.v4.content.ContextCompat
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.buyint.mergerbot.R
import com.buyint.mergerbot.base.BaseActivity
import com.buyint.mergerbot.database.getLoginResponse
import com.buyint.mergerbot.database.saveLoginResponse
import com.buyint.mergerbot.dto.*
import com.buyint.mergerbot.interfaces.IAccountVerificationGetMessage
import com.buyint.mergerbot.interfaces.IAccountVerificationVerifyMessage
import com.buyint.mergerbot.presenter.VerificationVerificaionCodePresenter
import kotlinx.android.synthetic.main.activity_verification_verification_code.*
import kotlinx.android.synthetic.main.toolbar_white.*

/**
 * Created by huheming on 2018/8/2
 */
class VerificationVerificaionCodeActivity : BaseActivity(), IAccountVerificationVerifyMessage, IAccountVerificationGetMessage {

    private var canClick = false
    private var presenter: VerificationVerificaionCodePresenter? = null
    private var lawyerAndUser: Boolean? = null
    private var amiAccessBean: AmiAccessBean? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        presenter = VerificationVerificaionCodePresenter(this, this)
        setTitleWhiteAndTextBlank()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verification_verification_code)

        amiAccessBean = intent.getSerializableExtra("amiAccessBean") as AmiAccessBean
        lawyerAndUser = intent.getBooleanExtra("lawyerAndUser", false)

        toolbar_white_top.visibility = View.GONE
        toolbar_white_right_add_rl.visibility = View.GONE
        toolbar_white_right_search_rl.visibility = View.GONE
        toolbar_white_title.text = getString(R.string.verify_code)
        toolbar_white_back.setOnClickListener { onBackPressed() }

        val request = intent.getSerializableExtra(getString(R.string.TYPE)) as AccountVerificationGetMessageRequest

        activity_verification_verification_code_resend.setOnClickListener {
            if (canClick) {
                canClick = false
                presenter!!.accountVerificationGetMessage(request)
            }
        }

        activity_verification_verification_code_et.addTextChangedListener(
                object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {
                    }

                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                    }

                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                        if (activity_verification_verification_code_et.text.toString().trim().length == 6) {
                            val request = AccountVerificationVerifyMessageRequest()
                            request.id = intent.getStringExtra(getString(R.string.DATA))
                            request.code = activity_verification_verification_code_et.text.toString().trim()
                            presenter!!.accountVerificationVerifyMessage(request)
                        }
                    }
                })

        handler.sendEmptyMessage(60)
    }

    internal var handler: Handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)

            if (msg.what != 0) {
                this.sendEmptyMessageDelayed(msg.what - 1, 1000)
                activity_verification_verification_code_resend.setTextColor(ContextCompat.getColor(this@VerificationVerificaionCodeActivity, R.color.color_999999))
                activity_verification_verification_code_resend.setBackgroundResource(R.drawable.md_transparent)
            } else {
                activity_verification_verification_code_resend.setTextColor(ContextCompat.getColor(this@VerificationVerificaionCodeActivity, R.color.color_1f0398))
                activity_verification_verification_code_resend.setBackgroundResource(R.drawable.tx_bg_blue_reverse)
                canClick = true
            }
            activity_verification_verification_code_time.text = msg.what.toString() + "s"
        }

    }

    override fun accountVerificationVerifyMessageSuccess(response: BooleanResponse) {
        setResult(1001)
        val intent = Intent()
        if (response.data) {
            val loginResponse = getLoginResponse(this)
            loginResponse!!.model.authentication = true
            saveLoginResponse(this, loginResponse)
            intent.setClass(this@VerificationVerificaionCodeActivity, BusinessIdentityVerificationSuccessActivity::class.java)
            startActivity(intent)
        } else {
            intent.setClass(this@VerificationVerificaionCodeActivity, BusinessIdentityVerificationFailActivity::class.java)
            startActivity(intent)
        }
        finish()
    }

    override fun accountVerificationVerifyMessageFail(throwable: Throwable) {

        if (lawyerAndUser!!) {

            val intent = Intent(this@VerificationVerificaionCodeActivity, BusinessIdentityVerificationInviteLawyerActivity::class.java)
            intent.putExtra("amiAccessBean", amiAccessBean)
            startActivity(intent)

        } else {
            showThrowable(throwable)

        }
    }

    override fun accountVerificationGetMessageSuccess(response: AccountVerificationGetMessageResponse) {
        handler.sendEmptyMessageDelayed(60, 1000)
    }

    override fun accountVerificationGetMessageFail(throwable: Throwable) {
        showThrowable(throwable)
        canClick = false
    }

    override fun onDestroy() {
        super.onDestroy()
        if (presenter != null) {
            presenter!!.destory()
            presenter = null
        }
    }

}