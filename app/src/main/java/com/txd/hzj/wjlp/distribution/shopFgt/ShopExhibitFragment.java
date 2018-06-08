package com.txd.hzj.wjlp.distribution.shopFgt;

import android.os.Bundle;

import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseFgt;

public class ShopExhibitFragment extends BaseFgt {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public ShopExhibitFragment() {
        // Required empty public constructor
    }

    public static ShopExhibitFragment newInstance(String param1, String param2) {
        ShopExhibitFragment fragment = new ShopExhibitFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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
    protected void initialized() {

    }

    @Override
    protected void requestData() {

    }


    @Override
    protected void immersionInit() {

    }
}
