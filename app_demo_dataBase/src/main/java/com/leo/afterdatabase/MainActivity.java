package com.leo.afterdatabase;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.leo.utils.OpenSqlite;

public class MainActivity extends AppCompatActivity {

    View addLayout = null;
    View deleteLayout = null;
    //View addLayout = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void clickButton(View v) {
        int id = v.getId();
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        //根据按钮点击，弹出 增、删、查弹出层
        switch (id) {
            case R.id.doAdd:

                addLayout = View.inflate(MainActivity.this, R.layout.activity_add_data, null);
                builder.setView(addLayout).setNegativeButton("取消", cancel).
                        setPositiveButton("添加", addConfirm).show();

                // Toast.makeText(MainActivity.this, "弹出层：添加一行数据", Toast.LENGTH_SHORT).show();
                break;
            case R.id.doDelete:
                deleteLayout = View.inflate(MainActivity.this, R.layout.activity_delete, null);
                builder.setView(deleteLayout).setNegativeButton("取消", cancel).
                        setPositiveButton("删除", deleteConfirm).show();

              //  Toast.makeText(MainActivity.this, "弹出层：根据商品编号删除一行数据", Toast.LENGTH_SHORT).show();
                break;
            case R.id.doQuery:
                Intent intent=new Intent(MainActivity.this,QueryActivity.class);
                startActivity(intent);
                //Toast.makeText(MainActivity.this, "数据查询", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    //弹出层事件
    //添加确认
    private DialogInterface.OnClickListener addConfirm = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            String name=((EditText)addLayout.findViewById(R.id.addName)).getText().toString().trim();
            String num=((EditText)addLayout.findViewById(R.id.addNumber)).getText().toString().trim();
            String price=((EditText)addLayout.findViewById(R.id.addPrice)).getText().toString().trim();

            String sql="insert into Goods (goodsName,goodsNumber,goodsPrice) " +
                    "values ('"+name+"','"+num+"','"+price+"')";
            getSQLiteDatabase(MainActivity.this).execSQL(sql);

            Toast.makeText(MainActivity.this, name+num+price, Toast.LENGTH_SHORT).show();
        }
    };
    //删除确认
    private DialogInterface.OnClickListener deleteConfirm = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            String deleteCode=((EditText)deleteLayout.findViewById(R.id.deleteCode)).getText().toString().trim();

            String sql="delete from Goods where code='"+deleteCode+"'";
            getSQLiteDatabase(MainActivity.this).execSQL(sql);

            Toast.makeText(MainActivity.this,"delete code:"+deleteCode , Toast.LENGTH_SHORT).show();
        }
    };



    //取消操作
    private DialogInterface.OnClickListener cancel = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            Toast.makeText(MainActivity.this, "取消操作", Toast.LENGTH_SHORT).show();
        }
    };


    public SQLiteDatabase getSQLiteDatabase(Context c){
        OpenSqlite os = new OpenSqlite(c);
        SQLiteDatabase sqLiteDatabase = os.getReadableDatabase();
        return sqLiteDatabase;
    }


}
