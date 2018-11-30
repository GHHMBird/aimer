package com.buyint.mergerbot.UIs.user.activity

import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import cn.jpush.android.api.JPushInterface
import com.buyint.mergerbot.R
import com.buyint.mergerbot.UIs.setting.ColorActivity
import com.buyint.mergerbot.UIs.setting.LanguageActivity
import com.buyint.mergerbot.UIs.setting.TextSizeActivity
import com.buyint.mergerbot.Utility.UpDateFragment
import com.buyint.mergerbot.Utility.ViewHelper
import com.buyint.mergerbot.base.BaseActivity
import com.buyint.mergerbot.bus.annotation.BusReceiver
import com.buyint.mergerbot.database.clearLoginResponse
import com.buyint.mergerbot.helper.HttpHelper
import com.snappydb.DBFactory
import kotlinx.android.synthetic.main.activity_setting.*
import kotlinx.android.synthetic.main.toolbar.*

/**
 * Created by huheming on 2018/6/25
 */
class SettingActivity : BaseActivity(), View.OnClickListener {

    override fun onClick(v: View?) {
        when (v) {
            toolbar_back -> onBackPressed()
            activity_setting_quit -> bye()
            activity_setting_language -> startActivity(Intent(this@SettingActivity, LanguageActivity::class.java))
            activity_setting_push -> startActivity(Intent(this@SettingActivity, PushActivity::class.java))
            activity_setting_private_setting -> startActivity(Intent(this@SettingActivity, PrivateSettingActivity::class.java))
            activity_setting_theme_color -> startActivity(Intent(this@SettingActivity, ColorActivity::class.java))
            activity_setting_text_size -> startActivity(Intent(this@SettingActivity, TextSizeActivity::class.java))
            activity_setting_clear_chat_history -> {
                val db = DBFactory.open(this)
                db.destroy()
                showToast(getString(R.string.cleared_cache))
            }
            activity_setting_version_check -> {
                HttpHelper.getVersion().subscribe({
                    val pm = packageManager
                    var info: PackageInfo? = null
                    try {
                        info = pm.getPackageInfo(packageName, 0)
                    } catch (e: PackageManager.NameNotFoundException) {
                        e.printStackTrace()
                    }

                    val versionCode = info!!.versionCode

                    if (it?.data != null && Integer.parseInt(it.data.version) > versionCode) {
                        run {
                            val upDateFragment = UpDateFragment.newInstance("updateStart", getString(R.string.now_version) + versionCode + getString(R.string.new_version) + it.data.version, it.data.apkPath, 10)//10强制更新   0普通更新
                            supportFragmentManager
                                    .beginTransaction()
                                    .replace(R.id.activity_splash_fl, upDateFragment, UpDateFragment.TAG)
                                    .commitAllowingStateLoss()
                        }
                    } else {
                        activity_setting_version_check_tv.text = getString(R.string.now_is_new_version)
                    }
                }, {
                    showThrowable(it)
                })
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setMyTitleColor()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        initView()
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)

        initView()
    }

    private fun initView() {
        toorbar_title.text = getString(R.string.setting)
        toolbar_back.setOnClickListener(this)

        activity_setting_quit.setOnClickListener(this)
        activity_setting_language.setOnClickListener(this)
        activity_setting_push.setOnClickListener(this)
        activity_setting_private_setting.setOnClickListener(this)
        activity_setting_theme_color.setOnClickListener(this)
        activity_setting_text_size.setOnClickListener(this)
        activity_setting_version_check.setOnClickListener(this)
        activity_setting_clear_chat_history.setOnClickListener(this)
    }

    @BusReceiver
    fun StringEvent(event: String) {
        if (event == getString(R.string.buyint_color_change) || event == getString(R.string.buyint_language_change) || event == getString(R.string.buyint_text_size_change)) {
            recreate()
        }
    }

    private fun bye() {
        ViewHelper.showOneLineCard(this, getString(R.string.sure_to_log_off), getString(R.string.alright), getString(R.string.cancel), { _, _ ->
            JPushInterface.setAlias(this, 1, "")
            clearLoginResponse(this)
            setResult(100)
            finish()
        }, { _, _ -> })
    }

}