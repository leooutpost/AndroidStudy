package com.leo.adpter;

import android.content.Context;
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
 * Created by DELL on 2018/8/5.
 */
public class SpareSeatAdapter extends BaseAdapter  {

        private List<Seat> seatList =new ArrayList<>();
        private Context context;

        public SpareSeatAdapter(List<Seat> userList,Context context) {
            this.seatList = userList;
        this.context=context;
        }

        public void setSeatList( List<Seat> seatList){
            this.seatList=seatList;
        }

        @Override
        public int getCount() {
            return seatList.size();
        }

        @Override
        public Object getItem(int i) {
            return seatList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return seatList.get(i).getSeatNumber();
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            View v; //== image_text.xml
            if (view == null) {
                v = LayoutInflater.from(context).inflate(R.layout.item_seat, null);
            } else {
                v = view;
            }

            TextView seatNumber=(TextView)v.findViewById(R.id.item_seat_number);
            TextView seatConcrete=(TextView)v.findViewById(R.id.item_seat_concrete);
            seatNumber.setText(""+getItemId(i));
            seatConcrete.setText(seatList.get(i).getSeatConcrete());

            return v;

        }
}
