package com.txd.hzj.wjlp.catchDoll.ui.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.tencent.rtmp.TXLiveConstants;
import com.tencent.rtmp.TXVodPlayer;
import com.tencent.rtmp.ui.TXCloudVideoView;
import com.txd.hzj.wjlp.Constant;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;

/**
 * 创建者：voodoo_jie
 * <br>创建时间：2018/09/20 020上午 11:51
 * <br>功能描述：视频播放（回放）界面
 */
public class VideoPlayerActivity extends BaseAty {

    @ViewInject(R.id.titleView_share_imgv)
    public ImageView titleView_share_imgv;
    @ViewInject(R.id.titleView_title_tv)
    public TextView titleView_title_tv;

    @ViewInject(R.id.videoPlayer_video_txcvv) // 视频播放View
    public TXCloudVideoView videoPlayer_video_txcvv;

    private String videoPlayerUrl; // 视频地址
    private TXVodPlayer txVodPlayer; // 腾讯点播View对象

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_video_player;
    }

    @Override
    protected void initialized() {
        titleView_title_tv.setText("视频回放");
        titleView_share_imgv.setVisibility(View.VISIBLE);
        videoPlayerUrl = getIntent().getExtras().getString(Constant.VIDEO_VOD_URL_KEY);
        initPlayer();
        startPlayer();
    }

    @Override
    protected void requestData() {
    }

    private void initPlayer() {
        if (txVodPlayer == null) {
            txVodPlayer = new TXVodPlayer(this);
        }
        txVodPlayer.setRenderMode(TXLiveConstants.RENDER_MODE_ADJUST_RESOLUTION); // 设置视频居中显示，但是可能会有黑边
        txVodPlayer.setPlayerView(videoPlayer_video_txcvv); // 关联VideoView
    }

    private void startPlayer() {
        if (videoPlayerUrl != null && !videoPlayerUrl.isEmpty()) {
            txVodPlayer.startPlay(videoPlayerUrl);
        } else {
            showToast("视频地址出错，传入为空");
        }
    }

    @OnClick({R.id.titleView_goback_imgv, R.id.titleView_share_imgv})
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.titleView_goback_imgv:
                beBack(v);
                break;
            case R.id.titleView_share_imgv:
                toShare("title", "pic", "url", "测试分享Context", "id", "shapeType");
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        txVodPlayer.stopPlay(true);
        videoPlayer_video_txcvv.onDestroy();
    }

}
