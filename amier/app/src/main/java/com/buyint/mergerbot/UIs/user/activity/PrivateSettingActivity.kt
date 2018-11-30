package com.buyint.mergerbot.UIs.user.activity

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import android.view.View
import com.buyint.mergerbot.R
import com.buyint.mergerbot.UIs.user.adapter.PrivateSettingAdapter
import com.buyint.mergerbot.UIs.user.fragment.ArchivesFragment
import com.buyint.mergerbot.UIs.user.fragment.PersonFragment
import com.buyint.mergerbot.base.BaseActivity
import com.buyint.mergerbot.database.getLoginResponse
import com.buyint.mergerbot.database.saveLoginResponse
import com.buyint.mergerbot.dto.BooleanResponse
import com.buyint.mergerbot.dto.UpdatePrivacySettingRequest
import com.buyint.mergerbot.interfaces.IUpdatePrivacySetting
import com.buyint.mergerbot.presenter.PrivateSettingPresenter
import kotlinx.android.synthetic.main.activity_private_setting.*
import kotlinx.android.synthetic.main.toolbar.*

/**
 * Created by huheming on 2018/7/11
 */
class PrivateSettingActivity : BaseActivity(), View.OnClickListener, IUpdatePrivacySetting {
    override fun updatePrivacySettingSuccess(response: BooleanResponse) {
        if (response.data) {
            val response = getLoginResponse(this)
            response!!.model.userPrivacySetting = archivesFragment!!.getModel()
            saveLoginResponse(this, response)
            showToast(getString(R.string.file_save_success))
        }
        removeLoadingFragment()
    }

    override fun updatePrivacySettingFail(throwable: Throwable) {
        removeLoadingFragment()
        showThrowable(throwable)
    }

    private var presenter: PrivateSettingPresenter? = null
    private var archivesFragment: ArchivesFragment? = null
    private var personFragment: PersonFragment? = null

    override fun onClick(v: View?) {
        when (v) {
            activity_private_setting_archives -> {
                activity_private_setting_viewpager.currentItem = 0
            }
            activity_private_setting_people -> {
                activity_private_setting_viewpager.currentItem = 1
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setMyTitleColor()
        presenter = PrivateSettingPresenter(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_private_setting)

        toolbar_back.setOnClickListener { onBackPressed() }
        toorbar_title.text = getString(R.string.private_setting)

        val drawable = resources.getDrawable(R.mipmap.duigou)
        drawable.setBounds(0, 0, dp2px(16f), dp2px(16f))
        toolbar_right.setCompoundDrawables(null, null, drawable, null)
        toolbar_right_rl.setOnClickListener {
            if (activity_private_setting_viewpager.currentItem == 0) {
               showLoadingFragment(R.id.activity_private_setting_fl)
                updateArchives()
            } else if (activity_private_setting_viewpager.currentItem == 1) {
               showLoadingFragment(R.id.activity_private_setting_fl)
                updatePerson()
            }
        }

        activity_private_setting_archives.setOnClickListener(this)
        activity_private_setting_people.setOnClickListener(this)

        val response = getLoginResponse(this)

        val list = ArrayList<Fragment>()
        archivesFragment = ArchivesFragment(response!!.model.userPrivacySetting)
        personFragment = PersonFragment()
        list.add(archivesFragment!!)
        list.add(personFragment!!)
        activity_private_setting_viewpager.adapter = PrivateSettingAdapter(supportFragmentManager, list)
        activity_private_setting_viewpager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                if (position == 0) {
                    activity_private_setting_archives_iv.setBackgroundColor(ContextCompat.getColor(this@PrivateSettingActivity, R.color.colorRed))
                    activity_private_setting_people_iv.setBackgroundColor(ContextCompat.getColor(this@PrivateSettingActivity, R.color.transform))
                } else if (position == 1) {
                    activity_private_setting_archives_iv.setBackgroundColor(ContextCompat.getColor(this@PrivateSettingActivity, R.color.transform))
                    activity_private_setting_people_iv.setBackgroundColor(ContextCompat.getColor(this@PrivateSettingActivity, R.color.colorRed))
                }
            }
        })
    }

    private fun updatePerson() {

    }

    private fun updateArchives() {
        val model = archivesFragment!!.getModel()
        val request = UpdatePrivacySettingRequest()
        request.userPrivacySetting = model
        presenter!!.updatePrivacySettingArchives(request)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (presenter != null) {
            presenter!!.destory()
            presenter = null
        }
    }
}