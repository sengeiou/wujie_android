package com.txd.hzj.wjlp.view;

import android.content.ClipboardManager;
import android.content.Context;
import android.util.AttributeSet;

/**
 * 作者 Created by 王尧 on 2018/5/30.
 */

public class MyEditText extends android.support.v7.widget.AppCompatEditText {
    private static final int ID_PASTE = android.R.id.paste;
    public MyEditText(Context context) {
        super(context);
    }
    public MyEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTextContextMenuItem(int id) {
        if(id == ID_PASTE){
            ClipboardManager clip = (ClipboardManager)getContext().getSystemService(Context.CLIPBOARD_SERVICE);
            clip.setText("");
        }
        return super.onTextContextMenuItem(id);
    }
}
