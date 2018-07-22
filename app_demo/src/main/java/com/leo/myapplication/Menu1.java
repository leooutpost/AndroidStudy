package com.leo.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class Menu1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mi=getMenuInflater();
        mi.inflate(R.menu.menu1,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.option1:
                Toast.makeText(Menu1.this, "click m1", Toast.LENGTH_SHORT).show();
                break;
            case R.id.option2:Toast.makeText(Menu1.this, "click m2", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(Menu1.this,Menu2.class);
                startActivity(intent);
                break;
            case R.id.option3:Toast.makeText(Menu1.this, "click m3", Toast.LENGTH_SHORT).show();
                Intent intent2=new Intent(Menu1.this,Menu3.class);
                startActivity(intent2);
                break;
        }

        return super.onOptionsItemSelected(item);
    }



}
