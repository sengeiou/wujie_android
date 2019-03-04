package com.txd.hzj.wjlp.clearinventory;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.ants.theantsgo.tips.MikyouCommonDialog;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.publicinterface.OnItemViewClickListener;

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
    private ConsignmentAdpater mConsignmentAdpater;

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
        mConsignmentAdpater = new ConsignmentAdpater();
        recyclerView.setAdapter(mConsignmentAdpater);
        mConsignmentAdpater.setOnItemViewClickListener(new OnItemViewClickListener() {
            @Override
            public void onItemViewClick(View view, int position) {
                showPop(view);
            }
        });
    }

    private void showPop(View view) {
        PopupWindow popupWindow = new PopupWindow();
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        popupWindow.setOutsideTouchable(false);
        popupWindow.setFocusable(true);
        popupWindow.setWidth(LinearLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        View contentView = LayoutInflater.from(mContext).inflate(R.layout.pop_consignment,null);
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

            }
        });

        refundLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MikyouCommonDialog mikyouCommonDialog = new MikyouCommonDialog(mContext,"您确定要申请退款吗？","提示","确认","取消",false);
                mikyouCommonDialog.setOnDiaLogListener(new MikyouCommonDialog.OnDialogListener() {
                    @Override
                    public void dialogListener(int btnType, View customView, DialogInterface dialogInterface, int which) {
                        if (btnType == MikyouCommonDialog.OK){

                        }
                        dialogInterface.dismiss();
                    }
                });
                mikyouCommonDialog.showDialog();
            }
        });


        shareLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        popupWindow.setContentView(contentView);

        int[] locations = new int[2];
        view.getLocationOnScreen(locations);
        popupWindow.showAtLocation(view, Gravity.NO_GRAVITY,view.getWidth()*6, (int) (locations[1]-view.getHeight()*1.5));

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

    }



    private static class ConsignmentAdpater extends RecyclerView.Adapter<ConsignmentAdpater.ViewHolder>{
        private Context mContext;

        private OnItemViewClickListener mOnItemViewClickListener;

        public void setOnItemViewClickListener(OnItemViewClickListener onItemViewClickListener) {
            mOnItemViewClickListener = onItemViewClickListener;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            mContext = parent.getContext();
            View view = LayoutInflater.from(mContext).inflate(R.layout.layout_item_consignment,parent,false);
            ViewHolder holder = new ViewHolder(view);
            ViewUtils.inject(holder,view);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {


            holder.moreImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemViewClickListener != null){
                        mOnItemViewClickListener.onItemViewClick(v,holder.getLayoutPosition());
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return 5;
        }

        public static class ViewHolder extends RecyclerView.ViewHolder{

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
