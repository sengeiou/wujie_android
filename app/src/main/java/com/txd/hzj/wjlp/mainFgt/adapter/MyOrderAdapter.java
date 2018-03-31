package com.txd.hzj.wjlp.mainFgt.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ants.theantsgo.base.BaseView;
import com.ants.theantsgo.config.Config;
import com.ants.theantsgo.httpTools.ApiTool2;
import com.ants.theantsgo.tools.AlertDialog;
import com.ants.theantsgo.util.JSONUtils;
import com.ants.theantsgo.util.L;
import com.ants.theantsgo.view.inScroll.ListViewForScrollView;
import com.bumptech.glide.Glide;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.bean.Order;
import com.txd.hzj.wjlp.mellOnLine.gridClassify.hous.FindHouseByMapAty;
import com.txd.hzj.wjlp.new_wjyp.CarOrderInfo;
import com.txd.hzj.wjlp.new_wjyp.aty_comment;
import com.txd.hzj.wjlp.new_wjyp.aty_pay;

import java.util.List;
import java.util.Map;

/**
 * Created by lienchao on 2017/7/14 0014.
 */
public class MyOrderAdapter extends BaseAdapter {
    private Context context;
    private List<Order> list;
    private LayoutInflater mInflater;
    private ProgressDialog progressDialog;
    private String type;
    private int index;

    public MyOrderAdapter(Context context, List<Order> list, String type) {
        this.context = context;
        this.type = type;
        this.list = list;
        mInflater = LayoutInflater.from(context);
        progressDialog = new ProgressDialog(context, com.ants.theantsgo.R.style.loading_dialog);// 初始化progressDialog，设置样式
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(true);// 点击返回消失
        progressDialog.setCanceledOnTouchOutside(false);// 点击外部消失
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.order_item_li, null);
            ViewUtils.inject(holder, convertView);
            convertView.setTag(holder);//绑定ViewHolder对象
        } else {
            holder = (ViewHolder) convertView.getTag();//取出ViewHolder对象
        }
        holder.title.setText(type.equals("1") ? list.get(i).getShop_name() : list.get(i).getHouse_name());
        switch (list.get(i).getStatus()) {
            case "0":
                holder.state.setText("待付款");
                holder.tv_btn_left.setText("取消订单");
                holder.tv_btn_right.setText("付款");
                holder.tv_btn_left.setVisibility(View.VISIBLE);
                holder.tv_btn_right.setVisibility(View.VISIBLE);
                break;
            case "1":
                holder.state.setText("办理手续中");
                holder.tv_btn_left.setVisibility(View.GONE);
                holder.tv_btn_right.setVisibility(View.GONE);
                break;
            case "2":
                holder.state.setText("待评价");
                //holder.tv_btn_left.setText("查看物流");
                holder.tv_btn_right.setText("评价");
                holder.tv_btn_left.setVisibility(View.GONE);
                holder.tv_btn_right.setVisibility(View.VISIBLE);
                break;
//            case "3":
//                holder.state.setText("卖家已发货");
//                holder.tv_btn_left.setText("查看物流");
//                holder.tv_btn_right.setText("确认收货");
//                break;
            case "4":
                holder.state.setText("已完成");
                holder.tv_btn_right.setText("删除");
                holder.tv_btn_left.setVisibility(View.GONE);
                holder.tv_btn_right.setVisibility(View.VISIBLE);
                break;
            case "5":
                holder.state.setText("已取消");
                holder.tv_btn_left.setVisibility(View.GONE);
                holder.tv_btn_right.setVisibility(View.VISIBLE);
                holder.tv_btn_right.setText("删除");
                break;
        }
        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(context, FindHouseByMapAty.class);
                intent.putExtra("lng", list.get(i).getLng());
                intent.putExtra("lat", list.get(i).getLat());
                context.startActivity(intent);
            }
        });
        holder.tv_btn_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (list.get(i).getStatus().equals("0")) {
                    new AlertDialog(context).builder().setTitle("提示").setMsg("取消订单").setPositiveButton("确定", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            index = i;
                            RequestParams params = new RequestParams();
                            params.addBodyParameter("order_id", list.get(i).getOrder_id());
                            ApiTool2 apiTool2 = new ApiTool2();
                            apiTool2.postApi(Config.BASE_URL + (type.equals("1") ? "CarOrder/cancelOrder" : "HouseOrder/cancelOrder"), params, baseView);
                        }
                    }).setNegativeButton("取消", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    }).show();

                }
            }
        });
        holder.tv_btn_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (list.get(i).getStatus().equals("0")) {
                    RequestParams params = new RequestParams();
                    params.addBodyParameter("order_id", list.get(i).getOrder_id());
                    ApiTool2 apiTool2 = new ApiTool2();
                    apiTool2.postApi(Config.BASE_URL + (type.equals("1") ? "CarOrder/addOrder" : "HouseOrder/addOrder"), params, baseView);
                    progressDialog.show();
                    progressDialog.setContentView(com.ants.theantsgo.R.layout.loading_dialog);
                } else if (list.get(i).getStatus().equals("2")) {
                    Intent intent = new Intent(context, aty_comment.class);
                    intent.putExtra("id", list.get(i).getOrder_id());
                    intent.putExtra("type", type);
                    context.startActivity(intent);
                } else {

                    new AlertDialog(context).builder().setTitle("提示").setMsg("删除订单").setPositiveButton("确定", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            index = i;
                            RequestParams params = new RequestParams();
                            params.addBodyParameter("order_id", list.get(i).getOrder_id());
                            ApiTool2 apiTool2 = new ApiTool2();
                            apiTool2.postApi(Config.BASE_URL + (type.equals("1") ? "CarOrder/deleteOrder" : "HouseOrder/deleteOrder"), params, baseView);
                        }
                    }).setNegativeButton("取消", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    }).show();

                }
            }
        });

//        ChangeTextViewStyle.getInstance().forOrderPrice(context,
//                holder.goods_price_info_tv,
//                "共2件商品 合计：￥190.00(含运费￥10.00)");
        holder.goods_price_info_tv.setText("总计：¥" + list.get(i).getOrder_price());
        holder.goods_for_order_lv.setAdapter(new GoodsForOrderAdapter(i));
        holder.goods_for_order_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int a, long l) {
                Intent intent = new Intent();
                intent.setClass(context, CarOrderInfo.class);
                intent.putExtra("id", list.get(i).getOrder_id());
                intent.putExtra("type", type);
                context.startActivity(intent);
            }
        });
        return convertView;
    }

    class ViewHolder {
        @ViewInject(R.id.ItemTitle)
        public TextView title;
        @ViewInject(R.id.tv_state)
        public TextView state;

        @ViewInject(R.id.goods_price_info_tv)
        private TextView goods_price_info_tv;

        @ViewInject(R.id.tv_btn_left)
        public TextView tv_btn_left;
        @ViewInject(R.id.tv_btn_right)
        public TextView tv_btn_right;


        @ViewInject(R.id.goods_for_order_lv)
        private ListViewForScrollView goods_for_order_lv;

    }

    private class GoodsForOrderAdapter extends BaseAdapter {
        private int p;

        public GoodsForOrderAdapter(int i) {
            this.p = i;
        }

        private GOVH goVh;

        @Override
        public int getCount() {
            return 1;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = LayoutInflater.from(context).inflate(R.layout.aty_goods_for_order, null);
                goVh = new GOVH();
                ViewUtils.inject(goVh, view);
                view.setTag(goVh);
            } else {
                goVh = (GOVH) view.getTag();
            }
            Glide.with(context).load(type.equals("1") ? list.get(p).getCar_img() : list.get(p).getHouse_style_img()).into(goVh.image);
            goVh.name.setText(type.equals("1") ? list.get(p).getCar_name() : list.get(p).getStyle_name() + list.get(p).getTags());
            goVh.num.setText("x" + list.get(p).getNum());
            String str = null;
            if (type.equals("1")) {
                str = "车款";
            } else {
                str = "房款";
            }
            goVh.title.setText("可抵：¥" + list.get(p).getTrue_pre_money() + str);
            goVh.tv_price.setVisibility(View.VISIBLE);
            goVh.tv_price.setText("¥" + list.get(p).getPre_money());
            return view;
        }

        private class GOVH {
            @ViewInject(R.id.image)
            private ImageView image;
            @ViewInject(R.id.name)
            private TextView name;
            @ViewInject(R.id.jifenTv)
            private TextView jifenTv;
            @ViewInject(R.id.num)
            private TextView num;
            @ViewInject(R.id.title)
            private TextView title;
            @ViewInject(R.id.tv_price)
            private TextView tv_price;
        }

    }

    BaseView baseView = new BaseView() {
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
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            Toast.makeText(context, "网络连接失败...", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onComplete(String requestUrl, String jsonStr) {
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            if (requestUrl.contains("addOrder")) {
                Map<String, String> data = JSONUtils.parseKeyAndValueToMap(jsonStr);
                Intent i = new Intent();
                i.setClass(context, aty_pay.class);
                i.putExtra("data", data.get("data"));
                i.putExtra("type", type);
                context.startActivity(i);
            }
            if (requestUrl.contains("cancelOrder")) {
                list.get(index).setStatus("5");
                notifyDataSetChanged();
            }
            if (requestUrl.contains("deleteOrder")) {
                list.remove(index);
                notifyDataSetChanged();
            }
        }

        @Override
        public void onError(String requestUrl, Map<String, String> error) {
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            Toast.makeText(context, error.get("message"), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onErrorTip(String tips) {

        }
    };

}
