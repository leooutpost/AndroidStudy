package com.leo.restaurantorder;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.leo.infoContainer.IpContainer;
import com.leo.restaurantorder.R;
import com.leo.utils.HttpUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private Button button;
    private ProgressDialog progressDialog;
    private EditText nameEditText,keyEditText;

    private String name,key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        progressDialog=new ProgressDialog(LoginActivity.this);

        nameEditText=(EditText)findViewById(R.id.name);
        keyEditText=(EditText)findViewById(R.id.key);
        name=nameEditText.getText().toString().trim();
        key=keyEditText.getText().toString().trim();

        button=(Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name=nameEditText.getText().toString().trim();
                key=keyEditText.getText().toString().trim();
                new MyTask().execute("");
            }
        });
    }

    class MyTask extends AsyncTask<String,Void,String> {
        //子线程执行前
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show();
        }

        //子线程执行任务,返回值会作为onPostExecute(String s)的参数
        /*
        servlet:PrintWriter返回数据；
        android Activity:HttpURLConnection 建立连接、访问，BufferReader读取数据
        */
        @Override
        protected String doInBackground(String... strings) {

            Map<String,String> map=new HashMap<>();
            map.put("name",name);
            map.put("key",key);

            String confirmResult= HttpUtil.sendHttpRequest(map, IpContainer.LoginServlet,null);

            return confirmResult;
        }

        //回归UI线程，完成控件更新等任务.
        @Override
        protected void onPostExecute(String s) {//参数被传入以doInBackground的返回值
            super.onPostExecute(s);
            if("验证成功".equals(s)){
                Toast.makeText(LoginActivity.this, "验证成功！跳转！", Toast.LENGTH_SHORT).show();
                //Log.i("json:",s);
                Intent i=new Intent(LoginActivity.this,MainActivity.class);
                i.putExtra("jsonData",s);
                startActivity(i);

            }else{
                keyEditText.setText("");
                Toast.makeText(LoginActivity.this, s, Toast.LENGTH_SHORT).show();
//                Toast.makeText(LoginActivity.this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
            }

            progressDialog.dismiss();
        }


    }



}
