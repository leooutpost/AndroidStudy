package com.leo.restaurantorder;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.leo.adpter.FoodsMenuAdapter;
import com.leo.entity.FoodsMenu;
import com.leo.infoContainer.IpContainer;
import com.leo.utils.HttpUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class MenuActivity extends AppCompatActivity {

    private ProgressDialog progressDialog;
    private ListView listView;
    private Button viewOrderMap,clear;
    //交给adapte的list
    private static List<FoodsMenu> list=new ArrayList<>();

    //static private List<FoodsMenu> orderList=new ArrayList<>();
    //key:餐品名称；value：餐品数量。static的数据会在程序执行时一直存在。交给
    static private Map<String,String> tempMenuMap=new HashMap<>();

//    static String listString=null;
//    static int tempSeatNumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        if(tempMenuMap.size()!=0)
            Toast.makeText(MenuActivity.this, "注意！上一次的零时菜单还未清空", Toast.LENGTH_SHORT).show();

        progressDialog=new ProgressDialog(MenuActivity.this);

        clear=(Button)findViewById(R.id.clear_order);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tempMenuMap=null;
                tempMenuMap=new HashMap<String, String>();
                Toast.makeText(MenuActivity.this, "选餐列表已清空", Toast.LENGTH_SHORT).show();
            }
        });


        listView = (ListView) findViewById(R.id.listView_foods_menu);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                FoodsMenu fm=list.get(i);
                if(fm.getIsProvide()==0){
                    AlertDialog.Builder builder=new AlertDialog.Builder(MenuActivity.this);
                    builder.setTitle("选取失败").setMessage("该商品已售空！").show();
                }else{
                    if(!tempMenuMap.containsKey(fm.getFoodsName())){
                        tempMenuMap.put(fm.getFoodsName(),"1");
                    }else{
                        String numString=tempMenuMap.get(""+fm.getFoodsName());
                        int newNum=Integer.parseInt(numString)+1;
                        tempMenuMap.remove(fm.getFoodsName());
                        tempMenuMap.put(fm.getFoodsName(),""+newNum);
                    }
                    Toast.makeText(MenuActivity.this, "+1"+fm.getFoodsName(), Toast.LENGTH_SHORT).show();
                }
//               TextView test=(TextView) view.findViewById(R.id.item_foods_name);
//                test.setText("哈哈哈");
                return true;
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                FoodsMenu fm=list.get(i);
                if(tempMenuMap.containsKey(fm.getFoodsName())){
                    int num=Integer.parseInt(tempMenuMap.get(fm.getFoodsName()));
                    if(num==1){
                        tempMenuMap.remove(fm.getFoodsName());
                    }else if(num>1){
                        tempMenuMap.put(fm.getFoodsName(),""+(num-1));
                    }
                    Toast.makeText(MenuActivity.this, "-1:"+fm.getFoodsName(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        viewOrderMap=(Button)findViewById(R.id.view_order_map);
        viewOrderMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(MenuActivity.this,TempOrderActivity.class);

                i.putExtra("menuMap",(Serializable)tempMenuMap);
                startActivity(i);

                Iterator<Map.Entry<String,String>> it=tempMenuMap.entrySet().iterator();
                while (it.hasNext()){
                    Map.Entry<String,String> e=it.next();
                    Log.i(e.getKey(),e.getValue());
                }
                Log.i("end","-----");
            }
        });

        new MyTask().execute();
    }

    private DialogInterface.OnClickListener dialogClick=new DialogInterface.OnClickListener(){
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            switch (i){
                case -1:
                    Toast.makeText(MenuActivity.this, "click OK!", Toast.LENGTH_SHORT).show();
                    break;
                case -2:
                    Toast.makeText(MenuActivity.this, "click BAD!", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };




    //异步任务,请求“菜单”内容
    class MyTask extends AsyncTask<String,Void,String> {
        //子线程执行前
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {

            String jsonData= HttpUtil.sendHttpRequest(null, IpContainer.QueryMenuServlet,null);
            Log.i("子线程处理数据：",jsonData);
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
               // Log.i("jsonArray Length:",""+ jsonArray.length());
                for (int i = 0; i < jsonArray.length(); i++) {
                    Log.i("deal with jsonArray:",""+i);
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    FoodsMenu fm=new FoodsMenu();
                    fm.setFoodsId(jsonObject.getInt("foodsId"));
                    fm.setFoodsName(jsonObject.getString("foodsName"));
                    fm.setFoodsPrice(jsonObject.getDouble("foodsPrice"));
                    fm.setFoodsType(jsonObject.getString("foodsType"));
                    fm.setIsProvide(jsonObject.getInt("isProvide"));

                    list.add(fm);
                }
                Log.i("listSize1:",""+list.size());
            } catch (JSONException e) {
                e.printStackTrace();
            }

            FoodsMenuAdapter adapter = new FoodsMenuAdapter(list,MenuActivity.this);
            listView.setAdapter(adapter);
            adapter.notifyDataSetChanged();

            progressDialog.dismiss();
        }
    }



}
