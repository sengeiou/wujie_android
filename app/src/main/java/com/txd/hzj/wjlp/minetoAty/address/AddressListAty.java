package com.txd.hzj.wjlp.minetoAty.address;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ants.theantsgo.gson.GsonUtil;
import com.ants.theantsgo.listenerForAdapter.AdapterTextViewClickListener;
import com.ants.theantsgo.tips.MikyouCommonDialog;
import com.ants.theantsgo.view.pulltorefresh.PullToRefreshBase;
import com.ants.theantsgo.view.pulltorefresh.PullToRefreshListView;
import com.google.gson.Gson;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.bean.AddressList;
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

    private List<AddressList.Address> addresses;
    private List<AddressList.Address> addresses2;
    private AddressAdapter addressAdapter;

    /**
     * 来源
     * 1.个人中心
     * 2.
     */
    private int type;

    private AddressPst addressPst;

    private int p = 1;

    private int all_num = 0;
    private Bundle bundle;
    private int toOperation = -1;

    @ViewInject(R.id.no_data_layout)
    private LinearLayout no_data_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("收货地址");

        address_lv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                p = 1;
                addressPst.addressList(p);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                if (all_num >= addresses.size()) {
                    address_lv.onRefreshComplete();
                    return;
                }
                p++;
                addressPst.addressList(p);
            }
        });

        address_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            }
        });
        address_lv.setEmptyView(no_data_layout);
    }

    @Override
    @OnClick(R.id.add_address_tv)
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.add_address_tv:// 新增地址
                bundle = new Bundle();
                bundle.putInt("type", 0);
                startActivity(AddNewAddressAty.class, bundle);
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
        addresses2 = new ArrayList<>();

    }

    @Override
    protected void requestData() {
    }

    @Override
    protected void onResume() {
        super.onResume();
        addressPst.addressList(p);
    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        if (requestUrl.contains("addressList")) {
            AddressList addressList = GsonUtil.GsonToBean(jsonStr, AddressList.class);
            all_num = addressList.getNums();
            if (1 == p) {

                addresses.clear();// 清除掉之前的数据

                addresses = addressList.getData();
                addressAdapter = new AddressAdapter(this, addresses);
                address_lv.setAdapter(addressAdapter);
                toOperationAddress();
            } else {
                addresses2 = addressList.getData();
                addresses.addAll(addresses2);
                addressAdapter.notifyDataSetChanged();
            }
            address_lv.onRefreshComplete();
            return;
        }
        if (requestUrl.contains("setDefault")) {
            p = 1;
            addressPst.addressList(p);
            return;
        }
        if (requestUrl.contains("delAddress")) {
            if (toOperation >= 0 && toOperation < addresses.size()) {
                showRightTip("删除成功");
                addresses.remove(toOperation);
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
                final String address_id = addresses.get(position).getAddress_id();
                toOperation = position;
                switch (v.getId()) {
                    case R.id.set_address_to_default_layout://设为默认地址
                        addressPst.setDefault(address_id);
                        break;
                    case R.id.edit_address_tv://编辑
                        bundle = new Bundle();
                        bundle.putInt("type", 1);
                        bundle.putString("address_id", address_id);
                        startActivity(AddNewAddressAty.class, bundle);
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
        address_lv.onRefreshComplete();
    }
}
