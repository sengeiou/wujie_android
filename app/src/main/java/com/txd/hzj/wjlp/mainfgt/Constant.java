package com.txd.hzj.wjlp.mainfgt;

import com.txd.hzj.wjlp.bean.offline.OffLineDataBean;

/**
 * 创建者：TJDragon(LiuGang)
 * 创建时间：2018/7/23 14:23
 * 功能描述：
 * 联系方式：常用邮箱或电话
 */
public interface Constant {
    public interface Pranster {
        void setView(Constant.View view);
    }

    public interface View {
        void onItemClickListener(OffLineDataBean offLineDataBean,int position);
        void loadComplate();

    }
}
