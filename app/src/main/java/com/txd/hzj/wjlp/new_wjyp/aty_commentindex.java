package com.txd.hzj.wjlp.new_wjyp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.ants.theantsgo.util.CompressionUtil;
import com.bumptech.glide.Glide;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.minetoAty.order.adapter.GridImageAdapter;
import com.txd.hzj.wjlp.minetoAty.order.utils.FullyGridLayoutManager;
import com.txd.hzj.wjlp.new_wjyp.http.Order;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class aty_commentindex extends BaseAty {
    @ViewInject(R.id.titlt_conter_tv)
    public TextView titlt_conter_tv;
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
    @ViewInject(R.id.tv_submit)
    private TextView tv_submit;

    private GridImageAdapter gridImageAdapter;
    private FullyGridLayoutManager manager;
    private int selectPicNum = 9;
    List<File> list = new ArrayList<>();

    String goods_id, goods_img, order_id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.item_for_goods_evaluste_lv;
    }

    @Override
    protected void initialized() {
        goods_id = getIntent().getStringExtra("order_goods_id");
        goods_img = getIntent().getStringExtra("goods_img");
        order_id = getIntent().getStringExtra("order_id");
        Glide.with(this).load(goods_img).into(imageview);
        titlt_conter_tv.setText("发布评价");
        manager = new FullyGridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false);
        updata_pic_rv.setLayoutManager(manager);
        gridImageAdapter = new GridImageAdapter(this,
                new GridImageAdapter.onAddPicClickListener() {
                    @Override
                    public void onAddPicClick(int type, int position) {
                        if (0 == type) {
                            startActivityForResult(ImageGridActivity.class, null, 100);
                        } else {
                            list.remove(position);
                            gridImageAdapter.notifyDataSetChanged();
                        }
                    }
                });

        gridImageAdapter.setList(list);
        gridImageAdapter.setSelectMax(selectPicNum);
        updata_pic_rv.setAdapter(gridImageAdapter);
        tv_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Order.CommentGoods(goods_id, evalusete_context_tv.getText().toString(),
                        list, String.valueOf(goods_grade_rb.getRating()), order_id, "1", aty_commentindex.this);
                showProgressDialog();
            }
        });
    }

    @Override
    protected void requestData() {

    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        showToast("评价成功！");
        finish();
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
                    list.add(file);
                    gridImageAdapter.notifyDataSetChanged();
                }

            } else {
                showErrorTip("哎呀出错了。。");
            }
        }
    }
}