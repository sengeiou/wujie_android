package com.txd.hzj.wjlp.minetoAty.setting;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.ants.theantsgo.config.Settings;
import com.ants.theantsgo.gson.GsonUtil;
import com.ants.theantsgo.imageLoader.GlideImageLoader;
import com.ants.theantsgo.tool.ToolKit;
import com.ants.theantsgo.util.CompressionUtil;
import com.ants.theantsgo.util.L;
import com.bigkoo.pickerview.OptionsPickerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.bean.addres.CityForTxd;
import com.txd.hzj.wjlp.bean.addres.DistrictsForTxd;
import com.txd.hzj.wjlp.bean.addres.ProvinceForTxd;
import com.txd.hzj.wjlp.http.user.UserPst;
import com.txd.hzj.wjlp.minetoAty.order.TextListAty;
import com.txd.hzj.wjlp.tool.GetJsonDataUtil;
import com.txd.hzj.wjlp.view.flowlayout.ClearEditText;

import org.json.JSONArray;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;

import cn.gavinliu.android.lib.shapedimageview.ShapedImageView;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/26 0026
 * 时间：下午 4:19
 * 描述：个人信息
 * ===============Txunda===============
 */
public class EditProfileAty extends BaseAty implements View.OnClickListener {
    /**
     * 标题
     */
    @ViewInject(R.id.titlt_conter_tv)
    public TextView titlt_conter_tv;
    /**
     * 修改后保存
     */
    @ViewInject(R.id.titlt_right_tv)
    public TextView titlt_right_tv;
    /**
     * 性别
     */
    @ViewInject(R.id.tv_Sex)
    public TextView tv_Sex;
    /**
     * dialog控件
     */
    private Dialog dialog;
    /**
     * 头像
     */
    @ViewInject(R.id.img_head_edit)
    private ShapedImageView img_head_edit;

    private int size = 0;
    private File file;


    /**
     * 街道选择
     */
    @ViewInject(R.id.user_select_street_tv)
    private TextView user_select_street_tv;
    /**
     * 省市区区域选择
     */
    @ViewInject(R.id.user_select_zoon_tv)
    private TextView user_select_zoon_tv;

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

    private UserPst userPst;

    /**
     * 用户id
     */
    @ViewInject(R.id.user_id_tv)
    private TextView user_id_tv;
    /**
     * 用户昵称
     */
    @ViewInject(R.id.user_nickname_tv)
    private ClearEditText user_nickname_tv;
    /**
     * 这是姓名
     */
    @ViewInject(R.id.user_real_name_tv)
    private TextView user_real_name_tv;
    /**
     * 身份证号
     */
    @ViewInject(R.id.user_card_num_tv)
    private TextView user_card_num_tv;

    /**
     * 邮箱
     */
    @ViewInject(R.id.user_email_ev)
    private ClearEditText user_email_ev;

    /**
     * 推荐人姓名
     */
    @ViewInject(R.id.user_parent_name_tv)
    private TextView user_parent_name_tv;
    /**
     * 推荐人手机号
     */
    @ViewInject(R.id.user_parent_phone_tv)
    private TextView user_parent_phone_tv;

    /**
     * 性别
     */
    private String sex = "1";
    /**
     * 邮箱
     */
    private String email = "";
    /**
     * 昵称
     */
    private String nickname = "";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("修改个人资料");
        titlt_right_tv.setText("保存");
        titlt_right_tv.setVisibility(View.VISIBLE);
        titlt_right_tv.setTextColor(Color.RED);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_editprofile;
    }

    @Override
    protected void initialized() {
        size = ToolKit.dip2px(this, 80);
        forImagePacker();
        userPst = new UserPst(this);
    }

    private void forImagePacker() {
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
        mHandler.sendEmptyMessage(MSG_LOAD_DATA);
    }

    @Override
    protected void requestData() {
        userPst.userInfo();
    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        if (requestUrl.contains("userInfo")) {
            super.onComplete(requestUrl, jsonStr);
            Map<String, Object> map = GsonUtil.GsonToMaps(jsonStr);
            Map<String, String> data = (Map<String, String>) map.get("data");
            img_head_edit.setVisibility(View.VISIBLE);
            Glide.with(this).load(data.get("head_pic")).error(R.drawable.ic_default)
                    .placeholder(R.drawable.ic_default)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .override(size, size).into(img_head_edit);
            // 用户id
            user_id_tv.setText(data.get("user_id"));
            // 昵称
            nickname = data.get("nickname");
            user_nickname_tv.setText(nickname);
            // 真实姓名
            user_real_name_tv.setText(data.get("real_name"));
            // 身份证号
            user_card_num_tv.setText(data.get("id_card_num"));

            sex = data.get("sex");
            if (sex.equals("2")) {
                tv_Sex.setText("女");
            } else {
                tv_Sex.setText("男");
            }
            // 邮箱
            email = data.get("email");
            user_email_ev.setText(email);
            province_id = data.get("province_id");
            city_id = data.get("city_id");
            area_id = data.get("area_id");
            street_id = data.get("street_id");

            user_select_zoon_tv.setText(data.get("province_name") + data.get("city_name") + data.get("area_name"));
            user_select_street_tv.setText(data.get("street_name"));

            user_parent_name_tv.setText(data.get("parent_name"));
            user_parent_phone_tv.setText(data.get("parent_phone"));
            return;
        }
        if (requestUrl.contains("editInfo")) {
            super.onComplete(requestUrl, jsonStr);
            showRightTip("修改成功");
            finish();
        }
    }

    @Override
    @OnClick({R.id.img_head_edit, R.id.rel_Sex, R.id.user_select_zoon_layout, R.id.user_select_street_layout,
            R.id.titlt_right_tv})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_head_edit:// 头像
                startActivityForResult(ImageGridActivity.class, null, 100);
                break;
            case R.id.rel_Sex:// 性别
                show();
                break;
            case R.id.user_select_zoon_layout:// 区域选择
                if (isLoaded) {
                    ShowPickerView();
                }
//                startActivityForResult(AreaSelectAty.class, null, 102);
                break;
            case R.id.user_select_street_layout:// 选择街道
                if (area_id.equals("")) {
                    showErrorTip("请选择省市区");
                    break;
                }
                Bundle bundle = new Bundle();
                bundle.putString("title", "选择街道");
                bundle.putString("area_id", area_id);
                startActivityForResult(TextListAty.class, bundle, 101);
                break;
            case R.id.titlt_right_tv:// 保存
                nickname = user_nickname_tv.getText().toString();
                email = user_email_ev.getText().toString();
                userPst.editInfo(nickname, sex, email, province_id, city_id, area_id, street_id, file);
                break;
        }
    }

    /**
     * 显示dialog
     */
    public void show() {
        dialog = new Dialog(this, R.style.BottomDialog);
        View inflate = LayoutInflater.from(this).inflate(R.layout.dialog_bottom_li, null);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (file != null && file.exists()) {
            file.delete();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(
                        ImagePicker.EXTRA_RESULT_ITEMS);
                String pic_path = CompressionUtil.compressionBitmap(images.get(0).path);
                switch (requestCode) {
                    case 100:
                        file = new File(pic_path);
                        Glide.with(this).load(file).override(size, size).centerCrop().into(img_head_edit);
                        break;
                }
            } else {
                showErrorTip("哎呀出错了。。");
            }
        } else if (resultCode == RESULT_OK) {
            if (data != null) {
                switch (requestCode) {
                    case 101:
                        /*街道*/
                        String street = data.getStringExtra("street");
                        street_id = data.getStringExtra("street_id");
                        user_select_street_tv.setText(street);
                        break;
                }

            }
        }
    }
    // TODO==========城市选择==========
    // TODO==========城市选择==========
    // TODO==========城市选择==========
    // TODO==========城市选择==========
    // TODO==========城市选择==========

    private ArrayList<ProvinceForTxd> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<CityForTxd>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<DistrictsForTxd>>> options3Items = new ArrayList<>();
    private Thread thread;
    private static final int MSG_LOAD_DATA = 0x0001;
    private static final int MSG_LOAD_SUCCESS = 0x0002;
    private static final int MSG_LOAD_FAILED = 0x0003;


    private void ShowPickerView() {// 弹出选择器
        OptionsPickerView pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView
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
                user_select_zoon_tv.setText(tx);
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
        String JsonData = new GetJsonDataUtil().getJson(this, "provinceFotTxd.json");//获取assets目录下的json文件数据
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
}
