package com.buyint.mergerbot.UIs.main.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.buyint.mergerbot.R;

/**
 * Created by huheming on 2018/6/5
 */

public class BttomGridAdapter extends BaseAdapter {

    private boolean isFinish;
    private Context context;
    private int[] strings = new int[]{R.string.secret_match_project, R.string.wisdom_in_opportunity, R.string.match_record};
    private int[] pics = new int[]{R.mipmap.bottom_grid_adapter_1, R.mipmap.bottom_grid_adapter_2, R.mipmap.bottom_grid_adapter_3};
    private GotoListener listener;

    public BttomGridAdapter(Context context, boolean isFinish) {
        this.context = context;
        this.isFinish = isFinish;
    }

    @Override
    public int getCount() {
        return strings.length;
    }

    @Override
    public Object getItem(int position) {
        return strings[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.item_bottom, null);
        TextView tvBottom = inflate.findViewById(R.id.item_bottom_tv);
        ImageView ivBottom = inflate.findViewById(R.id.item_bottom_iv);
        tvBottom.setText(context.getString(strings[position]));
        Glide.with(context).load(pics[position]).into(ivBottom);
        inflate.setOnClickListener(v -> {
            /*if (position == 0) {
                if (isFinish) {
                    Intent intent = new Intent(context, MainActivity.class);
                    intent.putExtra(context.getString(R.string.TYPE), context.getString(R.string.quick_match));
                    context.startActivity(intent);
                    ((Activity) context).finish();
                } else if (listener != null) {
                    listener.click(context.getString(R.string.quick_match));
                }
            } else if (position == 1) {
                LoginResponse response = DBUtilsKt.getLoginResponse(context);
                if (!TextUtils.isEmpty(response.model.matchRecordPersonId)) {
//                    context.startActivity(new Intent(context, IntellectualRelationshipActivity.class));
                    Intent intent = new Intent(context, WebActivity.class);
                    intent.putExtra(context.getString(R.string.TITLE), "智配关系");
                    intent.putExtra("params", "http://117.184.66.210:8091/relation/#/");
                    context.startActivity(intent);
                } else {
                    context.startActivity(new Intent(context, MatchRecordSplashActivity.class));
                }
                if (isFinish) {
                    ((Activity) context).finish();
                }
            } else if (position == 2) {
                LoginResponse response = DBUtilsKt.getLoginResponse(context);
                if (!TextUtils.isEmpty(response.model.matchRecordPersonId)) {
                    context.startActivity(new Intent(context, MatchRecordActivity.class));
                } else {
                    context.startActivity(new Intent(context, MatchRecordSplashActivity.class));
                }
                if (isFinish) {
                    ((Activity) context).finish();
                }
            }*/
        });
        return inflate;
    }

    public void setGotoListener(GotoListener listener) {
        this.listener = listener;
    }

    public interface GotoListener {
        void click(String type);
    }
}
