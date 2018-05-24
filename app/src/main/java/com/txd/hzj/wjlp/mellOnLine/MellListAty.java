package com.txd.hzj.wjlp.mellOnLine;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ants.theantsgo.gson.GsonUtil;
import com.ants.theantsgo.util.L;
import com.ants.theantsgo.util.ListUtils;
import com.ants.theantsgo.util.PreferencesUtils;
import com.ants.theantsgo.view.pulltorefresh.PullToRefreshBase;
import com.ants.theantsgo.view.pulltorefresh.PullToRefreshListView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.bean.footPoint.FootMellsBan;
import com.txd.hzj.wjlp.bean.search.SearchMell;
import com.txd.hzj.wjlp.http.goods.GoodsPst;
import com.txd.hzj.wjlp.mellOnLine.adapter.MellListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/6 0006
 * 时间：下午 3:57
 * 描述：商铺列表页(1-5搜索结果2)
 * ===============Txunda===============
 */
public class MellListAty extends BaseAty {

    @ViewInject(R.id.search_title_layout)
    private RelativeLayout search_title_layout;

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
    private String type = "";
    private String keyword = "";
    private String his_str = "";
    private StringBuilder sb;
    /**
     * 商家列表
     */
    @ViewInject(R.id.mell_lv)
    private PullToRefreshListView mell_lv;

    private MellListAdapter mlAdapter;

    private List<FootMellsBan> mells;
    private List<String> prodects;

    private GoodsPst goodsPst;
    private int p = 1;

    @ViewInject(R.id.no_data_layout)
    private LinearLayout no_data_layout;
    private int allNum = 0;
    private int search_type = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.search_title_layout);
        forTitle();

        mell_lv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                p = 1;

                goodsPst.search(type.equals("商品") ? "1" : "2", keyword, p, false);

            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                if (allNum <= mells.size()) {
                    mell_lv.onRefreshComplete();
                    return;
                }
                p++;
                goodsPst.search(type.equals("商品") ? "1" : "2", keyword, p, false);
            }
        });

        mell_lv.setEmptyView(no_data_layout);

        forKeyboardSearch();
        mell_lv.setAdapter(mlAdapter);
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
    @OnClick({R.id.search_title_right_tv, R.id.search_type_tv})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.search_title_right_tv:
                forSearch();
                break;
            case R.id.search_type_tv:
                finish();
                break;
        }
    }

    // 搜索
    private void forSearch() {
        hideKeyBoard();
        String key = title_search_ev.getText().toString();
        if (key.equals("")) {
            showErrorTip("请输入搜索关键词");
            return;
        }
        his_str = PreferencesUtils.getString(this, "history", "");
        if (!his_str.contains(key)) {
            sb = new StringBuilder();
            sb.append(key).append(",").append(his_str);
            PreferencesUtils.putString(this, "history", sb.toString());
        }
        keyword = key;
        p = 1;
        requestData();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_mell_list;
    }

    @Override
    protected void initialized() {
        type = getIntent().getStringExtra("type");
        keyword = getIntent().getStringExtra("keyword");
        mells = new ArrayList<>();
        prodects = new ArrayList<>();
        goodsPst = new GoodsPst(this);
    }

    @Override
    protected void requestData() {
        // 如果type对象为空则直接将其设置一个空字符串，防止空指针异常
        type = type == null ? "" : type;
        goodsPst.search(type.equals("商品") ? "1" : "2", keyword, p, true);
    }

    private void forTitle() {
        search_title_layout.setBackgroundColor(ContextCompat.getColor(this, R.color.white));
        search_title_be_back_iv.setVisibility(View.VISIBLE);
        title_search_tv.setVisibility(View.GONE);
        search_lin_layout.setVisibility(View.VISIBLE);
        message_layout.setVisibility(View.GONE);
        search_title_right_tv.setVisibility(View.VISIBLE);
        title_search_ev.setText(keyword);
        search_type_tv.setText(type);
    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        L.e("wang", "=========>>>>>>>>jsonstr:" + jsonStr);
        if (requestUrl.contains("search")) {
            SearchMell mell = GsonUtil.GsonToBean(jsonStr, SearchMell.class);

            allNum = mell.getNums();
            if (1 == p) {
                mells = mell.getData().getList();
                if (!ListUtils.isEmpty(mells)) {
                    mlAdapter = new MellListAdapter(this, mells, 1);
                    mell_lv.setAdapter(mlAdapter);
                }
            } else {
                List<FootMellsBan> mells2 = mell.getData().getList();
                if (!ListUtils.isEmpty(mells2)) {
                    mells.addAll(mells2);
                    mlAdapter.notifyDataSetChanged();
                }
            }
            mell_lv.onRefreshComplete();

        }
    }
}
