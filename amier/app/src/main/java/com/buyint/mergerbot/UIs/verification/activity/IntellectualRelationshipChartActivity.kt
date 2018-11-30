package com.buyint.mergerbot.UIs.verification.activity

import android.content.Intent
import android.net.Uri
import android.net.http.SslError
import android.os.Bundle
import android.text.TextUtils
import android.view.KeyEvent
import android.view.View
import android.webkit.*
import com.alibaba.fastjson.JSON
import com.buyint.mergerbot.R
import com.buyint.mergerbot.UIs.contact.ContactAddFirstActivity
import com.buyint.mergerbot.UIs.verification.fragment.BusinessCardFragment
import com.buyint.mergerbot.UIs.verification.fragment.CompanyLineCardFragment
import com.buyint.mergerbot.base.BaseActivity
import com.buyint.mergerbot.database.getBean
import com.buyint.mergerbot.database.saveBean
import com.buyint.mergerbot.dto.*
import com.buyint.mergerbot.helper.HttpHelper
import com.buyint.mergerbot.interfaces.IGetPeopleRelationShip
import com.buyint.mergerbot.presenter.IntellectualRelationshipChartPresenter
import com.buyint.mergerbot.view.SelectableBackgroundView
import kotlinx.android.synthetic.main.activity_intellectual_relationship_chart.*
import kotlinx.android.synthetic.main.toolbar_white.*


/**
 * Created by huheming on 2018/8/13
 */
class IntellectualRelationshipChartActivity : BaseActivity(), IGetPeopleRelationShip {
    override fun getPeopleRelationShipSuccess(response: GetPeopleRelationShipModel) {
        removeLoadingFragment()
        if (response.links != null && response.links!!.size > 0 && response.nodes != null && response.nodes!!.size > 0) {
            for (bean in response.nodes!!) {
                val name = bean.name
                for (data in response.links!!) {
                    if (data.source == name) {
                        data.source = bean.id
                    }
                    if (data.target == name) {
                        data.target = bean.id
                    }
                }
            }
            links = JSON.toJSONString(response.links)
            data = JSON.toJSONString(response.nodes)
            if (!TextUtils.isEmpty(links) && !TextUtils.isEmpty(data)) {
                activity_intellectual_relationship_chart_webview.loadUrl("javascript: setData($data,$links)")
                var bean = getBean(this@IntellectualRelationshipChartActivity)
                if (bean?.list == null || bean.list?.size == 0) {
                    bean = SourceTargetGetPeopleRelationship()
                    bean.list = ArrayList()
                    val a = SourceTargetGetPeopleRelationshipModel()
                    a.bean = response
                    a.source = source
                    a.targer = target
                    a.textTitle = textTitle
                    bean.list?.add(a)
                    saveBean(this@IntellectualRelationshipChartActivity, bean)
                } else {
                    val a = SourceTargetGetPeopleRelationshipModel()
                    a.bean = response
                    a.source = source
                    a.targer = target
                    a.textTitle = textTitle
                    bean.list?.add(a)
                    saveBean(this@IntellectualRelationshipChartActivity, bean)
                }
            }
        } else {
            //没有数据
            activity_intellectual_relationship_chart_webview.visibility = View.GONE
            activity_intellectual_relationship_chart_ll.visibility = View.VISIBLE
        }
    }

    override fun getPeopleRelationShipFail(throwable: Throwable) {
        removeLoadingFragment()
        showThrowable(throwable)
    }

    var links: String? = null
    var data: String? = null
    private var source: String? = null
    private var target: String? = null
    private var textTitle: String? = null
    private var model: GetPeopleRelationShipModel? = null
    private lateinit var presenter: IntellectualRelationshipChartPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        presenter = IntellectualRelationshipChartPresenter(this)
        setTitleWhiteAndTextBlank()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intellectual_relationship_chart)

        toolbar_white_title.text = getString(R.string.human_relation)
        toolbar_white_right_add.setImageResource(R.mipmap.choose)
        toolbar_white_right_add_rl.setOnClickListener { }
        toolbar_white_right_search_rl.setOnClickListener { }
        toolbar_white_top.visibility = View.GONE
        toolbar_white_back.setOnClickListener { onBackPressed() }

        activity_intellectual_relationship_chart_btn_1.setListener(object : SelectableBackgroundView.SelectableBackgroundViewListener {
            override fun finished() {
                //上传通讯录
            }
        })
        activity_intellectual_relationship_chart_btn_2.setListener(object : SelectableBackgroundView.SelectableBackgroundViewListener {
            override fun finished() {
                val intent = Intent(this@IntellectualRelationshipChartActivity, ContactAddFirstActivity::class.java)
                intent.putExtra(getString(R.string.TYPE), getString(R.string.TYPE_0))
                startActivity(intent)
            }
        })

        initView()
        getUrl()
    }

    private fun initView() {
        activity_intellectual_relationship_chart_webview.setOnKeyListener({ _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN) {
                if (keyCode == KeyEvent.KEYCODE_BACK && activity_intellectual_relationship_chart_webview.canGoBack()) {
                    activity_intellectual_relationship_chart_webview.goBack()
                    return@setOnKeyListener true
                }
            }
            false
        })
    }

    private fun getUrl() {
        val webSettings = activity_intellectual_relationship_chart_webview.settings
        //如果访问的页面中有Javascript，则webview必须设置支持Javascript
        webSettings.javaScriptEnabled = true
        webSettings.javaScriptCanOpenWindowsAutomatically = true
        // 设置可以访问文件
        webSettings.allowFileAccess = true
        webSettings.setSupportZoom(true)
        webSettings.allowContentAccess = true
        webSettings.loadWithOverviewMode = true
        webSettings.builtInZoomControls = true
        //        webSettings.setLoadWithOverviewMode(true);
        webSettings.useWideViewPort = true
        //不使用cache 全部从网络上获取
        webSettings.cacheMode = WebSettings.LOAD_NO_CACHE
        //无模式选择，通过setAppCacheEnabled(boolean flag)设置是否打开。默认关闭，即，H5的缓存无法使用。
        webSettings.setAppCacheEnabled(true)
        //设置支持HTML5本地存储
        webSettings.domStorageEnabled = true
        webSettings.databaseEnabled = true
        activity_intellectual_relationship_chart_webview.addJavascriptInterface(HookInterface(), getString(R.string.result))

        //设置Web视图
        activity_intellectual_relationship_chart_webview.webViewClient = webViewClient()
        activity_intellectual_relationship_chart_webview.webChromeClient = webChromeClient()

        showLoadingFragment(R.id.activity_intellectual_relationship_chart_fl)
        val url = "file:///android_asset/test.html"
        activity_intellectual_relationship_chart_webview.loadUrl(url)
    }

    private inner class webViewClient : WebViewClient() {

        override fun onPageFinished(view: WebView, url: String) {
            super.onPageFinished(view, url)

            source = intent.getStringExtra(getString(R.string.data1))
            target = intent.getStringExtra(getString(R.string.data2))
            textTitle = intent.getStringExtra(getString(R.string.TITLE))
            model = intent.getSerializableExtra(getString(R.string.DATA)) as GetPeopleRelationShipModel?
            if (model == null) {
                presenter.getPeopleRelationShip(source!!, target!!)
            } else {
                removeLoadingFragment()
                for (bean in model!!.nodes!!) {
                    val name = bean.name
                    for (data in model!!.links!!) {
                        if (data.source == name) {
                            data.source = bean.id
                        }
                        if (data.target == name) {
                            data.target = bean.id
                        }
                    }
                }
                links = JSON.toJSONString(model!!.links)
                data = JSON.toJSONString(model!!.nodes)
                activity_intellectual_relationship_chart_webview.loadUrl("javascript: setData($data,$links)")
            }
        }

        override fun onReceivedSslError(view: WebView, handler: SslErrorHandler, error: SslError) {
            handler.proceed()
        }

        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            if (url.contains("mobile.nm121.com") || url.contains("jr.mmuza.com")) {
                return false
            }
            return if (url.startsWith("http:") || url.startsWith("https:")) {
                view.loadUrl(url)
                false
            } else {
                try {
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
                    true
                } catch (e: Exception) {
                    false
                }
            }
        }

        override fun shouldOverrideUrlLoading(view: WebView, resourceRequest: WebResourceRequest): Boolean {
            val url = resourceRequest.url.toString()
            if (url.contains("mobile.nm121.com") || url.contains("jr.mmuza.com")) {
                return false
            }
            if (url.startsWith("http") || url.startsWith("https")) {
                view.loadUrl(resourceRequest.url.toString())
            }
            return true
        }
    }

    //Web视图
    private inner class webChromeClient : WebChromeClient()

    override fun onDestroy() {
        super.onDestroy()
        presenter.destory()
    }

    inner class HookInterface {
        @JavascriptInterface
        fun test(json: String) {
            val parse = JSON.parseObject(json)
            when {
                parse.containsKey("date") -> {
                    //date id name
                    val f = CompanyLineCardFragment(parse.toJavaObject(RelationNode::class.java))
                    f.show(fragmentManager, "f")
                }
                parse.containsKey("source") -> {
                    //source value targer source
                }
                else -> {
                    //name id
                    var b = parse.toJavaObject(NameIdBean::class.java)
                    HttpHelper.getPeopleInformation(b.name).subscribe({
                        val f = BusinessCardFragment(it)
                        f.show(fragmentManager, "f")
                    }, {
                        showThrowable(it)
                    })
                }
            }
        }
    }
}