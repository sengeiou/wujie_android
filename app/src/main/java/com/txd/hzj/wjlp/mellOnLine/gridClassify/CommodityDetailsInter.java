package com.txd.hzj.wjlp.mellOnLine.gridClassify;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.synnapps.carouselview.CarouselView;
import com.txd.hzj.wjlp.view.ObservableScrollView;

/**
 * 创建者：TJDragon(LiuGang)
 * 创建时间：2018/5/20 11:29
 * 功能描述：
 * 联系方式：常用邮箱或电话
 */
public interface CommodityDetailsInter {
    public interface CommodityPranster{
        void setView(CommodityView view);

        /**
         * 设置选项卡
         * @param title_goods_layout
         * @param title_details_layout
         * @param title_evaluate_layout
         */
       void setTabViews(View title_goods_layout, View title_details_layout, View title_evaluate_layout);

        /**
         * 加载完成后获取控件
         * @param online_carvouse_view
         * @param top_lin_layout
         * @param second_lin_layout
         * @param limit_goods_details_sc
         * @param be_back_top_iv
         */
        void getHeight(CarouselView online_carvouse_view, LinearLayout top_lin_layout, LinearLayout second_lin_layout, ObservableScrollView limit_goods_details_sc, ImageView be_back_top_iv);
    }
    public interface CommodityView{
            
    }
    public interface GoodLuckView{

    }
    public interface GoodLuckPranster{
        void showExperiencePopWindow(Context context, View view,StringBuffer stringBuffer);
        void setGoodLuckView(GoodLuckView goodLuckView);
    }
}
