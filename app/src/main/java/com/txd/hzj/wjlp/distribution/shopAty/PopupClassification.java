package com.txd.hzj.wjlp.distribution.shopAty;

import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.distribution.adapter.ShopUpGoodsAdapet;

import java.util.ArrayList;

/**
 * 创建者：Qyl
 * 创建时间：2018/6/8 0008 15:27
 * 功能描述：
 * 联系方式：无
 */
public class PopupClassification extends BaseAty implements View.OnClickListener, OnItemClickListener {

    private TextView title;
    private LinearLayout ll_view;
    private ImageView back;
    private GridView grView;
    private ArrayList<String> list;
    private ShopUpGoodsAdapet myAdapter;





    @Override
    protected int getLayoutResId() {
        return R.layout.shop_up_goods_popup_item;
    }

    @Override
    protected void initialized() {
        title = findViewById(R.id.titlt_conter_tv);
        ll_view = findViewById(R.id.views);
        back = findViewById(R.id.popup_back);
        grView = findViewById(R.id.gr_view);
        grView.setGravity(Gravity.CENTER);
        list = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            list.add("视频生鲜");
        }
        myAdapter = new ShopUpGoodsAdapet(this, list);
        //注册点击事件
        ll_view.setOnClickListener(this);
        back.setOnClickListener(this);
        grView.setOnItemClickListener(this);
    }

    @Override
    protected void requestData() {
        title.setText("小店上货");
        grView.setAdapter(myAdapter);

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.views:
                PopupClassification.this.finish();
                break;
            case R.id.popup_back:
                PopupClassification.this.finish();
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        super.onItemClick(parent, view, position, id);
        myAdapter.setseclection(position);
        myAdapter.notifyDataSetChanged();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0,R.anim.push_top_out);
    }
}
