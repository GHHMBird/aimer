package com.buyint.mergerbot.UIs.verification.fragment

import android.annotation.SuppressLint
import android.app.DialogFragment
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import com.buyint.mergerbot.R
import com.buyint.mergerbot.Utility.AnimationUtils
import com.buyint.mergerbot.dto.RelationNode

@SuppressLint("ValidFragment")
/**
 * Created by huheming on 2018/8/16
 */
class CompanyLineCardFragment(var model: RelationNode) : DialogFragment() {

    private lateinit var mRootView: View
    private var title: TextView? = null
    private var body: TextView? = null

    override fun onStart() {
        super.onStart()
        val window = dialog.window
        val params = window!!.attributes
        params.gravity = Gravity.CENTER_VERTICAL
        params.softInputMode = WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
        params.width = WindowManager.LayoutParams.MATCH_PARENT
        window.attributes = params
        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        mRootView = inflater?.inflate(R.layout.fragment_company_line_card, container, false)!!
        AnimationUtils.slideToUp(mRootView)
        val ivX = mRootView.findViewById(R.id.fragment_company_line_card_x) as ImageView
        mRootView.setOnClickListener { dismiss() }
        ivX.setOnClickListener { dismiss() }

        title = mRootView.findViewById(R.id.fragment_company_line_card_title) as TextView
        body = mRootView.findViewById(R.id.fragment_company_line_card_body) as TextView

        setData()

        return mRootView
    }

    private fun setData() {
        title!!.text = model.date
        body!!.text = model.id!!
    }
}