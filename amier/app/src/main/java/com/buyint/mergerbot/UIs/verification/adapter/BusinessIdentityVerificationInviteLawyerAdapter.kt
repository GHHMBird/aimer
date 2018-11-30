package com.buyint.mergerbot.UIs.verification.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.buyint.mergerbot.R
import com.buyint.mergerbot.dto.LayerListBean
import kotlinx.android.synthetic.main.item_invite_lawyer.view.*

/**
 * Created by huheming on 2018/8/7
 */
class BusinessIdentityVerificationInviteLawyerAdapter(var context: Context, var list: ArrayList<LayerListBean> = ArrayList(),
                                                      var callBack: (Int) -> Unit) : RecyclerView.Adapter<BusinessIdentityVerificationInviteLawyerAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_invite_lawyer, null, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder!!.itemView.apply {
            Glide.with(context).load("").error(R.mipmap.default_user).placeholder(R.mipmap.default_user).into(item_invite_lawyer_iv)
            item_invite_lawyer_name.text = list.get(position).name
            item_invite_lawyer_company.text = "${list.get(position).company}|${list.get(position).position}"
            item_invite_lawyer_come_from_tv.text = "来自${list.get(position).source}"
            item_invite_lawyer_invite.text = "邀请"
            Glide.with(context).load("").error(R.mipmap.default_user).placeholder(R.mipmap.default_user).into(item_invite_lawyer_come_from_iv)

            item_invite_lawyer_invite.setOnClickListener {

                callBack(position)

            }
        }
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}