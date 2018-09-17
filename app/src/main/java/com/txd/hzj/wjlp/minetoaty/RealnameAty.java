package com.txd.hzj.wjlp.minetoaty;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ants.theantsgo.config.Settings;
import com.ants.theantsgo.gson.GsonUtil;
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
import com.txd.hzj.wjlp.http.user.UserPst;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by lienchao on 2017/7/14 0014.
 */

public class RealnameAty extends BaseAty {
    /**
     * 设置标题
     */
    @ViewInject(R.id.titlt_conter_tv)
    public TextView titlt_conter_tv;

    @ViewInject(R.id.id_card_front_iv)
    private ImageView id_card_front_iv;
    private int w = 0;
    private int h = 0;
    private File file2;

    /**
     * 真实姓名
     */
    @ViewInject(R.id.user_real_name_ev)
    private EditText user_real_name_ev;

    /**
     * 身份证号
     */
    @ViewInject(R.id.user_id_card_num_tv)
    private EditText user_id_card_num_tv;
    private String real_name = "";
    private String id_card_num = "";

    private UserPst userPst;

    private String auth_status = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("实名认证");

        w = Settings.displayWidth / 3;
        h = w * 2 / 3;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(w, h);
        id_card_front_iv.setLayoutParams(params);

        if ("1".equals(auth_status)) {
            user_real_name_ev.setEnabled(false);
            user_id_card_num_tv.setEnabled(false);
            userPst.getAuth();
        }

    }

    @Override
    @OnClick({R.id.id_card_front_layout, R.id.user_auth_tv})
    public void onClick(View v) {
        super.onClick(v);
        if ("1".equals(auth_status)) {
            return;
        }
        switch (v.getId()) {
            case R.id.id_card_front_layout:
                startActivityForResult(ImageGridActivity.class, null, 101);
                break;
            case R.id.user_auth_tv:
                real_name = user_real_name_ev.getText().toString();
                id_card_num = user_id_card_num_tv.getText().toString();
                userPst.addAuth(real_name, id_card_num, file2);
                break;
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_realname_li;
    }

    @Override
    protected void initialized() {
        forImagePacker();
        userPst = new UserPst(this);
        auth_status = getIntent().getStringExtra("auth_status");
    }

    @Override
    protected void requestData() {

    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        if (requestUrl.contains("addAuth")) {
            showRightTip("提交成功");
            finish();
            return;
        }
        if (requestUrl.contains("getAuth")) {
            Map<String, Object> map = GsonUtil.GsonToMaps(jsonStr);
            Map<String, String> data = (Map<String, String>) map.get("data");
            user_real_name_ev.setText(data.get("real_name"));
            user_id_card_num_tv.setText(data.get("id_card_num"));
            Glide.with(this).load(data.get("id_card_pic"))
                    .override(w, h).centerCrop()
                    .error(R.drawable.ic_default)
                    .placeholder(R.drawable.ic_default)
                    .into(id_card_front_iv);
        }
    }

    private void forImagePacker() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());// 图片加载
        imagePicker.setCrop(true);// 裁剪
        imagePicker.setSaveRectangle(true);// 矩形保存
        imagePicker.setFocusWidth(Settings.displayWidth);//裁剪框宽度
        imagePicker.setFocusHeight(Settings.displayWidth * 2 / 3);// 裁剪框高度
        imagePicker.setOutPutX(Settings.displayWidth);// 保存图片高度
        imagePicker.setOutPutY(Settings.displayWidth * 2 / 3);// 保存图片宽度
        imagePicker.setMultiMode(false);// 但须
        imagePicker.setShowCamera(true);// 显示拍照按钮
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (file2 != null && file2.exists()) {
            file2.delete();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker
                        .EXTRA_RESULT_ITEMS);
                String pic_path = CompressionUtil.compressionBitmap(images.get(0).path);
                switch (requestCode) {
                    case 101:
                        file2 = new File(pic_path);
                        Glide.with(this).load(file2).override(w, h).centerCrop().into(id_card_front_iv);
                        break;
                }
            } else {
                showErrorTip("哎呀出错了。。");
            }
        }
    }
}
