package com.txd.hzj.wjlp.mellOffLine;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.bean.offline.OffLineBean;

import java.util.List;

/**
 * 创建者：zhangyunfei
 * 创建时间：2018/8/20 11:22
 * 功能描述：
 */
public class ShopMallPop extends PopupWindow {
    private List<OffLineBean.NumsBean> mNumsBeanList;
    /**
     * 标题的集合
     */
    private List<String> title_list;

    /**
     * 图片的集合
     */
    private List<String> image_list;

    /**
     * 每个图标对应的type_id
     */
    private List<String> rec_type_id_list;

    private Context mContext;
    private RecyclerView left_recyclerView;
    private RecyclerView right_recyclerView;
    private Left_Adapter mLeft_adapter;
    private Right_Adapter mRight_adapter;

    private int mInt=0;

    private OnPopItemListener mOnPopItemListener;
    public interface  OnPopItemListener{
        void leftClick(String top_cate,int position );
        void rightClick(String little_cate);
    }

    public void setOnPopItemListener(OnPopItemListener onPopItemListener){
        this.mOnPopItemListener=onPopItemListener;
    }


    public ShopMallPop(Context context, List<String> title_list,List<String> image_list,List<String> rec_type_id_list,List<OffLineBean.NumsBean> numsBeanList) {
        super(context);
        this.mContext=context;
        this.title_list=title_list;
        this.image_list=image_list;
        this.rec_type_id_list=rec_type_id_list;
        this.mNumsBeanList=numsBeanList;
        initPop();
    }

    private void initPop() {
        setFocusable(true);
        setOutsideTouchable(true);
        setBackgroundDrawable(new ColorDrawable(0x000000));
        View view=View.inflate(mContext, R.layout.shop_mall_pop,null);
        left_recyclerView=view.findViewById(R.id.left_recyclerView);
        right_recyclerView=view.findViewById(R.id.right_recyclerView);
        left_recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        right_recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        right_recyclerView.addItemDecoration(new DividerItemDecoration(mContext,DividerItemDecoration.VERTICAL));
        mLeft_adapter = new Left_Adapter(mContext, title_list, image_list,  new Left_Adapter.OnLeftItemClickListener() {
            @Override
            public void leftClick(int position) {
                mInt=position;
                if (mOnPopItemListener!=null){
                    mOnPopItemListener.leftClick(rec_type_id_list.get(position),position);
                }
            }
        });
        left_recyclerView.setAdapter(mLeft_adapter);
        mRight_adapter = new Right_Adapter(mNumsBeanList, new Right_Adapter.OnRightItemClickListener() {
            @Override
            public void rightClick(int position) {

            }
        });

        mRight_adapter.setTitle(title_list.get(mInt));

        right_recyclerView.setAdapter(mRight_adapter);
        setContentView(view);
    }


    public void notifyRightData(List<OffLineBean.NumsBean> numsBeanList,String title){
        if (mRight_adapter!=null){
            mRight_adapter.setNumsBeanList(numsBeanList);
            mRight_adapter.setTitle(title);
            mRight_adapter.notifyDataSetChanged();
        }
    }

    public void setWidthAndHeight(int width,int height){
        if (width == 0 || height == 0) {
            //如果没设置宽高，默认是WRAP_CONTENT
            this.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
            this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        } else {
            this.setWidth(width);
            this.setHeight(height);
        }
    }


    /**
     * 显示popupWindow
     */
    public void showPopupWindow(View parent) {
        if (!this.isShowing()) {
            // 以下拉方式显示popupwindow
            this.showAsDropDown(parent, 0, 0);
        } else {
            this.dismiss();
        }
    }


    private static class Left_Adapter extends RecyclerView.Adapter<Left_Adapter.ViewHolder>{

        private Context mContext;

        private List<String> title_list;

        private List<String> image_list;


        private OnLeftItemClickListener mOnLeftItemClickListener;

        private int lastChoice=0;

        public interface OnLeftItemClickListener{
            void leftClick(int position);
        }

        public Left_Adapter(Context context,List<String> title_list, List<String> image_list, OnLeftItemClickListener onItemClickListener) {
            this.mContext=context;
            this.title_list = title_list;
            this.image_list = image_list;
            mOnLeftItemClickListener = onItemClickListener;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.shop_mall_left_item,parent,false);
            ViewHolder holder=new ViewHolder(v);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
            Glide.with(mContext).load(image_list.get(position)).asBitmap().into(holder.mImageView);
            holder.mTextView.setText(title_list.get(position));
            holder.itemView.setBackgroundResource(lastChoice==position?R.color.white:R.color.bg_color);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnLeftItemClickListener != null) {
                        mOnLeftItemClickListener.leftClick(position);
                    }

                    togglePosition(holder.getLayoutPosition());
                }
            });

        }

        public void togglePosition(int position) {
            if (lastChoice != position) {
                notifyItemChanged(lastChoice);
                lastChoice = position;
            }
            notifyItemChanged(position);
        }

        @Override
        public int getItemCount() {
            return title_list.size()>0?title_list.size():0;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            private ImageView mImageView;
            private TextView mTextView;
            public ViewHolder(View itemView) {
                super(itemView);
                mImageView=itemView.findViewById(R.id.left_img);
                mTextView=itemView.findViewById(R.id.left_title_tv);
            }
        }
    }

    private static class Right_Adapter extends RecyclerView.Adapter<Right_Adapter.ViewHolder>{
        private List<OffLineBean.NumsBean> mNumsBeanList;
        private OnRightItemClickListener mOnRightItemClickListener;
        private String title;

        public Right_Adapter(List<OffLineBean.NumsBean> numsBeanList, OnRightItemClickListener onRightItemClickListener) {
            mNumsBeanList = numsBeanList;
            mOnRightItemClickListener = onRightItemClickListener;
        }

        public interface OnRightItemClickListener{
            void rightClick(int position);
        }


        public void setNumsBeanList(List<OffLineBean.NumsBean> numsBeanList){
            this.mNumsBeanList=numsBeanList;
        }
        public void setTitle(String title){
            this.title=title;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.shop_mall_right_item,parent,false);
            Right_Adapter.ViewHolder holder=new Right_Adapter.ViewHolder(v);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull final ViewHolder holder,  int position) {
            if (position==0){
                holder.mTextView.setText("全部"+title);
            }else {
                holder.mTextView.setText(mNumsBeanList.get(position-1).getType());
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mOnRightItemClickListener!=null){
                            mOnRightItemClickListener.rightClick(holder.getLayoutPosition());
                        }
                    }
                });
            }
        }

        @Override
        public int getItemCount() {
            return mNumsBeanList.size()>0?mNumsBeanList.size()+1:0;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            private TextView mTextView;
            public ViewHolder(View itemView) {
                super(itemView);
                mTextView=itemView.findViewById(R.id.right_title_tv);
            }
        }
    }
}
