package com.mobile.project;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;


public class DetailsFragment extends Fragment implements AdapterView.OnItemClickListener {

    ArrayList menu_icon;
    ArrayList menu_name;
    AlertDialog.Builder builder;

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
        menu_icon.add(R.drawable.exit);

        menu_name.add(getString(R.string.menu1));
        menu_name.add(getString(R.string.menu2));
        menu_name.add(getString(R.string.menu3));
        menu_name.add(getString(R.string.menu4));
        menu_name.add(getString(R.string.menu5));
        menu_name.add(getString(R.string.menu6));
        menu_name.add(getString(R.string.menu7));
        menu_name.add(getString(R.string.menu8));
        menu_name.add(getString(R.string.menu9));

        //AppCompatActivity context = (AppCompatActivity)getActivity().getApplicationContext();
        GridView gridview = (GridView)view.findViewById(R.id.gridView);
        MenuAdapter adapter = new MenuAdapter(view.getContext(),menu_name,menu_icon);
        gridview.setAdapter(adapter);
        gridview.setOnItemClickListener(this);

        builder = new AlertDialog.Builder(view.getContext());

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        switch (i){
            case 0 : showDateTime(); break;
            case 1 : showThirdParty(); break;
            case 2 : showVehicule(); break;
            case 3 : showInsurance(); break;
            case 4 : showPolice(); break;
            case 5 : showDescription(); break;
            case 6 : showCasuality(); break;
            case 7 : showWitness(); break;
            case 8 : getActivity().finish(); break;
        }

    }


    private void showDateTime() {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dview = inflater.inflate(R.layout.dialog_third_party, null);
        //final TextView student_name = (TextView) dview.findViewById(  R.id.dialog_mark_student_name );

        builder.setView(dview)
                // Add action buttons
                .setPositiveButton(getString(R.string.save), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {


                    }
                });
        builder.show();
    }
    private void showThirdParty() {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dview = inflater.inflate(R.layout.dialog_third_party, null);
        //final TextView student_name = (TextView) dview.findViewById(  R.id.dialog_mark_student_name );

        builder.setView(dview)
                // Add action buttons
                .setPositiveButton(getString(R.string.save), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {


                    }
                });
        builder.show();
    }
    private void showVehicule() {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dview = inflater.inflate(R.layout.dialog_vehicle, null);
        //final TextView student_name = (TextView) dview.findViewById(  R.id.dialog_mark_student_name );

        builder.setView(dview)
                // Add action buttons
                .setPositiveButton(getString(R.string.save), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {


                    }
                });
        builder.show();
    }
    private void showInsurance() {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dview = inflater.inflate(R.layout.dialog_insurance, null);
        //final TextView student_name = (TextView) dview.findViewById(  R.id.dialog_mark_student_name );

        builder.setView(dview)
                // Add action buttons
                .setPositiveButton(getString(R.string.save), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {


                    }
                });
        builder.show();
    }
    private void showPolice() {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dview = inflater.inflate(R.layout.dialog_police, null);
        //final TextView student_name = (TextView) dview.findViewById(  R.id.dialog_mark_student_name );

        builder.setView(dview)
                // Add action buttons
                .setPositiveButton(getString(R.string.save), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {


                    }
                });
        builder.show();
    }
    private void showDescription() {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dview = inflater.inflate(R.layout.dialog_description, null);
        //final TextView student_name = (TextView) dview.findViewById(  R.id.dialog_mark_student_name );

        builder.setView(dview)
                // Add action buttons
                .setPositiveButton(getString(R.string.save), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {


                    }
                });
        builder.show();
    }
    private void showCasuality() {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dview = inflater.inflate(R.layout.dialog_casualty, null);
        //final TextView student_name = (TextView) dview.findViewById(  R.id.dialog_mark_student_name );

        builder.setView(dview)
                // Add action buttons
                .setPositiveButton(getString(R.string.save), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {


                    }
                });
        builder.show();
    }
    private void showWitness() {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dview = inflater.inflate(R.layout.dialog_witness, null);
        //final TextView student_name = (TextView) dview.findViewById(  R.id.dialog_mark_student_name );

        builder.setView(dview)
                // Add action buttons
                .setPositiveButton(getString(R.string.save), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {


                    }
                });
        builder.show();
    }


}
