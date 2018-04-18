package com.txd.hzj.wjlp.minetoAty.myGrade;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ants.theantsgo.tool.ToolKit;
import com.ants.theantsgo.util.JSONUtils;
import com.ants.theantsgo.util.L;
import com.ants.theantsgo.view.pulltorefresh.PullToRefreshBase;
import com.ants.theantsgo.view.pulltorefresh.PullToRefreshListView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.http.user.UserPst;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.gavinliu.android.lib.shapedimageview.ShapedImageView;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/21 0021
 * 时间：17:00
 * 描述：我的推荐
 * ===============Txunda===============
 */
public class RecommendSuccessAty extends BaseAty {

    @ViewInject(R.id.titlt_conter_tv)
    public TextView titlt_conter_tv;

    @ViewInject(R.id.share_times_lv)
    private PullToRefreshListView share_times_lv;

    private ReSuccessAdapter reSuccessAdapter;

    @ViewInject(R.id.no_data_layout)
    private LinearLayout layout;

    private List<Map<String, String>> list;

    private int size = 0;
    private int p = 1;
    private UserPst userPst;

    private String parent_id;
    private String nickname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);

        share_times_lv.setEmptyView(layout);

        share_times_lv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                p = 1;
                userPst.myRecommend(p, parent_id, false);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                p++;
                userPst.myRecommend(p, parent_id, false);
            }
        });
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_share_times;
    }

    @Override
    protected void initialized() {
        size = ToolKit.dip2px(this, 60);
        list = new ArrayList<>();
        userPst = new UserPst(this);
        reSuccessAdapter = new ReSuccessAdapter();

        parent_id = getIntent().getStringExtra("parent_id"); // 查询id
        nickname = getIntent().getStringExtra("nickname"); // 查询id

        titlt_conter_tv.setText((nickname.equals("") ? "我" : nickname) + "的推荐");

        share_times_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putString("parent_id", list.get(position - 1).get("id"));
                bundle.putString("nickname", list.get(position - 1).get("nickname"));
                startActivity(RecommendSuccessAty.class, bundle);
            }
        });

        userPst.myRecommend(p, parent_id, true);

    }

    @Override
    protected void requestData() {
    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        Map<String, String> map = JSONUtils.parseKeyAndValueToMap(jsonStr);
        if (requestUrl.contains("myRecommend")) {

            list.removeAll(list);

            if (ToolKit.isList(map, "data")) {
                if (1 == p) {
                    list = JSONUtils.parseKeyAndValueToMapList(map.get("data"));
                    share_times_lv.setAdapter(reSuccessAdapter);
                } else {
                    list.addAll(JSONUtils.parseKeyAndValueToMapList(map.get("data")));
                    reSuccessAdapter.notifyDataSetChanged();
                }
                reSuccessAdapter.notifyDataSetChanged();

            }
            share_times_lv.onRefreshComplete();
        }
    }

    @Override
    public void onError(String requestUrl, Map<String, String> error) {
        super.onError(requestUrl, error);
        share_times_lv.onRefreshComplete();
    }

    private class ReSuccessAdapter extends BaseAdapter {

        private RSVH rsvh;

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Map<String, String> getItem(int i) {
            return list.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            Map<String, String> map = getItem(i);
            if (view == null) {
                view = LayoutInflater.from(RecommendSuccessAty.this).inflate(R.layout.item_recomment_success_lv,
                        viewGroup, false);
                rsvh = new RSVH();
                ViewUtils.inject(rsvh, view);
                view.setTag(rsvh);
            } else {
                rsvh = (RSVH) view.getTag();
            }

            Glide.with(RecommendSuccessAty.this).load(map.get("head_pic"))
                    .error(R.drawable.ic_default)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .placeholder(R.drawable.ic_default)
                    .override(size, size)
                    .into(rsvh.my_recommend_pic_iv);

            rsvh.my_recommend_nick_tv.setText(map.get("nickname"));
            rsvh.recommend_num_tv.setText("成功推荐" + map.get("recommend_num") + "人");
            rsvh.user_phone_tv.setText(map.get("phone"));

            String create_time = map.get("create_time");
            String[] split = create_time.split(" ");
            rsvh.my_recommend_time_tv.setText("推荐时间\n" + split[0] + "\n" + split[1]);


            return view;
        }

        private class RSVH {

            @ViewInject(R.id.my_recommend_pic_iv)
            private ShapedImageView my_recommend_pic_iv;

            @ViewInject(R.id.my_recommend_nick_tv)
            private TextView my_recommend_nick_tv;

            @ViewInject(R.id.recommend_num_tv)
            private TextView recommend_num_tv;

            @ViewInject(R.id.my_recommend_time_tv)
            private TextView my_recommend_time_tv;

            @ViewInject(R.id.user_phone_tv)
            private TextView user_phone_tv;

        }

    }

}
