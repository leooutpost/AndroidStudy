package com.leo.webmodule;

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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class WebAccessTest extends AppCompatActivity {

    private Button button;
    private ProgressDialog progressDialog;
    private EditText nameEditText,keyEditText;

    private String name,key;

    //请求的Servlet（ip必须是静态ip，不能是localhost、1127.0.0.1）
    final static String PATH="http://192.168.1.154:8080/AndroidWebTest/AndroidServlet";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_access_test);

        progressDialog=new ProgressDialog(WebAccessTest.this);

        nameEditText=(EditText)findViewById(R.id.name);
        keyEditText=(EditText)findViewById(R.id.key);

        button=(Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name=nameEditText.getText().toString().trim();
                key=keyEditText.getText().toString().trim();
                new MyTask().execute(PATH);
            }
        });

    }//onCreate end


    class MyTask extends AsyncTask<String,Void,String>{
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

            Log.i("访问地址：",strings[0]);
            try {
                Log.i("--：","try-catch");
                Thread.sleep(500);
                HttpURLConnection huc=(HttpURLConnection) new URL(strings[0]).openConnection();
                huc.setRequestMethod("POST");
                huc.setReadTimeout(5000);

                //传递数据
                OutputStream os=huc.getOutputStream();
                String values="name="+name+"&key="+key;
                os.write(values.getBytes());
                os.flush();
                os.close();

                //接收数据
                BufferedReader br=new BufferedReader(new InputStreamReader(huc.getInputStream()));
                String temp="";
                StringBuffer confirmResult=new StringBuffer();

                while ((temp=br.readLine())!=null){
                    Log.i("--","while excuted");
                    confirmResult.append(temp);
                }
                return confirmResult.toString();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "failed";
        }

        //回归UI线程，完成控件更新等任务.
        @Override
        protected void onPostExecute(String s) {//参数被传入以doInBackground的返回值
            super.onPostExecute(s);
            if("验证成功".equals(s)){
                Toast.makeText(WebAccessTest.this, "验证成功！跳转！", Toast.LENGTH_SHORT).show();
                Intent i=new Intent(WebAccessTest.this,HomeActivity.class);
                startActivity(i);

            }else{
                keyEditText.setText("");
                Toast.makeText(WebAccessTest.this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
            }

            progressDialog.dismiss();
        }


    }



}
