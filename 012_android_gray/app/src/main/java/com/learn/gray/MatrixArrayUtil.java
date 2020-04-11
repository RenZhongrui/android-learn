package com.learn.gray;

/**
 * create: Ren Zhongrui
 * date: 2020-04-10
 * description:
 */
public class MatrixArrayUtil {

    // 灰色效果
    public static float[] gray_color = {
            0.33F, 0.59F, 0.11F, 0, 0, //R决定红色
            0.33F, 0.59F, 0.11F, 0, 0, //G决定绿色
            0.33F, 0.59F, 0.11F, 0, 0, //B决定蓝色
            0, 0, 0, 1, 0}; //A决定了透明度,如果有第五列决定偏移量

    // 图像反转
    public static float[] reverse_color = {
            -1, 0, 0, 1, 1,
            0, -1, 0, 1, 1,
            0, 0, -1, 1, 1,
            0, 0, 0, 1, 0};

    // 怀旧效果
    public static float[] old_color = {
            0.393F, 0.769F, 0.189F, 0, 0,
            0.349F, 0.686F, 0.168F, 0, 0,
            0.272F, 0.534F, 0.131F, 0, 0,
            0, 0, 0, 1, 0};

    // 去色效果
    public static float[] remove_color = {
            1.5F, 1.5F, 1.5F, 0, -1,
            1.5F, 1.5F, 1.5F, 0, -1,
            1.5F, 1.5F, 1.5F, 0, -1,
            0, 0, 0, 1, 0};

    // 高饱和度效果
    public static float[] high_saturation_level_color = {
            1.438F, -0.122F, -0.016F, 0, -0.03F,
            -0.062F, 1.378F, -0.016F, 0, 0.05F,
            -0.062F, -0.122F, 1.483F, 0, -0.02F,
            0, 0, 0, 1, 0};


}
