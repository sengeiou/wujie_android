package com.txd.hzj.wjlp.minetoaty.friendsofmerchant;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.ants.theantsgo.base.BaseView;
import com.ants.theantsgo.config.Config;
import com.ants.theantsgo.httpTools.ApiTool2;
import com.ants.theantsgo.util.JSONUtils;
import com.bumptech.glide.Glide;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.gavinliu.android.lib.shapedimageview.ShapedImageView;

/**
 * 创建者：zhangyunfei
 * 创建时间：2019/1/23 16:42
 * 功能描述：食品
 */
public class NotAddFriendAty extends BaseAty {
    private Context mContext;

    @ViewInject(R.id.titlt_conter_tv)
    private TextView titlt_conter_tv;

    @ViewInject(R.id.titlt_right_tv)
    private TextView titlt_right_tv;

    @ViewInject(R.id.friendRecyclerView)
    private RecyclerView friendRecyclerView;

    private NotAddFriendAdapter mNotAddFriendAdapter;
    private String mSta_mid;
    private String mId;
    private String mType = "show";

    private String mUid;

    private List<String> mStringList = new ArrayList<>();
    @Override
    protected int getLayoutResId() {
        return R.layout.activity_not_add_friend;
    }

    @Override
    protected void initialized() {
        mContext = this;
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("食品");
        titlt_right_tv.setVisibility(View.VISIBLE);
        titlt_right_tv.setText("完成");
        mSta_mid = getIntent().getStringExtra("sta_mid");
        mId = getIntent().getStringExtra("id");

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        friendRecyclerView.setLayoutManager(layoutManager);
        mNotAddFriendAdapter = new NotAddFriendAdapter();
        friendRecyclerView.setAdapter(mNotAddFriendAdapter);
        titlt_right_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mType = "add";
                if (mStringList.size()>0){
                    StringBuilder builder = new StringBuilder();
                    for (int i = 0; i < mStringList.size(); i++) {
                        builder.append(mStringList.get(i));
                        if (i<mStringList.size()-1){
                            builder.append(",");
                        }
                    }
                    mUid = builder.toString();
                }
                if (TextUtils.isEmpty(mUid)){
                    showToast("请选择需要添加的好友！");
                    return;
                }
                addfriend_cate(NotAddFriendAty.this);
            }
        });
    }

    @Override
    protected void requestData() {
        bfriend_cate(this);
    }

    void bfriend_cate(BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("sta_mid", mSta_mid);
        params.addBodyParameter("id", mId);
        params.addBodyParameter("type", "3");
        params.addBodyParameter("t", "1");
        apiTool2.postApi(Config.BASE_URL + "OsManager/bfriend_cate", params, baseView);
    }

    void addfriend_cate(BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("sta_mid", mSta_mid);
        params.addBodyParameter("id", mId);
        params.addBodyParameter("uid", mUid);
        params.addBodyParameter("type", "2");
        apiTool2.postApi(Config.BASE_URL + "OsManager/bfriend_cate", params, baseView);
    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        Map<String, String> map = JSONUtils.parseKeyAndValueToMap(jsonStr);
        if (requestUrl.endsWith("bfriend_cate")){
            if (mType.equals("show")){
                Map<String, String> data = JSONUtils.parseKeyAndValueToMap(map.get("data"));
                if (data.containsKey("list")) {
                    ArrayList<Map<String, String>> arrayList = JSONUtils.parseKeyAndValueToMapList(data.get("list"));
                    if (arrayList != null) {
                        mNotAddFriendAdapter.setList(arrayList);
                    }
                }
            }else {
                showToast(map.get("message"));
                if (map.get("code").equals("1")) {
                    mType = "show";
                    finish();
                }
            }
        }
    }


    private class NotAddFriendAdapter extends RecyclerView.Adapter<NotAddFriendAdapter.ViewHolder> {

        private Context mContext;
        private ArrayList<Map<String, String>> mList;

        public NotAddFriendAdapter() {
            mList = new ArrayList<>();
        }

        public void setList(ArrayList<Map<String, String>> list) {
            mList.clear();
            mList.addAll(list);
            notifyDataSetChanged();
        }


        @NonNull
        @Override
        public NotAddFriendAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            mContext = parent.getContext();
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_my_friends, parent, false);
            NotAddFriendAdapter.ViewHolder holder = new NotAddFriendAdapter.ViewHolder(view);
            ViewUtils.inject(holder, view);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull NotAddFriendAdapter.ViewHolder holder, int position) {
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
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_not_add_friend, parent, false);
            ItemAdapter.ViewHolder holder = new ItemAdapter.ViewHolder(view);
            ViewUtils.inject(holder, view);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull final ItemAdapter.ViewHolder holder, int position) {
            final Map<String, String> map = mItemList.get(position);
            Glide.with(mContext).load(map.get("head_pic")).into(holder.headImg);
            holder.nameTV.setText(map.get("nickname"));
            holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked){
                        mStringList.add(map.get("real_uid"));
                    }else {
//                        if (mStringList.size()>0){
//                            for (int i = 0; i < mStringList.size(); i++) {
//
//                            }
//                        }
                        mStringList.remove(map.get("real_uid"));
                    }
                }
            });
        }


        @Override
        public int getItemCount() {
            return mItemList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            @ViewInject(R.id.checkBox)
            private CheckBox checkBox;

            @ViewInject(R.id.headImg)
            private ShapedImageView headImg;


            @ViewInject(R.id.nameTV)
            private TextView nameTV;


            public ViewHolder(View itemView) {
                super(itemView);
            }
        }
    }
}
