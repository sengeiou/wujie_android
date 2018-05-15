package com.txd.hzj.wjlp.mellOnLine.gridClassify;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ants.theantsgo.tools.ObserTool;
import com.ants.theantsgo.util.L;
import com.ants.theantsgo.util.ListUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.bean.GoodsAttrs;
import com.txd.hzj.wjlp.bean.commodity.FirstListBean;
import com.txd.hzj.wjlp.bean.commodity.FirstValBean;
import com.txd.hzj.wjlp.bean.commodity.HeadPicBean;
import com.txd.hzj.wjlp.bean.commodity.OfferedBean;
import com.txd.hzj.wjlp.bean.commodity.OfferedDataBean;
import com.txd.hzj.wjlp.bean.commodity.OfferedOfferBean;
import com.txd.hzj.wjlp.http.groupbuy.GroupBuyPst;
import com.txd.hzj.wjlp.mellOnLine.adapter.GroupMemberAdapter;
import com.txd.hzj.wjlp.new_wjyp.http.GroupBuyOrder;
import com.txd.hzj.wjlp.shoppingCart.BuildOrderAty;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.iwgang.countdownview.CountdownView;

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
     * 倒计时模块
     */
    @ViewInject(R.id.create_group_ll)
    private LinearLayout create_group_ll;
    /**
     * 倒计时控件
     * */
    @ViewInject(R.id.times_id)
    private CountdownView times;
    /**
     * 剩余成团人数
     */
    @ViewInject(R.id.text_nums)
    private TextView textNums;
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
    private OfferedBean data;
    private Long i1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showStatusBar(R.id.title_re_layout);
        if (0 == status) {
            titlt_conter_tv.setText("拼单");
        } else {
            titlt_conter_tv.setText("拼单");
        }
        if (0 != status) {
            group_operation_tv.setText("一键拼单");
            group_operation_tv.setEnabled(true);
//            group_status_tv.setText("团已满");
            group_operation_tv.setBackgroundResource(R.drawable.shape_good_luck_tv);
        }

        //times.setStopTime(Long.valueOf("14903387320000"));
        group_member_rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        group_member_rv.setHasFixedSize(true);
        group_member_rv.setItemAnimator(new DefaultItemAnimator());
        group_operation_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                List<FirstListBean> goods_attr = (List<FirstListBean>) intent.getSerializableExtra("goods_attr_first");
                List<FirstValBean> goods_val = (List<FirstValBean>) intent.getSerializableExtra("first_val");
                toAttrs(v,
                        0,
                        "4",
                        goods_id,
                        data.getGoods_img(),
                        data.getShop_price(),
                        getIntent().getStringExtra("group_buy_id") + "-" + log_id,
                        goods_attr, goods_val,
                        getIntent().getStringExtra("is_attr")
                );

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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000) {
            L.e("返回商品详情");
            if (resultCode == 0x0001) {
                Bundle bundle = new Bundle();
                bundle.putString("mid", data.getStringExtra("mid"));
                bundle.putString("type",data.getStringExtra("type"));
                bundle.putString("goods_id", data.getStringExtra("goods_id"));
                bundle.putString("group_buy_id",data.getStringExtra("group_buy_id"));
                String order_id=data.getStringExtra("order_id");
                if(!android.text.TextUtils.isEmpty(order_id));
                bundle.putString("order_id",order_id);
                bundle.putString("num", data.getStringExtra("num"));
                bundle.putString("product_id", data.getStringExtra("product_id"));
                startActivity(BuildOrderAty.class, bundle);
            }
        }
    }

    @Override
    protected void requestData() {
        GroupBuyOrder.offered(log_id, this);
        showProgressDialog();
    }

    List<HeadPicBean> list_pic;
    List<OfferedOfferBean> offered;
    StringBuffer buffer;

    @Override
    public void onComplete(final String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        Log.i("所有接口", jsonStr.toString());
        if (requestUrl.contains("offered")) {
            ObserTool.gainInstance().jsonToBean(jsonStr, OfferedDataBean.class, new ObserTool.BeanListener() {
                @Override
                public void returnObj(Object t) {
                    OfferedDataBean offeredDataBean = (OfferedDataBean) t;
                    data = offeredDataBean.getData();
                    list_pic = data.getHead_pic();
                    offered = data.getOffered();
                    buffer = new StringBuffer();

                    /**
                     * 倒计时
                     * */

                    textNums.setText(data.getM_short());
                    i1 = Long.valueOf(data.getEnd_time()) - Long.valueOf(data.getSys_time());
                    Log.i("倒计时时间", i1 + "");
                    times.start((10000+i1)*1000);
                    for (int i = 0; i < offered.size(); i++) {
                        buffer.append(" · ");
                        buffer.append(offered.get(i).getOneself());
                        buffer.append("\n");
                    }
                    textview.setText(buffer.toString());
                    if (!ListUtils.isEmpty(list_pic)) {
                        HeadPicBean head = new HeadPicBean();
                        head.setPic(list_pic.get(0).getPic());
                        head.setType(data.getIs_colonel());
                        list_pic.clear();
                        list_pic.add(head);
                    }
                    HeadPicBean head1 = new HeadPicBean();
                    head1.setType(data.getIs_colonel());
                    head1.setPic(data.getColonel_head_pic());
                    list_pic.add(head1);

                    groupMemberAdapter = new GroupMemberAdapter(CreateGroupAty.this, list_pic);
                    group_member_rv.setAdapter(groupMemberAdapter);
                    Glide.with(CreateGroupAty.this).load(data.getGoods_img())
                            .placeholder(R.drawable.ic_default)
                            .centerCrop()
                            .error(R.drawable.ic_default)
                            .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                            .into(group_goods_pic_iv);
                    group_goods_name_tv.setText(data.getGoods_name());
                    group_other_info_tv.setText("已团" + data.getAlready() + "件●" + data.getNumber());
                    group_goods_price_tv.setText("￥" + data.getShop_price());
                    if (data.getIs_colonel().equals("1")) {// 我是团长
                        group_operation_tv.setText("我是团长");
                        group_operation_tv.setEnabled(false);
                        group_operation_tv.setBackgroundResource(R.drawable.shape_un_operation);
//                group_status_tv.setText(groupPager.getData().getDiff());
                    } else {// 我不是团长，参团
                        group_operation_tv.setText("一键参团");
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

//            data = groupPager.getData().getPerson();
//            if (!ListUtils.isEmpty(data)) {
//                groupMemberAdapter = new GroupMemberAdapter(this, data);
//                group_member_rv.setAdapter(groupMemberAdapter);
//            }
//            // =====参团页======
//        }

            });
        }
    }
}
