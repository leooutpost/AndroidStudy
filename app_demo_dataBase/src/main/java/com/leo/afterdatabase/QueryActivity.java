package com.leo.afterdatabase;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.leo.beans.Goods;
import com.leo.utils.OpenSqlite;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class QueryActivity extends AppCompatActivity {

    List<Goods> goodsList;
    View updateLayout = null;
    private int updateCode = 0;

    private QueryActivityAdapter adapter = new QueryActivityAdapter();
    private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query);

    }

    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(QueryActivity.this, "onStart()", Toast.LENGTH_SHORT).show();

        initGoodsList();

        //ListView 设置适配器
        lv = (ListView) findViewById(R.id.listView);
        lv.setAdapter(adapter);


        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            AlertDialog.Builder builder = new AlertDialog.Builder(QueryActivity.this);

            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Goods goods = goodsList.get(i);
                updateCode = goods.getCode();

                updateLayout = View.inflate(QueryActivity.this, R.layout.activity_update, null);
                EditText newName, newNum, newPrice;
                newName = (EditText) updateLayout.findViewById(R.id.newName);
                newNum = (EditText) updateLayout.findViewById(R.id.newNumber);
                newPrice = (EditText) updateLayout.findViewById(R.id.newPrice);


                newName.setHint(goods.getGoodsName());
                newNum.setHint(goods.getGoodsNumber());
                newPrice.setHint(goods.getGoodsPrice());

                builder.setView(updateLayout).setPositiveButton("确认修改", updateConfirm).show();

                //Toast.makeText(QueryActivity.this, goodsList.get(i).getGoodsName(), Toast.LENGTH_SHORT).show();
                return false;
            }
        });


    }

    //弹出层事件
    private DialogInterface.OnClickListener updateConfirm = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {

            String name = ((EditText) updateLayout.findViewById(R.id.newName)).getText().toString().trim();
            if ("".equals(name))
                name = ((EditText) updateLayout.findViewById(R.id.newName)).getHint().toString();
            String num = ((EditText) updateLayout.findViewById(R.id.newNumber)).getText().toString().trim();
            if ("".equals(num))
                num = ((EditText) updateLayout.findViewById(R.id.newNumber)).getHint().toString();
            String price = ((EditText) updateLayout.findViewById(R.id.newPrice)).getText().toString().trim();
            if ("".equals(price))
                price = ((EditText) updateLayout.findViewById(R.id.newPrice)).getHint().toString();

            String sql="update Goods set " +
                    "goodsName='"+name+"',goodsNumber='"+num+"',goodsPrice='"+price+"' where code="+updateCode+"";
            OpenSqlite os = new OpenSqlite(QueryActivity.this);
            SQLiteDatabase sqLiteDatabase = os.getReadableDatabase();
            sqLiteDatabase.execSQL(sql);

            Toast.makeText(QueryActivity.this, name + ";" + num + ";" + price + "--" + updateCode, Toast.LENGTH_SHORT).show();
            //QueryActivity.this.finish();

            initGoodsList();
            adapter.notifyDataSetChanged();

        }
    };

    //获取数据库数据，并返回List
    public void initGoodsList(){
        goodsList=null;
        goodsList=new ArrayList<>();
        OpenSqlite os = new OpenSqlite(QueryActivity.this);
        SQLiteDatabase sqLiteDatabase = os.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from Goods", null);
        while (cursor.moveToNext()) {
            Goods g = new Goods(Integer.parseInt(cursor.getString(cursor.getColumnIndex("code"))),
                    cursor.getString(cursor.getColumnIndex("goodsName")),
                    cursor.getString(cursor.getColumnIndex("goodsNumber")),
                    cursor.getString(cursor.getColumnIndex("goodsPrice")));
            Log.i("goods:", Integer.toString(g.getCode()) + ";" + g.getGoodsName() + ";" + g.getGoodsNumber() + ";" + g.getGoodsPrice());

            goodsList.add(g);
        }
    }


    //ListView的适配器
    public class QueryActivityAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return goodsList.size();
        }

        @Override
        public Object getItem(int position) {
            return goodsList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = View.inflate(QueryActivity.this, R.layout.item, null);

            ((TextView) view.findViewById(R.id.t1)).setText(Integer.toString(goodsList.get(position).getCode()));
            ((TextView) view.findViewById(R.id.t2)).setText(goodsList.get(position).getGoodsName());
            ((TextView) view.findViewById(R.id.t3)).setText(goodsList.get(position).getGoodsNumber());
            ((TextView) view.findViewById(R.id.t4)).setText(goodsList.get(position).getGoodsPrice());

            return view;
        }
    }

}
