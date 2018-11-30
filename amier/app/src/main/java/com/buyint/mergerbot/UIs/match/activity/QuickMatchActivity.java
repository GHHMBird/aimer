package com.buyint.mergerbot.UIs.match.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.buyint.mergerbot.R;
import com.buyint.mergerbot.UIs.match.RecyclerAdapter.QuickMatchCoedNameAdapter;
import com.buyint.mergerbot.UIs.match.RecyclerAdapter.QuickMatchCoedTitleAdapter;
import com.buyint.mergerbot.UIs.match.RecyclerAdapter.QuickMatchLawyerAdapter;
import com.buyint.mergerbot.UIs.match.RecyclerAdapter.QuickMatchLawyerCompanyAdapter;
import com.buyint.mergerbot.UIs.match.RecyclerAdapter.QuickMatchNameDetailAdapter;
import com.buyint.mergerbot.UIs.match.RecyclerAdapter.QuickMatchProjectIdNameAdapter;
import com.buyint.mergerbot.UIs.match.RecyclerAdapter.QuickMatchStringAdapter;
import com.buyint.mergerbot.base.BaseActivity;
import com.buyint.mergerbot.dto.CodeNameBean;
import com.buyint.mergerbot.dto.CodeNameResponse;
import com.buyint.mergerbot.dto.CodeTitleBean;
import com.buyint.mergerbot.dto.GetAutoProductResponse;
import com.buyint.mergerbot.dto.GetLawyerBean;
import com.buyint.mergerbot.dto.GetLawyerCompanyBean;
import com.buyint.mergerbot.dto.GetNameDetailResponse;
import com.buyint.mergerbot.dto.IndustryListResponse;
import com.buyint.mergerbot.dto.ProjectIdNameBean;
import com.buyint.mergerbot.dto.UserAndCompanyNameAutoCompleteResponse;
import com.buyint.mergerbot.interfaces.IGetCompanyList;
import com.buyint.mergerbot.interfaces.IGetIndustryList;
import com.buyint.mergerbot.interfaces.IGetLawyer;
import com.buyint.mergerbot.interfaces.IGetLawyerCompany;
import com.buyint.mergerbot.interfaces.IGetLocationInput;
import com.buyint.mergerbot.interfaces.IGetNameDetail;
import com.buyint.mergerbot.interfaces.IGetProductList;
import com.buyint.mergerbot.interfaces.IUserAndCompanyNameAutoComplete;
import com.buyint.mergerbot.presenter.QuickMatchPresenter;
import com.iflytek.sunflower.FlowerCollector;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * Created by CXC on 2018/4/17
 * 匹配信息页面
 * 10 地点
 * 11  行业
 * 12  产品
 * 13-14 匹配录 公司、姓名
 * 16 自动补全人名（匹配录智配关系配人的时候）
 * 17-18自动补全律师的名字和律师事务所 （认证的时候）
 * 19 自动补全公司名 所有公司
 */

public class QuickMatchActivity extends BaseActivity implements IGetLocationInput, IGetIndustryList, IGetProductList, IUserAndCompanyNameAutoComplete, IGetNameDetail, IGetLawyerCompany, IGetLawyer, IGetCompanyList {

    private static String TAG = QuickMatchActivity.class.getSimpleName();
    public static CodeNameBean placeBean = new CodeNameBean();
    private RecyclerView recyclerView;
    private QuickMatchCoedNameAdapter codeNameAdapter;
    private QuickMatchCoedTitleAdapter codeTitleAdapter;
    private QuickMatchStringAdapter stringAdapter;
    private QuickMatchNameDetailAdapter nameDetailAdapter;
    private EditText etView;
    private int type;
    private static CodeTitleBean hangyeBean = new CodeTitleBean();
    private boolean clickNow;
    private static CodeNameBean productBean = new CodeNameBean();
    private CodeNameBean userName;
    private String companyName;
    private QuickMatchPresenter presenter;
    private GetLawyerCompanyBean getLawyerCompanyBean;
    private QuickMatchLawyerCompanyAdapter lawyerCompanyAdapter;
    private GetLawyerBean getLawyerBean;
    private QuickMatchLawyerAdapter lawyerAdapter;
    private String law_id = "";
    private ProjectIdNameBean companyNameBean;
    private QuickMatchProjectIdNameAdapter projectIdNameAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        presenter = new QuickMatchPresenter(this, this, this, this, this, this, this, this);
        setMyTitleColor();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quick_match);

        findViewById(R.id.toolbar_image_top).setVisibility(View.GONE);

        etView = findViewById(R.id.activity_quick_match_et);
        TextView tv = findViewById(R.id.activity_quick_match_tv);
        TextView ivSend = findViewById(R.id.activity_quick_match_send);
        findViewById(R.id.toolbar_image_back).setOnClickListener(v -> onBackPressed());
        recyclerView = findViewById(R.id.activity_quick_match_re);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Intent intent = getIntent();
        type = intent.getIntExtra(getString(R.string.TYPE), -1);
        String name = intent.getStringExtra(getString(R.string.NAME));
        if (TextUtils.isEmpty(name)) {
            name = "";
        }
        switch (type) {
            case 10:
                tv.setText(getString(R.string.may_i_ask_where_you_want_the_company_to_be));
                etView.setHint(getString(R.string.input_intentional) + name + getString(R.string.company_area));
                etView.setSingleLine();
                etView.setFilters(new InputFilter[]{new InputFilter.LengthFilter(10)});
                break;
            case 11:
                tv.setText(getString(R.string.you_have_the_intention_to) + name + getString(R.string.the_project_industry));
                etView.setHint(getString(R.string.input_intentional) + name + getString(R.string.project_industry));
                etView.setSingleLine();
                etView.setFilters(new InputFilter[]{new InputFilter.LengthFilter(10)});
                break;
            case 12:
                tv.setText(getString(R.string.you_have_the_intention_to) + name + getString(R.string.the_products_of_the_project));
                etView.setHint(getString(R.string.input_intentional) + name + getString(R.string.project_products));
                etView.setSingleLine();
                etView.setFilters(new InputFilter[]{new InputFilter.LengthFilter(10)});
                break;
            case 13:
                tv.setText(getString(R.string.please_write_your_name));
                etView.setHint(getString(R.string.please_write_your_name));
                etView.setSingleLine();
                userName = (CodeNameBean) intent.getSerializableExtra(getString(R.string.data1));
                companyName = intent.getStringExtra(getString(R.string.data2));
                if (!TextUtils.isEmpty(userName.name)) {
                    etView.setText(userName.name);
                } else {
                    userName.name = "";
                }
                if (TextUtils.isEmpty(companyName)) {
                    companyName = "";
                }
                if (!TextUtils.isEmpty(userName.name) && TextUtils.isEmpty(companyName)) {
                    presenter.userAndCompanyNameAutoComplete(type, userName.name, companyName);
                }
                break;
            case 14:
                tv.setText(getString(R.string.please_write_company_name));
                etView.setHint(getString(R.string.please_write_company_name));
                etView.setSingleLine();
                userName = (CodeNameBean) intent.getSerializableExtra(getString(R.string.data1));
                companyName = intent.getStringExtra(getString(R.string.data2));
                if (!TextUtils.isEmpty(companyName)) {
                    etView.setText(companyName);
                } else {
                    companyName = "";
                }
                if (TextUtils.isEmpty(userName.name)) {
                    userName.name = "";
                }
                if (!TextUtils.isEmpty(userName.name) && TextUtils.isEmpty(companyName)) {
                    presenter.userAndCompanyNameAutoComplete(type, userName.name, companyName);
                }
                break;
            case 16:
                tv.setText(getString(R.string.please_write_the_name));
                etView.setHint(getString(R.string.please_write_the_name));
                etView.setSingleLine();
                tv.setText(name);
                ivSend.setVisibility(View.GONE);
                break;
            case 17:
                getLawyerCompanyBean = (GetLawyerCompanyBean) intent.getSerializableExtra(getString(R.string.DATA));
                tv.setText(getString(R.string.please_write_company_name));
                etView.setHint(getString(R.string.please_write_company_name));
                etView.setSingleLine();
                etView.setText(getLawyerCompanyBean.getName());
                break;
            case 18:
                getLawyerBean = (GetLawyerBean) intent.getSerializableExtra(getString(R.string.data1));
                law_id = intent.getStringExtra(getString(R.string.data2));
                tv.setText(getString(R.string.please_write_your_name));
                etView.setHint(getString(R.string.please_write_your_name));
                etView.setSingleLine();
                etView.setText(getLawyerBean.getName());
                break;
            case 19:
                companyNameBean = (ProjectIdNameBean) intent.getSerializableExtra(getString(R.string.DATA));
                tv.setText(getString(R.string.please_write_company_name));
                etView.setHint(getString(R.string.please_write_company_name));
                etView.setSingleLine();
                etView.setText(TextUtils.isEmpty(companyNameBean.getProjectName()) ? "" : companyNameBean.getProjectName());
                break;
            case -1:
                tv.setText(name);
                etView.setHint(name);
                etView.setSingleLine();
                break;
        }
        String title = intent.getStringExtra(getString(R.string.TITLE));
        if (!TextUtils.isEmpty(title)) {
            tv.setText(title);
        }

        ivSend.setOnClickListener(v -> {

            switch (type) {
                case 10:
                    if (!TextUtils.isEmpty(placeBean.name)) {
                        Intent intent1 = new Intent();
                        intent1.putExtra(getString(R.string.PlaceBean), placeBean);
                        setResult(1001, intent1);
                        onBackPressed();
                    }
                    break;
                case 11:
                    if (!TextUtils.isEmpty(hangyeBean.name.get(0))) {
                        Intent intent1 = new Intent();
                        intent1.putExtra(getString(R.string.IndustryBean), hangyeBean);
                        setResult(1001, intent1);
                        onBackPressed();
                    }
                    break;
                case 12:
                    if (!TextUtils.isEmpty(productBean.name)) {
                        Intent intent1 = new Intent();
                        intent1.putExtra(getString(R.string.ProductBean), productBean);
                        setResult(1001, intent1);
                        onBackPressed();
                    }
                    break;
                case 13:
                    if (!TextUtils.isEmpty(userName.name)) {
                        Intent intent1 = new Intent();
                        intent1.putExtra(getString(R.string.data1), userName);
                        setResult(1001, intent1);
                        onBackPressed();
                    }
                    break;
                case 14:
                    if (!TextUtils.isEmpty(companyName)) {
                        Intent intent1 = new Intent();
                        intent1.putExtra(getString(R.string.data2), companyName);
                        setResult(1001, intent1);
                        onBackPressed();
                    }
                    break;
                case 16:
                    if (!TextUtils.isEmpty(companyName)) {
                        Intent intent1 = new Intent();
                        intent1.putExtra(getString(R.string.data2), companyName);
                        setResult(1001, intent1);
                        onBackPressed();
                    }
                    break;
                case 17:
                    if (!TextUtils.isEmpty(getLawyerCompanyBean.getName())) {
                        Intent intent1 = new Intent();
                        intent1.putExtra(getString(R.string.DATA), getLawyerCompanyBean);
                        setResult(1001, intent1);
                        onBackPressed();
                    }
                    break;
                case 18:
                    if (!TextUtils.isEmpty(getLawyerBean.getName())) {
                        Intent intent1 = new Intent();
                        intent1.putExtra(getString(R.string.DATA), getLawyerBean);
                        setResult(1001, intent1);
                        onBackPressed();
                    }
                    break;
                case 19:
                    if (!TextUtils.isEmpty(companyNameBean.getProjectName())) {
                        Intent intent1 = new Intent();
                        intent1.putExtra(getString(R.string.DATA), companyNameBean);
                        setResult(1001, intent1);
                        onBackPressed();
                    }
                    break;
                case -1:
                    if (!TextUtils.isEmpty(etView.getText().toString().trim())) {
                        Intent intent1 = new Intent();
                        intent1.putExtra(getString(R.string.DATA), etView.getText().toString().trim());
                        setResult(1001, intent1);
                        finish();
                    }
                    break;
            }
        });

        etView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(etView.getText().toString().trim())) {
                    if (codeNameAdapter != null) {
                        codeNameAdapter.clearData();
                    }
                    if (codeTitleAdapter != null) {
                        codeTitleAdapter.clearData();
                    }
                    if (stringAdapter != null) {
                        stringAdapter.clearData();
                    }
                }
                if (clickNow) {
                    clickNow = false;
                    return;
                }
                switch (type) {
                    case 10:
                        presenter.getLocationInput(s.toString().trim());
                        placeBean.name = s.toString().trim();
                        placeBean.code = "";
                        break;
                    case 11:
                        presenter.getIndustryList(s.toString().trim());
                        hangyeBean.code = "";
                        if (hangyeBean.name == null) {
                            hangyeBean.name = new ArrayList<>();
                        } else {
                            hangyeBean.name.clear();
                        }
                        hangyeBean.name.add(s.toString().trim());
                        break;
                    case 12:
                        presenter.getProductList(s.toString().trim());
                        productBean.name = s.toString().trim();
                        productBean.code = "";
                        break;
                    case 13:
                        if (!TextUtils.isEmpty(s.toString().trim())/* && TextUtils.isEmpty(companyName))*/) {
                            presenter.userAndCompanyNameAutoComplete(type, s.toString().trim(), companyName);
                        }
                        userName.name = s.toString().trim();
                        userName.code = "";
                        break;
                    case 14:
                        if (!/*(TextUtils.isEmpty(userName.name) && */TextUtils.isEmpty(s.toString().trim())) {
                            presenter.userAndCompanyNameAutoComplete(type, userName.name, s.toString().trim());
                        }
                        companyName = s.toString().trim();
                        break;
                    case 16:
                        if (!TextUtils.isEmpty(s.toString().trim())) {
                            presenter.getNameDetail(s.toString().trim());
                        }
                        break;
                    case 17:
                        if (!TextUtils.isEmpty(s.toString().trim())) {
                            presenter.getLawyerCompany(s.toString().trim());
                        }
                        getLawyerCompanyBean.setLaw_id("");
                        getLawyerCompanyBean.setName(s.toString().trim());
                        break;
                    case 18:
                        if (!TextUtils.isEmpty(s.toString().trim())) {
                            presenter.getLawyer(s.toString().trim(), law_id);
                        }
                        getLawyerBean.setCompany("");
                        getLawyerBean.setEmail("");
                        getLawyerBean.setId("");
                        getLawyerBean.setPosition("");
                        getLawyerBean.setSource("");
                        getLawyerBean.setName(s.toString().trim());
                        break;
                    case 19:
                        if (!TextUtils.isEmpty(s.toString().trim())) {
                            presenter.getCompanyList(s.toString().trim());
                        }
                        companyNameBean.setProjectId("");
                        companyNameBean.setProjectName(s.toString().trim());
                        break;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        etView.findFocus();
        etView.requestFocus();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.destory();
            presenter = null;
        }
    }

    @Override
    protected void onResume() {
        // 开放统计 移动数据统计分析
        FlowerCollector.onResume(QuickMatchActivity.this);
        FlowerCollector.onPageStart(TAG);
        super.onResume();
    }

    @Override
    protected void onPause() {
        // 开放统计 移动数据统计分析
        FlowerCollector.onPageEnd(TAG);
        FlowerCollector.onPause(QuickMatchActivity.this);
        super.onPause();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1001 && resultCode == 10000) {
            finish();
        }
    }

    @Override
    public void getLocationInputSuccess(@NotNull CodeNameResponse response) {
        if (response.data != null) {
            if (codeNameAdapter == null) {
                codeNameAdapter = new QuickMatchCoedNameAdapter(QuickMatchActivity.this, bean -> {

                    Intent intent1 = new Intent();
                    intent1.putExtra(getString(R.string.PlaceBean), bean);
                    setResult(1001, intent1);
                    onBackPressed();

                });
                recyclerView.setAdapter(codeNameAdapter);
                codeNameAdapter.setList(response.data);
            } else {
                codeNameAdapter.setList(response.data);
            }
        }
    }

    @Override
    public void getLocationInputFail(@NotNull Throwable throwable) {
        showThrowable(throwable);
    }

    @Override
    public void getIndustryListSuccess(@NotNull IndustryListResponse response) {
        if (response.data != null) {
            if (codeTitleAdapter == null) {
                codeTitleAdapter = new QuickMatchCoedTitleAdapter(QuickMatchActivity.this, bean -> {

                    Intent intent1 = new Intent();
                    intent1.putExtra(getString(R.string.IndustryBean), bean);
                    setResult(1001, intent1);
                    onBackPressed();

                });
                recyclerView.setAdapter(codeTitleAdapter);
                codeTitleAdapter.setList(response.data);
            } else {
                codeTitleAdapter.setList(response.data);
            }
        }
    }

    @Override
    public void getIndustryListFail(@NotNull Throwable throwable) {
        showThrowable(throwable);
    }

    @Override
    public void getProductListSuccess(@NotNull GetAutoProductResponse response) {
        if (response.data != null) {

            ArrayList<CodeNameBean> list = new ArrayList<>();
            for (int i = 0; i < response.data.size(); i++) {
                CodeNameBean bean = new CodeNameBean();
                bean.code = response.data.get(i);
                bean.name = response.data.get(i);
                list.add(bean);
            }

            if (codeNameAdapter == null) {
                codeNameAdapter = new QuickMatchCoedNameAdapter(QuickMatchActivity.this, bean -> {

                    Intent intent1 = new Intent();
                    intent1.putExtra(getString(R.string.ProductBean), bean);
                    setResult(1001, intent1);
                    onBackPressed();

                });
                recyclerView.setAdapter(codeNameAdapter);
                codeNameAdapter.setList(list);
            } else {
                codeNameAdapter.setList(list);
            }
        }
    }

    @Override
    public void getProductListFail(@NotNull Throwable throwable) {
        showThrowable(throwable);
    }

    @Override
    public void userAndCompanyNameAutoCompleteUserNameSuccess(@NotNull UserAndCompanyNameAutoCompleteResponse response) {
        if (response.getData() != null && response.getData().getUserNames() != null) {
            if (codeNameAdapter == null) {
                codeNameAdapter = new QuickMatchCoedNameAdapter(QuickMatchActivity.this, bean -> {

                    Intent intent1 = new Intent();
                    intent1.putExtra(getString(R.string.data1), bean);
                    setResult(1001, intent1);
                    onBackPressed();

                });
                recyclerView.setAdapter(codeNameAdapter);
                codeNameAdapter.setList(response.getData().getUserNames());
            } else {
                codeNameAdapter.setList(response.getData().getUserNames());
            }
        }
    }

    @Override
    public void userAndCompanyNameAutoCompleteUserCompanyNameSuccess(@NotNull UserAndCompanyNameAutoCompleteResponse response) {
        if (response.getData() != null && response.getData().getCompanyNames() != null) {
            if (stringAdapter == null) {
                stringAdapter = new QuickMatchStringAdapter(QuickMatchActivity.this, companyName -> {

                    Intent intent1 = new Intent();
                    intent1.putExtra(getString(R.string.data2), companyName);
                    setResult(1001, intent1);
                    finish();

                });
                recyclerView.setAdapter(stringAdapter);
                stringAdapter.setList(response.getData().getCompanyNames());
            } else {
                stringAdapter.setList(response.getData().getCompanyNames());
            }
        }
    }

    @Override
    public void userAndCompanyNameAutoCompleteUserFail(@NotNull Throwable throwable) {
        showThrowable(throwable);
    }

    @Override
    public void getNameDetailSuccess(@NotNull GetNameDetailResponse response) {
        if (response.getData() != null) {
            if (nameDetailAdapter == null) {
                nameDetailAdapter = new QuickMatchNameDetailAdapter(QuickMatchActivity.this, response.getData(), bean -> {

                    Intent intent1 = new Intent();
                    intent1.putExtra(getString(R.string.DATA), bean);
                    setResult(1001, intent1);
                    finish();

                });
                recyclerView.setAdapter(nameDetailAdapter);
            } else {
                nameDetailAdapter.setListData(response.getData());
            }
        }
    }

    @Override
    public void getNameDetailFail(@NotNull Throwable throwable) {
        showThrowable(throwable);
    }

    @Override
    public void getLawyerCompanyFail(@NotNull Throwable throwable) {
        showThrowable(throwable);
    }

    @Override
    public void getLawyerCompanySuccess(@NotNull ArrayList<GetLawyerCompanyBean> list) {
        if (list.size() > 0) {
            if (lawyerCompanyAdapter == null) {
                lawyerCompanyAdapter = new QuickMatchLawyerCompanyAdapter(QuickMatchActivity.this, list, bean -> {

                    Intent intent1 = new Intent();
                    intent1.putExtra(getString(R.string.DATA), bean);
                    setResult(1001, intent1);
                    finish();

                });
                recyclerView.setAdapter(lawyerCompanyAdapter);
            } else {
                lawyerCompanyAdapter.setListData(list);
            }
        }
    }

    @Override
    public void getLawyerSuccess(@NotNull ArrayList<GetLawyerBean> list) {
        if (list.size() > 0) {
            if (lawyerAdapter == null) {
                lawyerAdapter = new QuickMatchLawyerAdapter(QuickMatchActivity.this, list, bean -> {

                    Intent intent1 = new Intent();
                    intent1.putExtra(getString(R.string.DATA), bean);
                    setResult(1001, intent1);
                    finish();

                });
                recyclerView.setAdapter(lawyerAdapter);
            } else {
                lawyerAdapter.setListData(list);
            }
        }
    }

    @Override
    public void getLawyerFail(@NotNull Throwable throwable) {
        showThrowable(throwable);
    }

    @Override
    public void getCompanyListSuccess(@NotNull ArrayList<ProjectIdNameBean> list) {
        if (list.size() > 0) {
            if (projectIdNameAdapter == null) {
                projectIdNameAdapter = new QuickMatchProjectIdNameAdapter(QuickMatchActivity.this, list, b -> {

                    Intent intent1 = new Intent();
                    intent1.putExtra(getString(R.string.DATA), b);
                    setResult(1001, intent1);
                    finish();

                });
                recyclerView.setAdapter(projectIdNameAdapter);
            } else {
                projectIdNameAdapter.setListData(list);
            }
        }
    }

    @Override
    public void getCompanyListFail(@NotNull Throwable throwable) {
        showThrowable(throwable);
    }
}
