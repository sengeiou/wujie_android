package com.txd.hzj.wjlp.new_wjyp;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ants.theantsgo.WeApplication;
import com.ants.theantsgo.base.BaseActivity;
import com.ants.theantsgo.imageLoader.GlideImageLoader;
import com.ants.theantsgo.tips.MikyouCommonDialog;
import com.ants.theantsgo.tool.ToolKit;
import com.ants.theantsgo.tool.glide.GlideUtils;
import com.ants.theantsgo.util.CompressionUtil;
import com.ants.theantsgo.util.JSONUtils;
import com.ants.theantsgo.util.L;
import com.bigkoo.pickerview.TimePickerView;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseFgt;
import com.txd.hzj.wjlp.http.User;
import com.txd.hzj.wjlp.minetoAty.order.TextListAty;
import com.txd.hzj.wjlp.tool.proUrbArea.ProUrbAreaUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import static android.app.Activity.RESULT_OK;

/**
 * 企业认证
 */
public class Company_fgt extends BaseFgt implements ProUrbAreaUtil.GetData {
    /**
     * 省
     */
    private String province = "";
    /**
     * 市
     */
    private String city = "";
    /**
     * 区
     */
    private String area = "";

    /**
     * 省id
     */
    private String province_id = "";
    /**
     * 市id
     */
    private String city_id = "";
    /**
     * 区id
     */
    private String area_id = "";

    /**
     * 街道id
     */
    private String street_id = "";


    @ViewInject(R.id.name)
    private EditText name;
    @ViewInject(R.id.num)
    private EditText num;
    @ViewInject(R.id.comp_start_time)
    private TextView comp_start_time;
    @ViewInject(R.id.comp_end_time)
    private TextView comp_end_time;
    @ViewInject(R.id.ads0)
    private TextView ads0;
    @ViewInject(R.id.ads1)
    private TextView ads1;
    private int size;
    @ViewInject(R.id.im1)
    private ImageView im1;
    @ViewInject(R.id.image1)
    private ImageView image1;
    private File file1;

    private TimePickerView pvTime;
    private boolean is_card;
    private boolean is_Long;
    private String start_time;
    private String end_time;
    private boolean check;
    private boolean isFirst = true;
    private boolean choiceAddress = false;

    @Override
    protected int getLayoutResId() {
        return R.layout.layout_lh_f2;
    }

    @OnClick({R.id.ads0, R.id.ads1, R.id.comp_start_time, R.id.comp_end_time, R.id.tv_submit, R.id.image1})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.ads0:
                if (check) {
                    ProUrbAreaUtil proUrbAreaUtil = ProUrbAreaUtil.gainInstance();
                    proUrbAreaUtil.showPickerView(ads0, "","","", (BaseActivity) getActivity(), null);
                }
                break;
            case R.id.ads1:
                if (check) {
                    province_id = ProUrbAreaUtil.gainInstance().getProvince_id();
                    city_id = ProUrbAreaUtil.gainInstance().getCity_id();
                    area_id = ProUrbAreaUtil.gainInstance().getArea_id();
                    if (area_id.equals("")) {
                        showErrorTip("请选择省市区");
                        return;
                    }
                    Intent intent = new Intent();
                    intent.setClass(getActivity(), TextListAty.class);
                    intent.putExtra("title", "选择街道");
                    intent.putExtra("area_id", area_id);
                    startActivityForResult(intent, 100);
                }
                break;
            case R.id.comp_start_time:
                if (check) {
                    is_card = true;
                    is_Long = true;
                    initTimePicker(view);
                }
                break;
            case R.id.comp_end_time:
                if (check) {
                    is_card = false;
                    is_Long = false;
                    initTimePicker(view);
                }
                break;
            case R.id.tv_submit:

                if (choiceAddress) {
                    province_id = ProUrbAreaUtil.gainInstance().getProvince_id();
                    city_id = ProUrbAreaUtil.gainInstance().getCity_id();
                    area_id = ProUrbAreaUtil.gainInstance().getArea_id();
                }

                if (TextUtils.isEmpty(name.getText().toString())) {
                    showToast("请输入企业名称！");
                    return;
                }
                if (TextUtils.isEmpty(num.getText().toString())) {
                    showToast("请输入注册号!");
                    return;
                }
                if (TextUtils.isEmpty(start_time)) {
                    showToast("请选择开始日期!");
                    return;
                }
                if (TextUtils.isEmpty(end_time)) {
                    showToast("请选择结束日期！");
                    return;
                }
                if (TextUtils.isEmpty(province_id)) {
                    showToast("请选择所在地区！");
                    return;
                }
                if (TextUtils.isEmpty(street_id)) {
                    showToast("请选择所在街道！");
                    return;
                }

                if (file1 == null && isFirst) {
                    showToast("请上传营业执照！");
                    return;
                }

                if (file1 != null) {
                    User.compAuth(this, name.getText().toString(), num.getText().toString(), start_time, end_time, province_id, city_id, area_id, street_id, file1);
                    showProgressDialog();
                }
                break;
            case R.id.image1:
                if (check) {
                    Intent i = new Intent(getActivity(), ImageGridActivity.class);
                    startActivityForResult(i, 101);
                }
                break;
        }
    }

    @Override
    protected void initialized() {

        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());// 图片加载
        imagePicker.setCrop(false);// 不裁剪
        imagePicker.setMultiMode(false);// 单选
        imagePicker.setShowCamera(true);// 显示拍照按钮

        String data = application.getCityProvienceJson();

        ProUrbAreaUtil.gainInstance().checkData((WeApplication) getActivity().getApplication());
        ProUrbAreaUtil.gainInstance().setGetData(this);

    }

    @ViewInject(R.id.textview)
    private TextView textview; // 一个提示
    @ViewInject(R.id.tv_submit)
    private TextView tv_submit;

    @Override
    protected void requestData() {
        if (!getArguments().getString("comp_auth_status").equals("0")) {
            User.personalAuthInfo(this);
            showProgressDialog();
        }
        switch (getArguments().getString("comp_auth_status")) {
            case "1": {
                name.setEnabled(false);
                num.setEnabled(false);
                textview.setText("认证：认证中");
                tv_submit.setVisibility(View.GONE);
                break;
            }
            case "2": {
                name.setEnabled(false);
                num.setEnabled(false);
                textview.setText("认证：已认证");
                tv_submit.setVisibility(View.GONE);
                break;
            }
            case "3": {
                check = true;
                tv_submit.setText("重新认证");
                break;
            }
            default:
                check = true;
                break;
        }
        ProUrbAreaUtil.gainInstance().checkData((WeApplication) getActivity().getApplication());
        size = ToolKit.getScreenWidth(getActivity());
        size = size / 2 - 15;
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(size, size);
        layoutParams.leftMargin = 10;
        layoutParams.rightMargin = 5;
        im1.setLayoutParams(layoutParams);
        image1.setLayoutParams(layoutParams);

    }

    @Override
    protected void immersionInit() {

    }

    Map<String, String> data;

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        removeProgressDialog();
        data = JSONUtils.parseKeyAndValueToMap(jsonStr);
        data = JSONUtils.parseKeyAndValueToMap(data.get("data"));
        if (requestUrl.contains("User/personalAuthInfo")) {
            if (data.get("comp_auth_status").equals("3")) {
                textview.setText("认证：已拒绝\n拒绝原因：" + data.get("comp_desc"));
                Glide.with(getActivity()).load(data.get("comp_business_license")).error(R.mipmap.icon_yyzz1).placeholder(R.mipmap.icon_yyzz1).diskCacheStrategy(DiskCacheStrategy.ALL).into(image1);
            } else {
                Glide.with(getActivity()).load(data.get("comp_business_license")).error(R.mipmap.icon_yyzz1).placeholder(R.mipmap.icon_yyzz1).diskCacheStrategy(DiskCacheStrategy.ALL).into(image1);
            }

            // 将获取到的图片缓存成文件
            final String comp_business_license = data.get("comp_business_license").toString();
            if (!comp_business_license.isEmpty()) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        file1 = new File(GlideUtils.getGlideFilePath(getActivity(), comp_business_license));
                    }
                }).start();
            }else {
                file1=null;
            }

            isFirst = TextUtils.isEmpty(data.get("comp_business_license")) ? true : false;
            start_time = data.get("comp_start_time");
            end_time = data.get("comp_end_time");
            province_id = data.get("comp_province_id");
            city_id = data.get("comp_city_id");
            area_id = data.get("comp_area_id");
            street_id = data.get("comp_street_id");
            name.setText(data.get("com_name"));
            num.setText(data.get("comp_reg_num"));
            comp_start_time.setText(data.get("comp_start_date"));
            comp_end_time.setText(data.get("comp_end_date").equals("0") ? "永久" : data.get("comp_end_date"));
            ads0.setText(data.get("comp_province_name") + data.get("comp_city_name") + data.get("comp_area_name"));
            ads1.setText(data.get("comp_street_name"));
            name.setTextColor(Color.BLACK);
            num.setTextColor(Color.BLACK);
        }
        if (requestUrl.contains("User/compAuth")) { // 企业认证
            // 弹窗提示要等待1~3个工作日，具体提示语句后台返回
            String showMsg = "";
            try {
                JSONObject jsonObject = new JSONObject(jsonStr);
                showMsg = jsonObject.getString("message");
            } catch (JSONException e) {
                L.e("User/personalAuth：回传Json字符串格式异常！");
                showMsg = "请耐心等待1-3个工作日";
            }
            new MikyouCommonDialog(getActivity(), showMsg, "温馨提示", "知道了", "", true)
                    .setOnDiaLogListener(new MikyouCommonDialog.OnDialogListener() {
                        @Override
                        public void dialogListener(int btnType, View customView, DialogInterface dialogInterface, int which) {
                            switch (btnType) {
                                case MikyouCommonDialog.OK: { // 确定按钮
                                    getActivity().finish();
                                }
                                break;
                            }
                        }
                    }).showDialog();
        }

    }

    private void initTimePicker(View v) {
        pvTime = new TimePickerView.Builder(getActivity(), new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                if (is_card) {
                    start_time = String.valueOf(date.getTime() / 1000);
                    comp_start_time.setText(getTime(date));
                } else {
                    end_time = String.valueOf(date.getTime() / 1000);
                    comp_end_time.setText(getTime(date));
                }
                //showToast(getTime(date));
            }
        }).setLayoutRes(R.layout.pickerview_custom_time, new CustomListener() {
            @Override
            public void customLayout(View v) {
                TextView tv_long = (TextView) v.findViewById(R.id.tv_long);
                TextView tvSubmit = (TextView) v.findViewById(R.id.tv_finish);
                TextView ivCancel = (TextView) v.findViewById(R.id.tv_cancel);
                if (is_Long) {
                    tv_long.setVisibility(View.GONE);
                } else {
                    tv_long.setVisibility(View.VISIBLE);
                }
                tvSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pvTime.returnData();
                        pvTime.dismiss();
                    }
                });
                ivCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pvTime.dismiss();
                    }
                });
                tv_long.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pvTime.dismiss();
                        is_Long = true;
                        end_time = String.valueOf("0");
                        comp_end_time.setText("永久");
                    }
                });
            }
        })
                .setType(new boolean[]{true, true, true, false, false, false})
                .setLabel("", "", "", "", "", "") //设置空字符串以隐藏单位提示   hide label
                .setDividerColor(Color.DKGRAY)
                .setContentSize(20)
                .setBackgroundId(0x00000000)
                .setOutSideCancelable(false)
                .build();
        pvTime.show(v, true);
    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                String pic_path = CompressionUtil.compressionBitmap(images.get(0).path);
                switch (requestCode) {
                    case 101:
                        file1 = new File(pic_path);
                        Glide.with(this).load(file1).error(R.mipmap.icon_yyzz1).placeholder(R.mipmap.icon_yyzz1).override(size, size).centerCrop().into(image1);
                        break;
                }
            }

        }

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 100:
                    /*街道*/
                    String street = data.getStringExtra("street");
                    street_id = data.getStringExtra("street_id");
                    ads1.setText(street);
                    break;

            }
        }
    }


    @Override
    public void getAddress() {
        ProUrbAreaUtil proUrbAreaUtil = ProUrbAreaUtil.gainInstance();
        ads0.setText(proUrbAreaUtil.getProvince() + "," + proUrbAreaUtil.getCity() + "," + proUrbAreaUtil.getArea());
        ads1.setText("");
        street_id = "";
    }
}
