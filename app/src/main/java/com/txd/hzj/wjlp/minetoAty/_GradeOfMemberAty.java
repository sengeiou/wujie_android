package com.txd.hzj.wjlp.minetoAty;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ants.theantsgo.tool.ToolKit;
import com.ants.theantsgo.util.JSONUtils;
import com.ants.theantsgo.util.L;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.http.user.UserPst;
import com.txd.hzj.wjlp.listener.ItemClickForRecyclerView;
import com.txd.hzj.wjlp.minetoAty.adapter.GrowthValueAdapter;
import com.txd.hzj.wjlp.minetoAty.tricket.ParticularsUsedByTricketAty;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.gavinliu.android.lib.shapedimageview.ShapedImageView;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/17 0017
 * 时间：上午 10:19
 * 描述：会员
 * ===============Txunda===============
 */
public class _GradeOfMemberAty extends BaseAty {
    @ViewInject(R.id.titlt_conter_tv)
    private TextView titlt_conter_tv;

    @OnClick({R.id.layout_v1})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.layout_v1:


                break;
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        showStatusBar(R.id.title_re_layout);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout._aty_grade_of_member;
    }

    @Override
    protected void initialized() {
        titlt_conter_tv.setText("会员等级");
    }

    @Override
    protected void requestData() {

    }

}
