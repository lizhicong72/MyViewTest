package com.example.viewtest;

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
import android.view.View;

import androidx.annotation.Nullable;

public class MyView1 extends View {
    Paint paint;
    Paint paint1;
    Path pathDst=null;
    PathMeasure pathMeasure;
    Bitmap bitmap;
    Path path;
    float length;
    Boolean f=false;
    public MyView1(Context context) {
        super(context);
    }
    ValueAnimator valueAnimator=null;
    public void init(){

            f = true;

            path = new Path();
            //     pathDst=new Path();
            //  pathDst.moveTo(0,0);
            path.moveTo(50, 150);
            path.lineTo(300, 350);
            path.lineTo(200, 50);
            path.lineTo(100, 350);
            path.lineTo(350, 150);
            path.lineTo(50, 150);
            paint = new Paint();
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(5);
            paint.setColor(Color.RED);

            paint1=new Paint();
            paint1.setColor(Color.RED);
            paint1.setStyle(Paint.Style.FILL_AND_STROKE);

            pathMeasure = new PathMeasure();
            pathMeasure.setPath(path, true);
          //  pathMeasure.getLength();

            valueAnimator = new ValueAnimator();
            valueAnimator.setFloatValues(0, pathMeasure.getLength());
            valueAnimator.start();
            valueAnimator.setDuration(5000);
            valueAnimator.setRepeatCount(ValueAnimator.INFINITE);

            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    length= (float) animation.getAnimatedValue();

//                    if ((Float) animation.getAnimatedValue() == 0) {
//                        pathDst = null;
//                        invalidate();
//                        pathDst = new Path();
//                    }
//                    pathMeasure.getSegment(0, (Float) animation.getAnimatedValue(),
//                            pathDst, true);
                    invalidate();
                }
            });
//Path.close();//闭合曲线
//paint.setStyle(Paint.Style.STROKE);// 为空心五角星


    }
    public MyView1(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        bitmap= BitmapFactory.decodeResource(getResources(),R.drawable.pen_1);


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(f){
            pathDst=new Path();

            pathMeasure.getSegment(0, length,
                           pathDst, true);
            float[] pos=new float[2];
            float[]tan=new float[2];
            pathMeasure.getPosTan(length,pos,tan);

            canvas.drawPath(pathDst,paint);
            canvas.drawBitmap(bitmap,pos[0],pos[1]-bitmap.getHeight(),null);
            canvas.drawCircle(pos[0],pos[1],5,paint1);

        }

    }
}
