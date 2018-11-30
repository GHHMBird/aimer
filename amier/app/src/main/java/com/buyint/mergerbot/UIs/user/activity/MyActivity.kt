package com.buyint.mergerbot.UIs.user.activity

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory
import android.text.TextUtils
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.BitmapImageViewTarget
import com.buyint.mergerbot.R
import com.buyint.mergerbot.UIs.verification.activity.BusinessIdentityVerificationActivity
import com.buyint.mergerbot.UIs.verification.activity.BusinessIdentityVerificationSuccessActivity
import com.buyint.mergerbot.base.BaseActivity
import com.buyint.mergerbot.bus.annotation.BusReceiver
import com.buyint.mergerbot.database.getLoginResponse
import com.buyint.mergerbot.enums.ShowNameType
import kotlinx.android.synthetic.main.activity_mine_infor.*

/**
 * created by licheng  on date 2018/8/7
 * 我的账户页面
 */
class MyActivity : BaseActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setMyTitleColor()
        setContentView(R.layout.activity_mine_infor)
        tv_mine_title.text = getString(R.string.my_account)
        iv_mine_edit.setOnClickListener(this)
        ll_mine_account.setOnClickListener(this)
        ll_mine_power.setOnClickListener(this)
        activity_my_assets.setOnClickListener(this)
        activity_my_reliable_add.setOnClickListener(this)

        ll_mine_verification.setOnClickListener(this)
        iv_back.setOnClickListener(this)

        activity_my_money_tv.setPostfixString("B")
        activity_my_money_tv.setNumberString("10000", "0")
        activity_my_reliable_tv.setNumberString("360", "210")

        initData()
    }

    private fun initData() {
        val loginResponse = getLoginResponse(this)
        Glide.with(this).load(loginResponse!!.model.avatars).asBitmap().centerCrop().error(R.mipmap.default_user).placeholder(R.mipmap.default_user).into(object : BitmapImageViewTarget(activity_my2_user_icon_iv) {
            override fun setResource(resource: Bitmap?) {
                val rbd = RoundedBitmapDrawableFactory.create(resources, resource)
                rbd.isCircular = true
                activity_my2_user_icon_iv.setImageDrawable(rbd)
            }
        })
        if (loginResponse.model.userPrivacySetting == null) {
            if (TextUtils.isEmpty(loginResponse.model.englishName)) {
                tv_name.text = getString(R.string.unknown_user)
            } else {
                tv_name.text = loginResponse.model.englishName
            }
        } else {
            when (loginResponse.model.userPrivacySetting.showNameType) {
                ShowNameType.REALNAME.name -> {
                    if (TextUtils.isEmpty(loginResponse.model.userName)) {
                        tv_name.text = getString(R.string.unknown_user)
                    } else {
                        tv_name.text = loginResponse.model.userName
                    }
                }
                ShowNameType.NICKNAME.name -> {
                    if (TextUtils.isEmpty(loginResponse.model.englishName)) {
                        tv_name.text = getString(R.string.unknown_user)
                    } else {
                        tv_name.text = loginResponse.model.englishName
                    }
                }
                ShowNameType.NAMDE.name -> {
                    if (TextUtils.isEmpty(loginResponse.model.userName)) {
                        tv_name.text = getString(R.string.unknown_user)
                    } else {
                        tv_name.text = getString(R.string.sir_or_miss_2_start) + loginResponse!!.model.userName.substring(0, 1) + getString(R.string.sir_or_miss_2_end)
                    }
                }
            }
        }

        if (loginResponse.model.userWorkMessage == null) {
            tv_company.text = getString(R.string.undisclosed_company)
        } else {
            var companyName = loginResponse.model.userWorkMessage.companyName
            if (TextUtils.isEmpty(companyName)) {
                companyName = getString(R.string.undisclosed_company)
            }
            tv_company.text = companyName
        }

        if (loginResponse.model.userWorkMessage == null) {
            tv_job.text = getString(R.string.undisclosed_position)
        } else {
            var position = loginResponse.model.userWorkMessage.position
            if (TextUtils.isEmpty(position)) {
                position = getString(R.string.undisclosed_position)
            }
            tv_job.text = position
        }

        if (loginResponse.model.authentication) {
            activity_mine_infor_verification_iv.visibility = View.GONE
        }

    }

    override fun onClick(v: View?) {

        when (v) {

            iv_mine_edit -> {
                val intent = Intent(this@MyActivity, MyDetailActivity::class.java)
                startActivity(intent)

            }
            ll_mine_account -> {
                val intent = Intent(this@MyActivity, AccountManagementActivity::class.java)
                startActivity(intent)
            }

        //授权管理
            ll_mine_power -> {
                startActivity(Intent(this@MyActivity, AuthorizationManagementListActivity::class.java))
            }

        //身份认证
            ll_mine_verification -> {
                val loginResponse = getLoginResponse(this)
                if (loginResponse!!.model.authentication) {
                    val intent = Intent(this@MyActivity, BusinessIdentityVerificationSuccessActivity::class.java)
                    startActivity(intent)
                } else {
                    val intent = Intent(this@MyActivity, BusinessIdentityVerificationActivity::class.java)
                    startActivity(intent)
                }
            }

            activity_my_assets -> {
                showToast(getString(R.string.by_comon_no_action))
            }
            activity_my_reliable_add -> {
                showToast(getString(R.string.by_comon_no_action))
            }
            iv_back -> {
                finish()
            }
        }
    }

    @BusReceiver
    fun StringEvent(event: String) {
        if (event == getString(R.string.USER_ICON_CHANGE)) {
            initData()
        }
    }
}
