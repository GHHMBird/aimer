package com.buyint.mergerbot.UIs.main.adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.buyint.mergerbot.R
import com.buyint.mergerbot.UIs.trainAimer.activity.TrainAimerChangeTextActivity
import com.buyint.mergerbot.dto.QAHistoryBean
import java.text.SimpleDateFormat

/**
 * Created by huheming on 2018/9/7
 */
class QAHistoryAdapter(var context: Context, var list: ArrayList<QAHistoryBean>) : RecyclerView.Adapter<QAHistoryAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_qa_history, null, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val bean = list[position]
        if (!TextUtils.isEmpty(bean.projectName)) {
            holder.tvTitle!!.text = bean.projectName
        } else {
            holder.tvTitle!!.text = context.getString(R.string.no_title_now)
        }
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        holder.tvTime!!.text = sdf.format(bean.createTime)

        if (bean.projectCategory == 0) {
            holder.tvCome!!.text = context.getString(R.string.come_from_quick_match)
        } else {
            holder.tvCome!!.text = context.getString(R.string.come_from_deep_match)
        }

        holder.tvResult!!.text = context.getString(R.string.match_result_start) + bean.result + context.getString(R.string.match_result_end)

        holder.itemView.setOnClickListener {
            val intent = Intent(context, TrainAimerChangeTextActivity::class.java)
            intent.putExtra(context.getString(R.string.DATA), bean.qid)
            context.startActivity(intent)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvTitle: TextView? = null
        var tvTime: TextView? = null
        var tvCome: TextView? = null
        var tvResult: TextView? = null

        init {
            tvTitle = itemView.findViewById(R.id.item_qa_history_title) as TextView
            tvTime = itemView.findViewById(R.id.item_qa_history_time) as TextView
            tvCome = itemView.findViewById(R.id.item_qa_history_come) as TextView
            tvResult = itemView.findViewById(R.id.item_qa_history_result) as TextView
        }
    }
}