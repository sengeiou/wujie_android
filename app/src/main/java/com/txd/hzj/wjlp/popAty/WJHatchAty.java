package com.txd.hzj.wjlp.popAty;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.ants.theantsgo.config.Settings;
import com.ants.theantsgo.tool.ToolKit;
import com.ants.theantsgo.util.JSONUtils;
import com.ants.theantsgo.view.inScroll.ListViewForScrollView;
import com.ants.theantsgo.view.pulltorefresh.PullToRefreshBase;
import com.ants.theantsgo.view.pulltorefresh.PullToRefreshScrollView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.http.companyDevelop.CompanyDevelopPst;
import com.txd.hzj.wjlp.mellonLine.NoticeDetailsAty;
import com.txd.hzj.wjlp.mellonLine.gridClassify.MellInfoAty;
import com.txd.hzj.wjlp.mellonLine.gridClassify.TicketGoodsDetialsAty;
import com.txd.hzj.wjlp.popAty.adapter.RedPackageAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 作者：DUKE_HwangZj
 * 日期：2017/7/24 0024
 * 时间：下午 7:51
 * 描述：上市孵化
 */
public class WJHatchAty extends BaseAty {

    @ViewInject(R.id.titlt_conter_tv)
    public TextView titlt_conter_tv;

    @ViewInject(R.id.online_carvouse_view)
    private CarouselView online_carvouse_view;

    /**
     * 轮播图图片
     */
    private ArrayList<Map<String, String>> image;

    @ViewInject(R.id.hatch_lv)
    private ListViewForScrollView hatch_lv;

    private RedPackageAdapter redPackageAdapter;

    @ViewInject(R.id.wjh_sc)
    private PullToRefreshScrollView wjh_sc;

    private List<Map<String, String>> list;
    private int p = 1;

    @ViewInject(R.id.no_data_layout)
    private LinearLayout no_data_layout;

    private CompanyDevelopPst companyDevelopPst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("上市孵化");

        // 设置轮播图高度
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(Settings.displayWidth,
                Settings.displayWidth * 400 / 1242);
        online_carvouse_view.setLayoutParams(layoutParams);

        forBanner();

        hatch_lv.setEmptyView(no_data_layout);


        hatch_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (!TextUtils.isEmpty(list.get(i).get("merchant_id")) && !list.get(i).get("merchant_id").equals("0")) {
                    Bundle bundle = new Bundle();
                    bundle.putString("mell_id", list.get(i).get("merchant_id"));
                    startActivity(MellInfoAty.class, bundle);
                } else if (!TextUtils.isEmpty(list.get(i).get("goods_id")) && !list.get(i).get("goods_id").equals("0")) {
                    Bundle bundle = new Bundle();
                    bundle.putString("ticket_buy_id", list.get(i).get("goods_id"));
                    bundle.putInt("from", 1);
                    startActivity(TicketGoodsDetialsAty.class, bundle);
                } else {
                    bundle = new Bundle();
                    bundle.putString("company_id", list.get(i).get("company_id"));
                    startActivity(HatchDetailsAty.class, bundle);
                }


            }
        });

        wjh_sc.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ScrollView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                p = 1;
                companyDevelopPst.companyList(p, 0);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                p++;
                companyDevelopPst.companyList(p, 0);
            }
        });

        wjh_sc.scrollTo(0, 0);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_wjhatch_hzj;
    }

    @Override
    protected void initialized() {
        list = new ArrayList<>();
        companyDevelopPst = new CompanyDevelopPst(this);
        image = new ArrayList<>();
    }

    @Override
    protected void requestData() {
        companyDevelopPst.companyList(p, 0);
    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        Map<String, String> map = JSONUtils.parseKeyAndValueToMap(jsonStr);
        if (requestUrl.contains("companyList")) {
            if (ToolKit.isList(map, "data")) {
                Map<String, String> data = JSONUtils.parseKeyAndValueToMap(map.get("data"));
                if (1 == p) {
                    if (ToolKit.isList(data, "ads")) {
                        image = JSONUtils.parseKeyAndValueToMapList(data.get("ads"));
                        forBanner();
                    }
                    if (ToolKit.isList(data, "mer_list")) {
                        list = JSONUtils.parseKeyAndValueToMapList(data.get("mer_list"));
                        redPackageAdapter = new RedPackageAdapter(this, 0, list);
                        hatch_lv.setAdapter(redPackageAdapter);
                    }
                } else {
                    if (ToolKit.isList(data, "mer_list")) {
                        list.addAll(JSONUtils.parseKeyAndValueToMapList(data.get("mer_list")));
                        redPackageAdapter.notifyDataSetChanged();
                    }
                }

            }
            wjh_sc.onRefreshComplete();
        }
    }

    /**
     * 轮播图
     */
    private void forBanner() {
        online_carvouse_view.setImageListener(imageListener);
        online_carvouse_view.setPageCount(image.size());
    }

    private Bundle bundle;
    /**
     * 轮播图的点击事件
     */
    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(final int position, ImageView imageView) {

            Glide.with(WJHatchAty.this).load(image.get(position).get("picture"))
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .override(Settings.displayWidth, Settings.displayWidth * 400 / 1242)
                    .placeholder(R.drawable.ic_default)
                    .error(R.drawable.ic_default)
                    .centerCrop()
                    .into(imageView);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    bundle = new Bundle();
                    bundle.putString("desc", image.get(position).get("desc"));
                    bundle.putString("href", image.get(position).get("href"));
                    bundle.putInt("from", 2);
                    startActivity(NoticeDetailsAty.class, bundle);
                }
            });
        }
    };
}
