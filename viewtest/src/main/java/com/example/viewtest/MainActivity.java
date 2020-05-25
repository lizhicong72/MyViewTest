package com.example.viewtest;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimatedImageDrawable;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

   private Bitmap bitmap;
 private    ImageView image;
    private EditText x,y,angle;
    private  MyView myView;
    private  MyView1 myView1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        image=findViewById(R.id.image);
        x=findViewById(R.id.x);
        y=findViewById(R.id.y);
        angle=findViewById(R.id.angle);
        myView=findViewById(R.id.myView);
        myView1=findViewById(R.id.myView1);
        bitmap= BitmapFactory.decodeResource(getResources(),R.drawable.gril);

    }
    int i=0;
    boolean f=false;
    public void click(View view){
        switch (view.getId()){
            case R.id.start:
                myView1.init();
                start(i);
                i++;
                if(i==4)i=0;
///                myView.setAngle(Integer.parseInt(angle.getText().toString()));
//                myView.setPovit(Integer.parseInt(angle.getText().toString()),
//                        Integer.parseInt(angle.getText().toString()));

                if(f)
                {
                    myView.drawCancel();
                    f=false;
                }
                else
                {
                    myView.drawStart();
                    f=true;
                }

                break;
        }
    }

    private void start(int x) {
        int width=bitmap.getWidth();
        int heigth=bitmap.getHeight();
        int W=8;
        int wd=width/W;
        int H=4;
        int wh=heigth/H;

        AnimationDrawable animationDrawable=new AnimationDrawable();
        for(int i=0;i<W;i++)
        {
            Bitmap b=Bitmap.createBitmap(bitmap,i*wd,wh*x,wd,wh);
            animationDrawable.addFrame(new BitmapDrawable(getResources(),b),200);
        }
        image.setBackground(animationDrawable);
        animationDrawable.start();



    }
}
