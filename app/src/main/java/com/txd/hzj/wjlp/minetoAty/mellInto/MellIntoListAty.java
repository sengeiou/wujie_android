package com.txd.hzj.wjlp.minetoAty.mellInto;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ants.theantsgo.tool.ToolKit;
import com.ants.theantsgo.util.JSONUtils;
import com.bumptech.glide.Glide;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.http.user.User;
import com.txd.hzj.wjlp.http.user.UserPst;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/8/11 0011
 * 时间：上午 11:41
 * 描述：推荐列表
 * ===============Txunda===============
 */
public class MellIntoListAty extends BaseAty {

    /**
     * 推荐列表
     */
    @ViewInject(R.id.titlt_conter_tv)
    public TextView titlt_conter_tv;

    @ViewInject(R.id.mell_into_lv)
    private ListView mell_into_lv;
    private MellIntoAdapter mellInto;

    private UserPst userPst;

    private List<Map<String, String>> data;

    private int size = 0;

    /**
     * 空视图
     */
    @ViewInject(R.id.no_data_layout)
    private LinearLayout no_data_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("推荐列表");
        mell_into_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Bundle bundle = new Bundle();
                bundle.putString("refer_id", data.get(i).get("refer_id"));
                startActivity(MellIntoInfoAty.class, bundle);
            }
        });
        mell_into_lv.setEmptyView(no_data_layout);
    }

    @Override
    @OnClick({R.id.to_mell_into_tv})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.to_mell_into_tv:// 推荐商家
                startActivity(MerchantWillMoveIntoAty.class, null);
                break;
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_mell_into_list_hzj;
    }

    @Override
    protected void initialized() {
        userPst = new UserPst(this);
        data = new ArrayList<>();
        size = ToolKit.dip2px(this, 80);
    }

    @Override
    protected void requestData() {
    }

    @Override
    protected void onResume() {
        super.onResume();
        userPst.referList();
    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        Map<String, String> map = JSONUtils.parseKeyAndValueToMap(jsonStr);
        if (requestUrl.contains("referList")) {
            if (ToolKit.isList(map, "data")) {
                data = JSONUtils.parseKeyAndValueToMapList(map.get("data"));
                mellInto = new MellIntoAdapter();
                mell_into_lv.setAdapter(mellInto);
            }
        }
    }

    private class MellIntoAdapter extends BaseAdapter {
        private MIVH mivh;

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Map<String, String> getItem(int i) {
            return data.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            Map<String, String> referMell = getItem(i);
            if (view == null) {
                view = LayoutInflater.from(MellIntoListAty.this).inflate(R.layout.item_mell_into_lv, null);
                mivh = new MIVH();
                ViewUtils.inject(mivh, view);
                view.setTag(mivh);
            } else {
                mivh = (MIVH) view.getTag();
            }

            Glide.with(MellIntoListAty.this).load(referMell.get("product_pic"))
                    .override(size, size).centerCrop()
                    .placeholder(R.drawable.ic_default)
                    .error(R.drawable.ic_default)
                    .into(mivh.refer_mell_logo_iv);

            mivh.refer_mell_name_tv.setText(referMell.get("name"));
            mivh.refer_create_time_tv.setText(referMell.get("create_time"));
            String status = referMell.get("status");

            if (status.equals("0")) {
                mivh.refer_status_tv.setText("待审核");
            } else if (status.equals("1")) {
                mivh.refer_status_tv.setText("推荐成功");
            } else {
                mivh.refer_status_tv.setText("审核未通过");
            }
            return view;
        }

        class MIVH {
            /**
             * 推荐商家logo
             */
            @ViewInject(R.id.refer_mell_logo_iv)
            private ImageView refer_mell_logo_iv;

            /**
             * 商户名称
             */
            @ViewInject(R.id.refer_mell_name_tv)
            private TextView refer_mell_name_tv;

            /**
             * 推荐时间
             */
            @ViewInject(R.id.refer_create_time_tv)
            private TextView refer_create_time_tv;
            /**
             * 推荐状态
             */
            @ViewInject(R.id.refer_status_tv)
            private TextView refer_status_tv;

        }
    }

}
