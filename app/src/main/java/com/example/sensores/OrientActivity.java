package com.example.sensores;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class OrientActivity extends AppCompatActivity {
    SensorManager mySensorManager;
    LinearLayout mike;
    TextView allsensor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orient);
        mySensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        allsensor = (TextView) findViewById(R.id.allsensor);
        mike = (LinearLayout) findViewById(R.id.mike);

        Sensor proxySensor = mySensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
        if(proxySensor != null){
            Toast.makeText(OrientActivity.this, "Sensor de Orientacion Disponible",Toast.LENGTH_LONG).show();
            mySensorManager.registerListener(
                    proxySensorListener,
                    proxySensor,
                    SensorManager.SENSOR_DELAY_NORMAL);

        } else {
            Toast.makeText(OrientActivity.this, "Sensor de Orientacion NO Disponible",Toast.LENGTH_LONG).show();
        }


    }
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
                // X
                if(ax > -45 && ax < 0)
                    mike.setBackgroundResource(R.drawable.mikeca);
                if(ax < -45 && ax > -95)
                    mike.setBackgroundResource(R.drawable.mikecc);
                if(ax < -95 && ax> -170)
                    mike.setBackgroundResource(R.drawable.mikecb);
                // Y
                if(ay < -40 && ay > -70)
                    mike.setBackgroundResource(R.drawable.mikeiz);
                if(ay > 40 && ay < 70)
                    mike.setBackgroundResource(R.drawable.mikede);
                //allsensor.setText("X: "+ax+" \n\n "+"Y:"+ay);
            }

        }

    };
}
