package com.txd.hzj.wjlp.catchDoll.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ants.theantsgo.tool.glide.GlideUtils;
import com.bumptech.glide.Glide;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.txd.hzj.wjlp.Constant;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.catchDoll.adapter.HomeBannerViewHolder;
import com.txd.hzj.wjlp.catchDoll.adapter.RoomListAdapter;
import com.txd.hzj.wjlp.catchDoll.base.BaseFgt;
import com.txd.hzj.wjlp.catchDoll.bean.MessageBean;
import com.txd.hzj.wjlp.catchDoll.bean.RoomBean;
import com.txd.hzj.wjlp.catchDoll.bean.SignInPrizeBean;
import com.txd.hzj.wjlp.catchDoll.socketcmd.SockAPP;
import com.txd.hzj.wjlp.catchDoll.ui.activity.AttentionActivity;
import com.txd.hzj.wjlp.catchDoll.ui.activity.GameRoomActivity;
import com.txd.hzj.wjlp.catchDoll.ui.activity.GrabTheRecordActivity;
import com.txd.hzj.wjlp.catchDoll.ui.activity.NewOnlineActivity;
import com.txd.hzj.wjlp.catchDoll.ui.dialog.LuckMonkeyDialog;
import com.txd.hzj.wjlp.catchDoll.ui.dialog.NewcomerRewardDialog;
import com.txd.hzj.wjlp.catchDoll.ui.dialog.SingInResultDialog;
import com.txd.hzj.wjlp.catchDoll.util.L;
import com.txd.hzj.wjlp.catchDoll.util.Util;
import com.txd.hzj.wjlp.catchDoll.view.MarqueeView;
import com.txd.hzj.wjlp.catchDoll.view.NoScrollRecyclerView;
import com.txd.hzj.wjlp.catchDoll.view.nineLotteryView.LuckyMonkeyPanelView;
import com.youth.banner.loader.ImageLoader;
import com.zhouwei.mzbanner.MZBannerView;
import com.zhouwei.mzbanner.holder.MZHolderCreator;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 创建者：voodoo_jie
 * <br>创建时间：2018/09/20 020上午 11:51
 * <br>功能描述：首页Fragment
 */
public class HomeFragment extends BaseFgt implements RoomListAdapter.OnRoomItemClickListener {

    private static final String TAG = "HomeFragment";

    @ViewInject(R.id.homeFgt_smartRefresh_llayout) // 刷新加载
    public RefreshLayout homeFgt_smartRefresh_llayout;
    @ViewInject(R.id.homeFgt_newOnline_llayout) // 最新上线
    public LinearLayout homeFgt_newOnline_llayout;
    @ViewInject(R.id.homeFgt_boutique_llayout) // 高价精品
    public LinearLayout homeFgt_boutique_llayout;
    @ViewInject(R.id.homeFgt_justLove_llayout) // 只爱娃娃
    public LinearLayout homeFgt_justLove_llayout;
    @ViewInject(R.id.homeFgt_girlArea_llayout) // 美女专场
    public LinearLayout homeFgt_girlArea_llayout;
    @ViewInject(R.id.homeFgt_practicalArea_llayout) // 实用专区
    public LinearLayout homeFgt_practicalArea_llayout;

    @ViewInject(R.id.homeFgt_marqueeMessage_mView) // View跑马灯效果控件
    public MarqueeView homeFgt_marqueeMessage_mView;
    @ViewInject(R.id.homeFgt_bannerShow_banner) // 轮播图展示控件
    public MZBannerView homeFgt_bannerShow_banner;
    @ViewInject(R.id.homeFgt_roomShow_gView) // 房间列表控件
    public NoScrollRecyclerView homeFgt_roomShow_gView;


    private List<String> list_path; // 图片网络路径列表
    private List<RoomBean> roomList; // 房间列表
    private String playerUrl1, playerUrl2;

    private boolean isNewcomer = true; // 是否是新人，为展示弹窗
    private SockAPP sendThread;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initialized() {
    }

    @Override
    protected void requestData() {

        homeFgt_smartRefresh_llayout.setEnableAutoLoadMore(false); // 是否启用列表惯性滑动到底部时自动加载更多
        homeFgt_smartRefresh_llayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                homeFgt_smartRefresh_llayout.finishRefresh(500);
                L.e("onRefresh");
            }
        });
        homeFgt_smartRefresh_llayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                homeFgt_smartRefresh_llayout.finishLoadMore(500);
                L.e("onLoadMore");
            }
        });

        final List<SignInPrizeBean> signInPrizeBeans = new ArrayList<>();
        signInPrizeBeans.add(new SignInPrizeBean(1, "", "https://pic.52112.com/icon/256/20180327/13670/873122.png", "+10"));
        signInPrizeBeans.add(new SignInPrizeBean(2, "", "", "再来一次"));
        signInPrizeBeans.add(new SignInPrizeBean(1, "", "https://pic.52112.com/icon/256/20180327/13670/873122.png", "+20"));
        signInPrizeBeans.add(new SignInPrizeBean(3, "", "https://pic.52112.com/icon/256/20181121/25067/1200094.png", ""));
        signInPrizeBeans.add(new SignInPrizeBean(1, "", "https://pic.52112.com/icon/256/20180327/13670/873122.png", "+30"));
        signInPrizeBeans.add(new SignInPrizeBean(2, "", "", "再来一次"));
        signInPrizeBeans.add(new SignInPrizeBean(1, "", "https://pic.52112.com/icon/256/20180327/13670/873122.png", "+50"));
        signInPrizeBeans.add(new SignInPrizeBean(3, "", "https://pic.52112.com/icon/256/20181121/25067/1200094.png", ""));

        if (Util.isTodayFastStart(getActivity())) { // 如果当天是首次启动则弹窗

            new LuckMonkeyDialog.Builder(getActivity())
                    .setData(signInPrizeBeans)
                    .setOnStartClickListener(new LuckMonkeyDialog.OnStartClickListener() {
                        @Override
                        public void onStartClick(LuckyMonkeyPanelView luckyMonkeyPanelView) {
                            luckyMonkeyPanelView.startGame(new Random().nextInt(8));
                        }
                    })
                    .setOnPanelStateListener(new LuckyMonkeyPanelView.PanelStateListener() {
                        @Override
                        public void onPanelStateStart() {
                        }

                        @Override
                        public void onPanelStateStop(final int position) {
                            SignInPrizeBean signInPrizeBean = signInPrizeBeans.get(position);
                            switch (signInPrizeBean.getType()) {
                                case 1: // 抽中奖品
                                    String content = signInPrizeBean.getContent();
                                    new SingInResultDialog.Builder(getActivity()).setMoneyStr(content).setOnBtnClickListener("我知道了", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                        }
                                    }).create().show();
                                    break;
                                default:
                                    break;
                            }
                        }
                    })
                    .create().show();
        }

        if (isNewcomer) { // 如果是新人则显示新人奖励弹窗
            new NewcomerRewardDialog.Builder(getActivity()).setOnClickListener("立即领取", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            }).create().show();
        }

        setMarqueeMessage(); // 设置消息横向滚动跑马灯效果
        setBanner(); // 设置轮播图
        setRoomList(); // 设置房间列表
    }

    /**
     * 设置消息横向滚动跑马灯效果
     */
    private void setMarqueeMessage() {
        List<MessageBean> messageBeans = new ArrayList<>();
        MessageBean messageBean;
        for (int i = 0; i < 8; i++) {
            messageBean = new MessageBean();
            messageBean.setHeadUrl("http://img4.duitang.com/uploads/item/201407/26/20140726141237_32faQ.png");
            messageBean.setContent("测试消息模拟那个谁左边这个头像的人已经抓到了" + i);
            messageBeans.add(messageBean);
        }

        for (int i = 0; i < messageBeans.size(); i++) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.item_marquee_view, null);
            ImageView itemMarquee_show_imgv = (ImageView) view.findViewById(R.id.itemMarquee_show_imgv);
            TextView itemMarquee_show_tv = (TextView) view.findViewById(R.id.itemMarquee_show_tv);
            messageBean = messageBeans.get(i);
            GlideUtils.urlCirclePicNoBg(messageBean.getHeadUrl(), 20, 20, itemMarquee_show_imgv);
            itemMarquee_show_tv.setText(messageBean.getContent());
            homeFgt_marqueeMessage_mView.addViewInQueue(view);
        }
        homeFgt_marqueeMessage_mView.setScrollSpeed(8);
        homeFgt_marqueeMessage_mView.setScrollDirection(MarqueeView.RIGHT_TO_LEFT);
        homeFgt_marqueeMessage_mView.setViewMargin(15);
        homeFgt_marqueeMessage_mView.startScroll();
    }

    /**
     * 设置Banner轮播图
     */
    private void setBanner() {
        list_path = new ArrayList<>();
        list_path.add("http://gss0.baidu.com/9fo3dSag_xI4khGko9WTAnF6hhy/zhidao/pic/item/503d269759ee3d6d8fb6e66a45166d224f4ade1b.jpg");
        list_path.add("http://e.hiphotos.baidu.com/zhidao/pic/item/32fa828ba61ea8d369805b46930a304e241f58d5.jpg");
        list_path.add("http://img1.imgtn.bdimg.com/it/u=227855470,2752218639&fm=26&gp=0.jpg");
        list_path.add("http://img3.imgtn.bdimg.com/it/u=2524367231,3547626596&fm=26&gp=0.jpg");
        list_path.add("http://gss0.baidu.com/-Po3dSag_xI4khGko9WTAnF6hhy/zhidao/pic/item/91529822720e0cf37475ecc80c46f21fbe09aa07.jpg");

        // 先设置点击监听事件，然后再去添加item
        homeFgt_bannerShow_banner.setBannerPageClickListener(new MZBannerView.BannerPageClickListener() {
            @Override
            public void onPageClick(View view, int i) {
                showToast(list_path.get(i));
            }
        });
        homeFgt_bannerShow_banner.setPages(list_path, new MZHolderCreator<HomeBannerViewHolder>() {
            @Override
            public HomeBannerViewHolder createViewHolder() {
                return new HomeBannerViewHolder();
            }
        });

    }

    @Override
    public void onPause() {
        super.onPause();
        homeFgt_bannerShow_banner.pause();
    }

    @Override
    public void onStart() {
        super.onStart();
        homeFgt_bannerShow_banner.start();
    }

    /**
     * 设置房间展示列表
     */
    private void setRoomList() {
        roomList = new ArrayList<>();
        roomList.add(new RoomBean(1, "http://h.hiphotos.baidu.com/zhidao/pic/item/2f738bd4b31c87018e126740237f9e2f0608fff4.jpg", "测试房间1", 10, 0));
        roomList.add(new RoomBean(2, "http://c.hiphotos.baidu.com/zhidao/pic/item/4034970a304e251f27d9d254a586c9177f3e536b.jpg", "测试房间2", 10, 1));
        roomList.add(new RoomBean(3, "http://b-ssl.duitang.com/uploads/blog/201404/23/20140423205758_FJ4NN.jpeg", "测试房间3", 10, 1));
        roomList.add(new RoomBean(4, "http://c.hiphotos.baidu.com/zhidao/pic/item/cdbf6c81800a19d8265bbb793bfa828ba61e4675.jpg", "测试房间4", 10, 1));
        roomList.add(new RoomBean(5, "http://img.zcool.cn/community/0165c75a602e0ba8012113c7d220e7.jpg@2o.jpg", "测试房间5", 10, 0));
        roomList.add(new RoomBean(6, "http://life.southmoney.com/tuwen/UploadFiles_6871/201809/20180919114327818.jpg", "测试房间6", 10, 0));
        roomList.add(new RoomBean(7, "http://ccstatic-1252317822.file.myqcloud.com/portraitimg/2018-02-07/5a7a8526367d2.jpg", "测试房间7", 10, 0));
        roomList.add(new RoomBean(8, "http://img.xspic.com/img/116/194/574068_5.jpg", "测试房间8", 10, 0));
        roomList.add(new RoomBean(9, "http://gss0.baidu.com/-vo3dSag_xI4khGko9WTAnF6hhy/zhidao/pic/item/314e251f95cad1c8fa989c4b783e6709c93d51be.jpg", "测试房间9", 10, 1));
        roomList.add(new RoomBean(10, "http://life.southmoney.com/tuwen/UploadFiles_6871/201809/20180919114327377.jpg", "测试房间10", 10, 0));
        roomList.add(new RoomBean(11, "http://img1.imgtn.bdimg.com/it/u=165501638,2373619033&fm=27&gp=0.jpg", "测试房间11", 10, 0));
        roomList.add(new RoomBean(12, "http://hbimg.b0.upaiyun.com/afd558e0130d4141116a66a282a0d9267f4602721614e-kb2Tjj_fw658", "测试房间12", 10, 1));
        roomList.add(new RoomBean(13, "http://kanimg.9ku.com/kanqq/pic/upload/2018/0530/b78eea8df704a1e831fb6c8778a618cb.jpg", "测试房间13", 10, 0));
        RoomListAdapter adapter = new RoomListAdapter(roomList, getActivity());
        adapter.setOnRoomItemClickListener(this);
        homeFgt_roomShow_gView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        homeFgt_roomShow_gView.setNestedScrollingEnabled(false); // 重新设置外层Scroll滑动阻尼效果
        homeFgt_roomShow_gView.setAdapter(adapter);
    }

    @Override
    protected void immersionInit() {
    }

    @OnClick({R.id.homeFgt_marqueeMessage_llayout, R.id.homeFgt_attention_tv, R.id.homeFgt_close_tv,
            R.id.homeFgt_newOnline_llayout, R.id.homeFgt_boutique_llayout, R.id.homeFgt_justLove_llayout, R.id.homeFgt_girlArea_llayout, R.id.homeFgt_practicalArea_llayout})
    @Override
    public void onClick(View v) {
        super.onClick(v);
        Bundle bundle = new Bundle();
        switch (v.getId()) {
            case R.id.homeFgt_marqueeMessage_llayout: // 滚动消息展示布局点击
                startActivity(GrabTheRecordActivity.class, null);
                break;
            case R.id.homeFgt_attention_tv: // 关注
                bundle.putInt("type", 1);
                startActivity(AttentionActivity.class, bundle);
                break;
            case R.id.homeFgt_close_tv: // 关闭
                getActivity().finish();
                break;
            case R.id.homeFgt_newOnline_llayout: // 新品上线
                bundle.putInt("type", 1);
                startActivity(NewOnlineActivity.class, bundle);
                break;
            case R.id.homeFgt_boutique_llayout: // 高价精品
                bundle.putInt("type", 2);
                startActivity(NewOnlineActivity.class, bundle);
                break;
            case R.id.homeFgt_justLove_llayout: // 只爱娃娃
                bundle.putInt("type", 3);
                startActivity(NewOnlineActivity.class, bundle);
                break;
            case R.id.homeFgt_girlArea_llayout: // 美女专场
                bundle.putInt("type", 4);
                startActivity(NewOnlineActivity.class, bundle);
                break;
            case R.id.homeFgt_practicalArea_llayout: // 实用专区
                bundle.putInt("type", 5);
                startActivity(NewOnlineActivity.class, bundle);
                break;
        }
    }

    /**
     * 房间列表项点击
     *
     * @param position
     */
    @Override
    public void onRoomItemClick(int position) {
        // TODO 先去访问后台数据，允许进入之后再进行连接
        showProgressDialog("正在连接房间，请稍后....");
        sendThread = new SockAPP();
        sendThread.StartWokring(handler, "192.168.0.4", 7771);
        Constant.SOCK_APP = sendThread;
    }

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Log.e(TAG, "handleMessage: " + msg.toString());
            switch (msg.what) {
                case Constant.SOCKET_LINE_SUCCESS:
                    L.i("Socket连接成功....");
                    String amac = "AA121"; // 房间号
                    String mac = "32336AC82E68"; // 机器MAC地址
                    //发送进设备命令-并切换界面
//                    rtmp://pili-publish.aoquzhuwawa.dx1ydb.com/aoquwawaji003/AA121_1
                    JSONObject cmdJson = new JSONObject();
                    try {
//                        cmd:{"cmd":"enter_room","mac":mac} // 进入房间
                        cmdJson.put("cmd", "enter_room");
                        cmdJson.put("mac", mac);

                        String jsoncmd = cmdJson.toString();
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
                    playerUrl1 = "rtmp://pili-publish.aoquzhuwawa.dx1ydb.com/aoquwawaji003/" + amac + "_1";
                    playerUrl2 = "rtmp://pili-publish.aoquzhuwawa.dx1ydb.com/aoquwawaji003/" + amac + "_2";

                    removeProgressDialog(); // 移除进入房间时候展示的等待界面

                    Bundle bundle = new Bundle();
                    bundle.putString(Constant.VIDEO_LIVE_1_URL_KEY, playerUrl1);
                    bundle.putString(Constant.VIDEO_LIVE_2_URL_KEY, playerUrl2);
                    startActivity(GameRoomActivity.class, bundle);
                    break;
                case Constant.SOCKET_LINE_FAIL:
                    removeProgressDialog(); // 移除进入房间时候展示的等待界面
                    showToast("连接失败，暂时无法进入房间。。。。");
                    break;
            }
            super.handleMessage(msg);
        }
    };

    /**
     * 自定义图片加载
     */
    private class MyLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            Glide.with(context).load((String) path).into(imageView); // 传入路径,因为list泛型为String格式,path为Object格式,所以强制类型转换.
        }
    }
}
