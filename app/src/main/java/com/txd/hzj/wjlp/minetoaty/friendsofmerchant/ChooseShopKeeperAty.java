package com.txd.hzj.wjlp.minetoaty.friendsofmerchant;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.txd.hzj.wjlp.tool.MessageEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Map;

import cn.gavinliu.android.lib.shapedimageview.ShapedImageView;

/**
 * 创建者：zhangyunfei
 * 创建时间：2019/1/18 15:41
 * 功能描述：选择店主
 */
public class ChooseShopKeeperAty extends BaseAty {
    private Context mContext;

    @ViewInject(R.id.titlt_conter_tv)
    private TextView titlt_conter_tv;

    @ViewInject(R.id.recyclerView)
    private RecyclerView mRecyclerView;

    private ChooseShopKeeperAdapter mChooseShopKeeperAdapter;
    private String mSta_mid;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_choose_shopkeeper;
    }

    @Override
    protected void initialized() {
        mContext = this;
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("选择店主");
        mSta_mid = getIntent().getStringExtra("sta_mid");
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(layoutManager);
        mChooseShopKeeperAdapter = new ChooseShopKeeperAdapter();
        mRecyclerView.setAdapter(mChooseShopKeeperAdapter);
    }

    @Override
    protected void requestData() {
        app_stage_merchant_user(mSta_mid,this);
    }
    void app_stage_merchant_user( String sta_mid, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("sta_mid", sta_mid);
        apiTool2.postApi(Config.BASE_URL + "OsManager/app_stage_merchant_user", params, baseView);
    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        Map<String, String> map = JSONUtils.parseKeyAndValueToMap(jsonStr);
        if (requestUrl.endsWith("app_stage_merchant_user")){
            final ArrayList<Map<String, String>> arrayList = JSONUtils.parseKeyAndValueToMapList(map.get("data"));
            if (arrayList != null && arrayList.size()>0){
                mChooseShopKeeperAdapter.setList(arrayList);
                mChooseShopKeeperAdapter.setOnItemViewClickListener(new ChooseShopKeeperAdapter.OnItemClickListener() {
                    @Override
                    public void onClick(int position) {
                        Map<String, String> stringMap = arrayList.get(position);
                        EventBus.getDefault().post(new MessageEvent(stringMap.get("nickname")+";"+stringMap.get("user_id"),"ChooseShopKeeperAty"));
                        finish();
                    }
                });
            }

        }
    }

    private static class ChooseShopKeeperAdapter extends RecyclerView.Adapter<ChooseShopKeeperAdapter.ViewHolder>{

        private Context mContext;
        private OnItemClickListener mOnItemClickListener;
        private ArrayList<Map<String, String>> mList;
        public ChooseShopKeeperAdapter() {
            mList = new ArrayList<>();
        }

        public void setList(ArrayList<Map<String, String>> list) {
            mList = list;
            notifyDataSetChanged();
        }

        public void setOnItemViewClickListener(OnItemClickListener onItemClickListener) {
            mOnItemClickListener = onItemClickListener;
        }

        @NonNull
        @Override
        public ChooseShopKeeperAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            mContext = parent.getContext();
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_new_friends,parent,false);
            ChooseShopKeeperAdapter.ViewHolder holder = new ChooseShopKeeperAdapter.ViewHolder(view);
            ViewUtils.inject(holder,view);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull ChooseShopKeeperAdapter.ViewHolder holder, final int position) {
            Map<String, String> map = mList.get(position);
            Glide.with(mContext).load(map.get("head_pic")).into(holder.headImg);
            holder.nameTv.setText(map.get("nickname"));

            holder.itemView.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   if (mOnItemClickListener != null){
                       mOnItemClickListener.onClick(position);
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

            public ViewHolder(View itemView) {
                super(itemView);
            }
        }
        public interface  OnItemClickListener{
            void onClick(int position);
        }
    }
}
