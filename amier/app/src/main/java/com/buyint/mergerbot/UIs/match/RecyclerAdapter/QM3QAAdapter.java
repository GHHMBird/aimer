package com.buyint.mergerbot.UIs.match.RecyclerAdapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mobstat.StatService;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.buyint.mergerbot.R;
import com.buyint.mergerbot.UIs.main.activity.MainListActivity;
import com.buyint.mergerbot.UIs.main.activity.MatchProjectBackActivity;
import com.buyint.mergerbot.UIs.main.adapter.QM3QABean;
import com.buyint.mergerbot.UIs.match.activity.MatchDetailActivity;
import com.buyint.mergerbot.Utility.StringUtils;
import com.buyint.mergerbot.Utility.ViewHelper;
import com.buyint.mergerbot.base.BaseActivity;
import com.buyint.mergerbot.dto.ABC;
import com.buyint.mergerbot.dto.BooleanResponse;
import com.buyint.mergerbot.dto.IndustrySuperiorModel;
import com.buyint.mergerbot.dto.MatchCompanyBean;
import com.buyint.mergerbot.dto.MatchCompanyModel;
import com.buyint.mergerbot.dto.MatchRecordDetailClientModel;
import com.buyint.mergerbot.dto.ProjectInfoBean;
import com.buyint.mergerbot.enums.ProjectType;
import com.buyint.mergerbot.interfaces.INotNoticeProject;
import com.buyint.mergerbot.interfaces.INoticeProject;
import com.buyint.mergerbot.presenter.QM3QAPresenter;
import com.buyint.mergerbot.rx.RequestErrorThrowable;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;

/*
 * Created by CXC on 2018/5/16.
 */

public class QM3QAAdapter extends RecyclerView.Adapter<QM3QAAdapter.ViewHolder> implements INotNoticeProject, INoticeProject {

    private Context context;
    private ArrayList<QM3QABean> list = new ArrayList<>();
    private final int SEND = 0;
    private final int RECEIVE = 1;
    private final int ZPXM = 2;
    private final int TIME = -1;
    private final int XGGS = 3;
    private final int TYPE0 = 100;
    private final int TYPE1 = 101;
    private final int TYPE2 = 102;
    private final int CHANGE_AIMER = 103;
    private final int CHANGE_USER = 104;
    private final int EMPTY = 1000;
    private final QM3QABean emptyBean = new QM3QABean();
    private QM3QAAdapterRegisterListener qm3QAAdapterRegisterListener;
    private QM3QAAdapterMainListener mainListener;
    private QM3QAAdapterChangeTextListener changeTextListener;
    private File file;
    private QM3QAPresenter presenter;

    public QM3QAAdapter(Context context) {
        this.context = context;
        presenter = new QM3QAPresenter(this, this);
        emptyBean.code = context.getString(R.string.EMPTY);
    }

    @Override
    public int getItemViewType(int position) {
        if (context.getString(R.string.SEND).equals(list.get(position).code)) {
            return SEND;
        } else if (context.getString(R.string.RECEIVE).equals(list.get(position).code)) {
            return RECEIVE;
        } else if (context.getString(R.string.ZPXM).equals(list.get(position).code)) {
            return ZPXM;
        } else if (context.getString(R.string.TIME).equals(list.get(position).code)) {
            return TIME;
        } else if (context.getString(R.string.XGGS).equals(list.get(position).code)) {
            return XGGS;
        } else if (context.getString(R.string.TYPE_1).equals(list.get(position).code)) {
            return TYPE1;
        } else if (context.getString(R.string.TYPE_0).equals(list.get(position).code)) {
            return TYPE0;
        } else if (context.getString(R.string.TYPE_2).equals(list.get(position).code)) {
            return TYPE2;
        } else if (context.getString(R.string.EMPTY).equals(list.get(position).code)) {
            return EMPTY;
        } else if (context.getString(R.string.aimer).equals(list.get(position).code)) {
            return CHANGE_AIMER;
        } else if (context.getString(R.string.user).equals(list.get(position).code)) {
            return CHANGE_USER;
        } else {
            return -1;
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case SEND:
                return new ViewHolder(View.inflate(context, R.layout.item_send_msg, null));
            case RECEIVE:
                return new ViewHolder(View.inflate(context, R.layout.item_receive_msg, null));
            case TYPE2:
                return new ViewHolder(View.inflate(context, R.layout.item_user_card, null));
            case TYPE0:
                return new ViewHolder(View.inflate(context, R.layout.item_i_am, null));
            case TYPE1:
                return new ViewHolder(View.inflate(context, R.layout.item_aimer_card, null));
            case XGGS:
                return new ViewHolder(View.inflate(context, R.layout.item_related_master, null));
            case TIME:
                return new ViewHolder(View.inflate(context, R.layout.item_time, null));
            case ZPXM:
                return new ViewHolder(View.inflate(context, R.layout.item_zpxm, null));
            case EMPTY:
                return new ViewHolder(View.inflate(context, R.layout.item_empty, null));
            case CHANGE_AIMER:
                return new ViewHolder(View.inflate(context, R.layout.item_change_aimer, null));
            case CHANGE_USER:
                return new ViewHolder(View.inflate(context, R.layout.item_change_user, null));
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (context.getString(R.string.EMPTY).equals(list.get(position).code)) {

        } else if (context.getString(R.string.TIME).equals(list.get(position).code)) {
            holder.tvTime.setText(list.get(position).name);
        } else if (context.getString(R.string.SEND).equals(list.get(position).code)) {
            holder.tvSend.setText(list.get(position).name);
        } else if (context.getString(R.string.RECEIVE).equals(list.get(position).code)) {
            holder.tvReceive.setText(list.get(position).name);
        } else if (context.getString(R.string.aimer).equals(list.get(position).code)) {
            holder.aimerTv.setText(list.get(position).name);
            holder.aimerEt.setHint(list.get(position).name);
            holder.aimerTv.setOnClickListener(v -> {
                holder.aimerTv.setVisibility(View.GONE);
                holder.aimerEt.setVisibility(View.VISIBLE);
                holder.aimerTvClick.setVisibility(View.VISIBLE);
            });
            holder.aimerTvClick.setOnClickListener(v -> {
                if (!TextUtils.isEmpty(holder.aimerEt.getText().toString().trim())) {
                    if (changeTextListener != null) {
                        changeTextListener.changeText(list.get(position).time, list.get(position).name, holder.aimerEt.getText().toString().trim());
                    }
                } else {

                }
            });
        } else if (context.getString(R.string.user).equals(list.get(position).code)) {
            holder.userTv.setText(list.get(position).name);
            holder.userEt.setHint(list.get(position).name);
            holder.userTv.setOnClickListener(v -> {
                holder.userTv.setVisibility(View.GONE);
                holder.userEt.setVisibility(View.VISIBLE);
                holder.userTvClick.setVisibility(View.VISIBLE);
            });
            holder.userTvClick.setOnClickListener(v -> {
                if (!TextUtils.isEmpty(holder.userEt.getText().toString().trim())) {
                    if (changeTextListener != null) {
                        changeTextListener.changeText(list.get(position).time, list.get(position).name, holder.userEt.getText().toString().trim());
                    }
                } else {

                }
            });
        } else if (context.getString(R.string.TYPE_0).equals(list.get(position).code)) {
            holder.etName.setOnEditorActionListener((v, actionId, event) -> {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    if (qm3QAAdapterRegisterListener != null && !TextUtils.isEmpty(holder.etName.getText().toString().trim())) {
                        qm3QAAdapterRegisterListener.nameOk(holder.etName.getText().toString().trim());
                    }
                }
                return false;
            });
        } else if (context.getString(R.string.TYPE_1).equals(list.get(position).code)) {
            holder.viewOk.setOnClickListener(v -> {
                if (qm3QAAdapterRegisterListener != null) {
                    qm3QAAdapterRegisterListener.type2Ok();
                }
            });
            holder.viewNo.setOnClickListener(v -> {
                if (qm3QAAdapterRegisterListener != null) {
                    qm3QAAdapterRegisterListener.typeNo();
                }
            });
        } else if (context.getString(R.string.TYPE_2).equals(list.get(position).code)) {
            QM3QABean qm3QABean = list.get(position);
            Glide.with(context).load(file).asBitmap().centerCrop().error(R.mipmap.default_user).placeholder(R.mipmap.default_user).into(new BitmapImageViewTarget(holder.type2Iv) {
                @Override
                protected void setResource(Bitmap resource) {
                    RoundedBitmapDrawable rbd = RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                    rbd.setCircular(true);
                    holder.type2Iv.setImageDrawable(rbd);
                }
            });
            holder.type2Iv.setOnClickListener(v -> {
                if (qm3QAAdapterRegisterListener != null) {
                    qm3QAAdapterRegisterListener.type2Image();
                }
            });
            holder.type2EtName.setText(qm3QABean.name);
            holder.type2ViewOk.setOnClickListener(v -> {
                if (TextUtils.isEmpty(holder.type2EtPosition.getText().toString().trim())) {
                    return;
                }
                if (TextUtils.isEmpty(holder.type2EtCompanyName.getText().toString().trim())) {
                    return;
                }
                if (TextUtils.isEmpty(holder.type2EtWorkEmail.getText().toString().trim())) {
                    return;
                }
                if (TextUtils.isEmpty(holder.type2EtCompanyWeb.getText().toString().trim())) {
                    return;
                }
                qm3QAAdapterRegisterListener.type3Ok(holder.type2EtName.getText().toString().trim(), holder.type2EtPosition.getText().toString().trim(), holder.type2EtCompanyWeb.getText().toString().trim(), holder.type2EtWorkEmail.getText().toString().trim(), holder.type2EtIndustrySubdivision.getText().toString().trim(), holder.type2EtDivisionOfLabor.getText().toString().trim(), holder.type2EtCompanyName.getText().toString().trim(), holder.type2EtMajorBusiness.getText().toString().trim());
            });
            holder.type2ViewNo.setOnClickListener(v -> {
                if (qm3QAAdapterRegisterListener != null) {
                    qm3QAAdapterRegisterListener.typeNo();
                }
            });
        } else if (context.getString(R.string.XGGS).equals(list.get(position).code)) {
            holder.llXGGS.removeAllViews();
            ArrayList<IndustrySuperiorModel> data = list.get(position).is.data;
            for (int i = 0; i < data.size(); i++) {
                IndustrySuperiorModel model = data.get(i);
                View view = LayoutInflater.from(context).inflate(R.layout.item_people, null, false);
                ImageView ivIcon = view.findViewById(R.id.item_people_iv);
                TextView tvName = view.findViewById(R.id.item_people_name);
                TextView tvCompany = view.findViewById(R.id.item_people_company);
                TextView tvIndustry = view.findViewById(R.id.item_people_industry);
                TextView tvDesc = view.findViewById(R.id.item_people_desc);
                Glide.with(context).load(model.avatarUrl).placeholder(R.mipmap.default_user).error(R.mipmap.default_user).into(ivIcon);
                tvName.setText(model.name);
                tvCompany.setText(model.companyName);
                tvDesc.setText(model.discription);
                if (context.getString(R.string.lawyer).equals(model.role)) {
                    tvIndustry.setText(model.role);
                    tvIndustry.setBackgroundColor(ContextCompat.getColor(context, R.color.colorBlue));
                } else if (context.getString(R.string.investor).equals(model.role)) {
                    tvIndustry.setText(model.role);
                    tvIndustry.setBackgroundColor(ContextCompat.getColor(context, R.color.colorRed));
                }

                if (BaseActivity.fontScale == 0.8f) {
                    tvDesc.setMaxLines(3);
                } else if (BaseActivity.fontScale == 0.9f) {
                    tvDesc.setMaxLines(2);
                } else if (BaseActivity.fontScale == 1.0f) {
                    tvDesc.setMaxLines(2);
                } else if (BaseActivity.fontScale == 1.1f) {
                    tvDesc.setMaxLines(2);
                } else if (BaseActivity.fontScale == 1.2f) {
                    tvDesc.setMaxLines(1);
                }

                holder.llXGGS.addView(view);
            }
        } else if (context.getString(R.string.ZPXM).equals(list.get(position).code)) {
            holder.ll.removeAllViews();
            MatchCompanyModel response = list.get(position).data;
            ArrayList<MatchCompanyBean> beanList = response.relationProject;
            ArrayList<MatchCompanyBean> arr = new ArrayList<>();
            if (beanList.size() > 3) {
                arr.add(beanList.get(0));
                arr.add(beanList.get(1));
                arr.add(beanList.get(2));
                holder.tvSeeMore.setVisibility(View.VISIBLE);
                holder.tvGoToWeb.setVisibility(View.GONE);
            } else {
                holder.tvSeeMore.setVisibility(View.GONE);
                holder.tvGoToWeb.setVisibility(View.VISIBLE);
                arr.addAll(beanList);
            }
            for (int i = 0; i < arr.size(); i++) {
                MatchCompanyBean bean = arr.get(i);

                if (bean.matchedProjectType.equals(ProjectType.USER.name())) {

                    View view = LayoutInflater.from(context).inflate(R.layout.item_match_record, null, false);
                    TextView tvRate = view.findViewById(R.id.item_match_record_match_rate);
                    TextView tvName = view.findViewById(R.id.item_match_record_name);
                    TextView company = view.findViewById(R.id.item_match_record_company);

                    ((TextView) view.findViewById(R.id.item_match_record_match_project)).setText(context.getString(R.string.undisclosed));
                    ((TextView) view.findViewById(R.id.item_match_record_friend)).setText(context.getString(R.string.undisclosed));
                    ((TextView) view.findViewById(R.id.item_match_record_client)).setText(context.getString(R.string.undisclosed));

                    ABC item = bean.matchedUserListItem;

                    tvRate.setText(((int) item.reliableRate) + "");
                    tvName.setText(item.userName);
                    StringBuilder ccc = new StringBuilder();
                    if (!TextUtils.isEmpty(item.companyName)) {
                        ccc.append(item.companyName).append("|");
                    } else {
                        ccc.append(context.getString(R.string.undisclosed_company)).append("|");
                    }
                    if (!TextUtils.isEmpty(item.position)) {
                        ccc.append(item.position);
                    } else {
                        ccc.append(context.getString(R.string.undisclosed_position));
                    }
                    company.setText(ccc.toString());

                    holder.ll.addView(view);

                } else if (bean.matchedProjectType.equals(ProjectType.COMPANY.name())) {
                    View view = LayoutInflater.from(context).inflate(R.layout.item_company, null, false);
                    TextView name = view.findViewById(R.id.item_company_name);
                    LinearLayout moreLl = view.findViewById(R.id.item_company_more);
                    ImageView moreIv = view.findViewById(R.id.item_company_iv_more);
                    TextView product = view.findViewById(R.id.item_company_product);
                    TextView industry = view.findViewById(R.id.item_company_industry);
                    TextView place = view.findViewById(R.id.item_company_place);

                    MatchRecordDetailClientModel item = bean.matchRecordCompanyItem;

                    name.setText(item.getName());
                    product.setText(item.getProduct());
                    place.setText(item.getAddress());
                    industry.setText(item.getIndustry());

                    holder.ll.addView(view);

                } else if (bean.matchedProjectType.equals(ProjectType.PROJECT.name())) {
                    View view = LayoutInflater.from(context).inflate(R.layout.item_result2, null, false);
                    TextView tvTitle = view.findViewById(R.id.item_result2_title);
                    LinearLayout llMore = view.findViewById(R.id.item_result2_more);
                    ImageView ivMore = view.findViewById(R.id.item_result2_iv_more);
                    TextView tvPlace = view.findViewById(R.id.item_result2_place);
                    TextView tvPrice = view.findViewById(R.id.item_result2_price);
                    TextView tvIndustry = view.findViewById(R.id.item_result2_industry);
                    TextView tvType = view.findViewById(R.id.item_result2_type);
                    TextView tvCome = view.findViewById(R.id.item_result2_come);
                    tvTitle.setText(bean.projectInfo.projectName);
                    tvCome.setText(bean.projectSource);
                    ProjectInfoBean projectInfo = bean.projectInfo;

                    if (projectInfo != null && projectInfo.intention != null && projectInfo.intention.size() > 0) {
                        if (context.getString(R.string.TYPE_0101).equals(projectInfo.intention.get(0))) {
                            tvType.setText(context.getString(R.string.acquisition));
                            if (projectInfo.requirement.location != null && projectInfo.requirement.location.size() > 0) {
                                tvPlace.setText(projectInfo.requirement.location.get(0).name);
                            } else {
                                tvPlace.setText(context.getString(R.string.guess));
                            }
                            if (projectInfo.requirement.industry_Classification != null && projectInfo.requirement.industry_Classification.size() > 0) {
                                tvIndustry.setText(projectInfo.requirement.industry_Classification.get(0).name);
                            } else {
                                tvIndustry.setText(context.getString(R.string.guess));
                            }
                            if (projectInfo.requirement.price == null || projectInfo.requirement.price.size() == 0) {
                                tvPrice.setText(context.getString(R.string.price_not_show));
                            } else {
                                if (projectInfo.requirement.price.get(0).value >= 0) {
                                    tvPrice.setText(projectInfo.requirement.price.get(0).unit + StringUtils.numberAddDouHao(projectInfo.requirement.price.get(0).value));
                                } else {
                                    tvPrice.setText(projectInfo.requirement.price.get(0).unit);
                                }
                            }
                        } else if (context.getString(R.string.TYPE_0102).equals(projectInfo.intention.get(0))) {
                            tvType.setText(context.getString(R.string.sell));
                            if (projectInfo.condition.location != null && projectInfo.condition.location.size() > 0) {
                                tvPlace.setText(projectInfo.condition.location.get(0).name);
                            } else {
                                tvPlace.setText(context.getString(R.string.guess));
                            }
                            if (projectInfo.condition.industry_Classification != null && projectInfo.condition.industry_Classification.size() > 0) {
                                tvIndustry.setText(projectInfo.condition.industry_Classification.get(0).name);
                            } else {
                                tvIndustry.setText(context.getString(R.string.guess));
                            }
                            if (projectInfo.condition.price == null || projectInfo.condition.price.size() == 0) {
                                tvPrice.setText(context.getString(R.string.price_not_show));
                            } else {
                                if (projectInfo.condition.price.get(0).value >= 0) {
                                    tvPrice.setText(projectInfo.condition.price.get(0).unit + StringUtils.numberAddDouHao(projectInfo.condition.price.get(0).value));
                                } else {
                                    tvPrice.setText(projectInfo.condition.price.get(0).unit);
                                }
                            }
                        } else if (context.getString(R.string.TYPE_010301).equals(projectInfo.intention.get(0))) {
                            tvType.setText(context.getString(R.string.investment));
                            if (projectInfo.requirement.location != null && projectInfo.requirement.location.size() > 0) {
                                tvPlace.setText(projectInfo.requirement.location.get(0).name);
                            } else {
                                tvPlace.setText(context.getString(R.string.guess));
                            }
                            if (projectInfo.requirement.industry_Classification != null && projectInfo.requirement.industry_Classification.size() > 0) {
                                tvIndustry.setText(projectInfo.requirement.industry_Classification.get(0).name);
                            } else {
                                tvIndustry.setText(context.getString(R.string.guess));
                            }
                            if (projectInfo.requirement.price == null || projectInfo.requirement.price.size() == 0) {
                                tvPrice.setText(context.getString(R.string.price_not_show));
                            } else {
                                if (projectInfo.requirement.price.get(0).value >= 0) {
                                    tvPrice.setText(projectInfo.requirement.price.get(0).unit + StringUtils.numberAddDouHao(projectInfo.requirement.price.get(0).value));
                                } else {
                                    tvPrice.setText(projectInfo.requirement.price.get(0).unit);
                                }
                            }
                        } else if (context.getString(R.string.TYPE_010302).equals(projectInfo.intention.get(0))) {
                            tvType.setText(context.getString(R.string.financing));
                            if (projectInfo.condition.location != null && projectInfo.condition.location.size() > 0) {
                                tvPlace.setText(projectInfo.condition.location.get(0).name);
                            } else {
                                tvPlace.setText(context.getString(R.string.guess));
                            }
                            if (projectInfo.condition.industry_Classification != null && projectInfo.condition.industry_Classification.size() > 0) {
                                tvIndustry.setText(projectInfo.condition.industry_Classification.get(0).name);
                            } else {
                                tvIndustry.setText(context.getString(R.string.guess));
                            }
                            if (projectInfo.condition.price == null || projectInfo.condition.price.size() == 0) {
                                tvPrice.setText(context.getString(R.string.price_not_show));
                            } else {
                                if (projectInfo.condition.price.get(0).value >= 0) {
                                    tvPrice.setText(projectInfo.condition.price.get(0).unit + StringUtils.numberAddDouHao(projectInfo.condition.price.get(0).value));
                                } else {
                                    tvPrice.setText(projectInfo.condition.price.get(0).unit);
                                }
                            }
                        }
                    }

                    if (BaseActivity.fontScale == 0.8f) {
                        tvTitle.setMaxLines(2);
                    } else if (BaseActivity.fontScale == 0.9f) {
                        tvTitle.setMaxLines(2);
                    } else if (BaseActivity.fontScale == 1.0f) {
                        tvTitle.setMaxLines(2);
                    } else if (BaseActivity.fontScale == 1.1f) {
                        tvTitle.setMaxLines(1);
                    } else if (BaseActivity.fontScale == 1.2f) {
                        tvTitle.setMaxLines(1);
                    }

                    llMore.setOnClickListener(v -> {
                        if (bean.attention) {
                            //取关
                            ViewHelper.showNoticePopupWindowAuto(context, ivMore, 1, v2 -> presenter.notNoticeProject(bean, projectInfo.qid), v11 -> {
                                Intent intent = new Intent(context, MatchProjectBackActivity.class);
                                intent.putExtra(context.getString(R.string.DATA), projectInfo.qid);
                                context.startActivity(intent);
                            });
                        } else {
                            //关注
                            ViewHelper.showNoticePopupWindowAuto(context, ivMore, 0, v3 -> presenter.noticeProject(bean, projectInfo.qid), v12 -> {
                                Intent intent = new Intent(context, MatchProjectBackActivity.class);
                                intent.putExtra(context.getString(R.string.DATA), projectInfo.qid);
                                context.startActivity(intent);
                            });
                        }
                    });

                    view.setOnClickListener(v -> {
                        if (context.getString(R.string.quick_match).equals(bean.projectSource)) {
                            Toast.makeText(context, context.getString(R.string.now_not_have_detail), Toast.LENGTH_SHORT).show();
                        } else {
                            StatService.onEvent(context, MatchDetailActivity.class.getSimpleName(), "从列表页面进入项目详情页面", 1);
                            Intent intent = new Intent(context, MatchDetailActivity.class);
                            intent.putExtra(context.getString(R.string.CLICK_ID), bean.projectInfo.qid);
                            intent.putExtra(context.getString(R.string.TYPE), bean.type);
                            intent.putExtra(context.getString(R.string.PROID), context.getString(R.string.userRecommend));
                            context.startActivity(intent);
                        }
                    });
                    holder.ll.addView(view);
                }
            }
            holder.tvSeeMore.setOnClickListener(v -> {
                Intent intent = new Intent(context, MainListActivity.class);
                intent.putExtra(context.getString(R.string.TYPE), list.get(position).type);
                intent.putExtra(context.getString(R.string.result), list.get(position).data);
                context.startActivity(intent);
            });
            holder.tvGoToWeb.setOnClickListener(v -> {
                if (mainListener != null) {
                    mainListener.goToWeb();
                }
            });
        }
    }

    private void changeText() {

    }

    public void addData(QM3QABean bean) {
        if (list.contains(emptyBean)) {
            list.remove(emptyBean);
        }
        list.add(bean);
        list.add(emptyBean);
        notifyDataSetChanged();
    }

    public ArrayList<QM3QABean> getList() {
        return list;
    }

    public void addList(ArrayList<QM3QABean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void clearData() {
        list.clear();
        notifyDataSetChanged();
    }

    @Override
    public void notNoticeProjectSuccess(MatchCompanyBean bean, @NotNull BooleanResponse response) {
        if (response.data) {
            Toast.makeText(context, "已取消关注", Toast.LENGTH_SHORT).show();
            bean.attention = false;
        } else {
            Toast.makeText(context, "取消关注失败，请稍后再试", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void notNoticeProjectFail(@NotNull Throwable throwable) {
        if (throwable instanceof RequestErrorThrowable) {
            RequestErrorThrowable requestErrorThrowable = (RequestErrorThrowable) throwable;
            Toast.makeText(context, requestErrorThrowable.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void noticeProjectSuccess(MatchCompanyBean bean, @NotNull BooleanResponse response) {
        if (response.data) {
            Toast.makeText(context, "关注成功", Toast.LENGTH_SHORT).show();
            bean.attention = true;
        } else {
            Toast.makeText(context, "关注失败，请稍后再试", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void noticeProjectFail(@NotNull Throwable throwable) {
        if (throwable instanceof RequestErrorThrowable) {
            RequestErrorThrowable requestErrorThrowable = (RequestErrorThrowable) throwable;
            Toast.makeText(context, requestErrorThrowable.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void destory() {
        if (presenter != null) {
            presenter.destory();
            presenter = null;
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout ll, llXGGS;
        private TextView userTv, userTvClick, aimerTvClick, aimerTv, tvSeeMore, tvGoToWeb, type2EtCompanyName, type2EtMajorBusiness, type2EtIndustrySubdivision, type2EtDivisionOfLabor, tvTime, type2EtCompanyWeb, tvSend, type2EtWorkEmail, tvReceive, type2EtName, type2EtPosition;
        private View viewOk, viewNo, type2ViewNo, type2ViewOk;
        private EditText etName, userEt, aimerEt;
        private ImageView type2Iv;

        public ViewHolder(View itemView) {
            super(itemView);
            //type send
            tvSend = itemView.findViewById(R.id.item_send_msg_tv);

            //type receive
            tvReceive = itemView.findViewById(R.id.item_receive_msg_tv);

            //type zpxm
            ll = itemView.findViewById(R.id.item_zpxm_ll);
            tvSeeMore = itemView.findViewById(R.id.item_zpxm_see_more);
            tvGoToWeb = itemView.findViewById(R.id.item_zpxm_go_to_web);

            //type time
            tvTime = itemView.findViewById(R.id.item_time_time);

            //type xggs
            llXGGS = itemView.findViewById(R.id.item_related_projects_ll);

            //type type1
            viewOk = itemView.findViewById(R.id.item_aimer_card_ok);
            viewNo = itemView.findViewById(R.id.item_aimer_card_no);

            //type type0
            etName = itemView.findViewById(R.id.item_i_am_et);

            //type type2
            type2ViewNo = itemView.findViewById(R.id.item_user_card_no);
            type2ViewOk = itemView.findViewById(R.id.item_user_card_ok);
            type2EtName = itemView.findViewById(R.id.item_user_card_name);
            type2EtPosition = itemView.findViewById(R.id.item_user_card_position);
            type2EtCompanyWeb = itemView.findViewById(R.id.item_user_card_company_web);
            type2EtWorkEmail = itemView.findViewById(R.id.item_user_card_work_email);
            type2EtIndustrySubdivision = itemView.findViewById(R.id.item_user_card_industry_subdivision);
            type2EtDivisionOfLabor = itemView.findViewById(R.id.item_user_card_division_of_labor);
            type2EtCompanyName = itemView.findViewById(R.id.item_user_card_company_name);
            type2EtMajorBusiness = itemView.findViewById(R.id.item_user_card_major_business);
            type2Iv = itemView.findViewById(R.id.item_user_card_icon);

            //type change_aimer
            aimerTv = itemView.findViewById(R.id.item_change_aimer_tv);
            aimerEt = itemView.findViewById(R.id.item_change_aimer_et);
            aimerTvClick = itemView.findViewById(R.id.item_change_aimer_tv_click);

            //type change_user
            userTv = itemView.findViewById(R.id.item_change_user_tv);
            userEt = itemView.findViewById(R.id.item_change_user_et);
            userTvClick = itemView.findViewById(R.id.item_change_user_tv_click);
        }
    }

    public interface QM3QAAdapterRegisterListener {
        void nameOk(String name);

        void type2Ok();

        void typeNo();

        void type3Ok(String name, String position, String companyWeb, String workEmail, String industrySubdivision, String divisionOfLabor, String companyName, String majorBusiness);

        void type2Image();
    }

    public void setMainListener(QM3QAAdapterMainListener listener) {
        this.mainListener = listener;
    }

    public interface QM3QAAdapterMainListener {
        void goToWeb();
    }

    public void setChangeTextListener(QM3QAAdapterChangeTextListener listener) {
        this.changeTextListener = listener;
    }

    public interface QM3QAAdapterChangeTextListener {
        void changeText(long time, String oldText, String newText);
    }
}
