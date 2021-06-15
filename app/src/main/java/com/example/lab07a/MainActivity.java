package com.example.lab07a;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {


    TextView texto;
    private SensorManager sensorManager;
    private final float[] accelerometerReading = new float[3];
    private final float[] magnetometerReading = new float[3];

    private final float[] rotationMatrix = new float[9];
    private final float[] orientationAngles = new float[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        texto = (TextView)findViewById(R.id.texto);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    protected void onResume() {
        super.onResume();


        Sensor accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (accelerometer != null) {
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL, SensorManager.SENSOR_DELAY_UI);
        }
        Sensor magneticField = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        if (magneticField != null) {
            sensorManager.registerListener(this, magneticField, SensorManager.SENSOR_DELAY_NORMAL, SensorManager.SENSOR_DELAY_UI);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            System.arraycopy(event.values, 0, accelerometerReading,
                    0, accelerometerReading.length);
        } else if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            System.arraycopy(event.values, 0, magnetometerReading,
                    0, magnetometerReading.length);
        }
        updateOrientationAngles();
        texto.setText("");
        texto.append("\n" + "El valor de x: " + orientationAngles[0] + "\n"+ "El valor de y: " + orientationAngles[1] + "\n" +"El valor de z: " + orientationAngles[2]);
    }


    public void updateOrientationAngles() {

        SensorManager.getRotationMatrix(rotationMatrix, null,
                accelerometerReading, magnetometerReading);



        SensorManager.getOrientation(rotationMatrix, orientationAngles);


    }
}