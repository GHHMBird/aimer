package com.buyint.mergerbot.base;

import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Toast;

import com.buyint.mergerbot.R;

/**
 * Created by CXC on 2018/3/30
 */

public class BaseFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void showLoadingFragment(int viewId) {
        LoadingFragment mLoadingFragment = LoadingFragment.newInstance();
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(viewId, mLoadingFragment, LoadingFragment.TAG)
                .commitAllowingStateLoss();
    }

    public void removeLoadingFragment() {
        new Handler().postDelayed(() -> {
            LoadingFragment mLoadingFragment = findLoadingFragment();
            if (mLoadingFragment != null) {
                mLoadingFragment.removeSelf(getActivity().getSupportFragmentManager());
            }
        }, 2000);
    }

    public LoadingFragment findLoadingFragment() {
        if (getActivity() != null) {
            Fragment fragment = getActivity().getSupportFragmentManager().findFragmentByTag(LoadingFragment.TAG);
            if (fragment != null) {
                return (LoadingFragment) fragment;
            }
        }
        return null;
    }

    private ViewTreeObserver.OnGlobalLayoutListener keyboardLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
        @Override
        public void onGlobalLayout() {
            // navigation bar height
            int navigationBarHeight = 0;
            int resourceId = getResources().getIdentifier(getString(R.string.navigation_bar_height), getString(R.string.dimen), getString(R.string.android));
            if (resourceId > 0) {
                navigationBarHeight = getResources().getDimensionPixelSize(resourceId);
            }

            // status bar height
            int statusBarHeight = 0;
            resourceId = getResources().getIdentifier(getString(R.string.status_bar_height), getString(R.string.dimen), getString(R.string.android));
            if (resourceId > 0) {
                statusBarHeight = getResources().getDimensionPixelSize(resourceId);
            }

            // display window size for the app layout
            Rect rect = new Rect();
            if (getActivity() != null) {
                getActivity().getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
            }

            // screen height - (user app height + status + nav) ..... if non-zero, then there is a soft keyboard
            int keyboardHeight = 0;
            if (rootLayout != null) {
                keyboardHeight = rootLayout.getRootView().getHeight() - (statusBarHeight + navigationBarHeight + rect.height());
            }

            if (keyboardHeight <= 0) {
                onHideKeyboard();
            } else {
                onShowKeyboard(keyboardHeight);
            }
        }
    };

    private boolean keyboardListenersAttached = false;
    private ViewGroup rootLayout;

    protected void onShowKeyboard(int keyboardHeight) {
    }

    protected void onHideKeyboard() {
    }

    protected void attachKeyboardListeners() {
        if (keyboardListenersAttached) {
            return;
        }

        rootLayout = (ViewGroup) getActivity().findViewById(R.id.rootLayout);
        rootLayout.getViewTreeObserver().addOnGlobalLayoutListener(keyboardLayoutListener);

        keyboardListenersAttached = true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

//        RefWatcher refWatcher = BaseApplication.getRefWatcher(getActivity());
//        refWatcher.watch(this);

        if (keyboardListenersAttached && rootLayout != null) {
            rootLayout.getViewTreeObserver().removeGlobalOnLayoutListener(keyboardLayoutListener);
        }
    }

    public void showToast(String string) {
        Toast.makeText(getActivity(), string, Toast.LENGTH_SHORT).show();
    }

    public void showThrowable(Throwable throwable) {
        showToast(throwable.getMessage());
    }
}
