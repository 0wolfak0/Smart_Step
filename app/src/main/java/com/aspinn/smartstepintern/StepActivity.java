package com.aspinn.smartstepintern;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class StepActivity extends AppCompatActivity implements SensorEventListener {

    private TextView counterTextView;

    private SensorManager sensorManager;

    private boolean isWalking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_step);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        counterTextView = findViewById(R.id.textView);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
    }
    @Override
    protected void onResume()
    {
        super.onResume();
        isWalking = true;
        Sensor countSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if(countSensor != null)
        {
            sensorManager.registerListener(this, countSensor, SensorManager.SENSOR_DELAY_UI);
        }
        else
        {
            counterTextView.setText("WARNING SENSOR NOT FOUND");
        }

    }

    @Override
    protected void onPause()
    {
        super.onPause();
        isWalking = false;
    }

    @Override
    public void onSensorChanged(SensorEvent event)
    {
        if(isWalking)
        {
            counterTextView.setText(String.valueOf(event.values[0]));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy)
    {

    }
}