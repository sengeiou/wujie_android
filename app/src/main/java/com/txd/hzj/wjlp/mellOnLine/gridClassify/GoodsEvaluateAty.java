package com.txd.hzj.wjlp.mellOnLine.gridClassify;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.ants.theantsgo.config.Settings;
import com.ants.theantsgo.gson.GsonUtil;
import com.ants.theantsgo.view.DukeScrollView;
import com.ants.theantsgo.view.PullToRefreshLayout;
import com.ants.theantsgo.view.inScroll.ListViewForScrollView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.http.carbuy.CarBuy;
import com.txd.hzj.wjlp.http.user.UserPst;
import com.txd.hzj.wjlp.mellOnLine.adapter.GoodsEvalusteAdapter;
import com.txd.hzj.wjlp.txunda_lh.BeanCommentList;
import com.txd.hzj.wjlp.view.flowlayout.FlowLayout;
import com.txd.hzj.wjlp.view.flowlayout.TagAdapter;
import com.txd.hzj.wjlp.view.flowlayout.TagFlowLayout;

import java.util.List;
import java.util.Map;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/10 0010
 * 时间：下午 5:49
 * 描述：商品评价
 * ===============Txunda===============
 */
public class GoodsEvaluateAty extends BaseAty implements DukeScrollView.ScrollViewListener {

    @ViewInject(R.id.titlt_conter_tv)
    public TextView titlt_conter_tv;

    /**
     * 商品评价类型
     */
    @ViewInject(R.id.goods_comment_tag)
    private TagFlowLayout goods_comment_tag;

    @ViewInject(R.id.goods_evaluste_lv)
    private ListViewForScrollView goods_evaluste_lv;


    /**
     * 0.商品全部评价
     * 1.我的全部评价
     * 2.店铺全部评价
     */
    private int from = 0;

    @ViewInject(R.id.evaluate_lin_layout)
    private LinearLayout evaluate_lin_layout;

    private GoodsEvalusteAdapter goodsEvalusteAdapter;

    List<BeanCommentList.DataBean.LabelListBean> label_list;

    /**
     * 滚动监听
     */
    @ViewInject(R.id.goods_comment_sc)
    private DukeScrollView goods_comment_sc;

    /**
     * 回到顶部
     */
    @ViewInject(R.id.gc_be_back_top_iv)
    private ImageView gc_be_back_top_iv;

    private UserPst userPst;
    private int p = 1;

    @ViewInject(R.id.refresh_view)
    private PullToRefreshLayout refresh_view;

    @ViewInject(R.id.evaluate_num_tv)
    private TextView evaluate_num_tv;

    private int numall = 0;

    @ViewInject(R.id.no_data_layout)
    private LinearLayout no_data_layout;

    @ViewInject(R.id.tv_composite)
    private TextView tv_composite;
    @ViewInject(R.id.rb)
    private RatingBar rb;
    @ViewInject(R.id.tv_cmm)
    private TextView tv_cmm;
    private String label_id = "";

    List<BeanCommentList.DataBean.CommentListBean> comment_list;
    private TagAdapter<BeanCommentList.DataBean.LabelListBean> tagAdapter;
    private List<BeanCommentList.DataBean.CommentListBean> Comment_list;
    private List<BeanCommentList.DataBean.CommentListBean> Comment_more;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
        if (0 == from) {
            titlt_conter_tv.setText("全部评价" + getIntent().getStringExtra("num"));
            evaluate_lin_layout.setVisibility(View.GONE);
            goods_comment_tag.setVisibility(View.VISIBLE);
        }
//        else if (1 == from) {
//            titlt_conter_tv.setText("我的评价");
//            evaluate_lin_layout.setVisibility(View.VISIBLE);
//            goods_comment_tag.setVisibility(View.GONE);
//        } else {
//            titlt_conter_tv.setText("店铺评价");
//            evaluate_lin_layout.setVisibility(View.VISIBLE);
//            goods_comment_tag.setVisibility(View.GONE);
//        }


        // 滚动到顶部
        goods_comment_sc.smoothScrollTo(0, 0);
        // 滚动监听
        goods_comment_sc.setScrollViewListener(this);

        goods_evaluste_lv.setEmptyView(no_data_layout);

        refresh_view.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
                p = 1;
                CarBuy.commentList(getIntent().getStringExtra("id"), label_id, p, GoodsEvaluateAty.this);

            }

            @Override
            public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {

                // 加载操作
                p++;
                CarBuy.commentList(getIntent().getStringExtra("id"), label_id, p, GoodsEvaluateAty.this);
            }
        });

    }

    @Override
    @OnClick({R.id.gc_be_back_top_iv})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.gc_be_back_top_iv:
                goods_comment_sc.smoothScrollTo(0, 0);
                break;
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_goods_evaluate;
    }

    @Override
    protected void initialized() {
        from = getIntent().getIntExtra("from", 0);
        userPst = new UserPst(this);


    }

    @Override
    protected void requestData() {
        if (1 == from) {
            p = 1;
            userPst.myCommentList(p);
        }
        CarBuy.commentList(getIntent().getStringExtra("id"), "", p, this);
        showProgressDialog();

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
            tv_cmm.setText("外观内饰" + data.getExterior() + "分\t" + "控件舒适" + data.getSpace() + "分\t"
                    + "操控性能" + data.getControllability() + "分\t" + "油耗动力" + data.getConsumption() + "分");
            label_list = data.getLabel_list();
            tagAdapter = new TagAdapter<BeanCommentList.DataBean.LabelListBean>(label_list) {
                @Override
                public View getView(FlowLayout parent, final int position, final BeanCommentList.DataBean.LabelListBean labelListBean) {
                    TextView tv = (TextView) LayoutInflater.from(GoodsEvaluateAty.this).inflate(R.layout
                                    .item_goods_attrs_tfl,
                            parent, false);
                    tv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                           // tagAdapter.setSelectedList(position);
                            label_id = labelListBean.getLabel_id();
                            CarBuy.commentList(getIntent().getStringExtra("id"), label_id, p, GoodsEvaluateAty.this);
                        }
                    });
                    tv.setText(labelListBean.getLabel_name());
                    return tv;
                }


            };
           // tagAdapter.setSelectedList(0);
            goods_comment_tag.setAdapter(tagAdapter);

            if (p == 1) {
                Comment_list = data.getComment_list();
                goodsEvalusteAdapter = new GoodsEvalusteAdapter(this, data.getComment_list(), from);
                goods_evaluste_lv.setAdapter(goodsEvalusteAdapter);
            } else {
                Comment_more = data.getComment_list();
                Comment_list.addAll(Comment_more);
                goodsEvalusteAdapter.notifyDataSetChanged();
            }

        }

//        L.e("=====数据-=====", jsonStr);
//        if (requestUrl.contains("myCommentList")) {
//            Comment comment = GsonUtil.GsonToBean(jsonStr, Comment.class);
//            numall = comment.getNums();
//            evaluate_num_tv.setText("已有 " + numall + "条评价");
//            if (1 == p) {
//                data.clear();
//                data = comment.getData();
//                if (!ListUtils.isEmpty(data)) {
//                    goodsEvalusteAdapter = new GoodsEvalusteAdapter(this, data, from);
//                    goods_evaluste_lv.setAdapter(goodsEvalusteAdapter);
//                }
//                refresh_view.refreshFinish(PullToRefreshLayout.SUCCEED); // 刷新成功
//            } else {
//                data2 = comment.getData();
//                if (!ListUtils.isEmpty(data2)) {
//                    data.addAll(data2);
//                    goodsEvalusteAdapter.notifyDataSetChanged();
//                }
//                refresh_view.loadmoreFinish(PullToRefreshLayout.SUCCEED); // 刷新成功
//            }
//        }
    }

    @Override
    public void onError(String requestUrl, Map<String, String> error) {
        super.onError(requestUrl, error);
        evaluate_num_tv.setText("已有 0 条评价");
        if (1 == p) {
            refresh_view.refreshFinish(PullToRefreshLayout.SUCCEED); // 刷新成功
        } else {
            refresh_view.loadmoreFinish(PullToRefreshLayout.SUCCEED); // 刷新成功
        }
    }

    @Override
    public void onScrollChanged(DukeScrollView scrollView, int x, int y, int oldx, int oldy) {
        if (y < Settings.displayWidth) {
            gc_be_back_top_iv.setVisibility(View.GONE);
        } else {
            gc_be_back_top_iv.setVisibility(View.VISIBLE);
        }
    }
}
