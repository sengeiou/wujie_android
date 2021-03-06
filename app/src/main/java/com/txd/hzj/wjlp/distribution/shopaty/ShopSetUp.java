package com.txd.hzj.wjlp.distribution.shopaty;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ants.theantsgo.imageLoader.GlideImageLoader;
import com.ants.theantsgo.util.CompressionUtil;
import com.ants.theantsgo.util.JSONUtils;
import com.ants.theantsgo.util.PreferencesUtils;
import com.bumptech.glide.Glide;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.distribution.presenter.ShopExhibitPst;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

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
    private TextView titleRight;
    private ShopExhibitPst pst;
    private String shopName;
    private String shopDesc;
    private String shopUrl;
    private EditText inPutName;
    private EditText shopDetails;
    private String uri;
    private boolean isUpdata = false;
    private String mShop_id;
    private String user_id;
    private TextView date_tv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.shop_set_up;
    }

    @Override
    protected void initialized() {

        setIma = findViewById(R.id.shop_set_ima);
        titleRight = findViewById(R.id.titlt_right_tv);
        shopImage = findViewById(R.id.img_head);
        titleName = findViewById(R.id.titlt_conter_tv);
        inPutName = findViewById(R.id.shop_imput_name);
        shopDetails = findViewById(R.id.shop_details);
        date_tv=findViewById(R.id.date_tv);
        shop_person_title_manage = findViewById(R.id.shop_person_title_manage);
        shop_person_title_manage.setVisibility(View.GONE);
        titleName.setText("店铺设置");
        //注册点击事件
        setIma.setOnClickListener(this);
        titleRight.setOnClickListener(this);
        forImagePacker();
        if (PreferencesUtils.containKey(ShopSetUp.this, "shop_id")) {
            mShop_id = PreferencesUtils.getString(ShopSetUp.this, "shop_id");
        }
    }

    @Override
    protected void requestData() {
        titleRight.setVisibility(View.VISIBLE);
        titleRight.setText("保存");
        titleRight.setTextColor(Color.rgb(255, 0, 0));
        pst = new ShopExhibitPst(this);
        if (null != pst && !TextUtils.isEmpty(mShop_id)) {
            pst.shops(mShop_id);
        }

    }

    /**
     * 设置拍照数据
     */
    private void forImagePacker() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());// 图片加载
        imagePicker.setCrop(true);// 裁剪
        imagePicker.setSaveRectangle(true);// 矩形保存
        imagePicker.setFocusWidth(500);//裁剪框宽度
        imagePicker.setFocusHeight(500);// 裁剪框高度
        imagePicker.setOutPutX(1000);// 保存图片高度
        imagePicker.setOutPutY(1000);// 保存图片宽度
        imagePicker.setMultiMode(false);// 但须
        imagePicker.setShowCamera(true);// 显示拍照按钮
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.shop_set_ima:
                showDialogs();
                break;
            case R.id.titlt_right_tv:
                String tvName = inPutName.getText().toString().trim();
                String tvDetails = shopDetails.getText().toString().trim();
                long l = System.currentTimeMillis();
                isUpdata = true;
//                if (file1 != null) {
                    pst.shopsetData(mShop_id, tvName, file1, tvDetails, user_id, l + "");
//                }

                break;
        }
    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        if (requestUrl.contains("shops")) {
            Log.i("获取信息", jsonStr.toString());
            Map<String, String> map = JSONUtils.parseKeyAndValueToMap(jsonStr);
            if ("200".equals(JSONUtils.getMapValue(map, "code"))) {
                if (isUpdata) {
                    ShopSetUp.this.finish();
                    showToast(JSONUtils.getMapValue(map, "message"));
                }

                String data = JSONUtils.getMapValue(map, "data");
                Map<String, String> mapData = JSONUtils.parseKeyAndValueToMap(data);

                //                List<ShopGetDetailsBean.DataBean> data = detailsBean.getData();
                //店铺名字
                shopName = JSONUtils.getMapValue(mapData, "shop_name");
                //店铺描述
                shopDesc = JSONUtils.getMapValue(mapData, "shop_desc");
                //店铺头像
                shopUrl = JSONUtils.getMapValue(mapData, "shop_url");

                String end_time = JSONUtils.getMapValue(mapData, "end_time");
                SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd");
                int i = Integer.parseInt(end_time);
                String times = sdr.format(new Date(i * 1000L));
                date_tv.setText(times);

                user_id = JSONUtils.getMapValue(mapData, "user_id");
                if (!TextUtils.isEmpty(shopUrl)) {
                    Glide.with(ShopSetUp.this).load(shopUrl).centerCrop().into(shopImage);
                }
                if (!TextUtils.isEmpty(shopName)) {
                    inPutName.setText(shopName);
                }
                if (!TextUtils.isEmpty(shopDesc) && !"null".equalsIgnoreCase(shopDesc)) {
                    shopDetails.setText(shopDesc);
                }

            }

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
                if (popupWindow != null && popupWindow.isShowing()) {
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
                Intent intent = new Intent(ShopSetUp.this, ImageGridActivity.class);
                intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS, true); // 是否是直接打开相机
                startActivityForResult(intent, 100);
            }
        });
        setAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(ShopSetUp.this, ImageGridActivity.class);
                startActivityForResult(in, 102);
            }
        });
        popupWindow = new PopupWindow(view, RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setAnimationStyle(R.style.BottomDialog_Animation);
        setBackgroundAlpha(0.5f);
        popupWindow.setOutsideTouchable(false);
        popupWindow.setTouchable(true);
        popupWindow.setFocusable(false);
        popupWindow.showAtLocation(view1, Gravity.BOTTOM, 0,
                0);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setBackgroundAlpha(1.0f);
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try {
            if (data != null && data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS) != null) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                String pic_path = CompressionUtil.compressionBitmap(images.get(0).path);

                // 设置照片
                if (!pic_path.equals("")) {
                    //图片路径
                    file1 = new File(pic_path);
                    if (file1 != null && file1.isFile()) {
                        Glide.with(ShopSetUp.this).load(file1).error(R.mipmap.icon_idcard_front)
                                .placeholder(R.mipmap.icon_idcard_front).centerCrop().into(shopImage);
                    }

                }
            }
            popupWindow.dismiss();
        } catch (Exception e) {
            popupWindow.dismiss();
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
