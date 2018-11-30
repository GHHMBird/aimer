package com.buyint.mergerbot.UIs.match.RecyclerAdapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.buyint.mergerbot.R;
import com.buyint.mergerbot.UIs.match.activity.SimilarCompanyOrMechanismActivity;
import com.buyint.mergerbot.dto.SimilarCompanyBean;
import com.buyint.mergerbot.dto.SimilarMechanismBean;

import java.util.ArrayList;

/**
 * Created by CXC on 2018/4/25
 */

public class MatchDetailAdapter extends RecyclerView.Adapter<MatchDetailAdapter.ViewHolder> {

    private ArrayList<SimilarCompanyBean> companyList = new ArrayList<>();
    private ArrayList<SimilarMechanismBean> mechanismList = new ArrayList<>();
    private Context context;
    private int type = 0;//0 相似公司  1相似机构

    public MatchDetailAdapter(Context context) {
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_match_detail, null, false));
    }

    public void setData(ArrayList<SimilarCompanyBean> list, ArrayList<SimilarMechanismBean> list2) {
        this.companyList = list;
        this.mechanismList = list2;
        notifyDataSetChanged();
    }

    public int setType(int type) {
        this.type = type;
        notifyDataSetChanged();
        if (type == 0) {
            return companyList.size();
        } else {
            return mechanismList.size();
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (type == 0) {

            if (companyList.size() == 1) {
                holder.topLine.setVisibility(View.GONE);
                holder.bottomLine.setVisibility(View.GONE);
            } else {
                if (position == 0) {
                    holder.topLine.setVisibility(View.GONE);
                } else if (position == companyList.size() - 1) {
                    holder.bottomLine.setVisibility(View.GONE);
                }
            }

            holder.tv1.setText(companyList.get(position).name);
            holder.tv2.setText(companyList.get(position).descrip);

            holder.itemView.setOnClickListener(v -> {
                Intent intent = new Intent(context, SimilarCompanyOrMechanismActivity.class);
                intent.putExtra(context.getString(R.string.TYPE), context.getString(R.string.TYPE_0));
                intent.putExtra(context.getString(R.string.DATA), companyList.get(position));
                context.startActivity(intent);
            });
        } else {

            if (mechanismList.size() == 1) {
                holder.topLine.setVisibility(View.GONE);
                holder.bottomLine.setVisibility(View.GONE);
            } else {
                if (position == 0) {
                    holder.topLine.setVisibility(View.GONE);
                } else if (position == mechanismList.size() - 1) {
                    holder.bottomLine.setVisibility(View.GONE);
                }
            }

            holder.tv1.setText(mechanismList.get(position).name);
            holder.tv2.setText(mechanismList.get(position).descrip);

            holder.itemView.setOnClickListener(v -> {
                Intent intent = new Intent(context, SimilarCompanyOrMechanismActivity.class);
                intent.putExtra(context.getString(R.string.TYPE), context.getString(R.string.TYPE_1));
                intent.putExtra(context.getString(R.string.DATA), mechanismList.get(position));
                context.startActivity(intent);
            });
        }
    }

    @Override
    public int getItemCount() {
        if (type == 0) {
            return companyList.size();
        } else {
            return mechanismList.size();
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tv1;
        private TextView tv2;
        private View topLine, bottomLine;

        public ViewHolder(View itemView) {
            super(itemView);
            tv1 = itemView.findViewById(R.id.item_match_detail_tv1);
            tv2 = itemView.findViewById(R.id.item_match_detail_tv2);
            topLine = itemView.findViewById(R.id.item_match_detail_line_top);
            bottomLine = itemView.findViewById(R.id.item_match_detail_line_bottom);
        }
    }
}
