package com.smartalgorithms.tousidestest.Helpers;

import android.content.res.Resources;

import com.smartalgorithms.tousidestest.Application;

/**
 * Created by Ndivhuwo Nthambeleni on 2018/05/04.
 * Updated by Ndivhuwo Nthambeleni on 2018/05/04.
 */

public class ResourcesHelper {

    public static String getString(int resourceId){
        try {
            return Application.getAppContext().getResources().getString(resourceId);
        } catch (Resources.NotFoundException e) {
            return "";
        }
    }
}
