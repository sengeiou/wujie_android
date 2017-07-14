package com.txd.hzj.wjlp.mainFgt.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.txd.hzj.wjlp.R;

import java.util.List;

/**
 * Created by Administrator on 2017/7/14 0014.
 */

public class MyOrderAdapter extends BaseAdapter {
    private Context context;
    private List<String> list;
    private LayoutInflater mInflater;
    public MyOrderAdapter(Context context,List<String>list){
        this.context=context;
        this.list=list;
        mInflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.order_item_li, null);
            holder = new ViewHolder();
            holder.title=convertView.findViewById(R.id.ItemTitle);
            convertView.setTag(holder);//绑定ViewHolder对象
        }else{
            holder = (ViewHolder)convertView.getTag();//取出ViewHolder对象
            holder.title.setText(list.get(i));
        }
        return convertView;
    }
}
    class ViewHolder{
        public TextView title;
    }
