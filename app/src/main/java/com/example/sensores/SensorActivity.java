package com.example.sensores;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.widget.Toast;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class SensorActivity extends AppCompatActivity {

    SensorManager mySensorManager;
    SensorEventListener sensorEventListener;
    TextView datasensor, allsensor;
    LinearLayout mifondo, imagen;
    TextView libro;
    private AnimatedView mAnimatedView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor2);

        allsensor = (TextView) findViewById(R.id.allsensor);

        /* Variables de sensor LUZ*/
        datasensor = (TextView) findViewById(R.id.num);
        mifondo = (LinearLayout) findViewById(R.id.sensorluz);
        imagen = (LinearLayout) findViewById(R.id.imagen);
        libro = (TextView) findViewById(R.id.libro);

        mAnimatedView = new AnimatedView(this);
        /*OBTENGO DEL MENU*/
        Bundle b = getIntent().getExtras();
        String id = b.getString("id");

        mySensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);

        if (id.toLowerCase().contains("light")){
            allsensor.setVisibility(View.INVISIBLE);
            Sensor lightSensor = mySensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
            if(lightSensor != null){
                Toast.makeText(SensorActivity.this, "Sensor de Luz Disponible",Toast.LENGTH_LONG).show();
                mySensorManager.registerListener(
                        lightSensorListener,
                        lightSensor,
                        SensorManager.SENSOR_DELAY_NORMAL);

            } else {
                Toast.makeText(SensorActivity.this, "Sensor de Luz NO Disponible",Toast.LENGTH_LONG).show();
            }
        }
        else if(id.toLowerCase().contains("accelerometer")){
            Sensor accelSensor = mySensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            setContentView(mAnimatedView);
            if(accelSensor != null){
                Toast.makeText(SensorActivity.this, "Sensor de Acelerometro Disponible",Toast.LENGTH_LONG).show();
                mySensorManager.registerListener(
                        accelerometerSensorListener,
                        accelSensor,
                        SensorManager.SENSOR_DELAY_NORMAL);

            } else {
                Toast.makeText(SensorActivity.this, "Sensor de Acelerometro NO Disponible",Toast.LENGTH_LONG).show();
            }

        }
        else if(id.toLowerCase().contains("orientation")){
            imagen.setVisibility(View.INVISIBLE);
            libro.setVisibility(View.INVISIBLE);
            Sensor proxySensor = mySensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
            if(proxySensor != null){
                Toast.makeText(SensorActivity.this, "Sensor de Proximidad Disponible",Toast.LENGTH_LONG).show();
                mySensorManager.registerListener(
                        proxySensorListener,
                        proxySensor,
                        SensorManager.SENSOR_DELAY_NORMAL);

            } else {
                Toast.makeText(SensorActivity.this, "Sensor de Proximidad NO Disponible",Toast.LENGTH_LONG).show();
            }

        }
        else{
            imagen.setVisibility(View.INVISIBLE);
            libro.setVisibility(View.INVISIBLE);
            Toast.makeText(SensorActivity.this, "Prueba otro Sensor",Toast.LENGTH_LONG).show();

        }
    }

    private final SensorEventListener lightSensorListener = new SensorEventListener(){
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            // TODO Auto-generated method stub
        }

        @Override
        public void onSensorChanged(SensorEvent event) {
            if(event.sensor.getType() == Sensor.TYPE_LIGHT){
                if(event.values[0] <30){
                    libro.setTextColor(Color.WHITE);
                    mifondo.setBackgroundResource(R.color.noche);
                }
                else if(event.values[0] >30){
                    libro.setTextColor(Color.BLACK);
                    mifondo.setBackgroundResource(R.color.dia);
                }

                datasensor.setText("Luz: " + event.values[0]);
            }

        }

    };

    private final SensorEventListener proxySensorListener = new SensorEventListener(){
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            // TODO Auto-generated method stub
        }

        @Override
        public void onSensorChanged(SensorEvent event) {
            if(event.sensor.getType() == Sensor.TYPE_ORIENTATION){
                float az = event.values[0];
                float ax = event.values[1];
                float ay = event.values[2];
                allsensor.setText("X: "+ax+" \n\n "+"Y:"+ay);
            }

        }

    };

    private final SensorEventListener accelerometerSensorListener = new SensorEventListener(){
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            // TODO Auto-generated method stub
        }

        @Override
        public void onSensorChanged(SensorEvent event) {
            if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
                float x = event.values[0];
                float y = event.values[1];
                mAnimatedView.onSensorEvent(event);

            }

        }

    };

    public class AnimatedView extends View {

        private static final int r = 50; //pixels

        private Paint mPaint;
        private int x;
        private int y;
        private int viewWidth;
        private int viewHeight;

        public AnimatedView(Context context) {
            super(context);
            mPaint = new Paint();
            mPaint.setColor(Color.MAGENTA);
        }

        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            super.onSizeChanged(w, h, oldw, oldh);
            viewWidth = w;
            viewHeight = h;
        }

        public void onSensorEvent (SensorEvent event) {
            x = x - (int) event.values[0];
            y = y + (int) event.values[1];

            if (x <= r) {
                x = r;
            }
            if (x >= viewWidth - r) {
                x = viewWidth - r;
            }
            if (y <= r) {
                y = r;
            }
            if (y >= viewHeight - r) {
                y = viewHeight - r;
            }
        }

        @Override
        protected void onDraw(Canvas canvas) {
            canvas.drawCircle(x, y, r, mPaint);
            invalidate();
        }
    }
}


