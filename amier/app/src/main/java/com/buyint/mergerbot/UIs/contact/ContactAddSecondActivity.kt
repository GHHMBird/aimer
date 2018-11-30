package com.buyint.mergerbot.UIs.contact

import android.content.Intent
import android.os.Bundle
import com.buyint.mergerbot.R
import com.buyint.mergerbot.UIs.contact.mvp.ContactAddFirstContract
import com.buyint.mergerbot.base.AppApplication
import com.buyint.mergerbot.base.BaseActivity
import com.buyint.mergerbot.dto.ContactAddRequest
import com.buyint.mergerbot.dto.ContactPersonInfoBean
import com.buyint.mergerbot.enums.RelationType
import com.buyint.mergerbot.view.SelectableBackgroundView
import kotlinx.android.synthetic.main.activity_contact_add_second.*
import kotlinx.android.synthetic.main.layout_contact_title.*

/**
 * created by licheng  on date 2018/8/7
 */
class ContactAddSecondActivity : BaseActivity() {

    private lateinit var mPreset: ContactAddFirstContract.Present
    private lateinit var personBean: ContactPersonInfoBean
    private var states = 1//1表示原生添加，2表示跳过来修改
    private var mPersonInfo: ContactAddRequest? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTitleWhiteAndTextBlank()
        setContentView(R.layout.activity_contact_add_second)
        AppApplication.getAppApplication().addActivity(this@ContactAddSecondActivity)

        if (null != intent.getSerializableExtra("mPersonInfo")) {
            mPersonInfo = intent.getSerializableExtra("mPersonInfo") as ContactAddRequest
        }

        personBean = intent.getSerializableExtra("personBean") as ContactPersonInfoBean
        states = intent.getIntExtra("states", -1)

        tv_contact_title.text = getString(R.string.contact_add_second)

        iv_contact_back.setOnClickListener { finish() }

        btn_contact_mate.setOnClickListener {
            refreshIcon()
            btn_contact_mate.selectBackground()

        }

        btn_contact_mate.setListener(object : SelectableBackgroundView.SelectableBackgroundViewListener {
            override fun finished() {

                personBean.relationType = RelationType.WORK_MATE.name
                val intent = Intent(this@ContactAddSecondActivity, ContactMateActivity::class.java)
                intent.putExtra("personBean", personBean)
                intent.putExtra("states", states)
                if (null != mPersonInfo) {
                    intent.putExtra("mPersonInfo", mPersonInfo)
                }
                startActivity(intent)

            }
        })

        btn_contact_busy.setOnClickListener {
            refreshIcon()
            btn_contact_busy.selectBackground()
        }

        btn_contact_busy.setListener(object : SelectableBackgroundView.SelectableBackgroundViewListener {
            override fun finished() {

                personBean.relationType = RelationType.BUSINESS_FRIEND.name

                val intent = Intent(this@ContactAddSecondActivity, ContactCusBusinActivity::class.java)
                intent.putExtra("personBean", personBean)
                intent.putExtra("type", RelationType.BUSINESS_FRIEND.name)
                intent.putExtra("states", states)
                if (null != mPersonInfo) {
                    intent.putExtra("mPersonInfo", mPersonInfo)
                }
                startActivity(intent)

            }
        })

        btn_contact_custom.setOnClickListener {
            refreshIcon()
            btn_contact_custom.selectBackground()
        }

        btn_contact_custom.setListener(object : SelectableBackgroundView.SelectableBackgroundViewListener {
            override fun finished() {

                personBean.relationType = RelationType.CUSTOMER.name
                val intent = Intent(this@ContactAddSecondActivity, ContactCusBusinActivity::class.java)
                intent.putExtra("personBean", personBean)
                intent.putExtra("type", RelationType.CUSTOMER.name)
                intent.putExtra("states", states)
                if (null != mPersonInfo) {
                    intent.putExtra("mPersonInfo", mPersonInfo)
                }
                startActivity(intent)

            }
        })

        btn_contact_other.setOnClickListener {
            refreshIcon()
            btn_contact_other.selectBackground()
        }

        btn_contact_other.setListener(object : SelectableBackgroundView.SelectableBackgroundViewListener {
            override fun finished() {
                personBean.relationType = RelationType.OTHER.name
                val intent = Intent(this@ContactAddSecondActivity, ContactOtherActivity::class.java)
                intent.putExtra("personBean", personBean)
                intent.putExtra("states", states)
                if (null != mPersonInfo) {
                    intent.putExtra("mPersonInfo", mPersonInfo)
                }
                startActivity(intent)
            }
        })

    }

    private fun refreshIcon() {
        btn_contact_mate.deselectBackground()
        btn_contact_busy.deselectBackground()
        btn_contact_custom.deselectBackground()
        btn_contact_other.deselectBackground()
    }

}
