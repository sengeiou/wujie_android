package com.txd.hzj.wjlp.minetoAty.order;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.Layout;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ants.theantsgo.imageLoader.GlideImageLoader;
import com.ants.theantsgo.tools.ConstantUtils;
import com.ants.theantsgo.util.CompressionUtil;
import com.ants.theantsgo.util.L;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.bean.GoodsEvaluation;
import com.txd.hzj.wjlp.minetoAty.order.adapter.GridImageAdapter;
import com.txd.hzj.wjlp.minetoAty.order.utils.FullyGridLayoutManager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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
    @ViewInject(R.id.titlt_right_tv)
    public TextView titlt_right_tv;

    @ViewInject(R.id.for_goods_evaluste_lv)
    private ListView for_goods_evaluste_lv;

    private GoodsEvalustionAdapter goodsEvalustionAdapter;

    private List<GoodsEvaluation> goodsEvaluations;

    private ImagePicker imagePicker;
    private int selectPicNum = 9;
    /**
     * 当前评价的商品
     */
    private int goods_pos = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("发布评价");
        titlt_right_tv.setVisibility(View.VISIBLE);
        titlt_right_tv.setText("提交");
        titlt_right_tv.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));

        for_goods_evaluste_lv.setAdapter(goodsEvalustionAdapter);
    }

    @Override
    @OnClick({R.id.titlt_right_tv})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.titlt_right_tv:
                L.e("=====最终数据=====", goodsEvaluations.toString());
                break;
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_evaluation_release;
    }

    @Override
    protected void initialized() {
        goodsEvalustionAdapter = new GoodsEvalustionAdapter();
        goodsEvaluations = new ArrayList<>();

        imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());// 使用Glide加载
        imagePicker.setMultiMode(true);// 多选
        imagePicker.setCrop(false);// 是否裁剪
        imagePicker.setShowCamera(true);// 是否显示拍照按钮
        imagePicker.setSelectLimit(selectPicNum);

        for (int i = 0; i < 2; i++) {
            goodsEvaluations.add(new GoodsEvaluation("", (i + 1) + "", "0", "", new ArrayList<File>()));
        }
    }

    @Override
    protected void requestData() {
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
        public GoodsEvaluation getItem(int i) {
            return goodsEvaluations.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            final GoodsEvaluation ge = getItem(i);
            if (view == null) {
                view = LayoutInflater.from(EvaluationReleaseAty.this).inflate(R.layout.item_for_goods_evaluste_lv,
                        null);
                gevvh = new GEVVH();
                ViewUtils.inject(gevvh, view);
                view.setTag(gevvh);
            } else {
                gevvh = (GEVVH) view.getTag();
            }

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
                                ge.getGoodsPicByBuyer().remove(position);
                                ge.setGoodsPicByBuyer(ge.getGoodsPicByBuyer());
                                notifyDataSetChanged();
                            }
                        }
                    });
            gridImageAdapter.setList(ge.getGoodsPicByBuyer());
            gridImageAdapter.setSelectMax(selectPicNum);
            gevvh.updata_pic_rv.setAdapter(gridImageAdapter);

            gevvh.goods_grade_rb.setRating(Float.parseFloat(ge.getGoodsGrade()));
            gevvh.evalusete_context_tv.setText(ge.getGoodsContent());
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
                    ge.setGoodsContent(editable.toString());
                    L.e("========", editable.toString());
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

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == 100) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker
                        .EXTRA_RESULT_ITEMS);
                for (ImageItem img : images) {
                    String pic_path = CompressionUtil.compressionBitmap(img.path);
                    File file = new File(pic_path);
                    goodsEvaluations.get(goods_pos).getGoodsPicByBuyer().add(file);
                    goodsEvalustionAdapter.notifyDataSetChanged();
                }

            } else {
                showErrorTip("哎呀出错了。。");
            }
        }
    }
}
