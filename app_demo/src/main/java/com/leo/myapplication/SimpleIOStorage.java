package com.leo.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.BatchUpdateException;

public class SimpleIOStorage extends AppCompatActivity {

    private EditText name, key;
    private Button saveName, getSavedName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_iostorage);

        name = (EditText) findViewById(R.id.userName);
        key = (EditText) findViewById(R.id.userKey);
        saveName = (Button) findViewById(R.id.saveButton);
        getSavedName = (Button)findViewById(R.id.getSavedButton);



        //IO留
        saveName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameString=name.getText().toString().trim();//trim()去除字符串左右端的空白
                String keyString=key.getText().toString().trim();

                FileOutputStream out = null;
                boolean bol = false;
                try {
                    //虚拟机目录：data文件夹-data文件夹-找对应app名的文件
                    out = openFileOutput("name&key.txt",0);
                    String str = "name:"+nameString+";key:"+keyString;
                    out.write(str.getBytes());
                    bol = true;

                    Toast.makeText(SimpleIOStorage.this, "保存状态="+bol
                            +"\n name:"+nameString+"\n key:"+keyString, Toast.LENGTH_SHORT).show();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    try {
                        out.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        getSavedName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FileInputStream input=null;
                try {
                    input =  openFileInput("name&key.txt");//指定文件名，获取该文件的输入流

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();//字节数组输出流对象

                    byte[] buffer = new byte[1024];//字节数组（缓冲区）

                    int len = 0;//用来记录input 写入到 buffer 的字节数

                    while ((len = input.read(buffer)) != -1) {//len=-1时读取结束，这里input流字节写入buffer中
                        baos.write(buffer, 0, len);//buffer字节数组内容写入到baos中
                    }

                    input.close();

                    String getString=baos.toString("GBK");

                    baos.close();

                    Toast.makeText(SimpleIOStorage.this, "getString:"+getString, Toast.LENGTH_SHORT).show();

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    try {
                        input.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });


    }
}
