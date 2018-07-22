package com.leo.myapplication.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by DELL on 2018/7/16.
 */
public class OpenSqlite extends SQLiteOpenHelper{

    private static final int DB_Version=1;
    private static final String DB_Name="PhoneBook.db";

    public OpenSqlite(Context context){
        super(context,DB_Name,null,DB_Version);//在实例化该类时被调用，开启当前app的数据库
    }

    //首次创建数据库时调用
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table  PhoneBook (id integer primary key autoincrement,phoneName varChar(20),phoneNumber long(11),headPicPath varchar(50))");
        Log.i("执行了创建方法:","onCreate(SQLiteDatabase sqLiteDatabase)");
    }

    //数据库版本升级时调用
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase,int oldVersion, int newVersion) {
        Log.i("执行了更新方法:","onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion)");
    }

}
