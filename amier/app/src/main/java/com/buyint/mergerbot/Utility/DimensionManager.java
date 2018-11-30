package com.buyint.mergerbot.Utility;

import android.app.Activity;
import android.graphics.Point;
import android.view.Display;

/**
 * Created by franciscopera on 4/30/17.
 *  Dimension Manager
 */

public class DimensionManager {


    /**
     * @param activity -- needed for getting the Display object
     * @return -- screen width
     */
    // This method returns the screen width
    public static  int getScreenWidth(Activity activity){
        Display display = activity.getWindowManager().getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        return point.x;
    }

    /**
     * @param activity -- needed for getting the Display object
     * @return ints -- screen height
     */
    // This method returns the screen height
    public static  int getScreenHeight(Activity activity){
        Display display = activity.getWindowManager().getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        return point.y;
    }

}
