package com.leo.restaurantorder;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.leo.adpter.SpareSeatAdapter;
import com.leo.entity.Seat;
import com.leo.infoContainer.IpContainer;
import com.leo.utils.HttpUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ViewSpareSeatActivity extends AppCompatActivity {

    private ProgressDialog progressDialog;
    private ListView listView;
    private Button flash;

    private static List<Seat> list=new ArrayList<>();
    static String listString=null;
    static int tempSeatNumber;

    public void setListString(String listString){
        this.listString=listString;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_spare_seat);

        progressDialog=new ProgressDialog(ViewSpareSeatActivity.this);
        flash=(Button)findViewById(R.id.flash_spare_seatlist);
        flash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new MyTask().execute();
            }
        });

        new MyTask().execute();

        listView = (ListView) findViewById(R.id.listView_spare_seat);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                //不能再主线程（UI线程）中发送Http请求
//               HttpUtil.sendHttpRequest(null, IpContainer.ChangeSeatStateServlet,null);
                tempSeatNumber=list.get(i).getSeatNumber();
                new Thread() {
                    @Override
                    public void run() {
                        Map<String,String> map=new HashMap<String, String>();
                        map.put("opration","change seat state to occupied");
                        map.put("seatNumber",""+tempSeatNumber);
                       HttpUtil.sendHttpRequest(map,IpContainer.ChangeSeatStateServlet,null);
                    }
                }.start();
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                new MyTask().execute();
                return false;
            }
        });

    }

    //异步任务
    class MyTask extends AsyncTask<String,Void,String> {
        //子线程执行前
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {

            String jsonData= HttpUtil.sendHttpRequest(null, IpContainer.QuerySpareSeatServlet,null);

            return jsonData;
        }

        //回归UI线程，完成控件更新等任务.
        @Override
        protected void onPostExecute(String data) {//参数被传入以doInBackground的返回值
            super.onPostExecute(data);
                list=null;
                list=new ArrayList<>();
                try {
                    JSONArray jsonArray = new JSONArray(data);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Seat s=new Seat();
                        s.setSeatNumber(jsonObject.getInt("seatNumber"));
                        s.setSeatConcrete(jsonObject.getString("seatConcrete"));
                        list.add(s);
                    }
                    Log.i("listSize1:",""+list.size());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            SpareSeatAdapter adapter = new SpareSeatAdapter(list,ViewSpareSeatActivity.this);
            listView.setAdapter(adapter);
            adapter.notifyDataSetChanged();

            progressDialog.dismiss();
        }
    }

}

/*注意问题：
* 1、UI线程不能发送Http请求、OkHttp请求（要创建非UI线程区发送）
* 2、非UI线程中不能改变UI控件内容（要回归到UI线程中区改变）
*
* */
