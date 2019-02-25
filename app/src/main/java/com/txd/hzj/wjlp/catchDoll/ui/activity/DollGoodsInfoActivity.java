package com.txd.hzj.wjlp.catchDoll.ui.activity;

import android.content.DialogInterface;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ants.theantsgo.tool.glide.GlideUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.catchDoll.ui.dialog.MessageDialog;
import com.txd.hzj.wjlp.catchDoll.ui.dialog.RedemptionResultDialog;

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

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_doll_goods_info;
    }

    @Override
    protected void initialized() {
        titleView_title_tv.setText("商品详情");
        GlideUtils.loadUrlImg(this, "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1546078175734&di=ac93f5e5b7c316a1cd14d93a57ddaaf8&imgtype=0&src=http%3A%2F%2Fimg.wugu.com.cn%2F20180308%2F2031%2F20180308z3tzbgp20nw.jpg", dollGoodsInfo_image_imgv);
        dollGoodsInfo_name_tv.setText("抓娃娃，你有我厉害吗");
        dollGoodsInfo_time_tv.setText("2018.18.18 18:18:18");
        GlideUtils.urlCirclePicNoBg("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1546078336276&di=e0b455ee36d927b678ed70e758489832&imgtype=0&src=http%3A%2F%2Fimg4.duitang.com%2Fuploads%2Fitem%2F201407%2F26%2F20140726141237_32faQ.png", 40, 40, dollGoodsInfo_userHeader_imgv);
        dollGoodsInfo_userName_tv.setText("voodoo_jie");
        dollGoodsInfo_goodsType_tv.setText("寄存中");
        dollGoodsInfo_roomName_tv.setText("为啥是输入房间名字");
        dollGoodsInfo_roomNumber_tv.setText("250");
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
                showToast("兑换商品");
                break;
            case R.id.dollGoodsInfo_redeemSilver_tv:
                new MessageDialog.Builder(this)
                        .setTitle("提示")
                        .setMessage("确定要把此商品兑换成银两吗？")
                        .setOnPositiveBtnClickListener("确认", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                new RedemptionResultDialog.Builder(DollGoodsInfoActivity.this).setMoneyStr("200").create().show();
                            }
                        })
                        .setOnNegativeBtnClickListener("取消", null).create().show();
                break;
        }
    }
}
