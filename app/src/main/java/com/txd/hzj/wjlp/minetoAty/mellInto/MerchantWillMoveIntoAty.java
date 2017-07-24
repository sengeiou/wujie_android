package com.txd.hzj.wjlp.minetoAty.mellInto;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.txd.hzj.wjlp.minetoAty.order.TextListAty;

import java.io.File;
import java.util.ArrayList;

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
     * 身份证正面
     */
    @ViewInject(R.id.top_left_iv)
    private ImageView top_left_iv;
    /**
     * 身份证背面
     */
    @ViewInject(R.id.top_right_iv)
    private ImageView top_right_iv;
    /**
     * 手持身份证
     */
    @ViewInject(R.id.bottom_left_iv)
    private ImageView bottom_left_iv;
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

    private File file1, file2, file3, file4;

    private ImagePicker imagePacker;
    /**
     * 经营范围
     */
    private String scope = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("商家入驻");
        int width = Settings.displayWidth - padding;
        w = width / 2;
        h = width / 4;
        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(w, h);

        top_left_iv.setLayoutParams(param);
        top_right_iv.setLayoutParams(param);
        bottom_left_iv.setLayoutParams(param);
        bottom_right_iv.setLayoutParams(param);
    }

    @Override
    @OnClick({R.id.top_left_iv, R.id.top_right_iv, R.id.bottom_left_iv, R.id.bottom_right_iv, R.id.manage_scope_layout})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.manage_scope_layout:// 经营范围
                Bundle bundle = new Bundle();
                bundle.putString("title", "选择经营范围");
                startActivityForResult(TextListAty.class, bundle, 104);
                break;
            case R.id.top_left_iv:// 身份证正面照
                startActivityForResult(ImageGridActivity.class, null, 100);
                break;
            case R.id.top_right_iv:// 身份证反面照
                startActivityForResult(ImageGridActivity.class, null, 101);
                break;
            case R.id.bottom_left_iv:// 手持身份证照
                startActivityForResult(ImageGridActivity.class, null, 102);
                break;
            case R.id.bottom_right_iv:// 营业执照
                startActivityForResult(ImageGridActivity.class, null, 103);
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
        imagePacker = ImagePicker.getInstance();
        imagePacker.setImageLoader(new GlideImageLoader());// 使用Glide加载
        imagePacker.setShowCamera(true);
        imagePacker.setCrop(false);
        imagePacker.setMultiMode(false);
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
                        file1 = new File(pic_path);
                        Glide.with(this).load(file1).override(w, h).centerCrop().into(top_left_iv);
                        break;
                    case 101:
                        file2 = new File(pic_path);
                        Glide.with(this).load(file2).override(w, h).centerCrop().into(top_right_iv);
                        break;
                    case 102:
                        file3 = new File(pic_path);
                        Glide.with(this).load(file3).override(w, h).centerCrop().into(bottom_left_iv);
                        break;
                    case 103:
                        file4 = new File(pic_path);
                        Glide.with(this).load(file4).override(w, h).centerCrop().into(bottom_right_iv);
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
