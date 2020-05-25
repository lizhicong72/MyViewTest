package com.example.myview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.Random;

public class PathView extends View {

    PathMeasure pathMeasure;
    float mAnimatedValue;
    Paint paint;
    Path path, dst;
    float mlength;
    ValueAnimator valueAnimator=null;
    public PathView(Context context) {
        super(context);
    }
    public PathView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

    }


    public void start() {
        if(valueAnimator!=null)return;

        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);
        path = new Path();
        dst = new Path();

        path.addCircle(getWidth()/2, getHeight()/2, getWidth() / 3, Path.Direction.CW);
        pathMeasure = new PathMeasure(path, true);
        mlength = pathMeasure.getLength();
         valueAnimator = ValueAnimator.ofFloat(0, 1);
        valueAnimator.setDuration(2000);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mAnimatedValue = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        valueAnimator.start();


    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
          if (valueAnimator!=null) {
            dst.reset();
             float stopD = mAnimatedValue * mlength;
            float startD = 0;
            startD=(float) (stopD - ((0.5 - Math.abs(mAnimatedValue - 0.5)) * mlength));
            //获取当前进度的路径，同时赋值给传入的mDstPath
            pathMeasure.getSegment(startD, stopD, dst, true);
            canvas.save();
             canvas.drawPath(dst, paint);
            canvas.restore();
        }
    }
    public  void stop(){
        if(valueAnimator!=null)
        {   valueAnimator.end();
            valueAnimator=null;

        }
    }
}
