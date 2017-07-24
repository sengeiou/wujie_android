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

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText(title);
        all_text_lv.setAdapter(tAdapter);
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
        tAdapter = new TextAdapter();
    }

    @Override
    protected void requestData() {

    }

    private class TextAdapter extends BaseAdapter {

        private TVVh tvvh;

        @Override
        public int getCount() {
            return 5;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
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
            }

            return view;
        }

        private class TVVh {
            @ViewInject(R.id.text_context_tv)
            private TextView text_context_tv;
        }

    }

}
