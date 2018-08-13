package com.txd.hzj.wjlp.mellOnLine;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.TranslateAnimation;
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

    @ViewInject(R.id.pop_search_layout)
    private LinearLayout pop_search_layout;

    @ViewInject(R.id.lower_et)
    private EditText lower_et;

    @ViewInject(R.id.higher_et)
    private EditText higher_et;

    @ViewInject(R.id.lower_tv)
    private TextView lower_tv;

    @ViewInject(R.id.higher_tv)
    private TextView higher_tv;

    @ViewInject(R.id.cancel_tv)
    private TextView cancel_tv;

    @ViewInject(R.id.sure_tv)
    private TextView sure_tv;


    @ViewInject(R.id.bg_view)
    private View bg_view;

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
    private int priceNum = 0;
    //按销量从高到底排序
    private String sell = "";
    //按用券比例从高到底排序
    private String tsort = "";
    //按可返积分从高到底排序
    private String integral = "";
    //psort=1按价格从底到高，psort=2按价格从高到底
    private String psort = "";
    //价格区间内检索商品，格式是 开始金额_结束金额 ，两个价格中间用下划线链接
    private String price = "";

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
                goodsPst.search("1", keyword, p, sell, tsort, integral, psort, price, false);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<GridView> refreshView) {
                p++;
                if (allNum <= data.size()) {
                    search_goods_gv.onRefreshComplete();
                    return;
                }
                goodsPst.search("1", keyword, p, sell, tsort, integral, psort, price, false);
            }
        });

        search_goods_gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // 票券区详情
                Bundle bundle = new Bundle();
                bundle.putString("ticket_buy_id", data.get(i).getGoods_id());
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

    @SuppressLint("ResourceAsColor")
    @Override
    @OnClick({R.id.search_title_right_tv, R.id.search_type_tv, R.id.internal_tv, R.id.cash_coupon_tv, R.id.sales_volume_tv, R.id.price_tv, R.id.lower_tv, R.id.higher_tv, R.id.cancel_tv, R.id.sure_tv})
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
                if (pop_search_layout.getVisibility() == View.VISIBLE) {
                    bg_view.setVisibility(View.GONE);
                    pop_search_layout.setVisibility(View.GONE);
                    pop_search_layout.setFocusable(false);
                }
                break;
            case R.id.cash_coupon_tv:
                setChioceItem(1);
                if (pop_search_layout.getVisibility() == View.VISIBLE) {
                    bg_view.setVisibility(View.GONE);
                    pop_search_layout.setVisibility(View.GONE);
                    pop_search_layout.setFocusable(false);
                }
                break;
            case R.id.sales_volume_tv:
                setChioceItem(2);
                if (pop_search_layout.getVisibility() == View.VISIBLE) {
                    bg_view.setVisibility(View.GONE);
                    pop_search_layout.setVisibility(View.GONE);
                    pop_search_layout.setFocusable(false);
                }
                break;
            case R.id.price_tv:
                setChioceItem(3);
                if (pop_search_layout.getVisibility() == View.GONE) {
                    bg_view.setVisibility(View.VISIBLE);
                    bg_view.setClickable(true);
                    bg_view.setFocusableInTouchMode(true);
                    pop_search_layout.setVisibility(View.VISIBLE);
                    pop_search_layout.setFocusable(true);
                    TranslateAnimation animation  = new TranslateAnimation(0, 0, 0, 0.5f);
                    animation.setDuration(200);
                    pop_search_layout.startAnimation(animation);
                } else if (pop_search_layout.getVisibility() == View.VISIBLE) {
                    bg_view.setVisibility(View.GONE);
                    pop_search_layout.setVisibility(View.GONE);
                    pop_search_layout.setFocusable(false);
                }
                break;
            case R.id.lower_tv:
                priceNum=1;
                price_tv.setCompoundDrawables(null, null,selectId, null);
                clearTv();
                lower_tv.setTextColor(Color.parseColor("#FE666A"));
                lower_tv.setBackgroundResource(R.drawable.shape_red_pop);
                sell = "";
                tsort = "";
                integral = "";
                psort = "1";
                price = "";
                requestData();
                if (pop_search_layout.getVisibility() == View.VISIBLE) {
                    bg_view.setVisibility(View.GONE);
                    pop_search_layout.setVisibility(View.GONE);
                    pop_search_layout.setFocusable(false);
                }
                break;
            case R.id.higher_tv:
                priceNum=2;
                price_tv.setCompoundDrawables(null, null,twoSelectId, null);
                clearTv();
                higher_tv.setTextColor(Color.parseColor("#FE666A"));
                higher_tv.setBackgroundResource(R.drawable.shape_red_pop);
                sell = "";
                tsort = "";
                integral = "";
                psort = "2";
                price = "";
                requestData();
                if (pop_search_layout.getVisibility() == View.VISIBLE) {
                    bg_view.setVisibility(View.GONE);
                    pop_search_layout.setVisibility(View.GONE);
                    pop_search_layout.setFocusable(false);
                }
                break;
            case R.id.cancel_tv:
                if (pop_search_layout.getVisibility() == View.VISIBLE) {
                    bg_view.setVisibility(View.GONE);
                    pop_search_layout.setVisibility(View.GONE);
                    pop_search_layout.setFocusable(false);
                }
                break;
            case R.id.sure_tv:
                clearTv();
                String lower = lower_et.getText().toString();
                String higher=higher_et.getText().toString();
                if (TextUtils.isEmpty(lower)){
                    lower="0";
                }
                if (TextUtils.isEmpty(higher)){
                    higher="0";
                }
                int low = Integer.parseInt(lower);
                int high = Integer.parseInt(higher);
                if (low>high){
                    showToast("输入有误，最低价不能高于最高价");
                    return;
                }

                PreferencesUtils.putInt(GoodsListAty.this,"lower",low);
                PreferencesUtils.putInt(GoodsListAty.this,"higher",high);
                sell = "";
                tsort = "";
                integral = "";
                psort = "";
                price = low+"_"+high;
                requestData();
                if (pop_search_layout.getVisibility() == View.VISIBLE) {
                    bg_view.setVisibility(View.GONE);
                    pop_search_layout.setVisibility(View.GONE);
                    pop_search_layout.setFocusable(false);
                }
                break;
        }
    }

    private void clearTv() {
        lower_tv.setTextColor(getResources().getColor(R.color.hint_text_color));
        lower_tv.setBackgroundResource(R.drawable.gr_item_back);
        higher_tv.setTextColor(getResources().getColor(R.color.hint_text_color));
        higher_tv.setBackgroundResource(R.drawable.gr_item_back);
    }

    private void setChioceItem(int index) {
        clearChioce();
        if (index == 0) {
            internal_tv.setTextColor(Color.parseColor(redColor));
            sell = "";
            tsort = "";
            integral = "1";
            psort = "";
            price = "";
            requestData();
        } else if (index == 1) {
            cash_coupon_tv.setTextColor(Color.parseColor(redColor));
            sell = "";
            tsort = "1";
            integral = "";
            psort = "";
            price = "";
            requestData();
        } else if (index == 2) {
            sales_volume_tv.setTextColor(Color.parseColor(redColor));
            sell = "1";
            tsort = "";
            integral = "";
            psort = "";
            price = "";
            requestData();
        } else if (index == 3) {
            price_tv.setTextColor(Color.parseColor(redColor));
            if (priceNum==1){
                price_tv.setCompoundDrawables(null, null,selectId, null);
            }else if (priceNum==2){
                price_tv.setCompoundDrawables(null, null,twoSelectId, null);
            }

        }
    }

    private void clearChioce() {
        internal_tv.setTextColor(Color.parseColor(blgColor));
        cash_coupon_tv.setTextColor(Color.parseColor(blgColor));
        sales_volume_tv.setTextColor(Color.parseColor(blgColor));
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
        if (PreferencesUtils.containKey(GoodsListAty.this,"lower")) {
            lower_et.setText(PreferencesUtils.getInt(GoodsListAty.this, "lower",0)+"");
        }
        if (PreferencesUtils.containKey(GoodsListAty.this,"higher")) {
            higher_et.setText(PreferencesUtils.getInt(GoodsListAty.this, "higher",0)+"");
        }
        lower_et.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                lower_et.setCursorVisible(true);
                return false;
            }
        });
        higher_et.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                higher_et.setCursorVisible(true);
                return false;
            }
        });
    }

    @Override
    protected void requestData() {
        goodsPst.search("1", keyword, p, sell, tsort, integral, psort, price, true);
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
