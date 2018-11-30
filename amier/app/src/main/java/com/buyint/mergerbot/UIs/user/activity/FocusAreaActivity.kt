package com.buyint.mergerbot.UIs.user.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.buyint.mergerbot.R
import com.buyint.mergerbot.UIs.user.adapter.FocusAreaAdapter
import com.buyint.mergerbot.base.BaseActivity
import com.buyint.mergerbot.dto.BooleanResponse
import com.buyint.mergerbot.dto.CodeNameBean
import com.buyint.mergerbot.dto.CodeNameResponse
import com.buyint.mergerbot.dto.CodeTitleBean
import com.buyint.mergerbot.interfaces.IPopularIndustry
import com.buyint.mergerbot.interfaces.IUpdateFocusArea
import com.buyint.mergerbot.presenter.FocusAreaPresenter
import kotlinx.android.synthetic.main.activity_focus_area.*
import kotlinx.android.synthetic.main.toolbar.*

/**
 * Created by huheming on 2018/7/19
 */
class FocusAreaActivity : BaseActivity(), IPopularIndustry, IUpdateFocusArea {

    private var focusList = arrayListOf<CodeNameBean>()
    private var chooseList = arrayListOf<CodeNameBean>()
    private var otherList = arrayListOf<CodeNameBean>()
    private var adapter: FocusAreaAdapter? = null
    private var presenter: FocusAreaPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        presenter = FocusAreaPresenter(this, this)

        setMyTitleColor()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_focus_area)

        toolbar_back.setOnClickListener { onBackPressed() }
        toorbar_title.text = getString(R.string.focus_area)
        val drawable = resources.getDrawable(R.mipmap.duigou)
        drawable.setBounds(0, 0, dp2px(16f), dp2px(16f))
        toolbar_right.setCompoundDrawables(null, null, drawable, null)
        toolbar_right_rl.setOnClickListener {
           showLoadingFragment(R.id.activity_focus_area_fl)
            focusList.clear()
            focusList.addAll(chooseList)
            focusList.addAll(otherList)
            presenter!!.updateFocusArea(focusList)
        }

        focusList = intent.getSerializableExtra(getString(R.string.DATA)) as ArrayList<CodeNameBean>

        showLoadingFragment(R.id.activity_focus_area_fl)
        presenter!!.popularIndustry()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 11 && resultCode == 1001) {
            val bean = data!!.getSerializableExtra(getString(R.string.IndustryBean)) as CodeTitleBean
            val cnb = CodeNameBean()
            cnb.name = bean.name[0]
            cnb.code = bean.code
            adapter!!.otherList.add(cnb)
            adapter!!.notifyDataSetChanged()
        }
    }

    override fun popularIndustrySuccess(response: CodeNameResponse) {
        removeLoadingFragment()
        val textList = response.data

        for (a in focusList) {
            for (b in textList) {
                if (a.name == b.name && a.code == b.code) {
                    chooseList.add(a)
                }
            }
        }
        for (a in focusList) {
            if (!chooseList.contains(a)) {
                otherList.add(a)
            }
        }

        activity_focus_area_recycler.layoutManager = LinearLayoutManager(this)
        adapter = FocusAreaAdapter(this, textList, chooseList, otherList)
        activity_focus_area_recycler.adapter = adapter

    }

    override fun popularIndustryFail(throwable: Throwable) {
        removeLoadingFragment()
        showThrowable(throwable)
    }

    override fun updateFocusAreaSuccess(response: BooleanResponse) {
        removeLoadingFragment()
        if (response.data) {
            val intent = Intent()
            intent.putExtra(getString(R.string.DATA), focusList)
            setResult(10014, intent)
            finish()
        }
    }

    override fun updateFocusAreaFail(throwable: Throwable) {
        removeLoadingFragment()
        showThrowable(throwable)
    }


    override fun onDestroy() {
        super.onDestroy()
        if (presenter != null) {
            presenter!!.ondestory()
            presenter = null
        }
    }
}