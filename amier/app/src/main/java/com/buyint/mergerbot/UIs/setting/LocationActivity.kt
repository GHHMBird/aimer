package com.buyint.mergerbot.UIs.setting

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.support.v13.app.ActivityCompat
import android.text.TextUtils
import com.buyint.mergerbot.R
import com.buyint.mergerbot.Utility.PermissionUtils
import com.buyint.mergerbot.base.BaseActivity
import kotlinx.android.synthetic.main.activity_location.*
import kotlinx.android.synthetic.main.toolbar.*

/**
 * Created by huheming on 2018/7/16
 */
class LocationActivity : BaseActivity() {

    private val LOCATION_PERMISSION = 10

    override fun onCreate(savedInstanceState: Bundle?) {
        setMyTitleColor()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location)

        toolbar_back.setOnClickListener { onBackPressed() }
        toorbar_title.text = getString(R.string.work_place)
        val drawable = resources.getDrawable(R.mipmap.duigou)
        drawable.setBounds(0, 0, dp2px(16f), dp2px(16f))
        toolbar_right.setCompoundDrawables(null, null, drawable, null)
        toolbar_right_rl.setOnClickListener {
            if (!TextUtils.isEmpty(activity_location_set_your_location.text.toString().trim())) {
                val intent = Intent()
                intent.putExtra(getString(R.string.DATA), activity_location_set_your_location.text.toString().trim())
                setResult(1001, intent)
                finish()
            }
        }

        activity_location_read_now_location.setOnClickListener {
            //检查应用必备权限
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val strings = PermissionUtils.hasPermission(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                if (strings != null && strings.isNotEmpty()) {
                    ActivityCompat.requestPermissions(this, strings, LOCATION_PERMISSION)
                } else {
                    startActivityForResult(Intent(this@LocationActivity, AMapLocationActivity::class.java), 1005)
                }
            } else {
                startActivityForResult(Intent(this@LocationActivity, AMapLocationActivity::class.java), 1005)
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startActivityForResult(Intent(this@LocationActivity, AMapLocationActivity::class.java), 1005)
            } else {
                showToast(getString(R.string.permission_denied))
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1005 && resultCode == 1001) {
            setResult(1001, data)
            finish()
        }
    }
}