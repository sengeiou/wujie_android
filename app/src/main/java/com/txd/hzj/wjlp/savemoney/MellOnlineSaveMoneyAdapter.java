package com.txd.hzj.wjlp.savemoney;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.StrikethroughSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;

import java.util.ArrayList;
import java.util.Map;

import static com.txd.hzj.wjlp.savemoney.SaveMoneyFgt.openPinduoduo;
import static com.txd.hzj.wjlp.savemoney.SaveMoneyFgt.openTaobao;

/**
 * 创建者：zhangyunfei
 * 创建时间：2019/3/20 9:28
 * 功能描述：
 */
public class MellOnlineSaveMoneyAdapter extends BaseAdapter {
    private ArrayList<Map<String, String>> mList;
    private Context mContext;
    private LayoutInflater mInflater;

    public MellOnlineSaveMoneyAdapter(ArrayList<Map<String, String>> list, Context context) {
        mList = list;
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder;
        if (view == null){
            holder = new ViewHolder();
            view = mInflater.inflate(R.layout.item_save_money,parent,false);
            view.setTag(holder);
            ViewUtils.inject(holder,view);
        }else {
            holder = (ViewHolder) view.getTag();
        }

        final Map<String, String> map = mList.get(position);
        if (map.containsKey("pict_url") && !TextUtils.isEmpty(map.get("pict_url"))) {
            Glide.with(mContext).load(map.get("pict_url")).into(holder.img);
        }
        final String biaoshi = map.get("biaoshi");
        if (biaoshi.equals("taobao")){
            Drawable drawable = mContext.getDrawable(R.drawable.tb);
            holder.titleTv.setButtonDrawable(drawable);
        }else if (biaoshi.equals("tianmao")){
            Drawable drawable = mContext.getDrawable(R.drawable.tm);
            holder.titleTv.setButtonDrawable(drawable);
        }else if (biaoshi.equals("pinduoduo")){
            Drawable drawable = mContext.getDrawable(R.drawable.pdd);
            holder.titleTv.setButtonDrawable(drawable);
        }
        holder.titleTv.setText(map.get("title"));
        holder.titleTv.setFocusable(false);
        holder.priceTv.setText("¥" + map.get("zk_final_price"));
        SpannableString spannableString = new SpannableString("¥" + map.get("reserve_price"));
        StrikethroughSpan strikethroughSpan = new StrikethroughSpan();
        spannableString.setSpan(strikethroughSpan, 0, spannableString.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        holder.older_price_tv.setText(spannableString);
        holder.sellNumTv.setText(map.get("volume"));
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (biaoshi.equals("taobao") || biaoshi.equals("tianmao")){
                    openTaobao(mContext,map.get("item_url"));
                }else if (biaoshi.equals("pinduoduo")){
                    openPinduoduo(mContext,map.get("item_url"));
                }
            }
        });
        return view;
    }

    public static class ViewHolder{
        @ViewInject(R.id.img)
        ImageView img;
        @ViewInject(R.id.titleTv)
        RadioButton titleTv;
        @ViewInject(R.id.priceTv)
        TextView priceTv;
        @ViewInject(R.id.older_price_tv)
        TextView older_price_tv;
        @ViewInject(R.id.sellNumTv)
        TextView sellNumTv;
    }
}
