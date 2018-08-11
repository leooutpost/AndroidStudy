package com.leo.restaurantorder;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.leo.adpter.CheakOutAdapter;
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

public class CheckOutActivity extends AppCompatActivity {

    private ProgressDialog progressDialog;
    ListView listView;

    private List<Seat> seatList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheack_out);

        progressDialog=new ProgressDialog(CheckOutActivity.this);

        new MyTask().execute();

        listView = (ListView) findViewById(R.id.listView_check_out);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView seatNumberTextView=(TextView)view.findViewById(R.id.item_seat_number);
                String seatNumber=""+seatNumberTextView.getText().toString().trim();
                Intent intent=new Intent(CheckOutActivity.this,CheckOutConcreteActivity.class);
                intent.putExtra("seatNumber",seatNumber);
                startActivity(intent);
            }
        });

    }


//    public void onClickCheckOrder(View v){
//        Toast.makeText(CheckOutActivity.this, "c o", Toast.LENGTH_SHORT).show();
//    }


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

            String jsonData= HttpUtil.sendHttpRequest(null, IpContainer.QueryOccupiedSeatServlet,null);

            return jsonData;
        }

        //回归UI线程，完成控件更新等任务.
        @Override
        protected void onPostExecute(String data) {//参数被传入以doInBackground的返回值
            super.onPostExecute(data);
            seatList=null;
            seatList=new ArrayList<>();
            try {
                JSONArray jsonArray = new JSONArray(data);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    Seat s=new Seat();
                    s.setSeatNumber(jsonObject.getInt("seatNumber"));
                    s.setSeatConcrete(jsonObject.getString("seatConcrete"));
                    seatList.add(s);
                }
                Log.i("listSize1:",""+seatList.size());
            } catch (JSONException e) {
                e.printStackTrace();
            }

            CheakOutAdapter adapter = new CheakOutAdapter(seatList,CheckOutActivity.this);
            listView.setAdapter(adapter);
            adapter.notifyDataSetChanged();

            progressDialog.dismiss();
        }
    }

}
