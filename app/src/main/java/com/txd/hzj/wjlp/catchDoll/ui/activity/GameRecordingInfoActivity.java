package com.txd.hzj.wjlp.catchDoll.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ants.theantsgo.tool.glide.GlideUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.Constant;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.catchDoll.bean.GameRecordingBean;
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

    private GameRecordingBean mGameRecordingBean;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_game_recording_info;
    }

    @Override
    protected void initialized() {
        showStatusBar(R.id.titleView_bg_rlayout);
        titleView_title_tv.setText("游戏详情");
        mGameRecordingBean = (GameRecordingBean) getIntent().getSerializableExtra("GameRecordingAdapter");

        GlideUtils.urlCirclePicNoBg(mGameRecordingBean.getRoom_pic(), 46, 46, gameRecordInfo_header_imgv);
        gameRecordInfo_name_tv.setText(mGameRecordingBean.getName());
        gameRecordInfo_time_tv.setText(Util.millis2String(Long.parseLong(mGameRecordingBean.getUpdate_time())*1000, "yyyy-MM-dd HH:mm"));
        gameRecordInfo_type_tv.setText((mGameRecordingBean.getMode().equals("1") ) ? "抓取成功" : "抓取失败");
        gameRecordInfo_type_tv.setTextColor((mGameRecordingBean.getMode().equals("1")) ? Color.parseColor("#F54697") : Color.parseColor("#999999"));
        gameRecordInfo_roomNumber_tv.setText(mGameRecordingBean.getRoomid());
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
//                showToast("房间号");
                break;
            case R.id.gameRecordInfo_gameVideo_llayout:
                Intent intent = new Intent(this, VideoPlayerActivity.class);
                // 网络点播资源《喜欢你》MV
                intent.putExtra(Constant.VIDEO_VOD_URL_KEY, "http://221.228.226.23/11/t/j/v/b/tjvbwspwhqdmgouolposcsfafpedmb/sh.yinyuetai.com/691201536EE4912BF7E4F1E2C67B8119.mp4");
                startActivity(intent);
                break;
            case R.id.gameRecordInfo_appeal_tv:
                Bundle bundle = new Bundle();
                bundle.putString("GameRecordingInfoActivityId",mGameRecordingBean.getId());
                startActivity(AppealActivity.class, bundle);
                break;
        }
    }
}
