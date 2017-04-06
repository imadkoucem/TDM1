package com.mobile.project;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;


public class DetailsFragment extends Fragment implements AdapterView.OnItemClickListener {

    ArrayList menu_icon;
    ArrayList menu_name;
    public DetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_details, container, false);
        menu_icon = new ArrayList<Integer>();
        menu_name = new ArrayList<String>();

        menu_icon.add(R.drawable.date_time);
        menu_icon.add(R.drawable.third_party);
        menu_icon.add(R.drawable.vehicule);
        menu_icon.add(R.drawable.insurance_icon);
        menu_icon.add(R.drawable.police);
        menu_icon.add(R.drawable.description);
        menu_icon.add(R.drawable.casuality);
        menu_icon.add(R.drawable.witnesses);

        menu_name.add(getString(R.string.menu1));
        menu_name.add(getString(R.string.menu2));
        menu_name.add(getString(R.string.menu3));
        menu_name.add(getString(R.string.menu4));
        menu_name.add(getString(R.string.menu5));
        menu_name.add(getString(R.string.menu6));
        menu_name.add(getString(R.string.menu7));
        menu_name.add(getString(R.string.menu8));

        //AppCompatActivity context = (AppCompatActivity)getActivity().getApplicationContext();
        GridView gridview = (GridView)view.findViewById(R.id.gridView);
        MenuAdapter adapter = new MenuAdapter(view.getContext(),menu_name,menu_icon);
        gridview.setAdapter(adapter);
        gridview.setOnItemClickListener(this);

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
}
