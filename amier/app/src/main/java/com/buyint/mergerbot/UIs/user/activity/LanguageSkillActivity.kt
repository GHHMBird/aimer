package com.buyint.mergerbot.UIs.user.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.buyint.mergerbot.R
import com.buyint.mergerbot.UIs.user.adapter.LanguageSkillAdapter
import com.buyint.mergerbot.base.BaseActivity
import com.buyint.mergerbot.dto.BooleanResponse
import com.buyint.mergerbot.dto.UpdateLanguageSkillRequest
import com.buyint.mergerbot.interfaces.IUpdateLanguageSkill
import com.buyint.mergerbot.presenter.LanguageSkillPresenter
import kotlinx.android.synthetic.main.activity_language_skill.*
import kotlinx.android.synthetic.main.toolbar.*

/**
 * Created by huheming on 2018/7/18
 */
class LanguageSkillActivity : BaseActivity(), IUpdateLanguageSkill {

    private val picList: ArrayList<Int> = arrayListOf(R.mipmap.chinese_pic, R.mipmap.english_pic, R.mipmap.france_pic, R.mipmap.japanese_pic, R.mipmap.korea_pic, R.mipmap.germany_pic, R.mipmap.russian_pic)
    private val textList: ArrayList<Int> = arrayListOf(R.string.mandarin_language, R.string.english_language, R.string.french_language, R.string.japanese_language, R.string.korean_language, R.string.German_language, R.string.russian_language)
    private val enumList: ArrayList<String> = arrayListOf("CHINESE_MANDARIN", "ENGLISH", "FRENCH", "JAPANESE", "KOREAN", "GERMAN", "RUSSIAN")
    private var chooseList = arrayListOf<String>()
    private var otherList = arrayListOf<String>()

    private var adapter: LanguageSkillAdapter? = null
    private var presenter: LanguageSkillPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        presenter = LanguageSkillPresenter(this)

        setMyTitleColor()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_language_skill)

        chooseList = intent.getStringArrayListExtra(getString(R.string.data1))
        otherList = intent.getStringArrayListExtra(getString(R.string.data2))


        toolbar_back.setOnClickListener { onBackPressed() }
        toorbar_title.text = getString(R.string.language_skill)
        val drawable = resources.getDrawable(R.mipmap.duigou)
        drawable.setBounds(0, 0, dp2px(16f), dp2px(16f))
        toolbar_right.setCompoundDrawables(null, null, drawable, null)
        toolbar_right_rl.setOnClickListener {
            showLoadingFragment(R.id.activity_language_skill_fl)
            val request = UpdateLanguageSkillRequest()
            request.languageCompetence = chooseList
            request.otherLanguageCompetence = otherList
            presenter!!.updateLanguageSkill(request)
        }

        activity_language_skill_recycler.layoutManager = LinearLayoutManager(this)
        adapter = LanguageSkillAdapter(this, picList, enumList, textList, chooseList, otherList)
        activity_language_skill_recycler.adapter = adapter
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1100 && resultCode == 1001) {
            val data2 = data!!.getStringExtra(getString(R.string.DATA))
            adapter!!.otherList.add(data2)
            adapter!!.notifyDataSetChanged()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (presenter != null) {
            presenter!!.destory()
            presenter = null
        }
    }

    override fun updateLanguageSkillSuccess(response: BooleanResponse) {
        removeLoadingFragment()
        val intent = Intent()
        intent.putExtra(getString(R.string.data1), adapter!!.chooseList)
        intent.putExtra(getString(R.string.data2), adapter!!.otherList)
        setResult(10012, intent)
        finish()
    }

    override fun updateLanguageSkillFail(throwable: Throwable) {
        removeLoadingFragment()
        showThrowable(throwable)
    }
}