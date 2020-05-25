package com.example.myprogress;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class TextAnimaterView extends View {
    TextPaint mPaint;
    ValueAnimator valueAnimator;
    float changeValue = 0.0f;
    Path mPath, dst;
    PathMeasure pathMeasure;

    public TextAnimaterView(Context context) {
        super(context);
    }

    public TextAnimaterView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
init();
   }
   private  void init(){
       mPaint = new TextPaint();
       mPaint.setAntiAlias(true);
       mPaint.setStrokeWidth(10.0f);
       mPaint.setStyle(Paint.Style.STROKE);
       mPaint.setColor(Color.RED);
       mPaint.setTextSize(200);
       dst=new Path();
       mPaint.getTextPath("我是一个兵",0,"我是一个兵".length(),
    50,100,dst);
   }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(dst,mPaint);
    }
}
