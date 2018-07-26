package com.txd.hzj.wjlp.minetoAty.mell;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ants.theantsgo.config.Settings;
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

import java.io.File;
import java.util.ArrayList;

import static com.txd.hzj.wjlp.R.id.img_head_edit;

/**
 * 作者：DUKE_HwangZj
 * 日期：2017/7/31 0031
 * 时间：下午 1:27
 * 描述：修改驿站资料
 */
public class EditMellInfoAty extends BaseAty {

    /**
     * 设置标题
     */
    @ViewInject(R.id.titlt_conter_tv)
    public TextView titlt_conter_tv;

    /**
     * 保存
     */
    @ViewInject(R.id.titlt_right_tv)
    public TextView titlt_right_tv;

    /**
     * 商店头像
     */
    @ViewInject(R.id.nell_head_iv)
    private ImageView nell_head_iv;
    private File file;
    private int size=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("设置");
        titlt_right_tv.setText("保存");
        titlt_right_tv.setVisibility(View.VISIBLE);
        titlt_right_tv.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));
    }

    @Override
    @OnClick({R.id.updata_mell_head_layout})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.updata_mell_head_layout:
                startActivityForResult(ImageGridActivity.class, null, 100);
                break;
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_edit_mell_info;
    }

    @Override
    protected void initialized() {
        size = ToolKit.dip2px(this, 80);
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());// 图片加载
        imagePicker.setCrop(true);// 裁剪
        imagePicker.setSaveRectangle(true);// 矩形保存
        imagePicker.setFocusWidth(Settings.displayWidth);//裁剪框宽度
        imagePicker.setFocusHeight(Settings.displayWidth);// 裁剪框高度
        imagePicker.setOutPutX(Settings.displayWidth);// 保存图片高度
        imagePicker.setOutPutY(Settings.displayWidth);// 保存图片宽度
        imagePicker.setMultiMode(false);// 但须
        imagePicker.setShowCamera(true);// 显示拍照按钮
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
                String pic_path = CompressionUtil.compressionBitmap(images.get(0).path);
                switch (requestCode) {
                    case 100:
                        file = new File(pic_path);
                        Glide.with(this).load(file).override(size, size).centerCrop().into(nell_head_iv);
                        break;
                }
            } else {
                showErrorTip("哎呀出错了。。");
            }
        }
    }
}
