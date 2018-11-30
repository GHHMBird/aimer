package com.buyint.mergerbot.UIs.trainAimer.activity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import com.buyint.mergerbot.R
import com.buyint.mergerbot.UIs.main.adapter.QM3QABean
import com.buyint.mergerbot.UIs.match.RecyclerAdapter.QM3QAAdapter
import com.buyint.mergerbot.base.BaseActivity
import com.buyint.mergerbot.dto.ChangeQATrainAimerRequest
import com.buyint.mergerbot.helper.HttpHelper
import kotlinx.android.synthetic.main.activity_train_aimer_change_text.*
import kotlinx.android.synthetic.main.toolbar_left.*

/**
 * Created by huheming on 2018/9/17
 */
class TrainAimerChangeTextActivity : BaseActivity() {

    private var qid: String? = null
    private lateinit var adapter: QM3QAAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        setMyTitleColor()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_train_aimer_change_text)

        toolbar_left_title.text = getString(R.string.write_qa)
        toolbar_left_left_back.setOnClickListener { onBackPressed() }

        val layoutManager = LinearLayoutManager(this)
        activity_train_aimer_change_text_recycler.layoutManager = layoutManager
        adapter = QM3QAAdapter(this)
        adapter.setChangeTextListener { t, t1, t2 ->
            changeText(t, t1, t2)
        }
        activity_train_aimer_change_text_recycler.adapter = adapter

        getData()
    }

    fun getData() {
        showLoadingFragment(R.id.activity_train_aimer_change_text_fl)
        val qid = intent.getStringExtra(getString(R.string.DATA))
        HttpHelper.getHistoryQA(qid).subscribe({
            removeLoadingFragment()
            if (it?.dialogueSentences != null && it.dialogueSentences!!.size > 0) {
                this.qid = it.qid
                for (item in it.dialogueSentences!!) {
                    val sentence = item.sentence
                    if (sentence != null) {
                        val aimerText = StringBuilder()
                        for (i in sentence.indices) {
                            aimerText.append(sentence[i])
                            val bean = QM3QABean()
                            bean.code = item.from
                            bean.name = sentence[i]
                            bean.time = item.sendTime!!
                            adapter.addData(bean)
                        }

                        if (TextUtils.isEmpty(aimerText.toString())) {
                            aimerText.append(getString(R.string.aimer_hint_answer))

                            val bean = QM3QABean()
                            bean.code = item.from
                            bean.name = aimerText.toString()
                            adapter.addData(bean)
                        }
                    } else {
                        val bean = QM3QABean()
                        bean.code = item.from
                        bean.name = getString(R.string.aimer_hint_answer)
                        adapter.addData(bean)
                    }
                }
            }
        }, {
            showThrowable(it)
            removeLoadingFragment()
        })
    }

    fun changeText(t: Long, t1: String, t2: String) {
        showLoadingFragment(R.id.activity_train_aimer_change_text_fl)
        val request = ChangeQATrainAimerRequest()
        request.sendTime = t
        request.newSentence = t2
        if (t1 == getString(R.string.aimer_hint_answer)) {
            request.sentence = ""
        } else {
            request.sentence = t1
        }
        request.qid = qid
        HttpHelper.changeQATrainAimer(request).subscribe({
            if (it) {
                adapter.clearData()
                getData()
            }
        }, {
            removeLoadingFragment()
            showThrowable(it)
        })
    }

}