package com.leo.webmodule;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.leo.entity.Manager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ManagerListActivity extends AppCompatActivity {

    private List<Manager> list=new ArrayList<>();
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_list);

        String jsonData = getIntent().getStringExtra("jsonData");
        //Toast.makeText(ManagerListActivity.this, jsonData, Toast.LENGTH_SHORT).show();

        list = new ArrayList<Manager>();
        //android解析json串
        try {
            JSONArray jsonArray = new JSONArray(jsonData);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Manager m = new Manager();
                m.setManagerName(jsonObject.getString("managerName"));
                m.setManagerPassword(jsonObject.getString("managerPassword"));
                list.add(m);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

//        Log.i("list foreach:","");
//        for (Manager m:list){
//            Log.i("name&key:",m.getManagerName()+";"+m.getManagerPassword());
//        }

        listView = (ListView) findViewById(R.id.listView);
        MyAdapter adapter = new MyAdapter(list);
        listView.setAdapter(adapter);
    }

    class MyAdapter extends BaseAdapter {

        private List<Manager> managerList=new ArrayList<>();

        MyAdapter(List<Manager> managerList) {
            this.managerList = managerList;
        }

        @Override
        public int getCount() {
            return managerList.size();
        }

        @Override
        public Object getItem(int i) {
            return managerList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            View v; //== image_text.xml
            if (view == null) {
                v = LayoutInflater.from(ManagerListActivity.this).inflate(R.layout.item, null);
            } else {
                v = view;
            }
            ImageView imageView = (ImageView) v.findViewById(R.id.item_image);
            TextView textView1 = (TextView) v.findViewById(R.id.item_name);
            TextView textView2 = (TextView) v.findViewById(R.id.textView7);

            imageView.setImageResource(R.mipmap.ic_launcher);
            textView1.setText(managerList.get(i).getManagerName());
            textView2.setText(managerList.get(i).getManagerPassword());

            return v;

        }
    }


}
