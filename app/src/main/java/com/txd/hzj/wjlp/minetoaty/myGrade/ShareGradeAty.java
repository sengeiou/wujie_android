package com.txd.hzj.wjlp.minetoaty.myGrade;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ants.theantsgo.config.Config;
import com.ants.theantsgo.tool.ToolKit;
import com.ants.theantsgo.util.JSONUtils;
import com.ants.theantsgo.util.L;
import com.ants.theantsgo.view.inScroll.ListViewForScrollView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.tamic.novate.Novate;
import com.tamic.novate.Throwable;
import com.tamic.novate.callback.RxStringCallback;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.cityselect1.ac.EasySideBarBuilder;
import com.txd.hzj.wjlp.http.user.UserPst;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.gavinliu.android.lib.shapedimageview.ShapedImageView;

/**
 * 作者：DUKE_HwangZj
 * 日期：2017/7/21 0021
 * 时间：上午 10:44
 * 描述：工作成绩
 */
public class ShareGradeAty extends BaseAty {
    /**
     * lg 无限点击事件
     */
    private String infinite;
    /**
     * AppBarLAyout
     */
    @ViewInject(R.id.grade_app_bar_layout)
    private AppBarLayout app_bar_layout;

    /**
     * CollapsingToolbarLayout
     */
    @ViewInject(R.id.sh_collapsing_toolbar_layout)
    private CollapsingToolbarLayout collapsing_toolbar_layout;

    /**
     * ToolBar
     */
    @ViewInject(R.id.sh_toolbar)
    private Toolbar toolbar;

    @ViewInject(R.id.sh_left_tv)
    private TextView left_tv;
    @ViewInject(R.id.sh_left_view)
    private View left_view;

    @ViewInject(R.id.sh_righr_tv)
    private TextView right_tv;
    @ViewInject(R.id.sh_right_view)
    private View right_view;

    @ViewInject(R.id.my_share_grade_lv)
    private ListViewForScrollView my_share_grade_lv;

    private RankingListAdapter rankingListAdapter;

    private UserPst userPst;
    private String city_name = "天津";
    private int p = 1;
    private String city_id = "";

    private String type = "share";

    @ViewInject(R.id.grade_location_tv)
    private TextView grade_location_tv;

    @ViewInject(R.id.no_data_layout)
    private LinearLayout no_data_layout;

    // Header View
    private ProgressBar progressBar;
    private TextView textView;
    private ImageView imageView;

    // Footer View
    private ProgressBar footerProgressBar;
    private TextView footerTextView;
    private ImageView footerImageView;

    /**
     * 是不是第一次进入
     */
    private boolean frist = true;

//    private List<Map<String, String>> rankList;
    private List<RankBean> rankList;
    @ViewInject(R.id.user_head_iv)
    private ShapedImageView user_head_iv;

    private int size = 0;
    private int size2 = 0;

    @ViewInject(R.id.nick_name_tv)
    private TextView nick_name_tv;
    @ViewInject(R.id.share_num_tv)
    private TextView share_num_tv;
    @ViewInject(R.id.recommend_num_tv)
    private TextView recommend_num_tv;
    @ViewInject(R.id.nestedScrollView)
    private NestedScrollView nestedScrollView;

//    ShareAdapter shareAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        // 沉浸式
        showStatusBar(R.id.sh_toolbar);
        app_bar_layout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                collapsing_toolbar_layout.setTitle(" ");
            }
        });

        my_share_grade_lv.setEmptyView(no_data_layout);
        p = 1;
//        userPst.gradeRank(p, city_id, type, city_name, true);
        changeViewStatus(0);
        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY == (v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight())) {
                    frist = false;
                    p++;
                    userPst.gradeRank(p, city_id, type, city_name, false);
                    showProgressDialog();
                }
            }


        });
//        userPst.gradeRank(p, "", "share", grade_location_tv.getText().toString(), true);
    }

    @Override
    @OnClick({R.id.recommend_success_layout, R.id.sh_left_lin_layout, R.id.sh_right_lin_layout, R.id
            .grade_location_tv, R.id.share_time_layout})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.share_time_layout:// 分享次数
                startActivity(ShareTimesAty.class, null);
                break;
            case R.id.recommend_success_layout:// 推荐成功
                Bundle bundle = new Bundle();
                bundle.putString("parent_id", "");
                bundle.putString("nickname", "");
                startActivity(RecommendSuccessAty.class, bundle);
                break;
            case R.id.sh_left_lin_layout:// 左(分享榜)
                changeViewStatus(0);
//                shareHttp();
//                userPst.gradeRank(p, "", "share", grade_location_tv.getText().toString(), true);
                break;
            case R.id.sh_right_lin_layout:// 右(推荐榜)
                changeViewStatus(1);
//                userPst.gradeRank(p, "", "recommend", grade_location_tv.getText().toString(), true);
                break;
            case R.id.grade_location_tv:// 地址选择
                download();
                break;
        }
    }

    private void changeViewStatus(int i) {
        left_tv.setTextColor(ContextCompat.getColor(this, R.color.app_text_color));
        right_tv.setTextColor(ContextCompat.getColor(this, R.color.app_text_color));
        left_view.setBackgroundColor(ContextCompat.getColor(this, R.color.white));
        right_view.setBackgroundColor(ContextCompat.getColor(this, R.color.white));
        if (0 == i) {
            left_tv.setTextColor(Color.parseColor("#E60012"));
            left_view.setBackgroundColor(Color.parseColor("#E60012"));
            type = "share";
        } else {
            right_tv.setTextColor(Color.parseColor("#E60012"));
            right_view.setBackgroundColor(Color.parseColor("#E60012"));
            type = "recommend";
        }
        p = 1;
        userPst.gradeRank(p, city_id, type, city_name, false);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_share_grade;
    }

    @Override
    protected void initialized() {
        userPst = new UserPst(this);
        rankingListAdapter = new RankingListAdapter();
        rankList = new ArrayList<>();
        size = ToolKit.dip2px(this, 80);
        size2 = ToolKit.dip2px(this, 60);

        /**
         * 列表项点击
         */
        my_share_grade_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                RankBean rankBean= rankList.get(position);
                if(!TextUtils.isEmpty(rankBean.getParent_id())){//有parent_id时候可以跳转
                    Bundle bundle = new Bundle();
                    bundle.putString("parent_id", rankBean.getParent_id());
                    bundle.putString("nickname", rankBean.getNickname());
                    bundle.putString("infinite",infinite);
                    startActivity(RecommendSuccessAty.class, bundle);
                }
            }
        });
    }

    @Override
    protected void requestData() {
    }

    @Override
    public void onError(String requestUrl, Map<String, String> map) {
        super.onError(requestUrl, map);
        if (requestUrl.contains("gradeRank")) {
            L.e("==============gradeRank===============", requestUrl);
            if (ToolKit.isList(map, "data")) {
                Map<String, String> data = JSONUtils.parseKeyAndValueToMap(map.get("data"));
                if (1 == p) {
                    Glide.with(this).load(data.get("head_pic"))
                            .override(size, size)
                            .placeholder(R.drawable.ic_default)
                            .error(R.drawable.ic_default)
                            .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                            .into(user_head_iv);
                    nick_name_tv.setText(data.get("nickname") + "\n推荐人：" + data.get("parent_name"));
                    share_num_tv.setText(data.get("share_num")+"/"+data.get("share_rank"));
                    recommend_num_tv.setText(data.get("recommend_num")+"/"+data.get("recommend_rank"));
                    if (ToolKit.isList(data, "rank_list")) {
                        rankList = JSONUtils.parseKeyAndValueToMapList(RankBean.class,data.get("rank_list"));
                        my_share_grade_lv.setAdapter(rankingListAdapter);
                    }

                    if (!frist) {
//                        swipe_refresh.setRefreshing(false);
                        progressBar.setVisibility(View.GONE);
                    }

                } else {
                    if (ToolKit.isList(data, "rank_list")) {
                        rankList.addAll(JSONUtils.parseKeyAndValueToMapList(RankBean.class,data.get("rank_list")));
                        my_share_grade_lv.setAdapter(rankingListAdapter);

                    }
                    footerImageView.setVisibility(View.VISIBLE);
                    footerProgressBar.setVisibility(View.GONE);
//                    swipe_refresh.setLoadMore(false);
                }
            } else {
                if (1 == p) {
                    if (!frist) {
//                        swipe_refresh.setRefreshing(false);
                        progressBar.setVisibility(View.GONE);
                    }
                } else {
                    footerImageView.setVisibility(View.VISIBLE);
                    footerProgressBar.setVisibility(View.GONE);
//                    swipe_refresh.setLoadMore(false);
                }
            }
        }
    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        Map<String, String> map = JSONUtils.parseKeyAndValueToMap(jsonStr);
        if (requestUrl.contains("gradeRank")) { // 如果请求的是排名
            if (ToolKit.isList(map, "data")) { // 如果data字段可以解析成集合形式
                Map<String, String> data = JSONUtils.parseKeyAndValueToMap(map.get("data")); // 将字段转换成Map集合
                L.e("==========p===========" + p);
                L.e("==========jsonStr===========" + jsonStr);
                if (1 == p) { // 如果当前请求是第一页
                    rankList.removeAll(rankList); // 移除掉List中所有值
                    Glide.with(this).load(data.get("head_pic")) // 设置显示头像
                            .override(size, size)
                            .placeholder(R.drawable.ic_default)
                            .error(R.drawable.ic_default)
                            .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                            .into(user_head_iv);
                    nick_name_tv.setText(data.get("nickname") + "\n推荐人：" + data.get("parent_name")); // 设置显示文字消息
                    share_num_tv.setText(data.get("share_num")+"/"+data.get("share_rank"));// 设置显示推荐人数
                    recommend_num_tv.setText(data.get("recommend_num")+"/"+data.get("recommend_rank"));// 设置显示消息
                    if (ToolKit.isList(data, "rank_list")) { // 如果rank_list字段可以解析为集合
//                        rankList = JSONUtils.parseKeyAndValueToMapList(data.get("rank_list")); // 将rank_list字段转换为集合
                        rankList = JSONUtils.parseKeyAndValueToMapList(RankBean.class,data.get("rank_list")); // 将rank_list字段转换为集合
                        if (rankList.size() > 0) { // 如果集合中元素个数大于0
                            my_share_grade_lv.setAdapter(rankingListAdapter); // 设置Adapter
                        } else { // 否则为空集合
                            footerImageView.setVisibility(View.VISIBLE);
                            footerProgressBar.setVisibility(View.GONE);
                        }
                    }
                    if (!frist) { // 如果不是第一次请求
                        progressBar.setVisibility(View.GONE);
                    }
                } else { // 否则是多次请求数据，页数大于1
                    if (ToolKit.isList(data, "rank_list")) { // 判断rank_list是否可以解析成集合
//                        ArrayList<Map<String, String>> rank_list = JSONUtils.parseKeyAndValueToMapList(data.get("rank_list")); // 将rank_list转换成集合
                        ArrayList<RankBean> rank_list = JSONUtils.parseKeyAndValueToMapList(RankBean.class,data.get("rank_list")); // 将rank_list转换成集合
                        if (rank_list.size() > 0) { // 集合中有数据
                            rankList.addAll(rank_list); // 添加新集合到原始List数组中
                            rankingListAdapter.notifyDataSetChanged(); // 更新Adapter
                        } else {
                            showToast("已经是最后一页，没有更多数据了"); // 否则显示提示：没有更多数据
                        }
                    }
                }
                String infiniteStr=data.get("infinite");
                if(data.containsKey("infinite")&& !android.text.TextUtils.isEmpty(infiniteStr)){//lg 无限点击事件数据获取
                    infinite=infiniteStr;
                }

            } else { // 否则，请求回传的data字段无法转换成集合形式
                if (1 == p) { // 如果请求的事第一页数据
                    if (!frist) { // 如果不是第一次进入
                        progressBar.setVisibility(View.GONE);
                    }
                } else { // 否则请求的是之后页面的数据
                    footerImageView.setVisibility(View.VISIBLE);
                    footerProgressBar.setVisibility(View.GONE);
                }
            }
            // 无论是否可以将data转换成集合类型，都更新一下Adapter
            rankingListAdapter.notifyDataSetChanged();
        }
    }

    private class RankingListAdapter extends BaseAdapter {

        private RLVH rlvh;
        private int imageId;

        @Override
        public int getCount() {
            return rankList.size();
        }

        @Override
        public Object getItem(int position) {
            return rankList.get(position);
        }


        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

//            Map<String, String> rank = getItem(i);
            RankBean rank = (RankBean) getItem(i);

            if (view == null) {
                view = LayoutInflater.from(ShareGradeAty.this).inflate(R.layout.item_share_grade_lv, viewGroup, false);
                rlvh = new RLVH();
                ViewUtils.inject(rlvh, view);
                view.setTag(rlvh);
            } else {
                rlvh = (RLVH) view.getTag();
            }

//            List<String> keyList = new ArrayList<String>();
//            for (String key : rank.keySet()) {
//                keyList.add(key);
//            } // 获取所有的Key
//            for (int x = 0; x < keyList.size(); x++) {
//                if (!keyList.get(x).equals("parent_id") && x == keyList.size() - 1) {
                    if(null==rank.getParent_id()){
                        rlvh.rank_type_tv.setText("分享");
                        rlvh.rank_unit_tv.setText("次");
                    }
//                }
//            }

            if (i < 100 && i >= 3) {// 第四名和第五名
                rlvh.top_from_four_to_five_iv.setVisibility(View.VISIBLE); // 4到5右下角勋章显示
                rlvh.top_three_iv.setVisibility(View.GONE); // 头饰隐藏
                imageId = getResources().getIdentifier("icon_ranking_more", "drawable", getPackageName());
                rlvh.top_from_four_to_five_iv.setImageResource(imageId);
                rlvh.top_from_four_to_five_tv.setText((i + 1) + "");
            } else if (i < 3) { // 前三名
                rlvh.top_from_four_to_five_iv.setVisibility(View.GONE); // 前三名勋章隐藏
                rlvh.top_from_four_to_five_tv.setVisibility(View.GONE); // 同时将显示的名次隐藏
                rlvh.top_three_iv.setVisibility(View.VISIBLE); // 头饰显示
                imageId = getResources().getIdentifier("icon_ranking_" + i, "drawable", getPackageName());
                rlvh.top_three_iv.setImageResource(imageId);
            } else {
                rlvh.top_from_four_to_five_iv.setVisibility(View.GONE);
                rlvh.top_from_four_to_five_tv.setVisibility(View.GONE); // 同时将显示的名次隐藏
                rlvh.top_three_iv.setVisibility(View.GONE);
            }


//            Glide.with(ShareGradeAty.this).load(rank.get("head_pic"))
            Glide.with(ShareGradeAty.this).load(rank.getHead_pic0())
                    .override(size, size)
                    .placeholder(R.drawable.ic_default)
                    .error(R.drawable.ic_default)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(rlvh.rank_item_haed_iv);
            rlvh.rank_nickname_Tv.setText(rank.getNickname());
//            rlvh.rank_nickname_Tv.setText(rank.get("nickname"));
//            rlvh.rank_num_tv.setText(rank.get("num"));
            rlvh.rank_num_tv.setText(rank.getNum());

            return view;
        }

        class RLVH {
            /**
             * 前三的标签
             */
            @ViewInject(R.id.top_three_iv)
            private ImageView top_three_iv;
            /**
             * 第四第五的标签
             */
            @ViewInject(R.id.top_from_four_to_five_iv)
            private ImageView top_from_four_to_five_iv;
            /**
             * 第四第五的标签
             */
            @ViewInject(R.id.top_from_four_to_five_tv)
            private TextView top_from_four_to_five_tv;
            /**
             * 头像
             */
            @ViewInject(R.id.rank_item_haed_iv)
            private ShapedImageView rank_item_haed_iv;

            @ViewInject(R.id.rank_nickname_Tv)
            private TextView rank_nickname_Tv;

            @ViewInject(R.id.rank_type_tv)
            private TextView rank_type_tv;

            @ViewInject(R.id.rank_num_tv)
            private TextView rank_num_tv;

            @ViewInject(R.id.rank_unit_tv)
            private TextView rank_unit_tv;

        }
    }

    private final String[] mIndexItems = {"热门"};//头部额外的索引

    /**
     * 选择城市
     */
    private void download() {

        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("region_id", "");
        new Novate.Builder(this)
                .baseUrl(Config.BASE_URL)
                .build()
                .rxPost("Address/getRegion", parameters, new RxStringCallback() {

                    @Override
                    public void onNext(Object tag, String response) {

                        L.e("=======response===========", response);

                        try {
                            JSONObject jsonObject = new JSONObject(response); // 获取Json数据
                            String data = jsonObject.getString("data"); // 解析data值
                            JSONObject jsonObject2 = new JSONObject(data); // 将解析的data转换成JsonObject
                            JSONArray jsonArray = jsonObject2.getJSONArray("hot_list"); // 获取热门城市列表转成JsonArray
                            ArrayList<String> list = new ArrayList<String>(); // 创建热门城市List集合
                            for (int i = 0; i < jsonArray.length(); i++) { // 循环遍历JsonArray
                                JSONObject jsonObject1 = jsonArray.getJSONObject(i); // 获取JsonObject
                                String region_id = jsonObject1.getString("region_id"); // 读取城市的ID region_id
                                String region_name = jsonObject1.getString("region_name"); // 读取城市的名称 region_name
//                              String letter =jsonObject1.getString("letter");
                                list.add(region_name); // 给List中添加城市名称 region_name
                            }

                            SharedPreferences pref = getSharedPreferences("HistoricalCity", MODE_PRIVATE);
                            String historicalCityStr = pref.getString("historicalCity", ""); // 第二个参数为默认值

                            new EasySideBarBuilder(ShareGradeAty.this)
                                    .setTitle("城市选择")
                        /*.setIndexColor(Color.BLUE)*/
                                    .setIndexColor(0xFF0095EE)
                       /* .isLazyRespond(true) // 懒加载模式*/
                                    .setHotCityList(list) // 热门城市列表
                                    .setIndexItems(mIndexItems) // 索引字母
                                    .setLocationCity("天津") // 定位城市
                                    .setHistoricalCity(historicalCityStr) // 上次选择的城市
                                    .setMaxOffset(60) // 索引的最大偏移量
                                    .start();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Object tag, Throwable e) {
                    }

                    @Override
                    public void onCancel(Object tag, Throwable e) {
                    }

                });

    }

    // 数据回调
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case EasySideBarBuilder.CODE_SIDEREQUEST:
                if (data != null) {
                    String city = data.getStringExtra("selected");
                    L.e(data.toString());
//                    Toast.makeText(this,"选择的城市："+city,Toast.LENGTH_SHORT).show();
                    grade_location_tv.setText(city);
//                    changeViewStatus(0);

                    left_tv.setTextColor(ContextCompat.getColor(this, R.color.app_text_color));
                    right_tv.setTextColor(ContextCompat.getColor(this, R.color.app_text_color));
                    left_view.setBackgroundColor(ContextCompat.getColor(this, R.color.white));
                    right_view.setBackgroundColor(ContextCompat.getColor(this, R.color.white));
                    left_tv.setTextColor(Color.parseColor("#E60012"));
                    left_view.setBackgroundColor(Color.parseColor("#E60012"));
                    type = "share";

                    city_name = grade_location_tv.getText().toString();
                    userPst.gradeRank(p, "", "share", grade_location_tv.getText().toString(), true);
                }
                break;

            default:
                break;
        }

    }

}
