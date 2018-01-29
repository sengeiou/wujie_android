package com.txd.hzj.wjlp.mellOnLine.gridClassify;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.ants.theantsgo.config.Settings;
import com.ants.theantsgo.gson.GsonUtil;
import com.ants.theantsgo.tool.ToolKit;
import com.ants.theantsgo.util.ListUtils;
import com.ants.theantsgo.view.DukeScrollView;
import com.ants.theantsgo.view.PullToRefreshLayout;
import com.ants.theantsgo.view.inScroll.GridViewForScrollView;
import com.ants.theantsgo.view.inScroll.ListViewForScrollView;
import com.bumptech.glide.Glide;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.bean.Comment;
import com.txd.hzj.wjlp.http.carbuy.CarBuy;
import com.txd.hzj.wjlp.http.merchant.MerchantPst;
import com.txd.hzj.wjlp.http.user.UserPst;
import com.txd.hzj.wjlp.mellOnLine.adapter.GoodsEvalusteAdapter;
import com.txd.hzj.wjlp.tool.ChangeTextViewStyle;
import com.txd.hzj.wjlp.tool.TextUtils;
import com.txd.hzj.wjlp.txunda_lh.BeanCommentList;
import com.txd.hzj.wjlp.view.flowlayout.FlowLayout;
import com.txd.hzj.wjlp.view.flowlayout.TagAdapter;
import com.txd.hzj.wjlp.view.flowlayout.TagFlowLayout;

import java.util.List;
import java.util.Map;

import cn.gavinliu.android.lib.shapedimageview.ShapedImageView;

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
    @ViewInject(R.id.layout_top)
    private LinearLayout layout_top;

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
    private _GoodsEvalusteAdapter _goodsEvalusteAdapter;

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
    private MerchantPst merchantPst;
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
        } else if (1 == from) {
            titlt_conter_tv.setText("我的评价");
            evaluate_lin_layout.setVisibility(View.VISIBLE);
            goods_comment_tag.setVisibility(View.GONE);
        } else {
            titlt_conter_tv.setText("店铺评价");
            evaluate_lin_layout.setVisibility(View.VISIBLE);
            goods_comment_tag.setVisibility(View.GONE);
        }


        // 滚动到顶部
        goods_comment_sc.smoothScrollTo(0, 0);
        // 滚动监听
        goods_comment_sc.setScrollViewListener(this);

        goods_evaluste_lv.setEmptyView(no_data_layout);

        refresh_view.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {


                p = 1;
                if (1 == from) {
                    userPst.myCommentList(p);
                    layout_top.setVisibility(View.GONE);
                    goods_comment_tag.setVisibility(View.GONE);
                } else if (2 == from) {
                    merchantPst.commentList(getIntent().getStringExtra("mid"), p);
                } else if (3 == from) {
                    CarBuy.commentList(getIntent().getStringExtra("id"), label_id, p, GoodsEvaluateAty.this);
                    showProgressDialog();
                }

            }

            @Override
            public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {

                // 加载操作


                p++;
                if (1 == from) {
                    userPst.myCommentList(p);
                    layout_top.setVisibility(View.GONE);
                    goods_comment_tag.setVisibility(View.GONE);
                } else if (2 == from) {
                    merchantPst.commentList(getIntent().getStringExtra("mid"), p);
                } else if (3 == from) {
                    CarBuy.commentList(getIntent().getStringExtra("id"), label_id, p, GoodsEvaluateAty.this);

                    showProgressDialog();
                }
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
        merchantPst = new MerchantPst(this);

    }

    @Override
    protected void requestData() {
        if (1 == from) {
            p = 1;
            userPst.myCommentList(p);
            layout_top.setVisibility(View.GONE);
            goods_comment_tag.setVisibility(View.GONE);
        } else if (2 == from) {
            p = 1;
            merchantPst.commentList(getIntent().getStringExtra("mid"), p);
        } else if (3 == from) {
            CarBuy.commentList(getIntent().getStringExtra("id"), "", p, this);
            showProgressDialog();
        }
    }

    List<Comment.CommentList> data;
    List<Comment.CommentList> data2;

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        if (requestUrl.contains("Merchant/commentList")) {

            Comment comment = GsonUtil.GsonToBean(jsonStr, Comment.class);
            numall = comment.getNums();
            evaluate_num_tv.setText("已有 " + numall + "条评价");
            if (1 == p) {
                data.clear();
                data = comment.getData();
                if (!ListUtils.isEmpty(data)) {
                    _goodsEvalusteAdapter = new _GoodsEvalusteAdapter(this, data, from);
                    goods_evaluste_lv.setAdapter(_goodsEvalusteAdapter);
                }
                refresh_view.refreshFinish(PullToRefreshLayout.SUCCEED); // 刷新成功
            } else {
                data2 = comment.getData();
                if (!ListUtils.isEmpty(data2)) {
                    data.addAll(data2);
                    _goodsEvalusteAdapter.notifyDataSetChanged();
                }
                refresh_view.loadmoreFinish(PullToRefreshLayout.SUCCEED); // 刷新成功

        }
        }
        if (requestUrl.contains("CarBuy/commentList")) {
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
                    tv.setText(labelListBean.getLabel_name() + "(" + labelListBean.getNum() + ")");
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

        if (requestUrl.contains("myCommentList")) {
            Comment comment = GsonUtil.GsonToBean(jsonStr, Comment.class);
            numall = comment.getNums();
            evaluate_num_tv.setText("已有 " + numall + "条评价");
            if (1 == p) {
                data.clear();
                data = comment.getData();
                if (!ListUtils.isEmpty(data)) {
                    _goodsEvalusteAdapter = new _GoodsEvalusteAdapter(this, data, from);
                    goods_evaluste_lv.setAdapter(_goodsEvalusteAdapter);
                }
                refresh_view.refreshFinish(PullToRefreshLayout.SUCCEED); // 刷新成功
            } else {
                data2 = comment.getData();
                if (!ListUtils.isEmpty(data2)) {
                    data.addAll(data2);
                    _goodsEvalusteAdapter.notifyDataSetChanged();
                }
                refresh_view.loadmoreFinish(PullToRefreshLayout.SUCCEED); // 刷新成功
            }
        }
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

    class _GoodsEvalusteAdapter extends BaseAdapter {
        private Context context;
        private List<Comment.CommentList> list;
        private LayoutInflater inflater;
        private GEVH gevh;
        private int r = 0;

        private int head_size = 0;
        private int goods_size = 0;

        private int type;

        public _GoodsEvalusteAdapter(Context context, List<Comment.CommentList> list, int type) {
            this.context = context;
            this.list = list;
            this.type = type;
            if (1 == type) {
                r = ToolKit.dip2px(context, 4);
                goods_size = ToolKit.dip2px(context, 100);
            }
            head_size = ToolKit.dip2px(context, 60);
            inflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Comment.CommentList getItem(int i) {
            return list.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            Comment.CommentList commentList = getItem(i);
            if (view == null) {
                view = inflater.inflate(R.layout.aty_estimate_layout, viewGroup, false);
                gevh = new GEVH();
                ViewUtils.inject(gevh, view);
                view.setTag(gevh);
            } else {
                gevh = (GEVH) view.getTag();
            }
            List<Comment.CommentList.Pictures> pic = commentList.getPictures();
            if (pic != null) {
                gevh.estimate_pic.setVisibility(View.VISIBLE);
                gevh.estimate_pic.setAdapter(new PICAdapter(pic, context));
            } else {
                gevh.estimate_pic.setVisibility(View.GONE);
            }

            Glide.with(context)
                    .load(commentList.getUser_head_pic())
                    .error(R.drawable.ic_default)
                    .placeholder(R.drawable.ic_default)
                    .override(head_size, head_size)
                    .into(gevh.comm_user_head_iv);

            gevh.comm_user_name_tv.setText(commentList.getNickname());
            gevh.comm_content_tv.setText(commentList.getContent());

            if (0 == type) {
                gevh.goods_for_my_evaluste_layout.setVisibility(View.GONE);
            } else {

                Glide.with(context)
                        .load(commentList.getGoods_img())
                        .error(R.drawable.ic_default)
                        .placeholder(R.drawable.ic_default)
                        .override(head_size, head_size)
                        .into(gevh.goods_comment_pic);
                String type = "";
                switch (commentList.getOrder_type()) {
                    case "0":
                    case "6":
                    case "7":
                    case "8":
                        type = "";
                        break;
                    case "1":
                        type = "拼团";
                        break;
                    case "2":
                        type = "预购";
                        break;
                    case "3":
                        type = "竞拍";
                        break;
                    case "4":
                        type = "夺宝";
                        break;
                }
                gevh.goods_for_my_evaluste_layout.setVisibility(View.VISIBLE);
                TextUtils.titleTipUtils(context, gevh.goods_title_for_evaluate_tv, type, commentList.getGoods_name(),
                        Color.parseColor("#47CEF7"), r);
                ChangeTextViewStyle.getInstance().forOrderPrice2(context, gevh.price_for_goods_tv,
                        "￥" + commentList.getShop_price());
            }

            return view;
        }

        class GEVH {
            @ViewInject(R.id.estimate_pic)
            private GridViewForScrollView estimate_pic;

            @ViewInject(R.id.goods_for_my_evaluste_layout)
            private LinearLayout goods_for_my_evaluste_layout;

            /**
             * 用户头像
             */
            @ViewInject(R.id.comm_user_head_iv)
            private ShapedImageView comm_user_head_iv;
            /**
             * 昵称
             */
            @ViewInject(R.id.comm_user_name_tv)
            private TextView comm_user_name_tv;
            /**
             * 评价内容
             */
            @ViewInject(R.id.comm_content_tv)
            private TextView comm_content_tv;

            /**
             * 商品图片
             */
            @ViewInject(R.id.goods_comment_pic)
            private ImageView goods_comment_pic;

            /**
             * 标题
             */
            @ViewInject(R.id.goods_title_for_evaluate_tv)
            private TextView goods_title_for_evaluate_tv;
            /**
             * 价格
             */
            @ViewInject(R.id.price_for_goods_tv)
            private TextView price_for_goods_tv;

        }

        private class PICAdapter extends BaseAdapter {
            private List<Comment.CommentList.Pictures> pic;
            private Context context;
            private LayoutInflater inflater;
            private PicVh pvh;

            private int pic_size = 0;

            public PICAdapter(List<Comment.CommentList.Pictures> pic, Context context) {
                this.pic = pic;
                this.context = context;
                pic_size = ToolKit.dip2px(context, 88);
                inflater = LayoutInflater.from(context);
            }

            @Override
            public int getCount() {
                return pic.size();
            }

            @Override
            public Comment.CommentList.Pictures getItem(int i) {
                return pic.get(i);
            }

            @Override
            public long getItemId(int i) {
                return i;
            }

            @Override
            public View getView(int i, View view, ViewGroup viewGroup) {
                Comment.CommentList.Pictures pictures = getItem(i);
                if (view == null) {
                    view = inflater.inflate(R.layout.item_evaluste_pic_gv, viewGroup, false);
                    pvh = new PicVh();
                    ViewUtils.inject(pvh, view);
                    view.setTag(pvh);
                } else {
                    pvh = (PicVh) view.getTag();
                }

                Glide.with(context)
                        .load(pictures.getPath())
                        .error(R.drawable.ic_default)
                        .placeholder(R.drawable.ic_default)
                        .override(pic_size, pic_size)
                        .into(pvh.comm_pic_iv);
                return view;
            }

            class PicVh {

                /**
                 * 评论图片
                 */
                @ViewInject(R.id.comm_pic_iv)
                private ImageView comm_pic_iv;
            }
        }

    }
}
