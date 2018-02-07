package com.txd.hzj.wjlp.minetoAty.tricket;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.ants.theantsgo.util.JSONUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.new_wjyp.http.User;

import java.util.Map;

public class IntegralAty extends BaseAty {
    @ViewInject(R.id.titlt_conter_tv)
    public TextView titlt_conter_tv;
    @ViewInject(R.id.layout_bottom_tv)
    private TextView layout_bottom_tv;
    @ViewInject(R.id.tv_date)
    private TextView tv_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("积分");
    }

    @Override
    @OnClick({R.id.check_details_tv, R.id.exchange_money_tv})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.check_details_tv:// 积分明细
                Bundle bundle = new Bundle();
                bundle.putInt("from", 2);
                startActivity(ParticularsUsedByTricketAty.class, bundle);
                break;
            case R.id.exchange_money_tv:// 确认兑换
                bundle = new Bundle();
                bundle.putInt("to", 1);
                startActivity(ExchangeMoneyAty.class, bundle);
                break;
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_integral;
    }

    @Override
    protected void initialized() {

    }

    @Override
    protected void requestData() {
        User.myIntegral(this);
        showProgressDialog();
    }

    Map<String, String> map;

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        if (requestUrl.contains("myIntegral")) {
            map = JSONUtils.parseKeyAndValueToMap(jsonStr);
            map = JSONUtils.parseKeyAndValueToMap(map.get("data"));
            layout_bottom_tv.setText(map.get("my_integral"));
            map = JSONUtils.parseKeyAndValueToMap(map.get("point_list"));
            tv_date.setText(map.get("date"));

        }
    }
}
