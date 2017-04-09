package com.mobile.project;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class DamageActivity extends AppCompatActivity implements View.OnTouchListener {

    ImageView imageView ;
    ImageView imageView2 ;

    private Point point ;

    private DrawingCrl imgCircle = new DrawingCrl();
    private Bitmap mutableBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_damage);

        Spinner spinner = (Spinner)findViewById(R.id.spinner);
        final String[] choices = getResources().getStringArray(R.array.spinner);
        ArrayAdapter<String> adapter =new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, choices);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        Spinner spinner2 = (Spinner)findViewById(R.id.spinner2);
        spinner2.setAdapter(adapter);

        imageView = (ImageView)findViewById(R.id.imageView1);
        imageView.setOnTouchListener(this);
        imageView2 = (ImageView)findViewById(R.id.imageView2);


    }

    public void clickSave1(View v){
        /*Document doc = new Document();
        String outPath = MainActivity.dirFile + MainActivity.fileName + ".pdf";
        try {
            PdfWriter.getInstance(doc,new FileOutputStream(outPath));
            doc.open();
            doc.addTitle("Image: ");
            doc.add(imageView)
            String path = “res/drawable/myImage.png”
            Image image = Image.getInstance(path);
            document.add(image);
            doc.close();
        } catch (DocumentException e) {
            e.printStackTrace();
            Log.i("MainActivity",e.toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Log.i("MainActivity",e.toString());
        }*/
    }

    public void clickSave2(View v){

    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        point = new Point();
        point.x = Float.valueOf(motionEvent.getX());
        point.y = Float.valueOf(motionEvent.getY());
        imgCircle.DrawCircle(point);
        return true;
    }

    public class Point {
        float x;
        float y;
    }

    private class DrawingCrl {
        private Paint paint;
        private Canvas canvas ;

        public void DrawCircle(Point point) {
            //mBitmap = Bitmap.createBitmap(400, 800, Bitmap.Config.ARGB_8888);
            /*canvas = new Canvas();
            paint = new Paint();
            paint.setColor(Color.BLUE);
            paint.setStyle(Paint.Style.FILL);
            canvas.drawCircle(point.x, point.y, 25, paint);*/
            BitmapFactory.Options myOptions = new BitmapFactory.Options();
            myOptions.inDither = true;
            myOptions.inScaled = false;
            myOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;// important
            myOptions.inPurgeable = true;

            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.car_icon,myOptions);
            Paint paint = new Paint();
            paint.setAntiAlias(true);
            paint.setColor(Color.BLUE);
            paint.setStyle(Paint.Style.STROKE);


            Bitmap workingBitmap = Bitmap.createBitmap(bitmap);
            mutableBitmap = workingBitmap.copy(Bitmap.Config.ARGB_8888, true);


            Canvas canvas = new Canvas(mutableBitmap);
            canvas.drawCircle(point.x-70, point.y, 50, paint);

            ImageView imageView = (ImageView)findViewById(R.id.imageView1);
            imageView.setAdjustViewBounds(true);
            imageView.setImageBitmap(mutableBitmap);
        }
    }
}
