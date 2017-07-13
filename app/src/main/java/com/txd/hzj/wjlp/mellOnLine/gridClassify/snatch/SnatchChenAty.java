package com.txd.hzj.wjlp.mellOnLine.gridClassify.snatch;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;

/**
 *
 *
 *
 * Created by Administrator on 2017/7/13.
 */
/**
 * ===============Txunda===============
 * 作者：chen
 * 日期：2017/7/13 0007
 * 时间：下午17:10
 * 描述： 一元夺宝(11-1房产购)
 * ===============Txunda===============
 */
public class SnatchChenAty extends BaseAty{
    @ViewInject(R.id.titlt_conter_tv)//标题
    private TextView titlt_conter_tv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**
         * 沉浸式解决顶部标题重叠
         */
        showStatusBar(R.id.title_re_layout);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_snatch_chen;
    }

    @Override
    protected void initialized() {
        titlt_conter_tv.setText("一元夺宝");

    }

    @Override
    protected void requestData() {

    }
}
