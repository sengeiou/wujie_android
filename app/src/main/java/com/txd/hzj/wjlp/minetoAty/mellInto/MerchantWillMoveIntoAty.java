package com.txd.hzj.wjlp.minetoAty.mellInto;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ants.theantsgo.imageLoader.GlideImageLoader;
import com.ants.theantsgo.tool.ToolKit;
import com.ants.theantsgo.util.CompressionUtil;
import com.bumptech.glide.Glide;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.minetoAty.order.EvaluationReleaseAty;
import com.txd.hzj.wjlp.minetoAty.order.TextListAty;
import com.txd.hzj.wjlp.minetoAty.order.adapter.GridImageAdapter;
import com.txd.hzj.wjlp.minetoAty.order.utils.FullyGridLayoutManager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/24 0024
 * 时间：下午 2:42
 * 描述：商家入驻
 * ===============Txunda===============
 */
public class MerchantWillMoveIntoAty extends BaseAty {

    @ViewInject(R.id.titlt_conter_tv)
    private TextView titlt_conter_tv;

    /**
     * 营业执照
     */
    @ViewInject(R.id.bottom_right_iv)
    private ImageView bottom_right_iv;

    @ViewInject(R.id.manage_scope_tv)
    private TextView manage_scope_tv;

    private int padding = 0;

    private int w = 0;
    private int h = 0;

    private File file4;

    /**
     * 经营范围
     */
    private String scope = "";


    /**
     * 上传商品照片
     */
    @ViewInject(R.id.goods_pic_rv)
    private RecyclerView goods_pic_rv;
    /**
     * 上传其他证件
     */
    @ViewInject(R.id.identification_photo_rv)
    private RecyclerView identification_photo_rv;

    private List<File> goods_pic;
    private List<File> identification_photo;
    private FullyGridLayoutManager manager;

    private GridImageAdapter gridImageAdapter;
    private GridImageAdapter gridImageAdapter2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("商家推荐");
        manager = new FullyGridLayoutManager(MerchantWillMoveIntoAty.this, 3, GridLayoutManager.VERTICAL, false);
        goods_pic_rv.setLayoutManager(manager);

        gridImageAdapter = new GridImageAdapter(this, new GridImageAdapter.onAddPicClickListener() {
            @Override
            public void onAddPicClick(int type, int position) {
                if (0 == type) {
                    forImagePicker(9);
                    startActivityForResult(ImageGridActivity.class, null, 101);
                } else {
                    goods_pic.remove(position);
                    gridImageAdapter.notifyItemRemoved(position);
                }
            }
        });
        gridImageAdapter.setList(goods_pic);
        gridImageAdapter.setSelectMax(9);
        goods_pic_rv.setAdapter(gridImageAdapter);

        FullyGridLayoutManager manager2 = new FullyGridLayoutManager(MerchantWillMoveIntoAty.this, 3, 
                GridLayoutManager.VERTICAL, false);
        identification_photo_rv.setLayoutManager(manager2);
        gridImageAdapter2 = new GridImageAdapter(this, new GridImageAdapter.onAddPicClickListener() {
            @Override
            public void onAddPicClick(int type, int position) {
                if (0 == type) {
                    forImagePicker(9);
                    startActivityForResult(ImageGridActivity.class, null, 103);
                } else {
                    identification_photo.remove(position);
                    gridImageAdapter2.notifyItemRemoved(position);
                }
            }
        });
        gridImageAdapter2.setList(identification_photo);
        gridImageAdapter2.setSelectMax(9);
        identification_photo_rv.setAdapter(gridImageAdapter2);


    }

    @Override
    @OnClick({ R.id.bottom_right_iv, R.id.manage_scope_layout})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.manage_scope_layout:// 经营范围
                Bundle bundle = new Bundle();
                bundle.putString("title", "选择经营范围");
                startActivityForResult(TextListAty.class, bundle, 104);
                break;
            case R.id.bottom_right_iv:// 营业执照
                forImagePicker(1);
                startActivityForResult(ImageGridActivity.class, null, 102);
                break;
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_merchant_will_move_into;
    }

    @Override
    protected void initialized() {
        padding = ToolKit.dip2px(this, 36);
        w = ToolKit.dip2px(this, 100);
        h = ToolKit.dip2px(this, 100);
        goods_pic = new ArrayList<>();
        identification_photo = new ArrayList<>();
    }

    private void forImagePicker(int num) {
        ImagePicker imagePacker = ImagePicker.getInstance();
        imagePacker.setImageLoader(new GlideImageLoader());// 使用Glide加载
        imagePacker.setShowCamera(true);
        imagePacker.setCrop(false);
        if (num == 1) {
            imagePacker.setMultiMode(false);
        } else {
            imagePacker.setSelectLimit(9);
            imagePacker.setMultiMode(true);
        }
    }

    @Override
    protected void requestData() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker
                        .EXTRA_RESULT_ITEMS);
                switch (requestCode) {
                    case 101:
                        for (ImageItem img : images) {
                            String pic_path = CompressionUtil.compressionBitmap(img.path);
                            File file = new File(pic_path);
                            goods_pic.add(file);
                        }
                        gridImageAdapter.notifyDataSetChanged();
                        break;
                    case 102:
                        String pic_path = CompressionUtil.compressionBitmap(images.get(0).path);
                        file4 = new File(pic_path);
                        Glide.with(this).load(file4).override(w, h).centerCrop().into(bottom_right_iv);
                        break;
                    case 103:
                        for (ImageItem img : images) {
                            String id_pic = CompressionUtil.compressionBitmap(img.path);
                            File iden_file = new File(id_pic);
                            identification_photo.add(iden_file);
                        }
                        gridImageAdapter2.notifyDataSetChanged();
                        break;
                }

            } else {
                showErrorTip("哎呀出错了。。");
            }
        } else if (resultCode == RESULT_OK) {
            if (data != null && requestCode == 104) {
                scope = data.getStringExtra("scope");
                manage_scope_tv.setText(scope);
            }
        }
    }
}
