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

import com.ants.theantsgo.gson.GsonUtil;
import com.ants.theantsgo.tool.glide.GlideUtils;
import com.ants.theantsgo.util.L;
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
import com.txd.hzj.wjlp.catchDoll.bean.HomeBannerBean;
import com.txd.hzj.wjlp.catchDoll.bean.HomeFragmentBean;
import com.txd.hzj.wjlp.catchDoll.bean.HomeVictoryBean;
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
import com.txd.hzj.wjlp.catchDoll.util.Util;
import com.txd.hzj.wjlp.catchDoll.view.MarqueeView;
import com.txd.hzj.wjlp.catchDoll.view.NoScrollRecyclerView;
import com.txd.hzj.wjlp.catchDoll.view.nineLotteryView.LuckyMonkeyPanelView;
import com.txd.hzj.wjlp.http.catchDoll.Catcher;
import com.youth.banner.loader.ImageLoader;
import com.zhouwei.mzbanner.MZBannerView;
import com.zhouwei.mzbanner.holder.MZHolderCreator;

import org.json.JSONArray;
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

    private int signInPosition = -1; // 转盘签到指定位置 小于0则随机转，大于0等于0，指定位置

    private List<HomeBannerBean> bannerList; // 轮播图列表
    private List<RoomBean> roomList; // 房间列表
    private List<HomeVictoryBean> victoryList; // 房间列表
    private String playerUrl1, playerUrl2;

    private SockAPP sendThread;

    private int page = 1; // 当前第几页
    private int per = 0; // 每页加载的房间数量，为0的情况下后台默认回传10个
    private String inRoomNumber; // 点击房间的房间号
    private String inRoomMac; // 点击房间的Mac地址

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initialized() {
    }

    @Override
    protected void requestData() {

        Catcher.getRoomList(page, per, this);

        homeFgt_smartRefresh_llayout.setEnableAutoLoadMore(false); // 是否启用列表惯性滑动到底部时自动加载更多
        homeFgt_smartRefresh_llayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                homeFgt_smartRefresh_llayout.finishRefresh(500);
                page = 1; // 下拉刷新 Page = 1
                Catcher.getRoomList(page, per, HomeFragment.this);
            }
        });
        homeFgt_smartRefresh_llayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                homeFgt_smartRefresh_llayout.finishLoadMore(500);
                page++;
                Catcher.getRoomList(page, per, HomeFragment.this);
            }
        });

    }

    /**
     * 设置消息横向滚动跑马灯效果
     */
    private void setMarqueeMessage(List<HomeVictoryBean> list) {
        victoryList = list;

        for (int i = 0; i < victoryList.size(); i++) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.item_marquee_view, null);
            ImageView itemMarquee_show_imgv = view.findViewById(R.id.itemMarquee_show_imgv);
            TextView itemMarquee_show_tv = view.findViewById(R.id.itemMarquee_show_tv);
            HomeVictoryBean homeVictoryBean = victoryList.get(i);
            GlideUtils.urlCirclePicNoBg(homeVictoryBean.getHead_pic(), 20, 20, itemMarquee_show_imgv);
            itemMarquee_show_tv.setText(homeVictoryBean.getNickname());
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
    private void setBanner(List<HomeBannerBean> list) {
        bannerList = list;

        // 先设置点击监听事件，然后再去添加item
        homeFgt_bannerShow_banner.setBannerPageClickListener(new MZBannerView.BannerPageClickListener() {
            @Override
            public void onPageClick(View view, int i) {
                showToast(bannerList.get(i).getPicture());
            }
        });
        homeFgt_bannerShow_banner.setPages(bannerList, new MZHolderCreator<HomeBannerViewHolder>() {
            @Override
            public HomeBannerViewHolder createViewHolder() {
                return new HomeBannerViewHolder();
            }
        });
        homeFgt_bannerShow_banner.start();
    }

    @Override
    public void onPause() {
        super.onPause();
        homeFgt_bannerShow_banner.pause();
    }

    /**
     * 设置房间展示列表
     */
    private void setRoomList(List<RoomBean> list) {
        if (list.size() <= 0) {
            showToast("没有更多房间了！");
            return;
        }
        if (page == 1) {
            roomList = new ArrayList<>();
            roomList = list;
        } else {
            roomList.addAll(list);
        }
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
        RoomBean roomBean = roomList.get(position);
        inRoomNumber = roomBean.getId(); // 点击房间的房间号
        inRoomMac = roomBean.getMac(); // 点击房间的Mac地址
        if (inRoomNumber.isEmpty()) {
            showToast("房间异常，未获取到房间号！");
            return;
        }
        if (inRoomMac.isEmpty()) {
            showToast("房间异常，未获取到房间MAC地址！");
            return;
        }
        // 房间状态不为空并且方建伟空闲状态时可进入房间
        if (roomBean.getStatus() != null && roomBean.getStatus().equals("0")) {
            showProgressDialog("正在连接房间，请稍后....");
            sendThread = new SockAPP();
            sendThread.StartWokring(handler, Constant.SERVER_IP, Constant.SERVER_PORT);
            Constant.SOCK_APP = sendThread;
        } else {
            showToast("房间占用中，请选择其他房间！");
        }
    }

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Log.e(TAG, "handleMessage: " + msg.toString());
            switch (msg.what) {
                case Constant.SOCKET_LINE_SUCCESS:
                    L.i("Socket连接成功....");
                    String amac = inRoomNumber; // 房间号 id
                    String mac = inRoomMac; // 机器MAC地址
                    //发送进设备命令-并切换界面
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

    /**
     * 新人奖励
     *
     * @param moneyStr 奖励数据
     */
    private void showNewcomer(String moneyStr) {
        new NewcomerRewardDialog.Builder(getActivity())
                .setMoneyStr(moneyStr)
                .setOnClickListener("立即领取", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).create().show();
    }

    /**
     * 签到转盘
     *
     * @param signInPrizeBeans 奖品项列表
     */
    private void showSignInPrize(final List<SignInPrizeBean> signInPrizeBeans) {
        new LuckMonkeyDialog.Builder(getActivity())
                .setData(signInPrizeBeans)
                .setOnStartClickListener(new LuckMonkeyDialog.OnStartClickListener() {
                    @Override
                    public void onStartClick(LuckyMonkeyPanelView luckyMonkeyPanelView) {
                        // 如果回传的值小于0则直接随机生成停止的位置，否则的话直接设置后台指定的位置
                        luckyMonkeyPanelView.startGame(signInPosition < 0 ? new Random().nextInt(8) : signInPosition);
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
                            case 3: // 未中奖
                                break;
                            default:
                                break;
                        }
                    }
                })
                .create().show();
    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        if (requestUrl.contains("Catcher/getRoomList")) { // 获取房间列表
            L.e("onComplete =====> " + jsonStr);
            try {
                JSONObject requestJson = new JSONObject(jsonStr);
                JSONObject dataJson = requestJson.getJSONObject("data");

                // 房间列表
                List<RoomBean> tempRoomList = new ArrayList<>();
                JSONArray listArray = dataJson.getJSONArray("list");
                for (int i = 0; i < listArray.length(); i++) {
                    JSONObject roomListJson = listArray.getJSONObject(i);
                    RoomBean roomBean = GsonUtil.GsonToBean(roomListJson.toString(), RoomBean.class);
                    tempRoomList.add(roomBean);
                }
                setRoomList(tempRoomList);

                // 设置抓中记录
                List<HomeVictoryBean> tempVictoryList = new ArrayList<>();
                JSONArray victoryArray = dataJson.getJSONArray("victory");
                for (int i = 0; i < victoryArray.length(); i++) {
                    JSONObject victoryListJson = victoryArray.getJSONObject(i);
                    HomeVictoryBean victoryBean = GsonUtil.GsonToBean(victoryListJson.toString(), HomeVictoryBean.class);
                    tempVictoryList.add(victoryBean);
                }
                setMarqueeMessage(tempVictoryList);

                // 设置轮播图
                List<HomeBannerBean> tempBannerList = new ArrayList<>();
                JSONArray bannerArray = dataJson.getJSONArray("banner");
                for (int i = 0; i < bannerArray.length(); i++) {
                    JSONObject bannerListJson = bannerArray.getJSONObject(i);
                    HomeBannerBean bannerBean = GsonUtil.GsonToBean(bannerListJson.toString(), HomeBannerBean.class);
                    bannerBean.setPicture("http://www.pptbz.com/pptpic/UploadFiles_6909/201203/2012031220134655.jpg");
                    tempBannerList.add(bannerBean);
                }
                setBanner(tempBannerList);

                JSONObject status = dataJson.getJSONObject("status");
                if (status.getInt("sign") == 1) { // 未签到

                    List<SignInPrizeBean> signInPrizeBeans = new ArrayList<>();
                    signInPrizeBeans.add(new SignInPrizeBean(1, "", "https://pic.52112.com/icon/256/20180327/13670/873122.png", "+10"));
                    signInPrizeBeans.add(new SignInPrizeBean(2, "", "", "再来一次"));
                    signInPrizeBeans.add(new SignInPrizeBean(1, "", "https://pic.52112.com/icon/256/20180327/13670/873122.png", "+20"));
                    signInPrizeBeans.add(new SignInPrizeBean(3, "", "https://pic.52112.com/icon/256/20181121/25067/1200094.png", ""));
                    signInPrizeBeans.add(new SignInPrizeBean(1, "", "https://pic.52112.com/icon/256/20180327/13670/873122.png", "+30"));
                    signInPrizeBeans.add(new SignInPrizeBean(2, "", "", "再来一次"));
                    signInPrizeBeans.add(new SignInPrizeBean(1, "", "https://pic.52112.com/icon/256/20180327/13670/873122.png", "+50"));
                    signInPrizeBeans.add(new SignInPrizeBean(3, "", "https://pic.52112.com/icon/256/20181121/25067/1200094.png", ""));

                    showSignInPrize(signInPrizeBeans);
                }
                if (status.getInt("new") == 0) { // 未领取新人奖励
                    showNewcomer("100银两");
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
}
