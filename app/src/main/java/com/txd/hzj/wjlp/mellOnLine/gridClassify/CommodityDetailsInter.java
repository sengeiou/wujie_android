package com.txd.hzj.wjlp.mellOnLine.gridClassify;

import android.content.Context;
import android.view.View;

/**
 * 创建者：TJDragon(LiuGang)
 * 创建时间：2018/5/20 11:29
 * 功能描述：
 * 联系方式：常用邮箱或电话
 */
public interface CommodityDetailsInter {
    public interface CommodityPranster{
        void setView(CommodityView view);
    }
    public interface CommodityView{
            
    }
    public interface GoodLuckView{

    }
    public interface GoodLuckPranster{
        void showExperiencePopWindow(Context context,View view);
        void setGoodLuckView(GoodLuckView goodLuckView);
    }
}
