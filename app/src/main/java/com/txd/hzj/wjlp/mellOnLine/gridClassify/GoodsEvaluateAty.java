package com.txd.hzj.wjlp.mellOnLine.gridClassify;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.mellOnLine.adapter.GoodsEvalusteAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/10 0010
 * 时间：下午 5:49
 * 描述：商品评价
 * ===============Txunda===============
 */
public class GoodsEvaluateAty extends BaseAty {

    @ViewInject(R.id.titlt_conter_tv)
    public TextView titlt_conter_tv;

    @ViewInject(R.id.goods_evaluste_lv)
    private ListView goods_evaluste_lv;

    private List<String> data;
    private List<String> pic;
    private GoodsEvalusteAdapter goodsEvalusteAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("全部评价(45)");
        goods_evaluste_lv.setAdapter(goodsEvalusteAdapter);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_goods_evaluate;
    }

    @Override
    protected void initialized() {
        data = new ArrayList<>();
        pic = new ArrayList<>();
        goodsEvalusteAdapter = new GoodsEvalusteAdapter(this, data, pic);
    }

    @Override
    protected void requestData() {
    }


}
