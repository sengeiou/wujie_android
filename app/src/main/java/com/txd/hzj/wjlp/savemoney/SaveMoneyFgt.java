package com.txd.hzj.wjlp.savemoney;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.StrikethroughSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ants.theantsgo.base.BaseView;
import com.ants.theantsgo.config.Config;
import com.ants.theantsgo.httpTools.ApiTool2;
import com.ants.theantsgo.util.JSONUtils;
import com.bumptech.glide.Glide;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseFgt;
import com.txd.hzj.wjlp.view.SuperSwipeRefreshLayout;
import com.txd.hzj.wjlp.webviewH5.WebViewAty;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 创建者：zhangyunfei
 * 创建时间：2019/3/18 10:27
 * 功能描述：
 */
public class SaveMoneyFgt extends BaseFgt {
    private static final String ARG_PARAM1 = "param1";
    private String mTitle;
    private String mType;
    private String mSortType;
    private String mQ;


    private String redColor = "#ffe71f19";
    private String blgColor = "#ff333333";

    //积分
    @ViewInject(R.id.internal_tv)
    private TextView internal_tv;
    //代金券
    @ViewInject(R.id.cash_coupon_tv)
    private TextView cash_coupon_tv;
    //销量
    @ViewInject(R.id.sales_volume_tv)
    private TextView sales_volume_tv;
    //价格
    @ViewInject(R.id.price_tv)
    private TextView price_tv;

    @ViewInject(R.id.priceLayout)
    private LinearLayout priceLayout;

    private Drawable selectId;
    private Drawable twoSelectId;
    private Drawable unSelectId;

    //    private int internalNum = 1;
    //    private int cashCouponNum = 0;
    //    private int salesVolumeNum = 0;
    private int priceNum = 0;

    @ViewInject(R.id.superSwipeRefreshLayout)
    private SuperSwipeRefreshLayout superSwipeRefreshLayout;

    private int p = 1; // 请求的分页
    // Header View
    private ProgressBar progressBar;
    private TextView textView;
    private ImageView imageView;

    // Footer View
    private ProgressBar footerProgressBar;
    private TextView footerTextView;
    private ImageView footerImageView;

    @ViewInject(R.id.recyclerView)
    private RecyclerView mRecyclerView;
    private SaveMoneyAdapter mAdapter;

    private ArrayList<Map<String, String>> mList = new ArrayList<>();
    private ProgressDialog mProgressDialog;


    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_save_money;
    }

    public static SaveMoneyFgt newInstance(String title) {
        SaveMoneyFgt fragment = new SaveMoneyFgt();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initialized() {
        selectId = getResources().getDrawable(R.drawable.shopjiantou);
        twoSelectId = getResources().getDrawable(R.drawable.shop_red_down);
        unSelectId = getResources().getDrawable(R.drawable.shopblgjiantou);
        selectId.setBounds(0, 0, selectId.getMinimumWidth(), selectId.getMinimumHeight());
        twoSelectId.setBounds(0, 0, selectId.getMinimumWidth(), selectId.getMinimumHeight());
        unSelectId.setBounds(0, 0, unSelectId.getMinimumWidth(), unSelectId.getMinimumHeight());

        mProgressDialog = new ProgressDialog(getActivity(), R.style.loading_dialog);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setCanceledOnTouchOutside(false);
    }


    public void setRefresh(){
        mProgressDialog.show();
        mProgressDialog.setContentView(com.ants.theantsgo.R.layout.loading_dialog);
    }

    public void stopRefresh(){
        if (mProgressDialog != null && mProgressDialog.isShowing()){
            mProgressDialog.dismiss();
        }
    }



    @Override
    protected void requestData() {
        superSwipeRefreshLayout.setHeaderView(createHeaderView());// add headerView
        superSwipeRefreshLayout.setFooterView(createFooterView());
        superSwipeRefreshLayout.setHeaderViewBackgroundColor(Color.WHITE);
        superSwipeRefreshLayout.setTargetScrollWithLayout(true);
        superSwipeRefreshLayout.setOnPullRefreshListener(new SuperSwipeRefreshLayout.OnPullRefreshListener() {
            @Override
            public void onRefresh() {
                textView.setText("正在刷新");
                imageView.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                p = 1;
                getShengqiangou(SaveMoneyFgt.this);
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
        superSwipeRefreshLayout.setOnPushLoadMoreListener(new SuperSwipeRefreshLayout.OnPushLoadMoreListener() {
            @Override
            public void onLoadMore() {
                footerTextView.setText("正在加载...");
                footerImageView.setVisibility(View.GONE);
                footerProgressBar.setVisibility(View.VISIBLE);
                p++;
                getShengqiangou(SaveMoneyFgt.this);
            }

            @Override
            public void onPushDistance(int distance) {
            }

            @Override
            public void onPushEnable(boolean enable) {
                footerTextView.setText(enable ? "松开加载" : "上拉加载");
                footerImageView.setVisibility(View.VISIBLE);
                footerImageView.setRotation(enable ? 0 : 180);
            }
        });
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                if (parent.getChildLayoutPosition(view) % 2 == 0) {
                    outRect.left = 0;
                } else {
                    outRect.left = 10;
                }
                outRect.bottom = 10;

            }
        });
        if (getArguments() != null) {
            mTitle = getArguments().getString(ARG_PARAM1);
            if (mTitle.equals("淘宝")) {
                mType = "1";
                mSortType = "999";
                priceLayout.setVisibility(View.GONE);
                internal_tv.setText("综合");
                cash_coupon_tv.setText("优惠");

            } else if (mTitle.equals("拼多多")) {
                mType = "3";
                mSortType = "14";
                priceLayout.setVisibility(View.VISIBLE);
                internal_tv.setText("优惠");
                cash_coupon_tv.setText("用券");
            }
        }
        clearChioce();
        internal_tv.setTextColor(Color.parseColor(redColor));
    }

    @Override
    @OnClick({R.id.internal_tv, R.id.cash_coupon_tv, R.id.sales_volume_tv, R.id.price_tv})
    public void onClick(View v) {
        super.onClick(v);
        int id = v.getId();
        if (id == R.id.internal_tv) {
            setChioceItem(0);
        } else if (id == R.id.cash_coupon_tv) {
            setChioceItem(1);
        } else if (id == R.id.sales_volume_tv) {
            setChioceItem(2);
        } else if (id == R.id.price_tv) {
            setChioceItem(3);
        }
    }


    public void getSearchLabel(String label){
        mQ = label;
        p = 1;
        getShengqiangou(this);
    }

    private void setChioceItem(int index) {
        clearChioce();
        p = 1;
        if (index == 0) {
            internal_tv.setTextColor(Color.parseColor(redColor));
            //            internal_tv.setCompoundDrawables(null, null, internalNum % 2 == 0 ? selectId : twoSelectId, null);
            //            internalNum++;
            //            cashCouponNum = 0;
            //            salesVolumeNum = 0;
            //            priceNum = 0;
            if (mTitle.equals("淘宝")) {
                mSortType = "999";
            } else if (mTitle.equals("拼多多")) {
                mSortType = "14";
            }
        } else if (index == 1) {
            cash_coupon_tv.setTextColor(Color.parseColor(redColor));
            //            cash_coupon_tv.setCompoundDrawables(null, null, cashCouponNum % 2 == 0 ? selectId : twoSelectId, null);
            //
            //            internalNum = 0;
            //            cashCouponNum++;
            //            salesVolumeNum = 0;
            //            priceNum = 0;
            if (mTitle.equals("淘宝")) {
                mSortType = "2";
            } else if (mTitle.equals("拼多多")) {
                mSortType = "9";
            }
        } else if (index == 2) {
            sales_volume_tv.setTextColor(Color.parseColor(redColor));
            //            sales_volume_tv.setCompoundDrawables(null, null, salesVolumeNum % 2 == 0 ? selectId : twoSelectId, null);
            //            internalNum = 0;
            //            cashCouponNum = 0;
            //            salesVolumeNum++;
            //            priceNum = 0;
            if (mTitle.equals("淘宝")) {
                mSortType = "1";
            } else if (mTitle.equals("拼多多")) {
                mSortType = "6";
            }
        } else if (index == 3) {
            setRefresh();
            price_tv.setTextColor(Color.parseColor(redColor));
            price_tv.setCompoundDrawables(null, null, priceNum % 2 == 0 ? selectId : twoSelectId, null);
            //            internalNum = 0;
            //            cashCouponNum = 0;
            //            salesVolumeNum = 0;
            if (priceNum % 2 == 0){
                mSortType = "3";
            }else {
                mSortType = "4";
            }
            priceNum++;

        }
        getShengqiangou(this);
    }

    private void clearChioce() {
        internal_tv.setTextColor(Color.parseColor(blgColor));
        //        internal_tv.setCompoundDrawables(null, null, unSelectId, null);
        cash_coupon_tv.setTextColor(Color.parseColor(blgColor));
        //        cash_coupon_tv.setCompoundDrawables(null, null, unSelectId, null);
        sales_volume_tv.setTextColor(Color.parseColor(blgColor));
        //        sales_volume_tv.setCompoundDrawables(null, null, unSelectId, null);
        price_tv.setTextColor(Color.parseColor(blgColor));
        price_tv.setCompoundDrawables(null, null, unSelectId, null);
    }

    @Override
    protected void immersionInit() {

    }

    /**
     * 1：获取淘宝商品  2：京东（京东目前申请不下来，未用） 3：获取拼多多商品
     * <p>
     * 搜索商品关键字
     * 淘宝排序：999综合  2优惠  1销量    拼多多排序：14优惠  9用券  6销量  3按价格升序  4按价格降序
     *
     * @param baseView
     */
    private void getShengqiangou(BaseView baseView) {
        setRefresh();
        ApiTool2 apiTool2 = new ApiTool2();
        RequestParams requestParams = new RequestParams();
        requestParams.addBodyParameter("type", mType);
        requestParams.addBodyParameter("q", mQ);
        requestParams.addBodyParameter("sort_type", mSortType);
        requestParams.addBodyParameter("p",String.valueOf(p));
        if (mQ != null){
            apiTool2.postApi(Config.SHARE_URL + "index.php/Api/Goods/getShengqiangou", requestParams, baseView);
        }
    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        stopRefresh();
        refreshVisibleState();
        Map<String, String> map = JSONUtils.parseKeyAndValueToMap(jsonStr);
        Map<String, String> data = JSONUtils.parseKeyAndValueToMap(map.get("data"));
        final ArrayList<Map<String, String>> mapArrayList = JSONUtils.parseKeyAndValueToMapList(data.get("goods_list"));
        if (p == 1){
            mList.clear();
            if (mapArrayList != null && mapArrayList.size()>0){
                mList.addAll(mapArrayList);
            }
        }else {
            if (mapArrayList != null && mapArrayList.size()>0){
                mList.addAll(mapArrayList);
            }
        }
        if ( mList.size() >= 0) {
            if (mAdapter == null){
                mAdapter = new SaveMoneyAdapter(mList);
                mRecyclerView.setAdapter(mAdapter);
                mAdapter.setOnItemClickListener(new SaveMoneyAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        Map<String, String> item = mList.get(position);
                        if (mTitle.equals("淘宝")) {
                            openTaobao(getActivity(),item.get("item_url"));
                        } else if (mTitle.equals("拼多多")) {
                            openPinduoduo(getActivity(),item.get("item_url"));
                        }
                    }
                });
            }else {
                mAdapter.notifyDataSetChanged();
            }

        }

    }



    private void refreshVisibleState() {
        if (progressBar.getVisibility()== View.VISIBLE){
            superSwipeRefreshLayout.setRefreshing(false);
            progressBar.setVisibility(View.GONE);
        }
        if (footerProgressBar.getVisibility()==View.VISIBLE) {
            superSwipeRefreshLayout.setLoadMore(false);
            footerProgressBar.setVisibility(View.GONE);
        }
    }


    @Override
    public void onError(String requestUrl, Map<String, String> error) {
        super.onError(requestUrl, error);
        stopRefresh();
        refreshVisibleState();
    }

    public static void openTaobao(Context context, String url) {
        if (isInstallByread("com.taobao.taobao")){
//            Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse(url));
//            context.startActivity(intent);
            WebView webView = new WebView(context);
            webView.loadUrl(url);
        }else {
            Bundle bundle = new Bundle();
            bundle.putString("url", url);
            bundle.putBoolean("isShowTitle",true);
            bundle.putString("title", "省钱购");
            Intent intent = new Intent(context,WebViewAty.class);
            intent.putExtras(bundle);
            context.startActivity(intent);
        }
    }

    public static void openPinduoduo(Context context,String url) {
        if (isInstallByread("com.xunmeng.pinduoduo")){
            Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            context.startActivity(intent);
        }else {
            Bundle bundle = new Bundle();
            bundle.putString("url", url);
            bundle.putBoolean("isShowTitle",true);
            bundle.putString("title", "省钱购");
            Intent intent = new Intent(context,WebViewAty.class);
            intent.putExtras(bundle);
            context.startActivity(intent);
        }
    }

    /**
     * 判断是否安装目标应用
     *
     * @param packageName 目标应用安装后的包名
     * @return 是否已安装目标应用
     */
    public static boolean isInstallByread(String packageName) {
        return new File("/data/data/" + packageName).exists();
    }

    /**
     * 创建底部加载布局
     *
     * @return
     */
    private View createFooterView() {
        View footerView = LayoutInflater.from(superSwipeRefreshLayout.getContext()).inflate(R.layout.layout_footer, null);
        footerProgressBar = footerView.findViewById(R.id.footer_pb_view);
        footerImageView = footerView.findViewById(R.id.footer_image_view);
        footerTextView = footerView.findViewById(R.id.footer_text_view);
        footerProgressBar.setVisibility(View.GONE);
        footerImageView.setVisibility(View.VISIBLE);
        footerImageView.setImageResource(R.drawable.down_arrow);
        footerTextView.setText("上拉加载更多...");
        return footerView;
    }

    /**
     * 创建头部加载布局
     *
     * @return
     */
    private View createHeaderView() {
        View headerView = LayoutInflater.from(superSwipeRefreshLayout.getContext()).inflate(R.layout.layout_head, null);
        progressBar = headerView.findViewById(R.id.pb_view);
        textView = headerView.findViewById(R.id.text_view);
        textView.setText("下拉刷新");
        imageView = headerView.findViewById(R.id.image_view);
        imageView.setVisibility(View.VISIBLE);
        imageView.setImageResource(R.drawable.down_arrow);
        progressBar.setVisibility(View.GONE);
        return headerView;
    }

    public static class SaveMoneyAdapter extends RecyclerView.Adapter<SaveMoneyAdapter.ViewHolder> {

        private Context mContext;

        private List<Map<String, String>> mList;

        private OnItemClickListener mOnItemClickListener;

        public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
            mOnItemClickListener = onItemClickListener;
        }

        public SaveMoneyAdapter(List<Map<String, String>> list) {
            mList = list;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            mContext = parent.getContext();
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_save_money, parent, false);
            ViewHolder holder = new ViewHolder(view);
            ViewUtils.inject(holder, view);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
            Map<String, String> map = mList.get(position);
            if (map.containsKey("pict_url") && !TextUtils.isEmpty(map.get("pict_url"))) {
                Glide.with(mContext).load(map.get("pict_url")).into(holder.img);
            }
            String biaoshi = map.get("biaoshi");
            if (biaoshi.equals("taobao")){
                holder.biaoshi.setImageResource(R.drawable.tb);
            }else if (biaoshi.equals("tianmao")){
                holder.biaoshi.setImageResource(R.drawable.tm);
            }else if (biaoshi.equals("pinduoduo")){
                holder.biaoshi.setImageResource(R.drawable.pdd);
            }
            holder.titleTv.setText("\u3000\u3000"+map.get("title"));
            holder.priceTv.setText("¥" + map.get("zk_final_price"));
            SpannableString spannableString = new SpannableString("¥" + map.get("reserve_price"));
            StrikethroughSpan strikethroughSpan = new StrikethroughSpan();
            spannableString.setSpan(strikethroughSpan, 0, spannableString.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            holder.older_price_tv.setText(spannableString);
            holder.sellNumTv.setText(map.get("volume"));

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null){
                        mOnItemClickListener.onItemClick(holder.getLayoutPosition());
                    }
                }
            });

        }

        @Override
        public int getItemCount() {
            return mList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            @ViewInject(R.id.img)
            ImageView img;
            @ViewInject(R.id.biaoshi)
            ImageView biaoshi;
            @ViewInject(R.id.titleTv)
            TextView titleTv;
            @ViewInject(R.id.priceTv)
            TextView priceTv;
            @ViewInject(R.id.older_price_tv)
            TextView older_price_tv;
            @ViewInject(R.id.sellNumTv)
            TextView sellNumTv;

            public ViewHolder(View itemView) {
                super(itemView);
            }
        }

        public interface OnItemClickListener{
            void onItemClick(int position);
        }
    }
}
