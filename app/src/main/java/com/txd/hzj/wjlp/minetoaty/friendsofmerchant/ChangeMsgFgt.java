package com.txd.hzj.wjlp.minetoaty.friendsofmerchant;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
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
import com.txd.hzj.wjlp.view.SuperSwipeRefreshLayout;

import java.util.ArrayList;
import java.util.Map;

import cn.gavinliu.android.lib.shapedimageview.ShapedImageView;

/**
 * 创建者：zhangyunfei
 * 创建时间：2019/1/18 8:59
 * 功能描述：互换消息
 */
public class ChangeMsgFgt extends BaseFgt {
    private Context mContext;

    @ViewInject(R.id.super_refreshLayout)
    private SuperSwipeRefreshLayout mSuperSwipeRefreshLayout;

    @ViewInject(R.id.recyclerView)
    private RecyclerView mRecyclerView;

    private ChangeMsgAdapter mChangeMsgAdapter;

    private int p = 1;

    // Header View
    private ProgressBar progressBar;
    private TextView textView;
    private ImageView imageView;

    // Footer View
    private ProgressBar footerProgressBar;
    private TextView footerTextView;
    private ImageView footerImageView;

    @Override
    protected int getLayoutResId() {
        return R.layout.fgt_change_msg;
    }

    @Override
    protected void initialized() {
        mContext = getActivity();
    }

    @Override
    protected void requestData() {
        mSuperSwipeRefreshLayout.setHeaderView(createHeaderView());// add headerView
        mSuperSwipeRefreshLayout.setFooterView(createFooterView());
        mSuperSwipeRefreshLayout.setHeaderViewBackgroundColor(Color.WHITE);
        mSuperSwipeRefreshLayout.setTargetScrollWithLayout(true);
        mSuperSwipeRefreshLayout.setOnPullRefreshListener(new SuperSwipeRefreshLayout.OnPullRefreshListener() {
            @Override
            public void onRefresh() {
                textView.setText("正在刷新");
                imageView.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                p = 1;
                app_exchange_list(((ChangeMembersAty)getActivity()).getSta_mid(),String.valueOf(p),ChangeMsgFgt.this);
            }

            @Override
            public void onPullDistance(int distance) {

            }

            @Override
            public void onPullEnable(boolean enable) {
                textView.setText(enable ? "松开刷新" : "下拉刷新");
                imageView.setVisibility(View.VISIBLE);
                imageView.setRotation(enable ? 180 : 0);
            }
        });
        mSuperSwipeRefreshLayout.setOnPushLoadMoreListener(new SuperSwipeRefreshLayout.OnPushLoadMoreListener() {
            @Override
            public void onLoadMore() {
                footerTextView.setText("正在加载...");
                footerImageView.setVisibility(View.GONE);
                footerProgressBar.setVisibility(View.VISIBLE);
                p++;
                app_exchange_list(((ChangeMembersAty)getActivity()).getSta_mid(),String.valueOf(p),ChangeMsgFgt.this);
            }

            @Override
            public void onPushDistance(int distance) {
            }

            @Override
            public void onPushEnable(boolean enable) {
                footerTextView.setText(enable ? "松开加载" : "上拉加载");
                footerImageView.setVisibility(View.VISIBLE);
                footerImageView.setRotation(enable ? 0 : 180);
            }
        });


        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(layoutManager);
        mChangeMsgAdapter = new ChangeMsgAdapter();
        mRecyclerView.setAdapter(mChangeMsgAdapter);

    }

    @Override
    public void onResume() {
        super.onResume();
        app_exchange_list(((ChangeMembersAty)getActivity()).getSta_mid(),String.valueOf(p),this);
    }

    void app_exchange_list(String sta_mid, String p, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("p", p);
        params.addBodyParameter("sta_mid", sta_mid);
        apiTool2.postApi(Config.BASE_URL + "OsManager/app_exchange_list", params, baseView);
    }


    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        refreshVisibleState();
        Map<String, String> map = JSONUtils.parseKeyAndValueToMap(jsonStr);
        if (requestUrl.endsWith("app_exchange_list")){
            final ArrayList<ExchangeBean> data = JSONUtils.parseKeyAndValueToMapList(ExchangeBean.class, map.get("data"));
            if (data != null && data.size()>0){
                mChangeMsgAdapter.setList(data);
                mChangeMsgAdapter.setOnItemClickListener(new ChangeMsgAdapter.onItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        Bundle bundle = new Bundle();
                        bundle.putString("sta_mid",((ChangeMembersAty)getActivity()).getSta_mid());
                        bundle.putString("cid",data.get(position).getC_id());
                        startActivity(ChangeMsgDetailsAty.class,bundle);
                    }
                });
            }
        }

    }

    @Override
    public void onError(String requestUrl, Map<String, String> error) {
        super.onError(requestUrl, error);
        refreshVisibleState();
    }

    private void refreshVisibleState() {
        if (progressBar.getVisibility()== View.VISIBLE){
            mSuperSwipeRefreshLayout.setRefreshing(false);
            progressBar.setVisibility(View.GONE);
        }
        if (footerProgressBar.getVisibility()==View.VISIBLE) {
            mSuperSwipeRefreshLayout.setLoadMore(false);
            footerProgressBar.setVisibility(View.GONE);
        }
    }

    @Override
    protected void immersionInit() {
    }
    /**
     * 创建底部加载布局
     *
     * @return
     */
    private View createFooterView() {
        View footerView = LayoutInflater.from(mSuperSwipeRefreshLayout.getContext()).inflate(R.layout.layout_footer, null);
        footerProgressBar = footerView.findViewById(R.id.footer_pb_view);
        footerImageView = footerView.findViewById(R.id.footer_image_view);
        footerTextView = footerView.findViewById(R.id.footer_text_view);
        footerProgressBar.setVisibility(View.GONE);
        footerImageView.setVisibility(View.VISIBLE);
        footerImageView.setImageResource(R.drawable.down_arrow);
        footerTextView.setText("上拉加载更多...");
        return footerView;
    }

    /**
     * 创建头部加载布局
     *
     * @return
     */
    private View createHeaderView() {
        View headerView = LayoutInflater.from(mSuperSwipeRefreshLayout.getContext()).inflate(R.layout.layout_head, null);
        progressBar = headerView.findViewById(R.id.pb_view);
        textView = headerView.findViewById(R.id.text_view);
        textView.setText("下拉刷新");
        imageView = headerView.findViewById(R.id.image_view);
        imageView.setVisibility(View.VISIBLE);
        imageView.setImageResource(R.drawable.down_arrow);
        progressBar.setVisibility(View.GONE);
        return headerView;
    }


    private static class ChangeMsgAdapter extends RecyclerView.Adapter<ChangeMsgAdapter.ViewHolder>{

        private Context mContext;

        private ArrayList<ExchangeBean> mList;

        private onItemClickListener mOnItemClickListener;

        private int[] colors = {R.color.orange,Color.YELLOW,Color.BLUE,Color.RED};
        public ChangeMsgAdapter() {
            mList = new ArrayList<>();
        }

        public void setList(ArrayList<ExchangeBean> list) {
            mList.clear();
            mList.addAll(list);
            notifyDataSetChanged();
        }

        public void setOnItemClickListener(onItemClickListener onItemClickListener) {
            mOnItemClickListener = onItemClickListener;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            mContext = parent.getContext();
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_change_msg,parent,false);
            ViewHolder holder = new ViewHolder(view);
            ViewUtils.inject(holder,view);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
            ExchangeBean exchangeBean = mList.get(position);
            holder.lineView.setBackgroundColor(colors[(int) (Math.random()*colors.length)]);
            Glide.with(mContext).load(exchangeBean.getHead_pic()).into(holder.headImg);
            holder.nameTv.setText(exchangeBean.getNickname());
            holder.contentTv.setText(exchangeBean.getTitle());
            holder.statusTv.setVisibility(View.VISIBLE);
            holder.statusTv.setText(exchangeBean.getStatus_desc());
            int read_status = Integer.parseInt(exchangeBean.getRead_status());
            if (read_status == 0){
                holder.pointTv.setVisibility(View.GONE);
            }else {
                holder.pointTv.setVisibility(View.VISIBLE);
            }
            holder.timeTv.setText(exchangeBean.getCreate_time());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null){
                        mOnItemClickListener.onItemClick(holder.getLayoutPosition());
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }

        public static class ViewHolder extends RecyclerView.ViewHolder{

            @ViewInject(R.id.lineView)
            private View lineView;

            @ViewInject(R.id.headImg)
            private ShapedImageView headImg;

            @ViewInject(R.id.nameTv)
            private TextView nameTv;

            @ViewInject(R.id.contentTv)
            private TextView contentTv;

            @ViewInject(R.id.statusTv)
            private TextView statusTv;

            @ViewInject(R.id.pointTv)
            private TextView pointTv;

            @ViewInject(R.id.timeTv)
            private TextView timeTv;
            public ViewHolder(View itemView) {
                super(itemView);
            }
        }
        public interface  onItemClickListener{
            void onItemClick(int position);
        }
    }



    public static class ExchangeBean{

        /**
         * c_id : 5
         * status : 0
         * nickname : 无界新人2
         * head_pic : http://test2.wujiemall.com/Uploads/User/2018-10-08/5bbabf7e0e45d.jpg
         * type : 1
         * title : 您已向好友发出会员互换请求~
         * status_desc : 请等待
         * create_time : 2019-01-18 15:38:04
         * read_status : 0
         */

        private String c_id;
        private String status;
        private String nickname;
        private String head_pic;
        private String type;
        private String title;
        private String status_desc;
        private String create_time;
        private String read_status;

        public String getC_id() {
            return c_id;
        }

        public void setC_id(String c_id) {
            this.c_id = c_id;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getHead_pic() {
            return head_pic;
        }

        public void setHead_pic(String head_pic) {
            this.head_pic = head_pic;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getStatus_desc() {
            return status_desc;
        }

        public void setStatus_desc(String status_desc) {
            this.status_desc = status_desc;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getRead_status() {
            return read_status;
        }

        public void setRead_status(String read_status) {
            this.read_status = read_status;
        }
    }


}
