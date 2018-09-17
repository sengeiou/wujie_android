package com.txd.hzj.wjlp.mellonLine.gridClassify.fgt;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ants.theantsgo.config.Settings;
import com.ants.theantsgo.gson.GsonUtil;
import com.ants.theantsgo.tool.ToolKit;
import com.ants.theantsgo.util.JSONUtils;
import com.ants.theantsgo.util.ListUtils;
import com.ants.theantsgo.view.banner.BannerAdapter;
import com.ants.theantsgo.view.banner.DotView;
import com.ants.theantsgo.view.banner.SliderBanner;
import com.ants.theantsgo.view.inScroll.GridViewForScrollView;
import com.ants.theantsgo.view.inScroll.ListViewForScrollView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseFgt;
import com.txd.hzj.wjlp.bean.commodity.PicturesBean;
import com.txd.hzj.wjlp.http.house.HouseBuyPst;
import com.txd.hzj.wjlp.mellonLine.gridClassify.adapter.CommentPicAdapter;
import com.txd.hzj.wjlp.mellonLine.gridClassify.adapter.HouseArrtAdapter;
import com.txd.hzj.wjlp.mellonLine.gridClassify.hous.FindHouseByMapAty;
import com.txd.hzj.wjlp.new_wjyp.BeanComment;
import com.txd.hzj.wjlp.view.ObservableScrollView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.gavinliu.android.lib.shapedimageview.ShapedImageView;

/**
 *
 * 作者：chen
 * 日期：2017/7/8 0007
 * 时间：上午 12:06
 * 描述：房产购详情页_楼盘(10-2房产购)
 *
 */

public class HousDetailsHousesChenFgt extends BaseFgt implements ObservableScrollView.ScrollViewListener {
    /**
     * 轮播图
     */
    @ViewInject(R.id.sb_houses_banner)
    private SliderBanner sb_houses_banner;
    @ViewInject(R.id.sb_houses_viewpager)
    private ViewPager sb_houses_viewpager;
    @ViewInject(R.id.sb_houses_dotview)
    private DotView sb_houses_dotview;
    private List<ImageView> albums;//图片
    private List<Map<String, String>> list;//图片
    private MyViewAdapter adapter;//轮播图适配器

    @ViewInject(R.id.scrollView)
    private ObservableScrollView scrollView;

    @ViewInject(R.id.hd_be_back_top_iv)
    private ImageView hd_be_back_top_iv;

    private SkipToComment skipToComment;

    private SetPhone setPhone;

    private String house_id = "";

    private HouseBuyPst houseBuyPst;

    @ViewInject(R.id.house_name_tv)
    private TextView house_name_tv;
    @ViewInject(R.id.house_prive_tv)
    private TextView house_prive_tv;

    @ViewInject(R.id.house_arrt_lv)
    private ListViewForScrollView house_arrt_lv;

    private List<Map<String, String>> houseList;


    /**
     * 商品条数
     */
    @ViewInject(R.id.tv_houses_evaluate)
    private TextView all_comment_num_tv;
    /**
     * 评价商品的买家头像
     */
    @ViewInject(R.id.comm_user_head_iv)
    private ShapedImageView comm_user_head_iv;
    /**
     * 买家头像大小60dp
     */
    private int head_size = 0;
    /**
     * 买家昵称
     */
    @ViewInject(R.id.comm_user_name_tv)
    private TextView comm_user_name_tv;
    /**
     * 评论内容
     */
    @ViewInject(R.id.comm_content_tv)
    private TextView comm_content_tv;
    /**
     * 评论图片
     */
    @ViewInject(R.id.estimate_pic)
    private GridViewForScrollView estimate_pic;

    @ViewInject(R.id.comment_layout)
    private LinearLayout comment_layout;

    @ViewInject(R.id.goods_for_my_evaluste_layout)
    private LinearLayout goods_for_my_evaluste_layout;

    @ViewInject(R.id.house_address_tv)
    private TextView house_address_tv;
    private String lat = "";
    private String lng = "";

    @ViewInject(R.id.tv_houses_evaluate)
    private TextView tv_houses_evaluate;
    @ViewInject(R.id.layout_cmm)
    private RelativeLayout layout_cmm;
    @ViewInject(R.id.comment_layout)
    private LinearLayout layout_comment;

    public static HousDetailsHousesChenFgt getFgt(String house_id) {
        HousDetailsHousesChenFgt housDetailsHousesChenFgt = new HousDetailsHousesChenFgt();
        housDetailsHousesChenFgt.house_id = house_id;
        return housDetailsHousesChenFgt;
    }

    @Override
    public void onAttach(Context context) {
        skipToComment = (SkipToComment) context;
        setPhone = (SetPhone) context;
        super.onAttach(context);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        LayoutParams params = new LayoutParams(Settings.displayWidth, Settings.displayWidth);
        sb_houses_banner.setLayoutParams(params);

        scrollView.setScrollViewListener(this);

        goods_for_my_evaluste_layout.setVisibility(View.GONE);
    }

    @OnClick({R.id.tv_houses_evaluate, R.id.to_check_location_layout, R.id.hd_be_back_top_iv})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_houses_evaluate:
                if (skipToComment != null) {
                    skipToComment.beSkip(true);
                }
                break;
            case R.id.to_check_location_layout:// 查看地图
                Bundle bundle = new Bundle();
                bundle.putString("lng", lng);
                bundle.putString("lat", lat);
                bundle.putString("title", house_name_tv.getText().toString());
                startActivity(FindHouseByMapAty.class, bundle);
                break;
            case R.id.hd_be_back_top_iv:// 回到顶部
                scrollView.smoothScrollTo(0, 0);
                break;
        }
    }

    @Override
    protected void immersionInit() {
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fgt_hous_details_houses_chen;
    }

    @Override
    protected void initialized() {
        houseBuyPst = new HouseBuyPst(this);
        list = new ArrayList<>();
        houseList = new ArrayList<>();
        head_size = ToolKit.dip2px(getActivity(), 60);
    }

    @Override
    protected void requestData() {
        houseBuyPst.houseInfo(house_id);
    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        Map<String, String> map = JSONUtils.parseKeyAndValueToMap(jsonStr);
        if (requestUrl.contains("houseInfo")) {
            Map<String, String> data = JSONUtils.parseKeyAndValueToMap(map.get("data"));
            if (ToolKit.isList(data, "banner")) {
                list = JSONUtils.parseKeyAndValueToMapList(data.get("banner"));
                adapter = new MyViewAdapter();//轮播图适配器初始化
                albums = new ArrayList<>();
                startBanner();//启动轮播图
            }
            house_name_tv.setText(data.get("house_name"));
            house_prive_tv.setText(data.get("min_price") + "-" + data.get("max_price"));
            house_address_tv.setText(data.get("province_name") + data.get("city_name") + data.get("area_name") +
                    data.get("address"));
            lng = data.get("lng");
            lat = data.get("lat");

            if (ToolKit.isList(data, "house_attr")) {
                houseList = JSONUtils.parseKeyAndValueToMapList(data.get("house_attr"));
                HouseArrtAdapter arrtAdapter = new HouseArrtAdapter(getActivity(), houseList);
                house_arrt_lv.setAdapter(arrtAdapter);
            }
            if (setPhone != null) {
                setPhone.setPhone(data.get("link_phone"));
            }
            if (!data.get("comment_num").equals("0")) {
                layout_cmm.setVisibility(View.VISIBLE);
                layout_comment.setVisibility(View.VISIBLE);
                tv_houses_evaluate.setText("更多评价(" + data.get("comment_num")+")");
                //     all_comment_num_tv.setText("商品评价(" + data.get("comment_num") + ")");
                List<BeanComment> comment = GsonUtil.getObjectList(data.get("comment_new"), BeanComment.class);
                Glide.with(this).load(comment.get(0).getHead_pic()).into(comm_user_head_iv);
                comm_user_name_tv.setText(comment.get(0).getNickname());
                comm_content_tv.setText(comment.get(0).getContent());
                List<PicturesBean> pic = comment.get(0).getPictures_arr();
                CommentPicAdapter picadapter = new CommentPicAdapter(getActivity(), pic);
                estimate_pic.setAdapter(picadapter);
            } else {
                layout_cmm.setVisibility(View.GONE);
                layout_comment.setVisibility(View.GONE);
            }
//            // 评论
//            if (ToolKit.isList(data, "comment")) {
//                try {
//                    CommentBean comment = GsonUtil.GsonToBean(data.get("comment"), CommentBean.class);
////                    all_comment_num_tv.setText("更多评价(" + comment.getTotal() + ")");
//                    Map<String, String> commentMap = JSONUtils.parseKeyAndValueToMap(data.get("comment"));
//                    CommentBean.BodyBean bodyBean = comment.getBody();
//                    if (bodyBean != null) {
//                        Glide.with(this).load(bodyBean.getHead_pic())
//                                .override(head_size, head_size)
//                                .placeholder(R.drawable.ic_default)
//                                .error(R.drawable.ic_default)
//                                .centerCrop()
//                                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
//                                .into(comm_user_head_iv);
//                        comm_user_name_tv.setText(bodyBean.getNickname());
//                        comm_content_tv.setText(bodyBean.getContent());
//                        List<CommentBean.BodyBean.PicturesBean> pictures = bodyBean.getPictures();
//                        if (!ListUtils.isEmpty(pictures)) {
//                            CommentPicAdapter picadapter = new CommentPicAdapter(getActivity(), pictures);
//                            estimate_pic.setAdapter(picadapter);
//                        }
//                    }
//                } catch (JsonSyntaxException e) {
//                    all_comment_num_tv.setText("商品评价(0)");
//                    comment_layout.setVisibility(View.GONE);
//                }
//            }

        }
    }

    /**
     * 启动轮播图
     */
    private void startBanner() {
        for (int i = 0; i < list.size(); i++) {
            ImageView imageView = new ImageView(getActivity());
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            albums.add(imageView);
        }
        sb_houses_banner.setAdapter(adapter);
        sb_houses_banner.setDotNum(ListUtils.getSize(albums));
        sb_houses_banner.beginPlay();
    }

    @Override
    public void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy) {
        if (y < Settings.displayWidth / 2) {
            hd_be_back_top_iv.setVisibility(View.GONE);
        } else {
            hd_be_back_top_iv.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 轮播图适配器
     *
     * @author Administrator
     */
    private class MyViewAdapter extends BannerAdapter {

        @Override
        public View getView(LayoutInflater layoutInflater, final int position) {
            ImageView imageView = albums.get(position);
            Glide.with(getActivity()).load(list.get(position).get("path"))
                    .centerCrop()
                    .error(R.drawable.ic_default)
                    .placeholder(R.drawable.ic_default)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .override(Settings.displayWidth, Settings.displayWidth)
                    .into(imageView);
            return imageView;
        }

        @Override
        public int getCount() {
            return ListUtils.getSize(albums);
        }
    }

    public interface SkipToComment {
        void beSkip(boolean skip);
    }

    public interface SetPhone {
        void setPhone(String phone);
    }

}
