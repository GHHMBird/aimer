package com.buyint.mergerbot.UIs.loginAndRegister.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.buyint.mergerbot.R;
import com.buyint.mergerbot.Utility.GlideCircleTransform;
import com.buyint.mergerbot.base.BaseFragment;
import com.buyint.mergerbot.dto.PostUserInfoType3Request;
import com.buyint.mergerbot.dto.ProjectIdNameBean;
import com.buyint.mergerbot.view.SelectableBackgroundView;
import com.makeramen.roundedimageview.RoundedImageView;

/**
 * Created by CXC on 2018/5/14
 */

public class RegisterLoginFragment4 extends BaseFragment {

    private LoginFragment4Listener listener;
    private TextView tvName, tvCompany, tvDomain, tvPosition, tvWebsite, tvEmail;
    private RoundedImageView ivIcon;
    private ProjectIdNameBean companyBean = new ProjectIdNameBean();
    private String url;

    public RegisterLoginFragment4() {
    }

    @SuppressLint("ValidFragment")
    public RegisterLoginFragment4(LoginFragment4Listener listener) {
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register_login_4, container, false);
        ((TextView) view.findViewById(R.id.toolbar_white_title)).setText(getString(R.string.fill_in_information));
        view.findViewById(R.id.toolbar_white_top).setVisibility(View.GONE);
        view.findViewById(R.id.toolbar_white_right_add_rl).setVisibility(View.GONE);
        view.findViewById(R.id.toolbar_white_right_search_rl).setVisibility(View.GONE);
        view.findViewById(R.id.toolbar_white_back).setOnClickListener(v -> {
            if (listener != null) {
                listener.back();
            }
        });
        ivIcon = view.findViewById(R.id.fragment_register_login_4_iv);
        ivIcon.setOnClickListener(v -> {
            if (listener != null) {
                listener.setIcon();
            }
        });

        tvName = view.findViewById(R.id.fragment_register_login_4_name);
        tvDomain = view.findViewById(R.id.fragment_register_login_4_domain);
        tvCompany = view.findViewById(R.id.fragment_register_login_4_company);
        tvPosition = view.findViewById(R.id.fragment_register_login_4_position);
        tvWebsite = view.findViewById(R.id.fragment_register_login_4_website);
        tvEmail = view.findViewById(R.id.fragment_register_login_4_email);

        tvName.setOnClickListener(v -> {
            if (listener != null) {
                listener.Match(10);
            }
        });
        tvCompany.setOnClickListener(v -> {
            if (listener != null) {
                listener.Match(11);
            }
        });
        tvDomain.setOnClickListener(v -> {
            if (listener != null) {
                listener.Match(12);
            }
        });
        tvPosition.setOnClickListener(v -> {
            if (listener != null) {
                listener.Match(13);
            }
        });
        tvWebsite.setOnClickListener(v -> {
            if (listener != null) {
                listener.Match(14);
            }
        });
        tvEmail.setOnClickListener(v -> {
            if (listener != null) {
                listener.Match(15);
            }
        });
        ((SelectableBackgroundView) view.findViewById(R.id.fragment_register_login_4_button)).setListener(() -> {
            PostUserInfoType3Request request = new PostUserInfoType3Request();
            request.setAvatars(url);
            request.setBusinessArea(tvDomain.getText().toString().trim());
            request.setCompanyId(companyBean.getProjectId());
            request.setCompanyName(companyBean.getProjectName());
            request.setCompanyWebSite(tvWebsite.getText().toString().trim());
            request.setPosition(tvPosition.getText().toString().trim());
            request.setUserName(tvName.getText().toString().trim());
            request.setWorkEmail(tvEmail.getText().toString().trim());
            if (listener != null) {
                listener.writeFinish(request);
            }
        });
        return view;
    }

    public interface LoginFragment4Listener {
        void writeFinish(PostUserInfoType3Request request);

        void setIcon();

        void back();

        void Match(int position);
    }

    public void setIcon(String icon) {
        this.url = icon;
        Glide.with(getActivity()).load(icon).transform(new GlideCircleTransform(getActivity())).placeholder(R.mipmap.default_user).error(R.mipmap.default_user).into(ivIcon);
    }

    public void setName(String extra) {
        tvName.setText(extra);
    }

    public void setCompany(ProjectIdNameBean bean) {
        this.companyBean = bean;
        tvCompany.setText(bean.getProjectName());
    }

    public void setDomain(String text) {
        tvDomain.setText(text);
    }

    public void setPosition(String text) {
        tvPosition.setText(text);
    }

    public void setWeb(String text) {
        tvWebsite.setText(text);
    }

    public void setEmail(String text) {
        tvEmail.setText(text);
    }
}
