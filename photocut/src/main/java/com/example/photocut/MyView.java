package com.example.photocut;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.UUID;

public class MyView extends View {
    Bitmap bitmap = null;
    Canvas bitmapCanvas = null;
    float w, h;
    Matrix matrix = null;
    Paint paint;
    Handler handler;
    float scaleXY=1;
    public MyView(Context context) {
        super(context, null);
    }

    public MyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint=new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);
        handler=new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                Toast.makeText(getContext(),"图片已保存",Toast.LENGTH_SHORT).show();
            }
        };
    }

    public  void save(){
        if(x2>x1&&y2>y1 && bitmap!=null)
        {

            new Thread() {
                @Override
                public void run() {
                    super.run();
                    Bitmap b1=Bitmap.createBitmap(bitmap,(int)(x1/scaleXY),(int)(y1/scaleXY),
                            (int)((x2-x1)/scaleXY),(int)((y2-y1)/scaleXY));
                        String path = Environment.getExternalStorageDirectory().getAbsolutePath() +
                                "/" + UUID.randomUUID() + ".jpg";
                        OutputStream ops = null;
                        try {
                            ops = new FileOutputStream(path);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        b1.compress(Bitmap.CompressFormat.JPEG, 70, ops);
                        Message message = handler.obtainMessage();
                        message.sendToTarget();
                    }

            }.start();

        }

    }
    public void loadImg(Bitmap bitmap) {
        this.bitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);
        bitmapCanvas = new Canvas();
        bitmapCanvas.setBitmap(this.bitmap);



    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        w = getWidth();
        h = getHeight();
        float bw = bitmap.getWidth();
        float bh = bitmap.getHeight();
        matrix = new Matrix();
        float tmpx, tmpy;
        if (bw < w) tmpx = 1;
        else tmpx = w / bw;
        if (bh < h) tmpy = 1;
        else tmpy = h / bh;
        if (tmpx < tmpy) scaleXY = tmpx;
        else scaleXY = tmpy;
        //    Log.d("scale--->>>",w+  " "+ tmpx+"  "+tmpy);
        matrix.setScale(scaleXY, scaleXY);
        //    invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (bitmap != null) {
            canvas.drawBitmap(bitmap, matrix, null);

            canvas.drawRect(x1,y1,x2,y2,paint);

        }
    }

    float x1=0, y1=0;
    float x2=0,y2=0;
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x, y;
        x = event.getX();
        y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                x1=x2 = x;
                y1=y2 = y;
                break;
            case MotionEvent.ACTION_MOVE:
                x2=x;
                y2=y;
                break;
            case MotionEvent.ACTION_UP:
                x2=x;
                y2=y;
                break;
        }
        invalidate();
        return true;

    }
}
