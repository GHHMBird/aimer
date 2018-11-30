package com.buyint.mergerbot.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 不带isFirst则每次都加载数据，走initData
 * 带上isFirst则只有初始化的时候加载一次数据，走initData，切换的时候就不在刷新数据，需要用户手动刷新
 * Created by huheming on 2018/9/21
 */

public abstract class LazyBaseFragment extends BaseFragment {

    protected boolean isVisible;
    protected View mRootView;
    private boolean isPrepared;
//    private boolean isFirst = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = initView();
        }
        return mRootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isPrepared = true;
        lazyLoad();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
            lazyLoad();
        } else {
            isVisible = false;
            onInvisible();
        }
    }

    public void lazyLoad() {
        if (!isPrepared || !isVisible /*|| !isFirst*/) {
            return;
        }
        initData();
//        isFirst = false;
    }

    protected void onInvisible() {
    }

    public abstract View initView();

    public abstract void initData();

}
