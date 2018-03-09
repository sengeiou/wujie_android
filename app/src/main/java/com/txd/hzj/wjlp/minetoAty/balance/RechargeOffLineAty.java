package com.txd.hzj.wjlp.minetoAty.balance;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.ants.theantsgo.tool.ToolKit;
import com.ants.theantsgo.util.JSONUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.http.balance.BalancePst;

import java.util.Map;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/19 0019
 * 时间：上午 9:08
 * 描述：线下充值明细
 * ===============Txunda===============
 */
public class RechargeOffLineAty extends BaseAty {
    @ViewInject(R.id.titlt_conter_tv)
    public TextView titlt_conter_tv;
    private String act_id = "";


    @ViewInject(R.id.bank_card_number_tv1)
    TextView bank_card_number_tv1;
    @ViewInject(R.id.card_owner_name_tv1)
    TextView card_owner_name_tv1;
    @ViewInject(R.id.card_open_ban_tv1)
    TextView card_open_ban_tv1;
    /**
     * 状态
     */


    @ViewInject(R.id.status_tv)
    private TextView status_tv;
    /**
     * 银行卡号
     */
    @ViewInject(R.id.bank_card_number_tv)
    private TextView bank_card_number_tv;
    /**
     * 持卡人
     */
    @ViewInject(R.id.card_owner_name_tv)
    private TextView card_owner_name_tv;
    /**
     * 开户行
     */
    @ViewInject(R.id.card_open_ban_tv)
    private TextView card_open_ban_tv;
    /**
     * 汇款时间
     */
    @ViewInject(R.id.act_time_tv)
    private TextView act_time_tv;
    /**
     * 汇款金额
     */
    @ViewInject(R.id.money_tv)
    private TextView money_tv;
    /**
     * 汇款人
     */
    @ViewInject(R.id.name_tv)
    private TextView name_tv;
    /**
     * 汇款凭证
     */
    @ViewInject(R.id.pic_iv)
    private ImageView pic_iv;

    private int picSize = 0;
    /**
     * 说明
     */
    @ViewInject(R.id.desc_tv)
    private TextView desc_tv;

    private BalancePst balancePst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("线下充值详情");
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_recharge_off_line;
    }

    @Override
    protected void initialized() {
        picSize = ToolKit.dip2px(this, 300);
        balancePst = new BalancePst(this);
        act_id = getIntent().getStringExtra("act_id");
    }

    @Override
    protected void requestData() {
        balancePst.getUnderInfo(act_id);
    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        Map<String, String> map = JSONUtils.parseKeyAndValueToMap(jsonStr);
        if (requestUrl.contains("getUnderInfo")) {
            Map<String, String> data = JSONUtils.parseKeyAndValueToMap(map.get("data"));
            switch (data.get("status")) {
                case "0":
                    status_tv.setText("审核中");
                    break;
                case "1":
                    status_tv.setText("已完成");
                    break;
                case "2":
                    status_tv.setText("已取消");
                    break;
            }
            bank_card_number_tv.setText(data.get("card_number"));
            card_owner_name_tv.setText(data.get("card_name"));
            card_open_ban_tv.setText(data.get("open_bank"));

            card_open_ban_tv1.setText(data.get("p_open_bank"));
            card_owner_name_tv1.setText(data.get("p_bank_name"));
            bank_card_number_tv1.setText(data.get("p_bank_num"));
            act_time_tv.setText(data.get("act_time"));
            money_tv.setText(data.get("money"));
            name_tv.setText(data.get("name"));
            Glide.with(this).load(data.get("pic"))
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .placeholder(R.drawable.ic_default)
                    .error(R.drawable.ic_default)
                    .centerCrop()
                    .override(picSize, picSize)
                    .into(pic_iv);
            desc_tv.setText(data.get("desc"));
        }
    }
}
