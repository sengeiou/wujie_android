package com.txd.hzj.wjlp.minetoaty.order;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.ants.theantsgo.gson.GsonUtil;
import com.ants.theantsgo.imageLoader.GlideImageLoader;
import com.ants.theantsgo.util.CompressionUtil;
import com.ants.theantsgo.util.JSONUtils;
import com.ants.theantsgo.util.L;
import com.bumptech.glide.Glide;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.http.Order;
import com.txd.hzj.wjlp.new_wjyp.CommentindexBean;
import com.txd.hzj.wjlp.new_wjyp.Commentindex_aty;
import com.txd.hzj.wjlp.tool.WJConfig;
import com.txd.hzj.wjlp.view.RatingBar;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 作者：DUKE_HwangZj
 * 日期：2017/7/20 0020
 * 时间：上午 11:55
 * 描述：商品评价
 */
public class EvaluationReleaseAty extends BaseAty {
    @ViewInject(R.id.titlt_conter_tv)
    public TextView titlt_conter_tv;
    @ViewInject(R.id.tv_submit)
    public TextView titlt_right_tv;

    @ViewInject(R.id.for_goods_evaluste_lv)
    private ListView for_goods_evaluste_lv;

    private MyAdapter Adapter;
    private CommentindexBean commentindexBean;
    private List<CommentindexBean.GoodsListBean> goodsEvaluations;

    private ImagePicker imagePicker;
    private int selectPicNum = 9;
    /**
     * 当前评价的商品
     */
    private int goods_pos = 0;
    private String order_id;
    private String type;
    @ViewInject(R.id.rb_1)
    RatingBar ratingBar1;
    @ViewInject(R.id.rb_2)
    RatingBar ratingBar2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("订单评价");
    }

    @Override
    @OnClick({R.id.tv_submit})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_submit: // 提交订单的服务评价
                // 参数说明：订单ID， 商家评分1~5星， 配送评分， 订评论订单单类型（1普通单 2拼单购 3无界预购 4比价购 5限量购 6积分抽奖）， BaseView
                Order.CommentOrder(order_id, String.valueOf(ratingBar1.getStarStep()), String.valueOf(ratingBar2.getStarStep()), type, this);
                showProgressDialog();
                break;
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_evaluation_release;
    }

    @Override
    protected void initialized() {
        imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());// 使用Glide加载
        imagePicker.setMultiMode(true);// 多选
        imagePicker.setCrop(false);// 是否裁剪
        imagePicker.setShowCamera(true);// 是否显示拍照按钮
        imagePicker.setSelectLimit(selectPicNum);
    }

    @Override
    protected void requestData() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (TextUtils.isEmpty(order_id)) {
            order_id = getIntent().getStringExtra("order_id");
        }
        if (TextUtils.isEmpty(type)) {
            type = getIntent().getStringExtra("type");
            if (WJConfig.TYPE_SJJZQ.equals(type)){
                type="0";
            }
        }
        Order.Commentindex(order_id,type, this);
        showProgressDialog();
    }

    Map<String, String> map;

    @Override
    public void onComplete(String requestUrl, String jsonStr) {

        L.e("requestUrl:" + requestUrl + "\tjsonStr:" + jsonStr);

        super.onComplete(requestUrl, jsonStr);
        map = JSONUtils.parseKeyAndValueToMap(jsonStr);
        map = JSONUtils.parseKeyAndValueToMap(map.get("data"));
        if (requestUrl.contains("CommentOrder")) { // 评价订单
            Order.Commentindex(order_id, type,this); // 再次申请获取一下评论列表主页
            showProgressDialog();
        }
        if (requestUrl.contains("Commentindex")) {
            goodsEvaluations = GsonUtil.getObjectList(map.get("goods_list"), CommentindexBean.GoodsListBean.class);
            for (CommentindexBean.GoodsListBean goodsBean : goodsEvaluations) {
                L.e("===========goodsBean=============" + goodsBean.toString());
            }
            Adapter = new MyAdapter();
            for_goods_evaluste_lv.setAdapter(Adapter);
            if (map.get("order_status").equals("0")) { // 未评价
                titlt_right_tv.setVisibility(View.VISIBLE);
                titlt_right_tv.setText("评价服务");
                titlt_right_tv.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));
                ratingBar1.setClickable(true);
                ratingBar2.setClickable(true);
            } else { // 已评价
                titlt_right_tv.setVisibility(View.GONE);
                ratingBar1.setStar(map.get("merchant_star").equals("") ? 0.0f : Float.parseFloat(map.get("merchant_star"))); // 店铺评分
                ratingBar2.setStar(map.get("delivery_star").equals("") ? 0.0f : Float.parseFloat(map.get("delivery_star"))); // 物流评分
                L.e("===============merchant_star:===============" + Float.parseFloat(map.get("merchant_star")));
                L.e("===============delivery_star:===============" + Float.parseFloat(map.get("delivery_star")));
                ratingBar1.setClickable(false);// 卖家服务
                ratingBar2.setClickable(false);// 物流服务
            }
        }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == 100) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                for (ImageItem img : images) {
                    String pic_path = CompressionUtil.compressionBitmap(img.path);
                    File file = new File(pic_path);
//                    goodsEvaluations.get(goods_pos).getFileList().add(file);
//                    goodsEvalustionAdapter.notifyDataSetChanged();
                }
            } else {
                showErrorTip("哎呀出错了。。");
            }
        }
    }

    class MyAdapter extends BaseAdapter {
        ViewHolder viewHolder;

        @Override
        public int getCount() {
            return goodsEvaluations.size();
        }

        @Override
        public CommentindexBean.GoodsListBean getItem(int i) {
            return goodsEvaluations.get(i);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int i, View convertView, ViewGroup parent) {
            final CommentindexBean.GoodsListBean bean = getItem(i);
            L.e("============getView.GoodsListBean=============:" + bean.toString());
            if (convertView == null) {
                convertView = LayoutInflater.from(EvaluationReleaseAty.this).inflate(R.layout.item_commentindex, null);
                viewHolder = new ViewHolder();
                convertView.setTag(viewHolder);
                ViewUtils.inject(viewHolder, convertView);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            if (bean.getStatus().equals("1")) {
                viewHolder.tv_btn_right.setVisibility(View.GONE);
                viewHolder.goods_grade_rb.setStar(Float.parseFloat(bean.getAll_star()));
                viewHolder.goods_grade_rb.setVisibility(View.VISIBLE);
            } else {
                viewHolder.tv_btn_right.setVisibility(View.VISIBLE);
                viewHolder.goods_grade_rb.setVisibility(View.GONE);
            }

            if (bean.getIs_active()!=null && bean.getIs_active().equals("2")){
                viewHolder.tv_399.setVisibility(View.VISIBLE);
            }else {
                viewHolder.tv_399.setVisibility(View.GONE);
            }
            Glide.with(EvaluationReleaseAty.this).load(bean.getGoods_img()).into(viewHolder.imageview);
            viewHolder.tv_goods_name.setText(bean.getGoods_name());
            viewHolder.tv_btn_right.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (bean.getStatus().equals("0")) {
                        Bundle bundle = new Bundle();
                        bundle.putString("order_goods_id", bean.getOrder_goods_id());
                        bundle.putString("goods_img", bean.getGoods_img());
                        bundle.putString("order_id", order_id);
                        bundle.putString("type", type);
                        bundle.putString("good_name",bean.getGoods_name());
                        bundle.putString("is_active",bean.getIs_active());
                        startActivity(Commentindex_aty.class, bundle);
                    }
                }
            });
            return convertView;
        }

        class ViewHolder {
            @ViewInject(R.id.imageview)
            private ImageView imageview;
            @ViewInject(R.id.tv_goods_name)
            private TextView tv_goods_name;
            @ViewInject(R.id.goods_grade_rb)
            private RatingBar goods_grade_rb;
            @ViewInject(R.id.tv_btn_right)
            private TextView tv_btn_right;
            @ViewInject(R.id.tv_399)
            private TextView tv_399;
        }
    }


}
