package com.leo.restaurantorder;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.leo.infoContainer.IpContainer;
import com.leo.utils.HttpUtil;

import java.util.HashMap;
import java.util.Map;

public class ChangeSeatActivity extends AppCompatActivity {

    TextView oldSeat,newSeat;
    Button changeSeat;

    String oldSeatString,newSeatString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_seat);

        oldSeat=(TextView)findViewById(R.id.old_seat);
        newSeat =(TextView)findViewById(R.id.new_seat);
        Button changeSeat=(Button)findViewById(R.id.change_seat_excute);
        changeSeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                oldSeatString=oldSeat.getText().toString().trim();
                newSeatString=newSeat.getText().toString().trim();
                new MyTask().execute();
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

            final Map<String,String> map=new HashMap<>();
            map.put("oldSeatNumber",oldSeatString);
            map.put("newSeatNumber",newSeatString);

            String result= HttpUtil.sendHttpRequest(map, IpContainer.ChangeSeatServlet,null);

            return result;
        }

        //回归UI线程，完成控件更新等任务.
        @Override
        protected void onPostExecute(String s) {//参数被传入以doInBackground的返回值
            super.onPostExecute(s);
            Toast.makeText(ChangeSeatActivity.this, s, Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(ChangeSeatActivity.this,MainActivity.class);
            startActivity(intent);
        }


    }


}
