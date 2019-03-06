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
import com.ants.theantsgo.util.JSONUtils;
import com.ants.theantsgo.util.L;
import com.bumptech.glide.Glide;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.txd.hzj.wjlp.Constant;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseFgt;
import com.txd.hzj.wjlp.catchDoll.adapter.HomeBannerViewHolder;
import com.txd.hzj.wjlp.catchDoll.adapter.RoomListAdapter;
import com.txd.hzj.wjlp.catchDoll.bean.HomeBannerBean;
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
import java.util.Map;

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

    private int signInBtnClickCount = 0; // 点击签到按钮次数，只去控制签到成功结果弹窗上头部文字标题
    private LuckyMonkeyPanelView mluckyMonkeyPanelView;

    private List<HomeBannerBean> bannerList; // 轮播图列表
    private List<RoomBean> roomList; // 房间列表
    private List<HomeVictoryBean> victoryList; // 房间列表
    private String playerUrl1, playerUrl2;

    private SockAPP sendThread;

    private int page = 1; // 当前第几页
    private int per = 0; // 每页加载的房间数量，为0的情况下后台默认回传10个
    private String inRoomNumber; // 点击房间的房间号
    private String inRoomMac; // 点击房间的Mac地址
    private LuckMonkeyDialog.Builder luckMonkeyDialogBuilder;
    private int remainingNumber; // 剩余签到次数
    private ArrayList<Map<String, String>> mSxArray;

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
                if (mSxArray != null && mSxArray.size()>=1){
                    bundle.putInt("type", 1);
                    bundle.putString("clumn",mSxArray.get(0).get("clumn"));
                    bundle.putString("status",mSxArray.get(0).get("status"));
                    startActivity(NewOnlineActivity.class, bundle);
                }else {
                    showToast("暂无数据");
                }
                break;
            case R.id.homeFgt_boutique_llayout: // 高价精品
                if (mSxArray != null && mSxArray.size()>=2){
                    bundle.putInt("type", 2);
                    bundle.putString("clumn",mSxArray.get(1).get("clumn"));
                    bundle.putString("status",mSxArray.get(1).get("status"));
                    startActivity(NewOnlineActivity.class, bundle);
                }else {
                    showToast("暂无数据");
                }
                break;
            case R.id.homeFgt_justLove_llayout: // 只爱娃娃
                if (mSxArray != null && mSxArray.size()>=3){
                    bundle.putInt("type",3);
                    bundle.putString("clumn",mSxArray.get(2).get("clumn"));
                    bundle.putString("status",mSxArray.get(2).get("status"));
                    startActivity(NewOnlineActivity.class, bundle);
                }else {
                    showToast("暂无数据");
                }
                break;
            case R.id.homeFgt_girlArea_llayout: // 美女专场
                if (mSxArray != null && mSxArray.size()>=4){
                    bundle.putInt("type", 4);
                    bundle.putString("clumn",mSxArray.get(3).get("clumn"));
                    bundle.putString("status",mSxArray.get(3).get("status"));
                    startActivity(NewOnlineActivity.class, bundle);
                }else {
                    showToast("暂无数据");
                }
                break;
            case R.id.homeFgt_practicalArea_llayout: // 实用专区
                if (mSxArray != null && mSxArray.size()>=5){
                    bundle.putInt("type", 5);
                    bundle.putString("clumn",mSxArray.get(4).get("clumn"));
                    bundle.putString("status",mSxArray.get(4).get("status"));
                    startActivity(NewOnlineActivity.class, bundle);
                }else {
                    showToast("暂无数据");
                }
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
        Catcher.enterRoom(inRoomNumber, this);
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
//                      cmd:{"cmd":"enter_room","mac":mac} // 进入房间
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
                    playerUrl1 = "rtmp://pili-publish.aoquzhuwawa.dx1ydb.com/aoquwawaji003/" + mac + "_1";
                    playerUrl2 = "rtmp://pili-publish.aoquzhuwawa.dx1ydb.com/aoquwawaji003/" + mac + "_2";

                    removeProgressDialog(); // 移除进入房间时候展示的等待界面

                    Bundle bundle = new Bundle();
                    bundle.putString("inRoomNumber", inRoomNumber);
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
                        Catcher.newAward(HomeFragment.this); // 领取新人奖励
                    }
                }).create().show();
    }

    /**
     * 签到转盘
     *
     * @param signInPrizeBeans 奖品项列表
     */
    private void showSignInPrize(final List<SignInPrizeBean> signInPrizeBeans) {
        luckMonkeyDialogBuilder = new LuckMonkeyDialog.Builder(getActivity());
        luckMonkeyDialogBuilder.setData(signInPrizeBeans);
        luckMonkeyDialogBuilder.setRemainingNumber(remainingNumber);
        luckMonkeyDialogBuilder.setOnStartClickListener(new LuckMonkeyDialog.OnStartClickListener() {
            @Override
            public void onStartClick(LuckyMonkeyPanelView luckyMonkeyPanelView) {
                // 请求接口获取剩余次数和指定位置 在请求成功之后回传的方法中将点击事件 +1
                mluckyMonkeyPanelView = luckyMonkeyPanelView;
                Catcher.userSign(HomeFragment.this);
            }
        });
        luckMonkeyDialogBuilder.setOnPanelStateListener(new LuckyMonkeyPanelView.PanelStateListener() {
            @Override
            public void onPanelStateStart() {
            }

            @Override
            public void onPanelStateStop(final int position) {
                SignInPrizeBean signInPrizeBean = signInPrizeBeans.get(position);
                switch (signInPrizeBean.getType()) {
                    case 1: // 抽中奖品
                        String content = signInPrizeBean.getContent();
                        String contentType = signInPrizeBean.getContentType();
                        new SingInResultDialog.Builder(getActivity())
                                .setShowTitleStr(signInBtnClickCount <= 1 ? "签到成功" : "抽中奖品") // 第一次点击抽中奖品后提示签到成功，后续再次抽中的话直接显示抽中奖品
                                .setContentTypeStr(contentType)
                                .setMoneyStr(content)
                                .setOnBtnClickListener("我知道了", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                }).create().show();
                        break;
                    default:
                        break;
                }
            }
        });
        luckMonkeyDialogBuilder.create().show();
    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        try {
            JSONObject requestJson = new JSONObject(jsonStr);
            if (requestUrl.contains("Catcher/getRoomList")) { // 获取房间列表
                L.e("onComplete =====> " + jsonStr);
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
                    tempBannerList.add(bannerBean);
                }
                setBanner(tempBannerList);

                JSONObject turnable = dataJson.getJSONObject("turntable");
                boolean isSign = turnable.getInt("check") == 0; // 是否签到 0已签到  1未签到
                remainingNumber = turnable.getInt("num"); // 剩余次数
                if (!isSign) { // 未签到
                    List<SignInPrizeBean> signInPrizeBeans = new ArrayList<>();
                    JSONArray sign_news = turnable.getJSONArray("sign_news");
                    SignInPrizeBean signInPrizeBean;
                    for (int i = 0; i < sign_news.length() - 1; i++) { // 回传数据里最后一个位置多传了一个中间的按钮，将其删除掉
                        JSONObject jsonObject = sign_news.getJSONObject(i);
                        signInPrizeBean = GsonUtil.GsonToBean(jsonObject.toString(), SignInPrizeBean.class);
                        signInPrizeBeans.add(signInPrizeBean);
                    }
                    showSignInPrize(signInPrizeBeans);
                }

                JSONObject newPeople = dataJson.getJSONObject("new");
                boolean isNew = newPeople.getInt("is_new") == 0; // 是否是新人 1 非新人 0 新人
                if (isNew) { // 是新人
                    showNewcomer(newPeople.getString("rewardStr"));
                }

                mSxArray = JSONUtils.parseKeyAndValueToMapList(dataJson.getString("sx"));

                return;
            }

            if (requestUrl.contains("newAward")) { // 领取新人奖励返回
                showToast(requestJson.getString("message"));
                return;
            }

            if (requestUrl.contains("userSign")) {
                JSONObject data = requestJson.getJSONObject("data");
                int signInPosition = data.getInt("position"); // 获取回传的位置值
                remainingNumber = data.getInt("remainingNumber"); // 获取剩余次数
                if (remainingNumber > 0) {
                    showToast(new StringBuffer().append("剩余抽奖次数：").append(remainingNumber).toString());
                }
                if (luckMonkeyDialogBuilder != null) {
                    luckMonkeyDialogBuilder.setRemainingNumber(remainingNumber);
                }
                if (mluckyMonkeyPanelView != null) {
                    mluckyMonkeyPanelView.startGame(signInPosition);
                    signInBtnClickCount++; // 主要是控制抽奖结果弹窗展示的提示文字
                }
                return;
            }

            if (requestUrl.contains("enterRoom")) {
                if (requestJson.getJSONObject("data").getString("status").equals("0")) {
                    showProgressDialog("正在连接房间，请稍后....");
                    sendThread = new SockAPP();
                    sendThread.StartWokring(handler, Constant.SERVER_IP, Constant.SERVER_PORT);
                    Constant.SOCK_APP = sendThread;
                } else {
                    showToast(requestJson.getString("message"));
                }
            }

        } catch (JSONException e) {
            L.e(e.toString());
            showToast("回传Json字符串异常");
        }
    }
}
