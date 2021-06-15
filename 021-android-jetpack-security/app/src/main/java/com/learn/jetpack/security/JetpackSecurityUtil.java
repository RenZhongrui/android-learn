package com.learn.jetpack.security;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.security.keystore.KeyGenParameterSpec;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import androidx.annotation.RequiresApi;
import androidx.security.crypto.EncryptedFile;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKey;
import androidx.security.crypto.MasterKeys;

/**
 * create: Ren Zhongrui
 * date: 2021-06-09
 * description: Jetpack Security加解密工具
 */
public class JetpackSecurityUtil {

    private static final int length = 2048;
    private static final String security_prefs_name = "security_prefs";

    /**
     * 加密文件，注：耗时操作，要在子线程操作
     *
     * @param context
     * @param encryptPath 加密后的文件路径
     * @param plainPath 原文件路径
     * @throws Exception
     */
    public static void encryptFile(Context context, String encryptPath, String plainPath) throws Exception {
        String masterKeyAlias = "";
        // 6.0以上
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            // 废弃的写法
            KeyGenParameterSpec keyGenParameterSpec = MasterKeys.AES256_GCM_SPEC;
            masterKeyAlias = MasterKeys.getOrCreate(keyGenParameterSpec);
        }
        // 加密后的文件
        File encryptFile = new File(encryptPath);
        // 如果密文文件已经存在
        if (encryptFile.exists()) {
            encryptFile.delete();
        }
        // 已经废弃的写法，下面解密使用的是最新写法
        EncryptedFile encryptedFile = new EncryptedFile.Builder(encryptFile, context, masterKeyAlias,
                EncryptedFile.FileEncryptionScheme.AES256_GCM_HKDF_4KB).build();
        // 获取输出流，然后将原文件的流写入到加密文件中
        FileOutputStream fos = encryptedFile.openFileOutput();
        File file = new File(plainPath);
        FileInputStream fis = new FileInputStream(file);
        byte[] buff = new byte[length];
        int len = 0;
        while ((len = fis.read(buff)) != -1) {
            fos.write(buff, 0, len);
            fos.flush();
        }
        fis.close();
        fos.close();
    }

    /**
     * 解密文件，注：耗时操作，要在子线程操作
     * @param context
     * @param encryptPath 加密后的文件
     * @param plainPath   明文文件路径
     * @throws Exception
     */
    public static void decryptFile(Context context, String encryptPath, String plainPath) throws Exception {
        // 6.0以上
        MasterKey masterKey = new MasterKey.Builder(context)
                .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                .build();

        File encryptFile = new File(encryptPath);
        // 已废弃API
   /*     EncryptedFile encryptedFile = new EncryptedFile.Builder(encryptFile, context, masterKeyAlias,
                EncryptedFile.FileEncryptionScheme.AES256_GCM_HKDF_4KB).build();*/
        // 新版API
        EncryptedFile encryptedFile = new EncryptedFile.Builder(context, encryptFile, masterKey,
                EncryptedFile.FileEncryptionScheme.AES256_GCM_HKDF_4KB).build();

        // 获取文件输入流，读取输入流写入到新建的明文文件中
        FileInputStream fis = encryptedFile.openFileInput();
        byte[] buff = new byte[length];
        int len = 0;
        File file = new File(plainPath);
        if (file.exists()) {
            file.delete();
        }
        FileOutputStream fos = new FileOutputStream(file);
        while ((len = fis.read(buff)) != -1) {
            fos.write(buff, 0, len);
            fos.flush();
        }
        fis.close();
        fos.close();
    }

    /**
     * 存储字符串
     *
     * @param context
     * @param key     存储的键
     * @param value   存储的值
     * @throws Exception
     */
    public static void setString(Context context, String key, String value) throws Exception {
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.M) {
            return;
        }
        MasterKey masterKey = new MasterKey.Builder(context)
                .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                .build();
        SharedPreferences security_prefs = EncryptedSharedPreferences.create(context, security_prefs_name, masterKey, EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM);
        // 废弃API写法
/*        KeyGenParameterSpec aes256GcmSpec = MasterKeys.AES256_GCM_SPEC;
        String masterKeyAlias = MasterKeys.getOrCreate(aes256GcmSpec);

        SharedPreferences security_prefs = EncryptedSharedPreferences.create("security_prefs", masterKeyAlias, this, EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM);*/
        // 同步存储的
        security_prefs.edit().putString(key, value).commit();
    }

    /**
     * 获取字符串
     *
     * @param context
     * @param key     存储的键
     * @throws Exception
     */
    public static String getString(Context context, String key) throws Exception {
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.M) {
            return null;
        }
        MasterKey masterKey = new MasterKey.Builder(context)
                .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                .build();
        SharedPreferences security_prefs = EncryptedSharedPreferences.create(context, security_prefs_name, masterKey, EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM);
        String value = security_prefs.getString(key, "");
        return value;
    }

}
