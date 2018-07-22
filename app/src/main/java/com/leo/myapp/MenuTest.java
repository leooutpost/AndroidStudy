package com.leo.myapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class MenuTest extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_test);
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
                Toast.makeText(MenuTest.this, "click option1", Toast.LENGTH_SHORT).show();
                break;
            case R.id.option2:Toast.makeText(MenuTest.this, "click option2", Toast.LENGTH_SHORT).show();
                break;
            case R.id.option3:Toast.makeText(MenuTest.this, "click option3", Toast.LENGTH_SHORT).show();
                break;
        }
        
        return super.onOptionsItemSelected(item);
    }
}
