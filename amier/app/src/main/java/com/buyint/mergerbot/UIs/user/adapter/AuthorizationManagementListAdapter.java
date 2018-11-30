package com.buyint.mergerbot.UIs.user.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.buyint.mergerbot.R;
import com.buyint.mergerbot.UIs.user.activity.AddAuthorizationManagementActivity;
import com.buyint.mergerbot.UIs.user.activity.AuthorizationManagementListActivity;
import com.buyint.mergerbot.dto.SecretaryBean;

import java.util.ArrayList;

public class AuthorizationManagementListAdapter extends RecyclerView.Adapter<AuthorizationManagementListAdapter.ViewHolder> {

    private Context context;
    private ArrayList<SecretaryBean> list = new ArrayList<>();
    private AuthorizationManagementListListener listener;

    public AuthorizationManagementListAdapter(Context context, AuthorizationManagementListListener listener) {
        this.context = context;
        this.listener = listener;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == list.size()) {
            return 0;
        } else {
            return 1;
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 0) {
            return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_add, null, false));
        } else {
            return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_secretary, null, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (position == list.size()) {
            holder.ivAdd.setOnClickListener(v -> ((AuthorizationManagementListActivity) context).startActivityForResult(new Intent(context, AddAuthorizationManagementActivity.class), 101));
        } else {
            SecretaryBean bean = list.get(position);
            holder.tvName.setText(bean.name);
            StringBuffer sb = new StringBuffer();
            if (!TextUtils.isEmpty(bean.company)) {
                sb.append(bean.company).append("|");
            } else {
                sb.append(context.getString(R.string.undisclosed_company)).append("|");
            }
            if (!TextUtils.isEmpty(bean.title)) {
                sb.append(bean.title);
            } else {
                sb.append(context.getString(R.string.undisclosed_position));
            }
            holder.tvCompany.setText(sb.toString());
            holder.tvRemove.setOnClickListener(v -> {
                listener.remove(bean);
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size() + 1;
    }

    public void setList(ArrayList<SecretaryBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivAdd;
        private TextView tvName, tvCompany, tvRemove;

        public ViewHolder(View itemView) {
            super(itemView);

            ivAdd = itemView.findViewById(R.id.item_add_iv);

            tvName = itemView.findViewById(R.id.item_secretary_name);
            tvCompany = itemView.findViewById(R.id.item_secretary_company);
            tvRemove = itemView.findViewById(R.id.item_secretary_remove);

        }
    }

    public interface AuthorizationManagementListListener {
        void remove(SecretaryBean bean);
    }
}