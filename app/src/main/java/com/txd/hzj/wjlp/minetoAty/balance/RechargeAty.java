package com.txd.hzj.wjlp.minetoAty.balance;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.ants.theantsgo.util.L;
import com.bigkoo.pickerview.TimePickerView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/18 0018
 * 时间：下午 1:39
 * 描述：余额充值(16-2-1充值)
 * ===============Txunda===============
 */
public class RechargeAty extends BaseAty {
    @ViewInject(R.id.titlt_conter_tv)
    public TextView titlt_conter_tv;

    /**
     * 线下充值
     */
    @ViewInject(R.id.re_left_tv)
    private TextView re_left_tv;

    @ViewInject(R.id.re_left_view)
    private View re_left_view;
    /**
     * 线下充值
     */
    @ViewInject(R.id.re_right_tv)
    private TextView re_right_tv;

    @ViewInject(R.id.re_right_view)
    private View re_right_view;
    private int type = 0;
    /**
     * 线上充值
     */
    @ViewInject(R.id.on_line_recharge_layout)
    private LinearLayout on_line_recharge_layout;
    /**
     * 线上充值
     */
    @ViewInject(R.id.off_line_recharge_layout)
    private LinearLayout off_line_recharge_layout;


    /**
     * 微信支付
     */
    @ViewInject(R.id.pay_by_wechat_cb)
    private CheckBox pay_by_wechat_cb;
    /**
     * 支付宝支付
     */
    @ViewInject(R.id.pay_by_ali_cb)
    private CheckBox pay_by_ali_cb;
    /**
     * 余额支付
     */
    @ViewInject(R.id.pay_by_balance_cb)
    private CheckBox pay_by_balance_cb;

    private int bottom_type = 0;

    /**
     * 银行卡号
     */
    @ViewInject(R.id.bank_cart_num_tv)
    private TextView bank_cart_num_tv;
    /**
     * 选择时间
     */
    @ViewInject(R.id.picker_time_tv)
    private TextView picker_time_tv;

    private TimePickerView pvCustomTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("余额");
        changeTVAndViewStyle(type);


        pay_by_balance_cb.setVisibility(View.GONE);
        selectCheckBoxBottom(bottom_type);
    }

    /**
     * 选择充值方式
     *
     * @param type 0，线上充值，1.线下充值
     */
    private void changeTVAndViewStyle(int type) {
        re_left_tv.setTextColor(ContextCompat.getColor(this, R.color.app_text_color));
        re_left_view.setBackgroundColor(ContextCompat.getColor(this, R.color.white));

        re_right_tv.setTextColor(ContextCompat.getColor(this, R.color.app_text_color));
        re_right_view.setBackgroundColor(ContextCompat.getColor(this, R.color.white));

        if (0 == type) {
            re_left_tv.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));
            re_left_view.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent));
            on_line_recharge_layout.setVisibility(View.VISIBLE);
            off_line_recharge_layout.setVisibility(View.GONE);
        } else {
            re_right_tv.setTextColor(ContextCompat.getColor(this, R.color.colorAccent));
            re_right_view.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent));
            on_line_recharge_layout.setVisibility(View.GONE);
            off_line_recharge_layout.setVisibility(View.VISIBLE);
            initCustomTimePicker();
        }

    }

    /**
     * 支付方式选择
     *
     * @param type 方式
     */
    private void selectCheckBoxBottom(int type) {
        L.e("============", String.valueOf(type));
        pay_by_wechat_cb.setChecked(false);
        pay_by_ali_cb.setChecked(false);
        pay_by_balance_cb.setChecked(false);
        if (0 == type) {
            pay_by_wechat_cb.setChecked(true);
        } else if (1 == type) {
            pay_by_ali_cb.setChecked(true);
        }
    }

    @Override
    @OnClick({R.id.re_left_layout, R.id.re_right_layout, R.id.pay_by_wechat_cb,
            R.id.pay_by_ali_cb, R.id.select_card_num_layout, R.id.picker_time_layout})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.re_left_layout:// 线上充值
                type = 0;
                changeTVAndViewStyle(type);
                break;
            case R.id.re_right_layout:// 线下充值
                type = 1;
                changeTVAndViewStyle(type);
                break;
            case R.id.pay_by_wechat_cb:// 微信
                bottom_type = 0;
                selectCheckBoxBottom(bottom_type);
                break;
            case R.id.pay_by_ali_cb:// 支付宝
                bottom_type = 1;
                selectCheckBoxBottom(bottom_type);
                break;
            case R.id.select_card_num_layout:// 线下支付，选择银行卡号
                startActivityForResult(BankInfoForReChargeAty.class, null, 100);
                break;
            case R.id.picker_time_layout:// 线下支付，选择汇款时间

                if (pvCustomTime != null) {
                    pvCustomTime.show(picker_time_tv);
                }

                break;
        }

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_recharge;
    }

    @Override
    protected void initialized() {

    }

    @Override
    protected void requestData() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data == null) {
            return;
        }

        if (RESULT_OK == resultCode) {
            switch (requestCode) {
                case 100:// 银行卡号
                    String card_num = data.getStringExtra("card_num");
                    bank_cart_num_tv.setText(card_num);
                    break;
            }
        }
    }

    private void initCustomTimePicker() {

        //控制时间范围(如果不设置范围，则使用默认时间1900-2100年，此段代码可注释)
        //因为系统Calendar的月份是从0-11的,所以如果是调用Calendar的set方法来设置时间,月份的范围也要是从0-11
        Calendar selectedDate = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        startDate.setTimeInMillis(System.currentTimeMillis());
        int start_year = startDate.get(Calendar.YEAR) - 1;
        int end_year = start_year + 100;
        int mon = startDate.get(Calendar.MONTH);
        int day = startDate.get(Calendar.DAY_OF_MONTH);

        startDate.set(start_year, mon, day);
        Calendar endDate = Calendar.getInstance();
        endDate.set(end_year, mon, day);
        //时间选择器
        pvCustomTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                // 这里回调过来的v,就是show()方法里面所添加的 View 参数，如果show的时候没有添加参数，v则为null
                TextView btn = (TextView) v;
                btn.setText(getTime(date));
            }
        })
                //年月日时分秒 的显示与否，不设置则默认全部显示
                .setType(new boolean[]{true, true, true, true, true, false})
                .setLabel("年", "月", "日", "点", "分", "")
                .isCenterLabel(false)
                .setContentSize(12)
                .setDividerColor(Color.DKGRAY)
                .setContentSize(21)
                .setDate(selectedDate)
                .setRangDate(startDate, endDate)
                .setBackgroundId(0x00FFFFFF) //设置外部遮罩颜色
                .setDecorView(null)
                .setCancelColor(ContextCompat.getColor(RechargeAty.this, R.color.colorAccent))
                .setSubmitColor(ContextCompat.getColor(RechargeAty.this, R.color.colorAccent))
                .setTitleText("选择时间")
                .setTitleColor(ContextCompat.getColor(RechargeAty.this, R.color.app_text_color))
                .build();

    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return format.format(date);
    }

}
