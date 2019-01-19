package com.txd.hzj.wjlp.minetoaty.friendsofmerchant;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;

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

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_new_friend;
    }

    @Override
    protected void initialized() {
        mContext = this;
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("新的好友");
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(layoutManager);
        mNewFriendAdapter = new NewFriendAdapter();
        mRecyclerView.setAdapter(mNewFriendAdapter);
    }

    @Override
    protected void requestData() {

    }

    private static class NewFriendAdapter extends RecyclerView.Adapter<NewFriendAdapter.ViewHolder>{

        private Context mContext;
        private OnItemViewClickListener mOnItemViewClickListener;
        public NewFriendAdapter() {
        }

        public void setOnItemViewClickListener(OnItemViewClickListener onItemViewClickListener) {
            mOnItemViewClickListener = onItemViewClickListener;
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
        public void onBindViewHolder(@NonNull NewFriendAdapter.ViewHolder holder, final int position) {

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
            return 5;
        }

        public static class ViewHolder extends RecyclerView.ViewHolder{

            @ViewInject(R.id.headImg)
            private ShapedImageView headImg;

            @ViewInject(R.id.nameTv)
            private TextView nameTv;

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
}
