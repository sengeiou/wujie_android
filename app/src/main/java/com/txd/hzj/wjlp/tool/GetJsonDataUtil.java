package com.txd.hzj.wjlp.tool;

import android.content.Context;
import android.content.res.AssetManager;

import com.ants.theantsgo.util.L;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * TODO<读取Json文件的工具类>
 *
 * @author: 小嵩
 * @date: 2017/3/16 16:22
 */

public class GetJsonDataUtil {

    public String getJson(Context context, String fileName) {

        StringBuilder stringBuilder = new StringBuilder();
        try {
            AssetManager assetManager = context.getAssets();
            BufferedReader bf = new BufferedReader(new InputStreamReader(assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            L.e("GetJsonDataUtil.getJson Exception -- IOException:" + e.toString());
        }
        return stringBuilder.toString();
    }
}

