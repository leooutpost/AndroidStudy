package com.leo.myapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class GridActivity extends AppCompatActivity {

    private int []data={R.drawable.nicefood,R.drawable.nicefood,R.drawable.nicefood,R.drawable.nicefood,
            R.drawable.nicefood,R.drawable.nicefood,R.drawable.nicefood,R.drawable.nicefood,
            R.drawable.nicefood,R.drawable.nicefood,R.drawable.nicefood,R.drawable.nicefood,
            R.drawable.nicefood,R.drawable.nicefood,R.drawable.nicefood,R.drawable.nicefood,
            R.drawable.nicefood,R.drawable.nicefood,R.drawable.nicefood,R.drawable.nicefood,};

    private GridView gv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid);

        gv=(GridView) findViewById(R.id.gridView);
        MyAdapter ma=new MyAdapter();
        gv.setAdapter(ma);
    }

    public class MyAdapter extends BaseAdapter{
        @Override
        public int getCount() {
            return data.length;
        }
        @Override
        public Object getItem(int position) {
            return data[position];
        }
        @Override
        public long getItemId(int position) {
            return data[position];
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View view= View.inflate(GridActivity.this,R.layout.picture_text,null);

            ImageView imageV = (ImageView) view.findViewById(R.id.pictureA);
            imageV.setImageResource(data[position]);
            TextView textV = (TextView) view.findViewById(R.id.textA);
            textV.setText("A");

            return view;
        }

    }


}
