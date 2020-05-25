package com.example.myview;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    PathAnimation animation;
    AnimatorSet animatorSet;
    RelativeLayout p;
    ImageView iv;
    Path path;
    PathView pathView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        animation=findViewById(R.id.path);
        p=findViewById(R.id.p);
        pathView=findViewById(R.id.pathView);

    }
    public void click(View v){
        switch (v.getId()){
            case R.id.start:
                start1();
                pathView.start();
                break;
            case R.id.stop:
                pathView.stop();
                break;
        }
    }
    int flag=1;
    private Path myPath(float x,float y,float x1,float y1){
        float dy=y-y1;
        Random dx=new Random();
        flag=-flag;
        Path path=new Path();
        path.moveTo(x,y);
        path.cubicTo(x+flag*dx.nextInt()%80,y-dy/3,
                x-flag*dx.nextInt()%80,y-dy*2/3,x1,y1);
        return path;
    }
    private  void start1(){
        final ImageView iv=new ImageView(this);
        final float x,y;
        Path path;
        View view= findViewById(R.id.p);
        x=view.getWidth();
        y=view.getHeight();
       // Display display = getWindowManager().getDefaultDisplay();
        //Point size =new Point();
        //display.getSize(size);
       // x = size.x;
       // y = size.y;
        path=myPath(x/2,y,x/2+flag*100,y/2);
        final PathMeasure measure=new PathMeasure(path,false);
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, measure.getLength());


        iv.setBackground(getResources().getDrawable(R.drawable.heart));
        RelativeLayout.LayoutParams params=new RelativeLayout.LayoutParams(50,50);
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        addContentView(iv,new AbsListView.LayoutParams(80,80));

        animatorSet=new AnimatorSet();
        ObjectAnimator alpha=ObjectAnimator.ofFloat(iv,"alpha",1f,0.1f);
        ObjectAnimator rotate=ObjectAnimator.ofFloat(iv,"rotation",0,30,0,-30,0);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float[] mCurrentPosition=new float[2];
                measure.getPosTan((Float) animation.getAnimatedValue(),
                        mCurrentPosition, null);

                iv.setX(mCurrentPosition[0]);
              iv.setY(mCurrentPosition[1]);
           //     iv.setLeft((int) mCurrentPosition[0]);
             //   iv.setTop((int) mCurrentPosition[1]);
                Log.i("(x,y)-->>>",iv.getTop()+" "+ iv.getX()+" "+iv.getLeft()+" "+iv.getY());
            }
        });

        animatorSet.playTogether(alpha,rotate,valueAnimator);
        animatorSet.setDuration(2000);
        animatorSet.start();
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                ViewGroup p= (ViewGroup) iv.getParent();
                p.removeView(iv);
            }
        });

    }
    private void start() {
       final ImageView iv=new ImageView(this);
       final float x,y;
        Display display = getWindowManager().getDefaultDisplay();
         x = display.getWidth();
        y = display.getHeight();

        iv.setBackground(getResources().getDrawable(R.drawable.heart));
        RelativeLayout.LayoutParams params=new RelativeLayout.LayoutParams(50,50);
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
       // params.g
        addContentView(iv,new AbsListView.LayoutParams(80,80));

        animatorSet=new AnimatorSet();
        ObjectAnimator alpha=ObjectAnimator.ofFloat(iv,"alpha",1f,0.1f);
        ObjectAnimator rotate=ObjectAnimator.ofFloat(iv,"rotation",0,30,0,-30,0);
        ValueAnimator trans=ValueAnimator.ofFloat(0,1000);
        trans.setDuration(2000);
       // trans.start();
        trans.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float y1= (float) animation.getAnimatedValue();
                iv.setX(x-200);
                iv.setY(y-y1);
            }
        });
        animatorSet.playTogether(alpha,rotate,trans);
        animatorSet.setDuration(2000);
        animatorSet.start();
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                ViewGroup p= (ViewGroup) iv.getParent();
                   p.removeView(iv);
            }
        });
    }
}
