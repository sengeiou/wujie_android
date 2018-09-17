package com.txd.hzj.wjlp.mellonLine.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.ants.theantsgo.tool.ToolKit;
import com.ants.theantsgo.view.inScroll.GridViewForScrollView;
import com.bumptech.glide.Glide;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.new_wjyp.BeanCommentList;

import java.util.List;

import cn.gavinliu.android.lib.shapedimageview.ShapedImageView;

/**
 * 作者：DUKE_HwangZj
 * 日期：2017/7/10 0010
 * 时间：17:56
 * 描述：
 */

public class GoodsEvalusteAdapter extends BaseAdapter {
    private Context context;
    List<BeanCommentList.DataBean.CommentListBean> comment_list;
    private LayoutInflater inflater;
    private GEVH gevh;
    private int r = 0;

    private int head_size = 0;
    private int goods_size = 0;

    private int type;

    public GoodsEvalusteAdapter(Context context, List<BeanCommentList.DataBean.CommentListBean> comment_list, int type) {
        this.context = context;
        this.comment_list = comment_list;
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
        return comment_list.size();
    }

    @Override
    public BeanCommentList.DataBean.CommentListBean getItem(int i) {
        return comment_list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        BeanCommentList.DataBean.CommentListBean commentList = getItem(i);
        if (view == null) {
            view = inflater.inflate(R.layout.aty_estimate_layout, viewGroup, false);
            gevh = new GEVH();
            ViewUtils.inject(gevh, view);
            view.setTag(gevh);
        } else {
            gevh = (GEVH) view.getTag();
        }
        List<BeanCommentList.DataBean.CommentListBean.PicturesArrBean> pictures_arr = commentList.getPictures_arr();
        if (pictures_arr != null) {
            gevh.estimate_pic.setVisibility(View.VISIBLE);
            gevh.estimate_pic.setAdapter(new PICAdapter(pictures_arr, context));
        } else {
            gevh.estimate_pic.setVisibility(View.GONE);
        }
        gevh.comment_layout.setVisibility(View.VISIBLE);
        Glide.with(context)
                .load(commentList.getHead_pic())
                .error(R.drawable.ic_default)
                .placeholder(R.drawable.ic_default)
                .override(head_size, head_size)
                .into(gevh.comm_user_head_iv);
        gevh.comm_user_name_tv.setText(commentList.getNickname());
        gevh.comm_content_tv.setText(commentList.getContent());
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("标签：");
        for (BeanCommentList.DataBean.CommentListBean.LabelArrBean labelArrBean : getItem(i).getLabel_arr()) {
            stringBuffer.append(labelArrBean.getLabel());
            stringBuffer.append("、");
        }
        gevh.tv_label.setText(stringBuffer.toString());
        gevh.tv_date.setText(getItem(i).getCreate_time());
        gevh.ratingBar.setRating(Float.parseFloat(getItem(i).getComment_star()));
        gevh.ratingBar.setVisibility(View.VISIBLE);
        if (0 == type) {
            gevh.goods_for_my_evaluste_layout.setVisibility(View.GONE);
        }
// else {
//
//            Glide.with(context)
//                    .load(commentList.getGoods_img())
//                    .error(R.drawable.ic_default)
//                    .placeholder(R.drawable.ic_default)
//                    .override(head_size, head_size)
//                    .into(gevh.goods_comment_pic);
//            String type = "";
//            switch (commentList.getOrder_type()) {
//                case "0":
//                case "6":
//                case "7":
//                case "8":
//                    type = "";
//                    break;
//                case "1":
//                    type = "拼单";
//                    break;
//                case "2":
//                    type = "预购";
//                    break;
//                case "3":
//                    type = "竞拍";
//                    break;
//                case "4":
//                    type = "夺宝";
//                    break;
//            }
//            gevh.goods_for_my_evaluste_layout.setVisibility(View.VISIBLE);
//            TextUtils.titleTipUtils(context, gevh.goods_title_for_evaluate_tv, type, commentList.getGoods_name(),
//                    Color.parseColor("#47CEF7"), r);
//            ChangeTextViewStyle.getInstance().forOrderPrice2(context, gevh.price_for_goods_tv,
//                    "￥" + commentList.getShop_price());
//        }

        return view;
    }

    class GEVH {
        @ViewInject(R.id.comment_layout)
        private LinearLayout comment_layout;
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

        @ViewInject(R.id.tv_label)
        private TextView tv_label;
        @ViewInject(R.id.tv_date)
        private TextView tv_date;
        @ViewInject(R.id.rb)
        private RatingBar ratingBar;

    }

    private class PICAdapter extends BaseAdapter {
        private List<BeanCommentList.DataBean.CommentListBean.PicturesArrBean> pic;
        private Context context;
        private LayoutInflater inflater;
        private PicVh pvh;

        private int pic_size = 0;

        public PICAdapter(List<BeanCommentList.DataBean.CommentListBean.PicturesArrBean> pic, Context context) {
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
        public BeanCommentList.DataBean.CommentListBean.PicturesArrBean getItem(int i) {
            return pic.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            BeanCommentList.DataBean.CommentListBean.PicturesArrBean pictures = getItem(i);
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
