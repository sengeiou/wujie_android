package com.txd.hzj.wjlp.distribution.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.distribution.bean.ShopPersonBean;

import java.util.List;

/**
 * 创建者：Qyl
 * 创建时间：2018/6/13 0013 15:03
 * 功能描述：顾客管理adapter
 * 联系方式：无
 */
public class ShopPersonAdapter extends RecyclerView.Adapter {
    private ShopPersonBean mShopPersonBean;
    private Context context;
    //传入的类型,0代表店主身份 1代表普通顾客
    private int mInt;
    //普通顾客列表
    private final List<ShopPersonBean.DataBean.ConsumerBean> mConsumer;
    //店主身份列表
    private final List<ShopPersonBean.DataBean.ShopBean> mShop;

    public ShopPersonAdapter(ShopPersonBean shopPersonBean, Context context, int type) {
        this.mShopPersonBean = shopPersonBean;
        this.context = context;
        this.mInt = type;
        mConsumer = mShopPersonBean.getData().getConsumer();
        mShop = mShopPersonBean.getData().getShop();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.shop_person_relist_item, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        String state="";
        if (mInt == 0 && holder instanceof MyViewHolder) {
            ShopPersonBean.DataBean.ShopBean shopBean = mShop.get(position);
            Glide.with(context).load(shopBean.getHead_path()).into(((MyViewHolder) holder).shop_person_item_ima);
            ((MyViewHolder) holder).shop_person_item_name.setText(shopBean.getNickname());
            if (null!=shopBean.getIs_balance() && "0".equals(shopBean.getIs_balance())){
                state="积分";
            }else if (null!=shopBean.getIs_balance() && "1".equals(shopBean.getIs_balance())){
                state="余额";
            }
            ((MyViewHolder) holder).shop_priceTv.setText("贡献"+shopBean.getProfit_num()+state);
            ((MyViewHolder) holder).shop_set_nameTv.setText(shopBean.getSet_name() + "店主");
            ((MyViewHolder) holder).deal_timeT.setText(shopBean.getDeal_time());

        } else if (mInt == 1 && holder instanceof MyViewHolder) {
            ShopPersonBean.DataBean.ConsumerBean consumerBean = mConsumer.get(position);
            Glide.with(context).load(consumerBean.getHead_path()).into(((MyViewHolder) holder).shop_person_item_ima);
            ((MyViewHolder) holder).shop_person_item_name.setText(consumerBean.getNickname());
            if (null!=consumerBean.getIs_balance() && "0".equals(consumerBean.getIs_balance())){
                state="积分";
            }else if (null!=consumerBean.getIs_balance() && "1".equals(consumerBean.getIs_balance())){
                state="余额";
            }
            ((MyViewHolder) holder).shop_priceTv.setText("贡献"+consumerBean.getProfit_num()+state);
            ((MyViewHolder) holder).shop_set_nameTv.setText(consumerBean.getMember_coding_html());
            ((MyViewHolder) holder).deal_timeT.setText(consumerBean.getDeal_time());
        }
    }

    @Override
    public int getItemCount() {
        return mInt == 0 ? mShop.size() > 0 ? mShop.size() : 0 : mConsumer.size() > 0 ? mConsumer.size() : 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        //头像
        private ImageView shop_person_item_ima;
        //店名
        private TextView shop_person_item_name;
        //价格
        private TextView shop_priceTv;
        //等级
        private TextView shop_set_nameTv;
        //日期
        private TextView deal_timeT;

        public MyViewHolder(View itemView) {
            super(itemView);
            shop_person_item_ima = itemView.findViewById(R.id.shop_person_item_ima);
            shop_person_item_name = itemView.findViewById(R.id.shop_person_item_name);
            shop_priceTv = itemView.findViewById(R.id.shop_priceTv);
            shop_set_nameTv = itemView.findViewById(R.id.shop_set_nameTv);
            deal_timeT = itemView.findViewById(R.id.deal_timeT);
        }
    }
}
