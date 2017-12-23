package com.txd.hzj.wjlp.mellOnLine.gridClassify;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ants.theantsgo.gson.GsonUtil;
import com.ants.theantsgo.tool.ToolKit;
import com.ants.theantsgo.util.JSONUtils;
import com.ants.theantsgo.util.L;
import com.ants.theantsgo.util.ListUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.bean.GoodsAttrs;
import com.txd.hzj.wjlp.bean.groupbuy.GroupPager;
import com.txd.hzj.wjlp.http.groupbuy.GroupBuyPst;
import com.txd.hzj.wjlp.mellOnLine.adapter.GroupMemberAdapter;
import com.txd.hzj.wjlp.txunda_lh.http.GroupBuyOrder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
//    @ViewInject(R.id.group_status_tv)
//    private TextView group_status_tv;

    /**
     * 一键开团，参团，团长不能操作
     */
    @ViewInject(R.id.group_operation_tv)
    private TextView group_operation_tv;
    private int status = 0;

    private GroupMemberAdapter groupMemberAdapter;

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

    /**
     * 用户id
     */
    private String user_id = "";
    List<Map<String, String>> list = new ArrayList<>();
    @ViewInject(R.id.textview)
    private TextView textview;
    private String goods_id;
    ArrayList<GoodsAttrs> list_;
    ArrayList<GoodsAttrs.product> list_p;

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
//            group_status_tv.setText("团已满");
            group_operation_tv.setBackgroundResource(R.drawable.shape_good_luck_tv);
        }

        group_member_rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        group_member_rv.setHasFixedSize(true);
        group_member_rv.setItemAnimator(new DefaultItemAnimator());
        group_operation_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {// list_, list_p,
                toAttrs(v, 0, "4", goods_id, data.get("goods_img"), data.get("shop_price"), getIntent().getStringExtra("group_buy_id") + "-" + log_id);
            }
        });
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.aty_create_group;
    }

    @Override
    protected void initialized() {
        list_ = getIntent().getParcelableArrayListExtra("list");
        list_p = getIntent().getParcelableArrayListExtra("list_p");
        goods_id = getIntent().getStringExtra("goods_id");
        status = getIntent().getIntExtra("status", 0);
        log_id = getIntent().getStringExtra("id");
        groupBuyPst = new GroupBuyPst(this);

//        user_id = application.getUserInfo().get("user_id");
    }

    @Override
    protected void requestData() {
//        groupBuyPst.goGroup(log_id);
        GroupBuyOrder.offered(log_id, this);
        showProgressDialog();
    }

    Map<String, String> data;
    Map<String, String> head;
    List<Map<String, String>> list_pic;
    List<Map<String, String>> offered;
    StringBuffer buffer;

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        data = JSONUtils.parseKeyAndValueToMap(jsonStr);
        data = JSONUtils.parseKeyAndValueToMap(data.get("data"));
        if (requestUrl.contains("offered")) {
            list_pic = JSONUtils.parseKeyAndValueToMapList(data.get("head_pic"));
            offered = JSONUtils.parseKeyAndValueToMapList(data.get("offered"));
            buffer = new StringBuffer();
            for (int i = 0; i < offered.size(); i++) {
                buffer.append(" · ");
                buffer.append(offered.get(i).get("oneself"));
                buffer.append("\n");
            }
            textview.setText(buffer.toString());
            if (!ListUtils.isEmpty(list_pic)) {
                head = new HashMap<>();
                head.put("type", data.get("is_colonel"));
                head.put("pic", list_pic.get(0).get("pic"));
                list_pic.clear();
                list_pic.add(head);
            }
            head = new HashMap<>();
            head.put("type", data.get("is_colonel"));
            head.put("pic", data.get("colonel_head_pic"));
            list_pic.add(head);
            groupMemberAdapter = new GroupMemberAdapter(this, list_pic);
            group_member_rv.setAdapter(groupMemberAdapter);
            Glide.with(this).load(data.get("goods_img"))
                    .placeholder(R.drawable.ic_default)
                    .centerCrop()
                    .error(R.drawable.ic_default)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(group_goods_pic_iv);
            group_goods_name_tv.setText(data.get("goods_name"));
            group_other_info_tv.setText("已团" + data.get("already") + "件●" + data.get("number"));
            group_goods_price_tv.setText("￥" + data.get("shop_price"));
            if (data.get("is_colonel").equals("1")) {// 我是团长
                group_operation_tv.setText("我是团长");
                group_operation_tv.setEnabled(false);
                group_operation_tv.setBackgroundResource(R.drawable.shape_un_operation);
//                group_status_tv.setText(groupPager.getData().getDiff());
            } else {// 我不是团长，参团
                group_operation_tv.setText("我要参团");
                group_operation_tv.setEnabled(true);
//                group_status_tv.setText(groupPager.getData().getDiff());
                group_operation_tv.setBackgroundResource(R.drawable.shape_good_luck_tv);
            }
        }

//        if (requestUrl.contains("goGroup")) {
//            GroupPager groupPager = GsonUtil.GsonToBean(jsonStr, GroupPager.class);
//
//            GroupPager.Data.GroupInfo groupInfo = groupPager.getData().getInfo();
//            Glide.with(this).load(groupInfo.getGoods_img())
//                    .override(size, size)
//                    .placeholder(R.drawable.ic_default)
//                    .centerCrop()
//                    .error(R.drawable.ic_default)
//                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
//                    .into(group_goods_pic_iv);
//
//
//
//
//
//
//
//            data = groupPager.getData().getPerson();
//            if (!ListUtils.isEmpty(data)) {
//                groupMemberAdapter = new GroupMemberAdapter(this, data);
//                group_member_rv.setAdapter(groupMemberAdapter);
//            }
//            // =====参团页======
//        }
    }
}
