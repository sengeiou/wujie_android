package com.txd.hzj.wjlp.minetoAty;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ants.theantsgo.util.L;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.tencent.mm.opensdk.utils.Log;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;

/**
 * Created by lienchao on 2017/7/13 0013.
 */

public class EditProfileAty extends BaseAty implements View.OnClickListener{
    /**
     * 标题
     * */
    @ViewInject(R.id.titlt_conter_tv)
    public TextView titlt_conter_tv;
    /**
     * 修改后保存
     * */
    @ViewInject(R.id.titlt_right_tv)
    public TextView titlt_right_tv;
    /**
     * 性别
     * */
    @ViewInject(R.id.rel_Sex)
    public RelativeLayout rel_Sex;
    @ViewInject(R.id.tv_Sex)
    public TextView tv_Sex;
    /**
     * dialog中TextView
     * */
    private Button btn_men,btn_women,btn_cancel;
    private Button choosePhoto;
    private Button takePhoto;
    private Button cancel;
    private Dialog dialog;
    private View inflate;
    /**
     * 头像
     * */
    @ViewInject(R.id.img_head_edit)
    private ImageView img_head_edit;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("修改个人资料");
        titlt_right_tv.setText("保存");
        titlt_right_tv.setVisibility(View.VISIBLE);
        titlt_right_tv.setTextColor(Color.RED);
        initEvent();

    }

    private void initEvent() {
        rel_Sex.setOnClickListener(this);
        img_head_edit.setOnClickListener(this);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_editprofile;
    }

    @Override
    protected void initialized() {

    }

    @Override
    protected void requestData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rel_Sex:
                show();
                break;
            case R.id.takePhoto:
                tv_Sex.setText("男");
                dialog.dismiss();
                break;
            case R.id.choosePhoto:
                tv_Sex.setText("女");
                dialog.dismiss();
                break;
            case R.id.btn_cancel:
                dialog.dismiss();
        }
    }
    /**
     * 显示dialog
     * */
    public void show(){
        dialog = new Dialog(this, R.style.BottomDialog);
        inflate = LayoutInflater.from(this).inflate(R.layout.dialog_bottom_li, null);
        choosePhoto = (Button) inflate.findViewById(R.id.choosePhoto);
        takePhoto = (Button) inflate.findViewById(R.id.takePhoto);
        cancel = (Button) inflate.findViewById(R.id.btn_cancel);
        choosePhoto.setOnClickListener(this);
        takePhoto.setOnClickListener(this);
        cancel.setOnClickListener(this);
        dialog.setContentView(inflate);
        Window dialogWindow = dialog.getWindow();
        dialogWindow.setGravity( Gravity.BOTTOM);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.y = 20;
        dialogWindow.setAttributes(lp);
        dialog.show();
    }
}
