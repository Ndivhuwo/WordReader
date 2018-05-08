package com.smartalgorithms.tousidestest.Home;

import android.Manifest;

import com.smartalgorithms.tousidestest.Constants;
import com.smartalgorithms.tousidestest.Helpers.LoggingHelper;
import com.smartalgorithms.tousidestest.Helpers.ResourcesHelper;
import com.smartalgorithms.tousidestest.R;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Provider;

/**
 * Created by Ndivhuwo Nthambeleni on 2018/05/04.
 * Updated by Ndivhuwo Nthambeleni on 2018/05/04.
 */

public class HomePresenter implements HomeContract.Presenter {
    private static final String TAG = HomePresenter.class.getSimpleName();

    private HomeContract.View viewContract;
    private Provider<HomeInteractor> homeInteractorProvider;
    private RxPermissions rxPermissions;

    HomePresenter(HomeContract.View viewContract, Provider<HomeInteractor> homeInteractorProvider, RxPermissions rxPermissions) {
        this.viewContract = viewContract;
        this.homeInteractorProvider = homeInteractorProvider;
        this.rxPermissions = rxPermissions;
    }

    void requestPhonePermissions() {
        if(!rxPermissions.isGranted(Manifest.permission.READ_EXTERNAL_STORAGE)) {
            rxPermissions.request(Manifest.permission.READ_EXTERNAL_STORAGE)
                    .subscribe(granted -> {
                        if(granted) {
                            LoggingHelper.d(TAG, "Permissions set");
                            viewContract.togglePermissions(true);
                        }
                        else {
                            LoggingHelper.d(TAG, "Permissions not set");
                            viewContract.displayMessage(ResourcesHelper.getString(R.string.text_error), ResourcesHelper.getString(R.string.text_permissions_not_set));
                            viewContract.togglePermissions(false);
                        }
                    });
        }else {
            LoggingHelper.d(TAG, "Permissions Already set");
        }
    }

    void onBtnClickSelectInput() {
        viewContract.transitionTo(null, Constants.INTENT_REQUEST_CODE_GET_TEXT_FILE);
    }

    @Override
    public void onGetHighestScoringWord(HashMap<String,Integer> highestScoringWords) {
        ArrayList<String> highestScoringWordsList = new ArrayList<>();
        for(Map.Entry<String, Integer> entry : highestScoringWords.entrySet()){
            highestScoringWordsList.add("\"" +entry.getKey() + "\" " + ResourcesHelper.getString(R.string.text_with_score) + " " + entry.getValue());
        }
        viewContract.displayHSAnimation(false);
        viewContract.displayHSW(highestScoringWordsList);
    }

    @Override
    public void onGetMostFrequent7CharWord(Map.Entry<String, Integer> mostFrequent7CharWord) {
        viewContract.display7CFAnimation(false);
        String result = "\"" +mostFrequent7CharWord.getKey() + "\" " + ResourcesHelper.getString(R.string.text_occurred) + " " + mostFrequent7CharWord.getValue() + " " + ResourcesHelper.getString(R.string.text_times);
        viewContract.display7CFW(result);
    }

    @Override
    public void onGetMostFrequentWord(Map.Entry<String, Integer> mostFrequentWord) {
        viewContract.displayFAnimation(false);
        String result = "\"" +mostFrequentWord.getKey() + "\" " + ResourcesHelper.getString(R.string.text_occurred) + " " + mostFrequentWord.getValue() + " " + ResourcesHelper.getString(R.string.text_times);
        viewContract.displayMFW(result);
    }

    @Override
    public void onError(String message) {
        viewContract.displayMessage(ResourcesHelper.getString(R.string.text_error), message);
    }

    void processText(String filePath) {
        viewContract.toggleOutput(true);
        viewContract.displayFAnimation(true);
        viewContract.display7CFAnimation(true);
        viewContract.displayHSAnimation(true);
        homeInteractorProvider.get().setTextFile(filePath);
    }
}
