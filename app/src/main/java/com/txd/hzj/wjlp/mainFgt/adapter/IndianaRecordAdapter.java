package com.txd.hzj.wjlp.mainFgt.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.bean.Order;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lienchao on 2017/7/15 0015.
 * 夺宝纪录适配器
 */


public class IndianaRecordAdapter extends BaseAdapter {
    private Context context;
    private List<Order>list;
    private LayoutInflater mInflater;
    public IndianaRecordAdapter(Context context, List<Order> list,int type){
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
            convertView = mInflater.inflate(R.layout.indiana_record_item_li, null);
            holder.state=convertView.findViewById(R.id.tv_state);
            holder.rel_bottom=convertView.findViewById(R.id.rel_bottom);
            convertView.setTag(holder);//绑定ViewHolder对象
        }else{
            holder = (ViewHolder) convertView.getTag();//取出ViewHolder对象
            if(list.get(i).getType()==1){
                holder.state.setText("进行中");
                holder.rel_bottom.setVisibility(View.VISIBLE);
            }else if(list.get(i).getType()==2){
                holder.state.setText("已揭晓");
            }else {
                holder.state.setText("中奖记录");
            }
        }
        return convertView;
    }
    class ViewHolder{
        public TextView state;
        public RelativeLayout rel_bottom;
    }
}
