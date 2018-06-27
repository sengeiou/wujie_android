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
import com.txd.hzj.wjlp.minetoAty.order.TextListAty;
import com.txd.hzj.wjlp.http.Recommending;
import com.txd.hzj.wjlp.tool.proUrbArea.ProUrbAreaUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;

public class WujiePostFgt extends BaseFgt implements ProUrbAreaUtil.GetData  {

    private int size = 0;
    @ViewInject(R.id.im1)
    private ImageView im1;
    @ViewInject(R.id.im2)
    private ImageView im2;
    @ViewInject(R.id.im3)
    private ImageView im3;
    @ViewInject(R.id.im4)
    private ImageView im4;
    @ViewInject(R.id.image1)
    private ImageView image1;
    @ViewInject(R.id.image2)
    private ImageView image2;
    @ViewInject(R.id.image3)
    private ImageView image3;

    @ViewInject(R.id.image4)
    private ImageView image4;
    @ViewInject(R.id.tv_type)
    private TextView tv_type;
    private String type;
    private String rec_type_id;
    @ViewInject(R.id.im_logo)
    private ImageView im_logo;
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
    private File f4;
    private String street;
    private boolean isC = true;

    @ViewInject(R.id.tv_submit)
    private TextView tv_submit;

    @OnClick({R.id.tv_type, R.id.layout_logo, R.id.image1, R.id.image2, R.id.image3, R.id.image4, R.id.layout_city, R.id.layout_street, R.id.tv_submit})
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
                case R.id.image4:
                    forImagePicker(1);
                    startActivityForResult(new Intent(getActivity(), ImageGridActivity.class), 107);
                    break;
                case R.id.layout_city:{
                    ProUrbAreaUtil.gainInstance().checkData((WeApplication) getActivity().getApplication());
                    ProUrbAreaUtil.gainInstance().setGetData(this);
                    ProUrbAreaUtil.gainInstance().showPickerView(tv_city, "","","", (BaseActivity) getActivity(), null);
                }
                    break;
                case R.id.layout_street:
//                    if (choiceAddress) {
                        area_id = ProUrbAreaUtil.gainInstance().getArea_id();
//                    }
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
                        showToast("请上传LOGO");
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
                    if (TextUtils.isEmpty(street)||TextUtils.isEmpty(tv_street.getText().toString())) {
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
                    if (f4 == null) {
                        showToast("请上传申请书");
                        return;
                    }
                    province = ProUrbAreaUtil.gainInstance().getProvince();
                    city = ProUrbAreaUtil.gainInstance().getCity();
                    area = ProUrbAreaUtil.gainInstance().getArea();
                    Recommending.addBusiness(mechant_name.getText().toString(), user_name.getText().toString(), user_position.getText().toString(),
                            user_phone.getText().toString(), province + city + area, street, desc.getText().toString(), f1, f2, f3, "2", f4, logo, rec_type_id, this);
                    showProgressDialog();
                    break;
            }
        }
    }


    @Override
    protected void immersionInit() {
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
    protected int getLayoutResId() {
        return R.layout.fgt_wujiepost;
    }

    @Override
    protected void initialized() {


    }


    @Override
    protected void requestData() {
        isC = (getArguments() == null ? true : false);
        if (isC) {
            ProUrbAreaUtil.gainInstance().checkData((WeApplication) getActivity().getApplication());
        } else {
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
        im4.setLayoutParams(layoutParams);
        layoutParams.leftMargin = 5;
        layoutParams.rightMargin = 10;
        image1.setLayoutParams(layoutParams);
        image2.setLayoutParams(layoutParams);
        image3.setLayoutParams(layoutParams);
        image4.setLayoutParams(layoutParams);
    }

    Map<String, String> map;

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        if (requestUrl.contains("addBusiness")) {
            showToast("提交成功！");
            getActivity().finish();
        }
        if (requestUrl.contains("businessInfo")) {
            map = JSONUtils.parseKeyAndValueToMap(jsonStr);
            map = JSONUtils.parseKeyAndValueToMap(map.get("data"));
            mechant_name.setText(map.get("merchant_name"));
            mechant_name.setEnabled(false);
            tv_type.setEnabled(false);
            user_name.setEnabled(false);
            user_position.setEnabled(false);
            user_phone.setEnabled(false);
            Glide.with(getActivity()).load(map.get("logo")).bitmapTransform(new RoundTransformation(getActivity(), 10)).into(im_logo);
            tv_type.setText(map.get("rec_type_id"));
            user_name.setText(map.get("user_name"));
            user_position.setText(map.get("user_position"));
            user_phone.setText(map.get("user_phone"));
            tv_city.setText(map.get("city"));
            tv_city.setEnabled(false);
            tv_street.setEnabled(false);
            desc.setEnabled(false);
            tv_street.setText(map.get("street"));
            desc.setText(map.get("desc"));
            Glide.with(getActivity()).load(map.get("license")).into(image1);
            Glide.with(getActivity()).load(map.get("facade")).into(image2);
            Glide.with(getActivity()).load(map.get("identity")).into(image3);
            Glide.with(getActivity()).load(map.get("apply")).into(image4);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker
                        .EXTRA_RESULT_ITEMS);
                switch (requestCode) {
                    case 102:
                        String pic_path = CompressionUtil.compressionBitmap(images.get(0).path);
                        logo = new File(pic_path);
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
                    case 107:
                        String pic4 = CompressionUtil.compressionBitmap(images.get(0).path);
                        f4 = new File(pic4);
                        Glide.with(this).load(pic4).into(image4);
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
    @Override
    public void getAddress() {
        tv_street.setText("");
    }
}
