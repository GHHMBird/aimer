package com.buyint.mergerbot.UIs.user.activity

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import com.afollestad.materialdialogs.MaterialDialog
import com.buyint.mergerbot.R
import com.buyint.mergerbot.base.BaseActivity
import com.buyint.mergerbot.database.getLoginResponse
import com.buyint.mergerbot.dto.BooleanResponse
import com.buyint.mergerbot.dto.PasswordChangePasswordRequest
import com.buyint.mergerbot.dto.SmsResponse
import com.buyint.mergerbot.interfaces.ILoginGetSms
import com.buyint.mergerbot.interfaces.IPasswordChangePassword
import com.buyint.mergerbot.presenter.SetPasswordPresenter
import kotlinx.android.synthetic.main.activity_set_password.*
import kotlinx.android.synthetic.main.toolbar.*

/**
 * Created by huheming on 2018/7/2
 */
class SetPasswordActivity : BaseActivity(), IPasswordChangePassword, ILoginGetSms {
    override fun loginGetMsmSuccess(response: SmsResponse) {
        removeLoadingFragment()
        startActivityForResult(Intent(this@SetPasswordActivity, VerificationSetPasswordActivity::class.java), 10085)
    }

    override fun loginGetMsmFail(throwable: Throwable) {
        removeLoadingFragment()
        showThrowable(throwable)
    }

    override fun passwordChangePasswordSuccess(booleanResponse: BooleanResponse) {
        if (booleanResponse.data) {
            removeLoadingFragment()
            showToast(getString(R.string.password_reset_success))
            finish()
        }
    }

    override fun passwordChangePasswordFail(throwable: Throwable) {
        removeLoadingFragment()
        showThrowable(throwable)
    }

    var phone: String? = null
    private var presenter: SetPasswordPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        setMyTitleColor()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_password)

        presenter = SetPasswordPresenter(this, this)



        toorbar_title.text = getString(R.string.set_password)
        toolbar_back.setOnClickListener { onBackPressed() }
        val drawable = resources.getDrawable(R.mipmap.duigou)
        drawable.setBounds(0, 0, dp2px(16f), dp2px(16f))
        toolbar_right.setCompoundDrawables(null, null, drawable, null)
        toolbar_right_rl.setOnClickListener {
            if (TextUtils.isEmpty(activity_set_password_old_password.text.toString().trim()) || TextUtils.isEmpty(activity_set_password_new_password.text.toString().trim()) || TextUtils.isEmpty(activity_set_password_new_password2.text.toString().trim())) {
            } else if (activity_set_password_new_password.text.toString().trim() != activity_set_password_new_password2.text.toString().trim()) {
            } else {
                showLoadingFragment(R.id.activity_set_password_fl)
                update()
            }
        }

        activity_set_password_forget.setOnClickListener { sendMessageDialog() }

        val response = getLoginResponse(this)
        phone = response!!.model.loginCount
        activity_set_password_account.text = response.model.phone
    }

    private fun sendMessageDialog() {
        val builder = MaterialDialog.Builder(this)
        builder.positiveText(R.string.send)
        builder.negativeText(R.string.cancel)
        builder.positiveColorRes(R.color.color_1f0398)
        builder.negativeColorRes(R.color.color_999999)
        builder.content(getString(R.string.sure_to_send_verification_code) + phone)
        builder.onNegative { dialog, _ ->
            dialog.dismiss()
        }
        builder.onPositive { dialog, _ ->
            dialog.dismiss()
            showLoadingFragment(R.id.activity_set_password_fl)
            //判断是电话还是邮箱
            val poa: String = if (phone!!.contains("@")) {
                //邮箱
                getString(R.string.TYPE_1)
            } else {
                //电话
                getString(R.string.TYPE_0)
            }
            presenter!!.loginGetSms(poa, phone!!)
        }
        builder.build().show()
    }

    fun update() {
        val request = PasswordChangePasswordRequest()
        request.account = phone
        request.oldPassword = activity_set_password_old_password.text.toString().trim()
        request.password = activity_set_password_new_password.text.toString().trim()
        presenter!!.passwordChangePassword(request)
    }

    override fun onDestroy() {
        if (presenter != null) {
            presenter!!.destory()
            presenter = null
        }
        super.onDestroy()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 10085 && resultCode == 10086) {
            finish()
        }
    }
}