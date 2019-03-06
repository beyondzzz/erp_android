package com.jl.jlapp.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.jl.jlapp.mvp.base.MyApplication;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;

/**
 * 功能：
 * 描述：
 * Created by huangfu on 2017/5/23 15:14;
 */

public class ShareUtils {

    /**
     * 保存在手机里面的文件名
     */
    private static final String FILE_NAME = "share_data";
    private static final String FILE_NAMES = "share_datas";
    private static final String TAG = "ShareUtils";
    static SharedPreferences sp = MyApplication.getInstance().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);

    /**
     * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
     *
     * @param context
     * @param key
     * @param object
     */
    public static void setParam(Context context, String key, Object object) {

        String type = object.getClass().getSimpleName();
        SharedPreferences.Editor editor = sp.edit();

        if ("String".equals(type)) {
            editor.putString(key, (String) object);
        } else if ("Integer".equals(type)) {
            editor.putInt(key, (Integer) object);
        } else if ("Boolean".equals(type)) {
            editor.putBoolean(key, (Boolean) object);
        } else if ("Float".equals(type)) {
            editor.putFloat(key, (Float) object);
        } else if ("Long".equals(type)) {
            editor.putLong(key, (Long) object);
        }

        editor.apply();
    }

    /**
     * MyApplication.getInstance()对象储存share
     *
     * @param key
     * @param object
     */
    public static void setParam(String key, Object object) {
        setParam(MyApplication.getInstance(), key, object);
    }

    /**
     * int key  MyApplication.getInstance()对象储存
     *
     * @param key
     * @param object
     */
    public static void setParam(int key, Object object) {
        setParam(MyApplication.getInstance(), key + "", object);
    }

    /**
     * int key 获取 share
     *
     * @param context
     * @param key
     * @param defaultObject
     * @return
     */
    public static Object getParam(Context context, int key, Object defaultObject) {
        return getParam(context, key + "", defaultObject);
    }

    /**
     * MyApplication.getInstance()对象获取 int share
     *
     * @param key
     * @param defaultObject
     * @return
     */
    public static int getParam(String key, Object defaultObject) {
        return (int) getParam(MyApplication.getInstance(), key, defaultObject);
    }

    /**
     * 获取String类型的share
     *
     * @param key
     * @param defaultObject
     * @return
     */
    public static String getParam(String key, String defaultObject) {
        return (String) getParam(MyApplication.getInstance(), key, defaultObject);
    }

    /**
     * MyApplication.getInstance()对象获取 int share
     *
     * @param key
     * @param defaultObject
     * @return
     */
    public static int getIntParam(int key, Object defaultObject) {
        return (int) getParam(MyApplication.getInstance(), key, defaultObject);
    }

    /**
     * MyApplication.getInstance()对象获取String share
     *
     * @param key
     * @param defaultObject
     * @return
     */
    public static String getParam(int key, Object defaultObject) {
        return (String) getParam(MyApplication.getInstance(), key + "", defaultObject);
    }

    /**
     * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
     *
     * @param context
     * @param key
     * @param defaultObject
     * @return
     */
    public static Object getParam(Context context, String key, Object defaultObject) {
        String type = defaultObject.getClass().getSimpleName();
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);

        if ("String".equals(type)) {
            return sp.getString(key, (String) defaultObject);
        } else if ("Integer".equals(type)) {
            return sp.getInt(key, (Integer) defaultObject);
        } else if ("Boolean".equals(type)) {
            return sp.getBoolean(key, (Boolean) defaultObject);
        } else if ("Float".equals(type)) {
            return sp.getFloat(key, (Float) defaultObject);
        } else if ("Long".equals(type)) {
            return sp.getLong(key, (Long) defaultObject);
        }

        return null;
    }

    /**
     * desc:保存对象
     *
     * @param context
     * @param key
     * @param obj     要保存的对象，只能保存实现了serializable的对象
     *                modified:
     */
    public static void saveObject(Context context, String key, Object obj) {
        try {
            // 保存对象
            SharedPreferences.Editor sharedata = context.getSharedPreferences(FILE_NAMES, Context.MODE_PRIVATE).edit();
            sharedata.clear();
            sharedata.apply();

            //先将序列化结果写到byte缓存中，其实就分配一个内存空间
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream os = new ObjectOutputStream(bos);
            //将对象序列化写入byte缓存
            os.writeObject(obj);
            //将序列化的数据转为16进制保存
            String bytesToHexString = bytesToHexString(bos.toByteArray());
            //保存该16进制数组
            sharedata.putString(key, bytesToHexString);
            sharedata.apply();
        } catch (IOException e) {
            e.printStackTrace();
//                Log.i("saveObject: ", e.getMessage());
        }
    }

    /**
     * desc:将数组转为16进制
     *
     * @param bArray
     * @return modified:
     */
    public static String bytesToHexString(byte[] bArray) {
        if (bArray == null) {
            return null;
        }
        if (bArray.length == 0) {
            return "";
        }
        StringBuffer sb = new StringBuffer(bArray.length);
        String sTemp;
        for (int i = 0; i < bArray.length; i++) {
            sTemp = Integer.toHexString(0xFF & bArray[i]);
            if (sTemp.length() < 2)
                sb.append(0);
            sb.append(sTemp.toUpperCase());
        }
        return sb.toString();
    }

    /**
     * desc:获取保存的Object对象
     *
     * @param context
     * @param key
     * @return modified:
     */
    public static Object readObject(Context context, String key) {
        try {
            SharedPreferences sharedata = context.getSharedPreferences(FILE_NAMES, Context.MODE_PRIVATE);
            if (sharedata.contains(key)) {
                String string = sharedata.getString(key, "");
                if (TextUtils.isEmpty(string)) {
                    return null;
                } else {
                    //将16进制的数据转为数组，准备反序列化
                    byte[] stringToBytes = StringToBytes(string);
                    ByteArrayInputStream bis = new ByteArrayInputStream(stringToBytes);
                    ObjectInputStream is = new ObjectInputStream(bis);
                    //返回反序列化得到的对象
                    Object readObject = is.readObject();
                    return readObject;
                }
            }
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        //所有异常返回null
        return null;

    }

    /**
     * desc:将16进制的数据转为数组
     *
     * @param data
     * @return modified:
     */
    public static byte[] StringToBytes(String data) {
        String hexString = data.toUpperCase().trim();
        if (hexString.length() % 2 != 0) {
            return null;
        }
        byte[] retData = new byte[hexString.length() / 2];
        for (int i = 0; i < hexString.length(); i++) {
            int int_ch;  // 两位16进制数转化后的10进制数
            char hex_char1 = hexString.charAt(i); ////两位16进制数中的第一位(高位*16)
            int int_ch1;
            if (hex_char1 >= '0' && hex_char1 <= '9')
                int_ch1 = (hex_char1 - 48) * 16;   //// 0 的Ascll - 48
            else if (hex_char1 >= 'A' && hex_char1 <= 'F')
                int_ch1 = (hex_char1 - 55) * 16; //// A 的Ascll - 65
            else
                return null;
            i++;
            char hex_char2 = hexString.charAt(i); ///两位16进制数中的第二位(低位)
            int int_ch2;
            if (hex_char2 >= '0' && hex_char2 <= '9')
                int_ch2 = (hex_char2 - 48); //// 0 的Ascll - 48
            else if (hex_char2 >= 'A' && hex_char2 <= 'F')
                int_ch2 = hex_char2 - 55; //// A 的Ascll - 65
            else
                return null;
            int_ch = int_ch1 + int_ch2;
            retData[i / 2] = (byte) int_ch;//将转化后的数放入Byte里
        }
        return retData;
    }

    /**
     * 按Key清除Share
     *
     * @param key
     */
    public static void clearShare(String key) {
        SharedPreferences.Editor sharedata = MyApplication.getInstance().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE).edit();
        sharedata.remove(key);
        sharedata.apply();
    }

    /**
     * 清除所有Share
     */
    public static void clearAllShare() {
        SharedPreferences.Editor editor = MyApplication.getInstance().getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE).edit();
        editor.clear();
        editor.apply();
    }


}
