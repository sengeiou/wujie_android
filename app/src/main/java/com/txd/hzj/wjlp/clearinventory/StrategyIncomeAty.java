package com.txd.hzj.wjlp.clearinventory;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ants.theantsgo.base.BaseView;
import com.ants.theantsgo.config.Config;
import com.ants.theantsgo.httpTools.ApiTool2;
import com.ants.theantsgo.util.JSONUtils;
import com.bumptech.glide.Glide;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseAty;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.gavinliu.android.lib.shapedimageview.ShapedImageView;

/**
 * 创建者：zhangyunfei
 * 创建时间：2019/3/27 14:01
 * 功能描述：
 */
public class StrategyIncomeAty extends BaseAty {
    @ViewInject(R.id.titlt_conter_tv)
    private TextView titlt_conter_tv;
    @ViewInject(R.id.llyt_week)
    private LinearLayout llyt_week;
    @ViewInject(R.id.llyt_month)
    private LinearLayout llyt_month;
    @ViewInject(R.id.llyt_year)
    private LinearLayout llyt_year;
    @ViewInject(R.id.view_week)
    private View view_week;
    @ViewInject(R.id.view_month)
    private View view_month;
    @ViewInject(R.id.view_year)
    private View view_year;
    @ViewInject(R.id.line_chart)
    private LineChart lineChart;

    @ViewInject(R.id.rankTv)
    private TextView rankTv;

    @ViewInject(R.id.shapeImg)
    private ShapedImageView shapeImg;


    @ViewInject(R.id.nameTv)
    private TextView nameTv;


    @ViewInject(R.id.incomeTv)
    private TextView incomeTv;


    @ViewInject(R.id.recyclerView)
    private RecyclerView recyclerView;
    /**
     * 1:日、2:月、3:年
     */
    private String mType = "1";
    private Context mContext;
    private ArrayList<String> bottomList;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_strategy_income;
    }

    @Override
    protected void initialized() {
        mContext = this;
        showStatusBar(R.id.title_re_layout);
        titlt_conter_tv.setText("寄售收益");
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);
                if (parent.getChildLayoutPosition(view) == parent.getAdapter().getItemCount() - 1) {
                    outRect.bottom = 15;
                }
            }
        });
    }

    @Override
    protected void requestData() {
        getRevenueData(mType, this);
    }

    public void getRevenueData(String type, BaseView baseView) {
        RequestParams params = new RequestParams();
        ApiTool2 apiTool2 = new ApiTool2();
        params.addBodyParameter("type", type);
        apiTool2.postApi(Config.SHARE_URL + "index.php/Api/Clean/getRevenue", params, baseView);
    }


    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        Map<String, String> map = JSONUtils.parseKeyAndValueToMap(jsonStr);
        if (requestUrl.endsWith("getRevenue")) {
            Map<String, String> data = JSONUtils.parseKeyAndValueToMap(map.get("data"));
            ArrayList<Map<String, String>> mapArrayList = JSONUtils.parseKeyAndValueToMapList(data.get("my_rank"));
            if (mapArrayList != null && mapArrayList.size() > 0) {
                Map<String, String> stringMap = mapArrayList.get(0);
                rankTv.setText(stringMap.get("rank"));
                incomeTv.setText(stringMap.get("revenue"));
                Map<String, String> userInfo = application.getUserInfo();
                Glide.with(mContext).load(userInfo.get("head_pic")).into(shapeImg);
                nameTv.setText(userInfo.get("nickname"));
            }
            setLineChart(data);
            ArrayList<Map<String, String>> rankList = JSONUtils.parseKeyAndValueToMapList(data.get("rank"));
            if (rankList != null){
                recyclerView.setAdapter(new IncomeAdapter(rankList));
            }
        }
    }

    private void setLineChart(Map<String, String> data) {
        //设置图表的描述
        lineChart.setDrawBorders(false);
        //设置x轴的数据
        //        int numX = 5;

        try {
            JSONArray horizon = new JSONArray(data.get("horizon"));
            bottomList = new ArrayList<>();
            for (int i = 0; i < horizon.length(); i++) {
                bottomList.add(horizon.getString(i));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        lineChart.animateY(2000);
        Description description = new Description();
        description.setEnabled(false);
        lineChart.setDescription(description);
        lineChart.setScaleEnabled(false);
        Map<String, String> map = JSONUtils.parseKeyAndValueToMap(data.get("data"));
        ArrayList<Entry> normal = new ArrayList<>();
        for (int i = 0; i < bottomList.size(); i++) {
            normal.add(new Entry(i, Float.parseFloat(map.get(bottomList.get(i)))));
        }
        LineData lineDatas = new LineData(new LineDataSet(normal, ""));
        lineDatas.setHighlightEnabled(false);
        lineChart.setData(lineDatas);
        Legend legend = lineChart.getLegend();
        legend.setEnabled(false);

        YAxis axisRight = lineChart.getAxisRight();
        YAxis axisLeft = lineChart.getAxisLeft();
        axisLeft.setEnabled(false);
        axisRight.setEnabled(false);

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return bottomList.get((int) (value));

            }
        });
        xAxis.setAxisMaximum((bottomList.size() - 1));
        xAxis.setAxisMinimum(0f);
        //        xAxis.setLabelCount(numX);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(true);
    }

    @OnClick({R.id.llyt_week, R.id.llyt_month, R.id.llyt_year})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.llyt_week:
                clearChoice();
                view_week.setBackgroundColor(getResources().getColor(R.color.titleColors));
                mType = "1";
                requestData();
                break;
            case R.id.llyt_month:
                clearChoice();
                view_month.setBackgroundColor(getResources().getColor(R.color.titleColors));
                mType = "2";
                requestData();
                break;
            case R.id.llyt_year:
                clearChoice();
                view_year.setBackgroundColor(getResources().getColor(R.color.titleColors));
                mType = "3";
                requestData();
                break;
            default:
                break;
        }
    }

    private void clearChoice() {
        view_week.setBackgroundColor(getResources().getColor(R.color.white));
        view_month.setBackgroundColor(getResources().getColor(R.color.white));
        view_year.setBackgroundColor(getResources().getColor(R.color.white));
    }

    public static class IncomeAdapter extends RecyclerView.Adapter<IncomeAdapter.ViewHolder> {

        private Context mContext;

        private List<Map<String, String>> mList;

        private OnItemClickListener mOnItemClickListener;

        public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
            mOnItemClickListener = onItemClickListener;
        }

        public IncomeAdapter(List<Map<String, String>> list) {
            mList = list;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            mContext = parent.getContext();
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_ranking_list, parent, false);
            ViewHolder holder = new ViewHolder(view);
            ViewUtils.inject(holder, view);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
            Map<String, String> map = mList.get(position);
            if (map.containsKey("head_pic") && !TextUtils.isEmpty(map.get("head_pic"))) {
                Glide.with(mContext).load(map.get("head_pic")).into(holder.headImg);
            }
            holder.nameTv.setText(map.get("nickname"));
            holder.priceTv.setText("¥"+map.get("revenue"));

            if (position == 0){
                holder.numImg.setVisibility(View.VISIBLE);
                holder.numTv.setVisibility(View.GONE);
                holder.numImg.setImageDrawable(mContext.getDrawable(R.drawable.icon_strategy_1));
            }else if (position == 1){
                holder.numImg.setVisibility(View.VISIBLE);
                holder.numTv.setVisibility(View.GONE);
                holder.numImg.setImageDrawable(mContext.getDrawable(R.drawable.icon_strategy_2));
            }else if (position == 2){
                holder.numImg.setVisibility(View.VISIBLE);
                holder.numTv.setVisibility(View.GONE);
                holder.numImg.setImageDrawable(mContext.getDrawable(R.drawable.icon_strategy_3));
            }else{
                holder.numImg.setVisibility(View.GONE);
                holder.numTv.setVisibility(View.VISIBLE);
                holder.numTv.setText(String.valueOf(position+1));
            }

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnItemClickListener != null){
                        mOnItemClickListener.onItemClick(holder.getLayoutPosition());
                    }
                }
            });

        }

        @Override
        public int getItemCount() {
            return mList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            @ViewInject(R.id.numImg)
            ImageView numImg;
            @ViewInject(R.id.numTv)
            TextView numTv;
            @ViewInject(R.id.headImg)
            ShapedImageView headImg;
            @ViewInject(R.id.nameTv)
            TextView nameTv;
            @ViewInject(R.id.priceTv)
            TextView priceTv;
            public ViewHolder(View itemView) {
                super(itemView);
            }
        }

        public interface OnItemClickListener{
            void onItemClick(int position);
        }
    }  
    
}
