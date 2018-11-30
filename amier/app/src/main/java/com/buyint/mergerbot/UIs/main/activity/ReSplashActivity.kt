package com.buyint.mergerbot.UIs.main.activity

import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.text.TextUtils
import com.buyint.mergerbot.R
import com.buyint.mergerbot.Utility.DeviceUtils
import com.buyint.mergerbot.Utility.UpDateFragment
import com.buyint.mergerbot.base.BaseActivity
import com.buyint.mergerbot.database.getLoginResponse
import com.buyint.mergerbot.dto.VersionResponse
import com.buyint.mergerbot.interfaces.IUpdate
import com.buyint.mergerbot.presenter.ReSplashActivityPresenter
import com.buyint.mergerbot.stroage.PreferencesKeeper

/**
 * Created by huheming on 2018/8/30
 */
class ReSplashActivity : BaseActivity(), IUpdate {
    override fun getUpdateDataSuccess(versionResponse: VersionResponse?) {
        val pm = packageManager
        var info: PackageInfo? = null
        try {
            info = pm.getPackageInfo(packageName, 0)
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }

        val versionCode: Int
        if (info == null) {
            return
        } else {
            versionCode = info.versionCode
        }

        if (versionResponse?.data != null && Integer.parseInt(versionResponse.data.version) > versionCode) {
            val upDateFragment = UpDateFragment.newInstance("updateStart", getString(R.string.now_version) + versionCode + getString(R.string.new_version) + versionResponse.data.version, versionResponse.data.apkPath, 10)//10强制更新   0普通更新
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.activity_resplash_fl, upDateFragment, UpDateFragment.TAG)
                    .commitAllowingStateLoss()
        } else {
            initHttp()
        }
    }

    override fun getUpdateDataFail(throwable: Throwable?) {
        showThrowable(throwable)
        initHttp()
    }

    private var presenter: ReSplashActivityPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        splashTheme()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resplash)

        BaseActivity.themeColor = PreferencesKeeper.getInt(this, getString(R.string.THEMECOLOR), 5)

        val textSize = PreferencesKeeper.getInt(this, getString(R.string.TEXTSIZE), 3)

        when (textSize) {
            1 -> BaseActivity.fontScale = 0.8f
            2 -> BaseActivity.fontScale = 0.9f
            3 -> BaseActivity.fontScale = 1.0f
            4 -> BaseActivity.fontScale = 1.1f
            5 -> BaseActivity.fontScale = 1.2f
        }

        try {
            if (!DeviceUtils.isApkDebug(this)) {
                presenter = ReSplashActivityPresenter(this)
                presenter!!.getUpdateData()
            } else {
                initHttp()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (presenter != null) {
            presenter!!.destory()
            presenter = null
        }
    }

    private fun initHttp() {
        val loginResponse = getLoginResponse(this)
        if (loginResponse?.model != null && !TextUtils.isEmpty(loginResponse.model.token) && ("3" == loginResponse.model.userMessageLevel || "2" == loginResponse.model.userMessageLevel)) {
            startActivity(Intent(this@ReSplashActivity, HomeActivity::class.java))
            finish()
        } else {
            startActivity(Intent(this@ReSplashActivity, SplashActivity::class.java))
            finish()
        }
    }

}