package com.leo.myapplication;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class DialogAndThemeChange extends AppCompatActivity {

    private Button alertDialogButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_and_theme_change);
        alertDialogButton = (Button) findViewById(R.id.button);

        alertDialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder=new AlertDialog.Builder(DialogAndThemeChange.this,R.style.MyCommonDialog);
                //建造者模式，设置方法的返回都是 return this（调用该方法的对象）;
                builder.setMessage("hi!")
                        .setTitle("R U OK?")
                        .setPositiveButton("ok",dialogClick)
                        .setNegativeButton("bad",dialogClick)
                        .show();

            }
        });//end onClick - alertDialogButton

    }

    private DialogInterface.OnClickListener dialogClick=new DialogInterface.OnClickListener(){
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            switch (i){
                case -1:
                    Toast.makeText(DialogAndThemeChange.this, "click OK!", Toast.LENGTH_SHORT).show();
                    break;
                case -2:
                    Toast.makeText(DialogAndThemeChange.this, "click BAD!", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };



}


