package com.leo.myapplication.utils;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.leo.myapplication.bean.PhoneBook;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DELL on 2018/7/18.
 */
public class DBUtils {


public static SQLiteDatabase getDB(Context context){
    OpenSqlite os = new OpenSqlite(context);
    SQLiteDatabase sqLiteDatabase = os.getReadableDatabase();
    return  sqLiteDatabase;
}

public static List<PhoneBook> getPhoneBookList(Context context){

    List<PhoneBook> list=new ArrayList<>();

    SQLiteDatabase db= DBUtils.getDB(context);
    Cursor cursor=db.query("PhoneBook",null,null,null,null,null,"id");
    while (cursor.moveToNext()){
        PhoneBook pb=new PhoneBook();
        pb.setId(cursor.getInt(cursor.getColumnIndex("id")));
        pb.setPhoneName(cursor.getString(cursor.getColumnIndex("phoneName")));
        pb.setPhoneNumber(cursor.getLong(cursor.getColumnIndex("phoneNumber")));
        pb.setHeadPicPath(cursor.getString(cursor.getColumnIndex("headPicPath")));

        list.add(pb);
    }
    return list;
}


}
