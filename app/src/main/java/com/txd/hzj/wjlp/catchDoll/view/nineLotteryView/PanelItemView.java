package com.txd.hzj.wjlp.catchDoll.view.nineLotteryView;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.txd.hzj.wjlp.R;

/**
 * 创建者：voodoo_jie
 * <br>创建时间：2018/09/20 020上午 11:51
 * <br>功能描述：
 */
public class PanelItemView extends FrameLayout implements ItemView {

    private View viewPanel_overlay_view; // 遮罩
    private ImageView viewPanel_bg_imgv; // 九宫格单个背景图片
    private LinearLayout viewPanel_prize_llayout; // 奖品图片和名称父布局
    private ImageView viewPanel_prize_imgv; // 奖品图片
    private TextView viewPanel_content_tv; // 奖品名称
    private ImageView viewPanel_fail_imgv; // 未中奖图片，显示此图片时将viewPanel_prize_llayout隐藏

    public PanelItemView(Context context) {
        this(context, null);
    }

    public PanelItemView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PanelItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context, R.layout.view_panel_item, this);
        viewPanel_overlay_view = findViewById(R.id.viewPanel_overlay_view);
        viewPanel_bg_imgv = findViewById(R.id.viewPanel_bg_imgv);
        viewPanel_prize_llayout = findViewById(R.id.viewPanel_prize_llayout);
        viewPanel_prize_imgv = findViewById(R.id.viewPanel_prize_imgv);
        viewPanel_content_tv = findViewById(R.id.viewPanel_content_tv);
        viewPanel_fail_imgv = findViewById(R.id.viewPanel_fail_imgv);
    }

    public View getViewPanel_overlay_view() {
        return viewPanel_overlay_view;
    }

    public ImageView getViewPanel_bg_imgv() {
        return viewPanel_bg_imgv;
    }

    public LinearLayout getViewPanel_prize_llayout() {
        return viewPanel_prize_llayout;
    }

    public ImageView getViewPanel_prize_imgv() {
        return viewPanel_prize_imgv;
    }

    public TextView getViewPanel_content_tv() {
        return viewPanel_content_tv;
    }

    public ImageView getViewPanel_fail_imgv() {
        return viewPanel_fail_imgv;
    }

    @Override
    public void setFocus(boolean isFocused) {
        if (viewPanel_overlay_view != null) {
            viewPanel_overlay_view.setVisibility(isFocused ? VISIBLE : INVISIBLE);
        }
    }

}
