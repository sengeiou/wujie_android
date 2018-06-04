package com.txd.hzj.wjlp.minetoAty.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;

import java.util.List;
import java.util.Map;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/20 0020
 * 时间：19:03
 * 描述：查看全部号码
 * ===============Txunda===============
 */

public class CheckNumDialog extends Dialog implements View.OnClickListener {

    private List<Map<String,String>> nums;

    private TextView join_times_tv;

    private CanDismess canDismess;

    private Context context;

    private NumAdapter numAdapter;

    private ListView num_dialog_lv;

    public CheckNumDialog(@NonNull Context context, List<Map<String,String>> nums, CanDismess canDismess) {
        super(context, R.style.dialog_style);
        this.context = context;
        this.nums = nums;
        this.canDismess = canDismess;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_check_all_num_layout);
        findViewById(R.id.be_dismiss_iv).setOnClickListener(this);
        join_times_tv = (TextView) findViewById(R.id.join_times_tv);
        num_dialog_lv = findViewById(R.id.num_dialog_lv);
        join_times_tv.setText("您参与了" + nums.size() + "次，参与号码如下");
        numAdapter = new NumAdapter();
        num_dialog_lv.setAdapter(numAdapter);
    }

    public interface CanDismess {
        void onClick(View view);
    }

    @Override
    public void onClick(View view) {
        if (canDismess != null) {
            canDismess.onClick(view);
        }
    }

    private class NumAdapter extends BaseAdapter {

        private NumVH numVH;

        @Override
        public int getCount() {
            return nums.size();
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
                view = LayoutInflater.from(context).inflate(R.layout.item_dialog_num_lv, null);
                numVH = new NumVH();
                ViewUtils.inject(numVH, view);
                view.setTag(numVH);
            } else {
                numVH = (NumVH) view.getTag();
            }
            numVH.num_record_tv.setText(nums.get(i).get("number_list"));
            return view;
        }

        class NumVH {
            @ViewInject(R.id.num_record_tv)
            private TextView num_record_tv;
        }
    }

}
