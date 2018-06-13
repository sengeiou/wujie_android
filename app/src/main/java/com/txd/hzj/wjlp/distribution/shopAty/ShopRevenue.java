package com.txd.hzj.wjlp.distribution.shopAty;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.view.MyShopTitleView;

/**
 * 创建者：Zyf
 * 功能描述：小店营收
 * 联系方式：无
 */
public class ShopRevenue extends BaseAty implements View.OnClickListener{

    private TextView titlt_conter_tv;

    private LinearLayout shop_person_title_manage;

    private ImageView time_select;

    private MyShopTitleView mytitle_tv;

    private LinearLayout llyt_week;

    private LinearLayout llyt_month;

    private LinearLayout llyt_year;

    private View view_week;

    private View view_month;

    private View view_year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_store_revenue;
    }

    @Override
    protected void initialized() {
        titlt_conter_tv=findViewById(R.id.titlt_conter_tv);
        shop_person_title_manage=findViewById(R.id. shop_person_title_manage);
        time_select=findViewById(R.id.time_select_img);
        mytitle_tv=findViewById(R.id.mytitle_tv);
        shop_person_title_manage.setVisibility(View.VISIBLE);
        llyt_week=findViewById(R.id.llyt_week);
        llyt_month=findViewById(R.id.llyt_month);
        llyt_year=findViewById(R.id.llyt_year);
        view_week=findViewById(R.id.view_week);
        view_month=findViewById(R.id.view_month);
        view_year=findViewById(R.id.view_year);
        mytitle_tv.setVisibility(View.VISIBLE);
        titlt_conter_tv.setVisibility(View.GONE);
        time_select.setVisibility(View.VISIBLE);




        mytitle_tv.setLeftName("销售额");
        mytitle_tv.setRightName("净收益");


        time_select.setOnClickListener(this);
        llyt_week.setOnClickListener(this);
        llyt_month.setOnClickListener(this);
        llyt_year.setOnClickListener(this);


        mytitle_tv.setleftListener(new MyShopTitleView.LeftContent() {
            @Override
            public void getLeft(String string) {

            }
        });
    }

    @Override
    protected void requestData() {

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.time_select_img:
                break;
            case R.id.llyt_week:
                clearChoice();
                view_week.setBackgroundColor(getResources().getColor(R.color.titleColors));
                break;
            case R.id.llyt_month:
                clearChoice();
                view_month.setBackgroundColor(getResources().getColor(R.color.titleColors));
                break;
            case R.id.llyt_year:
                clearChoice();
                view_year.setBackgroundColor(getResources().getColor(R.color.titleColors));
                break;
        }

    }

    private void clearChoice() {
        view_week.setBackgroundColor(getResources().getColor(R.color.white));
        view_month.setBackgroundColor(getResources().getColor(R.color.white));
        view_year.setBackgroundColor(getResources().getColor(R.color.white));
    }
}
