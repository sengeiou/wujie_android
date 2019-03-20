package com.txd.hzj.wjlp.savemoney;

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
import android.widget.ImageView;
import android.widget.LinearLayout;
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
    private String mQ = "女装";


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

    @ViewInject(R.id.recyclerView)
    private RecyclerView mRecyclerView;
    private SaveMoneyAdapter mAdapter;

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
    }

    @Override
    protected void requestData() {
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
                priceLayout.setVisibility(View.GONE);
                internal_tv.setText("综合");
                cash_coupon_tv.setText("优惠");

            } else if (mTitle.equals("拼多多")) {
                mType = "3";
                priceLayout.setVisibility(View.VISIBLE);
                internal_tv.setText("优惠");
                cash_coupon_tv.setText("用券");
            }
        }
        setChioceItem(0);
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
        getShengqiangou(this);
    }

    private void setChioceItem(int index) {
        clearChioce();
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
        ApiTool2 apiTool2 = new ApiTool2();
        RequestParams requestParams = new RequestParams();
        requestParams.addBodyParameter("type", mType);
        requestParams.addBodyParameter("q", mQ);
        requestParams.addBodyParameter("sort_type", mSortType);
        apiTool2.postApi(Config.SHARE_URL + "index.php/Api/Goods/getShengqiangou", requestParams, baseView);
    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        Map<String, String> map = JSONUtils.parseKeyAndValueToMap(jsonStr);
        Map<String, String> data = JSONUtils.parseKeyAndValueToMap(map.get("data"));
        final ArrayList<Map<String, String>> list = JSONUtils.parseKeyAndValueToMapList(data.get("goods_list"));
        if (list != null && list.size() > 0) {
            mAdapter = new SaveMoneyAdapter(list);
            mRecyclerView.setAdapter(mAdapter);
            mAdapter.setOnItemClickListener(new SaveMoneyAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    Map<String, String> item = list.get(position);
                    if (mTitle.equals("淘宝")) {
                        openTaobao(getActivity(),item.get("item_url"));
                    } else if (mTitle.equals("拼多多")) {
                        openPinduoduo(getActivity(),item.get("item_url"));
                    }
                }
            });
        }

    }

    public static void openTaobao(Context context,String url) {
        if (isInstallByread("com.taobao.taobao")){
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
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
            holder.titleTv.setText(map.get("title"));
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
