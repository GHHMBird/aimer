package com.buyint.mergerbot.UIs.match.activity

import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.TextView
import com.buyint.mergerbot.R
import com.buyint.mergerbot.UIs.WebActivity
import com.buyint.mergerbot.UIs.match.RecyclerAdapter.TeamMembersAdapter
import com.buyint.mergerbot.base.AppApplication
import com.buyint.mergerbot.base.BaseActivity
import com.buyint.mergerbot.dto.SimilarCompanyBean
import com.buyint.mergerbot.dto.SimilarMechanismBean
import com.buyint.mergerbot.stroage.PreferencesKeeper
import com.iflytek.cloud.*
import com.iflytek.sunflower.FlowerCollector
import kotlinx.android.synthetic.main.activity_similar_or_mechanism_company.*
import kotlinx.android.synthetic.main.layout_company_detail.*
import kotlinx.android.synthetic.main.layout_team_members.*
import kotlinx.android.synthetic.main.toolbar.*

/**
 * Created by huheming on 2018/6/11
 */
class SimilarCompanyOrMechanismActivity : BaseActivity() {

    // 语音合成对象
    private var mTts: SpeechSynthesizer? = null
    // 默认发音人
    private var voicer = ""

    private var type: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        voicer = if (AppApplication.language == getString(R.string.CHINESE)) {
            getString(R.string.xiaoyan)
        } else {
            getString(R.string.kaiselin)
        }

        setMyTitleColor()

        // 初始化合成对象
        mTts = SpeechSynthesizer.createSynthesizer(this@SimilarCompanyOrMechanismActivity, mTtsInitListener)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_similar_or_mechanism_company)

        toolbar_back.setOnClickListener { finish() }
        val tvTitle = findViewById<TextView>(R.id.toorbar_title)
        val tvWeb = findViewById<TextView>(R.id.activity_similar_company_or_mechanism_open)
        val tv3 = findViewById<TextView>(R.id.activity_similar_company_or_mechanism_phone)
        val tv2 = findViewById<TextView>(R.id.activity_similar_company_or_mechanism_place)
        val tv1 = findViewById<TextView>(R.id.activity_similar_company_or_mechanism_name)
        val recyclerView = findViewById<RecyclerView>(R.id.layout_team_members_recycler)
        val tvDes = findViewById<TextView>(R.id.layout_company_detail_tv)

        type = intent.getStringExtra(getString(R.string.TYPE))

        if (getString(R.string.TYPE_0) == type) {

            tvTitle.text = getString(R.string.similar_company)
            layout_team_members_title.text = getString(R.string.shareholder_share)
            val bean = intent.getSerializableExtra(getString(R.string.DATA)) as SimilarCompanyBean

            if (!TextUtils.isEmpty(bean.website)) {
                tvWeb.visibility = View.VISIBLE
                tvWeb.setOnClickListener {
                    val intent = Intent(this@SimilarCompanyOrMechanismActivity, WebActivity::class.java)
                    intent.putExtra("params", bean.website)
                    intent.putExtra(getString(R.string.TITLE), "")
                    startActivity(intent)
                }
            }

            tvDes.text = bean.descrip
            tv1.text = bean.name
            tv2.text = bean.location
            tv3.text = "ticker: " + bean.companyTicket

            if (bean.shareholder == null || bean.shareholder.size == 0) {
                activity_similar_company_or_mechanism_team_members.visibility = View.GONE
                return
            }

            recyclerView.layoutManager = LinearLayoutManager(this)
            val adapter = TeamMembersAdapter(this, bean.shareholder, true)
            recyclerView.adapter = adapter

        } else {

            tvTitle.text = getString(R.string.similar_mechanism)
            layout_team_members_title.text = getString(R.string.team_members)
            val bean = intent.getSerializableExtra(getString(R.string.DATA)) as SimilarMechanismBean

            if (!TextUtils.isEmpty(bean.website)) {
                tvWeb.visibility = View.VISIBLE
                tvWeb.setOnClickListener {
                    val intent = Intent(this@SimilarCompanyOrMechanismActivity, WebActivity::class.java)
                    intent.putExtra("params", bean.website)
                    intent.putExtra(getString(R.string.TITLE), "")
                    startActivity(intent)
                }
            }

            tvDes.text = bean.descrip
            tv1.text = bean.name
            val s = bean.foundTime
            tv2.text = "成立于 $s"
            if (!TextUtils.isEmpty(bean.email)) {
                tv3.text = "联系方式  " + bean.email
            }

            if (bean.coreTeam == null || bean.coreTeam.size == 0) {
                activity_similar_company_or_mechanism_team_members.visibility = View.GONE
                return
            }

            recyclerView.layoutManager = LinearLayoutManager(this)

            val adapter = TeamMembersAdapter(this, bean.coreTeam, false)
            recyclerView.adapter = adapter

        }

        recyclerView.isNestedScrollingEnabled = false

        layout_company_detail_iv.setOnClickListener {
            // 移动数据分析，收集开始合成事件
            FlowerCollector.onEvent(this@SimilarCompanyOrMechanismActivity, "tts_play")

            val text = (findViewById(R.id.layout_company_detail_tv) as TextView).text.toString()
            // 设置参数
            setParam()
            val code = mTts!!.startSpeaking(text, mTtsListener)
            //			/**
            //			 * 只保存音频不进行播放接口,调用此接口请注释startSpeaking接口
            //			 * text:要合成的文本，uri:需要保存的音频全路径，listener:回调接口
            //			*/
            //			String path = Environment.getExternalStorageDirectory()+"/tts.ico";
            //			int code = mTts.synthesizeToUri(text, path, mTtsListener);

            if (code != ErrorCode.SUCCESS) {
                //                    showToast("语音合成失败");
                Log.d("MDA", "语音合成失败,错误码: \$code")
            }
        }
    }

    /**
     * 合成回调监听。
     */
    private val mTtsListener = object : SynthesizerListener {
        override fun onSpeakBegin() {

        }

        override fun onBufferProgress(i: Int, i1: Int, i2: Int, s: String) {
            // 合成进度 percent
        }

        override fun onSpeakPaused() {

        }

        override fun onSpeakResumed() {

        }

        override fun onSpeakProgress(i: Int, i1: Int, i2: Int) {
            // 播放进度 percent
        }

        override fun onCompleted(error: SpeechError?) {
            if (error == null) {
                //播放完成
            } else {
                showToast(error.getPlainDescription(true))
            }
        }

        override fun onEvent(i: Int, i1: Int, i2: Int, bundle: Bundle) {

        }
    }


    /**
     * 初始化监听。
     */
    private val mTtsInitListener = InitListener { i ->
        Log.d("MAD", "InitListener init() code = \$code")
        if (i != ErrorCode.SUCCESS) {
            //                showToast("初始化失败");
            Log.d("MDA", "初始化失败,错误码：\$code")
        } else {
            // 初始化成功，之后可以调用startSpeaking方法
            // 注：有的开发者在onCreate方法中创建完合成对象之后马上就调用startSpeaking进行合成，
            // 正确的做法是将onCreate中的startSpeaking调用移至这里
        }
    }

    /**
     * 参数设置
     *
     * @return
     */

    private fun setParam() {
        // 清空参数
        mTts!!.setParameter(SpeechConstant.PARAMS, null)
        // 根据合成引擎设置相应参数
        mTts!!.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD)
        // 设置在线合成发音人
        mTts!!.setParameter(SpeechConstant.VOICE_NAME, voicer)
        //设置合成语速
        var speed_preference = PreferencesKeeper.getString(this, "speed_preference")
        if (TextUtils.isEmpty(speed_preference)) {
            speed_preference = "50"
        }
        mTts!!.setParameter(SpeechConstant.SPEED, speed_preference)
        //设置合成音调
        var pitch_preference = PreferencesKeeper.getString(this, "pitch_preference")
        if (TextUtils.isEmpty(pitch_preference)) {
            pitch_preference = "50"
        }
        mTts!!.setParameter(SpeechConstant.PITCH, pitch_preference)
        //设置合成音量
        var volume_preference = PreferencesKeeper.getString(this, "volume_preference")
        if (TextUtils.isEmpty(volume_preference)) {
            volume_preference = "50"
        }
        mTts!!.setParameter(SpeechConstant.VOLUME, volume_preference)
        //设置播放器音频流类型
        var stream_preference = PreferencesKeeper.getString(this, "stream_preference")
        if (TextUtils.isEmpty(stream_preference)) {
            stream_preference = "3"
        }
        mTts!!.setParameter(SpeechConstant.STREAM_TYPE, stream_preference)
        // 设置播放合成音频打断音乐播放，默认为true
        mTts!!.setParameter(SpeechConstant.KEY_REQUEST_FOCUS, "true")

        // 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
        // 注：AUDIO_FORMAT参数语记需要更新版本才能生效
        mTts!!.setParameter(SpeechConstant.AUDIO_FORMAT, "wav")
        mTts!!.setParameter(SpeechConstant.TTS_AUDIO_PATH, Environment.getExternalStorageDirectory().toString() + "/msc/tts.wav")
    }

    override fun onDestroy() {
        if (null != mTts) {
            mTts!!.stopSpeaking()
            // 退出时释放连接
            mTts!!.destroy()
        }
        super.onDestroy()
    }

    override fun finish() {
        super.finish()
        if (null != mTts) {
            mTts!!.stopSpeaking()
            // 退出时释放连接
            mTts!!.destroy()
        }
    }

    override fun onResume() {
        //移动数据统计分析
        FlowerCollector.onResume(this@SimilarCompanyOrMechanismActivity)
        FlowerCollector.onPageStart("MDA")
        super.onResume()
    }

    override fun onPause() {
        //移动数据统计分析
        FlowerCollector.onPageEnd("MDA")
        FlowerCollector.onPause(this@SimilarCompanyOrMechanismActivity)
        super.onPause()
    }

}