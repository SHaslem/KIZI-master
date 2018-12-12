/* This code was developed for KIZI LLC by Nevada JumpStarter LLC
*
* This class implents a hardware listener on the devices accelerometer to determine how level the
* device is.
* It gives feedback to the user in the form of colloring the control panel red or green depending
* on if the device is level enough*/


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
import android.widget.ImageButton;

import static android.content.Context.SENSOR_SERVICE;
import static java.lang.Math.abs;



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

        public ImageButton button;
        public View controlView;

    SensorLevel(Context context, View view) {
        this.mContext = context;
        Log.i("Orientation", "Test top of Create");

        mSensorManager = (SensorManager) mContext.getSystemService(SENSOR_SERVICE);

        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mMagnetometer = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        Log.i("Orientation", "Test bottom of Create");
        button = view.findViewById(R.id.picture);
        controlView = view.findViewById(R.id.control);

    }


        public void onResume() {
            mLastAccelerometerSet = false;
            mLastMagnetometerSet = false;
            mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
            mSensorManager.registerListener(this, mMagnetometer, SensorManager.SENSOR_DELAY_NORMAL);
        }

        public void onPause() {
            mSensorManager.unregisterListener(this);
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            return;
        }

        /* listens for changes in orientation and changes color of control panel accordingly */
        @Override
        public void onSensorChanged(SensorEvent event) {
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


                controlView.setBackgroundColor(setColor());
//
                Log.i("OrientationTestActivity", String.format("Orientation: %f, %f, %f",
                        mOrientation[0], mOrientation[1], mOrientation[2]));
            }




        }
        //Returns color such that when device is tilted out of range the color is red and shifts to
        //green as device gets closer to level
        public int setColor(){
            double tolerance = 0.0523599; //3 degrees

            if( mOrientation[1] > tolerance || mOrientation[1] < -tolerance ||
                    mOrientation[2] > tolerance || mOrientation[2] < -tolerance )
            {
                return Color.rgb(188, 28 , 28);
            }
            else
                return Color.rgb(27, 94, 32);

        }

        //Returns orientation of device by reference 0 = x, 1 = y, 2 = z
        public void getOrientation(float myOrientation[])
        {
            Log.i("Orientation", "Test in getOrientatino");
            myOrientation[0] = mOrientation[0];
            myOrientation[1] = mOrientation[1];
            myOrientation[2] = mOrientation[2];


        }
    }