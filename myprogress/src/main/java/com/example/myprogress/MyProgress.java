package com.example.myprogress;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

/*
 <attr name="fontsize" format="float"/>
        <attr name="fontcolor" format="color"/>
        <attr name="max" format="integer"/>
        <attr name="progress" format="integer"/>
        <attr name="incirclecolor" format="color"/>
        <attr name="outcirclecolor" format="color"/>
        <attr name="outcirclewidth" format="integer"/>
 */
public class MyProgress extends View {
    private int cx,cy,max=100;
    private int fontcolor=Color.BLACK;

    public float getFontsize() {
        return fontsize;
    }

    public void setFontsize(int fontsize) {
        this.fontsize = fontsize;
    }

    private  float fontsize=40;
    private int incirclecolor=Color.parseColor("#ffcccccc");
    private int outcirclecolor=Color.RED,progress=0,outcirclewidth=30;
    private Paint paint;
    private  void init(){
        paint=new Paint();

    }
    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getFontcolor() {
        return fontcolor;
    }

    public void setFontcolor(int fontcolor) {
        this.fontcolor = fontcolor;
    }

    public int getIncirclecolor() {
        return incirclecolor;
    }

    public void setIncirclecolor(int incirclecolor) {
        this.incirclecolor = incirclecolor;
    }

    public int getOutcirclecolor() {
        return outcirclecolor;
    }

    public void setOutcirclecolor(int outcirclecolor) {
        this.outcirclecolor = outcirclecolor;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        if(progress>=max)
            this.progress=max;
        else
            this.progress = progress;
        invalidate();
    }

    public int getOutcirclewidth() {
        return outcirclewidth;
    }

    public void setOutcirclewidth(int outcirclewidth) {
        this.outcirclewidth = outcirclewidth;
    }

    public MyProgress(Context context) {
        super(context);
        init();
    }

    public MyProgress(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray ta=context.obtainStyledAttributes(attrs,R.styleable.myprogress);
        if(ta!=null){
            fontsize=ta.getDimension(R.styleable.myprogress_fontsize,10f);
            fontcolor=ta.getColor(R.styleable.myprogress_fontcolor, Color.BLACK);
            incirclecolor=ta.getColor(R.styleable.myprogress_incirclecolor,Color.parseColor("#ffcccccc"));
            outcirclecolor=ta.getColor(R.styleable.myprogress_outcirclecolor,Color.RED);
            max=ta.getInteger(R.styleable.myprogress_max,100);
            outcirclewidth=ta.getInteger(R.styleable.myprogress_outcirclewidth,5);
        }
init();

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
       // canvas.drawColor(Color.RED);
        if(progress!=0){

            cx=getWidth()/2;
            cy=getHeight()/2;
            paint.setAntiAlias(true);
            SweepGradient shader=new SweepGradient(cx,cy,new int[]{
                    Color.parseColor("#ffffcccc"), Color.YELLOW,Color.RED
            },null);
            paint.setShader(shader);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(outcirclewidth);
            paint.setColor(outcirclecolor);
            RectF oval=new RectF(15,15,getWidth()-15,getHeight()-15);
            canvas.drawArc(oval,0,progress*360/max,false,paint);
            paint.setShader(null);
            paint.setColor(incirclecolor);
            paint.setStyle(Paint.Style.FILL);
            oval=new RectF(15,15,getWidth()-15,
                    getHeight()-15);
            canvas.drawArc(oval,0,progress*360/max,true,paint);
            paint.setTextAlign(Paint.Align.CENTER);
            paint.setStrokeWidth(1);
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(fontcolor);
            paint.setTextSize(fontsize);
            Paint.FontMetrics fontMetrics=paint.getFontMetrics();
            if(progress!=max)
            canvas.drawText(progress+"",cx,cy+fontMetrics.descent,paint);
            else
                canvas.drawText("完成",cx,cy+fontMetrics.descent,paint);


        }

    }
}
