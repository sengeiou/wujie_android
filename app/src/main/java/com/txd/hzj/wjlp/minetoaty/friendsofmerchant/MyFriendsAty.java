package com.txd.hzj.wjlp.minetoaty.friendsofmerchant;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;

import cn.gavinliu.android.lib.shapedimageview.ShapedImageView;

/**
 * 创建者：zhangyunfei
 * 创建时间：2019/1/17 13:42
 * 功能描述：以商会友
 */
public class MyFriendsAty extends BaseAty implements TextView.OnEditorActionListener{
    private Context mContext;

    @ViewInject(R.id.titlt_conter_tv)
    private TextView titlt_conter_tv;

    @ViewInject(R.id.titlt_right_tv)
    private TextView titlt_right_tv;


    @ViewInject(R.id.searchEdit)
    private EditText searchEdit;

    @ViewInject(R.id.friendRecyclerView)
    private RecyclerView friendRecyclerView;

    private MyFriendsAdapter mFriendsAdapter;

    private String mSta_mid="1";

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_my_friends;
    }

    @Override
    protected void initialized() {
        mContext = this;
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("以商会友");
        titlt_right_tv.setVisibility(View.VISIBLE);
        titlt_right_tv.setText("+");
        titlt_right_tv.setTextSize(20);
//        mSta_mid = getIntent().getStringExtra("sta_mid");
        searchEdit.setOnEditorActionListener(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        friendRecyclerView.setLayoutManager(layoutManager);
        mFriendsAdapter = new MyFriendsAdapter();
        friendRecyclerView.setAdapter(mFriendsAdapter);
    }

    @Override
    protected void requestData() {

    }

    @Override
    @OnClick({R.id.titlt_right_tv,R.id.groupManagementTv,R.id.addShopKeeperTv})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.titlt_right_tv:
                showPop(view);
                break;
            case R.id.groupManagementTv:

                break;
            case R.id.addShopKeeperTv:
                break;
        }
    }

    private void showPop(View view){
        View contentView = LayoutInflater.from(mContext).inflate(R.layout.pop_myfriends,null);
        TextView changeMemberTv = contentView.findViewById(R.id.changeMemberTv);
        TextView newFriendTv = contentView.findViewById(R.id.newFriendsTv);
        final PopupWindow popupWindow = new PopupWindow(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setContentView(contentView);
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        popupWindow.setFocusable(true);
        popupWindow.showAtLocation(view, Gravity.NO_GRAVITY, (int) (location[0]-view.getWidth()*1.3),location[1]+view.getHeight());
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.alpha = 1.0f;
        window.setAttributes(attributes);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                Window window = getWindow();
                window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                WindowManager.LayoutParams attributes = window.getAttributes();
                attributes.alpha = 1.0f;
                window.setAttributes(attributes);
            }
        });
        changeMemberTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("sta_mid",mSta_mid);
                startActivity(ChangeMembersAty.class,bundle);
                popupWindow.dismiss();

            }
        });

        newFriendTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(NewFriendAty.class,null);
                popupWindow.dismiss();
            }
        });
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH){
            /*隐藏软键盘*/
            InputMethodManager imm = (InputMethodManager) v
                    .getContext().getSystemService(
                            Context.INPUT_METHOD_SERVICE);
            if (imm.isActive()) {
                imm.hideSoftInputFromWindow(
                        v.getApplicationWindowToken(), 0);
            }
            return true;
        }
        return false;
    }


    private static class MyFriendsAdapter extends RecyclerView.Adapter<MyFriendsAdapter.ViewHolder>{

        private Context mContext;
        private ItemAdapter mItemAdapter;
        public MyFriendsAdapter() {
        }

        @NonNull
        @Override
        public MyFriendsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            mContext = parent.getContext();
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_my_friends,parent,false);
            MyFriendsAdapter.ViewHolder holder = new MyFriendsAdapter.ViewHolder(view);
            ViewUtils.inject(holder,view);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull MyFriendsAdapter.ViewHolder holder, int position) {
            LinearLayoutManager layoutManager = new LinearLayoutManager(mContext){
                @Override
                public boolean canScrollVertically() {
                    return false;
                }
            };
            holder.itemRecyclerView.setLayoutManager(layoutManager);
            mItemAdapter = new ItemAdapter();
            holder.itemRecyclerView.setAdapter(mItemAdapter);
        }

        @Override
        public int getItemCount() {
            return 2;
        }

        public static class ViewHolder extends RecyclerView.ViewHolder{

            @ViewInject(R.id.titleTv)
            private TextView titleTv;

            @ViewInject(R.id.itemRecyclerView)
            private RecyclerView itemRecyclerView;

            public ViewHolder(View itemView) {
                super(itemView);
            }
        }
    }

    private static class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder>{

        private Context mContext;
        public ItemAdapter() {
        }

        @NonNull
        @Override
        public ItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            mContext = parent.getContext();
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_my_friends_child,parent,false);
            ViewHolder holder = new ViewHolder(view);
            ViewUtils.inject(holder,view);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 3;
        }

        public static class ViewHolder extends RecyclerView.ViewHolder{

            @ViewInject(R.id.headImg)
            private ShapedImageView headImg;

            @ViewInject(R.id.numTv)
            private TextView numTv;

            @ViewInject(R.id.nameTV)
            private TextView nameTV;

            @ViewInject(R.id.contentTv)
            private TextView contentTv;

            @ViewInject(R.id.timeTv)
            private TextView timeTv;

            public ViewHolder(View itemView) {
                super(itemView);
            }
        }
    }

}
