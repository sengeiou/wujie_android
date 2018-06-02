package com.txd.hzj.wjlp.minetoAty.dialog;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ants.theantsgo.config.Config;
import com.ants.theantsgo.tips.MikyouCommonDialog;
import com.ants.theantsgo.tool.ToolKit;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lidroid.xutils.util.LogUtils;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.tool.BitmapUtils;

import java.util.HashMap;

import cn.bingoogolapple.qrcode.zxing.QRCodeEncoder;

/**
 * 创建者：TJDragon(LiuGang)
 * 创建时间：2018/6/1 12:53
 * 功能描述：拜师码
 * 联系方式：常用邮箱或电话
 */
public class ApprenticeCodeAty extends BaseAty implements View.OnClickListener {
    private TextView member_trainerTv;
    private TextView merchant_trainerTv;
    private HashMap<String, StringBuffer> stringBuffers;
    private ImageView user_code_iv;
    private HashMap<String, Bitmap> recordBtimaps;
    private final String TYPE_MEMBER = "1", TYPE_MERCHANT = "2";
    private String currentType;

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_apprentice_code;
    }

    @Override
    protected void initialized() {
        member_trainerTv = findViewById(R.id.member_trainerTv);
        merchant_trainerTv = findViewById(R.id.merchant_trainerTv);
        user_code_iv = findViewById(R.id.user_code_iv);
    }

    @Override
    protected void requestData() {
        Intent gintent = getIntent();
        String head_pic = gintent.getStringExtra("head_pic");
        ImageView code_user_head_iv = findViewById(R.id.code_user_head_iv);
        int head_size = ToolKit.dip2px(this, 80);
        Glide.with(this).load(head_pic)
                .centerCrop().override(head_size, head_size)
                .placeholder(R.drawable.ic_default)
                .error(R.drawable.ic_default)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(code_user_head_iv);

        String is_member_trainer = gintent.getStringExtra("is_member_trainer");
        String is_merchant_trainer = gintent.getStringExtra("is_merchant_trainer");
        String invite_code = gintent.getStringExtra("code");
        stringBuffers = new HashMap<>();
        recordBtimaps = new HashMap<>();
        setTxt(is_member_trainer, member_trainerTv, TYPE_MEMBER, invite_code);//汇源拜师码 1
        setTxt(is_merchant_trainer, merchant_trainerTv, TYPE_MERCHANT, invite_code);//商家拜师码 2
        if (stringBuffers.size() > 1) {//两个拜师码
            changeSelect(TYPE_MEMBER);
            toCodeType(TYPE_MEMBER);
            member_trainerTv.setOnClickListener(this);
            merchant_trainerTv.setOnClickListener(this);
        } else if (stringBuffers.size() == 1) {//一个拜师码
            if (member_trainerTv.getVisibility() == View.VISIBLE) {
                toCodeType(TYPE_MEMBER);
            } else {
                toCodeType(TYPE_MERCHANT);
            }
        }
        user_code_iv.setOnClickListener(this);
    }

    private void setTxt(String txtStr, TextView tv, String type, String invite_code) {
        if (!TextUtils.isEmpty(txtStr)) {
            tv.setVisibility(View.VISIBLE);
            tv.setText(txtStr);
            StringBuffer memberCodeUrl = new StringBuffer(Config.BASE_URL + "User/mentorship/invite_code/" + invite_code + "/type/" + type);
            stringBuffers.put(type, memberCodeUrl);
        } else {
            tv.setVisibility(View.GONE);
        }
    }

    /**
     * 切换到哪个二维码
     *
     * @param type
     */
    private void toCodeType(final String type) {
        showProgressDialog();
        String invite_code = String.valueOf(stringBuffers.get(type));
        LogUtils.e("invite_code " + invite_code);
        if (recordBtimaps.containsKey(type)) {
            removeDialog();
            currentType = type;
            user_code_iv.setImageBitmap(recordBtimaps.get(currentType));
        } else {
            int code_size = ToolKit.dip2px(this, 200);
            //生成二维码，第一个参数为要生成的文本，第二个参数为生成尺寸，第三个参数为生成回调
            QRCodeEncoder.encodeQRCode(invite_code, code_size, new QRCodeEncoder.Delegate() {
                /**
                 * 生成成功
                 * @param bitmap bitmap
                 */
                @Override
                public void onEncodeQRCodeSuccess(Bitmap bitmap) {
                    currentType = type;
                    user_code_iv.setImageBitmap(bitmap);
                    recordBtimaps.put(type, bitmap);
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.member_trainerTv: {
                changeSelect(TYPE_MEMBER);
                toCodeType(TYPE_MEMBER);
            }
            break;
            case R.id.merchant_trainerTv: {
                changeSelect(TYPE_MERCHANT);
                toCodeType(TYPE_MERCHANT);
            }
            break;
            case R.id.user_code_iv: {
                if (recordBtimaps.size() > 0) {
                    Bitmap bitmap = recordBtimaps.get(currentType);
                    BitmapUtils.gainInstance().saveBmp2Gallery(ApprenticeCodeAty.this, bitmap, TYPE_MEMBER.equals(currentType) ? "huiyuan_baishima" : "shangjia_baishima", new BitmapUtils.Listener() {
                        @Override
                        public void saveSuccess() {
                            Toast.makeText(ApprenticeCodeAty.this, "已成功保存到相册！", Toast.LENGTH_LONG).show();
                        }
                    });
                   /* new MikyouCommonDialog(ApprenticeCodeAty.this, "将二维码保存到本地相册", "操作提示", "确定", "取消", true)
                            .setOnDiaLogListener(new MikyouCommonDialog.OnDialogListener() {
                                @Override
                                public void dialogListener(int btnType, View customView, DialogInterface dialogInterface, int which) {
                                    switch (btnType) {
                                        case MikyouCommonDialog.OK: { //确定
                                            Bitmap bitmap = recordBtimaps.get(currentType);
                                            BitmapUtils.gainInstance().saveBmp2Gallery(ApprenticeCodeAty.this, bitmap, TYPE_MEMBER.equals(currentType) ? "huiyuan_baishima" : "shangjia_baishima", new BitmapUtils.Listener() {
                                                @Override
                                                public void saveSuccess() {
                                                    Toast.makeText(ApprenticeCodeAty.this, "保存成功", Toast.LENGTH_LONG).show();
                                                }
                                            });
                                        }
                                        break;
                                        case MikyouCommonDialog.NO: { // 取消

                                        }
                                        break;
                                    }
                                }
                            }).showDialog();*/
                }
            }
        }
    }

    /**
     * 改变选中状态
     *
     * @param type
     */
    private void changeSelect(String type) {
        member_trainerTv.clearFocus();
        member_trainerTv.setSelected(false);
        merchant_trainerTv.clearFocus();
        merchant_trainerTv.setSelected(false);
        switch (type) {
            case TYPE_MEMBER: {
                member_trainerTv.requestFocus();
                member_trainerTv.setSelected(true);
            }
            break;
            case TYPE_MERCHANT: {
                merchant_trainerTv.requestFocus();
                merchant_trainerTv.setSelected(true);
            }
            break;
        }

    }
}
