package com.txd.hzj.wjlp.minetoaty.storemanagement;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.view.CustomDialog;

/**
 * 创建者：zhangyunfei
 * 创建时间：2019/1/8 17:01
 * 功能描述：多规格
 */
public class MultipleSpecificationsAty extends BaseAty {
    private Context mContext;

    @ViewInject(R.id.titlt_conter_tv)
    private TextView titlt_conter_tv;

    @ViewInject(R.id.addTv)
    private TextView addTv;

    @ViewInject(R.id.recyclerView)
    private RecyclerView recyclerView;

    @ViewInject(R.id.saveTv)
    private TextView saveTv;

    private int num;
    private MyAdapter mAdapter;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_multiple_specifications;
    }

    @Override
    protected void initialized() {
        mContext=this;
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("多规格");
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new MyAdapter(new MyAdapter.OnViewClickLisener() {
            @Override
            public void onClick(final int pos) {
                CustomDialog customDialog = new CustomDialog.Builder(mContext)
                        .setIsShowTitle(false)
                        .setMessage("确定要删除此分类？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                mAdapter.notifyItemRemoved(pos);
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .create();
                customDialog.show();
            }
        });
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void requestData() {

    }


    @Override
    @OnClick({R.id.addTv ,R.id.saveTv})
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.addTv:
                num++;
                mAdapter.setCount(num);
                break;
            case R.id.saveTv:

                break;
        }
    }



    public static class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{
        private int count;
        private OnViewClickLisener mOnViewClickLisener;

        public MyAdapter(OnViewClickLisener onViewClickLisener) {
            mOnViewClickLisener = onViewClickLisener;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_specifications,parent,false);
            ViewHolder holder = new ViewHolder(view);
            ViewUtils.inject(holder,view);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
            holder.numTv.setText("规格"+(position+1));
            holder.deleteImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnViewClickLisener != null){
                        mOnViewClickLisener.onClick(position);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return count;
        }

        public void remove(int pos){
            notifyItemRemoved(pos);
        }
        public void setCount(int count){
            this.count = count;
            notifyDataSetChanged();
        }

        public static class ViewHolder extends RecyclerView.ViewHolder{
            @ViewInject(R.id.numTv)
            private TextView numTv;
            @ViewInject(R.id.deleteImg)
            private ImageView deleteImg;
            @ViewInject(R.id.nameEdit)
            private EditText nameEdit;
            @ViewInject(R.id.moneyEdit)
            private EditText moneyEdit;

            public ViewHolder(View itemView) {
                super(itemView);
            }
        }


        public interface  OnViewClickLisener{
            void onClick(int pos);
        }
    }
}
