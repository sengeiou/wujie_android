package com.txd.hzj.wjlp.minetoAty.order;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.minetoAty.dialog.CheckNumDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/20 0020
 * 时间：下午 5:44
 * 描述：一元夺宝订单详情
 * ===============Txunda===============
 */
public class GoodLuckOrderDetailsAty extends BaseAty {

    @ViewInject(R.id.titlt_right_tv)
    public TextView titlt_right_tv;


    private CheckNumDialog checkNumDialog;

    private List<String> nums;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
        titlt_right_tv.setText("订单详情");
    }

    @Override
    @OnClick({R.id.check_all_num_tv})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.check_all_num_tv:
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
        nums = new ArrayList<>();
    }

    @Override
    protected void requestData() {

    }
}
