package com.txd.hzj.wjlp.minetoAty.balance;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ants.theantsgo.util.JSONUtils;
import com.ants.theantsgo.util.L;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.http.balance.BalancePst;
import com.txd.hzj.wjlp.http.user.UserPst;
import com.txd.hzj.wjlp.minetoAty.order.TextListAty;

import java.util.Map;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/18 0018
 * 时间：下午 7:49
 * 描述：添加银行卡
 * ===============Txunda===============
 */
public class AddBankCardAty extends BaseAty {
    @ViewInject(R.id.titlt_conter_tv)
    public TextView titlt_conter_tv;

    @ViewInject(R.id.card_type_tv)
    private TextView card_type_tv;

    private BalancePst balancePst;

    @ViewInject(R.id.card_name_tv)
    private TextView card_name_tv;
    @ViewInject(R.id.open_card_ev)
    private EditText open_card_ev;
    @ViewInject(R.id.card_num_ev)
    private EditText card_num_ev;
    @ViewInject(R.id.phone_ev)
    private EditText phone_ev;
    private String bank_type_id = "";
    private UserPst userPst;
    private String auth_status; // 个人认证状态 0 未认证 1认证中 2 已认证 3被拒绝
    private String comp_auth_status; // 企业认证状态 0 未认证 1认证中 2 已认证 3被拒绝
    private String authName;
    private String compAuthName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("添加银行卡");
    }

    @Override
    @OnClick({R.id.card_type_layout, R.id.add_bank_tv, R.id.card_name_tv})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.card_type_layout:
                Bundle bundle = new Bundle();
                bundle.putString("title", "银行卡");
                startActivityForResult(TextListAty.class, bundle, 100);
                break;
            case R.id.card_name_tv:
                if (auth_status.equals("2") && comp_auth_status.equals("2")) {
                    showSelectWindow(authName, compAuthName);
                }
                break;
            case R.id.add_bank_tv:// 添加银行卡
                String name = card_name_tv.getText().toString();
                String open_bank = open_card_ev.getText().toString();
                String card_number = card_num_ev.getText().toString();
                String phone = phone_ev.getText().toString();
                balancePst.addBank(name, bank_type_id, open_bank, card_number, phone);
                break;
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_add_bank_card;
    }

    @Override
    protected void initialized() {
        balancePst = new BalancePst(this);
        userPst = new UserPst(this);
        userPst.userInfo();
    }

    @Override
    protected void requestData() {

    }

    /**
     * 选择显示的用户
     */
    private void showSelectWindow(String realNameStr, String compAuthNameStr) {
        AlertDialog.Builder localBuilder = new AlertDialog.Builder(this);
        final String[] arrayOfString = {realNameStr, compAuthNameStr};
        localBuilder
                .setTitle("持卡人名称")
                .setIcon(R.mipmap.ic_launcher)
                .setCancelable(false) // 点击外侧不关闭对话框
                .setItems(arrayOfString, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {
                        card_name_tv.setText(arrayOfString[paramAnonymousInt]); // 选择选项之后直接设置控件值
                        paramAnonymousDialogInterface.dismiss();
                    }
                }).create().show();
    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        Map<String, String> map = JSONUtils.parseKeyAndValueToMap(jsonStr);
        if (requestUrl.contains("userInfo")) {
            L.e("wang", "jsonStr:" + jsonStr);
            Map<String, String> data = JSONUtils.parseKeyAndValueToMap(map.get("data"));
            auth_status = data.get("auth_status"); // 个人认证状态
            comp_auth_status = data.get("comp_auth_status"); // 企业认证状态
            if (auth_status.equals("2") && comp_auth_status.equals("2")) { // 如果个人和企业认证同时认证
                // 获取两个名称，并弹出选择框
                authName = data.get("auth_name");
                compAuthName = data.get("comp_auth_name");
                showSelectWindow(authName, compAuthName);
            } else if (auth_status.equals("2")) { // 否则的话只是个人认证，那么直接设置个人认证的名称
                card_name_tv.setText(data.get("auth_name"));
            }
        }
        if (requestUrl.contains("addBank")) {
            showRightTip("添加成功");
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null)
            return;
        if (RESULT_OK == resultCode) {
            if (100 == requestCode) {
                card_type_tv.setText(data.getStringExtra("card_type"));
                bank_type_id = data.getStringExtra("bank_type_id");

                open_card_ev.setText(""); // 清空开户行
                card_num_ev.setText(""); // 清空银行卡号
            }
        }
    }
}
