package com.txd.hzj.wjlp.distribution.shopAty;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.bigkoo.pickerview.TimePickerView;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.distribution.bean.RevenueBean;
import com.txd.hzj.wjlp.distribution.presenter.ShopExhibitPst;
import com.txd.hzj.wjlp.view.MyShopTitleView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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

    private LineChart lineChart;
    private ArrayList<String> bottom;
    private ShopExhibitPst mExhibitPst;
    //普通商品数据
    private RevenueBean.DataBeanXX.NormalBean.DataBean mNormalData;
    //分销商品数据
    private RevenueBean.DataBeanXX.DisBean.DataBeanX mDisData;

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
        mExhibitPst=new ShopExhibitPst(this);
//        time_select.setVisibility(View.VISIBLE);
        bottom = new ArrayList<>();
        bottom.add("6-14");
        bottom.add("6-15");
        bottom.add("6-16");
        bottom.add("6-17");
        bottom.add("6-18");
        bottom.add("6-19");
        bottom.add("6-20");
        bottom.add("6-21");

        lineChart = findViewById(R.id.line_chart);
        //设置图表的描述
        lineChart.setDrawBorders(false);
        //设置x轴的数据
        int numX = 6;
        //设置y轴的数据
        float[] datas1 = {536, 148, 569, 432, 102, 26, 233, 120};//数据
        float[] datas2 = {736, 103, 369, 132, 82, 126, 94, 50};//数据

        ArrayList<Entry> list2 = new ArrayList<>();
        ArrayList<Entry> list1 = new ArrayList<>();
        for (int i = 0; i < datas2.length; i++) {
            list2.add(new Entry(i, datas2[i]));
        }
        for (int i = 0; i < datas1.length; i++) {
            list1.add(new Entry(i, datas1[i]));
        }
        lineChart.animateY(2000);
        Description description = new Description();
        description.setEnabled(false);
        lineChart.setDescription(description);
        lineChart.setScaleEnabled(false);

        LineData lineDatas = new LineData(getLineDatasets1(list2), getLineDatasets(list1));
        lineDatas.setHighlightEnabled(false);
        lineChart.setData(lineDatas);
        Legend legend = lineChart.getLegend();
        legend.setEnabled(false);

        YAxis axisRight = lineChart.getAxisRight();
        YAxis axisLeft = lineChart.getAxisLeft();
        axisLeft.setEnabled(false);
        axisRight.setEnabled(false);

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return bottom.get((int) value);
            }
        });
        xAxis.setAxisMaximum(7f);
        xAxis.setAxisMinimum(0f);
        xAxis.setLabelCount(numX);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(true);



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
        mExhibitPst.getRevenue("18","1","1","1");
    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        RevenueBean revenueBean = JSONObject.parseObject(jsonStr, RevenueBean.class);
        if (200==revenueBean.getCode()){
            mNormalData = revenueBean.getData().getNormal().getData();
            mDisData = revenueBean.getData().getDis().getData();
        }
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
    //蓝线
    private LineDataSet getLineDatasets(List list) {
        LineDataSet lineDataSet = new LineDataSet(list, "");
        lineDataSet.setDrawCircleHole(false);
        lineDataSet.setFillAlpha(1);
        lineDataSet.setLineWidth(2f);
        lineDataSet.setColor(Color.rgb(12, 175, 232), 255);
        lineDataSet.setCircleColor(Color.rgb(12, 175, 232));
        lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        lineDataSet.setCircleSize(5f);
        return lineDataSet;
    }
//红线
    private LineDataSet getLineDatasets1(List list) {
        LineDataSet lineDataSets = new LineDataSet(list, "");
        lineDataSets.setDrawCircleHole(false);
        lineDataSets.setFillAlpha(1);
        lineDataSets.setLineWidth(2f);
        lineDataSets.setColor(Color.rgb(253, 100, 94), 255);
        lineDataSets.setCircleColor(Color.rgb(253, 100, 94));
        lineDataSets.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        lineDataSets.setCircleSize(5f);
        return lineDataSets;
    }
}
