package com.txd.hzj.wjlp.distribution.shopAty;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.view.MyShopTitleView;

/**
 * 创建者：Qyl
 * 创建时间：2018/6/6 0006 14:36
 * 功能描述：顾客管理页面
 * 联系方式：无
 */
public class ShopPersonManage extends BaseAty implements View.OnClickListener {

    private MyShopTitleView titleView;

    @Override
    protected int getLayoutResId() {
        return R.layout.shop_person_manage;
    }

    @Override
    protected void initialized() {
        findViewById(R.id.titlt_conter_tv).setVisibility(View.GONE);
        titleView = findViewById(R.id.mytitle_tv);
        titleView.setVisibility(View.VISIBLE);
        titleView.setLeftName("店主身份");
        titleView.setRightName("普通顾客");
        titleView.setleftListener(new MyShopTitleView.LeftContent() {
            @Override
            public void getLeft(String string) {
                if (string.equals("0")) {
                    Toast.makeText(ShopPersonManage.this, "店主" + string, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ShopPersonManage.this, "用户" + string, Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);

    }

    @Override
    protected void requestData() {

    }
}
