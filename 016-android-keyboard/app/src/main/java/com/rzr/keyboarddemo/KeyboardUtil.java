package com.rzr.keyboarddemo;

import android.app.Activity;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.view.View;

/**
 * create: Ren Zhongrui
 * date: 2018-05-23
 * description:
 */

public class KeyboardUtil {
    private Activity context;
    private Keyboard numberKb; // 数字键盘
    private Keyboard letterKb; // 字母键盘
    private Keyboard symbolKb; // 字符键盘
    private KeyboardView keyboardView;

    public KeyboardUtil(Activity context, KeyboardView keyboardView) {
        this.context = context;
        this.keyboardView = keyboardView;
        numberKb = new Keyboard(context, R.xml.number);
        letterKb = new Keyboard(context, R.xml.letter);
        symbolKb = new Keyboard(context, R.xml.letter);
        keyboardView.setKeyboard(numberKb);
        keyboardView.setEnabled(true);
        keyboardView.setPreviewEnabled(true);
        keyboardView.setOnKeyboardActionListener(listener);
    }

    public void showKeyboard() {
        int visibility = keyboardView.getVisibility();
        if (visibility == View.GONE || visibility == View.INVISIBLE) {
            keyboardView.setVisibility(View.VISIBLE);
        }
    }

    private KeyboardView.OnKeyboardActionListener listener = new KeyboardView.OnKeyboardActionListener() {
        @Override
        public void onKey(int i, int[] ints) {

        }

        @Override
        public void onPress(int i) {

        }

        @Override
        public void onRelease(int i) {

        }

        @Override
        public void onText(CharSequence charSequence) {

        }

        @Override
        public void swipeLeft() {

        }

        @Override
        public void swipeRight() {

        }

        @Override
        public void swipeDown() {

        }

        @Override
        public void swipeUp() {

        }
    };
}
