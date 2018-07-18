package com.txd.hzj.wjlp.view;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ants.theantsgo.tool.glide.GlideUtils;
import com.txd.hzj.wjlp.R;
import com.txd.hzj.wjlp.bean.commodity.Event_msgBean;
import com.txd.hzj.wjlp.tool.GlideUtil;

import java.util.List;

/**
 * 创建者：TJDragon(LiuGang)
 * 创建时间：2018/7/18 10:42
 * 功能描述：
 * 联系方式：常用邮箱或电话
 */
public class ToastView extends LinearLayout {

    private View view;
    private MsgRunable msgRunable;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1: {
                    show();
                    handler.post(msgRunable);
                }
                break;
                case 2: {
                    currentPos++;
                    if (currentPos > event_msgBeans.size() - 1) {
                        currentPos = 0;
                    }
                    view.setVisibility(GONE);
                }
                break;
            }
        }
    };
    int start = 5;
    int end = 20;
    private int currentPos;
    private List<Event_msgBean> event_msgBeans;

    public void setDatas(List<Event_msgBean> event_msgBeans) {
        if (null != msgRunable) {
            cancle();
        }
        this.event_msgBeans = event_msgBeans;
        currentPos = 0;
        msgRunable = new MsgRunable();
        handler.post(msgRunable);
    }

    private class MsgRunable implements Runnable {
        @Override
        public void run() {
            int s = start + (int) (Math.random() * (end - start));
            handler.sendEmptyMessageDelayed(1, s * 1000);
        }
    }

    public void cancle() {
        handler.removeMessages(1);
        handler.removeCallbacks(msgRunable);
    }

    /**
     * 自定义居中显示toast
     */
    private void show() {
        view.setVisibility(VISIBLE);
        view.measure(0, 0);
        ImageView imageView = view.findViewById(R.id.userImg);
        TextView usernickNameTv = view.findViewById(R.id.usernickNameTv);
        TextView userdoTv = view.findViewById(R.id.userdoTv);
        Event_msgBean event_msgBean = event_msgBeans.get(currentPos);
        String headPic = event_msgBean.getHead_pic();
        GlideUtils.urlCirclePic(headPic, imageView.getMeasuredWidth(), imageView.getMeasuredHeight(), imageView);
        usernickNameTv.setText(event_msgBean.getNick_name());
        userdoTv.setText(event_msgBean.getMsg());

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                handler.sendEmptyMessage(2);
            }
        }, 3000);
    }

    public ToastView(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //自定义布局
        if (null == view) {
            view = inflater.inflate(R.layout.layout_pd_toast, null);
            view.setAlpha(0.7f);
        }
        view.setVisibility(GONE);
        addView(view);
    }

    public ToastView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ToastView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public ToastView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }
}
