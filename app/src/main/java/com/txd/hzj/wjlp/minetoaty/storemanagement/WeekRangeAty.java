package com.txd.hzj.wjlp.minetoaty.storemanagement;

import android.content.Context;
import android.widget.EditText;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 创建者：zhangyunfei
 * 创建时间：2019/1/14 9:46
 * 功能描述：
 */
public class WeekRangeAty extends BaseAty {
    private Context mContext;

    @ViewInject(R.id.titlt_conter_tv)
    private TextView titlt_conter_tv;

    @ViewInject(R.id.salePriceEdit1)
    private EditText salePriceEdit1;

    @ViewInject(R.id.balancePriceEdit1)
    private EditText balancePriceEdit1;

    @ViewInject(R.id.salePriceEdit2)
    private EditText salePriceEdit2;

    @ViewInject(R.id.balancePriceEdit2)
    private EditText balancePriceEdit2;

    @ViewInject(R.id.salePriceEdit3)
    private EditText salePriceEdit3;

    @ViewInject(R.id.balancePriceEdit3)
    private EditText balancePriceEdit3;

    @ViewInject(R.id.salePriceEdit4)
    private EditText salePriceEdit4;

    @ViewInject(R.id.balancePriceEdit4)
    private EditText balancePriceEdit4;

    @ViewInject(R.id.salePriceEdit5)
    private EditText salePriceEdit5;

    @ViewInject(R.id.balancePriceEdit5)
    private EditText balancePriceEdit5;

    @ViewInject(R.id.salePriceEdit6)
    private EditText salePriceEdit6;

    @ViewInject(R.id.balancePriceEdit6)
    private EditText balancePriceEdit6;

    @ViewInject(R.id.salePriceEdit7)
    private EditText salePriceEdit7;

    @ViewInject(R.id.balancePriceEdit7)
    private EditText balancePriceEdit7;

    /**
     * takeaway  外卖
     * dinner  堂食
     */
    private String mType;
    private String mData;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_week_range;
    }

    @Override
    protected void initialized() {
        mContext = this;
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("星期范围");
        mType = getIntent().getStringExtra("type");
        mData = getIntent().getStringExtra("WeekRangeAty");
        if (mData != null){
            parseData();
        }
    }

    private void parseData() {
        try {
            JSONObject jsonObject = new JSONObject(mData);
            String monday = jsonObject.optString("monday");
            String monday_jiesuan = jsonObject.optString("monday_jiesuan");
            salePriceEdit1.setText(monday);
            balancePriceEdit1.setText(monday_jiesuan);
            String tuesday = jsonObject.optString("tuesday");
            String tuesday_jiesuan = jsonObject.optString("tuesday_jiesuan");
            salePriceEdit2.setText(tuesday);
            balancePriceEdit2.setText(tuesday_jiesuan);
            String wednesday = jsonObject.optString("wednesday");
            String wednesday_jiesuan = jsonObject.optString("wednesday_jiesuan");
            salePriceEdit3.setText(wednesday);
            balancePriceEdit3.setText(wednesday_jiesuan);
            String thursday = jsonObject.optString("thursday");
            String thursday_jiesuan = jsonObject.optString("thursday_jiesuan");
            salePriceEdit4.setText(thursday);
            balancePriceEdit4.setText(thursday_jiesuan);
            String friday = jsonObject.optString("friday");
            String friday_jiesuan = jsonObject.optString("friday_jiesuan");
            salePriceEdit5.setText(friday);
            balancePriceEdit5.setText(friday_jiesuan);
            String saturday = jsonObject.optString("saturday");
            String saturday_jiesuan = jsonObject.optString("saturday_jiesuan");
            salePriceEdit6.setText(saturday);
            balancePriceEdit6.setText(saturday_jiesuan);
            String sunday = jsonObject.optString("sunday");
            String sunday_jiesuan = jsonObject.optString("sunday_jiesuan");
            salePriceEdit7.setText(sunday);
            balancePriceEdit7.setText(sunday_jiesuan);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void requestData() {

    }
}
