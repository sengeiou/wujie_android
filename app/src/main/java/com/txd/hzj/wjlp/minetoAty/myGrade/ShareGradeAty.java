package com.txd.hzj.wjlp.minetoAty.myGrade;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ants.theantsgo.view.inScroll.ListViewForScrollView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.citySelect.CitySelectAty;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/21 0021
 * 时间：上午 10:44
 * 描述：工作成绩
 * ===============Txunda===============
 */
public class ShareGradeAty extends BaseAty {


    /**
     * 最外层布局
     */
    @ViewInject(R.id.grade_root_layout)
    private CoordinatorLayout root_layout;

    /**
     * AppBarLAyout
     */
    @ViewInject(R.id.grade_app_bar_layout)
    private AppBarLayout app_bar_layout;

    /**
     * CollapsingToolbarLayout
     */
    @ViewInject(R.id.sh_collapsing_toolbar_layout)
    private CollapsingToolbarLayout collapsing_toolbar_layout;

    /**
     * ToolBar
     */
    @ViewInject(R.id.sh_toolbar)
    private Toolbar toolbar;

    @ViewInject(R.id.sh_left_tv)
    private TextView left_tv;
    @ViewInject(R.id.sh_left_view)
    private View left_view;

    @ViewInject(R.id.sh_righr_tv)
    private TextView right_tv;
    @ViewInject(R.id.sh_right_view)
    private View right_view;

    @ViewInject(R.id.my_share_grade_lv)
    private ListViewForScrollView my_share_grade_lv;

    private RankingListAdapter rankingListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        // 沉浸式
        showStatusBar(R.id.sh_toolbar);
        app_bar_layout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                collapsing_toolbar_layout.setTitle(" ");
            }
        });

        changeViewStatus(0);
        my_share_grade_lv.setAdapter(rankingListAdapter);
    }

    @Override
    @OnClick({R.id.recommend_success_layout, R.id.sh_left_lin_layout, R.id.sh_right_lin_layout, R.id
            .grade_location_tv, R.id.share_time_layout})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.share_time_layout:// 分享次数
                startActivity(ShareTimesAty.class, null);
                break;
            case R.id.recommend_success_layout:// 推荐成功
                startActivity(RecommendSuccessAty.class, null);
                break;
            case R.id.sh_left_lin_layout:// 左(分享榜)
                changeViewStatus(0);
                break;
            case R.id.sh_right_lin_layout:// 右(推荐榜)
                changeViewStatus(1);
                break;
            case R.id.grade_location_tv:// 地址选择
                startActivity(CitySelectAty.class, null);
                break;
        }
    }

    private void changeViewStatus(int i) {
        left_tv.setTextColor(ContextCompat.getColor(this, R.color.app_text_color));
        right_tv.setTextColor(ContextCompat.getColor(this, R.color.app_text_color));
        left_view.setBackgroundColor(ContextCompat.getColor(this, R.color.white));
        right_view.setBackgroundColor(ContextCompat.getColor(this, R.color.white));
        if (0 == i) {
            left_tv.setTextColor(Color.parseColor("#E60012"));
            left_view.setBackgroundColor(Color.parseColor("#E60012"));
        } else {
            right_tv.setTextColor(Color.parseColor("#E60012"));
            right_view.setBackgroundColor(Color.parseColor("#E60012"));
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_share_grade;
    }

    @Override
    protected void initialized() {
        rankingListAdapter = new RankingListAdapter();
    }

    @Override
    protected void requestData() {

    }

    private class RankingListAdapter extends BaseAdapter {

        private RLVH rlvh;
        private int imageId;

        @Override
        public int getCount() {
            return 10;
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
                view = LayoutInflater.from(ShareGradeAty.this).inflate(R.layout.item_share_grade_lv, null);
                rlvh = new RLVH();
                ViewUtils.inject(rlvh, view);
                view.setTag(rlvh);
            } else {
                rlvh = (RLVH) view.getTag();
            }

            if (5 > i && 3 <= i) {
                rlvh.top_from_four_to_five_iv.setVisibility(View.VISIBLE);
                rlvh.top_three_iv.setVisibility(View.GONE);
                imageId = getResources().getIdentifier("icon_ranking_" + i, "drawable", getPackageName());
                rlvh.top_from_four_to_five_iv.setImageResource(imageId);
            } else if (3 > i) {
                rlvh.top_from_four_to_five_iv.setVisibility(View.GONE);
                rlvh.top_three_iv.setVisibility(View.VISIBLE);
                imageId = getResources().getIdentifier("icon_ranking_" + i, "drawable", getPackageName());
                rlvh.top_three_iv.setImageResource(imageId);
            } else {
                rlvh.top_from_four_to_five_iv.setVisibility(View.GONE);
                rlvh.top_three_iv.setVisibility(View.GONE);
            }

            return view;
        }

        class RLVH {
            /**
             * 前三的标签
             */
            @ViewInject(R.id.top_three_iv)
            private ImageView top_three_iv;
            /**
             * 第四第五的标签
             */
            @ViewInject(R.id.top_from_four_to_five_iv)
            private ImageView top_from_four_to_five_iv;
        }
    }

}
