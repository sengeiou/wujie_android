package com.txd.hzj.wjlp.mainFgt.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ants.theantsgo.base.BaseView;
import com.ants.theantsgo.tools.AlertDialog;
import com.ants.theantsgo.util.JSONUtils;
import com.ants.theantsgo.view.taobaoprogressbar.CustomProgressBar;
import com.bumptech.glide.Glide;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.minetoAty.PayForAppAty;
import com.txd.hzj.wjlp.minetoAty.order.GoodLuckOrderDetailsAty;
import com.txd.hzj.wjlp.tool.ChangeTextViewStyle;
import com.txd.hzj.wjlp.tool.CommonPopupWindow;
import com.txd.hzj.wjlp.txunda_lh.http.IntegralOrder;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import cn.gavinliu.android.lib.shapedimageview.ShapedImageView;

/**
 * Created by lienchao on 2017/7/15 0015.
 * 夺宝纪录适配器
 */


public class IndianaRecordAdapter extends BaseAdapter {
    private Context context;
    private List<Map<String, String>> list;
    private ViewHolder holder;
    private CommonPopupWindow commonPopupWindow;
    private Map<String, String> data;

    public IndianaRecordAdapter(Context context, List<Map<String, String>> list) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Map<String, String> getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View convertView, ViewGroup viewGroup) {
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.indiana_record_item_li, null);
            holder = new ViewHolder();
            convertView.setTag(holder);//绑定ViewHolder对象
            ViewUtils.inject(holder, convertView);
        } else {
            holder = (ViewHolder) convertView.getTag();//取出ViewHolder对象
        }
        data = JSONUtils.parseKeyAndValueToMap(getItem(i).get("order_goods"));
        switch (getItem(i).get("order_status")) {
            case "10":
                holder.state.setText("进行中");
                holder.btn.setText("追加");
                break;
            case "11":
                holder.state.setText("未中奖");
                holder.btn.setText("删除");
                break;
            case "12":
                holder.state.setText("已中奖");
                holder.btn.setText("删除");
                break;
            default:
                holder.state.setText("状态有误,请联系客服");
                holder.btn.setVisibility(View.GONE);
                break;
        }
        Glide.with(context).load(data.get("pic")).into(holder.img_pic);
        holder.tv_name.setText(data.get("goods_name"));
        holder.tv_price.setText("¥" + data.get("shop_price"));
        holder.tv_integral.setText("期号：" + getItem(i).get("time_num"));
        holder.tv_add_num.setText("我已参加：" + getItem(i).get("goods_num") + "人次");
        int max;
        int sell_num;
        max = Integer.parseInt(getItem(i).get("all_num"));
        sell_num = Integer.parseInt(getItem(i).get("add_num"));
        double coll = sell_num * 100.0f / max;
        if (sell_num >= max) {
            coll = 100;
        }
        holder.cpb_progresbar2.setMaxProgress(max);
        holder.cpb_progresbar2.setCurProgress(sell_num);
        String str = new BigDecimal(coll).setScale(2, BigDecimal.ROUND_DOWN).toString();
        holder.preferential_type_tv.setText(str + "%");
        holder.tv_person_num.setText("总需" + getItem(i).get("all_num") + "人/剩余" + getItem(i).get("add_num") + "人");
        holder.tv_sum_price.setText("共" + getItem(i).get("goods_num") + "人次 合计：¥" + getItem(i).get("order_price"));
        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getItem(i).get("order_status").equals("10")) {
                    showPop(v, data.get("pic"), data.get("shop_price"), getItem(i).get("order_id"));
                } else {
                    new AlertDialog(context).builder().setTitle("提示").setMsg("是否删除订单？").setNegativeButton("确定", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            IntegralOrder.DeleteOrder(getItem(i).get("order_id"), new BaseView() {
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
                                    list.remove(i);
                                    notifyDataSetChanged();

                                }

                                @Override
                                public void onLoading(long total, long current, boolean isUploading) {

                                }

                                @Override
                                public void onException(Exception exception) {

                                }

                                @Override
                                public void onComplete(String requestUrl, String jsonStr) {

                                }

                                @Override
                                public void onError(String requestUrl, Map<String, String> error) {

                                }

                                @Override
                                public void onErrorTip(String tips) {

                                }
                            });
                        }
                    }).setPositiveButton("取消", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    }).show();
                }
            }
        });
        holder.root_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent o = new Intent();
                o.putExtra("id", getItem(i).get("order_id"));
                o.setClass(context, GoodLuckOrderDetailsAty.class);
                context.startActivity(o);

            }
        });
        return convertView;
    }

    class ViewHolder {
        @ViewInject(R.id.layout)
        private LinearLayout root_layout;
        @ViewInject(R.id.tv_state)
        private TextView state;
        @ViewInject(R.id.img_pic)
        private ImageView img_pic;
        @ViewInject(R.id.tv_name)
        private TextView tv_name;
        @ViewInject(R.id.tv_price)
        private TextView tv_price;
        @ViewInject(R.id.tv_integral)
        private TextView tv_integral;
        @ViewInject(R.id.tv_add_num)
        private TextView tv_add_num;
        @ViewInject(R.id.tv_show)
        private TextView tv_show;
        @ViewInject(R.id.cpb_progresbar2)
        private CustomProgressBar cpb_progresbar2;
        @ViewInject(R.id.preferential_type_tv)
        private TextView preferential_type_tv;
        @ViewInject(R.id.tv_person_num)
        private TextView tv_person_num;
        @ViewInject(R.id.tv_sum_price)
        private TextView tv_sum_price;
        @ViewInject(R.id.btn)
        private TextView btn;

    }


    public void showPop(View view, final String url, final String price, final String order_id) {
        if (commonPopupWindow != null && commonPopupWindow.isShowing()) return;
        commonPopupWindow = new CommonPopupWindow.Builder(context)
                .setView(R.layout.popp_addorder)
                .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                .setBackGroundLevel(0.7f)
                .setViewOnclickListener(new CommonPopupWindow.ViewInterface() {
                    @Override
                    public void getChildView(View view, int layoutResId, int position) {
                        ImageView im_clone = view.findViewById(R.id.im_clone);
                        ImageView im_jian = view.findViewById(R.id.im_jian);
                        ImageView im_jia = view.findViewById(R.id.im_jia);
                        TextView goods_price_tv = view.findViewById(R.id.goods_price_tv);
                        final TextView tv_num = view.findViewById(R.id.tv_num);
                        TextView tv_submit = view.findViewById(R.id.tv_submit);
                        ShapedImageView imageview = (ShapedImageView) view.findViewById(R.id.imageview);
                        Glide.with(context).load(url).into(imageview);
                        ChangeTextViewStyle.getInstance().forGoodsPrice24(context, goods_price_tv, "￥" + price);
                        im_jian.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                int i = Integer.parseInt(tv_num.getText().toString());
                                if (i == 1) {
                                    return;
                                }
                                i--;
                                tv_num.setText(String.valueOf(i));
                            }
                        });
                        im_jia.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                int i = Integer.parseInt(tv_num.getText().toString());
                                i++;
                                tv_num.setText(String.valueOf(i));
                            }
                        });
                        im_clone.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                commonPopupWindow.dismiss();
                            }
                        });
                        tv_submit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                commonPopupWindow.dismiss();
                                Intent intent = new Intent();
                                intent.putExtra("type", "7");
                                intent.putExtra("order_type", "7");
                                intent.putExtra("num", tv_num.getText().toString());
                                intent.putExtra("order_id", order_id);
                                intent.setClass(context, PayForAppAty.class);
                                context.startActivity(intent);
                            }
                        });


                    }
                }, 0)
                .setAnimationStyle(R.style.animbottom)
                .create();
        commonPopupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
    }

}
