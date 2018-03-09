package com.txd.hzj.wjlp.mellOnLine.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ants.theantsgo.base.BaseView;
import com.ants.theantsgo.config.Config;
import com.ants.theantsgo.httpTools.ApiTool2;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.bean.groupbuy.TicketListBean;
import com.txd.hzj.wjlp.login.LoginAty;

import java.util.List;
import java.util.Map;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/8/31 0031
 * 时间：10:11
 * 描述：
 * ===============Txunda===============
 */

public class TheTrickAdapter extends RecyclerView.Adapter<TheTrickAdapter.TrickViewHolder> {

    private Context context;
    private List<TicketListBean> list;

    public TheTrickAdapter(Context context, List<TicketListBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public TrickViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_popp_lingquan, null);
        TrickViewHolder trickViewHolder = new TrickViewHolder(view);
        ViewUtils.inject(trickViewHolder, view);
        return trickViewHolder;
    }

    private TicketListBean getItem(int i) {
        return list.get(i);
    }

    @Override
    public void onBindViewHolder(TrickViewHolder holder, final int position) {
        holder.tv_title.setText("¥" + getItem(position).getValue() + "优惠券");
        holder.tv_desc.setText(getItem(position).getTicket_name());
        if (getItem(position).getGet_receive().equals("0")) {
            holder.layout.setBackgroundResource(R.mipmap.icon_yhq_bg);
        } else {

            holder.layout.setBackgroundResource(R.mipmap.icon_yhq_bg_1);
        }
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Config.isLogin()) {
                    Intent i = new Intent();
                    i.setClass(context, LoginAty.class);
                    context.startActivity(i);
                    return;
                }

                RequestParams params = new RequestParams();
                params.addBodyParameter("ticket_id", getItem(position).getTicket_id());
                ApiTool2 apiTool2 = new ApiTool2();
                apiTool2.postApi(Config.BASE_URL + "Goods/getTicket", params, new BaseView() {
                    @Override
                    public void showDialog() {

                    }

                    @Override
                    public void showDialog(String text) {

                    }

                    @Override
                    public void showContent() {

                    }

                    @Override
                    public void removeDialog() {

                    }

                    @Override
                    public void removeContent() {

                    }

                    @Override
                    public void onStarted() {

                    }

                    @Override
                    public void onCancelled() {

                    }

                    @Override
                    public void onLoading(long total, long current, boolean isUploading) {

                    }

                    @Override
                    public void onException(Exception exception) {

                    }

                    @Override
                    public void onComplete(String requestUrl, String jsonStr) {
                        getItem(position).setGet_receive("1");
                        Toast.makeText(context, "领取成功！", Toast.LENGTH_SHORT).show();
                        notifyDataSetChanged();
                    }

                    @Override
                    public void onError(String requestUrl, Map<String, String> error) {
                        Toast.makeText(context, error.get("message"), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onErrorTip(String tips) {

                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class TrickViewHolder extends RecyclerView.ViewHolder {

        /**
         * 面值
         */
        @ViewInject(R.id.tv_title)
        private TextView tv_title;
        /**
         * 满足条件
         */
        @ViewInject(R.id.tv_desc)
        private TextView tv_desc;
        @ViewInject(R.id.layout)
        private LinearLayout layout;

        TrickViewHolder(View itemView) {
            super(itemView);
        }
    }

}
