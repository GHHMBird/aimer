package com.buyint.mergerbot.UIs.user.fragment

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.*
import android.view.WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
import android.widget.RelativeLayout
import android.widget.TextView
import com.buyint.mergerbot.R
import com.buyint.mergerbot.Utility.AnimationUtils


/**
 * Created by huheming on 2018/6/27
 */
@SuppressLint("ValidFragment")
class SetUserIconFragment(val listener: SetUserIconFragmentListener) : DialogFragment() {

    private var mRootView: View? = null
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

    override fun dismiss() {
        AnimationUtils.slideToDown(mRootView) { super@SetUserIconFragment.dismiss() }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        mRootView = inflater.inflate(R.layout.fragment_set_user_icon, container, false)
        AnimationUtils.slideToUp(mRootView)

        val cancel: TextView = mRootView!!.findViewById(R.id.fragment_set_user_icon_cancel)
        val rl: RelativeLayout = mRootView!!.findViewById(R.id.fragment_set_user_icon_rl)
        val photo:TextView = mRootView!!.findViewById(R.id.fragment_set_user_icon_photo)
        val picture :TextView= mRootView!!.findViewById(R.id.fragment_set_user_icon_picture)

        cancel.setOnClickListener { dismiss() }
        rl.setOnClickListener { dismiss() }
        photo.setOnClickListener {
            listener.photoClick()
            dismiss()
        }
        picture.setOnClickListener {
            listener.pictureClick()
            dismiss()
        }

        return mRootView
    }

    interface SetUserIconFragmentListener {
        fun photoClick()
        fun pictureClick()
    }
}