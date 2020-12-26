package com.sadam.ui4;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * 自定义圆角头像
 * Created by Lrxc on 2017/5/22.
 */

public class CircleIco extends View {
    private Bitmap bitmap;

    public CircleIco(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制
        if (bitmap != null)
            canvas.drawBitmap(getCircleBitmap(), 0, 0, null);
    }

    // 设置bitmap
    public void setImageBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
        invalidate();
    }

    //获取圆角图片
    public Bitmap getCircleBitmap() {
        //获取屏幕宽高
        int w = getWidth();
        int h = getHeight();

        //新建一个位图文件
        Bitmap newBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        //在此位图上进行绘制
        Canvas canvas = new Canvas(newBitmap);

        //初始化画笔
        Paint paint = new Paint();
//        paint.setStrokeWidth(5);//画笔宽度
//        paint.setAntiAlias(true);//是否抗锯齿
//        paint.setDither(true); //防抖动
//        paint.setStyle(Paint.Style.FILL); //画笔类型 STROKE空心 FILL 实心
//        paint.setColor(Color.BLUE);//画笔颜色

        //绘制一个圆
        int radius = Math.min(w, h) / 2;//获取宽和高的较小数
        canvas.drawCircle(w / 2, h / 2, radius, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));//图片相交模式

        //绘制图片底图
        Matrix matrix = new Matrix();
//        matrix.postScale(1, 1);//不缩放，原图显示
        matrix.postScale((float) w / bitmap.getWidth(), (float) h / bitmap.getHeight(), 0, 0);//缩放全部显示
        canvas.drawBitmap(bitmap, matrix, paint);
        return newBitmap;
    }

    // 测量模式
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (bitmap == null) return;

        int bw = bitmap.getWidth();
        int bh = bitmap.getHeight();

        int sizeW = MeasureSpec.getSize(widthMeasureSpec);
        int sizeH = MeasureSpec.getSize(heightMeasureSpec);
        int modeW = MeasureSpec.getMode(widthMeasureSpec);
        int modeH = MeasureSpec.getMode(heightMeasureSpec);

        if (modeW == MeasureSpec.AT_MOST)
            sizeW = bw;

        if (modeH == MeasureSpec.AT_MOST)
            sizeH = bh;

        setMeasuredDimension(sizeW, sizeH);
    }
}