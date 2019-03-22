package com.txd.hzj.wjlp.savemoney;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.ants.theantsgo.base.BaseView;
import com.ants.theantsgo.config.Config;
import com.ants.theantsgo.httpTools.ApiTool2;
import com.ants.theantsgo.util.JSONUtils;
import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 创建者：zhangyunfei
 * 创建时间：2019/3/18 10:02
 * 功能描述：省钱购
 */
public class SaveMoneyAty extends BaseAty {
    @ViewInject(R.id.slidingTabLayout)
    private SlidingTabLayout slidingTabLayout;

    @ViewInject(R.id.viewPager)
    private ViewPager viewPager;

    @ViewInject(R.id.title_search_ev)
    private EditText title_search_ev;

    @ViewInject(R.id.recyclerView)
    private RecyclerView recyclerView;


    private List<Fragment> mFragmentList;

    private String[] mTitles = new String[]{"淘宝/天猫","拼多多"};
    private SaveMoneyFgt mSaveMoneyFgt1;
    private SaveMoneyFgt mSaveMoneyFgt2;
    private SaveMoneyAdapter mAdapter;

    private int selectPosition = 0;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_save_money;
    }

    @Override
    protected void initialized() {
        showStatusBar(R.id.title_re_layout);
        mFragmentList = new ArrayList<>();
        mSaveMoneyFgt1 = SaveMoneyFgt.newInstance("淘宝");
        mFragmentList.add(mSaveMoneyFgt1);
        mSaveMoneyFgt2 = SaveMoneyFgt.newInstance("拼多多");
        mFragmentList.add(mSaveMoneyFgt2);
        viewPager.setAdapter(new PageAdapter(getSupportFragmentManager(),mFragmentList,mTitles));
        slidingTabLayout.setViewPager(viewPager);
        slidingTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                selectPosition = position;
                setSearch();
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
        title_search_ev.setSelection(title_search_ev.getText().length());
        title_search_ev.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                title_search_ev.setSelection(s.length());
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                outRect.left = 10;
            }
        });


    }

    @Override
    protected void requestData() {
        getReci(this);
    }

    private void getReci(BaseView baseView) {
        ApiTool2 apiTool2 = new ApiTool2();
        RequestParams requestParams = new RequestParams();
        apiTool2.getApi(Config.SHARE_URL + "index.php/Api/Goods/getReci", requestParams, baseView);
    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        Map<String, String> map = JSONUtils.parseKeyAndValueToMap(jsonStr);
        if (requestUrl.endsWith("getReci")){
            final ArrayList<Map<String, String>> mapArrayList = JSONUtils.parseKeyAndValueToMapList(map.get("data"));
            if (mapArrayList != null && mapArrayList.size()>0){
                mAdapter = new SaveMoneyAdapter(mapArrayList);
                for (int i = 0; i < mapArrayList.size(); i++) {
                    if (mapArrayList.get(i).get("is_default").equals("1")){
                        title_search_ev.setText(mapArrayList.get(i).get("name"));
                        mAdapter.setSelectPosition(i);
                        setSearch();
                        break;
                    }
                }
                if (TextUtils.isEmpty(title_search_ev.getText())){
                    title_search_ev.setText(mapArrayList.get(0).get("name"));
                    mAdapter.setSelectPosition(0);
                    setSearch();
                }
                recyclerView.setAdapter(mAdapter);
                mAdapter.setOnItemClickListener(new SaveMoneyAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        Map<String, String> data = mapArrayList.get(position);
                        title_search_ev.setText(data.get("name"));
                        mAdapter.setSelectPosition(position);
                        setSearch();
                    }
                });
            }

        }
    }

    private void setSearch() {
        if (selectPosition == 0){
            mSaveMoneyFgt1.getSearchLabel(title_search_ev.getText().toString());
        }else if (selectPosition == 1){
            mSaveMoneyFgt2.getSearchLabel(title_search_ev.getText().toString());
        }
    }

    @OnClick({R.id.search_title_right_tv})
    public void onClick(View view){
        int id = view.getId();
        if (id == R.id.search_title_right_tv){
            if (TextUtils.isEmpty(title_search_ev.getText())){
                showToast("搜索内容不能为空");
                return;
            }
            setSearch();
        }
    }


    public static class SaveMoneyAdapter extends RecyclerView.Adapter<SaveMoneyAdapter.ViewHolder> {

        private Context mContext;

        private List<Map<String, String>> mList;

        private SaveMoneyAdapter.OnItemClickListener mOnItemClickListener;

        private int selectPosition = -1;

        public void setOnItemClickListener(SaveMoneyAdapter.OnItemClickListener onItemClickListener) {
            mOnItemClickListener = onItemClickListener;
        }

        public SaveMoneyAdapter(List<Map<String, String>> list) {
            mList = list;
        }

        public void setSelectPosition(int selectPosition) {
            this.selectPosition = selectPosition;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public SaveMoneyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            mContext = parent.getContext();
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_label, parent, false);
            SaveMoneyAdapter.ViewHolder holder = new SaveMoneyAdapter.ViewHolder(view);
            ViewUtils.inject(holder, view);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull final SaveMoneyAdapter.ViewHolder holder, int position) {
            Map<String, String> map = mList.get(position);
            holder.labelTv.setText(map.get("name"));
            if (selectPosition == position){
                holder.labelTv.setBackgroundResource(R.drawable.shape_save_money);
                holder.labelTv.setTextColor(ContextCompat.getColor(mContext,R.color.holo_red_light));
            }else {
                holder.labelTv.setBackgroundResource(R.drawable.shape_black_line_5);
                holder.labelTv.setTextColor(ContextCompat.getColor(mContext,R.color.text_color));
            }
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null){
                        mOnItemClickListener.onItemClick(holder.getLayoutPosition());
                    }
                }
            });

        }

        @Override
        public int getItemCount() {
            return mList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            @ViewInject(R.id.labelTv)
            TextView labelTv;

            public ViewHolder(View itemView) {
                super(itemView);
            }
        }

        public interface OnItemClickListener{
            void onItemClick(int position);
        }
    }


    public static class PageAdapter extends FragmentStatePagerAdapter{

        private List<Fragment> mFragments;
        private String[] mTitles;

        public PageAdapter(FragmentManager fm,List<Fragment> fragments,String[] titles) {
            super(fm);
            mFragments = fragments;
            mTitles = titles;
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }
    }
}
