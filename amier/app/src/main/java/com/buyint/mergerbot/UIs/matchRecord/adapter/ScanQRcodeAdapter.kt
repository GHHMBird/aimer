package com.buyint.mergerbot.UIs.matchRecord.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.buyint.mergerbot.R
import com.buyint.mergerbot.dto.GetFriendListBean
import com.buyint.mergerbot.helper.HttpHelper
import com.buyint.mergerbot.rx.RequestErrorThrowable

/**
 * Created by huheming on 2018/9/3
 */
class ScanQRcodeAdapter(var context: Context, var list: ArrayList<GetFriendListBean>) : RecyclerView.Adapter<ScanQRcodeAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_scan_qr_code, null, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tv!!.text = list[position].projectName
        holder.notice!!.setOnClickListener {
            if (list[position].attention) {
                //取关
                unNotice(list[position])
            } else {
                //关注
                notice(list[position])
            }
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tv: TextView? = null
        var notice: View? = null

        init {
            tv = itemView.findViewById(R.id.item_scan_qr_code_tv) as TextView
            notice = itemView.findViewById(R.id.item_scan_qr_code_notice) as TextView
        }
    }

    private fun unNotice(bean: GetFriendListBean) {
        HttpHelper.notNoticeProject(bean.projectId).subscribe({ booleanResponse ->
            if (booleanResponse.data) {
                Toast.makeText(context, "已取消关注", Toast.LENGTH_SHORT).show()
                bean.attention = false
            } else {
                Toast.makeText(context, "取消关注失败，请稍后再试", Toast.LENGTH_SHORT).show()
            }
        }) { throwable ->
            if (throwable is RequestErrorThrowable) {
                Toast.makeText(context, throwable.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun notice(bean: GetFriendListBean) {
        HttpHelper.noticeProject(bean.projectId).subscribe({ booleanResponse ->
            if (booleanResponse.data) {
                Toast.makeText(context, "关注成功", Toast.LENGTH_SHORT).show()
                bean.attention = true
            } else {
                Toast.makeText(context, "关注失败，请稍后再试", Toast.LENGTH_SHORT).show()
            }
        }) { throwable ->
            if (throwable is RequestErrorThrowable) {
                Toast.makeText(context, throwable.message, Toast.LENGTH_SHORT).show()
            }
        }
    }
}