package com.example.myview;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;

import androidx.annotation.Nullable;

import java.util.Random;

public class PathAnimation extends View {
    Bitmap bitmap;

    Path  path;
    Paint paint;
    private float[] mCurrentPosition = new float[2];
    public void setPath(Path path) {
        this.path = path;
    }

    public PathAnimation(Context context) {
        super(context);
        init();
    }

    public PathAnimation(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    private  void init() {
        int x, y;
        Random t = new Random();
        paint=new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);
        x = getWidth() - 20;
        y = getHeight() - 10;
        path = new Path();
        path.moveTo(x, y);
        path.cubicTo(x-50, y-50, x +t.nextInt() %70, y - 150, x, y - 250);
        bitmap= BitmapFactory.decodeResource(getResources(),R.drawable.heart);

       // ValueAnimator valueAnimator=ValueAnimator.;

    }
    public void startPathAnim(long duration) {
        // 0 － getLength()  
        final PathMeasure mPathMeasure=new PathMeasure(path,false);
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, mPathMeasure.getLength());
        Log.i("length--->>>", "measure length = " + mPathMeasure.getLength());
        valueAnimator.setDuration(duration);
        // 减速插值器  
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (Float) animation.getAnimatedValue();
                // 获取当前点坐标封装到mCurrentPosition  
                mPathMeasure.getPosTan(value, mCurrentPosition, null);
                postInvalidate();
            }
        });
        valueAnimator.start();

       // ObjectAnimator alpha=ObjectAnimator.ofFloat(bitmap,)

        AnimatorSet set=new AnimatorSet();
        set.playTogether(valueAnimator);

    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        init();
        startPathAnim(2000);
       // invalidate();
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        getBackground().setAlpha(100);
       canvas.drawPath(path, paint);
       canvas.drawBitmap(bitmap,mCurrentPosition[0], mCurrentPosition[1],paint);

    }
}
