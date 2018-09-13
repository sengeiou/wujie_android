package com.txd.hzj.wjlp.new_wjyp;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ants.theantsgo.WeApplication;
import com.ants.theantsgo.base.BaseActivity;
import com.ants.theantsgo.config.Config;
import com.ants.theantsgo.config.Settings;
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
import com.txd.hzj.wjlp.tool.TimeStampUtil;
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
 * 个人身份认证
 */
public class Personal_fgt extends BaseFgt {
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

    private int size;//设置图片比例
    /**
     * 身份证正反面
     */
    @ViewInject(R.id.im1)
    private ImageView im1;
    @ViewInject(R.id.im2)
    private ImageView im2;
    @ViewInject(R.id.image1)
    private ImageView image1;
    @ViewInject(R.id.image2)
    private ImageView image2;
    /**
     * 个人资料
     */
    @ViewInject(R.id.name)
    private EditText name;
    @ViewInject(R.id.idcard)
    private EditText idcard;
    private boolean is_card = true;
    @ViewInject(R.id.id_card_start_time)
    private TextView id_card_start_time;
    @ViewInject(R.id.id_card_end_time)
    private TextView id_card_end_time;
    @ViewInject(R.id.ads0)
    private TextView ads0;
    @ViewInject(R.id.ads1)
    private TextView ads1;

    private File file1;
    private File file2;
    private String start_time = "";
    private String end_time = "";

    /**
     * dialog控件
     */
    private Dialog dialog;
    private String sex = "";
    @ViewInject(R.id.sex)
    private TextView tv_Sex;
    private boolean is_Long = false;
    private TimePickerView pvTime;
    private boolean check;
    private boolean isFirst = true; // 是否是第一次申请，如果回传的值不为空则不是第一次提交

    @OnClick({R.id.layout_sex, R.id.ads0, R.id.ads1, R.id.image1, R.id.image2, R.id.id_card_start_time, R.id.id_card_end_time, R.id.tv_submit})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.layout_sex:
                if (check) {
                    show();
                }
                break;
            case R.id.tv_submit:

                if (ProUrbAreaUtil.gainInstance().getProvince_id() != null && !ProUrbAreaUtil.gainInstance().getProvince_id().isEmpty()) {
                    // 如果回传的id不为空并且字符串不为空，则将其赋值给上传的字段中，下同
                    province_id = ProUrbAreaUtil.gainInstance().getProvince_id();
                } else if (province_id == null || province_id.isEmpty()) {
                    showToast("请选择地址!");
                    return;
                }
                if (ProUrbAreaUtil.gainInstance().getProvince() != null && !ProUrbAreaUtil.gainInstance().getProvince().isEmpty()) {
                    province = ProUrbAreaUtil.gainInstance().getProvince();
                } else if (province == null || province.isEmpty()) {
                    showToast("请选择地址!");
                    return;
                }
                if (ProUrbAreaUtil.gainInstance().getCity_id() != null && !ProUrbAreaUtil.gainInstance().getCity_id().isEmpty()) {
                    city_id = ProUrbAreaUtil.gainInstance().getCity_id();
                } else if (city_id == null || city_id.isEmpty()) {
                    showToast("请选择地址!");
                    return;
                }
                if (ProUrbAreaUtil.gainInstance().getArea_id() != null && !ProUrbAreaUtil.gainInstance().getArea_id().isEmpty()) {
                    area_id = ProUrbAreaUtil.gainInstance().getArea_id();
                } else if (area_id == null || area_id.isEmpty()) {
                    showToast("请选择地址!");
                    return;
                }

//                start_time = id_card_start_time.getText().toString().trim();
                if (name.getText().toString().trim() == null || TextUtils.isEmpty(name.getText().toString().trim())) {
                    showToast("请输入真实姓名!");
                    return;
                }
                if ((sex == null || TextUtils.isEmpty(sex)) && isFirst) {
                    showToast("请选择性别！");
                    return;
                }
                if (idcard.getText().toString().trim() == null || TextUtils.isEmpty(idcard.getText().toString().trim())) {
                    showToast("请输入身份证号！");
                    return;
                }
                if ((start_time == null || TextUtils.isEmpty(start_time)) && isFirst) {
                    showToast("请选择身份开始时间");
                    return;
                }
                if ((end_time == null || TextUtils.isEmpty(end_time)) && isFirst) {
                    showToast("请选择身份结束时间");
                    return;
                }
                if ((province == null || TextUtils.isEmpty(province)) && isFirst) {
                    showToast("请选择所在地区!");
                    return;
                }
                if ((street_id == null || TextUtils.isEmpty(street_id)) && isFirst) {
                    showToast("请选择所在街道！");
                    return;
                }
                if (file1 == null && isFirst) {
                    showToast("请上传身份证正面照");
                    return;
                }
                if (file2 == null && isFirst) {
                    showToast("请上传身份证反面照！");
                    return;
                }

                if (file1 != null && file2 != null) { // 请求接口
                    try {
//  TimeStampUtil.getTimeFour(start_time)
                        User.personalAuth(this, name.getText().toString().trim(), sex, idcard.getText().toString().trim(),
                                start_time , end_time, province_id, city_id, area_id,
                                street_id, file1, file2);
                    } catch (Exception e) {
                        L.e("User.personalAuth is Exception:" + e.toString());
                        showErrorTip("上传参数出现异常");
                    } finally {
                        L.e("BaseView = " + this + "\nname = " + name.getText().toString().trim() + "\nsex = " + sex +
                                "\nidcard = " + idcard.getText().toString() + "\nstart_time = " + TimeStampUtil.getTimeFour(start_time) +
                                "\nend_time = " + end_time + "\nprovince_id = " + province_id + "\ncity_id = " + city_id +
                                "\narea_id = " + area_id + "\nstreet_id = " + street_id + "\nfile1 = " + file1 + "\nfile2 = " + file2);
                    }

                    showProgressDialog();
                }
                break;
            case R.id.ads0:
                if (check) {
                    ProUrbAreaUtil.gainInstance().showPickerView(ads0, "","","", (BaseActivity) getActivity(), null);
                }
                break;
            case R.id.ads1:
                if (check) {
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
            case R.id.image1:
                if (check) {
                    Intent i = new Intent(getActivity(), ImageGridActivity.class);
                    startActivityForResult(i, 101);
                }
                break;
            case R.id.image2:
                if (check) {
                    Intent in = new Intent(getActivity(), ImageGridActivity.class);
                    startActivityForResult(in, 102);
                }
                break;
            case R.id.id_card_start_time:
                if (check) {
                    is_card = true;
                    is_Long = true;
                    initTimePicker(v);
                }
                break;
            case R.id.id_card_end_time:
                if (check) {
                    is_card = false;
                    is_Long = false;
                    initTimePicker(v);
                }
                break;
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.layout_lh_f1;
    }

    private Map<String, String> data;

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        removeProgressDialog();
        data = JSONUtils.parseKeyAndValueToMap(jsonStr);
        data = JSONUtils.parseKeyAndValueToMap(data.get("data"));
        if (requestUrl.contains("User/personalAuthInfo")) { // 查询个人认证详情
            if (data.get("auth_status").equals("3")) {
                textview.setText("认证：" + data.get("auth_desc"));
                Glide.with(getActivity()).load(data.get("positive_id_card")).error(R.mipmap.icon_idcard_front)
                        .placeholder(R.mipmap.icon_idcard_front).diskCacheStrategy(DiskCacheStrategy.ALL).into(image1);
                Glide.with(getActivity()).load(data.get("back_id_card")).error(R.mipmap.icon_idcard_back)
                        .placeholder(R.mipmap.icon_idcard_back).diskCacheStrategy(DiskCacheStrategy.ALL).into(image2);
            } else {
                Glide.with(getActivity()).load(data.get("positive_id_card")).error(R.mipmap.icon_idcard_front)
                        .placeholder(R.mipmap.icon_idcard_front).diskCacheStrategy(DiskCacheStrategy.ALL).into(image1);
                Glide.with(getActivity()).load(data.get("back_id_card")).error(R.mipmap.icon_idcard_back)
                        .placeholder(R.mipmap.icon_idcard_back).diskCacheStrategy(DiskCacheStrategy.ALL).into(image2);
            }

            // 将获取到的图片缓存成文件
            final String positive_id_card = data.get("positive_id_card").toString();
            final String back_id_card = data.get("back_id_card").toString();
            if (!positive_id_card.isEmpty()) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        file1 = new File(GlideUtils.getGlideFilePath(getActivity(), positive_id_card));
                    }
                }).start();
            }
            if (!back_id_card.isEmpty()) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        file2 = new File(GlideUtils.getGlideFilePath(getActivity(), back_id_card));
                    }
                }).start();
            }

            isFirst = (TextUtils.isEmpty(data.get("positive_id_card")) && TextUtils.isEmpty(data.get("back_id_card"))) ? true : false;
            sex = data.get("sex");
            start_time = data.get("id_card_start_date");
            end_time = data.get("id_card_end_time");
            province = data.get("auth_province_name");
            street_id = data.get("auth_street_id");
            city_id = data.get("auth_city_id");
            area_id = data.get("auth_area_id");
            name.setText(data.get("real_name"));
            province_id = data.get("auth_province_id");
            tv_Sex.setText(data.get("sex").equals("1") ? "男" : "女");
            idcard.setText(data.get("id_card_num"));
            idcard.setTextColor(Color.BLACK);
            name.setTextColor(Color.BLACK);
            id_card_start_time.setText(data.get("id_card_start_date"));
            id_card_end_time.setText(data.get("id_card_end_date").equals("0") ? "永久" : data.get("id_card_end_date"));
            ads0.setText(data.get("auth_province_name") + data.get("auth_city_name") + data.get("auth_area_name"));
            ads1.setText(data.get("auth_street_name"));
        }
        if (requestUrl.equals(Config.BASE_URL + "User/personalAuth")) { // 个人认证
            // 设置提示框弹窗提示
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

    @Override
    public void onError(String requestUrl, Map<String, String> error) {
        super.onError(requestUrl, error);
        L.e("cccc" + error.get("message"));
    }

    @Override
    protected void initialized() {

        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());// 图片加载
        imagePicker.setCrop(false);// 不裁剪
        imagePicker.setMultiMode(false);// 单选
        imagePicker.setShowCamera(true);// 显示拍照按钮

        String data = application.getCityProvienceJson();
    }

    @ViewInject(R.id.textview)
    private TextView textview; // 一个提示
    @ViewInject(R.id.tv_submit)
    private TextView tv_submit;

    @Override
    protected void requestData() {
        if (!getArguments().getString("auth_status").equals("0")) {
            User.personalAuthInfo(this);
            showProgressDialog();
        }
        switch (getArguments().getString("auth_status")) {
            case "1": {
                name.setEnabled(false);
                idcard.setEnabled(false);
                textview.setText("认证：认证中");
                tv_submit.setVisibility(View.GONE);
                break;
            }
            case "2": {
                name.setEnabled(false);
                idcard.setEnabled(false);
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
        layoutParams.leftMargin = 5;
        layoutParams.rightMargin = 10;
        im2.setLayoutParams(layoutParams);
        image2.setLayoutParams(layoutParams);

    }

    @Override
    protected void immersionInit() {
    }

    /**
     * 显示dialog
     */
    public void show() {
        dialog = new Dialog(getActivity(), R.style.BottomDialog);
        View inflate = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_bottom_li, null);
        TextView takePhoto = inflate.findViewById(R.id.takePhoto);
        TextView choosePhoto = inflate.findViewById(R.id.choosePhoto);
        TextView cancel = inflate.findViewById(R.id.btn_cancel);
        takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_Sex.setText("男");
                sex = "1";
                dialog.dismiss();
            }
        });
        choosePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_Sex.setText("女");
                sex = "2";
                dialog.dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.setContentView(inflate);
        Window dialogWindow = dialog.getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.y = 20;
        lp.width = -1;
        dialogWindow.setAttributes(lp);
        dialog.show();
    }

    private void initTimePicker(View v) {
        pvTime = new TimePickerView.Builder(getActivity(), new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) { // 选中事件回调
                if (is_card) {
                    start_time = String.valueOf(date.getTime() / 1000);
                    id_card_start_time.setText(getTime(date));
                } else {
                    end_time = String.valueOf(date.getTime() / 1000);
                    id_card_end_time.setText(getTime(date));
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
                        id_card_end_time.setText("永久");
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
                                            .placeholder(R.mipmap.icon_idcard_front).override(size, size).centerCrop().into(image1);
                                }
                            }
                            break;
                        case 102: // 身份证反面照
                            if (!pic_path.equals("")) {
                                file2 = new File(pic_path);
                                if (file2 != null && file2.isFile()) {
                                    Glide.with(this).load(file2).error(R.mipmap.icon_idcard_back)
                                            .placeholder(R.mipmap.icon_idcard_back).override(size, size).centerCrop().into(image2);
                                }
                            }
                            break;
                    }
                }
            } catch (Exception e) {
                L.e("Personal_fgt onActivityResult is exception:" + e.toString());
                showErrorTip("图片选择出现异常");
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

}
