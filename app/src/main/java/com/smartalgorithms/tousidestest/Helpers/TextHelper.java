package com.smartalgorithms.tousidestest.Helpers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Single;

/**
 * Created by Ndivhuwo Nthambeleni on 2018/05/04.
 * Updated by Ndivhuwo Nthambeleni on 2018/05/04.
 */

public class TextHelper {
    private static final String TAG = TextHelper.class.getSimpleName();

    private String filePath  = null;
    private int[] letterScore = {1, 3, 3, 2, 1, 4, 2, 4, 1, 8, 5, 1, 3, 1, 1, 3, 10, 1, 1, 1, 1, 4, 4, 8, 4, 10};
    private Map<Character, Integer> lettersMap = new HashMap<>();
    private String capsLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private HashMap<String, Integer> wordCountHashMap = new HashMap<>();
    private HashMap<String, Integer> word7CharCountHashMap = new HashMap<>();
    private HashMap<String, Integer> scrabbleScoreCountHashMap = new HashMap<>();
    private boolean inMemory = false;
    @Inject public TextHelper() {
    }

    private Boolean initializeWordMap(){
        for (int i = 0; i < capsLetters.length(); i++) {
            lettersMap.put(capsLetters.charAt(i), letterScore[i]);
            lettersMap.put(capsLetters.toLowerCase().charAt(i), letterScore[i]);
        }
        return true;
    }

    private void setFile(String filePath) {
        if(this.filePath == null || !this.filePath.equalsIgnoreCase(filePath)) {
            inMemory = false;
            this.filePath = filePath;
            wordCountHashMap.clear();
            word7CharCountHashMap.clear();
            scrabbleScoreCountHashMap.clear();
            try {
                BufferedReader br = new BufferedReader(new FileReader(new File(filePath)));
                String line;
                while ((line = br.readLine()) != null) {
                    String[] words = line.split("-|:|\\s+");
                    for (String wordAnyCase : words) {
                        String word = wordAnyCase.toLowerCase();
                        if (wordCountHashMap.get(word) != null)
                            wordCountHashMap.put(word, wordCountHashMap.get(word) + 1);
                        else {
                            wordCountHashMap.put(word, 1);
                        }

                        if (word.length() == 7) {
                            if (word7CharCountHashMap.get(word) != null)
                                word7CharCountHashMap.put(word, word7CharCountHashMap.get(word) + 1);
                            else
                                word7CharCountHashMap.put(word, 1);
                        }
                    }
                }

            } catch (IOException ioe) {
                ioe.printStackTrace();
                LoggingHelper.e(TAG, "getWords Error: " + ioe.getMessage());
            }
        }else
            inMemory = true;
    }

    private Map.Entry<String,Integer> getMostFrequentWord(){
        Map.Entry<String,Integer> mostFrequent = null;
        for(Map.Entry<String, Integer> e: wordCountHashMap.entrySet())
        {
            if(mostFrequent == null || mostFrequent.getValue()<e.getValue())
                mostFrequent = e;
        }
        return mostFrequent;
    }

    private Map.Entry<String,Integer> getMostFrequent7CharWord(){
        Map.Entry<String,Integer> mostFrequent = null;
        for(Map.Entry<String, Integer> e: word7CharCountHashMap.entrySet())
        {
            if(mostFrequent == null || mostFrequent.getValue()<e.getValue())
                mostFrequent = e;
        }

        return mostFrequent;
    }

    private int calculateScrabbleScore(String word){
        int totalScore = 0;
        for (int i = 0; i < word.length(); i++) {
            if(capsLetters.contains(word.charAt(i)+"") || capsLetters.toLowerCase().contains(word.charAt(i)+""))
                totalScore += lettersMap.get(word.charAt(i));
        }
        return totalScore;
    }

    private HashMap<String,Integer> getScrabbleHighestScoringWord(){

        if(!inMemory) {
            scrabbleScoreCountHashMap.clear();
            for (Map.Entry<String, Integer> entry : wordCountHashMap.entrySet()) {
                String[] words = entry.getKey().split("\\p{Pd}");
                for (String word : words) {
                    scrabbleScoreCountHashMap.put(word, calculateScrabbleScore(word));
                }
            }
        }

        HashMap<String,Integer> mostFrequent = new HashMap<>();
        for(Map.Entry<String, Integer> e: scrabbleScoreCountHashMap.entrySet())
        {
            if(mostFrequent.size() == 0 || mostFrequent.entrySet().iterator().next().getValue() < e.getValue()) {
                mostFrequent.clear();
                mostFrequent.put(e.getKey(), e.getValue());
            }
            else if(mostFrequent.entrySet().iterator().next().getValue() == e.getValue())
                mostFrequent.put(e.getKey(), e.getValue());
        }

        return mostFrequent;
    }

    public Single<Boolean> initializeWordMapObservable(){
        return Single.defer(() -> Single.just(initializeWordMap()));
    }

    public Completable setFileObservable(String path){
        return Completable.defer(() -> Completable.fromAction(() ->setFile(path)));
    }

    public Single<Map.Entry<String,Integer>> getMostFrequentWordSingle(){
        return Single.defer(() -> Single.just(getMostFrequentWord()));
    }

    public Single<Map.Entry<String,Integer>> getMostFrequent7CharWordSingle(){
        return Single.defer(() -> Single.just(getMostFrequent7CharWord()));
    }

    public Single<HashMap<String,Integer>> getScrabbleHighestScoringWordSingle(){
        return Single.defer(() -> Single.just(getScrabbleHighestScoringWord()));
    }
}
