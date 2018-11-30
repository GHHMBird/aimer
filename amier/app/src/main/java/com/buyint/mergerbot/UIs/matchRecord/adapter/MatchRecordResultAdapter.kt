package com.buyint.mergerbot.UIs.matchRecord.adapter

import android.app.Activity
import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.buyint.mergerbot.R
import com.buyint.mergerbot.UIs.matchRecord.activity.MatchRecordDetailActivity
import com.buyint.mergerbot.UIs.matchRecord.activity.MatchRecordMoreActivity
import com.buyint.mergerbot.Utility.ViewHelper
import com.buyint.mergerbot.base.BaseActivity
import com.buyint.mergerbot.database.getLoginResponse
import com.buyint.mergerbot.dto.MatchRecordListModel
import com.buyint.mergerbot.enums.RelationType
import com.buyint.mergerbot.helper.HttpHelper
import com.buyint.mergerbot.rx.RequestErrorThrowable


/**
 * Created by huheming on 2018/7/25
 */
class MatchRecordResultAdapter(var context: Context, var all: Boolean, var list: ArrayList<MatchRecordListModel>?) : RecyclerView.Adapter<MatchRecordResultAdapter.ViewHolder>() {

    val response = getLoginResponse(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_match_record, null, false))
    }

    override fun getItemCount(): Int {
        return list!!.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        setViewType0Data(list, holder, position)
    }

    private fun setViewType0Data(list: ArrayList<MatchRecordListModel>?, holder: ViewHolder, position: Int) {
        val model = list!![position]
        holder.name!!.text = model.userName

        if (model.relationType == RelationType.WORK_MATE_AND_BUSINESS_FRIEND.name) {
            holder.relationType!!.visibility = View.VISIBLE
        } else {
            holder.relationType!!.visibility = View.GONE
        }

        var s = ""
        s += if (!TextUtils.isEmpty(model.companyName)) {
            model.companyName + " | "
        } else {
            context.getString(R.string.undisclosed_company) + " | "
        }
        s += if (!TextUtils.isEmpty(model.position)) {
            model.position
        } else {
            context.getString(R.string.undisclosed_position)
        }
        holder.company!!.text = s

        holder.rate!!.text = model.reliableRate!!.toInt().toString()

        holder.news!!.text = model.latestnew

        when {
            BaseActivity.fontScale == 0.8f -> holder.company!!.maxLines = 2
            BaseActivity.fontScale == 0.9f -> holder.company!!.maxLines = 2
            BaseActivity.fontScale == 1.0f -> holder.company!!.maxLines = 2
            BaseActivity.fontScale == 1.1f -> holder.company!!.maxLines = 2
            BaseActivity.fontScale == 1.2f -> holder.company!!.maxLines = 1
        }

        holder.more!!.visibility = View.VISIBLE

        holder.more!!.setOnClickListener {
            val intent = Intent(context, MatchRecordMoreActivity::class.java)
            intent.putExtra(context.getString(R.string.DATA), model)
            context.startActivity(intent)
        }

        if (BaseActivity.themeColor == 3) {
            holder.colorView!!.setBackgroundResource(R.drawable.red_left)
        } else {
            holder.colorView!!.setBackgroundResource(R.drawable.item_left_app_color)
        }

        holder.line2!!.visibility = View.VISIBLE
        holder.clientll!!.visibility = View.VISIBLE
        holder.projectNum!!.text = model.project.toString()
        holder.clientNum!!.text = model.company.toString()
        holder.friendNum!!.text = model.businessFriend.toString()
        holder.text1!!.text = context.getString(R.string.matching_his_project)
        holder.text2!!.text = context.getString(R.string.business_friend)

        holder.clickView!!.setOnClickListener {
            val intent = Intent(context, MatchRecordDetailActivity::class.java)
            val p2 = android.util.Pair(holder.transactionName, "match_record_detail_trans")
            val options = ActivityOptions.makeSceneTransitionAnimation(context as Activity, p2)
            intent.putExtra(context.getString(R.string.DATA), model)
            intent.putExtra(context.getString(R.string.TYPE), 0)
            context.startActivity(intent, options.toBundle())
        }

        holder.projectll!!.setOnClickListener {
            val intent = Intent(context, MatchRecordDetailActivity::class.java)
            val p2 = android.util.Pair(holder.transactionName, "match_record_detail_trans")
            val options = ActivityOptions.makeSceneTransitionAnimation(context as Activity, p2)
            intent.putExtra(context.getString(R.string.DATA), model)
            intent.putExtra(context.getString(R.string.TYPE), 0)
            context.startActivity(intent, options.toBundle())
        }

        holder.clientll!!.setOnClickListener {
            val intent = Intent(context, MatchRecordDetailActivity::class.java)
            val p2 = android.util.Pair(holder.transactionName, "match_record_detail_trans")
            val options = ActivityOptions.makeSceneTransitionAnimation(context as Activity, p2)
            intent.putExtra(context.getString(R.string.DATA), model)
            intent.putExtra(context.getString(R.string.TYPE), 2)
            context.startActivity(intent, options.toBundle())
        }

        holder.friendll!!.setOnClickListener {
            val intent = Intent(context, MatchRecordDetailActivity::class.java)
            val p2 = android.util.Pair(holder.transactionName, "match_record_detail_trans")
            val options = ActivityOptions.makeSceneTransitionAnimation(context as Activity, p2)
            intent.putExtra(context.getString(R.string.DATA), model)
            intent.putExtra(context.getString(R.string.TYPE), 1)
            context.startActivity(intent, options.toBundle())
        }
    }

    private fun delete(model: MatchRecordListModel) {
        ViewHelper.showOneLineCard(context, context.getString(R.string.sure_to_delete_contact), context.getString(R.string.alright), context.getString(R.string.cancel), { _, _ ->
            HttpHelper.deleteMatchRecordPeople(model.personId).subscribe({
                if (it.data) {
                    list?.remove(model)
                    notifyDataSetChanged()
                } else {
                    Toast.makeText(context, context.getString(R.string.delete_fail), Toast.LENGTH_SHORT).show()
                }
            }, {
                if (it is RequestErrorThrowable) {
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
            })
        }, { _, _ -> })
    }

    fun call(phoneNumber: String?) {
        val intent = Intent(Intent.ACTION_DIAL)
        val uri = Uri.parse("tel:$phoneNumber")
        intent.data = uri
        context.startActivity(intent)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var name: TextView? = null
        var company: TextView? = null
        var rate: TextView? = null
        var projectNum: TextView? = null
        var clientNum: TextView? = null
        var friendNum: TextView? = null
        var verification: TextView? = null
        var text1: TextView? = null
        var text2: TextView? = null
        var news: TextView? = null
        var transactionName: View? = null
        var projectll: View? = null
        var clientll: View? = null
        var friendll: View? = null
        var more: LinearLayout? = null
        var clickView: View? = null
        var line2: View? = null
        var colorView: View? = null
        var relationType: View? = null

        init {
            colorView = itemView.findViewById(R.id.item_match_record_app_color_view)
            name = itemView.findViewById(R.id.item_match_record_name) as TextView
            company = itemView.findViewById(R.id.item_match_record_company) as TextView
            rate = itemView.findViewById(R.id.item_match_record_match_rate) as TextView
            projectNum = itemView.findViewById(R.id.item_match_record_match_project) as TextView
            clientNum = itemView.findViewById(R.id.item_match_record_client) as TextView
            friendNum = itemView.findViewById(R.id.item_match_record_friend) as TextView
            transactionName = itemView.findViewById(R.id.item_match_record_transaction_name)
            projectll = itemView.findViewById(R.id.item_match_record_match_project_ll)
            clientll = itemView.findViewById(R.id.item_match_record_client_ll)
            friendll = itemView.findViewById(R.id.item_match_record_friend_ll)
            more = itemView.findViewById(R.id.item_match_record_more)
            clickView = itemView.findViewById(R.id.item_match_record_itemview)
            news = itemView.findViewById(R.id.item_match_record_client_news)
            text1 = itemView.findViewById(R.id.item_match_record_match_project_tv)
            text2 = itemView.findViewById(R.id.item_match_record_friend_tv)
            line2 = itemView.findViewById(R.id.item_match_record_line2)
            relationType = itemView.findViewById(R.id.item_match_record_pic)
        }
    }

    fun setData(data: ArrayList<MatchRecordListModel>?) {
        this.list = data
        notifyDataSetChanged()
    }
}