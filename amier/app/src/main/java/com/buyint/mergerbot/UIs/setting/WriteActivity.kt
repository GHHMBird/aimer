package com.buyint.mergerbot.UIs.setting

import android.content.Intent
import android.os.Bundle
import android.text.*
import com.afollestad.materialdialogs.MaterialDialog
import com.buyint.mergerbot.R
import com.buyint.mergerbot.base.BaseActivity
import com.buyint.mergerbot.dto.BooleanResponse
import com.buyint.mergerbot.interfaces.ISetAttachMessage
import com.buyint.mergerbot.presenter.WritePresenter
import kotlinx.android.synthetic.main.activity_write.*
import kotlinx.android.synthetic.main.toolbar.*

/**
 * Created by huheming on 2018/7/12
 */
class WriteActivity : BaseActivity(), ISetAttachMessage {

    private var presenter: WritePresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        presenter = WritePresenter(this)

        setMyTitleColor()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write)

        toolbar_back.setOnClickListener { onBackPressed() }

        val title = intent.getStringExtra(getString(R.string.TITLE))
        toorbar_title.text = title
        val name = intent.getStringExtra(getString(R.string.NAME))
        activity_write_et.hint = name

        val max = intent.getIntExtra(getString(R.string.MAX), 0)
        activity_write_et.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(max))
        val data = intent.getStringExtra(getString(R.string.DATA))
        if (!TextUtils.isEmpty(data)) {
            activity_write_et.text = SpannableStringBuilder(data)
            activity_write_et.setSelection(data.length)
            activity_write_tv.text = "" + data.length + "/" + max
        } else {
            activity_write_tv.text = "0/" + max
        }

        val drawable = resources.getDrawable(R.mipmap.duigou)
        drawable.setBounds(0, 0, dp2px(16f), dp2px(16f))
        toolbar_right.setCompoundDrawables(null, null, drawable, null)
        toolbar_right_rl.setOnClickListener {
            if (!TextUtils.isEmpty(activity_write_et.text.toString().trim())) {
                val result = intent.getStringExtra(getString(R.string.result))
                if (result == getString(R.string.TYPE_1)) {
                    showLoadingFragment(R.id.activity_write_fl)
                    updateExtraInformation()
                } else {
                    val intent = Intent()
                    intent.putExtra(getString(R.string.DATA), activity_write_et.text.toString().trim())
                    setResult(1001, intent)
                    finish()
                }
            }
        }

        activity_write_et.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (activity_write_et.text.toString().trim().isEmpty()) {
                    activity_write_tv.text = "0/" + max
                } else {
                    activity_write_tv.text = "" + activity_write_et.text.toString().trim().length + "/" + max
                }
            }

        })
    }

    private fun updateExtraInformation() {
        presenter!!.setAttachMessage(activity_write_et.text.toString().trim())
    }

    override fun setAttachMessageSuccess(response: BooleanResponse) {
        removeLoadingFragment()
        if (response.data) {
            finish()
        }
    }

    override fun setAttachMessageFail(throwable: Throwable) {
        removeLoadingFragment()
        showThrowable(throwable)
    }

    override fun onBackPressed() {
        if (!TextUtils.isEmpty(activity_write_et.text.toString().trim())) {
            val builder = MaterialDialog.Builder(this)
            builder.positiveText(R.string.alright)
            builder.negativeText(R.string.cancel)
            builder.positiveColorRes(R.color.colorBlack)
            builder.negativeColorRes(R.color.colorBlack)
            builder.content(R.string.sure_to_quit_write)
            builder.onNegative { dialog, _ ->
                dialog.dismiss()
            }
            builder.onPositive { dialog, _ ->
                dialog.dismiss()
                super.onBackPressed()
            }
            builder.build().show()
        } else {
            super.onBackPressed()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (presenter != null) {
            presenter!!.destory()
            presenter = null
        }
    }
}