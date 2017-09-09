package nvjumpstarter.kizi;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import static android.content.Context.SENSOR_SERVICE;
import static java.lang.Math.abs;

/**
 * Created by johnk on 8/28/2017.
 */

public class SensorLevel implements SensorEventListener {
        private SensorManager mSensorManager;
        private final Context mContext;
        private Sensor mAccelerometer;
        private Sensor mMagnetometer;

        private float[] mLastAccelerometer = new float[3];
        private float[] mLastMagnetometer = new float[3];
        private boolean mLastAccelerometerSet = false;
        private boolean mLastMagnetometerSet = false;

        private float[] mR = new float[9];
        private float[] mOrientation = new float[3];

        public Button button;

    SensorLevel(Context context, View view) {
        this.mContext = context;
        Log.i("Orientation", "Test top of Create");

        mSensorManager = (SensorManager) mContext.getSystemService(SENSOR_SERVICE);

        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mMagnetometer = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        Log.i("Orientation", "Test bottom of Create");
        button = (Button) view.findViewById(R.id.picture);

    }


//    public static SensorLevel newInstance() {
//        return new SensorLevel();
//    }


        public void onResume() {
            //super.onResume();
            mLastAccelerometerSet = false;
            mLastMagnetometerSet = false;
            mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
            mSensorManager.registerListener(this, mMagnetometer, SensorManager.SENSOR_DELAY_NORMAL);
        }

        public void onPause() {
//            super.onPause();
            mSensorManager.unregisterListener(this);
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            Log.i("Orientation", "Test in accuracey change");
            return;
        }

        @Override
        public void onSensorChanged(SensorEvent event) {
            Log.i("Orientation", "Test at top of onSensorChanged");
            if (event.sensor == mAccelerometer) {
                System.arraycopy(event.values, 0, mLastAccelerometer, 0, event.values.length);
                mLastAccelerometerSet = true;
            } else if (event.sensor == mMagnetometer) {
                System.arraycopy(event.values, 0, mLastMagnetometer, 0, event.values.length);
                mLastMagnetometerSet = true;
            }
            if (mLastAccelerometerSet && mLastMagnetometerSet) {
                SensorManager.getRotationMatrix(mR, null, mLastAccelerometer, mLastMagnetometer);
                SensorManager.getOrientation(mR, mOrientation);


                button.setBackgroundColor(setColor());
//
                Log.i("OrientationTestActivity", String.format("Orientation: %f, %f, %f",
                        mOrientation[0], mOrientation[1], mOrientation[2]));
            }




        }
        //Returns color such that when device is tilted out of range the color is red and shifts to
        //green as device gets closer to level
        public int setColor(){
            double tolerance = 0.0872566; //5 degrees
            double midTolerance = 0.08; //bordeline leveled

            if( mOrientation[1] > tolerance || mOrientation[1] < -tolerance ||
                    mOrientation[2] > tolerance || mOrientation[2] < -tolerance )
            {
                return Color.rgb(255, 0 , 0);
            }
//            else if ( mOrientation[1] > midTolerance || mOrientation[1] < -midTolerance ||
//                    mOrientation[2] > midTolerance || mOrientation[2] < -midTolerance )
//                return Color.rgb(255,255,0);
            else
                return Color.rgb(0, 255, 0);

        }

        public void getOrientation(float myOrientation[])
        {
            Log.i("Orientation", "Test in getOrientatino");
            myOrientation[0] = mOrientation[0];
            myOrientation[1] = mOrientation[1];
            myOrientation[2] = mOrientation[2];


        }
    }