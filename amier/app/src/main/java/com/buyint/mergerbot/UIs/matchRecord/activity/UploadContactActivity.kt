package com.buyint.mergerbot.UIs.matchRecord.activity

import android.Manifest
import android.os.Bundle
import android.view.View
import com.buyint.mergerbot.R
import com.buyint.mergerbot.Utility.ContactUtil
import com.buyint.mergerbot.base.BaseActivity
import com.buyint.mergerbot.bus.Bus
import com.buyint.mergerbot.helper.HttpHelper
import com.buyint.mergerbot.view.SelectableBackgroundView
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.activity_upload_contact.*
import kotlinx.android.synthetic.main.toolbar_white.*

/**
 * Created by huheming on 2018/9/5
 */
class UploadContactActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setTitleWhiteAndTextBlank()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload_contact)

        toolbar_white_top.visibility = View.GONE
        toolbar_white_back.setOnClickListener { onBackPressed() }
        toolbar_white_title.text = getString(R.string.aimer_private_policy)
        toolbar_white_right_add_rl.visibility = View.GONE
        toolbar_white_right_search_rl.visibility = View.GONE

        activity_upload_contact_select.setListener(object : SelectableBackgroundView.SelectableBackgroundViewListener {
            override fun finished() {
                showLoadingFragment(R.id.activity_upload_contact_fl)
                //检查应用必备权限
                val rxPermissions = RxPermissions(this@UploadContactActivity)
                rxPermissions.request(Manifest.permission.READ_CONTACTS)
                        .subscribe { granted ->
                            if (granted) {
                                val list = ContactUtil.getContactInfo(this@UploadContactActivity)
                                HttpHelper.uploadContact(list).subscribe({
                                    removeLoadingFragment()
                                    if (it) {
                                        showToast(getString(R.string.update_success))
                                        Bus.getDefault().post(getString(R.string.upload_contact_success))
                                        finish()
                                    } else {
                                        showToast(getString(R.string.update_fail))
                                    }
                                }, {
                                    showThrowable(it)
                                    removeLoadingFragment()
                                })
                            } else {
                                showToast(getString(R.string.permission_denied))
                            }
                        }
            }
        })
    }
}