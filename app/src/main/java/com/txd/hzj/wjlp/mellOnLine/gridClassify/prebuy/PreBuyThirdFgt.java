package com.txd.hzj.wjlp.mellOnLine.gridClassify.prebuy;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.ants.theantsgo.gson.GsonUtil;
import com.ants.theantsgo.util.JSONUtils;
import com.ants.theantsgo.util.L;
import com.ants.theantsgo.util.ListUtils;
import com.ants.theantsgo.view.pulltorefresh.PullToRefreshBase;
import com.ants.theantsgo.view.pulltorefresh.PullToRefreshGridView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseFgt;
import com.txd.hzj.wjlp.bean.AllGoodsBean;
import com.txd.hzj.wjlp.http.country.CountryPst;
import com.txd.hzj.wjlp.http.integral.IntegralBuyPst;
import com.txd.hzj.wjlp.http.prebuy.PerBuyPst;
import com.txd.hzj.wjlp.http.ticketbuy.TicketBuyPst;
import com.txd.hzj.wjlp.mainFgt.adapter.AllGvLvAdapter;
import com.txd.hzj.wjlp.mellOnLine.adapter.WjMellAdapter;
import com.txd.hzj.wjlp.mellOnLine.gridClassify.InputGoodsDetailsAty;
import com.txd.hzj.wjlp.mellOnLine.gridClassify.LimitGoodsAty;
import com.txd.hzj.wjlp.mellOnLine.gridClassify.TicketGoodsDetialsAty;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/9/8 0008
 * 时间：13:56
<<<<<<< HEAD
 * 描述：无界预购三积分了商品列表碎片
=======
 * 描述：xfte预购三积分了商品列表碎片
>>>>>>> master
 * ===============Txunda===============
 */

public class PreBuyThirdFgt extends BaseFgt {
    private String two = "";
    private String three = "";

    private int type = 0;

    @ViewInject(R.id.pr_third_lv)
    private PullToRefreshGridView pr_third_lv;

    @ViewInject(R.id.no_data_layout)
    private LinearLayout no_data_layout;

    private List<AllGoodsBean> data;
    private List<AllGoodsBean> data2;
    /**
<<<<<<< HEAD
     * 无界预购
=======
     * xfte预购
>>>>>>> master
     */
    private PerBuyPst perBuyPst;
    /**
     * 票券区
     */
    private TicketBuyPst ticketBuyPst;
    /**
<<<<<<< HEAD
     * 无界商店
=======
     * xfte商店
>>>>>>> master
     */
    private IntegralBuyPst integralBuyPst;
    /**
     * 进口馆
     */
    private CountryPst countryPst;
    private int p = 1;
    private AllGvLvAdapter allGvLvAdapter1;
    private WjMellAdapter wjAdapter;

    private String country_id = "";

    public PreBuyThirdFgt() {
    }

    public static PreBuyThirdFgt getFgt(String two, String three, int type, String country_id) {
        PreBuyThirdFgt subClassifyListFgt = new PreBuyThirdFgt();
        subClassifyListFgt.two = two;
        subClassifyListFgt.three = three;
        subClassifyListFgt.type = type;
        subClassifyListFgt.country_id = country_id;
        return subClassifyListFgt;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        pr_third_lv.setEmptyView(no_data_layout);


        pr_third_lv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<GridView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<GridView> refreshView) {
                p = 1;
                forData();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<GridView> refreshView) {
                p++;
                forData();
            }
        });
        pr_third_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Bundle bundle = new Bundle();
                switch (type) {
                    case 1:// 票券区
                        bundle.putString("ticket_buy_id", data.get(i).getTicket_buy_id());
                        startActivity(TicketGoodsDetialsAty.class, bundle);
                        break;
<<<<<<< HEAD
                    case 2:// 无界预购
=======
                    case 2:// xfte预购
>>>>>>> master
                        bundle.putString("limit_buy_id", data.get(i).getPre_buy_id());
                        bundle.putInt("type", 2);
                        startActivity(LimitGoodsAty.class, bundle);
                        break;
<<<<<<< HEAD
                    case 10:// 无界商店
=======
                    case 10:// xfte商店
>>>>>>> master
                        bundle.putString("limit_buy_id", data.get(i).getIntegral_buy_id());
                        bundle.putInt("type", 10);
                        startActivity(LimitGoodsAty.class, bundle);
                        break;
                    case 3:// 进口馆
                        bundle.putString("ticket_buy_id", data.get(i).getGoods_id());
                        bundle.putInt("from",1);
                        startActivity(TicketGoodsDetialsAty.class, bundle);
                        break;
                }
            }
        });
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fgt_pre_buy_third;
    }

    @Override
    protected void initialized() {
        data = new ArrayList<>();
        data2 = new ArrayList<>();
        perBuyPst = new PerBuyPst(this);
        ticketBuyPst = new TicketBuyPst(this);
        integralBuyPst = new IntegralBuyPst(this);
        countryPst = new CountryPst(this);
    }

    @Override
    protected void requestData() {
        forData();
    }

    private void forData() {
        switch (type) {
            case 1:// 票券区
                ticketBuyPst.threeList(two, three, p);
                break;
<<<<<<< HEAD
            case 2:// 无界预购
=======
            case 2:// xfte预购
>>>>>>> master
                perBuyPst.threeList(two, p, three);
                break;
            case 3:// 进口馆
                countryPst.threeList(two, country_id, p, three);
                break;
<<<<<<< HEAD
            case 10:// 无界预购
=======
            case 10:// xfte预购
>>>>>>> master
                integralBuyPst.threeList(two, three, p);
                break;
        }
    }

    @Override
    protected void immersionInit() {

    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        if (requestUrl.contains("threeList")) {
            Map<String, String> map = JSONUtils.parseKeyAndValueToMap(jsonStr);
            Map<String, String> datajson = JSONUtils.parseKeyAndValueToMap(map.get("data"));
            L.e("=====数据=====", jsonStr);
            if (1 == p) {
                switch (type) {
                    case 1:// 票券区
                        data = GsonUtil.getObjectList(datajson.get("ticket_buy_list"), AllGoodsBean.class);
                        break;
<<<<<<< HEAD
                    case 2:// 无界预购
=======
                    case 2:// xfte预购
>>>>>>> master
                        data = GsonUtil.getObjectList(datajson.get("pre_buy_list"), AllGoodsBean.class);
                        break;
                    case 3:// 进口馆
                        data = GsonUtil.getObjectList(datajson.get("list"), AllGoodsBean.class);
                        break;
<<<<<<< HEAD
                    case 10:// 无界商店
=======
                    case 10:// xfte商店
>>>>>>> master
                        data = GsonUtil.getObjectList(datajson.get("integral_buy_list"), AllGoodsBean.class);
                        break;
                }
                if (!ListUtils.isEmpty(data)) {
                    L.e("Fgt=====type=====", String.valueOf(type));
                    if (10 == type) {
                        wjAdapter = new WjMellAdapter(getContext(), data);
                        pr_third_lv.setAdapter(wjAdapter);
                    } else {
                        allGvLvAdapter1 = new AllGvLvAdapter(getActivity(), data, type);
                        pr_third_lv.setAdapter(allGvLvAdapter1);
                    }
                }
            } else {
                switch (type) {
                    case 1:// 票券区
                        data2 = GsonUtil.getObjectList(datajson.get("ticket_buy_list"), AllGoodsBean.class);
                        break;
<<<<<<< HEAD
                    case 2:// 无界预购
=======
                    case 2:// xfte预购
>>>>>>> master
                        data2 = GsonUtil.getObjectList(datajson.get("pre_buy_list"), AllGoodsBean.class);
                        break;
                    case 3:// 进口馆
                        data = GsonUtil.getObjectList(datajson.get("list"), AllGoodsBean.class);
                        break;
<<<<<<< HEAD
                    case 10:// 无界商店
=======
                    case 10:// xfte商店
>>>>>>> master
                        data2 = GsonUtil.getObjectList(datajson.get("integral_buy_list"), AllGoodsBean.class);
                        break;
                }
                if (!ListUtils.isEmpty(data2)) {
                    data.addAll(data2);
                    if (10 == type)
                        wjAdapter.notifyDataSetChanged();
                    else
                        allGvLvAdapter1.notifyDataSetChanged();
                }
            }
            pr_third_lv.onRefreshComplete();
        }
    }
}
