package com.txd.hzj.wjlp.catchDoll.ui.activity;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.Constant;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.catchDoll.util.Util;

/**
 * 创建者：voodoo_jie
 * <br>创建时间：2018/09/20 020上午 11:51
 * <br>功能描述：游戏记录详情
 */
public class GameRecordingInfoActivity extends BaseAty {

    @ViewInject(R.id.titleView_title_tv)
    public TextView titleView_title_tv;
    @ViewInject(R.id.gameRecordInfo_header_imgv)
    public ImageView gameRecordInfo_header_imgv;
    @ViewInject(R.id.gameRecordInfo_name_tv)
    public TextView gameRecordInfo_name_tv;
    @ViewInject(R.id.gameRecordInfo_time_tv)
    public TextView gameRecordInfo_time_tv;
    @ViewInject(R.id.gameRecordInfo_type_tv)
    public TextView gameRecordInfo_type_tv;
    @ViewInject(R.id.gameRecordInfo_roomNumber_tv)
    public TextView gameRecordInfo_roomNumber_tv;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_game_recording_info;
    }

    @Override
    protected void initialized() {
        titleView_title_tv.setText("游戏详情");
        gameRecordInfo_header_imgv.setImageResource(R.mipmap.icon_money_unchecked);
        gameRecordInfo_name_tv.setText("不知道是谁");
        gameRecordInfo_time_tv.setText(Util.millis2String(1547111445 * 1000L, "yyyy-MM-dd HH:mm"));
    }

    @Override
    protected void requestData() {

    }

    @OnClick({R.id.titleView_goback_imgv, R.id.gameRecordInfo_roomNumber_llayout, R.id.gameRecordInfo_gameVideo_llayout, R.id.gameRecordInfo_appeal_tv})
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.titleView_goback_imgv:
                finish();
                break;
            case R.id.gameRecordInfo_roomNumber_llayout:
                showToast("房间号");
                break;
            case R.id.gameRecordInfo_gameVideo_llayout:
                Intent intent = new Intent(this, VideoPlayerActivity.class);
                // 网络点播资源《喜欢你》MV
                intent.putExtra(Constant.VIDEO_VOD_URL_KEY, "http://221.228.226.23/11/t/j/v/b/tjvbwspwhqdmgouolposcsfafpedmb/sh.yinyuetai.com/691201536EE4912BF7E4F1E2C67B8119.mp4");
                startActivity(intent);
                break;
            case R.id.gameRecordInfo_appeal_tv:
                startActivity(AppealActivity.class, null);
                break;
        }
    }
}
