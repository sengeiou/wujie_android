package com.txd.hzj.wjlp.mellOnLine;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ants.theantsgo.util.PreferencesUtils;
import com.ants.theantsgo.view.inScroll.GridViewForScrollView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.mellOnLine.adapter.MellListAdapter;
import com.txd.hzj.wjlp.tool.ChangeTextViewStyle;

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
    private ListView mell_lv;

    private MellListAdapter mlAdapter;

    private List<String> mells;
    private List<String> prodects;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.search_title_layout);
        forTitle();
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
    @OnClick({R.id.search_title_right_tv})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.search_title_right_tv:
                forSearch();
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
        mlAdapter = new MellListAdapter(this, mells);
    }

    @Override
    protected void requestData() {

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
}
