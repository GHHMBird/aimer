package com.buyint.mergerbot.UIs.setting

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.View
import com.buyint.mergerbot.R
import com.buyint.mergerbot.base.AppApplication
import com.buyint.mergerbot.base.BaseActivity
import com.buyint.mergerbot.bus.Bus
import com.buyint.mergerbot.stroage.PreferencesKeeper
import kotlinx.android.synthetic.main.activity_language.*
import kotlinx.android.synthetic.main.toolbar.*

/**
 * Created by huheming on 2018/6/25
 */
class LanguageActivity : BaseActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        setMyTitleColor()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_language)

        initView()
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)

        initView()
    }

    private fun initView() {
        if (getString(R.string.ENGLISH) == AppApplication.language) {
            activity_language_english_iv.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ring_blue))
            activity_language_chinese_iv.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ring_gray))
        } else {
            activity_language_chinese_iv.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ring_blue))
            activity_language_english_iv.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ring_gray))
        }

        toorbar_title.text = getString(R.string.settings_language)
        toolbar_back.setOnClickListener(this)
        activity_language_chinese.setOnClickListener(this)
        activity_language_english.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v) {
            toolbar_back -> onBackPressed()
            activity_language_chinese -> {
                AppApplication.language = getString(R.string.CHINESE)
                PreferencesKeeper.putString(this, getString(R.string.LANGUAGE), getString(R.string.CHINESE))
                recreate()
            }
            activity_language_english -> {
                AppApplication.language = getString(R.string.ENGLISH)
                PreferencesKeeper.putString(this, getString(R.string.LANGUAGE), getString(R.string.ENGLISH))
                recreate()
            }
        }
    }

    override fun onBackPressed() {
        Bus.getDefault().post(getString(R.string.buyint_language_change))
        super.onBackPressed()
    }
}