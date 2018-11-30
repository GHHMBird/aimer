package com.buyint.mergerbot.UIs.contact

import android.os.Bundle
import android.text.TextUtils
import com.buyint.mergerbot.R
import com.buyint.mergerbot.UIs.contact.mvp.ContactMateContract
import com.buyint.mergerbot.UIs.contact.mvp.ContactMatePresent
import com.buyint.mergerbot.base.AppApplication
import com.buyint.mergerbot.base.BaseActivity
import com.buyint.mergerbot.dto.ContactAddRequest
import com.buyint.mergerbot.dto.ContactPersonInfoBean
import kotlinx.android.synthetic.main.activity_contact_other.*
import kotlinx.android.synthetic.main.layout_contact_title.*

/**
 * created by licheng  on date 2018/8/8
 */
class ContactOtherActivity : BaseActivity(), ContactMateContract.View {


    private lateinit var personBean: ContactPersonInfoBean
    private lateinit var mPresent: ContactMateContract.Present
    private var states = 1//1表示原生添加，2表示跳过来修改
    private var mPersonInfo: ContactAddRequest? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTitleWhiteAndTextBlank()
        setContentView(R.layout.activity_contact_other)
        tv_contact_title.text = getString(R.string.contact_mate_title)
        AppApplication.getAppApplication().addActivity(this@ContactOtherActivity)

        if (null != intent.getSerializableExtra("mPersonInfo")) {
            mPersonInfo = intent.getSerializableExtra("mPersonInfo") as ContactAddRequest
        }

        if (null != mPersonInfo) {
            if (!TextUtils.isEmpty(mPersonInfo!!.personInfo.relationName)) {
                edit_name.setText(mPersonInfo!!.personInfo.relationName)
            }
        }

        personBean = intent.getSerializableExtra("personBean") as ContactPersonInfoBean
        states = intent.getIntExtra("states", -1)
        ContactMatePresent(this)
        btn_contact_next.setOnClickListener {

            val name = edit_name.text.toString().trim()
            personBean.relationName = name

            val confirmationRequest = ContactAddRequest()
            confirmationRequest.personInfo = personBean

            if (states == 2) {
                mPresent.putName(confirmationRequest)
            } else {
                mPresent.addName(confirmationRequest)
            }
        }

        iv_contact_back.setOnClickListener { onBackPressed() }

    }

    override fun addtNameSuccess(succ: Boolean) {
        AppApplication.getAppApplication().cleanActivity()
    }

    override fun addtNameFailed() {
    }

    override fun setPresent(present: ContactMateContract.Present) {
        mPresent = present
    }

    override fun putNameSuccess(succ: Boolean) {
        AppApplication.getAppApplication().cleanActivity()
    }

    override fun aputNameFailed() {
    }
}
