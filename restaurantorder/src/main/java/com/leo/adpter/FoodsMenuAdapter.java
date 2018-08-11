package com.leo.adpter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.leo.entity.FoodsMenu;
import com.leo.entity.Seat;
import com.leo.restaurantorder.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DELL on 2018/8/6.FoodsMenuAdapter
 */
public class FoodsMenuAdapter extends BaseAdapter {

    private List<FoodsMenu> menuList =new ArrayList<>();
    private Context context;
    static int temp;
    static Object tempObj;

    public FoodsMenuAdapter(List<FoodsMenu> menuList,Context context) {
        this.menuList = menuList;
        this.context=context;
    }

    public void setMenuList(List<FoodsMenu> menuList){
        this.menuList = menuList;
    }

    @Override
    public int getCount() {
        return menuList.size();
    }

    @Override
    public Object getItem(int i) {
        return menuList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder=null;
        if (view == null) {
            Log.i("view:","null");
            holder = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.item_foods, null);
            holder.foodsName=(TextView)view.findViewById(R.id.item_foods_name);
            holder.foodsPrice=(TextView)view.findViewById(R.id.item_foods_price);
            holder.foodsType=(TextView)view.findViewById(R.id.item_foods_type);
            holder.foodsProvide=(TextView)view.findViewById(R.id.item_provide);
            view.setTag(holder);
        }else {
             holder = (ViewHolder) view.getTag();

             }

//        TextView foodsName=(TextView)view.findViewById(R.id.item_foods_name);
//        TextView foodsPrice=(TextView)view.findViewById(R.id.item_foods_price);
//        TextView foodsType=(TextView)view.findViewById(R.id.item_foods_type);
//        TextView foodsProvide=(TextView)view.findViewById(R.id.item_provide);

        FoodsMenu fm=menuList.get(i);

        holder.foodsName.setText(fm.getFoodsName());
        holder.foodsName.setTag(fm.getFoodsId());
        Log.i("id:",""+fm.getFoodsId()+"provide:"+fm.getIsProvide());

        holder.foodsPrice.setText(""+fm.getFoodsPrice());
        holder.foodsType.setText(fm.getFoodsType());
        if(fm.getIsProvide()==0){
            holder.foodsProvide.setText("否");
            //出现控件错乱问题，有规律。目前只能用else解决！！就是再else中再次设置它的默认颜色（或其他属性）
            holder.foodsProvide.setBackgroundColor(Color.parseColor("#BEBEBE"));
        }else{
            holder.foodsProvide.setText("是");
            holder.foodsProvide.setBackgroundColor(Color.parseColor("#FFFFFF"));//出现控件错乱问题，有规律
        }


        //如果list.get(i) 的isProvide为0，说明菜品不供应,那么按钮不可用

        return view;

    }

    static class ViewHolder {
        TextView foodsName;
        TextView foodsPrice;
        TextView foodsType;
        TextView foodsProvide;
         }


}
