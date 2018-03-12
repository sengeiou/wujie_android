package com.txd.hzj.wjlp.new_wjyp;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
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

import com.ants.theantsgo.config.Config;
import com.ants.theantsgo.config.Settings;
import com.ants.theantsgo.httpTools.ApiTool2;
import com.ants.theantsgo.imageLoader.GlideImageLoader;
import com.ants.theantsgo.tool.ToolKit;
import com.ants.theantsgo.util.CompressionUtil;
import com.ants.theantsgo.util.JSONUtils;
import com.ants.theantsgo.util.L;
import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.tamic.novate.Novate;
import com.tamic.novate.Throwable;
import com.tamic.novate.callback.RxStringCallback;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseFgt;
import com.txd.hzj.wjlp.bean.addres.CityForTxd;
import com.txd.hzj.wjlp.bean.addres.DistrictsForTxd;
import com.txd.hzj.wjlp.bean.addres.ProvinceForTxd;
import com.txd.hzj.wjlp.cityselect1.ac.activity.SortAdapter;
import com.txd.hzj.wjlp.cityselect1.ac.activity.SortCityActivity;
import com.txd.hzj.wjlp.cityselect1.ac.utils.PinyinComparator;
import com.txd.hzj.wjlp.minetoAty.order.TextListAty;
import com.txd.hzj.wjlp.tool.GetJsonDataUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static android.app.Activity.RESULT_OK;

public class fragment1 extends BaseFgt {
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

    private File flie1;
    private File flie2;
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
    private boolean isFirst=true;

    @OnClick({R.id.layout_sex, R.id.ads0, R.id.ads1, R.id.image1, R.id.image2, R.id.id_card_start_time, R.id.id_card_end_time, R.id.tv_submit})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.layout_sex:
                if (check) {
                    show();
                }
                break;
            case R.id.tv_submit:
                if (TextUtils.isEmpty(name.getText().toString())) {
                    showToast("请输入真实姓名!");
                    return;
                }
                if (TextUtils.isEmpty(sex)&&isFirst) {
                    showToast("请选择性别！");
                    return;
                }
                if (TextUtils.isEmpty(idcard.getText().toString())) {
                    showToast("请输入身份证号！");
                    return;
                }
                if (TextUtils.isEmpty(start_time)&&isFirst) {
                    showToast("请选择身份开始时间");
                    return;
                }
                if (TextUtils.isEmpty(end_time)&&isFirst) {
                    showToast("请选择身份结束时间");
                    return;
                }

                if (TextUtils.isEmpty(province)&&isFirst) {
                    showToast("请选择所在地区!");
                    return;
                }
                if (TextUtils.isEmpty(street_id)&&isFirst) {
                    showToast("请选择所在街道！");
                    return;
                }
                if (flie1 == null&&isFirst) {
                    showToast("请上传身份证正面照");
                    return;
                }
                if (flie2 == null&&isFirst) {
                    showToast("请上传身份证反面照！");
                    return;
                }
                RequestParams params = new RequestParams();
                ApiTool2 apiTool2 = new ApiTool2();
                L.e("cccc"+start_time+"--"+end_time);
                params.addBodyParameter("real_name", name.getText().toString());
                params.addBodyParameter("sex", sex);
                params.addBodyParameter("id_card_num", idcard.getText().toString());
                params.addBodyParameter("id_card_start_time", start_time);
                params.addBodyParameter("id_card_end_time", end_time);
                params.addBodyParameter("auth_province_id", province_id);
                params.addBodyParameter("auth_city_id", city_id);
                params.addBodyParameter("auth_area_id", area_id);
                params.addBodyParameter("auth_street_id", street_id);
                if(isFirst){
                    params.addBodyParameter("positive_id_card", flie1);
                    params.addBodyParameter("back_id_card", flie2);
                }
                apiTool2.postApi(Config.BASE_URL + "User/personalAuth", params, this);
                showProgressDialog();
                break;
            case R.id.ads0:
                if (isLoaded && check) {
                    ShowPickerView();
                }
                break;
            case R.id.ads1:
                if (check) {
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
                    removeProgressDialog();
                    isLoaded = true;
                    break;
                case MSG_LOAD_FAILED:
                    removeProgressDialog();
                    showErrorTip("解析失败");
                    break;

            }
        }
    };


    private Map<String, String> data;

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        data = JSONUtils.parseKeyAndValueToMap(jsonStr);
        data = JSONUtils.parseKeyAndValueToMap(data.get("data"));
        if (requestUrl.contains("User/personalAuthInfo")) {
            L.e("cccc"+jsonStr);
            if (data.get("auth_status").equals("3")) {
                textview.setText("认证：" + data.get("auth_desc"));
                Glide.with(getActivity()).load(data.get("positive_id_card")).into(image1);
                Glide.with(getActivity()).load(data.get("back_id_card")).into(image2);
            } else {
                Glide.with(getActivity()).load(data.get("positive_id_card")).into(image1);
                Glide.with(getActivity()).load(data.get("back_id_card")).into(image2);
            }
            isFirst=(TextUtils.isEmpty(data.get("positive_id_card"))&&TextUtils.isEmpty(data.get("back_id_card")))?true:false;
            sex = data.get("sex");
//            start_time=Integer.parseInt(data.get("id_card_start_date"))/1000+"";
            start_time=data.get("id_card_start_date");
            end_time=data.get("id_card_end_time");
//            end_time=Integer.parseInt(data.get("id_card_end_time"))/1000+"";
            province=data.get("auth_province_name");
            street_id=data.get("auth_street_id");
            city_id=data.get("auth_city_id");
            area_id=data.get("auth_area_id");
            name.setText(data.get("real_name"));
            province_id=data.get("auth_province_id");
            tv_Sex.setText(data.get("sex").equals("1") ? "男" : "女");
            idcard.setText(data.get("id_card_num"));
            idcard.setTextColor(Color.BLACK);
            name.setTextColor(Color.BLACK);
            id_card_start_time.setText(data.get("id_card_start_date"));
            id_card_end_time.setText(data.get("id_card_end_date").equals("0") ? "永久" : data.get("id_card_end_date"));
            ads0.setText(data.get("auth_province_name") + data.get("auth_city_name") + data.get("auth_area_name"));
            ads1.setText(data.get("auth_street_name"));

        }
        if (requestUrl.equals(Config.BASE_URL + "User/personalAuth")) {
            showToast("成功！");
            getActivity().finish();
        }
    }

    @Override
    public void onError(String requestUrl, Map<String, String> error) {
        super.onError(requestUrl, error);
        L.e("cccc"+error.get("message"));
    }

    private void download() {

        Map<String, Object> parameters = new HashMap<String, Object>();

        parameters.put("token", Config.getToken());
        new Novate.Builder(getActivity())
                .baseUrl(Config.BASE_URL)
                .addHeader(parameters)
                .build()
                .rxPost("User/personalAuthInfo", null, new RxStringCallback() {


                    @Override
                    public void onNext(Object tag, String response) {
//                        Toast.makeText(SortCityActivity.this, response, Toast.LENGTH_SHORT).show();
                        name.setText(data.get("real_name"));
                        tv_Sex.setText(data.get("sex").equals("1") ? "男" : "女");
                        idcard.setText(data.get("id_card_num"));
                        id_card_start_time.setText(data.get("id_card_start_date"));
                        id_card_end_time.setText(data.get("id_card_end_date").equals("0") ? "永久" : data.get("id_card_end_date"));
                        ads0.setText(data.get("auth_province_name") + data.get("auth_city_name") + data.get("auth_area_name"));
                        ads1.setText(data.get("auth_street_name"));
                        Glide.with(getActivity()).load(data.get("positive_id_card")).into(image1);
                        Glide.with(getActivity()).load(data.get("back_id_card")).into(image2);
                    }

                    @Override
                    public void onError(Object tag, Throwable e) {

                    }

                    @Override
                    public void onCancel(Object tag, Throwable e) {

                    }

                });

    }





    @Override
    protected void initialized() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());// 图片加载
        imagePicker.setCrop(true);// 裁剪
        imagePicker.setSaveRectangle(true);// 矩形保存
        imagePicker.setFocusWidth(Settings.displayWidth);//裁剪框宽度
        imagePicker.setFocusHeight(Settings.displayWidth);// 裁剪框高度
        imagePicker.setOutPutX(Settings.displayWidth);// 保存图片高度
        imagePicker.setOutPutY(Settings.displayWidth);// 保存图片宽度
        imagePicker.setMultiMode(false);// 但须
        imagePicker.setShowCamera(true);// 显示拍照按钮
    }

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
                String tx = province + city + area;
                ads0.setText(tx);
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

    @ViewInject(R.id.textview)
    private TextView textview;//一个提示
    @ViewInject(R.id.tv_submit)
    private TextView tv_submit;

    @Override
    protected void requestData() {
        download();
        if (!getArguments().getString("auth_status").equals("0")) {
            ApiTool2 apiTool2 = new ApiTool2();
            apiTool2.postApi(Config.BASE_URL + "User/personalAuthInfo", new RequestParams(), this);
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

        mHandler.sendEmptyMessage(MSG_LOAD_DATA);
        showProgressDialog();
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

    private void initTimePicker(View v) {
        pvTime = new TimePickerView.Builder(getActivity(), new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
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
            e.printStackTrace();
            mHandler.sendEmptyMessage(MSG_LOAD_FAILED);
        }
        return detail;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(
                        ImagePicker.EXTRA_RESULT_ITEMS);
                String pic_path = CompressionUtil.compressionBitmap(images.get(0).path);
                switch (requestCode) {
                    case 101:
                        flie1 = new File(pic_path);
                        Glide.with(this).load(flie1).override(size, size).centerCrop().into(image1);
                        break;
                    case 102:
                        flie2 = new File(pic_path);
                        Glide.with(this).load(flie2).override(size, size).centerCrop().into(image2);
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

}
