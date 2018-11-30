package com.buyint.mergerbot.UIs.user.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.buyint.mergerbot.R
import com.buyint.mergerbot.UIs.user.adapter.EducationSelectAdapter
import com.buyint.mergerbot.base.BaseActivity
import kotlinx.android.synthetic.main.activity_education_select.*
import kotlinx.android.synthetic.main.toolbar.*

/**
 * Created by huheming on 2018/7/19
 */
class EducationSelectActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setMyTitleColor()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_education_select)

        toolbar_back.setOnClickListener { onBackPressed() }
        toorbar_title.text = getString(R.string.education_select)

        activity_education_select_recycler.layoutManager = LinearLayoutManager(this)
        val list = arrayListOf(getString(R.string.high_school_and_below), getString(R.string.junior_high_school_technical_school), getString(R.string.junior_high_school), getString(R.string.da_zhuan), getString(R.string.bachelor), getString(R.string.master_degree), getString(R.string.doctor))
        val adapter = EducationSelectAdapter(this, list)
        activity_education_select_recycler.adapter = adapter
    }

}