package com.txd.hzj.wjlp.minetoaty.storemanagement;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;

/**
 * 创建者：zhangyunfei
 * 创建时间：2019/1/2 9:45
 * 功能描述：线下店铺的商品管理
 */
public class CommodityManagementAty extends BaseAty {
    @ViewInject(R.id.titlt_conter_tv)
    private TextView titlt_conter_tv;

    @ViewInject(R.id.leftRecyclerView)
    private RecyclerView leftRecyclerView;

    @ViewInject(R.id.rightRecyclerView)
    private RecyclerView rightRecyclerView;


    @ViewInject(R.id.nameTv)
    private TextView nameTv;


    @ViewInject(R.id.empty_layout)
    private LinearLayout empty_layout;

    @ViewInject(R.id.fenleiTv)
    private TextView fenleiTv;

    @ViewInject(R.id.lucaiLayout)
    private LinearLayout lucaiLayout;

    @ViewInject(R.id.guanliTv)
    private TextView guanliTv;
    private LeftAdapter mLeftAdapter;
    private RightAdapter mRightAdapter;


    @Override
    protected int getLayoutResId() {
        return R.layout.activity_commodity_management_melloffline;
    }

    @Override
    protected void initialized() {
        titlt_conter_tv.setText("商品管理");
        LinearLayoutManager leftLayoutManager = new LinearLayoutManager(this);
        LinearLayoutManager rightLayoutManager = new LinearLayoutManager(this);
        leftRecyclerView.setLayoutManager(leftLayoutManager);
        leftRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        rightRecyclerView.setLayoutManager(rightLayoutManager);
    }

    @Override
    protected void requestData() {
        mLeftAdapter = new LeftAdapter();
        mLeftAdapter.setOnItemClickListener(new LeftAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                mLeftAdapter.setSelectPosition(position);
            }
        });
        leftRecyclerView.setAdapter(mLeftAdapter);
        mRightAdapter = new RightAdapter();
        rightRecyclerView.setAdapter(mRightAdapter);
    }

    @Override
    @OnClick({R.id.fenleiTv, R.id.lucaiLayout, R.id.guanliTv})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.fenleiTv:
                startActivity(ClassifyManageAty.class, null);
                break;
            case R.id.lucaiLayout:
                startActivity(InputAty.class, null);
                break;
            case R.id.guanliTv:
                createPop(v);
                break;

        }
    }

    private void createPop(View v) {
        PopupWindow popupWindow = new PopupWindow(LinearLayout.LayoutParams.MATCH_PARENT, 150);
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        View view = LayoutInflater.from(this).inflate(R.layout.pop_melloffline_manage, null);
        popupWindow.setContentView(view);
        popupWindow.setOutsideTouchable(true);
        popupWindow.showAsDropDown(v, 0, 30);
    }

    public static class LeftAdapter extends RecyclerView.Adapter<LeftAdapter.ViewHolder> {

        private int selectPosition = 0;
        private OnItemClickListener mOnItemClickListener;

        public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
            mOnItemClickListener = onItemClickListener;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_left_text, parent, false);
            ViewHolder holder = new ViewHolder(view);
            ViewUtils.inject(holder, view);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
            //            holder.nameTv.setText();
            if (position == selectPosition) {
                holder.itemView.setBackgroundColor(Color.parseColor("#ffffffff"));
            } else {
                holder.itemView.setBackgroundColor(Color.parseColor("#ffeeeeee"));
            }
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null) {
                        mOnItemClickListener.onItemClick(holder.getLayoutPosition());
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return 5;
        }


        public void setSelectPosition(int position) {
            this.selectPosition = position;
            notifyDataSetChanged();
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {
            @ViewInject(R.id.nameTv)
            private TextView nameTv;

            public ViewHolder(View itemView) {
                super(itemView);
            }
        }


        public interface OnItemClickListener {
            void onItemClick(int position);
        }
    }

    public static class RightAdapter extends RecyclerView.Adapter<RightAdapter.ViewHolder> {
        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_right_layout, parent, false);
            ViewHolder holder = new ViewHolder(view);
            ViewUtils.inject(holder, view);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 5;
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {
            @ViewInject(R.id.nameTv)
            private TextView nameTv;

            public ViewHolder(View itemView) {
                super(itemView);
            }
        }
    }
}
