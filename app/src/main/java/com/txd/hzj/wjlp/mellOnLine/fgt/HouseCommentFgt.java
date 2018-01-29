package com.txd.hzj.wjlp.mellOnLine.fgt;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.ants.theantsgo.config.Settings;
import com.ants.theantsgo.gson.GsonUtil;
import com.ants.theantsgo.view.inScroll.ListViewForScrollView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseFgt;
import com.txd.hzj.wjlp.http.house.HouseBuy;
import com.txd.hzj.wjlp.mellOnLine.adapter.GoodsEvalusteAdapter;
import com.txd.hzj.wjlp.txunda_lh.BeanCommentList;
import com.txd.hzj.wjlp.view.ObservableScrollView;
import com.txd.hzj.wjlp.view.flowlayout.FlowLayout;
import com.txd.hzj.wjlp.view.flowlayout.TagAdapter;
import com.txd.hzj.wjlp.view.flowlayout.TagFlowLayout;

import java.util.List;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/28 0028
 * 时间：上午 9:22
 * 描述：点评
 * ===============Txunda===============
 */
public class HouseCommentFgt extends BaseFgt implements ObservableScrollView.ScrollViewListener {

    @ViewInject(R.id.house_comment_tag)
    private TagFlowLayout house_comment_tag;
    int post = -1;

    /**
     * 点评
     */
    @ViewInject(R.id.house_comment_lv)
    private ListViewForScrollView house_comment_lv;


    /**
     * 回到顶部
     */
    @ViewInject(R.id.hc_be_back_top_iv)
    private ImageView hc_be_back_top_iv;

    /**
     * 滚动监听的ScollView
     */
    @ViewInject(R.id.comment_sc)
    private ObservableScrollView comment_sc;

    private String house_id = "";
    private int p = 1;
    private String label_id = "";

    @ViewInject(R.id.tv_composite)
    private TextView tv_composite;
    @ViewInject(R.id.rb)
    private RatingBar rb;
    @ViewInject(R.id.tv_cmm)
    private TextView tv_cmm;

    private TagAdapter<BeanCommentList.DataBean.LabelListBean> tagAdapter;
    private List<BeanCommentList.DataBean.CommentListBean> Comment_list;
    private List<BeanCommentList.DataBean.CommentListBean> Comment_more;

    private GoodsEvalusteAdapter goodsEvalusteAdapter;
    List<BeanCommentList.DataBean.LabelListBean> label_list;

    public static HouseCommentFgt getFgt(String house_id) {
        HouseCommentFgt housDetailsHousesChenFgt = new HouseCommentFgt();
        housDetailsHousesChenFgt.house_id = house_id;
        return housDetailsHousesChenFgt;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        comment_sc.setScrollViewListener(this);
    }

    @Override
    @OnClick({R.id.hc_be_back_top_iv})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.hc_be_back_top_iv:
                comment_sc.smoothScrollTo(0, 0);
                break;
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fgt_house_comment;
    }

    @Override
    protected void initialized() {
    }

    @Override
    protected void requestData() {
        HouseBuy.commentList(house_id, p, label_id, this);
    }

    @Override
    protected void immersionInit() {

    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        if (requestUrl.contains("commentList")) {
            BeanCommentList list = GsonUtil.GsonToBean(jsonStr, BeanCommentList.class);
            BeanCommentList.DataBean data = list.getData();
            float num = Float.parseFloat(data.getComposite());
            tv_composite.setText(String.valueOf((int) num));
            rb.setRating(Float.parseFloat(data.getComposite()));
            tv_cmm.setText("价格评分" + data.getPrice() + "分\t" + "地段评分" + data.getLot() + "分\t"
                    + "配套评分" + data.getSupporting() + "分\t" + "交通评分" + data.getTraffic() + "分" + "环境评分" + data.getEnvironment() + "分");
            label_list = data.getLabel_list();
            tagAdapter = new TagAdapter<BeanCommentList.DataBean.LabelListBean>(label_list) {
                @Override
                public View getView(FlowLayout parent, final int position, final BeanCommentList.DataBean.LabelListBean labelListBean) {
                    TextView tv = (TextView) LayoutInflater.from(getActivity()).inflate(R.layout
                                    .item_goods_attrs_tfl,
                            parent, false);
                    tv.setText(labelListBean.getLabel_name() + "(" + labelListBean.getNum() + ")");
                    return tv;
                }

            };

            house_comment_tag.setAdapter(tagAdapter);
            house_comment_tag.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
                @Override
                public boolean onTagClick(View view, int position, FlowLayout parent) {

//                    tagAdapter.setSelectedList(position);
                    post = position;
                    label_id = label_list.get(position).getLabel_id();
                    HouseBuy.commentList(house_id, p, label_id, HouseCommentFgt.this);
                    return false;
                }
            });
            if (post != -1) {
                tagAdapter.setSelectedList(post);
            }

            if (p == 1) {
                Comment_list = data.getComment_list();
                goodsEvalusteAdapter = new GoodsEvalusteAdapter(getActivity(), data.getComment_list(), 0);
                house_comment_lv.setAdapter(goodsEvalusteAdapter);
            } else {
                Comment_more = data.getComment_list();
                Comment_list.addAll(Comment_more);
                goodsEvalusteAdapter.notifyDataSetChanged();
            }

        }
    }

    @Override
    public void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy) {

        if (y < Settings.displayWidth) {
            hc_be_back_top_iv.setVisibility(View.GONE);
        } else {
            hc_be_back_top_iv.setVisibility(View.VISIBLE);
        }
    }
}
