package com.txd.hzj.wjlp.distribution.shopAty;

import android.content.Intent;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ants.theantsgo.util.CompressionUtil;
import com.ants.theantsgo.util.L;
import com.bumptech.glide.Glide;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.ui.ImagePreviewActivity;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;

import java.io.File;
import java.util.ArrayList;

import cn.gavinliu.android.lib.shapedimageview.ShapedImageView;

/**
 * 创建者：Qyl
 * 创建时间：2018/6/6 0006 14:19
 * 功能描述：店铺设置页面
 * 联系方式：无
 */
public class ShopSetUp extends BaseAty implements View.OnClickListener {

    private RelativeLayout setIma;
    private View view;
    private PopupWindow popupWindow;
    private TextView titleName;
    private LinearLayout shop_person_title_manage;
    private TextView setPicture;
    private TextView setAlbum;
    private File file1;
    private ShapedImageView shopImage;

    @Override
    protected int getLayoutResId() {
        return R.layout.shop_set_up;
    }

    @Override
    protected void initialized() {
        setIma = findViewById(R.id.shop_set_ima);
        shopImage = findViewById(R.id.img_head);
        titleName = findViewById(R.id.titlt_conter_tv);
        shop_person_title_manage=findViewById(R.id.shop_person_title_manage);
        shop_person_title_manage.setVisibility(View.GONE);
        titleName.setText("店铺设置");
        //注册点击事件
        setIma.setOnClickListener(this);
    }

    @Override
    protected void requestData() {

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.shop_set_ima:
                showDialogs();
                break;

        }
    }

    private void showDialogs() {

        view = View.inflate(ShopSetUp.this, R.layout.shop_dialog_popup, null);
         view.findViewById(R.id.shop_dialog_disimis).setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 popupWindow.dismiss();
             }
         });

        //跟布局
        RelativeLayout view1 = (RelativeLayout) view.findViewById(R.id.shop_set_main);
        view1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupWindow != null && popupWindow.isShowing()){
                    popupWindow.dismiss();
                    popupWindow = null;
                }

            }
        });
        //拍照
        setPicture = view1.findViewById(R.id.shop_set_up_picture);
        //相册
        setAlbum = view1.findViewById(R.id.shop_set_up_album);
        setPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ShopSetUp.this, ImagePreviewActivity.class);
                startActivityForResult(i, 1001);
            }
        });
        setAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        popupWindow = new PopupWindow(view, RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setAnimationStyle(R.style.BottomDialog_Animation);
        setBackgroundAlpha(0.5f);
        popupWindow.setOutsideTouchable(false);
        popupWindow.setTouchable(true);
        popupWindow.setFocusable(false);
        popupWindow.showAtLocation(view1, Gravity.BOTTOM , 0,
                0);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setBackgroundAlpha(1.0f);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            try {
                if (data != null && data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS) != null) {
                    ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                    String pic_path = CompressionUtil.compressionBitmap(images.get(0).path);
                    switch (requestCode) {
                        case 101: // 身份证正面照
                            if (!pic_path.equals("")) {
                                file1 = new File(pic_path);
                                if (file1 != null && file1.isFile()) {
                                    Glide.with(this).load(file1).error(R.mipmap.icon_idcard_front)
                                            .placeholder(R.mipmap.icon_idcard_front).centerCrop().into(shopImage);
                                }
                            }
                            break;
                    }
                }
            } catch (Exception e) {
                L.e("fragment1 onActivityResult is exception:" + e.toString());
                showErrorTip("图片选择出现异常");
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
            popupWindow = null;
        }
        return super.onTouchEvent(event);
    }
    public void setBackgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = (this).getWindow()
                .getAttributes();
        lp.alpha = bgAlpha;
        (this).getWindow().setAttributes(lp);
    }
}
