package com.txd.hzj.wjlp.mellonLine.gridClassify;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.bean.EasemobBean;

import java.util.List;

/**
 * 创建者：TJDragon(LiuGang)
 * 创建时间：2018/6/19 11:35
 * 功能描述：  商家详情界面调用聊天，弹出聊天对话框适配器
 * 联系方式：常用邮箱或电话
 */
public class MerchantDialogAdapter extends BaseAdapter {
    List<EasemobBean.DataBean.EasemobAccountBean> list;
    private LayoutInflater inflater;
    private Context mContext;

    public MerchantDialogAdapter(List<EasemobBean.DataBean.EasemobAccountBean> list, Context context) {
        this.list = list;
        mContext = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyHolder holder;
        if (convertView == null) {
            holder = new MyHolder();
            convertView = inflater.inflate(R.layout.item_popup_sel_chat, null);
            convertView.setTag(holder);
            com.lidroid.xutils.ViewUtils.inject(holder, convertView);
        } else {
            holder = (MyHolder) convertView.getTag();
        }

        EasemobBean.DataBean.EasemobAccountBean easemobAccountBean = list.get(position);

        Glide.with(mContext).load(list.get(position).getHead_pic())
                .placeholder(R.drawable.ic_default)
                .error(R.drawable.ic_default)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(holder.itemPopSelChat_headPic_imgv);
        holder.itemPopSelChat_nickname_tv.setText(easemobAccountBean.getNickname());
        holder.itemPopSelChat_position_tv.setText(easemobAccountBean.getPosition());
        return convertView;
    }

    class MyHolder {
        @ViewInject(R.id.itemPopSelChat_headPic_imgv)
        ImageView itemPopSelChat_headPic_imgv; // 客服头像
        @ViewInject(R.id.itemPopSelChat_nickname_tv)
        TextView itemPopSelChat_nickname_tv; // 客服名称
        @ViewInject(R.id.itemPopSelChat_position_tv)
        TextView itemPopSelChat_position_tv; // 在线状态
    }
}
