package com.buyint.mergerbot.UIs.main.activity

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.text.TextUtils
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.buyint.mergerbot.R
import com.buyint.mergerbot.UIs.match.activity.QuickMatchActivity
import com.buyint.mergerbot.UIs.match.fragment.QAMoneyFragment
import com.buyint.mergerbot.base.BaseActivity
import com.buyint.mergerbot.dto.*
import com.buyint.mergerbot.helper.HttpHelper
import com.buyint.mergerbot.view.FluidLayout
import kotlinx.android.synthetic.main.activity_project_wrong_rematch.*
import kotlinx.android.synthetic.main.toolbar_left.*
import java.util.*

/**
 * Created by huheming on 2018/9/7
 */
class ProjectWrongReMatchActivity : BaseActivity(), View.OnClickListener {

    private var styleLl: LinearLayout? = null
    private var stylelll: LinearLayout? = null
    private var styleLl2: LinearLayout? = null
    private var styleLl3: LinearLayout? = null
    private var styleLl4: LinearLayout? = null
    private var styleTv: TextView? = null
    private var styleLl1: LinearLayout? = null
    private var styleIv1: ImageView? = null
    private var styleIv2: ImageView? = null
    private var styleIv3: ImageView? = null
    private var styleIv4: ImageView? = null
    private var flPlace: FluidLayout? = null
    private var flChanpin: FluidLayout? = null
    private var flHangye: FluidLayout? = null
    private val placeBeanList = ArrayList<CodeNameBean>()
    private val hangyeBeanList = ArrayList<CodeTitleBean>()
    private val chanpinList = ArrayList<CodeNameBean>()
    private var tvGuiMo: TextView? = null
    var danwei = "￥"
    private var money = "0"
    private var request: MatchCompanyRequest? = null
    private var placeLine: View? = null
    private var hangyeLine: View? = null
    private var tvTitle: TextView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        setMyTitleColor()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_project_wrong_rematch)

        placeLine = findViewById(R.id.activity_project_wrong_rematch_place_line)
        hangyeLine = findViewById(R.id.activity_project_wrong_rematch_hangye_line)

        activity_project_wrong_rematch_next.setOnClickListener({ _ ->
            if (placeBeanList.size == 0 && hangyeBeanList.size == 0) {
                placeLine!!.setBackgroundColor(Color.RED)
                hangyeLine!!.setBackgroundColor(Color.RED)
                return@setOnClickListener
            }
            if (hangyeBeanList.size == 0) {
                hangyeLine!!.setBackgroundColor(Color.RED)
                return@setOnClickListener
            }
            if (placeBeanList.size == 0) {
                placeLine!!.setBackgroundColor(Color.RED)
                return@setOnClickListener
            }
            request = MatchCompanyRequest()
            initRequest(request!!)
            when {
                getString(R.string.acquisition) == styleTv!!.text.toString().trim { it <= ' ' } -> {
                    request!!.intention.add(getString(R.string.TYPE_0101))//购买意图或者出售意图
                    request!!.requirement.location = placeBeanList
                    request!!.requirement.product_productWords = chanpinList
                    for (i in hangyeBeanList.indices) {
                        val cb = CodeNameBean()
                        cb.name = hangyeBeanList[i].name[0]
                        cb.code = hangyeBeanList[i].code
                        request!!.requirement.industry_Classification.add(cb)//行业列表
                    }
                    val bean = UnitValueBean()
                    bean.unit = danwei
                    bean.value = java.lang.Double.parseDouble(money) * 10000
                    request!!.requirement.price.add(bean)//规模

                    request!!.projectName = "我要" + styleTv!!.text.toString().trim { it <= ' ' } + request!!.requirement.industry_Classification[0].name + "项目"

                }
                getString(R.string.sell) == styleTv!!.text.toString().trim { it <= ' ' } -> {

                    request!!.intention.add(getString(R.string.TYPE_0102))//购买意图或者出售意图
                    request!!.condition.location = placeBeanList//地理位置列表
                    request!!.condition.product_productWords = chanpinList//产品名称列表
                    for (i in hangyeBeanList.indices) {
                        val cb = CodeNameBean()
                        cb.name = hangyeBeanList[i].name[0]
                        cb.code = hangyeBeanList[i].code
                        request!!.condition.industry_Classification.add(cb)//行业列表
                    }
                    val bean = UnitValueBean()
                    bean.unit = danwei
                    bean.value = java.lang.Double.parseDouble(money) * 10000
                    request!!.condition.price.add(bean)//规模

                    request!!.projectName = "我要" + styleTv!!.text.toString().trim { it <= ' ' } + request!!.condition.industry_Classification[0].name + "项目"

                }
                getString(R.string.investment) == styleTv!!.text.toString().trim { it <= ' ' } -> {
                    request!!.intention.add(getString(R.string.TYPE_010301))//购买意图或者出售意图

                    request!!.requirement.location = placeBeanList
                    request!!.requirement.product_productWords = chanpinList
                    for (i in hangyeBeanList.indices) {
                        val cb = CodeNameBean()
                        cb.name = hangyeBeanList[i].name[0]
                        cb.code = hangyeBeanList[i].code
                        request!!.requirement.industry_Classification.add(cb)//行业列表
                    }
                    val bean = UnitValueBean()
                    bean.unit = danwei
                    bean.value = java.lang.Double.parseDouble(money) * 10000
                    request!!.requirement.price.add(bean)//规模

                    request!!.projectName = "我要" + styleTv!!.text.toString().trim { it <= ' ' } + request!!.requirement.industry_Classification[0].name + "项目"

                }
                getString(R.string.financing) == styleTv!!.text.toString().trim { it <= ' ' } -> {
                    request!!.intention.add(getString(R.string.TYPE_010302))//购买意图或者出售意图

                    request!!.condition.location = placeBeanList//地理位置列表
                    request!!.condition.product_productWords = chanpinList//产品名称列表
                    for (i in hangyeBeanList.indices) {
                        val cb = CodeNameBean()
                        cb.name = hangyeBeanList[i].name[0]
                        cb.code = hangyeBeanList[i].code
                        request!!.condition.industry_Classification.add(cb)//行业列表
                    }
                    val bean = UnitValueBean()
                    bean.unit = danwei
                    bean.value = java.lang.Double.parseDouble(money) * 10000
                    request!!.condition.price.add(bean)//规模

                    request!!.projectName = "我要" + styleTv!!.text.toString().trim { it <= ' ' } + request!!.condition.industry_Classification[0].name + "项目"

                }
                else -> return@setOnClickListener
            }
            showLoadingFragment(R.id.activity_project_wrong_rematch_fl)
            matchCompany()
        })

        toolbar_left_left_back.setOnClickListener({ _ -> onBackPressed() })
        toolbar_left_title.text = getString(R.string.project_is_wrong)

        tvTitle = findViewById(R.id.activity_project_wrong_rematch_tv)
        styleTv = findViewById(R.id.activity_project_wrong_rematch_style_tv)
        stylelll = findViewById(R.id.activity_project_wrong_rematch_style_lll)
        styleLl1 = findViewById(R.id.activity_project_wrong_rematch_style_ll1)
        styleLl2 = findViewById(R.id.activity_project_wrong_rematch_style_ll2)
        styleLl3 = findViewById(R.id.activity_project_wrong_rematch_style_ll3)
        styleLl4 = findViewById(R.id.activity_project_wrong_rematch_style_ll4)
        styleLl = findViewById(R.id.activity_project_wrong_rematch_style_ll)
        styleIv1 = findViewById(R.id.activity_project_wrong_rematch_style_iv1)
        styleIv2 = findViewById(R.id.activity_project_wrong_rematch_style_iv2)
        styleIv3 = findViewById(R.id.activity_project_wrong_rematch_style_iv3)
        styleIv4 = findViewById(R.id.activity_project_wrong_rematch_style_iv4)
        tvGuiMo = findViewById(R.id.activity_project_wrong_rematch_tv_money)

        activity_project_wrong_rematch_iv_place.setOnClickListener({ _ ->
            val intent = Intent(this@ProjectWrongReMatchActivity, QuickMatchActivity::class.java)
            intent.putExtra(getString(R.string.TYPE), 10)
            intent.putExtra(getString(R.string.NAME), styleTv!!.text.toString().trim { it <= ' ' })
            startActivityForResult(intent, 101)
        })
        add_ll_place.setOnClickListener({ _ ->
            val intent = Intent(this@ProjectWrongReMatchActivity, QuickMatchActivity::class.java)
            intent.putExtra(getString(R.string.TYPE), 10)
            intent.putExtra(getString(R.string.NAME), styleTv!!.text.toString().trim { it <= ' ' })
            startActivityForResult(intent, 101)
        })
        activity_project_wrong_rematch_iv_hangye.setOnClickListener({ _ ->
            val intent = Intent(this@ProjectWrongReMatchActivity, QuickMatchActivity::class.java)
            intent.putExtra(getString(R.string.TYPE), 11)
            intent.putExtra(getString(R.string.NAME), styleTv!!.text.toString().trim { it <= ' ' })
            startActivityForResult(intent, 102)
        })
        add_ll_hangye.setOnClickListener({ _ ->
            val intent = Intent(this@ProjectWrongReMatchActivity, QuickMatchActivity::class.java)
            intent.putExtra(getString(R.string.TYPE), 11)
            intent.putExtra(getString(R.string.NAME), styleTv!!.text.toString().trim { it <= ' ' })
            startActivityForResult(intent, 102)
        })
        activity_project_wrong_rematch_iv_chanpin.setOnClickListener({ _ ->
            val intent = Intent(this@ProjectWrongReMatchActivity, QuickMatchActivity::class.java)
            intent.putExtra(getString(R.string.TYPE), 12)
            intent.putExtra(getString(R.string.NAME), styleTv!!.text.toString().trim { it <= ' ' })
            startActivityForResult(intent, 103)
        })
        add_ll_chanpin.setOnClickListener({ _ ->
            val intent = Intent(this@ProjectWrongReMatchActivity, QuickMatchActivity::class.java)
            intent.putExtra(getString(R.string.TYPE), 12)
            intent.putExtra(getString(R.string.NAME), styleTv!!.text.toString().trim { it <= ' ' })
            startActivityForResult(intent, 103)
        })

        styleLl1!!.setOnClickListener(this)
        styleLl2!!.setOnClickListener(this)
        styleLl3!!.setOnClickListener(this)
        styleLl4!!.setOnClickListener(this)
        activity_project_wrong_rematch_style_more.setOnClickListener({ _ ->
            stylelll!!.visibility = View.GONE
            styleLl!!.visibility = View.VISIBLE
        })
        activity_project_wrong_rematch_money_more.setOnClickListener({ _ -> showMoneyFragment() })

        flPlace = findViewById(R.id.activity_project_wrong_rematch_fl_place)
        flChanpin = findViewById(R.id.activity_project_wrong_rematch_fl_chanpin)
        flHangye = findViewById(R.id.activity_project_wrong_rematch_fl_hangye)

        val response = intent.getSerializableExtra(getString(R.string.DATA)) as QASpeakResponse

        when {
            getString(R.string.TYPE_0101) == response.data.intention[0] -> styleTv!!.text = getString(R.string.acquisition)
            getString(R.string.TYPE_0102) == response.data.intention[0] -> styleTv!!.text = getString(R.string.sell)
            getString(R.string.TYPE_010301) == response.data.intention[0] -> styleTv!!.text = getString(R.string.investment)
            getString(R.string.TYPE_010302) == response.data.intention[0] -> styleTv!!.text = getString(R.string.financing)
            else -> {
                styleTv!!.text = ""
                stylelll!!.visibility = View.GONE
                styleLl!!.visibility = View.VISIBLE
            }
        }
        if (response.data.requirementOrCondition.location != null && response.data.requirementOrCondition.location.size > 0) {
            placeBeanList.addAll(response.data.requirementOrCondition.location)
            initFluidLayoutPlace(initListPlace())
        }
        if (response.data.requirementOrCondition.product_productWords != null && response.data.requirementOrCondition.product_productWords.size > 0) {
            chanpinList.addAll(response.data.requirementOrCondition.product_productWords)
            initFluidLayoutChanPin(initListChanPin())
        }
        if (response.data.requirementOrCondition.industry_Classification != null && response.data.requirementOrCondition.industry_Classification.size > 0) {
            for (i in response.data.requirementOrCondition.industry_Classification.indices) {
                val bean = CodeTitleBean()
                if (bean.name == null) {
                    bean.name = ArrayList()
                } else {
                    bean.name.clear()
                }
                bean.code = response.data.requirementOrCondition.industry_Classification[i].code
                bean.name.add(response.data.requirementOrCondition.industry_Classification[i].name)
                hangyeBeanList.add(bean)
            }
            initFluidLayoutHangYe(initListHangYe())
        }

    }

    private fun showMoneyFragment() {
        hideKeyBoard()
        val mbf1 = QAMoneyFragment(this@ProjectWrongReMatchActivity, object : QAMoneyFragment.QAMoneyFragmentListener {
            override fun priceOk(list: ArrayList<MoneyFragmentDto>) {

                val b0 = list[0]
                this@ProjectWrongReMatchActivity.danwei = b0.danwei
                if (b0.price == getString(R.string.unlimited) || "0" == b0.price) {
                    this@ProjectWrongReMatchActivity.money = "0"
                    tvGuiMo!!.text = getString(R.string.unlimited)
                } else {
                    this@ProjectWrongReMatchActivity.money = b0.price
                    if (b0.beishu == "wan") {
                        tvGuiMo!!.text = money + "万"
                    } else if (b0.beishu == "yi") {
                        tvGuiMo!!.text = money + "亿"
                    }
                }
            }

            override fun priceCannel() {}
        }, 1)
        mbf1.show(supportFragmentManager, "mbf")
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.activity_project_wrong_rematch_style_ll1 -> {
                styleIv1!!.background = ContextCompat.getDrawable(this, R.drawable.cicle_black_shi)
                styleIv2!!.background = ContextCompat.getDrawable(this, R.drawable.cicle_black)
                styleIv3!!.background = ContextCompat.getDrawable(this, R.drawable.cicle_black)
                styleIv4!!.background = ContextCompat.getDrawable(this, R.drawable.cicle_black)
                styleTv!!.text = getString(R.string.acquisition)
                stylelll!!.visibility = View.VISIBLE
                styleLl!!.visibility = View.GONE
            }
            R.id.activity_project_wrong_rematch_style_ll2 -> {
                styleIv2!!.background = ContextCompat.getDrawable(this, R.drawable.cicle_black_shi)
                styleIv1!!.background = ContextCompat.getDrawable(this, R.drawable.cicle_black)
                styleIv3!!.background = ContextCompat.getDrawable(this, R.drawable.cicle_black)
                styleIv4!!.background = ContextCompat.getDrawable(this, R.drawable.cicle_black)
                styleTv!!.text = getString(R.string.sell)
                stylelll!!.visibility = View.VISIBLE
                styleLl!!.visibility = View.GONE
            }
            R.id.activity_project_wrong_rematch_style_ll3 -> {
                styleIv3!!.background = ContextCompat.getDrawable(this, R.drawable.cicle_black_shi)
                styleIv2!!.background = ContextCompat.getDrawable(this, R.drawable.cicle_black)
                styleIv1!!.background = ContextCompat.getDrawable(this, R.drawable.cicle_black)
                styleIv4!!.background = ContextCompat.getDrawable(this, R.drawable.cicle_black)
                styleTv!!.text = getString(R.string.investment)
                stylelll!!.visibility = View.VISIBLE
                styleLl!!.visibility = View.GONE
            }
            R.id.activity_project_wrong_rematch_style_ll4 -> {
                styleIv4!!.background = ContextCompat.getDrawable(this, R.drawable.cicle_black_shi)
                styleIv2!!.background = ContextCompat.getDrawable(this, R.drawable.cicle_black)
                styleIv3!!.background = ContextCompat.getDrawable(this, R.drawable.cicle_black)
                styleIv1!!.background = ContextCompat.getDrawable(this, R.drawable.cicle_black)
                styleTv!!.text = getString(R.string.financing)
                stylelll!!.visibility = View.VISIBLE
                styleLl!!.visibility = View.GONE
            }
        }
    }

    private fun initListPlace(): ArrayList<FluidLayoutBean> {
        val list = ArrayList<FluidLayoutBean>()
        for (i in placeBeanList.indices) {
            val b = FluidLayoutBean()
            val code = placeBeanList[i].code
            if (TextUtils.isEmpty(code)) {
                b.index = placeBeanList[i].name
            } else {
                b.index = code
            }
            b.text = placeBeanList[i].name
            list.add(b)
        }
        return list
    }

    private fun initFluidLayoutPlace(list: ArrayList<FluidLayoutBean>) {
        flPlace!!.removeAllViews()
        flPlace!!.setGravity(Gravity.TOP)
        for (i in list.indices) {

            val bean = list[i]

            val tv = TextView(this)
            tv.setPadding(25, 0, 25, 0)
            tv.text = bean.text
            tv.gravity = Gravity.CENTER
            tv.textSize = 14f
            tv.isClickable = true

            tv.setBackgroundResource(R.mipmap.bf_match_delete)
            tv.setTextColor(ContextCompat.getColor(this, R.color.color_2b3563))

            tv.setOnClickListener {
                for (j in placeBeanList.indices) {
                    if (placeBeanList[j].name == bean.text) {
                        placeBeanList.removeAt(j)
                    }
                }
                initFluidLayoutPlace(initListPlace())
            }

            val params = FluidLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
            )
            params.setMargins(0, 20, 30, 20)
            flPlace!!.addView(tv, params)
        }
    }

    private fun initListChanPin(): ArrayList<FluidLayoutBean> {
        val list = ArrayList<FluidLayoutBean>()
        for (i in chanpinList.indices) {
            val b = FluidLayoutBean()
            b.index = chanpinList[i].name
            b.text = chanpinList[i].name
            list.add(b)
        }
        return list
    }

    private fun initFluidLayoutChanPin(list: ArrayList<FluidLayoutBean>) {
        flChanpin!!.removeAllViews()
        flChanpin!!.setGravity(Gravity.TOP)
        for (i in list.indices) {

            val bean = list[i]

            val tv = TextView(this)
            tv.setPadding(25, 0, 25, 0)
            tv.text = bean.text
            tv.gravity = Gravity.CENTER
            tv.textSize = 14f
            tv.isClickable = true

            tv.setBackgroundResource(R.mipmap.bf_match_delete)
            tv.setTextColor(ContextCompat.getColor(this, R.color.color_2b3563))

            tv.setOnClickListener {
                for (j in chanpinList.indices) {
                    if (chanpinList[j].name == bean.text) {
                        chanpinList.removeAt(j)
                    }
                }
                initFluidLayoutChanPin(initListChanPin())
            }

            val params = FluidLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            )
            params.setMargins(0, 20, 30, 20)
            flChanpin!!.addView(tv, params)
        }
    }

    private fun initListHangYe(): ArrayList<FluidLayoutBean> {
        val list = ArrayList<FluidLayoutBean>()
        for (i in hangyeBeanList.indices) {
            val b = FluidLayoutBean()
            val code = hangyeBeanList[i].code
            if (TextUtils.isEmpty(code)) {
                b.index = hangyeBeanList[i].name[0]
            } else {
                b.index = code
            }
            b.text = hangyeBeanList[i].name[0]
            list.add(b)
        }
        return list
    }

    private fun initFluidLayoutHangYe(list: ArrayList<FluidLayoutBean>) {
        flHangye!!.removeAllViews()
        flHangye!!.setGravity(Gravity.TOP)
        for (i in list.indices) {

            val bean = list[i]

            val tv = TextView(this)
            tv.setPadding(25, 0, 25, 0)
            tv.text = bean.text
            tv.gravity = Gravity.CENTER
            tv.textSize = 14f
            tv.isClickable = true

            tv.setBackgroundResource(R.mipmap.bf_match_delete)
            tv.setTextColor(ContextCompat.getColor(this, R.color.color_2b3563))

            tv.setOnClickListener {
                for (j in hangyeBeanList.indices) {
                    if (hangyeBeanList[j].name[0] == bean.text) {
                        hangyeBeanList.removeAt(j)
                    }
                }
                initFluidLayoutHangYe(initListHangYe())
            }

            val params = FluidLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            )
            params.setMargins(0, 20, 30, 20)
            flHangye!!.addView(tv, params)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == 1001) {
            when (requestCode) {
                101 -> {
                    val placeBean = data.getSerializableExtra(getString(R.string.PlaceBean)) as CodeNameBean
                    if (!placeBeanList.contains(placeBean)) {
                        placeLine!!.setBackgroundColor(ContextCompat.getColor(this@ProjectWrongReMatchActivity, R.color.color_999999))
                        placeBeanList.add(placeBean)
                        initFluidLayoutPlace(initListPlace())
                    }
                }
                102 -> {
                    val codeTitleBean = data.getSerializableExtra(getString(R.string.IndustryBean)) as CodeTitleBean
                    if (!hangyeBeanList.contains(codeTitleBean)) {
                        hangyeLine!!.setBackgroundColor(ContextCompat.getColor(this@ProjectWrongReMatchActivity, R.color.color_999999))
                        hangyeBeanList.add(codeTitleBean)
                        initFluidLayoutHangYe(initListHangYe())
                    }
                }
                103 -> {
                    val chanpin = data.getSerializableExtra(getString(R.string.ProductBean)) as CodeNameBean
                    if (!chanpinList.contains(chanpin)) {
                        chanpinList.add(chanpin)
                        initFluidLayoutChanPin(initListChanPin())
                    }
                }
            }
        }
    }

    private fun initRequest(request: MatchCompanyRequest) {

        request.projectCategory = 0
        request.intention = ArrayList()
        val response = intent.getSerializableExtra(getString(R.string.DATA)) as QASpeakResponse
        request.qid = response.data.projectId

        //买：0101
        //        卖：0102
        //        投资：010301
        //        融资：010302

        request.condition = MatchCompanyRequestBean()//购买方自己的信息

        //对于所有数字类型的，对对方的需求是填区间，对自己的描述是填数字

        request.condition.ebitda = ArrayList()//买方ebitda
        request.condition.industry_Classification = ArrayList()//买方行业名称
        request.condition.location = ArrayList()//购买方地理位置
        request.condition.market_Share = MarketShareBean()
        request.condition.market_Share.marketShareQuantity = ArrayList()//购买方市场占有率，市场份额
        request.condition.netProfit = ArrayList()//买方净利润
        request.condition.otherKeyIndex = ArrayList()//买方其他关键指标
        request.condition.price = ArrayList() //买方交易价格
        request.condition.product_productWords = ArrayList() //买方产品名称
        request.condition.profitMargin = ArrayList()  //买方利润率
        request.condition.publisherInfo = PublisherInfoBean()
        request.condition.publisherInfo.publisher_type = ArrayList()//买方发布者信息
        request.condition.turnover = ArrayList() //买方营业额
        request.condition.round = ArrayList()

        request.requirement = MatchCompanyRequestBean()//卖方自己的信息

        //对于所有数字类型的，对对方的需求是填区间，对自己的描述是填数字

        request.requirement.ebitda = ArrayList()//卖方ebitda
        request.requirement.industry_Classification = ArrayList()//卖方行业名称
        request.requirement.location = ArrayList()//卖方方地理位置
        request.requirement.market_Share = MarketShareBean()
        request.requirement.market_Share.marketShareQuantity = ArrayList()//卖方市场占有率，市场份额
        request.requirement.netProfit = ArrayList()//卖方净利润
        request.requirement.otherKeyIndex = ArrayList()//卖方其他关键指标
        request.requirement.price = ArrayList() //卖方交易价格
        request.requirement.product_productWords = ArrayList() //卖方产品名称
        request.requirement.profitMargin = ArrayList()  //卖方利润率
        request.requirement.publisherInfo = PublisherInfoBean()
        request.requirement.publisherInfo.publisher_type = ArrayList()//卖方发布者信息
        request.requirement.turnover = ArrayList() //卖方营业额
        request.requirement.round = ArrayList()

        request.condition.info_source = ""
        request.requirement.info_source = ""
        request.condition.entity_type = ""
    }

    private fun matchCompany() {
        HttpHelper.reMatchWrongProject(request).subscribe({ matchCompanyResponse ->
            removeLoadingFragment()
            onBackPressed()
            val intent = Intent(this@ProjectWrongReMatchActivity, MainActivity::class.java)
            intent.putExtra(getString(R.string.TITLE), request!!.projectName)
            intent.putExtra(getString(R.string.TYPE), getString(R.string.TYPE_0))
            intent.putExtra(getString(R.string.result), matchCompanyResponse)
            startActivity(intent)
        }) {
            removeLoadingFragment()
            showThrowable(it)
        }
    }

}