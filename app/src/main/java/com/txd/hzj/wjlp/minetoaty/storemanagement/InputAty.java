package com.txd.hzj.wjlp.minetoaty.storemanagement;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
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

import com.ants.theantsgo.base.BaseView;
import com.ants.theantsgo.config.Config;
import com.ants.theantsgo.httpTools.ApiTool2;
import com.ants.theantsgo.imageLoader.GlideImageLoader;
import com.ants.theantsgo.util.CompressionUtil;
import com.ants.theantsgo.util.JSONUtils;
import com.bumptech.glide.Glide;
import com.lidroid.xutils.http.RequestParams;
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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;

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

    @ViewInject(R.id.attributesTv)
    private TextView attributesTv;

    @ViewInject(R.id.picImg)
    private ImageView picImg;

    @ViewInject(R.id.labelTv)
    private TextView labelTv;

    @ViewInject(R.id.takeawayTimeLayout)
    private LinearLayout takeawayTimeLayout;

    @ViewInject(R.id.takeawayTimeTv)
    private TextView takeawayTimeTv;

    @ViewInject(R.id.takeawayJieSuanLayout)
    private LinearLayout takeawayJieSuanLayout;

    @ViewInject(R.id.takeawayJieSuanPriceEdit)
    private TextView takeawayJieSuanPriceEdit;

    @ViewInject(R.id.dinnerJieSuanLayout)
    private LinearLayout dinnerJieSuanLayout;

    @ViewInject(R.id.dinnerJieSuanPriceEdit)
    private TextView dinnerJieSuanPriceEdit;

    @ViewInject(R.id.dinnerTimeLayout)
    private LinearLayout dinnerTimeLayout;

    @ViewInject(R.id.dinnerTimeTv)
    private TextView dinnerTimeTv;

    @ViewInject(R.id.briefEdit)
    private EditText briefEdit;
    private PopupWindow mPopupWindow;
    private File file1;

    private int mSaveType = 1;
    private String mGoods_id;
    private String mSta_mid;
    private String mSup_type;
    private String mCate_id;
    private String mGoods_attr;
    private String mGoods_property;
    private String mWeek_price;
    private String mChurch_week_price;
    private String mTime_price;
    private String mChurch_time_price;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_input;
    }

    @Override
    protected void initialized() {
        mContext = this;
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("录入");
        radioGroup.setOnCheckedChangeListener(mListener);
        forImagePacker();
        EventBus.getDefault().register(this);
        mGoods_id = getIntent().getStringExtra("goods_id");
        mSta_mid = getIntent().getStringExtra("sta_mid");
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
    protected void onResume() {
        super.onResume();
        if (mGoods_id != null) {
            goods_info(mGoods_id, this);
        }
    }

    void goods_info(String goods_id, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("goods_id", goods_id);
        apiTool2.postApi(Config.BASE_URL + "OsManager/goods_info", params, baseView);
    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        Map<String, String> map = JSONUtils.parseKeyAndValueToMap(jsonStr);
        if (requestUrl.endsWith("goods_info")) {
            Map<String, String> data = JSONUtils.parseKeyAndValueToMap(map.get("data"));
            int supType = Integer.parseInt(data.get("sup_type"));
            if (supType == 1) {
                takeawayBtn.setChecked(true);
                takeawayPriceEdit.setText(data.get("shop_price"));
                takeawayJieSuanPriceEdit.setText(data.get("shop_jiesuan_price"));
            } else if (supType == 2) {
                dinnerBtn.setChecked(true);
                dinnerPriceEdit.setText(data.get("church_shop_price"));
                dinnerJieSuanPriceEdit.setText(data.get("church_jiesuan_shop_price"));
            } else if (supType == 3) {
                allBtn.setChecked(true);
                takeawayPriceEdit.setText(data.get("shop_price"));
                takeawayJieSuanPriceEdit.setText(data.get("shop_jiesuan_price"));
                dinnerPriceEdit.setText(data.get("church_shop_price"));
                dinnerJieSuanPriceEdit.setText(data.get("church_jiesuan_shop_price"));
            }
            nameEdit.setText(data.get("name"));
            classifyTv.setText(data.get("cate_name"));
            lunchBoxNumEdit.setText(data.get("boxware"));
            multipleSpecificationsTv.setText(data.containsKey("attr_name") ? data.get("attr_name") : "");
            attributesTv.setText(data.containsKey("specs_name") ? data.get("specs_name") : "");
            if (data.containsKey("goods_pic")) {
                Glide.with(mContext).load(data.get("goods_pic")).into(picImg);
            }
            int label = Integer.parseInt(data.get("label"));
            if (label == 0) {
                labelTv.setText("");
            } else if (label == 1) {
                labelTv.setText("招牌");
            } else if (label == 2) {
                labelTv.setText("推荐");
            }

            briefEdit.setText(data.get("desc"));
            return;
        }

        if (requestUrl.endsWith("addAppStageGoods")) {
            showToast(map.get("message"));
            if ("1".equals(map.get("code"))) {
                if (mSaveType > 1) {
                    Bundle bundle = new Bundle();
                    bundle.putString("sta_mid", mSta_mid);
                    startActivity(InputAty.class, bundle);
                }

                finish();
            }
            return;
        }
    }

    @Override
    @OnClick({R.id.classifyTv, R.id.multipleSpecificationsLayout, R.id.attributesLayout, R.id.picImg, R.id.labelLayout, R.id.takeawayTimeTv, R.id.dinnerTimeTv, R.id.saveTv, R.id.saveAddTv})
    public void onClick(View v) {
        super.onClick(v);
        Bundle bundle = new Bundle();
        switch (v.getId()) {
            case R.id.classifyTv:
                bundle.putString("sta_mid",mSta_mid);
                bundle.putBoolean("isShowDelete",false);
                startActivity(ClassifyManageAty.class, bundle);
                break;
            case R.id.multipleSpecificationsLayout:
                bundle.putString("goods_id", mGoods_id);
                bundle.putString("goods_attr",mGoods_attr);
                bundle.putString("sta_mid", mSta_mid);
                startActivity(MultipleSpecificationsAty.class, bundle);
                break;
            case R.id.attributesLayout:
                bundle.putString("goods_id", mGoods_id);
                bundle.putString("goods_property", mGoods_property);
                startActivity(AttributesFirstAty.class, bundle);
                break;
            case R.id.picImg:
                showPicDialogs();
                break;
            case R.id.labelLayout:
                showLabelDialogs();
                break;
            case R.id.takeawayTimeTv:
                showTimeDialogs(1);
                break;
            case R.id.dinnerTimeTv:
                showTimeDialogs(2);
                break;
            case R.id.saveTv:
                mSaveType = 1;
                saveData();
                break;
            case R.id.saveAddTv:
                mSaveType = 2;
                saveData();
                break;

        }
    }

    private void saveData() {
        if (mSup_type == null) {
            showToast("请选择商品类型");
            return;
        }
        if (TextUtils.isEmpty(nameEdit.getText().toString())) {
            showToast("请输入商品名称");
            return;
        }
        if (mCate_id == null) {
            showToast("请选择商品分类");
            return;
        }
        if (mSup_type.equals("1") || mSup_type.equals("3")) {
            if (TextUtils.isEmpty(takeawayPriceEdit.getText().toString())) {
                showToast("请输入正确的外卖售卖价");
                return;
            }
            if (TextUtils.isEmpty(takeawayJieSuanPriceEdit.getText().toString())) {
                showToast("请输入正确的外卖结算价");
                return;
            }
        } else if (mSup_type.equals("2") || mSup_type.equals("3")) {
            if (TextUtils.isEmpty(dinnerPriceEdit.getText().toString())) {
                showToast("请输入正确的堂食售卖价");
                return;
            }
            if (TextUtils.isEmpty(dinnerJieSuanPriceEdit.getText().toString())) {
                showToast("请输入正确的堂食价");
                return;
            }
        }

        if (file1 == null) {
            showToast("请上传商品图片");
            return;
        }
        addAppStageGoods(mSta_mid, mSup_type, nameEdit.getText().toString(), mCate_id, lunchBoxNumEdit.getText().toString(), mGoods_attr, mGoods_property, takeawayPriceEdit.getText().toString()
                , dinnerPriceEdit.getText().toString(), file1, labelTv.getText().toString(), mWeek_price, mChurch_week_price, mTime_price, mChurch_time_price, briefEdit.getText().toString(), mGoods_id, takeawayJieSuanPriceEdit.getText().toString(), dinnerJieSuanPriceEdit.getText().toString(), this);
    }

    void addAppStageGoods(String sta_mid, String sup_type, String name, String cate_id, String boxware, String goods_attr, String goods_property, String shop_price, String church_shop_price, File pic, String label, String week_price, String church_week_price, String time_price, String church_time_price, String desc, String goods_id, String shop_jiesuan_price, String church_jiesuan_shop_price, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("sta_mid", sta_mid);
        params.addBodyParameter("sup_type", sup_type);
        params.addBodyParameter("name", name);
        params.addBodyParameter("cate_id", cate_id);
        params.addBodyParameter("boxware", boxware);
        params.addBodyParameter("goods_attr", goods_attr);
        params.addBodyParameter("goods_property", goods_property);
        params.addBodyParameter("shop_price", shop_price);
        params.addBodyParameter("church_shop_price", church_shop_price);
        params.addBodyParameter("pic", pic);
        params.addBodyParameter("label", label);
        params.addBodyParameter("week_price", week_price);
        params.addBodyParameter("church_week_price", church_week_price);
        params.addBodyParameter("time_price", time_price);
        params.addBodyParameter("church_time_price", church_time_price);
        params.addBodyParameter("desc", desc);
        params.addBodyParameter("goods_id", goods_id);
        params.addBodyParameter("shop_jiesuan_price", shop_jiesuan_price);
        params.addBodyParameter("church_jiesuan_shop_price", church_jiesuan_shop_price);
        apiTool2.postApi(Config.BASE_URL + "OsManager/addAppStageGoods", params, baseView);
    }

    private void showTimeDialogs(final int type) {
        View view = View.inflate(mContext, R.layout.dialog_pop, null);
        view.findViewById(R.id.dismissTv).setOnClickListener(new View.OnClickListener() {
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
        TextView tv1 = view1.findViewById(R.id.tv1);
        TextView tv2 = view1.findViewById(R.id.tv2);
        tv1.setText("不限时间");
        tv1.setTextColor(Color.parseColor("#fff23030"));
        tv2.setText("自定义");
        tv2.setTextColor(Color.parseColor("#ff333333"));
        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type == 1) {
                    takeawayTimeTv.setText("不限时间");
                } else {
                    dinnerTimeTv.setText("不限时间");
                }
                mPopupWindow.dismiss();
            }
        });
        tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type == 1) {
                    takeawayTimeTv.setText("自定义");
                } else {
                    dinnerTimeTv.setText("自定义");
                }
                mPopupWindow.dismiss();
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

    private void showLabelDialogs() {
        View view = View.inflate(mContext, R.layout.dialog_pop, null);
        view.findViewById(R.id.dismissTv).setOnClickListener(new View.OnClickListener() {
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
        TextView tv1 = view1.findViewById(R.id.tv1);
        TextView tv2 = view1.findViewById(R.id.tv2);
        tv1.setText("招牌");
        tv1.setTextColor(Color.parseColor("#ff333333"));
        tv2.setText("推荐");
        tv2.setTextColor(Color.parseColor("#ff333333"));
        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                labelTv.setText("招牌");
                mPopupWindow.dismiss();
            }
        });
        tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                labelTv.setText("推荐");
                mPopupWindow.dismiss();
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


    private void showPicDialogs() {
        View view = View.inflate(mContext, R.layout.dialog_pop, null);
        view.findViewById(R.id.dismissTv).setOnClickListener(new View.OnClickListener() {
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
        TextView setPicture = view1.findViewById(R.id.tv1);
        //相册
        TextView setAlbum = view1.findViewById(R.id.tv2);
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
            switch (checkedId) {
                case R.id.takeawayBtn:
                    mSup_type = "1";
                    takeawayLayout.setVisibility(View.VISIBLE);
                    takeawayJieSuanLayout.setVisibility(View.VISIBLE);
                    dinnerLayout.setVisibility(View.GONE);
                    dinnerJieSuanLayout.setVisibility(View.GONE);
                    takeawayTimeLayout.setVisibility(View.VISIBLE);
                    dinnerTimeLayout.setVisibility(View.GONE);
                    break;
                case R.id.dinnerBtn:
                    mSup_type = "2";
                    takeawayLayout.setVisibility(View.GONE);
                    takeawayJieSuanLayout.setVisibility(View.GONE);
                    dinnerJieSuanLayout.setVisibility(View.VISIBLE);
                    dinnerLayout.setVisibility(View.VISIBLE);
                    takeawayTimeLayout.setVisibility(View.GONE);
                    dinnerTimeLayout.setVisibility(View.VISIBLE);
                    break;
                case R.id.allBtn:
                    mSup_type = "3";
                    takeawayJieSuanLayout.setVisibility(View.VISIBLE);
                    dinnerJieSuanLayout.setVisibility(View.VISIBLE);
                    takeawayLayout.setVisibility(View.VISIBLE);
                    dinnerLayout.setVisibility(View.VISIBLE);
                    takeawayTimeLayout.setVisibility(View.VISIBLE);
                    dinnerTimeLayout.setVisibility(View.VISIBLE);
                    break;
            }
        }
    };

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void event(MessageEvent messageEvent) {
        String label = messageEvent.getLabel();
        String message = messageEvent.getMessage();
        if (label.equals("ClassifyManageAty")) {
            String[] split = message.split("-");
            classifyTv.setText(split[0]);
            mCate_id = split[1];
        }
        if (label.equals("MultipleSpecificationsAty")) {
            mGoods_attr = message;
            ArrayList<Map<String, String>> maps = JSONUtils.parseKeyAndValueToMapList(message);
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < maps.size(); i++) {
                builder.append(maps.get(i).get("name"));
                if (i != maps.size()-1){
                    builder.append("/");
                }
            }
            multipleSpecificationsTv.setText(builder);
        }
        if (label.equals("AttributesFirstAty")) {
            mGoods_property = message;
            try {
                StringBuilder builder = new StringBuilder();
                JSONArray jsonArray = new JSONArray(message);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject object = new JSONObject(String.valueOf(jsonArray.getJSONObject(i)));
                    builder.append(object.optString("title"));
                    if (i != jsonArray.length()-1){
                        builder.append("/");
                    }
                }
                attributesTv.setText(builder);
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 || requestCode == 102) {
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
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

}
