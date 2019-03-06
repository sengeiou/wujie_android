package com.txd.hzj.wjlp.catchDoll.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.catchDoll.bean.AppealBean;

import java.util.List;

/**
 * 创建者：voodoo_jie
 * <br>创建时间：2018/09/20 020上午 11:51
 * <br>功能描述：申诉原因列表适配器
 */
public class AppealAdapter extends RecyclerView.Adapter<AppealAdapter.ViewHolder> {

    private Context context;
    private List<AppealBean> list;

    public AppealAdapter(Context context, List<AppealBean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_appeal, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        AppealBean appealBean = list.get(position);

        holder.itemAppeal_show_tv.setText(appealBean.getCauseStr());
        holder.itemAppeal_check_cb.setChecked(appealBean.isChecked());

        holder.itemAppeal_check_cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.itemView.performClick(); // 防止多选，直接将复选框按钮点击事件传递给列表项点击事件
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemSelectListener != null) {
                    onItemSelectListener.selectItem(position);
                }
                for (int i = 0; i < list.size(); i++) {
                    list.get(i).setChecked(i == position);
                }
                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView itemAppeal_show_tv;
        CheckBox itemAppeal_check_cb;

        public ViewHolder(View itemView) {
            super(itemView);
            itemAppeal_show_tv = itemView.findViewById(R.id.itemAppeal_show_tv);
            itemAppeal_check_cb = itemView.findViewById(R.id.itemAppeal_check_cb);
        }
    }

    private OnItemSelectListener onItemSelectListener;

    public void setOnItemSelectListener(OnItemSelectListener onItemSelectListener) {
        this.onItemSelectListener = onItemSelectListener;
    }

    public interface OnItemSelectListener {
        /**
         * 选中之后返回索引
         *
         * @param position 选中的索引
         */
        void selectItem(int position);
    }
}
