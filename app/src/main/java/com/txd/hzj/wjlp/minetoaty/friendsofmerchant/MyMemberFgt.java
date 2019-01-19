package com.txd.hzj.wjlp.minetoaty.friendsofmerchant;

import android.content.Context;
import android.os.Bundle;
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
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseFgt;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.gavinliu.android.lib.shapedimageview.ShapedImageView;

/**
 * 创建者：zhangyunfei
 * 创建时间：2019/1/18 8:59
 * 功能描述：我的会员
 */
public class MyMemberFgt extends BaseFgt {
    private Context mContext;

    @ViewInject(R.id.numTv)
    private TextView numTv;

    @ViewInject(R.id.recyclerView)
    private RecyclerView mRecyclerView;

    private MyMemberAdapter mMemberAdapter;

    private String mType = "1";

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
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(layoutManager);
        mMemberAdapter = new MyMemberAdapter();
        mRecyclerView.setAdapter(mMemberAdapter);
        app_my_member_list(((ChangeMembersAty) getActivity()).getSta_mid(), mType, this);
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
        if (requestUrl.endsWith("app_my_member_list")) {
            numTv.setText("会员总数\u0020" + map.get("nums"));
            ArrayList<MyMemberBean> data = JSONUtils.parseKeyAndValueToMapList(MyMemberBean.class, map.get("data"));
            if (data != null && data.size() > 0) {
                mMemberAdapter.setList(data);
            }
        }
    }

    @Override
    protected void immersionInit() {
    }

    @Override
    @OnClick({R.id.changeTv})
    public void onClick(View view) {
        Bundle bundle = new Bundle();
        bundle.putString("sta_mid",((ChangeMembersAty) getActivity()).getSta_mid());
        startActivity(SetExchangeConditionsAty.class, bundle);
    }

    private static class MyMemberAdapter extends RecyclerView.Adapter<MyMemberAdapter.ViewHolder> {

        private Context mContext;
        private ArrayList<MyMemberBean> mList;

        public MyMemberAdapter() {
            mList = new ArrayList<>();
        }

        public void setList(ArrayList<MyMemberBean> list) {
            mList.clear();
            mList.addAll(list);
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public MyMemberAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            mContext = parent.getContext();
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_my_member, parent, false);
            MyMemberAdapter.ViewHolder holder = new MyMemberAdapter.ViewHolder(view);
            ViewUtils.inject(holder, view);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull MyMemberAdapter.ViewHolder holder, int position) {
            MyMemberBean myMemberBean = mList.get(position);
            Glide.with(mContext).load(myMemberBean.getHead_pic()).into(holder.headImg);
            holder.nameTv.setText(myMemberBean.getUser_name());
            holder.gradeTv.setText(myMemberBean.getMember_coding());
            int sex = Integer.parseInt(myMemberBean.getSex());
            if (sex == 1) {
                holder.sexTv.setVisibility(View.VISIBLE);
                holder.sexTv.setText("男");
            } else if (sex == 2) {
                holder.sexTv.setVisibility(View.VISIBLE);
                holder.sexTv.setText("女");
            } else if (sex == 3) {
                holder.sexTv.setVisibility(View.GONE);
            }

           if (!android.text.TextUtils.isEmpty(myMemberBean.getAge())) {
               holder.ageTv.setText(myMemberBean.getAge() + "岁");
           }else {
               holder.ageTv.setText("");
           }
            holder.timeTv.setText(myMemberBean.getCreate_time());
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {

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


    public static class MyMemberBean {


        /**
         * user_id : 277
         * user_name : 无界新人
         * member_coding : 无界会员
         * sex : 0
         * id_card_num :
         * create_time : 2018-12-05 10:42:55
         * head_pic : http://test2.wujiemall.com/Uploads/User/pd_user.png
         * type : 0
         * easemob_account : 154397777588312
         * easemob_pwd : 1543977775
         * age :
         * param : eyJpZCI6IjI3NyIsIm5pY2tuYW1lIjoiXHU2NWUwXHU3NTRjXHU2NWIwXHU0ZWJhIiwiaGVhZF9waWMiOiJodHRwOlwvXC90ZXN0Mi53dWppZW1hbGwuY29tXC9VcGxvYWRzXC9Vc2VyXC9wZF91c2VyLnBuZyIsImVhc2Vtb2JfYWNjb3VudCI6IjE1NDM5Nzc3NzU4ODMxMiIsImVhc2Vtb2JfcHdkIjoiMTU0Mzk3Nzc3NSJ9
         * stage_message : [{"content":"东方闪电fd","status":"0","pic":"27850","create_time":"2018-12-21 14:56:55"}]
         */

        private String user_id;
        private String user_name;
        private String member_coding;
        private String sex;
        private String id_card_num;
        private String create_time;
        private String head_pic;
        private String type;
        private String easemob_account;
        private String easemob_pwd;
        private String age;
        private String param;
        private List<StageMessageBean> stage_message;

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getMember_coding() {
            return member_coding;
        }

        public void setMember_coding(String member_coding) {
            this.member_coding = member_coding;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getId_card_num() {
            return id_card_num;
        }

        public void setId_card_num(String id_card_num) {
            this.id_card_num = id_card_num;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
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

        public String getEasemob_account() {
            return easemob_account;
        }

        public void setEasemob_account(String easemob_account) {
            this.easemob_account = easemob_account;
        }

        public String getEasemob_pwd() {
            return easemob_pwd;
        }

        public void setEasemob_pwd(String easemob_pwd) {
            this.easemob_pwd = easemob_pwd;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public String getParam() {
            return param;
        }

        public void setParam(String param) {
            this.param = param;
        }

        public List<StageMessageBean> getStage_message() {
            return stage_message;
        }

        public void setStage_message(List<StageMessageBean> stage_message) {
            this.stage_message = stage_message;
        }

        public static class StageMessageBean {
            /**
             * content : 东方闪电fd
             * status : 0
             * pic : 27850
             * create_time : 2018-12-21 14:56:55
             */

            private String content;
            private String status;
            private String pic;
            private String create_time;

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getPic() {
                return pic;
            }

            public void setPic(String pic) {
                this.pic = pic;
            }

            public String getCreate_time() {
                return create_time;
            }

            public void setCreate_time(String create_time) {
                this.create_time = create_time;
            }
        }
    }
}
