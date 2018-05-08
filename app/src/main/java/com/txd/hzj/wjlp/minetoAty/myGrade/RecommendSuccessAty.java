package com.txd.hzj.wjlp.minetoAty.myGrade;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
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
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.google.gson.Gson;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.bean.MyRecommendBean;
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

    private List<MyRecommendBean.DataBean> list;

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
                userPst.myRecommendNew(p, parent_id, false);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                p++;
                userPst.myRecommendNew(p, parent_id, false);
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
        nickname = getIntent().getStringExtra("nickname"); // 查询name

        titlt_conter_tv.setText((nickname.equals("") ? "我" : nickname) + "的推荐");
        final String tempInfiniteStr = getIntent().getStringExtra("infinite");
        if (!TextUtils.isEmpty(tempInfiniteStr)) {
            if ("1".equals(tempInfiniteStr)) {
                share_times_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Bundle bundle = new Bundle();
                        bundle.putString("parent_id", list.get(position - 1).getId());
                        bundle.putString("nickname", list.get(position - 1).getNickname());
                        bundle.putString("infinite", tempInfiniteStr);
                        startActivity(RecommendSuccessAty.class, bundle);
                    }
                });
            } else
                share_times_lv.setOnItemClickListener(null);
        } else {
            share_times_lv.setOnItemClickListener(null);
        }
        userPst.myRecommendNew(p, parent_id, true);

    }

    @Override
    protected void requestData() {
    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        L.e("=========myRecommendNew=============" + jsonStr);
        Map<String, String> map = JSONUtils.parseKeyAndValueToMap(jsonStr);
        if (requestUrl.contains("myRecommendNew")) {
            Gson gson = new Gson();
            MyRecommendBean myRecommendBean = gson.fromJson(jsonStr, MyRecommendBean.class);

            if (myRecommendBean != null) {
                if (1 == p) {
                    list.removeAll(list);
                    list = myRecommendBean.getData();
                    share_times_lv.setAdapter(reSuccessAdapter);
                } else {
                    list.addAll(myRecommendBean.getData());
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
        public MyRecommendBean.DataBean getItem(int i) {
            return list.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            MyRecommendBean.DataBean dataBean = getItem(i);
            if (view == null) {
                view = LayoutInflater.from(RecommendSuccessAty.this).inflate(R.layout.item_share_grade_lv_new, viewGroup, false);
                rsvh = new RSVH();
                ViewUtils.inject(rsvh, view);
                view.setTag(rsvh);
            } else {
                rsvh = (RSVH) view.getTag();
            }

            // 设置头像
            Glide.with(RecommendSuccessAty.this)
                    .load(dataBean.getHead_pic())
                    .error(R.drawable.ic_default)
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .placeholder(R.drawable.ic_default)
                    .into(rsvh.itemShareGrade_head_imgv);
            rsvh.itemShareGrade_nickName_tv.setText(dataBean.getNickname());// 设置昵称
            String member_coding = dataBean.getMember_coding(); // 获取会员级别
            member_coding = member_coding == null ? "1" : member_coding;
            rsvh.itemShareGrade_memberCoding_tv.setText(member_coding.equals("3") ? "优享会员" : member_coding.equals("2") ? "无忧会员" : "无界会员"); // 1无界 2无忧 3优享
            rsvh.itemShareGrade_time_tv.setText(dataBean.getTime()); // 设置时间
            rsvh.itemShareGrade_userId_tv.setText("ID:" + dataBean.getId()); // 设置会员ID
            rsvh.itemShareGrade_num_tv.setText(dataBean.getNum() == null ? "0" : dataBean.getNum()); // 成功推荐人数
            rsvh.itemShareGrade_phone_tv.setText(dataBean.getPhone()); // 设置电话号码
            // 设置无界伞的图标
            Glide.with(RecommendSuccessAty.this).load(dataBean.getUmbrella_icon()).error(R.drawable.ic_default).diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .placeholder(R.drawable.ic_default).into(rsvh.itemShareGrade_umbrellaIconWuJie_imgv);
            // 设置无忧伞的图标
            Glide.with(RecommendSuccessAty.this).load(dataBean.getUmbrella_icon()).error(R.drawable.ic_default).diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .placeholder(R.drawable.ic_default).into(rsvh.itemShareGrade_umbrellaIconWuYou_imgv);
            // 设置优享伞的图标
            Glide.with(RecommendSuccessAty.this).load(dataBean.getUmbrella_icon()).error(R.drawable.ic_default).diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .placeholder(R.drawable.ic_default).into(rsvh.itemShareGrade_umbrellaIconYouXiang_imgv);
            // 设置无界直推的图标
            Glide.with(RecommendSuccessAty.this).load(dataBean.getStraight_icon()).error(R.drawable.ic_default).diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .placeholder(R.drawable.ic_default).into(rsvh.itemShareGrade_straightIconWuJie_imgv);
            // 设置无忧直推的图标
            Glide.with(RecommendSuccessAty.this).load(dataBean.getStraight_icon()).error(R.drawable.ic_default).diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .placeholder(R.drawable.ic_default).into(rsvh.itemShareGrade_straightIconWuYou_imgv);
            // 设置优享直推的图标
            Glide.with(RecommendSuccessAty.this).load(dataBean.getStraight_icon()).error(R.drawable.ic_default).diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .placeholder(R.drawable.ic_default).into(rsvh.itemShareGrade_straightIconYouXiang_imgv);
            rsvh.itemShareGrade_umbrellaCodingOne_tv.setText(dataBean.getUmbrella_coding_one()); // 设置伞下无界人数
            rsvh.itemShareGrade_umbrellaCodingTwo_tv.setText(dataBean.getUmbrella_coding_two()); // 设置伞下无忧人数
            rsvh.itemShareGrade_umbrellaCodingThree_tv.setText(dataBean.getUmbrella_coding_three()); // 设置伞下优享人数
            rsvh.itemShareGrade_codingOne_tv.setText(dataBean.getCoding_one()); // 设置直推无界人数
            rsvh.itemShareGrade_codingTwo_tv.setText(dataBean.getCoding_two()); // 设置直推无忧人数
            rsvh.itemShareGrade_codingThree_tv.setText(dataBean.getCoding_three()); // 设置直推优享人数

            return view;
        }

        private class RSVH {

            // 头像
            @ViewInject(R.id.itemShareGrade_head_imgv)
            private ShapedImageView itemShareGrade_head_imgv;
            // 昵称
            @ViewInject(R.id.itemShareGrade_nickName_tv)
            private TextView itemShareGrade_nickName_tv;
            // 会员级别
            @ViewInject(R.id.itemShareGrade_memberCoding_tv)
            private TextView itemShareGrade_memberCoding_tv;
            // 时间
            @ViewInject(R.id.itemShareGrade_time_tv)
            private TextView itemShareGrade_time_tv;
            // 用户ID
            @ViewInject(R.id.itemShareGrade_userId_tv)
            private TextView itemShareGrade_userId_tv;
            // 成功推荐人数
            @ViewInject(R.id.itemShareGrade_num_tv)
            private TextView itemShareGrade_num_tv;
            // 电话
            @ViewInject(R.id.itemShareGrade_phone_tv)
            private TextView itemShareGrade_phone_tv;
            // 无界上方伞图标
            @ViewInject(R.id.itemShareGrade_umbrellaIconWuJie_imgv)
            private ImageView itemShareGrade_umbrellaIconWuJie_imgv;
            // 伞图标处对应的人数
            @ViewInject(R.id.itemShareGrade_umbrellaCodingOne_tv)
            private TextView itemShareGrade_umbrellaCodingOne_tv;
            // 无界下方直推图标
            @ViewInject(R.id.itemShareGrade_straightIconWuJie_imgv)
            private ImageView itemShareGrade_straightIconWuJie_imgv;
            // 直推图标对应的人数
            @ViewInject(R.id.itemShareGrade_codingOne_tv)
            private TextView itemShareGrade_codingOne_tv;
            // 无忧上方的伞图标
            @ViewInject(R.id.itemShareGrade_umbrellaIconWuYou_imgv)
            private ImageView itemShareGrade_umbrellaIconWuYou_imgv;
            // 伞图标对应的人数
            @ViewInject(R.id.itemShareGrade_umbrellaCodingTwo_tv)
            private TextView itemShareGrade_umbrellaCodingTwo_tv;
            // 无忧下方的直推图标
            @ViewInject(R.id.itemShareGrade_straightIconWuYou_imgv)
            private ImageView itemShareGrade_straightIconWuYou_imgv;
            // 直推图标对应的人数
            @ViewInject(R.id.itemShareGrade_codingTwo_tv)
            private TextView itemShareGrade_codingTwo_tv;
            // 优享上方对应的伞图标
            @ViewInject(R.id.itemShareGrade_umbrellaIconYouXiang_imgv)
            private ImageView itemShareGrade_umbrellaIconYouXiang_imgv;
            // 伞图标对应的人数
            @ViewInject(R.id.itemShareGrade_umbrellaCodingThree_tv)
            private TextView itemShareGrade_umbrellaCodingThree_tv;
            // 优享下方的直推图标
            @ViewInject(R.id.itemShareGrade_straightIconYouXiang_imgv)
            private ImageView itemShareGrade_straightIconYouXiang_imgv;
            // 直推图标对应的人数
            @ViewInject(R.id.itemShareGrade_codingThree_tv)
            private TextView itemShareGrade_codingThree_tv;

        }
    }

}
