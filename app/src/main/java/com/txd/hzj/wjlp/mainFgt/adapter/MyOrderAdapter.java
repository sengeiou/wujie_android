package com.txd.hzj.wjlp.mainFgt.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ants.theantsgo.util.L;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.bean.Order;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by lienchao on 2017/7/14 0014.
 */

public class MyOrderAdapter extends BaseAdapter {
    private Context context;
    private List<Order> list;
    private LayoutInflater mInflater;
    private int type;
    public MyOrderAdapter(Context context,List<Order>list,int type){
        this.context=context;
        List<Order>orderlist=new ArrayList<>();
        if(type==0){
            this.list=list;
        }else{
            for(int i=0;i<list.size();i++){
                if(type==list.get(i).getType()){
                    orderlist.add(list.get(i));
                }
            }
            this.list=orderlist;
        }
        this.type=type;
        mInflater = LayoutInflater.from(context);
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
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.order_item_li, null);

            holder.title=convertView.findViewById(R.id.ItemTitle);
            holder.state=convertView.findViewById(R.id.tv_state);
            holder.tv_btn_left=convertView.findViewById(R.id.tv_btn_left);
            holder.tv_btn_right=convertView.findViewById(R.id.tv_btn_right);
            convertView.setTag(holder);//绑定ViewHolder对象
        }else{
            holder = (ViewHolder)convertView.getTag();//取出ViewHolder对象
            holder.title.setText(list.get(i).getName());
            if(list.get(i).getType()==1){
                holder.state.setText("待付款");
                holder.tv_btn_left.setText("取消订单");
                holder.tv_btn_right.setText("付款");
            }else if(list.get(i).getType()==2){
                holder.state.setText("买家已付款");
                holder.tv_btn_left.setVisibility(View.GONE);
                holder.tv_btn_right.setVisibility(View.GONE);
            }else if(list.get(i).getType()==3){
                holder.state.setText("卖家已发货");
                holder.tv_btn_left.setText("查看物流");
                holder.tv_btn_right.setText("确认收货");
            }else if(list.get(i).getType()==4){
                holder.state.setText("交易成功");
                holder.tv_btn_left.setText("查看物流");
                holder.tv_btn_right.setText("评价");
            }else{

            }
        }
        return convertView;
    }
    class ViewHolder{
        public TextView title;
        public TextView state;
        public TextView tv_btn_left;
        public TextView tv_btn_right;
    }

}
