package com.txd.hzj.wjlp.mellOnLine.gridClassify;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;

import org.w3c.dom.Text;

public class MellInfoAty extends BaseAty {
    /**
     * 返回
     */
    @ViewInject(R.id.search_title_be_back_iv)
    public ImageView search_title_be_back_iv;

    /**
     * 扫一扫
     */
    @ViewInject(R.id.title_scan_tv)
    public TextView title_scan_tv;
    /**
     * 搜索
     */
    @ViewInject(R.id.title_search_tv)
    public TextView title_search_tv;
    /**
     * 搜索框
     */
    @ViewInject(R.id.search_lin_layout)
    public LinearLayout search_lin_layout;
    /**
     * 搜索类
     */
    @ViewInject(R.id.search_type_tv)
    public TextView search_type_tv;
    /**
     * 分类
     */
    @ViewInject(R.id.title_classify_tv)
    public TextView title_classify_tv;
    /**
     * 消息
     */
    @ViewInject(R.id.message_layout)
    public FrameLayout message_layout;
    /**
     *
     */
    @ViewInject(R.id.search_title_right_tv)
    public TextView search_title_right_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.search_title_layout);
        forTitle();
    }

    /**
     * 标题栏
     */
    private void forTitle() {
        search_title_be_back_iv.setVisibility(View.VISIBLE);
        title_scan_tv.setVisibility(View.INVISIBLE);
        title_search_tv.setVisibility(View.GONE);
        search_lin_layout.setVisibility(View.VISIBLE);
        search_type_tv.setVisibility(View.GONE);
        title_classify_tv.setVisibility(View.GONE);
        message_layout.setVisibility(View.GONE);
        search_title_right_tv.setVisibility(View.VISIBLE);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_mell_info_hzj;
    }

    @Override
    protected void initialized() {

    }

    @Override
    protected void requestData() {

    }
}
