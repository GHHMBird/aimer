package com.buyint.mergerbot.UIs.matchRecord.activity

import android.graphics.Bitmap
import android.os.Bundle
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.BitmapImageViewTarget
import com.buyint.mergerbot.R
import com.buyint.mergerbot.UIs.matchRecord.adapter.ScanQRcodeAdapter
import com.buyint.mergerbot.base.BaseActivity
import com.buyint.mergerbot.dto.GetFriendListResponse
import com.buyint.mergerbot.interfaces.IGetFriendList
import com.buyint.mergerbot.interfaces.ISendQRString
import com.buyint.mergerbot.presenter.ScanQRcodePresenter
import kotlinx.android.synthetic.main.activity_scan_qr_code.*
import kotlinx.android.synthetic.main.item_search_person.*
import kotlinx.android.synthetic.main.toolbar_left.*

/**
 * Created by huheming on 2018/9/3
 */
class ScanQRcodeActivity : BaseActivity(), ISendQRString, IGetFriendList {

    private var presenter: ScanQRcodePresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        presenter = ScanQRcodePresenter(this, this)

        setMyTitleColor()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan_qr_code)

        toolbar_left_title.text = getString(R.string.match_result)
        toolbar_left_left_back.setOnClickListener { onBackPressed() }

        val result = intent.getStringExtra(getString(R.string.DATA))

        item_search_person_add.setOnClickListener {
            showLoadingFragment(R.id.activity_scan_qr_code_fl)
            presenter!!.sendQRString(result)
        }

        showLoadingFragment(R.id.activity_scan_qr_code_fl)
        presenter!!.getFriendList(result)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (presenter != null) {
            presenter!!.destory()
            presenter = null
        }
    }

    override fun sendQRStringSuccess(boolean: Boolean) {
        removeLoadingFragment()
        if (boolean) {
            showToast(getString(R.string.add_success))
            finish()
        } else {
            showToast(getString(R.string.add_fail))
        }
    }

    override fun sendQRStringFail(throwable: Throwable) {
        showThrowable(throwable)
        removeLoadingFragment()
    }

    override fun getFriendListSuccess(response: GetFriendListResponse) {
        removeLoadingFragment()
        Glide.with(this).load(response.avatars).asBitmap().centerCrop().error(R.mipmap.default_user).placeholder(R.mipmap.default_user).into(object : BitmapImageViewTarget(item_search_person_iv) {
            override fun setResource(resource: Bitmap) {
                val drawable = RoundedBitmapDrawableFactory.create(resources, resource)
                drawable.isCircular = true
                item_search_person_iv.setImageDrawable(drawable)
            }
        })
        item_search_person_name.text = response.name
        if (TextUtils.isEmpty(response.companyName)) {
            response.companyName = getString(R.string.undisclosed_company)
        }
        if (TextUtils.isEmpty(response.position)) {
            response.position = getString(R.string.undisclosed_position)
        }
        item_search_person_company.text = response.companyName + " | " + response.position
        if (response.profileList != null && response.profileList!!.size > 0) {
            activity_scan_qr_code_tv.text = getString(R.string.match_success)
            activity_scan_qr_code_recycler.layoutManager = LinearLayoutManager(this)
            activity_scan_qr_code_recycler.adapter = ScanQRcodeAdapter(this, response.profileList!!)
        } else {
            activity_scan_qr_code_tv.text = getString(R.string.match_fail)
            activity_scan_qr_code_image.setImageResource(R.mipmap.activity_match_record_splash)
        }
    }

    override fun getFriendListFail(throwable: Throwable) {
        activity_scan_qr_code_tv.text = getString(R.string.match_fail)
        activity_scan_qr_code_image.setImageResource(R.mipmap.activity_match_record_splash)
        showThrowable(throwable)
        removeLoadingFragment()
    }
}