package com.buyint.mergerbot.UIs.user.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.buyint.mergerbot.R

/**
 * Created by huheming on 2018/7/11
 */
class HistoryDealAdapter(var context: Context, val list: ArrayList<String>) : RecyclerView.Adapter<HistoryDealAdapter.HistoryDealViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryDealViewHolder {
        return HistoryDealViewHolder(View.inflate(context, R.layout.item_history_deal, null))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: HistoryDealViewHolder, position: Int) {
        holder!!.title.text = "阿里巴巴150亿投资分众传媒，成为第二大股东"
        holder!!.time.text = "2018"
        holder!!.desc.text = "7月19日，分众传媒发布公告称：阿里巴巴集团及其关联方将以约150亿人民币战略入股分众，双方将共同探索新零售大趋势下数字营销的模式创新。"
        holder!!.other.text = "相关双方：阿里巴巴，分众传媒"
    }


    class HistoryDealViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        val title = itemView!!.findViewById(R.id.item_history_deal_title) as TextView
        val time = itemView!!.findViewById(R.id.item_history_deal_time) as TextView
        val desc = itemView!!.findViewById(R.id.item_history_deal_desc) as TextView
        val other = itemView!!.findViewById(R.id.item_history_deal_other) as TextView
    }
}