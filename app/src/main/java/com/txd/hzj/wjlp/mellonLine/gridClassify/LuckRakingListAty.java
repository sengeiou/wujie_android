package com.txd.hzj.wjlp.mellonLine.gridClassify;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ants.theantsgo.base.BaseView;
import com.ants.theantsgo.config.Config;
import com.ants.theantsgo.httpTools.ApiTool2;
import com.ants.theantsgo.tools.ObserTool;
import com.bumptech.glide.Glide;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;
import com.txd.hzj.wjlp.bean.AwardRankListBean;

import java.util.List;

import cn.gavinliu.android.lib.shapedimageview.ShapedImageView;

/**
 * 创建者：zhangyunfei
 * 创建时间：2018/12/25 13:57
 * 功能描述：
 */
public class LuckRakingListAty extends BaseAty {
    @ViewInject(R.id.titlt_conter_tv)
    public TextView titlt_conter_tv;
    @ViewInject(R.id.personalLayout)
    public RelativeLayout personalLayout;
    @ViewInject(R.id.numTv)
    public TextView numTv;
    @ViewInject(R.id.headImg)
    public ShapedImageView headImg;
    @ViewInject(R.id.nameTv)
    public TextView nameTv;
    @ViewInject(R.id.priceTv)
    public TextView priceTv;
    @ViewInject(R.id.rakingList)
    public ListView rakingList;
    private String mA_id;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_luck_raking_list;
    }

    @Override
    protected void initialized() {
        titlt_conter_tv.setText("手气值排行榜");
        mA_id = getIntent().getStringExtra("a_id");
    }

    @Override
    protected void requestData() {
        postRanking(mA_id,"1","",this);
    }

    private void postRanking(String a_id, String p, String size,BaseView baseView) {
        ApiTool2 apiTool2 = new ApiTool2();
        RequestParams requestParams = new RequestParams();
        requestParams.addBodyParameter("a_id", a_id);
        requestParams.addBodyParameter("p", p);
        requestParams.addBodyParameter("size", size);
        apiTool2.postApi(Config.SHARE_URL + "index.php/Api/GroupBuy/getAwardRankList", requestParams, baseView);
    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        ObserTool.gainInstance().jsonToBean(jsonStr, AwardRankListBean.class, new ObserTool.BeanListener() {
            @Override
            public void returnObj(Object t) {
                AwardRankListBean awardRankListBean= (AwardRankListBean) t;
                AwardRankListBean.DataBean data = awardRankListBean.getData();
                AwardRankListBean.DataBean.UserInfoBean user_info = data.getUser_info();
                if (user_info != null){
                    personalLayout.setVisibility(View.VISIBLE);
                    String rank = user_info.getRank();
                    if ("1".equals(rank)){
                        numTv.setBackgroundResource(R.drawable.icon_one);
                        numTv.setText("");
                    }else if ("2".equals(rank)){
                        numTv.setBackgroundResource(R.drawable.icon_two);
                        numTv.setText("");
                    }else if ("3".equals(rank)){
                        numTv.setBackgroundResource(R.drawable.icon_three);
                        numTv.setText("");
                    }else {
                        numTv.setBackground(null);
                        numTv.setText(user_info.getRank());
                    }
                    nameTv.setText(user_info.getUser_name());
                    priceTv.setText(user_info.getUser_count());
                    Glide.with(LuckRakingListAty.this).load(user_info.getHead_pic()).into(headImg);
                }
                List<AwardRankListBean.DataBean.RankListBean> rank_list = data.getRank_list();
                rakingList.setAdapter(new LuckAdapter(rank_list,LuckRakingListAty.this));
            }
        });
    }


    public class LuckAdapter extends BaseAdapter {
        private List<AwardRankListBean.DataBean.RankListBean> mList;
        private Context mContext;

        public LuckAdapter(List<AwardRankListBean.DataBean.RankListBean> list, Context context) {
            mList = list;
            mContext = context;
        }

        @Override
        public int getCount() {
            return mList.size()>0?mList.size():0;
        }

        @Override
        public Object getItem(int position) {
            return mList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null){
                convertView = LayoutInflater.from(mContext).inflate(R.layout.luck_item,null);
                holder = new ViewHolder();
                ViewUtils.inject(holder, convertView);
                convertView.setTag(holder);
            }else {
                holder = (ViewHolder) convertView.getTag();
            }
            AwardRankListBean.DataBean.RankListBean rankListBean = mList.get(position);
            if (position == 0){
                holder.numTv.setBackgroundResource(R.drawable.icon_one);
                holder.numTv.setText("");
            }else if (position == 1){
                holder.numTv.setBackgroundResource(R.drawable.icon_two);
                holder.numTv.setText("");
            }else if (position == 2){
                holder.numTv.setBackgroundResource(R.drawable.icon_three);
                holder.numTv.setText("");
            }else {
                holder.numTv.setBackground(null);
                holder.numTv.setText(rankListBean.getRank());
            }
            holder.nameTv.setText(rankListBean.getUser_name());
            holder.priceTv.setText(rankListBean.getUser_count());
            Glide.with(mContext).load(rankListBean.getHead_pic()).into(holder.headImg);

            return convertView;
        }

        class ViewHolder{
            @ViewInject(R.id.numTv)
            private TextView numTv;
            @ViewInject(R.id.nameTv)
            private TextView nameTv;
            @ViewInject(R.id.priceTv)
            private TextView priceTv;
            @ViewInject(R.id.headImg)
            private ShapedImageView headImg;
        }
    }
}
