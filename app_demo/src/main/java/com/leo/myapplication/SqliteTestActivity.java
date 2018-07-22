package com.leo.myapplication;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class SqliteTestActivity extends AppCompatActivity {

    private Button create, add,query,delete,upadte;

        public static SQLiteDatabase a(Context c){
        OpenSqlite os = new OpenSqlite(c);
        SQLiteDatabase sqLiteDatabase = os.getReadableDatabase();
            return sqLiteDatabase;
         }


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite_test);

        create=(Button)findViewById(R.id.createDB);
        add =(Button)findViewById(R.id.addRow);
        query=(Button)findViewById(R.id.queryDB);
        delete=(Button)findViewById(R.id.deleteRow);
        upadte=(Button)findViewById(R.id.updateID1Name);

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenSqlite os = new OpenSqlite(SqliteTestActivity.this);
                SQLiteDatabase sqLiteDatabase = os.getReadableDatabase();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenSqlite os = new OpenSqlite(SqliteTestActivity.this);
                SQLiteDatabase sqLiteDatabase = os.getReadableDatabase();

                sqLiteDatabase.execSQL("insert into userinfo (username,age) values ('leo',123)");
            }
        });

        query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenSqlite os = new OpenSqlite(SqliteTestActivity.this);
                SQLiteDatabase sqLiteDatabase = os.getReadableDatabase();

                Cursor cursor=sqLiteDatabase.rawQuery("select * from userinfo",null);
                int colNum=cursor.getCount();
                Toast.makeText(SqliteTestActivity.this, "colNumber:"+colNum, Toast.LENGTH_SHORT).show();
                while (cursor.moveToNext()){
                    String username = cursor.getString(cursor.getColumnIndex("username"));
                    int id = cursor.getInt(cursor.getColumnIndex("id"));
                    Toast.makeText(SqliteTestActivity.this, username+"\n id:"+id, Toast.LENGTH_SHORT).show();
                }

            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenSqlite os = new OpenSqlite(SqliteTestActivity.this);
                SQLiteDatabase sqLiteDatabase = os.getReadableDatabase();

                sqLiteDatabase.execSQL("delete from userinfo where id>1");
            }
        });

        upadte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenSqlite os = new OpenSqlite(SqliteTestActivity.this);
                SQLiteDatabase sqLiteDatabase = os.getReadableDatabase();

                sqLiteDatabase.execSQL("update userinfo set username='red' where id=1");
            }
        });


    }


}
