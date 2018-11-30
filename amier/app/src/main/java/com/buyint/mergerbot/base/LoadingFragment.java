package com.buyint.mergerbot.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.buyint.mergerbot.R;
import com.buyint.mergerbot.view.TextImageView;
import com.wang.avi.AVLoadingIndicatorView;

public class LoadingFragment extends Fragment {

    public static final String TAG = LoadingFragment.class.getSimpleName();
    private TextImageView xuanTv;
    private AVLoadingIndicatorView imageView;
    private TextView errorTextView;
    private RelativeLayout loadingFailLayout;

    /*
     * 新建一个LoadingFragment对象
     *
     * @param event RxBus广播的事件，字符串类型，不能为空，保证唯一。若为NULL或“”则不会发送事件广播
     * @return LoadingFragment 对象
     */
    public static LoadingFragment newInstance() {
        return new LoadingFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_loading, container, false);
        imageView = view.findViewById(R.id.loading_view);
        loadingFailLayout = view.findViewById(R.id.loading_fail_layout);
        errorTextView = view.findViewById(R.id.error_text);
        xuanTv = view.findViewById(R.id.fragment_loading_tv);
        imageView.show();
        return view;
    }

    public void stopAnimation() {
        imageView.hide();
    }

    public void removeSelf(final FragmentManager fragmentManager) {
        xuanTv.destory();
        if (fragmentManager == null) {
            return;
        }
        stopAnimation();
        getFragmentManager().beginTransaction()
                .setCustomAnimations(0, 0)
                .remove(LoadingFragment.this).commitAllowingStateLoss();
    }

    public void showLoadingFailView() {
        stopAnimation();
        errorTextView.setText(getString(R.string.page_loading_fail_please_click_retry));
        loadingFailLayout.setVisibility(View.VISIBLE);
    }

    public void showNoDataView() {
        stopAnimation();
        errorTextView.setText(getString(R.string.sorry_you_have_no_data_now));
        loadingFailLayout.setVisibility(View.VISIBLE);
    }

    private void removeLoadingFailView() {
        if (loadingFailLayout.getVisibility() == View.VISIBLE) {
            loadingFailLayout.setVisibility(View.GONE);
        }
    }
}
