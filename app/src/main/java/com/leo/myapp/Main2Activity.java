package com.leo.myapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Map;

public class Main2Activity extends AppCompatActivity {

    int [] imgData={R.drawable.hamburger,R.drawable.noodles,R.drawable.hotdog,
            R.drawable.hamburger,R.drawable.noodles,R.drawable.hotdog,
            R.drawable.hamburger,R.drawable.noodles,R.drawable.hotdog,};

    String [] nameData={"1","2","3","4","5","6","7","8","9"};

    String [] priceData={"$1.1","$1.1","$1.1","$1.1","$1.1","$1.1","$1.1","$1.1","$1.1"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        ListView lv=(ListView)findViewById(R.id.listView);
        Main2ActivityAdapter adapter=new Main2ActivityAdapter();
        lv.setAdapter(adapter);

    }

    public void picButton(View v) {
        String tag = (String) v.getTag();
        Toast.makeText(Main2Activity.this, tag, Toast.LENGTH_SHORT).show();

    }

    public class Main2ActivityAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return imgData.length;
        }

        @Override
        public Object getItem(int position) {
            return imgData[position];
        }

        @Override
        public long getItemId(int position) {
            return imgData[position];
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view= View.inflate(Main2Activity.this,R.layout.activity_main2_item,null);

            ImageView imageV = (ImageView) view.findViewById(R.id.item_image);
            imageV.setImageResource(imgData[position]);
            TextView textV = (TextView) view.findViewById(R.id.item_name);
            textV.setText(nameData[position]);
            TextView textV2 = (TextView) view.findViewById(R.id.textView7);
            textV2.setText(priceData[position]);

            return view;
        }
    }

}
