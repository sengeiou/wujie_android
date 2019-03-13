package com.txd.hzj.wjlp.minetoaty.friendsofmerchant;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

import cn.gavinliu.android.lib.shapedimageview.ShapedImageView;

/**
 * 创建者：zhangyunfei
 * 创建时间：2019/1/18 14:45
 * 功能描述：
 */
public class NewFriendAty extends BaseAty {
    private Context mContext;

    @ViewInject(R.id.titlt_conter_tv)
    private TextView titlt_conter_tv;

    @ViewInject(R.id.recyclerView)
    private RecyclerView mRecyclerView;

    private NewFriendAdapter mNewFriendAdapter;
    private String mSta_mid;

    private String mType = "1";
    @Override
    protected int getLayoutResId() {
        return R.layout.activity_new_friend;
    }

    @Override
    protected void initialized() {
        mContext = this;
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("新的好友");
        mSta_mid = getIntent().getStringExtra("sta_mid");
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(layoutManager);
        mNewFriendAdapter = new NewFriendAdapter();
        mRecyclerView.setAdapter(mNewFriendAdapter);
    }

    @Override
    protected void requestData() {
            bfmsglist(mSta_mid,this);
    }


    void bfmsglist( String sta_mid, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("sta_mid", sta_mid);
        apiTool2.postApi(Config.BASE_URL + "OsManager/bfmsglist", params, baseView);
    }


    void get_bfriend( String sta_mid,String id,String vinfo,String status,String type, BaseView baseView) {
        mType = "1";
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("sta_mid", sta_mid);
        params.addBodyParameter("id", id);
        params.addBodyParameter("vinfo", vinfo);
        params.addBodyParameter("status", status);
        params.addBodyParameter("type", type);
        apiTool2.postApi(Config.BASE_URL + "OsManager/get_bfriend", params, baseView);
    }

    void get_bfriend(String phone,BaseView baseView) {
        mType = "2";
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("sta_mid", mSta_mid);
        params.addBodyParameter("phone", phone);
        params.addBodyParameter("type", "0");
        apiTool2.postApi(Config.BASE_URL + "OsManager/get_bfriend", params, baseView);
    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
         Map<String, String> map = JSONUtils.parseKeyAndValueToMap(jsonStr);
        if (requestUrl.endsWith("bfmsglist")){
            final ArrayList<Map<String, String>> arrayList = JSONUtils.parseKeyAndValueToMapList(map.get("data"));
            mNewFriendAdapter.setList(arrayList);
            mNewFriendAdapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    get_bfriend(arrayList.get(position).get("phone"),NewFriendAty.this);
                }
            });
            mNewFriendAdapter.setOnItemViewClickListener(new OnItemViewClickListener() {
                @Override
                public void onClick(View view, int position) {
                    if (view.getId() == R.id.agreeTv){
                        get_bfriend(mSta_mid,arrayList.get(position).get("id"),"","1","3",NewFriendAty.this);
                    }else if (view.getId() == R.id.refuseTv){
                        get_bfriend(mSta_mid,arrayList.get(position).get("id"),"","2","3",NewFriendAty.this);
                    }
                }
            });
            return;
        }

        if (requestUrl.endsWith("get_bfriend")){
            if (mType.equals("1")){
                showToast(map.get("message"));
                if (map.get("code").equals("1")){
                    bfmsglist(mSta_mid,this);
                }
            }else {
                Map<String, String> stringMap = JSONUtils.parseKeyAndValueToMap(jsonStr);
                ArrayList<Map<String, String>> mapArrayList = JSONUtils.parseKeyAndValueToMapList(stringMap.get("data"));
                if (mapArrayList != null && mapArrayList.size()>0) {
                    Map<String, String> map1 = mapArrayList.get(0);
                    Bundle bundle = new Bundle();
                    bundle.putString("sta_mid",mSta_mid);
                    bundle.putSerializable("map", (Serializable) map1);
                    startActivity(BusinessFriendDataAty.class,bundle);
                }
            }

        }
    }

    private static class NewFriendAdapter extends RecyclerView.Adapter<NewFriendAdapter.ViewHolder>{

        private Context mContext;
        private OnItemViewClickListener mOnItemViewClickListener;
        private OnItemClickListener mOnItemClickListener;
        private ArrayList<Map<String, String>> mList;
        public NewFriendAdapter() {
            mList = new ArrayList<>();
        }

        public void setList(ArrayList<Map<String, String>> list) {
            mList.clear();
            mList.addAll(list);
            notifyDataSetChanged();
        }

        public void setOnItemViewClickListener(OnItemViewClickListener onItemViewClickListener) {
            mOnItemViewClickListener = onItemViewClickListener;
        }

        public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
            mOnItemClickListener = onItemClickListener;
        }

        @NonNull
        @Override
        public NewFriendAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            mContext = parent.getContext();
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_new_friends,parent,false);
            NewFriendAdapter.ViewHolder holder = new NewFriendAdapter.ViewHolder(view);
            ViewUtils.inject(holder,view);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull final NewFriendAdapter.ViewHolder holder, final int position) {
            Map<String, String> map = mList.get(position);
            Glide.with(mContext).load(map.get("head_pic")).into(holder.headImg);
            holder.nameTv.setText(map.get("nickname"));
            String sName = map.get("s_name");
            if (sName.equals("等待通过")){
                holder.hasPassTv.setVisibility(View.GONE);
                holder.notPassTv.setVisibility(View.VISIBLE);
                holder.layout.setVisibility(View.GONE);
                holder.notPassTv.setText(sName);
            }else if (sName.equals("接受")){
                holder.hasPassTv.setVisibility(View.GONE);
                holder.notPassTv.setVisibility(View.GONE);
                holder.layout.setVisibility(View.VISIBLE);
            }else{
                holder.hasPassTv.setVisibility(View.VISIBLE);
                holder.notPassTv.setVisibility(View.GONE);
                holder.layout.setVisibility(View.GONE);
                holder.hasPassTv.setText(sName);
            }

            String info = map.get("info");
            if (TextUtils.isEmpty(info)){
                holder.descTv.setVisibility(View.GONE);
            }else {
                holder.descTv.setVisibility(View.VISIBLE);
                holder.descTv.setText(info);
            }

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null){
                        mOnItemClickListener.onItemClick(holder.getLayoutPosition());
                    }
                }
            });

            holder.agreeTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemViewClickListener != null){
                        mOnItemViewClickListener.onClick(v,position);
                    }
                }
            });
            holder.refuseTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemViewClickListener != null){
                        mOnItemViewClickListener.onClick(v,position);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }

        public static class ViewHolder extends RecyclerView.ViewHolder{

            @ViewInject(R.id.headImg)
            private ShapedImageView headImg;

            @ViewInject(R.id.nameTv)
            private TextView nameTv;

            @ViewInject(R.id.descTv)
            private TextView descTv;

            @ViewInject(R.id.hasPassTv)
            private TextView hasPassTv;

            @ViewInject(R.id.notPassTv)
            private TextView notPassTv;


            @ViewInject(R.id.layout)
            private LinearLayout layout;

            @ViewInject(R.id.refuseTv)
            private TextView refuseTv;

            @ViewInject(R.id.agreeTv)
            private TextView agreeTv;

            public ViewHolder(View itemView) {
                super(itemView);
            }
        }
    }

    public interface  OnItemViewClickListener{
        void onClick(View view,int position);
    }


    public interface OnItemClickListener{
        void onItemClick(int position);
    }
}
