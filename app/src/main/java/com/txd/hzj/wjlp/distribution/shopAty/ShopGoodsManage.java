package com.txd.hzj.wjlp.distribution.shopAty;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.flyco.tablayout.utils.FragmentChangeManager;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.distribution.shopFgt.ShopManageOpenShopFgt;
import com.txd.hzj.wjlp.distribution.shopFgt.ShopManageOrdinaryFgt;
import com.txd.hzj.wjlp.view.MyShopTitleView;

import java.util.ArrayList;

/**
 * 创建者：Zyf
 * 功能描述：商品管理
 * 联系方式：无
 */
public class ShopGoodsManage extends BaseAty implements View.OnClickListener {

    TextView titlt_conter_tv;


    private ArrayList<Fragment> fragments; // 展示的Fragment集合
    private FragmentChangeManager fragmentChangeManager;
    private MyShopTitleView titleView;
    TextView titlt_right_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_commodity_management;
    }

    @Override
    protected void initialized() {
        titlt_conter_tv = findViewById(R.id.titlt_conter_tv);
        titlt_right_tv = findViewById(R.id.titlt_right_tv);
        titleView = findViewById(R.id.mytitle_tv);
        titleView.setVisibility(View.VISIBLE);
        titleView.setLeftName("普通商品");
        titleView.setRightName("开店商品");
        titleView.setleftListener(new MyShopTitleView.LeftContent() {
            @Override
            public void getLeft(String string) {
                fragmentChangeManager.setFragments(Integer.valueOf(string));
                if (string.equals("0")){
                    Toast.makeText(ShopGoodsManage.this, "普通" + string, Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(ShopGoodsManage.this, "开店" + string, Toast.LENGTH_SHORT).show();
                }
                titlt_right_tv.setVisibility(View.GONE);
            }
        });

        titlt_conter_tv.setVisibility(View.GONE);

    }

    @Override
    protected void requestData() {
        fragments = new ArrayList<>();
        fragments.add(new ShopManageOrdinaryFgt()); // 普通商品管理界面
        fragments.add(new ShopManageOpenShopFgt()); // 开店商品管理界面
        fragmentChangeManager = new FragmentChangeManager(getSupportFragmentManager(), R.id.commodityManage_content_fLayout, fragments);
    }


    /**
     * 设置控件的显示隐藏
     *
     * @param isShow 是否显示
     */
    public TextView setTitltRightVisibility(boolean isShow) {
        titlt_right_tv.setVisibility(isShow ? View.VISIBLE : View.GONE);
        return titlt_right_tv;
    }

    /**
     * 设置显示文字
     *
     * @param showStr 显示的字符串
     */
    public void setTitltRightText(String showStr) {
        titlt_right_tv.setText(showStr);
    }

}
