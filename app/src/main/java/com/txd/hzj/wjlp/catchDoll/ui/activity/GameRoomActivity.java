package com.txd.hzj.wjlp.catchDoll.ui.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ants.theantsgo.tool.glide.GlideUtils;
import com.ants.theantsgo.tools.AlertDialog;
import com.ants.theantsgo.util.L;
import com.bumptech.glide.Glide;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.tencent.rtmp.ITXLivePlayListener;
import com.tencent.rtmp.TXLiveConstants;
import com.tencent.rtmp.TXLivePlayer;
import com.tencent.rtmp.ui.TXCloudVideoView;
import com.txd.hzj.wjlp.Constant;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.catchDoll.adapter.GameRoomGoodsAdapter;
import com.txd.hzj.wjlp.catchDoll.adapter.GameRoomHeaderAdapter;
import com.txd.hzj.wjlp.catchDoll.base.BaseAty;
import com.txd.hzj.wjlp.catchDoll.bean.GameRoomGoodsBean;
import com.txd.hzj.wjlp.catchDoll.bean.GameRoomHeaderBean;
import com.txd.hzj.wjlp.catchDoll.socketcmd.SockAPP;
import com.txd.hzj.wjlp.catchDoll.ui.dialog.CatchFailDialog;
import com.txd.hzj.wjlp.catchDoll.ui.dialog.CatchSuccessDialog;
import com.txd.hzj.wjlp.catchDoll.ui.dialog.ImageDialog;
import com.txd.hzj.wjlp.catchDoll.util.PhoneNetworkUtils;
import com.txd.hzj.wjlp.catchDoll.util.SharedPreferencesUtils;
import com.txd.hzj.wjlp.catchDoll.util.Util;
import com.txd.hzj.wjlp.catchDoll.view.BarrageView;
import com.txd.hzj.wjlp.catchDoll.view.NoScrollRecyclerView;
import com.txd.hzj.wjlp.catchDoll.view.RockerView;
import com.txd.hzj.wjlp.catchDoll.view.VScrollScreenLayout;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * 创建者：voodoo_jie
 * <br>创建时间：2018/09/20 020上午 11:51
 * <br>功能描述：游戏房间界面
 */
public class GameRoomActivity extends BaseAty implements GameRoomGoodsAdapter.OnGoodsItemClickListener {

    private static final String TAG = "GameRoomActivity";

    @ViewInject(R.id.v_scroll_screen_layout)
    public VScrollScreenLayout v_scroll_screen_layout;

    @ViewInject(R.id.gameRoomVideo_video_txcvv)
    public TXCloudVideoView gameRoomVideo_video_txcvv;

    //    @ViewInject(R.id.gameRoomVideo_videoView_rlatout)
//    public  gameRoomVideo_videoView_rlatout;
    @ViewInject(R.id.gameRoomVideo_barrage_barrv)
    public BarrageView gameRoomVideo_barrage_barrv;
    //    @ViewInject(R.id.gameRoomVideo_peopleNumber_tv)
//    public  gameRoomVideo_peopleNumber_tv;
    @ViewInject(R.id.gameRoomVideo_peopleNumber1_imgv)
    public ImageView gameRoomVideo_peopleNumber1_imgv;
    @ViewInject(R.id.gameRoomVideo_peopleNumber2_imgv)
    public ImageView gameRoomVideo_peopleNumber2_imgv;
    @ViewInject(R.id.gameRoomVideo_peopleNumber3_imgv)
    public ImageView gameRoomVideo_peopleNumber3_imgv;
    @ViewInject(R.id.gameRoomVideo_countdownTime_rlayout)
    public RelativeLayout gameRoomVideo_countdownTime_rlayout;
    @ViewInject(R.id.gameRoomVideo_countdownTime_tv)
    public TextView gameRoomVideo_countdownTime_tv;
//    @ViewInject(R.id.gameRoomVideo_roomNumber_tv)
//    public  gameRoomVideo_roomNumber_tv;
//    @ViewInject(R.id.gameRoomVideo_price_tv)
//    public  gameRoomVideo_price_tv;
//    @ViewInject(R.id.gameRoomVideo_balance_tv)
//    public  gameRoomVideo_balance_tv;
    @ViewInject(R.id.gameRoomVideo_music_imgv) // 音乐开关
    public ImageView gameRoomVideo_music_imgv;
    @ViewInject(R.id.gameRoomVideo_direction_rockview)
    public RockerView gameRoomVideo_direction_rockview;
    @ViewInject(R.id.gameRoomVideo_showOne_rlayout)
    public RelativeLayout gameRoomVideo_showOne_rlayout;
    @ViewInject(R.id.gameRoomVideo_showTwo_rlayout)
    public RelativeLayout gameRoomVideo_showTwo_rlayout;
    @ViewInject(R.id.gameRoomVideo_up_imgv) // 界面底部上划按钮
    public ImageView gameRoomVideo_up_imgv;
    @ViewInject(R.id.gameRoomList_recentlyArrested_rlView) // 抓中记录头像
    public RecyclerView gameRoomList_recentlyArrested_rlView;
    @ViewInject(R.id.gameRoomList_bannerShow_banner) // 轮播图
    public Banner gameRoomList_bannerShow_banner;
    @ViewInject(R.id.gameRoomList_goodsList_nrlView) // 商品列表
    public NoScrollRecyclerView gameRoomList_goodsList_nrlView;
    private List<GameRoomGoodsBean> gameRoomGoodsBeans;

    private CountDownTimer gameCountDownTimer; // 游戏开始倒计时

    private String videoUrl1;
    private String videoUrl2;
    private String videoUrl; // 直播地址
    private boolean isMute = false; // 是否静音
    private TXLivePlayer txLivePlayer;

    private int tempUserMoney = 5; // 临时变量用户余额
    private int tempGameMoney = 10; // 临时变量抓娃娃单局价格

    private SockAPP sendThread;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_game_room;
    }

    @Override
    protected void initialized() {

        sendThread = Constant.SOCK_APP;

        Bundle bundle = getIntent().getExtras();
        videoUrl1 = bundle.getString(Constant.VIDEO_LIVE_1_URL_KEY);
        videoUrl2 = bundle.getString(Constant.VIDEO_LIVE_2_URL_KEY);
        videoUrl = videoUrl1;

        // 设置屏蔽页面上下滑动事件，避免摇杆滑动与界面冲突
        gameRoomVideo_direction_rockview.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                    case MotionEvent.ACTION_MOVE:
                        VScrollScreenLayout.TOUCH_BOOLEAN = false;
                        break;
                    case MotionEvent.ACTION_UP:
                        VScrollScreenLayout.TOUCH_BOOLEAN = true;
                        break;
                }
                return false;
            }
        });
        // 获取是否是设置的静音，SharedPreferences在设置中保存的是背景音乐是否打开，如果背景音乐打开，返回值为true，静音应该是false，所以刚好取反
        isMute = !SharedPreferencesUtils.getBoolean(this, "bgMusic", false);
        gameRoomVideo_music_imgv.setImageResource(isMute ? R.mipmap.icon_video_music_close : R.mipmap.icon_video_music_open);
        gameRoomVideo_direction_rockview.setCallBackMode(RockerView.CallBackMode.CALL_BACK_MODE_STATE_CHANGE);
        gameRoomVideo_direction_rockview.setOnShakeListener(RockerView.DirectionMode.DIRECTION_4_ROTATE_45, new RockerView.OnShakeListener() {
            @Override
            public void onStart() {
            }

            @Override
            public void direction(RockerView.Direction direction) {
                JSONObject operationJson = new JSONObject();
                try {
                    operationJson.put("cmd", "operation");
                    switch (direction) {
                        case DIRECTION_UP: // 上
                            operationJson.put("type", 1);
                            break;
                        case DIRECTION_DOWN: // 下
                            operationJson.put("type", 0);
                            break;
                        case DIRECTION_LEFT: // 左
                            operationJson.put("type", 2);
                            break;
                        case DIRECTION_RIGHT: // 右
                            operationJson.put("type", 3);
                            break;
                    }
                    String jsoncmd = operationJson.toString();
                    byte msg_content[] = new byte[3 + jsoncmd.length()];
                    msg_content[0] = (byte) 0xda;
                    msg_content[1] = (byte) (jsoncmd.length() / 256);
                    msg_content[2] = (byte) (jsoncmd.length() % 256);
                    System.arraycopy(jsoncmd.getBytes(), 0, msg_content, 3, jsoncmd.getBytes().length);
                    if (sendThread != null) {
                        sendThread.SendOut(msg_content);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFinish() {
                try {
                    JSONObject operationJson = new JSONObject();
                    operationJson.put("cmd", "operation");
                    operationJson.put("type", 5);
                    String jsoncmd = operationJson.toString();
                    byte msg_content[] = new byte[3 + jsoncmd.length()];
                    msg_content[0] = (byte) 0xda;
                    msg_content[1] = (byte) (jsoncmd.length() / 256);
                    msg_content[2] = (byte) (jsoncmd.length() % 256);
                    System.arraycopy(jsoncmd.getBytes(), 0, msg_content, 3, jsoncmd.getBytes().length);
                    if (sendThread != null) {
                        sendThread.SendOut(msg_content);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        sendThread.SetHandler(myHandler); // 设置Handler，消息回调

    }


    @Override
    protected void requestData() {
        String msg;
        if (PhoneNetworkUtils.getNetWorkType(this) != Constant.NETWORK_CLASS_WIFI) {
            if (PhoneNetworkUtils.getNetWorkType(this) != Constant.NETWORK_CLASS_UNKNOW) {
                // 网络类型未知
                msg = "当前网络连接类型未知，请注意流量使用情况！";
            } else {
                // 2G/3G/4G流量网络
                msg = "当前使用数据流量，是否继续播放？";
            }
            new AlertDialog(this)
                    .setTitle("流量提醒")
                    .setMsg(msg)
                    .setPositiveButton("播放", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            initPlayer();
                        }
                    })
                    .setNegativeButton("关闭", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            GameRoomActivity.this.finish();
                        }
                    }).show();
            showToast("非WIFI环境");
        } else {
            initPlayer();
        }
        initBarrage();
        initHeaderList();
        initBanner();
        initGoods();

        GlideUtils.urlCirclePicNoBg("http://kanimg.9ku.com/kanqq/pic/upload/2018/0530/b78eea8df704a1e831fb6c8778a618cb.jpg", 25, 25, gameRoomVideo_peopleNumber1_imgv);
        GlideUtils.urlCirclePicNoBg("http://img1.imgtn.bdimg.com/it/u=165501638,2373619033&fm=27&gp=0.jpg", 25, 25, gameRoomVideo_peopleNumber2_imgv);
        GlideUtils.urlCirclePicNoBg("http://img.xspic.com/img/116/194/574068_5.jpg", 25, 25, gameRoomVideo_peopleNumber3_imgv);

    }

    /**
     * 初始化播放器
     */
    private void initPlayer() {
        if (txLivePlayer == null) {
            txLivePlayer = new TXLivePlayer(this);
            txLivePlayer.setRenderMode(TXLiveConstants.RENDER_MODE_ADJUST_RESOLUTION);
        }
        txLivePlayer.setPlayerView(gameRoomVideo_video_txcvv);
        txLivePlayer.setMute(isMute); // 设置是否静音
        txLivePlayer.setPlayListener(new ITXLivePlayListener() {
            @Override
            public void onPlayEvent(int i, Bundle bundle) {
                switch (i) {
                    case TXLiveConstants.PLAY_EVT_GET_MESSAGE:
                        String msg = null;
                        try {
                            msg = new String(bundle.getByteArray(TXLiveConstants.EVT_GET_MSG), "UTF-8");
                            L.i("onPlayEvent: " + msg);
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                        break;
                    case TXLiveConstants.PLAY_EVT_PLAY_END: // 视频播放结束
                    case TXLiveConstants.PLAY_ERR_NET_DISCONNECT: // 网络断连，且经多次重连亦不能恢复，更多重试请自行重启播放
                        L.i(new StringBuffer().append("onPlayEvent: 播放停止").append(i == TXLiveConstants.PLAY_EVT_PLAY_END ? "视频播放结束" : "网络断连，且经多次重连亦不能恢复，更多重试请自行重启播放").toString());
                        break;

                }
                L.e("onPlayEvent: " + bundle.toString());
            }

            @Override
            public void onNetStatus(Bundle bundle) {
//                NET_STATUS_CPU_USAGE	当前瞬时 CPU 使用率
//                NET_STATUS_VIDEO_WIDTH	视频分辨率 - 宽
//                NET_STATUS_VIDEO_HEIGHT	视频分辨率 - 高
//                NET_STATUS_NET_SPEED	当前的网络数据接收速度
//                NET_STATUS_NET_JITTER	网络抖动情况，抖动越大，网络越不稳定
//                NET_STATUS_VIDEO_FPS	当前流媒体的视频帧率
//                NET_STATUS_VIDEO_BITRATE	当前流媒体的视频码率，单位 kbps
//                NET_STATUS_AUDIO_BITRATE	当前流媒体的音频码率，单位 kbps
//                NET_STATUS_CACHE_SIZE	缓冲区（jitterbuffer）大小，缓冲区当前长度为 0，说明离卡顿就不远了
//                NET_STATUS_SERVER_IP	连接的服务器 IP
            }
        });
        startPlayer();
    }

    /**
     * 开始播放视频
     */
    private void startPlayer() {
        if (videoUrl != null && !videoUrl.isEmpty()) {
            // 参数1：地址Url
            // 参数2：url直播地址视频类型：
            //      PLAY_TYPE_LIVE_RTMP:     0 URL为RTMP直播地址
            //      PLAY_TYPE_LIVE_FLV:      1 URL为FLV直播地址（推荐FLV）
            //      PLAY_TYPE_LIVE_RTMP_ACC: 5 低延迟链路地址（仅适合于连麦场景）
            //      PLAY_TYPE_VOD_HLS:       3 URL为HLS（m3u8）播放地址
            txLivePlayer.startPlay(videoUrl, TXLivePlayer.PLAY_TYPE_LIVE_RTMP);
        } else {
            L.e("startPlayer: 播放地址为空");
        }
    }

    /**
     * 初始化弹幕
     */
    private void initBarrage() {
        gameRoomVideo_barrage_barrv.setBackgroundResource(0);
        gameRoomVideo_barrage_barrv.setRowNum(5);
        List<String> msgList = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            if (i % 2 == 0) {
                msgList.add(new StringBuffer().append("第").append(i).append("条弹幕").toString());
            } else {
                msgList.add(new StringBuffer().append("第").append(i).append("<font color=#FFC000>条弹幕</font>").append("第").append(i).append("<font color=#FFC000>条弹幕</font>").toString());
            }
        }
        Util.showBarrage(GameRoomActivity.this, gameRoomVideo_barrage_barrv, msgList);
    }

    /**
     * 初始化头像列表
     */
    private void initHeaderList() {
        List<GameRoomHeaderBean> gameRoomHeaderBeans = new ArrayList<>();
        GameRoomHeaderBean gameRoomHeaderBean;
        for (int i = 0; i < 10; i++) {
            gameRoomHeaderBean = new GameRoomHeaderBean();
            gameRoomHeaderBean.setHeaderUrl("http://img.25pp.com/uploadfile/youxi/images/2015/0324/20150324035941178.jpg");
            gameRoomHeaderBean.setNumber(i + 5);
            gameRoomHeaderBeans.add(gameRoomHeaderBean);
        }
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        gameRoomList_recentlyArrested_rlView.setLayoutManager(layoutManager);
        GameRoomHeaderAdapter headerAdapter = new GameRoomHeaderAdapter(gameRoomHeaderBeans, this);
        gameRoomList_recentlyArrested_rlView.setAdapter(headerAdapter);
    }

    /**
     * 设置Banner轮播图
     */
    private void initBanner() {
        final List<String> list_path = new ArrayList<>();
        list_path.add("http://gss0.baidu.com/9fo3dSag_xI4khGko9WTAnF6hhy/zhidao/pic/item/503d269759ee3d6d8fb6e66a45166d224f4ade1b.jpg");
        list_path.add("http://e.hiphotos.baidu.com/zhidao/pic/item/32fa828ba61ea8d369805b46930a304e241f58d5.jpg");
        list_path.add("http://img1.imgtn.bdimg.com/it/u=227855470,2752218639&fm=26&gp=0.jpg");
        list_path.add("http://img3.imgtn.bdimg.com/it/u=2524367231,3547626596&fm=26&gp=0.jpg");
        list_path.add("http://gss0.baidu.com/-Po3dSag_xI4khGko9WTAnF6hhy/zhidao/pic/item/91529822720e0cf37475ecc80c46f21fbe09aa07.jpg");
        gameRoomList_bannerShow_banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        // 设置图片加载器
        gameRoomList_bannerShow_banner.setImageLoader(new MyLoader());
        // 设置图片集合
        gameRoomList_bannerShow_banner.setImages(list_path);
        // 设置banner动画效果
        gameRoomList_bannerShow_banner.setBannerAnimation(Transformer.DepthPage);
        // 设置自动轮播，默认为true
        gameRoomList_bannerShow_banner.isAutoPlay(true);
        // 设置轮播时间
        gameRoomList_bannerShow_banner.setDelayTime(2000);
        // 设置指示器位置（当banner模式中有指示器时）
        gameRoomList_bannerShow_banner.setIndicatorGravity(BannerConfig.CENTER);
        gameRoomList_bannerShow_banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                showToast(list_path.get(position));
            }
        });
        // banner设置方法全部调用完毕时最后调用
        gameRoomList_bannerShow_banner.start();
    }

    /**
     * 自定义图片加载
     */
    private class MyLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            Glide.with(context).load((String) path).into(imageView); // 传入路径,因为list为String格式,path为Object格式,所以强制类型转换.
        }
    }

    /**
     * 初始化商品列表
     */
    private void initGoods() {
        gameRoomGoodsBeans = new ArrayList<>();
        GameRoomGoodsBean gameRoomGoodsBean;
        for (int i = 0; i < 10; i++) {
            gameRoomGoodsBean = new GameRoomGoodsBean();
            gameRoomGoodsBean.setImgUrl("http://gss0.baidu.com/9fo3dSag_xI4khGko9WTAnF6hhy/zhidao/pic/item/503d269759ee3d6d8fb6e66a45166d224f4ade1b.jpg");
            gameRoomGoodsBean.setNumber(i + 5);
            gameRoomGoodsBean.setName("巴拉巴拉一大堆");
            gameRoomGoodsBeans.add(gameRoomGoodsBean);
        }
        gameRoomList_goodsList_nrlView.setLayoutManager(new LinearLayoutManager(this));
        GameRoomGoodsAdapter goodsAdapter = new GameRoomGoodsAdapter(gameRoomGoodsBeans, this);
        goodsAdapter.setOnGoodsItemClickListener(this);
        gameRoomList_goodsList_nrlView.setAdapter(goodsAdapter);
    }

    /**
     * 商品列表项点击事件
     *
     * @param position 索引
     */
    @Override
    public void goodsItemClick(int position) {
        GameRoomGoodsBean gameRoomGoodsBean = gameRoomGoodsBeans.get(position);
        new ImageDialog.Builder(this).setUrlPath(gameRoomGoodsBean.getImgUrl()).create().show();
    }

    @OnClick({R.id.gameRoomVideo_goback_imgv, R.id.gameRoomVideo_peopleInRoom_llayout, R.id.gameRoomVideo_checkedRoom_imgv, R.id.gameRoomVideo_collection_imgv,
            R.id.gameRoomVideo_music_imgv, R.id.gameRoomVideo_camera_imgv, R.id.gameRoomVideo_enter_imgv,
            R.id.gameRoomVideo_share_imgv, R.id.gameRoomVideo_start_imgv, R.id.gameRoomVideo_recharge_imgv})
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.gameRoomVideo_goback_imgv: // 退出
                finish();
                break;
            case R.id.gameRoomVideo_peopleInRoom_llayout: // 观众展示布局
                startActivity(PlayerPeopleListActivity.class, null);
                break;
            case R.id.gameRoomVideo_checkedRoom_imgv: // 切换房间
                showToast("切换房间");
                break;
            case R.id.gameRoomVideo_collection_imgv: // 收藏
                showToast("收藏");
                break;
            case R.id.gameRoomVideo_music_imgv: // 音乐
                isMute = !isMute;
                txLivePlayer.setMute(isMute); // 设置是否静音
                gameRoomVideo_music_imgv.setImageResource(isMute ? R.mipmap.icon_video_music_close : R.mipmap.icon_video_music_open);
                break;
            case R.id.gameRoomVideo_camera_imgv: // 相机
                if (videoUrl.equals(videoUrl1)) {
                    videoUrl = videoUrl2;
                } else {
                    videoUrl = videoUrl1;
                }
                initPlayer();
                break;
            case R.id.gameRoomVideo_enter_imgv: // 下抓
                // {"cmd":"operation","type":4}
                String enter_jsoncmd = "{\"cmd\":\"operation\",\"type\":4}";
                byte msg_content[] = new byte[3 + enter_jsoncmd.length()];
                msg_content[0] = (byte) 0xda;
                msg_content[1] = (byte) (enter_jsoncmd.length() / 256);
                msg_content[2] = (byte) (enter_jsoncmd.length() % 256);
                System.arraycopy(enter_jsoncmd.getBytes(), 0, msg_content, 3, enter_jsoncmd.getBytes().length);
                if (sendThread != null) {
                    sendThread.SendOut(msg_content);
                }
                break;
            case R.id.gameRoomVideo_share_imgv: // 分享
                toShare("抓娃娃", "pic", "url", "content", "id", "shapetype");
                break;
            case R.id.gameRoomVideo_start_imgv: // 开局
//                    new MoneyNotEnoughDialog.Builder(this)
//                            .setOnBtnClickListener("购买银两", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//                                    startActivity(MoneyActivity.class, null);
//                                }
//                            })
//                            .create().show();
//                    tempUserMoney += 100;

                String start_jsoncmd = "{\"cmd\":\"start_game\"}";
                byte start_msg_content[] = new byte[3 + start_jsoncmd.length()];
                start_msg_content[0] = (byte) 0xda;
                start_msg_content[1] = (byte) (start_jsoncmd.length() / 256);
                start_msg_content[2] = (byte) (start_jsoncmd.length() % 256);
                System.arraycopy(start_jsoncmd.getBytes(), 0, start_msg_content, 3, start_jsoncmd.getBytes().length);
                if (sendThread != null) {
                    sendThread.SendOut(start_msg_content);
                }
//                }
                break;
            case R.id.gameRoomVideo_recharge_imgv: // 充值
                startActivity(MoneyActivity.class, null);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        txLivePlayer.stopPlay(true); // true 表示清除最后一帧
        gameRoomVideo_video_txcvv.onDestroy();

        String jsoncmd = "{\"cmd\":\"exit_room\"}";
        byte msg_content[] = new byte[3 + jsoncmd.length()];
        msg_content[0] = (byte) 0xda;
        msg_content[1] = (byte) (jsoncmd.length() / 256);
        msg_content[2] = (byte) (jsoncmd.length() % 256);
        System.arraycopy(jsoncmd.getBytes(), 0, msg_content, 3, jsoncmd.getBytes().length);
        if (sendThread != null) {
            sendThread.SendOut(msg_content);
        }

    }

    @SuppressLint("HandlerLeak")
    Handler myHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Log.e(TAG, "handleMessage: " + msg.toString());
            switch (msg.what) {
                case 10:
                    int msg_len = msg.arg1;
                    byte test_data[] = (byte[]) (msg.obj);
                    String jsonString = new String(test_data, 0, msg_len);//从第八位开始，最后一位不要。
                    Log.e("PlayerA", jsonString);
                    try {
                        JSONObject resultJson = new JSONObject(jsonString);
                        String cmdStr = resultJson.getString("cmd");
                        if (cmdStr.equals("start_game")) { // 开始游戏
                            if (resultJson.getInt("ret") == 1) { // 返回成功标志位
                                v_scroll_screen_layout.setIsSlidable(false);
                                gameRoomVideo_countdownTime_rlayout.setVisibility(View.VISIBLE); // 倒计时控件显示
                                gameRoomVideo_up_imgv.setVisibility(View.INVISIBLE); // 上划的图片占位隐藏
                                gameCountDownTimer = new CountDownTimer(60 * 1000, 1 * 1000) {
                                    @Override
                                    public void onTick(long millisUntilFinished) {
                                        gameRoomVideo_countdownTime_tv.setText(new StringBuffer().append(millisUntilFinished / 1000).append("s"));
                                    }

                                    @Override
                                    public void onFinish() {
                                        gameRoomVideo_countdownTime_tv.setText(new StringBuffer().append("0").append("s"));
                                        gameRoomVideo_countdownTime_rlayout.setVisibility(View.GONE);
                                        gameRoomVideo_showOne_rlayout.setVisibility(View.GONE);
                                        gameRoomVideo_showTwo_rlayout.setVisibility(View.VISIBLE);
                                        gameRoomVideo_up_imgv.setVisibility(View.VISIBLE);
                                        v_scroll_screen_layout.setIsSlidable(true);
                                    }
                                }.start(); // 设置倒计时
                                gameRoomVideo_showOne_rlayout.setVisibility(View.VISIBLE); // 操纵杆的布局显示
                                gameRoomVideo_showTwo_rlayout.setVisibility(View.GONE); // 开局的布局隐藏
                            }
                        } else if (cmdStr.equals("game_ret")) { // 游戏返回
                            int game_ret = resultJson.getInt("ret");
                            switch (game_ret) {
                                case 0: // 抓取失败
                                    new CatchFailDialog.Builder(GameRoomActivity.this)
                                            .setCountdownTime(10)
                                            .setOnOneTimeBtnClickListener("再来一局", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    showToast("再来一局");
                                                }
                                            }).create().show();
                                    break;
                                case 1: // 抓取成功
                                    new CatchSuccessDialog.Builder(GameRoomActivity.this)
                                            .setCountdownTime(10)
                                            .setImageUrl("http://pic28.photophoto.cn/20130720/0037037114345013_b.jpg")
                                            .setOnOneTimeBtnClickListener("再来一次", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    showToast("再来一次");
                                                }
                                            })
                                            .setOnHelpFriendBtnClickListener("好友助力", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    showToast("好友助力");
                                                }
                                            }).create().show();
                                    break;
                                default: // 异常状态
                                    Toast.makeText(getApplicationContext(), "other code:" + game_ret, Toast.LENGTH_SHORT).show();
                                    break;
                            }
                            v_scroll_screen_layout.setIsSlidable(true);
                            gameRoomVideo_countdownTime_rlayout.setVisibility(View.GONE); // 倒计时控件隐藏
                            gameRoomVideo_up_imgv.setVisibility(View.VISIBLE); // 上划的图片显示
                            gameCountDownTimer.cancel(); // 设置倒计时取消
                            gameRoomVideo_showOne_rlayout.setVisibility(View.GONE); // 操纵杆的布局隐藏
                            gameRoomVideo_showTwo_rlayout.setVisibility(View.VISIBLE); // 开局的布局显示
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
            }
            super.handleMessage(msg);
        }
    };

}
