package com.txd.hzj.wjlp.mainFgt.mellOnLine;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;

public class AllClassifyAty extends BaseAty {

    /**
     * 标题栏
     */
    @ViewInject(R.id.search_title_layout)
    public RelativeLayout search_title_layout;
    /**
     * 扫一扫
     */
    @ViewInject(R.id.title_scan_tv)
    public TextView title_scan_tv;

    @ViewInject(R.id.title_search_tv)
    public TextView title_search_tv;
    /**
     * 分类
     */
    @ViewInject(R.id.title_classify_tv)
    public TextView title_classify_tv;
    /**
     * 消息
     */
    @ViewInject(R.id.search_title_message_tv)
    public TextView search_title_message_tv;
    /**
     * 消息顶部的图标
     */
    private Drawable top;
    /**
     * 搜索左侧的图标
     */
    private Drawable search_left;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        forTitle();
        showStatusBar(R.id.search_title_layout);
    }

    /**
     * 标题栏
     */
    private void forTitle() {
        search_title_layout.setBackgroundColor(Color.WHITE);
        title_scan_tv.setVisibility(View.GONE);
        title_classify_tv.setVisibility(View.GONE);

        title_search_tv.setBackgroundResource(R.drawable.shape_search_tv_bg);
        title_search_tv.setCompoundDrawables(search_left, null, null, null);
        title_search_tv.setHintTextColor(Color.parseColor("#9E9E9E"));

        search_title_message_tv.setTextColor(ContextCompat.getColor(this, R.color.gray_text_color));
        search_title_message_tv.setCompoundDrawables(null, top, null, null);
    }
    @Override
    protected int getLayoutResId() {
        return R.layout.aty_all_classify;
    }

    @Override
    protected void initialized() {
        top = ContextCompat.getDrawable(this, R.drawable.icon_message_gray);
        top.setBounds(0, 0, top.getMinimumWidth(), top.getMinimumHeight());
        search_left = ContextCompat.getDrawable(this, R.drawable.icon_search_gray);
        search_left.setBounds(0, 0, top.getMinimumWidth(), top.getMinimumHeight());
    }

    @Override
    protected void requestData() {

    }
}
