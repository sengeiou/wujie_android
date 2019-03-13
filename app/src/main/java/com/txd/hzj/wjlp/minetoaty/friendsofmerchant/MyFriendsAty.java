package com.txd.hzj.wjlp.minetoaty.friendsofmerchant;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.ants.theantsgo.base.BaseView;
import com.ants.theantsgo.config.Config;
import com.ants.theantsgo.httpTools.ApiTool2;
import com.ants.theantsgo.util.JSONUtils;
import com.bumptech.glide.Glide;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.huanxin.ui.ChatActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

import cn.gavinliu.android.lib.shapedimageview.ShapedImageView;

/**
 * 创建者：zhangyunfei
 * 创建时间：2019/1/17 13:42
 * 功能描述：以商会友
 */
public class MyFriendsAty extends BaseAty{
    private Context mContext;

    @ViewInject(R.id.titlt_conter_tv)
    private TextView titlt_conter_tv;

    @ViewInject(R.id.redPoint)
    private TextView redPoint;


    @ViewInject(R.id.searchEdit)
    private EditText searchEdit;

    @ViewInject(R.id.friendRecyclerView)
    private RecyclerView friendRecyclerView;

    @ViewInject(R.id.recyclerView)
    private RecyclerView mRecyclerView;

    private MyFriendsAdapter mFriendsAdapter;

    private String mSta_mid;
    private String mMsg_num;
    private String mChange_num;
    private ScreeningResultAty.ScreeningResultAdapter mScreeningResultAdapter;
    private String mType;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_my_friends;
    }

    @Override
    protected void initialized() {
        mContext = this;
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("以商会友");
        mSta_mid = getIntent().getStringExtra("sta_mid");
        mType = getIntent().getStringExtra("type");
        if (mType != null && mType.equals("1")){
            show(mContext,"好友请求在这里");
        }else if (mType != null && mType.equals("2")){
            show(mContext,"会员请求在这里");
        }
        searchEdit.setOnKeyListener(mOnKeyListener);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        friendRecyclerView.setLayoutManager(layoutManager);
        mFriendsAdapter = new MyFriendsAdapter();
        friendRecyclerView.setAdapter(mFriendsAdapter);

        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager2);
        mScreeningResultAdapter = new ScreeningResultAty.ScreeningResultAdapter();
        mRecyclerView.setAdapter(mScreeningResultAdapter);
    }

    public  void show(Context context, String msg) {
        Toast toast = new Toast(context);
        //设置Toast显示位置，居中，向 X、Y轴偏移量均为0
        toast.setGravity(Gravity.TOP|Gravity.RIGHT, 80, 40);
        //获取自定义视图
        View view = LayoutInflater.from(context).inflate(R.layout.toast_view, null);
        TextView tvMessage =  view.findViewById(R.id.tv_message_toast);
        //设置文本
        tvMessage.setText(msg);
        //设置视图
        toast.setView(view);
        //设置显示时长
        toast.setDuration(Toast.LENGTH_SHORT);
        //显示
        toast.show();
    }

    @Override
    protected void requestData() {
    }

    @Override
    protected void onResume() {
        super.onResume();
        bflist(mSta_mid,this);
    }

    void bflist(String sta_mid, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("sta_mid", sta_mid);
        params.addBodyParameter("t", "1");
        apiTool2.postApi(Config.BASE_URL + "OsManager/bflist", params, baseView);
    }

    void get_bfriend( String sta_mid,String phone,String type, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("sta_mid", sta_mid);
        params.addBodyParameter("phone", phone);
        params.addBodyParameter("type", type);
        apiTool2.postApi(Config.BASE_URL + "OsManager/get_bfriend", params, baseView);
    }


    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        Map<String, String> map = JSONUtils.parseKeyAndValueToMap(jsonStr);
        if (requestUrl.endsWith("bflist")){
            friendRecyclerView.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);
            Map<String, String> data = JSONUtils.parseKeyAndValueToMap(map.get("data"));
            mMsg_num = data.get("msg_num");
            mChange_num = data.get("change_num");
            ArrayList<Map<String, String>> arrayList = JSONUtils.parseKeyAndValueToMapList(data.get("list"));
            mFriendsAdapter.setList(arrayList);
            if (Integer.parseInt(mMsg_num)>0 || Integer.parseInt(mChange_num)>0){
                redPoint.setVisibility(View.VISIBLE);
            }else {
                redPoint.setVisibility(View.GONE);
            }
            return;
        }

        if (requestUrl.endsWith("get_bfriend")){
            friendRecyclerView.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.VISIBLE);
            final ArrayList<Map<String, String>> list = JSONUtils.parseKeyAndValueToMapList(map.get("data"));
            mScreeningResultAdapter.setItemList(list);
            mScreeningResultAdapter.setOnItemViewClickLisener(new ScreeningResultAty.ScreeningResultAdapter.onItemViewClickLisener() {
                @Override
                public void onViewClick(int positon) {
                    Map<String, String> map = list.get(positon);
                    Bundle bundle = new Bundle();
                    bundle.putString("sta_mid",mSta_mid);
                    bundle.putSerializable("map", (Serializable) map);
                    startActivity(BusinessFriendDataAty.class,bundle);
                }
            });
            return;
        }
    }

    @Override
    @OnClick({R.id.titlt_right_tv,R.id.groupManagementTv,R.id.addShopKeeperTv})
    public void onClick(View view){
        Bundle bundle = new Bundle();
        bundle.putString("sta_mid",mSta_mid);
        switch (view.getId()){
            case R.id.titlt_right_tv:
                showPop(view);
                break;
            case R.id.groupManagementTv:
                startActivity(GroupManagementAty.class,bundle);
                break;
            case R.id.addShopKeeperTv:
                startActivity(AddShopOwnerAty.class,bundle);
                break;
        }
    }

    private void showPop(View view){
        View contentView = LayoutInflater.from(mContext).inflate(R.layout.pop_myfriends,null);
        TextView changeMemberTv = contentView.findViewById(R.id.changeMemberTv);
        TextView newFriendTv = contentView.findViewById(R.id.newFriendsTv);
        changeMemberTv.setText("会员互换("+mChange_num+")");
        newFriendTv.setText("新的好友("+mMsg_num+")");
        final PopupWindow popupWindow = new PopupWindow(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setContentView(contentView);
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        popupWindow.setFocusable(true);
        popupWindow.showAtLocation(view, Gravity.NO_GRAVITY, (int) (location[0]-view.getWidth()*1.3),location[1]+view.getHeight());
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.alpha = 1.0f;
        window.setAttributes(attributes);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                Window window = getWindow();
                window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                WindowManager.LayoutParams attributes = window.getAttributes();
                attributes.alpha = 1.0f;
                window.setAttributes(attributes);
            }
        });
        changeMemberTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("sta_mid",mSta_mid);
                startActivity(ChangeMembersAty.class,bundle);
                popupWindow.dismiss();

            }
        });

        newFriendTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("sta_mid",mSta_mid);
                startActivity(NewFriendAty.class,bundle);
                popupWindow.dismiss();
            }
        });
    }

    View.OnKeyListener mOnKeyListener = new View.OnKeyListener() {
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if (event.getKeyCode() == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN){
                /*隐藏软键盘*/
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm.isActive()) {
                    imm.hideSoftInputFromWindow(MyFriendsAty.this.getCurrentFocus().getWindowToken(), 0);
                }
                get_bfriend(mSta_mid,searchEdit.getText().toString(),"0",MyFriendsAty.this);
                return true;
            }
            return false;
        }
    };


    private static class MyFriendsAdapter extends RecyclerView.Adapter<MyFriendsAdapter.ViewHolder>{

        private Context mContext;
        private ItemAdapter mItemAdapter;
        private ArrayList<Map<String, String>> mList;

        public MyFriendsAdapter() {
            mList = new ArrayList<>();
        }

        public void setList(ArrayList<Map<String, String>> list) {
            mList.clear();
            mList.addAll(list);
            notifyDataSetChanged();
        }


        @NonNull
        @Override
        public MyFriendsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            mContext = parent.getContext();
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_my_friends,parent,false);
            MyFriendsAdapter.ViewHolder holder = new MyFriendsAdapter.ViewHolder(view);
            ViewUtils.inject(holder,view);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull MyFriendsAdapter.ViewHolder holder, int position) {
            Map<String, String> map = mList.get(position);
            holder.titleTv.setText(map.get("name"));
            ArrayList<Map<String, String>> list = JSONUtils.parseKeyAndValueToMapList(map.get("list"));

            LinearLayoutManager layoutManager = new LinearLayoutManager(mContext){
                @Override
                public boolean canScrollVertically() {
                    return false;
                }
            };
            holder.itemRecyclerView.setLayoutManager(layoutManager);
            if (list != null) {
                mItemAdapter = new ItemAdapter(list);
                holder.itemRecyclerView.setAdapter(mItemAdapter);
            }
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }

        public static class ViewHolder extends RecyclerView.ViewHolder{

            @ViewInject(R.id.titleTv)
            private TextView titleTv;

            @ViewInject(R.id.itemRecyclerView)
            private RecyclerView itemRecyclerView;

            public ViewHolder(View itemView) {
                super(itemView);
            }
        }

    }

    private static class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder>{

        private Context mContext;

        private ArrayList<Map<String, String>> mItemList;

        public ItemAdapter(ArrayList<Map<String, String>> itemList) {
            mItemList = itemList;
        }

        @NonNull
        @Override
        public ItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            mContext = parent.getContext();
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_my_friends_child,parent,false);
            ViewHolder holder = new ViewHolder(view);
            ViewUtils.inject(holder,view);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            final Map<String, String> map = mItemList.get(position);
            final Map<String, String> userInfo = JSONUtils.parseKeyAndValueToMap(map.get("user_info"));
            Glide.with(mContext).load(userInfo.get("head_pic")).into(holder.headImg);
            holder.nameTV.setText(map.get("nickname"));
            final String phone = userInfo.get("phone");
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RequestParams params = new RequestParams();
                    ApiTool2 apiTool2 = new ApiTool2();
                    params.addBodyParameter("sta_mid", ((MyFriendsAty)mContext).mSta_mid);
                    params.addBodyParameter("phone", phone);
                    params.addBodyParameter("type", "0");
                    apiTool2.postApi(Config.BASE_URL + "OsManager/get_bfriend", params, new BaseView() {
                        @Override
                        public void showDialog() {

                        }

                        @Override
                        public void showDialog(String text) {

                        }

                        @Override
                        public void showContent() {

                        }

                        @Override
                        public void removeDialog() {

                        }

                        @Override
                        public void removeContent() {

                        }

                        @Override
                        public void onStarted() {

                        }

                        @Override
                        public void onCancelled() {

                        }

                        @Override
                        public void onLoading(long total, long current, boolean isUploading) {

                        }

                        @Override
                        public void onException(Exception exception) {

                        }

                        @Override
                        public void onComplete(String requestUrl, String jsonStr) {
                            Map<String, String> stringMap = JSONUtils.parseKeyAndValueToMap(jsonStr);
                            ArrayList<Map<String, String>> mapArrayList = JSONUtils.parseKeyAndValueToMapList(stringMap.get("data"));
                            if (mapArrayList != null && mapArrayList.size()>0) {
                                String easemob_account = userInfo.get("easemob_account");
                                if (TextUtils.isEmpty(easemob_account)) {
                                    ((MyFriendsAty) mContext).showErrorTip("对方不在线");
                                    return;
                                }
                                String my_easemob_account = ((MyFriendsAty) mContext).application.getUserInfo().get("easemob_account");
                                if (easemob_account.equals(my_easemob_account)) {
                                    ((MyFriendsAty) mContext).showErrorTip("自己不能和自己聊天");
                                    return;
                                }
                                Bundle bundle = new Bundle();
                                bundle.putString("userId", easemob_account);// 对方环信账号
                                String head_pic = userInfo.get("head_pic");
                                bundle.putString("userHead", head_pic);// 对方头像
                                String nickname = userInfo.get("nickname");
                                bundle.putString("userName", nickname);// 对方昵称
                                bundle.putString("myName", ((MyFriendsAty) mContext).application.getUserInfo().get("nickname"));// 我的昵称
                                bundle.putString("myHead", ((MyFriendsAty) mContext).application.getUserInfo().get("head_pic"));// 我的头像
                                bundle.putSerializable("map", (Serializable) mapArrayList.get(0));
                                bundle.putString("sta_mid",((MyFriendsAty)mContext).mSta_mid);
                                Intent intent = new Intent();
                                intent.putExtras(bundle);
                                intent.setClass(mContext, ChatActivity.class);
                                mContext.startActivity(intent);

                            }
                        }

                        @Override
                        public void onError(String requestUrl, Map<String, String> error) {

                        }

                        @Override
                        public void onErrorTip(String tips) {

                        }
                    });

                }
            });
        }

        @Override
        public int getItemCount() {
            return mItemList.size();
        }

        public static class ViewHolder extends RecyclerView.ViewHolder{

            @ViewInject(R.id.headImg)
            private ShapedImageView headImg;

            @ViewInject(R.id.numTv)
            private TextView numTv;

            @ViewInject(R.id.nameTV)
            private TextView nameTV;

            @ViewInject(R.id.contentTv)
            private TextView contentTv;

            @ViewInject(R.id.timeTv)
            private TextView timeTv;

            public ViewHolder(View itemView) {
                super(itemView);
            }
        }
    }

}
