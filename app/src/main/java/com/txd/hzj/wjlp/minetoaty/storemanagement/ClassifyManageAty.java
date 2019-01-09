package com.txd.hzj.wjlp.minetoaty.storemanagement;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ants.theantsgo.tips.MikyouCommonDialog;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.tool.MessageEvent;

import org.greenrobot.eventbus.EventBus;

/**
 * 创建者：zhangyunfei
 * 创建时间：2019/1/7 16:13
 * 功能描述：
 */
public class ClassifyManageAty extends BaseAty {
    @ViewInject(R.id.titlt_conter_tv)
    private TextView titlt_conter_tv;

    @ViewInject(R.id.time_select_img)
    private ImageView time_select_img;

    @ViewInject(R.id.recyclerView)
    private RecyclerView recyclerView;

    @ViewInject(R.id.editLayout)
    private LinearLayout editLayout;

    @ViewInject(R.id.titleEdit)
    private EditText titleEdit;

    @ViewInject(R.id.numEdit)
    private EditText numEdit;

    @ViewInject(R.id.briefEdit)
    private EditText briefEdit;

    @ViewInject(R.id.addClassifyTv)
    private TextView addClassifyTv;


    private ClassifyAdpater mClassifyAdpater;

    private Context mContext;
    private boolean mIsReturn;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_classify_manage;
    }

    @Override
    protected void initialized() {
        mContext = this;
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("分类管理");
        time_select_img.setVisibility(View.VISIBLE);
        time_select_img.setImageResource(R.drawable.icon_trash);
        mIsReturn = getIntent().getBooleanExtra("isReturn", false);
    }

    @Override
    protected void requestData() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        mClassifyAdpater = new ClassifyAdpater(new ClassifyAdpater.OnItemViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                setType();
            }
        }, new ClassifyAdpater.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (mIsReturn){
                    EventBus.getDefault().post(new MessageEvent("哈哈"));
                    finish();
                }else {
                    mClassifyAdpater.setSelectPosition(position);
                }

            }
        });
        recyclerView.setAdapter(mClassifyAdpater);
    }

    @Override
    @OnClick({R.id.time_select_img, R.id.addClassifyTv})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.time_select_img:
                MikyouCommonDialog dialog = new MikyouCommonDialog(mContext, "确定要删除此分类？", "提示", "取消", "确认", true);
                dialog.setOnDiaLogListener(new MikyouCommonDialog.OnDialogListener() {
                    @Override
                    public void dialogListener(int btnType, View customView, DialogInterface dialogInterface, int which) {
                        if (btnType == MikyouCommonDialog.NO){
                            showToast("即可将考虑");
                        }
                    }
                });
                dialog.showDialog();
                break;
            case R.id.addClassifyTv:
                setType();
                break;

        }
    }

    private void setType() {
        if (addClassifyTv.getText().toString().equals("添加分类")){
            recyclerView.setVisibility(View.GONE);
            editLayout.setVisibility(View.VISIBLE);
            addClassifyTv.setText("保存");
        }else {
            recyclerView.setVisibility(View.VISIBLE);
            editLayout.setVisibility(View.GONE);
            addClassifyTv.setText("添加分类");
        }
    }


    public static class  ClassifyAdpater extends RecyclerView.Adapter<ClassifyAdpater.ViewHolder>{

        private int selectPosition = -1;

        private OnItemViewClickListener mOnItemViewClickListener;
        private OnItemClickListener mOnItemClickListener;

        public ClassifyAdpater(OnItemViewClickListener onItemViewClickListener, OnItemClickListener onItemClickListener) {
            mOnItemViewClickListener = onItemViewClickListener;
            mOnItemClickListener = onItemClickListener;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_classify_manage,parent,false);
            ViewHolder viewHolder = new ViewHolder(view);
            ViewUtils.inject(viewHolder,view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
            if (selectPosition == position){
                holder.itemView.setBackgroundColor(Color.parseColor("#EEEEEE"));
            }else {
                holder.itemView.setBackgroundColor(Color.parseColor("#FFFFFF"));
            }
            holder.editTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemViewClickListener != null){
                        mOnItemViewClickListener.onClick(v,position);
                    }
                }
            });

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null){
                        mOnItemClickListener.onItemClick(position);
                    }
                }
            });
        }

        public void setSelectPosition(int position){
            selectPosition = position;
            notifyDataSetChanged();
        }

        @Override
        public int getItemCount() {
            return 3;
        }

        public static class ViewHolder extends RecyclerView.ViewHolder{
            @ViewInject(R.id.titleTv)
            TextView titleTv;
            @ViewInject(R.id.editTv)
            TextView editTv;
            public ViewHolder(View itemView) {
                super(itemView);
            }
        }

        public interface OnItemViewClickListener{
            void onClick(View view,int position);
        }
        public interface OnItemClickListener{
            void onItemClick(int position);
        }
    }


}
