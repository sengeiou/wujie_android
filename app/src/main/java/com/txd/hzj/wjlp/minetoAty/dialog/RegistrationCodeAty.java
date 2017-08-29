package com.txd.hzj.wjlp.minetoAty.dialog;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.ants.theantsgo.tool.ToolKit;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.login.RegisterGetCodeAty;
import com.txd.hzj.wjlp.mainFgt.CartFgt;

import cn.bingoogolapple.qrcode.core.DisplayUtils;
import cn.bingoogolapple.qrcode.zxing.QRCodeEncoder;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/18 0018
 * 时间：上午 9:46
 * 描述：注册码
 * ===============Txunda===============
 */
public class RegistrationCodeAty extends BaseAty {

    /**
     * 用户头像
     */
    @ViewInject(R.id.code_user_head_iv)
    private ImageView code_user_head_iv;

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

    private int head_size = 0;

    private int code_size = 0;

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
}
