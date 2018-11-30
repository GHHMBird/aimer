package com.buyint.mergerbot.UIs.matchRecord.activity;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.buyint.mergerbot.R;
import com.buyint.mergerbot.dto.CheckStringBean;

import java.util.ArrayList;

/**
 * Created by huheming on 2018/10/15
 */

public class MatchRecordExpandableAdapter extends BaseExpandableListAdapter {

    private Context context;
    private ArrayList<ArrayList<CheckStringBean>> childList;
    private ArrayList<String> mainList;

    public MatchRecordExpandableAdapter(Context context, ArrayList<String> mainList, ArrayList<ArrayList<CheckStringBean>> childList) {
        this.context = context;
        this.mainList = mainList;
        this.childList = childList;
    }

    @Override
    public int getGroupCount() {
        return mainList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return childList.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mainList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return childList.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_match_record_elv_main, null, false);
        }
        ImageView iv = convertView.findViewById(R.id.item_match_record_elv_main_iv);
        TextView tvLeft = convertView.findViewById(R.id.item_match_record_elv_main_tv_left);
        TextView tvRight = convertView.findViewById(R.id.item_match_record_elv_main_tv_right);
        tvLeft.setText(mainList.get(groupPosition));
        ArrayList<CheckStringBean> beans = childList.get(groupPosition);
        for (CheckStringBean b : beans) {
            if (b.isCheck) {
                tvRight.setText(b.text);
            }
        }
        if (isExpanded) {
            iv.setImageResource(R.mipmap.arrow_up);
        } else {
            iv.setImageResource(R.mipmap.arrow_down);
        }
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_match_record_elv_child, null, false);
        }
        TextView tv = convertView.findViewById(R.id.item_match_record_elv_child_tv);
        CheckStringBean bean = childList.get(groupPosition).get(childPosition);
        tv.setText(bean.text);
        if (bean.isCheck) {
            tv.setTextColor(Color.WHITE);
            tv.setBackground(ContextCompat.getDrawable(context, R.drawable.item_app_rentage_corner));
        } else {
            tv.setTextColor(Color.BLACK);
            tv.setBackground(ContextCompat.getDrawable(context, R.drawable.item_f6f7f8_rentage_corner));
        }
        tv.setOnClickListener(v -> {
            for (int i = 0; i < childList.get(groupPosition).size(); i++) {
                CheckStringBean stringBean = childList.get(groupPosition).get(i);
                stringBean.isCheck = false;
            }
            bean.isCheck = true;
            notifyDataSetChanged();
        });

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    public ArrayList<ArrayList<CheckStringBean>> getChildList() {
        return childList;
    }

    public void reSetList(ExpandableListView elv, ArrayList<String> mainList, ArrayList<ArrayList<CheckStringBean>> childList) {
        this.mainList = mainList;
        this.childList = childList;
        notifyDataSetChanged();
        for (int i = 0; i < mainList.size(); i++) {
            elv.collapseGroup(i);
            elv.expandGroup(i);
        }
    }
}
