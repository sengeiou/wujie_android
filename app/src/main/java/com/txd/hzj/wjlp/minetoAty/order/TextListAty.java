package com.txd.hzj.wjlp.minetoAty.order;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.ants.theantsgo.gson.GsonUtil;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.http.address.AddressPst;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/20 0020
 * 时间：下午 4:48
 * 描述：售后的各种原因，快递。。。。
 * ===============Txunda===============
 */
public class TextListAty extends BaseAty {
    @ViewInject(R.id.titlt_conter_tv)
    public TextView titlt_conter_tv;

    @ViewInject(R.id.all_text_lv)
    private ListView all_text_lv;

    private String title;

    private TextAdapter tAdapter;

    private AddressPst addressPst;

    private List<Map<String, String>> dataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText(title);
        all_text_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent data = new Intent();
                if (title.equals("售后类型")) {
                    data.putExtra("type", "我要退款(无需退货)");
                } else if (title.equals("原因")) {
                    data.putExtra("cause", "我不喜欢");
                } else if (title.equals("货物状态")) {
                    data.putExtra("status", "已收到货物");
                } else if (title.equals("选择快递")) {
                    data.putExtra("express", "申通快递");
                } else if (title.equals("选择经营范围")) {
                    data.putExtra("scope", "服饰");
                } else if (title.equals("选择街道")) {
                    data.putExtra("street", dataList.get(i).get("street_name"));
                    data.putExtra("street_id", dataList.get(i).get("street_id"));
                } else if (title.equals("举报类型")) {
                    data.putExtra("type", "卫生");
                } else if (title.equals("银行卡类型")) {
                    data.putExtra("card_type", "工商银行");
                }
                setResult(RESULT_OK, data);
                finish();
            }
        });
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_text_list;
    }

    @Override
    protected void initialized() {
        title = getIntent().getStringExtra("title");

        addressPst = new AddressPst(this);

        dataList = new ArrayList<>();

    }

    @Override
    protected void requestData() {
        if (title.equals("选择街道")) {
            String area_id = getIntent().getStringExtra("area_id");
            addressPst.getStreet(area_id);
        }
    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        if (requestUrl.contains("getStreet")) {//街道
            Map<String, Object> map = GsonUtil.GsonToMaps(jsonStr);
            dataList = (List<Map<String, String>>) map.get("data");
            tAdapter = new TextAdapter();
            all_text_lv.setAdapter(tAdapter);
        }
    }

    private class TextAdapter extends BaseAdapter {

        private TVVh tvvh;

        @Override
        public int getCount() {
            return dataList.size();
        }

        @Override
        public Map<String, String> getItem(int i) {
            return dataList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            Map<String, String> map = getItem(i);
            if (view == null) {
                view = LayoutInflater.from(TextListAty.this).inflate(R.layout.item_text_lv_hzj, null);
                tvvh = new TVVh();
                ViewUtils.inject(tvvh, view);
                view.setTag(tvvh);
            } else
                tvvh = (TVVh) view.getTag();

            if (title.equals("售后类型")) {
                if (0 == i) {
                    tvvh.text_context_tv.setText("我要退款(无需退货)");
                } else {
                    tvvh.text_context_tv.setText("我要退货");
                }
            } else if (title.equals("原因")) {
                tvvh.text_context_tv.setText("不喜欢");
            } else if (title.equals("货物状态")) {
                tvvh.text_context_tv.setText("已收到货物");
            } else if (title.equals("选择快递")) {
                tvvh.text_context_tv.setText("申通快递");
            } else if (title.equals("选择经营范围")) {
                tvvh.text_context_tv.setText("服饰");
            } else if (title.equals("选择街道")) {
                tvvh.text_context_tv.setText(map.get("street_name"));
            } else if (title.equals("举报类型")) {
                tvvh.text_context_tv.setText("卫生");
            } else if (title.equals("银行卡类型")) {
                tvvh.text_context_tv.setText("工商银行");
            }

            return view;
        }

        private class TVVh {
            @ViewInject(R.id.text_context_tv)
            private TextView text_context_tv;
        }

    }

}
