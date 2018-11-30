package com.buyint.mergerbot.UIs.match.RecyclerAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.buyint.mergerbot.R;
import com.buyint.mergerbot.dto.ProjectIdNameBean;

import java.util.ArrayList;

/**
 * Created by CXC on 2018/4/17
 */

public class QuickMatchProjectIdNameAdapter extends RecyclerView.Adapter<QuickMatchProjectIdNameAdapter.QuickMatchHolder> {

    private QuickMatchProjectIdNameAdapterListener listener;
    private Context context;
    private ArrayList<ProjectIdNameBean> list = new ArrayList<>();

    public QuickMatchProjectIdNameAdapter(Context context, ArrayList<ProjectIdNameBean> list, QuickMatchProjectIdNameAdapterListener listener) {
        this.context = context;
        this.listener = listener;
        this.list = list;
    }

    public void setList(ArrayList<ProjectIdNameBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public QuickMatchHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new QuickMatchHolder(LayoutInflater.from(context).inflate(R.layout.item_quick_match, null));
    }

    @Override
    public void onBindViewHolder(QuickMatchHolder holder, int position) {
        ProjectIdNameBean bean = list.get(position);
        holder.tv.setText(bean.getProjectName());
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

    public void setListData(ArrayList<ProjectIdNameBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    class QuickMatchHolder extends RecyclerView.ViewHolder {
        private TextView tv;

        QuickMatchHolder(View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.item_quick_match_tv);
        }
    }

    public interface QuickMatchProjectIdNameAdapterListener {
        void click(ProjectIdNameBean bean);
    }
}

