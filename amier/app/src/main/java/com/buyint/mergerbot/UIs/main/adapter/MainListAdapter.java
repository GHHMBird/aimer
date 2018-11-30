package com.buyint.mergerbot.UIs.main.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mobstat.StatService;
import com.buyint.mergerbot.R;
import com.buyint.mergerbot.UIs.main.activity.MatchProjectBackActivity;
import com.buyint.mergerbot.UIs.match.activity.MatchDetailActivity;
import com.buyint.mergerbot.Utility.StringUtils;
import com.buyint.mergerbot.Utility.ViewHelper;
import com.buyint.mergerbot.base.BaseActivity;
import com.buyint.mergerbot.dto.ABC;
import com.buyint.mergerbot.dto.MatchCompanyBean;
import com.buyint.mergerbot.dto.MatchRecordDetailClientModel;
import com.buyint.mergerbot.dto.ProjectInfoBean;
import com.buyint.mergerbot.enums.ProjectType;
import com.buyint.mergerbot.helper.HttpHelper;
import com.buyint.mergerbot.rx.RequestErrorThrowable;

import java.util.ArrayList;

public class MainListAdapter extends RecyclerView.Adapter<MainListAdapter.ResultHolder> {

    private String proId;
    private Context context;
    private ArrayList<MatchCompanyBean> list = new ArrayList<>();
    private int pageType;

    public MainListAdapter(Context context, ArrayList<MatchCompanyBean> list, String projectId, int pageType) {
        this.context = context;
        this.list = list;
        this.proId = projectId;
        this.pageType = pageType;
    }

    public void setList(ArrayList<MatchCompanyBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public ArrayList<MatchCompanyBean> getList() {
        return list;
    }

    public void addList(ArrayList<MatchCompanyBean> list) {
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MainListAdapter.ResultHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ResultHolder(LayoutInflater.from(context).inflate(R.layout.item_user_company_project, null, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MainListAdapter.ResultHolder holder, int position) {

        MatchCompanyBean bean = list.get(position);

        if (bean.matchedProjectType.equals(ProjectType.USER.name())) {

            holder.projectItem.setVisibility(View.GONE);
            holder.matchRecordItem.setVisibility(View.VISIBLE);
            holder.companyItem.setVisibility(View.GONE);
            ABC item = bean.matchedUserListItem;

            holder.mtvRate.setText(((int) item.reliableRate) + "");
            holder.mtvName.setText(item.userName);
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
            holder.mcompany.setText(ccc.toString());

        } else if (bean.matchedProjectType.equals(ProjectType.COMPANY.name())) {

            holder.projectItem.setVisibility(View.GONE);
            holder.matchRecordItem.setVisibility(View.GONE);
            holder.companyItem.setVisibility(View.VISIBLE);
            MatchRecordDetailClientModel item = bean.matchRecordCompanyItem;

            holder.cname.setText(item.getName());
            holder.cproduct.setText(item.getProduct());
            holder.cplace.setText(item.getAddress());
            holder.cindustry.setText(item.getIndustry());

        } else if (bean.matchedProjectType.equals(ProjectType.PROJECT.name())) {

            holder.projectItem.setVisibility(View.VISIBLE);
            holder.matchRecordItem.setVisibility(View.GONE);
            holder.companyItem.setVisibility(View.GONE);

            holder.ptvTitle.setText(bean.projectInfo.projectName);
            holder.ptvCome.setText(bean.projectSource);
            ProjectInfoBean projectInfo = bean.projectInfo;

            if (projectInfo != null && projectInfo.intention != null && projectInfo.intention.size() > 0) {
                if (context.getString(R.string.TYPE_0101).equals(projectInfo.intention.get(0))) {
                    holder.ptvType.setText(context.getString(R.string.acquisition));
                    if (projectInfo.requirement.location != null && projectInfo.requirement.location.size() > 0) {
                        holder.ptvPlace.setText(projectInfo.requirement.location.get(0).name);
                    } else {
                        holder.ptvPlace.setText(context.getString(R.string.guess));
                    }
                    if (projectInfo.requirement.industry_Classification != null && projectInfo.requirement.industry_Classification.size() > 0) {
                        holder.ptvIndustry.setText(projectInfo.requirement.industry_Classification.get(0).name);
                    } else {
                        holder.ptvIndustry.setText(context.getString(R.string.guess));
                    }
                    if (projectInfo.requirement.price == null || projectInfo.requirement.price.size() == 0) {
                        holder.ptvPrice.setText(context.getString(R.string.price_not_show));
                    } else {
                        if (projectInfo.requirement.price.get(0).value >= 0) {
                            holder.ptvPrice.setText(projectInfo.requirement.price.get(0).unit + StringUtils.numberAddDouHao(projectInfo.requirement.price.get(0).value));
                        } else {
                            holder.ptvPrice.setText(projectInfo.requirement.price.get(0).unit);
                        }
                    }
                } else if (context.getString(R.string.TYPE_0102).equals(projectInfo.intention.get(0))) {
                    holder.ptvType.setText(context.getString(R.string.sell));
                    if (projectInfo.condition.location != null && projectInfo.condition.location.size() > 0) {
                        holder.ptvPlace.setText(projectInfo.condition.location.get(0).name);
                    } else {
                        holder.ptvPlace.setText(context.getString(R.string.guess));
                    }
                    if (projectInfo.condition.industry_Classification != null && projectInfo.condition.industry_Classification.size() > 0) {
                        holder.ptvIndustry.setText(projectInfo.condition.industry_Classification.get(0).name);
                    } else {
                        holder.ptvIndustry.setText(context.getString(R.string.guess));
                    }
                    if (projectInfo.condition.price == null || projectInfo.condition.price.size() == 0) {
                        holder.ptvPrice.setText(context.getString(R.string.price_not_show));
                    } else {
                        if (projectInfo.condition.price.get(0).value >= 0) {
                            holder.ptvPrice.setText(projectInfo.condition.price.get(0).unit + StringUtils.numberAddDouHao(projectInfo.condition.price.get(0).value));
                        } else {
                            holder.ptvPrice.setText(projectInfo.condition.price.get(0).unit);
                        }
                    }
                } else if (context.getString(R.string.TYPE_010301).equals(projectInfo.intention.get(0))) {
                    holder.ptvType.setText(context.getString(R.string.investment));
                    if (projectInfo.requirement.location != null && projectInfo.requirement.location.size() > 0) {
                        holder.ptvPlace.setText(projectInfo.requirement.location.get(0).name);
                    } else {
                        holder.ptvPlace.setText(context.getString(R.string.guess));
                    }
                    if (projectInfo.requirement.industry_Classification != null && projectInfo.requirement.industry_Classification.size() > 0) {
                        holder.ptvIndustry.setText(projectInfo.requirement.industry_Classification.get(0).name);
                    } else {
                        holder.ptvIndustry.setText(context.getString(R.string.guess));
                    }
                    if (projectInfo.requirement.price == null || projectInfo.requirement.price.size() == 0) {
                        holder.ptvPrice.setText(context.getString(R.string.price_not_show));
                    } else {
                        if (projectInfo.requirement.price.get(0).value >= 0) {
                            holder.ptvPrice.setText(projectInfo.requirement.price.get(0).unit + StringUtils.numberAddDouHao(projectInfo.requirement.price.get(0).value));
                        } else {
                            holder.ptvPrice.setText(projectInfo.requirement.price.get(0).unit);
                        }
                    }
                } else if (context.getString(R.string.TYPE_010302).equals(projectInfo.intention.get(0))) {
                    holder.ptvType.setText(context.getString(R.string.financing));
                    if (projectInfo.condition.location != null && projectInfo.condition.location.size() > 0) {
                        holder.ptvPlace.setText(projectInfo.condition.location.get(0).name);
                    } else {
                        holder.ptvPlace.setText(context.getString(R.string.guess));
                    }
                    if (projectInfo.condition.industry_Classification != null && projectInfo.condition.industry_Classification.size() > 0) {
                        holder.ptvIndustry.setText(projectInfo.condition.industry_Classification.get(0).name);
                    } else {
                        holder.ptvIndustry.setText(context.getString(R.string.guess));
                    }
                    if (projectInfo.condition.price == null || projectInfo.condition.price.size() == 0) {
                        holder.ptvPrice.setText(context.getString(R.string.price_not_show));
                    } else {
                        if (projectInfo.condition.price.get(0).value >= 0) {
                            holder.ptvPrice.setText(projectInfo.condition.price.get(0).unit + StringUtils.numberAddDouHao(projectInfo.condition.price.get(0).value));
                        } else {
                            holder.ptvPrice.setText(projectInfo.condition.price.get(0).unit);
                        }
                    }
                }
            }

            if (BaseActivity.fontScale == 0.8f) {
                holder.ptvTitle.setMaxLines(2);
            } else if (BaseActivity.fontScale == 0.9f) {
                holder.ptvTitle.setMaxLines(2);
            } else if (BaseActivity.fontScale == 1.0f) {
                holder.ptvTitle.setMaxLines(2);
            } else if (BaseActivity.fontScale == 1.1f) {
                holder.ptvTitle.setMaxLines(1);
            } else if (BaseActivity.fontScale == 1.2f) {
                holder.ptvTitle.setMaxLines(1);
            }

            holder.pllMore.setOnClickListener(v -> {
                if (bean.attention) {
                    //取关
                    ViewHelper.showNoticePopupWindowAuto(context, holder.pivMore, 1, v2 -> unNotice(bean, projectInfo.qid), v11 -> {
                        Intent intent = new Intent(context, MatchProjectBackActivity.class);
                        intent.putExtra(context.getString(R.string.DATA), projectInfo.qid);
                        context.startActivity(intent);
                    });
                } else {
                    //关注
                    ViewHelper.showNoticePopupWindowAuto(context, holder.pivMore, 0, v3 -> notice(bean, projectInfo.qid), v12 -> {
                        Intent intent = new Intent(context, MatchProjectBackActivity.class);
                        intent.putExtra(context.getString(R.string.DATA), projectInfo.qid);
                        context.startActivity(intent);
                    });
                }
            });

            holder.itemView.setOnClickListener(v -> {
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
        }
    }

    private void unNotice(MatchCompanyBean bean, String qid) {
        HttpHelper.notNoticeProject(qid).subscribe(booleanResponse -> {
            if (booleanResponse.data) {
                Toast.makeText(context, "已取消关注", Toast.LENGTH_SHORT).show();
                bean.attention = false;
            } else {
                Toast.makeText(context, "取消关注失败，请稍后再试", Toast.LENGTH_SHORT).show();
            }
        }, throwable -> {
            if (throwable instanceof RequestErrorThrowable) {
                RequestErrorThrowable requestErrorThrowable = (RequestErrorThrowable) throwable;
                Toast.makeText(context, requestErrorThrowable.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void notice(MatchCompanyBean bean, String qid) {
        HttpHelper.noticeProject(qid).subscribe(booleanResponse -> {
            if (booleanResponse.data) {
                Toast.makeText(context, "关注成功", Toast.LENGTH_SHORT).show();
                bean.attention = true;
            } else {
                Toast.makeText(context, "关注失败，请稍后再试", Toast.LENGTH_SHORT).show();
            }
        }, throwable -> {
            if (throwable instanceof RequestErrorThrowable) {
                RequestErrorThrowable requestErrorThrowable = (RequestErrorThrowable) throwable;
                Toast.makeText(context, requestErrorThrowable.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ResultHolder extends RecyclerView.ViewHolder {

        private ImageView pivMore, cmoreIv;
        private TextView ptvPlace, ptvTitle, mtvRate, mtvName, mcompany, cname, ptvCome, ptvType, ptvIndustry, ptvPrice, cplace, cindustry, cproduct;
        private LinearLayout pllMore, cmoreLl, matchRecordItem, companyItem;
        private RelativeLayout projectItem;

        public ResultHolder(View itemView) {
            super(itemView);

            //项目的
            projectItem = itemView.findViewById(R.id.item_user_company_project_1);
            ptvTitle = itemView.findViewById(R.id.item_result2_title);
            ptvPlace = itemView.findViewById(R.id.item_result2_place);
            ptvIndustry = itemView.findViewById(R.id.item_result2_industry);
            ptvPrice = itemView.findViewById(R.id.item_result2_price);
            pllMore = itemView.findViewById(R.id.item_result2_more);
            ptvCome = itemView.findViewById(R.id.item_result2_come);
            ptvType = itemView.findViewById(R.id.item_result2_type);
            pivMore = itemView.findViewById(R.id.item_result2_iv_more);

            //匹配录的
            matchRecordItem = itemView.findViewById(R.id.item_user_company_project_2);
            mtvRate = itemView.findViewById(R.id.item_match_record_match_rate);
            mtvName = itemView.findViewById(R.id.item_match_record_name);
            mcompany = itemView.findViewById(R.id.item_match_record_company);

            //公司的
            companyItem = itemView.findViewById(R.id.item_user_company_project_3);
            cname = itemView.findViewById(R.id.item_company_name);
            cmoreLl = itemView.findViewById(R.id.item_company_more);
            cmoreIv = itemView.findViewById(R.id.item_company_iv_more);
            cproduct = itemView.findViewById(R.id.item_company_product);
            cindustry = itemView.findViewById(R.id.item_company_industry);
            cplace = itemView.findViewById(R.id.item_company_place);

            ((TextView) itemView.findViewById(R.id.item_match_record_match_project)).setText(context.getString(R.string.undisclosed));
            ((TextView) itemView.findViewById(R.id.item_match_record_friend)).setText(context.getString(R.string.undisclosed));
            ((TextView) itemView.findViewById(R.id.item_match_record_client)).setText(context.getString(R.string.undisclosed));
        }
    }
}