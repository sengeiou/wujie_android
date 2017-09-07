package com.txd.hzj.wjlp.mellOnLine.gridClassify;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.ants.theantsgo.gson.GsonUtil;
import com.ants.theantsgo.tool.ToolKit;
import com.ants.theantsgo.util.L;
import com.ants.theantsgo.util.ListUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.bean.groupbuy.GroupPager;
import com.txd.hzj.wjlp.http.groupbuy.GroupBuyPst;
import com.txd.hzj.wjlp.mellOnLine.adapter.GroupMemberAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/10 0010
 * 时间：下午 1:21
 * 描述：4-3参团
 * ===============Txunda===============
 */
public class CreateGroupAty extends BaseAty {

    @ViewInject(R.id.titlt_conter_tv)
    public TextView titlt_conter_tv;
    /**
     * 团圆列表
     */
    @ViewInject(R.id.group_member_rv)
    private RecyclerView group_member_rv;

    /**
     * 团状态
     */
    @ViewInject(R.id.group_status_tv)
    private TextView group_status_tv;

    /**
     * 一键开团，参团，团长不能操作
     */
    @ViewInject(R.id.group_operation_tv)
    private TextView group_operation_tv;
    private int status = 0;

    private GroupMemberAdapter groupMemberAdapter;
    private List<GroupPager.Data.PersonBean> data;

    private GroupBuyPst groupBuyPst;
    /**
     * 团购id
     */
    private String log_id = "";

    /**
     * 商品头像
     */
    @ViewInject(R.id.group_goods_pic_iv)
    private ImageView group_goods_pic_iv;
    /**
     * 商品标题
     */
    @ViewInject(R.id.group_goods_name_tv)
    private TextView group_goods_name_tv;
    /**
     * 其他信息
     * 已团10567件·2人团
     */
    @ViewInject(R.id.group_other_info_tv)
    private TextView group_other_info_tv;
    /**
     * 商品价格
     */
    @ViewInject(R.id.group_goods_price_tv)
    private TextView group_goods_price_tv;

    private int size = 0;
    /**
     * 用户id
     */
    private String user_id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
        if (0 == status) {
            titlt_conter_tv.setText("参团");
        } else {
            titlt_conter_tv.setText("开团");
        }
        if (0 != status) {
            group_operation_tv.setText("一键开团");
            group_operation_tv.setEnabled(true);
            group_status_tv.setText("团已满");
            group_operation_tv.setBackgroundResource(R.drawable.shape_good_luck_tv);
        }

        group_member_rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        group_member_rv.setHasFixedSize(true);
        group_member_rv.setItemAnimator(new DefaultItemAnimator());

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_create_group;
    }

    @Override
    protected void initialized() {
        status = getIntent().getIntExtra("status", 0);
        log_id = getIntent().getStringExtra("log_id");
        data = new ArrayList<>();
        groupBuyPst = new GroupBuyPst(this);
        size = ToolKit.dip2px(this, 120);
        user_id = application.getUserInfo().get("user_id");
    }

    @Override
    protected void requestData() {
        groupBuyPst.goGroup(log_id);
    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        if (requestUrl.contains("goGroup")) {
            GroupPager groupPager = GsonUtil.GsonToBean(jsonStr, GroupPager.class);

            GroupPager.Data.GroupInfo groupInfo = groupPager.getData().getInfo();
            Glide.with(this).load(groupInfo.getGoods_img())
                    .override(size, size)
                    .placeholder(R.drawable.ic_default)
                    .centerCrop()
                    .error(R.drawable.ic_default)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(group_goods_pic_iv);

            group_goods_name_tv.setText(groupInfo.getGoods_name());
            group_other_info_tv.setText("已团" + groupInfo.getTotal() + "件●" + groupInfo.getGroup_num() + "人团");
            group_goods_price_tv.setText("￥" + groupInfo.getGroup_price());

            if (user_id.equals(groupInfo.getUser_id())) {// 我是团长
                group_operation_tv.setText("我是团长");
                group_operation_tv.setEnabled(false);
                group_operation_tv.setBackgroundResource(R.drawable.shape_un_operation);
                group_status_tv.setText(groupPager.getData().getDiff());
            } else {// 我不是团长，参团
                group_operation_tv.setText("我要参团");
                group_operation_tv.setEnabled(true);
                group_status_tv.setText(groupPager.getData().getDiff());
                group_operation_tv.setBackgroundResource(R.drawable.shape_good_luck_tv);
            }

            data = groupPager.getData().getPerson();
            if (!ListUtils.isEmpty(data)) {
                groupMemberAdapter = new GroupMemberAdapter(this, data);
                group_member_rv.setAdapter(groupMemberAdapter);
            }
            // =====参团页======
        }
    }
}
