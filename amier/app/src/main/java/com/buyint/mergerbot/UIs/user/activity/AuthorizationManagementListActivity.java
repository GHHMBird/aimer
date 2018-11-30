package com.buyint.mergerbot.UIs.user.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.buyint.mergerbot.R;
import com.buyint.mergerbot.UIs.user.adapter.AuthorizationManagementListAdapter;
import com.buyint.mergerbot.Utility.ViewHelper;
import com.buyint.mergerbot.base.BaseActivity;
import com.buyint.mergerbot.helper.HttpHelper;
import com.buyint.mergerbot.view.SelectableBackgroundView;

public class AuthorizationManagementListActivity extends BaseActivity {

    private AuthorizationManagementListAdapter adapter;
    private RecyclerView recyclerView;
    private LinearLayout linearLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setMyTitleColor();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorization_management_list);

        findViewById(R.id.toolbar_left_left_back).setOnClickListener(v -> onBackPressed());
        ((TextView) findViewById(R.id.toolbar_left_title)).setText(getString(R.string.mine_buyint_power));

        recyclerView = findViewById(R.id.activity_authorization_management_list_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AuthorizationManagementListAdapter(this, bean -> ViewHelper.showOneLineCard(AuthorizationManagementListActivity.this, getString(R.string.are_you_sure_remove_authorization), getString(R.string.alright), getString(R.string.cancel), (dialog, which) -> {
            showLoadingFragment(R.id.activity_authorization_management_list_fl);
            HttpHelper.removeSecretary(bean.authId).subscribe(b -> {
                if (b) {
                    getData();
                }
            }, throwable -> {
                showThrowable(throwable);
                removeLoadingFragment();
            });
        }, (dialog, which) -> {
        }));
        recyclerView.setAdapter(adapter);
        linearLayout = findViewById(R.id.activity_authorization_management_list_ll);

        SelectableBackgroundView sbv = findViewById(R.id.activity_authorization_management_list_sbv);
        sbv.setListener(() -> startActivityForResult(new Intent(AuthorizationManagementListActivity.this, AddAuthorizationManagementActivity.class), 101));

        getData();
    }

    private void getData() {
        showLoadingFragment(R.id.activity_authorization_management_list_fl);
        HttpHelper.getSecretaryList().subscribe(list -> {

            if (list != null && list.size() > 0) {
                recyclerView.setVisibility(View.VISIBLE);
                linearLayout.setVisibility(View.GONE);
                adapter.setList(list);
            } else {
                recyclerView.setVisibility(View.GONE);
                linearLayout.setVisibility(View.VISIBLE);
            }

            removeLoadingFragment();
        }, throwable -> {
            showThrowable(throwable);
            removeLoadingFragment();
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101 && resultCode == 102) {
            getData();
        }
    }
}