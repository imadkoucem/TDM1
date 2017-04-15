package com.mobile.project;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import model.Data;


public class DamageActivity extends AppCompatActivity implements View.OnTouchListener {

    ImageView imageView,imageView2 ;

    Switch switch1, switch2;

    Spinner spinner,spinner2;

    private List<Point> listPoints = new ArrayList<>();
    private List<Point> listPoints2 = new ArrayList<>();

    Bitmap mutableBitmap1,mutableBitmap2;

    private int[] images = {R.drawable.car_icon,R.drawable.motorcycle_200};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_damage);

        switch1 = (Switch)  findViewById(R.id.switch1);
        switch2 = (Switch)  findViewById(R.id.switch2);

        imageView = (ImageView)findViewById(R.id.imageView1);
        imageView.setOnTouchListener(this);

        imageView2 = (ImageView)findViewById(R.id.imageView2);
        imageView2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if (!switch2.isChecked()) return true;
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN){

                    listPoints2.add(new Point( motionEvent.getX()-view.getLeft(),
                            motionEvent.getY()-view.getTop()) );
                    DrawCross(view.getId(),images[spinner2.getSelectedItemPosition()]);
                }

                return true;
            }
        });


        spinner = (Spinner)findViewById(R.id.spinner);
        final String[] choices = getResources().getStringArray(R.array.spinner);
        ArrayAdapter<String> adapter =new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, choices);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                imageView.setImageResource(images[i]);
                listPoints.clear();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        spinner2 = (Spinner)findViewById(R.id.spinner2);
        spinner2.setAdapter(adapter);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                imageView2.setImageResource(images[i]);
                listPoints2.clear();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    public void clickSave1(View v){
        Data.image1 = mutableBitmap1;
        new SweetAlertDialog(this,SweetAlertDialog.SUCCESS_TYPE)
        .setTitleText(getString(R.string.saved))
                .show();
    }
    public void clickSave2(View v){
        Data.image2 = mutableBitmap2;
        new SweetAlertDialog(this,SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText(getString(R.string.saved))
                .show();
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (!switch1.isChecked()) return true;
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN){

            listPoints.add(new Point( motionEvent.getX()-view.getLeft(),
                    motionEvent.getY()-view.getTop()) );
            DrawCross(view.getId(), images[spinner.getSelectedItemPosition()]);
        }
        return true;
    }

    public class Point {
        float x;
        float y;
        public Point(float x, float y) { this.x = x; this.y = y; }
    }


        public void DrawCross(int id, int imageSrcId) {

            BitmapFactory.Options myOptions = new BitmapFactory.Options();
            myOptions.inDither = true;
            myOptions.inScaled = false;
            myOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;// important
            myOptions.inPurgeable = true;

            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), imageSrcId, myOptions);
            Paint paint = new Paint();
            paint.setAntiAlias(true);
            paint.setColor(Color.BLUE);
            paint.setStyle(Paint.Style.STROKE);


            Bitmap workingBitmap = Bitmap.createBitmap(bitmap);



            if(id == imageView.getId()){
                mutableBitmap1 = workingBitmap.copy(Bitmap.Config.ARGB_8888, true);
                Canvas canvas = new Canvas(mutableBitmap1);
                Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.cross);
                for(Point p:listPoints) canvas.drawBitmap(b,p.x,p.y,paint);
                imageView.setAdjustViewBounds(true);
                imageView.setImageBitmap(mutableBitmap1);
            }
            else {
                mutableBitmap2 = workingBitmap.copy(Bitmap.Config.ARGB_8888, true);
                Canvas canvas = new Canvas(mutableBitmap2);
                Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.cross);
                for(Point p:listPoints2) canvas.drawBitmap(b,p.x,p.y,paint);
                imageView2.setAdjustViewBounds(true);
                imageView2.setImageBitmap(mutableBitmap2);
            }
        }
}
