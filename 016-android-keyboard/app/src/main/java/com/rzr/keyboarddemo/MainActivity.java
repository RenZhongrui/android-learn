package com.rzr.keyboarddemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

public class MainActivity extends AppCompatActivity {
    private Button btn_show;
    private TextView tv_result;
    private static final String KEYBOARD_PUBLIC_KEY = "" +
            "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCv/4O9zAGS4+E/uQMHRuW+lTJX" +
            "p6pkd96zTPol94CcGjjiySGxVwjnjIXIWXeE5LlD6hSr6RJEZg53SozeCxPOdKQU" +
            "bmOk+pP+z9SJTScelEOV+EtnT6aGDcExDDSpQo9fz1fpM60Z2xgBmbMYQvme8Wwl" +
            "e7xb3s0vWgbteM5VmwIDAQAB";
    private static final String KEYBOARD_PRIVATE_KEY = "" +
            "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAK//g73MAZLj4T+5" +
            "AwdG5b6VMlenqmR33rNM+iX3gJwaOOLJIbFXCOeMhchZd4TkuUPqFKvpEkRmDndK" +
            "jN4LE850pBRuY6T6k/7P1IlNJx6UQ5X4S2dPpoYNwTEMNKlCj1/PV+kzrRnbGAGZ" +
            "sxhC+Z7xbCV7vFvezS9aBu14zlWbAgMBAAECgYEAmFS8Hka5Bf6fM5xa9q1ypOZV" +
            "0HCalqL56o8x5DK46mP34LLdqrT+TAvgr2xgenHNuO6ePb1ZxkHfO3kXPAiydowg" +
            "1XHXUrGeuA6isuxgrwrmGk7/0mYVwispQOlRZo1vLSM3DM3QGAfPG33BPd/S9tAE" +
            "MTBerAMuiEw7wMJvwGECQQDWQJc7nS4A6V8JM1m6D1dNOw8QHrLys3qryWI3aOOr" +
            "oLZQqQw1Pcmr1GoTCaj7b6Xnw+Xl/+sGV27bp65GIA3lAkEA0kq208xrauJhU6j1" +
            "mD1UctK1Iksia2P6iSTm/d9lMgewZeoXxLMOJlBgSDKP0q63Jr53IR5kF50Sfixa" +
            "VrqdfwJAciMMrexpEoFAaKVbenwrtO/ucVb5W8PzaMqqsPwGtWqzVTsJTt+wrQJG" +
            "1AeEZxYuWs6IjcOND97KJugoNiFjZQJAfqizn7PjCviY6Fu+uNhPse0JXkNk0svg" +
            "DZRlcImKGO0aqPerIzU5k4gbxcB2cc370GEtvYvhe3uGtOof61bxHwJBALPKVEh7" +
            "F0FB2x0T07wqxNPm4pYh+Wh+hbo45CRZKNIQ5x87au5Bxs/0IByRNC/37/ibONy0" +
            "AU9RNLKOkaPLnBk=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_show = findViewById(R.id.btn_show);
        tv_result = findViewById(R.id.tv_result);
        btn_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
  /*              SecurityKeyboard securityKeyboard = new SecurityKeyboard(MainActivity.this);
                securityKeyboard.show();
                securityKeyboard.setOnInputListener(new SecurityKeyboard.InputListener() {
                    @Override
                    public void input(String number, String result) {
                        tv_result.setText(result);
                    }

                    @Override
                    public void delete(String result) {
                        tv_result.setText(result);
                    }

                    @Override
                    public void complete(String result) {
                        tv_result.setText(result);
                    }
                });*/
              /*  CommomDialog commomDialog = new CommomDialog(MainActivity.this);
                commomDialog.show();*/
            }
        });
        // 签名测试(服务器操作)
        String token = "12345678"; // 1、生成随机token
        RSAPrivateKey rsaPrivateKey1 = RSAUtil.loadPrivateKey(KEYBOARD_PRIVATE_KEY); // 2、获取密钥
        byte[] priToken = RSAUtil.encryptByPriKey(rsaPrivateKey1, token.getBytes());// 3、使用密钥进行加密
        Log.e("token加密结果：", RSAUtil.byteArrayToString(priToken));
        byte[] signResult = RSAUtil.sign(RSAUtil.loadPrivateKey(KEYBOARD_PRIVATE_KEY), priToken); // 4、给密文token进行加签
        Log.e("签名结果：", RSAUtil.byteArrayToString(signResult)); // 5、将密文token和签名结果发送给客户端
        // 验签 (客户端操作，客户端验签不需要进行解密，只需对验签结果和token密文进行验证即可)
        // 1、使用加签结果和解密后的token进行验签
        boolean isVerify = RSAUtil.verifySign(RSAUtil.loadPublicKey(KEYBOARD_PUBLIC_KEY), signResult, priToken);
        // 2、获取验签结果
        Log.e("signSrc签名后结果：", isVerify + "");

        // 测试私钥加密，公钥解密
        RSAPublicKey rsaPublicKey1 = RSAUtil.loadPublicKey(KEYBOARD_PUBLIC_KEY);
        byte[] pubToken = RSAUtil.decryptByPubKey(rsaPublicKey1, priToken);
        Log.e("token解密结果：", new String(pubToken));

        // 测试公钥加密，私钥解密
        RSAPublicKey rsaPublicKey = RSAUtil.loadPublicKey(KEYBOARD_PUBLIC_KEY);
        String src = "abc";
        byte[] result = RSAUtil.encryptByPubKey(rsaPublicKey, src.getBytes());
        Log.e("公钥加密后结果：", RSAUtil.byteArrayToString(result));
        RSAPrivateKey rsaPrivateKey = RSAUtil.loadPrivateKey(KEYBOARD_PRIVATE_KEY);
        byte[] decrypt = RSAUtil.decryptByPriKey(rsaPrivateKey, result);
        Log.e("私钥解密后结果：", new String(decrypt));

        Log.e("AES加解密=======", "测试");
        String encrypt = AESUtil.encrypt("12345678", "AES Test");
        Log.e("AES加密结果：", encrypt);
        String resultAES = AESUtil.decrypt("12345678", encrypt);
        Log.e("AES解密结果：", resultAES);

    }
}
