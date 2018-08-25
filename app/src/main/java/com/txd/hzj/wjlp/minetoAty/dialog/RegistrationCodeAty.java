package com.txd.hzj.wjlp.minetoAty.dialog;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ants.theantsgo.config.Config;
import com.ants.theantsgo.tool.ToolKit;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.tool.BitmapUtils;

import java.util.regex.Pattern;

import cn.bingoogolapple.qrcode.zxing.QRCodeEncoder;

/**
 * 作者：DUKE_HwangZj
 * 日期：2017/7/18 0018
 * 时间：上午 9:46
 * 描述：注册码
 */
public class RegistrationCodeAty extends BaseAty {

    /**
     * 用户头像
     */
    @ViewInject(R.id.code_user_head_iv)
    private ImageView code_user_head_iv;

    @ViewInject(R.id.registrationCode_zhucema_tv)
    private TextView registrationCode_zhucema_tv; // 头像右侧的文字框，显示的是注册码那个
    @ViewInject(R.id.registrationCode_show_tv)
    private TextView registrationCode_show_tv; // 二维码下方的提示文字

    /**
     * 邀请码二维码图片
     */
    @ViewInject(R.id.user_code_iv)
    private ImageView user_code_iv;
    /**
     * 头像
     */
    private String head_pic = "";
    /**
     * 注册码
     */
    private String invite_code = "";
    /**
     * 联盟商家id
     */
    private String stage_merchant_id = "";

    private int head_size = 0;

    private int code_size = 0;
    private Bitmap recordBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showDialog();
        createQRCode();
        Glide.with(this).load(head_pic)
                .centerCrop().override(head_size, head_size)
                .placeholder(R.drawable.ic_default)
                .error(R.drawable.ic_default)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(code_user_head_iv);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_registration_code;
    }

    @Override
    protected void initialized() {
        head_pic = getIntent().getStringExtra("head_pic");
        invite_code = getIntent().getStringExtra("invite_code");
        stage_merchant_id = getIntent().getStringExtra("stage_merchant_id");
        if (stage_merchant_id != null && !stage_merchant_id.equals("")) {
            // 商家码
            code_user_head_iv.setVisibility(View.GONE); // 隐藏头像
            registrationCode_zhucema_tv.setText("商家码"); // 头像右侧的文字框，显示的是注册码那个
            registrationCode_zhucema_tv.setGravity(Gravity.CENTER); // 标题居中
            registrationCode_show_tv.setText("点击下载放到线下进行扫码支付"); // 二维码下方的提示文字

            Config.OFFICIAL_WEB.replace("api", "www");

            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(Config.OFFICIAL_WEB.replace("api", "www")); // 将网址的api替换成www
            stringBuffer.append("Wap/OfflineStore/confirmation/stage_merchant_id/");
            stringBuffer.append(stage_merchant_id);
            stringBuffer.append("/invite_code/");
            stringBuffer.append(invite_code);
            stringBuffer.append(".html");
            invite_code = stringBuffer.toString();
//          生成字符串的样式：http://test2.wujiemall.com/Wap/OfflineStore/confirmation/stage_merchant_id/39/invite_code/GYrJovNW.html

        }

        head_size = ToolKit.dip2px(this, 80);
        code_size = ToolKit.dip2px(this, 200);
    }

    @Override
    protected void requestData() {

    }

    /**
     * 创建二维码
     */
    private void createQRCode() {

        //生成二维码，第一个参数为要生成的文本，第二个参数为生成尺寸，第三个参数为生成回调
        QRCodeEncoder.encodeQRCode(invite_code, code_size, new QRCodeEncoder.Delegate() {
            /**
             * 生成成功
             * @param bitmap bitmap
             */
            @Override
            public void onEncodeQRCodeSuccess(Bitmap bitmap) {
                user_code_iv.setImageBitmap(bitmap);
                recordBitmap = bitmap;
                removeDialog();
            }

            /**
             * 生成失败
             */
            @Override
            public void onEncodeQRCodeFailure() {
                removeDialog();
                showErrorTip("生成二维码失败");
            }
        });
    }


    @Override
    @OnClick({R.id.user_code_iv})
    public void onClick(View v) {
        super.onClick(v);
        if (null != recordBitmap) {
            BitmapUtils.gainInstance().saveBmp2Gallery(RegistrationCodeAty.this, recordBitmap, "zhucema", new BitmapUtils.Listener() {
                @Override
                public void saveSuccess() {
                    Toast.makeText(RegistrationCodeAty.this, "已成功保存到相册！", Toast.LENGTH_LONG).show();
                }
            });
        }

    }
}
