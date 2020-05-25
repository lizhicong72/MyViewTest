package com.example.canvastest;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.PermissionChecker;

import android.Manifest;
import android.content.DialogInterface;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

public class MainActivity extends AppCompatActivity {
    private String mPermissions[] = {Manifest.permission.WRITE_EXTERNAL_STORAGE

    };
    private MyView myView;

    Button save;
    Button edit;
    Button color;
    int redInt = 0, greenInt = 0, blueInt = 0;
    int colorSelect = 0;
    EditText line_width;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        power();
        myView = findViewById(R.id.myView);
        myView.setBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.fengjing_1));
        edit = findViewById(R.id.edit);

        color = findViewById(R.id.color);
        save = findViewById(R.id.save);
        line_width = findViewById(R.id.line_width);
//        line_width.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                myView.setWidth(Integer.parseInt(v.getText().toString()));
//                return true;
//            }
//        });
        line_width.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                     if( s.toString().trim()!=""){
                         int m=(Integer.parseInt("0"+s.toString()));
                         if(m<=0||m>50)
                             Toast.makeText(MainActivity.this,"线宽介于1-50之间",Toast.LENGTH_SHORT).show();
                         if(m<=0  )m=1;
                         else  if(m>50)m=50;

                     }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myView.save();
            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!myView.isFlag()) {
                    myView.setFlag(true);
                    edit.setText("结束编辑");
                } else {
                    myView.setFlag(false);
                    edit.setText("编辑");
                }

            }
        });
        color.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                View view = getLayoutInflater().inflate(R.layout.color, null);
                SeekBar red = view.findViewById(R.id.red);
                SeekBar green = view.findViewById(R.id.green);
                SeekBar blue = view.findViewById(R.id.blue);
                red.setProgress(redInt);
                green.setProgress(greenInt);
                blue.setProgress(blueInt);
                final TextView textView = view.findViewById(R.id.back);
                setMyColor(textView, redInt, greenInt, blueInt);
                red.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        redInt = progress;
                        setMyColor(textView, redInt, greenInt, blueInt);
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                });
                green.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        greenInt = progress;
                        setMyColor(textView, redInt, greenInt, blueInt);
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                });
                blue.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        blueInt = progress;
                        setMyColor(textView, redInt, greenInt, blueInt);
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                });
                dialog.setTitle("设置颜色");
                dialog.setIcon(R.drawable.colours);

                dialog.setView(view).setNegativeButton("Cancle", null)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                colorSelect = Color.rgb(redInt, greenInt, blueInt);
                                myView.setColor(colorSelect);

                            }
                        })
                        .create().show();
            }
        });
    }

    private void setMyColor(TextView t, int red, int green, int blue) {
        t.setBackgroundColor(Color.rgb(red, green, blue));
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
