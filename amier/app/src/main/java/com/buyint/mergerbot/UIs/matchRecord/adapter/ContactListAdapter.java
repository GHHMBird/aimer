package com.buyint.mergerbot.UIs.matchRecord.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.buyint.mergerbot.R;
import com.buyint.mergerbot.UIs.contact.ContactAddFirstActivity;
import com.buyint.mergerbot.dto.NamePhonePidBean;

import java.util.ArrayList;

public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.ViewHolder> {

    private Context context;
    private ArrayList<NamePhonePidBean> list = new ArrayList<>();
    private ContactListListener listener;

    public ContactListAdapter(Context context, ArrayList<NamePhonePidBean> list, ContactListListener listener) {
        this.context = context;
        this.list = list;
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
            return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_contact, null, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (position == list.size()) {
            holder.ivAdd.setOnClickListener(v -> {
                if(listener!=null){
                    listener.add();
                }
            });
        } else {
            NamePhonePidBean bean = list.get(position);
            holder.tvName.setText(bean.name);
//            holder.tvPhone.setText(bean.phone);
            holder.ivMessage.setOnClickListener(v -> {
                if (!TextUtils.isEmpty(bean.phone)) {
                    Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + bean.phone));
                    context.startActivity(intent);
                }
            });
            holder.itemView.setOnClickListener(v -> {
                Intent intent = new Intent();
                intent.setClass(context, ContactAddFirstActivity.class);
                intent.putExtra(context.getString(R.string.TYPE), context.getString(R.string.TYPE_0));
                context.startActivity(intent);
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size() + 1;
    }

    public void setData(ArrayList<NamePhonePidBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivMessage, ivAdd;
        private TextView tvName, tvPhone;

        public ViewHolder(View itemView) {
            super(itemView);

            ivMessage = itemView.findViewById(R.id.item_contact_message);
            ivAdd = itemView.findViewById(R.id.item_add_iv);

            tvName = itemView.findViewById(R.id.item_contact_name);
            tvPhone = itemView.findViewById(R.id.item_contact_phone);

        }
    }

    public interface ContactListListener {
        void add();
    }
}