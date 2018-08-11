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

/**
 * Created by DELL on 2018/8/7.
 */
public class CheakOutAdapter extends BaseAdapter {

    private List<Seat> occupiedList = new ArrayList<>();
    private Context context;
    static int temp;
    static Object tempObj;

    public CheakOutAdapter(List<Seat> occupiedList, Context context) {
        this.occupiedList = occupiedList;
        this.context = context;
    }

    public void setOccupiedList(List<Seat> occupiedList) {
        this.occupiedList = occupiedList;
    }

    @Override
    public int getCount() {
        return occupiedList.size();
    }

    @Override
    public Object getItem(int i) {
        return occupiedList.get(i);
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
            view = LayoutInflater.from(context).inflate(R.layout.item_seat, null);//实际上是occupied_seat
            holder.seatNumber = (TextView) view.findViewById(R.id.item_seat_number);
            holder.seatConcrete = (TextView) view.findViewById(R.id.item_seat_concrete);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();

        }

        Seat s=occupiedList.get(i);

        holder.seatNumber.setText(""+s.getSeatNumber());
        holder.seatConcrete.setText(s.getSeatConcrete());

        return view;
    }

    static class ViewHolder {
        TextView seatNumber;
        TextView seatConcrete;
    }


}


