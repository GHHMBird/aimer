package com.buyint.mergerbot.UIs.match.RecyclerAdapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.buyint.mergerbot.R
import com.buyint.mergerbot.dto.NameTitleShareBean
import com.makeramen.roundedimageview.RoundedImageView
import java.util.*

/**
 * Created by huheming on 2018/6/6
 */
class TeamMembersAdapter(val context: Context, val list: ArrayList<NameTitleShareBean>, var isShow: Boolean) : RecyclerView.Adapter<TeamMembersAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_match_detail, null))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var s = ""
        s += if (!TextUtils.isEmpty(list[position].name)) {
            list[position].name
        } else {
            context.getString(R.string.unknown_user)
        }
        if (!TextUtils.isEmpty(list[position].title)) {
            s += " | " + list[position].title
        }
        holder.tvName.text = s
        if (isShow) {
            if (list[position].share != (-1).toDouble()) {
                holder.tvDesc.text = context.getString(R.string.the_investor_accounts_for_the_share_of_the_stock) + list[position].share + "%"
            } else {
                holder.tvDesc.text = context.getString(R.string.unknown_the_investor_accounts_for_the_share_of_the_stock)
            }
        } else {
            holder.tvDesc.text = ""
        }
        if (position == 0) {
            holder.lineTop.visibility = View.GONE
        } else {
            holder.lineTop.visibility = View.VISIBLE
        }
        if (position == list.size - 1) {
            holder.lineBottom.visibility = View.GONE
        } else {
            holder.lineBottom.visibility = View.VISIBLE
        }
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val lineTop = itemView.findViewById(R.id.item_match_detail_line_top) as View
        val cicle = itemView.findViewById(R.id.item_match_detail_cicle) as ImageView
        val lineBottom = itemView.findViewById(R.id.item_match_detail_line_bottom) as View
        val tvName: TextView = itemView.findViewById(R.id.item_match_detail_tv1) as TextView
        val tvDesc: TextView = itemView.findViewById(R.id.item_match_detail_tv2) as TextView
    }
}