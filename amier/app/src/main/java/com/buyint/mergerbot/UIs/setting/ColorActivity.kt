package com.buyint.mergerbot.UIs.setting

import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.animation.AnimationUtils
import com.buyint.mergerbot.R
import com.buyint.mergerbot.base.BaseActivity
import com.buyint.mergerbot.bus.Bus
import com.buyint.mergerbot.stroage.PreferencesKeeper
import kotlinx.android.synthetic.main.activity_color.*

/**
 * Created by huheming on 2018/6/20
 */
class ColorActivity : BaseActivity(), View.OnClickListener, View.OnTouchListener {
    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        when (event!!.action) {
            MotionEvent.ACTION_DOWN -> {
                val animation = AnimationUtils.loadAnimation(this@ColorActivity, R.anim.anim_scale_big_to_small)
                when (v) {
                    red_rl -> {
                        red_rl.startAnimation(animation)
                        red.startAnimation(animation)
                    }
                    black_rl -> {
                        black_rl.startAnimation(animation)
                        black.startAnimation(animation)
                    }
                    light_blue_rl -> {
                        light_blue_rl.startAnimation(animation)
                        light_blue.startAnimation(animation)
                    }
                    deep_blue_rl -> {
                        deep_blue_rl.startAnimation(animation)
                        deep_blue.startAnimation(animation)
                    }
                    standard_rl -> {
                        standard_rl.startAnimation(animation)
                        standard.startAnimation(animation)
                    }
                    pink_rl -> {
                        pink_rl.startAnimation(animation)
                        pink.startAnimation(animation)
                    }
                }
            }
            MotionEvent.ACTION_UP -> {
                val animation = AnimationUtils.loadAnimation(this@ColorActivity, R.anim.anim_scale_small_to_big)
                when (v) {
                    red_rl -> {
                        red_rl.startAnimation(animation)
                        red.startAnimation(animation)
                    }
                    black_rl -> {
                        black_rl.startAnimation(animation)
                        black.startAnimation(animation)
                    }
                    light_blue_rl -> {
                        light_blue_rl.startAnimation(animation)
                        light_blue.startAnimation(animation)
                    }
                    deep_blue_rl -> {
                        deep_blue_rl.startAnimation(animation)
                        deep_blue.startAnimation(animation)
                    }
                    standard_rl -> {
                        standard_rl.startAnimation(animation)
                        standard.startAnimation(animation)
                    }
                    pink_rl -> {
                        pink_rl.startAnimation(animation)
                        pink.startAnimation(animation)
                    }
                }
            }
        }
        return false
    }

    override fun onClick(v: View?) {
        when (v) {
            black_rl -> themeColor = 1
            light_blue_rl -> themeColor = 2
            red_rl -> themeColor = 3
            deep_blue_rl -> themeColor = 4
            standard_rl -> themeColor = 5
            pink_rl -> themeColor = 6
        }
        PreferencesKeeper.putInt(this, getString(R.string.THEMECOLOR), themeColor)
        recreate()
    }

    override fun onBackPressed() {
        Bus.getDefault().post(getString(R.string.buyint_color_change))
        super.onBackPressed()
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        setText()
        red_rl.setOnClickListener(this)
        light_blue_rl.setOnClickListener(this)
        black_rl.setOnClickListener(this)
        pink_rl.setOnClickListener(this)
        deep_blue_rl.setOnClickListener(this)
        standard_rl.setOnClickListener(this)
        activity_color_back.setOnClickListener { onBackPressed() }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setMyTitleColor()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_color)

        setText()

        red_rl.setOnClickListener(this)
        light_blue_rl.setOnClickListener(this)
        black_rl.setOnClickListener(this)
        pink_rl.setOnClickListener(this)
        deep_blue_rl.setOnClickListener(this)
        standard_rl.setOnClickListener(this)

        red_rl.setOnTouchListener(this)
        black_rl.setOnTouchListener(this)
        light_blue_rl.setOnTouchListener(this)
        pink_rl.setOnTouchListener(this)
        deep_blue_rl.setOnTouchListener(this)
        standard_rl.setOnTouchListener(this)

        activity_color_back.setOnClickListener { onBackPressed() }
    }

    private fun setText() {
        activity_color_tv.text = when (themeColor) {
            1 -> getString(R.string.u_use_black)
            2 -> getString(R.string.u_use_light_blue)
            3 -> getString(R.string.u_use_red)
            4 -> getString(R.string.u_use_deep_blue)
            5 -> getString(R.string.u_use_standard)
            6 -> getString(R.string.u_use_pink)
            else -> getString(R.string.u_use_standard)
        }
    }

}