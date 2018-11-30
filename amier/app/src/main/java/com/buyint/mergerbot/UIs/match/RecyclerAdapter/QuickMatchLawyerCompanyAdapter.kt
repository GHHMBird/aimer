package com.buyint.mergerbot.UIs.match.RecyclerAdapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.buyint.mergerbot.R
import com.buyint.mergerbot.dto.GetLawyerCompanyBean

/**
 * Created by huheming on 2018/8/15
 */
class QuickMatchLawyerCompanyAdapter(var context: Context, var list: ArrayList<GetLawyerCompanyBean>, var listener: QuickMatchNameDetailAdapterListener?) : RecyclerView.Adapter<QuickMatchLawyerCompanyAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_quick_match, null, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = list[position]
        holder.textView.text = model.name
        holder.itemView.setOnClickListener {
            if (listener != null) {
                listener!!.choose(model)
            }
        }
    }

    fun setListData(list: ArrayList<GetLawyerCompanyBean>) {
        this.list = list
        notifyDataSetChanged()
    }

    interface QuickMatchNameDetailAdapterListener {
        fun choose(bean: GetLawyerCompanyBean)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textView = itemView.findViewById(R.id.item_quick_match_tv) as TextView
    }
}