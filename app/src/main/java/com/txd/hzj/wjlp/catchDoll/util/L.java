package com.txd.hzj.wjlp.catchDoll.util;

import android.util.Log;

/**
 * 创建者：voodoo_jie
 * <br>创建时间：2018/09/20 020上午 11:51
 * <br>功能描述：
 */
public class L {

    public static boolean isDebug = true;
    private static String TAG = "=====voodoo=====";

    // =================== 自定义TAG ===================
    public static void v(String tagStr, String msgStr) {
        if (isDebug) {
            Log.v(tagStr, msgStr);
        }
    }

    public static void d(String tagStr, String msgStr) {
        if (isDebug) {
            Log.d(tagStr, msgStr);
        }
    }

    public static void i(String tagStr, String msgStr) {
        if (isDebug) {
            Log.i(tagStr, msgStr);
        }
    }

    public static void w(String tagStr, String msgStr) {
        if (isDebug) {
            Log.w(tagStr, msgStr);
        }
    }

    public static void e(String tagStr, String msgStr) {
        if (isDebug) {
            Log.e(tagStr, msgStr);
        }
    }

    // ==================== 默认TAG ====================
    public static void v(String msgStr) {
        if (isDebug) {
            Log.v(TAG, msgStr);
        }
    }

    public static void d(String msgStr) {
        if (isDebug) {
            Log.d(TAG, msgStr);
        }
    }

    public static void i(String msgStr) {
        if (isDebug) {
            Log.i(TAG, msgStr);
        }
    }

    public static void w(String msgStr) {
        if (isDebug) {
            Log.w(TAG, msgStr);
        }
    }

    public static void e(String msgStr) {
        if (isDebug) {
            Log.e(TAG, msgStr);
        }
    }

    // =================== 类名的TAG ===================
    public static void v(Class<?> cls, String msgStr) {
        if (isDebug) {
            Log.v(cls.getName(), msgStr);
        }
    }

    public static void d(Class<?> cls, String msgStr) {
        if (isDebug) {
            Log.d(cls.getName(), msgStr);
        }
    }

    public static void i(Class<?> cls, String msgStr) {
        if (isDebug) {
            Log.i(cls.getName(), msgStr);
        }
    }

    public static void w(Class<?> cls, String msgStr) {
        if (isDebug) {
            Log.w(cls.getName(), msgStr);
        }
    }

    public static void e(Class<?> cls, String msgStr) {
        if (isDebug) {
            Log.e(cls.getName(), msgStr);
        }
    }

}
