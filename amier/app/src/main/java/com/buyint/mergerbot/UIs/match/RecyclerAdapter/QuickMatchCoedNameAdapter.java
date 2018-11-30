package com.buyint.mergerbot.UIs.match.RecyclerAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.buyint.mergerbot.R;
import com.buyint.mergerbot.dto.CodeNameBean;

import java.util.ArrayList;

/**
 * Created by CXC on 2018/4/17
 */

public class QuickMatchCoedNameAdapter extends RecyclerView.Adapter<QuickMatchCoedNameAdapter.QuickMatchHolder> {

    private QuickMatchCodeNameAdapterListener listener;
    private Context context;
    private ArrayList<CodeNameBean> list = new ArrayList<>();

    public QuickMatchCoedNameAdapter(Context context, QuickMatchCodeNameAdapterListener listener) {
        this.context = context;
        this.listener = listener;
    }

    public void setList(ArrayList<CodeNameBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public QuickMatchHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new QuickMatchHolder(LayoutInflater.from(context).inflate(R.layout.item_quick_match, null));
    }

    @Override
    public void onBindViewHolder(QuickMatchHolder holder, int position) {
        CodeNameBean bean = list.get(position);
        holder.tv.setText(bean.name);
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

        public QuickMatchHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.item_quick_match_tv);
        }
    }

    public interface QuickMatchCodeNameAdapterListener {
        void click(CodeNameBean bean);
    }
}

