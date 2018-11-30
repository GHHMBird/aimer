package com.buyint.mergerbot.Utility;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.buyint.mergerbot.R;
import com.buyint.mergerbot.bus.Bus;

public class UpDateFragment extends Fragment implements View.OnClickListener {

    private Button btn;
    private ProgressBar pbar;
    private static final String KEY_EVENT = "KEY";
    private static String string, urlStr;
    public static final String TAG = "UpDateFragment";
    private static int type;

    public static UpDateFragment newInstance(@NonNull String event, String text, String url, int num) {
        string = text;
        urlStr = url;
        type = num;
        Bundle args = new Bundle();
        args.putString(KEY_EVENT, event);
        UpDateFragment fragment = new UpDateFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_update, container, false);
        btn = view.findViewById(R.id.update_btn);
        ImageView closeImage = view.findViewById(R.id.update_close);
        RelativeLayout rl_update_out = view.findViewById(R.id.rl_update_out);
        RelativeLayout rl_update_in = view.findViewById(R.id.rl_update_in);
        TextView text = view.findViewById(R.id.update_text);
        text.setText(Html.fromHtml(string));
        pbar = view.findViewById(R.id.update_pbar);
        closeImage.setOnClickListener(this);
        btn.setOnClickListener(this);
        rl_update_out.setOnClickListener(this);
        rl_update_in.setOnClickListener(this);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String event = getArguments().getString(KEY_EVENT, "");
        if (!TextUtils.isEmpty(event)) {
            Bus.getDefault().post(event);
        }
    }

    public void removeSelf(final FragmentManager fragmentManager) {
        if (fragmentManager == null) {
            return;
        }
        Bus.getDefault().register(this);
        Bus.getDefault().post("download_finish");
        Bus.getDefault().unregister(this);
        getFragmentManager().beginTransaction()
                .setCustomAnimations(0, 0)
                .remove(UpDateFragment.this).commitAllowingStateLoss();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.update_btn:
                btn.setVisibility(View.GONE);
                pbar.setVisibility(View.VISIBLE);
                downLoad();
                break;
            case R.id.update_close:
            case R.id.rl_update_out:
                if (type == 0) {
                    removeSelf(getFragmentManager());
                }
                break;
        }
    }

    private void downLoad() {
        DownLoadUtil downLoadUtil = new DownLoadUtil(getActivity());
        downLoadUtil.downLoadFile(getActivity(), getString(R.string.buyint), urlStr);
        downLoadUtil.setOnProgressListener((fraction, downloadId) -> pbar.setProgress((int) (fraction * 100)));
    }

}