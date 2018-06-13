package com.txd.hzj.wjlp.distribution.shopAty;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.TimePickerView;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.view.MyShopTitleView;

import java.util.Calendar;
import java.util.Date;

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
                showPop();
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

    private void showPop() {
        //时间选择器
        Calendar selectedDate = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        Calendar endDate = Calendar.getInstance();

        //正确设置方式 原因：注意事项有说明
        startDate.set(2013,0,1);
        endDate.set(2020,11,31);

        TimePickerView pvTime = new TimePickerView.Builder(ShopRevenue.this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                Toast.makeText(ShopRevenue.this, date.toString(), Toast.LENGTH_SHORT).show();
            }
        })
                .setType(new boolean[]{true, true, false, false, false, false})
                .setCancelText("取消")//取消按钮文字
                .setSubmitText("完成")//确认按钮文字
                .setContentSize(18)//滚轮文字大小
                .setTitleSize(16)//标题文字大小
                .setTitleText("选择日期")//标题文字
                .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
                .isCyclic(true)//是否循环滚动
                .setTitleColor(getResources().getColor(R.color.shop_main_fontcolor_black))//标题文字颜色
                .setSubmitColor(getResources().getColor(R.color.shop_main_fontcolor_black))//确定按钮文字颜色
                .setCancelColor(getResources().getColor(R.color.shop_main_fontcolor_black))//取消按钮文字颜色
                .setTitleBgColor(getResources().getColor(R.color.white))//标题背景颜色 Night mode
//                .setBgColor(0xFF333333)//滚轮背景颜色 Night mode
//                .setDate(selectedDate)// 如果不设置的话，默认是系统时间*/
//                .setRangDate(startDate,endDate)//起始终止年月日设定
                .setLabel("年","月","日","时","分","秒")//默认设置为年月日时分秒
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .isDialog(false)//是否显示为对话框样式
                .build();
        pvTime.show();
    }
}
