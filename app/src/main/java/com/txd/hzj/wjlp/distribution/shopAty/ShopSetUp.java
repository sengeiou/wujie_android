package com.txd.hzj.wjlp.distribution.shopAty;

import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;

/**
 * 创建者：Qyl
 * 创建时间：2018/6/6 0006 14:19
 * 功能描述：店铺设置页面
 * 联系方式：无
 */
public class ShopSetUp extends BaseAty implements View.OnClickListener {

    private RelativeLayout setIma;
    private View view;
    private PopupWindow popupWindow;
    private TextView titleName;
    private LinearLayout shop_person_title_manage;

    @Override
    protected int getLayoutResId() {
        return R.layout.shop_set_up;
    }

    @Override
    protected void initialized() {
        setIma = findViewById(R.id.shop_set_ima);
        titleName = findViewById(R.id.titlt_conter_tv);
        shop_person_title_manage=findViewById(R.id.shop_person_title_manage);
        shop_person_title_manage.setVisibility(View.GONE);
        titleName.setText("店铺设置");
        //注册点击事件
        setIma.setOnClickListener(this);
    }

    @Override
    protected void requestData() {

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.shop_set_ima:
                showDialogs();
                break;

        }
    }

    private void showDialogs() {

        view = View.inflate(ShopSetUp.this, R.layout.shop_dialog_popup, null);
         view.findViewById(R.id.shop_dialog_disimis).setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 popupWindow.dismiss();
             }
         });

        //跟布局
        RelativeLayout view1 = (RelativeLayout) view.findViewById(R.id.shop_set_main);
        view1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupWindow != null && popupWindow.isShowing()){
                    popupWindow.dismiss();
                    popupWindow = null;
                }

            }
        });
        popupWindow = new PopupWindow(view, RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setAnimationStyle(R.style.BottomDialog_Animation);
        setBackgroundAlpha(0.5f);
        popupWindow.setOutsideTouchable(false);
        popupWindow.setTouchable(true);
        popupWindow.setFocusable(false);
        popupWindow.showAtLocation(view1, Gravity.BOTTOM , 0,
                0);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setBackgroundAlpha(1.0f);
            }
        });

    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
            popupWindow = null;
        }
        return super.onTouchEvent(event);
    }
    public void setBackgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = (this).getWindow()
                .getAttributes();
        lp.alpha = bgAlpha;
        (this).getWindow().setAttributes(lp);
    }
}
