package com.example.pnattawut.practice_androidservice.model;

import android.util.Log;

/**
 * Created by PNattawut on 20-Aug-17.
 */

public class Word {
    private String word;
    private int index;
    private StringBuilder wordStat;

    private static Word instanceWord;
    public static Word getInstance(){
        if(instanceWord == null) instanceWord = new Word();
        return instanceWord;
    }

    public Word() {
        this.wordStat = new StringBuilder();
    }

    public void genWord(){
        index += 1;
        this.word = String.valueOf(new Character((char)((int)(Math.random()*(122-97+1)+97))));
        wordStat.append(this.word);
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public StringBuilder getWordStat() {
        return wordStat;
    }

    public void setWordStat(StringBuilder wordStat) {
        this.wordStat = wordStat;
    }

    public static void main(String[] args) {
        Word.getInstance().genWord();
        Word.getInstance().genWord();
        System.out.println(Word.getInstance().toString());
    }

    @Override
    public String toString() {
        return word+" : "+index+" : ";
    }
}
