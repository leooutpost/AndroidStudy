package com.leo.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by DELL on 2018/7/15.
 */
public class OpenSqlite extends SQLiteOpenHelper{

    private final static int DB_VERSION=2;
    private final static String DB_NAME="firstData.db";

    OpenSqlite(Context context){
        super(context,DB_NAME,null,DB_VERSION);
    }

    //第一次创建数据库时执行
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table userinfo(id integer primary key autoincrement,username varchar(10))");
        Log.i("执行了创建方法:","onCreate(SQLiteDatabase sqLiteDatabase)");
    }

    //数据库版本升级时执行
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("alter table userinfo add age int(10)");
        Log.i("执行了更新方法:","onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion)");
    }
}
