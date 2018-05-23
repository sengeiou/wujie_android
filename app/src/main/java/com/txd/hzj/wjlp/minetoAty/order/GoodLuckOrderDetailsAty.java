package com.txd.hzj.wjlp.minetoAty.order;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ants.theantsgo.util.JSONUtils;
import com.ants.theantsgo.util.ListUtils;
import com.bumptech.glide.Glide;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.minetoAty.dialog.CheckNumDialog;
import com.txd.hzj.wjlp.http.IntegralOrder;

import java.util.List;
import java.util.Map;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/20 0020
 * 时间：下午 5:44
 * 描述：一元夺宝订单详情
 * ===============Txunda===============
 */
public class GoodLuckOrderDetailsAty extends BaseAty {

    @ViewInject(R.id.titlt_conter_tv)
    public TextView titlt_conter_tv;

    @ViewInject(R.id.tv_status)
    private TextView tv_status;
    @ViewInject(R.id.imageview)
    private ImageView imageview;
    @ViewInject(R.id.tv_name)
    private TextView tv_name;
    @ViewInject(R.id.tv_num)
    private TextView tv_num;
    @ViewInject(R.id.tv_person_num)
    private TextView tv_person_num;

    private CheckNumDialog checkNumDialog;

    private List<Map<String, String>> nums;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("订单详情");
    }

    @Override
    @OnClick({R.id.check_all_num_tv})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.check_all_num_tv:
                if (ListUtils.isEmpty(nums)) {
                    return;
                }
                checkNumDialog.show();
                break;
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_good_luck_order_details;
    }

    @Override
    protected void initialized() {

    }

    @Override
    protected void requestData() {
        IntegralOrder.details(getIntent().getStringExtra("id"), this);
        showProgressDialog();
    }

    Map<String, String> data;

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        data = JSONUtils.parseKeyAndValueToMap(jsonStr);
        data = JSONUtils.parseKeyAndValueToMap(data.get("data"));
        tv_num.setText(tv_num.getText().toString() + data.get("order_sn"));
        switch (data.get("order_status")) {
            case "10":
                tv_status.setText("进行中");
                break;
            case "11":
                tv_status.setText("未中奖");
                break;
            case "12":
                tv_status.setText("已中奖");
                break;
            default:
                tv_status.setText("状态有误,请联系客服");
                break;
        }
        data = JSONUtils.parseKeyAndValueToMap(data.get("list"));
        Glide.with(this).load(data.get("goods_img")).into(imageview);
        tv_name.setText(data.get("goods_name"));
        nums = JSONUtils.parseKeyAndValueToMapList(data.get("number"));
        tv_person_num.setText(tv_person_num.getText().toString() + "" + nums.size() + "次");
        checkNumDialog = new CheckNumDialog(this, nums, new CheckNumDialog.CanDismess() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.be_dismiss_iv:
                        checkNumDialog.dismiss();
                        break;
                }
            }
        });
    }
}
