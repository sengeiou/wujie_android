package com.txd.hzj.wjlp.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.ants.theantsgo.config.Settings;
import com.txd.hzj.wjlp.R;

/**
 * 创建者：zhangyunfei
 * 创建时间：2018/7/27 14:19
 * 功能描述：
 */
public class PayForDialog extends Dialog {

    private Context mContext;
    private TextView look_order_tv;
    private TextView back_main_tv;
    private OnPayForInterface mOnPayForInterface;


    public PayForDialog(@NonNull Context context) {
        this(context,R.style.Ticket_Dialog);


    }

    public PayForDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        mContext=context;
        init();
    }

    private void init(){
        setCancelable(false);
        setCanceledOnTouchOutside(false);
        View inflate = getLayoutInflater().inflate(R.layout.pay_complete_dialog, null);
        setContentView(inflate);
        look_order_tv=inflate.findViewById(R.id.look_order_tv);
        back_main_tv=inflate.findViewById(R.id.back_main_tv);
        look_order_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnPayForInterface!=null){
                    mOnPayForInterface.onLookListener();
                }
            }
        });

        back_main_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnPayForInterface!=null){
                    mOnPayForInterface.onBackMainListener();
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.gravity= Gravity.CENTER;
        attributes.height= (int) (Settings.displayHeight*0.6);
        attributes.width= (int) (Settings.displayWidth*0.8);
        window.setAttributes(attributes);
    }

    public interface  OnPayForInterface{
        void onLookListener();
        void onBackMainListener();
    }

    public void setOnPayforListener(OnPayForInterface onPayforListener){
        mOnPayForInterface=onPayforListener;
    }

}
