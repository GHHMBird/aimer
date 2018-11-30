package com.buyint.mergerbot.UIs.matchRecord.activity

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.text.TextUtils
import com.bumptech.glide.Glide
import com.buyint.mergerbot.R
import com.buyint.mergerbot.UIs.verification.activity.VerificationPromptActivity
import com.buyint.mergerbot.Utility.GlideCircleTransform
import com.buyint.mergerbot.base.BaseActivity
import com.buyint.mergerbot.database.getLoginResponse
import com.buyint.mergerbot.dto.LoginResponse
import com.buyint.mergerbot.enums.ShowNameType
import com.buyint.mergerbot.interfaces.IGetQRString
import com.buyint.mergerbot.presenter.MyQRcodePresenter
import com.uuzuche.lib_zxing.activity.CodeUtils
import kotlinx.android.synthetic.main.activity_my_qrcode.*
import kotlinx.android.synthetic.main.toolbar_left.*

/**
 * Created by huheming on 2018/8/20
 */
class MyQRcodeActivity : BaseActivity(), IGetQRString {

    private var presenter: MyQRcodePresenter? = null
    private var response: LoginResponse? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        presenter = MyQRcodePresenter(this)
        setMyTitleColor()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_qrcode)

        toolbar_left_left_back.setOnClickListener { onBackPressed() }
        toolbar_left_title.text = getString(R.string.my_qr_code)

        response = getLoginResponse(this)

        Glide.with(this).load(response!!.model.avatars).transform(GlideCircleTransform(this)).placeholder(R.mipmap.default_user).into(activity_my_qrcode_iv)

        if (response!!.model.userPrivacySetting == null) {
            if (TextUtils.isEmpty(response!!.model.englishName)) {
                activity_my_qrcode_name.text = getString(R.string.unknown_user)
            } else {
                activity_my_qrcode_name.text = response!!.model.englishName
            }
        } else {
            when (response!!.model.userPrivacySetting.showNameType) {
                ShowNameType.REALNAME.name -> {
                    if (TextUtils.isEmpty(response!!.model.userName)) {
                        activity_my_qrcode_name.text = getString(R.string.unknown_user)
                    } else {
                        activity_my_qrcode_name.text = response!!.model.userName
                    }
                }
                ShowNameType.NICKNAME.name -> {
                    if (TextUtils.isEmpty(response!!.model.englishName)) {
                        activity_my_qrcode_name.text = getString(R.string.unknown_user)
                    } else {
                        activity_my_qrcode_name.text = response!!.model.englishName
                    }
                }
                ShowNameType.NAMDE.name -> {
                    if (TextUtils.isEmpty(response!!.model.userName)) {
                        activity_my_qrcode_name.text = getString(R.string.unknown_user)
                    } else {
                        activity_my_qrcode_name.text = getString(R.string.sir_or_miss_2_start) + response!!.model.userName.substring(0, 1) + getString(R.string.sir_or_miss_2_end)
                    }
                }
            }
        }

        if (response!!.model.userWorkMessage == null) {
            activity_my_qrcode_company.text = getString(R.string.undisclosed_company) + "|" + getString(R.string.undisclosed_position)
        } else {
            var companyName = response!!.model.userWorkMessage.companyName
            if (TextUtils.isEmpty(companyName)) {
                companyName = getString(R.string.undisclosed_company)
            }
            var position = response!!.model.userWorkMessage.position
            if (TextUtils.isEmpty(position)) {
                position = getString(R.string.undisclosed_position)
            }
            activity_my_qrcode_company.text = "$companyName|$position"
        }

        if (response!!.model.authentication) {
            activity_my_qrcode_renzheng.setImageResource(R.mipmap.renzheng)
        } else {
            activity_my_qrcode_renzheng.setImageResource(R.mipmap.unrenzheng)
            activity_my_qrcode_renzheng.setOnClickListener {
                startActivity(Intent(this, VerificationPromptActivity::class.java))
            }
        }

       showLoadingFragment(R.id.activity_my_qrcode_fl)
        presenter!!.getQRString()
    }

    override fun getQRStringSuccess(text: String) {
        val image = CodeUtils.createImage(text, 400, 400, BitmapFactory.decodeResource(resources, R.mipmap.qr_logo))
        activity_my_qrcode_qr.setImageBitmap(image)
        removeLoadingFragment()
    }

    override fun getQRStringFail(throwable: Throwable) {
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
}