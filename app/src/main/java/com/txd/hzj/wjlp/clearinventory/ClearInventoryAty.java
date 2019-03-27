package com.txd.hzj.wjlp.clearinventory;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.ants.theantsgo.config.Config;
import com.ants.theantsgo.httpTools.ApiTool2;
import com.ants.theantsgo.util.JSONUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.tool.Util;

import java.util.Map;

/**
 * 创建者：zhangyunfei
 * 创建时间：2019/2/28 9:01
 * 功能描述：互清库存
 */
public class ClearInventoryAty extends BaseAty {
    @ViewInject(R.id.titlt_conter_tv)
    private TextView titlt_conter_tv;

    @ViewInject(R.id.gradeTv)
    private TextView gradeTv;

    @ViewInject(R.id.moneyTv)
    private TextView moneyTv;

    @ViewInject(R.id.couldSaleTv)
    private TextView couldSaleTv;

    @ViewInject(R.id.consignmentTv)
    private TextView consignmentTv;

    @ViewInject(R.id.completeTv)
    private TextView completeTv;
    @Override
    protected int getLayoutResId() {
        return R.layout.activity_clear_inventory;
    }

    @Override
    protected void initialized() {
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("互清库存");
    }

    @Override
    protected void requestData() {
        ApiTool2 apiTool2 = new ApiTool2();
        RequestParams params = new RequestParams();
        params.addBodyParameter("token", Config.getToken());
        apiTool2.postApi(Config.SHARE_URL+"index.php/Api/Clean/index",params,this);
    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        Map<String, String> map = JSONUtils.parseKeyAndValueToMap(jsonStr);
        if (requestUrl.endsWith("Clean/index")){
            Map<String, String> data = JSONUtils.parseKeyAndValueToMap(map.get("data"));
            /**
             * "can_sale_num": "0",//可寄售数量
             "saling_num": "3",//已寄售件数
             "ready_num": "2",//已完成件数
             "income": "48.00"//我的寄售收益
             "level"://级别
             */
            moneyTv.setText(Util.judgexistkey(data,"income","0"));
            couldSaleTv.setText(Util.judgexistkey(data,"can_sale_num","0"));
            consignmentTv.setText(Util.judgexistkey(data,"saling_num","0"));
            completeTv.setText(Util.judgexistkey(data,"ready_num","0"));
            String level = Util.judgexistkey(data, "level", "0");
            if (level.equals("")){
                gradeTv.setText("初级");
            }else if (level.equals("")){
                gradeTv.setText("中级");
            }else if (level.equals("")){
                gradeTv.setText("高级");
            }
        }
    }

    @Override
    @OnClick({R.id.saleLayout, R.id.tradeLayout,R.id.pickUpGoodsLayout, R.id.refundLayout,R.id.manageLayout, R.id.incomeLayout,R.id.strategyLayout, R.id.problemLayout})
    public void onClick(View view) {
        Bundle bundle;
        switch (view.getId()) {
            case R.id.saleLayout:
                startActivity(ConsignmentAty.class,null);
                break;
            case R.id.tradeLayout:
                startActivity(AlreadyTradedAty.class,null);
                break;
            case R.id.pickUpGoodsLayout:
                startActivity(PickupAty.class,null);
                break;
            case R.id.refundLayout:
                startActivity(AlreadyRefundedAty.class,null);
                break;
            case R.id.manageLayout:
                startActivity(ManageAty.class,null);
                break;
            case R.id.incomeLayout:
                break;
            case R.id.strategyLayout:
                bundle = new Bundle();
                bundle.putString("name","寄存攻略");
                startActivity(StrategyAndProblemAty.class,bundle);
                break;
            case R.id.problemLayout:
                bundle = new Bundle();
                bundle.putString("name","常见问题");
                startActivity(StrategyAndProblemAty.class,bundle);
                break;
                default:
                    break;
        }
    }
}
