package com.example.canvastest;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

public class MyView extends View {
    Bitmap mBitmap = null;
    Canvas mCanvas = null;
    Handler handler;
    Paint paint = null;
    Path path = null;
   Matrix matrix=null;
    public void setColor(int color) {
        this.color = color;
        paint.setColor(color);
    }

    public void setWidth(int width) {
        this.width = width;
        paint.setStrokeWidth(width);
    }

    float preX, preY;
    int color=Color.RED;
    int tranX=0,tranY=0;
    boolean flag=false;
    int width=2;
    public MyView(Context context) {
        super(context);
    }

    public void setBitmap(Bitmap bitmap) {
         mBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);
      //  mBitmap = bitmap;
        mCanvas = new Canvas();
        mCanvas.setBitmap(mBitmap);

    }

    public void save() {

        new Thread() {
            @Override
            public void run() {
                super.run();
                if (mBitmap != null) {
                    String path = Environment.getExternalStorageDirectory().getAbsolutePath() +
                            "/" + UUID.randomUUID() + ".jpg";
                    OutputStream ops = null;
                    try {
                        ops = new FileOutputStream(path);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    mBitmap.compress(Bitmap.CompressFormat.JPEG, 70, ops);
                    Message message = handler.obtainMessage();
                    message.sendToTarget();
                }
            }
        }.start();


    }

    public MyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                Toast.makeText(getContext(), "已保存", Toast.LENGTH_SHORT).show();
            }
        };
        paint=new Paint();
        paint.setStyle(Paint.Style.STROKE);
        path=new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(mBitmap==null)return ;
     //   canvas.drawBitmap(mBitmap,0,0,paint);

       Rect rect=new Rect(tranX,tranY,getWidth()+tranX,getHeight()+tranY);
        Rect rectf=new Rect(0,0,getWidth(),getHeight());
        canvas.drawBitmap(mBitmap,rect,rectf,paint);


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        if(!flag){
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:

                    preX = x;
                    preY = y;
                    break;
                case MotionEvent.ACTION_MOVE:

                    break;
                case MotionEvent.ACTION_UP:
                 //   mCanvas.drawPath(path, paint);

                    tranX=tranX+(int)(preX-x);
                    tranY=tranY+(int)(preY-y);
                    if(tranX>mBitmap.getWidth()-getWidth())
                        tranX=mBitmap.getWidth()-getWidth();
                    else if(tranX<=0)
                        tranX=0;
                    if(tranY>mBitmap.getHeight()-getHeight())
                        tranY=mBitmap.getHeight()-getHeight();
                    else if(tranY<=0)
                        tranY=0;
                    //   path.reset();
                    break;
            }
        }
        else{
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    path.moveTo(tranX+x, tranY+y);
                    preX = tranX+x;
                    preY = tranY+y;
                    break;
                case MotionEvent.ACTION_MOVE:
                    path.quadTo(preX, preY, tranX+x, tranY+y);
                    preX = x+tranX;
                    preY = y+tranY;
                    break;
                case MotionEvent.ACTION_UP:
                    mCanvas.drawPath(path, paint);
                    path.reset();
                    break;
            }

        }
        invalidate();
        return true;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public boolean isFlag() {
        return flag;
    }
}
