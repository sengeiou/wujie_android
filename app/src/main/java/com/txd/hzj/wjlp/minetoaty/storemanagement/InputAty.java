package com.txd.hzj.wjlp.minetoaty.storemanagement;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ants.theantsgo.imageLoader.GlideImageLoader;
import com.ants.theantsgo.util.CompressionUtil;
import com.bumptech.glide.Glide;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.tool.MessageEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.ArrayList;

/**
 * 创建者：zhangyunfei
 * 创建时间：2019/1/8 11:49
 * 功能描述：录入
 */
public class InputAty extends BaseAty {
    private Context mContext;

    @ViewInject(R.id.titlt_conter_tv)
    private TextView titlt_conter_tv;

    @ViewInject(R.id.radioGroup)
    private RadioGroup radioGroup;

    @ViewInject(R.id.takeawayBtn)
    private RadioButton takeawayBtn;

    @ViewInject(R.id.dinnerBtn)
    private RadioButton dinnerBtn;

    @ViewInject(R.id.allBtn)
    private RadioButton allBtn;

    @ViewInject(R.id.nameEdit)
    private EditText nameEdit;

    @ViewInject(R.id.classifyTv)
    private TextView classifyTv;

    @ViewInject(R.id.takeawayLayout)
    private LinearLayout takeawayLayout;

    @ViewInject(R.id.takeawayPriceEdit)
    private EditText takeawayPriceEdit;

    @ViewInject(R.id.dinnerLayout)
    private LinearLayout dinnerLayout;

    @ViewInject(R.id.dinnerPriceEdit)
    private EditText dinnerPriceEdit;

    @ViewInject(R.id.lunchBoxNumEdit)
    private EditText lunchBoxNumEdit;

    @ViewInject(R.id.multipleSpecificationsTv)
    private TextView multipleSpecificationsTv;

    @ViewInject(R.id.picImg)
    private ImageView picImg;

    @ViewInject(R.id.labelTv)
    private TextView labelTv;

    @ViewInject(R.id.takeawayTimeLayout)
    private LinearLayout takeawayTimeLayout;

    @ViewInject(R.id.takeawayTimeTv)
    private TextView takeawayTimeTv;

    @ViewInject(R.id.dinnerTimeLayout)
    private LinearLayout dinnerTimeLayout;

    @ViewInject(R.id.dinnerTimeTv)
    private TextView dinnerTimeTv;

    @ViewInject(R.id.briefEdit)
    private EditText briefEdit;
    private PopupWindow mPopupWindow;
    private File file1;

    public static  final int CLASSIFY_REQUESTCODE = 80;
    @Override
    protected int getLayoutResId() {
        return R.layout.activity_input;
    }

    @Override
    protected void initialized() {
        mContext = this;
        titlt_conter_tv.setText("录入");
        radioGroup.setOnCheckedChangeListener(mListener);
        forImagePacker();
        EventBus.getDefault().register(this);
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
    protected void requestData() {

    }


    @Override
    @OnClick({R.id.classifyTv, R.id.multipleSpecificationsLayout, R.id.attributesLayout,R.id.picImg, R.id.labelLayout, R.id.takeawayTimeTv,R.id.dinnerTimeTv, R.id.saveTv, R.id.saveAddTv})
    public void onClick(View v) {
        super.onClick(v);
        Bundle bundle = new Bundle();
        switch (v.getId()) {
            case R.id.classifyTv:
                bundle.putBoolean("isReturn",true);
//                startActivityForResult(ClassifyManageAty.class,bundle,CLASSIFY_REQUESTCODE);
                startActivity(ClassifyManageAty.class,bundle);
                break;
            case R.id.multipleSpecificationsLayout:
                break;
            case R.id.attributesLayout:
                break;
            case R.id.picImg:
                showDialogs();
                break;
            case R.id.labelLayout:
                break;
            case R.id.takeawayTimeTv:
                break;
            case R.id.dinnerTimeTv:
                break;
            case R.id.saveTv:
                break;
            case R.id.saveAddTv:
                break;

        }
    }


    private void showDialogs() {
        View view = View.inflate(mContext, R.layout.shop_dialog_popup, null);
        view.findViewById(R.id.shop_dialog_disimis).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopupWindow.dismiss();
            }
        });

        //跟布局
        RelativeLayout view1 = view.findViewById(R.id.shop_set_main);
        view1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPopupWindow != null && mPopupWindow.isShowing()) {
                    mPopupWindow.dismiss();
                    mPopupWindow = null;
                }

            }
        });
        //拍照
       TextView setPicture = view1.findViewById(R.id.shop_set_up_picture);
        //相册
        TextView setAlbum = view1.findViewById(R.id.shop_set_up_album);
        setPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ImageGridActivity.class);
                intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS, true); // 是否是直接打开相机
                startActivityForResult(intent, 100);
            }
        });
        setAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(mContext, ImageGridActivity.class);
                startActivityForResult(in, 102);
            }
        });
        mPopupWindow = new PopupWindow(view, RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        mPopupWindow.setAnimationStyle(R.style.BottomDialog_Animation);
        setBackgroundAlpha(0.5f);
        mPopupWindow.setOutsideTouchable(false);
        mPopupWindow.setTouchable(true);
        mPopupWindow.setFocusable(false);
        mPopupWindow.showAtLocation(view1, Gravity.BOTTOM, 0,
                0);
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setBackgroundAlpha(1.0f);
            }
        });



    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mPopupWindow != null && mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
            mPopupWindow = null;
        }
        return super.onTouchEvent(event);
    }

    public void setBackgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = (this).getWindow()
                .getAttributes();
        lp.alpha = bgAlpha;
        (this).getWindow().setAttributes(lp);
    }

    private RadioGroup.OnCheckedChangeListener mListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId){
                case R.id.takeawayBtn:
                    takeawayLayout.setVisibility(View.VISIBLE);
                    dinnerLayout.setVisibility(View.GONE);
                    takeawayTimeLayout.setVisibility(View.VISIBLE);
                    dinnerTimeLayout.setVisibility(View.GONE);
                    break;
                case R.id.dinnerBtn:
                    takeawayLayout.setVisibility(View.GONE);
                    dinnerLayout.setVisibility(View.VISIBLE);
                    takeawayTimeLayout.setVisibility(View.GONE);
                    dinnerTimeLayout.setVisibility(View.VISIBLE);
                    break;
                case R.id.allBtn:
                    takeawayLayout.setVisibility(View.VISIBLE);
                    dinnerLayout.setVisibility(View.VISIBLE);
                    takeawayTimeLayout.setVisibility(View.VISIBLE);
                    dinnerTimeLayout.setVisibility(View.VISIBLE);
                    break;
            }
        }
    };

    @Subscribe (threadMode = ThreadMode.MAIN)
    public void event(MessageEvent messageEvent){
        int code = messageEvent.getCode();
        if (code == 0){
            classifyTv.setText(messageEvent.getMessage());
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 || requestCode == 102){
            if (data != null && data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS) != null) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                String pic_path = CompressionUtil.compressionBitmap(images.get(0).path);

                // 设置照片
                if (!pic_path.equals("")) {
                    //图片路径
                    file1 = new File(pic_path);
                    if (file1 != null && file1.isFile()) {
                        Glide.with(mContext).load(file1).centerCrop().into(picImg);
                    }

                }
            }
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }
}
