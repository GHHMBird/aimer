package com.buyint.mergerbot.UIs.match.RecyclerAdapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.buyint.mergerbot.R;
import com.buyint.mergerbot.dto.CodeTitleBean;

import java.util.ArrayList;

/**
 * Created by CXC on 2018/4/17
 */

public class QuickMatchCoedTitleAdapter extends RecyclerView.Adapter<QuickMatchCoedTitleAdapter.QuickMatchHolder> {

    private QuickMatchCodeTitleAdapterListener listener;
    private Context context;
    private ArrayList<CodeTitleBean> list = new ArrayList<>();

    public QuickMatchCoedTitleAdapter(Context context, QuickMatchCodeTitleAdapterListener listener) {
        this.context = context;
        this.listener = listener;
    }

    public void setList(ArrayList<CodeTitleBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public QuickMatchHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new QuickMatchHolder(LayoutInflater.from(context).inflate(R.layout.item_quick_match, null,false));
    }

    @Override
    public void onBindViewHolder(@NonNull QuickMatchHolder holder, int position) {
        CodeTitleBean bean = list.get(position);
        holder.tv.setText(bean.name.get(0));
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.click(bean);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void clearData() {
        list.clear();
        notifyDataSetChanged();
    }

    class QuickMatchHolder extends RecyclerView.ViewHolder {
        private TextView tv;

         QuickMatchHolder(View itemView) {
            super(itemView);
            tv =  itemView.findViewById(R.id.item_quick_match_tv);
        }
    }

    public interface QuickMatchCodeTitleAdapterListener {
        void click(CodeTitleBean bean);
    }
}

