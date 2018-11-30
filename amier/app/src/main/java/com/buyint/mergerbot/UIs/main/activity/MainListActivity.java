package com.buyint.mergerbot.UIs.main.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.buyint.mergerbot.R;
import com.buyint.mergerbot.UIs.main.adapter.MainListAdapter;
import com.buyint.mergerbot.base.BaseActivity;
import com.buyint.mergerbot.database.DBUtilsKt;
import com.buyint.mergerbot.dto.GetProductRequest;
import com.buyint.mergerbot.dto.LoginResponse;
import com.buyint.mergerbot.dto.MatchCompanyModel;
import com.buyint.mergerbot.dto.MatchCompanyMorePageRequest;
import com.buyint.mergerbot.helper.HttpHelper;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;

/**
 * Created by huheming on 2018/6/1
 */

public class MainListActivity extends BaseActivity {

    private SmartRefreshLayout swipe;
    private RecyclerView recyclerView;
    private MainListAdapter adapter;
    private int pageNum = 1;
    private boolean isLoading;
    private String type;
    private MatchCompanyModel data;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        setMyTitleColor();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_list);

        findViewById(R.id.toolbar_image_back).setVisibility(View.VISIBLE);
        findViewById(R.id.toolbar_image_back).setOnClickListener(v -> finish());
        swipe = findViewById(R.id.activity_main_list_swipe);
        recyclerView = findViewById(R.id.activity_main_list_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        swipe.setOnRefreshListener(refreshLayout -> {
            pageNum = 1;
            adapter.notifyDataSetChanged();
            isLoading = false;

            if (type.equals(getString(R.string.MAIN))) {
                getProduct();
            } else if (type.equals(getString(R.string.QUICK))) {
                matchCompanyMorePage(0);
            }
        });
        swipe.setOnLoadMoreListener(refreshLayout -> {
            isLoading = true;
            pageNum += 1;
            adapter.notifyDataSetChanged();

            if (type.equals(getString(R.string.MAIN))) {
                getProductMore(1);
            } else if (type.equals(getString(R.string.QUICK))) {
                matchCompanyMorePage(1);
            }
        });

        Intent intent = getIntent();
        type = intent.getStringExtra(getString(R.string.TYPE));
        if (type.equals(getString(R.string.MAIN))) {
            adapter = new MainListAdapter(this, new ArrayList<>(), getString(R.string.userRecommend), 0);
            recyclerView.setAdapter(adapter);
            showLoadingFragment(R.id.activity_main_list_fl);
            getProduct();
        } else if (type.equals(getString(R.string.QUICK))) {
            data = (MatchCompanyModel) intent.getSerializableExtra(getString(R.string.result));
            adapter = new MainListAdapter(this, data.relationProject, data.projectId, 0);
            recyclerView.setAdapter(adapter);
        }
    }

    private void getProduct() {

        GetProductRequest request = new GetProductRequest();
        LoginResponse data = DBUtilsKt.getLoginResponse(this);
        request.userIntentions = data.model.userIntentions;
        request.industry_Classification = data.model.registerIndustryClassification;

        HttpHelper.getProduct(request).subscribe(response -> {

            if (response != null && response.data != null && response.data.relationProject != null) {
                for (int i = 0; i < response.data.relationProject.size(); i++) {
                    response.data.relationProject.get(i).type = getString(R.string.userRecommend);
                }

                if (adapter == null) {
                    adapter = new MainListAdapter(MainListActivity.this, response.data.relationProject, getString(R.string.userRecommend), 0);
                    recyclerView.setAdapter(adapter);
                } else {
                    adapter.setList(response.data.relationProject);
                }
            }

            swipe.finishRefresh();
            swipe.finishLoadMore();
            removeLoadingFragment();

        }, throwable -> {
            swipe.finishRefresh();
            swipe.finishLoadMore();
            removeLoadingFragment();
            showThrowable(throwable);
        });
    }

    public void getProductMore(int type) {
        MatchCompanyMorePageRequest request = new MatchCompanyMorePageRequest();
        request.pageSize = 10;
        request.pageNum = pageNum;
        HttpHelper.getProductMore(request).subscribe(response -> {

            for (int i = 0; i < response.data.size(); i++) {
                response.data.get(i).type = getString(R.string.userRecommend);
            }

            if (response.data != null && response.data.size() > 0) {
                adapter.notifyDataSetChanged();
                if (type == 0) {
                    adapter.setList(response.data);
                } else if (type == 1) {
                    adapter.addList(response.data);
                }
            } else {
                adapter.notifyDataSetChanged();
            }
            isLoading = false;
            swipe.finishRefresh();
            swipe.finishLoadMore();
        }, throwable -> {
            removeLoadingFragment();
            if (isLoading && pageNum != 1) {
                pageNum--;
                isLoading = false;
                adapter.notifyDataSetChanged();
            }
            swipe.finishRefresh();
            swipe.finishLoadMore();
            showThrowable(throwable);
        });
    }

    public void matchCompanyMorePage(int type) {
        MatchCompanyMorePageRequest request = new MatchCompanyMorePageRequest();
        request.pageNum = pageNum;
        request.pageSize = 10;
        request.projectId = data.projectId;
        HttpHelper.matchCompanyMorePage(request).subscribe(response -> {
            removeLoadingFragment();

            if (response.data != null && response.data.size() > 0) {
                adapter.notifyDataSetChanged();
                if (type == 0) {
                    adapter.setList(response.data);
                    if (response.data.size() < 10) {
                        adapter.notifyDataSetChanged();
                    }
                } else if (type == 1) {
                    adapter.addList(response.data);
                }
            } else {
                adapter.notifyDataSetChanged();
            }
            isLoading = false;
            swipe.finishRefresh();
            swipe.finishLoadMore();
        }, throwable -> {
            removeLoadingFragment();
            swipe.finishRefresh();
            swipe.finishLoadMore();
            if (isLoading && pageNum != 1) {
                pageNum--;
                isLoading = false;
                adapter.notifyDataSetChanged();
            }
            showThrowable(throwable);
        });
    }
}
