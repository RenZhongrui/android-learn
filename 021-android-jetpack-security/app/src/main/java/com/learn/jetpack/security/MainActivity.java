package com.learn.jetpack.security;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.security.crypto.EncryptedFile;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKey;
import androidx.security.crypto.MasterKeys;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.security.keystore.KeyGenParameterSpec;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.permissionx.guolindev.PermissionX;
import com.permissionx.guolindev.callback.RequestCallback;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private String TAG = this.getClass().getSimpleName();

    private Button btn_encrypt, btn_decrypt, btn_sp_encrypt, btn_sp_decrypt;
    private String path;
    private String encryptPath;
    private String plainPath;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        PermissionX.init(this)
                .permissions(Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS,
                        Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .request(new RequestCallback() {
                    @Override
                    public void onResult(boolean allGranted, List<String> grantedList, List<String> deniedList) {
                        if (allGranted) {
                        }
                    }
                });

        path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/download/demo.jpg";
        encryptPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/download/demo.encrypt.jpg";
        plainPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/download/demo.plain.jpg";

        btn_sp_encrypt = findViewById(R.id.btn_sp_encrypt);
        btn_sp_decrypt = findViewById(R.id.btn_sp_decrypt);
        btn_encrypt = findViewById(R.id.btn_encrypt);
        btn_decrypt = findViewById(R.id.btn_decrypt);
        // 点击加密
        btn_encrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    // encryptFile();
                    JetpackSecurityUtil.encryptFile(mContext, encryptPath, path);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        // 点击解密
        btn_decrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    //decryptFile();
                    JetpackSecurityUtil.decryptFile(mContext, encryptPath, plainPath);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        // sp加密
        btn_sp_encrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    // encryptSP();
                    JetpackSecurityUtil.setString(mContext, "password", "123456");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        // sp解密
        btn_sp_decrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    //decryptSP();
                    String password = JetpackSecurityUtil.getString(mContext, "password");
                    Log.e(TAG, "decryptSP: " + password);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 文件加密，注意要写到子线程运行
     *
     * @throws Exception
     */
    private void encryptFile() throws Exception {
        String masterKeyAlias = "";
        // 6.0以上
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            KeyGenParameterSpec keyGenParameterSpec = MasterKeys.AES256_GCM_SPEC;
            masterKeyAlias = MasterKeys.getOrCreate(keyGenParameterSpec);
        }

        File encryptFile = new File(encryptPath);
        if (encryptFile.exists()) {
            encryptFile.delete();
        }

        File file = new File(path);
        FileInputStream fis = new FileInputStream(file);

        EncryptedFile encryptedFile = new EncryptedFile.Builder(encryptFile, this, masterKeyAlias,
                EncryptedFile.FileEncryptionScheme.AES256_GCM_HKDF_4KB).build();
        FileOutputStream fos = encryptedFile.openFileOutput();

        byte[] buff = new byte[2048];
        int len = 0;
        while ((len = fis.read(buff)) != -1) {
            fos.write(buff, 0, len);
            fos.flush();
        }
        fis.close();
        fos.close();
    }

    private void decryptFile() throws Exception {

        String masterKeyAlias = "";
        // 6.0以上
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            KeyGenParameterSpec keyGenParameterSpec = MasterKeys.AES256_GCM_SPEC;
            masterKeyAlias = MasterKeys.getOrCreate(keyGenParameterSpec);
        }

        File encryptFile = new File(encryptPath);

/*        MasterKey masterKey = new MasterKey.Builder(this, "").build();
        new EncryptedFile.Builder(this, encryptFile, masterKey, EncryptedFile.FileEncryptionScheme.AES256_GCM_HKDF_4KB).build();*/

        EncryptedFile encryptedFile = new EncryptedFile.Builder(encryptFile, this, masterKeyAlias,
                EncryptedFile.FileEncryptionScheme.AES256_GCM_HKDF_4KB).build();
        FileInputStream fis = encryptedFile.openFileInput();
        byte[] buff = new byte[2048];
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

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void encryptSP() throws Exception {
        //
        MasterKey masterKey = new MasterKey.Builder(this)
                .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                .build();
        SharedPreferences security_prefs = EncryptedSharedPreferences.create(this, "security_prefs", masterKey, EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM);

/*        KeyGenParameterSpec aes256GcmSpec = MasterKeys.AES256_GCM_SPEC;
        String masterKeyAlias = MasterKeys.getOrCreate(aes256GcmSpec);

        SharedPreferences security_prefs = EncryptedSharedPreferences.create("security_prefs", masterKeyAlias, this, EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM);*/
        security_prefs.edit().putString("password", "123456").commit();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void decryptSP() throws Exception {
        MasterKey masterKey = new MasterKey.Builder(this)
                .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                .build();
        SharedPreferences security_prefs = EncryptedSharedPreferences.create(this, "security_prefs", masterKey, EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM);
        String password = security_prefs.getString("password", "");
        Log.e(TAG, "decryptSP: " + password);
    }

}
