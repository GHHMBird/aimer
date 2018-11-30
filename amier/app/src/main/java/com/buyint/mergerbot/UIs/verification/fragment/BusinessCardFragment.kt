package com.buyint.mergerbot.UIs.verification.fragment

import android.annotation.SuppressLint
import android.app.DialogFragment
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.view.WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.buyint.mergerbot.R
import com.buyint.mergerbot.Utility.AnimationUtils
import com.buyint.mergerbot.dto.MatchRecordListModel

@SuppressLint("ValidFragment")
/**
 * Created by huheming on 2018/8/16
 */
class BusinessCardFragment(var model: MatchRecordListModel) : DialogFragment() {

    private lateinit var mRootView: View
    var name: TextView? = null
    var company: TextView? = null
    private var rate: TextView? = null
    private var projectNum: TextView? = null
    private var clientNum: TextView? = null
    private var friendNum: TextView? = null
    private var transactionName: View? = null
    private var more: LinearLayout? = null
    private var clickView: View? = null
    private var text1: TextView? = null
    private var text2: TextView? = null
    private var news: TextView? = null
    private var projectll: View? = null
    private var clientll: View? = null
    private var friendll: View? = null
    private var line2: View? = null
    private var colorView: View? = null


    override fun onStart() {
        super.onStart()
        val window = dialog.window
        val params = window!!.attributes
        params.gravity = Gravity.CENTER_VERTICAL
        params.softInputMode = SOFT_INPUT_ADJUST_RESIZE
        params.width = WindowManager.LayoutParams.MATCH_PARENT
        window.attributes = params
        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        mRootView = inflater?.inflate(R.layout.fragment_business_card, container, false)!!
        AnimationUtils.slideToUp(mRootView)
        val ivX = mRootView.findViewById(R.id.fragment_business_card_x) as ImageView
        mRootView.setOnClickListener { dismiss() }
        ivX.setOnClickListener { dismiss() }

        colorView = mRootView.findViewById(R.id.item_match_record_app_color_view)
        name = mRootView.findViewById(R.id.item_match_record_name)
        company = mRootView.findViewById(R.id.item_match_record_company)
        rate = mRootView.findViewById(R.id.item_match_record_match_rate)
        projectNum = mRootView.findViewById(R.id.item_match_record_match_project)
        clientNum = mRootView.findViewById(R.id.item_match_record_client)
        friendNum = mRootView.findViewById(R.id.item_match_record_friend)
        transactionName = mRootView.findViewById(R.id.item_match_record_transaction_name)
        projectll = mRootView.findViewById(R.id.item_match_record_match_project_ll)
        clientll = mRootView.findViewById(R.id.item_match_record_client_ll)
        friendll = mRootView.findViewById(R.id.item_match_record_friend_ll)
        more = mRootView.findViewById(R.id.item_match_record_more)
        clickView = mRootView.findViewById(R.id.item_match_record_itemview)
        news = mRootView.findViewById(R.id.item_match_record_client_news)
        text1 = mRootView.findViewById(R.id.item_match_record_match_project_tv)
        text2 = mRootView.findViewById(R.id.item_match_record_friend_tv)
        line2 = mRootView.findViewById(R.id.item_match_record_line2)

        setData()

        return mRootView
    }

    private fun setData() {
        name!!.text = model.userName

        var s = ""
        s += if (!TextUtils.isEmpty(model.companyName)) {
            model.companyName + "|"
        } else {
            activity.getString(R.string.undisclosed_company) + "|"
        }
        s += if (!TextUtils.isEmpty(model.position)) {
            model.position
        } else {
            activity.getString(R.string.undisclosed_position)
        }
        company!!.text = s
        rate!!.text = model.reliableRate!!.toInt().toString()
        rate!!.typeface = Typeface.createFromAsset(activity.assets, "fonts/hwcy.ttf")
        projectNum!!.text = model.project.toString()
        clientNum!!.text = model.company.toString()
        friendNum!!.text = model.businessFriend.toString()

        more!!.visibility = View.GONE

        news!!.text = model.latestnew

        clickView!!.setOnClickListener {
//            transactionName!!.transitionName = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
//            val intent = Intent(activity, MatchRecordDetailActivity::class.java)
//            val p2 = android.util.Pair(transactionName, "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa")
//            val options = ActivityOptions.makeSceneTransitionAnimation(activity as Activity, p2)
//            intent.putExtra(activity.getString(R.string.DATA), model)
//            intent.putExtra(activity.getString(R.string.TYPE), 0)
//            activity.startActivity(intent, options.toBundle())
        }

        projectll!!.setOnClickListener {
//            transactionName!!.transitionName = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
//            val intent = Intent(activity, MatchRecordDetailActivity::class.java)
//            val p2 = android.util.Pair(transactionName, "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa")
//            val options = ActivityOptions.makeSceneTransitionAnimation(activity as Activity, p2)
//            intent.putExtra(activity.getString(R.string.DATA), model)
//            intent.putExtra(activity.getString(R.string.TYPE), 0)
//            activity.startActivity(intent, options.toBundle())
        }

        clientll!!.setOnClickListener {
//            transactionName!!.transitionName = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
//            val intent = Intent(activity, MatchRecordDetailActivity::class.java)
//            val p2 = android.util.Pair(transactionName, "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa")
//            val options = ActivityOptions.makeSceneTransitionAnimation(activity as Activity, p2)
//            intent.putExtra(activity.getString(R.string.DATA), model)
//            intent.putExtra(activity.getString(R.string.TYPE), 2)
//            activity.startActivity(intent, options.toBundle())
        }

        friendll!!.setOnClickListener {
//            transactionName!!.transitionName = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
//            val intent = Intent(activity, MatchRecordDetailActivity::class.java)
//            val p2 = android.util.Pair(transactionName, "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa")
//            val options = ActivityOptions.makeSceneTransitionAnimation(activity as Activity, p2)
//            intent.putExtra(activity.getString(R.string.DATA), model)
//            intent.putExtra(activity.getString(R.string.TYPE), 1)
//            activity.startActivity(intent, options.toBundle())
        }

    }
}