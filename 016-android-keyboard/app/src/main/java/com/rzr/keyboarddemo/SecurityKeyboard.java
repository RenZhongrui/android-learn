package com.rzr.keyboarddemo;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.LinkedList;

/**
 * create: Ren Zhongrui
 * date: 2018-06-01
 * description: 自定义安全软键盘, 字母、数字、字符三种类型，并且随机排列，内容加密
 */

public class SecurityKeyboard extends Dialog implements View.OnClickListener {
    private String TAG = SecurityKeyboard.class.getSimpleName();
    private String type;
    private static final String NUMBER_KB = "number";
    private static final String LETTER_KB = "letter";
    private static final String SYMBOL_KB = "symbol";
    private Context context;
    private String[] numbers = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
    private String[] letters = new String[]{"q", "w", "e", "r", "t", "y", "u", "i", "o", "p", "a", "s", "d", "f", "g", "h", "j", "k", "l", "z", "x", "c", "v", "b", "n", "m"};
    private String[] symbols = new String[]{"~", "`", "!", "@", "#", "$", "%", "^", "&", "*", "(", ")", "_", "-", "+", "=", "{", "}", "[", "]", "|", "\\", ":", ";", "\"", "'", "<", ",", ">", ".", "?", "/"};
    private boolean isUpLetter = false;
    private TextView kb_close, num0, num1, num2, num3, num4, num5, num6, num7, num8, num9;
    private TextView letter0, letter1, letter2, letter3, letter4, letter5, letter6, letter7, letter8, letter9,
            letter10, letter11, letter12, letter13, letter14, letter15, letter16, letter17, letter18, letter19,
            letter20, letter21, letter22, letter23, letter24, letter25, key_letter_number, key_letter_symbol;
    private TextView symbol0, symbol1, symbol2, symbol3, symbol4, symbol5, symbol6, symbol7, symbol8, symbol9, symbol10,
            symbol11, symbol12, symbol13, symbol14, symbol15, symbol16, symbol17, symbol18, symbol19, symbol20, symbol21,
            symbol22, symbol23, symbol24, symbol25, symbol26, symbol27, symbol28, symbol29, symbol30, symbol31;
    private LinkedList<TextView> tv_numbers = new LinkedList<>();
    private LinkedList<TextView> tv_letters = new LinkedList<>();
    private LinkedList<TextView> tv_symbols = new LinkedList<>();
    private TextView key_number_symbol, key_number_letter, key_symbol_number, key_symbol_letter;
    private LinearLayout layout_kb_number, layout_kb_letter, layout_kb_symbol, key_number_delete, key_letter_toupcase, key_letter_delete, key_symbol_delete;
    private int resultCountNum = 0;
    private String result = "";
    private InputListener mInputListener;

    public interface InputListener {
        void input(String key, String result);

        void delete(String result);

        void complete(String result);
    }

    public SecurityKeyboard(@NonNull Context context) {
        super(context, R.style.SecurityKeyboardTheme);
        this.context = context;
    }

    public void setOnInputListener(InputListener inputListener) {
        this.mInputListener = inputListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.keyboard_view);
        setLayout();
        initView();
    }

    private void setLayout() {
        setCanceledOnTouchOutside(false);
        int width = context.getResources().getDisplayMetrics().widthPixels;
        int height = context.getResources().getDisplayMetrics().heightPixels;
        Window window = getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.width = width;
        attributes.height = (int) (0.4 * height);
        attributes.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
        window.setAttributes(attributes);
    }

    // 初始化
    private void initView() {
        layout_kb_number = findViewById(R.id.layout_kb_number);
        layout_kb_letter = findViewById(R.id.layout_kb_letter);
        layout_kb_symbol = findViewById(R.id.layout_kb_symbol);
        kb_close = findViewById(R.id.kb_close);
        kb_close.setOnClickListener(this);

        initNumberView();
        initLetterView();
        initSymbolView();
    }

    // 初始化数字键盘
    private void initNumberView() {
        shuffleSort(numbers);
        num0 = findViewById(R.id.num0);
        tv_numbers.add(num0);
        num1 = findViewById(R.id.num1);
        tv_numbers.add(num1);
        num2 = findViewById(R.id.num2);
        tv_numbers.add(num2);
        num3 = findViewById(R.id.num3);
        tv_numbers.add(num3);
        num4 = findViewById(R.id.num4);
        tv_numbers.add(num4);
        num5 = findViewById(R.id.num5);
        tv_numbers.add(num5);
        num6 = findViewById(R.id.num6);
        tv_numbers.add(num6);
        num7 = findViewById(R.id.num7);
        tv_numbers.add(num7);
        num8 = findViewById(R.id.num8);
        tv_numbers.add(num8);
        num9 = findViewById(R.id.num9);
        tv_numbers.add(num9);
        for (int i = 0; i < numbers.length; i++) {
            TextView number = tv_numbers.get(i);
            number.setText(numbers[i]);
            number.setOnClickListener(this);
        }
        key_number_symbol = findViewById(R.id.key_number_symbol);
        key_number_symbol.setOnClickListener(this);
        key_number_letter = findViewById(R.id.key_number_letter);
        key_number_letter.setOnClickListener(this);
        key_number_delete = findViewById(R.id.key_number_delete);
        key_number_delete.setOnClickListener(this);
    }

    // 初始化字母键盘
    private void initLetterView() {
        shuffleSort(letters);
        letter0 = findViewById(R.id.letter0);
        tv_letters.add(letter0);

        letter1 = findViewById(R.id.letter1);
        tv_letters.add(letter1);

        letter2 = findViewById(R.id.letter2);
        tv_letters.add(letter2);

        letter3 = findViewById(R.id.letter3);
        tv_letters.add(letter3);

        letter4 = findViewById(R.id.letter4);
        tv_letters.add(letter4);

        letter5 = findViewById(R.id.letter5);
        tv_letters.add(letter5);

        letter6 = findViewById(R.id.letter6);
        tv_letters.add(letter6);

        letter7 = findViewById(R.id.letter7);
        tv_letters.add(letter7);

        letter8 = findViewById(R.id.letter8);
        tv_letters.add(letter8);

        letter9 = findViewById(R.id.letter9);
        tv_letters.add(letter9);

        letter10 = findViewById(R.id.letter10);
        tv_letters.add(letter10);

        letter11 = findViewById(R.id.letter11);
        tv_letters.add(letter11);

        letter12 = findViewById(R.id.letter12);
        tv_letters.add(letter12);

        letter13 = findViewById(R.id.letter13);
        tv_letters.add(letter13);

        letter14 = findViewById(R.id.letter14);
        tv_letters.add(letter14);

        letter15 = findViewById(R.id.letter15);
        tv_letters.add(letter15);

        letter16 = findViewById(R.id.letter16);
        tv_letters.add(letter16);

        letter17 = findViewById(R.id.letter17);
        tv_letters.add(letter17);

        letter18 = findViewById(R.id.letter18);
        tv_letters.add(letter18);

        letter19 = findViewById(R.id.letter19);
        tv_letters.add(letter19);

        letter20 = findViewById(R.id.letter20);
        tv_letters.add(letter20);

        letter21 = findViewById(R.id.letter21);
        tv_letters.add(letter21);

        letter22 = findViewById(R.id.letter22);
        tv_letters.add(letter22);

        letter23 = findViewById(R.id.letter23);
        tv_letters.add(letter23);

        letter24 = findViewById(R.id.letter24);
        tv_letters.add(letter24);

        letter25 = findViewById(R.id.letter25);
        tv_letters.add(letter25);
        for (int i = 0; i < letters.length; i++) {
            TextView letter = tv_letters.get(i);
            letter.setText(String.valueOf(letters[i]));
            letter.setOnClickListener(this);
        }
        key_letter_number = findViewById(R.id.key_letter_number);
        key_letter_number.setOnClickListener(this);
        key_letter_symbol = findViewById(R.id.key_letter_symbol);
        key_letter_symbol.setOnClickListener(this);
        key_letter_toupcase = findViewById(R.id.key_letter_toupcase);
        key_letter_toupcase.setOnClickListener(this);
        key_letter_delete = findViewById(R.id.key_letter_delete);
        key_letter_delete.setOnClickListener(this);

    }

    // 初始化字符键盘
    private void initSymbolView() {
        symbol0 = findViewById(R.id.symbol0);
        tv_symbols.add(symbol0);

        symbol1 = findViewById(R.id.symbol1);
        tv_symbols.add(symbol1);

        symbol2 = findViewById(R.id.symbol2);
        tv_symbols.add(symbol2);

        symbol3 = findViewById(R.id.symbol3);
        tv_symbols.add(symbol3);

        symbol4 = findViewById(R.id.symbol4);
        tv_symbols.add(symbol4);

        symbol5 = findViewById(R.id.symbol5);
        tv_symbols.add(symbol5);

        symbol6 = findViewById(R.id.symbol6);
        tv_symbols.add(symbol6);

        symbol7 = findViewById(R.id.symbol7);
        tv_symbols.add(symbol7);

        symbol8 = findViewById(R.id.symbol8);
        tv_symbols.add(symbol8);

        symbol9 = findViewById(R.id.symbol9);
        tv_symbols.add(symbol9);

        symbol10 = findViewById(R.id.symbol10);
        tv_symbols.add(symbol10);

        symbol11 = findViewById(R.id.symbol11);
        tv_symbols.add(symbol11);

        symbol12 = findViewById(R.id.symbol12);
        tv_symbols.add(symbol12);

        symbol13 = findViewById(R.id.symbol13);
        tv_symbols.add(symbol13);

        symbol14 = findViewById(R.id.symbol14);
        tv_symbols.add(symbol14);

        symbol15 = findViewById(R.id.symbol15);
        tv_symbols.add(symbol15);

        symbol16 = findViewById(R.id.symbol16);
        tv_symbols.add(symbol16);

        symbol17 = findViewById(R.id.symbol17);
        tv_symbols.add(symbol17);

        symbol18 = findViewById(R.id.symbol18);
        tv_symbols.add(symbol18);

        symbol19 = findViewById(R.id.symbol19);
        tv_symbols.add(symbol19);

        symbol20 = findViewById(R.id.symbol20);
        tv_symbols.add(symbol20);

        symbol21 = findViewById(R.id.symbol21);
        tv_symbols.add(symbol21);

        symbol22 = findViewById(R.id.symbol22);
        tv_symbols.add(symbol22);

        symbol23 = findViewById(R.id.symbol23);
        tv_symbols.add(symbol23);

        symbol24 = findViewById(R.id.symbol24);
        tv_symbols.add(symbol24);

        symbol25 = findViewById(R.id.symbol25);
        tv_symbols.add(symbol25);

        symbol26 = findViewById(R.id.symbol26);
        tv_symbols.add(symbol26);

        symbol27 = findViewById(R.id.symbol27);
        tv_symbols.add(symbol27);

        symbol28 = findViewById(R.id.symbol28);
        tv_symbols.add(symbol28);

        symbol29 = findViewById(R.id.symbol29);
        tv_symbols.add(symbol29);

        symbol30 = findViewById(R.id.symbol30);
        tv_symbols.add(symbol30);

        symbol31 = findViewById(R.id.symbol31);
        tv_symbols.add(symbol31);

        for (int i = 0; i < symbols.length; i++) {
            TextView symbol = tv_symbols.get(i);
            symbol.setText(symbols[i]);
            symbol.setOnClickListener(this);
        }

        key_symbol_number = findViewById(R.id.key_symbol_number);
        key_symbol_number.setOnClickListener(this);
        key_symbol_letter = findViewById(R.id.key_symbol_letter);
        key_symbol_letter.setOnClickListener(this);
        key_symbol_delete = findViewById(R.id.key_symbol_delete);
        key_symbol_delete.setOnClickListener(this);
    }

    // 创建三种布局
    public void createKeyboardView() {


    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.kb_close) {
            this.dismiss();
        } else if (id == R.id.key_number_symbol) {
            shuffleSort(numbers);
            setNumberKey();
            shuffleSort(letters);
            setLetterLowerCase();
            layout_kb_number.setVisibility(View.GONE);
            layout_kb_letter.setVisibility(View.GONE);
            layout_kb_symbol.setVisibility(View.VISIBLE);
        } else if (id == R.id.key_number_letter) {
            shuffleSort(numbers);
            setNumberKey();
            shuffleSort(letters);
            setLetterLowerCase();
            layout_kb_number.setVisibility(View.GONE);
            layout_kb_letter.setVisibility(View.VISIBLE);
            layout_kb_symbol.setVisibility(View.GONE);
        } else if (id == R.id.key_letter_number) {
            shuffleSort(numbers);
            setNumberKey();
            shuffleSort(letters);
            setLetterLowerCase();
            layout_kb_number.setVisibility(View.VISIBLE);
            layout_kb_letter.setVisibility(View.GONE);
            layout_kb_symbol.setVisibility(View.GONE);
        } else if (id == R.id.key_letter_symbol) {
            shuffleSort(numbers);
            setNumberKey();
            shuffleSort(letters);
            setLetterLowerCase();
            layout_kb_number.setVisibility(View.GONE);
            layout_kb_letter.setVisibility(View.GONE);
            layout_kb_symbol.setVisibility(View.VISIBLE);
        } else if (id == R.id.key_symbol_number) {
            shuffleSort(numbers);
            setNumberKey();
            shuffleSort(letters);
            setLetterLowerCase();
            layout_kb_number.setVisibility(View.VISIBLE);
            layout_kb_letter.setVisibility(View.GONE);
            layout_kb_symbol.setVisibility(View.GONE);
        } else if (id == R.id.key_symbol_letter) {
            shuffleSort(numbers);
            setNumberKey();
            shuffleSort(letters);
            setLetterLowerCase();
            layout_kb_number.setVisibility(View.GONE);
            layout_kb_letter.setVisibility(View.VISIBLE);
            layout_kb_symbol.setVisibility(View.GONE);
        } else if (id == R.id.key_number_delete || id == R.id.key_letter_delete || id == R.id.key_symbol_delete) {
            deleteResult();
        } else if (id == R.id.key_letter_toupcase) {
            changeLowerAndUpperCase();
        } else {
            resultCountNum++;
            String key = ((TextView) view).getText().toString();
            result += key;
            if (mInputListener != null) {
                mInputListener.input(key, result);
            }
        }
    }

    private void setNumberKey() {
        for (int i = 0; i < numbers.length; i++) {
            TextView number = tv_numbers.get(i);
            number.setText(numbers[i]);
        }
    }

    // 切换大小写
    private void changeLowerAndUpperCase() {
        if (!isUpLetter) {
            setLetterUpperCase();
        } else {
            setLetterLowerCase();
        }
    }

    // 设置成小写
    private void setLetterLowerCase() {
        for (int i = 0; i < letters.length; i++) {
            TextView letter = tv_letters.get(i);
            letter.setText(letters[i].toLowerCase());
        }
        isUpLetter = false;
    }

    // 设置成大写
    private void setLetterUpperCase() {
        for (int i = 0; i < letters.length; i++) {
            TextView letter = tv_letters.get(i);
            letter.setText(String.valueOf(letters[i].toUpperCase()));
        }
        isUpLetter = true;
    }

    // 删除按键
    private void deleteResult() {
        if (resultCountNum == 0) return;
        resultCountNum--;
        result = result.substring(0, result.length() - 1);
        if (mInputListener != null) {
            mInputListener.delete(result);
        }
    }

    /**
     * 洗牌算法
     *
     * @param data
     */
    private void shuffleSort(String[] data) {

        for (int i = 0; i < data.length - 1; i++) {
            int j = (int) (data.length * Math.random());
            swap(data, i, j);
        }
    }

    /**
     * 数据交换
     *
     * @param data
     * @param i
     * @param j
     */
    private void swap(String[] data, int i, int j) {
        if (i == j) {
            return;
        }
        String temp = "";
        temp = data[i];
        data[i] = data[j];
        data[j] = temp;
    }
}
