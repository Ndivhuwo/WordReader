package com.smartalgorithms.tousidestest.Home;

import com.smartalgorithms.tousidestest.Helpers.LoggingHelper;
import com.smartalgorithms.tousidestest.Helpers.TextHelper;

import javax.inject.Provider;

import io.reactivex.Scheduler;

/**
 * Created by Ndivhuwo Nthambeleni on 2018/05/04.
 * Updated by Ndivhuwo Nthambeleni on 2018/05/04.
 */

public class HomeInteractor {
    private static final String TAG = HomeInteractor.class.getSimpleName();

    private HomeContract.Presenter presenterContract;
    private Scheduler mainThreadScheduler;
    private Provider<Scheduler> newThreadScheduler;
    private TextHelper textHelper;

    HomeInteractor(HomeContract.Presenter presenterContract, Scheduler mainThreadScheduler, Provider<Scheduler> newThreadScheduler, TextHelper textHelper) {
        this.presenterContract = presenterContract;
        this.mainThreadScheduler = mainThreadScheduler;
        this.newThreadScheduler = newThreadScheduler;
        this.textHelper = textHelper;
    }

    void setTextFile(String filePath) {
        textHelper.initializeWordMapObservable()
                .flatMapCompletable(success -> textHelper.setFileObservable(filePath))
                .subscribeOn(newThreadScheduler.get())
                .observeOn(mainThreadScheduler)
                .subscribe(() ->{
                    LoggingHelper.d(TAG, "setFileObservable Success");
                    getMostFrequentWord();
                    getMostFrequent7CharWord();
                    getHighestScoringWord();
                }, error -> {
            error.printStackTrace();
            LoggingHelper.e(TAG, "setFileObservable Error: " + error.getMessage());
                    presenterContract.onError("setFileObservable Error: " + error.getMessage());
                });
    }

    private void getHighestScoringWord() {
        textHelper.getScrabbleHighestScoringWordSingle()
                .subscribeOn(newThreadScheduler.get())
                .observeOn(mainThreadScheduler)
                .subscribe(map ->{
                    LoggingHelper.d(TAG, "getScrabbleHighestScoringWordSingle Success");
                    presenterContract.onGetHighestScoringWord(map);
                }, error -> {
                    error.printStackTrace();
                    LoggingHelper.e(TAG, "getScrabbleHighestScoringWordSingle Error: " + error.getMessage());
                    presenterContract.onError("getScrabbleHighestScoringWordSingle Error: " + error.getMessage());
                });
    }

    private void getMostFrequent7CharWord() {
        textHelper.getMostFrequent7CharWordSingle()
                .subscribeOn(newThreadScheduler.get())
                .observeOn(mainThreadScheduler)
                .subscribe(map ->{
                    LoggingHelper.d(TAG, "getMostFrequent7CharWordSingle Success");
                    presenterContract.onGetMostFrequent7CharWord(map);
                }, error -> {
                    error.printStackTrace();
                    LoggingHelper.e(TAG, "getMostFrequent7CharWordSingle Error: " + error.getMessage());
                    presenterContract.onError("getMostFrequent7CharWordSingle Error: " + error.getMessage());
                });
    }

    private void getMostFrequentWord() {
        textHelper.getMostFrequentWordSingle()
                .subscribeOn(newThreadScheduler.get())
                .observeOn(mainThreadScheduler)
                .subscribe(map ->{
                    LoggingHelper.d(TAG, "getMostFrequentWordSingle Success");
                    presenterContract.onGetMostFrequentWord(map);
                }, error -> {
                    error.printStackTrace();
                    LoggingHelper.e(TAG, "getMostFrequentWordSingle Error: " + error.getMessage());
                    presenterContract.onError("getMostFrequentWordSingle Error: " + error.getMessage());
                });
    }
}
