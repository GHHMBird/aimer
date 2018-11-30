package com.buyint.mergerbot.UIs.user.activity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.buyint.mergerbot.R;
import com.buyint.mergerbot.UIs.user.fragment.MyProjectFragment;
import com.buyint.mergerbot.base.BaseActivity;
import com.buyint.mergerbot.base.MyFragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by huheming on 2018/5/29
 */

public class MyProjectActivity extends BaseActivity implements MyProjectFragment.MyProjectFragmentListener {

    public int clickColor, unClickColor;
    private TextView iCreate;
    private TextView iMatch;
    private TextView iCare;
    private View iCreateView;
    private View iCareView;
    private View iMatchView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        setTitleWhiteAndTextBlank();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_project);
        findViewById(R.id.toolbar_white_back).setOnClickListener(v -> finish());
        ((TextView) findViewById(R.id.toolbar_white_title)).setText(getString(R.string.my_project));
        findViewById(R.id.toolbar_white_top).setVisibility(View.GONE);
        findViewById(R.id.toolbar_white_right_add_rl).setVisibility(View.GONE);
        findViewById(R.id.toolbar_white_right_search_rl).setVisibility(View.GONE);
        iCreate = findViewById(R.id.activity_my_project_i_create);
        iMatch = findViewById(R.id.activity_my_project_i_match);
        iCare = findViewById(R.id.activity_my_project_i_care);
        iCreateView = findViewById(R.id.activity_my_project_i_create_view);
        iMatchView = findViewById(R.id.activity_my_project_i_match_view);
        iCareView = findViewById(R.id.activity_my_project_i_care_view);

        ViewPager viewpager = findViewById(R.id.activity_my_project_viewpager);

        clickColor = iCreate.getCurrentTextColor();
        unClickColor = iMatch.getCurrentTextColor();

        iCreate.setOnClickListener(v -> {
            iCreateShow();
            viewpager.setCurrentItem(0);
        });
        iMatch.setOnClickListener(v -> {
            iMatchShow();
            viewpager.setCurrentItem(1);
        });
        iCare.setOnClickListener(v -> {
            iCareShow();
            viewpager.setCurrentItem(2);
        });

        ArrayList<Fragment> list = new ArrayList<>();
        list.add(new MyProjectFragment(0, this));
        list.add(new MyProjectFragment(1, this));
        list.add(new MyProjectFragment(2, this));

        viewpager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(),list));
        viewpager.setOffscreenPageLimit(3);
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    iCreateShow();
                } else if (position == 1) {
                    iMatchShow();
                } else {
                    iCareShow();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    private void iCareShow() {
        iCreate.setTextColor(unClickColor);
        iMatch.setTextColor(unClickColor);
        iCare.setTextColor(clickColor);
        iCreateView.setBackgroundColor(Color.TRANSPARENT);
        iMatchView.setBackgroundColor(Color.TRANSPARENT);
        iCareView.setBackground(new ColorDrawable(clickColor));
    }

    private void iMatchShow() {
        iCreate.setTextColor(unClickColor);
        iMatch.setTextColor(clickColor);
        iCare.setTextColor(unClickColor);
        iCreateView.setBackgroundColor(Color.TRANSPARENT);
        iMatchView.setBackground(new ColorDrawable(clickColor));
        iCareView.setBackgroundColor(Color.TRANSPARENT);
    }

    private void iCreateShow() {
        iCreate.setTextColor(clickColor);
        iMatch.setTextColor(unClickColor);
        iCare.setTextColor(unClickColor);
        iCreateView.setBackground(new ColorDrawable(clickColor));
        iMatchView.setBackgroundColor(Color.TRANSPARENT);
        iCareView.setBackgroundColor(Color.TRANSPARENT);
    }

    @Override
    public void quickMatch() {
        setResult(102);
        finish();
    }
}
