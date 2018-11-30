package com.buyint.mergerbot.UIs.match.baseAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.buyint.mergerbot.R;
import com.buyint.mergerbot.dto.CodeTitleBean;

import java.util.ArrayList;

/**
 * Created by huheming on 2018/8/29
 */

public class QuickMatchCodeTitleAdapter extends BaseAdapter implements Filterable {

    private ArrayList<CodeTitleBean> list;
    private Context context;
    private QuickMatchCodeTitleAdapterListener listener;

    public QuickMatchCodeTitleAdapter(Context context, ArrayList<CodeTitleBean> list, QuickMatchCodeTitleAdapterListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_quick_match2, null, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tv.setText(list.get(position).name.get(0));
        viewHolder.tv.setOnClickListener(v -> {
            if (listener != null) {
                listener.click(list.get(position));
            }
        });

        return convertView;
    }

    public void setList(ArrayList<CodeTitleBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                results.values = list;
                results.count = list.size();
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                list = (ArrayList<CodeTitleBean>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    class ViewHolder {

        TextView tv;

        public ViewHolder(View itemView) {
            tv = itemView.findViewById(R.id.item_quick_match2_text);
        }
    }

    public interface QuickMatchCodeTitleAdapterListener {
        void click(CodeTitleBean bean);
    }
}
