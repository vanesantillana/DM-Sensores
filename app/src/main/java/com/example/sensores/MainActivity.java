package com.example.sensores;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private SensorManager sensorManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ListView listaSensores = (ListView) findViewById(R.id.lista);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        List<Sensor> deviceSensors = sensorManager.getSensorList(Sensor.TYPE_ALL);
        final ArrayList<String> list = new ArrayList<String>();

        for (Sensor s : deviceSensors) {
            list.add(s.getName());
            //pw.write(s.toString() + "\n");
        }
        Log.e("si","lei sensores");
        /*

        if (sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD) != null){
            list.add("MAGNETIC_FIELD");
        }
*/
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
        listaSensores.setAdapter(arrayAdapter);

        listaSensores.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View v, int position, long id) {
                String name = arg0.getItemAtPosition(position).toString();
                Log.d("Elemento",  name);
                Intent myintent;
                if(name.toLowerCase().contains("orientation"))
                    myintent = new Intent(getApplicationContext(), OrientActivity.class);
                else
                    myintent = new Intent(getApplicationContext(), SensorActivity.class);
                myintent.putExtra("id",name);
                startActivity(myintent);
            }

        });


    }
}
