package com.txd.hzj.wjlp.minetoaty.balance;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ants.theantsgo.base.BaseView;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.http.balance.BalancePst;

import java.util.ArrayList;

/**
 * by Txunda_LH on 2018/2/27.
 */

public class PtBaseAdapter extends BaseAdapter {

    Context context;
    ArrayList<PtEntity> alist;
    private final BalancePst balancePst;
    boolean isPlatform;

    /**
     * 适配器构造函数
     *
     * @param context    上下文
     * @param list       适配的列表
     * @param baseView   BaseView
     * @param isPlatform 是否是平台银行卡
     */
    public PtBaseAdapter(Context context, ArrayList<PtEntity> list, BaseView baseView, boolean isPlatform) {
        this.alist = list;
        this.context = context;
        balancePst = new BalancePst(baseView);
        this.isPlatform = isPlatform;
    }

    @Override
    public int getCount() {
        return alist.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if (convertView == null) {
            vh = new ViewHolder();
            convertView = View.inflate(context, R.layout.item_bank_info_lv, null);
            vh.create_card_bank_name_tv = convertView.findViewById(R.id.create_card_bank_name_tv);
            vh.bank_card_owner_name = convertView.findViewById(R.id.bank_card_owner_name);
            vh.bank_card_num_tv = convertView.findViewById(R.id.bank_card_num_tv);
            vh.bank_card_edit_tv = convertView.findViewById(R.id.bank_card_edit_tv); // 编辑银行卡
            vh.bank_card_delete_tv = convertView.findViewById(R.id.bank_card_delete_tv); // 删除银行卡
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        vh.bank_card_num_tv.setText(alist.get(position).getBank_card_code());
        vh.bank_card_owner_name.setText(alist.get(position).getName());
        vh.create_card_bank_name_tv.setText(alist.get(position).getOpen_bank());

        if (isPlatform) { // 如果是平台银行卡列表，则隐藏编辑和删除按钮
            vh.bank_card_edit_tv.setVisibility(View.GONE);
            vh.bank_card_delete_tv.setVisibility(View.GONE);
        } else { // 否则的话是加载的个人银行卡，显示这俩按钮并添加点击事件
            vh.bank_card_edit_tv.setVisibility(View.VISIBLE);
            vh.bank_card_delete_tv.setVisibility(View.VISIBLE);
            vh.bank_card_edit_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setClass(context, AddBankCardAty.class);
                    intent.putExtra("isEdit", true); // 是否是编辑状态
                    intent.putExtra("bank_card_id", alist.get(position).getBank_card_id());
                    intent.putExtra("bank_card_code", alist.get(position).getBank_card_code());
                    intent.putExtra("bank_name", alist.get(position).getBank_name());
                    intent.putExtra("open_bank", alist.get(position).getOpen_bank());
                    intent.putExtra("name", alist.get(position).getName());
                    intent.putExtra("phone", alist.get(position).getPhone());
                    intent.putExtra("bank_type_id", alist.get(position).getBank_type_id());
                    context.startActivity(intent);
                }
            });
            vh.bank_card_delete_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    balancePst.delBank(alist.get(position).getBank_card_id());
                }
            });
        }

        return convertView;
    }

    class ViewHolder {

        /**
         * 银行卡名称
         */
        private TextView create_card_bank_name_tv;

        /**
         * 持卡人
         */
        private TextView bank_card_owner_name;

        /**
         * 卡号
         */
        private TextView bank_card_num_tv;

        /**
         * 编辑
         */
        private TextView bank_card_edit_tv;

        /**
         * 删除
         */
        private TextView bank_card_delete_tv;
    }
}
