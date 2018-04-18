package com.txd.hzj.wjlp.minetoAty.address;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ants.theantsgo.tips.MikyouCommonDialog;
import com.ants.theantsgo.tool.ToolKit;
import com.ants.theantsgo.util.JSONUtils;
import com.ants.theantsgo.view.pulltorefresh.PullToRefreshBase;
import com.ants.theantsgo.view.pulltorefresh.PullToRefreshListView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.http.address.AddressPst;

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
    private Map<String, String> defaultAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("收货地址");


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
    @OnClick({R.id.add_address_tv, R.id.edit_address_tv, R.id.delete_address_tv, R.id.default_address_layout})
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
                new MikyouCommonDialog(AddressListAty.this, "确定要删除地址吗?", "提示", "删除", "取消", true).setOnDiaLogListener
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
            case R.id.default_address_layout:
                if (2 == type) {// 地址选择
                    Intent intent = new Intent();
                    intent.putExtra("phone", defaultAddress.get("phone"));
                    intent.putExtra("receiver", defaultAddress.get("receiver"));
                    intent.putExtra("ads", defaultAddress.get("province") + defaultAddress.get("city") +
                            defaultAddress.get("area") + defaultAddress.get("address"));
                    intent.putExtra("id", defaultAddress_id);
                    setResult(RESULT_OK, intent);
                    finish();
                }
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
                    defaultAddress = JSONUtils.parseKeyAndValueToMap(map.get("default_address"));
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
                } else {
                    if (addressAdapter != null) {
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
//            if (toOperation >= 0 && toOperation < addresses.size()) {
            showRightTip("删除成功");
//                p = 1;
            addressPst.addressList(p, false);
//            }
        }

    }


    @Override
    public void onError(String requestUrl, Map<String, String> error) {
        super.onError(requestUrl, error);
        address_lv.onRefreshComplete();
        if (1 == p) {
            addresses.clear();
            if (addressAdapter != null) {
                addressAdapter.notifyDataSetChanged();
            }
        }
    }


    class AddressAdapter extends BaseAdapter {

        private Context context;
        private List<Map<String, String>> address;
        private LayoutInflater inflater;
        private AddressAdapter.AVH avh;


        public AddressAdapter(Context context, List<Map<String, String>> address) {
            this.context = context;
            this.address = address;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return address.size();
        }

        @Override
        public Map<String, String> getItem(int i) {
            return address.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            Map<String, String> ad = getItem(i);
            if (view == null) {
                view = inflater.inflate(R.layout.item_address_hzj_lv, null);
                avh = new AddressAdapter.AVH();
                ViewUtils.inject(avh, view);
                view.setTag(avh);
            } else {
                avh = (AddressAdapter.AVH) view.getTag();
            }
            avh.address_status_iv.setImageResource(R.drawable.icon_un_default_address);
            avh.address_defailt_tv.setText("设为默认");
            avh.address_defailt_tv.setTextColor(ContextCompat.getColor(context, R.color.gray_text_color));
            avh.under_address_iv.setVisibility(View.GONE);
            final String address_id = ad.get("address_id");
            avh.root_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (2 == type) {// 地址选择
                        Map<String, String> ad = getItem(i);
                        String phone = ad.get("phone");
                        String receiver = ad.get("receiver");
                        String ads = ad.get("province") + ad.get("city") + ad.get("area") + ad.get("street") +
                                ad.get("address");
                        String a_id = ad.get("address_id");
                        Intent intent = new Intent();
                        intent.putExtra("phone", phone);
                        intent.putExtra("receiver", receiver);
                        intent.putExtra("ads", ads);
                        intent.putExtra("id", a_id);
                        setResult(RESULT_OK, intent);
                        finish();
                    }

                }
            });

            // 设置为默认地址
            avh.set_address_to_default_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    addressPst.setDefault(address_id);
                }
            });
            // 编辑
            avh.edit_address_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    bundle = new Bundle();
                    bundle.putInt("type", 1);
                    bundle.putString("address_id", address_id);
                    startActivity(AddNewAddressAty2.class, bundle);
                }
            });
            // 删除
            avh.delete_address_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new MikyouCommonDialog(AddressListAty.this, "确定要删除地址吗?", "提示", "删除", "取消", true).setOnDiaLogListener
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
                }
            });


            avh.add_name_tv.setText(ad.get("receiver"));
            avh.add_phone_tv.setText(ad.get("phone"));
            avh.add_details_tv.setText(ad.get("province") + ad.get("city") + ad.get("area") + ad.get("street") +
                    ad.get("address"));

            return view;
        }


        class AVH {
            @ViewInject(R.id.root_layout)
            private LinearLayout root_layout;
            /**
             * 设置为默认地址布局
             */
            @ViewInject(R.id.set_address_to_default_layout)
            private LinearLayout set_address_to_default_layout;
            /**
             * 默认地址。非默认地址
             */
            @ViewInject(R.id.address_status_iv)
            private ImageView address_status_iv;
            /**
             * 默认地址。非默认地址
             */
            @ViewInject(R.id.address_defailt_tv)
            private TextView address_defailt_tv;
            /**
             * 编辑
             */
            @ViewInject(R.id.edit_address_tv)
            private TextView edit_address_tv;
            /**
             * 删除
             */
            @ViewInject(R.id.delete_address_tv)
            private TextView delete_address_tv;

            /**
             * 默认地址显示的线
             */
            @ViewInject(R.id.under_address_iv)
            private View under_address_iv;

            /**
             * 姓名
             */
            @ViewInject(R.id.add_name_tv)
            private TextView add_name_tv;

            /**
             * 电话
             */
            @ViewInject(R.id.add_phone_tv)
            private TextView add_phone_tv;

            /**
             * 地址
             */
            @ViewInject(R.id.add_details_tv)
            private TextView add_details_tv;

        }
    }
}
