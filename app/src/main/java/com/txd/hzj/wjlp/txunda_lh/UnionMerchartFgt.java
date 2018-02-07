package com.txd.hzj.wjlp.txunda_lh;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ants.theantsgo.imageLoader.GlideImageLoader;
import com.ants.theantsgo.util.CompressionUtil;
import com.ants.theantsgo.util.JSONUtils;
import com.ants.theantsgo.util.L;
import com.bigkoo.pickerview.OptionsPickerView;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseFgt;
import com.txd.hzj.wjlp.bean.addres.CityForTxd;
import com.txd.hzj.wjlp.bean.addres.DistrictsForTxd;
import com.txd.hzj.wjlp.bean.addres.ProvinceForTxd;
import com.txd.hzj.wjlp.minetoAty.order.TextListAty;
import com.txd.hzj.wjlp.tool.GetJsonDataUtil;
import com.txd.hzj.wjlp.txunda_lh.http.Recommending;

import org.json.JSONArray;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;

/**
 * by Txunda_LH on 2018/1/22.
 */

public class UnionMerchartFgt extends BaseFgt {
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
    private String tx;
    private String street;

    @ViewInject(R.id.tv_submit)
    private TextView tv_submit;


    private boolean isC = true;


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
                    if (isLoaded) {
                        ShowPickerView();
                    }
                    break;
                case R.id.layout_street:
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
                    if (TextUtils.isEmpty(tx)) {
                        showToast("请选择城市");
                        return;
                    }
                    if (TextUtils.isEmpty(street)) {
                        showToast("请选择街道");
                        return;
                    }
                    if (TextUtils.isEmpty(desc.getText().toString())) {
                        showToast("请选择街道");
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
                    Recommending.addBusiness(mechant_name.getText().toString(), user_name.getText().toString(), user_position.getText().toString(),
                            user_phone.getText().toString(), tx, street, desc.getText().toString(), f1, f2, f3, "1", null, logo, rec_type_id, this);
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
        isC = (getArguments() == null ? true : false);
        if (isC) {
            mHandler.sendEmptyMessage(MSG_LOAD_DATA);
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

        layoutParams.leftMargin = 5;
        layoutParams.rightMargin = 10;
        image1.setLayoutParams(layoutParams);
        image2.setLayoutParams(layoutParams);
        image3.setLayoutParams(layoutParams);


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


    private ArrayList<ProvinceForTxd> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<CityForTxd>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<DistrictsForTxd>>> options3Items = new ArrayList<>();
    private Thread thread;
    private static final int MSG_LOAD_DATA = 0x0001;
    private static final int MSG_LOAD_SUCCESS = 0x0002;
    private static final int MSG_LOAD_FAILED = 0x0003;


    private void ShowPickerView() {// 弹出选择器
        OptionsPickerView pvOptions = new OptionsPickerView.Builder(getActivity(), new OptionsPickerView
                .OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                // 省
                province = options1Items.get(options1).getPickerViewText();
                province_id = options1Items.get(options1).getProvince_id();
                // 市
                city = options2Items.get(options1).get(options2).getPickerViewText();
                city_id = options2Items.get(options1).get(options2).getCity_id();
                // 区
                area = options3Items.get(options1).get(options2).get(options3).getPickerViewText();
                area_id = options3Items.get(options1).get(options2).get(options3).getDistrict_id();
                // 设置省市区
                tx = province + "，" + city + "，" + area;
                tv_city.setText(tx);

            }
        }).setTitleText("城市选择")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .setOutSideCancelable(false)// default is true
                .build();

        pvOptions.setPicker(options1Items, options2Items, options3Items);//三级选择器
        pvOptions.show();
    }

    private boolean isLoaded = false;
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_LOAD_DATA:
                    if (thread == null) {//如果已创建就不再重新创建子线程了
                        thread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                // 写子线程中的操作,解析省市区数据
                                initJsonData();
                            }
                        });
                        thread.start();
                    }
                    break;

                case MSG_LOAD_SUCCESS:
                    removeDialog();
                    isLoaded = true;
                    break;

                case MSG_LOAD_FAILED:
                    removeDialog();
                    showErrorTip("解析失败");
                    break;

            }
        }
    };

    private void initJsonData() {//解析数据

        /*
         * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
         * 关键逻辑在于循环体
         */
        String JsonData = new GetJsonDataUtil().getJson(getActivity(), "provinceFotTxd.json");//获取assets目录下的json文件数据
        ArrayList<ProvinceForTxd> jsonBean = parseData(JsonData);//用Gson 转成实体

        /*
         * 添加省份数据
         *
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        options1Items = jsonBean;

        for (int i = 0; i < jsonBean.size(); i++) {//遍历省份
            ArrayList<CityForTxd> CityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<DistrictsForTxd>> Province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）

            for (int c = 0; c < jsonBean.get(i).getCities().size(); c++) {//遍历该省份的所有城市

                CityList.add(jsonBean.get(i).getCities().get(c));//添加城市

                ArrayList<DistrictsForTxd> City_AreaList = new ArrayList<>();//该城市的所有地区列表
                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                if (jsonBean.get(i).getCities().get(c).getDistricts() == null
                        || jsonBean.get(i).getCities().get(c).getDistricts().size() == 0) {
                    City_AreaList.add(new DistrictsForTxd("", ""));
                } else {
                    for (int d = 0; d < jsonBean.get(i).getCities().get(c).getDistricts().size(); d++) {//该城市对应地区所有数据
                        DistrictsForTxd AreaName = jsonBean.get(i).getCities().get(c).getDistricts().get(d);
                        City_AreaList.add(AreaName);//添加该城市所有地区数据
                    }
                }
                Province_AreaList.add(City_AreaList);//添加该省所有地区数据
            }
            /*
             * 添加城市数据
             */
            options2Items.add(CityList);
            /*
             * 添加地区数据
             */
            options3Items.add(Province_AreaList);
        }

        mHandler.sendEmptyMessage(MSG_LOAD_SUCCESS);

    }


    public ArrayList<ProvinceForTxd> parseData(String result) {//Gson 解析
        ArrayList<ProvinceForTxd> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                ProvinceForTxd entity = gson.fromJson(data.optJSONObject(i).toString(), ProvinceForTxd.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            L.e("=====异常=====", e.getMessage());
            e.printStackTrace();
            mHandler.sendEmptyMessage(MSG_LOAD_FAILED);
        }
        return detail;
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
}
