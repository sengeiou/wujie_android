package com.txd.hzj.wjlp.mainFgt.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.bean.Order;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lienchao on 2017/7/15 0015.
 */

public class AuctionRecordAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private Context context;
    private List<Order> list;
    public AuctionRecordAdapter(Context context, List<Order> list , int type){
        List<Order>orderlist=new ArrayList<>();
        for(int i=0;i<list.size();i++){
            if(type==list.get(i).getType()){
                orderlist.add(list.get(i));
            }
        }
        this.list=orderlist;
        this.context=context;
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
            convertView = mInflater.inflate(R.layout.auction_record_item_li, null);
            holder.state=convertView.findViewById(R.id.tv_state);
            convertView.setTag(holder);//绑定ViewHolder对象
        }else{
            holder = (ViewHolder)convertView.getTag();//取出ViewHolder对象
            if(list.get(i).getType()==1){
                holder.state.setText("正在进行 今天21:00结束");
            }else if(list.get(i).getType()==2){
                holder.state.setText("竞拍成功");
            }else {
                holder.state.setText("竞拍结束");
            }
        }
        return convertView;
    }
    class ViewHolder{
        public TextView state;
    }
}
