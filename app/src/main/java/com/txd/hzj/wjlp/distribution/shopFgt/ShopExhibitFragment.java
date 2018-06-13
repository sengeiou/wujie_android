package com.txd.hzj.wjlp.distribution.shopFgt;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseFgt;
import com.txd.hzj.wjlp.distribution.adapter.ShopExhibitAdapter;
import com.txd.hzj.wjlp.distribution.bean.ExhibitGoosBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建者：Zyf
 * 功能描述：小店上货fragment
 * 联系方式：无
 */
public class ShopExhibitFragment extends BaseFgt {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //积分
    private TextView internal_tv;
    //代金券
    private TextView cash_coupon_tv;
    //销量
    private TextView sales_volume_tv;
    //价格
    private TextView price_tv;

    private RecyclerView exhibit_recyclerView;

    private Context mContext;

    private String redColor = "#ffe71f19";
    private String blgColor = "#ff333333";
    private Drawable selectId;
    private Drawable twoSelectId;
    private Drawable unSelectId;
    private int internalNum = 0;
    private int cashCouponNum = 0;
    private int salesVolumeNum = 0;
    private int priceNum = 0;
    private ShopExhibitAdapter shopExhibitAdapter;
    private List<ExhibitGoosBean> datas;

    public static ShopExhibitFragment newInstance(String param1, String param2) {
        ShopExhibitFragment fragment = new ShopExhibitFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext=context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_shop_exhibit;
    }

    @Override
    protected void getView(View view) {
        super.getView(view);
        internal_tv = view.findViewById(R.id.internal_tv);
        cash_coupon_tv = view.findViewById(R.id.cash_coupon_tv);
        sales_volume_tv = view.findViewById(R.id.sales_volume_tv);
        price_tv = view.findViewById(R.id.price_tv);
        exhibit_recyclerView=view.findViewById(R.id.exhibit_recyclerView);
        exhibit_recyclerView.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false));
        exhibit_recyclerView.addItemDecoration(new DividerItemDecoration(mContext,DividerItemDecoration.HORIZONTAL));
    }

    @Override
    protected void initialized() {
        selectId = getResources().getDrawable(R.drawable.shopjiantou);
        twoSelectId = getResources().getDrawable(R.drawable.shop_red_down);
        unSelectId = getResources().getDrawable(R.drawable.shopblgjiantou);
        selectId.setBounds(0, 0, selectId.getMinimumWidth(), selectId.getMinimumHeight());
        twoSelectId.setBounds(0, 0, twoSelectId.getMinimumWidth(), twoSelectId.getMinimumWidth());
        unSelectId.setBounds(0, 0, unSelectId.getMinimumWidth(), unSelectId.getMinimumHeight());
    }

    @Override
    protected void requestData() {
        datas=new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            ExhibitGoosBean exhibitGoosBean=new ExhibitGoosBean();
            exhibitGoosBean.setImageUrl("https://gd1.alicdn.com/imgextra/i1/646527539/TB2goIfbiMnBKNjSZFCXXX0KFXa_!!646527539.jpg_400x400.jpg");
            exhibitGoosBean.setGoodsTitle("康尔馨五星级酒店毛巾纯棉加大加厚面巾洗脸全棉吸水男女成人");
            exhibitGoosBean.setDaijinquan("最多可使用50%代金券");
            exhibitGoosBean.setJifen("10");
            exhibitGoosBean.setPrice("1380.00");
            datas.add(exhibitGoosBean);
        }
        shopExhibitAdapter=new ShopExhibitAdapter(datas);
        exhibit_recyclerView.setAdapter(shopExhibitAdapter);
    }


    @Override
    protected void immersionInit() {

    }

    @Override
    @OnClick({R.id.internal_tv, R.id.cash_coupon_tv, R.id.sales_volume_tv, R.id.price_tv})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.internal_tv:
                setChioceItem(0);
                break;
            case R.id.cash_coupon_tv:
                setChioceItem(1);
                break;
            case R.id.sales_volume_tv:
                setChioceItem(2);
                break;
            case R.id.price_tv:
                setChioceItem(3);
                break;
        }
    }

    private void setChioceItem(int index) {
        clearChioce();
        switch (index) {
            case 0:
                internal_tv.setTextColor(Color.parseColor(redColor));
                internal_tv.setCompoundDrawables(null, null, internalNum % 2 == 0 ? selectId : twoSelectId, null);
                internalNum++;
                cashCouponNum=0;
                salesVolumeNum=0;
                priceNum=0;
                break;
            case 1:
                cash_coupon_tv.setTextColor(Color.parseColor(redColor));
                cash_coupon_tv.setCompoundDrawables(null, null, cashCouponNum % 2 == 0 ? selectId : twoSelectId, null);
                internalNum=0;
                cashCouponNum++;
                salesVolumeNum=0;
                priceNum=0;
                break;
            case 2:
                sales_volume_tv.setTextColor(Color.parseColor(redColor));
                sales_volume_tv.setCompoundDrawables(null, null, salesVolumeNum % 2 == 0 ? selectId : twoSelectId, null);
                internalNum=0;
                cashCouponNum=0;
                salesVolumeNum++;
                priceNum=0;
                break;
            case 3:
                price_tv.setTextColor(Color.parseColor(redColor));
                price_tv.setCompoundDrawables(null, null, priceNum % 2 == 0 ? selectId : twoSelectId, null);
                internalNum=0;
                cashCouponNum=0;
                salesVolumeNum=0;
                priceNum++;
                break;
        }
    }

    private void clearChioce() {
        internal_tv.setTextColor(Color.parseColor(blgColor));
        internal_tv.setCompoundDrawables(null, null, unSelectId, null);
        cash_coupon_tv.setTextColor(Color.parseColor(blgColor));
        cash_coupon_tv.setCompoundDrawables(null, null, unSelectId, null);
        sales_volume_tv.setTextColor(Color.parseColor(blgColor));
        sales_volume_tv.setCompoundDrawables(null, null, unSelectId, null);
        price_tv.setTextColor(Color.parseColor(blgColor));
        price_tv.setCompoundDrawables(null, null, unSelectId, null);
    }

}
