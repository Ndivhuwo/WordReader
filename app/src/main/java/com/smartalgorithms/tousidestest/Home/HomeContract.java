package com.smartalgorithms.tousidestest.Home;

import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ndivhuwo Nthambeleni on 2018/05/04.
 * Updated by Ndivhuwo Nthambeleni on 2018/05/04.
 */

public class HomeContract {
    public interface View {
        void displayMessage(String title, String message);
        void transitionTo(@Nullable Class<?> toClass, @Nullable Integer requestCode);
        void toggleOutput(boolean show);
        void displayFAnimation(boolean show);
        void display7CFAnimation(boolean show);
        void displayHSAnimation(boolean show);

        void displayHSW(ArrayList<String> highestScoringWords);
        void display7CFW(String result);
        void displayMFW(String result);

        void togglePermissions(boolean permissionsSet);
    }

    public interface Presenter {
        void onGetHighestScoringWord(HashMap<String,Integer> highestScoringWord);
        void onGetMostFrequent7CharWord(Map.Entry<String, Integer> mostFrequent7CharWord);
        void onGetMostFrequentWord(Map.Entry<String, Integer> mostFrequentWord);
        void onError(String message);
    }
}
