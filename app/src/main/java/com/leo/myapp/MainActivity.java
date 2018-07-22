package com.leo.myapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    Button loginButton,registerButton;
    EditText username,userkey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginButton=(Button)findViewById(R.id.loginButton);
        registerButton=(Button)findViewById(R.id.registerButton);

        username=(EditText)findViewById(R.id.editText);
        userkey=(EditText)findViewById(R.id.editText2);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=username.getText().toString();
                String key=userkey.getText().toString();
                if(name.equals("admin")&&key.equals("admin")){
                    Toast.makeText(MainActivity.this,"登入成功",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainActivity.this,"登入失败"+name+key,Toast.LENGTH_SHORT).show();

                }


            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Toast.makeText(MainActivity.this,"注册",Toast.LENGTH_SHORT).show();
            }
        });


    }
}
