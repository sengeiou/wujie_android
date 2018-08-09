package com.txd.hzj.wjlp.mellOnLine;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ants.theantsgo.gson.GsonUtil;
import com.ants.theantsgo.util.L;
import com.ants.theantsgo.util.ListUtils;
import com.ants.theantsgo.util.PreferencesUtils;
import com.ants.theantsgo.view.pulltorefresh.PullToRefreshBase;
import com.ants.theantsgo.view.pulltorefresh.PullToRefreshGridView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.bean.CFGoodsList;
import com.txd.hzj.wjlp.bean.search.SeaechBean;
import com.txd.hzj.wjlp.http.goods.GoodsPst;
import com.txd.hzj.wjlp.mellOnLine.adapter.SearchGoodsAdapter;
import com.txd.hzj.wjlp.mellOnLine.gridClassify.TicketGoodsDetialsAty;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 作者：DUKE_HwangZj
 * 日期：2017/7/6 0006
 * 时间：下午 3:58
 * 描述：商品列表(1-4搜索结果1)
 */
public class GoodsListAty extends BaseAty {
    @ViewInject(R.id.search_title_be_back_iv)
    public ImageView search_title_be_back_iv;

    @ViewInject(R.id.title_search_tv)
    public TextView title_search_tv;

    @ViewInject(R.id.search_lin_layout)
    public LinearLayout search_lin_layout;

    @ViewInject(R.id.search_title_right_tv)
    public TextView search_title_right_tv;
    @ViewInject(R.id.search_type_tv)
    public TextView search_type_tv;

    @ViewInject(R.id.message_layout)
    public FrameLayout message_layout;

    @ViewInject(R.id.title_search_ev)
    public EditText title_search_ev;


    //积分
    @ViewInject(R.id.internal_tv)
    private TextView internal_tv;
    //代金券
    @ViewInject(R.id.cash_coupon_tv)
    private TextView cash_coupon_tv;
    //销量
    @ViewInject(R.id.sales_volume_tv)
    private TextView sales_volume_tv;
    //价格
    @ViewInject(R.id.price_tv)
    private TextView price_tv;

    @ViewInject(R.id.search_goods_gv)
    private PullToRefreshGridView search_goods_gv;

    @ViewInject(R.id.no_data_layout)
    private LinearLayout no_data_layout;

    private String type = "";
    private String keyword = "";
    private String his_str;
    private StringBuilder sb;

    private GoodsPst goodsPst;

    private SearchGoodsAdapter searchGoodsAdapter;
    private int p = 1;
    private int allNum = 0;
    private List<CFGoodsList> data;

    private String redColor = "#ffe71f19";
    private String blgColor = "#ff333333";
    private Drawable selectId;
    private Drawable twoSelectId;
    private Drawable unSelectId;
    private int internalNum = 0;
    private int cashCouponNum = 0;
    private int salesVolumeNum = 0;
    private int priceNum = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        forTitle();
        showStatusBar(R.id.search_title_layout);

        search_goods_gv.setEmptyView(no_data_layout);

        search_goods_gv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<GridView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<GridView> refreshView) {
                p = 1;
                goodsPst.search("1", keyword, p, false);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<GridView> refreshView) {
                p++;
                if (allNum <= data.size()) {
                    search_goods_gv.onRefreshComplete();
                    return;
                }
                goodsPst.search("1", keyword, p, false);
            }
        });

        search_goods_gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // 票券区详情
                Bundle bundle = new Bundle();
                bundle.putString("ticket_buy_id", data.get(i ).getGoods_id());
                bundle.putInt("from", 1);
                startActivity(TicketGoodsDetialsAty.class, bundle);
            }
        });

        forKeyboardSearch();
    }

    private void forTitle() {
        search_title_be_back_iv.setVisibility(View.VISIBLE);
        title_search_tv.setVisibility(View.GONE);
        search_lin_layout.setVisibility(View.VISIBLE);
        message_layout.setVisibility(View.GONE);
        search_title_right_tv.setVisibility(View.VISIBLE);
        title_search_ev.setText(keyword);
        search_type_tv.setText(type);
    }

    /**
     * 键盘搜索
     */
    private void forKeyboardSearch() {
        title_search_ev.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_SEARCH) {
                    forSearch();
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    @OnClick({R.id.search_title_right_tv,R.id.search_type_tv,R.id.internal_tv,R.id.cash_coupon_tv,R.id.sales_volume_tv,R.id.price_tv})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.search_title_right_tv:
                forSearch();
                break;
            case R.id.search_type_tv:
                finish();
                break;
            case R.id.internal_tv:
                setChioceItem(0);
                break;
            case R.id.cash_coupon_tv:
                setChioceItem(1);
                break;
            case R.id.sales_volume_tv:
                setChioceItem(2);
                break;
            case R.id.price_tv:
                setChioceItem(3);
                break;
        }
    }

    private void setChioceItem(int index) {
        clearChioce();
        if (index == 0) {
            internal_tv.setTextColor(Color.parseColor(redColor));
            internal_tv.setCompoundDrawables(null, null, internalNum % 2 == 0 ? selectId : twoSelectId, null);
            internalNum++;
            cashCouponNum = 0;
            salesVolumeNum = 0;
            priceNum = 0;
        } else if (index == 1) {
            cash_coupon_tv.setTextColor(Color.parseColor(redColor));
            cash_coupon_tv.setCompoundDrawables(null, null, cashCouponNum % 2 == 0 ? selectId : twoSelectId, null);

            internalNum = 0;
            cashCouponNum++;
            salesVolumeNum = 0;
            priceNum = 0;
        } else if (index == 2) {
            sales_volume_tv.setTextColor(Color.parseColor(redColor));
            sales_volume_tv.setCompoundDrawables(null, null, salesVolumeNum % 2 == 0 ? selectId : twoSelectId, null);
            internalNum = 0;
            cashCouponNum = 0;
            salesVolumeNum++;
            priceNum = 0;
        } else if (index == 3) {
            price_tv.setTextColor(Color.parseColor(redColor));
            price_tv.setCompoundDrawables(null, null, priceNum % 2 == 0 ? selectId : twoSelectId, null);
            internalNum = 0;
            cashCouponNum = 0;
            salesVolumeNum = 0;
            priceNum++;
        }
    }

    private void clearChioce() {
        internal_tv.setTextColor(Color.parseColor(blgColor));
        internal_tv.setCompoundDrawables(null, null, unSelectId, null);
        cash_coupon_tv.setTextColor(Color.parseColor(blgColor));
        cash_coupon_tv.setCompoundDrawables(null, null, unSelectId, null);
        sales_volume_tv.setTextColor(Color.parseColor(blgColor));
        sales_volume_tv.setCompoundDrawables(null, null, unSelectId, null);
        price_tv.setTextColor(Color.parseColor(blgColor));
        price_tv.setCompoundDrawables(null, null, unSelectId, null);
    }

    // 搜索
    private void forSearch() {
        hideKeyBoard();
        String key = title_search_ev.getText().toString();
        if (key.equals("")) {
            showErrorTip("请输入搜索关键词");
            return;
        }
        his_str = PreferencesUtils.getString(this, "onlinehistory", "");
        if (!his_str.contains(key)) {
            sb = new StringBuilder();
            sb.append(key).append(",").append(his_str);
            PreferencesUtils.putString(this, "onlinehistory", sb.toString());
        }
        p = 1;
        keyword = key;
        requestData();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_goods_list;
    }

    @Override
    protected void initialized() {
        type = getIntent().getStringExtra("type");
        keyword = getIntent().getStringExtra("keyword");
        goodsPst = new GoodsPst(this);
        data = new ArrayList<>();
        selectId = getResources().getDrawable(R.drawable.shopjiantou);
        twoSelectId = getResources().getDrawable(R.drawable.shop_red_down);
        unSelectId = getResources().getDrawable(R.drawable.shopblgjiantou);
        selectId.setBounds(0, 0, selectId.getMinimumWidth(), selectId.getMinimumHeight());
        twoSelectId.setBounds(0, 0, selectId.getMinimumWidth(), selectId.getMinimumHeight());
        unSelectId.setBounds(0, 0, unSelectId.getMinimumWidth(), unSelectId.getMinimumHeight());
    }

    @Override
    protected void requestData() {
        goodsPst.search("1", keyword, p, true);
    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        if (requestUrl.contains("search")) {
            SeaechBean goods = GsonUtil.GsonToBean(jsonStr, SeaechBean.class);
            L.e("商品=====解析数据=====", goods.toString());
            L.e("商品=====解析数据=====", jsonStr);
            allNum = goods.getNums();
            if (1 == p) {
                data = goods.getData().getList();
                if (!ListUtils.isEmpty(data)) {
                    // 适配器初始化
                    searchGoodsAdapter = new SearchGoodsAdapter(data, this);
                    search_goods_gv.setAdapter(searchGoodsAdapter);
                }
            } else {
                List<CFGoodsList> data2 = goods.getData().getList();
                if (!ListUtils.isEmpty(data2)) {
                    data.addAll(data2);
                    searchGoodsAdapter.notifyDataSetChanged();
                }
            }
            search_goods_gv.onRefreshComplete();
        }
    }

    @Override
    public void onError(String requestUrl, Map<String, String> error) {
        super.onError(requestUrl, error);
        if (requestUrl.contains("search")) {
            search_goods_gv.onRefreshComplete();
        }
    }
}
