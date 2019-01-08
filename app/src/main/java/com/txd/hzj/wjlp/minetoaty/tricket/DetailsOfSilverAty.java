package com.txd.hzj.wjlp.minetoaty.tricket;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ants.theantsgo.config.Config;
import com.ants.theantsgo.httpTools.ApiTool2;
import com.ants.theantsgo.tool.ToolKit;
import com.ants.theantsgo.util.JSONUtils;
import com.bumptech.glide.Glide;
import com.github.nuptboyzhb.lib.SuperSwipeRefreshLayout;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.bean.UserCoinLogInfoOnDate;
import com.txd.hzj.wjlp.minetoaty.adapter.StickyExampleAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 创建者：zhangyunfei
 * 创建时间：2019/1/7 14:21
 * 功能描述：银两明细
 */
public class DetailsOfSilverAty  extends BaseAty {
    @ViewInject(R.id.titlt_conter_tv)
    public TextView titlt_conter_tv;

    /**
     * 明细列表
     */
    @ViewInject(R.id.tricket_rv)
    private RecyclerView tricket_rv;

    @ViewInject(R.id.tv_sticky_header_view)
    private TextView tv_sticky_header_view;

    private List<UserCoinLogInfoOnDate> list;

    private DetailsOfSilverAdapter mDetailsOfSilverAdapter;

    @ViewInject(R.id.part_swipe_refresh)
    private SuperSwipeRefreshLayout swipe_refresh;
    private int p = 1;

    private ProgressBar progressBar;
    private TextView textView;
    private ImageView imageView;

    // Footer View
    private ProgressBar footerProgressBar;
    private TextView footerTextView;
    private ImageView footerImageView;

    /**
     * 是不是第一次进入
     */
    private boolean frist = true;

    /**
     * 空视图
     */
    @ViewInject(R.id.no_data_layout)
    private LinearLayout no_data_layout;

    /**
     * 列表视图
     */
    @ViewInject(R.id.lv_layout)
    private FrameLayout lv_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("银两明细");

        tricket_rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));


        tricket_rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                View stickyInfoView = recyclerView.findChildViewUnder(
                        tv_sticky_header_view.getMeasuredWidth() / 2, 5);

                if (stickyInfoView != null && stickyInfoView.getContentDescription() != null) {
                    tv_sticky_header_view.setText(String.valueOf(stickyInfoView.getContentDescription()));
                }

                View transInfoView = recyclerView.findChildViewUnder(
                        tv_sticky_header_view.getMeasuredWidth() / 2, tv_sticky_header_view.getMeasuredHeight() + 1);

                if (transInfoView != null && transInfoView.getTag() != null) {

                    int transViewStatus = (int) transInfoView.getTag();
                    int dealtY = transInfoView.getTop() - tv_sticky_header_view.getMeasuredHeight();

                    if (transViewStatus == StickyExampleAdapter.HAS_STICKY_VIEW) {
                        if (transInfoView.getTop() > 0) {
                            tv_sticky_header_view.setTranslationY(dealtY);
                        } else {
                            tv_sticky_header_view.setTranslationY(0);
                        }
                    } else if (transViewStatus == StickyExampleAdapter.NONE_STICKY_VIEW) {
                        tv_sticky_header_view.setTranslationY(0);
                    }
                }

            }
        });
        findViewById(R.id.title_be_back_iv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DetailsOfSilverAty.this.finish();
            }
        });
        swipe_refresh.setHeaderViewBackgroundColor(Color.WHITE);
        swipe_refresh.setHeaderView(createHeaderView());// add headerView
        swipe_refresh.setFooterView(createFooterView());
        swipe_refresh.setTargetScrollWithLayout(true);

        swipe_refresh
                .setOnPullRefreshListener(new SuperSwipeRefreshLayout.OnPullRefreshListener() {

                    @Override
                    public void onRefresh() {
                        frist = false;
                        textView.setText("正在刷新");
                        imageView.setVisibility(View.GONE);
                        progressBar.setVisibility(View.VISIBLE);
                        p = 1;
                        getData();
                    }

                    @Override
                    public void onPullDistance(int distance) {
                    }

                    @Override
                    public void onPullEnable(boolean enable) {
                        textView.setText(enable ? "松开刷新" : "下拉刷新");
                        imageView.setVisibility(View.VISIBLE);
                        imageView.setRotation(enable ? 180 : 0);
                    }
                });

        swipe_refresh
                .setOnPushLoadMoreListener(new SuperSwipeRefreshLayout.OnPushLoadMoreListener() {

                    @Override
                    public void onLoadMore() {
                        frist = false;
                        footerTextView.setText("正在加载...");
                        footerImageView.setVisibility(View.GONE);
                        footerProgressBar.setVisibility(View.VISIBLE);

                        p++;
                        getData();
                    }

                    @Override
                    public void onPushEnable(boolean enable) {
                        footerTextView.setText(enable ? "松开加载" : "上拉加载");
                        footerImageView.setVisibility(View.VISIBLE);
                        footerImageView.setRotation(enable ? 0 : 180);
                    }

                    @Override
                    public void onPushDistance(int distance) {

                    }

                });

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_particulars_used_by_tricket;
    }

    @Override
    protected void initialized() {
        list = new ArrayList<>();
    }

    @Override
    protected void requestData() {
        getData();
    }

    private void getData() {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("p", String.valueOf(p));
        apiTool2.postApi(Config.SHARE_URL + "index.php/Api/User/getUserCoinLogInfoOnDate", params, this);
    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        Map<String, String> map = JSONUtils.parseKeyAndValueToMap(jsonStr);
        Map<String, String> data = JSONUtils.parseKeyAndValueToMap(map.get("data"));
        if (requestUrl.contains("getUserCoinLogInfoOnDate")) { // 银两明细
            if (1 == p) {
                if (ToolKit.isList(data, "info")) {
                    list.clear();
                    ArrayList<Map<String, String>> info = JSONUtils.parseKeyAndValueToMapList(data.get("info"));
                    for (Map<String, String> temp : info) {
                        String time = temp.get("time");// 悬浮部分
                        ArrayList<Map<String, String>> list_temp = JSONUtils.parseKeyAndValueToMapList(temp.get("list"));
                        for (Map<String, String> temp2 : list_temp) {
                            UserCoinLogInfoOnDate userCoinLogInfoOnDate = new UserCoinLogInfoOnDate(time,temp2.get("id"),temp2.get("user_id"),temp2.get("type"),temp2.get("money"),temp2.get("id_val"),temp2.get("sub_type"),temp2.get("from_type"),temp2.get("desc"),temp2.get("create_time"),temp2.get("img"),temp2.get("sub_type_desc"));
                            list.add(userCoinLogInfoOnDate);
                        }
                    }
                    mDetailsOfSilverAdapter = new DetailsOfSilverAdapter(this, list);
                    tricket_rv.setAdapter(mDetailsOfSilverAdapter);

                    lv_layout.setVisibility(View.VISIBLE);
                    no_data_layout.setVisibility(View.GONE);
                } else {
                    lv_layout.setVisibility(View.GONE);
                    no_data_layout.setVisibility(View.VISIBLE);
                }
                if (!frist) {
                    swipe_refresh.setRefreshing(false);
                    progressBar.setVisibility(View.GONE);
                }
            } else {
                if (ToolKit.isList(data, "info")) {
                    ArrayList<Map<String, String>> info = JSONUtils.parseKeyAndValueToMapList(data.get("info"));
                    for (Map<String, String> temp : info) {
                        String time = temp.get("time");// 悬浮部分
                        ArrayList<Map<String, String>> list_temp = JSONUtils.parseKeyAndValueToMapList(temp.get("list"));
                        for (Map<String, String> temp2 : list_temp) {
                            UserCoinLogInfoOnDate userCoinLogInfoOnDate = new UserCoinLogInfoOnDate(time,temp2.get("id"),temp2.get("user_id"),temp2.get("type"),temp2.get("money"),temp2.get("id_val"),temp2.get("sub_type"),temp2.get("from_type"),temp2.get("desc"),temp2.get("create_time"),temp2.get("img"),temp2.get("sub_type_desc"));
                            list.add(userCoinLogInfoOnDate);
                        }
                    }
                    mDetailsOfSilverAdapter.notifyDataSetChanged();
                }

                footerImageView.setVisibility(View.VISIBLE);
                footerProgressBar.setVisibility(View.GONE);
                swipe_refresh.setLoadMore(false);
            }
        }
    }


    @Override
    public void onError(String requestUrl, Map<String, String> error) {
        super.onError(requestUrl, error);
        if (1 == p) {
            lv_layout.setVisibility(View.GONE);
            no_data_layout.setVisibility(View.VISIBLE);
            if (!frist) {
                swipe_refresh.setRefreshing(false);
                progressBar.setVisibility(View.GONE);
            }
        } else {
            footerImageView.setVisibility(View.VISIBLE);
            footerProgressBar.setVisibility(View.GONE);
            swipe_refresh.setLoadMore(false);
        }
    }

    private View createFooterView() {
        View footerView = LayoutInflater.from(swipe_refresh.getContext())
                .inflate(R.layout.layout_footer, null);
        footerProgressBar = footerView.findViewById(R.id.footer_pb_view);
        footerImageView = footerView.findViewById(R.id.footer_image_view);
        footerTextView = footerView.findViewById(R.id.footer_text_view);
        footerProgressBar.setVisibility(View.GONE);
        footerImageView.setVisibility(View.VISIBLE);
        footerImageView.setImageResource(R.drawable.down_arrow);
        footerTextView.setText("上拉加载更多...");
        return footerView;
    }

    private View createHeaderView() {
        View headerView = LayoutInflater.from(swipe_refresh.getContext())
                .inflate(R.layout.layout_head, null);
        progressBar = headerView.findViewById(R.id.pb_view);
        textView = headerView.findViewById(R.id.text_view);
        textView.setText("下拉刷新");
        imageView = headerView.findViewById(R.id.image_view);
        imageView.setVisibility(View.VISIBLE);
        imageView.setImageResource(R.drawable.down_arrow);
        progressBar.setVisibility(View.GONE);
        return headerView;
    }


    public static class DetailsOfSilverAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        public static final int FIRST_STICKY_VIEW = 1;
        public static final int HAS_STICKY_VIEW = 2;
        public static final int NONE_STICKY_VIEW = 3;

        private Context context;
        private List<UserCoinLogInfoOnDate> stickyExampleModels;

        public DetailsOfSilverAdapter(Context context, List<UserCoinLogInfoOnDate> recyclerViewModels) {
            this.context = context;
            this.stickyExampleModels = recyclerViewModels;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_tricket_rv, parent, false);
            RecyclerViewHolder recyclerViewHolder = new RecyclerViewHolder(view);
            ViewUtils.inject(recyclerViewHolder, view);
            return recyclerViewHolder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
            if (viewHolder instanceof RecyclerViewHolder) {
                // 获取ViewHolder
                RecyclerViewHolder recyclerViewHolder = (RecyclerViewHolder) viewHolder;
                // 获取明细实体类
                final UserCoinLogInfoOnDate stickyExampleModel = stickyExampleModels.get(position);
                // 费吸顶文本标题
                recyclerViewHolder.tvName.setText(stickyExampleModel.getDesc());
                // 交易记录时间
                recyclerViewHolder.tvGender.setText(stickyExampleModel.getCreate_time());
                if (position == 0) {// 第一条
                    // 显示吸顶文本
                    // 显示吸顶文本
                    recyclerViewHolder.tvStickyHeader.setVisibility(View.VISIBLE);
                    // 设置吸顶文本内容
                    recyclerViewHolder.tvStickyHeader.setText(stickyExampleModel.getTitle());
                    // 给itemView设置Tag
                    recyclerViewHolder.itemView.setTag(FIRST_STICKY_VIEW);
                } else {
                    if (!TextUtils.equals(stickyExampleModel.getTitle(), stickyExampleModels.get(position - 1).getTitle())) {
                        recyclerViewHolder.tvStickyHeader.setVisibility(View.VISIBLE);
                        recyclerViewHolder.tvStickyHeader.setText(stickyExampleModel.getTitle());
                        recyclerViewHolder.itemView.setTag(HAS_STICKY_VIEW);
                    } else {
                        recyclerViewHolder.tvStickyHeader.setVisibility(View.GONE);
                        recyclerViewHolder.itemView.setTag(NONE_STICKY_VIEW);
                    }
                }
                // 描述
                recyclerViewHolder.itemView.setContentDescription(stickyExampleModel.getDesc());
                // 查看详情
                    recyclerViewHolder.check_details_for_balance_tv.setVisibility(View.GONE);
                // 查看详情点击事件
                recyclerViewHolder.check_details_for_balance_tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
                // 查看线下充值详情----隐藏
                recyclerViewHolder.t_details_price_tv.setText("+" + stickyExampleModel.getMoney());

                    // 获得，消费积分(转出，消费) add_sub：1加 2减
                    if (stickyExampleModel.getSub_type() != null && stickyExampleModel.getSub_type().equals("1")) {
                        recyclerViewHolder.t_details_price_tv.setText("+" + stickyExampleModel.getMoney());
                        //                    recyclerViewHolder.check_details_for_balance_tv.setVisibility(View.GONE);
                    } else if (stickyExampleModel.getSub_type() != null && stickyExampleModel.getSub_type().equals("2")) {
                        recyclerViewHolder.t_details_price_tv.setText("-" + stickyExampleModel.getMoney());
                    }
                    Glide.with(context).load(stickyExampleModel.getImg()).into(recyclerViewHolder.t_details_logo_tv);
            }
        }

        @Override
        public int getItemCount() {
            return stickyExampleModels == null ? 0 : stickyExampleModels.size();
        }


        private class RecyclerViewHolder extends RecyclerView.ViewHolder {
            @ViewInject(R.id.tv_sticky_header_view)
            TextView tvStickyHeader;
            @ViewInject(R.id.t_details_title_tv)
            TextView tvName;
            @ViewInject(R.id.t_details_time_tv)
            TextView tvGender;
            @ViewInject(R.id.check_details_for_balance_tv)
            TextView check_details_for_balance_tv;
            @ViewInject(R.id.t_details_logo_tv)
            ImageView t_details_logo_tv;
            @ViewInject(R.id.t_details_price_tv)
            TextView t_details_price_tv;

            RecyclerViewHolder(View itemView) {
                super(itemView);
            }
        }
    }
}
