package com.leo.restaurantorder;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.leo.adpter.TempOrderAdapter;
import com.leo.infoContainer.IpContainer;
import com.leo.utils.HttpUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class TempOrderActivity extends AppCompatActivity {

    Button makeOrder;
    EditText seatNumber;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp_order);

        listView=(ListView)findViewById(R.id.listView_temp_order);

        Intent intent = getIntent();
        //final List<FoodsMenu> orderList = ( List<FoodsMenu>) intent.getSerializableExtra("orderList");
        final Map<String,String> orderMap=(HashMap<String,String>)intent.getSerializableExtra("menuMap");

        List<String> keyList=new ArrayList<>();
        List<String> valueList=new ArrayList<>();
        Iterator<Map.Entry<String,String>> it=orderMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String,String> e=it.next();
            keyList.add(e.getKey());
            valueList.add(e.getValue());
        }
        TempOrderAdapter adapter=new TempOrderAdapter(keyList,valueList,TempOrderActivity.this);
        listView.setAdapter(adapter);

        seatNumber=(EditText)findViewById(R.id.seat_umber);

        makeOrder=(Button)findViewById(R.id.make_order);
        makeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String seatNumberString=seatNumber.getText().toString().trim();
                if("".equals(seatNumberString)||orderMap.size()==0){
                    Toast.makeText(TempOrderActivity.this, "输入座位号后才能下单", Toast.LENGTH_SHORT).show();
                }else{
                    //Toast.makeText(TempOrderActivity.this, "下单", Toast.LENGTH_SHORT).show();

                    orderMap.put("seatNumber",seatNumberString);

                    new Thread() {
                        @Override
                        public void run() {
                            final String result=HttpUtil.sendHttpRequest(orderMap, IpContainer.MakeOrderServlet,null);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                   if("下单失败".equals(result)){
                                       AlertDialog.Builder builder=new AlertDialog.Builder(TempOrderActivity.this);
                                       builder.setTitle("下单失败").setMessage("座位不存在或已被占用!").show();
                                   }else{
                                       Toast.makeText(TempOrderActivity.this, result, Toast.LENGTH_SHORT).show();
                                   }
                                }
                            });
                        }
                    }.start();

                }
            }
        });


//        Log.i("menuMap size",""+orderMap.size());
//        Iterator<Map.Entry<String,String>> it2=orderMap.entrySet().iterator();
//        Log.i("TempOrderActivity","get map");
//        while (it2.hasNext()){
//            Map.Entry<String,String> e=it2.next();
//            Log.i(e.getKey(),e.getValue());
//        }

    }
}
