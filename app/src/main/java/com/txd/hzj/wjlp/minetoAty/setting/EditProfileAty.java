package com.txd.hzj.wjlp.minetoAty.setting;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ants.theantsgo.WeApplication;
import com.ants.theantsgo.config.Settings;
import com.ants.theantsgo.gson.GsonUtil;
import com.ants.theantsgo.imageLoader.GlideImageLoader;
import com.ants.theantsgo.tool.ToolKit;
import com.ants.theantsgo.tool.glide.GlideUtils;
import com.ants.theantsgo.util.CompressionUtil;
import com.ants.theantsgo.util.L;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.http.address.AddressPst;
import com.txd.hzj.wjlp.http.user.UserPst;
import com.txd.hzj.wjlp.minetoAty.order.TextListAty;
import com.txd.hzj.wjlp.new_wjyp.aty_authentication;
import com.txd.hzj.wjlp.tool.proUrbArea.ProUrbAreaUtil;
import com.txd.hzj.wjlp.view.flowlayout.ClearEditText;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;

import cn.gavinliu.android.lib.shapedimageview.ShapedImageView;

/**
 * 作者：DUKE_HwangZj
 * 日期：2017/7/26 0026
 * 时间：下午 4:19
 * 描述：个人信息
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
     * 真实姓名
     */
    @ViewInject(R.id.user_real_name_tv)
    private TextView user_real_name_tv;
    /**
     * 身份证号
     */
    @ViewInject(R.id.user_card_num_tv)
    private TextView user_card_num_tv;

    /**
     * 实名认证的容器，真实姓名、身份证号、性别
     */
    @ViewInject(R.id.user_authStatus_layout)
    private LinearLayout user_authStatus_layout;

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
    private String personal_data_status = "0";//是否完善了个人资料
    private AddressPst addressPst;


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
        ProUrbAreaUtil.gainInstance().checkData((WeApplication) getApplication());
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
    }

    @Override
    protected void requestData() {
        userPst.userInfo();
    }

    @ViewInject(R.id.parent_alliance_merchant_name)
    private TextView parent_alliance_merchant_name;
    @ViewInject(R.id.parent_alliance_merchant_sn)
    private TextView parent_alliance_merchant_sn;
    @ViewInject(R.id.hidden_parent_name)
    private TextView hidden_parent_name;
    @ViewInject(R.id.hidden_parent_phone)
    private TextView hidden_parent_phone;

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        L.e("=================userInfo==============" + jsonStr);
        super.onComplete(requestUrl, jsonStr);
        if (requestUrl.contains("userInfo")) {

            Map<String, Object> map = GsonUtil.GsonToMaps(jsonStr);
            final Map<String, String> data = (Map<String, String>) map.get("data");

            img_head_edit.setVisibility(View.VISIBLE);
            Glide.with(this).load(data.get("head_pic")).error(R.drawable.ic_default)
                    .placeholder(R.drawable.ic_default)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .override(size, size).into(img_head_edit);
            if (!data.get("head_pic").isEmpty()) { // 如果头像Url不为空则直接将缓存的文件路径转换为File，防止上传时出现空指针而报错
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        file = new File(GlideUtils.getGlideFilePath(EditProfileAty.this, data.get("head_pic")));
                    }
                }).start();
            }
            auth_status = data.get("auth_status");

            if (!auth_status.equals("2")) { // 未实名认证或实名认证未通过或是认证中需要隐藏掉这三项，只有已认证状态才显示这三项
                user_authStatus_layout.setVisibility(View.GONE);
                // 未实名认证，真实姓名，身份证号，性别显示灰色字体
                user_real_name_tv.setTextColor(Color.parseColor("#BCBBC1"));
                user_card_num_tv.setTextColor(Color.parseColor("#BCBBC1"));
                tv_Sex.setTextColor(Color.parseColor("#BCBBC1"));
            }

            comp_auth_status = data.get("comp_auth_status");
            // 用户id
            user_id_tv.setText(data.get("user_id"));
            // 昵称
            nickname = data.get("nickname");
            user_nickname_tv.setText(nickname);
            // 真实姓名
            user_real_name_tv.setText(data.get("real_name"));
            // 身份证号
            user_card_num_tv.setText(data.get("id_card_num"));
            tv_Sex.setText(data.get("sex"));
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
            parent_alliance_merchant_name.setText(data.get("parent_alliance_merchant_name"));
            parent_alliance_merchant_sn.setText(data.get("parent_alliance_merchant_sn"));
            hidden_parent_name.setText(data.get("hidden_parent_name"));
            hidden_parent_phone.setText(data.get("hidden_parent_phone"));
            this.personal_data_status = data.get("personal_data_status");
            return;
        }
        if (requestUrl.contains("editInfo")) {
            super.onComplete(requestUrl, jsonStr);
            L.e("======editInfo==========" + jsonStr);
            showRightTip("修改成功");
            finish();
        }

//        if (requestUrl.contains("androidAddress")) {
//            L.e("wang", jsonStr);
//            try {
//                JSONObject jsonObject = new JSONObject(jsonStr);
//                String data = jsonObject.getString("data");
//                application.setCityProvience(data);
//                L.e("wang", data);
//                initJsonData(data);
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }

    }

    String auth_status, comp_auth_status;

    @Override
    @OnClick({R.id.img_head_edit, R.id.rel_Sex, R.id.user_select_zoon_layout, R.id.user_select_street_layout
            , R.id.user_real_name_tv, R.id.user_card_num_tv, R.id.titlt_right_tv})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_head_edit:// 头像
                startActivityForResult(ImageGridActivity.class, null, 100);
                break;
            case R.id.rel_Sex: {// 性别
//                show();
                if (personal_data_status.equals("0")) {
                    showToast("请先完善个人资料");
                    return;
                }
                if (!auth_status.equals("2")) {
                    Bundle b = new Bundle();
                    b.putString("auth_status", auth_status);
                    b.putString("comp_auth_status", comp_auth_status);
                    startActivity(aty_authentication.class, b);
                }
                break;
            }
            case R.id.user_card_num_tv: {
                if (personal_data_status.equals("0")) {
                    showToast("请先完善个人资料");
                    return;
                }

                if (!auth_status.equals("2")) {
                    Bundle b = new Bundle();
                    b.putString("auth_status", auth_status);
                    b.putString("comp_auth_status", comp_auth_status);
                    startActivity(aty_authentication.class, b);
                }
                break;
            }
            case R.id.user_real_name_tv: {
                if (personal_data_status.equals("0")) {
                    showToast("请先完善个人资料");
                    return;
                }

                if (!auth_status.equals("2")) {
                    Bundle b = new Bundle();
                    b.putString("auth_status", auth_status);
                    b.putString("comp_auth_status", comp_auth_status);
                    startActivity(aty_authentication.class, b);
                }
                break;
            }
            case R.id.user_select_zoon_layout:// 区域选择
                ProUrbAreaUtil.gainInstance().showPickerView((TextView) findViewById(R.id.user_select_zoon_tv), "", "", "", EditProfileAty.this, null);
                // 添加省市区的文字变化监听
                ((TextView) findViewById(R.id.user_select_zoon_tv)).addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        user_select_street_tv.setText("");
                    }
                });
//                startActivityForResult(AreaSelectAty.class, null, 102);
                break;
            case R.id.user_select_street_layout:// 选择街道
                // 如果弹窗选择了区域则将区域赋值给变量，如果没有弹窗选择，则保持原有的区域值
                area_id = ProUrbAreaUtil.gainInstance().getArea_id().isEmpty() ? area_id : ProUrbAreaUtil.gainInstance().getArea_id();
                if (area_id.equals("")) {
                    showErrorTip("请选择省市区");
                    return;
                }
                Bundle bundle = new Bundle();
                bundle.putString("title", "选择街道");
                bundle.putString("area_id", area_id);
                startActivityForResult(TextListAty.class, bundle, 101);
                break;
            case R.id.titlt_right_tv:// 保存

                // 获取选择的地址，如果没有选择，则将回传的值上传至服务器
                if (ProUrbAreaUtil.gainInstance().getProvince_id() != null && !ProUrbAreaUtil.gainInstance().getProvince_id().isEmpty()) {
                    province_id = ProUrbAreaUtil.gainInstance().getProvince_id();
                }
                if (ProUrbAreaUtil.gainInstance().getCity_id() != null && !ProUrbAreaUtil.gainInstance().getCity_id().isEmpty()) {
                    city_id = ProUrbAreaUtil.gainInstance().getCity_id();
                }
                if (ProUrbAreaUtil.gainInstance().getArea_id() != null && !ProUrbAreaUtil.gainInstance().getArea_id().isEmpty()) {
                    area_id = ProUrbAreaUtil.gainInstance().getArea_id();
                }

                nickname = user_nickname_tv.getText().toString().trim();
                email = user_email_ev.getText().toString().trim();
                // 个人信息不能为空
                if (user_nickname_tv.getText().toString().trim().equals("")) {
                    showToast("昵称不能为空");
                    return;
                }
                if (user_email_ev.getText().toString().trim().equals("")) {
                    showToast("邮箱不能为空");
                    return;
                }
                if (user_select_zoon_tv.getText().toString().trim().equals("")) {
                    Toast.makeText(this, "所在地区不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (user_select_street_tv.getText().toString().trim().equals("")) {
                    Toast.makeText(this, "所在街道不能为空！", Toast.LENGTH_SHORT).show();
                    return;
                }
                try {
                    userPst.editInfo(nickname, sex, email, province_id, city_id, area_id, street_id, file);
                } catch (Exception e) {
                    L.e("EditProfileAty is throw Exception:" + e.toString());
                    showErrorTip("填写数据异常，请重新检查！");
                }
                hideSoftInput();
                break;
        }
    }

    /**
     * 强制收起输入键盘
     */
    private void hideSoftInput() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(user_nickname_tv.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(user_email_ev.getWindowToken(), 0);
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
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                if (images.size() > 0) {
                    String pic_path = CompressionUtil.compressionBitmap(images.get(0).path);
                    switch (requestCode) {
                        case 100:
                            try {
                                file = new File(pic_path);
                                Glide.with(this).load(file).override(size, size).centerCrop().into(img_head_edit);
                            } catch (Exception e) {
                                L.e("File Exception:" + e.toString());
                                showToast("未找到图片文件，请重新选择。");
                            }
                            break;
                    }
                }
            } else {
                showErrorTip("没有返回任何数据。。");
            }
        } else if (resultCode == RESULT_OK) {
            if (data != null) {
                switch (requestCode) {
                    case 101:
                        /* 街道 */
                        String street = data.getStringExtra("street");
                        street_id = data.getStringExtra("street_id");
                        user_select_street_tv.setText(street);
                        break;
                }

            }
        }
    }

}
