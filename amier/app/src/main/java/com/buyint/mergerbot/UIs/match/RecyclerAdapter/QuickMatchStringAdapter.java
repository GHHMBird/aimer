package com.buyint.mergerbot.UIs.match.RecyclerAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.buyint.mergerbot.R;

import java.util.ArrayList;

/**
 * Created by CXC on 2018/4/17
 */

public class QuickMatchStringAdapter extends RecyclerView.Adapter<QuickMatchStringAdapter.QuickMatchHolder> {

    private QuickMatchStringAdapterListener listener;
    private Context context;
    private ArrayList<String> list = new ArrayList<>();

    public QuickMatchStringAdapter(Context context, QuickMatchStringAdapterListener listener) {
        this.context = context;
        this.listener = listener;
    }

    @Override
    public QuickMatchHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new QuickMatchHolder(LayoutInflater.from(context).inflate(R.layout.item_quick_match, null));
    }

    @Override
    public void onBindViewHolder(QuickMatchHolder holder, int position) {
        holder.tv.setText(list.get(position));
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.click(list.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public ArrayList<String> getList() {
        return list;
    }

    public void setList(ArrayList<String> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void clearData() {
        list.clear();
        notifyDataSetChanged();
    }

    class QuickMatchHolder extends RecyclerView.ViewHolder {
        private TextView tv;

        QuickMatchHolder(View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.item_quick_match_tv);
        }
    }

    public interface QuickMatchStringAdapterListener {
        void click(String text);
    }
}

