package com.mobile.project;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.itextpdf.text.List;

import java.util.ArrayList;
import java.util.Calendar;

import model.Casualty;
import model.Data;
import model.Insurance;
import model.Police;
import model.ThirdParty;
import model.Vehicule;
import model.Witness;


public class DetailsFragment extends Fragment implements AdapterView.OnItemClickListener {

    ArrayList menu_icon;
    ArrayList menu_name;
    AlertDialog.Builder builder;
    LinearLayout layout;

    public DetailsFragment() {  }



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
        menu_icon.add(R.drawable.car);
        menu_icon.add(R.drawable.insurance_icon);
        menu_icon.add(R.drawable.police);
        menu_icon.add(R.drawable.description);
        menu_icon.add(R.drawable.casuality);
        menu_icon.add(R.drawable.witnesses);
        menu_icon.add(R.drawable.exit_icon);

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

        layout = (LinearLayout)view.findViewById(R.id.fragment_details);

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
        View dview = inflater.inflate(R.layout.dialog_date, null);
        final TextView tv1 = (TextView) dview.findViewById(  R.id.dialog_date_date );
        //final TextView tv2 = (TextView) dview.findViewById(  R.id.dialog_date_time );
        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog(tv1);
            }
        });

        builder.setView(dview)
                // Add action buttons
                .setPositiveButton(getString(R.string.save), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        Data.dateTime = tv1.getText().toString();
                        showSnackBar();
                    }
                });
        builder.show();
    }

    private void showThirdParty() {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dview = inflater.inflate(R.layout.dialog_third_party, null);
        final TextView tv1 = (TextView) dview.findViewById(  R.id.dialog_third_party_name );
        final TextView tv2 = (TextView) dview.findViewById(  R.id.dialog_third_party_adress );
        final TextView tv3 = (TextView) dview.findViewById(  R.id.dialog_third_party_phone);
        final TextView tv4 = (TextView) dview.findViewById(  R.id.dialog_third_party_licence );

        builder.setView(dview)
                // Add action buttons
                .setPositiveButton(getString(R.string.save), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        ThirdParty t = new ThirdParty(tv1.getText().toString(),tv2.getText().toString(),
                                tv3.getText().toString(),tv4.getText().toString());
                        Data.thirdParty = t;
                        showSnackBar();
                    }
                });
        builder.show();
    }

    private void showVehicule() {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dview = inflater.inflate(R.layout.dialog_vehicle, null);
        final TextView tv1 = (TextView) dview.findViewById(  R.id.dialog_vehicle_type );
        final TextView tv2 = (TextView) dview.findViewById(  R.id.dialog_vehicle_manufacturer );
        final TextView tv3 = (TextView) dview.findViewById(  R.id.dialog_vehicle_model );
        final TextView tv4 = (TextView) dview.findViewById(  R.id.dialog_vehicle_color );
        final TextView tv5 = (TextView) dview.findViewById(  R.id.dialog_vehicle_year );
        final TextView tv6 = (TextView) dview.findViewById(  R.id.dialog_vehicle_licence_plate );


        builder.setView(dview)
                // Add action buttons
                .setPositiveButton(getString(R.string.save), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        Vehicule v = new Vehicule(tv1.getText().toString(),tv2.getText().toString(),
                                tv3.getText().toString(),tv4.getText().toString(),
                                tv5.getText().toString(),tv6.getText().toString());
                        Data.listVehicule.add(v);
                        showSnackBar();
                    }
                });
        builder.show();
    }

    private void showInsurance() {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dview = inflater.inflate(R.layout.dialog_insurance, null);
        final TextView tv1 = (TextView) dview.findViewById(  R.id.dialog_insurance_agency_name );
        final TextView tv2 = (TextView) dview.findViewById(  R.id.dialog_insurance_policy_number );
        final TextView tv3 = (TextView) dview.findViewById(  R.id.dialog_insurance_agent_name );
        final TextView tv4 = (TextView) dview.findViewById(  R.id.dialog_insurance_agent_number );

        builder.setView(dview)
                // Add action buttons
                .setPositiveButton(getString(R.string.save), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        Insurance i = new Insurance(tv1.getText().toString(),tv2.getText().toString(),
                                tv3.getText().toString(),tv4.getText().toString());
                        Data.insurance = i;
                        showSnackBar();
                    }
                });
        builder.show();
    }

    private void showPolice() {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dview = inflater.inflate(R.layout.dialog_police, null);
        final TextView tv1 = (TextView) dview.findViewById(  R.id.dialog_police_event_number );
        final TextView tv2 = (TextView) dview.findViewById(  R.id.dialog_police_case_number );
        final TextView tv3 = (TextView) dview.findViewById(  R.id.dialog_police_unit_number );
        final TextView tv4 = (TextView) dview.findViewById(  R.id.dialog_police_station_number );

        builder.setView(dview)
                // Add action buttons
                .setPositiveButton(getString(R.string.save), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        Police p = new Police(tv1.getText().toString(),tv2.getText().toString(),
                                tv3.getText().toString(),tv4.getText().toString());
                        Data.police = p;
                        showSnackBar();
                    }
                });
        builder.show();
    }

    private void showDescription() {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dview = inflater.inflate(R.layout.dialog_description, null);
        final TextView tv = (TextView) dview.findViewById(  R.id.dialog_description_text );

        builder.setView(dview)
                // Add action buttons
                .setPositiveButton(getString(R.string.save), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        Data.desciption = tv.getText().toString();
                        showSnackBar();
                    }
                });
        builder.show();
    }

    private void showCasuality() {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dview = inflater.inflate(R.layout.dialog_casualty, null);
        final TextView tv1 = (TextView) dview.findViewById(  R.id.dialog_casualty_name );
        final TextView tv2 = (TextView) dview.findViewById(  R.id.dialog_casualty_address );
        final TextView tv3 = (TextView) dview.findViewById(  R.id.dialog_casualty_phone );
        final TextView tv4 = (TextView) dview.findViewById(  R.id.dialog_casualty_age );

        builder.setView(dview)
                // Add action buttons
                .setPositiveButton(getString(R.string.save), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        Casualty c = new Casualty(tv1.getText().toString(),tv2.getText().toString(),
                                tv3.getText().toString(),tv4.getText().toString());
                        Data.listCasualties.add(c);
                        showSnackBar();
                    }
                });
        builder.show();
    }

    private void showWitness() {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dview = inflater.inflate(R.layout.dialog_witness, null);
        final TextView tv1 = (TextView) dview.findViewById(  R.id.dialog_casualty_name );
        final TextView tv2 = (TextView) dview.findViewById(  R.id.dialog_casualty_address );
        final TextView tv3 = (TextView) dview.findViewById(  R.id.dialog_casualty_phone );
        final TextView tv4 = (TextView) dview.findViewById(  R.id.dialog_casualty_age );

        builder.setView(dview)
                // Add action buttons
                .setPositiveButton(getString(R.string.save), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        Witness c = new Witness(tv1.getText().toString(),tv2.getText().toString(),
                                tv3.getText().toString(),tv4.getText().toString());
                        Data.listWitnesses.add(c);
                        showSnackBar();
                    }
                });
        builder.show();
    }


    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        TextView tv;

        public TextView getTv() { return tv; }

        public void setTv(TextView tv) { this.tv = tv; }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
            Calendar calendar = Calendar.getInstance();
            calendar.set(year, month, day);
            tv.setText(calendar.getTime().toString());
        }
    }

    public void showDatePickerDialog(TextView tv) {
        DatePickerFragment newFragment = new DatePickerFragment();
        newFragment.setTv(tv);
        newFragment.show(getFragmentManager(), "datePicker");
    }

/*
    public static class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {

        TextView tv;

        public TextView getTv() { return tv; }

        public void setTv(TextView tv) { this.tv = tv; }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            // Do something with the time chosen by the user
            tv.setText(view.toString());
        }
    }

    public void showTimePickerDialog(TextView tv) {
        TimePickerFragment newFragment = new TimePickerFragment();
        newFragment.setTv(tv);
        newFragment.show(getFragmentManager(), "timePicker");
    }
   */

    public void showSnackBar(){
        Snackbar snackbar = Snackbar
                .make(layout, getString(R.string.saved), Snackbar.LENGTH_SHORT);
// Changing action  text color
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.YELLOW);
        snackbar.show();
    }
}
