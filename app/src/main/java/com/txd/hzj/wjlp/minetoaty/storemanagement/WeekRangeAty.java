package com.txd.hzj.wjlp.minetoaty.storemanagement;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.tool.MessageEvent;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
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
            JSONArray array = new JSONArray(mData);
            if (array.length() == 7){
                for (int i = 0; i < array.length(); i++) {
                    JSONObject jsonObject = array.getJSONObject(i);
                    String price = jsonObject.optString("price");
                    String jiesuan_price = jsonObject.optString("jiesuan_price");
                    if (i == 0){
                        salePriceEdit1.setText(price);
                        balancePriceEdit1.setText(jiesuan_price);
                    }else if (i==1){
                        salePriceEdit2.setText(price);
                        balancePriceEdit2.setText(jiesuan_price);
                    }else if (i==2){
                        salePriceEdit3.setText(price);
                        balancePriceEdit3.setText(jiesuan_price);
                    }else if (i==3){
                        salePriceEdit4.setText(price);
                        balancePriceEdit4.setText(jiesuan_price);
                    }else if (i==4){
                        salePriceEdit5.setText(price);
                        balancePriceEdit5.setText(jiesuan_price);
                    }else if (i==5){
                        salePriceEdit6.setText(price);
                        balancePriceEdit6.setText(jiesuan_price);
                    }else if (i==6){
                        salePriceEdit7.setText(price);
                        balancePriceEdit7.setText(jiesuan_price);
                    }
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void requestData() {

    }

    @Override
    @OnClick({R.id.sureTv})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sureTv:
                try {
                    JSONArray jsonArray = new JSONArray();
                    for (int i=0; i<7; i++){
                        JSONObject jsonObject = new JSONObject();
                        if (i == 0){
                            jsonObject.put("price",salePriceEdit1.getText().toString());
                            jsonObject.put("jiesuan_price",balancePriceEdit1.getText().toString());
                        }else if (i==1){
                            jsonObject.put("price",salePriceEdit2.getText().toString());
                            jsonObject.put("jiesuan_price",balancePriceEdit2.getText().toString());
                        }else if (i==2){
                            jsonObject.put("price",salePriceEdit3.getText().toString());
                            jsonObject.put("jiesuan_price",balancePriceEdit3.getText().toString());
                        }else if (i==3){
                            jsonObject.put("price",salePriceEdit4.getText().toString());
                            jsonObject.put("jiesuan_price",balancePriceEdit4.getText().toString());
                        }else if (i==4){
                            jsonObject.put("price",salePriceEdit5.getText().toString());
                            jsonObject.put("jiesuan_price",balancePriceEdit5.getText().toString());
                        }else if (i==5){
                            jsonObject.put("price",salePriceEdit6.getText().toString());
                            jsonObject.put("jiesuan_price",balancePriceEdit6.getText().toString());
                        }else if (i==6){
                            jsonObject.put("price",salePriceEdit7.getText().toString());
                            jsonObject.put("jiesuan_price",balancePriceEdit7.getText().toString());
                        }
                        jsonArray.put(jsonObject);
                    }
                    EventBus.getDefault().post(new MessageEvent(mType+"="+jsonArray.toString(),"WeekRangeAty"));
                    finish();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
        }
    }
}
