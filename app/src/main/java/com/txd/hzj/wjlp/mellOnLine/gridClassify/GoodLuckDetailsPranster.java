package com.txd.hzj.wjlp.mellOnLine.gridClassify;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.ants.theantsgo.config.Settings;
import com.ants.theantsgo.listenerForAdapter.AdapterTextViewClickListener;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.bean.commodity.GroupBean;
import com.txd.hzj.wjlp.mellOnLine.adapter.GoodLuckAdapter;
import com.txd.hzj.wjlp.mellOnLine.gridClassify.CommodityDetailsInter.GoodLuckPranster;
import com.txd.hzj.wjlp.tool.CommonPopupWindow;

import java.util.List;

/**
 * 创建者：TJDragon(LiuGang)
 * 创建时间：2018/5/20 11:33
 * 功能描述：
 * 联系方式：常用邮箱或电话
 */
public class GoodLuckDetailsPranster extends CommodityDetailsPranster implements GoodLuckPranster {
    CommodityDetailsInter.GoodLuckView goodLuckView;
    CommonPopupWindow commonPopupWindow;

    public GoodLuckDetailsPranster(CommodityDetailsInter.CommodityView view) {
        super(view);
    }

    private class ExpAdapter extends RecyclerView.Adapter {
        private List<String> strList;
        private Context context;
        private LayoutInflater inflater;
        private ExpHolder holder;

        ExpAdapter(List<String> strList, Context context) {
            this.strList = strList;
            this.context = context;
            inflater = LayoutInflater.from(context);

        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = inflater.inflate(R.layout.item_exper, null);
            holder = new ExpHolder(itemView);
            return holder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ExpHolder expHolder = (ExpHolder) holder;
            expHolder.item_expTv.setText(strList.get(position));
        }

        @Override
        public int getItemCount() {
            return null != strList ? strList.size() : 0;
        }

        private class ExpHolder extends RecyclerView.ViewHolder {
            private TextView item_expTv;

            public ExpHolder(View itemView) {
                super(itemView);
                item_expTv = itemView.findViewById(R.id.item_expTv);
            }
        }
    }

    @Override
    public void showExperiencePopWindow(Context context, View view, List<String> strList) {
        View contentView = LayoutInflater.from(context).inflate(R.layout.layout_comdetail_exper, null);
        if (null != strList && strList.size() > 0) {
            RecyclerView recyclerView = contentView.findViewById(R.id.experRecyclerView);
            recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
            recyclerView.setAdapter(new ExpAdapter(strList, context));
//            textView.setText(stringBuffer);
        }
        if (commonPopupWindow != null && commonPopupWindow.isShowing()) return;
        commonPopupWindow = new CommonPopupWindow.Builder(context)
                .setView(contentView)
                .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                .setBackGroundLevel(0.7f)
                .setViewOnclickListener(new CommonPopupWindow.ViewInterface() {
                    @Override
                    public void getChildView(View view, int layoutResId, int position) {
                        TextView cancel = (TextView) view.findViewById(R.id.cancel);
                        cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                commonPopupWindow.dismiss();
                            }
                        });

                    }
                }, 0)
                .setAnimationStyle(R.style.animbottom)
                .create();
        commonPopupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
        contentView.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commonPopupWindow.dismiss();
            }
        });
    }

    @Override
    public void setGoodLuckView(CommodityDetailsInter.GoodLuckView goodLuckView) {
        this.goodLuckView = goodLuckView;
    }


    public void showCollagePop(View view, final String title, final List<GroupBean> list, final String type, final Activity activity, final String group_count) {
        if (commonPopupWindow != null && commonPopupWindow.isShowing()) return;
        commonPopupWindow = new CommonPopupWindow.Builder(activity)
                .setView(R.layout.collagepop_layout)
                .setWidthAndHeight(Settings.displayWidth * 370 / 414, Settings.displayHeight / 2)
                .setBackGroundLevel(0.7f)
                .setViewOnclickListener(new CommonPopupWindow.ViewInterface() {
                    @Override
                    public void getChildView(View view, int layoutResId, final int position) {
                        Integer integer = Integer.valueOf(group_count);
                        TextView tenCollage = view.findViewById(R.id.tenCollage);
                        tenCollage.setVisibility(integer > 10 ? View.VISIBLE : View.GONE);
                        ListView listView = view.findViewById(R.id.collageListView);
                        GoodLuckAdapter goodLuckAdapter = new GoodLuckAdapter(activity, list, type);
                        listView.setAdapter(goodLuckAdapter);
                        goodLuckAdapter.setAdapterTextViewClickListener(new AdapterTextViewClickListener() {
                            @Override
                            public void onTextViewClick(View v, int position) {
                                goodLuckView.itmeClick(v, position);
                                commonPopupWindow.dismiss();
                            }
                        });
                        TextView tv_title = view.findViewById(R.id.popp_title);
                        tv_title.setText(title);

                    }
                }, 0)
                .setAnimationStyle(R.style.animbottom)
                .create();
        commonPopupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
    }
}
