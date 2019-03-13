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
 * 创建时间：2019/1/22 11:35
 * 功能描述：筛选结果
 */
public class ScreeningResultAty extends BaseAty {
    private Context mContext;

    @ViewInject(R.id.titlt_conter_tv)
    private TextView titlt_conter_tv;

    @ViewInject(R.id.recyclerView)
    private RecyclerView mRecyclerView;
    
    private String mSta_mid;
    private String mCate_id;
    private String mCity_id;
    private ScreeningResultAdapter mScreeningResultAdapter;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_screening_result;
    }

    @Override
    protected void initialized() {
        mContext = this;
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("筛选结果");
        mSta_mid = getIntent().getStringExtra("sta_mid");
        mCate_id = getIntent().getStringExtra("cate_id");
        mCity_id = getIntent().getStringExtra("city_id");

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mScreeningResultAdapter = new ScreeningResultAdapter();
        mRecyclerView.setAdapter(mScreeningResultAdapter);
    }

    @Override
    protected void requestData() {
        get_bfriend(mSta_mid,mCate_id,mCity_id,"4",this);
    }

    void get_bfriend( String sta_mid,String cate_id,String city_id,String type, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("sta_mid", sta_mid);
        if (!TextUtils.isEmpty(cate_id)){
            params.addBodyParameter("cate_id", cate_id);
        }
        if (!TextUtils.isEmpty(city_id)){
            params.addBodyParameter("city_id", city_id);
        }
        params.addBodyParameter("type", type);
        apiTool2.postApi(Config.BASE_URL + "OsManager/get_bfriend", params, baseView);
    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        Map<String, String> map = JSONUtils.parseKeyAndValueToMap(jsonStr);
        if (requestUrl.endsWith("get_bfriend")){
            final ArrayList<Map<String, String>> list = JSONUtils.parseKeyAndValueToMapList(map.get("data"));
            mScreeningResultAdapter.setItemList(list);
            mScreeningResultAdapter.setOnItemViewClickLisener(new ScreeningResultAdapter.onItemViewClickLisener() {
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

    public static class ScreeningResultAdapter extends RecyclerView.Adapter<ScreeningResultAdapter.ViewHolder>{

        private Context mContext;

        private ArrayList<Map<String, String>> mItemList;

        private ScreeningResultAdapter.onItemViewClickLisener mOnItemViewClickLisener;

        public ScreeningResultAdapter() {
            mItemList = new ArrayList<>();
        }

        public void setItemList(ArrayList<Map<String, String>> itemList) {
            mItemList.clear();
            mItemList.addAll(itemList);
            notifyDataSetChanged();
        }

        public void setOnItemViewClickLisener(ScreeningResultAdapter.onItemViewClickLisener onItemViewClickLisener) {
            mOnItemViewClickLisener = onItemViewClickLisener;
        }

        @NonNull
        @Override
        public ScreeningResultAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            mContext = parent.getContext();
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_my_friends_child,parent,false);
            ScreeningResultAdapter.ViewHolder holder = new ScreeningResultAdapter.ViewHolder(view);
            ViewUtils.inject(holder,view);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull ScreeningResultAdapter.ViewHolder holder, final int position) {
            final Map<String, String> map = mItemList.get(position);
            Glide.with(mContext).load(map.get("head_pic")).into(holder.headImg);
            holder.nameTV.setText(map.get("nickname"));
            holder.addFriendTv.setVisibility(View.VISIBLE);
            if (map.get("status").equals("1")){
                holder.addFriendTv.setText("已为好友");
                holder.addFriendTv.setOnClickListener(null);
            }else {
                holder.addFriendTv.setText("加好友");
                holder.addFriendTv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mOnItemViewClickLisener != null){
                            mOnItemViewClickLisener.onViewClick(position);
                        }
                    }
                });
            }


        }

        @Override
        public int getItemCount() {
            return mItemList.size();
        }

        public static class ViewHolder extends RecyclerView.ViewHolder{

            @ViewInject(R.id.headImg)
            private ShapedImageView headImg;


            @ViewInject(R.id.nameTV)
            private TextView nameTV;

            @ViewInject(R.id.addFriendTv)
            private TextView addFriendTv;


            public ViewHolder(View itemView) {
                super(itemView);
            }
        }

        public interface onItemViewClickLisener{
            void onViewClick(int positon);
        }
    }
}
