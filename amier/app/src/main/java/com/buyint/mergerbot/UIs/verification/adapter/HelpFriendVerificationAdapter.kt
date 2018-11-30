package com.buyint.mergerbot.UIs.verification.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.buyint.mergerbot.R
import kotlinx.android.synthetic.main.item_invite_lawyer.view.*

/**
 * Created by huheming on 2018/8/7
 */
class HelpFriendVerificationAdapter(var context: Context) : RecyclerView.Adapter<HelpFriendVerificationAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_invite_lawyer, null, false))
    }

    override fun getItemCount(): Int {
        return 10
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder!!.itemView.apply {
            Glide.with(context).load("").error(R.mipmap.default_user).placeholder(R.mipmap.default_user).into(item_invite_lawyer_iv)
            item_invite_lawyer_name.text = "任海超"
            item_invite_lawyer_company.text = "任海超市|厕所保洁员"
            item_invite_lawyer_invite.text = "帮助认证"
            item_invite_lawyer_come_from_iv.visibility = View.GONE
            item_invite_lawyer_come_from_tv.visibility = View.GONE
        }
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}