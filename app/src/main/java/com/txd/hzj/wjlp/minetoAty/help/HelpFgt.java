package com.txd.hzj.wjlp.minetoAty.help;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.ants.theantsgo.util.JSONUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.base.BaseFgt;
import com.txd.hzj.wjlp.bean.HelpCenter;
import com.txd.hzj.wjlp.http.article.ArticlePst;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/8/18 0018
 * 时间：15:00
 * 描述：
 * ===============Txunda===============
 */

public class HelpFgt extends BaseFgt {

    @ViewInject(R.id.expandableListView)
    private ExpandableListView expandableListView;

    private String type;

    private List<HelpCenter> helpCenters;

    private String help_type = "1";

    private ArticlePst articlePst;

    private MyExpandableListView elv;

    public static HelpFgt getFgt(String type) {
        HelpFgt helpFgt = new HelpFgt();
        helpFgt.type = type;
        return helpFgt;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        expandableListView.setGroupIndicator(null);
        if ("商家篇".equals(type)) {
            help_type = "1";
        } else if ("用户篇".equals(type)) {
            help_type = "2";
        } else {
            help_type = "3";
        }
        articlePst.helpCenter(help_type);
    }

    @Override
    protected void immersionInit() {

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.help_center_item_li;
    }

    @Override
    protected void initialized() {
        helpCenters = new ArrayList<>();
        articlePst = new ArticlePst(this);
    }

    @Override
    protected void requestData() {

    }

    @Override
    public void onComplete(String requestUrl, String jsonStr) {
        super.onComplete(requestUrl, jsonStr);
        Map<String, String> map = JSONUtils.parseKeyAndValueToMap(jsonStr);
        List<Map<String, String>> data = JSONUtils.parseKeyAndValueToMapList(map.get("data"));
        helpCenters.clear();
        for (Map<String, String> help : data) {
            List<String> content = new ArrayList();
            String title = help.get("title");
            String con = help.get("content");
            content.add(con);
            helpCenters.add(new HelpCenter(title,content));
        }
        elv = new MyExpandableListView();
        expandableListView.setAdapter(elv);
    }

    //为ExpandableListView自定义适配器
    private class MyExpandableListView extends BaseExpandableListAdapter {

        //返回一级列表的个数
        @Override
        public int getGroupCount() {
            return helpCenters.size();
        }

        //返回每个二级列表的个数
        @Override
        public int getChildrenCount(int groupPosition) { //参数groupPosition表示第几个一级列表
            Log.d("smyhvae", "-->" + groupPosition);
            return helpCenters.get(groupPosition).getContent().size();
        }

        //返回一级列表的单个item（返回的是对象）
        @Override
        public Object getGroup(int groupPosition) {
            return helpCenters.get(groupPosition);
        }

        //返回二级列表中的单个item（返回的是对象）
        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return helpCenters.get(groupPosition).getContent().get(childPosition);
            //不要误写成groups[groupPosition][childPosition]
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        //每个item的id是否是固定？一般为true
        @Override
        public boolean hasStableIds() {
            return true;
        }

        //【重要】填充一级列表
        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.help_item_parent, null);
            } else {

            }
            TextView tv_group = convertView.findViewById(R.id.tv_group);
            ImageView img_group = convertView.findViewById(R.id.img_group);
            if (isExpanded) {
                img_group.setBackgroundResource(R.drawable.icon_show_other_layout);
            } else {
                img_group.setBackgroundResource(R.drawable.icon_hide_other_layout);
            }
            tv_group.setText(helpCenters.get(groupPosition).getTitle());
            return convertView;
        }

        //【重要】填充二级列表
        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView,
                                 ViewGroup parent) {

            if (convertView == null) {
                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.help_item_child, null);
            }

            TextView tv_child = (TextView) convertView.findViewById(R.id.tv_child);

            //iv_child.setImageResource(resId);
            tv_child.setText(helpCenters.get(groupPosition).getContent().get(childPosition));

            return convertView;
        }

        //二级列表中的item是否能够被选中？可以改为true
        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
    }
}
