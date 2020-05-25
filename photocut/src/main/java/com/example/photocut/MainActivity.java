package com.example.photocut;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.PermissionChecker;

import android.Manifest;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private String mPermissions[] = {Manifest.permission.WRITE_EXTERNAL_STORAGE

    };
    MyView myView;
    Button cut;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cut=findViewById(R.id.cut);
        myView=findViewById(R.id.myView);
        myView.loadImg(BitmapFactory.decodeResource(getResources(),R.drawable.g1));
        power();
        cut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myView.save();
            }
        });
    }
    private void power() {
        if (Build.VERSION.SDK_INT >= 23) {//6.0才用动态权限

            try {//申请多个权限的方法
                List per = new ArrayList();
                for (int i = 0; i < mPermissions.length; i++)
                    if (PermissionChecker.checkSelfPermission(MainActivity.this, mPermissions[i]) !=
                            PermissionChecker.PERMISSION_GRANTED)

                        per.add(mPermissions[i]);

                if (!per.isEmpty()) {

                    requestPermissions((String[]) per.toArray(new String[per.size()]), 1);
                }
            } catch (Exception e) {

            }
        }
    }
}
