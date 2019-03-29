package com.txd.hzj.wjlp.clearinventory;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.ants.theantsgo.base.BaseView;
import com.ants.theantsgo.config.Config;
import com.ants.theantsgo.httpTools.ApiTool2;
import com.ants.theantsgo.util.JSONUtils;
import com.bumptech.glide.Glide;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.publicinterface.OnItemViewClickListener;
import com.txd.hzj.wjlp.shoppingCart.BuildOrderAty;
import com.txd.hzj.wjlp.tool.WJConfig;

import java.util.ArrayList;
import java.util.Map;

/**
 * 创建者：zhangyunfei
 * 创建时间：2019/2/28 10:10
 * 功能描述：
 */
public class ConsignmentAty extends BaseAty {
    @ViewInject(R.id.titlt_conter_tv)
    private TextView titlt_conter_tv;

    @ViewInject(R.id.recyclerView)
    private RecyclerView recyclerView;

    private Context mContext;
    private ConsignmentAdapter mConsignmentAdapter;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_consignment;
    }

    @Override
    protected void initialized() {
        mContext = this;
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("寄售中");

        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(layoutManager);
    }

    private void showPop(View view, final Map<String, String> map) {
        PopupWindow popupWindow = new PopupWindow();
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        popupWindow.setOutsideTouchable(false);
        popupWindow.setFocusable(true);
        popupWindow.setWidth(LinearLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        View contentView = LayoutInflater.from(mContext).inflate(R.layout.pop_consignment, null);
        LinearLayout detailsLayout = contentView.findViewById(R.id.detailsLayout);
        LinearLayout goodsLayout = contentView.findViewById(R.id.goodsLayout);
        LinearLayout refundLayout = contentView.findViewById(R.id.refundLayout);
        LinearLayout shareLayout = contentView.findViewById(R.id.shareLayout);

        detailsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        goodsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("type", WJConfig.TYPE_HQKCTH);
                bundle.putString("order_id",map.get("clean_id"));
                bundle.putString("num",map.get("goods_num"));
                startActivity(BuildOrderAty.class,bundle);
//                MikyouCommonDialog mikyouCommonDialog = new MikyouCommonDialog(mContext, "您确定要提货吗？", "提示", "确认", "取消", false);
//                mikyouCommonDialog.setOnDiaLogListener(new MikyouCommonDialog.OnDialogListener() {
//                    @Override
//                    public void dialogListener(int btnType, View customView, DialogInterface dialogInterface, int which) {
//                        if (btnType == MikyouCommonDialog.OK) {
//
//                        }
//                        dialogInterface.dismiss();
//                    }
//                });
//                mikyouCommonDialog.showDialog();
            }
        });

        refundLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("clean_id", map.get("clean_id"));
                bundle.putString("goods_num", map.get("goods_num"));
                startActivity(RefundedAty.class, bundle);
                //                MikyouCommonDialog mikyouCommonDialog = new MikyouCommonDialog(mContext,"您确定要申请退款吗？","提示","确认","取消",false);
                //                mikyouCommonDialog.setOnDiaLogListener(new MikyouCommonDialog.OnDialogListener() {
                //                    @Override
                //                    public void dialogListener(int btnType, View customView, DialogInterface dialogInterface, int which) {
                //                        if (btnType == MikyouCommonDialog.OK){
                //
                //                        }
                //                        dialogInterface.dismiss();
                //                    }
                //                });
                //                mikyouCommonDialog.showDialog();
            }
        });


        shareLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String goods_id = map.get("goods_id");
                String share_url = Config.OFFICIAL_WEB + "Wap/Goods/goodsInfo/" + "goods_id/" + goods_id + ".html";
                toShare(map.get("goods_name"), map.get("goods_img"), share_url, map.get("share_content"),goods_id , "1");
            }
        });

        popupWindow.setContentView(contentView);

        int[] locations = new int[2];
        view.getLocationOnScreen(locations);
        popupWindow.showAtLocation(view, Gravity.NO_GRAVITY, view.getWidth() * 6, (int) (locations[1] - view.getHeight() * 1.5));

        //        Window window = getWindow();
        //        window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        //        WindowManager.LayoutParams attributes = window.getAttributes();
        //        attributes.alpha = 1.0f;
        //        window.setAttributes(attributes);
        //
        //        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
        //            @Override
        //            public void onDismiss() {
        //                Window window = getWindow();
        //                window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        //                WindowManager.LayoutParams attributes = window.getAttributes();
        //                attributes.alpha = 1.0f;
        //                window.setAttributes(attributes);
        //            }
        //        });
    }

    @Override
    protected void requestData() {
        clean_goods_list("1", "", this);
    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        final Map<String, String> map = JSONUtils.parseKeyAndValueToMap(jsonStr);
        if (requestUrl.endsWith("clean_goods_list")) {
            final ArrayList<Map<String, String>> mapArrayList = JSONUtils.parseKeyAndValueToMapList(map.get("data"));
            if (mapArrayList != null) {
                mConsignmentAdapter = new ConsignmentAdapter(mapArrayList);
                recyclerView.setAdapter(mConsignmentAdapter);
                mConsignmentAdapter.setOnItemViewClickListener(new OnItemViewClickListener() {
                    @Override
                    public void onItemViewClick(View view, int position) {
                        showPop(view, mapArrayList.get(position));
                    }
                });
            }
            return;
        }

        if (requestUrl.endsWith("cleanPickup")) {
            showToast(map.get("message"));
            requestData();
        }
    }

    /**
     * @param clean_order_status 1寄售中 2已交易 3已提货 4已退款
     * @param order_status       clean_order_status = 2 1 待发货 2 待收货 3 已退款 4 已完成 clean_order_status = 3 1 待发货 2 待收货 3 已完成 clean_order_status = 4 1 退款中 2 已完成
     */
    public static void clean_goods_list(String clean_order_status, String order_status, BaseView baseView) {
        ApiTool2 apiTool2 = new ApiTool2();
        RequestParams params = new RequestParams();
        params.addBodyParameter("clean_order_status", clean_order_status);
        if (!TextUtils.isEmpty(order_status)) {
            params.addBodyParameter("order_status", order_status);
        }
        apiTool2.postApi(Config.SHARE_URL + "index.php/Api/Clean/clean_goods_list", params, baseView);
    }



    private static class ConsignmentAdapter extends RecyclerView.Adapter<ConsignmentAdapter.ViewHolder> {
        private Context mContext;

        private OnItemViewClickListener mOnItemViewClickListener;

        private ArrayList<Map<String, String>> mList;


        public ConsignmentAdapter(ArrayList<Map<String, String>> list) {
            mList = list;
        }

        public void setOnItemViewClickListener(OnItemViewClickListener onItemViewClickListener) {
            mOnItemViewClickListener = onItemViewClickListener;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            mContext = parent.getContext();
            View view = LayoutInflater.from(mContext).inflate(R.layout.layout_item_consignment, parent, false);
            ViewHolder holder = new ViewHolder(view);
            ViewUtils.inject(holder, view);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
            Map<String, String> map = mList.get(position);
            Glide.with(mContext).load(map.get("goods_img")).into(holder.picImg);
            holder.titleTv.setText(map.get("goods_name"));
            holder.priceTv1.setText("¥" + map.get("wholesale_price"));
            holder.priceTv2.setText("¥" + map.get("profit"));
            holder.timeTv.setText("活动时间：" + map.get("start_time") + "-" + map.get("end_time"));


            holder.moreImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemViewClickListener != null) {
                        mOnItemViewClickListener.onItemViewClick(v, holder.getLayoutPosition());
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {

            @ViewInject(R.id.picImg)
            private ImageView picImg;

            @ViewInject(R.id.titleTv)
            private TextView titleTv;

            @ViewInject(R.id.priceTv1)
            private TextView priceTv1;

            @ViewInject(R.id.priceTv2)
            private TextView priceTv2;

            @ViewInject(R.id.timeTv)
            private TextView timeTv;


            @ViewInject(R.id.moreImg)
            private ImageView moreImg;


            public ViewHolder(View itemView) {
                super(itemView);
            }
        }

    }
}
