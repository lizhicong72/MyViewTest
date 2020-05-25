package com.example.myview;


import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

public class MyView1 extends View {
    public MyView1(Context context) {
        super(context);
    }

    public MyView1(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.i("onMeasure--->>>",widthMeasureSpec+" "+heightMeasureSpec);
        Log.i("onMeasure--->>>",getWidth()+" "+getHeight());
        Log.i("onMeasure--->>>",getMeasuredWidth()+" "+getMeasuredHeight());
      //  if(getWidth()>0){widthMeasureSpec=getWidth();heightMeasureSpec=getHeight();}

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.i("onLayout--->>>",left+" "+top+" "+
                left+"   "+bottom);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.i("onDraw--->>>",getWidth()+" "+getHeight());
    }
}
