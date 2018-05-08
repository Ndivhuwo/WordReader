package com.smartalgorithms.tousidestest.Helpers;

import android.util.Log;

import com.smartalgorithms.tousidestest.Application;

/**
 * Created by Ndivhuwo Nthambeleni on 2018/05/07.
 * Updated by Ndivhuwo Nthambeleni on 2018/05/07.
 */

public class LoggingHelper {

    public static void d(String tag, String message){
        if(Application.LOGS_ENABLED) Log.d(tag, message);
    }
    public static void i(String tag, String message){
        if(Application.LOGS_ENABLED) Log.i(tag, message);
    }
    public static void e(String tag, String message){
        if(Application.LOGS_ENABLED) Log.e(tag, message);
    }
    public static void v(String tag, String message){
        if(Application.LOGS_ENABLED) Log.v(tag, message);
    }

}
