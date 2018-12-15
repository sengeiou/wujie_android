package com.ants.theantsgo.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

/**
 * 作者：DUKE_HwangZj
 * 日期：2017/6/30 0030
 * 时间：下午 2:43
 * 描述：SharedPreferences工具类
 */
public class PreferencesUtils {

    private static String PREFERENCE_NAME = "Duke";
    private static Gson gson = new Gson();

    public static String SHARED_KEY_HUANXIN_USER_ID = "SHARED_KEY_HUANXIN_USER_ID"; // 店家联系需要调用接口的id

    /**
     * put string preferences
     *
     * @param context
     * @param key     The name of the preference to modify
     * @param value   The new value for the preference
     * @return True if the new values were successfully written to persistent storage.
     */
    public static boolean putString(Context context, String key, String value) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key, value);
        return editor.commit();
    }

    /**
     * get string preferences
     *
     * @param context
     * @param key     The name of the preference to retrieve
     * @return The preference value if it exists, or null. Throws ClassCastException if there is a preference with
     * this name that is not a string
     * @see #getString(Context, String, String)
     */
    public static String getString(Context context, String key) {
        return getString(context, key, null);
    }

    /**
     * get string preferences
     *
     * @param context
     * @param key          The name of the preference to retrieve
     * @param defaultValue Value to return if this preference does not exist
     * @return The preference value if it exists, or defValue. Throws ClassCastException if there is a preference
     * with this name that is not a string
     */
    public static String getString(Context context, String key, String defaultValue) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        return settings.getString(key, defaultValue);
    }

    /**
     * put int preferences
     *
     * @param context
     * @param key     The name of the preference to modify
     * @param value   The new value for the preference
     * @return True if the new values were successfully written to persistent storage.
     */
    public static boolean putInt(Context context, String key, int value) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(key, value);
        return editor.commit();
    }

    /**
     * get int preferences
     *
     * @param context
     * @param key     The name of the preference to retrieve
     * @return The preference value if it exists, or -1. Throws ClassCastException if there is a preference with this
     * name that is not a int
     * @see #getInt(Context, String, int)
     */
    public static int getInt(Context context, String key) {
        return getInt(context, key, -1);
    }

    /**
     * get int preferences
     *
     * @param context
     * @param key          The name of the preference to retrieve
     * @param defaultValue Value to return if this preference does not exist
     * @return The preference value if it exists, or defValue. Throws ClassCastException if there is a preference
     * with this name that is not a int
     */
    public static int getInt(Context context, String key, int defaultValue) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        return settings.getInt(key, defaultValue);
    }

    /**
     * put long preferences
     *
     * @param context
     * @param key     The name of the preference to modify
     * @param value   The new value for the preference
     * @return True if the new values were successfully written to persistent storage.
     */
    public static boolean putLong(Context context, String key, long value) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putLong(key, value);
        return editor.commit();
    }

    public static boolean putObject(Context context, String key, Object o) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        String jsonStr = gson.toJson(o);
        editor.putString(key, jsonStr);
        return editor.commit();
    }

    ;

    /**
     * get long preferences
     *
     * @param context
     * @param key     The name of the preference to retrieve
     * @return The preference value if it exists, or -1. Throws ClassCastException if there is a preference with this
     * name that is not a long
     * @see #getLong(Context, String, long)
     */
    public static long getLong(Context context, String key) {
        return getLong(context, key, -1);
    }

    /**
     * get long preferences
     *
     * @param context
     * @param key          The name of the preference to retrieve
     * @param defaultValue Value to return if this preference does not exist
     * @return The preference value if it exists, or defValue. Throws ClassCastException if there is a preference
     * with this name that is not a long
     */
    public static long getLong(Context context, String key, long defaultValue) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        return settings.getLong(key, defaultValue);
    }

    /**
     * put float preferences
     *
     * @param context
     * @param key     The name of the preference to modify
     * @param value   The new value for the preference
     * @return True if the new values were successfully written to persistent storage.
     */
    public static boolean putFloat(Context context, String key, float value) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putFloat(key, value);
        return editor.commit();
    }

    /**
     * get float preferences
     *
     * @param context
     * @param key     The name of the preference to retrieve
     * @return The preference value if it exists, or -1. Throws ClassCastException if there is a preference with this
     * name that is not a float
     * @see #getFloat(Context, String, float)
     */
    public static float getFloat(Context context, String key) {
        return getFloat(context, key, -1);
    }

    /**
     * get float preferences
     *
     * @param context
     * @param key          The name of the preference to retrieve
     * @param defaultValue Value to return if this preference does not exist
     * @return The preference value if it exists, or defValue. Throws ClassCastException if there is a preference
     * with this name that is not a float
     */
    public static float getFloat(Context context, String key, float defaultValue) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        return settings.getFloat(key, defaultValue);
    }

    /**
     * put boolean preferences
     *
     * @param context
     * @param key     The name of the preference to modify
     * @param value   The new value for the preference
     * @return True if the new values were successfully written to persistent storage.
     */
    public static boolean putBoolean(Context context, String key, boolean value) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(key, value);
        return editor.commit();
    }

    /**
     * get boolean preferences, default is false
     *
     * @param context
     * @param key     The name of the preference to retrieve
     * @return The preference value if it exists, or false. Throws ClassCastException if there is a preference with
     * this name that is not a boolean
     * @see #getBoolean(Context, String, boolean)
     */
    public static boolean getBoolean(Context context, String key) {
        return getBoolean(context, key, false);
    }

    /**
     * get boolean preferences
     *
     * @param context
     * @param key          The name of the preference to retrieve
     * @param defaultValue Value to return if this preference does not exist
     * @return The preference value if it exists, or defValue. Throws ClassCastException if there is a preference
     * with this name that is not a boolean
     */
    public static boolean getBoolean(Context context, String key, boolean defaultValue) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        return settings.getBoolean(key, defaultValue);
    }

    public static boolean remove(Context context, String key) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.remove(key);
        return editor.commit();
    }

    public static boolean containKey(Context context, String key) {
        SharedPreferences settings = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        if (settings.contains(key)) {
            return true;
        }
        return false;
    }

// ==============================================蓝牙公共分享属性======================================================================

    private static String blueName = "blueName";
    private static String blueAddress = "blueAddress";

    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
    }

    /**
     * 保存蓝牙名称
     *
     * @param context     上下文
     * @param blueNameStr 蓝牙名称字符串
     * @return 是否保存成功
     */
    public static boolean saveBluetoothName(Context context, String blueNameStr) {
        return getSharedPreferences(context).edit().putString(blueName, blueNameStr).commit();
    }

    /**
     * 保存蓝牙地址
     *
     * @param context        上下文
     * @param blueAddressStr 蓝牙地址字符串
     * @return 是否保存成功
     */
    public static boolean saveBluetoothAddress(Context context, String blueAddressStr) {
        return getSharedPreferences(context).edit().putString(blueAddress, blueAddressStr).commit();
    }

    /**
     * 保存蓝牙地址
     *
     * @param context        上下文
     * @param blueNameStr    蓝牙名称字符串
     * @param blueAddressStr 蓝牙地址字符串
     * @return 名称和地址是否全部保存成功
     */
    public static boolean saveBluetoothAddress(Context context, String blueNameStr, String blueAddressStr) {
        boolean isNameCommit = getSharedPreferences(context).edit().putString(blueName, blueNameStr).commit();
        boolean isAddressCommit = getSharedPreferences(context).edit().putString(blueAddress, blueAddressStr).commit();
        return isNameCommit && isAddressCommit;
    }

    /**
     * 获取保存的保存蓝牙名称
     *
     * @param context 上下文
     * @return 保存的名称
     */
    public static String getBluetoothName(Context context) {
        return getSharedPreferences(context).getString(blueName, "");
    }

    /**
     * 获取保存的保存蓝牙名称，若未获取到则返回空字符串
     *
     * @param context 上下文
     * @return 保存的地址
     */
    public static String getBluetoothAddress(Context context) {
        return getSharedPreferences(context).getString(blueAddress, "");
    }

}
