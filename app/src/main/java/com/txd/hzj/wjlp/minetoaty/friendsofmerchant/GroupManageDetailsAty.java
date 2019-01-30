package com.txd.hzj.wjlp.minetoaty.friendsofmerchant;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

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

import java.util.ArrayList;
import java.util.Map;

import cn.gavinliu.android.lib.shapedimageview.ShapedImageView;

/**
 * 创建者：zhangyunfei
 * 创建时间：2019/1/22 17:36
 * 功能描述：分组商友管理
 */
public class GroupManageDetailsAty extends BaseAty {
    private Context mContext;

    @ViewInject(R.id.titlt_conter_tv)
    private TextView titlt_conter_tv;

    @ViewInject(R.id.titlt_right_tv)
    private TextView titlt_right_tv;

    @ViewInject(R.id.nameTv)
    private EditText nameTv;

    @ViewInject(R.id.numTv)
    private TextView numTv;

    @ViewInject(R.id.friendRecyclerView)
    private RecyclerView friendRecyclerView;

    private GroupManageDetailsAdapter mGroupManageDetailsAdapter;
    private String mSta_mid;
    private String mId;

    private String mType = "show";

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_group_manage_details;
    }

    @Override
    protected void initialized() {
        mContext = this;
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("分组管理");
        titlt_right_tv.setVisibility(View.VISIBLE);
        titlt_right_tv.setText("保存");
        mSta_mid = getIntent().getStringExtra("sta_mid");
        mId = getIntent().getStringExtra("id");

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        friendRecyclerView.setLayoutManager(layoutManager);
        mGroupManageDetailsAdapter = new GroupManageDetailsAdapter();
        friendRecyclerView.setAdapter(mGroupManageDetailsAdapter);
    }

    @Override
    protected void requestData() {
    }

    @Override
    protected void onResume() {
        super.onResume();
        bfriend_cate(mSta_mid, mId, this);
    }

    void bfriend_cate(String sta_mid, String id, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("sta_mid", sta_mid);
        params.addBodyParameter("id", id);
        params.addBodyParameter("type", "2");
        params.addBodyParameter("t", "1");
        apiTool2.postApi(Config.BASE_URL + "OsManager/bfriend_cate", params, baseView);
    }


    void editGroup(String id,String name, BaseView baseView){
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("sta_mid", mSta_mid);
        params.addBodyParameter("id", id);
        params.addBodyParameter("name", name);
        params.addBodyParameter("is_del", "0");
        apiTool2.postApi(Config.BASE_URL + "OsManager/bfriend_cate", params, baseView);
    }

    void deletefriend_cate(String uid, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("sta_mid", mSta_mid);
        params.addBodyParameter("id", mId);
        params.addBodyParameter("uid", uid);
        params.addBodyParameter("is_del", "2");
        apiTool2.postApi(Config.BASE_URL + "OsManager/bfriend_cate", params, baseView);
    }


    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        Map<String, String> map = JSONUtils.parseKeyAndValueToMap(jsonStr);
        if (requestUrl.endsWith("bfriend_cate")) {
            if (mType.equals("show")) {
                Map<String, String> data = JSONUtils.parseKeyAndValueToMap(map.get("data"));
                Map<String, String> info = JSONUtils.parseKeyAndValueToMap(data.get("data"));
                nameTv.setText(info.get("name"));
                nameTv.setSelection(nameTv.getText().toString().length());
                numTv.setText("商友数量(" + info.get("count") + ")");
                if (data.containsKey("list")) {
                    ArrayList<Map<String, String>> arrayList = JSONUtils.parseKeyAndValueToMapList(data.get("list"));
                    if (arrayList != null) {
                        mGroupManageDetailsAdapter.setList(arrayList);
                    }
                }
            } else {
                showToast(map.get("message"));
                if (map.get("code").equals("1")) {
                    if (mType.equals("edit")){
                        finish();
                    }else if (mType.equals("delete")){
                        mType = "show";
                        bfriend_cate(mSta_mid, mId, GroupManageDetailsAty.this);
                    }
                }
            }


        }
    }

    @Override
    @OnClick({R.id.titlt_right_tv,R.id.addFriendTv})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.titlt_right_tv:
                mType = "edit";
                editGroup(mId,nameTv.getText().toString(),this);
                break;
            case R.id.addFriendTv:
                Bundle bundle = new Bundle();
                bundle.putString("sta_mid",mSta_mid);
                bundle.putString("id",mId);
                bundle.putString("name",nameTv.getText().toString());
                startActivity(NotAddFriendAty.class,bundle);
                break;
        }
    }

    private class GroupManageDetailsAdapter extends RecyclerView.Adapter<GroupManageDetailsAdapter.ViewHolder> {

        private Context mContext;
        private ArrayList<Map<String, String>> mList;

        public GroupManageDetailsAdapter() {
            mList = new ArrayList<>();
        }

        public void setList(ArrayList<Map<String, String>> list) {
            mList.clear();
            mList.addAll(list);
            notifyDataSetChanged();
        }


        @NonNull
        @Override
        public GroupManageDetailsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            mContext = parent.getContext();
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_my_friends, parent, false);
            GroupManageDetailsAdapter.ViewHolder holder = new GroupManageDetailsAdapter.ViewHolder(view);
            ViewUtils.inject(holder, view);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull GroupManageDetailsAdapter.ViewHolder holder, int position) {
            Map<String, String> map = mList.get(position);
            holder.titleTv.setText(map.get("name"));
            ArrayList<Map<String, String>> list = JSONUtils.parseKeyAndValueToMapList(map.get("list"));

            LinearLayoutManager layoutManager = new LinearLayoutManager(mContext) {
                @Override
                public boolean canScrollVertically() {
                    return false;
                }
            };
            holder.itemRecyclerView.setLayoutManager(layoutManager);
            if (list != null) {
                ItemAdapter mItemAdapter = new ItemAdapter();
                holder.itemRecyclerView.setAdapter(mItemAdapter);
                mItemAdapter.setItemList(list);
            }
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            @ViewInject(R.id.titleTv)
            private TextView titleTv;

            @ViewInject(R.id.itemRecyclerView)
            private RecyclerView itemRecyclerView;

            public ViewHolder(View itemView) {
                super(itemView);
            }
        }

    }

    private class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

        private Context mContext;

        private ArrayList<Map<String, String>> mItemList;


        public ItemAdapter() {
            mItemList = new ArrayList<>();
        }

        public void setItemList(ArrayList<Map<String, String>> itemList) {
            mItemList.clear();
            mItemList = itemList;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public ItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            mContext = parent.getContext();
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_group_management_details, parent, false);
            ItemAdapter.ViewHolder holder = new ItemAdapter.ViewHolder(view);
            ViewUtils.inject(holder, view);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull final ItemAdapter.ViewHolder holder, int position) {
            final Map<String, String> map = mItemList.get(position);
            Glide.with(mContext).load(map.get("head_pic")).into(holder.headImg);
            holder.nameTV.setText(map.get("nickname"));
            holder.tv_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mType = "delete";
                    deletefriend_cate(map.get("real_uid"), GroupManageDetailsAty.this);
                }
            });
            holder.contentLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String easemob_account =  map.get("hx_id");
                    if (TextUtils.isEmpty(easemob_account)) {
                        ((GroupManageDetailsAty)mContext).showErrorTip("对方不在线");
                        return;
                    }
                    String my_easemob_account = ((GroupManageDetailsAty)mContext).application.getUserInfo().get("easemob_account");
                    if (easemob_account.equals(my_easemob_account)) {
                        ((GroupManageDetailsAty)mContext).showErrorTip("自己不能和自己聊天");
                        return;
                    }
                    Bundle bundle = new Bundle();
                    bundle.putString("userId", easemob_account);// 对方环信账号
                    String head_pic = map.get("head_pic");
                    bundle.putString("userHead", head_pic);// 对方头像
                    String nickname = map.get("nickname");
                    bundle.putString("userName", nickname);// 对方昵称
                    bundle.putString("myName",  ((GroupManageDetailsAty)mContext).application.getUserInfo().get("nickname"));// 我的昵称
                    bundle.putString("myHead",  ((GroupManageDetailsAty)mContext).application.getUserInfo().get("head_pic"));// 我的头像
                    Intent intent = new Intent();
                    intent.putExtras(bundle);
                    intent.setClass(mContext,ChatActivity.class);
                    mContext.startActivity(intent);
                }
            });
        }


        @Override
        public int getItemCount() {
            return mItemList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            @ViewInject(R.id.contentLayout)
            private LinearLayout contentLayout;

            @ViewInject(R.id.headImg)
            private ShapedImageView headImg;


            @ViewInject(R.id.nameTV)
            private TextView nameTV;


            @ViewInject(R.id.tv_delete)
            private TextView tv_delete;

            public ViewHolder(View itemView) {
                super(itemView);
            }
        }
    }
}
