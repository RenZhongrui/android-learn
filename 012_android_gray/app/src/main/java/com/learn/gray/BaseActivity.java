package com.learn.gray;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        changeGray();
    }

    /**
     * 灰白效果
     * 开启硬件加速  android:hardwareAccelerated="true"
     * 这个对AlertDialog不起作用，使用FragmentDailg可以
     */
    private void changeGray() {
        Log.e("changeGray: ", "灰白效果");
        View decorView = getWindow().getDecorView();
        Paint paint = new Paint();
        ColorMatrixColorFilter colorMatrixColorFilter = new ColorMatrixColorFilter(MatrixArrayUtil.gray_color);
        paint.setColorFilter(colorMatrixColorFilter);
        decorView.setLayerType(View.LAYER_TYPE_HARDWARE, paint);
    }

    /**
     *  相当于hook "android:id/content"这一层，替换FrameLayout
     *  这个操作会影响videoView 的显示问题
     */
/*    @Override
    public View onCreateView(@NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs) {
        if ("FrameLayout".equals(name)) {
            int count = attrs.getAttributeCount();
            for (int i = 0; i < count; i++) {
                String attributeName = attrs.getAttributeName(i);
                String attributeValue = attrs.getAttributeValue(i);
                if (attributeName.equals("id")) {
                    int id = Integer.parseInt(attributeValue.substring(1));
                    String idVal = getResources().getResourceName(id);
                    if ("android:id/content".equals(idVal)) {
                        GrayFrameLayout grayFrameLayout = new GrayFrameLayout(context, attrs);
                        return grayFrameLayout;
                    }
                }
            }
        }
        return super.onCreateView(name, context, attrs);
    }*/


}
