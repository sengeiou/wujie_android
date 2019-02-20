package com.txd.hzj.wjlp.minetoaty.friendsofmerchant;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.ants.theantsgo.config.Config;
import com.bumptech.glide.Glide;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.webviewH5.WebViewAty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.gavinliu.android.lib.shapedimageview.ShapedImageView;

/**
 * 创建者：zhangyunfei
 * 创建时间：2019/1/22 8:43
 * 功能描述：商友资料
 */
public class BusinessFriendDataAty extends BaseAty {

    private Context mContext;

    @ViewInject(R.id.titlt_conter_tv)
    private TextView titlt_conter_tv;

    @ViewInject(R.id.headImg)
    private ShapedImageView headImg;


    @ViewInject(R.id.sexTv)
    private TextView sexTv;


    @ViewInject(R.id.idTv)
    private TextView idTv;

    @ViewInject(R.id.nameTv)
    private TextView nameTv;

    @ViewInject(R.id.addressTv)
    private TextView addressTv;

//    @ViewInject(R.id.shopIdTv)
//    private TextView shopIdTv;
    @ViewInject(R.id.listView)
    private ListView mListView;


    private Map<String, String> map;
    private String mSta_mid;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_business_friend_data;
    }

    @Override
    protected void initialized() {
        mContext = this;
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("商友资料");
        map = (Map<String, String>) getIntent().getSerializableExtra("map");
        mSta_mid = getIntent().getStringExtra("sta_mid");

        Glide.with(mContext).load(map.get("head_pic")).into(headImg);
        sexTv.setText(map.get("sex"));
        idTv.setText(map.get("id"));
        nameTv.setText(map.get("nickname"));
        addressTv.setText(map.get("area"));
        String m_name = map.get("m_name");
        List<String> nameList = characterString(m_name);
        final String m_id = map.get("m_id");
        final List<String> idList = characterString(m_id);
        mListView.setAdapter(new TitleAdapter(nameList,mContext));
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putBoolean("isShowTitle",false);
                bundle.putString("url", Config.SHARE_URL + "/Wap/OfflineStore/offlineShop/merchant_id/"+idList.get(position)+".html");
                startActivity(WebViewAty.class, bundle);
            }
        });
    }

    public List<String> characterString(String s){
        List<String> stringList = new ArrayList<>();
        String substring = s.substring(1, s.length()-1);
        if (s.contains(",")){
            String[] split = substring.split(",");
            for (int i = 0; i < split.length; i++) {
                stringList.add(split[i].substring(1,split[i].length()-1));
            }
        }else {
            stringList.add(substring.substring(1,substring.length()-1));
        }
        return  stringList;
    }

    private class TitleAdapter extends BaseAdapter{
        private List<String> data;
        private Context mContext;
        private LayoutInflater mInflater;
        public TitleAdapter(List<String> data, Context context) {
            this.data = data;
            this.mContext = context;
            mInflater = LayoutInflater.from(mContext);
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null){
                holder = new ViewHolder();
                convertView = mInflater.inflate(R.layout.item_title2,null);
                holder.title = convertView.findViewById(R.id.tv_title);
                convertView.setTag(holder);
            }else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.title.setText(data.get(position));
            return convertView;
        }

        public  class ViewHolder{
            TextView title;
        }
    }

    @Override
    protected void requestData() {

    }

    @Override
    @OnClick({R.id.addFriendTv})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.addFriendTv:
                Bundle bundle = new Bundle();
                bundle.putString("sta_mid",mSta_mid);
                bundle.putSerializable("map", (Serializable) map);
                startActivity(AddFriendAty.class,bundle);
                finish();
                break;
        }
    }
}
