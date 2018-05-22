package com.txd.hzj.wjlp.minetoAty.order;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
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
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.http.Order;
import com.txd.hzj.wjlp.minetoAty.order.adapter.GridImageAdapter;
import com.txd.hzj.wjlp.minetoAty.order.utils.FullyGridLayoutManager;
import com.txd.hzj.wjlp.new_wjyp.CommentindexBean;
import com.txd.hzj.wjlp.new_wjyp.aty_commentindex;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/20 0020
 * 时间：上午 11:55
 * 描述：商品评价
 * ===============Txunda===============
 */
public class EvaluationReleaseAty extends BaseAty {
    @ViewInject(R.id.titlt_conter_tv)
    public TextView titlt_conter_tv;
    @ViewInject(R.id.tv_submit)
    public TextView titlt_right_tv;

    @ViewInject(R.id.for_goods_evaluste_lv)
    private ListView for_goods_evaluste_lv;

    //    private GoodsEvalustionAdapter goodsEvalustionAdapter;
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
        titlt_conter_tv.setText("评价列表");
    }

    @Override
    @OnClick({R.id.tv_submit})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_submit: // 提交订单的服务评价
                // 参数说明：订单ID， 商家评分1~5星， 配送评分， 订评论订单单类型（1普通单 2拼单购 3无界预购 4比价购 5限量购 6积分抽奖）， BaseView
                Order.CommentOrder(order_id, String.valueOf(ratingBar1.getRating()), String.valueOf(ratingBar2.getRating()), type, this);
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
            } else { // 已评价
                titlt_right_tv.setVisibility(View.GONE);
                ratingBar1.setRating(map.get("merchant_star").equals("") ? 0.0f : Float.parseFloat(map.get("merchant_star"))); // 店铺评分
                ratingBar2.setRating(map.get("delivery_star").equals("") ? 0.0f : Float.parseFloat(map.get("delivery_star"))); // 物流评分
                L.e("===============merchant_star:===============" + Float.parseFloat(map.get("merchant_star")));
                L.e("===============delivery_star:===============" + Float.parseFloat(map.get("delivery_star")));
                ratingBar1.setIsIndicator(true); // 卖家服务
                ratingBar2.setIsIndicator(true); // 物流服务
            }
        }
    }

    private class GoodsEvalustionAdapter extends BaseAdapter {

        private GEVVH gevvh;
        private GridImageAdapter gridImageAdapter;
        private FullyGridLayoutManager manager;

        @Override
        public int getCount() {
            return goodsEvaluations.size();
        }

        @Override
        public CommentindexBean.GoodsListBean getItem(int i) {
            return goodsEvaluations.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            final CommentindexBean.GoodsListBean ge = getItem(i);
            if (view == null) {
                view = LayoutInflater.from(EvaluationReleaseAty.this).inflate(R.layout.item_for_goods_evaluste_lv, null);
                gevvh = new GEVVH();
                ViewUtils.inject(gevvh, view);
                view.setTag(gevvh);
            } else {
                gevvh = (GEVVH) view.getTag();
            }
            Glide.with(EvaluationReleaseAty.this).load(ge.getGoods_img()).into(gevvh.imageview);
            manager = new FullyGridLayoutManager(EvaluationReleaseAty.this, 3, GridLayoutManager.VERTICAL, false);
            gevvh.updata_pic_rv.setLayoutManager(manager);
            gridImageAdapter = new GridImageAdapter(EvaluationReleaseAty.this,
                    new GridImageAdapter.onAddPicClickListener() {
                        @Override
                        public void onAddPicClick(int type, int position) {
                            goods_pos = i;
                            if (0 == type) {
                                startActivityForResult(ImageGridActivity.class, null, 100);
                            } else {

                            }
                        }
                    });
//            gridImageAdapter.setList(ge.getFileList());
            gridImageAdapter.setSelectMax(selectPicNum);
            gevvh.updata_pic_rv.setAdapter(gridImageAdapter);
            gevvh.goods_grade_rb.setRating(Float.parseFloat(ge.getAll_star()));
            gevvh.goods_grade_rb.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                @Override
                public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                    ge.setAll_star(String.valueOf(rating));
                }
            });
            gevvh.evalusete_context_tv.setText(ge.getContent());
            // 获取到每个商品的评价内容
            gevvh.evalusete_context_tv.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void afterTextChanged(Editable editable) {
                    ge.setContent(editable.toString());
                }
            });

            return view;
        }


        class GEVVH {
            /**
             * 评分条
             */
            @ViewInject(R.id.goods_grade_rb)
            private RatingBar goods_grade_rb;
            /**
             * 评价内容
             */
            @ViewInject(R.id.evalusete_context_tv)
            private EditText evalusete_context_tv;
            /**
             * 图片列表
             */
            @ViewInject(R.id.updata_pic_rv)
            private RecyclerView updata_pic_rv;
            @ViewInject(R.id.imageview)
            private ImageView imageview;

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
            return 0;
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
                viewHolder.goods_grade_rb.setRating(Float.parseFloat(bean.getAll_star()));
                viewHolder.goods_grade_rb.setVisibility(View.VISIBLE);
            } else {
                viewHolder.tv_btn_right.setVisibility(View.VISIBLE);
                viewHolder.goods_grade_rb.setVisibility(View.GONE);
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
                        startActivity(aty_commentindex.class, bundle);
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
        }
    }


}
