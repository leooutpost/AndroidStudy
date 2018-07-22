package com.leo.interfaces;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.leo.utils.OpenSqlite;

import java.util.Map;

/**
 * Created by DELL on 2018/7/16.
 */
public abstract class DataBaseOperationActivity{

   private SQLiteDatabase db;

    public DataBaseOperationActivity(){

    }

    public DataBaseOperationActivity(Context c){
        this.db=getDB(c);
       // Toast.makeText(DataBaseOperationActivity.this, "DataBaseOperationActivity inited", Toast.LENGTH_SHORT).show();
    }

    //获取数据库
    protected SQLiteDatabase getDB(Context c){
        OpenSqlite os=new OpenSqlite(c);
        SQLiteDatabase sqLiteDatabase=os.getReadableDatabase();
        return sqLiteDatabase;
    };

    //执行SQL语句，并返回影响行数（计算执行sql先后的）
    /*静态代理,要进行增、改、删时由子类实例调用
    * @exSql:要执行的sql语句
    * */
    protected int excuteModifyOperation(String exSql){
        int effectiveRows=0;
        Cursor cursor1=db.rawQuery("select * from userinfo",null);
        int beforeRows=cursor1.getCount();

        db.execSQL(exSql);

        Cursor cursor2=db.rawQuery("select * from userinfo",null);
        int afterRows=cursor2.getCount();
        return effectiveRows;
    }

    protected Cursor excuteQueryOpetion(String exSql){
        Cursor cursor=null;

        cursor=db.rawQuery(exSql,null);

        return cursor;
    }

    protected abstract String createSql(Map<String,String> wordsMap);


}
