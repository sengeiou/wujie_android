package com.txd.hzj.wjlp.mellonLine;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ants.theantsgo.util.ListUtils;
import com.ants.theantsgo.util.PreferencesUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.melloffLine.MellOffLineListAty;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：DUKE_HwangZj
 * 日期：2017/7/6 0006
 * 时间：上午 11:36
 * 描述：搜索(1-3搜索)
 */
public class SearchAty extends BaseAty {

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


    @ViewInject(R.id.search_history_gv)
    private GridView search_history_gv;

    private PopupWindow mCurPopupWindow;
    private int search_type = 1;
    private List<String> history;

    /**
     * 历史记录
     */
    private String his_str;

    private HistoryAdapter historyAdapter;
    private StringBuilder sb;
    private String type = "商品";
    private String[] his;
    private Bundle mBundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        forTitle();
        showStatusBar(R.id.search_title_layout);
        forKeyboardSearch();
        search_history_gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                nextAty(his[i]);
            }
        });
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

    private void forTitle() {
        search_title_layout.setBackgroundColor(ContextCompat.getColor(this, R.color.white));
        search_title_be_back_iv.setVisibility(View.VISIBLE);
        title_search_tv.setVisibility(View.GONE);
        search_lin_layout.setVisibility(View.VISIBLE);
        message_layout.setVisibility(View.GONE);
        search_title_right_tv.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getHistory();
    }

    /**
     * 本地获取历史记录
     */
    private void getHistory() {
        if (mBundle != null) {
            his_str = PreferencesUtils.getString(this, "offlinehistory", "");
        } else {
            his_str = PreferencesUtils.getString(this, "onlinehistory", "");
        }

        if (!his_str.equals("")) {// 本地有历史记录
            his = his_str.split(ListUtils.DEFAULT_JOIN_SEPARATOR);
            historyAdapter = new HistoryAdapter(his);
            search_history_gv.setVisibility(View.VISIBLE);
            search_history_gv.setAdapter(historyAdapter);
        } else {
            search_history_gv.setVisibility(View.GONE);
        }
    }

    @Override
    @OnClick({R.id.search_type_tv, R.id.search_title_right_tv, R.id.clear_history_tv})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.search_type_tv:// 商品商家弹窗
                mCurPopupWindow = showPopupWindow(v);
                break;
            case R.id.search_title_right_tv:// 搜索
                forSearch();
                break;
            case R.id.clear_history_tv:// 清除历史记录
                hideKeyBoard();
                if (mBundle != null) {
                    PreferencesUtils.putString(this, "offlinehistory", "");
                } else {
                    PreferencesUtils.putString(this, "onlinehistory", "");
                }
                search_history_gv.setVisibility(View.GONE);
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
        if (!his_str.contains(key)) {
            sb = new StringBuilder();
            sb.append(key).append(",").append(his_str);
            if (mBundle != null) {
                PreferencesUtils.putString(this, "offlinehistory", sb.toString());
            } else {
                PreferencesUtils.putString(this, "onlinehistory", sb.toString());
            }
        }
        nextAty(key);
    }

    /**
     * 跳转
     *
     * @param key 关键词
     */
    private void nextAty(String key) {

        if (mBundle != null) {
            Bundle bundle = new Bundle();
            bundle.putString("keyword", key);
            startActivity(MellOffLineListAty.class, bundle);
        } else {
            Bundle bundle = new Bundle();
            bundle.putString("keyword", key);
            bundle.putString("type", type);
            if (1 == search_type) {
                startActivity(GoodsListAty.class, bundle);
            } else {
                startActivity(MellListAty.class, bundle);
            }
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_search;
    }

    @Override
    protected void initialized() {
        history = new ArrayList<>();
        mBundle = getIntent().getExtras();
        if (mBundle != null) {
            title_search_tv.setHint("搜索商家");
            search_type_tv.setText("商家");
            search_type_tv.setClickable(false);
            search_type = 2;
        } else {
            title_search_tv.setHint("搜索商品、商家");
            search_type_tv.setText("商品");
            search_type_tv.setClickable(true);
            search_type = 1;
        }
    }

    @Override
    protected void requestData() {

    }

    public PopupWindow showPopupWindow(View anchorView) {
        View contentView = LayoutInflater.from(anchorView.getContext()).inflate(R.layout.pop_select_type, null);
        contentView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        // 创建PopupWindow时候指定高宽时showAsDropDown能够自适应
        // 如果设置为wrap_content,showAsDropDown会认为下面空间一直很充足（我以认为这个Google的bug）
        // 备注如果PopupWindow里面有ListView,ScrollView时，一定要动态设置PopupWindow的大小
        final PopupWindow popupWindow = new PopupWindow(contentView,
                contentView.getMeasuredWidth(), contentView.getMeasuredHeight(), false);
        // 如果不设置PopupWindow的背景，有些版本就会出现一个问题：无论是点击外部区域还是Back键都无法dismiss弹框
        popupWindow.setBackgroundDrawable(new ColorDrawable());

        // setOutsideTouchable设置生效的前提是setTouchable(true)和setFocusable(false)
        popupWindow.setOutsideTouchable(true);

        // 设置为true之后，PopupWindow内容区域 才可以响应点击事件
        popupWindow.setTouchable(true);

        // true时，点击返回键先消失 PopupWindow
        // 但是设置为true时setOutsideTouchable，setTouchable方法就失效了（点击外部不消失，内容区域也不响应事件）
        // false时PopupWindow不处理返回键
        popupWindow.setFocusable(false);
        popupWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;   // 这里面拦截不到返回键
            }
        });
        // 如果希望showAsDropDown方法能够在下面空间不足时自动在anchorView的上面弹出
        // 必须在创建PopupWindow的时候指定高度，不能用wrap_content
        popupWindow.showAsDropDown(anchorView);

        TextView pop_search_goods_tv = contentView.findViewById(R.id.pop_search_goods_tv);
        TextView pop_search_mell_tv = contentView.findViewById(R.id.pop_search_mell_tv);
        if (1 == search_type) {
            pop_search_goods_tv.setTextColor(ContextCompat.getColor(SearchAty.this, R.color.theme_color));
            pop_search_mell_tv.setTextColor(ContextCompat.getColor(SearchAty.this, R.color.white));
        } else {
            pop_search_goods_tv.setTextColor(ContextCompat.getColor(SearchAty.this, R.color.white));
            pop_search_mell_tv.setTextColor(ContextCompat.getColor(SearchAty.this, R.color.theme_color));
        }
        pop_search_goods_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search_type = 1;
                type = "商品";
                search_type_tv.setText(type);
                popupWindow.dismiss();
            }
        });
        pop_search_mell_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search_type = 2;
                type = "商家";
                search_type_tv.setText(type);
                popupWindow.dismiss();
            }
        });

        return popupWindow;
    }


    private class HistoryAdapter extends BaseAdapter {
        private HistoryViewHolder hvh;
        private String[] history;

        public HistoryAdapter(String[] history) {
            this.history = history;
        }

        @Override
        public int getCount() {
            return history.length;
        }

        @Override
        public String getItem(int i) {
            return history[i];
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (null == view) {
                view = LayoutInflater.from(SearchAty.this).inflate(R.layout.item_search_history_gv, viewGroup, false);
                hvh = new HistoryViewHolder();
                ViewUtils.inject(hvh, view);
                view.setTag(hvh);
            } else {
                hvh = (HistoryViewHolder) view.getTag();
            }
            hvh.search_history_tv.setText(getItem(i));
            return view;
        }

        class HistoryViewHolder {

            @ViewInject(R.id.search_history_tv)
            private TextView search_history_tv;

        }

    }

    @Override
    public void onBackPressed() {
        if (mCurPopupWindow != null && mCurPopupWindow.isShowing()) {
            mCurPopupWindow.dismiss();
        } else {
            super.onBackPressed();
        }
    }

}
