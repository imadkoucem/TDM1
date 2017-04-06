package com.mobile.project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YMC on 06/04/2017.
 */

class MenuAdapter extends BaseAdapter{

    Context context;
    List<String> menu_name;
    List<Integer> menu_icon;
    List<Integer> color_array;
    LayoutInflater inflater;
    public MenuAdapter(Context con, List<String> menuname, List<Integer> menuicon){
        context = con;
        menu_name = menuname;
        menu_icon = menuicon;
        inflater = LayoutInflater.from(context);
        color_array = new ArrayList<Integer>();
        color_array.add(R.color.colorPrimary);
        color_array.add(R.color.colorPrimary);
        color_array.add(R.color.colorPrimary);
        color_array.add(R.color.colorPrimary);
        color_array.add(R.color.colorPrimary);
        color_array.add(R.color.colorPrimary);
        color_array.add(R.color.colorPrimary);
        color_array.add(R.color.colorPrimary);
    }
    @Override
    public int getCount() {
        return menu_icon.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView= inflater.inflate(R.layout.row_main_menu, null);
        TextView txttitle = (TextView)convertView.findViewById(R.id.textTitle);
        ImageView imglogo = (ImageView)convertView.findViewById(R.id.imageLogo);

        txttitle.setText(menu_name.get(position));
        txttitle.setTextColor(context.getResources().getColor(color_array.get(position)));
        imglogo.setImageResource(menu_icon.get(position));

        return convertView;
    }
}
