package com.leo.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Toast;

public class Menu3 extends AppCompatActivity {

    //方式二（PopMenu，点击按钮弹出子菜单）
    private Button open;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu3);

        //方式二（点击按钮弹出子菜单）
        open = (Button) findViewById(R.id.open);
        open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(Menu3.this,v);
                popupMenu.getMenuInflater().inflate(R.menu.menu1,popupMenu.getMenu());
                popupMenu.show();

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int id = item.getItemId();
                        switch (id) {
                            case R.id.option1:
                                Toast.makeText(Menu3.this, "点击了op1", Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.option2:
                                Toast.makeText(Menu3.this, "点击了op2", Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.option3:
                                Toast.makeText(Menu3.this, "点击了op3", Toast.LENGTH_SHORT).show();
                                break;
                        }
                        return true;
                    }
                });
            }
        });

    }

    //方式一(选项菜单的子菜单)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //四个参数的含义。1，group的id,2,item的id,3,是否排序，4，将要显示的内容
        menu.add(0,1,0,"菜单一");
        menu.add(0,2,0,"菜单二");
        menu.add(0,3,0,"菜单三");
        menu.add(0,4,0,"菜单四");
        SubMenu sub=menu.addSubMenu("子菜单");
        sub.add(0,5,0,"子菜单一");
        sub.add(0,6,0,"子菜单二");
        sub.add(0,7,0,"子菜单三");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case 1:
                Toast.makeText(Menu3.this,"菜单一",Toast.LENGTH_SHORT).show();
                break;
            case 2:
                Toast.makeText(Menu3.this,"菜单二",Toast.LENGTH_SHORT).show();
                break;
            case 3:
                Toast.makeText(Menu3.this,"菜单三",Toast.LENGTH_SHORT).show();
                break;
            case 4:
                Toast.makeText(Menu3.this,"菜单四",Toast.LENGTH_SHORT).show();
                break;
            case 5:
                Toast.makeText(Menu3.this,"子菜单一",Toast.LENGTH_SHORT).show();
                break;
            case 6:
                Toast.makeText(Menu3.this,"子菜单二",Toast.LENGTH_SHORT).show();
                break;
            case 7:
                Toast.makeText(Menu3.this,"子菜单三",Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }





}
