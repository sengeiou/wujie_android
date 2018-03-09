package com.txd.hzj.wjlp.minetoAty.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ants.theantsgo.tool.ToolKit;
import com.bumptech.glide.Glide;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.bean.AcademyList;

import java.util.List;

/**
 * ===============Txunda===============
 * 作者：DUKE_HwangZj
 * 日期：2017/7/24 0024
 * 时间：11:30
 * 描述：收藏，书院
 * ===============Txunda===============
 */

public class WjBooksAdapter extends BaseAdapter {

    private Context context;
    private List<AcademyList> academyLists;

    private int w = 0;
    private int h = 0;

    private BVH bvh;

    private int selectNum = 0;

    /**
     * 这才是真正的够造函数
     *
     * @param context      上下文
     * @param academyLists 数据
     */
    public WjBooksAdapter(Context context, List<AcademyList> academyLists) {
        this.context = context;
        this.academyLists = academyLists;
        w = ToolKit.dip2px(context, 120);
        h = ToolKit.dip2px(context, 60);
    }

    private boolean canEdit = false;

    public void setCanEdit(boolean canEdit) {
        this.canEdit = canEdit;
    }

    @Override
    public int getCount() {
        return academyLists.size();
    }

    @Override
    public AcademyList getItem(int i) {
        return academyLists.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final AcademyList academyList = getItem(i);
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_collect_books_lv, null);
            bvh = new BVH();
            ViewUtils.inject(bvh, view);
            view.setTag(bvh);
        } else {
            bvh = (BVH) view.getTag();
        }

        if (canEdit) {
            bvh.books_select_iv.setVisibility(View.VISIBLE);

            if (academyList.isSelect()) {
                bvh.books_select_iv.setImageResource(R.drawable.icon_cart_goods_selected);
            } else {
                bvh.books_select_iv.setImageResource(R.drawable.icon_cart_goods_unselect);
            }

        } else {
            bvh.books_select_iv.setVisibility(View.GONE);
        }

        Glide.with(context).load(academyList.getLogo()).override(w, h)
                .placeholder(R.drawable.ic_default)
                .error(R.drawable.ic_default)
                .into(bvh.ac_pic_iv);

        bvh.ac_title_tv.setText(academyList.getTitle());

        bvh.other_info_tv.setText(academyList.getCollect_num() + "人收藏        " + academyList.getPage_views() + "人浏览");

        bvh.books_select_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (academyList.isSelect()) {
                    academyList.setSelect(false);
                } else {
                    academyList.setSelect(true);
                }
                if (forSelectNum != null) {// 设置选中个数
                    selectNum = 0;// 首先设置选中个数为零
                    for (AcademyList ac : academyLists) {// 遍历数组，获取选中的个数
                        if (ac.isSelect()) {
                            selectNum++;
                        }
                    }
                    // 通过接口将个数回传给界面
                    forSelectNum.getSelectNum(selectNum);
                }
                notifyDataSetChanged();
            }
        });

        return view;
    }

    class BVH {

        /**
         * 选中未选中
         */
        @ViewInject(R.id.books_select_iv)
        private ImageView books_select_iv;

        /**
         * logo
         */
        @ViewInject(R.id.ac_pic_iv)
        private ImageView ac_pic_iv;

        /**
         * 标题
         */
        @ViewInject(R.id.ac_title_tv)
        private TextView ac_title_tv;
        /**
         * 其他信息
         */
        @ViewInject(R.id.other_info_tv)
        private TextView other_info_tv;
    }

    public interface ForSelectNum {
        void getSelectNum(int num);
    }

    public ForSelectNum forSelectNum;

    public void setForSelectNum(ForSelectNum forSelectNum) {
        this.forSelectNum = forSelectNum;
    }
}
