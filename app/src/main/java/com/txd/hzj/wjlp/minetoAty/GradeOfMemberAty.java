package com.txd.hzj.wjlp.minetoAty;

import android.os.Bundle;
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
public class GradeOfMemberAty extends BaseAty {

    @ViewInject(R.id.toolbar)
    private Toolbar toolbar;

    /**
     * CollapsingToolbarLayout
     */
    @ViewInject(R.id.collapsing_toolbar_layout)
    private CollapsingToolbarLayout collapsing_toolbar_layout;

    @ViewInject(R.id.app_bar_layout)
    private AppBarLayout app_bar_layout;

    @ViewInject(R.id.growth_value_rv)
    private RecyclerView growth_value_rv;

    private List<Map<String, String>> list;
    private GrowthValueAdapter gvAdapter;

    /**
     * 成长值(消费金额)
     */
    @ViewInject(R.id.member_growth_value_tv)
    private TextView member_growth_value_tv;

    @ViewInject(R.id.member_title_tv)
    private TextView member_title_tv;

    @ViewInject(R.id.details_for_sb_tv)
    private TextView details_for_sb_tv;

    private int from = 0;

    private UserPst userPst;

    @ViewInject(R.id.uer_head_iv)
    private ShapedImageView uer_head_iv;

    private int size = 0;
    private int iconSize = 0;

    @ViewInject(R.id.rank_icon_iv)
    private ImageView rank_icon_iv;

    @ViewInject(R.id.grade_of_member_tv)
    private TextView grade_of_member_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.toolbar);
        setSupportActionBar(toolbar);
        collapsing_toolbar_layout.setTitle("");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        app_bar_layout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                collapsing_toolbar_layout.setTitle(" ");
            }
        });

        if (0 == from) {
            member_title_tv.setText("会员成长");
            details_for_sb_tv.setVisibility(View.VISIBLE);
        } else {
            member_title_tv.setText("会员等级");
            details_for_sb_tv.setVisibility(View.GONE);
        }

        growth_value_rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        growth_value_rv.setHasFixedSize(true);
        // 会员等级跳页
    }

    @Override
    @OnClick({R.id.details_for_sb_tv})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.details_for_sb_tv:// 成长值
                if (0 == from) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("from", 4);
                    startActivity(ParticularsUsedByTricketAty.class, bundle);
                }
                break;
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_grade_of_member;
    }

    @Override
    protected void initialized() {
        size = ToolKit.dip2px(this, 80);
        iconSize = ToolKit.dip2px(this, 20);
        from = getIntent().getIntExtra("from", 0);
        list = new ArrayList<>();
        userPst = new UserPst(this);
    }

    @Override
    protected void requestData() {
        if (0 == from) {
            userPst.userDevelop();
        } else {
            userPst.userRank();
        }
    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        Map<String, String> map = JSONUtils.parseKeyAndValueToMap(jsonStr);
        if (requestUrl.contains("userDevelop")) {
            Map<String, String> data = JSONUtils.parseKeyAndValueToMap(map.get("data"));
            member_growth_value_tv.setText(data.get("year") + "年度成长值：" + data.get("my_point"));
            grade_of_member_tv.setText(data.get("level"));
            Glide.with(this).load(data.get("head_pic"))
                    .override(size, size)
                    .placeholder(R.drawable.ic_default)
                    .error(R.drawable.ic_default)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(uer_head_iv);
            Glide.with(this).load(data.get("icon"))
                    .override(size, size)
                    .placeholder(R.drawable.ic_default)
                    .error(R.drawable.ic_default)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(rank_icon_iv);
            uer_head_iv.setVisibility(View.VISIBLE);
            if (ToolKit.isList(data, "level_list")) {
                list = JSONUtils.parseKeyAndValueToMapList(data.get("level_list"));
                gvAdapter = new GrowthValueAdapter(this, list, from);
                growth_value_rv.setAdapter(gvAdapter);
            }
            return;
        }
        if (requestUrl.contains("userRank")) {
            Map<String, String> data = JSONUtils.parseKeyAndValueToMap(map.get("data"));
            L.e("=====22222=====",data.get("rank_list"));

            member_growth_value_tv.setText(data.get("end_time") + "到期");
            grade_of_member_tv.setText(data.get("my_rank"));
            Glide.with(this).load(data.get("head_pic"))
                    .override(size, size)
                    .placeholder(R.drawable.ic_default)
                    .error(R.drawable.ic_default)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(uer_head_iv);
            Glide.with(this).load(data.get("icon"))
                    .override(size, size)
                    .placeholder(R.drawable.ic_default)
                    .error(R.drawable.ic_default)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(rank_icon_iv);
            uer_head_iv.setVisibility(View.VISIBLE);
            if (ToolKit.isList(data, "rank_list")) {
                list = JSONUtils.parseKeyAndValueToMapList(data.get("rank_list"));
                gvAdapter = new GrowthValueAdapter(this, list, from);
                growth_value_rv.setAdapter(gvAdapter);
                gvAdapter.setItemClickForRecyclerView(new ItemClickForRecyclerView() {
                    @Override
                    public void OnItemClick(View v, int pos) {

                        L.e("=====点击下标=====", String.valueOf(pos));

                        if (2 <= pos) {
                            Bundle bundle = new Bundle();
                            bundle.putInt("order_type", 0);
                            startActivity(PayForAppAty.class, bundle);
                        }
                    }
                });
            }
        }
    }
}
