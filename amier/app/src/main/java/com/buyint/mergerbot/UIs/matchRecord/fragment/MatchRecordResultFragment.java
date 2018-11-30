package com.buyint.mergerbot.UIs.matchRecord.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.buyint.mergerbot.R;
import com.buyint.mergerbot.UIs.matchRecord.adapter.MatchRecordAdapter;
import com.buyint.mergerbot.base.BaseFragment;
import com.buyint.mergerbot.dto.MatchRecordListModel;

import java.util.ArrayList;

/**
 * Created by huheming on 2018/10/16
 */

@SuppressLint("ValidFragment")
public class MatchRecordResultFragment extends BaseFragment {

    private ArrayList<MatchRecordListModel> list;
    private RecyclerView recyclerView;
    private MatchRecordAdapter adapter;

    public MatchRecordResultFragment(ArrayList<MatchRecordListModel> list) {
        this.list = list;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_match_record_result, null, false);
        recyclerView = inflate.findViewById(R.id.fragment_match_record_result_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new MatchRecordAdapter(getActivity(), false, list);
        recyclerView.setAdapter(adapter);
        return inflate;
    }

    public void setList(ArrayList<MatchRecordListModel> list) {
        this.list = list;
        adapter.setData(list);
    }
}
