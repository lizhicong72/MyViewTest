package com.example.viewtest;


import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

public class MyView extends View {
   private Bitmap bitmap;
    private int angle=15;
    private  int x=0,y=0;
    private Matrix matrix;
    Paint paint=new Paint();

    public MyView(Context context) {
        super(context,null);

    }

    public MyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        bitmap= Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.cat)
        ,200,260,true);
       matrix=new Matrix();
        paint.setColor(Color.RED);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.d("onMeasure--->>>",widthMeasureSpec+" "+heightMeasureSpec);
        Log.d("onMeasure--->>>",getWidth()+" "+getHeight());
        final int minimumWidth = getSuggestedMinimumWidth();
        final int minimumHeight = getSuggestedMinimumHeight();
        Log.d("onMeasure--->>>",minimumWidth+" "+minimumHeight);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.d("onlayout--->>>",left+" "+top+" "+right+" "+bottom);
    }

    public void setAngle(int angle){
        this.angle=angle;
    }
    public  void setPovit(int x,int y){
        this.x=x;
        this.y=y;
    }
    ValueAnimator valueAnimator=null;
    float drawAngle=0;
    float start=0;
    int f=1;
    public  void drawStart(){
        valueAnimator=ValueAnimator.ofFloat(0,360);
        valueAnimator.setDuration(3000);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.start();
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                start= (float) animation.getAnimatedValue();
                if(start>=270)
                {
                    if(start+drawAngle>=360)
                        drawAngle=360-start;
                }
                if(f==1)
                {
                    if(drawAngle<90)drawAngle+=5;
                    else if(drawAngle>=90){
                        f=0;drawAngle-=2;
                    }

                }
                else{
                    drawAngle-=2;
                    if(drawAngle<=0){
                        f=1;
                        drawAngle=2;
                    }
                }


                invalidate();
            }
        });

    }
    public  void drawCancel(){
        if(valueAnimator!=null){
            valueAnimator.cancel();
            valueAnimator=null;
            drawAngle=0;
            start=0;
            invalidate();
        }
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.parseColor("#ff3c3c3c"));
     //   Log.d("onDraw--->>>",getWidth()+" "+getHeight());
//        Paint paint=new Paint();
//        paint.setColor(Color.parseColor("#ffff0000"));
//        paint.setStyle(Paint.Style.FILL);
//        paint.setStrokeWidth(20);
//        paint.setStrokeCap(Paint.Cap.BUTT);
//        canvas.drawLine(10,10,200,10,paint);
//        paint.setStrokeCap(Paint.Cap.SQUARE);
//        canvas.drawLine(10,50,200,50,paint);
//        paint.setStrokeCap(Paint.Cap.ROUND);
//        canvas.drawLine(10,100,200,100,paint);
     //   Rect rect
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10);
        RectF rect=new RectF(50,50,getWidth()-50,getHeight()-50);
 //       Log.d("drawAngle--->>>>",drawAngle+"");

        canvas.drawArc(rect,start,drawAngle,false,paint);

  //   matrix.reset();
   //  matrix.preTranslate(x,y);
  //   matrix.postRotate(angle);
  //   canvas.drawBitmap(bitmap,matrix,null);

    }
}
