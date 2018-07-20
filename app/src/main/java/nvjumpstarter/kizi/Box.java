/* This code was developed for KIZI LLC by Nevada JumpStarter LLC */

package nvjumpstarter.kizi;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Surface;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

/**
 * Created by johnk on 9/5/2017.
 */

public class Box extends View {
    private Paint paint = new Paint();
    private Paint paintRed = new Paint();
    private Context mContext;
    Float Width;
    Float Height;
    Float Depth;
    String Name;
    String Notes;
    String LayoutName;

    Box(Context context, Float width, Float height, Float depth, String name, String notes, String layoutName) {
        super(context);
        mContext= context;
        Width = width;
        Height = height;
        Depth = depth;
        Name = name;
        Notes = notes;
        LayoutName = layoutName;
    }

    /* Function draws a rectangle and circle based on the size of the devices screen and the
        description of the drawer passed by the user
        THe largest size of the drawer is used to determine the size to draw the circle
        The circle is set to corespond to the size of a real world cd - 4.7 inch diameter
        Using that diameter the box is scaled such that if the user holds the phone at a height
        that matches the circle to the size of the cd the box will be the size of the users drawer
        in the real world
     */
    @Override
    protected void onDraw(Canvas canvas) { // Override the onDraw() Method
        int strokeWidth = 10;
        //used for setting height
        double cdSize = 4.724;//inches is the diameter of a cd

        int drawHeight;

        int drawWidth;


        int circleRad;
        float biggerValue;
        float smallerValue;

        super.onDraw(canvas);

        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.GREEN);
        paint.setStrokeWidth(strokeWidth);

        paintRed.setStyle(Paint.Style.STROKE);
        paintRed.setColor(Color.RED);
        paintRed.setStrokeWidth(strokeWidth);

        //center
        int x0 = canvas.getWidth()/2;

        //height of control panel determined in XML for capture activity
        int controlHeight = dpToPx(142);

        //Get information about size of screen
        int canvasHeight = canvas.getHeight();
        int cameraHeight = canvasHeight - controlHeight;

        int y0 = canvasHeight - (cameraHeight/2 + controlHeight);



        Display display = ((WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();


    //start by grabbing biggest value
        if(Height > Width)
        {
            biggerValue = Height;
            smallerValue = Width;
        }
        else
        {
            biggerValue = Width;
            smallerValue = Height;
        }
        Log.i("canvas biggerValue", Float.toString(biggerValue));
        Log.i("canvas smallerValue", Float.toString(smallerValue));



        //Use largest size to determine how big to draw the cd
        //take from distance 1 (close)
        if(isBetween(biggerValue, 0, 20))
        {
            if(smallerValue > 20)
                circleRad = 95;
            else
                circleRad = 165;
        }
        //distance 2 mid range
        else if(isBetween(biggerValue,21, 40))
        {
            if(smallerValue > 35)
                circleRad = 60;
            else
                circleRad = 95;
        }
            //take from distance 3 (far)
        else if(isBetween(biggerValue,41, 60) && smallerValue <= 53)
        {
            circleRad = 60;
        }
        //too big or too small
        else{
                circleRad = 0;
        }

        //using size of cd circle scale drawer to same dimensions
        double ppi = (circleRad * 2) / (cdSize);
        drawWidth = (int) Math.ceil((smallerValue * ppi))/2;
        drawHeight = (int) Math.ceil((biggerValue * ppi))/2;

        int edgeCheckY = y0 + drawHeight  + circleRad;
        int edgeCheckX = x0 - drawWidth;

        if (edgeCheckY > cameraHeight)
        {
            Log.i("Canvas:","Error: Size exceeds phone limits");

        }

        //Draw the box and circle
            canvas.drawRect(x0 - drawWidth, y0 - drawHeight  + circleRad, x0 + drawWidth, y0 + drawHeight  + circleRad, paint);
            canvas.drawCircle(x0 + circleRad + circleRad/2, y0 - drawHeight - strokeWidth * 2, circleRad, paintRed);


    }

    //converts px to dp
    public int pxToDp(int px) {
        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        int dp = Math.round(px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return dp;
    }

    //converts dp to px
    public int dpToPx(int dp) {
        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return px;
    }

    //checks to see if passed value is inbetween passed range (inclusive)
    public static boolean isBetween(float x, float lower, float upper) {
        return lower <= x && x <= upper;
    }
}