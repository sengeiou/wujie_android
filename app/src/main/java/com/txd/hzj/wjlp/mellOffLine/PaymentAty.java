package com.txd.hzj.wjlp.mellOffLine;

import android.graphics.Color;
import android.text.Html;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.tool.CommonPopupWindow;
import com.txd.hzj.wjlp.view.keyboard.MyKeyboard;

/**
 * 创建者：voodoo_jie
 * 创建时间：2018/07/20 020 下午 15:19
 * 功能描述：线下店铺支付结果展示页面
 */
public class PaymentAty extends BaseAty implements MyKeyboard.OnOkClick, View.OnClickListener {

    @ViewInject(R.id.offlinePay_meny_et)
    private EditText offlinePay_meny_et;

    private MyKeyboard myKeyboard;

    private CommonPopupWindow commonPopupWindow; // 代金券选择弹窗
    private CheckBox r; // 红券复选框
    private CheckBox y; // 黄券复选框
    private CheckBox b; // 蓝券复选框
    private boolean rChecked = false; // 红券是否选中
    private boolean yChecked = false; // 黄券是否选中
    private boolean bChecked = false; // 蓝券是否选中

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_offline_pay;
    }

    @Override
    protected void initialized() {
        // 获取到keyboard对象
        myKeyboard = new MyKeyboard(this);
        myKeyboard.setOnOkClick(this); // 确定按钮点击
        offlinePay_meny_et.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myKeyboard.attachTo(offlinePay_meny_et, false); // eiditext绑定keyboard，false表示普通数字键盘
            }
        });
        offlinePay_meny_et.setSelection(offlinePay_meny_et.getText().toString().length());
    }

    @Override
    protected void requestData() {
    }

    @Override
    public void onOkClick() {
        // 软键盘上确定按钮点击
        String meny = offlinePay_meny_et.getText().toString();
        if (!meny.equals("")) { // 获取输入的字符串，如果字符串不为空
            try { // 将输入的字符串转换为Float类型
                Float menyF = Float.parseFloat(meny);
                if (menyF > 0.00) { // 如果输入金额正确且大于0
                    Toast.makeText(this, offlinePay_meny_et.getText().toString(), Toast.LENGTH_SHORT).show();
                    showPop(getWindow().getDecorView());
                } else { // 否则输入的金额小于0或等于0
                    Toast.makeText(this, "输入有效金额", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) { // 类型转换异常，说明有其他字符，提示其自行检查重新输入
                Toast.makeText(this, "输入有误，请检查", Toast.LENGTH_SHORT).show();
            }
        } else { // 否则字符串为空，直接提示
            Toast.makeText(this, "请输入金额", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 显示代金券选择弹窗
     *
     * @param view
     */
    public void showPop(View view) {
//        if (order == null || (order.get("discount").equals("0") && order.get("yellow_discount").equals("0") && order.get("blue_discount").equals("0"))){
//            如果没有可用代金券，直接返回，不显示弹窗
//            return;
//        }
        if (commonPopupWindow != null && commonPopupWindow.isShowing()) {
            // 如果弹窗不为空并且已显示，则直接返回
            return;
        }

        commonPopupWindow = new CommonPopupWindow.Builder(this)
                .setView(R.layout.popup_layout_djq)
                .setWidthAndHeight(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                .setBackGroundLevel(0.5f)
                .setViewOnclickListener(new CommonPopupWindow.ViewInterface() {
                    @Override
                    public void getChildView(View view, int layoutResId, int position) {
                        LinearLayout layout_cb = (LinearLayout) view.findViewById(R.id.layout_cb);
                        LinearLayout layout_tv = (LinearLayout) view.findViewById(R.id.layout_tv);
                        layout_cb.setVisibility(View.VISIBLE);
                        layout_tv.setVisibility(View.GONE);
                        r = (CheckBox) view.findViewById(R.id.cb_1);
                        y = (CheckBox) view.findViewById(R.id.cb_2);
                        b = (CheckBox) view.findViewById(R.id.cb_3);
                        // 弹出对话框的时候默认设置一下各个复选框的选中状态
                        r.setChecked(rChecked);
                        y.setChecked(yChecked);
                        b.setChecked(bChecked);
                        // 获取订单中可用代金券的描述，使用富文本进行显示 现在显示的是模拟数据
//                        r.setText(order.get("red_desc"));
//                        y.setText(order.get("yellow_desc"));
//                        b.setText(order.get("blue_desc"));
                        r.setText(Html.fromHtml("本订单可使用<font color=\"#EF7F2B\">50</font>红券抵扣<font color=\"#EF7F2B\">￥19.8</font>，您现在拥有<font color=\"#EF7F2B\">100</font>红券"));
                        y.setText(Html.fromHtml("本订单可使用<font color=\"#EF7F2B\">50</font>黄券抵扣<font color=\"#EF7F2B\">￥19.8</font>，您现在拥有<font color=\"#EF7F2B\">100</font>黄券"));
                        b.setText(Html.fromHtml("本订单可使用<font color=\"#EF7F2B\">50</font>蓝券抵扣<font color=\"#EF7F2B\">￥19.8</font>，您现在拥有<font color=\"#EF7F2B\">100</font>蓝券"));
                        TextView cancel = (TextView) view.findViewById(R.id.tv_cancel);
                        cancel.setText("不使用代金券");
                        cancel.setTextColor(Color.parseColor("#EF7F2B"));

                        // 设置各个券的显隐，如果没有则隐藏掉对应的优惠券信息 现在现实的是模拟数据
//                        r.setVisibility(order.get("discount").equals("1") ? View.VISIBLE : View.GONE);
//                        y.setVisibility(order.get("yellow_discount").equals("1") ? View.VISIBLE : View.GONE);
//                        b.setVisibility(order.get("blue_discount").equals("1") ? View.VISIBLE : View.GONE);
                        r.setVisibility(View.VISIBLE);
                        y.setVisibility(View.VISIBLE);
                        b.setVisibility(View.VISIBLE);
                        r.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                rChecked = true;
                                yChecked = false;
                                bChecked = false;
                                r.setChecked(rChecked);
                                y.setChecked(yChecked);
                                b.setChecked(bChecked);
                                Toast.makeText(PaymentAty.this, "红劵选中", Toast.LENGTH_SHORT).show();
                                commonPopupWindow.dismiss();
                            }
                        });
                        y.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                rChecked = false;
                                yChecked = true;
                                bChecked = false;
                                Toast.makeText(PaymentAty.this, "黄劵选中", Toast.LENGTH_SHORT).show();
                                commonPopupWindow.dismiss();
                            }
                        });
                        b.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                rChecked = false;
                                yChecked = false;
                                bChecked = true;
                                Toast.makeText(PaymentAty.this, "蓝劵选中", Toast.LENGTH_SHORT).show();
                                commonPopupWindow.dismiss();
                            }
                        });
                        cancel.setOnClickListener(new View.OnClickListener() {
                                                      @Override
                                                      public void onClick(View v) {
                                                          rChecked = false;
                                                          yChecked = false;
                                                          bChecked = false;
                                                          Toast.makeText(PaymentAty.this, "不使用代金券", Toast.LENGTH_SHORT).show();
                                                          commonPopupWindow.dismiss();
                                                      }
                                                  }
                        );
                    }
                }, 0)
                .setAnimationStyle(R.style.animbottom)
                .create();
        commonPopupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

    }

}
