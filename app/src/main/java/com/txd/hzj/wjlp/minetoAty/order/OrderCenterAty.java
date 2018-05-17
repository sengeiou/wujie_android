package com.txd.hzj.wjlp.minetoAty.order;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.ants.theantsgo.util.JSONUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.http.index.IndexPst;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 订单中心
 * Created by Administrator on 2017/7/14 0014.
 */
public class OrderCenterAty extends BaseAty {

    @ViewInject(R.id.orderCenter_show_gv)
    private GridView orderCenter_show_gv;

    @ViewInject(R.id.titlt_conter_tv)
    private TextView titlt_conter_tv;

    List<ShowItem> list;

    Bundle mBundle = new Bundle();
    private ItemAdapter itemAdapter;

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_order_center_li;
    }

    @Override
    protected void initialized() {

        titlt_conter_tv.setText("订单中心");

//        IndexPst indexPst = new IndexPst(this);
//        indexPst.index("", "");

        list = new ArrayList<>();

        // 初始化添加三条记录
        list.add(new ShowItem(R.drawable.icon_order_center_01, "线上商城"));
        list.add(new ShowItem(R.mipmap.icon_order_vipcard, "会员卡"));
        list.add(new ShowItem(R.drawable.icon_chong, "线上充值"));
        list.add(new ShowItem(R.drawable.icon_order_center_04, "拼单购"));
        itemAdapter = new ItemAdapter();

        orderCenter_show_gv.setAdapter(itemAdapter);
        orderCenter_show_gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ShowItem showItem = list.get(position);
                if (showItem.getShowName().equals("线上商城")) {
                    mBundle.putString("title", "线上商城");
                    mBundle.putString("type", "0");
                    startActivity(OnlineShopAty.class, mBundle);
                } else if (showItem.getShowName().equals("线下商铺")) {
                    mBundle.putString("title", "线下商铺");
                    startActivity(OnlineShopAty.class, mBundle);
                } else if (showItem.getShowName().equals("无界商店")) {
                    mBundle.putString("title", "无界商店");
                    mBundle.putString("type", "7");
                    startActivity(OnlineShopAty.class, mBundle);
                } else if (showItem.getShowName().equals("拼单购")) {
                    mBundle.putString("title", "拼团区");
                    mBundle.putString("type", "3");
                    startActivity(OnlineShopAty.class, mBundle);
                } else if (showItem.getShowName().equals("无界预购")) {
                    mBundle.putString("title", "无界预购");
                    mBundle.putString("type", "4");
                    startActivity(OnlineShopAty.class, mBundle);
                } else if (showItem.getShowName().equals("比价购")) {
                    mBundle.putString("title", "比价购");
                    mBundle.putString("type", "6");
                    startActivity(OnlineShopAty.class, mBundle);
                } else if (showItem.getShowName().equals("积分抽奖")) {
                    mBundle.putString("title", "积分抽奖");
                    mBundle.putString("type", "5");
                    startActivity(OnlineShopAty.class, mBundle);
                } else if (showItem.getShowName().equals("汽车购")) {
                    mBundle.putString("title", "汽车购");
                    mBundle.putString("type", "1");
                    startActivity(OnlineShopAty.class, mBundle);
                } else if (showItem.getShowName().equals("房产购")) {
                    mBundle.putString("title", "房产购");
                    mBundle.putString("type", "2");
                    startActivity(OnlineShopAty.class, mBundle);
                } else if (showItem.getShowName().equals("会员卡")) {
                    startActivity(VipCardAty.class, null);
                } else if (showItem.getShowName().equals("线上充值")) {
                    mBundle.putString("title", "线上充值");
                    mBundle.putString("type", "8");
                    startActivity(OnlineShopAty.class, mBundle);
                }
            }
        });
    }

    @Override
    protected void requestData() {
    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        Map<String, String> map = JSONUtils.parseKeyAndValueToMap(jsonStr);

        if (requestUrl.contains("index") && map.get("code").equals("1")) { // 首页并且回传成功
            Map<String, String> data = JSONUtils.parseKeyAndValueToMap(map.get("data"));
            String activity_status = data.get("activity_status");
            if (activity_status.equals("1")) {
                // 获取信息成功之后先移除所有项
                list.removeAll(list);
                // 添加然后添加所有项
                list.add(new ShowItem(R.drawable.icon_order_center_01, "线上商城"));
                list.add(new ShowItem(R.drawable.icon_order_center_02, "线下商铺"));
                list.add(new ShowItem(R.drawable.icon_order_center_03, "无界商店"));
                list.add(new ShowItem(R.drawable.icon_order_center_04, "拼单购"));
                list.add(new ShowItem(R.drawable.icon_order_center_05, "无界预购"));
                list.add(new ShowItem(R.drawable.icon_order_center_06, "比价购"));
                list.add(new ShowItem(R.drawable.icon_order_center_07, "积分抽奖"));
                list.add(new ShowItem(R.drawable.icon_order_center_08, "汽车购"));
                list.add(new ShowItem(R.drawable.icon_order_center_09, "房产购"));
                list.add(new ShowItem(R.mipmap.icon_order_vipcard, "会员卡"));
                list.add(new ShowItem(R.drawable.icon_chong, "线上充值"));
                itemAdapter.notifyDataSetChanged(); // 通知Adapter刷新
            }
        }
    }

    class ShowItem {
        private int showImage; // 显示图片
        private String showName; // 显示名称

        public int getShowImage() {
            return showImage;
        }

        public void setShowImage(int showImage) {
            this.showImage = showImage;
        }

        public String getShowName() {
            return showName;
        }

        public void setShowName(String showName) {
            this.showName = showName;
        }

        public ShowItem(int showImage, String showName) {
            this.showImage = showImage;
            this.showName = showName;
        }
    }

    class ItemAdapter extends BaseAdapter {

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
            ViewHolder viewHolder;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = LayoutInflater.from(OrderCenterAty.this).inflate(R.layout.item_order_cenetr, null);
                ViewUtils.inject(viewHolder, convertView);
                convertView.setTag(viewHolder);//绑定ViewHolder对象
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            viewHolder.item_showImg_imgv.setImageResource(list.get(position).getShowImage());
            viewHolder.item_showText_tv.setText(list.get(position).getShowName());

            return convertView;
        }

        class ViewHolder {
            @ViewInject(R.id.item_showImg_imgv)
            private ImageView item_showImg_imgv;
            @ViewInject(R.id.item_showText_tv)
            private TextView item_showText_tv;
        }

    }

}
