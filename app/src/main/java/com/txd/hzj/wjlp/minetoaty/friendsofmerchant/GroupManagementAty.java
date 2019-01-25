package com.txd.hzj.wjlp.minetoaty.friendsofmerchant;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.ants.theantsgo.base.BaseView;
import com.ants.theantsgo.config.Config;
import com.ants.theantsgo.httpTools.ApiTool2;
import com.ants.theantsgo.util.JSONUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.tool.MessageEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Map;

/**
 * 创建者：zhangyunfei
 * 创建时间：2019/1/22 14:54
 * 功能描述：分组管理
 */
public class GroupManagementAty extends BaseAty {
    private Context mContext;

    @ViewInject(R.id.titlt_conter_tv)
    private TextView titlt_conter_tv;

    @ViewInject(R.id.recyclerView)
    private RecyclerView mRecyclerView;
    private String mSta_mid;

    private GroupAdapter mGroupAdapter;

    /**
     * show 获取分组列表
     * add  添加分组
     * edit 编辑分组
     * delete 删除分组
     */
    private String mType = "show";

    private String mId;

    private boolean mIsBack;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_group_management;
    }

    @Override
    protected void initialized() {
        mContext = this;
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("分组管理");
        mSta_mid = getIntent().getStringExtra("sta_mid");
        mIsBack =  getIntent().getBooleanExtra("isBack",false);

        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext,RecyclerView.VERTICAL,false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mGroupAdapter = new GroupAdapter();
        mRecyclerView.setAdapter(mGroupAdapter);

    }

    @Override
    protected void requestData() {
        bfriend_cate(mSta_mid,this);
    }


    void bfriend_cate(String sta_mid, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("sta_mid", sta_mid);
        params.addBodyParameter("t", "1");
        apiTool2.postApi(Config.BASE_URL + "OsManager/bfriend_cate", params, baseView);
    }

    void addGroup(String sta_mid,String name, BaseView baseView){
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("sta_mid", sta_mid);
        params.addBodyParameter("name", name);
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


    void deleteGroup(String id, BaseView baseView){
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("sta_mid", mSta_mid);
        params.addBodyParameter("id", id);
        params.addBodyParameter("is_del", "1");
        apiTool2.postApi(Config.BASE_URL + "OsManager/bfriend_cate", params, baseView);
    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        Map<String, String> map = JSONUtils.parseKeyAndValueToMap(jsonStr);
        if (requestUrl.endsWith("bfriend_cate")){
            if (mType.equals("show")) {
                final ArrayList<Map<String, String>> data = JSONUtils.parseKeyAndValueToMapList(map.get("data"));
                mGroupAdapter.setList(data);
                mGroupAdapter.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        if (mIsBack){
                            EventBus.getDefault().post(new MessageEvent(data.get(position).get("name")+";"+data.get(position).get("id"),"GroupManagementAty"));
                            finish();
                        }else {
                            Bundle bundle = new Bundle();
                            bundle.putString("sta_mid", mSta_mid);
                            bundle.putString("id", data.get(position).get("id"));
                            startActivity(GroupManageDetailsAty.class, bundle);
                        }
                    }
                });
                mGroupAdapter.setOnItemViewClickListener(new onItemViewClickListener() {
                    @Override
                    public void onItemViewClick(View view, int position) {
                        mId = data.get(position).get("id");
                        if (view.getId() == R.id.tv_edit){
                            mType = "edit";
                            addNameDialog();
                        }else if (view.getId() == R.id.tv_delete){
                            mType = "delete";
                            deleteGroup(mId,GroupManagementAty.this);
                        }
                    }
                });
            }else{
                showToast(map.get("message"));
                if (map.get("code").equals("1")){
                    mType = "show";
                    bfriend_cate(mSta_mid,this);
                }
            }
            return;
        }
    }

    @Override
    @OnClick({R.id.addGroupTv})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.addGroupTv:
                mType = "add";
                addNameDialog();
                break;
        }
    }

    private void addNameDialog() {
        final EditDialog.Builder builder = new EditDialog.Builder(mContext);
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (mType.equals("add")){
                    addGroup(mSta_mid,builder.getEditContent(),GroupManagementAty.this);
                }else if (mType.equals("edit")){
                    editGroup(mId,builder.getEditContent(),GroupManagementAty.this);
                }

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm.isActive()) {
                    imm.hideSoftInputFromWindow(GroupManagementAty.this.getCurrentFocus().getWindowToken(), 0);
                }
                dialog.dismiss();
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }


    private  class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.ViewHolder>{

        private Context mContext;
        private ArrayList<Map<String, String>> mList;

        private OnItemClickListener mOnItemClickListener;
        private onItemViewClickListener mOnItemViewClickListener;
        public GroupAdapter() {
            mList = new ArrayList<>();
        }

        public void setList(ArrayList<Map<String, String>> list) {
            mList.clear();
            mList.addAll(list);
            notifyDataSetChanged();
        }

        public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
            mOnItemClickListener = onItemClickListener;
        }

        public void setOnItemViewClickListener(onItemViewClickListener onItemViewClickListener) {
            mOnItemViewClickListener = onItemViewClickListener;
        }

        @NonNull
        @Override
        public GroupAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            mContext = parent.getContext();
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_group_management,parent,false);
            ViewHolder holder = new ViewHolder(view);
            ViewUtils.inject(holder,view);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
            Map<String, String> map = mList.get(position);
            holder.titleTv.setText(map.get("name"));
            holder.contentLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null){
                        mOnItemClickListener.onItemClick(holder.getLayoutPosition());
                    }
                }
            });

            holder.tv_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemViewClickListener != null){
                        mOnItemViewClickListener.onItemViewClick(v,holder.getLayoutPosition());
                    }
                }
            });

            holder.tv_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemViewClickListener != null){
                        mOnItemViewClickListener.onItemViewClick(v,holder.getLayoutPosition());
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder{


            @ViewInject(R.id.contentLayout)
            private FrameLayout contentLayout;

            @ViewInject(R.id.titleTv)
            private TextView titleTv;

            @ViewInject(R.id.tv_edit)
            private TextView tv_edit;

            @ViewInject(R.id.tv_delete)
            private TextView tv_delete;

            public ViewHolder(View itemView) {
                super(itemView);
            }
        }



    }
    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public interface onItemViewClickListener{
        void onItemViewClick(View view,int position);
    }

}
