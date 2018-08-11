package com.leo.restaurantorder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }



    public void onButtonClicked(View v){

        int id=v.getId();
        Intent i=null;
        switch (id){
            case R.id.order:
                i=new Intent(MainActivity.this,MenuActivity.class);
                startActivity(i);
                break;

            case R.id.view_spare_seat:
                i=new Intent(MainActivity.this,ViewSpareSeatActivity.class);
                startActivity(i);
                break;

            case R.id.checkout:
                i=new Intent(MainActivity.this,CheckOutActivity.class);
                startActivity(i);
                break;

            case R.id.change_seat:
                i=new Intent(MainActivity.this,ChangeSeatActivity.class);
                startActivity(i);
                break;

            case R.id.pay_merger:
                i=new Intent(MainActivity.this,PayMergerActivity.class);
                startActivity(i);
                break;

        }
    }


}
