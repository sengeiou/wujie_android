package com.txd.hzj.wjlp.minetoaty.storemanagement;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 创建者：zhangyunfei
 * 创建时间：2019/1/14 8:59
 * 功能描述：日期范围
 */
public class DateRangeAty extends BaseAty {

    private Context mContext;

    @ViewInject(R.id.titlt_conter_tv)
    private TextView titlt_conter_tv;

    @ViewInject(R.id.startDateTv)
    private TextView startDateTv;

    @ViewInject(R.id.endDateTv)
    private TextView endDateTv;

    @ViewInject(R.id.salePriceEdit)
    private EditText salePriceEdit;

    @ViewInject(R.id.balancePriceEdit)
    private EditText balancePriceEdit;

    private String mStartDate;
    private String mEndDate;

    /**
     * takeaway  外卖
     * dinner  堂食
     */
    private String mType;
    private String mData;


    @Override
    protected int getLayoutResId() {
        return R.layout.activity_date_range;
    }

    @Override
    protected void initialized() {
        mContext = this;
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("日期范围");
        mType = getIntent().getStringExtra("type");
        mData = getIntent().getStringExtra("DateRangeAty");

        if (mData != null){
            parseData();
        }

    }

    private void parseData() {
        try {
            JSONObject jsonObject = new JSONObject(mData);
            String start_time = jsonObject.optString("start_time");
            String end_time = jsonObject.optString("end_time");
            String price = jsonObject.optString("price");
            String jiesuan_price = jsonObject.optString("jiesuan_price");
            mStartDate = start_time;
            startDateTv.setText(start_time);
            mEndDate = end_time;
            endDateTv.setText(end_time);
            salePriceEdit.setText(price);
            balancePriceEdit.setText(jiesuan_price);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void requestData() {

    }

    @Override
    @OnClick({R.id.startDateLayout, R.id.endDateLayout,R.id.sureTv})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.startDateLayout:
                setDate("start");
                break;
            case R.id.endDateLayout:
                setDate("end");
                break;
            case R.id.sureTv:
                break;
        }
    }


    private void setDate(final String status){
        TimePickerView timePickerView = new TimePickerView.Builder(mContext, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
                String format = df.format(date);
                if (status.equals("start")){
                    mStartDate = format;
                    startDateTv.setText(format);
                }else if (status.equals("end")){
                    mEndDate = format;
                    endDateTv.setText(format);
                }
            }
        })
                .setType(new boolean[]{true, true, true, false, false, false})// 默认全部显示
                .build();
        timePickerView.show();
    }
}
