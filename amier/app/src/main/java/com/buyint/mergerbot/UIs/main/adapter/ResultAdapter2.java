package com.buyint.mergerbot.UIs.main.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mobstat.StatService;
import com.buyint.mergerbot.R;
import com.buyint.mergerbot.UIs.main.activity.MatchProjectBackActivity;
import com.buyint.mergerbot.UIs.match.activity.MatchDetailActivity;
import com.buyint.mergerbot.UIs.user.activity.MyProjectActivity;
import com.buyint.mergerbot.Utility.StringUtils;
import com.buyint.mergerbot.Utility.ViewHelper;
import com.buyint.mergerbot.base.BaseActivity;
import com.buyint.mergerbot.dto.MatchCompanyBean;
import com.buyint.mergerbot.dto.ProjectInfoBean;
import com.buyint.mergerbot.helper.HttpHelper;
import com.buyint.mergerbot.rx.RequestErrorThrowable;

import java.util.ArrayList;

public class ResultAdapter2 extends RecyclerView.Adapter<ResultAdapter2.ResultHolder> {

    private String proId;
    private Context context;
    private ArrayList<MatchCompanyBean> list = new ArrayList<>();
    private int pageType;

    public ResultAdapter2(Context context, ArrayList<MatchCompanyBean> list, String projectId, int pageType) {
        this.context = context;
        this.list = list;
        this.proId = projectId;
        this.pageType = pageType;
    }

    @Override
    public ResultHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ResultHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_result2, null));
    }

    @Override
    public void onBindViewHolder(ResultHolder holder, int position) {
        MatchCompanyBean bean = list.get(position);
        holder.tvTitle.setText(bean.projectInfo.projectName);
        holder.tvCome.setText(bean.projectSource);

        ProjectInfoBean projectInfo = bean.projectInfo;
        if (projectInfo != null && projectInfo.intention != null && projectInfo.intention.size() > 0) {
            if (context.getString(R.string.TYPE_0101).equals(projectInfo.intention.get(0))) {
                holder.tvType.setText(context.getString(R.string.acquisition));
                if (projectInfo.requirement.location != null && projectInfo.requirement.location.size() > 0) {
                    holder.tvPlace.setText(projectInfo.requirement.location.get(0).name);
                } else {
                    holder.tvPlace.setText(context.getString(R.string.i_dont_know));
                }
                if (projectInfo.requirement.industry_Classification != null && projectInfo.requirement.industry_Classification.size() > 0) {
                    holder.tvIndustry.setText(projectInfo.requirement.industry_Classification.get(0).name);
                } else {
                    holder.tvIndustry.setText(context.getString(R.string.i_dont_know));
                }
                if (projectInfo.requirement.price == null || projectInfo.requirement.price.size() == 0) {
                    holder.tvPrice.setText(context.getString(R.string.price_not_show));
                } else {
                    if (projectInfo.requirement.price.get(0).value >= 0) {
                        holder.tvPrice.setText(projectInfo.requirement.price.get(0).unit + StringUtils.numberAddDouHao(projectInfo.requirement.price.get(0).value));
                    } else {
                        holder.tvPrice.setText(projectInfo.requirement.price.get(0).unit);
                    }
                }
            } else if (context.getString(R.string.TYPE_0102).equals(projectInfo.intention.get(0))) {
                holder.tvType.setText(context.getString(R.string.sell));
                if (projectInfo.condition.location != null && projectInfo.condition.location.size() > 0) {
                    holder.tvPlace.setText(projectInfo.condition.location.get(0).name);
                } else {
                    holder.tvPlace.setText(context.getString(R.string.i_dont_know));
                }
                if (projectInfo.condition.industry_Classification != null && projectInfo.condition.industry_Classification.size() > 0) {
                    holder.tvIndustry.setText(projectInfo.condition.industry_Classification.get(0).name);
                } else {
                    holder.tvIndustry.setText(context.getString(R.string.i_dont_know));
                }
                if (projectInfo.condition.price == null || projectInfo.condition.price.size() == 0) {
                    holder.tvPrice.setText(context.getString(R.string.price_not_show));
                } else {
                    if (projectInfo.condition.price.get(0).value >= 0) {
                        holder.tvPrice.setText(projectInfo.condition.price.get(0).unit + StringUtils.numberAddDouHao(projectInfo.condition.price.get(0).value));
                    } else {
                        holder.tvPrice.setText(projectInfo.condition.price.get(0).unit);
                    }
                }
            } else if (context.getString(R.string.TYPE_010301).equals(projectInfo.intention.get(0))) {
                holder.tvType.setText(context.getString(R.string.investment));
                if (projectInfo.requirement.location != null && projectInfo.requirement.location.size() > 0) {
                    holder.tvPlace.setText(projectInfo.requirement.location.get(0).name);
                } else {
                    holder.tvPlace.setText(context.getString(R.string.i_dont_know));
                }
                if (projectInfo.requirement.industry_Classification != null && projectInfo.requirement.industry_Classification.size() > 0) {
                    holder.tvIndustry.setText(projectInfo.requirement.industry_Classification.get(0).name);
                } else {
                    holder.tvIndustry.setText(context.getString(R.string.i_dont_know));
                }
                if (projectInfo.requirement.price == null || projectInfo.requirement.price.size() == 0) {
                    holder.tvPrice.setText(context.getString(R.string.price_not_show));
                } else {
                    if (projectInfo.requirement.price.get(0).value >= 0) {
                        holder.tvPrice.setText(projectInfo.requirement.price.get(0).unit + StringUtils.numberAddDouHao(projectInfo.requirement.price.get(0).value));
                    } else {
                        holder.tvPrice.setText(projectInfo.requirement.price.get(0).unit);
                    }
                }
            } else if (context.getString(R.string.TYPE_010302).equals(projectInfo.intention.get(0))) {
                holder.tvType.setText(context.getString(R.string.financing));
                if (projectInfo.condition.location != null && projectInfo.condition.location.size() > 0) {
                    holder.tvPlace.setText(projectInfo.condition.location.get(0).name);
                } else {
                    holder.tvPlace.setText(context.getString(R.string.i_dont_know));
                }
                if (projectInfo.condition.industry_Classification != null && projectInfo.condition.industry_Classification.size() > 0) {
                    holder.tvIndustry.setText(projectInfo.condition.industry_Classification.get(0).name);
                } else {
                    holder.tvIndustry.setText(context.getString(R.string.i_dont_know));
                }
                if (projectInfo.condition.price == null || projectInfo.condition.price.size() == 0) {
                    holder.tvPrice.setText(context.getString(R.string.price_not_show));
                } else {
                    if (projectInfo.condition.price.get(0).value >= 0) {
                        holder.tvPrice.setText(projectInfo.condition.price.get(0).unit + StringUtils.numberAddDouHao(projectInfo.condition.price.get(0).value));
                    } else {
                        holder.tvPrice.setText(projectInfo.condition.price.get(0).unit);
                    }
                }
            }
        }

        if (BaseActivity.fontScale == 0.8f) {
            holder.tvTitle.setMaxLines(2);
        } else if (BaseActivity.fontScale == 0.9f) {
            holder.tvTitle.setMaxLines(2);
        } else if (BaseActivity.fontScale == 1.0f) {
            holder.tvTitle.setMaxLines(2);
        } else if (BaseActivity.fontScale == 1.1f) {
            holder.tvTitle.setMaxLines(1);
        } else if (BaseActivity.fontScale == 1.2f) {
            holder.tvTitle.setMaxLines(1);
        }

        holder.llMore.setOnClickListener(v -> {

            switch (pageType) {
                case 0://关注取关

                    if (bean.attention) {
                        //取关
                        ViewHelper.showNoticePopupWindowAuto(context, holder.ivMore, 1, v15 -> unNotice(bean, projectInfo.qid), v110 -> {
                            Intent intent = new Intent(context, MatchProjectBackActivity.class);
                            intent.putExtra(context.getString(R.string.DATA), projectInfo.qid);
                            context.startActivity(intent);
                        });
                    } else {
                        //关注
                        ViewHelper.showNoticePopupWindowAuto(context, holder.ivMore, 0, v14 -> notice(bean, projectInfo.qid), v111 -> {
                            Intent intent = new Intent(context, MatchProjectBackActivity.class);
                            intent.putExtra(context.getString(R.string.DATA), projectInfo.qid);
                            context.startActivity(intent);
                        });
                    }

                    break;
                case 1://删除
                    ViewHelper.showNoticePopupWindowAuto(context, holder.ivMore, 2, v1 -> delete(bean, projectInfo.qid), v112 -> {
                        Intent intent = new Intent(context, MatchProjectBackActivity.class);
                        intent.putExtra(context.getString(R.string.DATA), projectInfo.qid);
                        context.startActivity(intent);
                    });
                    break;
                case 2://关注页面(取消关注的时候从列表清除)
                    if (bean.attention) {
                        //取关
                        ViewHelper.showNoticePopupWindowAuto(context, holder.ivMore, 1, v13 -> unNoticeAndDelete(bean, projectInfo.qid), v113 -> {
                            Intent intent = new Intent(context, MatchProjectBackActivity.class);
                            intent.putExtra(context.getString(R.string.DATA), projectInfo.qid);
                            context.startActivity(intent);
                        });
                    } else {
                        //关注
                        ViewHelper.showNoticePopupWindowAuto(context, holder.ivMore, 0, v12 -> notice(bean, projectInfo.qid), v114 -> {
                            Intent intent = new Intent(context, MatchProjectBackActivity.class);
                            intent.putExtra(context.getString(R.string.DATA), projectInfo.qid);
                            context.startActivity(intent);
                        });
                    }
                    break;
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
                intent.putExtra(context.getString(R.string.PROID), proId);
                if (pageType == 1) {
                    intent.putExtra(context.getString(R.string.TYPE), 1);
                    ((MyProjectActivity) context).startActivityForResult(intent, 10012);
                } else {
                    context.startActivity(intent);
                }
            }
        });
    }

    private void unNoticeAndDelete(MatchCompanyBean bean, String qid) {
        HttpHelper.notNoticeProject(qid).subscribe(booleanResponse -> {
            if (booleanResponse.data) {
                Toast.makeText(context, "已取消关注", Toast.LENGTH_SHORT).show();
                bean.attention = false;
                list.remove(bean);
                notifyDataSetChanged();
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

    private void delete(MatchCompanyBean bean, String qid) {
        HttpHelper.deleteProject(qid).subscribe(booleanResponse -> {
            if (booleanResponse.data) {
                Toast.makeText(context, "已删除项目", Toast.LENGTH_SHORT).show();
                list.remove(bean);
                notifyDataSetChanged();
            } else {
                Toast.makeText(context, "删除项目失败，请稍后再试", Toast.LENGTH_SHORT).show();
            }
        }, throwable -> {
            if (throwable instanceof RequestErrorThrowable) {
                RequestErrorThrowable requestErrorThrowable = (RequestErrorThrowable) throwable;
                Toast.makeText(context, requestErrorThrowable.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
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

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ResultHolder extends RecyclerView.ViewHolder {

        private ImageView ivMore;
        private TextView tvPlace, tvTitle, tvCome, tvType, tvIndustry, tvPrice;
        private LinearLayout llMore;

        public ResultHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.item_result2_title);
            tvPlace = (TextView) itemView.findViewById(R.id.item_result2_place);
            tvIndustry = (TextView) itemView.findViewById(R.id.item_result2_industry);
            tvPrice = (TextView) itemView.findViewById(R.id.item_result2_price);
            llMore = (LinearLayout) itemView.findViewById(R.id.item_result2_more);
            tvCome = (TextView) itemView.findViewById(R.id.item_result2_come);
            tvType = (TextView) itemView.findViewById(R.id.item_result2_type);
            ivMore = (ImageView) itemView.findViewById(R.id.item_result2_iv_more);
        }
    }
}