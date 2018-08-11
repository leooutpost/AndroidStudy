package com.leo.restaurantorder;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.leo.adpter.CheckOutConcreteAdapter;
import com.leo.infoContainer.IpContainer;
import com.leo.utils.HttpUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class PayMergerActivity extends AppCompatActivity {

    TextView mergerConsume;
    EditText seat1,seat2;
    Button viewMergerConsume,checkout;

    String s1,s2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_merger);

        mergerConsume=(TextView) findViewById(R.id.merger_total_consume);
        seat1=(EditText)findViewById(R.id.merger1);
        seat2=(EditText)findViewById(R.id.merger2);
        viewMergerConsume=(Button)findViewById(R.id.view_merger_consume) ;
        viewMergerConsume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                s1=seat1.getText().toString().trim();
                s2=seat2.getText().toString().trim();
                new MyTask().execute();
            }
        });

        checkout=(Button)findViewById(R.id.merge_checkout);
        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                s1=seat1.getText().toString().trim();
                s2=seat2.getText().toString().trim();
                new MyTask2().execute();
            }
        });


    }

    class MyTask extends AsyncTask<String,Void,String> {

        //子线程执行任务,返回值会作为onPostExecute(String s)的参数
        /*
        servlet:PrintWriter返回数据；
        android Activity:HttpURLConnection 建立连接、访问，BufferReader读取数据
        */
        @Override
        protected String doInBackground(String... strings) {

            //获取对应座位1的消费情况
            Map<String, String> map = new HashMap<String, String>();
            map.put("seatNumber", "" + s1);
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
            //获取消费总额1
            Map<String,String> seatOrderMap=new HashMap<String, String>();
            Log.i("封装传递的数据","----");
            for (int i=0;i<keyList.size();i++){
                Log.i(keyList.get(i),valueList.get(i));
                seatOrderMap.put(keyList.get(i),valueList.get(i));
            }
            String totalConsume1 = HttpUtil.sendHttpRequest(seatOrderMap, IpContainer.QueryTotalConsumeServlet, null);

            //获取对应座位2的消费情况
            Map<String, String> map2 = new HashMap<String, String>();
            map2.put("seatNumber", "" + s2);
            final String jsonOrderInfo2 = HttpUtil.sendHttpRequest(map2, IpContainer.QuerySeatOrderServlet, null);
            final List<String> keyList2 = new ArrayList<String>();
            final List<String> valueList2 = new ArrayList<String>();
            try {

                JSONObject jsonObject = new JSONObject(jsonOrderInfo2);
                Iterator<String> it = jsonObject.keys();
                while (it.hasNext()) {
                    String key = it.next();
                    String value = jsonObject.getString(key);
                    keyList2.add(key);
                    valueList2.add(value);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            //获取消费总额2
            Map<String,String> seatOrderMap2=new HashMap<String, String>();
            Log.i("封装传递的数据","----");
            for (int i=0;i<keyList2.size();i++){

                seatOrderMap2.put(keyList2.get(i),valueList2.get(i));
            }
            String totalConsume2= HttpUtil.sendHttpRequest(seatOrderMap2, IpContainer.QueryTotalConsumeServlet, null);
            double t=Double.parseDouble(totalConsume1)+Double.parseDouble(totalConsume2);
            final String total=String.valueOf(t);

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mergerConsume.setText("总消费（元）："+total);
                    //Toast.makeText(CheckOutConcreteActivity.this, jsonOrderInfo, Toast.LENGTH_SHORT).show();
                }
            });

            return total;
        }
    }


    class MyTask2 extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... strings) {

            final Map<String,String> cheackOutMap=new HashMap<>();
            cheackOutMap.put("seatNumber",s1);
            HttpUtil.sendHttpRequest(cheackOutMap,IpContainer.CheckoutServlet,null);
            //String result=

            final Map<String,String> cheackOutMap2=new HashMap<>();
            cheackOutMap2.put("seatNumber",s2);
            String result=HttpUtil.sendHttpRequest(cheackOutMap2,IpContainer.CheckoutServlet,null);

            return result;
        }

        //回归UI线程，完成控件更新等任务.
        @Override
        protected void onPostExecute(String s) {//参数被传入以doInBackground的返回值
            super.onPostExecute(s);
            Toast.makeText(PayMergerActivity.this, s, Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(PayMergerActivity.this,MainActivity.class);
            startActivity(intent);
        }
    }


}
