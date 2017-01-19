package bng.dic.esp.sn.bngboussole;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import java.util.List;

import bng.dic.esp.sn.bngboussole.view.BoussoleView;

/**
 * Created by sonyvaio on 18/01/2017.
 */

public class Main extends Activity implements SensorEventListener{
    //private final SensorEventListener sensorListener = new SensorEventListener();
    private BoussoleView boussole;
    private SensorManager sensorManager;
    private Sensor sensor;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        boussole = (BoussoleView)this.findViewById(R.id.boussole);
        sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        //Demander au gestionnaire de capteur de nous retourner les capteurs de type boussole
        List<Sensor> sensors =sensorManager.getSensorList(Sensor.TYPE_ORIENTATION);
        if (sensors.size() > 0) {
            sensor = sensors.get(0);
        }
        //boussole.setNorthOrientation(45);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        boussole.setNorthOrientation(event.values[SensorManager.DATA_X]);

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onResume(){
        super.onResume();
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }
    @Override
    protected void onStop(){
        super.onStop();
        sensorManager.unregisterListener(this);
    }
}
