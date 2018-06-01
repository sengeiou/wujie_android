package com.txd.hzj.wjlp.mellOnLine.gridClassify;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.synnapps.carouselview.CarouselView;
import com.txd.hzj.wjlp.bean.commodity.DjTicketBean;
import com.txd.hzj.wjlp.bean.commodity.PromotionBean;
import com.txd.hzj.wjlp.mellOnLine.adapter.TheTrickAdapter;
import com.txd.hzj.wjlp.view.ObservableScrollView;

import java.util.List;

/**
 * 创建者：TJDragon(LiuGang)
 * 创建时间：2018/5/20 11:29
 * 功能描述：
 * 联系方式：常用邮箱或电话
 */
public interface CommodityDetailsInter {
    public interface CommodityPranster {
        void setView(CommodityView view);

        /**
         * 设置选项卡
         *
         * @param title_goods_layout
         * @param title_details_layout
         * @param title_evaluate_layout
         */
        void setTabViews(View title_goods_layout, View title_details_layout, View title_evaluate_layout);

        /**
         * 加载完成后获取控件
         *
         * @param online_carvouse_view
         * @param top_lin_layout
         * @param second_lin_layout
         * @param limit_goods_details_sc
         * @param be_back_top_iv
         */
        void getHeight(CarouselView online_carvouse_view, LinearLayout top_lin_layout, LinearLayout second_lin_layout, ObservableScrollView limit_goods_details_sc, ImageView be_back_top_iv);

        void freight(String goods_id, String tx);

        void showDjqPop(View view, List<DjTicketBean> list, Activity activity, String vouchers_desc);

        void showCXPop(View view, Activity activity, List<PromotionBean> promotion);

        void showLQPop(View view, String title, Activity activity, final TheTrickAdapter theTrickAdapter);

        /**
         * 拼单购、无界商店和进口馆商品详情页公共属性封装(包括国家的图片，描述和进口税，收藏)
         * @param context
         * @param url
         * @param view
         */
        void setBitmap(Context context,String url,View view);

        void setTextContent(String content,View view);

        void isCollect(String is_collect,String viewContent,View view,Context context);
    }

    public interface CommodityView {
        /**
         * 查询到运费
         *
         * @param payStr
         */
        void getFreightPay(String payStr);
    }

    public interface GoodLuckView {

    }

    public interface GoodLuckPranster {
        void showExperiencePopWindow(Context context, View view, StringBuffer stringBuffer);

        void setGoodLuckView(GoodLuckView goodLuckView);
    }

}
