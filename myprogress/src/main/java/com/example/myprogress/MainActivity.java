package com.example.myprogress;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {


    MyProgress myProgress;
    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);





        handler=new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case 1:
                        myProgress.setProgress(msg.arg1);

                        break;
                    case 2:
                        parent.removeView(myProgress);
                }
            }
        };
    }
    public  void click(View v){
        switch (v.getId()){
            case R.id.start:

                start();
                break;
        }
    }
    RelativeLayout parent;
    private void start() {
       // myProgress=findViewById(R.id.myProgress);
        myProgress=new MyProgress(this);
        RelativeLayout.LayoutParams params=new RelativeLayout.LayoutParams(200,300);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        params.addRule(RelativeLayout.CENTER_VERTICAL);
        myProgress.setLayoutParams(params);
        parent=findViewById(R.id.parent);
        parent.addView(myProgress);

        new Thread(){
            @Override
            public void run() {
                super.run();
                int i=0;
               do {

                    try {

                        sleep(200);
                        i+=5;
                        Log.e("thread--->>>",i+"");
                        Message message=handler.obtainMessage();
                        message.what=1;
                        message.arg1=i;
                        message.sendToTarget();

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }while(i<myProgress.getMax());
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Message message=handler.obtainMessage();
                message.what=2;
                message.arg1=myProgress.getMax();
                message.sendToTarget();
            }
        }.start();
    }
}
