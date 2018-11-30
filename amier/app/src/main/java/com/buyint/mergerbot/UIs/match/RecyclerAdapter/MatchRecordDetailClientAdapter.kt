package com.buyint.mergerbot.UIs.match.RecyclerAdapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.buyint.mergerbot.R
import com.buyint.mergerbot.dto.MatchRecordDetailClientModel
import kotlinx.android.synthetic.main.item_company.view.*

/**
 * Created by huheming on 2018/8/9
 */
class MatchRecordDetailClientAdapter(var context: Context, var list: ArrayList<MatchRecordDetailClientModel>) : RecyclerView.Adapter<MatchRecordDetailClientAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_company, null, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.apply {
            val model = list[position]
            item_company_name.text = model.name
            item_company_product.text = model.product
            item_company_place.text = model.address
            item_company_industry.text = model.industry
        }
    }

    class ViewHolder(item: View) : RecyclerView.ViewHolder(item)

    fun setData(data: ArrayList<MatchRecordDetailClientModel>) {
        this.list = data
        notifyDataSetChanged()
    }
}