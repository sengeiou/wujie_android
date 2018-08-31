package com.txd.hzj.wjlp.mellOffLine;

import android.graphics.Color;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ants.theantsgo.config.Settings;
import com.ants.theantsgo.view.inScroll.ListViewForScrollView;
import com.bumptech.glide.Glide;
import com.github.nuptboyzhb.lib.SuperSwipeRefreshLayout;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.bean.OffLineEvaluationListBean;
import com.txd.hzj.wjlp.http.OfflineStore;
import com.txd.hzj.wjlp.tool.MallRecyclerViewDivider;
import com.txd.hzj.wjlp.tool.UnitHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 创建者：zhangyunfei
 * 创建时间：2018/8/2 15:06
 * 功能描述：
 */
public class OffLineEvaluationShopListAty extends BaseAty {

    @ViewInject(R.id.titlt_conter_tv)
    public TextView titlt_conter_tv;

    @ViewInject(R.id.evaluate_num_tv)
    private TextView evaluate_num_tv;

    @ViewInject(R.id.goods_evaluste_lv)
    private ListViewForScrollView goods_evaluste_lv;

    /**
     * 滚动监听
     */
    @ViewInject(R.id.goods_comment_sc)
    private NestedScrollView goods_comment_sc;

    /**
     * 回到顶部
     */
    @ViewInject(R.id.gc_be_back_top_iv)
    private ImageView gc_be_back_top_iv;


    // Header View
    private ProgressBar progressBar;
    private TextView textView;
    private ImageView imageView;

    // Footer View
    private ProgressBar footerProgressBar;
    private TextView footerTextView;
    private ImageView footerImageView;

    @ViewInject(R.id.refresh_view)
    private SuperSwipeRefreshLayout refresh_view;


    @ViewInject(R.id.no_data_layout)
    private LinearLayout no_data_layout;
    //店铺ID
    private String mMerchant_id;
    private int p = 1;

    private List<OffLineEvaluationListBean> mListBeans = new ArrayList<>();
    private EvaluationAdapter mEvaluationAdapter;

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_evaluation_shop;
    }

    @Override
    protected void initialized() {
        titlt_conter_tv.setText("店铺评价");
        mMerchant_id = getIntent().getStringExtra("merchant_id");
        if (TextUtils.isEmpty(mMerchant_id)) {
            showToast("店铺ID不能为空");
            return;
        }


        // 滚动监听
        goods_comment_sc.setOnScrollChangeListener(new Scrollistener());

        goods_evaluste_lv.setFocusable(false);
        goods_evaluste_lv.setEmptyView(no_data_layout);

        refresh_view.setHeaderViewBackgroundColor(Color.WHITE);
        refresh_view.setHeaderView(createHeaderView());// add headerView
        refresh_view.setFooterView(createFooterView());
        refresh_view.setTargetScrollWithLayout(true);
        refresh_view.setOnPullRefreshListener(new SuperSwipeRefreshLayout.OnPullRefreshListener() {
            @Override
            public void onRefresh() {
                textView.setText("正在刷新");
                imageView.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                p = 1;
                mListBeans.clear();
                OfflineStore.commentList(mMerchant_id, String.valueOf(p), OffLineEvaluationShopListAty.this);

            }

            @Override
            public void onPullDistance(int i) {

            }

            @Override
            public void onPullEnable(boolean enable) {
                textView.setText(enable ? "松开刷新" : "下拉刷新");
                imageView.setVisibility(View.VISIBLE);
                imageView.setRotation(enable ? 180 : 0);
            }
        });
        refresh_view.setOnPushLoadMoreListener(new SuperSwipeRefreshLayout.OnPushLoadMoreListener() {
            @Override
            public void onLoadMore() {
                // 加载操作
                footerTextView.setText("正在加载...");
                footerImageView.setVisibility(View.GONE);
                footerProgressBar.setVisibility(View.VISIBLE);
                p++;
                OfflineStore.commentList(mMerchant_id, String.valueOf(p), OffLineEvaluationShopListAty.this);
            }

            @Override
            public void onPushDistance(int i) {

            }

            @Override
            public void onPushEnable(boolean enable) {
                footerTextView.setText(enable ? "松开加载" : "上拉加载");
                footerImageView.setVisibility(View.VISIBLE);
                footerImageView.setRotation(enable ? 0 : 180);
            }
        });

        gc_be_back_top_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 滚动到顶部
                goods_comment_sc.smoothScrollTo(0,0);
            }
        });
    }

    @Override
    protected void requestData() {
        OfflineStore.commentList(mMerchant_id, String.valueOf(p), this);
    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        refresh();
        if (requestUrl.contains("commentList")) {
            JSONObject ject = JSON.parseObject(jsonStr);
            if (ject.containsKey("data")) {
                JSONObject jsonObject = JSON.parseObject(ject.getString("data"));
                if (jsonObject.containsKey("count")) {
                    evaluate_num_tv.setText(jsonObject.getString("count"));
                }
                if (jsonObject.containsKey("list")) {
                    JSONArray array = JSONArray.parseArray(jsonObject.getString("list"));
                    for (int i = 0; i < array.size(); i++) {
                        JSONObject object = (JSONObject) array.get(i);
                        OffLineEvaluationListBean bean = new OffLineEvaluationListBean();
                        bean.setC_id(object.containsKey("c_id") ? object.getString("c_id") : "");
                        bean.setHead_pic(object.containsKey("head_pic") ? object.getString("head_pic") : "");
                        bean.setContent(object.containsKey("content") ? object.getString("content") : "");
                        bean.setStar(object.containsKey("star") ? object.getString("star") : "");
                        bean.setNickname(object.containsKey("nickname") ? object.getString("nickname") : "");
                        bean.setStart_time(object.containsKey("start_time") ? object.getString("start_time") : "");
                        if (object.containsKey("picture")) {
                            JSONArray picture = JSONArray.parseArray(object.getString("picture"));
                            if (picture != null) {
                                List<OffLineEvaluationListBean.PictureBean> mPictureBeanList = new ArrayList<>();
                                if (picture.size() > 0) {
                                    for (int i1 = 0; i1 < picture.size(); i1++) {
                                        OffLineEvaluationListBean.PictureBean pictureBean = new OffLineEvaluationListBean.PictureBean();
                                        JSONObject pic = (JSONObject) picture.get(i1);
                                        if (pic.containsKey("path")) {
                                            pictureBean.setPath(pic.getString("path"));
                                            mPictureBeanList.add(pictureBean);
                                        }

                                    }
                                    bean.setPicture(mPictureBeanList);
                                }else {
                                    bean.setPicture(mPictureBeanList);
                                }
                            }

                        }
                        mListBeans.add(bean);
                    }
                    mEvaluationAdapter = new EvaluationAdapter(mListBeans);
                    goods_evaluste_lv.setAdapter(mEvaluationAdapter);

                }
            }
        }

    }

    @Override
    public void onError(String requestUrl, Map<String, String> error) {
        super.onError(requestUrl, error);
        refresh();
    }

    private void refresh() {
        if (1 == p) {
            progressBar.setVisibility(View.GONE);
            refresh_view.setRefreshing(false); // 刷新成功
        } else {
            footerImageView.setVisibility(View.VISIBLE);
            footerProgressBar.setVisibility(View.GONE);
            refresh_view.setLoadMore(false); // 刷新成功
        }
    }

    private View createFooterView() {
        View footerView = LayoutInflater.from(refresh_view.getContext()).inflate(R.layout.layout_footer, null);
        footerProgressBar = footerView
                .findViewById(R.id.footer_pb_view);
        footerImageView = footerView
                .findViewById(R.id.footer_image_view);
        footerTextView = footerView
                .findViewById(R.id.footer_text_view);
        footerProgressBar.setVisibility(View.GONE);
        footerImageView.setVisibility(View.VISIBLE);
        footerImageView.setImageResource(R.drawable.down_arrow);
        footerTextView.setText("上拉加载更多...");
        return footerView;
    }

    private View createHeaderView() {
        View headerView = LayoutInflater.from(refresh_view.getContext()).inflate(R.layout.layout_head, null);
        progressBar = headerView.findViewById(R.id.pb_view);
        textView = headerView.findViewById(R.id.text_view);
        textView.setText("下拉刷新");
        imageView = headerView.findViewById(R.id.image_view);
        imageView.setVisibility(View.VISIBLE);
        imageView.setImageResource(R.drawable.down_arrow);
        progressBar.setVisibility(View.GONE);
        return headerView;
    }


    class Scrollistener implements NestedScrollView.OnScrollChangeListener {
        @Override
        public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
            if (scrollY < Settings.displayWidth) {
                gc_be_back_top_iv.setVisibility(View.GONE);
            } else {
                gc_be_back_top_iv.setVisibility(View.VISIBLE);
            }
        }
    }

    class EvaluationAdapter extends BaseAdapter {
        List<OffLineEvaluationListBean> mList;

        public EvaluationAdapter(List<OffLineEvaluationListBean> list) {
            mList = list;
        }

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public Object getItem(int position) {
            return mList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View view, ViewGroup parent) {
            ViewHolder viewHolder;
            if (view == null) {
                viewHolder = new ViewHolder();
                view = LayoutInflater.from(OffLineEvaluationShopListAty.this).inflate(R.layout.item_offline_evaluation, parent, false);
                viewHolder.head_img = view.findViewById(R.id.head_img);
                viewHolder.nickname_tv = view.findViewById(R.id.nickname_tv);
                viewHolder.time_tv = view.findViewById(R.id.time_tv);
                viewHolder.ratingBar = view.findViewById(R.id.rb_1);
                viewHolder.content_tv = view.findViewById(R.id.content_tv);
                viewHolder.recyclerView = view.findViewById(R.id.recyclerView);
                view.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }
            OffLineEvaluationListBean bean = mList.get(position);
            Glide.with(OffLineEvaluationShopListAty.this).load(bean.getHead_pic()).into(viewHolder.head_img);
            viewHolder.nickname_tv.setText(bean.getNickname());
            viewHolder.time_tv.setText(bean.getStart_time());
            viewHolder.ratingBar.setIsIndicator(true);
            viewHolder.ratingBar.setRating(Float.valueOf(bean.getStar()));
            viewHolder.content_tv.setVisibility(TextUtils.isEmpty(bean.getContent())?View.GONE:View.VISIBLE);
            viewHolder.content_tv.setText(bean.getContent());
            // 设置布局方式
            viewHolder.recyclerView.setLayoutManager(new LinearLayoutManager(OffLineEvaluationShopListAty.this, LinearLayoutManager.HORIZONTAL, false));
            // 默认分割线
            int dip = UnitHelper.dip2px(getApplicationContext(), 10);//转换dip的工具类
            viewHolder.recyclerView.addItemDecoration(new MallRecyclerViewDivider(getApplicationContext(), MallRecyclerViewDivider.HORIZONTAL_LIST, 0, dip));//自定义分割线使得纵向分割不至于撑开布局
            if (mList.get(position).getPicture() != null) {
                viewHolder.recyclerView.setAdapter(new PicAdapter(mList.get(position).getPicture()));
            }
            return view;
        }

        class ViewHolder {
            ImageView head_img;
            TextView nickname_tv;
            TextView time_tv;
            RatingBar ratingBar;
            TextView content_tv;
            RecyclerView recyclerView;
        }
    }

    class PicAdapter extends RecyclerView.Adapter<PicAdapter.PicHolder> {

        List<OffLineEvaluationListBean.PictureBean> mPictureBeans;
        int itemWidth;


        public PicAdapter(List<OffLineEvaluationListBean.PictureBean> pictureBeans) {
            mPictureBeans = pictureBeans;
            int dipbian = UnitHelper.dip2px(getApplicationContext(), 4);//转换之前是4转换成dip之后乘以2倍，外层布局8dip所以这里写4
            DisplayMetrics metrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(metrics);
            int dip = UnitHelper.dip2px(getApplicationContext(), 10);//转换dip的工具类
            itemWidth = (metrics.widthPixels - dipbian * 2) / 5 - dip;//屏幕宽度减去两边的边距8dip 然后除以4 减去行间距就是单条的宽度
        }

        @Override
        public PicHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(OffLineEvaluationShopListAty.this).inflate(R.layout.item_shop_pic, parent, false);
            return new PicHolder(view);
        }

        @Override
        public void onBindViewHolder(final PicHolder holder, int position) {
            Glide.with(OffLineEvaluationShopListAty.this)
                    .load(mPictureBeans.get(position).getPath())
                    .asBitmap()
                    .error(R.drawable.ic_default)
                    .placeholder(R.drawable.ic_default)
                    .centerCrop()
                    .override(itemWidth,itemWidth)
                    .into(holder.imageView);
        }

        @Override
        public int getItemCount() {
            return mPictureBeans.size() > 0 ? mPictureBeans.size() : 0;
        }

        class PicHolder extends RecyclerView.ViewHolder {
            ImageView imageView;

            public PicHolder(View itemView) {
                super(itemView);
                imageView = itemView.findViewById(R.id.shop_pic);
            }
        }
    }

}
