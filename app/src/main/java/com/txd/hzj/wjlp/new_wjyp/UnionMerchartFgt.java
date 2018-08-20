package com.txd.hzj.wjlp.new_wjyp;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ants.theantsgo.WeApplication;
import com.ants.theantsgo.base.BaseActivity;
import com.ants.theantsgo.imageLoader.GlideImageLoader;
import com.ants.theantsgo.util.CompressionUtil;
import com.ants.theantsgo.util.JSONUtils;
import com.bumptech.glide.Glide;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseFgt;
import com.txd.hzj.wjlp.http.Recommending;
import com.txd.hzj.wjlp.minetoAty.order.TextListAty;
import com.txd.hzj.wjlp.tool.proUrbArea.ProUrbAreaUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;

/**
 * 联盟商家界面
 */
public class UnionMerchartFgt extends BaseFgt implements ProUrbAreaUtil.GetData {
    private int size = 0;
    @ViewInject(R.id.im1)
    private ImageView im1;
    @ViewInject(R.id.im2)
    private ImageView im2;
    @ViewInject(R.id.im3)
    private ImageView im3;
    @ViewInject(R.id.image1)
    private ImageView image1;
    @ViewInject(R.id.image2)
    private ImageView image2;
    @ViewInject(R.id.image3)
    private ImageView image3;
    @ViewInject(R.id.tv_type)
    private TextView tv_type;
    private String type;
    private String rec_type_id;
    @ViewInject(R.id.im_logo)
    private ImageView im_logo;
    @ViewInject(R.id.hint_tv)
    private TextView hint_tv;
    private File logo;
    @ViewInject(R.id.mechant_name)
    private EditText mechant_name;
    @ViewInject(R.id.user_name)
    private EditText user_name;
    @ViewInject(R.id.user_position)
    private EditText user_position;
    @ViewInject(R.id.user_phone)
    private EditText user_phone;
    @ViewInject(R.id.desc)
    private EditText desc;

    @ViewInject(R.id.tv_city)
    private TextView tv_city;
    @ViewInject(R.id.tv_street)
    private TextView tv_street;
    private File f1;
    private File f2;
    private File f3;
    private String street;

    @ViewInject(R.id.textview)
    private TextView showMessageTv; // 显示提示消息的那个
    @ViewInject(R.id.tv_submit)
    private TextView tv_submit;

    private boolean isC = true;

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
     * 区id
     */
    private String area_id = "";
    private String street_id = "";

    @OnClick({R.id.tv_type, R.id.layout_logo, R.id.image1, R.id.image2, R.id.image3, R.id.layout_city, R.id.layout_street, R.id.tv_submit})
    public void OnClick(View view) {
        if (isC) {
            switch (view.getId()) {
                case R.id.layout_logo:
                    forImagePicker(1);
                    startActivityForResult(new Intent(getActivity(), ImageGridActivity.class), 102);
                    break;
                case R.id.tv_type:
                    Intent intent = new Intent();
                    intent.setClass(getActivity(), TextListAty.class);
                    intent.putExtra("title", "选择类型");
                    startActivityForResult(intent, 100);
                    break;
                case R.id.image1:
                    forImagePicker(1);
                    startActivityForResult(new Intent(getActivity(), ImageGridActivity.class), 103);
                    break;
                case R.id.image2:
                    forImagePicker(1);
                    startActivityForResult(new Intent(getActivity(), ImageGridActivity.class), 104);
                    break;
                case R.id.image3:
                    forImagePicker(1);
                    startActivityForResult(new Intent(getActivity(), ImageGridActivity.class), 105);
                    break;
                case R.id.layout_city:
                    ProUrbAreaUtil.gainInstance().checkData((WeApplication) getActivity().getApplication());
                    ProUrbAreaUtil.gainInstance().setGetData(this);
                    ProUrbAreaUtil.gainInstance().showPickerView(tv_city, "", "", "", (BaseActivity) getActivity(), null);
                    break;
                case R.id.layout_street:
                    if (choiceAddress && area_id.equals("")) {
                        area_id = ProUrbAreaUtil.gainInstance().getArea_id();
                    }
                    if (TextUtils.isEmpty(area_id)) {
                        showToast("请选择省市区");
                        return;
                    }
                    Intent i = new Intent();
                    i.setClass(getActivity(), TextListAty.class);
                    i.putExtra("title", "选择街道");
                    i.putExtra("area_id", area_id);
                    startActivityForResult(i, 106);
                    break;
                case R.id.tv_submit:
                    if (TextUtils.isEmpty(mechant_name.getText().toString())) {
                        showToast("请输入商家名称");
                        return;
                    }
                    if (logo == null) {
                        showToast("请上传门头");
                        return;
                    }
                    if (TextUtils.isEmpty(tv_type.getText().toString())) {
                        showToast("请选择类别");
                        return;
                    }
                    if (TextUtils.isEmpty(user_name.getText().toString())) {
                        showToast("请输入联系人");
                        return;
                    }
                    if (TextUtils.isEmpty(user_position.getText().toString())) {
                        showToast("请输入职位");
                        return;
                    }
                    if (TextUtils.isEmpty(user_phone.getText().toString())) {
                        showToast("请输入联系电话");
                        return;
                    }
                    if (TextUtils.isEmpty(tv_city.getText().toString())) {
                        showToast("请选择城市");
                        return;
                    }
                    if (TextUtils.isEmpty(street) || TextUtils.isEmpty(tv_street.getText().toString())) {
                        showToast("请选择街道");
                        return;
                    }
                    if (TextUtils.isEmpty(desc.getText().toString())) {
                        showToast("请填写申请店铺的情况");
                        return;
                    }
                    if (f1 == null) {
                        showToast("请上传营业执照");
                        return;
                    }
                    if (f2 == null) {
                        showToast("请上传身份证正面");
                        return;
                    }
                    if (f3 == null) {
                        showToast("请上传身份证反面");
                        return;
                    }
                    ProUrbAreaUtil proUrbAreaUtil = ProUrbAreaUtil.gainInstance();
                    if (choiceAddress) {
                        province = proUrbAreaUtil.getProvince();
                        city = proUrbAreaUtil.getCity();
                        area = proUrbAreaUtil.getArea();
                    }
                    Recommending.addBusiness(mechant_name.getText().toString(), user_name.getText().toString(), user_position.getText().toString(),
                            user_phone.getText().toString(), province + city + area, street, desc.getText().toString(), f1, f2, f3, "1", null, logo, rec_type_id, this);
                    showProgressDialog();
                    break;
            }
        }
    }

    @Override
    protected void immersionInit() {
    }

    Map<String, String> map;

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        if (requestUrl.contains("businessInfo")) {
            map = JSONUtils.parseKeyAndValueToMap(jsonStr);
            map = JSONUtils.parseKeyAndValueToMap(map.get("data"));
            mechant_name.setText(map.get("merchant_name"));
            mechant_name.setEnabled(false);
            tv_type.setEnabled(false);
            user_name.setEnabled(false);
            user_position.setEnabled(false);
            user_phone.setEnabled(false);
            hint_tv.setVisibility(View.GONE);
            im_logo.setVisibility(View.VISIBLE);
            Glide.with(getActivity()).load(map.get("logo")).bitmapTransform(new RoundTransformation(getActivity(), 10)).into(im_logo);
            tv_type.setText(map.get("rec_type_id"));
            rec_type_id = map.get("rec_type_id");
            user_name.setText(map.get("user_name"));
            user_position.setText(map.get("user_position"));
            user_phone.setText(map.get("user_phone"));
            tv_city.setText(map.get("city"));
            tv_city.setEnabled(false);
            tv_street.setEnabled(false);
            desc.setEnabled(false);
            tv_street.setText(map.get("street"));
            desc.setText(map.containsKey("description") ? map.get("description") : "");
//            desc.setText(map.get("desc"));
            // 检查是否存在状态字段，如果存在则直接提取状态
            if (map.containsKey("status")) {
                String status = map.get("status");
                // 如果状态码不为空并且是2、3、5则显示拒绝理由
                if (!status.isEmpty() && (status.equals("2") || status.equals("3") || status.equals("5"))) {
                    showMessageTv.setVisibility(View.VISIBLE);
                    showMessageTv.setText(map.containsKey("reason") ? map.get("reason") : "");

//                    // 申请遭拒绝之后
//                    isC = true;
//                    mechant_name.setEnabled(true);
//                    tv_type.setEnabled(true);
//                    user_name.setEnabled(true);
//                    user_position.setEnabled(true);
//                    user_phone.setEnabled(true);
//                    tv_city.setEnabled(true);
//                    tv_street.setEnabled(true);
//                    desc.setEnabled(true);
//                    tv_submit.setVisibility(View.VISIBLE);
                }
            }
            Glide.with(getActivity()).load(map.get("license")).into(image1);
            Glide.with(getActivity()).load(map.get("facade")).into(image2);
            Glide.with(getActivity()).load(map.get("identity")).into(image3);
        } else {
            showToast("提交成功！");
            getActivity().finish();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                switch (requestCode) {
                    case 102:
                        String pic_path = CompressionUtil.compressionBitmap(images.get(0).path);
                        logo = new File(pic_path);
                        hint_tv.setVisibility(View.GONE);
                        im_logo.setVisibility(View.VISIBLE);
                        Glide.with(this).load(pic_path).into(im_logo);
                        break;
                    case 103:
                        String pic1 = CompressionUtil.compressionBitmap(images.get(0).path);
                        f1 = new File(pic1);
                        Glide.with(this).load(pic1).into(image1);
                        break;
                    case 104:
                        String pic2 = CompressionUtil.compressionBitmap(images.get(0).path);
                        f2 = new File(pic2);
                        Glide.with(this).load(pic2).into(image2);
                        break;
                    case 105:
                        String pic3 = CompressionUtil.compressionBitmap(images.get(0).path);
                        f3 = new File(pic3);
                        Glide.with(this).load(pic3).into(image3);
                        break;

                }
            }
        } else {
            if (resultCode == getActivity().RESULT_OK) {
                if (requestCode == 100) {
                    type = data.getStringExtra("type");
                    rec_type_id = data.getStringExtra("rec_type_id");
                    tv_type.setText(type);
                } else {
                    street = data.getStringExtra("street");
                    street_id = data.getStringExtra("street_id");
                    tv_street.setText(street);
                }
            }
        }

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fgt_unionmerchart;
    }

    @Override
    protected void initialized() {
    }

    @Override
    protected void requestData() {
        province = ""; // 省
        city = ""; // 市
        area = ""; // 区
        area_id = ""; // 区ID
        street_id = ""; // 街道ID
        ProUrbAreaUtil.gainInstance().checkData((WeApplication) getActivity().getApplication());
        isC = (getArguments() == null ? true : false);
        if (!isC) {
            tv_submit.setVisibility(View.GONE);
            Recommending.businessInfo(getArguments().getString("id"), this);
            showProgressDialog();
        }
        size = getResources().getDisplayMetrics().widthPixels;
        size = size / 2 - 15;
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(size, size);
        layoutParams.leftMargin = 10;
        layoutParams.rightMargin = 5;
        im1.setLayoutParams(layoutParams);
        im2.setLayoutParams(layoutParams);
        im3.setLayoutParams(layoutParams);

        layoutParams.leftMargin = 5;
        layoutParams.rightMargin = 10;
        image1.setLayoutParams(layoutParams);
        image2.setLayoutParams(layoutParams);
        image3.setLayoutParams(layoutParams);


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

    boolean choiceAddress = false;

    @Override
    public void getAddress() {
        // 如果选择了地址
        choiceAddress = true;
        ProUrbAreaUtil proUrbAreaUtil = ProUrbAreaUtil.gainInstance();
        province = proUrbAreaUtil.getProvince();
        city = proUrbAreaUtil.getCity();
        area = proUrbAreaUtil.getArea();
        area_id = proUrbAreaUtil.getArea_id();
        tv_city.setText(proUrbAreaUtil.getProvince() + "," + proUrbAreaUtil.getCity() + "," + proUrbAreaUtil.getArea());
        tv_street.setText("");
        street_id = "";
    }

    @Override
    public void onStart() {
        super.onStart();
        province = province.equals("") ? "" : province;
        city = city.equals("") ? "" : city;
        area = area.equals("") ? "" : area;
        area_id = area_id.equals("") ? "" : area_id;
        street_id = street_id.equals("") ? "" : street_id;
    }
}
