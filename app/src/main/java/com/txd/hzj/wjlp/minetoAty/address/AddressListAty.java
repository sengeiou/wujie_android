package com.txd.hzj.wjlp.minetoAty.address;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ants.theantsgo.listenerForAdapter.AdapterTextViewClickListener;
import com.ants.theantsgo.tips.MikyouCommonDialog;
import com.ants.theantsgo.tool.ToolKit;
import com.ants.theantsgo.util.JSONUtils;
import com.ants.theantsgo.util.L;
import com.ants.theantsgo.view.pulltorefresh.PullToRefreshBase;
import com.ants.theantsgo.view.pulltorefresh.PullToRefreshListView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.http.address.AddressPst;
import com.txd.hzj.wjlp.minetoAty.address.adapter.AddressAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/20 0020
 * 时间：下午 7:55
 * 描述：收货地址
 * ===============Txunda===============
 */
public class AddressListAty extends BaseAty {

    @ViewInject(R.id.titlt_conter_tv)
    private TextView titlt_conter_tv;

    @ViewInject(R.id.address_lv)
    private PullToRefreshListView address_lv;

    private List<Map<String, String>> addresses;
    private AddressAdapter addressAdapter;

    /**
     * 来源
     * 1.个人中心
     * 2.地址选择
     */
    private int type;

    private AddressPst addressPst;

    private int p = 1;

    private Bundle bundle;
    private int toOperation = -1;

    @ViewInject(R.id.no_data_layout)
    private LinearLayout no_data_layout;

    /**
     * 默认地址收货人姓名
     */
    @ViewInject(R.id.add_name_tv)
    private TextView add_name_tv;
    /**
     * 默认地址收货人手机号
     */
    @ViewInject(R.id.add_phone_tv)
    private TextView add_phone_tv;

    /**
     * 默认地址详细地址
     */
    @ViewInject(R.id.add_details_tv)
    private TextView add_details_tv;

    /**
     * 默认地址布局
     */
    @ViewInject(R.id.default_address_layout)
    private LinearLayout default_address_layout;

    private String defaultAddress_id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("收货地址");
        address_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (2 == type) {// 地址选择

                }
            }
        });

        address_lv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                p = 1;
                addressPst.addressList(p, false);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                p++;
                addressPst.addressList(p, false);
            }
        });

        address_lv.setEmptyView(no_data_layout);
    }

    @Override
    @OnClick({R.id.add_address_tv, R.id.edit_address_tv, R.id.delete_address_tv})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.add_address_tv:// 新增地址
                bundle = new Bundle();
                bundle.putInt("type", 0);
                startActivity(AddNewAddressAty2.class, bundle);
                break;
            case R.id.edit_address_tv://编辑
                bundle = new Bundle();
                bundle.putInt("type", 1);
                bundle.putString("address_id", defaultAddress_id);
                startActivity(AddNewAddressAty2.class, bundle);
                break;
            case R.id.delete_address_tv://删除
                new MikyouCommonDialog(AddressListAty.this, "确定要删除地址吗?", "提示", "删除", "取消").setOnDiaLogListener
                        (new MikyouCommonDialog.OnDialogListener() {

                            @Override
                            public void dialogListener(int btnType, View customView, DialogInterface
                                    dialogInterface, int which) {
                                switch (btnType) {
                                    case MikyouCommonDialog.OK:
                                        addressPst.delAddress(defaultAddress_id);
                                        break;
                                    case MikyouCommonDialog.NO:
                                        break;
                                }
                            }
                        }).showDialog();
                break;
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_address_list;
    }

    @Override
    protected void initialized() {
        type = getIntent().getIntExtra("type", 1);
        addressPst = new AddressPst(this);
        addresses = new ArrayList<>();
    }

    @Override
    protected void requestData() {
    }

    @Override
    protected void onResume() {
        super.onResume();
        addressPst.addressList(p, true);
    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        if (requestUrl.contains("addressList")) {
            super.onComplete(requestUrl, jsonStr);
            Map<String, String> data = JSONUtils.parseKeyAndValueToMap(jsonStr);
            Map<String, String> map = JSONUtils.parseKeyAndValueToMap(data.get("data"));
            if (1 == p) {
                if (ToolKit.isList(map, "default_address")) {
                    Map<String, String> defaultAddress = JSONUtils.parseKeyAndValueToMap(map.get("default_address"));
                    if (ToolKit.isList(defaultAddress, "address_id")) {
                        defaultAddress_id = defaultAddress.get("address_id");
                        default_address_layout.setVisibility(View.VISIBLE);
                        add_name_tv.setText(defaultAddress.get("receiver"));
                        add_phone_tv.setText(defaultAddress.get("phone"));
                        add_details_tv.setText(defaultAddress.get("province") + defaultAddress.get("city") +
                                defaultAddress.get("area") + defaultAddress.get("address"));
                    } else {
                        default_address_layout.setVisibility(View.GONE);
                    }
                } else {
                    default_address_layout.setVisibility(View.GONE);
                }
                addresses.clear();// 清除掉之前的数据
                if (ToolKit.isList(map, "common_address")) {
                    addresses = JSONUtils.parseKeyAndValueToMapList(map.get("common_address"));
                    addressAdapter = new AddressAdapter(this, addresses);
                    address_lv.setAdapter(addressAdapter);
                    toOperationAddress();
                } else {
                    if(addressAdapter!=null){
                        addressAdapter.notifyDataSetChanged();
                    }
                }
            } else {
                if (ToolKit.isList(map, "common_address")) {
                    addresses.addAll(JSONUtils.parseKeyAndValueToMapList(map.get("common_address")));
                    addressAdapter.notifyDataSetChanged();
                }
            }
            address_lv.onRefreshComplete();
            return;
        }
        if (requestUrl.contains("setDefault")) {
            p = 1;
            addressPst.addressList(p, false);
            return;
        }
        if (requestUrl.contains("delAddress")) {
            if (toOperation >= 0 && toOperation < addresses.size()) {
                showRightTip("删除成功");
                p = 1;
                addressPst.addressList(p, false);
            }
        }

    }

    @Override
    public void onErrorTip(String tips) {
        super.onErrorTip(tips);
        address_lv.onRefreshComplete();
        if (1 == p) {
            addresses.clear();
            if (addressAdapter != null) {
                addressAdapter.notifyDataSetChanged();
            }
        }
    }

    /**
     * 数据操作
     */
    private void toOperationAddress() {
        addressAdapter.setAdapterTextViewClickListener(new AdapterTextViewClickListener() {
            @Override
            public void onTextViewClick(View v, int position) {
                final String address_id = addresses.get(position).get("address_id");
                toOperation = position;
                switch (v.getId()) {
                    case R.id.set_address_to_default_layout://设为默认地址
                        addressPst.setDefault(address_id);
                        break;
                    case R.id.edit_address_tv://编辑
                        bundle = new Bundle();
                        bundle.putInt("type", 1);
                        bundle.putString("address_id", address_id);
                        startActivity(AddNewAddressAty2.class, bundle);
                        break;
                    case R.id.delete_address_tv://删除
                        new MikyouCommonDialog(AddressListAty.this, "确定要删除地址吗?", "提示", "删除", "取消").setOnDiaLogListener
                                (new MikyouCommonDialog.OnDialogListener() {

                                    @Override
                                    public void dialogListener(int btnType, View customView, DialogInterface
                                            dialogInterface, int which) {
                                        switch (btnType) {
                                            case MikyouCommonDialog.OK:
                                                addressPst.delAddress(address_id);
                                                break;
                                            case MikyouCommonDialog.NO:
                                                break;
                                        }
                                    }
                                }).showDialog();
                        break;
                }
            }
        });
    }

    @Override
    public void onError(String requestUrl, Map<String, String> error) {
        super.onError(requestUrl, error);
    }
}
