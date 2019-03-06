package com.txd.hzj.wjlp.catchDoll.ui.activity;

import android.content.DialogInterface;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ants.theantsgo.tool.glide.GlideUtils;
import com.ants.theantsgo.util.TimeUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.catchDoll.bean.MyDollBean;
import com.txd.hzj.wjlp.catchDoll.ui.dialog.MessageDialog;
import com.txd.hzj.wjlp.catchDoll.ui.dialog.RedemptionResultDialog;
import com.txd.hzj.wjlp.catchDoll.util.Util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 创建者：voodoo_jie
 * <br>创建时间：2018/09/20 020上午 11:51
 * <br>功能描述：商品详情界面
 */
public class DollGoodsInfoActivity extends BaseAty {

    @ViewInject(R.id.titleView_title_tv)
    public TextView titleView_title_tv;

    @ViewInject(R.id.dollGoodsInfo_image_imgv)
    public ImageView dollGoodsInfo_image_imgv;
    @ViewInject(R.id.dollGoodsInfo_name_tv)
    public TextView dollGoodsInfo_name_tv;
    @ViewInject(R.id.dollGoodsInfo_time_tv)
    public TextView dollGoodsInfo_time_tv;
    @ViewInject(R.id.dollGoodsInfo_userHeader_imgv)
    public ImageView dollGoodsInfo_userHeader_imgv;
    @ViewInject(R.id.dollGoodsInfo_userName_tv)
    public TextView dollGoodsInfo_userName_tv;
    @ViewInject(R.id.dollGoodsInfo_goodsType_tv)
    public TextView dollGoodsInfo_goodsType_tv;
    @ViewInject(R.id.dollGoodsInfo_roomName_tv)
    public TextView dollGoodsInfo_roomName_tv;
    @ViewInject(R.id.dollGoodsInfo_roomNumber_tv)
    public TextView dollGoodsInfo_roomNumber_tv;

    private MyDollBean myDollBean; // 我的娃娃对象

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_doll_goods_info;
    }

    @Override
    protected void initialized() {
        titleView_title_tv.setText("商品详情");

        myDollBean = (MyDollBean) getIntent().getExtras().getSerializable("MyDollBean");

        // 初始化界面显示信息
        // 设置商品
        GlideUtils.loadUrlImg(this, myDollBean.getDollImageUrl(), dollGoodsInfo_image_imgv); // 商品图
        dollGoodsInfo_name_tv.setText(myDollBean.getName()); // 商品名称
        dollGoodsInfo_time_tv.setText(Util.millis2String(myDollBean.getTime(), "yyyy.MM.dd HH:mm:ss")); // 抓中记录时间
        // 设置下方用户和娃娃机信息
        GlideUtils.urlCirclePicNoBg(myDollBean.getUserHeader(), 40, 40, dollGoodsInfo_userHeader_imgv); // 玩家头像
        dollGoodsInfo_userName_tv.setText(myDollBean.getUserNickName()); // 玩家昵称
        dollGoodsInfo_goodsType_tv.setText(myDollBean.getDepositStatus()); // 寄存状态
        dollGoodsInfo_roomName_tv.setText(myDollBean.getRoomName()); // 房间名称
        dollGoodsInfo_roomNumber_tv.setText(myDollBean.getRoomNumber()); // 房间号
    }

    @Override
    protected void requestData() {
    }

    @OnClick({R.id.titleView_goback_imgv, R.id.dollGoodsInfo_redeemGoods_tv, R.id.dollGoodsInfo_redeemSilver_tv})
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.titleView_goback_imgv:
                finish();
                break;
            case R.id.dollGoodsInfo_redeemGoods_tv:
                new MessageDialog.Builder(this)
                        .setTitle("提示")
                        .setMessage("确定要兑换此商品吗？")
                        .setOnPositiveBtnClickListener("确认", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                showToast("确认兑换");
                            }
                        })
                        .setOnNegativeBtnClickListener("取消", null).create().show();
                break;
            case R.id.dollGoodsInfo_redeemSilver_tv:
                new MessageDialog.Builder(this)
                        .setTitle("提示")
                        .setMessage("确定要把此商品兑换成银两吗？")
                        .setOnPositiveBtnClickListener("确认", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO 请求兑换银两接口
                                new RedemptionResultDialog.Builder(DollGoodsInfoActivity.this).setMoneyStr(String.valueOf(myDollBean.getConvertible())).create().show();
                            }
                        })
                        .setOnNegativeBtnClickListener("取消", null).create().show();
                break;
        }
    }
}
