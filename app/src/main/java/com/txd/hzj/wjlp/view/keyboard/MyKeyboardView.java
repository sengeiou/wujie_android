package com.txd.hzj.wjlp.view.keyboard;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;

import com.txd.hzj.wjlp.R;

import org.w3c.dom.Text;

import java.lang.reflect.Field;
import java.util.List;

/**
 * 创建者：voodoo_jie
 * 创建时间：2018/07/23 023 上午 09:24
 * 功能描述：自定义数字键盘View
 */
public class MyKeyboardView extends KeyboardView {

    private Context context;
    private Keyboard keyboard;

    public MyKeyboardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        keyboard = this.getKeyboard();
        List<Keyboard.Key> keys = null;
        if (keyboard != null) {
            keys = keyboard.getKeys();
        }
        if (keys != null) {
            for (Keyboard.Key key : keys) {
                // 数字键盘的处理
                if (key.codes[0] == -4) { // 确定按钮
                    drawKeyBackground(R.color.colorOrange, R.color.colorOrange, canvas, key);
                    drawText(canvas, key);
                } else { // 其他按钮
                    drawKeyBackground(R.color.E2E2E2, R.color.colorOrange, canvas, key);
                    drawText(canvas, key);
                }
            }
        }
    }

    private void drawKeyBackground(int drawableId, int lineColor, Canvas canvas, Keyboard.Key key) {
        Drawable keyLine = context.getResources().getDrawable(lineColor); // 背景（主要突出分割线）
        Drawable npd = context.getResources().getDrawable(drawableId); // 按钮背景
        int[] drawableState = key.getCurrentDrawableState();
        if (key.codes[0] != 0) {
            npd.setState(drawableState);
        }
        keyLine.setBounds(key.x, key.y, key.x + key.width, key.y + key.height); // 先画背景（分割线）
        npd.setBounds(key.x + 1, key.y + 1, key.x + key.width - 1, key.y + key.height - 1); // 再画出按钮背景
        keyLine.draw(canvas);
        npd.draw(canvas);
    }

    private void drawText(Canvas canvas, Keyboard.Key key) {
        Rect bounds = new Rect();
        Paint paint = new Paint();
        paint.setTextAlign(Paint.Align.CENTER);

        paint.setAntiAlias(true);

        paint.setColor(Color.BLACK);
        if (key.label != null) {
            String label = key.label.toString();

            Field field;

            if (label.length() > 1 && key.codes.length < 2) { // 回车键
                int labelTextSize = 0;
                try {
                    field = KeyboardView.class.getDeclaredField("mLabelTextSize");
                    field.setAccessible(true);
                    labelTextSize = (int) field.get(this);
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                paint.setTextSize(labelTextSize);
                paint.setTypeface(Typeface.DEFAULT_BOLD);
            } else { // 数字键
                int keyTextSize = 0;
                try {
                    field = KeyboardView.class.getDeclaredField("mLabelTextSize");
                    field.setAccessible(true);
//                    keyTextSize = (int) field.get(this);
                    keyTextSize = 80;
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                }
//                catch (IllegalAccessException e) {
//                    e.printStackTrace();
//                }
                paint.setTextSize(keyTextSize);
                paint.setTypeface(Typeface.DEFAULT);
            }

            paint.getTextBounds(key.label.toString(), 0, key.label.toString().length(), bounds);
            canvas.drawText(key.label.toString(), key.x + (key.width / 2), (key.y + key.height / 2) + bounds.height() / 2, paint);
        } else if (key.icon != null) {
            key.icon.setBounds(key.x + (key.width - key.icon.getIntrinsicWidth()) / 2, key.y + (key.height - key.icon.getIntrinsicHeight()) / 2,
                    key.x + (key.width - key.icon.getIntrinsicWidth()) / 2 + key.icon.getIntrinsicWidth(), key.y + (key.height - key.icon.getIntrinsicHeight()) / 2 + key.icon.getIntrinsicHeight());
            key.icon.draw(canvas);
        }
    }
}
