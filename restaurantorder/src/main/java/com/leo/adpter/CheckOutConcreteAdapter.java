package com.leo.adpter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.leo.entity.Seat;
import com.leo.restaurantorder.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by DELL on 2018/8/7.
 */
public class CheckOutConcreteAdapter extends BaseAdapter{

    //private List<Map<String,String>> foodsNameAndNumber = new ArrayList<>();
    private List<String> keyList=null;
    private List<String> valueList=null;



    private Context context;
    static int temp;
    static Object tempObj;

    public CheckOutConcreteAdapter( List<String> keyList,List<String> valueList, Context context) {
        this.keyList = keyList;
        this.valueList = valueList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return keyList.size();
    }

    @Override
    public Object getItem(int i) {
        return keyList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null) {
            Log.i("view:", "null");
            holder = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.item_temp_order, null);//实际上是occupied_seat
            holder.foodsName = (TextView) view.findViewById(R.id.item_foods_name);
            holder.foodsNumber = (TextView) view.findViewById(R.id.item_foods_num);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();

        }

        holder.foodsName.setText(keyList.get(i));
        holder.foodsNumber.setText(valueList.get(i));

        return view;
    }

    static class ViewHolder {
        TextView foodsNumber;
        TextView foodsName;
    }




}
