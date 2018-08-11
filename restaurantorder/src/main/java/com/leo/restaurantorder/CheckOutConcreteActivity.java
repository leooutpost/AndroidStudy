package com.leo.restaurantorder;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.leo.adpter.CheckOutConcreteAdapter;
import com.leo.infoContainer.IpContainer;
import com.leo.utils.HttpUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class CheckOutConcreteActivity extends AppCompatActivity {

    Button checkOutButton;
    //EditText mergeSeatNumberEditText;
    TextView seatNumberTextView,totalConsumeTextView;

    ListView listView;
    String seatNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out_concrete);

        seatNumberTextView = (TextView) findViewById(R.id.seat_number);
        totalConsumeTextView=(TextView)findViewById(R.id.total_consume);
//        mergeSeatNumberEditText = (EditText) findViewById(R.id.merge_seat);
//        mergeSeatNumberEditText.clearFocus();
        checkOutButton = (Button) findViewById(R.id.pay_order);
        checkOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {//点击事件内不能new Thread，要用AsynTask
                AlertDialog.Builder builder=new AlertDialog.Builder(CheckOutConcreteActivity.this);
                //建造者模式，设置方法的返回都是 return this（调用该方法的对象）;
                builder.setMessage(seatNumber+":"+totalConsumeTextView.getText().toString().trim())
                        .setTitle("确认已付款?")
                        .setPositiveButton("确认",dialogClick)
                        .setNegativeButton("取消",dialogClick)
                        .show();
            }
        });



        Intent getter = getIntent();
        seatNumber = getter.getStringExtra("seatNumber");
        seatNumberTextView.setText(seatNumber);

        listView = (ListView) findViewById(R.id.listView_seat_order);

        new Thread() {
            @Override
            public void run() {
                //获取对应座位的消费情况
                Map<String, String> map = new HashMap<String, String>();
                map.put("seatNumber", "" + seatNumber);
                final String jsonOrderInfo = HttpUtil.sendHttpRequest(map, IpContainer.QuerySeatOrderServlet, null);
                final List<String> keyList = new ArrayList<String>();
                final List<String> valueList = new ArrayList<String>();
                try {

                    JSONObject jsonObject = new JSONObject(jsonOrderInfo);
                    Iterator<String> it = jsonObject.keys();
                    while (it.hasNext()) {
                        String key = it.next();
                        String value = jsonObject.getString(key);
                        keyList.add(key);
                        valueList.add(value);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //获取消费总额
                Map<String,String> seatOrderMap=new HashMap<String, String>();
                Log.i("封装传递的数据","----");
                for (int i=0;i<keyList.size();i++){
                    Log.i(keyList.get(i),valueList.get(i));
                    seatOrderMap.put(keyList.get(i),valueList.get(i));
                }
                final String totalConsume = HttpUtil.sendHttpRequest(seatOrderMap, IpContainer.QueryTotalConsumeServlet, null);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        totalConsumeTextView.setText("总消费（元）："+totalConsume);

                        CheckOutConcreteAdapter adapter=
                                new CheckOutConcreteAdapter(keyList,valueList,CheckOutConcreteActivity.this);
                        listView.setAdapter(adapter);
                        //Toast.makeText(CheckOutConcreteActivity.this, jsonOrderInfo, Toast.LENGTH_SHORT).show();
                    }
                });

            }
        }.start();
        //Log.i("get jsonOrderInfo",jsonOrderInfo);

    }

    private DialogInterface.OnClickListener dialogClick=new DialogInterface.OnClickListener(){
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            switch (i){
                case -1:
                    new MyTask().execute();
                    //Toast.makeText(CheckOutConcreteActivity.this, "sure", Toast.LENGTH_SHORT).show();
                    break;
                case -2:
                    Toast.makeText(CheckOutConcreteActivity.this, "cancel!", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };


    class MyTask extends AsyncTask<String,Void,String> {

        //子线程执行任务,返回值会作为onPostExecute(String s)的参数
        /*
        servlet:PrintWriter返回数据；
        android Activity:HttpURLConnection 建立连接、访问，BufferReader读取数据
        */
        @Override
        protected String doInBackground(String... strings) {

            final Map<String,String> cheackOutMap=new HashMap<>();
            cheackOutMap.put("seatNumber",seatNumber);

            String result=HttpUtil.sendHttpRequest(cheackOutMap,IpContainer.CheckoutServlet,null);

            return result;
        }

        //回归UI线程，完成控件更新等任务.
        @Override
        protected void onPostExecute(String s) {//参数被传入以doInBackground的返回值
            super.onPostExecute(s);
            Toast.makeText(CheckOutConcreteActivity.this, s, Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(CheckOutConcreteActivity.this,MainActivity.class);
            startActivity(intent);
        }


    }



}
/*
*
*



* */