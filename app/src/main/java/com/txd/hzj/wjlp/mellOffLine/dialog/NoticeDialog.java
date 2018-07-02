package com.txd.hzj.wjlp.mellOffLine.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/29 0029
 * 时间：09:11
 * 描述：
 * ===============Txunda===============
 */

public class NoticeDialog extends Dialog {

    private TextView dialog_titlt_tv;
    private ListView coup_lv;
    private TextView notice_content_tv;
    private ScrollView notice_content_sv;
    private Context context;
    private NoticeAdapter noticeAdapter;

    public NoticeDialog(@NonNull Context context) {
        super(context, R.style.dialog_style);
        this.context = context;
        noticeAdapter = new NoticeAdapter();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_coupon_layout);

        dialog_titlt_tv = findViewById(R.id.dialog_titlt_tv);
        dialog_titlt_tv.setText("活动公告");
        coup_lv = findViewById(R.id.coup_lv);
        coup_lv.setVisibility(View.GONE);
        findViewById(R.id.be_dimiss_iv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        notice_content_tv = findViewById(R.id.notice_content_tv);
        notice_content_sv = findViewById(R.id.notice_content_sv);

//        notice_content_tv.setVisibility(View.VISIBLE);
        notice_content_sv.setVisibility(View.VISIBLE);
    }

    private class NoticeAdapter extends BaseAdapter {

        private NtVh ntVh;

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
                view = LayoutInflater.from(context).inflate(R.layout.item_text_lv_hzj, null);
                ntVh = new NtVh();
                ViewUtils.inject(ntVh, view);
                view.setTag(ntVh);
            } else {
                ntVh = (NtVh) view.getTag();
            }

            ntVh.text_context_tv.setText("这是公告，这是公告");

            return view;
        }

        class NtVh {
            @ViewInject(R.id.text_context_tv)
            private TextView text_context_tv;
        }
    }

}
