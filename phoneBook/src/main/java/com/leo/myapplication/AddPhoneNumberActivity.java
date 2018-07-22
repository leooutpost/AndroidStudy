package com.leo.myapplication;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.leo.myapplication.utils.DBUtils;


public class AddPhoneNumberActivity extends AppCompatActivity {

    private Button excuteAdd,cancelAdd;
    private EditText widget_name,widget_number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_phone_number);

        excuteAdd=(Button)findViewById(R.id.button_excuteAdd);
        cancelAdd=(Button)findViewById(R.id.button_cancelAdd);
        widget_name=(EditText)findViewById(R.id.editText_newName);
        widget_number=(EditText)findViewById(R.id.editText_newNumber);

    }

    public void buttonClick(View v){
        int id=v.getId();
        Intent i=new Intent();
        switch (id){
            case R.id.button_excuteAdd:
                String name =widget_name.getText().toString().trim();
                Long number=Long.valueOf(widget_number.getText().toString().trim());

                /*执行数据库添加操作*/
                ContentValues cv=new ContentValues();
                cv.put("phoneName",name);
                cv.put("phoneNumber",number);

                SQLiteDatabase db= DBUtils.getDB(AddPhoneNumberActivity.this);
                db.insert("PhoneBook",null,cv);

                i.putExtra("addName","name:"+name);
                i.putExtra("addNumber","number:"+String.valueOf(number));
                setResult(1,i);
                break;
            case R.id.button_cancelAdd:
                i.putExtra("doSomething","excuteAdd done,do flash");
                setResult(0,i);
                break;
            default:break;
        }
        this.finish();
    }


}
