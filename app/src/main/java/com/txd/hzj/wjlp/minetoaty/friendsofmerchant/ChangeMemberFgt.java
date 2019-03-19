package com.txd.hzj.wjlp.minetoaty.friendsofmerchant;

import android.content.Context;
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
import com.txd.hzj.wjlp.base.BaseFgt;

import java.util.ArrayList;
import java.util.Map;

import cn.gavinliu.android.lib.shapedimageview.ShapedImageView;

/**
 * 创建者：zhangyunfei
 * 创建时间：2019/1/18 8:59
 * 功能描述：换入会员
 */
public class ChangeMemberFgt extends BaseFgt {
    private Context mContext;

    @ViewInject(R.id.numTv)
    private TextView numTv;

    @ViewInject(R.id.recyclerView)
    private RecyclerView mRecyclerView;

    @ViewInject(R.id.changeTv)
    private TextView changeTv;

    private ChangeMemberAdapter mChangeMemberAdapter;

    private String mType = "2";
    @Override
    protected int getLayoutResId() {
        return R.layout.fgt_my_member;
    }

    @Override
    protected void initialized() {
        mContext = getActivity();
    }

    @Override
    protected void requestData() {
        changeTv.setVisibility(View.GONE);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(layoutManager);
        mChangeMemberAdapter = new ChangeMemberAdapter();
        mRecyclerView.setAdapter(mChangeMemberAdapter);

    }

    @Override
    public void onResume() {
        super.onResume();
        app_my_member_list(((ChangeMembersAty)getActivity()).getSta_mid(),mType,this);
    }

    void app_my_member_list(String sta_mid, String type, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("type", type);
        params.addBodyParameter("sta_mid", sta_mid);
        apiTool2.postApi(Config.BASE_URL + "OsManager/app_my_member_list", params, baseView);
    }


    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        Map<String, String> map = JSONUtils.parseKeyAndValueToMap(jsonStr);
        if (requestUrl.endsWith("app_my_member_list")){
            numTv.setText("会员总数\u0020" + map.get("nums"));
            ArrayList<Map<String, String>> mapArrayList = JSONUtils.parseKeyAndValueToMapList(map.get("data"));
            if (mapArrayList != null && mapArrayList.size() > 0) {
                mChangeMemberAdapter.setList(mapArrayList);
            }
        }
    }


    @Override
    protected void immersionInit() {
    }

    private static class ChangeMemberAdapter extends RecyclerView.Adapter<ChangeMemberAdapter.ViewHolder>{

        private Context mContext;
        private ArrayList<Map<String, String>> mList;
        public ChangeMemberAdapter() {
            mList = new ArrayList<>();
        }

        public void setList(ArrayList<Map<String, String>> list) {
            mList.clear();
            mList.addAll(list);
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public ChangeMemberAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            mContext = parent.getContext();
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_my_member,parent,false);
            ChangeMemberAdapter.ViewHolder holder = new ChangeMemberAdapter.ViewHolder(view);
            ViewUtils.inject(holder,view);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull ChangeMemberAdapter.ViewHolder holder, int position) {
            Map<String, String> map = mList.get(position);
            Glide.with(mContext).load(map.get("head_pic")).into(holder.headImg);
            holder.nameTv.setText(map.get("user_name"));
            holder.gradeTv.setText(map.get("member_coding"));
            int sex = Integer.parseInt(map.get("sex"));
            if (sex == 1) {
                holder.sexTv.setVisibility(View.VISIBLE);
                holder.sexTv.setText("男");
            } else if (sex == 2) {
                holder.sexTv.setVisibility(View.VISIBLE);
                holder.sexTv.setText("女");
            } else {
                holder.sexTv.setVisibility(View.GONE);
            }

            if (!TextUtils.isEmpty(map.get("age")) && !map.get("age").equals("null")) {
                holder.ageTv.setText(map.get("age") + "岁");
            }else {
                holder.ageTv.setText("");
            }
            holder.timeTv.setText(map.get("create_time"));
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

            @ViewInject(R.id.gradeTv)
            private TextView gradeTv;

            @ViewInject(R.id.sexTv)
            private TextView sexTv;


            @ViewInject(R.id.ageTv)
            private TextView ageTv;

            @ViewInject(R.id.timeTv)
            private TextView timeTv;
            public ViewHolder(View itemView) {
                super(itemView);
            }
        }
    }
}
